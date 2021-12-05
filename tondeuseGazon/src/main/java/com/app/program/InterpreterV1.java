package com.app.program;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.app.machine.GrassGutterI;
import com.app.machine.context.Command;
import com.app.program.ParserI.Token;
import com.app.shared.InstructionEum;
import com.app.shared.InstructionTypeEum;
import com.app.shared.OrientationEum;
import com.app.shared.Position;
import com.app.shared.PositionOriented;
import com.app.shared.VersionOfInterface;
import com.app.workingArea.WorkingAreaI;

public class InterpreterV1 extends InterpreterAbstract<TokenTypeV1, List<PositionOriented>> {

	private static Logger LOGGER = Logger.getLogger(InterpreterV1.class);
	private String logLineChkPos = "Check Position of %s in %s --> %s";
	private String logLineInstQueue = "--> Current Pnstructions left in the Queue : %s";
	private String logLineCurrPos = "--> Current Postion : %s";

	public InterpreterV1(ParserI<TokenTypeV1> parser, GrassGutterI grassGutter, WorkingAreaI workingArea) {
		super(VersionOfInterface.V1, parser, grassGutter, workingArea);
	}

	@Override
	public Optional<List<PositionOriented>> call() {

		List<PositionOriented> result = new ArrayList<>();
		getParser().parse();
		Deque<Token<TokenTypeV1, Object>> queue = getParser().getTokens();
		Token<TokenTypeV1, Object> currentToken = null;

		while (!queue.isEmpty()) {
			currentToken = queue.poll();

			switch (currentToken.getType()) {
			case TKN_001_RECTANGULAR_WORKING_AREA_DIM:
				getWorkingArea().seHightPoint((Position) currentToken.getValue());
				break;
			case TKN_010_INITIAL_POSITION:
				getGrassGutter().setCurrentPosition((Position) currentToken.getValue());
				checkPosition(checkPositionOK(getWorkingArea().getHightPoint(), getGrassGutter().getCurrentPosition()));
				break;
			case TKN_020_INITIAL_POSITION_ORIENTATION:
				getGrassGutter().setCurrentOrientation((OrientationEum) currentToken.getValue());
				break;
			case TKN_030_MOVE_INSTRUCTION:
				getGrassGutter().setMoveInstruction((Queue<InstructionEum>) currentToken.getValue());

				LOGGER.debug("start new Grass Gutter in the Working Area"+getWorkingArea().getHightPoint());

				LOGGER.debug(String.format(logLineInstQueue,  getGrassGutter().getMoveInstructionAsDescription()));
				LOGGER.debug(String.format(logLineCurrPos,  getGrassGutter().getCurrentPosition()));

				Command state = getGrassGutter();
				Position nextPosition = state.doAction(InstructionTypeEum.INIT);
				do {
					LOGGER.debug(String.format(logLineInstQueue,  getGrassGutter().getMoveInstructionAsDescription()));
					LOGGER.debug(String.format(logLineCurrPos,  getGrassGutter().getCurrentPosition()));
					
					if (checkPositionOK(getWorkingArea().getHightPoint(), nextPosition)) {
						nextPosition = state.doAction(InstructionTypeEum.MOVE);
					} else {
						nextPosition = state.doAction(InstructionTypeEum.ROTATE);
					}

				} while (nextPosition != null);

				result.add(getGrassGutter().getCurrentPosition());
				break;

			default:
				throw new SemanticException("Unknown Token : " + currentToken.getType());

			}
		}
		if (result.isEmpty()) {
			return Optional.ofNullable(null);
		}
		return Optional.of(result);
	}

	private void checkPosition(boolean checkPositionOK) throws SemanticException {
		if (!checkPositionOK) {
			throw new SemanticException("Initial position is out of the box");
		}
	}

	private boolean checkPositionOK(Position WorkHightPosition, Position grassPosition) {

		int X = WorkHightPosition.getX();
		int Y = WorkHightPosition.getY();

		int x = grassPosition.getX();
		int y = grassPosition.getY();

		if (X < x || Y < y || x < 0 || y < 0) {

			LOGGER.debug(String.format(logLineChkPos, grassPosition, WorkHightPosition, "Not Ok"));
			return false;
		}

		LOGGER.debug(String.format(logLineChkPos, grassPosition, WorkHightPosition, "Ok"));

		return true;

	}

	@Override
	public InterpreterI<List<PositionOriented>> importFile(String path) throws FileNotFoundException {
		getParser().uploadFile(path);
		return this;
	}

}

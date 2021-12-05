package com.app.machine;

import java.util.Queue;

import org.apache.log4j.Logger;

import com.app.shared.InstructionEum;
import com.app.shared.InstructionTypeEum;
import com.app.shared.OrientationEum;
import com.app.shared.Position;
import com.app.shared.PositionOriented;

public class GrassCutterV1 implements GrassGutterI {

	private static Logger LOGGER = Logger.getLogger(GrassCutterV1.class);
	private String logLineActionExecute="Execute";
	private String logLineActionAttemp="Attemp";
	
	private String logLineAction = "[Action] = %s, [%s] %s from : %s To %s";
	private PositionOriented currentPosition;
	private Position nextPosition;
	private Queue<InstructionEum> moveInstruction;

	private void actionMove() {
		if ((!moveInstruction.isEmpty()) && InstructionTypeEum.MOVE.contains(moveInstruction.peek())) {
			// setCurrentOrientation(getCurrentPosition().getCurrentOrientation().getNext(moveInstruction.poll()));
			InstructionEum action = moveInstruction.poll();
			Position next = getCurrentPosition().getCurrentOrientation().getNext(getCurrentPosition());

			LOGGER.debug(String.format(logLineAction, action, logLineActionAttemp, action, getCurrentPosition(), new PositionOriented(getCurrentPosition().getCurrentOrientation(),next)));
			setNextPosition(next);

		}

		if (moveInstruction.isEmpty()) {
			//setNextPosition(null);
		}
	}

	private void actionRotate() {
		while ((!moveInstruction.isEmpty()) && InstructionTypeEum.ROTATE.contains(moveInstruction.peek())) {

			InstructionEum action = moveInstruction.poll();
			OrientationEum next = getCurrentPosition().getCurrentOrientation().getNext(action);
			LOGGER.debug(String.format(logLineAction, action,logLineActionExecute, action, getCurrentPosition().getCurrentOrientation(), next));

			setCurrentOrientation(next);

		}

		if (moveInstruction.isEmpty()) {
			setNextPosition(null);
		}
	}

	@Override
	public Position doAction(InstructionTypeEum action) {

		if (InstructionTypeEum.INIT.equals(action)) {
			actionRotate();
			actionMove();
			return getNextPosition();
		} else if (InstructionTypeEum.MOVE.equals(action)) {
			confirmMove(getCurrentPosition().getCurrentOrientation(), getNextPosition());
			resetNextPosition();
			actionRotate();
			actionMove();
			return getNextPosition();
		} else if (InstructionTypeEum.ROTATE.equals(action)) {
			actionRotate();
			actionMove();
			return getNextPosition();
		}

		return null;

	}

	@Override
	public PositionOriented getCurrentPosition() {
		return currentPosition;
	}

	@Override
	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = new PositionOriented(currentPosition);
	}

	private void confirmMove(OrientationEum currentOrientation, Position currentPosition) {
		PositionOriented next = new PositionOriented(currentOrientation, currentPosition);
		LOGGER.debug(String.format(logLineAction, InstructionTypeEum.MOVE, logLineActionExecute, InstructionTypeEum.MOVE, getCurrentPosition(), next));
		this.currentPosition = next;
	}

	@Override
	public void setCurrentOrientation(OrientationEum currentOrientation) {
		getCurrentPosition().setCurrentOrientation(currentOrientation);
	}

	@Override
	public void setMoveInstruction(Queue<InstructionEum> moveInstruction) {
		this.moveInstruction = moveInstruction;
	}

	private void resetNextPosition() {
		setNextPosition(null);
	}

	private Position getNextPosition() {
		return nextPosition;
	}

	private void setNextPosition(Position nextPosition) {
		this.nextPosition = nextPosition;
	}

	@Override
	public String getMoveInstructionAsDescription() {
		return  this.moveInstruction.toString();
	}
}

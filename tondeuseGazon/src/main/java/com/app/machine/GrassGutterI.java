package com.app.machine;

import java.util.Queue;

import com.app.machine.context.Command;
import com.app.shared.InstructionEum;
import com.app.shared.OrientationEum;
import com.app.shared.Position;
import com.app.shared.PositionOriented;

public interface GrassGutterI extends Command {

	void setCurrentPosition(Position currentPosition);

	void setCurrentOrientation(OrientationEum currentOrientation);

	void setMoveInstruction(Queue<InstructionEum> moveInstruction);

	PositionOriented getCurrentPosition();

	String getMoveInstructionAsDescription();

}

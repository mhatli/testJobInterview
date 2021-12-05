package com.app.machine.context;

import com.app.shared.InstructionTypeEum;
import com.app.shared.Position;

public interface Command {
   public Position doAction(InstructionTypeEum action);
}
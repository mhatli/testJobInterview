package com.app.shared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum InstructionTypeEum  {
	INIT,MOVE(InstructionEum.MOVE),ROTATE(InstructionEum.LEFT, InstructionEum.RIGHT);
	
	private Set<InstructionEum> subElement= new HashSet<>();

	private InstructionTypeEum(InstructionEum... subs) {
		subElement.addAll(Arrays.asList(subs));
	}

	
	public boolean contains(InstructionEum sub) {
		return subElement.contains(sub);
	}
}
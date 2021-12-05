package com.app.shared;

import java.util.stream.IntStream;

/**
 * 
 * the values are ordered
 *
 */
public enum OrientationEum {
	WEST("W"), NORTH("N"), EAST("E"), SOUTH("S");

	String shortName;
	private OrientationEum(String shortName) {
		this.shortName=shortName;
	}
	
	public Position getNext(Position currentPosition) {
		int nextX  = currentPosition.getX();
		int nextY  =  currentPosition.getY();
		switch (this) {
		case NORTH: nextY += 1;break;
		case EAST: nextX += 1;break;
		case SOUTH:nextY -= 1;break;
		case WEST: nextX -= 1;break;
		default:break;
		}
		return  new Position(nextX, nextY);
	}
	
	public OrientationEum getNext(InstructionEum Instruction) {
		OrientationEum[] values = OrientationEum.values();
		int currIndex = find(values);

		if (InstructionEum.LEFT.equals(Instruction)) {
			if (currIndex != 0) {
				return values[currIndex - 1];
			} else {
				return values[values.length - 1];
			}
		} else if (InstructionEum.RIGHT.equals(Instruction)) {
			if (currIndex != values.length - 1) {
				return values[currIndex + 1];
			} else {
				return values[0];
			}
		}
		return null;
	}

	private int find(OrientationEum[] values) {
		return IntStream.range(0, OrientationEum.values().length).filter(i -> this.equals(values[i])).findFirst()
				.getAsInt();
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public String toString() {
		return getShortName();
	}
}
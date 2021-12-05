package com.app.shared;

public class PositionOriented extends Position  {

	private OrientationEum currentOrientation;

	public PositionOriented(Position currentPosition) {
		super(currentPosition);
	}
	public PositionOriented(OrientationEum currentOrientation, Position currentPosition) {
		super(currentPosition);
		setCurrentOrientation(currentOrientation);
	}

	public OrientationEum getCurrentOrientation() {
		return currentOrientation;
	}

	public void setCurrentOrientation(OrientationEum currentOrientation) {
		this.currentOrientation = currentOrientation;
	}
	
	public String toString() {
		return getX() + " " + getY() + " " + currentOrientation;
	}

}

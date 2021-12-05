package com.app.workingArea;

import com.app.shared.Position;

public class WorkingAreaRectangular implements WorkingAreaI {

	private Position hightPoint;

	@Override
	public Position getHightPoint() {
		return hightPoint;
	}

	@Override
	public void seHightPoint(Position maxPosition) {
		this.hightPoint = maxPosition;
	}
	
}

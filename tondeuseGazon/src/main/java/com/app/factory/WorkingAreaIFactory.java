package com.app.factory;

import com.app.shared.AbstractSingleton;
import com.app.shared.VersionOfInterface;
import com.app.workingArea.WorkingAreaI;
import com.app.workingArea.WorkingAreaRectangular;

public class WorkingAreaIFactory implements AbstractFactory<WorkingAreaI> {

	private WorkingAreaIFactory() {
	}

	private static final AbstractSingleton<WorkingAreaIFactory> objHolder = new AbstractSingleton<WorkingAreaIFactory>() {
		@Override
		protected WorkingAreaIFactory newObj() {
			return new WorkingAreaIFactory();
		}
	};

	public static WorkingAreaIFactory getInstance() {
		return objHolder.getInstance();
	}

	@Override
	public WorkingAreaI create(VersionOfInterface version) {
		if (VersionOfInterface.V1.equals(version)) {
			return new WorkingAreaRectangular();
		}
		return null;
	}
}
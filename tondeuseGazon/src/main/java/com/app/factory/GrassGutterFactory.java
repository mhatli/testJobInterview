package com.app.factory;

import com.app.machine.GrassCutterV1;
import com.app.machine.GrassGutterI;
import com.app.shared.AbstractSingleton;
import com.app.shared.VersionOfInterface;

public class GrassGutterFactory implements AbstractFactory<GrassGutterI> {

	private GrassGutterFactory() {
	}

	private static final AbstractSingleton<GrassGutterFactory> objHolder = new AbstractSingleton<GrassGutterFactory>() {
		@Override
		protected GrassGutterFactory newObj() {
			return new GrassGutterFactory();
		}
	};

	public static GrassGutterFactory getInstance() {
		return objHolder.getInstance();
	}

	@Override
	public GrassGutterI create(VersionOfInterface version) {
		if (VersionOfInterface.V1.equals(version)) {
			return new GrassCutterV1();
		}
		return null;
	}

}
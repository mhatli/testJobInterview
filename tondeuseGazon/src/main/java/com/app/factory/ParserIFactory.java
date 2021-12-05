package com.app.factory;

import com.app.program.ParserI;
import com.app.program.ParserV1;
import com.app.shared.AbstractSingleton;
import com.app.shared.VersionOfInterface;

public class ParserIFactory implements AbstractFactory<ParserI<?>> {

	private ParserIFactory() {
	}

	private static final AbstractSingleton<ParserIFactory> objHolder = new AbstractSingleton<ParserIFactory>() {
		@Override
		protected ParserIFactory newObj() {
			return new ParserIFactory();
		}
	};

	public static ParserIFactory getInstance() {
		return objHolder.getInstance();
	}

	@Override
	public ParserI<?> create(VersionOfInterface version) {
		if (VersionOfInterface.V1.equals(version)) {
			return new ParserV1();
		}
		return null;
	}
}
package com.app.factory;

import com.app.machine.GrassGutterI;
import com.app.program.InterpreterI;
import com.app.program.InterpreterV1;
import com.app.program.ParserI;
import com.app.program.TokenTypeV1;
import com.app.shared.AbstractSingleton;
import com.app.shared.VersionOfInterface;
import com.app.workingArea.WorkingAreaI;

public class InterpreterFactory implements AbstractFactory<InterpreterI<?>> {

	private InterpreterFactory() {
	}

	private static final AbstractSingleton<InterpreterFactory> objHolder = new AbstractSingleton<InterpreterFactory>() {
		@Override
		protected InterpreterFactory newObj() {
			return new InterpreterFactory();
		}
	};

	public static InterpreterFactory getInstance() {
		return objHolder.getInstance();
	}

	@Override
	public InterpreterI<?> create(VersionOfInterface version) {
		if (VersionOfInterface.V1.equals(version)) {
			GrassGutterI grassGutter = GrassGutterFactory.getInstance().create(version);
			WorkingAreaI workingArea = WorkingAreaIFactory.getInstance().create(version);
			ParserI<TokenTypeV1> parser = (ParserI<TokenTypeV1>) ParserIFactory.getInstance().create(version);
			
			return new InterpreterV1(parser, grassGutter, workingArea);
		}
		return null;
	}
}
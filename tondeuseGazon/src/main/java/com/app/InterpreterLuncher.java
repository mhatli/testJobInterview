package com.app;

import java.io.FileNotFoundException;

import com.app.factory.InterpreterFactory;
import com.app.program.InterpreterI;
import com.app.shared.VersionOfInterface;

public class InterpreterLuncher {

	public static InterpreterI<?> get(VersionOfInterface version, String pathToFile) throws FileNotFoundException {
		InterpreterI<?> interpreter = InterpreterFactory.getInstance().create(version);
		return interpreter.importFile(pathToFile);
	}
}
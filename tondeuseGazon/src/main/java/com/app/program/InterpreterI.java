package com.app.program;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.Callable;

import com.app.shared.VersionOfInterface;

public interface InterpreterI<T> extends Callable<Optional<T>> {

	VersionOfInterface getVersion();
	InterpreterI<T> importFile(String path) throws FileNotFoundException;
	public static class SemanticException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SemanticException() {
			super("Exception While performing semantic checks.");
		}

		public SemanticException(String message) {
			super(message);
		}

		public SemanticException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}

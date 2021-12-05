package com.app.program;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public interface ParserI<T> {

	void parse();

	Deque<Token<T, Object>> getTokens();

	void uploadFile(String path) throws FileNotFoundException;
	
	default void load(List<String> codelines, String pathTofile) throws IOException {
		codelines.addAll(Files.readAllLines(Paths.get(pathTofile)));
	}

	public static class LexicalException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LexicalException() {
			super("Exception While Lexical Tokenizing");
		}

		public LexicalException(String message) {
			super(message);
		}

		public LexicalException(String message, Throwable cause) {
			super(message, cause);
		}
	}


	public static class Token<T, V extends Object> {
		public Token(T type, V value) {
			this.type = type;
			this.value = value;
		}

		public final T type;
		public final V value;

		@Override
		public String toString() {
			return Objects.toString(value.toString());
		}
		public T getType() {
			return type;
		}

		public V getValue() {
			return value;
		}
	}

}

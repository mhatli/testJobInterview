package com.app.program;

import com.app.machine.GrassGutterI;
import com.app.shared.VersionOfInterface;
import com.app.workingArea.WorkingAreaI;

public abstract class InterpreterAbstract<T,R> implements InterpreterI<R> {

	private final VersionOfInterface version;

	private GrassGutterI grassGutter;
	private WorkingAreaI workingArea;
	protected ParserI<T> parser;

	private InterpreterAbstract(VersionOfInterface version) {
		super();
		this.version = version;
	}

	public InterpreterAbstract(VersionOfInterface version, ParserI<T> parser, GrassGutterI grassGutter,
			WorkingAreaI workingArea) {
		this(version);
		this.parser = parser;
		this.grassGutter = grassGutter;
		this.workingArea = workingArea;
	}

	protected GrassGutterI getGrassGutter() {
		return grassGutter;
	}

	protected WorkingAreaI getWorkingArea() {
		return workingArea;
	}

	protected ParserI<T> getParser() {
		return parser;
	}

	@Override
	public VersionOfInterface getVersion() {
		return version;
	}

}

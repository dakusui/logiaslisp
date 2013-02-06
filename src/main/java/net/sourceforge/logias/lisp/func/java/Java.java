package net.sourceforge.logias.lisp.func.java;

import net.sourceforge.logias.lisp.func.Func;

public abstract class Java extends Func {
	private boolean staticMode;

	public Java() {
		this(false);
	}
	public Java(boolean staticMode) {
		this.staticMode = staticMode;
	}
	
	boolean isStatic() {
		return staticMode;
	}
	
}

package net.sourceforge.logias.lisp.func.core;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;

public class Quote extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != 1) {
			throw new RuntimeException();
		}
		return params[0];
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}
}

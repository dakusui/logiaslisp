package net.sourceforge.logias.lisp.func.core;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;

public class Eval extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != 1) {
			throw new RuntimeException();
		}
		Sexp p1 = params[0];
		if (p1 == null) {
			throw new RuntimeException();
		}
		return eval(context, p1);
	}
}

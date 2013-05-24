package com.github.dakusui.logias.lisp.func.core;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Eq extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != 2) {
			throw new RuntimeException();
		}
		Sexp p1 = params[0];
		Sexp p2 = params[1];
		if (p1 == null || p2 == null) {
			throw new RuntimeException();
		}
		return p1.equals(p2) ? Sexp.T : Sexp.nil;
	}

}

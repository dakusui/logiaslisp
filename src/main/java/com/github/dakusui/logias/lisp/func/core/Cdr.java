package com.github.dakusui.logias.lisp.func.core;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Cdr extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != 1) {
			throw new RuntimeException();
		}
		return params[0].cdr();
	}

}

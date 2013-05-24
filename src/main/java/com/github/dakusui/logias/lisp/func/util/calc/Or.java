package com.github.dakusui.logias.lisp.func.util.calc;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Or extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		for (Sexp s : params) {
			if (eval(context, s) != Sexp.nil) {
				return Sexp.T;
			}
		}
		return Sexp.nil;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}	
}

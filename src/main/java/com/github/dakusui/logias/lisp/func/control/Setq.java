package com.github.dakusui.logias.lisp.func.control;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;
import com.github.dakusui.logias.lisp.s.Symbol;

public class Setq extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		Symbol p1 = params[0].asSymbol();
		
		Sexp v = eval(context, params[1]);
		context.bind(p1, v);
		return v;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}
}

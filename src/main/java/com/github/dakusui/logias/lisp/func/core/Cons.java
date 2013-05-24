package com.github.dakusui.logias.lisp.func.core;

import java.util.Arrays;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Pair;
import com.github.dakusui.logias.lisp.s.Sexp;


public class Cons extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length < 1) {
			throw new RuntimeException("Expected parameter length is at least 1, but it was <" + params.length + ">" + Arrays.toString(params));
		}
		Sexp p1 = params[0];
		Sexp p2 = params.length > 1 ? params[1] : Sexp.nil;
		return new Pair(p1, p2);
	}

}

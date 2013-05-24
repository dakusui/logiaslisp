package com.github.dakusui.logias.lisp.func.util;

import java.util.Arrays;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;

import static java.lang.String.format;

public class Print extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params.length != 1) {
			throw new RuntimeException(format("The length of 'params' should be 1, but <%s>(%s)", params.length, Arrays.toString(params)));
		}
		System.out.println(params[0].print());
		return params[0];
	}

}

package net.sourceforge.logias.lisp.func.core;

import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

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

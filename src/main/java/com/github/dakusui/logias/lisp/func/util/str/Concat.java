package com.github.dakusui.logias.lisp.func.util.str;

import com.github.dakusui.logias.lisp.func.util.Op;
import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Concat extends Op {

	@Override
	protected Sexp calc(Literal... params) {
		String ret = "";
		for (Literal l : params) {
			ret += l.stringValue();
		}
		return new Literal(ret);
	}

}

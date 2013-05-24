package com.github.dakusui.logias.lisp.func.util.calc;

import com.github.dakusui.logias.lisp.func.util.Op;
import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Sexp;

public class BitOr extends Op {
	protected Sexp calc(Literal... params) {
		boolean first = true;
		long value = 0;
		for (Literal cur : params) {
			if (first) {
				value = cur.longValue();
				first = false;
			} else {
				value |= cur.longValue();
			}
		}
		Sexp ret = new Literal(value);
		return ret;
	}
}

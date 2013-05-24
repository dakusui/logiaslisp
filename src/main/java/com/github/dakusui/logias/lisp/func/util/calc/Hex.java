package com.github.dakusui.logias.lisp.func.util.calc;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Pair;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Hex extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params.length == 0) {
			throw new RuntimeException();
		}
	    if (params.length == 1) {
			return hex(context, params[0]);
		} else {
			Pair ret = null;
			Pair cur = null;
			for (Sexp sexp : params) {
				if (cur != null) {
					Pair tmp = new Pair(hex(context, sexp), Sexp.nil);
					cur.cdr(tmp);
					cur = tmp;
				} else {
					ret = cur = new Pair(hex(context, sexp), Sexp.nil);
				}
			}
			return ret;
		}
	}
	
	Literal hex(Context context, Sexp sexp) {
		return new Literal(Long.parseLong(eval(context, sexp).asLiteral().stringValue(), 16));
	}
}

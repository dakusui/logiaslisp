package com.github.dakusui.logias.lisp.func.util.calc;

import com.github.dakusui.logias.lisp.func.util.Op;
import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Sexp;

import static java.lang.String.format;

public abstract class ComparisonOp extends Op {

	@Override
	protected Sexp calc(Literal... params) {
		if (params.length != 2) {
			throw new RuntimeException(format("This function expects 2 parameters, but <%s> parameters are given.", params.length));
		}
		return doComparison(params[0], params[1]);
	}

	protected abstract Sexp doComparison(Literal p1, Literal p2);
}

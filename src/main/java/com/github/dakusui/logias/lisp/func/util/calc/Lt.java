package com.github.dakusui.logias.lisp.func.util.calc;

import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Sexp;


public class Lt extends ComparisonOp {
	
	protected Sexp doComparison(Literal p1, Literal p2) {
		Sexp ret = p1.doubleValue() < p2.doubleValue() ? Sexp.T : Sexp.nil;
		return ret;
	}
}

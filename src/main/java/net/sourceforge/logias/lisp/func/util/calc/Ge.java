package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;


public class Ge extends ComparisonOp {
	
	protected Sexp doComparison(Literal p1, Literal p2) {
		Sexp ret = p1.doubleValue() >= p2.doubleValue() ? Sexp.T : Sexp.nil;
		return ret;
	}
}

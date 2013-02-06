package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.func.util.Op;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;


public class Minus extends Op {
	
	protected Sexp calc(Literal... params) {
		boolean first = true;
		double value = 0;
		for (Literal cur : params) {
			if (first) {
				value = cur.doubleValue();
				first = false;
			} else {
				value -= cur.doubleValue();
			}
		}
		Sexp ret = new Literal(value);
		return ret;
	}
}

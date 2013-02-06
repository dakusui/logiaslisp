package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.func.util.Op;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;

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

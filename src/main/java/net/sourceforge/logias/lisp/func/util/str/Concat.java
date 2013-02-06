package net.sourceforge.logias.lisp.func.util.str;

import net.sourceforge.logias.lisp.func.util.Op;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;

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

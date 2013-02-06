package net.sourceforge.logias.lisp.func.core;

import static java.lang.String.format;
import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.SexpIterator;
import net.sourceforge.logias.lisp.s.Symbol;

public class Lambda extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		UserFunc ret = new UserFunc();
		if (!Sexp.nil.equals(params[0])) {
			SexpIterator i = params[0].iterator().assumeList();
			while (i.hasNext()) {
				Sexp cur = i.next();
				if (cur instanceof Symbol) {
					ret.addParameter((Symbol)cur);
				} else {
					throw new RuntimeException(format("<%s> is not a symbol", cur));
				}
			}
		}
		ret.setFuncBody(params[1]);
		return ret;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}

}

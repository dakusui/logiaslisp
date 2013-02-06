package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;

public class Not extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null || params.length != 1) {
			throw new RuntimeException("This function can take only one parameter.");
		}
		return Sexp.nil.equals(params[0]) ? Sexp.T : Sexp.nil;
	}

}

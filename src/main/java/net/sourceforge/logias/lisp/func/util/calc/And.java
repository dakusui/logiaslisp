package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;

public class And extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		for (Sexp s : params) {
			if (eval(context, s) == Sexp.nil) {
				return Sexp.nil;
			}
		}
		return Sexp.T;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}	
}

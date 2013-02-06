package net.sourceforge.logias.lisp.func.control;

import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.SexpIterator;

public abstract class ControlFunc extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		Context childContext = context.createChild();
		Sexp vardefs = params[0];
		if (!vardefs.equals(Sexp.nil)) {
			if (vardefs.isAtom()) {
				throw new RuntimeException("Variable definitions in 'let' cannot be an atom.");
			}
			SexpIterator i = vardefs.iterator().assumeList();
			while (i.hasNext()) {
				Sexp cur = i.next();
				assign(childContext, cur);
			}
		}
		Sexp ret = perform(childContext, Arrays.copyOfRange(params, 1, params.length));
		return ret;
	}

	@Override
	public boolean evaluatesLazily() {
		return true;
	}
	
	protected abstract void assign(Context context, Sexp cur);
	protected abstract Sexp perform(Context context, Sexp... params);
}

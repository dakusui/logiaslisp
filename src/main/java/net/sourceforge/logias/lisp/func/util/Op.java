package net.sourceforge.logias.lisp.func.util;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;

public abstract class Op extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length < 2) {
			throw new RuntimeException(format("This function takes at least 2 parameters. But only <%s> given. (%s:%s)", params.length, this.getClass().getSimpleName(), Arrays.toString(params)));
		}
		List<Sexp> unexpectedSexpList = new LinkedList<Sexp>();
		List<Literal> parameterList = new LinkedList<Literal>();
		for (Sexp s : params) {
			if (!s.isAtom() || !(s instanceof Literal)) {
				unexpectedSexpList.add(s);
			} else {
				parameterList.add((Literal) s);
			}
		}
		if (unexpectedSexpList.size() > 0) {
			throw new RuntimeException(format("A literal is expected, but non-atoms:%s are given.", unexpectedSexpList));
		}
		return calc(parameterList.toArray(new Literal[0]));
	}
	
	protected abstract Sexp calc(Literal... p1);
}

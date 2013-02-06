package net.sourceforge.logias.lisp.func.control;

import static java.lang.String.format;
import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.Symbol;

public class Let extends ControlFunc {
	@Override
	protected Sexp perform(Context context, Sexp... params) {
		Sexp ret = Sexp.nil;
		
		for (Sexp cur : params) {
			ret = eval(context, cur);
		}
		return ret;
	}

	@Override
	protected void assign(Context context, Sexp cur) {
		if (cur.isAtom()) {
			throw new RuntimeException("Each element in variable definition section cannot be an atom.");
		}
		Sexp s = cur.car();
		if (!(s instanceof Symbol)) {
			throw new RuntimeException(format("<%s> is not a symbol.", s));
		}
		Symbol symbol = (Symbol) s;
		Sexp v = cur.cdr();

		context.bind(symbol.name(), valueToAssign(context, v));
	}

	private Sexp valueToAssign(Context context, Sexp v) {
		if (v.isAtom() || !Sexp.nil.equals(v.cdr())) {
			throw new RuntimeException(
				format("Each element in variable definition section must be a list whose length is 2, but <%s> was extra.", 
						v.cdr())
			);
		}
		Sexp valueToAssign = eval(context, v.car());
		return valueToAssign;
	}
}

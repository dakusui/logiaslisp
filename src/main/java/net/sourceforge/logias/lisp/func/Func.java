package net.sourceforge.logias.lisp.func;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.SexpIterator;
import net.sourceforge.logias.lisp.s.Symbol;
import static java.lang.String.format;

public abstract class Func extends net.sourceforge.logias.lisp.s.Atom {
	public Func() {
		super(null);
		this.value = this.getClass().getName();
	}
	
	public boolean evaluatesLazily() {
		return false;
	}

	protected Sexp eval(Context context, Sexp s) {
		if (s == null) {
			throw new RuntimeException();
		}
		if (s.isAtom()) {
			if (s instanceof Symbol) {
				Sexp ret = context.lookup(((Symbol)s).name());
				if (ret == null) {
					throw new RuntimeException(format("Symbol <%s> was not found in the current symbol pool.", s));
				}
				return ret;
			}
			return s;
		}
		Sexp car = s.car();
		if (!(car instanceof Symbol)) {
			throw new RuntimeException(format("Symbol was expected, but <%s> was given", car));
		}
		String symbolName = ((Symbol)car).name();
		Sexp f = context.lookup(symbolName);
		if (f == null) {
			throw new RuntimeException(format("Symbol <%s> was not found in the current symbol pool.", symbolName));
		}
		if (!(f instanceof Func)) {
			throw new RuntimeException(String.format("A function object is expected, but <%s:%s> is given.", f, f.getClass()));
		}
		Func func = (Func) f;
		List<Sexp> params = new LinkedList<Sexp>();
		Sexp sparams = s.cdr();
		if (!Sexp.nil.equals(sparams)) {
			SexpIterator i = sparams.iterator();
			while (i.hasNext()) {
				Sexp cur = i.next();
				if (!func.evaluatesLazily()) {
					cur = eval(context, cur);
				}
				if (i.hasNext()) {
					params.add(cur);
				}
			}
		} else {
			
		}
		return func.invoke(context, params.toArray(new Sexp[0]));
	}

	abstract public Sexp invoke(Context context, Sexp... params);
}

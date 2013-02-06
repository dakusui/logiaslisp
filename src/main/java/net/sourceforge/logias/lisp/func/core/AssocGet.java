package net.sourceforge.logias.lisp.func.core;

import java.util.Iterator;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

public class AssocGet extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != 2) {
			throw new RuntimeException();
		}
		Sexp p1 = params[0];
		Sexp p2 = params[1];
		if (p1 == null || p2 == null) {
			throw new RuntimeException();
		}
		Iterator<Sexp> i = p1.iterator().assumeList();
		String key = p2.asAtom().stringValue();
		Sexp ret = Sexp.nil;
		while (i.hasNext()) {
			Pair pair = i.next().asPair();
			if (key.equals(pair.car().asLiteral().stringValue())) {
				ret = pair.cdr(); 
				break;
			}
		}
		return ret;
	}

}

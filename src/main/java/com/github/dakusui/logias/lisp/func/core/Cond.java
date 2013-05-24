package com.github.dakusui.logias.lisp.func.core;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;
import com.github.dakusui.logias.lisp.s.SexpIterator;

public class Cond extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... sexp) {
		Sexp ret = Sexp.nil;
		boolean done = false;
		for (Sexp cur : sexp) {
			if (cur == null || cur.isAtom()) {
				throw new RuntimeException(String.format("Atom (<%s>) is not valid here.", cur));
			}
			if (done) {
				break;
			}
			SexpIterator i = cur.iterator().assumeList();
			boolean firstTime = true;
			while (i.hasNext()) {
				ret = eval(context, i.next());
				if (firstTime) {
					if (Sexp.nil.equals(ret)) {
						break;
					} else {
						done = true;
					}
					firstTime = false;
				}
			}
		}
		return ret;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}

}

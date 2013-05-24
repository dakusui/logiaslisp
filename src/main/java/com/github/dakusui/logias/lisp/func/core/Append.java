package com.github.dakusui.logias.lisp.func.core;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Pair;
import com.github.dakusui.logias.lisp.s.Sexp;

public class Append extends Func {

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
		Sexp cur = null;
		if (p1.isAtom()) {
			cur = new Pair(p1, Sexp.nil);
		} else {
			cur = p1.asPair();
		}
		Sexp prev = null;
		while (!Sexp.nil.equals(cur.cdr())) {
			cur = cur.cdr();
			if (cur.isAtom()) {
				Pair tmp = new Pair(cur, Sexp.nil);
				prev.asPair().cdr(tmp);
				cur = tmp;
				break;
			}
			prev = cur;
		}
		if (Sexp.nil.equals(cur.cdr())) {
			cur.asPair().cdr(p2);
		} else {
			throw new RuntimeException();
		}
		return p1;
	}

}

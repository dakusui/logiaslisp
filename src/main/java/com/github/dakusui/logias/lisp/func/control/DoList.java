package com.github.dakusui.logias.lisp.func.control;

import java.util.Iterator;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Sexp;
import com.github.dakusui.logias.lisp.s.SexpIterator;
import com.github.dakusui.logias.lisp.s.Symbol;


public class DoList extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		context = context.createChild();
		Sexp ret = Sexp.nil;
		SexpIterator ip = params[0].iterator().assumeList();
		Symbol cur = ip.next().asSymbol();
		Iterator<Sexp> i = eval(context, ip.next()).iterator().assumeList();
		while (i.hasNext()) {
			Sexp v = i.next();
			context.bind(cur, v);
			for (int j = 1; j < params.length; j++) {
				Sexp s = params[j];
				ret = eval(context, s);
			}
		}
		return ret;
	}

	@Override
	public boolean evaluatesLazily() {
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		Iterator<Sexp> i = Sexp.nil.iterator().assumeList();
		while (i.hasNext()) {
			Sexp cur = i.next();
			System.out.println(cur);
		}
		System.out.println("bye");
	}
}

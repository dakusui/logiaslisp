package com.github.dakusui.logias.lisp.func.control;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.s.Pair;
import com.github.dakusui.logias.lisp.s.Sexp;
import com.github.dakusui.logias.lisp.s.SexpIterator;
import com.github.dakusui.logias.lisp.s.Symbol;


public class Do extends ControlFunc {
	ThreadLocal<List<Pair>> updaters = new ThreadLocal<List<Pair>>();
	
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		this.updaters.set(new LinkedList<Pair>());
		return super.invoke(context, params);
	}
	
	@Override
	protected Sexp perform(Context context, Sexp... params) {
		if (params == null || params.length < 2) {
			throw new RuntimeException();
		}
		SexpIterator i = params[0].iterator().assumeList();
		if (!i.hasNext()) {
			throw new RuntimeException();
		}
		
		Sexp cond = i.next();
		Sexp[] statements = Arrays.copyOfRange(params, 1, params.length);
		List<Pair> updaters = this.updaters.get();
		while (Sexp.nil.equals(eval(context, cond))) {
			for (Sexp cur : statements) {
				eval(context, cur);
			}
			for (Pair pair : updaters) {
				Symbol s = (Symbol) pair.car();
				context.bind(s.name(), eval(context, pair.cdr()));
			}
		}
		
		Sexp ret = Sexp.nil;
		while (i.hasNext()) {
			ret = eval(context, i.next());
		}
		return ret;
	}

	@Override
	protected void assign(Context context, Sexp cur) {
		if (cur.isAtom()) {
			throw new RuntimeException("Each element in variable definition section cannot be an atom.");
		}
		SexpIterator i = cur.iterator().assumeList();
		if (!i.hasNext()) {
			throw new RuntimeException();
		}
		Sexp s = i.next();
		if (!(s instanceof Symbol)) {
			throw new RuntimeException(format("<%s> is not a symbol.", s));
		}
		Symbol symbol = (Symbol) s;
		
		if (!i.hasNext()) {
			throw new RuntimeException(format("Initial value for <%s> is not given.", symbol));
		}
		Sexp initialValue = i.next();
		context.bind(symbol.name(), eval(context, initialValue));
		
		List<Pair> updaters = this.updaters.get();
		if (i.hasNext()) {
			Sexp updater = i.next();
			updaters.add(new Pair(symbol, updater));
			if (i.hasNext()) {
				throw new RuntimeException("Too many members for a list in variable definition section.");
			}
		}
	}
}

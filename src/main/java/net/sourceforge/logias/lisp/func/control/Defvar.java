package net.sourceforge.logias.lisp.func.control;

import static java.lang.String.format;

import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.Symbol;

public class Defvar extends Func {
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params.length != 2) {
			throw new RuntimeException(format("The length of 'params' should be 2, but <%s>(%s)", params.length, Arrays.toString(params)));
		}
		Symbol p1 = params[0].asSymbol();
		
		Sexp v = eval(context, params[1]);
		Context.ROOT.bind(p1, v);
		return p1;
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}

}

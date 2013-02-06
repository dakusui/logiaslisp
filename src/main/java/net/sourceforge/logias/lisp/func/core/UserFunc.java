package net.sourceforge.logias.lisp.func.core;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.Symbol;

public class UserFunc extends Func {
	List<Symbol> symbols = new LinkedList<Symbol>();
	Sexp funcBody = Sexp.nil;
	
	@Override
	public Sexp invoke(Context context, Sexp... params) {
		Context childContext = context.createChild();
		if (params == null) {
			throw new RuntimeException();
		}
		if (params.length != symbols.size()) {
			throw new RuntimeException(format("<%d> paramsters are excpected, but <%d> parameter(s) are given.(%s)", symbols.size(), params.length, Arrays.toString(params)));
		}
		int i = 0;
		for (Symbol symbol : symbols) {
			childContext.bind(symbol, params[i]);
			i++;
		}
		return eval(childContext, funcBody);
	}

	public void addParameter(Symbol param) {
		this.symbols.add(param);
	}
	
	public void setFuncBody(Sexp funcBody) {
		this.funcBody = funcBody;
	}
}

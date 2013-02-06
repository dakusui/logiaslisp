package net.sourceforge.logias.lisp.func.util.calc;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;

public class BitShift extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null || params.length != 2) {
			throw new RuntimeException();
		}
		int numShift = (int)params[0].asLiteral().longValue();
		long l = params[1].asLiteral().longValue();
		long value = 0;
		if (numShift > 0) {
			value = l << numShift;
		} else {
			value = l >> -numShift;
		}
		return new Literal(value);
	}

}

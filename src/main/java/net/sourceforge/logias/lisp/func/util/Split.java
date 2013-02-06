package net.sourceforge.logias.lisp.func.util;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.Func;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

public class Split extends Func {

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		char[] chars = params[0].asAtom().stringValue().toCharArray();
		if (chars.length == 0) {
			return Sexp.nil;
		}
		return toList(chars, 0);
	}
	
	private Sexp toList(char[] chars, int fromIndex) {
		if (fromIndex == chars.length) {
			return Sexp.nil;
		}
		return new Pair(new Literal(new Character(chars[fromIndex])), toList(chars, fromIndex + 1));
	}
	
}

package com.github.dakusui.logias.lisp.func.util;

import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.func.Func;
import com.github.dakusui.logias.lisp.s.Literal;
import com.github.dakusui.logias.lisp.s.Pair;
import com.github.dakusui.logias.lisp.s.Sexp;

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

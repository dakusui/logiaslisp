package net.sourceforge.logias.lisp.func.java;

import java.lang.reflect.Field;

import net.sourceforge.logias.lisp.s.Sexp;

public class Set extends Accessor {

	public Set(boolean b) {
		super(b);
	}

	@Override
	protected Sexp perform(Object target, Field field, Object valueToAssign) {
		try {
			field.set(target, valueToAssign);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return Sexp.nil;
	}

	@Override
	protected int numParams() {
		return 3;
	}

}

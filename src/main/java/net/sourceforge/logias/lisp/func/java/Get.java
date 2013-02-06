package net.sourceforge.logias.lisp.func.java;

import java.lang.reflect.Field;

import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;

public class Get extends Accessor {
	public Get(boolean b) {
		super(b);
	}
	@Override
	protected Sexp perform(Object target, Field field, Object valueToAssign) {
		Sexp ret = Sexp.nil;
		try {
			Object v;
			if (isStatic()) {
				v = field.get(null);
			} else {
				v = field.get(target);
			}
			if (v instanceof Boolean) {
				if (Boolean.TRUE.equals(v)) {
					ret = Sexp.T;
				} else {
					ret = Sexp.nil;
				}
			} else {
				ret = new Literal(v);
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return ret;
	}
	
	@Override
	protected int numParams() {
		return 2;
	}

}

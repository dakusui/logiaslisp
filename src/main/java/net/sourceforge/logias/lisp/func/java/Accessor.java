package net.sourceforge.logias.lisp.func.java;

import java.lang.reflect.Field;
import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Sexp;
import static java.lang.String.format;
public abstract class Accessor extends Java {

	public Accessor(boolean b) {
		super(b);
	}

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		if (params == null || params.length != numParams()) {
			throw new RuntimeException(format("<%s>, <%s>",  Arrays.toString(params), this.getClass().getSimpleName()));
		}
		Sexp ret = Sexp.nil;
		Object target = params[0].asAtom().value();
		Field field = getField(target, params[1]);
		Object valueToAssign = params.length > 2 ? params[2].asAtom().value() : null;
		
		ret = perform(target, field, valueToAssign);
		return ret;
	}

	protected abstract int numParams();

	protected Field getField(Object target, Sexp sexp) {
		Field ret = null;
		try {
			if (isStatic()) {
				ret = Class.forName(target.toString()).getField(sexp.asAtom().stringValue());
			} else {
				ret = target.getClass().getField(sexp.asAtom().stringValue());
			}
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return ret;
	}

	abstract protected Sexp perform(Object target, Field field, Object valueToAssign);
}

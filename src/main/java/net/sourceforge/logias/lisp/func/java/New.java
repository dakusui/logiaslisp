package net.sourceforge.logias.lisp.func.java;

import static java.lang.String.format;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Atom;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

public class New extends Exec {
	public New(boolean b) {
		super(b);
	}

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		Sexp ret = Sexp.nil;
		String className = ((Atom)eval(context, params[0])).stringValue();
		try {
			Class<?> klazz = Class.forName(className);
			Sexp[] rest = Arrays.copyOfRange(params, 1, params.length);
			for (int i = 0 ; i < rest.length; i++) {
				Sexp cur = rest[i];
				if (cur.isAtom()) {
					rest[i] = eval(context, cur);
				} else {
					Pair p = cur.asPair();
					rest[i] = new Pair(eval(context, p.car()), eval(context, p.get(1)));
				}
			}
			
			Class<?>[] paramTypesForConstructor = extractParamTypes(context, rest);
			Object[] paramsForConstructor = extractParams(context, rest);
			Constructor<?> constructor = this.getConstructor(klazz, paramTypesForConstructor);
			Object value = constructor.newInstance(paramsForConstructor);
			ret = new Literal(value);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		} catch (InstantiationException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		} catch (SecurityException e) {
			throw new RuntimeException(format("Filed to load a class:<%s>", className), e);
		}
		return ret;
	}
	
	protected Constructor<?> getConstructor(Class<?> klazz, Class<?>[] paramTypesForMethod) {
		if (klazz == null) {
			throw new RuntimeException(format("Constructor is not found"));
		}
		Constructor<?> ret = null;
		try {
			ret = klazz.getConstructor(paramTypesForMethod);
		} catch (NoSuchMethodException e) {
			for (Constructor<?> c : klazz.getDeclaredConstructors()) {
				if (isCompatibleWith(c, paramTypesForMethod)) {
					if (ret == null) {
						ret = c;
					} else {
						throw new RuntimeException(format("There are more than one compatible constructors:<<init>>(%s)", (Object)paramTypesForMethod));
					}
				}
			}
		}
		return ret;
	}
	
	protected boolean isCompatibleWith(Constructor<?> c, Class<?>[] paramTypes) {
		Class<?>[] actualParamTypes = c.getParameterTypes();
		if (actualParamTypes.length != paramTypes.length) {
			return false;
		}
		int i = 0;
		for (Class<?> p : actualParamTypes) {
			if (!p.isAssignableFrom(paramTypes[i])) {
				return false;
			}
			i++;
		}
		return true;
	}

}
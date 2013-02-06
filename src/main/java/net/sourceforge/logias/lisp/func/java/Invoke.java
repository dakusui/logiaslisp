package net.sourceforge.logias.lisp.func.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Atom;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

import static java.lang.String.format;

public class Invoke extends Exec {
	public Invoke(boolean b) {
		super(b);
	}

	@Override
	public Sexp invoke(Context context, Sexp... params) {
		System.out.println("===" + this.getClass().getSimpleName() + "===");
		Sexp ret = Sexp.nil;
		Object obj = ((Atom)eval(context, params[0])).value();
		String methodName = ((Atom)eval(context, params[1])).stringValue();
		try {
			Class<?> klazz = null;
			if (isStatic()) {
				klazz = Class.forName(obj.toString());
			} else {
				klazz = obj.getClass();
			}
			
			Sexp[] rest = Arrays.copyOfRange(params, 2, params.length);
			for (int i = 0 ; i < rest.length; i++) {
				Sexp cur = rest[i];
				if (cur.isAtom()) {
					rest[i] = eval(context, cur);
				} else {
					Pair p = cur.asPair();
					rest[i] = new Pair(eval(context, p.car()), eval(context, p.get(1)));
				}
			}
			
			Class<?>[] paramTypesForMethod = extractParamTypes(context, rest);
			Object[] paramsForMethod = extractParams(context, rest);
			Method method;
			method = getMethod(klazz, methodName, paramTypesForMethod); 
			Object value = method.invoke(obj, paramsForMethod);
			if (value instanceof Boolean) {
				if (Boolean.TRUE.equals(value)) {
					ret = Sexp.T;
				} else {
					ret = Sexp.nil;
				}
			} else {
				ret = new Literal(value);
			}
		} catch (SecurityException e) {
			throw new RuntimeException("Failed to invoke method.", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Failed to invoke method.", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Failed to invoke method.", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Failed to invoke method.", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to invoke method.", e);
		}
		return ret;
	}
	
	protected Method getMethod(Class<?> klazz, String name, Class<?>[] paramTypesForMethod) {
		if (klazz == null) {
			throw new RuntimeException(format("Method:<%s> is not found", name));
		}
		Method ret = null;
		try {
			ret = klazz.getMethod(name, paramTypesForMethod);
		} catch (NoSuchMethodException e) {
			for (Method m : klazz.getDeclaredMethods()) {
				if (!m.getName().equals(name)) {
					continue;
				}
				if (isCompatibleWith(m, paramTypesForMethod)) {
					if (ret == null) {
						ret = m;
					} else {
						throw new RuntimeException(format("There are more than one compatible methods:%s(%s)", name, paramTypesForMethod));
					}
				}
			}
		}
		return ret;
	}
	
	protected boolean isCompatibleWith(Method m, Class<?>[] paramTypes) {
		Class<?>[] actualParamTypes = m.getParameterTypes();
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
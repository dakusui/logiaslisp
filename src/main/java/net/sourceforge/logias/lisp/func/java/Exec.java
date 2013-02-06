package net.sourceforge.logias.lisp.func.java;

import static java.lang.String.format;

import java.util.LinkedList;
import java.util.List;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;

public abstract class Exec extends Java {
	
	public Exec(boolean b) {
		super(b);
	}

	protected Object[] extractParams(Context context, Sexp[] rest) {
		List<Object> tmp = new LinkedList<Object>();
		for (Sexp cur : rest) {
			Object v = null;
			if (cur.isAtom()) {
				if (Sexp.nil.equals(cur)) {
					v = Boolean.FALSE;
				} else if (Sexp.T.equals(cur)) {
					v = Boolean.TRUE;
				} else {
					v = cur.asAtom().value();
				}
			} else if (cur instanceof Pair) {
				v = cur.asPair().get(1).asAtom().value();
			} else {
				throw new RuntimeException();
			}
			tmp.add(v);
		}
		return tmp.toArray();
	}

	protected Class<?>[] extractParamTypes(Context context, Sexp[] rest) {
		List<Class<?>> tmp = new LinkedList<Class<?>>();
		int i = 0;
		for (Sexp cur : rest) {
			i++;
			if (cur.isAtom()) {
				Object obj = cur.asAtom().value();
				if (obj == null) {
					int j = i < 20 ? i : i % 10;
					String th = j == 1 ? "st"
							           : j == 2 ? "nd"
							        		    : j == 3 ? "rd"
							        		             : "th";
					throw new RuntimeException(format("In case the value is null, you have to specify the type of a parameter variable.(%d%s parameter for function:<%s>)", i, th, this));
				}
				Class<?> type = obj.getClass();
				tmp.add(type);
			} else if (cur instanceof Pair){
				Sexp car = cur.car();
				String typeName = car.asAtom().stringValue();
				try {
					if ("char".equals(typeName)) {
						tmp.add(Character.TYPE);
					} else if ("byte".equals(typeName)) {
						tmp.add(Byte.TYPE);
					} else if ("short".equals(typeName)) {
						tmp.add(Short.TYPE);
					} else if ("int".equals(typeName)) {
						tmp.add(Integer.TYPE);
					} else if ("long".equals(typeName)) {
						tmp.add(Long.TYPE);
					} else if ("float".equals(typeName)) {
						tmp.add(Float.TYPE);
					} else if ("double".equals(typeName)) {
						tmp.add(Double.TYPE);
					} else {
						tmp.add(Class.forName(typeName));
					}
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException();
			}
		}
		return tmp.toArray(new Class<?>[0]);
	}
	
	@Override
	public boolean evaluatesLazily() {
		return true;
	}

}
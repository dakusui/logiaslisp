package com.github.dakusui.logias.lisp.func.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Example {
	public abstract int hashCode();
	public static void main(String... args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		System.out.println("".getClass().getMethod("contains", CharSequence.class));
		System.out.println("".getClass().getDeclaredMethod("contains", CharSequence.class));
		System.out.println(Example.class.getMethod("hashCode"));
		System.out.println(Example.class.getDeclaredMethod("hashCode").invoke(new Example(){
			public int hashCode() {
				return 0;
			}
		}
		));
		Method m = System.class.getMethod("currentTimeMillis");
		System.out.println(m.invoke(""));
		System.out.println(100);
	}
	
}

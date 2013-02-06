package net.sourceforge.logias.lisp.s;

import com.google.gson.JsonElement;


public interface Sexp {
	public static enum Type {
		Atom,
		Pair,
	}
	public static final Sexp T = new Atom("T") {
		@Override public String stringValue() {
			return "$T";
		}
	};
	public static final Sexp nil = new Atom("nil") {
		@Override public String stringValue() {
			return "$nil";
		}
	};
	boolean isAtom();
	Sexp car();
	Sexp cdr();
	Type type();
	String print();
	SexpIterator iterator();
	Pair asPair();
	Atom asAtom();
	Literal asLiteral();
	Symbol asSymbol();
	JsonElement toJson();
}

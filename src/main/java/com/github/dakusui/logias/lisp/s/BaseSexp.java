package com.github.dakusui.logias.lisp.s;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public abstract class BaseSexp implements Sexp {
	@Override
	public JsonElement toJson() {
		JsonArray ret = new JsonArray();
		toJson(ret);
		return ret;
	}
	abstract protected void toJson(JsonArray buf);
	
	@Override
	public String print() {
		StringBuffer b = new StringBuffer();
		print(b);
		return b.toString();
	}
	protected abstract void print(StringBuffer cur);

	@Override
	public String toString() {
		return print();
	}

	public Pair asPair() {
		if (this instanceof Pair) {
			return (Pair) this;
		}
		throw new RuntimeException(String.format("This:<%s> is not a pair.", this));
	}
	
	public Atom asAtom() {
		if (this instanceof Atom) {
			return (Atom) this;
		}
		throw new RuntimeException(String.format("This:<%s> is not an atom.", this));
	}
	
	public Literal asLiteral() {
		if (this instanceof Literal) {
			return (Literal) this;
		}
		throw new RuntimeException(String.format("This:<%s> is not a literal.", this));
	}
	
	public Symbol asSymbol() {
		if (this instanceof Symbol) {
			return (Symbol) this;
		}
		throw new RuntimeException("This object is not a symbol:<" + this + ">");
	}
}

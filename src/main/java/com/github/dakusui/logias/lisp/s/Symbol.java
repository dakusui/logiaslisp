package com.github.dakusui.logias.lisp.s;

public class Symbol extends com.github.dakusui.logias.lisp.s.Atom {
	public Symbol(String name) {
		super(name);
	}

	public String name() {
		return this.value.toString();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		return super.equals(anotherObject) && (anotherObject instanceof Symbol); 
	}
	
	@Override
	public String toString() {
		return this.value == null ? null: ("$" + this.name());
	}
	
}

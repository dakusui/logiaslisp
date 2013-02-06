package net.sourceforge.logias.lisp.s;

import static java.lang.String.format;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public abstract class Atom extends BaseSexp {
	protected Object value;
	
	public Atom(Object value) {
		this.value = value;
	}

	@Override
	public Sexp car() {
		throw new RuntimeException(format("'car' cannot be used for atoms:<%s>", this));
	}

	@Override
	public Sexp cdr() {
		throw new RuntimeException(format("'cdr' cannot be used for atoms:<%s>", this));
	}

	@Override
	public boolean equals(Object anotherObject) {
		if (anotherObject == null) {
			return false;
		}
		if (!(anotherObject instanceof Atom)) {
			return false;
		}
		Atom another = (Atom)anotherObject;
		if (this.value == another.value){
			return true;
		}
		if (this.value == null || another.value == null) {
			return false;
		}
		return this.value.equals(another.value);
	}

	@Override
	public int hashCode() {
		if (this.value == null) {
			return 0;
		}
		return this.value.hashCode();
	}

	@Override
	public boolean isAtom() {
		return true;
	}

	@Override
	public SexpIterator iterator() {
		return new SexpIterator(this);
	}
	
	@Override
	public void print(StringBuffer cur) {
		cur.append(this.value == null ? null : this.value.toString());
	}
	
	@Override
	protected void toJson(JsonArray buf) {
		buf.add(this.value == null ? JsonNull.INSTANCE : new JsonPrimitive(this.stringValue()));
	}
	
	@Override
	public Type type() {
		return Type.Atom;
	}
	
	public Object value() {
		return this.value;
	}
	
	public double doubleValue() {
		return Double.parseDouble(this.stringValue());
	}
	
	public long longValue() {
		try {
			return Long.parseLong(this.stringValue());
		} catch (NumberFormatException e) {
			return (long)doubleValue();
		}
	}
	public String stringValue() {
		return this.toString();
	}
}

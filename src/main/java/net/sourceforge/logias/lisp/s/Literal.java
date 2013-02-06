package net.sourceforge.logias.lisp.s;

public class Literal extends Atom {
	public Literal(Object value) {
		super(value);
	}

	@Override
	public void print(StringBuffer cur) {
		cur.append("\"" + escapedValue() + "\"");
	}

	private String escapedValue() {
		if (this.value == null) {
			return Sexp.nil.toString();
		}
		return this.value.toString();
	}
	
	public String stringValue() {
		return this.value == null ? null: this.escapedValue();
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		return super.equals(anotherObject) && (anotherObject instanceof Literal); 
	}
}

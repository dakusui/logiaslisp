package com.github.dakusui.logias.lisp.s;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;


public class Pair extends BaseSexp {
	private Sexp car;
	private Sexp cdr;

	public Pair(Sexp car, Sexp cdr) {
		this.car = car;
		this.cdr = cdr;
	}

	@Override
	public boolean isAtom() {
		return false;
	}

	@Override
	public Sexp car() {
		return this.car;
	}

	@Override
	public Sexp cdr() {
		return this.cdr;
	}


	@Override
	public SexpIterator iterator() {
		return new SexpIterator(this);
	}

	@Override
	public Type type() {
		return Type.Pair;
	}

	@Override
	public void print(StringBuffer cur) {
		if (cur.length() == 0) {
			cur.append("(");
		}
		if (car == null) {
			cur.append(" ERR:NULL ");
		} else if (!car.isAtom()) {
			cur.append("(");
			((BaseSexp)car).print(cur);
		} else {
			((BaseSexp)car).print(cur);
		}
		if (cdr == null) {
			cur.append(" ERR:NULL ");
		} if (cdr.isAtom()) {
			if (Sexp.nil.equals(cdr)) {
				cur.append(")");
			} else {
				cur.append(" . " + cdr.print() + ")");
			}
		} else {
			cur.append(" ");
			((BaseSexp)cdr).print(cur);
		}
	}

	@Override
	public void toJson(JsonArray buf) {
		if (!this.car.isAtom()) {
			JsonArray tmp = new JsonArray();
			((BaseSexp)car).toJson(tmp);
			buf.add(tmp);
		} else {
			((BaseSexp)car).toJson(buf);
		}
		if (cdr.isAtom()) {
			if (!Sexp.nil.equals(cdr)) { 
				buf.add(new JsonPrimitive("$:"));
				buf.add(((BaseSexp)cdr).toJson());
			}
		} else {
			((BaseSexp)cdr).toJson(buf);
		}
	}

	public void car(Sexp sexp) {
		this.car = sexp;
	}
	
	public void cdr(Sexp sexp) {
		this.cdr = sexp;
	}
	
	public Sexp get(int i) {
		if (i < 0) {
			throw new RuntimeException();
		}
		if (i == 0) {
			return this.car();
		} else {
			Sexp cdr = this.cdr();
			if (cdr.isAtom()) {
				return cdr;
			} 
			if (cdr instanceof Pair) {
				return ((Pair)cdr).get(i - 1);
			}
			throw new RuntimeException();
		}
	}
}

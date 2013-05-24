package com.github.dakusui.logias.lisp.s;

import java.util.Iterator;

public class SexpIterator implements Iterator<Sexp> {
	protected Sexp sexp;
	protected Sexp cur = null;
	
	public SexpIterator(Sexp sexp) {
		this.sexp = sexp;
		this.cur = this.sexp;
	}
	
	@Override
	public boolean hasNext() {
		if (this.cur == null) {
			return false;
		}
		return true;
	}

	@Override
	public Sexp next() {
		Sexp ret = null;
		if (!this.hasNext()) {
			throw new RuntimeException("Illegal 'next' access is attempted. No more item in this object:<" + 
					this.getClass().getCanonicalName() + "> (an iterator for:" + this.sexp + ")"); 
		}
		if (!this.cur.isAtom()) {
			ret = this.cur.car();
			this.cur = this.cur.cdr();
		} else {
			ret = this.cur;
			this.cur = null;
		}
		return ret;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(String.format("remove operation is not supported by this class:<%s>", this.getClass().getSimpleName()));
	}
	
	public boolean isAtom() {
		return this.sexp.isAtom();
	}
	
	public SexpIterator assumeList() {
		return new SexpIterator(this.cur) {
			@Override
			public boolean hasNext() {
				if (this.sexp.isAtom() && Sexp.nil.equals(this.sexp)) {
					return false;
				}
				return super.hasNext();
			}
			@Override
			public Sexp next() {
				Sexp ret = super.next();
				if (!super.hasNext()) {
					if (ret != Sexp.nil) {
						throw new RuntimeException(
								String.format("This is not a list but ends with a dotted pair or an atom.:<%s>", ret)
								);
					}
				}
				if (cur == Sexp.nil) {
					this.cur = null;
				}
				return ret;
			}
		};
	}
	
	public static void main(String... args) {
		Sexp sexp = new Pair(Sexp.nil, new Pair(new Literal("Hello"), new Pair(new Literal("there"), Sexp.T)));
		System.out.println("<<" + sexp + ">>");
		Iterator<Sexp> i = new SexpIterator(sexp);
		while (i.hasNext()) {
			System.out.println(String.format("<%s>", i.next()));
		}
	}
}

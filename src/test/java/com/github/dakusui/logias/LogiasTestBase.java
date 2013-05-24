package com.github.dakusui.logias;


import org.junit.Before;

import com.github.dakusui.logias.Logias;
import com.github.dakusui.logias.lisp.Context;
import com.github.dakusui.logias.lisp.s.Sexp;

public class LogiasTestBase {
	protected Context context;
	protected Logias logias;
	
	@Before
	public void setUp() {
		this.context = Context.ROOT.createChild();
		this.logias = new Logias(context);
	}
	
	
	protected Sexp eval(String sexp) {
		return this.logias.run(this.logias.buildSexp(sexp));
	}

	protected void bind(String symbolName, Sexp sexp) {
		logias.getContext().bind(symbolName, sexp);		
	}}

package net.sourceforge.logias;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.s.Sexp;

import org.junit.Before;

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

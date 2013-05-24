package com.github.dakusui.logias;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.dakusui.logias.lisp.s.Sexp;

public class SetqTest extends LogiasTestBase {
	@Test
	public void test_001() {
		Sexp sexp = logias.buildSexp("[$setq, $dmy, 100]");
		Sexp result = logias.run(sexp);
		assertEquals("100", result.asAtom().stringValue());
	}

}

package com.github.dakusui.logias;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.dakusui.logias.lisp.s.Sexp;

public class DoListTest extends LogiasTestBase {
	@Test
	public void test_001() {
		//context.bind("$b", logias.buildSexp("[$quote, 1, 2, 3, 4]"));
		Sexp sexp = logias.buildSexp("[$dolist, [$a, [$quote, [1, 2, 3, 4] ] ], [$print, $a], $a ]");
		Sexp result = logias.run(sexp);
		assertEquals("4", result.asAtom().stringValue());
	}
	@Test
	public void test_002() {
		//context.bind("$b", logias.buildSexp("[$quote, 1, 2, 3, 4]"));
		Sexp sexp = logias.buildSexp(
				"[$let, [[$b, 0]], " +
						"[$dolist, " +
						"[$a, [$quote, [1, 2, 3, 4]] ]," + 
						"[$setq, $b, [$+, $a, $b]]" +
						"]" +
						"]"
				);
		Sexp result = logias.run(sexp);
		assertEquals(new Double(10.0), new Double(result.asAtom().doubleValue()));
	}

	@Test
	public void test_003() {
		//context.bind("$b", logias.buildSexp("[$quote, 1, 2, 3, 4]"));
		Sexp sexp = logias.buildSexp(
				"[$let, [[$b, 0]], " +
						"[$dolist, " +
						"[$a, [$quote, [1, 2, 3, 4]] ]," + 
						"[$setq, $b, [$+, $a, $b]]," +
						"[$+, $b, 1] ]" +
						"]"
				);
		Sexp result = logias.run(sexp);
		assertEquals(new Double(11.0), new Double(result.asAtom().doubleValue()));
	}
}

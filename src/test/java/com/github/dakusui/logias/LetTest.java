package com.github.dakusui.logias;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.dakusui.logias.lisp.s.Sexp;

public class LetTest extends LogiasTestBase {
	@Test
	public void test_001() {
		Sexp sexp = logias.buildSexp("[$let, [[$dmy, 100]]]");
		Sexp result = logias.run(sexp);
		assertEquals("$nil", result.asAtom().stringValue());
	}

	@Test
	public void test_002() {
		Sexp sexp = logias.buildSexp("[$let, [[$dmy, 100]], [$+, $dmy, 1]]");
		Sexp result = logias.run(sexp);
		assertEquals(new Double(101.0), new Double(result.asAtom().doubleValue()));
	}
	
	@Test
	public void test_003() {
		Sexp sexp = logias.buildSexp("[$let, [[$dmy, 100]], [$+, $dmy, 1], $dmy]");
		Sexp result = logias.run(sexp);
		assertEquals(new Double(100.0), new Double(result.asAtom().doubleValue()));
	}

	@Test
	public void test_004() {
		Sexp sexp = logias.buildSexp("[$let, [[$dmy, 100]], [$+, $dmy, 1], $nil, $nil]");
		Sexp result = logias.run(sexp);
		assertEquals(Sexp.nil, result.asAtom());
	}
	
	@Test
	public void test_005() {
		Sexp sexp = logias.buildSexp("[$let, [[$dmy, 100]], [$+, $dmy, 1], $nil]");
		System.out.println(sexp);
		Sexp result = logias.run(sexp);
		assertEquals(Sexp.nil, result.asAtom());
	}
}

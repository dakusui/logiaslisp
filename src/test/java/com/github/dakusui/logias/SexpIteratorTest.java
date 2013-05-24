package com.github.dakusui.logias;

import static junit.framework.TestCase.*;

import org.junit.Test;

import com.github.dakusui.logias.lisp.s.Sexp;
import com.github.dakusui.logias.lisp.s.SexpIterator;

public class SexpIteratorTest extends LogiasTestBase {
	@Test
	public void test_001() {
		Sexp sexp = logias.buildSexp("[1, 2, 3]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals(Sexp.nil, i.next().asAtom());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_002() {
		Sexp sexp = logias.buildSexp("[1]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals(Sexp.nil, i.next().asAtom());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_003() {
		Sexp sexp = logias.buildSexp("[]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals(Sexp.nil, i.next().asAtom());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_004() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1, 2]]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals(logias.buildSexp("[1, 2]").toString(), i.next().toString());
		assertEquals(Sexp.nil, i.next());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_005() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1]]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals(logias.buildSexp("[1]").toString(), i.next().toString());
		assertEquals(Sexp.nil, i.next());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_006() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1, 2]]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		SexpIterator j = i.next().iterator();
		assertEquals("1", j.next().asAtom().stringValue());
		assertEquals("2", j.next().asAtom().stringValue());
		assertEquals(Sexp.nil, j.next());
		assertEquals(Sexp.nil, i.next());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_007() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1]]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		SexpIterator j = i.next().iterator();
		assertEquals("1", j.next().asAtom().stringValue());
		assertEquals(Sexp.nil, j.next());
		assertEquals(Sexp.nil, i.next());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_008() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, $nil]"); 
		
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals("$nil", i.next().asAtom().stringValue());
		assertEquals("$nil", i.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}
	@Test
	public void test_101() {
		Sexp sexp = logias.buildSexp("[1, 2, 3]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_102() {
		Sexp sexp = logias.buildSexp("[1]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	private void makeSureErrorIsThrown(SexpIterator i) {
		try {
			System.err.println(i.next());
			fail();
		} catch (RuntimeException e) {
			System.err.println("err:" + e.getMessage());
			assertTrue(true);
		}
	}

	@Test
	public void test_103() {
		Sexp sexp = logias.buildSexp("[]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_104() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1, 2]]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals(logias.buildSexp("[1, 2]").toString(), i.next().toString());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_105() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1]]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals(logias.buildSexp("[1]").toString(), i.next().toString());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_106() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1, 2]]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		SexpIterator j = i.next().iterator().assumeList();
		assertEquals("1", j.next().asAtom().stringValue());
		assertEquals("2", j.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(j);
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_107() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, [1]]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		SexpIterator j = i.next().iterator().assumeList();
		assertEquals("1", j.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(j);
		makeSureErrorIsThrown(i);
	}

	@Test
	public void test_108() {
		Sexp sexp = logias.buildSexp("[1, 2, 3, $nil]"); 
		
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals("$nil", i.next().asAtom().stringValue());
		assertFalse(i.hasNext());
		makeSureErrorIsThrown(i);
	}

}

package net.sourceforge.logias;

import static org.junit.Assert.assertEquals;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.SexpIterator;

import org.junit.Before;
import org.junit.Test;


public class AppendTest extends LogiasTestBase {
	@Before
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void test_001() {
		bind("dmy", eval("[$quote, [1, 2, 3]]"));
		Sexp sexp = eval("[$append, $dmy, 100]");
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("3", i.next().asAtom().stringValue());
		assertEquals("100", i.next().asAtom().stringValue());
		assertEquals(true, !i.hasNext());
	}

	@Test
	public void test_002() {
		bind("dmy", eval("[$cons, 1, [$cons, 2, $nil]]"));
		Sexp sexp = eval("[$append, $dmy, 100]");
		SexpIterator i = sexp.iterator();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("100", i.next().asAtom().stringValue());
		assertEquals(true, !i.hasNext());
	}
	
	@Test
	public void test_003() {
		bind("dmy", eval("[$cons, 1, [$cons, 2, $nil]]"));
		Sexp sexp = eval("[$append, $dmy, [$cons, 100, $nil]]");
		SexpIterator i = sexp.iterator().assumeList();
		assertEquals("1", i.next().asAtom().stringValue());
		assertEquals("2", i.next().asAtom().stringValue());
		assertEquals("100", i.next().asAtom().stringValue());
		assertEquals(true, !i.hasNext());
	}	
}

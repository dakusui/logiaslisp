package net.sourceforge.logias;

import static org.junit.Assert.assertEquals;
import net.sourceforge.logias.lisp.s.Sexp;

import org.junit.Test;

public class SetqTest extends LogiasTestBase {
	@Test
	public void test_001() {
		Sexp sexp = logias.buildSexp("[$setq, $dmy, 100]");
		Sexp result = logias.run(sexp);
		assertEquals("100", result.asAtom().stringValue());
	}

}

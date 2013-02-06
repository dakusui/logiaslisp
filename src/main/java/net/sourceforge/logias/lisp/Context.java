package net.sourceforge.logias.lisp;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.logias.lisp.func.control.Defvar;
import net.sourceforge.logias.lisp.func.control.Do;
import net.sourceforge.logias.lisp.func.control.DoList;
import net.sourceforge.logias.lisp.func.control.Let;
import net.sourceforge.logias.lisp.func.control.Setq;
import net.sourceforge.logias.lisp.func.core.Append;
import net.sourceforge.logias.lisp.func.core.AssocGet;
import net.sourceforge.logias.lisp.func.core.Atom;
import net.sourceforge.logias.lisp.func.core.Car;
import net.sourceforge.logias.lisp.func.core.Cdr;
import net.sourceforge.logias.lisp.func.core.Cond;
import net.sourceforge.logias.lisp.func.core.Cons;
import net.sourceforge.logias.lisp.func.core.Eq;
import net.sourceforge.logias.lisp.func.core.Eval;
import net.sourceforge.logias.lisp.func.core.Lambda;
import net.sourceforge.logias.lisp.func.core.Quote;
import net.sourceforge.logias.lisp.func.java.Get;
import net.sourceforge.logias.lisp.func.java.Invoke;
import net.sourceforge.logias.lisp.func.java.New;
import net.sourceforge.logias.lisp.func.java.Set;
import net.sourceforge.logias.lisp.func.util.Print;
import net.sourceforge.logias.lisp.func.util.Split;
import net.sourceforge.logias.lisp.func.util.calc.And;
import net.sourceforge.logias.lisp.func.util.calc.BitAnd;
import net.sourceforge.logias.lisp.func.util.calc.BitOr;
import net.sourceforge.logias.lisp.func.util.calc.BitShift;
import net.sourceforge.logias.lisp.func.util.calc.Divide;
import net.sourceforge.logias.lisp.func.util.calc.Equals;
import net.sourceforge.logias.lisp.func.util.calc.Ge;
import net.sourceforge.logias.lisp.func.util.calc.Gt;
import net.sourceforge.logias.lisp.func.util.calc.Hex;
import net.sourceforge.logias.lisp.func.util.calc.Le;
import net.sourceforge.logias.lisp.func.util.calc.Lt;
import net.sourceforge.logias.lisp.func.util.calc.Minus;
import net.sourceforge.logias.lisp.func.util.calc.Mod;
import net.sourceforge.logias.lisp.func.util.calc.Multiply;
import net.sourceforge.logias.lisp.func.util.calc.Not;
import net.sourceforge.logias.lisp.func.util.calc.Or;
import net.sourceforge.logias.lisp.func.util.calc.Plus;
import net.sourceforge.logias.lisp.func.util.str.Concat;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.Symbol;

public class Context implements Cloneable {
	public static final Context ROOT = new Context(null);
	
	protected Map<String, Sexp> pool = new HashMap<String, Sexp>();

	private Context parent;
	
	static {
		// constants
		ROOT.bind("T", Sexp.T);
		ROOT.bind("nil", Sexp.nil);
		
		// core functions
		ROOT.bind("atom", new Atom());
		ROOT.bind("car", new Car());
		ROOT.bind("cdr", new Cdr());
		ROOT.bind("cond", new Cond());
		ROOT.bind("cons", new Cons());
		ROOT.bind("eq", new Eq());
		ROOT.bind("eval", new Eval());
		ROOT.bind("quote", new Quote());
		ROOT.bind("lambda", new Lambda());
		ROOT.bind("append", new Append());

		// control functions
		ROOT.bind("print", new Print());
		ROOT.bind("defvar", new Defvar());
		ROOT.bind("let", new Let());
		ROOT.bind("do", new Do());
		ROOT.bind("dolist", new DoList());
		ROOT.bind("setq", new Setq());
		
		// math functions
		ROOT.bind("+",  new Plus());
		ROOT.bind("-",  new Minus());
		ROOT.bind("*",  new Multiply());
		ROOT.bind("/",  new Divide());
		ROOT.bind("%",  new Mod());
		ROOT.bind("==", new Equals());
		ROOT.bind(">",  new Gt());
		ROOT.bind(">=", new Ge());
		ROOT.bind("<",  new Lt());
		ROOT.bind("<=", new Le());
		ROOT.bind("&",  new BitAnd());
		ROOT.bind("|",  new BitOr());
		ROOT.bind("shift",  new BitShift());
		ROOT.bind("hex",  new Hex());
		
		// logical operators
		ROOT.bind("and", new And());
		ROOT.bind("or", new Or());
		ROOT.bind("not", new Not());
		
		// str functions
		ROOT.bind("concat", new Concat());
		ROOT.bind("split", new Split());
		
		// java access functions
		ROOT.bind("jnew", new New(false));
		ROOT.bind("jinvoke", new Invoke(false));
		ROOT.bind("jinvokes", new Invoke(true));
		ROOT.bind("jget", new Get(false));
		ROOT.bind("jgets", new Get(true));
		ROOT.bind("jset", new Set(false));
		ROOT.bind("jsets", new Set(true));
		
		// associative array functions
		ROOT.bind("aget", new AssocGet());
		
		ROOT.bind("stdout", new Literal(System.out));
		ROOT.bind("stderr", new Literal(System.err));
		ROOT.bind("stdin", new Literal(System.in));
	}
	
	private Context(Context parent) {
		this.parent = parent;
	}

	public Sexp bind(Symbol symbol, Sexp sexp) {
		return this.bind(symbol.name(), sexp);
	}
	public Sexp bind(String symbolName, Sexp sexp) {
		Context context;
		if ((context = this.definingContext(symbolName)) == null) {
			return this.bindDirectly(symbolName, sexp);
		} 
		return context.bindDirectly(symbolName, sexp);
	}
	
	protected Sexp bindDirectly(String symbolName, Sexp sexp) {
		return this.pool.put(symbolName, sexp);
	}
	

	protected Context definingContext(String symbolName) {
		if (this.pool.containsKey(symbolName)) {
			return this;
		}
		if (this.parent == null) {
			return null;
		}
		return this.parent.definingContext(symbolName);
	}
	
	public Sexp lookup(String name) {
		Sexp ret = pool.get(name); 
		if (ret == null) {
			if (this.parent != null) {
				ret = this.parent.lookup(name);
			}
		}
		return ret; 
	}
	
	public Context createChild() {
		return new Context(this);
	}
}

package net.sourceforge.logias;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;

import static java.lang.String.format;

import net.sourceforge.logias.lisp.Context;
import net.sourceforge.logias.lisp.func.core.Eval;
import net.sourceforge.logias.lisp.s.Literal;
import net.sourceforge.logias.lisp.s.Pair;
import net.sourceforge.logias.lisp.s.Sexp;
import net.sourceforge.logias.lisp.s.Symbol;
import net.sourceforge.logias.util.JsonUtil;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Logias {
	public static void main(String... args) throws Exception {
		Logias logias = new Logias(Context.ROOT);
		for (String s : args) {
			System.out.println("====================================");
			System.out.println(String.format("s=<%s>", s));
			String f = loadFile(s);
			System.out.println(format("f=<%s>", f));
			JsonElement jselem1 = JsonUtil.toJson(f);
			System.out.println("--->" + jselem1);
			Sexp sexp1 = logias.buildSexp(jselem1);
			//Sexp sexp = logias.buildSexp(jselem);

			//System.out.println("--------");
			//System.out.println(String.format("jselem1=<%s>", jselem1));
			//System.out.println(String.format("sexp1=<%s>", sexp1.toString()));
			logias.dump(sexp1);
			Sexp result = logias.run(sexp1);
			System.out.println("--------");
			System.out.println(String.format("out=<%s>", result.toString()));
			System.out.println("--------");
			logias.dump(result);
			
		}
	}

	private Context context;
	
	private Eval eval;
	
	public Logias(Context context) {
		this.context = context;
		this.eval = new Eval();
	}
	
	public Sexp run(String s) {
		return this.eval.invoke(context, buildSexp(s));
	}
	
	public Sexp run(Sexp sexp) {
		return this.eval.invoke(this.context, sexp);
	}
	
	public Sexp buildSexp(String s) {
		return buildSexp(JsonUtil.toJson(s));
	}
	
	public Sexp buildSexp(JsonElement js) {
		if (js.isJsonArray()) {
			return processJsonArray(js.getAsJsonArray());
		} else if (js.isJsonObject()){
			return processJsonObject(js.getAsJsonObject());
		} else if (js.isJsonPrimitive()) {
			return processJsonPrimitive(js.getAsJsonPrimitive());
		} else if (js.isJsonNull()) {
			return Sexp.nil;
		}
		throw new RuntimeException(format("<%s> cannot be handled by Logias processor.", js.toString()));
	}

	private Sexp processJsonPrimitive(JsonPrimitive cur) {
		Sexp s;
		String str = cur.getAsString();
		if (str.startsWith("$")){
			s = new Symbol(str.substring(1));
		} else {
			if (cur.isBoolean()) {
				s = new Literal(cur.getAsBigDecimal());
			} else if (cur.isNumber()) {
				s = new Literal(cur.getAsNumber());
			} else if (cur.isString()) {
				s = new Literal(cur.getAsString());
			} else {
				throw new RuntimeException(format("<%s> is not a supported type.", cur));
			}
		}
		return s;
	}

	
	private Sexp processJsonObject(JsonObject jsonobj) {
		Pair ret = null;
		Pair cur = null;
		Pair next = null;
		for (Entry<String, JsonElement> entry : jsonobj.entrySet()) {
			next = new Pair(Sexp.nil, Sexp.nil);
			if (ret == null) {
				cur = ret = next;
			}
			Sexp keysexp = Sexp.nil;
			String s = entry.getKey();
			if (s.startsWith("$")) {
				keysexp = new Symbol(s.substring(1));
			} else {
				keysexp = new Literal(s);
			}
			Pair pair = new Pair(keysexp, buildSexp(entry.getValue()));
			next.car(pair);
			cur.cdr(next);
			cur = next;
			next = null;
		}
		return ret;
	}

	private Sexp processJsonArray(JsonArray jsarray) {
		if (jsarray.size() == 0) {
			return Sexp.nil;
		}
		Iterator<JsonElement> i = jsarray.iterator();
		Pair pair = null;
		Sexp ret = Sexp.nil;
		while (i.hasNext()) {
			JsonElement cur = i.next();
			Sexp s = null;
			boolean isDottedPair = false;
			if (cur.isJsonPrimitive()) {
				String str = cur.getAsString();
				if ("$:".equals(str)) {
					if (i.hasNext()) {
						cur = i.next();
						isDottedPair = true;
						if (i.hasNext()) {
							throw new RuntimeException("A '$:' is in a wrong context.");
						}
					} else {
						throw new RuntimeException("The last element of a json array cannot be a dot mark ('$:')");
					}
				}
			}
			s = buildSexp(cur);
			if (isDottedPair) {
				if (pair == null) {
					throw new RuntimeException("A dot mark '$:' is in a wrong context. A json array cannot start with it.");
				}
				pair.cdr(s);
			} else {
				if (pair == null) {
					ret = (pair = new Pair(s, Sexp.nil));
				} else {
					Pair tmp;
					pair.cdr(tmp = new Pair(s, Sexp.nil));
					pair = tmp;
				}
			}
		}
		return ret;
	}

	public static String loadFile(String fileName) throws Exception{
		StringBuffer b = new StringBuffer(4096);
		File f = new File(fileName);
		InputStream is = new BufferedInputStream(new FileInputStream(f));
		loadFromInputStream(b, is);
		return b.toString();
	}

	private static void loadFromInputStream(StringBuffer b, InputStream is) throws Exception {
		Reader r = new InputStreamReader(is, "utf-8");
		int c;
		while ((c = r.read()) != -1) {
			b.append((char)c);
		}
	}
	
	public void dump(Sexp sexp) {
		dump(0, sexp);
	}
	
	protected void dump(int indent, Sexp sexp) {
		for (int i = 0; i < indent; i++) {
			System.out.print("    ");
		}
		if (sexp.isAtom()) {
			System.out.println(format("<%s>", sexp));
		} else {
			Sexp car = sexp.car();
			if (car.isAtom()) {
				System.out.println(format("car:<%s>", sexp.car()));
			} else {
				System.out.println("car");
				dump(indent + 1, car);
			}
			for (int i = 0; i < indent; i++) {
				System.out.print("    ");
			}
			Sexp cdr = sexp.cdr();
			if (cdr.isAtom()) {
				System.out.println(format("cdr:<%s>", sexp.cdr()));
			} else {
				System.out.println("cdr");
				dump(indent + 1, cdr);
			}
		}
	}
	
	public Context getContext() {
		return this.context;
	}
}

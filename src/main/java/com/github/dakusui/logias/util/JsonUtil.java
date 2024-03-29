package com.github.dakusui.logias.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
	static final ThreadLocal<JsonParser> jsonParser;
	static {
		jsonParser = new ThreadLocal<JsonParser>();
	}
	
	public static boolean asBoolean(JsonObject json, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
				            : elem.getAsBoolean();
	}
	
	public static byte asByte(JsonObject json, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
	            : elem.getAsByte();
	}
	
	public static char asChar(JsonObject json, Object... path) {
		return asCharWithDefaultValue(json, (char)0, path);
	}
	public static char asCharWithDefaultValue(JsonObject json, char defaultValue, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
				            : elem.getAsCharacter();
	}
	
	public static double asDouble(JsonObject json, Object... path) {
		return asDoubleWithDefault(json, 0.0d, path);
	}
	
	public static double asDoubleWithDefault(JsonObject json, double defaultValue, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? defaultValue
				            : elem.getAsDouble();
	}

	public static float asFloat(JsonObject json, Object... path) {
		return asFloatWithDefault(json, 0.0f, path);
	}
	
	public static float asFloatWithDefault(JsonObject json, float defaultValue, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? defaultValue
				            : elem.getAsFloat();
	}
	
	public static int asInt(JsonObject json, Object... path) {
		return asIntWithDefault(json, 0, path);
	}
	
	public static int asIntWithDefault(JsonObject json, int defaultValue, Object... path) {
		JsonElement elem = asJson(json, path);
		return (elem == null) ? defaultValue
				              : elem.getAsInt();
	}
	
	public static JsonElement asJson(JsonElement json, Object... path) {
		JsonElement ret = get(json, path[0].toString());
		if (path.length == 1) {
			if (JsonNull.INSTANCE.equals(ret)) {
				ret = null;
			}
			return ret;
		}
		if (ret.isJsonObject() || ret.isJsonArray()) {
			JsonElement head = ret.getAsJsonObject();
			return asJson(head, Arrays.copyOfRange(path, 1, path.length));
		}
		return null;
	}

	public static JsonArray asJsonArray(JsonObject json, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
				: elem.getAsJsonArray();
	}
	
	public static JsonObject asJsonObject(JsonObject json, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
	            : elem.getAsJsonObject();
	}
	
	public static long asLong(JsonObject json, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? null
				            : elem.getAsLong();
	}
	
	public static String asString(JsonObject json, Object... path) {
		return asStringWithDefault(json, null, path);
	}
	
	public static String asStringWithDefault(JsonObject json, String defaultValue, Object... path) {
		JsonElement elem = asJson(json, path);
		return elem == null ? defaultValue
				            : elem.getAsString();
	}
	
	private static JsonElement get(JsonElement json, Object key) {
		if (key == null) {
			return null;
		}
		if (json.isJsonArray()) {
			return json.getAsJsonArray().get(toint(key));
		} else if (json.isJsonObject()) {
			return json.getAsJsonObject().get(key.toString());
		}
		throw new RuntimeException(String.format("<%1s> is neither JsonObject nor JsonArray", json));
	}
	
	private static boolean has(JsonElement json, Object key) {
		if (json.isJsonArray()) {
			return toint(key) < json.getAsJsonArray().size();
		} else if (json.isJsonObject()) {
			return json.getAsJsonObject().has(key.toString());
		}
		return false;
	} 
	
	public static boolean hasPath(JsonObject json, Object... path) {
		String car = path[0].toString();
		if (path.length == 1) {
			return has(json, car);
		}
		boolean ret = has(json, car);
		
		if (ret) {
			JsonElement cur = get(json, car);
			if (cur.isJsonObject()) {
				return hasPath(cur.getAsJsonObject(), Arrays.copyOfRange(path, 1, path.length));
			} else {
				return false;
			}
		} 
		return false;
	}
	
	private static JsonParser jsonParser() {
		JsonParser ret = null;
		if ((ret = jsonParser.get()) == null) {
			jsonParser.set(ret = new JsonParser());
		}
		return ret;
	}

	public static Iterator<String> keyIterator(final JsonObject json) {
		Iterator<String> i = new Iterator<String>() {
			Iterator<Entry<String, JsonElement>> iEntries = json.entrySet().iterator();
			public boolean hasNext() {
				return iEntries.hasNext();
			}
			public String next() {
				return iEntries.next().getKey();
			}
			public void remove() {
				iEntries.remove();
			}
		};
		return i;
	}
	
	private static int toint(Object o) {
		if (o == null) {
			throw new NullPointerException("null cannot be converted to int.");
		}
		if (o instanceof Number) {
			return ((Number)o).intValue();
		}
		if (o instanceof String) {
			return Integer.parseInt((String)o);
		}
		return Integer.parseInt(o.toString());
	}

	public static void main(String[] args) throws Exception {
		JsonObject obj;
		System.out.println(obj = toJson("{null:1, \"hello\":{\"HELLO\":\"world\"}}").getAsJsonObject());
		
		System.out.println(hasPath(obj, "HELLO"));
		System.out.println(hasPath(obj, "hello"));
		System.out.println(hasPath(obj, "hello", "HI"));
		System.out.println(hasPath(obj, "hello", "HELLO"));
		System.out.println(hasPath(obj, "hello", "HELLO", "hey"));
		
		System.out.println("----");
		
		System.out.println(obj.get("bye") == null);
		System.out.println(obj.get(null));
		System.out.println(JsonNull.INSTANCE == null);
		
		System.out.println(JsonUtil.toJson("hello"));
	}

	public static JsonElement toJson(String str) {
		return jsonParser().parse(str);
	}
}

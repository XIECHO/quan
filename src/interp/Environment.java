package interp;

import java.util.HashMap;

public class Environment {
	private HashMap<String, Object> values;

	public Environment() {
		values = new HashMap<String, Object>();
	}

	public void put(String name, Object value) {
		values.put(name, value);
	}

	public Object get(String name) {
		return values.get(name);
	}
}

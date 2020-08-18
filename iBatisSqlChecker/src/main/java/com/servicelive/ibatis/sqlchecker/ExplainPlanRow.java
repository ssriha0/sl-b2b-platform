package com.servicelive.ibatis.sqlchecker;

import java.util.HashMap;
import java.util.Map;

public class ExplainPlanRow {

	private Map<String, String> map = new HashMap<String, String>();

	public void addColumn(String name, Object value) {
		String val;
		if(value == null) {
			val = "null";
		} else {
			val = value.toString();
		}
		map.put(name, val);
	}

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}
}

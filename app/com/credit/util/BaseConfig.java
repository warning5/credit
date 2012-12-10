package com.credit.util;

import java.util.HashMap;
import java.util.Map;

public class BaseConfig {

	Map<String, String> map = new HashMap<String, String>();

	public String getConfig(String key) {
		return map.get(key);
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
}

/**
 * 
 */
package com.bluecloud.mvc.web.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hanlu
 *
 */
public final class DataObject {
	Map<String ,String> data;
	/**
	 * 
	 */
	protected DataObject() {
		data=new HashMap<String, String>();
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, String> data() {
		return data;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, String value) {
		data.put(key, value);
	}
}

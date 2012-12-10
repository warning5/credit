/**
 * 
 */
package com.bluecloud.mvc.external;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hanlu
 * 
 */
public final class FragmentEventRegister {

	Map<String, FragmentEvent> events;

	public FragmentEventRegister() {
		events = new HashMap<String, FragmentEvent>();
	}

	/**
	 * 
	 * @param event
	 */
	public void add(FragmentEvent event) {
		events.put(event.getName(), event);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public FragmentEvent find(String name) {
		return events.get(name);
	}

}

/**
 * 
 */
package com.bluecloud.mvc.web.data;

import java.util.HashMap;
import java.util.Map;

import com.bluecloud.mvc.web.http.BCWebRequest;

/**
 * @author Hanlu
 *
 */
public abstract class RequestData {

	protected Map<String, DataObject> dos=new HashMap<String, DataObject>();
	/**
	 * 
	 */
	protected RequestData() {
	}

	public void parse(BCWebRequest bcWebRequest) {
		doParse(bcWebRequest);
		
	}

	protected abstract void doParse(BCWebRequest bcWebRequest);

	/**
	 * 
	 * @param name
	 * @return
	 */
	public DataObject getData(String name) {
		DataObject fd=dos.get(name);
		return fd;
	}
}

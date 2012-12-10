/**
 * 
 */
package com.bluecloud.mvc.api;

import com.bluecloud.mvc.external.Action;

/**
 * @author Hanlu
 *
 */
public interface ActionDepository {

	/**
	 * 
	 * @param name
	 * @return
	 */
	Action getAction(String name);

	void load(ClassLoader classLoader) throws Exception;

}

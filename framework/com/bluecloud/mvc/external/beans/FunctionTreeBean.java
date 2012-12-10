/**
 * 
 */
package com.bluecloud.mvc.external.beans;

/**
 * @author Hanlu
 *
 */
public abstract class FunctionTreeBean extends TreeBean {

	/**
	 * 
	 */
	public FunctionTreeBean() {
	}

	/**
	 * 
	 * @return
	 */
	public abstract boolean isOpera() ;

	/**
	 * 
	 * @return
	 */
	public abstract String getUri() ;

	/**
	 * 
	 * @return
	 */
	public abstract Integer getFunctionOrder();
}

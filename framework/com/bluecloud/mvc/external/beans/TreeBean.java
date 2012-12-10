/**
 * 
 */
package com.bluecloud.mvc.external.beans;

/**
 * @author Hanlu
 *
 */
public abstract class TreeBean extends FragmentBean {
	/**
	 * 
	 */
	public TreeBean() {
		
	}

	/**
	 * 
	 * @return
	 */
	public abstract String getId() ;

	/**
	 * 
	 * @return
	 */
	public abstract String getParent() ;

	/**
	 * 
	 * @return
	 */
	public abstract String getName() ;

}

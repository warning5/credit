/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl.tree;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;

/**
 * @author Hanlu
 *
 */
public abstract class TreeItem extends BaseTree {

	private FunctionTreeBean bean;

	/**
	 * @param bean 
	 * 
	 */
	public TreeItem(FunctionTreeBean bean) {
		this.bean=bean;
	}

	/**
	 * @return the bean
	 */
	public FunctionTreeBean getBean() {
		return bean;
	}

	/**
	 * 
	 * @param item
	 * @param target
	 * @param isOpera
	 * @return
	 */
	public abstract String parseData(TreeItem item, String target, boolean isOpera) ;

	/**
	 * 
	 * @return
	 */
	public boolean hasChilds() {
		return !this.items.isEmpty();
	}
	
}

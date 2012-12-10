/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.List;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;
import com.bluecloud.mvc.external.widgets.impl.tree.FunctionTreeMenus;

/**
 * @author Hanlu
 *
 */
public class TreeMenusWidget extends FunctionTreeWidget {

	/**
	 * @param name 
	 * @param beans 
	 * 
	 */
	public TreeMenusWidget(String name, List<? extends FunctionTreeBean> beans) {
		super(name, beans);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.DefaultTreeWidget#getData()
	 */
	@Override
	public Object getData() {
		return getData(true,new FunctionTreeMenus(),"navTab");
	}

}

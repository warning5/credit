/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.List;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;
import com.bluecloud.mvc.external.widgets.impl.tree.FunctionTree;

/**
 * @author Hanlu
 *
 */
public class OrgTreeWidget extends FunctionTreeWidget {

	/**
	 * @param name
	 * @param beans
	 */
	public OrgTreeWidget(String name, List<? extends FunctionTreeBean> beans) {
		super(name, beans);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.DefaultTreeWidget#getData()
	 */
	@Override
	public Object getData() {
		return getData(true,new FunctionTree("机构树"),"ajax");
	}
}

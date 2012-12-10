/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.widgets.BizWidget;

/**
 * @author Hanlu
 *
 */
public class DefaultBizWidget extends DefaultFormWidget implements BizWidget {

	/**
	 * @param name 
	 * @param bean 
	 * 
	 */
	public DefaultBizWidget(String name,FragmentBean bean) {
		super(name,bean);
	}
}

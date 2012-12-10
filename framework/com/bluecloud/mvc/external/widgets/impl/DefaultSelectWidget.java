/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.widgets.SelectWidget;

/**
 * @author Hanlu
 *
 */
public class DefaultSelectWidget implements SelectWidget {

	private String name;
	private List<? extends FragmentBean> beans;

	/**
	 * @param beans 
	 * @param name 
	 * 
	 */
	public DefaultSelectWidget(String name, List<? extends FragmentBean> beans) {
		this.name=name;
		this.beans=beans;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getData()
	 */
	@Override
	public Object getData() {
		JsonConfig config = new JsonConfig();   
//		config.setIgnoreDefaultExcludes(false);      
//		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);    
		return JSONArray.fromObject(beans,config).toString();
	}

}

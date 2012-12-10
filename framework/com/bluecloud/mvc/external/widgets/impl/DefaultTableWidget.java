/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.PagingBean;
import com.bluecloud.mvc.external.widgets.TableWidget;

/**
 * @author Hanlu
 *
 */
public class DefaultTableWidget implements TableWidget {

	private String name;
	private List<? extends FragmentBean> beans;
	private PagingBean paging;

	/**
	 * @param name 
	 * @param beans 
	 * @param paging 
	 * 
	 */
	public DefaultTableWidget(String name, List<? extends FragmentBean> beans, PagingBean paging) {
		this.name=name;
		this.beans=beans;
		this.paging=paging;
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
		StringBuffer s=new StringBuffer();
		s.append("<div id=\"beans\">");
		for(FragmentBean bean:beans){
			s.append("<div>");
			JsonConfig config = new JsonConfig();   
//			config.setIgnoreDefaultExcludes(false);      
//			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);    
			s.append(JSONObject.fromObject(bean, config).toString());
			s.append("</div>");
		}
		s.append("</div>");
		s.append("<div id=\"paging\">");
		s.append(JSONObject.fromObject(paging).toString());
		s.append("</div>");
		return s;
	}

}

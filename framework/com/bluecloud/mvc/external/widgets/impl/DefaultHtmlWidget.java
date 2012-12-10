/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import com.bluecloud.mvc.external.widgets.HtmlWidget;

/**
 * @author Hanlu
 *
 */
public class DefaultHtmlWidget implements HtmlWidget {

	private String name;
	private String content;

	/**
	 * @param context 
	 * @param name 
	 * 
	 */
	public DefaultHtmlWidget(String name, String content) {
		this.name=name;
		this.content=content;
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
		return content;
	}

}

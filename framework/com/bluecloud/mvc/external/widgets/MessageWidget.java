/**
 * 
 */
package com.bluecloud.mvc.external.widgets;

/**
 * @author Hanlu
 *
 */
public interface MessageWidget extends FragmentWidget {

	/**
	 * 失败的消息
	 */
	public void fail();

	/**
	 * 设置刷新的tabid
	 * @param tabid
	 */
	public void setNavTabId(String tabid);
}

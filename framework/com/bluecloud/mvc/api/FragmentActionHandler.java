/**
 * 
 */
package com.bluecloud.mvc.api;

import com.bluecloud.mvc.external.Action;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 *
 */
public interface FragmentActionHandler {

	/**
	 * 
	 * @param fragment
	 * @throws Exception 
	 */
	void service(Action fragment) throws Exception;

	/**
	 * 获得请求的fragment标识，即http://ip:port/context/actionpath.action标识
	 * @return
	 */
	String getRequestActionName();

	/**
	 * 
	 * @return
	 */
	HtmlFragmentRequest getRrequest();
	
	/**
	 * 
	 * @return
	 */
	HtmlFragmentResponse getResponse();
}

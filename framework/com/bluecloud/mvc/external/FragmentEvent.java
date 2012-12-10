/**
 * 
 */
package com.bluecloud.mvc.external;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 * 
 */
public interface FragmentEvent {

	/**
	 * 
	 * @param req
	 * @param action
	 * @return
	 * @throws FragmentEventException
	 */
	public abstract HtmlFragmentResponse execute(HtmlFragmentRequest req, Action action) throws FragmentEventException;

	/**
	 * 
	 * @return
	 */
	public String getName();

}

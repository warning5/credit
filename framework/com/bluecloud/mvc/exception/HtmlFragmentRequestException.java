/**
 * 
 */
package com.bluecloud.mvc.exception;

/**
 * @author Leo
 *
 */
public class HtmlFragmentRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public HtmlFragmentRequestException() {
		
	}

	/**
	 * @param arg0
	 */
	public HtmlFragmentRequestException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public HtmlFragmentRequestException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public HtmlFragmentRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}

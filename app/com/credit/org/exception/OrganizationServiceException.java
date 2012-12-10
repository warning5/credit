/**
 * 
 */
package com.credit.org.exception;

/**
 * @author Hanlu
 *
 */
public class OrganizationServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -957996284296922871L;

	/**
	 * 
	 */
	public OrganizationServiceException() {
		
	}

	/**
	 * @param arg0
	 */
	public OrganizationServiceException(String arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 */
	public OrganizationServiceException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public OrganizationServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}

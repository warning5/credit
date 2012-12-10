/**
 * 
 */
package com.credit.rbac.exception;

/**
 * @author Hanlu
 *2012-8-29
 */
public class RBACServiceException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -987940999818124093L;

	public RBACServiceException() {
	}
	/**
	 * @param message
	 */
	public RBACServiceException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public RBACServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RBACServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

}

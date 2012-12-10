/**
 * 
 */
package com.bluecloud.persistence.exception;

/**
 * @author Hanlu
 *
 */
public class PersistenceServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5383873824937991794L;

	/**
	 * 
	 */
	public PersistenceServiceException() {
	}

	/**
	 * @param arg0
	 */
	public PersistenceServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public PersistenceServiceException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public PersistenceServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}

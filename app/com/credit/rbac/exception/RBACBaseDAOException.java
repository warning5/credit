/**
 * 
 */
package com.credit.rbac.exception;

/**
 * @author Hanlu
 *2012-8-29
 */
public class RBACBaseDAOException extends Exception {

	public RBACBaseDAOException(String message) {
		super(message);
	}

	public RBACBaseDAOException() {
		super();
	}

	public RBACBaseDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public RBACBaseDAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3591703026002046705L;

}

/**
 * 
 */
package com.credit.actions.beans.rbac;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.rbac.bo.Operation;

/**
 * @author Hanlu
 * 
 */
public class OperationBean extends FragmentBean{
	private Operation operation;

	/**
	 * 
	 */
	public OperationBean() {
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

}

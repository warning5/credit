/**
 * 
 */
package com.bluecloud.mvc.external.beans;

/**
 * @author Hanlu
 *
 */
public abstract class CheckTreeBean extends FunctionTreeBean {

	/**
	 * 
	 */
	public CheckTreeBean() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.FunctionTreeBean#getUri()
	 */
	@Override
	public String getUri() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.FunctionTreeBean#isOpera()
	 */
	@Override
	public boolean isOpera() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.FunctionTreeBean#getFunctionOrder()
	 */
	@Override
	public Integer getFunctionOrder() {
		return 0;
	}
}

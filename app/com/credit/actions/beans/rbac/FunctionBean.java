/**
 * 
 */
package com.credit.actions.beans.rbac;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;
import com.credit.rbac.bo.Function;

/**
 * @author Hanlu
 * 
 */
public class FunctionBean extends FunctionTreeBean {

	private Function function;
	private String parentName;

	/**
	 * 
	 */
	public FunctionBean() {
	}

	/**
	 * @return the function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * @param function
	 *            the function to set
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * 
	 * @param name
	 */
	public void setParentName(String name) {
		this.parentName = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getParentName() {
		return parentName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getId()
	 */
	@Override
	public String getId() {
		return this.function.getId();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getParent()
	 */
	@Override
	public String getParent() {
		return this.function.getParent();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getName()
	 */
	@Override
	public String getName() {
		return this.function.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#isOpera()
	 */
	@Override
	public boolean isOpera() {
		return this.function.isOpera();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getUri()
	 */
	@Override
	public String getUri() {
		return this.function.getUri();
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.FunctionTreeBean#getFunctionOrder()
	 */
	@Override
	public Integer getFunctionOrder() {
		return this.function.getFunctionOrder();
	}
}

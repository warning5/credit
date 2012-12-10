/**
 * 
 */
package com.credit.rbac.bo;

import com.bluecloud.persistence.pojo.BaseBO;


/**
 * @author Hanlu
 * 
 */
public final class Operation extends BaseBO{

	/**
	 * 0=浏览;1=增;2=删;3=改;4=查;5=提交
	 */
	private Integer code;
	private String name;

	public Operation() {
		super();
	}
	public Operation(Integer code, String name) {
		this();
		this.code=code;
		this.name=name;
	}
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	@Override
	protected String getPKName() {
		return "code";
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

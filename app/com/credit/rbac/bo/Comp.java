/**
 * 
 */
package com.credit.rbac.bo;

import com.bluecloud.persistence.pojo.BaseBO;


/**
 * @author Hanlu
 *
 */
public class Comp extends BaseBO {

	private Integer code;
	private String name;
	/**
	 * 
	 */
	public Comp() {
		super();
	}

	public Comp(int code, String name) {
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
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected String getPKName() {
		return null;
	}

}

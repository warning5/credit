/**
 * 
 */
package com.credit.org.bo;

import com.bluecloud.persistence.pojo.BaseBO;

/**
 * @author Hanlu
 *
 */
public class Company extends BaseBO {

	private String name;
	private String parent;
	private String description;
	/**
	 * 
	 */
	public Company() {
		super();
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

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.persistence.pojo.BaseBO#getPKName()
	 */
	@Override
	protected String getPKName() {
		return "companyid";
	}

}

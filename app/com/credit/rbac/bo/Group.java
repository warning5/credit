package com.credit.rbac.bo;

import java.util.List;

import com.bluecloud.persistence.pojo.BaseBO;


/**
 * 
 * @author Hanlu
 *
 */
public class Group extends BaseBO {

	private String name;

	private String description;

	private boolean disable;

	private List<User> users;

	private List<Role> roles;

	public Group() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the disable
	 */
	public boolean isDisable() {
		return disable;
	}

	/**
	 * @param disable the disable to set
	 */
	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	/**
	 * 
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users=users;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * 
	 * @param roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles=roles;
	}

	/**
	 * 
	 * @return
	 */
	public List<Role> getRoles() {
		return roles;
	}

	@Override
	protected String getPKName() {
		return "groupid";
	}
}

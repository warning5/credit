package com.credit.rbac.bo;

import java.util.List;
import java.util.Map;

import com.bluecloud.persistence.pojo.BaseBO;

/**
 * 
 * @author Hanlu
 * 
 */
public class Role extends BaseBO {
	private String name;

	private String description;

	private boolean disable;

	private Map<String, Function> functions;

	private List<User> users;

	private List<Group> groups;

	public Role() {
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
	 * 
	 * @return
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
	 * @return
	 */
	public Map<String, Function> getFunctions() {
		return functions;
	}

	/**
	 * 
	 * @param funs
	 */
	public void setFunctions(Map<String, Function> funs) {
		this.functions=funs;
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
	 * @param users
	 */
	public void setUsers(List<User> users) {
		this.users=users;
	}

	/**
	 * 
	 * @param groups
	 */
	public void setGroups(List<Group> groups) {
		this.groups=groups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	@Override
	protected String getPKName() {
		return "roleid";
	}

}

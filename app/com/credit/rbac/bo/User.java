package com.credit.rbac.bo;

import java.util.List;

import com.bluecloud.persistence.pojo.BaseBO;


/**
 * 
 * @author Hanlu
 * 
 */
public class User extends BaseBO{

	private String username;

	private String password;

	private String description;

	private boolean disable;

	private List<Role> roles;

	private List<Group> groups;

//	private String roleid;
//
//	private String groupid;
	public User() {
		super();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the roleid
	 */
//	public String getRoleid() {
//		roleid=mergeId(roles);
//		return roleid;
//	}

	/**
	 * @param roleid the roleid to set
	 */
//	public void setRoleid(String roleid) {
//		this.roleid = roleid;
//	}

	/**
	 * @return the groupid
	 */
//	public String getGroupid() {
//		groupid=mergeId(groups);
//		return groupid;
//	}

	/**
	 * @param groupid the groupid to set
	 */
//	public void setGroupid(String groupid) {
//		this.groupid = groupid;
//	}

	/**
	 * @param disable the disable to set
	 */
	public void setDisable(boolean disable) {
		this.disable = disable;
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

	/**
	 * 
	 * @return
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * 
	 * @param groups
	 */
	public void setGroups(List<Group> groups) {
		this.groups=groups;
	}

	@Override
	protected String getPKName() {
		return "userid";
	}
}
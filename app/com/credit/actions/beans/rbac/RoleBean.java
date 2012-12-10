/**
 * 
 */
package com.credit.actions.beans.rbac;

import com.bluecloud.mvc.external.beans.CheckTreeBean;
import com.credit.rbac.bo.Role;

/**
 * @author Hanlu
 *
 */
public class RoleBean extends CheckTreeBean {

	private Role role;
	/**
	 * 
	 */
	public RoleBean() {
	}
	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getId()
	 */
	@Override
	public String getId() {
		if(this.role!=null){
			return this.role.getId();
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getParent()
	 */
	@Override
	public String getParent() {
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.beans.TreeBean#getName()
	 */
	@Override
	public String getName() {
		if(this.role!=null){
			return this.role.getName();
		}
		return null;
	}
}

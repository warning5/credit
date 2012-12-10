/**
 * 
 */
package com.credit.actions.beans.rbac;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.rbac.bo.User;

/**
 * @author Hanlu
 *
 */
public class UserBean extends FragmentBean {

	private User user;
	/**
	 * 
	 */
	public UserBean() {
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
}

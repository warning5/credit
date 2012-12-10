/**
 * 
 */
package com.credit.actions.beans.rbac;

import com.bluecloud.mvc.external.beans.CheckTreeBean;
import com.credit.rbac.bo.Group;

/**
 * @author Hanlu
 *
 */
public class GroupBean extends CheckTreeBean {

	private Group group;
	/**
	 * 
	 */
	public GroupBean() {
	}
	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}
	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	@Override
	public String getId() {
		if(this.group!=null){
			return this.group.getId();
		}
		return null;
	}
	@Override
	public String getParent() {
		return null;
	}
	@Override
	public String getName() {
		if(this.group!=null){
			return this.group.getName();
		}
		return null;
	}


}

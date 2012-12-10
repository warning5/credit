/**
 * 
 */
package com.credit.actions.supervisor;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.org.bo.Company;

/**
 * @author Hanlu
 *
 */
public class SupervisorBean extends FragmentBean {
	private Company supervisor;
	/**
	 * 
	 */
	public SupervisorBean() {
	}
	/**
	 * @return the supervisor
	 */
	public Company getSupervisor() {
		return supervisor;
	}
	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(Company supervisor) {
		this.supervisor = supervisor;
	}

}

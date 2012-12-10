/**
 * 
 */
package com.credit.http.user;

import com.credit.org.bo.Company;

/**
 * @author Hanlu
 *
 */
public abstract class OrgUser implements IOrgUser {

	private Company org;
	/**
	 * @param org 
	 * 
	 */
	public OrgUser(Company org) {
		this.org=org;
	}

	@Override
	public final String getIdentify() {
		return this.org.getId();
	}

}

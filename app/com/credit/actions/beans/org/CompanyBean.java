/**
 * 
 */
package com.credit.actions.beans.org;

import com.bluecloud.mvc.external.beans.OrganizationBean;
import com.credit.org.bo.Company;

/**
 * @author Hanlu
 *
 */
public class CompanyBean extends OrganizationBean{

	private Company company;
	private String parentName;

	/**
	 * 
	 */
	public CompanyBean() {
	}

	public void setCompany(Company company) {
		this.company=company;
	}

	@Override
	public String getId() {
		if(this.company!=null){
			return this.company.getId();
		}
		return null;
	}

	@Override
	public String getParent() {
		if(this.company!=null){
			return this.company.getParent();
		}
		return null;
	}

	@Override
	public String getName() {
		if(this.company!=null){
			return this.company.getName();
		}
		return null;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setParentName(String name) {
		this.parentName=name;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

}

/**
 * 
 */
package com.credit.actions.financial;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.org.bo.Company;

/**
 * @author Hanlu
 *
 */
public class FinancialBean extends FragmentBean {

	private Company financial;

	/**
	 * 
	 */
	public FinancialBean() {
	}

	/**
	 * @param financial the financial to set
	 */
	public void setFinancial(Company financial) {
		this.financial = financial;
	}


	/**
	 * 
	 * @return
	 */
	public Company getFinancial() {
		return financial;
	}

}

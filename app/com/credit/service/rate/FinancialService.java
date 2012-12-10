/**
 * 
 */
package com.credit.service.rate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.org.bo.Company;
import com.credit.org.service.OrganizationService;

/**
 * @author Hanlu
 *
 */
@Service
public class FinancialService {
	@Autowired
	private OrganizationService service;

	/**
	 * 
	 * @param supervisor
	 * @return
	 * @throws Exception 
	 */
	public int allCount(Company supervisor) throws Exception {
		supervisor.setParent("3");
		return service.allCount(supervisor);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Company getFinancialByID(String id) throws Exception {
		return service.getCompanyByID(id);
	}

	/**
	 * 
	 * @param supervisor
	 * @return
	 * @throws Exception 
	 */
	public boolean updateFinancial(Company supervisor) throws Exception {
		supervisor.setParent("3");
		return service.updateCompany(supervisor);
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	public boolean deleteFinancials(List<String> ids) throws Exception {
		return service.deleteCompanies(ids);
	}

	/**
	 * 
	 * @param supervisor
	 * @return
	 * @throws Exception 
	 */
	public boolean saveFinancial(Company supervisor) throws Exception {
		supervisor.setParent("3");
		return service.saveCompany(supervisor);
	}

	/**
	 * 
	 * @param supervisor
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception 
	 */
	public List<Company> getCompanies(Company supervisor, int offset, int limit) throws Exception {
		supervisor.setParent("3");
		return service.getCompanies(supervisor, offset, limit);
	}
}

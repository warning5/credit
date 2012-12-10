/**
 * 
 */
package com.credit.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluecloud.persistence.exception.PersistenceServiceException;
import com.bluecloud.persistence.pojo.Relation;
import com.bluecloud.persistence.service.AllocationService;
import com.bluecloud.persistence.service.DisallocationService;
import com.bluecloud.persistence.service.RelationService;
import com.credit.org.bo.Company;
import com.credit.org.dao.CompanyMapper;
import com.credit.org.exception.OrganizationServiceException;
import com.credit.rbac.bo.User;

/**
 * @author Hanlu
 * 
 */
@Service
public class OrganizationService extends RelationService {
	
	@Autowired
	private CompanyMapper mapper;
	private Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * @return
	 * @throws OrganizationServiceException
	 */
	public List<Company> getCompanies() throws OrganizationServiceException {
		List<Company> companies = null;
		try {
			companies = mapper.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return companies;
	}

	/**
	 * 
	 * @param company
	 * @param offset
	 * @param limit
	 * @return
	 * @throws OrganizationServiceException
	 */
	public List<Company> getCompanies(Company company, int offset, int limit) throws OrganizationServiceException {
		if (company == null) {
			return this.getCompanies(offset, limit);
		}
		List<Company> cs = null;
		try {
			cs = mapper.searchLimit(company, new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return cs;
	}

	/**
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 * @throws OrganizationServiceException
	 */
	private List<Company> getCompanies(int offset, int limit) throws OrganizationServiceException {
		List<Company> cs = null;
		try {
			cs = mapper.listLimit(new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return cs;
	}

	/**
	 * 
	 * @param company
	 * @return
	 * @throws OrganizationServiceException
	 */
	public int allCount(Company company) throws OrganizationServiceException {
		try {
			return mapper.allCount(company);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws OrganizationServiceException
	 */
	public Map<String, Company> getCompanyKinds() throws OrganizationServiceException {
		List<Company> cs = null;
		Map<String, Company> cks = new HashMap<String, Company>();
		try {
			cs = mapper.listKind();
			for (Company c : cs) {
				cks.put(c.getId(), c);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return cks;
	}

	/**
	 * 
	 * @param ids
	 * @param companyid
	 * @return
	 * @throws OrganizationServiceException
	 */
	public boolean allocateGroup(List<String> ids, String companyid) throws OrganizationServiceException {
		try {
			return allocate(ids, companyid, new AllocationService() {
				@Override
				public void allocate(List<Relation> relations) {
					for (Relation r : relations) {
						mapper.allocateGroup(r);
					}
				}
			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param ids
	 * @param companyid
	 * @return
	 * @throws OrganizationServiceException
	 */
	public boolean disallocateGroup(List<String> ids, String companyid) throws OrganizationServiceException {
		try {
			return disallocate(ids, companyid, new DisallocationService() {

				@Override
				public void disallocate(Map<String, Object> params) throws PersistenceServiceException {
					mapper.disallocateGroup(params);
				}

			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
	}

	/**
	 * @param user
	 * @return
	 * @throws OrganizationServiceException
	 */
	public Company getOrgByUser(User user) throws OrganizationServiceException {
		Company company = null;
		try {
			company = mapper.getByUser(user.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return company;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws OrganizationServiceException
	 */
	public Company getCompanyByID(String id) throws OrganizationServiceException {
		if (id == null) {
			return null;
		}
		Company company = null;
		try {
			company = mapper.getByID(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return company;
	}

	/**
	 * 
	 * @param company
	 * @return
	 * @throws OrganizationServiceException
	 */
	public boolean updateCompany(Company company) throws OrganizationServiceException {
		if (company == null) {
			return false;
		}
		try {
			this.mapper.update(company);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws OrganizationServiceException
	 */
	public boolean deleteCompanies(List<String> ids) throws OrganizationServiceException {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		try {
			this.mapper.delete(ids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param supervisor
	 * @return
	 * @throws OrganizationServiceException
	 */
	public boolean saveCompany(Company company) throws OrganizationServiceException {
		if (company == null) {
			return false;
		}
		try {
			this.mapper.add(company);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new OrganizationServiceException(e.getMessage());
		}
		return true;
	}
}

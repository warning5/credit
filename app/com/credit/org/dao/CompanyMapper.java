/**
 * 
 */
package com.credit.org.dao;

import java.util.List;
import java.util.Map;

import com.bluecloud.persistence.dao.BaseDAO;
import com.bluecloud.persistence.pojo.Relation;
import com.credit.org.bo.Company;

/**
 * @author Hanlu
 *
 */
public interface CompanyMapper extends BaseDAO{

	/**
	 * 
	 * @return
	 */
	List<Company> listKind();

	/**
	 * 
	 * @param r
	 */
	void allocateGroup(Relation r);

	/**
	 * 
	 * @param params
	 */
	void disallocateGroup(Map<String, Object> params);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Company getByUser(String id);
}

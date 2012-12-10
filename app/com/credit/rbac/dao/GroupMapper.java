/**
 * 
 */
package com.credit.rbac.dao;

import java.util.List;

import com.bluecloud.persistence.dao.BaseDAO;
import com.credit.rbac.bo.Group;

/**
 * @author Hanlu
 *
 */
public interface GroupMapper extends BaseDAO{

	/**
	 * 
	 * @param ids
	 */
	public void deleteGroupRole(List<String> ids);

	/**
	 * 
	 * @param ids
	 */
	public void deleteGroupUser(List<String> ids);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Group> getByUser(String id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Group> getByRole(String id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Group> getByCompany(String id);

	/**
	 * 
	 * @param ids
	 */
	public void deleteGroupOrg(List<String> ids);

}

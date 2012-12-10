/**
 * 
 */
package com.credit.rbac.dao;

import java.util.List;
import java.util.Map;

import com.bluecloud.persistence.dao.BaseDAO;
import com.bluecloud.persistence.pojo.Relation;
import com.credit.rbac.bo.Role;

/**
 * @author Hanlu
 *
 */
public interface RoleMapper extends BaseDAO{

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public List<Role> getByUser(String userid);

	/**
	 * 
	 * @param ids
	 */
	public void deleteRoleGroup(List<String> ids);

	/**
	 * 
	 * @param ids
	 */
	public void deleteRoleUser(List<String> ids);

	/**
	 * 
	 * @param ids
	 */
	public void deleteRoleFunction(List<String> ids);

	/**
	 * 
	 * @param r
	 */
	public void allocateGroup(Relation r);

	/**
	 * 
	 * @param params
	 */
	public void disallocateGroup(Map<String, Object> params);

	/**
	 * 
	 * @param r
	 */
	public void allocateFunction(Relation r);

	/**
	 * 
	 * @param params
	 */
	public void disallocateFunction(Map<String, Object> params);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Role> getByGroup(String id);
}

/**
 * 
 */
package com.credit.rbac.dao;

import java.util.List;

import com.bluecloud.persistence.dao.BaseDAO;
import com.credit.rbac.bo.Function;
import com.credit.rbac.bo.Operation;

/**
 * @author Hanlu
 * 
 */
public interface FunctionMapper extends BaseDAO {

	/**
	 * 根据functionid，删除角色和功能的关联
	 * 
	 * @param ids
	 */
	public void deleteRoleFunction(List<String> ids);

	/**
	 * 
	 * @param roleid
	 * @return
	 */
	public List<Function> getByRole(String roleid);

	/**
	 * 
	 * @return
	 */
	public List<Operation> getOperations();

	/**
	 * 根据id，获得子功能列表
	 * @param id
	 * @return
	 */
	public List<Function> getChildren(String id);
}

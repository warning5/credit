package com.credit.rbac.dao;

import java.util.List;
import java.util.Map;

import com.bluecloud.persistence.dao.BaseDAO;
import com.bluecloud.persistence.pojo.Relation;
import com.credit.rbac.bo.User;

public interface UserMapper extends BaseDAO{

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param username
	 *            用户名
	 * @return 如果没有此用户名，返回null
	 */
	public User getByUsername(String username);

	/**
	 * 查询用户
	 * 
	 * @param user
	 * @return
	 */
	public List<User> searchUsers(User user);

	/**
	 * 删除用户-组的关联
	 * @param ids 用户ids
	 */
	public void deleteUserGroup(List<String> ids);

	/**
	 * 删除用户-角色的关联
	 * @param ids
	 */
	public void deleteUserRole(List<String> ids);

	/**
	 * 批量分配角色
	 * @param relations
	 */
	public void batchAllocateRole(List<Relation> relations);
	
	/**
	 * 批量分配组
	 * @param relations
	 */
	public void batchAllocateGroup(List<Relation> relations);

	/**
	 * 
	 * @param r
	 */
	public void allocateRole(Relation r);

	/**
	 * 
	 * @param r
	 */
	public void allocateGroup(Relation r);

	/**
	 * 
	 * @param params
	 */
	public void disallocateRole(Map<String, Object> params);

	/**
	 * 
	 * @param params
	 */
	public void disallocateGroup(Map<String, Object> params);
}

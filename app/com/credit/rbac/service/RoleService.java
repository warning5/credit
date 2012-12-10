/**
 * 
 */
package com.credit.rbac.service;

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
import com.credit.rbac.bo.Group;
import com.credit.rbac.bo.Role;
import com.credit.rbac.bo.User;
import com.credit.rbac.dao.RoleMapper;
import com.credit.rbac.exception.RBACServiceException;

/**
 * @author Hanlu
 * 
 */
@Service
public final class RoleService extends RelationService {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleMapper mapper;

	/**
	 * 根据用户获取该用户的角色列表，并将角色列表设置到该用户中
	 * 
	 * @param user
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Role> getRoles(User user) throws RBACServiceException {
		if (null == user) {
			return null;
		}
		List<Role> roles = user.getRoles();
		if (roles != null) {
			return roles;
		}
		List<Group> groups=user.getGroups();
		if(groups==null){
			groups=groupService.getGroups(user);
		}
		Map<String,Role> rs=new HashMap<String,Role>();
		if(groups!=null){
			for(Group group:groups){
				roles=this.getRoles(group);
				if(roles!=null){
					for(Role role:roles){
						rs.put(role.getId(), role);
					}
				}
			}
		}
		try {
			roles = mapper.getByUser(user.getId());
			if(roles!=null){
				for(Role role:roles){
					rs.put(role.getId(), role);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		roles.clear();
		roles.addAll(rs.values());
		user.setRoles(roles);
		return roles;
	}

	/**
	 * 
	 * @param group
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Role> getRoles(Group group) throws RBACServiceException {
		if(group==null){
			return null;
		}
		List<Role> roles = null;
		try {
			roles = mapper.getByGroup(group.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return roles;
	}

	/**
	 * 
	 * @param role
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Role> getRoles(Role role, int offset, int limit)
			throws RBACServiceException {
		if (role == null) {
			return this.getRoles(offset, limit);
		}
		List<Role> roles = null;
		try {
			roles = mapper.searchLimit(role, new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return roles;
	}

	/**
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Role> getRoles(int offset, int limit)
			throws RBACServiceException {
		List<Role> roles = null;
		try {
			roles = mapper.listLimit(new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return roles;
	}

	/**
	 * 获取所有的数据条数
	 * @param role 
	 * 
	 * @return
	 * @throws RBACServiceException
	 */
	public int allCount(Role role) throws RBACServiceException {
		try {
			return mapper.allCount(role);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RBACServiceException 
	 */
	public Role getRoleByID(String id) throws RBACServiceException {
		if(id==null){
			return null;
		}
		Role role=null;
		try {
			role= mapper.getByID(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return role;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean deleteRoles(List<String> ids) throws RBACServiceException {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		try{
			this.mapper.deleteRoleGroup(ids);
			this.mapper.deleteRoleUser(ids);
			this.mapper.deleteRoleFunction(ids);
			this.mapper.delete(ids);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param role
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean updateRole(Role role) throws RBACServiceException {
		if (role == null) {
			return false;
		}
		try{
			this.mapper.update(role);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param role
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean saveRole(Role role) throws RBACServiceException {
		if (role == null) {
			return false;
		}
		try{
			this.mapper.add(role);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param ids
	 * @param disable
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disableRoles(List<String> ids, boolean disable) throws RBACServiceException {
		if(ids==null||ids.isEmpty()){
			return false;
		}
		try{
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("disable", disable);
			params.put("ids", ids);
			mapper.batchUpdate(params);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * @return 
	 * @throws RBACServiceException 
	 * 
	 */
	public List<Role> getRoles() throws RBACServiceException {
		List<Role> roles = null;
		try {
			roles = mapper.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return roles;
	}

	/**
	 * 
	 * @param ids
	 * @param roleid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean allocateGroup(List<String> ids, String roleid) throws RBACServiceException {
		try {
			return allocate(ids, roleid,new AllocationService(){
				/*
				 * (non-Javadoc)
				 * @see com.credit.rbac.service.AllocateService#aloocate(java.util.List)
				 */
				@Override
				public void allocate(List<Relation> relations) {
//				mapper.batchAllocateGroup(relations);
					for(Relation r:relations){
						mapper.allocateGroup(r);
					}
				}
			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param ids
	 * @param roleid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disallocateGroup(List<String> ids, String roleid) throws RBACServiceException {
		try {
			return disallocate(ids, roleid,new DisallocationService(){
				/*
				 * (non-Javadoc)
				 * @see com.bluecloud.persistence.service.DisallocationService#disallocate(java.util.Map)
				 */
				@Override
				public void disallocate(Map<String, Object> params) {
					mapper.disallocateGroup(params);
				}
				
			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param ids
	 * @param roleid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean allocateFunction(List<String> ids, String roleid) throws RBACServiceException {
		try {
			return allocate(ids, roleid,new AllocationService(){
				/*
				 * (non-Javadoc)
				 * @see com.credit.rbac.service.AllocateService#aloocate(java.util.List)
				 */
				@Override
				public void allocate(List<Relation> relations) {
					for(Relation r:relations){
						mapper.allocateFunction(r);
					}
				}
			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param ids
	 * @param roleid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disallocateFunction(List<String> ids, String roleid) throws RBACServiceException {
		try {
			return this.disallocate(ids, roleid, new DisallocationService(){

				/*
				 * (non-Javadoc)
				 * @see com.bluecloud.persistence.service.DisallocationService#disallocate(java.util.Map)
				 */
				@Override
				public void disallocate(Map<String, Object> params) {
					mapper.disallocateFunction(params);
				}
				
			});
		} catch (PersistenceServiceException e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}
}

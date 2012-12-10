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
import com.credit.rbac.bo.User;
import com.credit.rbac.dao.UserMapper;
import com.credit.rbac.exception.RBACServiceException;

/**
 * @author Hanlu
 * 
 */
@Service(value = "userService")
public final class UserService extends RelationService{
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private UserMapper mapper;

	/**
	 * 
	 * @param username
	 * @return
	 * @throws RBACServiceException
	 */
	public User getUser(String username) throws RBACServiceException {
		User user = null;
		try {
			user = mapper.getByUsername(username);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return user;
	}

	/**
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */

	public List<User> getUsers(int offset, int limit)  throws RBACServiceException {
		List<User> users=null;
		try {
			users=mapper.listLimit(new RowBounds(offset, limit));
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return users;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean saveUser(User user) throws RBACServiceException {
		if (user == null) {
			return false;
		}
		try{
			this.mapper.add(user);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param user
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */
	public List<User> getUsers(User user, int offset, int limit) throws RBACServiceException {
		if (user == null) {
			return this.getUsers(offset, limit);
		}
		List<User> users=null;
		try{
			users = mapper.searchLimit(user, new RowBounds(offset, limit));
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return users;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean updateUser(User user) throws RBACServiceException {
		if (user == null) {
			return false;
		}
		try{
			this.mapper.update(user);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param ids
	 * @throws RBACServiceException 
	 */
	public boolean deleteUsers(List<String> ids) throws RBACServiceException {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		try{
			this.mapper.deleteUserGroup(ids);
			this.mapper.deleteUserRole(ids);
			this.mapper.delete(ids);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param userid
	 * @return
	 * @throws RBACServiceException 
	 */
	public User getUserByID(String userid) throws RBACServiceException {
		if (userid == null || userid.trim().equals("")) {
			return null;
		}
		User user=null;
		try{
			user = mapper.getByID(userid);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return user;
	}
	
	/**
	 * 获取所有的数据条数
	 * @param user 
	 * @return
	 * @throws RBACServiceException 
	 */
	public int allCount(User user) throws RBACServiceException {
		try{
			return mapper.allCount(user);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
	}

	/**
	 * 
	 * @param ids
	 * @param disable 
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disableUsers(List<String> ids, boolean disable) throws RBACServiceException {
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
	 * 
	 * @param ids
	 * @param userid 
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean allocateRole(List<String> ids, String userid) throws RBACServiceException {
		try {
			return allocate(ids, userid, new AllocationService(){

				/*
				 * (non-Javadoc)
				 * @see com.bluecloud.persistence.dao.AllocationService#aloocate(java.util.List)
				 */
				@Override
				public void allocate(List<Relation> relations) {
					for(Relation r:relations){
//					mapper.batchAllocateRole(relations);
						mapper.allocateRole(r);
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
	 * @param userid 
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean allocateGroup(List<String> ids, String userid) throws RBACServiceException {
		try {
			return allocate(ids, userid,new AllocationService(){
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
	 * @param userid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disallocateRole(List<String> ids, String userid) throws RBACServiceException {
		try {
			return disallocate(ids, userid,new DisallocationService(){

				/*
				 * (non-Javadoc)
				 * @see com.bluecloud.persistence.service.DisallocationService#disallocate(java.util.Map)
				 */
				@Override
				public void disallocate(Map<String, Object> params) {
					mapper.disallocateRole(params);
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
	 * @param userid
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean disallocateGroup(List<String> ids, String userid) throws RBACServiceException {
		try {
			return disallocate(ids, userid,new DisallocationService(){
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
}

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

import com.credit.org.bo.Company;
import com.credit.rbac.bo.Group;
import com.credit.rbac.bo.Role;
import com.credit.rbac.bo.User;
import com.credit.rbac.dao.GroupMapper;
import com.credit.rbac.exception.RBACServiceException;

/**
 * @author Hanlu
 * 
 */
@Service
public final class GroupService {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private GroupMapper mapper;

	/**
	 * 
	 * @param group
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Group> getGroups(Group group, int offset, int limit)
			throws RBACServiceException {
		if (group == null) {
			return this.getGroups(offset, limit);
		}
		List<Group> groups = null;
		try {
			groups = mapper.searchLimit(group, new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return groups;
	}

	/**
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Group> getGroups(int offset, int limit)
			throws RBACServiceException {
		List<Group> groups = null;
		try {
			groups = mapper.listLimit(new RowBounds(offset, limit));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return groups;
	}

	/**
	 * 获取所有的数据条数
	 * @param group
	 * @return
	 * @throws RBACServiceException
	 */
	public int allCount(Group group) throws RBACServiceException {
		try {
			return mapper.allCount(group);
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
	public Group getGroupByID(String id) throws RBACServiceException {
		if(id==null){
			return null;
		}
		Group group=null;
		try {
			group= mapper.getByID(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return group;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean deleteGroups(List<String> ids) throws RBACServiceException {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		try{
			this.mapper.deleteGroupRole(ids);
			this.mapper.deleteGroupUser(ids);
			this.mapper.deleteGroupOrg(ids);
			this.mapper.delete(ids);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param group
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean updateGroup(Group group) throws RBACServiceException {
		if (group == null) {
			return false;
		}
		try{
			this.mapper.update(group);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param group
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean saveGroup(Group group) throws RBACServiceException {
		if (group == null) {
			return false;
		}
		try{
			this.mapper.add(group);
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
	public boolean disableGroups(List<String> ids, boolean disable) throws RBACServiceException {
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
	 * @param user
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Group> getGroups(User user) throws RBACServiceException {
		if (null == user) {
			return null;
		}
		List<Group> groups = user.getGroups();
		if (groups != null) {
			return groups;
		}
		try {
			groups = mapper.getByUser(user.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		user.setGroups(groups);
		return groups;
	}

	/**
	 * 
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Group> getGroups() throws RBACServiceException {
		List<Group> groups = null;
		try {
			groups = mapper.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return groups;
	}

	/**
	 * 
	 * @param role
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Group> getGroups(Role role) throws RBACServiceException {
		if(role==null){
			return null;
		}
		List<Group> groups = role.getGroups();
		if (groups != null) {
			return groups;
		}
		try {
			groups = mapper.getByRole(role.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		role.setGroups(groups);
		return groups;
	}

	/**
	 * 
	 * @param company
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Group> getGroups(Company company) throws RBACServiceException {
		if(company==null){
			return null;
		}
		List<Group> groups=null;
		try {
			groups = mapper.getByCompany(company.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return groups;
	}
}

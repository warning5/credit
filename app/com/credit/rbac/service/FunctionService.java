/**
 * 
 */
package com.credit.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.rbac.bo.Comp;
import com.credit.rbac.bo.Function;
import com.credit.rbac.bo.Operation;
import com.credit.rbac.bo.Role;
import com.credit.rbac.bo.User;
import com.credit.rbac.dao.FunctionMapper;
import com.credit.rbac.exception.RBACServiceException;

/**
 * @author Hanlu
 * 
 */
@Service
public final class FunctionService {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionMapper functionMapper;

	/**
	 * 根据角色获取该角色功能列表，并将功能列表设置到该角色中
	 * 
	 * @param role
	 * @return
	 * @throws RBACServiceException
	 */
	public Map<String, Function> getFunctions(Role role) throws RBACServiceException {
		if (role == null) {
			return null;
		}
		Map<String, Function> funs = role.getFunctions();
		if (funs != null) {
			return funs;
		}
		funs = new HashMap<String, Function>();
		try {
			List<Function> fs = functionMapper.getByRole(role.getId());
			if (fs == null) {
				return null;
			}
			for (Function f : fs) {
				String key=f.getUri();
				if(key==null||key.trim().equals("")||key.trim().equals("none")){
					key=f.getId();
				}
				funs.put(key, f);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		if (funs.isEmpty()) {
			return null;
		}
		role.setFunctions(funs);
		return funs;
	}

	/**
	 * 
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Function> getFunctions() throws RBACServiceException {
		List<Function> functions = null;
		try {
			functions = functionMapper.list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return functions;
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws RBACServiceException
	 */
	public Map<String, Function> getFunctions(User user) throws RBACServiceException {
		List<Role> roles = user.getRoles();
		if (roles == null) {
			roles = roleService.getRoles(user);
		}
		if (roles == null) {
			return null;
		}
		Map<String, Function> funs = new HashMap<String, Function>();
		for (Role role : roles) {
			if (role.isDisable()) {// 角色被禁用
				continue;
			}
			Map<String, Function> functions = role.getFunctions();
			if (functions == null) {
				functions = this.getFunctions(role);
			}
			if (functions == null) {
				continue;
			}
			funs.putAll(functions);
		}
		if (funs.isEmpty()) {
			return null;
		}
		return funs;
	}

	/**
	 * 
	 * @return
	 * @throws RBACServiceException
	 */
	public List<Operation> getOpearations() throws RBACServiceException {
		List<Operation> operactions = new ArrayList<Operation>();
		try {
			operactions = functionMapper.getOperations();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return operactions;
	}

	/**
	 * 
	 * @return
	 */
	public List<Comp> getComps() {
		List<Comp> comps = new ArrayList<Comp>();
		comps.add(new Comp(0, "只读"));
		comps.add(new Comp(1, "可写"));
		return comps;
	}

	/**
	 * 
	 * @param id
	 * @return 
	 * @throws RBACServiceException 
	 */
	public Function getFunction(String id) throws RBACServiceException {
		Function func=null;
		try {
			func=functionMapper.getByID(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return func;
	}

	/**
	 * 
	 * @param function
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean saveFunction(Function function) throws RBACServiceException {
		if(function==null){
			return false;
		}
		try {
			functionMapper.add(function);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean deleteFunctions(List<String> ids) throws RBACServiceException {
		if(ids==null||ids.isEmpty()){
			return false;
		}
		try {
			functionMapper.deleteRoleFunction(ids);
			functionMapper.delete(ids);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws RBACServiceException 
	 */
	public List<Function> getChildren(String id) throws RBACServiceException {
		if(id==null){
			return null;
		}
		List<Function> functions = null;
		try {
			functions = functionMapper.getChildren(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return functions;
	}

	/**
	 * 
	 * @param function
	 * @return
	 * @throws RBACServiceException 
	 */
	public boolean updateFunction(Function function) throws RBACServiceException {
		if(function==null){
			return false;
		}
		try {
			functionMapper.update(function);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RBACServiceException(e.getMessage());
		}
		return true;
	}
}

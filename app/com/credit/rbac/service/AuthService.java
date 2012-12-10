/**
 * 
 */
package com.credit.rbac.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluecloud.persistence.util.MD5;
import com.credit.rbac.api.IPermission;
import com.credit.rbac.bo.Function;
import com.credit.rbac.bo.User;
import com.credit.rbac.exception.RBACServiceException;

/**
 * @author Hanlu
 * 
 */
@Service
public final class AuthService {

	private static Map<String, Boolean> authContext;
	
	@Autowired
	private FunctionService functionService;

	static{
		authContext = new HashMap<String, Boolean>();
		authContext.put("0:0", new Boolean(true));
		authContext.put("0:1", new Boolean(false));
		authContext.put("0:2", new Boolean(false));
		authContext.put("0:3", new Boolean(false));
		authContext.put("0:4", new Boolean(false));
		authContext.put("0:5", new Boolean(false));
		authContext.put("1:0", new Boolean(true));
		authContext.put("1:1", new Boolean(true));
		authContext.put("1:2", new Boolean(true));
		authContext.put("1:3", new Boolean(true));
		authContext.put("1:4", new Boolean(true));
		authContext.put("1:5", new Boolean(true));
	}

	/**
	 * 
	 * @param user
	 *            用户对象
	 * @param req
	 *            请求对象
	 * @param per 用户自定义验证接口
	 * @return 如果有权限，返回true,否则返回false
	 * @throws RBACServiceException 
	 */
	public boolean isPermission(User user, HttpServletRequest req, IPermission per) throws RBACServiceException {
		if (user==null||user.isDisable()) {//用户被禁用
			return false;
		}
		Map<String, Function> functions = functionService.getFunctions(user);
		if(functions==null){
			return false;
		}
		String uri=per.getURI(req);
		Function function = functions.get(uri);
		if (null != function) {
			StringBuilder key = new StringBuilder();
			key.append(function.getComp()).append(":")
					.append(function.getOperation().getCode());
			return authContext.get(key.toString());
		}
		return false;
	}

	/**
	 * 
	 * @param password
	 * @param user
	 * @return
	 */
	public boolean checkPassword(String password, User user) {
		if(password==null||user==null){
			return false;
		}
		if(user.getPassword()==null||user.getPassword().trim().equals("")){
			return false;
		}
		password=MD5.exchange(password.trim());
		if(!password.equals(user.getPassword())){
			return false;
		}
		return true;
	}
}
	

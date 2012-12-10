/**
 * 
 */
package com.credit.http.user;

import com.credit.rbac.bo.User;

/**
 * @author Hanlu
 * 
 */
public interface IUser {

	/**
	 * 
	 * @return
	 */
	public User getUser();

	/**
	 * 判断是否已经登录
	 * 
	 * @return 已登录返回true，否则返回false
	 */
	public boolean isLogon();

	/**
	 * 登录
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @return 
	 * @throws Exception 
	 */
	public boolean logon(String username, String password) throws Exception;
}

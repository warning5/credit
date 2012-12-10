/**
 * 
 */
package com.credit.rbac.api;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义权限验证接口
 * @author Hanlu
 *
 */
public interface IPermission {

	/**
	 * 获取需要验证的请求地址
	 * @param req 
	 * @return
	 */
	public String getURI(HttpServletRequest req);
}

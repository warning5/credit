/**
 * 
 */
package com.credit.http;

import javax.servlet.http.HttpServletRequest;

import com.credit.rbac.api.IPermission;

/**
 * @author Hanlu 
 *
 */
public class CreditPermission implements IPermission {

	/**
	 * 
	 */
	public CreditPermission() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.credit.rbac.api.IPermission#getURI(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String getURI(HttpServletRequest req) {
		String contextPath=req.getContextPath();
		String uri=req.getRequestURI().substring(contextPath.length());
		if(uri.indexOf(";")>0){
			uri=uri.substring(0,uri.indexOf(";"));
		}
		if(uri.startsWith("/")){
			uri=uri.substring(1);
		}
		return uri;
	}

}

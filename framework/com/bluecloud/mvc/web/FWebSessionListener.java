/**
 * 
 */
package com.bluecloud.mvc.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.bluecloud.mvc.constant.GlobalConstant;

/**
 * @author Hanlu
 *
 */
public class FWebSessionListener implements HttpSessionListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void sessionCreated(HttpSessionEvent e) {
		HttpSession session=e.getSession();
		if(session!=null){
			ServletContext context=session.getServletContext();
			Map<String,HttpSession> sessionCache=(Map<String, HttpSession>) session.getServletContext().getAttribute(GlobalConstant.HTTPSESSIONCACHE);
			if(sessionCache==null){
				sessionCache=new HashMap<String, HttpSession>();
				context.setAttribute(GlobalConstant.HTTPSESSIONCACHE,sessionCache);
			}
			sessionCache.put(session.getId(), session);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		HttpSession session=e.getSession();
		if(session!=null){
			Map<String,HttpSession> sessionCache=(Map<String, HttpSession>) session.getServletContext().getAttribute(GlobalConstant.HTTPSESSIONCACHE);
			if(sessionCache!=null){
				sessionCache.remove(session.getId());
			}
		}
	}

}

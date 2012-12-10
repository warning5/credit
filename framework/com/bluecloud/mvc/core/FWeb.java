/**
 * 
 */
package com.bluecloud.mvc.core;

import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.constant.GlobalConstant;
import com.bluecloud.mvc.web.data.AjaxData;
import com.bluecloud.mvc.web.data.FormData;
import com.bluecloud.mvc.web.http.BCWebRequest;
import com.bluecloud.mvc.web.http.BCWebResponse;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 * 
 */
public final class FWeb {

	private static Log log=LogFactory.getLog(FWeb.class);
	private static ActionDepository fragDepository;
	private static boolean isStart = false;
	private static Exception info;
	public static FragmentActionHandler createHtmlFragmentHandler(
			HttpServletRequest req, HttpServletResponse resp) {
		HtmlFragmentRequest request = new BCWebRequest(req);
		String sessionID=req.getSession().getId();
		HtmlFragmentResponse response = new BCWebResponse(resp,sessionID);
		String reqType=req.getParameter("reqType");
		if(reqType!=null){
			reqType=reqType.trim();
			if(reqType.equals("ajaxForm")){
				request.setData(new AjaxData());
				return new FragmentActionHandlerImpl(request, response,new AjaxData());
			}
			if(reqType.equals("fwebForm")){
				request.setData(new FormData());
				return new FragmentActionHandlerImpl(request, response,new FormData());
			}
		}
		return new FragmentActionHandlerImpl(request, response,new FormData());
	}

	public static ActionDepository getFragmentDepository() {
		return fragDepository;
	}

	/**
	 * @param config 
	 * 
	 */
	public static void start(ServletConfig config) {
		if (isStart()) {
			return;
		}
		log.debug("启动BCWeb...");
		fragDepository = ActionDepositoryFactory.getActionDepository(config);
		try {
			if(config==null){
				throw new Exception("web.xml中没有配置BCWebServlet的初始化参数");
			}
			log.debug("加载MVC组件...");
			fragDepository.load(fragDepository.getClass().getClassLoader());
			log.debug("加载MVC组件完毕");
			isStart = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			info=e;
		}
	}

	/**
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HttpSession getSession(HttpServletRequest req){
		String sessionid=req.getParameter(GlobalConstant.JSESSIONID);
//		String sessionid=req.getRequestedSessionId();
		if(sessionid==null){
			sessionid=req.getParameter(GlobalConstant.JSESSIONID);
		}
		if(sessionid==null){
			return req.getSession();
		}
		HttpSession session=null;
		ServletContext context=req.getSession().getServletContext();
		Map<String,HttpSession> cache=(Map<String, HttpSession>) context.getAttribute(GlobalConstant.HTTPSESSIONCACHE);
		if(cache!=null){
			session=cache.get(sessionid);
			if(session==null){
				sessionid=req.getParameter(GlobalConstant.JSESSIONID);
				session=cache.get(sessionid);
			}
		}
		if(session==null){
			return req.getSession();
		}
		return session;
	}
	/**
	 * 
	 * @return
	 */
	public static boolean isStart() {
		return isStart;
	}

	/**
	 * 
	 * @return
	 */
	public static Exception getStartInfo() {
		return info;
	}

}

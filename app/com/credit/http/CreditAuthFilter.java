/**
 * 
 */
package com.credit.http;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.core.FWeb;
import com.bluecloud.mvc.external.Action;
import com.credit.http.user.IUser;
import com.credit.http.user.OnlineUser;
import com.credit.org.service.OrganizationService;
import com.credit.rbac.api.IPermission;
import com.credit.rbac.service.AuthService;
import com.credit.rbac.service.UserService;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu
 * 
 */
public class CreditAuthFilter implements Filter {

	private Log log = LogFactory.getLog(CreditAuthFilter.class);
	private WebApplicationContext ctx = null;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html");
		log.debug("拦截URI:" + req.getRequestURI());
		try {
			HttpSession session = FWeb.getSession(req);//req.getSession();
			IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
			if (user == null) {
				user = new OnlineUser(getUserIp(req), getService(UserService.class), getService(AuthService.class),getService(OrganizationService.class));
				session.setAttribute(CreditConstants.ONLINEUSER, user);
			}
			AuthService authService = getService(AuthService.class);
			IPermission per = new CreditPermission();
				if (!authService.isPermission(user.getUser(), req, per)) {
					FragmentActionHandler fragmentHandler = FWeb.createHtmlFragmentHandler(req, res);
					ActionDepository depository = FWeb.getFragmentDepository();
					Action action = depository.getAction("auth");
					fragmentHandler.getRrequest().setEventName(null, true);
					fragmentHandler.service(action);
					return;
				}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			req.getRequestDispatcher(res.encodeURL("html/error.html")).forward(request, response);
			return;
		}
		filter.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	private String getUserIp(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}

	private <T> T getService(Class<T> clazz) {
		return ctx.getBean(clazz);
	}
}

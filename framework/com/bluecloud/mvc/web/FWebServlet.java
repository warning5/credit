/**
 * 
 */
package com.bluecloud.mvc.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.core.FWeb;
import com.bluecloud.mvc.external.Action;

/**
 * @author Hanlu
 *
 */
public class FWebServlet extends HttpServlet {

	Log log =LogFactory.getLog(FWebServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6121738819477670441L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if(!FWeb.isStart()){
			throw new ServletException(FWeb.getStartInfo());
		}
		try {
			FragmentActionHandler actionHandler=FWeb.createHtmlFragmentHandler(req,res);
			ActionDepository depository=FWeb.getFragmentDepository();
			String name=actionHandler.getRequestActionName();
			Action action=depository.getAction(name);
			actionHandler.service(action);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		FWeb.start(config);
	}
}

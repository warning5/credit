/**
 * 
 */
package com.bluecloud.mvc.web.http;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.exception.HtmlFragmentDispatcherException;

/**
 * @author Hanlu
 * 
 */
public abstract class HtmlFragmentDispatcher {
	private Log log =LogFactory.getLog(getClass());
	protected String path;

	public HtmlFragmentDispatcher(String path) {
		this.path = path;
	}

	/**
	 * 
	 * @param fragmentHandler
	 * @throws HtmlFragmentDispatcherException 
	 */
	protected final void doExce(FragmentActionHandler fragmentHandler) throws HtmlFragmentDispatcherException{
		HtmlFragmentResponse fResponse = fragmentHandler.getResponse();
		HttpServletResponse response = fResponse.getHttpServletResponse();
		HttpServletRequest request = fragmentHandler.getRrequest().getHttpServletRequest();
		try {
			String s=fResponse.getData().toString();
			if (path.indexOf(".jsp") != -1) {
				request.getRequestDispatcher(path).include(request, response);
				request.setAttribute("FWebResData",s);
				request.getRequestDispatcher("fweb/FWeb.jsp").include(request, response);
			}else{
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(),response.getCharacterEncoding()));
				request.getRequestDispatcher(response.encodeRedirectURL(path)).include(request, response);
				pw.println(s);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentDispatcherException(e);
		}
	}

	/**
	 * 
	 * @param fragmentHandler
	 * @throws HtmlFragmentDispatcherException
	 */
	public abstract void exce(FragmentActionHandler fragmentHandler)
			throws HtmlFragmentDispatcherException;
}

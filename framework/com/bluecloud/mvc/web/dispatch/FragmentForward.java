/**
 * 
 */
package com.bluecloud.mvc.web.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.exception.HtmlFragmentDispatcherException;
import com.bluecloud.mvc.web.http.HtmlFragmentDispatcher;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 *
 */
public class FragmentForward extends HtmlFragmentDispatcher {

	private Log log =LogFactory.getLog(FragmentForward.class);
	/**
	 * @param path 
	 * 
	 */
	public FragmentForward(String path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentDispatcher#exce(com.bluecloud.mvc.api.HttpFragmentHandler)
	 */
	@Override
	public void exce(FragmentActionHandler fragmentHandler) throws HtmlFragmentDispatcherException {
		log.debug("重定向："+path);
		HtmlFragmentResponse fResponse=fragmentHandler.getResponse();
		try {
			if(fResponse.getData().isNull()){
				HttpServletRequest req=fragmentHandler.getRrequest().getHttpServletRequest();
				HttpServletResponse res=fragmentHandler.getResponse().getHttpServletResponse();
				req.getRequestDispatcher(res.encodeRedirectURL(path)).forward(req, res);
				return;
			}
			this.doExce(fragmentHandler);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentDispatcherException(e);
		}
	}

}

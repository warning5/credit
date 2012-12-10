/**
 * 
 */
package com.bluecloud.mvc.web.dispatch;

import java.io.IOException;

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
public class FragmentRedirect extends HtmlFragmentDispatcher {

	private Log log=LogFactory.getLog(FragmentRedirect.class);
	/**
	 * @param path 
	 * 
	 */
	public FragmentRedirect(String path) {
		super(path);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentDispatcher#exce(com.bluecloud.mvc.api.HttpFragmentHandler)
	 */
	@Override
	public void exce(FragmentActionHandler fragmentHandler) throws HtmlFragmentDispatcherException {
		log.debug("转向到："+path);
		HtmlFragmentResponse fResponse=fragmentHandler.getResponse();
		try {
			if(fResponse.getData().isNull()){
				fragmentHandler.getResponse().getHttpServletResponse().sendRedirect(fragmentHandler.getResponse().getHttpServletResponse().encodeRedirectURL(path));
				return;
			}
			this.doExce(fragmentHandler);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentDispatcherException(e);
		}
	}

}

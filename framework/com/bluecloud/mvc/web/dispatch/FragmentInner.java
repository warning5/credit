/**
 * 
 */
package com.bluecloud.mvc.web.dispatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.core.FWeb;
import com.bluecloud.mvc.exception.HtmlFragmentDispatcherException;
import com.bluecloud.mvc.external.Action;
import com.bluecloud.mvc.web.http.HtmlFragmentDispatcher;

/**
 * @author Hanlu
 *2012-9-12
 */
public class FragmentInner extends HtmlFragmentDispatcher {

	private Log log=LogFactory.getLog(FragmentInner.class);
	private String path;
	private String eventName;

	/**
	 * @param path 
	 * 
	 */
	public FragmentInner(String path) {
		super(path);
	}

	/**
	 * 
	 * @param path
	 * @param eventName
	 */
	public FragmentInner(String path, String eventName) {
		this(null);
		this.path=path;
		this.eventName=eventName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentDispatcher#exce(com.bluecloud.mvc.api.HttpFragmentHandler)
	 */
	@Override
	public void exce(FragmentActionHandler fragmentHandler) throws HtmlFragmentDispatcherException {
		log.debug("内部跳转到："+path);
		ActionDepository depository=FWeb.getFragmentDepository();
		String innerPath=this.path.substring(0, this.path.indexOf("."));
		innerPath=innerPath.substring(innerPath.indexOf("_")+1,innerPath.length());
		Action action=depository.getAction(innerPath);
		fragmentHandler.getRrequest().setEventName(eventName,true);
		try {
			fragmentHandler.service(action);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentDispatcherException(e);
		}
	}

}

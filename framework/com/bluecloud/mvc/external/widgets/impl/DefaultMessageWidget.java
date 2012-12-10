/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import com.bluecloud.mvc.external.widgets.MessageWidget;

/**
 * @author Hanlu
 * 
 */
public class DefaultMessageWidget implements MessageWidget {

	public static final String CALLBACKTYPE_FORWARD = "forward";
	public static final String CALLBACKTYPE_CLOSECURRENT = "closeCurrent";
	public static final String CALLBACKTYPE_RELOADTAB = "reloadTab";

	private String name;

	private String statusCode = "200";
	private String callbackType = CALLBACKTYPE_RELOADTAB;
	private String message = "操作成功";
	private String navTabId = "";
	private String forwardUrl = "";
	private String title = "";


	/**
	 * @param name
	 * @param content
	 * 
	 */
	public DefaultMessageWidget(String name, String content) {
		this.name = name;
		this.message = content;
	}

	/**
	 * 
	 * @param content
	 */
	public DefaultMessageWidget(String content) {
		this.message = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getData()
	 */
	@Override
	public Object getData() {
		StringBuilder s = new StringBuilder("{");
		s.append("\"statusCode\":").append("\"").append(this.getStatusCode()).append("\",");
		s.append("\"message\":").append("\"").append(this.getMessage()).append("\",");
		s.append("\"navTabId\":").append("\"").append(this.getNavTabId()).append("\",");
		s.append("\"forwardUrl\":").append("\"").append(this.getForwardUrl()).append("\",");
		s.append("\"callbackType\":").append("\"").append(this.getCallbackType()).append("\",");
		s.append("\"title\":").append("\"").append(this.getTitle()).append("\"");
		s.append("}");
		return s;
	}

	public String getStatusCode() {
		if (statusCode == null || statusCode.trim().equals("")) {
			statusCode = "200";
		}
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		if (message == null) {
			if ("200".equals(statusCode)) {
				message = "操作成功";
			} else if ("300".equals(statusCode)) {
				message = "操作失败";
			}
		}
		return message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	@Override
	public void fail() {
		this.setStatusCode("300");
	}
}

package com.bluecloud.mvc.external;

import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

public interface Action {

	public void setResponse(HtmlFragmentResponse response);

	public FragmentBeanRegister getBeans();

	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException;

	public FragmentEvent getEvent(final String eventName);

}

/**
 * 
 */
package com.bluecloud.mvc.core;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.exception.HtmlFragmentDispatcherException;
import com.bluecloud.mvc.external.Action;
import com.bluecloud.mvc.external.FragmentEvent;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.web.data.RequestData;
import com.bluecloud.mvc.web.data.ResponseData;
import com.bluecloud.mvc.web.http.HtmlFragmentDispatcher;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 * 
 */
public class FragmentActionHandlerImpl implements FragmentActionHandler {

	private Log log = LogFactory.getLog(FragmentActionHandlerImpl.class);
	protected HtmlFragmentRequest request;
	protected HtmlFragmentResponse response;

	public FragmentActionHandlerImpl(HtmlFragmentRequest request, HtmlFragmentResponse response, RequestData reqData) {
		request.setData(reqData);
		this.request = request;
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.api.FragmentActionHandler#getRequestActionName()
	 */
	@Override
	public final String getRequestActionName() {
		return request.getActionName();
	}

	@Override
	public final void service(Action action) throws Exception {
		try {
			if (action == null) {
				this.processError(response, "html/404.html", "请求地址不正确："
						+ request.getHttpServletRequest().getRequestURI());
				return;
			}
			
			action.setResponse(response);
			request.setBeans(action.getBeans());
			String eventName = request.getEventName();
			if (eventName == null) {
				action.execute(request);
			} else {
				FragmentEvent event = action.getEvent(eventName);
				if (null == event) {
					this.processError(response, "html/404.html", "请求地址不正确："
							+ request.getHttpServletRequest().getRequestURI());
					return;
				} else {
					event.execute(request, action);
				}
			}
			ResponseData resData = response.getData();
			HtmlFragmentDispatcher dispatch = resData.getDispatcher();
			if (null != dispatch) {
				dispatch.exce(this);
			} else {
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getHttpServletResponse()
						.getOutputStream(), response.getHttpServletResponse().getCharacterEncoding()));
				String s = response.getData().toString();
				pw.println(s);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			this.processError(response, "html/error.html", e.getMessage());
		}
	}

	/**
	 * @param res
	 * @param path
	 * @param info
	 * @throws HtmlFragmentDispatcherException
	 * 
	 */
	private void processError(HtmlFragmentResponse res, String path, String info)
			throws HtmlFragmentDispatcherException {
		log.debug("请求出错，转向:" + path);
		MessageWidget message = new DefaultMessageWidget(info);
		message.fail();
		res.addMessage(message);
		res.forward(path);
		res.getData().getDispatcher().exce(this);
	}

	/**
	 * 
	 * @return
	 */
	public final HtmlFragmentRequest getRrequest() {
		return request;
	}

	/**
	 * 
	 * @return
	 */
	public final HtmlFragmentResponse getResponse() {
		return response;
	}
}

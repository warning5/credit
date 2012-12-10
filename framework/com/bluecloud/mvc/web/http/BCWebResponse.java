/**
 * 
 */
package com.bluecloud.mvc.web.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.bluecloud.mvc.external.widgets.BizWidget;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.HtmlWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.TableWidget;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.web.data.ResponseData;
import com.bluecloud.mvc.web.dispatch.DownloadDispatcher;
import com.bluecloud.mvc.web.dispatch.FragmentForward;
import com.bluecloud.mvc.web.dispatch.FragmentInner;
import com.bluecloud.mvc.web.dispatch.FragmentRedirect;

/**
 * @author Hanlu
 * 
 */
public final class BCWebResponse implements HtmlFragmentResponse {

	private HttpServletResponse res;
	private ResponseData responseData;

	public BCWebResponse(HttpServletResponse res, String sessionID) {
		this.res = res;
		responseData = new ResponseData(sessionID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#getData()
	 */
	@Override
	public ResponseData getData() {
		return responseData;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#forward(java.lang.String)
	 */
	@Override
	public void forward(String path) {
		responseData.setDispatcher(new FragmentForward(path));
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#redirect(java.lang.String)
	 */
	@Override
	public void redirect(String path) {
		responseData.setDispatcher(new FragmentRedirect(path));
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#inner(java.lang.String, java.lang.String)
	 */
	@Override
	public void inner(String actionName,String eventName) {
		responseData.setDispatcher(new FragmentInner(actionName,eventName));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#getHttpServletResponse()
	 */
	@Override
	public HttpServletResponse getHttpServletResponse() {
		return res;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addTree(com.bluecloud.mvc.external.widgets.TreeWidget)
	 */
	@Override
	public void addTree(TreeWidget tree) {
		responseData.setTree(tree);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addTreeMenus(com.bluecloud.mvc.external.widgets.TreeWidget)
	 */
	@Override
	public void addTreeMenus(TreeWidget treeMenus) {
		responseData.setTreeMenus(treeMenus);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addMessage(com.bluecloud.mvc.external.widgets.MessageWidget)
	 */
	@Override
	public void addMessage(MessageWidget message) {
		responseData.setMessage(message);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addForm(com.bluecloud.mvc.external.widgets.FormWidget)
	 */
	@Override
	public void addForm(FormWidget form) {
		responseData.setForm(form);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addTable(com.bluecloud.mvc.external.widgets.TableWidget)
	 */
	@Override
	public void addTable(TableWidget table) {
		responseData.setTable(table);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addHtml(com.bluecloud.mvc.external.widgets.HtmlWidget)
	 */
	@Override
	public void addHtml(HtmlWidget html) {
		responseData.setHtml(html);
	}
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addSelect(com.bluecloud.mvc.external.widgets.SelectWidget)
	 */
	@Override
	public void addSelect(SelectWidget select) {
		responseData.setSelect(select);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addCheckTree(com.bluecloud.mvc.external.widgets.TreeWidget)
	 */
	@Override
	public void addCheckTree(TreeWidget tree) {
		responseData.setCheckTree(tree);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#addBiz(com.bluecloud.mvc.external.widgets.BizWidget)
	 */
	@Override
	public void addBiz(BizWidget biz) {
		responseData.setBiz(biz);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentResponse#download(java.io.File, java.lang.String)
	 */
	@Override
	public void download(File file, String fileName) throws IOException {
		responseData.setDispatcher(new DownloadDispatcher(new FileInputStream(file),fileName));
	}
}

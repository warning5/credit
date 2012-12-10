/**
 * 
 */
package com.bluecloud.mvc.web.http;

import java.io.File;
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

/**
 * @author Hanlu
 * 
 */
public interface HtmlFragmentResponse {

	/**
	 * 
	 * @return
	 */
	ResponseData getData();

	/**
	 * 
	 * @param path
	 */
	void redirect(String path);

	/**
	 * 
	 * @param path
	 */
	void forward(String path);

	/**
	 * 
	 * @param actionName
	 * @param eventName
	 */
	void inner(String actionName,String eventName);

	/**
	 * @return 
	 * 
	 */
	HttpServletResponse getHttpServletResponse();

	/**
	 * 
	 * @param tree
	 */
	void addTree(TreeWidget tree);

	/**
	 * 
	 * @param treeMenus
	 */
	void addTreeMenus(TreeWidget treeMenus);

	/**
	 * 
	 * @param message
	 */
	void addMessage(MessageWidget message);

	/**
	 * 
	 * @param form
	 */
	void addForm(FormWidget form);

	/**
	 * 
	 * @param table
	 */
	void addTable(TableWidget table);

	/**
	 * 
	 * @param html
	 */
	void addHtml(HtmlWidget html);

	/**
	 * 
	 * @param select
	 */
	void addSelect(SelectWidget select);

	/**
	 * 
	 * @param tree
	 */
	void addCheckTree(TreeWidget tree);

	/**
	 * 
	 * @param biz
	 */
	void addBiz(BizWidget biz);

	/**
	 * 
	 * @param file
	 * @param fileName
	 * @throws IOException 
	 */
	void download(File file, String fileName) throws IOException;

}

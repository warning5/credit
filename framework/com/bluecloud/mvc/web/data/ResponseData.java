/**
 * 
 */
package com.bluecloud.mvc.web.data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONSerializer;


import com.bluecloud.mvc.external.widgets.BizWidget;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.HtmlWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.TableWidget;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentDispatcher;

/**
 * @author Hanlu
 * 
 */
public final class ResponseData {

	private HtmlFragmentDispatcher dispatcher;
	private Map<String, TreeWidget> tree;
	private Map<String, SelectWidget> select;
	private Map<String, TableWidget> table;
	private Map<String, FormWidget> form;
	private Map<String, MessageWidget> message;
	private Map<String, TreeWidget> treeMenus;
	private Map<String, TreeWidget> checkTree;
	private HashMap<String, HtmlWidget> html;
	private String json;
	private String JSESSIONID;
	private Map<String, BizWidget> biz;

	public ResponseData(String sessionID) {
		this.JSESSIONID=sessionID;
	}

	public HtmlFragmentDispatcher getDispatcher() {
		return this.dispatcher;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNull() {
		if (this.treeMenus != null && !this.treeMenus.isEmpty()) {
			return false;
		}
		if (this.tree != null && !this.tree.isEmpty()) {
			return false;
		}
		if (this.checkTree != null && !this.checkTree.isEmpty()) {
			return false;
		}
		if (this.select != null && !this.select.isEmpty()) {
			return false;
		}
		if (this.table != null && !this.table.isEmpty()) {
			return false;
		}
		if (this.form != null && !this.form.isEmpty()) {
			return false;
		}
		if (this.message != null && !this.message.isEmpty()) {
			return false;
		}
		if (this.biz != null && !this.biz.isEmpty()) {
			return false;
		}
		return true;
	}

	public void clear() {
		message = null;
		form = null;
		table = null;
		select = null;
		tree = null;
		treeMenus = null;
	}

	@Override
	public String toString() {

		if (StringUtils.isNotEmpty(json)) {
			return json;
		}

		StringBuffer s = new StringBuffer("<div id=\"FWebResData\" JSESSIONID=\""+JSESSIONID+"\"  style=\"display:none;\">");
		if (this.tree != null) {
			s.append(loadTree());
		}
		if (this.checkTree != null) {
			s.append(loadCheckTree());
		}
		if (this.treeMenus != null) {
			s.append(loadTreeMenus());
		}
		if (this.select != null) {
			s.append(loadSelect());
		}
		if (this.table != null) {
			s.append(loadTable());
		}
		if (this.form != null) {
			s.append(loadForm());
		}
		if (this.message != null) {
			s.append(loadMessage());
		}
		if (this.biz != null) {
			s.append(loadBiz());
		}
		if (this.html != null) {
			s.append(loadHtml());
		}
		s.append("</div>");
		return s.toString();
	}

	private String loadHtml() {
		StringBuffer s = new StringBuffer("<div id=\"FWebHtmlData\"  style=\"display:none;\">");
		Iterator<Entry<String, HtmlWidget>> bizs = this.html.entrySet().iterator();
		while (bizs.hasNext()) {
			Entry<String, HtmlWidget> entry = bizs.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadBiz() {
		StringBuffer s = new StringBuffer("<div id=\"FWebBizData\"  style=\"display:none;\">");
		Iterator<Entry<String, BizWidget>> bizs = this.biz.entrySet().iterator();
		while (bizs.hasNext()) {
			Entry<String, BizWidget> entry = bizs.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadTreeMenus() {
		StringBuffer s = new StringBuffer("<div id=\"FWebTreeMenusData\"  style=\"display:none;\">");
		Iterator<Entry<String, TreeWidget>> trees = this.treeMenus.entrySet().iterator();
		while (trees.hasNext()) {
			Entry<String, TreeWidget> entry = trees.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadMessage() {
		StringBuffer s = new StringBuffer("<div id=\"FWebMessageData\"  style=\"display:none;\">");
		Iterator<Entry<String, MessageWidget>> messages = this.message.entrySet().iterator();
		while (messages.hasNext()) {
			Entry<String, MessageWidget> entry = messages.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(JSONSerializer.toJSON(entry.getValue()));
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadForm() {
		StringBuffer s = new StringBuffer("<div id=\"FWebFormData\"  style=\"display:none;\">");
		Iterator<Entry<String, FormWidget>> forms = this.form.entrySet().iterator();
		while (forms.hasNext()) {
			Entry<String, FormWidget> entry = forms.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadTable() {
		StringBuffer s = new StringBuffer("<div id=\"FWebTableData\"  style=\"display:none;\">");
		Iterator<Entry<String, TableWidget>> tables = this.table.entrySet().iterator();
		while (tables.hasNext()) {
			Entry<String, TableWidget> entry = tables.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadSelect() {
		StringBuffer s = new StringBuffer("<div id=\"FWebSelectData\"  style=\"display:none;\">");
		Iterator<Entry<String, SelectWidget>> selects = this.select.entrySet().iterator();
		while (selects.hasNext()) {
			Entry<String, SelectWidget> entry = selects.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @return
	 */
	private String loadTree() {
		StringBuffer s = new StringBuffer("<div id=\"FWebTreeData\"  style=\"display:none;\">");
		Iterator<Entry<String, TreeWidget>> trees = this.tree.entrySet().iterator();
		while (trees.hasNext()) {
			Entry<String, TreeWidget> entry = trees.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}
	/**
	 * 
	 * @return
	 */
	private String loadCheckTree() {
		StringBuffer s = new StringBuffer("<div id=\"FWebCheckTreeData\"  style=\"display:none;\">");
		Iterator<Entry<String, TreeWidget>> trees = this.checkTree.entrySet().iterator();
		while (trees.hasNext()) {
			Entry<String, TreeWidget> entry = trees.next();
			s.append("<div name=\"");
			s.append(entry.getKey());
			s.append("\">");
			s.append(entry.getValue().getData());
			s.append("</div>");
		}
		s.append("</div>");
		return s.toString();
	}

	/**
	 * 
	 * @param fragmentForward
	 */
	public void setDispatcher(HtmlFragmentDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * 
	 * @param tree
	 */
	public void setTree(TreeWidget tree) {
		if (this.tree == null) {
			this.tree = new HashMap<String, TreeWidget>();
		}
		this.tree.put(tree.getName(), tree);
	}

	/**
	 * 
	 * @param treeWidget
	 */
	public void setTreeMenus(TreeWidget treeWidget) {
		if (this.treeMenus == null) {
			this.treeMenus = new HashMap<String, TreeWidget>();
		}
		this.treeMenus.put(treeWidget.getName(), treeWidget);
	}

	/**
	 * 
	 * @param messageWidget
	 */
	public void setMessage(MessageWidget messageWidget) {
		if (this.message == null) {
			this.message = new HashMap<String, MessageWidget>();
		}
		this.message.put(messageWidget.getName(), messageWidget);
	}

	/**
	 * 
	 * @param formWidget
	 */
	public void setForm(FormWidget formWidget) {
		if (this.form == null) {
			this.form = new HashMap<String, FormWidget>();
		}
		this.form.put(formWidget.getName(), formWidget);
	}

	/**
	 * 
	 * @param tableWidget
	 */
	public void setTable(TableWidget tableWidget) {
		if (this.table == null) {
			this.table = new HashMap<String, TableWidget>();
		}
		this.table.put(tableWidget.getName(), tableWidget);
	}

	/**
	 * 
	 * @param htmlWidget
	 */
	public void setHtml(HtmlWidget htmlWidget) {
		if (this.html == null) {
			this.html = new HashMap<String, HtmlWidget>();
		}
		this.html.put(htmlWidget.getName(), htmlWidget);
	}

	public void asJson(String json) {
		this.json = json;
	}

	/**
	 * 
	 * @param select
	 */
	public void setSelect(SelectWidget select) {
		if (this.select == null) {
			this.select = new HashMap<String, SelectWidget>();
		}
		this.select.put(select.getName(), select);
	}

	/**
	 * 
	 * @param tree
	 */
	public void setCheckTree(TreeWidget tree) {
		if (this.checkTree == null) {
			this.checkTree = new HashMap<String, TreeWidget>();
		}
		this.checkTree.put(tree.getName(), tree);
	}

	/**
	 * 
	 * @param biz
	 */
	public void setBiz(BizWidget bizWidget) {
		if (this.biz == null) {
			this.biz = new HashMap<String, BizWidget>();
		}
		this.biz.put(bizWidget.getName(), bizWidget);
	}

}

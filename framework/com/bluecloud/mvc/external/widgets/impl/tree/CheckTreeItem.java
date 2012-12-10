/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl.tree;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;

/**
 * @author Hanlu
 *
 */
public class CheckTreeItem extends FunctionTreeItem {

	/**
	 * @param bean 
	 * 
	 */
	public CheckTreeItem(FunctionTreeBean bean) {
		super(bean);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.tree.FunctionTreeItem#parseData(com.bluecloud.mvc.external.widgets.impl.tree.TreeItem, java.lang.String, boolean)
	 */
	@Override
	public String parseData(TreeItem item, String target, boolean isOpera) {
		StringBuilder li=new StringBuilder("<li>");
		StringBuilder a=new StringBuilder("<a");
		a.append(" tname=\"ids\"");
		a.append(" tvalue=\""+item.getBean().getId()+"\"");
		a.append(">");
		a.append(item.getBean().getName());
		a.append("</a>");
		li.append(a);
		if(!item.items.isEmpty()){
			String d=item.getData(isOpera,target);
			if(!d.trim().equals("")){
				StringBuilder ul=new StringBuilder("<ul>");
				ul.append(d);
				ul.append("</ul>");
				li.append(ul);
			}
		}
		li.append("</li>");
		return li.toString();
	}

}

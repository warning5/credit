/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hanlu
 *
 */
public class FunctionTreeMenus extends BaseTree{
	/**
	 * 
	 */
	public FunctionTreeMenus() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.tree.BaseTree#getData(boolean, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getData(boolean isOpera, String target) {
		List<String> treeData=new ArrayList<String>();
		for(TreeItem item:this.items.values()){
			if(item.getBean().isOpera()){
				continue;
			}
			StringBuilder header=new StringBuilder("<div class=\"accordionHeader\">");
			header.append("<h2>");
			header.append("<span>Folder</span>");
			header.append(item.getBean().getName());
			header.append("</h2>");
			header.append("</div>");
			
			StringBuilder tree=new StringBuilder("<ul class=\"tree treeFolder\">");
			tree.append(item.getData(isOpera,target));
			tree.append("</ul>");
			
			StringBuilder content=new StringBuilder("<div class=\"accordionContent\">");
			content.append(tree);
			content.append("</div>");
			
			StringBuilder treeMenus=new StringBuilder();
			treeMenus.append(header).append(content);
			
			treeData.add(treeMenus.toString());
		}
		return treeData;
	}

}

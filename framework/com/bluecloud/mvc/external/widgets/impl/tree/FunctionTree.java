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
public class FunctionTree extends BaseTree {

	private String name;

	/**
	 * 
	 */
	public FunctionTree(String name) {
		this.name=name;
	}

	public FunctionTree() {
	}
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.tree.BaseTree#getData(boolean, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<String> getData(boolean isOpera, String target) {
		List<String> treeData=new ArrayList<String>();
//		StringBuilder tree=new StringBuilder("<ul class=\"tree treeFolder collapse\">");
		StringBuilder tree=new StringBuilder("<ul class=\"tree treeFolder expand\">");
		StringBuilder main=new StringBuilder("<li>");
		StringBuilder a=new StringBuilder("<a");
		a.append(" href=\"javascript\"");
		a.append(" target=\""+target+"\"");
		a.append(" rel=\"\"");
		a.append(">");
		a.append(name);
		a.append("</a>");
		main.append(a);
		main.append("<ul>");
		for(TreeItem item:this.items.values()){
			if(!item.items.isEmpty()){
				main.append(item.parseData(item, target, isOpera));
			}else{
				main.append(item.getData(isOpera,target));
			}
		}
		main.append("</ul>");
		main.append("</li>");
		tree.append(main);
		tree.append("</ul>");
		treeData.add(tree.toString());
		return treeData;
	}

}

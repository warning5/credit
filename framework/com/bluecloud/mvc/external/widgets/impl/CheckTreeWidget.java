/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.List;
import java.util.Map;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;
import com.bluecloud.mvc.external.widgets.impl.tree.BaseTree;
import com.bluecloud.mvc.external.widgets.impl.tree.CheckTree;
import com.bluecloud.mvc.external.widgets.impl.tree.CheckTreeItem;
import com.bluecloud.mvc.external.widgets.impl.tree.TreeItem;

/**
 * @author Hanlu
 *
 */
public class CheckTreeWidget extends FunctionTreeWidget {

	private String rootName;
	/**
	 * @param name 
	 * @param beans 
	 * @param rootName 
	 * 
	 */
	public CheckTreeWidget(String name, List<? extends FunctionTreeBean> beans, String rootName) {
		super(name,beans);
		this.rootName=rootName;
	}

	/**
	 * 
	 * @param name
	 * @param rootName
	 */
	public CheckTreeWidget(String name, String rootName) {
		super(name);
		this.rootName=rootName;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getData()
	 */
	@Override
	public Object getData() {
		return getData(false, new CheckTree(this.rootName), null);
	}

	/**
	 * 
	 * @param bean
	 * @param fbeans
	 * @param tree
	 */
	@Override
	protected void parseTree(FunctionTreeBean bean,
			Map<String, FunctionTreeBean> fbeans, BaseTree tree) {
		String parentId=bean.getParent();
		if(parentId==null){
			CheckTreeItem item=new CheckTreeItem(bean);
			tree.addItem(bean.getId(), item);
		}else{
			parseTree(parentId, tree, fbeans,bean);
		}
	}
	
	/**
	 * @param parentId 
	 * @param tree
	 * @param fbeans 
	 * @param bean 
	 * 
	 */
	@Override
	protected void parseTree(String parentId, BaseTree tree, Map<String, FunctionTreeBean> fbeans, FunctionTreeBean bean) {
		TreeItem parentItem=tree.getItem(parentId);//获取父节点
		if(parentItem==null){
			FunctionTreeBean parent=fbeans.get(parentId);
			if(parent!=null){
				parseTree(parent, fbeans, tree);
				parseTree(parentId, tree, fbeans,bean);
			}
		}else{
			CheckTreeItem item=new CheckTreeItem(bean);
			parentItem.addItem(bean.getId(), item);
		}
	}
}

/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.tree.BaseTree;
import com.bluecloud.mvc.external.widgets.impl.tree.FunctionTree;
import com.bluecloud.mvc.external.widgets.impl.tree.FunctionTreeItem;
import com.bluecloud.mvc.external.widgets.impl.tree.TreeItem;

/**
 * @author Hanlu
 *
 */
public class FunctionTreeWidget implements TreeWidget {

	private String name;
	protected List<? extends FunctionTreeBean> beans;

	/**
	 * @param name 
	 * @param beans 
	 * 
	 */
	public FunctionTreeWidget(String name, List<? extends FunctionTreeBean> beans) {
		this.name=name;
		this.beans=beans;
	}

	/**
	 * 
	 * @param name
	 */
	public FunctionTreeWidget(String name) {
		this.name=name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getData()
	 */
	@Override
	public Object getData() {
		return this.getData(false,new FunctionTree("功能树"),"ajax");
	}

	/**
	 * 
	 * @param isOpera
	 * @param tree
	 * @param target 
	 * @return
	 */
	protected final Object getData(boolean isOpera,BaseTree tree, String target) {
		if(beans==null||beans.isEmpty()){
			return null;
		}
		Map<String, FunctionTreeBean> fbeans=new HashMap<String, FunctionTreeBean>(beans.size());
		for(FunctionTreeBean bean:beans){
			fbeans.put(bean.getId(), bean);
		}
		List<String> treeData=parseTree(fbeans,tree,isOpera, target);
		StringBuilder s=new StringBuilder();
		for(String data:treeData){
			s.append("<div>");
			s.append(data);
			s.append("</div>");
		}
		return s;
	}

	/**
	 * 
	 * @param fbeans
	 * @param treeMenus
	 * @param isOpera 
	 * @param target 
	 * @return
	 */
	private List<String> parseTree(Map<String, FunctionTreeBean> fbeans,
			BaseTree ftree, boolean isOpera, String target) {
		Iterator<Entry<String, FunctionTreeBean>> entries=fbeans.entrySet().iterator();
		while(entries.hasNext()){
			Entry<String, FunctionTreeBean> entry=entries.next();
			FunctionTreeBean bean=entry.getValue();
			if(ftree.getItem(bean.getId())==null){
				this.parseTree(bean,fbeans,ftree);
			}
		}
		return ftree.getData(isOpera,target);
	}

	/**
	 * 
	 * @param bean
	 * @param fbeans
	 * @param tree
	 */
	protected void parseTree(FunctionTreeBean bean,
			Map<String, FunctionTreeBean> fbeans, BaseTree tree) {
		String parentId=bean.getParent();
		if(parentId==null){
			FunctionTreeItem item=new FunctionTreeItem(bean);
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
	protected void parseTree(String parentId, BaseTree tree, Map<String, FunctionTreeBean> fbeans, FunctionTreeBean bean) {
		TreeItem parentItem=tree.getItem(parentId);//获取父节点
		if(parentItem==null){
			FunctionTreeBean parent=fbeans.get(parentId);
			if(parent!=null){
				parseTree(parent, fbeans, tree);
				parseTree(parentId, tree, fbeans,bean);
			}
		}else{
			FunctionTreeItem item=new FunctionTreeItem(bean);
			parentItem.addItem(bean.getId(), item);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.TreeWidget#reload(java.util.List, java.util.List)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void reload(List<? extends FunctionTreeBean> parent,
			List<? extends FunctionTreeBean> child) {
		if(child==null||child.isEmpty()){
			this.beans=parent;
			return;
		}
		Map<String, FunctionTreeBean> fbeans=new HashMap<String, FunctionTreeBean>(parent.size());
		for(FunctionTreeBean bean:parent){
			fbeans.put(bean.getId(), bean);
		}
		BaseTree ftree=new FunctionTree();
		Iterator<Entry<String, FunctionTreeBean>> entries=fbeans.entrySet().iterator();
		while(entries.hasNext()){
			Entry<String, FunctionTreeBean> entry=entries.next();
			FunctionTreeBean bean=entry.getValue();
			if(ftree.getItem(bean.getId())==null){
				this.parseTree(bean,fbeans,ftree);
			}
		}
		List<String> ids=new ArrayList<String>();
		for(FunctionTreeBean bean:child){
			this.removeItem(bean.getId(),ftree,ids);
		}
		for(String id:ids){
			fbeans.remove(id);
		}
		this.beans=new ArrayList(fbeans.values());
	}

	/**
	 * 
	 * @param id
	 * @param ftree 
	 * @param ids 
	 */
	private void removeItem(String id, BaseTree ftree, List<String> ids) {
		TreeItem item=ftree.getItem(id);
		if(item==null){
			return;
		}
		if(!item.hasChilds()){
			item=ftree.removeItem(item);
			if(item!=null){
				ids.add(id);
				String pid=item.getBean().getParent();
				if(pid==null){
					return;
				}
				this.removeItem(pid,ftree, ids);
			}
		}
	}
}

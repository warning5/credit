/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl.tree;

import com.bluecloud.mvc.external.beans.FunctionTreeBean;


/**
 * @author Hanlu
 *
 */
public class FunctionTreeItem extends TreeItem{
	/**
	 * @param bean 
	 * 
	 */
	public FunctionTreeItem(FunctionTreeBean bean) {
		super(bean);
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.widgets.impl.tree.BaseTree#getData(boolean, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String getData(boolean isOpera, String target) {
		StringBuilder tree=new StringBuilder();
		if(this.items.isEmpty()){
			tree.append(parseData(this, target, isOpera));
		}else{
			for(TreeItem item:this.items.values()){
				if(isOpera&&item.getBean().isOpera()){
					continue;
				}
				tree.append(parseData(item, target, isOpera));
			}
		}
		return tree.toString();
	}

	/**
	 * @param item 
	 * @param target 
	 * @param isOpera 
	 * @return 
	 * 
	 */
	public String parseData(TreeItem item, String target, boolean isOpera) {
		StringBuilder li=new StringBuilder("<li>");
		String uri=item.getBean().getUri();
		String funcId=item.getBean().getId();
		if(uri!=null&&!uri.trim().equals("none")&&!uri.trim().equals("")){
			if(uri.startsWith("/")){
				uri=uri.substring(1);
			}
			StringBuilder a=new StringBuilder("<a");
			a.append(" href=\""+uri+"\"");
			a.append(" target=\""+target+"\"");
			a.append(" rel=\""+this.getRel(item.getBean())+"\"");
			a.append(" pojo_id=\""+funcId+"\"");
			a.append(">");
			a.append(item.getBean().getName());
			a.append("</a>");
			li.append(a);
		}else{
			StringBuilder a=new StringBuilder("<a");
			a.append(" pojo_id=\""+funcId+"\"");
//			a.append(" target=\""+target+"\"");
			a.append(">");
			a.append(item.getBean().getName());
			a.append("</a>");
			li.append(a);
		}
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

	/**
	 * 
	 * @param bean
	 * @return
	 */
	private String getRel(FunctionTreeBean bean) {
		String uri=bean.getUri();
		if(uri.startsWith("/")){
			uri=uri.substring(1);
		}
		int i=uri.indexOf(".");
		String rel;
		if(i!=-1){
			rel=uri.substring(0, i);
		}else{
			rel=uri;
		}
		return rel;
	}
}

/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl.tree;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Hanlu
 *
 */
public abstract class BaseTree {
	protected Map<String,TreeItem> items=new LinkedHashMap<String,TreeItem>();
	/**
	 * 
	 */
	public BaseTree() {
	}

	/**
	 * 
	 * @param id
	 * @param item
	 */
	public final void addItem(String id, TreeItem item) {
		if(this.items.isEmpty()){
			this.items.put(id, item);
		}else{
			int i=item.getBean().getFunctionOrder();
			TreeItem[] tis=this.items.values().toArray(new TreeItem[0]);
			int order=tis[tis.length-1].getBean().getFunctionOrder();
			if(i>=order){
				this.items.put(id, item);
			}else{
				int sum=order-i;
				if(sum>tis.length){
					int split=sum/tis.length;
					this.change(split, tis, item, id);
				}else{
					this.change(1, tis, item, id);
				}
			}
		}
	}

	/**
	 * 
	 * @param split
	 * @param tis
	 * @param item
	 * @param id
	 */
	private void change(int split, TreeItem[] tis, TreeItem item, String id) {
		Map<String,TreeItem> cache=new LinkedHashMap<String,TreeItem>();
		int i=tis.length-split<0?0:tis.length-split;
		for(int n=0;n<i;n++){
			TreeItem ti=tis[n];
			cache.put(ti.getBean().getId(), ti);
		}
		cache.put(id, item);
		for(int n=i;n<tis.length;n++){
			TreeItem ti=tis[n];
			cache.put(ti.getBean().getId(), ti);
		}
		this.items.clear();
		this.items.putAll(cache);
		cache.clear();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public final TreeItem getItem(String id) {
		TreeItem item=this.items.get(id);
		if(item==null){
			Iterator<Entry<String, TreeItem>> cache=this.items.entrySet().iterator();
			while(cache.hasNext()){
				Entry<String, TreeItem> entry=cache.next();
				item=entry.getValue().getItem(id);
				if(item!=null){
					break;
				}
			}
		}
		return item;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param isOpera
	 * @param target
	 * @return
	 */
	public abstract <T> T getData(boolean isOpera,String target);

	/**
	 * 
	 * @param item
	 * @return 
	 */
	public TreeItem removeItem(TreeItem item) {
		TreeItem ti=this.items.remove(item.getBean().getId());
		if(ti==null){
			Iterator<Entry<String, TreeItem>> cache=this.items.entrySet().iterator();
			while(cache.hasNext()){
				Entry<String, TreeItem> entry=cache.next();
				ti=entry.getValue().removeItem(item);
				if(ti!=null){
					break;
				}
			}
		}
		return ti;
	}
}

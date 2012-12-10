/**
 * 
 */
package com.bluecloud.mvc.external;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.external.beans.FragmentBean;

/**
 * @author Hanlu
 *2012-9-11
 */
public final class FragmentBeanRegister {
	private Log log =LogFactory.getLog(getClass());
	Map<String, Class<? extends FragmentBean>> beans;

	/**
	 * 
	 */
	public FragmentBeanRegister() {
		beans = new HashMap<String, Class<? extends FragmentBean>>();
	}
	
	/**
	 * 
	 * @param beanName 
	 * @param beanClass
	 */
	public void add(String beanName, Class<? extends FragmentBean> beanClass) {
		if(beanName!=null&&!beanName.trim().equals("")){
			beans.put(beanName, beanClass);
		}
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public FragmentBean find(String name) {
		Class<? extends FragmentBean> beanClass=beans.get(name);
		if(beanClass==null){
			return null;
		}
		FragmentBean bean = null;
		try {
			bean = beanClass.newInstance();
			return bean;
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return bean;
	}

}

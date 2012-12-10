/**
 * 
 */
package com.bluecloud.mvc.external;

import java.lang.reflect.Method;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;

/**
 * @author Hanlu
 * 
 */
public abstract class FragmentAction implements Action{

	private HtmlFragmentResponse response;
	private FragmentEventRegister regEvent;
	private FragmentBeanRegister regBean;
	public FragmentAction() {
		this.regEvent=regEvent();
		this.regBean=regBean();
	}

	/**
	 * 注册事件，用于每个Actiond的方法
	 * 
	 * @return 事件注册器
	 */
	private final FragmentEventRegister regEvent(){
		FragmentEventRegister reg=new FragmentEventRegister();
		return reg;
	}

	/**
	 * 注册bean，用户每个页面或者片段提交数据的封装
	 * @return
	 */
	protected abstract FragmentBeanRegister regBean();

	/**
	 * 
	 * @param response
	 */
	public final void setResponse(HtmlFragmentResponse response) {
		this.response=response;
	}

	/**
	 * 
	 * @return
	 */
	public final  HtmlFragmentResponse getResponse() {
		return this.response;
	}

	/**
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 
	 * @param eventName
	 * @return
	 */
	public FragmentEvent getEvent(final String eventName) {
		if(eventName==null||eventName.trim().equals("")){
			return null;
		}
		if(this.regEvent==null){
			return null;
		}
		FragmentEvent event=this.regEvent.find(eventName);
		synchronized (this.regEvent) {
			if(event==null){
				try {
					final Method method=this.getClass().getMethod(eventName, new Class[]{HtmlFragmentRequest.class});
					Class<?>[] exceptions=method.getExceptionTypes();
					boolean hasException=false;
					for(Class<?> exception:exceptions){
						if(exception.isAssignableFrom(FragmentEventException.class)){
							hasException=true;
						}
					}
					if(method.getReturnType().isAssignableFrom(HtmlFragmentResponse.class)&&hasException){
						event=new FragmentEvent() {
							private String name=eventName;
							private Method event=method;
							@Override
							public String getName() {
								return this.name;
							}
							
							@Override
							public HtmlFragmentResponse execute(HtmlFragmentRequest req, Action action) throws FragmentEventException {
								try {
									return (HtmlFragmentResponse) this.event.invoke(action, req);
								} catch (Exception e) {
									throw new FragmentEventException(e);
								}
							}
						} ;
						this.regEvent.add(event);
					}else{
						return null;
					}
				} catch (Exception e) {
					return null;
				}
			}
		}
		return this.regEvent.find(eventName);
	}
	/**
	 * 
	 * @return
	 */
	public FragmentBeanRegister getBeans() {
		return regBean;
	}
}

/**
 * 
 */
package com.bluecloud.mvc.api;

import java.util.Map;

import com.bluecloud.mvc.exception.FragmentBeanBuilderException;

/**
 * @author Leo
 *
 */
public interface FragmentBeanBuilder {

	/**
	 * 
	 * @param bean
	 * @param formData
	 * @throws FragmentBeanBuilderException 
	 */
	void inject(Object bean, Map<String, String> formData) throws FragmentBeanBuilderException;

}

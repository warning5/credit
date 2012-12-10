/**
 * 
 */
package com.bluecloud.mvc.external.widgets.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.widgets.FormWidget;

/**
 * @author Hanlu
 * 
 */
public class DefaultFormWidget implements FormWidget {

	private String name;
	private FragmentBean bean;
	private final String datePattern = "yyyy-MM-dd HH:mm:ss";
	private final JsonConfig jsonConfig = configJson(datePattern);

	public static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(datePattern));

		return jsonConfig;
	}

	/**
	 * @param bean
	 * @param name
	 * 
	 */
	public DefaultFormWidget(String name, FragmentBean bean) {
		this.name = name;
		this.bean = bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.widgets.FragmentWidget#getData()
	 */
	@Override
	public Object getData() {
		String s = JSONObject.fromObject(bean, jsonConfig).toString();
		return s;
	}

	/**
	 * @return the bean
	 */
	public FragmentBean getBean() {
		return bean;
	}

	public static class DateJsonValueProcessor implements JsonValueProcessor {
		public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
		private DateFormat dateFormat;

		public DateJsonValueProcessor(String datePattern) {
			try {
				dateFormat = new SimpleDateFormat(datePattern);
			} catch (Exception ex) {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			}
		}

		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			if (value == null)
				return null;
			return process(value);
		}

		public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
			if (value == null)
				return null;
			return process(value);
		}

		private Object process(Object value) {
			return dateFormat.format((Date) value);
		}
	}
}

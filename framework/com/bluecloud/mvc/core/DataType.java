/**
 * 
 */
package com.bluecloud.mvc.core;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Hanlu
 * 
 */
public final class DataType {

	private static Log log = LogFactory.getLog(DataType.class);
	private final static Map<String, Class<?>> types = new HashMap<String, Class<?>>();
	private final static String timeEL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private final static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 */
	private DataType() {
	}

	static {
		types.put(String.class.getName(), String.class);
		types.put(Boolean.class.getName(), Boolean.class);
		types.put(Byte.class.getName(), Byte.class);
		types.put(Character.class.getName(), Character.class);
		types.put(Short.class.getName(), Short.class);
		types.put(Integer.class.getName(), Integer.class);
		types.put(Long.class.getName(), Long.class);
		types.put(Double.class.getName(), Double.class);
		types.put(Float.class.getName(), Float.class);
		types.put("boolean", boolean.class);
		types.put("byte", byte.class);
		types.put("char", char.class);
		types.put("short", short.class);
		types.put("int", int.class);
		types.put("long", long.class);
		types.put("double", double.class);
		types.put("float", float.class);
		types.put("BigDecimal", BigDecimal.class);
	}

	/**
	 * 判断是否是基本类型,支持的类型有：
	 * <ul>
	 * <li><strong> "java.lang.String"</strong>、
	 * <strong>"java.lang.Boolean"</strong>、 <strong>"java.lang.Byte"</strong>、
	 * <strong>"java.lang.Character"</strong>、
	 * <strong>"java.lang.Short"</strong>、 <strong>"java.lang.Integer"</strong>、
	 * <strong>"java.lang.Long"</strong>、 <strong>"java.lang.Double"</strong>、
	 * <strong
	 * >"java.lang.Float"</strong>、<strong>"java.math.BigDecimal"</strong>、
	 * <strong>"boolean"</strong>、 <strong>"byte"</strong>、
	 * <strong>"char"</strong>、 <strong>"short"</strong>、
	 * <strong>"int"</strong>、 <strong>"long"</strong>、
	 * <strong>"double"</strong>、 <strong>"float"</strong></li>
	 * </ul>
	 * 
	 * @param name
	 *            类型名称
	 * @return 如果是，返回true；否则返回false；
	 */
	public static boolean isBaseType(final String name) {
		if (types.get(name) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param paramClass
	 * @param value
	 * @return
	 */
	public static Object getDataType(final Class<?> paramClass, final String value) {
		if ((value == null) || ("null".equalsIgnoreCase(value)) || ("".equals(value))) {
			return null;
		}
		String name = paramClass.getName();
		if (name.equals(String.class.getName())) {
			return value;
		}
		if (name.equals(Boolean.class.getName())) {
			return new Boolean(value);
		}
		if (name.equals(Byte.class.getName())) {
			return new Byte(value);
		}
		if (name.equals(Character.class.getName())) {
			return new Character(value.toCharArray()[0]);
		}
		if (name.equals(Short.class.getName())) {
			return new Short(value);
		}
		if (name.equals(Integer.class.getName())) {
			return new Integer(value);
		}
		if (name.equals(Long.class.getName())) {
			return new Long(value);
		}
		if (name.equals(Double.class.getName())) {
			return new Double(value);
		}
		if (name.equals(Float.class.getName())) {
			return new Float(value);
		}
		if (name.equals(BigDecimal.class.getName())) {
			return new BigDecimal(value);
		}
		if (name.equals(Date.class.getName()) || name.equals(java.sql.Date.class.getName())) {

			Pattern p = Pattern.compile(timeEL);
			java.util.regex.Matcher m = p.matcher(value);
			try {
				if (m.matches()) {
					return dateFormat.parse(value);
				} else {
					return dateTimeFormat.parse(value);
				}
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (name.equals("int")) {
			return Integer.parseInt(value);
		}
		if (name.equals("double")) {
			return Double.parseDouble(value);
		}
		if (name.equals("float")) {
			return Float.parseFloat(value);
		}
		if (name.equals("short")) {
			return Short.parseShort(value);
		}
		if (name.equals("char")) {
			return value.toCharArray()[0];
		}
		if (name.equals("byte")) {
			return Byte.parseByte(value);
		}
		if (name.equals("boolean")) {
			return Boolean.parseBoolean(value);
		}
		if (name.equals("long")) {
			return Long.parseLong(value);
		}

		try {
			Object o = paramClass.newInstance();
			return o;
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}
}

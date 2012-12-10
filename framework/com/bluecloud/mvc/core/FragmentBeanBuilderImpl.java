/**
 * 
 */
package com.bluecloud.mvc.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FragmentBeanBuilder;
import com.bluecloud.mvc.exception.FragmentBeanBuilderException;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.bluecloud.util.ClassUtils;
import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * @author Hanlu
 * 
 */
public class FragmentBeanBuilderImpl implements FragmentBeanBuilder {
	private Log log = LogFactory.getLog(FragmentBeanBuilderImpl.class);
	/**
	 * 当前操作的类实现的所有接口
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<Class<?>> interfaceByClass = new CopyOnWriteArrayList();

	private Map<Class<?>, MethodAccess> reflectCache = new HashMap<Class<?>, MethodAccess>();

	@Override
	public void inject(Object o, Map<String, String> formData) throws FragmentBeanBuilderException {
		if (formData == null || formData.isEmpty()) {
			return;
		}
		if (o == null) {
			return;
		}
		Iterator<Entry<String, String>> itr = formData.entrySet().iterator();
		Map<String, Object> cache = new HashMap<String, Object>();
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			inject(o, entry.getKey(), entry.getValue(), cache);
		}
	}

	private void inject(Object object, String key, String value, Map<String, Object> cache)
			throws FragmentBeanBuilderException {
		int index = key.indexOf(".");
		try {
			if (index > 0) {
				String stringField = key.substring(0, key.indexOf("."));
				Object next = cache.get(stringField);
				if (next == null) {
					Field f = getField(object.getClass(), stringField);
					if (f != null) {
						next = f.getType().newInstance();
						cache.put(stringField, next);
					}
				}
				if (next == null)
					return;
				inject(next, key.substring(index + 1, key.length()), value, cache);

				MethodAccess access = reflectCache.get(object.getClass());
				if (access == null) {
					access = MethodAccess.get(object.getClass());
					reflectCache.put(object.getClass(), access);
				}
				String method = "set" + stringField.substring(0, 1).toUpperCase() + stringField.substring(1);
				access.invoke(object, method, next);
			} else {
				setValue(object, key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setValue(Object o, String key, String value) {

		MethodAccess access = reflectCache.get(o.getClass());
		if (access == null) {
			access = MethodAccess.get(o.getClass());
			reflectCache.put(o.getClass(), access);
		}
		try {
			Field field = getField(o.getClass(), key);
			String method = this.getSetterName(key);
			String[] methods=access.getMethodNames();
			if(this.getIndex(methods,method)==-1){
				method = this.getSetterName2(key);
			}
			if(this.getIndex(methods, method)==-1){
				method = this.getBooleanSetName(key);
			}
			if(this.getIndex(methods, method)!=-1){
				access.invoke(o, method, DataType.getDataType(field.getType(), value));
			}else{
				throw new RuntimeException(String.format("类：%s 的字段：%s 没有setter方法", o.getClass().getName(),
						field.getName()));
			}
		} catch (Exception e) {
			log.debug(e.getMessage(), e);
			return;
		}

	}
	/**
	 * 
	 * @param methods 
	 * @param method
	 * @return
	 */
	private int getIndex(String[] methods, String method) {
		if(method==null||method.trim().equals("")){
			return -1;
		}
		for(String m:methods){
			if(m.equals(method)){
				return 0;
			}
		}
		return -1;
	}

	private static Field getField(final Class<?> cls, String fieldName) {
		if (cls == null) {
			throw new IllegalArgumentException("The class must not be null");
		}
		if (fieldName == null) {
			throw new IllegalArgumentException("The field name must not be null");
		}
		for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
			try {
				Field field = acls.getDeclaredField(fieldName);
				return field;
			} catch (NoSuchFieldException ex) { // NOPMD
				// ignore
			}
		}
		Field match = null;
		for (Class<?> class1 : ClassUtils.getAllInterfaces(cls)) {
			try {
				Field test = ((Class<?>) class1).getField(fieldName);
				if (match != null) {
					throw new IllegalArgumentException("Reference to field " + fieldName + " is ambiguous relative to "
							+ cls + "; a matching field exists on two or more implemented interfaces.");
				}
				match = test;
			} catch (NoSuchFieldException ex) { // NOPMD
				// ignore
			}
		}
		return match;
	}

	@Deprecated
	public void inject1(Object o, Map<String, String> formData) throws FragmentBeanBuilderException {
		if (formData == null || formData.isEmpty()) {
			return;
		}
		if (o == null) {
			return;
		}
		this.interfaceByClass.clear();
		Class<?> paramClass = o.getClass();
		log.debug("构造bean:" + paramClass.getName());
		traverseClass(paramClass);
		Iterator<Entry<String, String>> itr = formData.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, String> entry = itr.next();
			Field localField = null;
			try {
				localField = paramClass.getDeclaredField(entry.getKey());
			} catch (Exception localException1) {
				Iterator<Class<?>> localIterator = this.interfaceByClass.iterator();
				while (localIterator.hasNext()) {
					Class<?> localClass = (Class<?>) localIterator.next();
					try {
						localField = localClass.getDeclaredField(entry.getKey());
					} catch (Exception localException2) {
					}
				}
			}
			try {
				if (localField != null) {
					this.setBeanValue(o, localField, entry.getValue());
				} else {
					String key = entry.getKey();
					int i = key.indexOf(".");
					if (i > -1) {
						this.setBeanValue(o, key, i, entry.getValue());
					}
				}
			} catch (Exception e) {
				throw new FragmentBeanBuilderException(e);
			}
		}
	}

	/**
	 * 
	 * @param bean
	 * @param key
	 * @param i
	 * @param value
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws FragmentBeanBuilderException
	 */
	@Deprecated
	private void setBeanValue(Object bean, String key, int i, String value) throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			FragmentBeanBuilderException {
		key = key.replace(".", "@");
		String[] fields = key.split("@");
		String filedName = fields[0];
		Object o = this.getBeanValue(bean, filedName);
		if (o == null) {
			Iterator<Class<?>> localIterator = this.interfaceByClass.iterator();
			while (localIterator.hasNext()) {
				Class<?> localClass = (Class<?>) localIterator.next();
				try {
					Field localField = localClass.getDeclaredField(filedName);
					this.setBeanValue(bean, localField, filedName);
				} catch (Exception localException2) {
				}
			}
			o = this.getBeanValue(bean, filedName);
		}
		if (o != null) {
			Map<String, String> formData = new HashMap<String, String>();
			key = key.replace("@", ".");
			formData.put(key.substring(i + 1), value);
			new FragmentBeanBuilderImpl().inject(o, formData);
		}
	}

	/**
	 * 遍历指定的类对象，去寻找其实现的所有接口信息
	 * 
	 * @param clz
	 */
	@Deprecated
	private void traverseClass(Class<?> clz) {
		if ((clz == null) || (clz == Object.class)) {
			return;
		}
		if (!this.interfaceByClass.contains(clz)) {
			this.interfaceByClass.add(clz);
		}
		if (clz.getAnnotation(NoMapSuperClass.class) == null)
			traverseClass(clz.getSuperclass());
		Class<?>[] clzArr = clz.getInterfaces();
		for (Class<?> localClass : clzArr) {
			traverseClass(localClass);
		}
	}

	/**
	 * 设置Bean的值
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	@Deprecated
	public void setBeanValue(Object localObject, Field localField, String value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method localMethod = this.getMethod(getSetterName(localField.getName()), localObject.getClass(),
				new Class[] { localField.getType() });
		if (localMethod == null) {
			if (localMethod == null) {
				localMethod = this.getMethod(getSetterName2(localField.getName()), localObject.getClass(),
						new Class[] { localField.getType() });
			}
			if (localMethod == null) {
				localMethod = this.getMethod(getBooleanSetName(localField.getName()), localObject.getClass(),
						new Class[] { localField.getType() });
			}
			if (localMethod == null) {
				throw new RuntimeException(String.format("类：%s 的字段：%s 没有setter方法", localObject.getClass().getName(),
						localField.getName()));
			}
		}
		Object localObject2 = DataType.getDataType(localField.getType(), value);
		localMethod.invoke(localObject, new Object[] { localObject2 });
	}

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	private String getBooleanSetName(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		if (paramString.startsWith("is")) {
			localStringBuffer.append("set");
			String str1 = paramString.substring(2, 3).toUpperCase();
			localStringBuffer.append(str1);
			String str2 = paramString.substring(3);
			localStringBuffer.append(str2);
		} else {
			localStringBuffer.append("set");
			String str1 = paramString.substring(0, 1).toUpperCase();
			localStringBuffer.append(str1);
			String str2 = paramString.substring(1);
			localStringBuffer.append(str2);
		}
		return localStringBuffer.toString();
	}

	/**
	 * 
	 * @param obj
	 * @param field
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object getBeanValue(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		String str1 = field.getName();
		return this.getBeanValue(obj, str1);
	}

	/**
	 * 
	 * @param obj
	 * @param name
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private Object getBeanValue(Object obj, String name) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		String str2 = getGetterName(name);
		Method localMethod = this.getMethod(str2, obj);

		if (localMethod == null) {
			localMethod = this.getMethod(getGetterName2(name), obj);
		}

		if (localMethod == null) {
			localMethod = this.getMethod(getBooleanName(name), obj);
		}
		if (localMethod == null) {
			throw new RuntimeException(String.format("类：%s 的字段：%s 没有getter方法", obj.getClass().getName(), name));
		}
		return localMethod.invoke(obj, new Object[0]);
	}

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	private String getBooleanName(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		if (paramString.startsWith("is")) {
			localStringBuffer.append("is");
			String str1 = paramString.substring(2, 3).toUpperCase();
			localStringBuffer.append(str1);
			String str2 = paramString.substring(3);
			localStringBuffer.append(str2);

		} else {
			localStringBuffer.append("is");
			String str1 = paramString.substring(0, 1).toUpperCase();
			localStringBuffer.append(str1);
			String str2 = paramString.substring(1);
			localStringBuffer.append(str2);
		}
		return localStringBuffer.toString();
	}

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	private String getSetterName(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("set");
		String str1 = paramString.substring(0, 1).toUpperCase();
		localStringBuffer.append(str1);
		String str2 = paramString.substring(1);
		localStringBuffer.append(str2);
		return localStringBuffer.toString();
	}

	/**
	 * 
	 * @param paramString
	 * @return
	 */
	private String getGetterName(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		localStringBuffer.append("get");
		String str1 = paramString.substring(0, 1).toUpperCase();
		localStringBuffer.append(str1);
		String str2 = paramString.substring(1);
		localStringBuffer.append(str2);
		return localStringBuffer.toString();
	}

	/**
	 * 针对以m_开始的field，如m_lang，则其setter为setLang()
	 * 
	 * @param paramString
	 * @return
	 */
	private String getSetterName2(String paramString) {
		if (paramString.startsWith("m_"))
			paramString = paramString.substring(2);
		else if (paramString.startsWith("_str"))
			paramString = paramString.substring(4);

		return getSetterName(paramString);
	}

	/**
	 * 针对以m_开始的field，如m_lang，则其getter为getLang()
	 * 
	 * @param paramString
	 * @return
	 */
	private String getGetterName2(String paramString) {
		if (paramString.startsWith("m_"))
			paramString = paramString.substring(2);
		else if (paramString.startsWith("_str"))
			paramString = paramString.substring(4);

		return getGetterName(paramString);
	}

	/**
	 * 
	 * @param paramString
	 * @param paramClass
	 * @param paramArrayOfClass
	 * @return
	 */
	private Method getMethod(String paramString, Class<?> paramClass, Class<?>[] paramArrayOfClass) {
		this.interfaceByClass.clear();
		traverseClass(paramClass);
		Method localMethod = null;
		try {
			localMethod = paramClass.getMethod(paramString, paramArrayOfClass);
			return localMethod;
		} catch (Exception localException1) {
			Iterator<Class<?>> localIterator = this.interfaceByClass.iterator();
			if (!localIterator.hasNext()) {
				return localMethod;
			}
			Class<?> localClass = (Class<?>) localIterator.next();
			try {
				localMethod = localClass.getMethod(paramString, paramArrayOfClass);
			} catch (Exception localException2) {
			}
		}
		return localMethod;
	}

	/**
	 * 获取指定对象中的指定方法名的方法反射对象
	 * 
	 * @param methodName
	 * @param entObj
	 * @return
	 */
	private Method getMethod(String methodName, Object entObj) {
		Class<? extends Object> clz = entObj.getClass();
		this.interfaceByClass.clear();
		traverseClass(clz);
		try {
			Method method = clz.getMethod(methodName, new Class[0]);
			if (method == null) {
				Iterator<Class<?>> iter = this.interfaceByClass.iterator();
				while (iter.hasNext()) {
					Class<?> clz2 = (Class<?>) iter.next();
					method = clz2.getMethod(methodName, new Class[0]);
				}
			}
			return method;
		} catch (SecurityException localSecurityException) {
			throw new RuntimeException(localSecurityException);
		} catch (NoSuchMethodException localNoSuchMethodException) {
			return null;
		}
	}

}

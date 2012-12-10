/**
 * 
 */
package com.bluecloud.mvc.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.exception.FragmentActionBuilderException;
import com.bluecloud.mvc.external.FragmentAction;

/**
 * @author Hanlu
 * 
 */
final class FragmentActionDepositoryImpl implements ActionDepository {

	private Log log=LogFactory.getLog(FragmentActionDepositoryImpl.class);
	private static final String CLASS_SUFFIX = ".class";
	private static final String JAR_SUFFIX = ".jar";
	private static final String ZIP_SUFFIX = ".zip";
	private Map<String, Class<?>> actions;

	public FragmentActionDepositoryImpl() {
		actions=new HashMap<String,Class<?>>();
	}
	
	@Override
	public FragmentAction getAction(String name) {
		Class<?> actionClass=actions.get(name);
		if(null==actionClass){
			return null;
		}
		FragmentAction action = null;
		try {
			action = (FragmentAction) actionClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return  action;
	}

	@Override
	public void load(ClassLoader classLoader) throws Exception {
		String[] paths = getClassPath();
		for (String path: paths) {
			if (path.toLowerCase().endsWith(JAR_SUFFIX) || path.toLowerCase().endsWith(ZIP_SUFFIX)) {
				try{
					this.loadJar(path, classLoader);
				}catch (MalformedURLException e) {
					log.error(e.getMessage(), e);
				}catch (IOException e) {
					log.error(e.getMessage(), e);
				}catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}else{
				if (!path.endsWith("\\") && !path.endsWith("/")){
					path = path + "/";
				}
				this.loadClassPath(path,classLoader);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private String[] getClassPath() {
		URLClassLoader clzLoader = (URLClassLoader) getClass().getClassLoader(); 
		List<String> listPath = new ArrayList<String> ();
		for (URL url: clzLoader.getURLs()) {
			if (url.getPath().startsWith("file:")) {
				listPath.add(url.getPath().substring(7));
			} else {
				listPath.add(url.getPath());
			}
		}
		return listPath.toArray(new String[0]);
	}
	
	/**
	 * 
	 * @param fileName
	 * @param classLoader
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	private void loadJar(String fileName, ClassLoader classLoader) throws MalformedURLException, IOException, Exception {
		URL localURL = new URL("jar:file:" + fileName.replace('\\', '/') + "!/");
		URLConnection conn = localURL.openConnection();
		JarFile jarFile = null;
		if (conn instanceof JarURLConnection) {
			JarURLConnection jarConn = (JarURLConnection) conn;
			jarConn.setUseCaches(false);
			jarFile = jarConn.getJarFile();
			JarEntry je=jarFile.getJarEntry("META-INF/fxml.conf");
			if(je!=null){
				Enumeration<JarEntry> iter = jarFile.entries();
				while (iter.hasMoreElements()) {
					JarEntry entry = (JarEntry) iter.nextElement();
					String name = entry.getName();
					if(name.indexOf("$") == -1&&name.endsWith(CLASS_SUFFIX)){
						int i=name.lastIndexOf(46);
						String className = name.substring(0,i).replace('/', '.');//46='.'
						register(className, classLoader);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param classPath
	 * @param classLoader
	 */
	private void loadClassPath(String classPath, ClassLoader classLoader) {
		try {
			classPath = URLDecoder.decode(classPath, "UTF-8");
			//classpath对应的目录和该目录下的列表
			File file = new File(classPath);
			File[] fileList = file.listFiles();
			if (fileList == null && classPath.startsWith("/")) {
				classPath = classPath.substring(1);
				file = new File(classPath);
				fileList = file.listFiles();
			}
			
			//dir 和 class 文件
			if (fileList != null) {
				for(File f:fileList) {
					if (f.isDirectory()) {
						this.loadClassDirectory(f, classPath, classLoader);
					}else if (f.getCanonicalPath().toLowerCase().endsWith(CLASS_SUFFIX)) {
						this.loadClass(f, classPath, classLoader);
					}
				}
			}
			
			//lib库中存放的.jar或.zip结尾,jar包中的类不能含有$符号
			String tempClassPath = classPath;
			if (classPath.endsWith("/classes/")) {
				tempClassPath = classPath.substring(0, classPath.lastIndexOf("classes/"));
			}
			File libPath = new File(tempClassPath + "lib");
			File[] arr = libPath.listFiles();
			if (arr != null) {
				for (File f:arr) {
					if (f.getName().toLowerCase().endsWith(JAR_SUFFIX) || f.getName().toLowerCase().endsWith(ZIP_SUFFIX)) {
						this.loadJar(f.getCanonicalPath(), classLoader);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param dir
	 * @param classPath
	 * @param classLoader
	 * @throws Exception
	 */
	private void loadClassDirectory(File dir, String classPath, ClassLoader classLoader)
			throws Exception {
		File[] arr = dir.listFiles();
		if (arr != null) {
			for (File f : arr) {
				if (f.isDirectory()) {
					this.loadClassDirectory(f, classPath, classLoader);
				}else if (f.getCanonicalPath().toLowerCase().endsWith(CLASS_SUFFIX)) {
					this.loadClass(f, classPath, classLoader);
				}else if (f.getName().toLowerCase().endsWith(JAR_SUFFIX) || f.getName().toLowerCase().endsWith(ZIP_SUFFIX)) {
					this.loadJar(f.getCanonicalPath(), classLoader);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param f
	 * @param classPath
	 * @param classLoader
	 * @throws Exception 
	 */
	private void loadClass(File f, String classPath, ClassLoader classLoader) throws Exception{
		String path = f.getCanonicalPath().replace('\\', '/');
		if((path.charAt(0) != classPath.charAt(0)) && (classPath.startsWith("/"))) {
			classPath = classPath.substring(1).replace("./", "");
		}
		String fileName = path.substring(classPath.length(), path.lastIndexOf('.')).replace('/', '.');
		this.register(fileName, classLoader);
	}
	/**
	 * 
	 * @param ctrlClassName
	 * @param classLoader
	 * @throws Exception
	 */
	private void register(String className, ClassLoader classLoader) throws Exception {
		Class<?> localClass = classLoader.loadClass(className);
		boolean isAbstract=Modifier.isAbstract(localClass.getModifiers());
		boolean isAssignableFrom=FragmentAction.class.isAssignableFrom(localClass);
		if (isAssignableFrom&&!isAbstract) {
			Object framentObj=localClass.newInstance();
			Method[] methods=localClass.getMethods();
			Object key = null;
			for(Method method:methods){
				if(method.getName().equals("getName")){
					key=method.invoke(framentObj, (Object[]) null);
				}
			}
			if(key!=null&&!key.toString().trim().equals("")){
				if(actions.get(key)==null){
					actions.put(key.toString(), localClass);
				}else{
					actions.remove(key);
					throw new FragmentActionBuilderException("注册失败：Action 的名称："+key+" 已经存在");
				}
			}else{
				throw new FragmentActionBuilderException("注册失败：Action 的名称为null，在类："+localClass.getName());
//				key=localClass.getSimpleName();
//				actions.put(key.toString(), localClass);
			}
			if(log.isDebugEnabled()){
				log.debug("加载Action："+key);
			}
		}
	}
}

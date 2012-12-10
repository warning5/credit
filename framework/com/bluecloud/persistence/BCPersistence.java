/**
 * 
 */
package com.bluecloud.persistence;

import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author Hanlu 2012-9-14
 */
@Deprecated
public final class BCPersistence {
	private static BCPersistence persistence;
	private static Object mutex = new Object();
	private SqlSessionFactory factory;

	private BCPersistence(SqlSessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * 
	 * @return
	 */
	public static BCPersistence getInstance() {
		return persistence;
	}

	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return factory.openSession().getConnection();
	}

	/**
	 * 
	 * @return
	 */
	public SqlSession getSession() {
		return factory.openSession();
	}

	/**
	 * 初始化持久化框架
	 * 
	 * @param reader
	 *            配置文件
	 */
	public static void init(Reader reader) {
		if (persistence != null) {
			return;
		}
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		SqlSessionFactory factory = builder.build(reader);
		synchronized (mutex) {
			if (persistence == null) {
				persistence = new BCPersistence(factory);
			}
		}
	}

	/**
	 * 注销持久化框架
	 */
	public static void destroy() {
		persistence = null;
	}

	/**
	 * 
	 * @param class
	 * @return
	 */
	public <T> T getMapper(Class<T> clazz) {
		return this.getSession().getMapper(clazz);
	}

}

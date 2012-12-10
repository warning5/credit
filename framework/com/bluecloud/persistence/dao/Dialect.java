/**
 * 
 */
package com.bluecloud.persistence.dao;

/**
 * @author Hanlu
 * 
 */
public abstract class Dialect {

	public static enum Type {
		MYSQL, ORACLE
	}

	/**
	 * 
	 * @param sql
	 * @param offset
	 * @param limit
	 * @return
	 */
	public abstract String getLimitString(String sql, int offset, int limit);
}

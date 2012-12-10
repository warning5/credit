/**
 * 
 */
package com.bluecloud.persistence.dao;

/**
 * @author Hanlu
 * 
 *         2012-10-11
 */
public class MySQLDialect extends Dialect {

	protected static final String SQL_END_DELIMITER = ";";

	/**
	 * 
	 */
	public MySQLDialect() {
	}

	/**
	 * 
	 * @param sql
	 * @param hasOffset
	 * @return
	 */
	public String getLimitString(String sql, boolean hasOffset) {
		return MySql5PageHepler.getLimitString(sql, -1, -1);
	}

	/**
	 * 
	 * @return
	 */
	public boolean supportsLimit() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.persistence.dao.Dialect#getLimitString(java.lang.String,
	 * int, int)
	 */
	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return MySql5PageHepler.getLimitString(sql, offset, limit);
	}

}

/**
 * 
 */
package com.bluecloud.persistence.util;


/**
 * @author Hanlu
 * 
 */
public final class PrimaryKey {

	/**
	 * 获得32位的主键值<p>
	 * 格式：XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
	 * @return 主键值
	 */
	public static String getID() {
		return new UUIDGenerator().generate().toString();
	}
}

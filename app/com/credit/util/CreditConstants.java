/**
 * 
 */
package com.credit.util;

/**
 * @author Hanlu
 * 
 */
public final class CreditConstants {

	public static final String ONLINEUSER = "onlineUser";// 在线用户
	public static final String GUEST = "guest";// 访客

	public enum Organization {
		Agc("2"), Firm("4"), Financial("3"), Supervisor("1");

		private String type;

		private Organization(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

}

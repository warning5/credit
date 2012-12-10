/**
 * 
 */
package com.bluecloud.persistence.pojo;

import com.bluecloud.persistence.util.PrimaryKey;

/**
 * @author Hanlu
 * 
 */
public abstract class BaseBO {

	protected String id;
	protected String sord = "desc";
	protected String sidx = getPKName();

	/**
	 * 
	 */
	public BaseBO() {
		this.id = PrimaryKey.getID();
	}

	protected abstract String getPKName();

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @param bos
	 * @return
	 */
	// protected final String mergeId(List<? extends BaseBO> bos) {
	// StringBuilder ids = new StringBuilder();
	// if (bos != null) {
	// for (BaseBO role : bos) {
	// ids.append(role.getId());
	// ids.append(",");
	// }
	// }
	// if (ids.lastIndexOf(",") != -1) {
	// ids.delete(ids.length() - 1, ids.length());
	// }
	// return ids.toString();
	// }

	public String getSord() {
		return this.sord;
	}

	public String getSidx() {
		return this.sidx;
	}

	/**
	 * @param sord
	 *            the sord to set
	 */
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * @param sidx
	 *            the sidx to set
	 */
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

}

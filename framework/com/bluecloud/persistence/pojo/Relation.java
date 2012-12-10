/**
 * 
 */
package com.bluecloud.persistence.pojo;

/**
 * @author Hanlu
 *
 */
public class Relation extends BaseBO {

	private String relationPK;
	private String relationFK;
	/**
	 * 
	 */
	public Relation() {
		super();
	}

	
	public Relation(String relationPK, String relationFK) {
		this();
		this.relationPK=relationPK;
		this.relationFK=relationFK;
	}


	/**
	 * @return the relationPK
	 */
	public String getRelationPK() {
		return relationPK;
	}


	/**
	 * @param relationPK the relationPK to set
	 */
	public void setRelationPK(String relationPK) {
		this.relationPK = relationPK;
	}


	/**
	 * @return the relationFK
	 */
	public String getRelationFK() {
		return relationFK;
	}


	/**
	 * @param relationFK the relationFK to set
	 */
	public void setRelationFK(String relationFK) {
		this.relationFK = relationFK;
	}


	/* (non-Javadoc)
	 * @see com.bluecloud.persistence.pojo.BaseBO#getPKName()
	 */
	@Override
	protected String getPKName() {
		return null;
	}

}

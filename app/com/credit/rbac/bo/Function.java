package com.credit.rbac.bo;

import com.bluecloud.persistence.pojo.BaseBO;



/**
 * 
 * @author Hanlu
 * 
 */
public class Function extends BaseBO {

	private String name;

	private String description;

	private String uri;

	private String parent;

	/**
	 * 功能权限 1=可写 0=只读
	 */
	private Integer comp=0;

	private Operation operation;

	private boolean isOpera;

	private Integer functionOrder=1;

	public Function() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		if(uri==null||uri.trim().equals("")){
			uri="none";
		}
		return uri;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getComp() {
		if (this.comp <0||this.comp>1) {
			comp = 0;
		}
		return this.comp;
	}

	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * @return the isOpera
	 */
	public boolean isOpera() {
		return isOpera;
	}

	/**
	 * @param isOpera the isOpera to set
	 */
	public void setOpera(boolean isOpera) {
		this.isOpera = isOpera;
	}

	/**
	 * @param comp the comp to set
	 */
	public void setComp(Integer comp) {
		this.comp = comp;
	}

	@Override
	protected String getPKName() {
		return "functionid";
	}

	/**
	 * 
	 * @return
	 */
	public Integer getFunctionOrder() {
		return this.functionOrder;
	}

	/**
	 * @param functionOrder the functionOrder to set
	 */
	public void setFunctionOrder(Integer functionOrder) {
		this.functionOrder = functionOrder;
	}
}

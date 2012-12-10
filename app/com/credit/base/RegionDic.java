package com.credit.base;

public class RegionDic extends Dictionary {

	private String father;

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father == null ? null : father.trim();
	}
}
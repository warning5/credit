package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class SelectOperationBean extends FragmentBean {
	
	
	private String code;
	private String name;

	public SelectOperationBean(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

}
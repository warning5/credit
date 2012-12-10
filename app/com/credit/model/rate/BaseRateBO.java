package com.credit.model.rate;

import com.bluecloud.persistence.pojo.BaseBO;

public class BaseRateBO extends BaseBO {

	@Override
	protected String getPKName() {
		return null;
	}

	private String tabId;

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

}

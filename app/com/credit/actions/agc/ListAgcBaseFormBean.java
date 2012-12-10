package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListAgcBaseFormBean extends FragmentBean {

	private int squence;
	private String agcId;
	private String agcCnName;
	private String agcRagisDate;
	private String agcNature;
	private String agcRegion;

	public ListAgcBaseFormBean(int squence) {
		this.squence = squence;
	}

	public String getAgcCnName() {
		return agcCnName;
	}

	public void setAgcCnName(String agcCnName) {
		this.agcCnName = agcCnName;
	}

	public String getAgcRagisDate() {
		return agcRagisDate;
	}

	public void setAgcRagisDate(String agcRagisDate) {
		this.agcRagisDate = agcRagisDate;
	}

	public String getAgcNature() {
		return agcNature;
	}

	public void setAgcNature(String agcNature) {
		this.agcNature = agcNature;
	}

	public String getAgcRegion() {
		return agcRegion;
	}

	public void setAgcRegion(String agcRegion) {
		this.agcRegion = agcRegion;
	}

	public int getSquence() {
		return squence;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public void setSquence(int squence) {
		this.squence = squence;
	}
}
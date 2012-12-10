package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListAgcProfessionalFormBean extends FragmentBean {

	private int squence;
	private String agcCnName;
	private String agcProName;
	private String agcProIDNum;
	private String agcProCertiType;
	private String agcProCertiStatus;
	private String agcId;
	private String agcProId;

	public ListAgcProfessionalFormBean(int squence) {
		this.squence = squence;
	}

	public int getSquence() {
		return squence;
	}

	public String getAgcCnName() {
		return agcCnName;
	}

	public void setAgcCnName(String agcCnName) {
		this.agcCnName = agcCnName;
	}

	public String getAgcProName() {
		return agcProName;
	}

	public void setAgcProName(String agcProName) {
		this.agcProName = agcProName;
	}

	public String getAgcProIDNum() {
		return agcProIDNum;
	}

	public void setAgcProIDNum(String agcProIDNum) {
		this.agcProIDNum = agcProIDNum;
	}

	public String getAgcProCertiType() {
		return agcProCertiType;
	}

	public void setAgcProCertiType(String agcProCertiType) {
		this.agcProCertiType = agcProCertiType;
	}

	public String getAgcProCertiStatus() {
		return agcProCertiStatus;
	}

	public void setAgcProCertiStatus(String agcProCertiStatus) {
		this.agcProCertiStatus = agcProCertiStatus;
	}

	public void setSquence(int squence) {
		this.squence = squence;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public String getAgcProId() {
		return agcProId;
	}

	public void setAgcProId(String agcProId) {
		this.agcProId = agcProId;
	}
}
package com.credit.actions.firm;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListFirmFormBean extends FragmentBean {

	public ListFirmFormBean(int squence) {
		this.squence = squence;
	}

	private int squence;

	private String firmId;

	private String firmCnName;

	private String firmNature;

	private String firmRegisAddr;

	private String firmRegion;

	private String firmRagisDate;

	public int getSquence() {
		return squence;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}

	public String getFirmNature() {
		return firmNature;
	}

	public void setFirmNature(String firmNature) {
		this.firmNature = firmNature;
	}

	public String getFirmRegisAddr() {
		return firmRegisAddr;
	}

	public void setFirmRegisAddr(String firmRegisAddr) {
		this.firmRegisAddr = firmRegisAddr;
	}

	public String getFirmRegion() {
		return firmRegion;
	}

	public void setFirmRegion(String firmRegion) {
		this.firmRegion = firmRegion;
	}

	public String getFirmRagisDate() {
		return firmRagisDate;
	}

	public void setFirmRagisDate(String firmRagisDate) {
		this.firmRagisDate = firmRagisDate;
	}
}
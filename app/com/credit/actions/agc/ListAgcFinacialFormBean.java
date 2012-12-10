package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListAgcFinacialFormBean extends FragmentBean {

	private int squence;
	private String agcFinaceId;
	private String agcCnName;
	private String agcOrgId;
	private Integer agcRptYear;
	private String agcRatIncm;
	private String agcId;

	public ListAgcFinacialFormBean(int squence) {
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

	public String getAgcOrgId() {
		return agcOrgId;
	}

	public void setAgcOrgId(String agcOrgId) {
		this.agcOrgId = agcOrgId;
	}

	public void setSquence(int squence) {
		this.squence = squence;
	}

	public Integer getAgcRptYear() {
		return agcRptYear;
	}

	public void setAgcRptYear(Integer agcRptYear) {
		this.agcRptYear = agcRptYear;
	}

	public String getAgcRatIncm() {
		return agcRatIncm;
	}

	public void setAgcRatIncm(String agcRatIncm) {
		this.agcRatIncm = agcRatIncm;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public String getAgcFinaceId() {
		return agcFinaceId;
	}

	public void setAgcFinaceId(String agcFinaceId) {
		this.agcFinaceId = agcFinaceId;
	}

}
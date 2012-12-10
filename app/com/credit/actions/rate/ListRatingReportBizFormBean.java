package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListRatingReportBizFormBean extends FragmentBean {

	private int squence;
	private String reportId;
	private String bizReportId;
	private String rateId;
	private String firmCnName;
	private String xyLevel;
	private String analystNum;
	private String xyStartDate;
	private String xyEndDate;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public int getSquence() {
		return squence;
	}

	public String getXyLevel() {
		return xyLevel;
	}

	public void setXyLevel(String xyLevel) {
		this.xyLevel = xyLevel;
	}

	public String getAnalystNum() {
		return analystNum;
	}

	public void setAnalystNum(String analystNum) {
		this.analystNum = analystNum;
	}

	public String getXyStartDate() {
		return xyStartDate;
	}

	public void setXyStartDate(String xyStartDate) {
		this.xyStartDate = xyStartDate;
	}

	public String getXyEndDate() {
		return xyEndDate;
	}

	public void setXyEndDate(String xyEndDate) {
		this.xyEndDate = xyEndDate;
	}

	public ListRatingReportBizFormBean(int squence) {
		this.squence = squence;
	}

	public void setSquence(int squence) {
		this.squence = squence;
	}

	public String getBizReportId() {
		return bizReportId;
	}

	public void setBizReportId(String bizReportId) {
		this.bizReportId = bizReportId;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}
}
package com.credit.model.rate;

import java.util.Date;
import java.util.List;

public class ListRatingReportBiz {

	private List<Integer> states;
	private int state;
	private Date xyStartDate;
	private Date xyEndDate;
	private String firmCnName;
	private String agcId;
	private String rateId;
	private String xyLevel;
	private String analystNum;
	private String reportId;
	private String reportGlobalId;
	private String valid;
	private Boolean hasComment = null;

	public Date getXyStartDate() {
		return xyStartDate;
	}

	public void setXyStartDate(Date xyStartDate) {
		this.xyStartDate = xyStartDate;
	}

	public Date getXyEndDate() {
		return xyEndDate;
	}

	public void setXyEndDate(Date xyEndDate) {
		this.xyEndDate = xyEndDate;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
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

	public String getReportGlobalId() {
		return reportGlobalId;
	}

	public void setReportGlobalId(String reportGlobalId) {
		this.reportGlobalId = reportGlobalId;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Boolean isHasComment() {
		return hasComment;
	}

	public void setHasComment(boolean hasComment) {
		this.hasComment = hasComment;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}
}
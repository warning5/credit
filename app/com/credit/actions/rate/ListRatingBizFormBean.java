package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;

public class ListRatingBizFormBean extends FragmentBean {

	private int squence;

	private String rateId;

	private String agcId;

	private String firmOrgId;

	private String firmId;

	private String firmCnName;

	private String rateRegion;

	private String rateHy;

	private String rateDate;

	private Double rateMoney;

	private String rateGz;

	private String submitRatingBizTime;

	private String handledRatingBizTime;

	private int state;

	private String reportId;

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

	public String getSubmitRatingBizTime() {
		return submitRatingBizTime;
	}

	public String getHandledRatingBizTime() {
		return handledRatingBizTime;
	}

	public void setHandledRatingBizTime(String handledRatingBizTime) {
		this.handledRatingBizTime = handledRatingBizTime;
	}

	public void setSubmitRatingBizTime(String submitRatingBizTime) {
		this.submitRatingBizTime = submitRatingBizTime;
	}

	public String getRateGz() {
		return rateGz;
	}

	public void setRateGz(String rateGz) {
		this.rateGz = rateGz;
	}

	public ListRatingBizFormBean(int squence) {
		this.squence = squence;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getRateRegion() {
		return rateRegion;
	}

	public void setRateRegion(String rateRegion) {
		this.rateRegion = rateRegion;
	}

	public String getRateHy() {
		return rateHy;
	}

	public void setRateHy(String rateHy) {
		this.rateHy = rateHy;
	}

	public int getSquence() {
		return squence;
	}

	public void setSquence(int squence) {
		this.squence = squence;
	}

	public String getRateDate() {
		return rateDate;
	}

	public void setRateDate(String rateDate) {
		this.rateDate = rateDate;
	}

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}

	public String getFirmOrgId() {
		return firmOrgId;
	}

	public void setFirmOrgId(String firmOrgId) {
		this.firmOrgId = firmOrgId;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}
}
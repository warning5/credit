package com.credit.model.rate;

import java.util.Date;
import java.util.List;

public class ListRatingBiz {

	private String rateId;

	private String agcId;

	private String firmOrgId;

	private String reportId;

	private String firmId;

	private String firmCnName;

	private String rateRegionProvince;

	private String rateRegionCity;

	private String rateRegionArea;

	private String rateHy;

	private Date rateDate;

	private Double rateMoney;

	private Integer rateGzId;

	private Date submitRatingBizTime;

	private Date handledRatingBizTime;

	private int state;

	private List<Integer> states;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public Date getSubmitRatingBizTime() {
		return submitRatingBizTime;
	}

	public void setSubmitRatingBizTime(Date submitRatingBizTime) {
		this.submitRatingBizTime = submitRatingBizTime;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getRateRegionProvince() {
		return rateRegionProvince;
	}

	public void setRateRegionProvince(String rateRegionProvince) {
		this.rateRegionProvince = rateRegionProvince;
	}

	public String getRateRegionCity() {
		return rateRegionCity;
	}

	public void setRateRegionCity(String rateRegionCity) {
		this.rateRegionCity = rateRegionCity;
	}

	public String getRateRegionArea() {
		return rateRegionArea;
	}

	public void setRateRegionArea(String rateRegionArea) {
		this.rateRegionArea = rateRegionArea;
	}

	public String getRateHy() {
		return rateHy;
	}

	public void setRateHy(String rateHy) {
		this.rateHy = rateHy;
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}

	public Integer getRateGzId() {
		return rateGzId;
	}

	public void setRateGzId(Integer rateGzId) {
		this.rateGzId = rateGzId;
	}

	public Date getHandledRatingBizTime() {
		return handledRatingBizTime;
	}

	public void setHandledRatingBizTime(Date handledRatingBizTime) {
		this.handledRatingBizTime = handledRatingBizTime;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}

	public String getFirmOrgId() {
		return firmOrgId;
	}

	public void setFirmOrgId(String firmOrgId) {
		this.firmOrgId = firmOrgId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
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

}
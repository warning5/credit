package com.credit.model.rate;

import java.util.Date;

public class RatingBiz extends BaseRateBO {

	private String rateId;

	private String agcId;

	private String reportId;

	private String firmId;

	private String firmCnName;

	private String rateBizType;

	private String rateRegionProvince;

	private String rateRegionCity;

	private String rateRegionArea;

	private String rateHy;

	private Date rateDate;

	private Double rateMoney;

	private Date rateChargeDate;

	private String rateScanFile;
	private String rateScanReceipt;
	private String rateGzApScanFile;

	private String rateScanFileOld;
	private String rateScanReceiptOld;
	private String rateGzApScanFileOld;

	private Integer rateGzId;

	private String rateThird;

	private Integer state;

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId == null ? null : rateId.trim();
	}

	public String getRateBizType() {
		return rateBizType;
	}

	public void setRateBizType(String rateBizType) {
		this.rateBizType = rateBizType == null ? null : rateBizType.trim();
	}

	public String getRateRegionProvince() {
		return rateRegionProvince;
	}

	public void setRateRegionProvince(String rateRegionProvince) {
		this.rateRegionProvince = rateRegionProvince == null ? null : rateRegionProvince.trim();
	}

	public String getRateRegionCity() {
		return rateRegionCity;
	}

	public void setRateRegionCity(String rateRegionCity) {
		this.rateRegionCity = rateRegionCity == null ? null : rateRegionCity.trim();
	}

	public String getRateRegionArea() {
		return rateRegionArea;
	}

	public void setRateRegionArea(String rateRegionArea) {
		this.rateRegionArea = rateRegionArea == null ? null : rateRegionArea.trim();
	}

	public String getRateHy() {
		return rateHy;
	}

	public void setRateHy(String rateHy) {
		this.rateHy = rateHy == null ? null : rateHy.trim();
	}

	public Date getRateDate() {
		return rateDate;
	}

	public void setRateDate(Date rateDate) {
		this.rateDate = rateDate;
	}

	public String getRateScanFile() {
		return rateScanFile;
	}

	public void setRateScanFile(String rateScanFile) {
		this.rateScanFile = rateScanFile == null ? null : rateScanFile.trim();
	}

	public Double getRateMoney() {
		return rateMoney;
	}

	public void setRateMoney(Double rateMoney) {
		this.rateMoney = rateMoney;
	}

	public Date getRateChargeDate() {
		return rateChargeDate;
	}

	public void setRateChargeDate(Date rateChargeDate) {
		this.rateChargeDate = rateChargeDate;
	}

	public String getRateScanReceipt() {
		return rateScanReceipt;
	}

	public void setRateScanReceipt(String rateScanReceipt) {
		this.rateScanReceipt = rateScanReceipt == null ? null : rateScanReceipt.trim();
	}

	public String getRateGzApScanFile() {
		return rateGzApScanFile;
	}

	public void setRateGzApScanFile(String rateGzApScanFile) {
		this.rateGzApScanFile = rateGzApScanFile == null ? null : rateGzApScanFile.trim();
	}

	public Integer getRateGzId() {
		return rateGzId;
	}

	public void setRateGzId(Integer rateGzId) {
		this.rateGzId = rateGzId;
	}

	public String getRateThird() {
		return rateThird;
	}

	public void setRateThird(String rateThird) {
		this.rateThird = rateThird == null ? null : rateThird.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getFirmCnName() {
		return firmCnName;
	}

	public void setFirmCnName(String firmCnName) {
		this.firmCnName = firmCnName;
	}

	public String getAgcId() {
		return agcId;
	}

	public void setAgcId(String agcId) {
		this.agcId = agcId;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getRateScanFileOld() {
		return rateScanFileOld;
	}

	public void setRateScanFileOld(String rateScanFileOld) {
		this.rateScanFileOld = rateScanFileOld;
	}

	public String getRateScanReceiptOld() {
		return rateScanReceiptOld;
	}

	public void setRateScanReceiptOld(String rateScanReceiptOld) {
		this.rateScanReceiptOld = rateScanReceiptOld;
	}

	public String getRateGzApScanFileOld() {
		return rateGzApScanFileOld;
	}

	public void setRateGzApScanFileOld(String rateGzApScanFileOld) {
		this.rateGzApScanFileOld = rateGzApScanFileOld;
	}
}
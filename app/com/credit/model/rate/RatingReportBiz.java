package com.credit.model.rate;

import java.util.Date;

public class RatingReportBiz extends BaseRateBO {

	private String reportGlobalId;

	private String reportId;
	private String reportType;
	private String xyLevel;
	private String xySuggestion;
	private String xyProspect;
	private Date xyOutDate;
	private Date xyStartDate;
	private Date xyEndDate;
	private Date xyRevokeDate;

	private Date xyChangeDate;

	private String reportDigestSummary;

	private String reportDigestRisk;

	private String reportScanFile;
	private String reportScanFileOld;
	private String analystNum;

	private Double entAssetsTotal;

	private String rateId;

	private String agcId;

	private String firmId;

	private Double entLoanBalance;

	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getReportGlobalId() {
		return reportGlobalId;
	}

	public void setReportGlobalId(String reportGlobalId) {
		this.reportGlobalId = reportGlobalId;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getXyLevel() {
		return xyLevel;
	}

	public void setXyLevel(String xyLevel) {
		this.xyLevel = xyLevel;
	}

	public String getXySuggestion() {
		return xySuggestion;
	}

	public void setXySuggestion(String xySuggestion) {
		this.xySuggestion = xySuggestion;
	}

	public String getXyProspect() {
		return xyProspect;
	}

	public void setXyProspect(String xyProspect) {
		this.xyProspect = xyProspect;
	}

	public Date getXyOutDate() {
		return xyOutDate;
	}

	public void setXyOutDate(Date xyOutDate) {
		this.xyOutDate = xyOutDate;
	}

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

	public Date getXyRevokeDate() {
		return xyRevokeDate;
	}

	public void setXyRevokeDate(Date xyRevokeDate) {
		this.xyRevokeDate = xyRevokeDate;
	}

	public Date getXyChangeDate() {
		return xyChangeDate;
	}

	public void setXyChangeDate(Date xyChangeDate) {
		this.xyChangeDate = xyChangeDate;
	}

	public String getReportDigestSummary() {
		return reportDigestSummary;
	}

	public void setReportDigestSummary(String reportDigestSummary) {
		this.reportDigestSummary = reportDigestSummary;
	}

	public String getReportDigestRisk() {
		return reportDigestRisk;
	}

	public void setReportDigestRisk(String reportDigestRisk) {
		this.reportDigestRisk = reportDigestRisk;
	}

	public String getReportScanFile() {
		return reportScanFile;
	}

	public void setReportScanFile(String reportScanFile) {
		this.reportScanFile = reportScanFile;
	}

	public String getAnalystNum() {
		return analystNum;
	}

	public void setAnalystNum(String analystNum) {
		this.analystNum = analystNum;
	}

	public Double getEntAssetsTotal() {
		return entAssetsTotal;
	}

	public void setEntAssetsTotal(Double entAssetsTotal) {
		this.entAssetsTotal = entAssetsTotal;
	}

	public String getRateId() {
		return rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public Double getEntLoanBalance() {
		return entLoanBalance;
	}

	public void setEntLoanBalance(Double entLoanBalance) {
		this.entLoanBalance = entLoanBalance;
	}

	public String getReportScanFileOld() {
		return reportScanFileOld;
	}

	public void setReportScanFileOld(String reportScanFileOld) {
		this.reportScanFileOld = reportScanFileOld;
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

}
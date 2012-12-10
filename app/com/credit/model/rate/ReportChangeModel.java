package com.credit.model.rate;


public class ReportChangeModel {

	@Override
	public String toString() {
		return "ReportChangeModel [rateId=" + rateId + ", reportId=" + reportId + "]";
	}

	String rateId;
	String reportId;

	public ReportChangeModel(String rateId, String reportId) {
		this.rateId = rateId;
		this.reportId = reportId;
	}

	public String getRateId() {
		return rateId;
	}

	public String getReportId() {
		return reportId;
	}

}
package com.credit.model.rate;

import java.util.Date;

public class StateChangeModel {

	@Override
	public String toString() {
		return "StateChangeModel [rateId=" + rateId + ", state=" + state + ", changeDate=" + changeDate + "]";
	}

	String rateId;
	int state;
	Date changeDate;

	public StateChangeModel(String rateId, int state, Date changeDate) {
		this.rateId = rateId;
		this.state = state;
		this.changeDate = changeDate;
	}

	public String getRateId() {
		return rateId;
	}

	public int getState() {
		return state;
	}

	public Date getChangeDate() {
		return changeDate;
	}
}

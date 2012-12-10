package com.credit.actions.rate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public abstract class AfterBizAction extends AbstractBizAction {

	public static final String RATINGREPORTBIZ_FORMBEAN = "ratingReportBiz";
	public static final String PAGE_ITEM = "ratingReportBiz";
	public static final String PAGE_ID = "reportId";
	private List<SelectOperationBean> ratingLevelBeans;;

	protected String[] getReportIdAndRateId(String id) {
		return id.split("_");
	}

	protected List<SelectOperationBean> getRateLevelSelect() {
		if (ratingLevelBeans == null) {
			ratingLevelBeans = new ArrayList<SelectOperationBean>(dicService.getRateLevelMap().size());
			for (Entry<String, String> entry : dicService.getRateLevelMap().entrySet()) {
				SelectOperationBean o = new SelectOperationBean(entry.getKey(), entry.getValue());
				ratingLevelBeans.add(o);
			}
		}
		return ratingLevelBeans;
	}

}

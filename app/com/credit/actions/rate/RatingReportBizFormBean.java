package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.RatingReportBiz;

@NoMapSuperClass
public class RatingReportBizFormBean extends FragmentBean {

	private RatingReportBiz ratingReportBiz;

	public RatingReportBiz getRatingReportBiz() {
		return ratingReportBiz;
	}

	public void setRatingReportBiz(RatingReportBiz ratingReportBiz) {
		this.ratingReportBiz = ratingReportBiz;
	}
}

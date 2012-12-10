package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.RatingBiz;

@NoMapSuperClass
public class RatingBizFormBean extends FragmentBean {

	private RatingBiz ratingBiz;

	public RatingBiz getRatingBiz() {
		return ratingBiz;
	}

	public void setRatingBiz(RatingBiz ratingBiz) {
		this.ratingBiz = ratingBiz;
	}

}

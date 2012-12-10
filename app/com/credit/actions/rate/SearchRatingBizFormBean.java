package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.model.rate.ListRatingBiz;

public class SearchRatingBizFormBean extends FragmentBean{

	private ListRatingBiz biz;

	public ListRatingBiz getBiz() {
		return biz;
	}

	public void setBiz(ListRatingBiz biz) {
		this.biz = biz;
	}

}

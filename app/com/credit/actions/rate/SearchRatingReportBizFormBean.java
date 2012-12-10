package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.model.rate.ListRatingReportBiz;

public class SearchRatingReportBizFormBean extends FragmentBean {

	private ListRatingReportBiz biz;

	private boolean useComment = false;

	public ListRatingReportBiz getBiz() {
		return biz;
	}

	public void setBiz(ListRatingReportBiz biz) {
		this.biz = biz;
	}

	public boolean isUseComment() {
		return useComment;
	}

	public void setUseComment(boolean useComment) {
		this.useComment = useComment;
	}

}

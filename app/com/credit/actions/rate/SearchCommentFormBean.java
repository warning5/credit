package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.credit.model.rate.ReportComment;

public class SearchCommentFormBean extends FragmentBean {

	private ReportComment comment;

	public ReportComment getComment() {
		return comment;
	}

	public void setComment(ReportComment comment) {
		this.comment = comment;
	}

}

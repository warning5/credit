package com.credit.actions.rate;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.ReportComment;

@NoMapSuperClass
public class ReportCommentFormBean extends FragmentBean {

	private ReportComment comment;

	public ReportComment getComment() {
		return comment;
	}

	public void setComment(ReportComment reportComment) {
		this.comment = reportComment;
	}
}

package com.credit.model.rate;

import java.util.Date;
import java.util.List;

import com.bluecloud.persistence.pojo.BaseBO;

public class ReportComment extends BaseBO{

	private String rateReptId;

	private String commentContent;

	private Date commentTime;

	private String commentParent;

	private String commentOrgId;

	private String commentOrgType;
	private List<ReportComment> children;

	
	public ReportComment() {
		super();
	}

	public String getRateReptId() {
		return rateReptId;
	}

	public void setRateReptId(String rateReptId) {
		this.rateReptId = rateReptId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentParent() {
		return commentParent;
	}

	public void setCommentParent(String commentParent) {
		this.commentParent = commentParent;
	}

	/**
	 * @return the commentOrgType
	 */
	public String getCommentOrgType() {
		return commentOrgType;
	}

	/**
	 * @param commentOrgType
	 *            the commentOrgType to set
	 */
	public void setCommentOrgType(String commentOrgType) {
		this.commentOrgType = commentOrgType;
	}

	/**
	 * @return the commentOrgId
	 */
	public String getCommentOrgId() {
		return commentOrgId;
	}

	/**
	 * @param commentOrgId
	 *            the commentOrgId to set
	 */
	public void setCommentOrgId(String commentOrgId) {
		this.commentOrgId = commentOrgId;
	}

	public List<ReportComment> getChildren() {
		return children;
	}

	public void setChildren(List<ReportComment> children) {
		this.children = children;
	}

	@Override
	protected String getPKName() {
		return "commentId";
	}

}
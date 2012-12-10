package com.credit.mapper.rate;

import java.util.List;

import com.bluecloud.persistence.dao.BaseDAO;
import com.credit.model.rate.ReportComment;

public interface ReportCommentMapper extends BaseDAO{

	ReportComment selectByPrimaryKey(String commentId);

	List<ReportComment> selectByReportId(String commentId);
}
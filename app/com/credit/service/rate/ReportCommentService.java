/**
 * 
 */
package com.credit.service.rate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.mapper.rate.ReportCommentMapper;
import com.credit.model.rate.ReportComment;

@Service
public final class ReportCommentService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private ReportCommentMapper mapper;

	public boolean saveReportComment(ReportComment reportComment) {
		try {
			reportComment.setCommentTime(new Date());
			mapper.add(reportComment);
		} catch (Exception e) {
			log.error(String.format("insert report comment failure,reportglobalId=%s", reportComment.getRateReptId()),
					e);
			return false;
		}
		return true;
	}

	public boolean deleteAgcBases(List<String> reportCommentIds) {
		if (reportCommentIds == null || reportCommentIds.isEmpty()) {
			return false;
		}
		try {
			this.mapper.delete(reportCommentIds);
		} catch (Exception e) {
			log.error("delete report comment failure. reportCommentIds=" + reportCommentIds, e);
			return false;
		}
		return true;
	}

	public boolean updateReportComment(ReportComment reportComment) {

		try {
			mapper.update(reportComment);
		} catch (Exception e) {
			log.error(
					"update report comment '" + reportComment.getId() + "' for report "
							+ reportComment.getRateReptId() + " failure.", e);
			return false;
		}
		return true;
	}

	public List<ReportComment> getReportComment(String reportCommentId) {
		try {
			return mapper.selectByReportId(reportCommentId);
		} catch (Exception e) {
			log.error("get report comment failure.agcId=" + reportCommentId, e);
		}
		return null;
	}
}

/**
 * 
 */
package com.credit.service.rate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.actions.rate.AfterRatingBizAction;
import com.credit.actions.rate.HandleBizBean;
import com.credit.actions.rate.SearchRatingReportBizFormBean;
import com.credit.mapper.rate.RatingBizMapper;
import com.credit.mapper.rate.RatingReportBizMapper;
import com.credit.model.rate.ListRatingReportBiz;
import com.credit.model.rate.RatingReportBiz;
import com.credit.model.rate.ReportChangeModel;
import com.credit.model.rate.StateChangeModel;

@Service
public final class RatingReportBizService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private RatingReportBizMapper mapper;
	@Autowired
	private RatingBizMapper ratingBizMapper;
	@Autowired
	private UploadService uploadService;

	public boolean saveRatingReportBiz(RatingReportBiz biz) {
		try {
			mapper.insert(biz);
			ratingBizMapper.updateBizReportKey(new ReportChangeModel(biz.getRateId(), biz.getReportGlobalId()));
		} catch (Exception e) {
			log.error(
					String.format("insert rating report biz failure,rateId=%s,state=%d", biz.getRateId(),
							biz.getState()), e);
			return false;
		}
		return true;
	}

	public boolean deleteRatingReportBizs(List<String> reportIds, List<String> rateIds) {
		if ((reportIds == null || reportIds.isEmpty()) && (rateIds == null || rateIds.isEmpty())
				&& reportIds.size() == rateIds.size()) {
			return false;
		}
		try {
			//TODO:only search report
			final List<RatingReportBiz> bizs = new ArrayList<RatingReportBiz>();
			for (String id : reportIds) {
				RatingReportBiz biz = mapper.selectByPrimaryKey(id);
				if (biz != null) {
					bizs.add(biz);
				}
			}
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (RatingReportBiz biz : bizs) {
						String reportScanFile = biz.getReportScanFile();
						uploadService.removeTargetFile(AfterRatingBizAction.reportScanFile, reportScanFile);
					}
				}
			});
			thread.start();

			this.mapper.deleteRatingReportBizs(reportIds);
			for (String rateId : rateIds) {
				ratingBizMapper.updateBizReportKey(new ReportChangeModel(rateId, null));
			}
		} catch (Exception e) {
			log.error("delete rate report biz failure. rateIds=" + reportIds, e);
			return false;
		}
		return true;
	}

	public boolean updateRatingReportBiz(RatingReportBiz biz) {
		try {
			mapper.update(biz);
		} catch (Exception e) {
			log.error(
					String.format("update rating report biz failure,rateId=%s,state=%d", biz.getRateId(),
							biz.getState()), e);
			return false;
		}
		return true;
	}

	public RatingReportBiz getRatingReportBizByID(String rateReportId) {
		return mapper.selectByPrimaryKey(rateReportId);
	}

	public List<ListRatingReportBiz> getRatingReportBizs(SearchRatingReportBizFormBean formBean) {
		if (formBean.getBiz() == null) {
			return getRatingReportBizs(formBean.getOffset(), formBean.getLimit());
		}
		if (!formBean.isUseComment()) {
			return mapper.searchLimit(formBean.getBiz(), new RowBounds(formBean.getOffset(), formBean.getLimit()));
		} else {
			return mapper.searchLimitWithComment(formBean.getBiz(),
					new RowBounds(formBean.getOffset(), formBean.getLimit()));
		}
	}

	public int allCount(SearchRatingReportBizFormBean formBean) {
		if (formBean.getBiz() == null) {
			return 0;
		}
		if (!formBean.isUseComment()) {
			return mapper.allCount(formBean.getBiz());
		} else {
			return mapper.allCountWithComment(formBean.getBiz());
		}
	}

	public List<ListRatingReportBiz> getRatingReportBizs(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}

	public boolean uploadReport(List<StateChangeModel> stateChangeModel) {

		for (StateChangeModel model : stateChangeModel) {
			try {
				mapper.uploadReport(model);
			} catch (Exception e) {
				log.error(String.format("upload report state failure, model=%s", model.toString()), e);
				return false;
			}
		}
		return true;
	}

	public boolean approve(List<StateChangeModel> stateModel) {
		for (StateChangeModel model : stateModel) {
			try {
				mapper.approve(model);
			} catch (Exception e) {
				log.error(String.format("approve state failure, model=%s", model.toString()), e);
				return false;
			}
		}
		return true;
	}

	public boolean back(HandleBizBean biz) {
		try {
			mapper.back(biz);
		} catch (Exception e) {
			log.error("back report '" + biz.getRateId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public String getBackReason(String rateId) {
		try {
			return mapper.getBackReason(rateId);
		} catch (Exception e) {
			log.error("get back reason failure", e);
		}
		return null;
	}
}

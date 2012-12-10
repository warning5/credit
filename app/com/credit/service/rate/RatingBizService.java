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

import com.credit.actions.rate.HandleBizBean;
import com.credit.actions.rate.RatingBizAction;
import com.credit.mapper.rate.RatingBizMapper;
import com.credit.model.rate.ListRatingBiz;
import com.credit.model.rate.RatingBiz;
import com.credit.model.rate.StateChangeModel;

@Service
public final class RatingBizService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private RatingBizMapper mapper;
	@Autowired
	private UploadService uploadService;

	public boolean saveRatingBiz(RatingBiz biz) {
		try {
			mapper.insert(biz);
		} catch (Exception e) {
			log.error("insert biz '" + biz.getRateId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public boolean back(HandleBizBean biz) {
		try {
			mapper.back(biz);
		} catch (Exception e) {
			log.error("back biz '" + biz.getRateId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public boolean updateRatingBiz(RatingBiz biz) {
		try {
			mapper.update(biz);
		} catch (Exception e) {
			log.error("update biz '" + biz.getRateId() + "' failure.", e);
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

	public RatingBiz getRatingBizByID(String rateId) {
		return mapper.selectByPrimaryKey(rateId);
	}

	public List<ListRatingBiz> getRatingBizs(ListRatingBiz listRatingBiz, int offset, int limit) {
		if (listRatingBiz == null) {
			return this.getRatingBizs(offset, limit);
		}
		return mapper.searchLimit(listRatingBiz, new RowBounds(offset, limit));
	}

	public List<ListRatingBiz> getRatingBizs(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}

	public boolean updateStates(List<StateChangeModel> stateModel) {
		for (StateChangeModel model : stateModel) {
			try {
				mapper.updateBizState(model);
			} catch (Exception e) {
				log.error(String.format("update state failure, model=%s", model.toString()), e);
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

	public boolean deleteRatingBizs(final List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return false;
		}
		try {
			final List<RatingBiz> bizs = new ArrayList<RatingBiz>();
			for (String id : ids) {
				RatingBiz biz = mapper.selectByPrimaryKey(id);
				if (biz != null) {
					bizs.add(biz);
				}
			}
			this.mapper.deleteBizs(ids);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (RatingBiz biz : bizs) {
						String rateGzApScanFile = biz.getRateGzApScanFile();
						uploadService.removeTargetFile(RatingBizAction.rateGzApScanFile, rateGzApScanFile);
						String rateScanFile = biz.getRateScanFile();
						uploadService.removeTargetFile(RatingBizAction.rateScanFile, rateScanFile);
						String rateScanReceipt = biz.getRateScanReceipt();
						uploadService.removeTargetFile(RatingBizAction.rateScanReceipt, rateScanReceipt);
					}
				}
			});
			thread.start();

		} catch (Exception e) {
			log.error("delete rate biz failure.ids=" + ids, e);
			return false;
		}
		return true;
	}

	public int allCount(ListRatingBiz listRatingBiz) {
		return mapper.allCount(listRatingBiz);
	}
}

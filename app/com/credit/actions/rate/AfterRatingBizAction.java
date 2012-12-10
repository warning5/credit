/**
 * 
 */
package com.credit.actions.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.bluecloud.persistence.util.PrimaryKey;
import com.credit.base.Constants;
import com.credit.base.Pair;
import com.credit.model.rate.RatingReportBiz;
import com.credit.model.rate.StateChangeModel;
import com.credit.service.rate.UploadService;
import com.credit.util.MessageUtils;

@Controller(Constants.AFTER_RATINGBIZ_ACTION_NAME)
public class AfterRatingBizAction extends AfterBizAction {

	private Log log = LogFactory.getLog(getClass());
	public static final String reportScanFile = "reportScanFile";

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchRatingReportBizFormBean.class);
		reg.add(RATINGREPORTBIZ_FORMBEAN, RatingReportBizFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			List<Integer> states = new ArrayList<Integer>();
			states.add(Constants.REPORT_SAVE_STATE);
			res = searchRateReportBiz(request, states, true);
			res.addSelect(new DefaultSelectWidget("afterRatingBiz_xyLevel", getRateLevelSelect()));
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/rate/after/reportList.html");
		return res;
	}

	public HtmlFragmentResponse search(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = searchRateReportBiz(request, null, false);
		res.forward("pages/query/searchAfterBiz.html");
		return res;
	}

	public HtmlFragmentResponse searchBrower(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getDetail(request);
		res.forward("pages/query/AfterBizBrower.html");
		return res;
	}

	public HtmlFragmentResponse searchReport(HtmlFragmentRequest request) throws FragmentEventException {
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.REPORT_APPROVAL_STATE);
		HtmlFragmentResponse res = searchRateReportBiz(request, states, false);
		res.forward("pages/query/searchReport.html");
		return res;
	}

	public HtmlFragmentResponse searchReportBrower(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getDetail(request);
		res.forward("pages/query/reportBrower.html");
		return res;
	}

	public HtmlFragmentResponse showAddAfter(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String bizIds = request.getHttpServletRequest().getParameter("bizIds");
		String tabId = request.getHttpServletRequest().getParameter(Constants.TABID);
		if (StringUtils.isEmpty(tabId)) {
			tabId = getName();
		}
		if (StringUtils.isEmpty(bizIds)) {
			MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
			message.fail();
			res.addMessage(message);
			return res;
		}
		request.getHttpServletRequest().setAttribute(Constants.RATELEVEL, dicService.getDicRateLevel());
		String[] ids = bizIds.split(":");
		request.getHttpServletRequest().setAttribute(Constants.RATEID, ids[0]);
		request.getHttpServletRequest().setAttribute("firmId", ids[1]);
		request.getHttpServletRequest().setAttribute("agcId", ids[2]);
		request.getHttpServletRequest().setAttribute(Constants.TABID, tabId);
		res.forward("pages/rate/after/addRateReportBiz.jsp");
		return res;
	}

	public HtmlFragmentResponse addRatingReportBiz(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			RatingReportBizFormBean ratingReportBizFormBean = (RatingReportBizFormBean) request
					.getForm(RATINGREPORTBIZ_FORMBEAN);
			RatingReportBiz ratingReportBiz = ratingReportBizFormBean.getRatingReportBiz();
			ratingReportBiz.setReportGlobalId(PrimaryKey.getID());
			ratingReportBiz.setState(Constants.REPORT_SAVE_STATE);
			Map<String, Pair<String, String>> folders = handleUploadFile(ratingReportBiz,
					request.getHttpServletRequest());
			boolean result = reportBizService.saveRatingReportBiz(ratingReportBiz);
			if (result) {
				String tabId = ratingReportBiz.getTabId();
				message = MessageUtils.build("新增后报备业务成功", tabId, MessageUtils.SUCCESS, true);
				if (folders.size() != 0)
					uploadService.afterUpload(folders, request.getHttpServletRequest());
			} else {
				message = MessageUtils.build("新增后报备业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			message = MessageUtils.build("新增后报备业务失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(), e);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse deleteRatingReportBizs(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			MessageWidget message = null;
			List<String> ids = req.getIDS();
			List<String> reportIds = null;
			List<String> rateIds = null;
			if (ids == null) {
				String[] tempIds = getReportIdAndRateId(req.getHttpServletRequest().getParameter(PAGE_ID));
				String reportId = tempIds[0];
				String rateId = tempIds[1];
				if (StringUtils.isNotEmpty(reportId)) {
					reportIds = new ArrayList<String>(1);
					reportIds.add(reportId);
				}
				if (StringUtils.isNotEmpty(rateId)) {
					rateIds = new ArrayList<String>(1);
					rateIds.add(rateId);
				}
			} else {
				reportIds = new ArrayList<String>(ids.size());
				rateIds = new ArrayList<String>(ids.size());
				for (String id : ids) {
					String[] tempIds = getReportIdAndRateId(id);
					reportIds.add(tempIds[0]);
					rateIds.add(tempIds[1]);
				}
			}
			if (reportIds == null) {
				message = new DefaultMessageWidget("请选择数据");
				message.fail();
			} else {
				boolean isSuccess = reportBizService.deleteRatingReportBizs(reportIds, rateIds);
				if (isSuccess) {
					message = new DefaultMessageWidget("删除后报备业务成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("删除后报备业务失败");
					message.fail();
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String rateReportId = getReportIdAndRateId(request.getHttpServletRequest().getParameter(PAGE_ID))[0];
			if (StringUtils.isEmpty(rateReportId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			RatingReportBiz biz = reportBizService.getRatingReportBizByID(rateReportId);
			FormWidget form;
			if (biz != null) {
				RatingReportBizFormBean bean = new RatingReportBizFormBean();
				String tabId = request.getHttpServletRequest().getParameter(Constants.TABID);
				if (StringUtils.isEmpty(tabId)) {
					tabId = getName();
				}
				biz.setTabId(tabId);
				bean.setRatingReportBiz(biz);
				form = new DefaultFormWidget(PAGE_ITEM, bean);
			} else {
				form = new DefaultFormWidget(PAGE_ITEM, new RatingBizFormBean());
			}

			res.addSelect(new DefaultSelectWidget("ratingReportBiz_xyLevel", getRateLevelSelect()));
			res.addForm(form);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/rate/after/editRateReportBiz.html");
		return res;
	}

	public HtmlFragmentResponse editRatingReportBiz(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		try {
			RatingReportBizFormBean bean = (RatingReportBizFormBean) req.getForm(RATINGREPORTBIZ_FORMBEAN);
			RatingReportBiz biz = bean.getRatingReportBiz();
			HttpServletRequest request = req.getHttpServletRequest();

			Map<String, Pair<String, String>> folders = new HashMap<String, Pair<String, String>>();

			if (uploadService.isSourceExist(reportScanFile, biz.getReportScanFile(), request)) {
				if (StringUtils.isNotEmpty(biz.getReportScanFileOld())) {
					if (uploadService.isTargetExist(reportScanFile, biz.getReportScanFileOld(), request)) {
						uploadService.removeFile(reportScanFile, biz.getReportScanFileOld(), request);
					}
				}
				String raw = biz.getReportScanFile();
				String targetFileName = null;
				if (StringUtils.isNotEmpty(raw)) {
					if (uploadService.isTargetExist(reportScanFile, raw, request)) {
						targetFileName = UploadService.formatFileNameWithDate(raw);
						biz.setReportScanFile(targetFileName);
					}
					UploadService.putFile(folders, request, reportScanFile, raw, targetFileName);
				}
			}

			String reportScanFileOld = biz.getReportScanFileOld();
			if (StringUtils.isNotEmpty(reportScanFileOld)) {
				if (!reportScanFileOld.equals(biz.getReportScanFile())) {
					uploadService.removeFile(reportScanFile, reportScanFileOld, req.getHttpServletRequest());
				}
			}

			if (reportBizService.updateRatingReportBiz(biz)) {
				String tabId = bean.getRatingReportBiz().getTabId();
				message = MessageUtils.build("修改后报备成功", tabId, MessageUtils.SUCCESS, true);
				if (folders.size() != 0)
					uploadService.afterUpload(folders, req.getHttpServletRequest());
			} else {
				message = MessageUtils.build("修改评级业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			message = MessageUtils.build("修改评级业务失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse brower(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getDetail(request);
		res.forward("pages/rate/after/reportDetail.html");
		return res;
	}
	
	public HtmlFragmentResponse supervisorBrower(HtmlFragmentRequest request) throws FragmentEventException {
		return brower(request);
	}

	public HtmlFragmentResponse getDetail(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String rateReportId = request.getHttpServletRequest().getParameter(PAGE_ID);
			if (StringUtils.isEmpty(rateReportId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			RatingReportBiz biz = reportBizService.getRatingReportBizByID(rateReportId);
			FormWidget form;
			if (biz != null) {
				biz.setXyLevel(dicService.getRateLevelMap().get(biz.getXyLevel()));
				RatingReportBizFormBean bean = new RatingReportBizFormBean();
				bean.setRatingReportBiz(biz);
				form = new DefaultFormWidget(PAGE_ITEM, bean);
			} else {
				form = new DefaultFormWidget(PAGE_ITEM, new RatingBizFormBean());
			}
			res.addForm(form);
		} catch (Exception e) {
			throw new FragmentEventException(e);
		}

		return res;
	}

	public HtmlFragmentResponse uploadReport(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<StateChangeModel> models = null;
		try {
			MessageWidget message = null;
			List<String> ids = req.getIDS();
			if (ids == null) {
				String rateId = getReportIdAndRateId(req.getHttpServletRequest().getParameter(PAGE_ID))[0];
				if (StringUtils.isNotEmpty(rateId)) {
					models = new ArrayList<StateChangeModel>(1);
					StateChangeModel model = new StateChangeModel(rateId, Constants.REPORT_SUBMIT_STATE, new Date());
					models.add(model);
				}
			} else {
				models = new ArrayList<StateChangeModel>(ids.size());
				for (String id : ids) {
					StateChangeModel model = new StateChangeModel(getReportIdAndRateId(id)[0],
							Constants.REPORT_SUBMIT_STATE, new Date());
					models.add(model);
				}
			}
			if (models == null) {
				message = MessageUtils.build("请选择数据", null, MessageUtils.ERROR, false);
			} else {
				boolean isSuccess = reportBizService.uploadReport(models);
				if (isSuccess) {
					String tabId = req.getHttpServletRequest().getParameter(Constants.TABID);
					if (StringUtils.isEmpty(tabId)) {
						tabId = getName();
					}
					message = MessageUtils.build("上报评级报告成功", null, MessageUtils.SUCCESS, false);
				} else {
					message = MessageUtils.build("上报评级报告失败", null, MessageUtils.ERROR, false);
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	private Map<String, Pair<String, String>> handleUploadFile(RatingReportBiz ratingReportBiz,
			HttpServletRequest request) {

		Map<String, Pair<String, String>> folders = new HashMap<String, Pair<String, String>>();

		String targetFileName = null;
		String raw = ratingReportBiz.getReportScanFile();
		if (StringUtils.isNotEmpty(raw)) {
			if (uploadService.isTargetExist(reportScanFile, raw, request)) {
				targetFileName = UploadService.formatFileNameWithDate(raw);
				ratingReportBiz.setReportScanFile(targetFileName);
			}
			UploadService.putFile(folders, request, reportScanFile, raw, targetFileName);
		}
		return folders;
	}

	public HtmlFragmentResponse submittedRatingBiz(HtmlFragmentRequest req) throws FragmentEventException {
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.REPORT_SUBMIT_STATE);
		HtmlFragmentResponse res = searchRateReportBiz(req, states, false);
		res.addSelect(new DefaultSelectWidget("submittedRatingBiz_xyLevel", getRateLevelSelect()));
		res.forward("pages/rate/after/afterSumitList.html");
		return res;
	}

	@Override
	public String getName() {
		return Constants.AFTER_RATINGBIZ_ACTION_NAME;
	}
}

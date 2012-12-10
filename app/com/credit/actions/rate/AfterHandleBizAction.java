package com.credit.actions.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.model.rate.StateChangeModel;
import com.credit.util.MessageUtils;

@Controller(Constants.AFTER_HANDLEBIZ_ACTION_NAME)
public class AfterHandleBizAction extends AfterBizAction {

	private Log log = LogFactory.getLog(getClass());

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchRatingReportBizFormBean.class);
		reg.add(RATINGREPORTBIZ_FORMBEAN, RatingReportBizFormBean.class);
		reg.add("handleBiz", HandleBizBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			List<Integer> states = new ArrayList<Integer>();
			states.add(Constants.REPORT_SUBMIT_STATE);
			res = searchRateReportBiz(request, states, false);
			res.addSelect(new DefaultSelectWidget("biz_xyLevel", getRateLevelSelect()));
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/rate/after/afterHandlinglist.html");
		return res;
	}

	public HtmlFragmentResponse afterShowBack(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String reportId = request.getHttpServletRequest().getParameter("reportId");
		if (StringUtils.isNotEmpty(reportId)) {
			request.getHttpServletRequest().setAttribute("rateId", reportId);
			request.getHttpServletRequest().setAttribute("action", "afterHandleBiz_afterBack.action");
			request.getHttpServletRequest().setAttribute("state", Constants.REPORT_BACK_STATE);
		}
		res.forward("pages/rate/back.jsp");
		return res;
	}

	public HtmlFragmentResponse afterShowReason(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String reportId = request.getHttpServletRequest().getParameter("rateId");
		if (StringUtils.isNotEmpty(reportId)) {
			request.getHttpServletRequest().setAttribute("backReason", reportBizService.getBackReason(reportId));
		}
		res.forward("pages/rate/back.jsp");
		return res;
	}

	public HtmlFragmentResponse afterApprovalList(HtmlFragmentRequest request) throws FragmentEventException {
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.REPORT_APPROVAL_STATE);
		HtmlFragmentResponse res = searchRateReportBiz(request, states, false);
		res.addSelect(new DefaultSelectWidget("afterApprovalList_xyLevel", getRateLevelSelect()));
		res.forward("pages/rate/after/approvalList.html");
		return res;
	}

	public HtmlFragmentResponse afterBackList(HtmlFragmentRequest request) throws FragmentEventException {
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.REPORT_BACK_STATE);
		HtmlFragmentResponse res = searchRateReportBiz(request, states, false);
		res.addSelect(new DefaultSelectWidget("afterBackList_xyLevel", getRateLevelSelect()));
		res.forward("pages/rate/after/afterBackList.html");
		return res;
	}

	public HtmlFragmentResponse afterBack(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			HandleBizBean handleBizBean = (HandleBizBean) request.getForm("handleBiz");
			handleBizBean.setChangeDate(new Date());
			if (reportBizService.back(handleBizBean)) {
				message = MessageUtils.build("退回评级业务成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("退回评级业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			message = MessageUtils.build("退回评级业务失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(), e);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse afterApprove(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<StateChangeModel> models = null;
		try {
			MessageWidget message = null;
			List<String> ids = req.getIDS();
			if (ids == null) {
				String reportId = req.getHttpServletRequest().getParameter(PAGE_ID);
				if (StringUtils.isNotEmpty(reportId)) {
					models = new ArrayList<StateChangeModel>(1);
					StateChangeModel model = new StateChangeModel(reportId, Constants.REPORT_APPROVAL_STATE, new Date());
					models.add(model);
				}
			} else {
				models = new ArrayList<StateChangeModel>(ids.size());
				for (String id : ids) {
					StateChangeModel model = new StateChangeModel(id, Constants.REPORT_APPROVAL_STATE, new Date());
					models.add(model);
				}
			}
			if (models == null) {
				message = new DefaultMessageWidget("请选择数据");
				message.fail();
			} else {
				boolean isSuccess = reportBizService.approve(models);
				if (isSuccess) {
					message = new DefaultMessageWidget("批准评级业务成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("批准评级业务失败");
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

	@Override
	public String getName() {
		return Constants.AFTER_HANDLEBIZ_ACTION_NAME;
	}
}
package com.credit.actions.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.beans.PagingBean;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.model.rate.StateChangeModel;
import com.credit.util.MessageUtils;

@Controller(Constants.BEFORE_HANDLEBIZ_ACTION_NAME)
public class BeforeHandleBizAction extends AbstractBizAction {
	private Log log = LogFactory.getLog(getClass());

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("searchForm", SearchRatingBizFormBean.class);
		reg.add("ratingBiz", RatingBizFormBean.class);
		reg.add("handleBiz", HandleBizBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			List<Integer> states = new ArrayList<Integer>();
			states.add(Constants.BEFORE_SUBMIT_STATE);
			res = searchRateBiz(request, states);
		} catch (FragmentEventException e) {
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/rate/before/handlinglist.html");
		return res;
	}

	public HtmlFragmentResponse beforeShowBack(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String rateId = request.getHttpServletRequest().getParameter("rateId");
		if (StringUtils.isNotEmpty(rateId)) {
			request.getHttpServletRequest().setAttribute("rateId", rateId);
			request.getHttpServletRequest().setAttribute("action", "beforeHandleBiz_beforeBack.action");
			request.getHttpServletRequest().setAttribute("state", Constants.BEFORE_BACK_STATE);
			String tabId = request.getHttpServletRequest().getParameter(Constants.TABID);
			if (StringUtils.isEmpty(tabId)) {
				tabId = getName();
			}
			request.getHttpServletRequest().setAttribute(Constants.TABID, tabId);
		}
		res.forward("pages/rate/back.jsp");
		return res;
	}

	public HtmlFragmentResponse beforeShowReason(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String rateId = request.getHttpServletRequest().getParameter("rateId");
		if (StringUtils.isNotEmpty(rateId)) {
			request.getHttpServletRequest().setAttribute("backReason", bizService.getBackReason(rateId));
		}
		res.forward("pages/rate/back.jsp");
		return res;
	}

	public HtmlFragmentResponse beforeApprovalList(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.BEFORE_APPROVAL_STATE);
		SearchRatingBizFormBean formBean;
		try {
			formBean = (SearchRatingBizFormBean) request.getForm(Constants.SEARCHFORM_NAME);
		} catch (HtmlFragmentRequestException e) {
			throw new FragmentEventException(e);
		}
		HttpServletRequest req = request.getHttpServletRequest();
		List<ListRatingBizFormBean> beans = searchFormBeans(request, formBean, states);
		req.setAttribute("data", beans);
		req.setAttribute("paging", new PagingBean(formBean.getPageNum(), formBean.getNumPerPage(), beans.size()));
		if (formBean.getBiz() != null) {
			req.setAttribute("firmCnName", formBean.getBiz().getFirmCnName());
			req.setAttribute("rateDate", formBean.getBiz().getRateDate());
			req.setAttribute("rateGzId", formBean.getBiz().getRateId());
		}

		res.forward("pages/rate/before/approvalList.jsp");
		return res;
	}

	public HtmlFragmentResponse beforeBackList(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Integer> states = new ArrayList<Integer>();
		states.add(Constants.BEFORE_BACK_STATE);
		res = searchRateBiz(request, states);
		res.forward("pages/rate/before/backList.html");
		return res;
	}

	public HtmlFragmentResponse beforeBack(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			HandleBizBean handleBizBean = (HandleBizBean) request.getForm("handleBiz");
			handleBizBean.setChangeDate(new Date());
			if (bizService.back(handleBizBean)) {
				message = MessageUtils.build("退回评级业务成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("退回评级业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			message = MessageUtils.build("退回评级业务失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse beforeApprove(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<StateChangeModel> models = null;
		try {
			DefaultMessageWidget message = null;
			List<String> ids = req.getIDS();
			if (ids == null) {
				String rateId = req.getHttpServletRequest().getParameter("rateId");
				if (StringUtils.isNotEmpty(rateId)) {
					models = new ArrayList<StateChangeModel>(1);
					StateChangeModel model = new StateChangeModel(rateId, Constants.BEFORE_APPROVAL_STATE, new Date());
					models.add(model);
				}
			} else {
				models = new ArrayList<StateChangeModel>(ids.size());
				for (String id : ids) {
					StateChangeModel model = new StateChangeModel(id, Constants.BEFORE_APPROVAL_STATE, new Date());
					models.add(model);
				}
			}
			if (models == null) {
				message = new DefaultMessageWidget("请选择数据");
				message.fail();
			} else {
				boolean isSuccess = bizService.approve(models);
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
			;
			throw new FragmentEventException(e);
		}
		return res;
	}

	@Override
	public String getName() {
		return Constants.BEFORE_HANDLEBIZ_ACTION_NAME;
	}
}
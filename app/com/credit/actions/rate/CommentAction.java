/**
 * 
 */
package com.credit.actions.rate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.HtmlWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultHtmlWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.http.user.IOrgUser;
import com.credit.http.user.OnlineUser;
import com.credit.model.rate.ReportComment;
import com.credit.service.rate.ReportCommentService;
import com.credit.util.CreditConstants;
import com.credit.util.MessageUtils;

@Controller(Constants.COMMENT_ACTION_NAME)
public class CommentAction extends AbstractBizAction {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private ReportCommentService commentService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchCommentFormBean.class);
		reg.add("comment", ReportCommentFormBean.class);
		return reg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.
	 * HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res;
		try {
			List<Integer> states = new ArrayList<Integer>();
			states.add(Constants.BEFORE_NEW_STATE);
			res = searchRateBiz(request, states);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/rate/list.html");
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addComment(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			ReportCommentFormBean bean = (ReportCommentFormBean) request.getForm("comment");
			boolean isSuccess = commentService.saveReportComment(bean.getComment());
			if (isSuccess) {
				message = MessageUtils.build("添加评价成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("添加评价失败", null, MessageUtils.ERROR, false);
			}
			res.addMessage(message);
		} catch (HtmlFragmentRequestException e) {
			message = MessageUtils.build("添加评价失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showComment(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		String reportId = request.getHttpServletRequest().getParameter("reportId");
		if (StringUtils.isEmpty(reportId)) {
			message = MessageUtils.build("无法评级报告评价标识", null, MessageUtils.ERROR, false);
		} else {
			try {
				FormWidget form = null;
				List<ReportComment> reportComments = commentService.getReportComment(reportId);
				if (reportComments != null) {
					String context = this.draw(reportComments, 0, "评价");
					HtmlWidget html = new DefaultHtmlWidget("commentsPanel", context);
					res.addHtml(html);
				} else {
					form = new DefaultFormWidget("comment", new ReportCommentFormBean());
				}
				ReportCommentFormBean bean = new ReportCommentFormBean();
				ReportComment reportComment = new ReportComment();
				reportComment.setRateReptId(reportId);
				OnlineUser user = (OnlineUser) request.getHttpServletRequest().getSession()
						.getAttribute(CreditConstants.ONLINEUSER);

				IOrgUser orgUser = user.getOrgUser();
				String orgId = null;
				String orgName = null;
				if (orgUser == null) {
					orgId = Constants.ADMIN_USER;
					orgName = orgId;
				} else {
					orgId = orgUser.getIdentify();
					orgName = user.getOrg().getName();
				}
				reportComment.setCommentOrgId(orgId);
				reportComment.setCommentOrgType(orgName);
				bean.setComment(reportComment);
				form = new DefaultFormWidget("comment", bean);
				res.addForm(form);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				message = MessageUtils.build("获取评级报告评价失败", getName(), MessageUtils.SUCCESS, true);
			}
		}
		if (message != null)
			res.addMessage(message);
		res.forward("pages/query/comment.html");
		return res;
	}

	/**
	 * 
	 * @param reportComments
	 * @param n
	 * @param type
	 * @return
	 */
	private String draw(List<ReportComment> reportComments, int n, String type) {
		StringBuffer sb = new StringBuffer();
		for (ReportComment reportComment : reportComments) {
			sb.append("<div style=\"margin-left: " + n + "px; \">");
			sb.append("<div class=\"divider\"></div>");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sb.append("<div style=\"font-size: 13px;font-weight: bold;\">");
			sb.append(reportComment.getCommentOrgType());
			sb.append("\t\t");
			sb.append(type);
			sb.append("\t\t");
			sb.append(format.format(reportComment.getCommentTime()));
			sb.append("<a class=\"btnEdit\" href=\"javascript:;\" title=\"发表回复\" commentParent=\""
					+ reportComment.getId() + "\">发表回复</a>");
			sb.append("</div>");
			sb.append("<pre style=\"margin: 5px; line-height: 1.4em\">" + reportComment.getCommentContent() + "</pre>");
			sb.append("</div>");
			List<ReportComment> children = reportComment.getChildren();
			if (children != null) {
				String context = this.draw(children, n + 10, "回复");
				sb.append(context);
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return Constants.COMMENT_ACTION_NAME;
	}
}

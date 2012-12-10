/**
 * 
 */
package com.credit.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.rbac.UserBean;
import com.credit.http.user.IUser;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu 2012-9-11
 */
@Controller("logon")
public class LogonAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("logonBean", UserBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) {
		HtmlFragmentResponse res = getResponse();
		res.forward("index.html");
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse doLogon(HtmlFragmentRequest req) throws FragmentEventException {
		HttpServletRequest request = req.getHttpServletRequest();
		HttpSession session = request.getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		HtmlFragmentResponse res = getResponse();
		if (user.isLogon()) {
			res.redirect("main.action");
			return res;
		}
		try {
			UserBean logonBean = (UserBean) req.getForm("logonBean");
			boolean isLogon = user.logon(logonBean.getUser().getUsername(), logonBean.getUser().getPassword());
			if (isLogon) {
				session.setAttribute(CreditConstants.ONLINEUSER, user);
				res.redirect("main.action");
			} else {
				FormWidget form = new DefaultFormWidget("logonForm", logonBean);
				res.addForm(form);
				MessageWidget message = new DefaultMessageWidget("logonMessage", "用户名或密码错误！");
				message.fail();
				res.addMessage(message);
				res.inner("logon.action", null);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	@Override
	public String getName() {
		return "logon";
	}

}

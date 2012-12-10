/**
 * 
 */
package com.credit.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.HtmlWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultHtmlWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.http.user.IUser;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu
 *
 */
@Controller("auth")
public class AuthAction extends FragmentAction {

	private Log log=LogFactory.getLog(AuthAction.class);

	/**
	 * 
	 */
	public AuthAction() {
	}

	@Override
	protected FragmentBeanRegister regBean() {
		return null;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res=getResponse();
		HttpServletRequest req=request.getHttpServletRequest();
		HttpSession session = req.getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		if(user.isLogon()){
			log.debug("没有权限，转向:html/noAuth.html");
			MessageWidget message=new DefaultMessageWidget("对不起！没有权限");
			message.fail();
			res.addMessage(message);
			res.forward("html/noAuth.html");
		}else{
			log.debug("没有登录，转向:"+req.getContextPath());
			MessageWidget message=new DefaultMessageWidget("您已经离开页面一段时间，请重新登录");
			message.fail();
			res.addMessage(message);
			HtmlWidget html=new DefaultHtmlWidget("nologon", "<script>window.location='" + req.getContextPath() + "'</script>");
			res.addHtml(html);
//			res.forward(req.getContextPath());
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.HtmlFragment#getName()
	 */
	@Override
	public String getName() {
		return "auth";
	}

}

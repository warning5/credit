/**
 * 
 */
package com.credit.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.BizWidget;
import com.bluecloud.mvc.external.widgets.HtmlWidget;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultBizWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultHtmlWidget;
import com.bluecloud.mvc.external.widgets.impl.TreeMenusWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.rbac.FunctionBean;
import com.credit.actions.beans.rbac.UserBean;
import com.credit.http.user.IUser;
import com.credit.rbac.bo.Function;
import com.credit.rbac.service.FunctionService;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu 2012-9-12
 */
@Controller("main")
public class MainAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private FunctionService functionService;

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HttpSession session = request.getHttpServletRequest().getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		HtmlFragmentResponse res = getResponse();
		try {
			Map<String, Function> functions = functionService.getFunctions(user.getUser());
			if (functions != null && !functions.isEmpty()) {
				List<FunctionBean> beans = new ArrayList<FunctionBean>(functions.size());
				for (Function function : functions.values()) {
					FunctionBean bean = new FunctionBean();
					bean.setFunction(function);
					beans.add(bean);
				}
				TreeWidget treeMenus = new TreeMenusWidget("ftreeMenus", beans);
				res.addTreeMenus(treeMenus);
//				UserBean bean=new UserBean();
//				bean.setUser(user.getUser());
//				BizWidget biz=new DefaultBizWidget("bizUser", bean);
//				res.addBiz(biz);
				String content=user.getUser().getUsername();
				HtmlWidget html=new DefaultHtmlWidget("bizUser", content);
				res.addHtml(html);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/main.html");
		return res;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "main";
	}
}

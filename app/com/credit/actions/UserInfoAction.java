/**
 * 
 */
package com.credit.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.beans.PagingBean;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.TableWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultTableWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.bluecloud.persistence.util.MD5;
import com.credit.actions.beans.rbac.GroupBean;
import com.credit.actions.beans.rbac.RoleBean;
import com.credit.actions.beans.rbac.UserBean;
import com.credit.http.user.IUser;
import com.credit.rbac.bo.Group;
import com.credit.rbac.bo.Role;
import com.credit.rbac.bo.User;
import com.credit.rbac.service.UserService;
import com.credit.util.CreditConstants;

/**
 * @author Hanlu
 *
 */
@Controller("userInfo")
public class UserInfoAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private UserService userService;
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HttpSession session = request.getHttpServletRequest().getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		HtmlFragmentResponse res = getResponse();
		try {
				UserBean bean=new UserBean();
//				User u=userService.getUserByID(user.getUser().getId());
				bean.setUser(user.getUser());
				FormWidget form=new DefaultFormWidget("user", bean);
				res.addForm(form);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/userinfo/showInfo.html");
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showRole(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res=getResponse();
		HttpSession session = request.getHttpServletRequest().getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		List<Role> roles = user.getUser().getRoles();
//		try {
//			User u=userService.getUserByID(user.getUser().getId());
//			roles = roleService.getRoles(u);
//		} catch (RBACServiceException e) {
//			log.error(e.getMessage(), e);
//		}
		List<RoleBean> rbs = new ArrayList<RoleBean>();
		if (roles != null) {
			for (Role role : roles) {
				RoleBean rb = new RoleBean();
				rb.setRole(role);
				rbs.add(rb);
			}
		}
		TableWidget table = new DefaultTableWidget("roles", rbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/userinfo/showRole.html");
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showGroup(HtmlFragmentRequest request)
			throws FragmentEventException {
		HtmlFragmentResponse res=getResponse();
		HttpSession session = request.getHttpServletRequest().getSession();
		IUser user = (IUser) session.getAttribute(CreditConstants.ONLINEUSER);
		List<Group>groups = user.getUser().getGroups();
//		try {
//			User u=userService.getUserByID(user.getUser().getId());
//			groups =groupService.getGroups(u);
//		} catch (RBACServiceException e) {
//			log.error(e.getMessage(), e);
//		}
		List<GroupBean> gbs = new ArrayList<GroupBean>();
		if (groups != null) {
			for (Group group :groups) {
				GroupBean rb = new GroupBean();
				rb.setGroup(group);
				gbs.add(rb);
			}
		}
		TableWidget table = new DefaultTableWidget("groups", gbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/userinfo/showGroup.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			UserBean bean = (UserBean) req.getForm("user");
			User oldUser = bean.getUser();
			User user = userService.getUser(oldUser.getUsername());
			MessageWidget message = null;
			if (user != null) {
				String password = oldUser.getPassword();
				if (password != null && !password.trim().equals("")) {
					if (!user.getPassword().equals(password)) {
						password = MD5.exchange(password);
						oldUser.setPassword(password);
					}
				}
				boolean isSuccess = userService.updateUser(oldUser);
				if (isSuccess) {
					message = new DefaultMessageWidget("修改信息成功");
//					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("修改信息失败");
					message.fail();
				}
			} else {
				message = new DefaultMessageWidget("修改信息失败");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}
	
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("user", UserBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "userInfo";
	}

}

/**
 * 
 */
package com.credit.actions.rbac;

import java.util.ArrayList;
import java.util.List;

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
import com.credit.actions.beans.rbac.UserBean;
import com.credit.rbac.bo.User;
import com.credit.rbac.service.UserService;
import com.credit.util.MessageUtils;

/**
 * @author Hanlu
 * 
 */
@Controller("userManager")
public class UserManagerAction extends FragmentAction {

	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private UserService userService;

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("searchUser", UserBean.class);
		reg.add("user", UserBean.class);
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
		HtmlFragmentResponse res = null;
		try {
			res = this.searchUser(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		// res.forward("pages/rbac/userManager.html");
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		res.forward("pages/rbac/user/addUser.html");
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			UserBean bean = (UserBean) request.getForm("user");
			if (bean.getUser() == null) {
				MessageWidget message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			User user = userService.getUserByID(bean.getUser().getId());
			if (user != null) {
				bean.setUser(user);
				FormWidget form = new DefaultFormWidget("user", bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget("user", new UserBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/rbac/user/editUser.html");
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse searchUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			UserBean bean = (UserBean) req.getForm("searchUser");
			User u = bean.getUser();
			if (u == null) {
				u = new User();
			}
			List<User> users = userService.getUsers(u, bean.getOffset(), bean.getLimit());
			List<UserBean> ubs = new ArrayList<UserBean>();
			if (users != null) {
				for (User user : users) {
					UserBean ub = new UserBean();
					ub.setUser(user);
					ubs.add(ub);
				}
			}
			int allCount = userService.allCount(u);
			TableWidget table = new DefaultTableWidget("users", ubs, new PagingBean(bean.getPageNum(),
					bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("searchUser", bean);
			res.addForm(form);
			res.forward("pages/rbac/user/userManager.html");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
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
					message = new DefaultMessageWidget("修改用户成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("修改用户失败");
					message.fail();
				}
			} else {
				message = new DefaultMessageWidget("修改用户失败");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse deleteUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			List<String> ids = req.getIDS();
			if (ids == null) {
				UserBean bean = (UserBean) req.getForm("user");
				if (bean != null && bean.getUser() != null) {
					ids = new ArrayList<String>();
					ids.add(bean.getUser().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = userService.deleteUsers(ids);
			if (isSuccess) {
				message = new DefaultMessageWidget("删除用户成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("删除用户失败");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			UserBean userBean = (UserBean) req.getForm("user");
			User user = userBean.getUser();
			String password = user.getPassword();
			if (password != null && !password.trim().equals("")) {
				password = MD5.exchange(password);
				user.setPassword(password);
			}
			User oldUser = userService.getUser(user.getUsername());
			if (oldUser != null) {
				message = MessageUtils.build("添加用户失败，用户名[" + user.getUsername() + "]已经存在", null, MessageUtils.ERROR,
						false);
			} else {
				boolean isSuccess = userService.saveUser(user);
				if (isSuccess) {
					message = MessageUtils.build("添加用户成功", getName(), MessageUtils.SUCCESS, true);
				} else {
					message = MessageUtils.build("添加用户失败", null, MessageUtils.ERROR, false);
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			message = MessageUtils.build("添加用户失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/**
	 * 执行禁用操作
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse disableUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableUser(req, res, "禁用用户成功", "禁用用户失败", true);
		return res;
	}

	/**
	 * 
	 * @param req
	 * @param res
	 * @param successInfo
	 * @param failInfo
	 * @param disable
	 * @throws FragmentEventException
	 */
	private void doDisableUser(HtmlFragmentRequest req, HtmlFragmentResponse res, String successInfo, String failInfo,
			boolean disable) throws FragmentEventException {
		try {
			List<String> ids = req.getIDS();
			if (ids == null) {
				UserBean bean = (UserBean) req.getForm("user");
				if (bean != null && bean.getUser() != null) {
					ids = new ArrayList<String>();
					ids.add(bean.getUser().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = userService.disableUsers(ids, disable);
			if (isSuccess) {
				message = new DefaultMessageWidget(successInfo);
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget(failInfo);
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
	}

	/**
	 * 执行解禁操作
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse enableUser(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableUser(req, res, "解禁用户成功", "解禁用户失败", false);
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.HtmlFragment#getName()
	 */
	@Override
	public String getName() {
		return "userManager";
	}

}

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
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.CheckTreeWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultTableWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.rbac.RoleBean;
import com.credit.actions.beans.rbac.UserBean;
import com.credit.rbac.bo.Role;
import com.credit.rbac.bo.User;
import com.credit.rbac.service.RoleService;
import com.credit.rbac.service.UserService;

/**
 * @author Hanlu
 *
 */
@Controller("userAllocateRole")
public class UserAllocateRoleAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			res = this.searchUser(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
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
			UserBean bean = (UserBean) req.getForm("allocateRoleSearchUser");
			User u=bean.getUser();
			if(u==null){
				u=new User();
			}
			List<User> users = userService.getUsers(u,bean.getOffset(),bean.getLimit());
			List<UserBean> ubs = new ArrayList<UserBean>();
			if (users != null) {
				for (User user : users) {
					UserBean ub = new UserBean();
					ub.setUser(user);
					ubs.add(ub);
				}
			}
			int allCount=userService.allCount(u);
			TableWidget table = new DefaultTableWidget("users", ubs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("allocateRoleSearchUser", bean);
			res.addForm(form);
			res.forward("pages/rbac/user/showAllocateRole.html");
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
	public HtmlFragmentResponse showUserRole(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Role> roles;
		try {
			UserBean bean=(UserBean) req.getForm("user");
			roles=roleService.getRoles(bean.getUser());
			if(bean.getUser()!=null){
				FormWidget form=new DefaultFormWidget("roleUser", bean);
				res.addForm(form);
				req.getHttpServletRequest().getSession().setAttribute("userid", bean.getUser().getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<RoleBean> rbs = new ArrayList<RoleBean>();
		if (roles != null) {
			for (Role role : roles) {
				RoleBean rb = new RoleBean();
				rb.setRole(role);
				rbs.add(rb);
			}
		}
		TableWidget table = new DefaultTableWidget("userRoles", rbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/rbac/user/showUserRole.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addRole(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String userid=(String) req.getHttpServletRequest().getSession().getAttribute("userid");
		try {
			List<String> ids=req.getIDS("userRoles");
			MessageWidget message = null;
			boolean isSuccess = userService.allocateRole(ids,userid);
			if (isSuccess) {
				message = new DefaultMessageWidget("分配角色成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("分配角色失败");
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
	public HtmlFragmentResponse showAddRole(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Role> roles;
		List<Role> oldRoles;
		try {
			roles=roleService.getRoles();
			UserBean bean=(UserBean) req.getForm("user");
			oldRoles=roleService.getRoles(bean.getUser());
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<RoleBean> parent = new ArrayList<RoleBean>();
		List<RoleBean> child = new ArrayList<RoleBean>();
		for (Role role : roles) {
			RoleBean rb = new RoleBean();
			rb.setRole(role);
			parent.add(rb);
		}
		if(oldRoles!=null){
			for(Role role:oldRoles){
				RoleBean rb = new RoleBean();
				rb.setRole(role);
				child.add(rb);
			}
		}
		TreeWidget tree=new CheckTreeWidget("userRoleTree","用户角色树");
		tree.reload(parent, child);
		res.addCheckTree(tree);
		res.forward("pages/rbac/user/showAddRole.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse disallocateRole(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String userid=(String) req.getHttpServletRequest().getSession().getAttribute("userid");
		try {
			List<String> ids = req.getIDS();
			if(ids==null){
				RoleBean bean=(RoleBean) req.getForm("role");
				if(bean!=null&&bean.getRole()!=null){
					ids=new ArrayList<String>();
					ids.add(bean.getRole().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = userService.disallocateRole(ids,userid);
			if (isSuccess) {
				message = new DefaultMessageWidget("取消用户角色成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("取消用户角色失败");
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
		FragmentBeanRegister reg=new FragmentBeanRegister();
		reg.add("allocateRoleSearchUser", UserBean.class);
		reg.add("user", UserBean.class);
		reg.add("role", RoleBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "userAllocateRole";
	}

}

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
import com.credit.actions.beans.rbac.GroupBean;
import com.credit.actions.beans.rbac.UserBean;
import com.credit.rbac.bo.Group;
import com.credit.rbac.bo.User;
import com.credit.rbac.service.GroupService;
import com.credit.rbac.service.UserService;

/**
 * @author Hanlu
 *
 */
@Controller("userAllocateGroup")
public class UserAllocateGroupAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
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
			UserBean bean = (UserBean) req.getForm("allocateGroupSearchUser");
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
			FormWidget form = new DefaultFormWidget("allocateGroupSearchUser", bean);
			res.addForm(form);
			res.forward("pages/rbac/user/showAllocateGroup.html");
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
	public HtmlFragmentResponse showUserGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Group> groups;
		try {
			UserBean bean=(UserBean) req.getForm("user");
			groups=groupService.getGroups(bean.getUser());
			if(bean.getUser()!=null){
				FormWidget form=new DefaultFormWidget("groupUser", bean);
				res.addForm(form);
				req.getHttpServletRequest().getSession().setAttribute("userid", bean.getUser().getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<GroupBean> gbs = new ArrayList<GroupBean>();
		if (groups != null) {
			for (Group group : groups) {
				GroupBean rb = new GroupBean();
				rb.setGroup(group);
				gbs.add(rb);
			}
		}
		TableWidget table = new DefaultTableWidget("userGroups", gbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/rbac/user/showUserGroup.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showAddGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Group> groups;
		List<Group> oldGroups;
		try {
			groups=groupService.getGroups();
			UserBean bean=(UserBean) req.getForm("user");
			oldGroups=groupService.getGroups(bean.getUser());
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<GroupBean> parent = new ArrayList<GroupBean>();
		List<GroupBean> child = new ArrayList<GroupBean>();
		for (Group group : groups) {
			GroupBean rb = new GroupBean();
			rb.setGroup(group);
			parent.add(rb);
		}
		if(oldGroups!=null){
			for(Group group:oldGroups){
				GroupBean rb = new GroupBean();
				rb.setGroup(group);
				child.add(rb);
			}
		}
		TreeWidget tree=new CheckTreeWidget("userGroupTree","用户组树");
		tree.reload(parent, child);
		res.addCheckTree(tree);
		res.forward("pages/rbac/user/showAddGroup.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String userid=(String) req.getHttpServletRequest().getSession().getAttribute("userid");
		try {
			List<String> ids=req.getIDS("userGroups");
			MessageWidget message = null;
			boolean isSuccess = userService.allocateGroup(ids,userid);
			if (isSuccess) {
				message = new DefaultMessageWidget("分配组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("分配组失败");
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
	public HtmlFragmentResponse disallocateGroup(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String userid=(String) req.getHttpServletRequest().getSession().getAttribute("userid");
		try {
			List<String> ids = req.getIDS();
			if(ids==null){
				GroupBean bean=(GroupBean) req.getForm("group");
				if(bean!=null&&bean.getGroup()!=null){
					ids=new ArrayList<String>();
					ids.add(bean.getGroup().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = userService.disallocateGroup(ids,userid);
			if (isSuccess) {
				message = new DefaultMessageWidget("取消用户组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("取消用户组失败");
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
		reg.add("allocateGroupSearchUser", UserBean.class);
		reg.add("user", UserBean.class);
		reg.add("group", GroupBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "userAllocateGroup";
	}

}

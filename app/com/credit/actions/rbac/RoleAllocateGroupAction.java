/**
 * 
 */
package com.credit.actions.rbac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.credit.actions.beans.rbac.RoleBean;
import com.credit.rbac.bo.Group;
import com.credit.rbac.bo.Role;
import com.credit.rbac.service.GroupService;
import com.credit.rbac.service.RoleService;

/**
 * @author Hanlu
 *
 */
@Controller("roleAllocateGroup")
public class RoleAllocateGroupAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private RoleService roleService;
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
			res = this.searchRole(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException 
	 */
	public HtmlFragmentResponse searchRole(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			RoleBean bean= (RoleBean) request.getForm("allocateGroupSearchRole");
			Role r=bean.getRole();
			if(r==null){
				r=new Role();
			}
			List<Role> roles = roleService.getRoles(r,bean.getOffset(),bean.getLimit());
			List<RoleBean> rbs = new ArrayList<RoleBean>();
			if (roles != null) {
				for (Role role : roles) {
					RoleBean rb = new RoleBean();
					rb.setRole(role);
					rbs.add(rb);
				}
			}
			int allCount=roleService.allCount(r);
			TableWidget table = new DefaultTableWidget("roles", rbs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("allocateGroupSearchRole", bean);
			res.addForm(form);
			res.forward("pages/rbac/role/showAllocateGroup.html");
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
	public HtmlFragmentResponse showRoleGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Group> groups;
		try {
			RoleBean bean=(RoleBean) req.getForm("role");
			groups=groupService.getGroups(bean.getRole());
			if(bean.getRole()!=null){
				FormWidget form=new DefaultFormWidget("groupRole", bean);
				res.addForm(form);
				req.getHttpServletRequest().getSession().setAttribute("roleid", bean.getRole().getId());
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
		TableWidget table = new DefaultTableWidget("roleGroups", gbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/rbac/role/showRoleGroup.html");
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
			RoleBean bean=(RoleBean) req.getForm("role");
			oldGroups=groupService.getGroups(bean.getRole());
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<GroupBean> gbs = new ArrayList<GroupBean>();
		Map<String,GroupBean> gs=new HashMap<String, GroupBean>(groups.size());
		for (Group group : groups) {
			GroupBean rb = new GroupBean();
			rb.setGroup(group);
			gs.put(group.getId(), rb);
//			gbs.add(rb);
		}
		if(oldGroups!=null){
			for(Group group:oldGroups){
				gs.remove(group.getId());
			}
		}
		gbs.addAll(gs.values());
		TreeWidget tree=new CheckTreeWidget("roleGroupTree",gbs,"角色组树");
		res.addCheckTree(tree);
		res.forward("pages/rbac/role/showAddGroup.html");
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
		String roleid=(String) req.getHttpServletRequest().getSession().getAttribute("roleid");
		try {
			List<String> ids=req.getIDS("roleGroups");
			MessageWidget message = null;
			boolean isSuccess = roleService.allocateGroup(ids,roleid);
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
		String roleid=(String) req.getHttpServletRequest().getSession().getAttribute("roleid");
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
			boolean isSuccess = roleService.disallocateGroup(ids,roleid);
			if (isSuccess) {
				message = new DefaultMessageWidget("取消角色组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("取消角色组失败");
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
		reg.add("group", GroupBean.class);
		reg.add("role", RoleBean.class);
		reg.add("allocateGroupSearchRole", RoleBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "roleAllocateGroup";
	}

}

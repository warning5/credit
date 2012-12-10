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
import com.credit.actions.beans.rbac.RoleBean;
import com.credit.rbac.bo.Role;
import com.credit.rbac.service.RoleService;

/**
 * @author Hanlu
 *
 */
@Controller("roleManager")
public class RoleManagerAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
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
			res = this.searchRole(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
//		res.forward("pages/rbac/roleManager.html");
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
			RoleBean bean= (RoleBean) request.getForm("searchRole");
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
			FormWidget form = new DefaultFormWidget("searchRole", bean);
			res.addForm(form);
			res.forward("pages/rbac/role/roleManager.html");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws HtmlFragmentException
	 */
	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		res.forward("pages/rbac/role/addRole.html");
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
			RoleBean bean = (RoleBean) request.getForm("role");
			if (bean.getRole() == null) {
				MessageWidget message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			Role role = roleService.getRoleByID(bean.getRole().getId());
			if (role != null) {
				bean.setRole(role);
				FormWidget form = new DefaultFormWidget("role", bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget("role", new RoleBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/rbac/role/editRole.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editRole(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message=null;
		try {
				RoleBean bean = (RoleBean) req.getForm("role");
				boolean isSuccess = roleService.updateRole(bean.getRole());
				if (isSuccess) {
					message = new DefaultMessageWidget("修改角色成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("修改角色失败");
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
	public HtmlFragmentResponse deleteRole(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
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
			boolean isSuccess = roleService.deleteRoles(ids);
			if (isSuccess) {
				message = new DefaultMessageWidget("删除角色成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("删除角色失败");
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
	public HtmlFragmentResponse addRole(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			RoleBean bean = (RoleBean) req.getForm("role");
			MessageWidget message = null;
			boolean isSuccess = roleService.saveRole(bean.getRole());
			if (isSuccess) {
				message = new DefaultMessageWidget("添加角色成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("添加角色失败");
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
	 * 执行禁用操作
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse disableRole(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableRole(req, res,"禁用角色成功","禁用角色失败",true);
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
	private void doDisableRole(HtmlFragmentRequest req, HtmlFragmentResponse res, String successInfo, String failInfo, boolean disable) throws FragmentEventException {
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
			boolean isSuccess = roleService.disableRoles(ids,disable);
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
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse enableRole(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableRole(req, res,  "解禁角色成功", "解禁角色失败", false);
		return res;
	}
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg=new FragmentBeanRegister();
		reg.add("role", RoleBean.class);
		reg.add("searchRole", RoleBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "roleManager";
	}

}

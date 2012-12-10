/**
 * 
 */
package com.credit.actions.rbac;

import java.util.ArrayList;
import java.util.Collection;
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
import com.credit.actions.beans.rbac.FunctionBean;
import com.credit.actions.beans.rbac.RoleBean;
import com.credit.rbac.bo.Function;
import com.credit.rbac.bo.Role;
import com.credit.rbac.service.FunctionService;
import com.credit.rbac.service.RoleService;

/**
 * @author Hanlu
 * 
 */
@Controller("roleAllocateFunction")
public class RoleAllocateFunctionAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionServices;

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
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
			RoleBean bean = (RoleBean) request.getForm("allocateFunctionSearchRole");
			Role r = bean.getRole();
			if (r == null) {
				r = new Role();
			}
			List<Role> roles = roleService.getRoles(r, bean.getOffset(), bean.getLimit());
			List<RoleBean> rbs = new ArrayList<RoleBean>();
			if (roles != null) {
				for (Role role : roles) {
					RoleBean rb = new RoleBean();
					rb.setRole(role);
					rbs.add(rb);
				}
			}
			int allCount = roleService.allCount(r);
			TableWidget table = new DefaultTableWidget("roles", rbs, new PagingBean(bean.getPageNum(),
					bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("allocateFunctionSearchRole", bean);
			res.addForm(form);
			res.forward("pages/rbac/role/showAllocateFunction.html");
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
	public HtmlFragmentResponse showRoleFunction(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		Collection<Function> funcations = null;
		try {
			RoleBean bean = (RoleBean) req.getForm("role");
			Map<String, Function> funcs = functionServices.getFunctions(bean.getRole());
			if (funcs != null) {
				bean.getRole().setFunctions(null);
				FormWidget form = new DefaultFormWidget("functionRole", bean);
				res.addForm(form);
				funcations = funcs.values();
			}
			req.getHttpServletRequest().getSession().setAttribute("roleid", bean.getRole().getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<FunctionBean> fbs = new ArrayList<FunctionBean>();
		if (funcations != null && !funcations.isEmpty()) {
			for (Function func : funcations) {
				FunctionBean fb = new FunctionBean();
				fb.setFunction(func);
				fbs.add(fb);
			}
		}
		TreeWidget tree = new CheckTreeWidget("roleFunctionTree", fbs, "功能树");
		res.addCheckTree(tree);
		res.forward("pages/rbac/role/showRoleFunction.html");
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showAddFunction(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		Collection<Function> oldFunctions = null;
		List<Function> functions;
		try {
			functions = functionServices.getFunctions();
			RoleBean bean = (RoleBean) req.getForm("role");
			Map<String, Function> funcs = functionServices.getFunctions(bean.getRole());
			if (funcs != null) {
				oldFunctions = funcs.values();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		List<FunctionBean> fbs = new ArrayList<FunctionBean>(functions.size());
		for (Function func : functions) {
			FunctionBean fb = new FunctionBean();
			fb.setFunction(func);
			fbs.add(fb);
		}
		List<FunctionBean> ofbs = null;
		if (oldFunctions != null && !oldFunctions.isEmpty()) {
			ofbs = new ArrayList<FunctionBean>(oldFunctions.size());
			for (Function func : oldFunctions) {
				FunctionBean fb = new FunctionBean();
				fb.setFunction(func);
				ofbs.add(fb);
			}
		}
		TreeWidget tree = new CheckTreeWidget("addFunctionTree", "功能树");
		tree.reload(fbs, ofbs);
		res.addCheckTree(tree);
		res.forward("pages/rbac/role/showAddFunction.html");
		return res;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse addFunction(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String roleid = (String) req.getHttpServletRequest().getSession().getAttribute("roleid");
		try {
			List<String> ids = req.getIDS("roleFunctions");
			Role role=new Role();
			role.setId(roleid);
			Map<String, Function> funcs = functionServices.getFunctions(role);
			if (funcs != null) {
				for(Map.Entry<String, Function> fun:funcs.entrySet()){
					ids.remove(fun.getValue().getId());
				}
			}
			DefaultMessageWidget message = null;
			boolean isSuccess = roleService.allocateFunction(ids, roleid);
			if (isSuccess) {
				message = new DefaultMessageWidget("分配功能成功");
				message.setCallbackType(DefaultMessageWidget.CALLBACKTYPE_CLOSECURRENT);
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("分配功能失败");
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
	public HtmlFragmentResponse disallocateFunction(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		String roleid = (String) req.getHttpServletRequest().getSession().getAttribute("roleid");
		try {
			List<String> ids = req.getIDS();
			MessageWidget message = null;
			if (ids == null) {
				message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
			} else {
				boolean isSuccess = roleService.disallocateFunction(ids, roleid);
				if (isSuccess) {
					message = new DefaultMessageWidget("取消角色功能成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("取消角色功能失败");
					message.fail();
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("role", RoleBean.class);
		reg.add("allocateFunctionSearchRole", RoleBean.class);
		return reg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "roleAllocateFunction";
	}

}

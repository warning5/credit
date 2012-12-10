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
import com.credit.actions.beans.rbac.GroupBean;
import com.credit.rbac.bo.Group;
import com.credit.rbac.service.GroupService;
import com.credit.util.MessageUtils;

/**
 * @author Hanlu
 *
 */
@Controller("groupManager")
public class GroupManagerAction extends FragmentAction {

	private Log log = LogFactory.getLog(getClass());
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
			res = this.searchGroup(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
//		res.forward("pages/rbac/groupManager.html");
		return res;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * @throws FragmentEventException 
	 */
	public HtmlFragmentResponse searchGroup(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			GroupBean bean= (GroupBean) request.getForm("searchGroup");
			Group g=bean.getGroup();
			if(g==null){
				g=new Group();
			}
			List<Group> groups = groupService.getGroups(g,bean.getOffset(),bean.getLimit());
			List<GroupBean> rbs = new ArrayList<GroupBean>();
			if (groups != null) {
				for (Group group : groups) {
					GroupBean rb = new GroupBean();
					rb.setGroup(group);
					rbs.add(rb);
				}
			}
			int allCount=groupService.allCount(g);
			TableWidget table = new DefaultTableWidget("groups", rbs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("searchGroup", bean);
			res.addForm(form);
			res.forward("pages/rbac/group/groupManager.html");
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
		res.forward("pages/rbac/group/addGroup.html");
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
			GroupBean bean = (GroupBean) request.getForm("group");
			if (bean.getGroup() == null) {
				MessageWidget message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			Group group = groupService.getGroupByID(bean.getGroup().getId());
			if (group != null) {
				bean.setGroup(group);
				FormWidget form = new DefaultFormWidget("group", bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget("group", new GroupBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/rbac/group/editGroup.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editGroup(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message=null;
		try {
				GroupBean bean = (GroupBean) req.getForm("group");
				boolean isSuccess = groupService.updateGroup(bean.getGroup());
				if (isSuccess) {
					message = new DefaultMessageWidget("修改组成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("修改组失败");
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
	public HtmlFragmentResponse deleteGroup(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
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
			boolean isSuccess = groupService.deleteGroups(ids);
			if (isSuccess) {
				message = new DefaultMessageWidget("删除组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("删除组失败");
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
	public HtmlFragmentResponse addGroup(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			GroupBean bean = (GroupBean) req.getForm("group");
			boolean isSuccess = groupService.saveGroup(bean.getGroup());
			if (isSuccess) {
				message = MessageUtils.build("添加组成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("添加组失败", null, MessageUtils.ERROR, false);
			}
			res.addMessage(message);
		} catch (Exception e) {
			message = MessageUtils.build("添加组失败", null, MessageUtils.ERROR, false);
			log.error(e.getMessage(),e);
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
	public HtmlFragmentResponse disableGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableGroup(req, res,"禁用组成功","禁用组失败",true);
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
	private void doDisableGroup(HtmlFragmentRequest req, HtmlFragmentResponse res, String successInfo, String failInfo, boolean disable) throws FragmentEventException {
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
			boolean isSuccess = groupService.disableGroups(ids,disable);
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
	public HtmlFragmentResponse enableGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		this.doDisableGroup(req, res,  "解禁组成功", "解禁组失败", false);
		return res;
	}
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg=new FragmentBeanRegister();
		reg.add("group", GroupBean.class);
		reg.add("searchGroup", GroupBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "groupManager";
	}

}

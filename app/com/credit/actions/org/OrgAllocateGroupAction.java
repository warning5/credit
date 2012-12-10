/**
 * 
 */
package com.credit.actions.org;

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
import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.TableWidget;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.CheckTreeWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultTableWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.org.CompanyBean;
import com.credit.actions.beans.rbac.GroupBean;
import com.credit.org.bo.Company;
import com.credit.org.service.OrganizationService;
import com.credit.rbac.bo.Group;
import com.credit.rbac.service.GroupService;

/**
 * @author Hanlu
 *
 */
@Controller("orgAllocateGroup")
public class OrgAllocateGroupAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private GroupService groupService;
	/**
	 * 
	 */
	public OrgAllocateGroupAction() {
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			res = this.searchOrg(request);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);;
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
	public HtmlFragmentResponse searchOrg(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			CompanyBean bean= (CompanyBean) request.getForm("allocateGroupSearchOrg");
			Company c=bean.getCompany();
			if(c==null){
				c=new Company();
				bean.setCompany(c);
			}
			List<Company> cs = orgService.getCompanies(c,bean.getOffset(),bean.getLimit());
			List<CompanyBean> cbs = new ArrayList<CompanyBean>();
			Map<String, Company> cks=orgService.getCompanyKinds();
			List<CompanyBean> companyKinds=new ArrayList<CompanyBean>(cks.size());
			if (cs != null) {
				for (Company company : cs) {
					CompanyBean cb = new CompanyBean();
					cb.setCompany(company);
					cb.setParentName(cks.get(company.getParent()).getName());
					cbs.add(cb);
				}
			}
			int allCount=orgService.allCount(c);
			TableWidget table = new DefaultTableWidget("companies", cbs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("allocateGroupSearchOrg", bean);
			res.addForm(form);
			for(Company ck:cks.values()){
				CompanyBean ckb=new CompanyBean();
				ckb.setCompany(ck);
				companyKinds.add(ckb);
			}
			SelectWidget select=new DefaultSelectWidget("company_parent", companyKinds);
			res.addSelect(select);
			res.forward("pages/org/showAllocateGroup.html");
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
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
	public HtmlFragmentResponse showOrgGroup(HtmlFragmentRequest req)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Group> groups;
		try {
			CompanyBean bean=(CompanyBean) req.getForm("company");
			groups=groupService.getGroups(bean.getCompany());
			if(bean.getCompany()!=null){
				FormWidget form=new DefaultFormWidget("groupCompany", bean);
				res.addForm(form);
				req.getHttpServletRequest().getSession().setAttribute("companyid", bean.getCompany().getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
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
		TableWidget table = new DefaultTableWidget("companyGroups", gbs,new PagingBean());
		res.addTable(table);
		res.forward("pages/org/showOrgGroup.html");
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
			CompanyBean bean=(CompanyBean) req.getForm("company");
			oldGroups=groupService.getGroups(bean.getCompany());
		}catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		List<GroupBean> gbs = new ArrayList<GroupBean>();
		Map<String,GroupBean> gs=new HashMap<String, GroupBean>(groups.size());
		for (Group group : groups) {
			GroupBean rb = new GroupBean();
			rb.setGroup(group);
			gs.put(group.getId(), rb);
		}
		if(oldGroups!=null){
			for(Group group:oldGroups){
				gs.remove(group.getId());
			}
		}
		gbs.addAll(gs.values());
		TreeWidget tree=new CheckTreeWidget("companyGroupTree",gbs,"机构组树");
		res.addCheckTree(tree);
		res.forward("pages/org/showAddGroup.html");
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
		String companyid=(String) req.getHttpServletRequest().getSession().getAttribute("companyid");
		try {
			List<String> ids=req.getIDS("companyGroups");
			MessageWidget message = null;
			boolean isSuccess = orgService.allocateGroup(ids,companyid);
			if (isSuccess) {
				message = new DefaultMessageWidget("分配组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("分配组失败");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
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
		String companyid=(String) req.getHttpServletRequest().getSession().getAttribute("companyid");
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
			boolean isSuccess = orgService.disallocateGroup(ids,companyid);
			if (isSuccess) {
				message = new DefaultMessageWidget("取消机构组成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("取消机构组失败");
				message.fail();
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
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
		reg.add("allocateGroupSearchOrg", CompanyBean.class);
		reg.add("company", CompanyBean.class);
		reg.add("group", GroupBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "orgAllocateGroup";
	}

}

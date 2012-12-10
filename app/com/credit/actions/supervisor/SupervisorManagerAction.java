/**
 * 
 */
package com.credit.actions.supervisor;

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
import com.credit.org.bo.Company;
import com.credit.service.rate.SupervisorService;

/**
 * @author Hanlu
 *
 */
@Controller("supervisorManager")
public class SupervisorManagerAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private SupervisorService supService;
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			res = this.searchSupervisor(request);
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
	public HtmlFragmentResponse searchSupervisor(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			SupervisorBean bean= (SupervisorBean) request.getForm("searchSupervisor");
			Company c=bean.getSupervisor();
			if(c==null){
				c=new Company();
				bean.setSupervisor(c);
			}
			List<Company> cs = supService.getCompanies(c,bean.getOffset(),bean.getLimit());
			List<SupervisorBean> cbs = new ArrayList<SupervisorBean>();
			if (cs != null) {
				for (Company company : cs) {
					SupervisorBean cb = new SupervisorBean();
					cb.setSupervisor(company);
					cbs.add(cb);
				}
			}
			int allCount=supService.allCount(c);
			TableWidget table = new DefaultTableWidget("supervisors", cbs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("searchSupervisor", bean);
			res.addForm(form);
			res.forward("pages/supervisor/supervisorManager.html");
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
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		res.forward("pages/supervisor/addSupervisor.html");
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
			SupervisorBean bean = (SupervisorBean) request.getForm("supervisor");
			if (bean.getSupervisor() == null) {
				MessageWidget message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			Company supervisor = supService.getSupervisorByID(bean.getSupervisor().getId());
			if (supervisor != null) {
				bean.setSupervisor(supervisor);
				FormWidget form = new DefaultFormWidget("supervisor", bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget("supervisor", new SupervisorBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/supervisor/editSupervisor.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editSupervisor(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message=null;
		try {
				SupervisorBean bean = (SupervisorBean) req.getForm("supervisor");
				boolean isSuccess = supService.updateSupervisor(bean.getSupervisor());
				if (isSuccess) {
					message = new DefaultMessageWidget("修改监管机构成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("修改监管机构失败");
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
	public HtmlFragmentResponse deleteSupervisor(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			List<String> ids = req.getIDS();
			if(ids==null){
				SupervisorBean bean=(SupervisorBean) req.getForm("supervisor");
				if(bean!=null&&bean.getSupervisor()!=null){
					ids=new ArrayList<String>();
					ids.add(bean.getSupervisor().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = supService.deleteSupervisors(ids);
			if (isSuccess) {
				message = new DefaultMessageWidget("删除监管机构成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("删除监管机构失败");
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
	public HtmlFragmentResponse addSupervisor(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			SupervisorBean bean = (SupervisorBean) req.getForm("supervisor");
			MessageWidget message = null;
			boolean isSuccess = supService.saveSupervisor(bean.getSupervisor());
			if (isSuccess) {
				message = new DefaultMessageWidget("添加监管机构成功");
				message.setNavTabId(getName());
			} else {
				message = new DefaultMessageWidget("添加监管机构失败");
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
		reg.add("searchSupervisor", SupervisorBean.class);
		reg.add("supervisor", SupervisorBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "supervisorManager";
	}

}

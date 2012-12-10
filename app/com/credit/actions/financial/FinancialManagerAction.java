/**
 * 
 */
package com.credit.actions.financial;

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
import com.credit.service.rate.FinancialService;

/**
 * @author Hanlu
 *
 */
@Controller("financialManager")
public class FinancialManagerAction extends FragmentAction {
	Log log =LogFactory.getLog(FinancialManagerAction.class);
	@Autowired
	private FinancialService finService;
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res = null;
		try {
			res = this.searchFinancial(request);
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
	public HtmlFragmentResponse searchFinancial(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			FinancialBean bean= (FinancialBean) request.getForm("searchFinancial");
			Company c=bean.getFinancial();
			if(c==null){
				c=new Company();
				bean.setFinancial(c);
			}
			List<Company> cs = finService.getCompanies(c,bean.getOffset(),bean.getLimit());
			List<FinancialBean> cbs = new ArrayList<FinancialBean>();
			if (cs != null) {
				for (Company company : cs) {
					FinancialBean cb = new FinancialBean();
					cb.setFinancial(company);
					cbs.add(cb);
				}
			}
			int allCount=finService.allCount(c);
			TableWidget table = new DefaultTableWidget("financials", cbs,
					new PagingBean(bean.getPageNum(), bean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget("searchFinancial", bean);
			res.addForm(form);
			res.forward("pages/financial/financialManager.html");
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
		res.forward("pages/financial/addFinancial.html");
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
			FinancialBean bean = (FinancialBean) request.getForm("financial");
			if (bean.getFinancial() == null) {
				MessageWidget message = new DefaultMessageWidget("请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			Company financial = finService.getFinancialByID(bean.getFinancial().getId());
			if (financial != null) {
				bean.setFinancial(financial);
				FormWidget form = new DefaultFormWidget("financial", bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget("financial", new FinancialBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/financial/editFinancial.html");
		return res;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws FragmentEventException
	 */
	public HtmlFragmentResponse editFinancial(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message=null;
		try {
				FinancialBean bean = (FinancialBean) req.getForm("financial");
				boolean isSuccess = finService.updateFinancial(bean.getFinancial());
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
	public HtmlFragmentResponse deleteFinancial(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			List<String> ids = req.getIDS();
			if(ids==null){
				FinancialBean bean=(FinancialBean) req.getForm("financial");
				if(bean!=null&&bean.getFinancial()!=null){
					ids=new ArrayList<String>();
					ids.add(bean.getFinancial().getId());
				}
			}
			MessageWidget message = null;
			boolean isSuccess = finService.deleteFinancials(ids);
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
	public HtmlFragmentResponse addFinancial(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			FinancialBean bean = (FinancialBean) req.getForm("financial");
			MessageWidget message = null;
			boolean isSuccess = finService.saveFinancial(bean.getFinancial());
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
		reg.add("searchFinancial", FinancialBean.class);
		reg.add("financial", FinancialBean.class);
		return reg;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "financialManager";
	}

}

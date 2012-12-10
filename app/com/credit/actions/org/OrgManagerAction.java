/**
 * 
 */
package com.credit.actions.org;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.TreeWidget;
import com.bluecloud.mvc.external.widgets.impl.OrgTreeWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.actions.beans.org.CompanyBean;
import com.credit.org.bo.Company;
import com.credit.org.exception.OrganizationServiceException;
import com.credit.org.service.OrganizationService;

/**
 * @author Hanlu
 *
 */
@Controller("orgManager")
public class OrgManagerAction extends FragmentAction {
	private Log log =LogFactory.getLog(getClass());
	@Autowired
	private OrganizationService orgService;
	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.Action#execute(com.bluecloud.mvc.web.http.HtmlFragmentRequest)
	 */
	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request)
			throws HtmlFragmentException {
		HtmlFragmentResponse res =getResponse();
		try {
			List<Company> companies=orgService.getCompanies();
			if (companies != null) {
				List<CompanyBean> beans = new ArrayList<CompanyBean>(companies.size());
				for (Company company : companies) {
					CompanyBean bean = new CompanyBean();
					bean.setCompany(company);
					beans.add(bean);
				}
				TreeWidget tree= new OrgTreeWidget("orgtree", beans);
				res.addTree(tree);
				res.forward("pages/org/orgManager.html");
			}
		}catch (OrganizationServiceException e) {
			log.error(e.getMessage(), e);;
			throw new HtmlFragmentException(e);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#regBean()
	 */
	@Override
	protected FragmentBeanRegister regBean() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.external.FragmentAction#getName()
	 */
	@Override
	public String getName() {
		return "orgManager";
	}

}

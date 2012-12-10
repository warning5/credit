/**
 * 
 */
package com.credit.actions.firm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
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
import com.credit.actions.rate.RatingBizFormBean;
import com.credit.base.Constants;
import com.credit.model.rate.Firm;
import com.credit.model.rate.ListFirm;
import com.credit.service.rate.DicService;
import com.credit.service.rate.FirmService;
import com.credit.util.MessageUtils;

@Controller(Constants.FIRM_ACTION_NAME)
public class FirmAction extends FragmentAction {

	private Log log = LogFactory.getLog(getClass());

	public static final String PAGE_ITEM = "firm";
	public static final String PAGE_ID = "firmId";

	@Autowired
	private FirmService firmService;
	@Autowired
	private DicService dicService;

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add("searchForm", SearchFirmFormBean.class);
		reg.add("firm", FirmFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchFirm(request,"firms");
		} catch (HtmlFragmentRequestException e) {
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/firm/listfirm.html");
		return res;
	}

	public HtmlFragmentResponse lookup(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchFirm(request,"firmsLookup");
		} catch (HtmlFragmentRequestException e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/firm/firmLookup.html");
		return res;
	}
	public HtmlFragmentResponse searchFirm(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchFirm(request,"firmsLookup");
		} catch (HtmlFragmentRequestException e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/firm/searchfirm.html");
		return res;
	}

	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		request.getHttpServletRequest().setAttribute(Constants.INDUSTRYTYPE, dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		request.getHttpServletRequest().setAttribute("firmRegisType", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute("firmNature", dicService.getDicIndustryList());
		res.forward("pages/firm/createfirm.jsp");
		return res;
	}

	public HtmlFragmentResponse createFirm(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			FirmFormBean firmFormBean = (FirmFormBean) request.getForm("firm");
			if (firmService.saveFirm(firmFormBean.getFirm())) {
				message = MessageUtils.build("新增企业成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("新增企业失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("新增企业失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	protected HtmlFragmentResponse searchFirm(HtmlFragmentRequest req, String id) throws HtmlFragmentRequestException {
		HtmlFragmentResponse res = getResponse();
		SearchFirmFormBean formBean = (SearchFirmFormBean) req.getForm("searchForm");
		List<ListFirmFormBean> listBean = searchBeans(formBean);
		TableWidget table = new DefaultTableWidget(id, listBean, new PagingBean(formBean.getPageNum(),
				formBean.getNumPerPage(), listBean.size()));
		res.addTable(table);
		FormWidget form = new DefaultFormWidget("searchForm", formBean);
		res.addForm(form);
		return res;
	}

	private List<ListFirmFormBean> searchBeans(SearchFirmFormBean formBean) throws HtmlFragmentRequestException {

		ListFirm listFirm = formBean.getListFirm();
		if (listFirm == null) {
			listFirm = new ListFirm();
		}
		List<ListFirm> firms = firmService.getFirms(listFirm, formBean.getOffset(), formBean.getLimit());
		List<ListFirmFormBean> listBean = new ArrayList<ListFirmFormBean>(firms.size());
		if (firms != null) {
			for (int i = 0, size = firms.size(); i < size; i++) {
				ListFirmFormBean bean = new ListFirmFormBean(i + 1);
				bean.setFirmCnName(firms.get(i).getFirmCnName());
				bean.setFirmId(firms.get(i).getFirmId());
				bean.setFirmNature(dicService.getIndustryMap().get(firms.get(i).getFirmNature().toString()));
				bean.setFirmRagisDate(firms.get(i).getFirmRagisDate());
				bean.setFirmRegion(dicService.getRegion(firms.get(i).getFirmMunicpPrv().toString(), firms.get(i)
						.getFirmMunicpCity().toString(), firms.get(i).getFirmMunicpDistr().toString()));
				listBean.add(bean);
			}
		}
		return listBean;
	}

	public HtmlFragmentResponse showAdvanceSearch(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		request.getHttpServletRequest().setAttribute("firmNature", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		res.forward("pages/firm/advancesearch.jsp");
		return res;
	}

	public HtmlFragmentResponse deleteFirms(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			MessageWidget message = null;
			List<String> ids = req.getIDS();
			if (ids == null) {
				String rateId = req.getHttpServletRequest().getParameter(PAGE_ID);
				if (StringUtils.isNotEmpty(rateId)) {
					ids = new ArrayList<String>(1);
					ids.add(rateId);
				}
			}
			if (ids == null) {
				message = new DefaultMessageWidget("请选择数据");
				message.fail();
			} else {
				boolean isSuccess = firmService.deleteFirms(ids);
				if (isSuccess) {
					message = new DefaultMessageWidget("删除企业成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("删除企业失败");
					message.fail();
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);;
			throw new FragmentEventException(e);
		}
		return res;
	}

	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String firmId = request.getHttpServletRequest().getParameter(PAGE_ID);
			if (StringUtils.isEmpty(firmId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			Firm firm = firmService.getFirm(firmId);
			if (firm != null) {
				FirmFormBean bean = new FirmFormBean();
				bean.setFirm(firm);
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, bean);
				res.addForm(form);
				res.addSelect(dicService.getProvinceSelectWidget("firm_firmMunicpPrv"));
				res.addSelect(dicService.getCitySelectWidget("firm_firmMunicpCity", firm.getFirmMunicpPrv().toString()));
				res.addSelect(dicService.getAreaSelectWidget("firm_firmMunicpDistr", firm.getFirmMunicpCity()
						.toString()));
				res.addSelect(dicService.getIndustryTypeSelectWidget("firm_firmIndustryType"));
				res.addSelect(dicService.getIndustryTypeSelectWidget("firm_firmRegisType"));
				res.addSelect(dicService.getIndustryTypeSelectWidget("firm_firmNature"));
			} else {
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, new RatingBizFormBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/firm/editfirm.html");
		return res;
	}

	public HtmlFragmentResponse editFirm(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		try {
			FirmFormBean bean = (FirmFormBean) req.getForm("firm");
			if (firmService.updateFirm(bean.getFirm())) {
				message = MessageUtils.build("修改企业成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("修改企业失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("修改企业失败", null, MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	@Override
	public String getName() {
		return Constants.FIRM_ACTION_NAME;
	}
}

/**
 * 
 */
package com.credit.actions.agc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
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
import com.credit.base.Constants;
import com.credit.model.rate.AgcBase;
import com.credit.model.rate.ListAgcBase;
import com.credit.service.rate.AgcService;
import com.credit.service.rate.DicService;
import com.credit.util.MessageUtils;

@Controller(Constants.AGC_ACTION_NAME)
public class AgcAction extends AbstractAgcAction {

	public static final String PAGE_ITEM = "agc";
	public static final String PAGE_ID = "agcId";

	@Autowired
	private AgcService agcService;
	@Autowired
	private DicService dicService;

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchAgcBaseFormBean.class);
		reg.add(PAGE_ITEM, AgcBaseFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchAgc(request,"agcs");
		} catch (HtmlFragmentRequestException e) {
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/agc/listagc.html");
		return res;
	}

	public HtmlFragmentResponse maintainOwnInfo(HtmlFragmentRequest request) throws FragmentEventException {

		HtmlFragmentResponse res = this.getResponse();
		res = getAgcResponse(validateService.validateAgcUser(request, PAGE_ITEM, true), res);
		res.forward("pages/agc/maintain.jsp");
		return res;
	}

	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		request.getHttpServletRequest().setAttribute("agcNature", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		request.getHttpServletRequest().setAttribute("agcRegisType", dicService.getDicIndustryList());
		res.forward("pages/agc/createagency.jsp");
		return res;
	}

	public HtmlFragmentResponse lookup(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchAgc(request,"agcsLookup");
		} catch (HtmlFragmentRequestException e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/agc/agcLookup.html");
		return res;
	}

	public HtmlFragmentResponse createAgc(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			AgcBaseFormBean agcBaseFormBean = (AgcBaseFormBean) request.getForm(PAGE_ITEM);
			if (agcService.saveAgcBase(agcBaseFormBean.getAgc())) {
				message = MessageUtils.build("新增评级机构成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("新增评级机构失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			message = MessageUtils.build("新增评级机构失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse showAdvanceSearch(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		request.getHttpServletRequest().setAttribute("agcNature", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		res.forward("pages/agc/advancesearch.jsp");
		return res;
	}

	protected HtmlFragmentResponse searchAgc(HtmlFragmentRequest req, String id) throws HtmlFragmentRequestException {
		HtmlFragmentResponse res = getResponse();
		SearchAgcBaseFormBean formBean = (SearchAgcBaseFormBean) req.getForm(Constants.SEARCHFORM_NAME);
		List<ListAgcBaseFormBean> listBean = searchBeans(formBean);
		TableWidget table = new DefaultTableWidget(id, listBean, new PagingBean(formBean.getPageNum(),
				formBean.getNumPerPage(), listBean.size()));
		res.addTable(table);
		FormWidget form = new DefaultFormWidget(Constants.SEARCHFORM_NAME, formBean);
		res.addForm(form);
		return res;
	}

	private List<ListAgcBaseFormBean> searchBeans(SearchAgcBaseFormBean formBean) throws HtmlFragmentRequestException {

		ListAgcBase listAgcBase = formBean.getListAgcBase();
		if (listAgcBase == null) {
			listAgcBase = new ListAgcBase();
		}
		List<ListAgcBase> agcs = agcService.getAgcBases(listAgcBase, formBean.getOffset(), formBean.getLimit());
		List<ListAgcBaseFormBean> listBean = new ArrayList<ListAgcBaseFormBean>(agcs.size());
		if (agcs != null) {
			for (int i = 0, size = agcs.size(); i < size; i++) {
				ListAgcBaseFormBean bean = new ListAgcBaseFormBean(i + 1);
				bean.setAgcCnName(agcs.get(i).getAgcCnName());
				bean.setAgcId(agcs.get(i).getAgcId());
				bean.setAgcNature(dicService.getIndustryMap().get(agcs.get(i).getAgcNature().toString()));
				bean.setAgcRagisDate(agcs.get(i).getAgcRagisDate());
				bean.setAgcRegion(dicService.getRegion(agcs.get(i).getAgcMunicpPrv().toString(), agcs.get(i)
						.getAgcMunicpCity().toString(), agcs.get(i).getAgcMunicpDistr().toString()));
				listBean.add(bean);
			}
		}
		return listBean;
	}

	public HtmlFragmentResponse deleteAgcs(HtmlFragmentRequest req) throws FragmentEventException {
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
				boolean isSuccess = agcService.deleteAgcBases(ids);
				if (isSuccess) {
					message = new DefaultMessageWidget("删除评级机构成功");
					message.setNavTabId(getName());
				} else {
					message = new DefaultMessageWidget("删除评级机构失败");
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

	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String agcId = request.getHttpServletRequest().getParameter(PAGE_ID);
			if (StringUtils.isEmpty(agcId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			res = getAgcResponse(agcId, res);
		} catch (Exception e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/agc/editagc.html");
		return res;
	}

	private HtmlFragmentResponse getAgcResponse(String agcId, HtmlFragmentResponse res) {
		AgcBase agcBase = agcService.getAgcBase(agcId);
		if (agcBase != null) {
			AgcBaseFormBean bean = new AgcBaseFormBean();
			bean.setAgc(agcBase);
			FormWidget form = new DefaultFormWidget(PAGE_ITEM, bean);
			res.addForm(form);
			res.addSelect(dicService.getProvinceSelectWidget("agc_agcMunicpPrv"));
			res.addSelect(dicService.getCitySelectWidget("agc_agcMunicpCity", agcBase.getAgcMunicpPrv().toString()));
			res.addSelect(dicService.getAreaSelectWidget("agc_agcMunicpDistr", agcBase.getAgcMunicpCity().toString()));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agc_agcBizScope"));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agc_agcRegisType"));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agc_agcNature"));
		} else {
			FormWidget form = new DefaultFormWidget(PAGE_ITEM, new AgcBaseFormBean());
			res.addForm(form);
		}
		return res;
	}

	public HtmlFragmentResponse editAgc(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		try {
			AgcBaseFormBean bean = (AgcBaseFormBean) req.getForm(PAGE_ITEM);
			if (agcService.updateAgc(bean.getAgc())) {
				message = MessageUtils.build("修改评级机构成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("修改评级机构失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("修改评级机构失败", null, MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	@Override
	public String getName() {
		return Constants.AGC_ACTION_NAME;
	}
}

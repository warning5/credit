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
import com.bluecloud.mvc.external.widgets.impl.DefaultTableWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.model.rate.AgcProfessional;
import com.credit.model.rate.ListAgcProfessional;
import com.credit.service.rate.AgcProfessionalService;
import com.credit.util.MessageUtils;

@Controller(Constants.AGC_PRO_ACTION_NAME)
public class AgcProfessionalAction extends AbstractAgcAction {

	public static final String PAGE_ITEM = "agcProfessional";
	public static final String PAGE_ID = "agcProId";
	public static final String TABLE_ITEM = "agcProfessionals";

	@Autowired
	private AgcProfessionalService agcProfessionalService;

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchAgcProfessionalFormBean.class);
		reg.add(PAGE_ITEM, AgcProfessionalFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchAgcProfessionals(request);
		} catch (HtmlFragmentRequestException e) {
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/agc/listagcprofessional.jsp");
		return res;
	}

	public HtmlFragmentResponse lookup(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchAgcProfessionals(request);
		} catch (HtmlFragmentRequestException e) {
			throw new FragmentEventException(e);
		}
		res.forward("pages/agc/proLookup.html");
		return res;
	}

	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		validateService.validateAgcUser(request, PAGE_ITEM, true);
		request.getHttpServletRequest().setAttribute("agcProIDType", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute("agcProHighestEdu", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute("agcProCertiType", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute("agcProCertiStatus", dicService.getDicIndustryList());
		res.forward("pages/agc/createagcprofessional.jsp");
		return res;
	}

	public HtmlFragmentResponse createAgcProfessional(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			AgcProfessionalFormBean agcBaseFormBean = (AgcProfessionalFormBean) request.getForm(PAGE_ITEM);
			if (agcProfessionalService.saveAgcProfessional(agcBaseFormBean.getAgcProfessional())) {
				message = MessageUtils.build("新增评级师成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("新增评级师失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("新增评级师失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse showAdvanceSearch(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		request.getHttpServletRequest().setAttribute("agcNature", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		res.forward("pages/agc/advancesearchprofessional.jsp");
		return res;
	}

	protected HtmlFragmentResponse searchAgcProfessionals(HtmlFragmentRequest req) throws HtmlFragmentRequestException {
		HtmlFragmentResponse res = getResponse();
		SearchAgcProfessionalFormBean formBean = (SearchAgcProfessionalFormBean) req.getForm(Constants.SEARCHFORM_NAME);
		List<ListAgcProfessionalFormBean> listBean = searchBeans(req, formBean);
		ListAgcProfessional listAgcProfessional = formBean.getListAgcProfessional();
		if (listAgcProfessional == null) {
			listAgcProfessional = new ListAgcProfessional();
		}
		TableWidget table = new DefaultTableWidget(TABLE_ITEM, listBean, new PagingBean(formBean.getPageNum(),
				formBean.getNumPerPage(), agcProfessionalService.allCount(listAgcProfessional)));
		res.addTable(table);
		FormWidget form = new DefaultFormWidget(Constants.SEARCHFORM_NAME, formBean);
		res.addForm(form);
		return res;
	}

	private List<ListAgcProfessionalFormBean> searchBeans(HtmlFragmentRequest req,
			SearchAgcProfessionalFormBean formBean) throws HtmlFragmentRequestException {

		ListAgcProfessional listAgcProfessional = formBean.getListAgcProfessional();
		if (listAgcProfessional == null) {
			listAgcProfessional = new ListAgcProfessional();
		}
		listAgcProfessional.setAgcId(validateService.validateAgcUser(req, null, false));
		List<ListAgcProfessional> agcProfessionals = agcProfessionalService.getAgcProfessionals(listAgcProfessional,
				formBean.getOffset(), formBean.getLimit());
		List<ListAgcProfessionalFormBean> listBean = new ArrayList<ListAgcProfessionalFormBean>(agcProfessionals.size());
		if (agcProfessionals != null) {
			for (int i = 0, size = agcProfessionals.size(); i < size; i++) {
				ListAgcProfessionalFormBean bean = new ListAgcProfessionalFormBean(i + 1);
				bean.setAgcCnName(agcProfessionals.get(i).getAgcCnName());
				bean.setAgcProId(agcProfessionals.get(i).getAgcProId());
				bean.setAgcProName(agcProfessionals.get(i).getAgcProName());
				bean.setAgcId(agcProfessionals.get(i).getAgcId());
				bean.setAgcProCertiStatus(dicService.getProCertiStatusMap().get(
						agcProfessionals.get(i).getAgcProCertiStatus()));
				bean.setAgcProCertiType(dicService.getProCertiTypeMap().get(
						agcProfessionals.get(i).getAgcProCertiType()));
				bean.setAgcProIDNum(agcProfessionals.get(i).getAgcProIDNum());
				listBean.add(bean);
			}
		}
		return listBean;
	}

	public HtmlFragmentResponse deleteAgcFinacials(HtmlFragmentRequest req) throws FragmentEventException {
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
				message = MessageUtils.build("请选择数据", null, MessageUtils.ERROR, false);
			} else {
				boolean isSuccess = agcProfessionalService.deleteAgcProfessionals(ids);
				if (isSuccess) {
					message = MessageUtils.build("删除评级师成功", getName(), MessageUtils.SUCCESS, false);
				} else {
					message = MessageUtils.build("删除评级师成功", null, MessageUtils.ERROR, false);
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
			String agcProfessionalId = request.getHttpServletRequest().getParameter(PAGE_ID);
			if (StringUtils.isEmpty(agcProfessionalId)) {
				MessageWidget message = MessageUtils.build("请选择一条记录", null, MessageUtils.ERROR, false);
				res.addMessage(message);
				return res;
			}
			validateService.validateAgcUser(request, PAGE_ITEM, false);

			AgcProfessional agcProfessional = agcProfessionalService.getAgcProfessional(agcProfessionalId);
			if (agcProfessional != null) {
				AgcProfessionalFormBean bean = new AgcProfessionalFormBean();
				bean.setAgcProfessional(agcProfessional);
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, new AgcBaseFormBean());
				res.addForm(form);
			}
			res.addSelect(dicService.getIndustryTypeSelectWidget("agcProfessional_agcProIDType"));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agcProfessional_agcProHighestEdu"));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agcProfessional_agcProCertiType"));
			res.addSelect(dicService.getIndustryTypeSelectWidget("agcProfessional_agcProCertiStatus"));
			request.getHttpServletRequest().setAttribute("data", res.getData().toString());
			res.getData().clear();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/agc/editagcprofessional.jsp");
		return res;
	}

	public HtmlFragmentResponse editAgcProfessional(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		try {
			AgcProfessionalFormBean bean = (AgcProfessionalFormBean) req.getForm(PAGE_ITEM);

			if (agcProfessionalService.updateAgcProfessional(bean.getAgcProfessional())) {
				message = MessageUtils.build("修改评级师成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("修改评级师失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("修改评级师失败", null, MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	@Override
	public String getName() {
		return Constants.AGC_PRO_ACTION_NAME;
	}
}

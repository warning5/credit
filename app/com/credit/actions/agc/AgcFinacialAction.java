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
import com.credit.model.rate.AgcFinacial;
import com.credit.model.rate.ListAgcFinacial;
import com.credit.service.rate.AgcFinacialService;
import com.credit.util.MessageUtils;

@Controller(Constants.AGC_FINACIAL_ACTION_NAME)
public class AgcFinacialAction extends AbstractAgcAction {

	public static final String PAGE_ITEM = "agcFinacial";
	public static final String PAGE_ID = "agcFinaceId";
	public static final String TABLE_ITEM = "agcFinacials";

	@Autowired
	private AgcFinacialService agcFinacialService;

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchAgcFinacialFormBean.class);
		reg.add(PAGE_ITEM, AgcFinacialFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res = getResponse();
		try {
			res = searchAgcFinacial(request);
		} catch (HtmlFragmentRequestException e) {
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/agc/listagcfinacial.jsp");
		return res;
	}

	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		validateService.validateAgcUser(request, PAGE_ITEM, true);
		res.forward("pages/agc/createagcfinacial.jsp");
		return res;
	}

	public HtmlFragmentResponse createAgcFinacial(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			AgcFinacialFormBean agcBaseFormBean = (AgcFinacialFormBean) request.getForm(PAGE_ITEM);
			if (agcFinacialService.saveAgcFinacial(agcBaseFormBean.getAgcFinacial())) {
				message = MessageUtils.build("新增评级机构财务信息成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("新增评级机构财务信息失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("新增评级机构财务信息失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse showAdvanceSearch(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		request.getHttpServletRequest().setAttribute("agcNature", dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		res.forward("pages/agc/advancesearchagcfinacial.jsp");
		return res;
	}

	protected HtmlFragmentResponse searchAgcFinacial(HtmlFragmentRequest req) throws HtmlFragmentRequestException {
		HtmlFragmentResponse res = getResponse();
		SearchAgcFinacialFormBean formBean = (SearchAgcFinacialFormBean) req.getForm(Constants.SEARCHFORM_NAME);
		List<ListAgcFinacialFormBean> listBean = searchBeans(req, formBean);
		ListAgcFinacial listAgcFinacial = formBean.getListAgcFinacial();
		if (listAgcFinacial == null) {
			listAgcFinacial = new ListAgcFinacial();
		}
		TableWidget table = new DefaultTableWidget(TABLE_ITEM, listBean, new PagingBean(formBean.getPageNum(),
				formBean.getNumPerPage(), agcFinacialService.allCount(listAgcFinacial)));
		res.addTable(table);
		FormWidget form = new DefaultFormWidget(Constants.SEARCHFORM_NAME, formBean);
		res.addForm(form);
		return res;
	}

	private List<ListAgcFinacialFormBean> searchBeans(HtmlFragmentRequest req, SearchAgcFinacialFormBean formBean)
			throws HtmlFragmentRequestException {

		ListAgcFinacial listAgcFinacial = formBean.getListAgcFinacial();
		if (listAgcFinacial == null) {
			listAgcFinacial = new ListAgcFinacial();
		}
		listAgcFinacial.setAgcId(validateService.validateAgcUser(req, null, false));
		List<ListAgcFinacial> agcFinacials = agcFinacialService.getAgcFinacials(listAgcFinacial, formBean.getOffset(),
				formBean.getLimit());
		List<ListAgcFinacialFormBean> listBean = new ArrayList<ListAgcFinacialFormBean>(agcFinacials.size());
		if (agcFinacials != null) {
			for (int i = 0, size = agcFinacials.size(); i < size; i++) {
				ListAgcFinacialFormBean bean = new ListAgcFinacialFormBean(i + 1);
				bean.setAgcCnName(agcFinacials.get(i).getAgcCnName());
				bean.setAgcFinaceId(agcFinacials.get(i).getAgcFinaceId());
				bean.setAgcId(agcFinacials.get(i).getAgcId());
				bean.setAgcOrgId(agcFinacials.get(i).getAgcOrgId());
				bean.setAgcRatIncm(agcFinacials.get(i).getAgcRatIncm().toString());
				bean.setAgcRptYear(agcFinacials.get(i).getAgcRptYear());
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
				boolean isSuccess = agcFinacialService.deleteAgcFinacials(ids);
				if (isSuccess) {
					message = MessageUtils.build("删除评级机构财务信息成功", getName(), MessageUtils.SUCCESS, false);
				} else {
					message = MessageUtils.build("删除评级机构财务信息失败", null, MessageUtils.ERROR, false);
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
			String agcFinaceId = request.getHttpServletRequest().getParameter(PAGE_ID);
			if (StringUtils.isEmpty(agcFinaceId)) {
				MessageWidget message = MessageUtils.build("请选择一条记录", null, MessageUtils.ERROR, false);
				res.addMessage(message);
				return res;
			}

			validateService.validateAgcUser(request, PAGE_ITEM, false);

			AgcFinacial agcFinacial = agcFinacialService.getAgcFinacial(agcFinaceId);
			if (agcFinacial != null) {
				AgcFinacialFormBean bean = new AgcFinacialFormBean();
				bean.setAgcFinacial(agcFinacial);
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, bean);
				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget(PAGE_ITEM, new AgcBaseFormBean());
				res.addForm(form);
			}
			request.getHttpServletRequest().setAttribute("data", res.getData().toString());
			res.getData().clear();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/agc/editagcfinacial.jsp");
		return res;
	}

	public HtmlFragmentResponse editAgcFinacial(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		try {
			AgcFinacialFormBean bean = (AgcFinacialFormBean) req.getForm(PAGE_ITEM);

			if (agcFinacialService.updateAgcFinacial(bean.getAgcFinacial())) {
				message = MessageUtils.build("修改评级机构财务数据成功", getName(), MessageUtils.SUCCESS, true);
			} else {
				message = MessageUtils.build("修改评级机构财务数据失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("修改评级机构财务数据失败", null, MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	@Override
	public String getName() {
		return Constants.AGC_FINACIAL_ACTION_NAME;
	}
}

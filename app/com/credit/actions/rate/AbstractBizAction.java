package com.credit.actions.rate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.beans.PagingBean;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.TableWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultTableWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.model.rate.ListRatingBiz;
import com.credit.model.rate.ListRatingReportBiz;
import com.credit.service.rate.DicService;
import com.credit.service.rate.RatingBizService;
import com.credit.service.rate.RatingReportBizService;
import com.credit.service.rate.UploadService;
import com.credit.service.rate.ValidateService;
import com.credit.util.BaseConfig;

public abstract class AbstractBizAction extends FragmentAction {

	private Log log = LogFactory.getLog(getClass());
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String RATEBIZS = "rateBizs";

	@Autowired
	protected RatingBizService bizService;

	@Autowired
	protected RatingReportBizService reportBizService;

	@Autowired
	protected DicService dicService;

	@Autowired
	protected BaseConfig baseConfig;

	@Autowired
	UploadService uploadService;

	@Autowired
	ValidateService validateService;

	protected HtmlFragmentResponse searchRateBiz(HtmlFragmentRequest req, List<Integer> states)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			SearchRatingBizFormBean formBean = (SearchRatingBizFormBean) req.getForm(Constants.SEARCHFORM_NAME);
			List<ListRatingBizFormBean> listBean = searchFormBeans(req, formBean, states);
			int allCount = bizService.allCount(formBean.getBiz());
			TableWidget table = new DefaultTableWidget(RATEBIZS, listBean, new PagingBean(formBean.getPageNum(),
					formBean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget(Constants.SEARCHFORM_NAME, formBean);
			res.addForm(form);
		} catch (Exception e) {
			throw new FragmentEventException(e);
		}
		return res;
	}

	protected List<ListRatingBizFormBean> searchFormBeans(HtmlFragmentRequest req, SearchRatingBizFormBean formBean,
			List<Integer> states) {
		ListRatingBiz listBiz = formBean.getBiz();
		if (listBiz == null) {
			listBiz = new ListRatingBiz();
		}
		listBiz.setStates(states);
		listBiz.setAgcId(validateService.validateAgcUser(req, null, false));
		List<ListRatingBiz> bizs = bizService.getRatingBizs(listBiz, formBean.getOffset(), formBean.getLimit());
		List<ListRatingBizFormBean> listBean = new ArrayList<ListRatingBizFormBean>(bizs.size());
		if (bizs != null) {
			for (int i = 0, size = bizs.size(); i < size; i++) {
				ListRatingBizFormBean bean = new ListRatingBizFormBean(i + 1);
				bean.setRateId(bizs.get(i).getRateId());
				bean.setAgcId(bizs.get(i).getAgcId());
				bean.setFirmId(bizs.get(i).getFirmId());
				bean.setFirmCnName(bizs.get(i).getFirmCnName());
				bean.setFirmOrgId(bizs.get(i).getFirmOrgId());
				bean.setRateRegion(dicService.getRegion(bizs.get(i).getRateRegionProvince(), bizs.get(i)
						.getRateRegionCity(), bizs.get(i).getRateRegionArea()));
				bean.setRateHy(dicService.getIndustryMap().get(bizs.get(i).getRateHy()));
				bean.setRateDate(format.format(bizs.get(i).getRateDate()));
				bean.setRateMoney(bizs.get(i).getRateMoney());
				bean.setRateGz(bizs.get(i).getRateGzId() == 1 ? "跟踪" : "不跟踪");
				bean.setState(bizs.get(i).getState());
				bean.setReportId(bizs.get(i).getReportId());
				if (bizs.get(i).getSubmitRatingBizTime() != null) {
					bean.setSubmitRatingBizTime(format.format(bizs.get(i).getSubmitRatingBizTime()));
				}
				if (bizs.get(i).getHandledRatingBizTime() != null) {
					bean.setHandledRatingBizTime(format.format(bizs.get(i).getHandledRatingBizTime()));
				}

				listBean.add(bean);
			}
		}
		return listBean;
	}

	protected HtmlFragmentResponse searchRateReportBiz(HtmlFragmentRequest req, List<Integer> states, boolean needRateId)
			throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			SearchRatingReportBizFormBean formBean = (SearchRatingReportBizFormBean) req
					.getForm(Constants.SEARCHFORM_NAME);
			ListRatingReportBiz listBiz = formBean.getBiz();
			if (listBiz == null) {
				listBiz = new ListRatingReportBiz();
				formBean.setBiz(listBiz);
			}
			listBiz.setStates(states);
			listBiz.setAgcId(validateService.validateAgcUser(req, null, false));
			if(listBiz.isHasComment()!=null){
				formBean.setUseComment(true);
			}
			List<ListRatingReportBiz> bizs = reportBizService.getRatingReportBizs(formBean);
			List<ListRatingReportBizFormBean> listBean = new ArrayList<ListRatingReportBizFormBean>(bizs.size());
			if (bizs != null) {
				for (int i = 0, size = bizs.size(); i < size; i++) {
					ListRatingReportBizFormBean bean = new ListRatingReportBizFormBean(i + 1);
					if (needRateId) {
						bean.setReportId(bizs.get(i).getReportGlobalId() + "_" + bizs.get(i).getRateId());
					} else {
						bean.setReportId(bizs.get(i).getReportGlobalId());
					}
					bean.setBizReportId(bizs.get(i).getReportId());
					bean.setRateId(bizs.get(i).getRateId());
					bean.setFirmCnName(bizs.get(i).getFirmCnName());
					bean.setAnalystNum(bizs.get(i).getAnalystNum());
					bean.setXyLevel(dicService.getRateLevelMap().get(bizs.get(i).getXyLevel()));
					bean.setXyStartDate(format.format(bizs.get(i).getXyStartDate()));
					bean.setXyEndDate(format.format(bizs.get(i).getXyEndDate()));
					listBean.add(bean);
				}
			}
			int allCount = reportBizService.allCount(formBean);
			TableWidget table = new DefaultTableWidget("ratingReportBizs", listBean, new PagingBean(
					formBean.getPageNum(), formBean.getNumPerPage(), allCount));
			res.addTable(table);
			FormWidget form = new DefaultFormWidget(Constants.SEARCHFORM_NAME, formBean);
			res.addForm(form);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}
}
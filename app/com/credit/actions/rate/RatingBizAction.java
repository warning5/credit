/**
 * 
 */
package com.credit.actions.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.FormWidget;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultFormWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultMessageWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.bluecloud.persistence.util.PrimaryKey;
import com.credit.base.Constants;
import com.credit.base.IndustryDic;
import com.credit.base.Pair;
import com.credit.model.rate.RatingBiz;
import com.credit.model.rate.StateChangeModel;
import com.credit.service.rate.UploadService;
import com.credit.util.MessageUtils;

@Controller(Constants.RATINGBIZ_ACTION_NAME)
public class RatingBizAction extends AbstractBizAction {

	private Log log = LogFactory.getLog(getClass());
	public static final String rateGzApScanFile = "rateGzApScanFile";
	public static final String rateScanFile = "rateScanFile";
	public static final String rateScanReceipt = "rateScanReceipt";
	private static final String ratingBiz = "ratingBiz";
	private static final String agc = "agc";

	@Override
	protected FragmentBeanRegister regBean() {
		FragmentBeanRegister reg = new FragmentBeanRegister();
		reg.add(Constants.SEARCHFORM_NAME, SearchRatingBizFormBean.class);
		reg.add(ratingBiz, RatingBizFormBean.class);
		return reg;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		HtmlFragmentResponse res;
		try {
			List<Integer> states = new ArrayList<Integer>();
			states.add(Constants.BEFORE_NEW_STATE);
			res = searchRateBiz(request, states);
		} catch (FragmentEventException e) {
			log.error(e.getMessage(), e);
			throw new HtmlFragmentException(e);
		}
		res.forward("pages/rate/list.html");
		return res;
	}

	public HtmlFragmentResponse search(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res;
		res = searchRateBiz(request, null);
		res.forward("pages/query/searchBiz.html");
		return res;
	}

	public HtmlFragmentResponse searchBrower(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getDetail(request);
		res.forward("pages/query/bizBrower.html");
		return res;
	}

	public HtmlFragmentResponse showAdd(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String identify = validateService.validateAgcUser(request, agc, true);
		request.getHttpServletRequest().setAttribute(Constants.IDENTITY, identify);
		request.getHttpServletRequest().setAttribute(Constants.INDUSTRYTYPE, dicService.getDicIndustryList());
		request.getHttpServletRequest().setAttribute(Constants.PROVINCE, dicService.getProvinceList());
		List<SelectOperationBean> beans = new ArrayList<SelectOperationBean>();
		for (IndustryDic dic : dicService.getDicIndustryList()) {
			SelectOperationBean bean = new SelectOperationBean(dic.getCode().toString(), dic.getName());
			beans.add(bean);
		}
		SelectWidget industryType = new DefaultSelectWidget("industryType", beans);
		res.addSelect(industryType);
		res.forward("pages/rate/add.jsp");
		return res;
	}

	public HtmlFragmentResponse showEdit(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String rateId = request.getHttpServletRequest().getParameter("rateId");
			if (StringUtils.isEmpty(rateId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			RatingBiz biz = bizService.getRatingBizByID(rateId);
			if (biz != null) {
				RatingBizFormBean bean = new RatingBizFormBean();

				String tabId = request.getHttpServletRequest().getParameter(Constants.TABID);
				if (StringUtils.isEmpty(tabId)) {
					tabId = getName();
				}
				biz.setTabId(tabId);
				bean.setRatingBiz(biz);
				FormWidget form = new DefaultFormWidget(ratingBiz, bean);
				res.addForm(form);
				res.addSelect(dicService.getProvinceSelectWidget("ratingBiz_rateRegionProvince"));
				res.addSelect(dicService.getCitySelectWidget("ratingBiz_rateRegionCity", biz.getRateRegionProvince()));
				res.addSelect(dicService.getAreaSelectWidget("ratingBiz_rateRegionArea", biz.getRateRegionCity()));
				res.addSelect(dicService.getIndustryTypeSelectWidget("ratingBiz_rateBizType"));
				res.addSelect(dicService.getIndustryTypeSelectWidget("ratingBiz_rateHy"));
			} else {
				FormWidget form = new DefaultFormWidget(ratingBiz, new RatingBizFormBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		res.forward("pages/rate/edit.html");
		return res;
	}

	public HtmlFragmentResponse brower(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getDetail(request);
		res.forward("pages/rate/detail.html");
		return res;
	}
	
	public HtmlFragmentResponse supervisorBrower(HtmlFragmentRequest request) throws FragmentEventException {
		return brower(request);
	}

	private HtmlFragmentResponse getDetail(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		try {
			String rateId = request.getHttpServletRequest().getParameter("rateId");
			if (StringUtils.isEmpty(rateId)) {
				MessageWidget message = new DefaultMessageWidget(null, "请选择一条记录");
				message.fail();
				res.addMessage(message);
				return res;
			}
			RatingBiz biz = bizService.getRatingBizByID(rateId);
			if (biz != null) {
				biz.setRateRegionProvince(dicService.getProvinceMap().get(biz.getRateRegionProvince()));
				biz.setRateRegionCity(dicService.getCityAllMap().get(biz.getRateRegionCity()));
				biz.setRateRegionArea(dicService.getAreaAllMap().get(biz.getRateRegionArea()));
				biz.setRateBizType(dicService.getIndustryMap().get(biz.getRateBizType()));
				biz.setRateHy(dicService.getIndustryMap().get(biz.getRateHy()));

				RatingBizFormBean bean = new RatingBizFormBean();
				bean.setRatingBiz(biz);
				FormWidget form = new DefaultFormWidget(ratingBiz, bean);

				res.addForm(form);
			} else {
				FormWidget form = new DefaultFormWidget(ratingBiz, new RatingBizFormBean());
				res.addForm(form);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	public HtmlFragmentResponse addRatingBiz(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		HttpServletRequest request = req.getHttpServletRequest();
		try {
			RatingBizFormBean ratingBizFormBean = (RatingBizFormBean) req.getForm(ratingBiz);
			RatingBiz ratingBiz = ratingBizFormBean.getRatingBiz();
			ratingBiz.setRateId(PrimaryKey.getID());

			Map<String, Pair<String, String>> folders = handleUploadFile(ratingBiz, request);

			boolean result = bizService.saveRatingBiz(ratingBiz);
			if (result) {
				message = MessageUtils.build("新增评级业务成功", getName(), MessageUtils.SUCCESS, true);
				if (folders.size() != 0)
					uploadService.afterUpload(folders, request);
			} else {
				message = MessageUtils.build("新增评级业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("新增评级业务失败", null, MessageUtils.ERROR, false);
		}
		if (message != null)
			res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse deleteBizs(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		MessageWidget message = null;
		try {
			List<String> ids = req.getIDS();
			if (ids == null) {
				String rateId = req.getHttpServletRequest().getParameter("rateId");
				if (StringUtils.isNotEmpty(rateId)) {
					ids = new ArrayList<String>(1);
					ids.add(rateId);
				}
			}
			if (ids == null) {
				message = MessageUtils.build("请选择数据", null, MessageUtils.ERROR, false);
			} else {
				boolean isSuccess = bizService.deleteRatingBizs(ids);
				if (isSuccess) {
					message = MessageUtils.build("删除评级业务成功", getName(), MessageUtils.SUCCESS, false);

				} else {
					message = MessageUtils.build("删除评级业务失败", null, MessageUtils.ERROR, false);
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("删除评级业务失败", getName(), MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		return res;
	}

	public HtmlFragmentResponse submittedRatingBiz(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<Integer> states = new ArrayList<Integer>();
		states.add(1);
		res = searchRateBiz(req, states);
		res.forward("pages/rate/submittedlist.html");
		return res;
	}

	public HtmlFragmentResponse editRatingBiz(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		HttpServletRequest request = req.getHttpServletRequest();
		try {

			Map<String, Pair<String, String>> folders = new HashMap<String, Pair<String, String>>();

			long start = System.currentTimeMillis();
			RatingBizFormBean bean = (RatingBizFormBean) req.getForm(ratingBiz);
			System.out.println("-------------------" + (System.currentTimeMillis() - start));
			RatingBiz biz = bean.getRatingBiz();
			String targetFileName = null;
			String raw = null;

			if (uploadService.isSourceExist(rateGzApScanFile, biz.getRateGzApScanFile(), request)) {
				if (StringUtils.isNotEmpty(biz.getRateGzApScanFileOld())) {
					if (uploadService.isTargetExist(rateGzApScanFile, biz.getRateGzApScanFileOld(), request)) {
						uploadService.removeFile(rateGzApScanFile, biz.getRateGzApScanFileOld(), request);
					}
				}
				raw = biz.getRateGzApScanFile();
				if (StringUtils.isNotEmpty(raw)) {
					if (uploadService.isTargetExist(rateGzApScanFile, raw, request)) {
						targetFileName = UploadService.formatFileNameWithDate(raw);
						biz.setRateGzApScanFile(targetFileName);
					}
					UploadService.putFile(folders, request, rateGzApScanFile, raw, targetFileName);
				}
			}

			if (uploadService.isSourceExist(rateScanFile, biz.getRateScanFile(), request)) {
				if (StringUtils.isNotEmpty(biz.getRateScanFileOld())) {
					if (uploadService.isTargetExist(rateScanFile, biz.getRateScanFileOld(), request)) {
						uploadService.removeFile(rateScanFile, biz.getRateScanFileOld(), request);
					}
				}
				raw = biz.getRateScanFile();
				if (StringUtils.isNotEmpty(raw)) {
					if (uploadService.isTargetExist(rateScanFile, raw, request)) {
						targetFileName = UploadService.formatFileNameWithDate(raw);
						biz.setRateScanFile(targetFileName);
					}
					UploadService.putFile(folders, request, rateScanFile, raw, targetFileName);
				}

			}
			if (uploadService.isSourceExist(rateScanReceipt, biz.getRateScanReceipt(), request)) {
				if (StringUtils.isNotEmpty(biz.getRateScanReceiptOld())) {
					if (uploadService.isTargetExist(rateScanReceipt, biz.getRateScanReceiptOld(), request)) {
						uploadService.removeFile(rateScanReceipt, biz.getRateScanReceiptOld(), request);
					}
				}

				raw = biz.getRateScanReceipt();
				if (StringUtils.isNotEmpty(raw)) {
					if (uploadService.isTargetExist(rateScanReceipt, raw, request)) {
						targetFileName = UploadService.formatFileNameWithDate(raw);
						biz.setRateScanReceipt(targetFileName);
					}
					UploadService.putFile(folders, request, rateScanReceipt, raw, targetFileName);
				}
			}

			boolean result = bizService.updateRatingBiz(bean.getRatingBiz());
			if (result) {
				String tabId = bean.getRatingBiz().getTabId();
				message = MessageUtils.build("修改评级业务成功", tabId, MessageUtils.SUCCESS, true);
				if (folders.size() != 0)
					uploadService.afterUpload(folders, request);
			} else {
				message = MessageUtils.build("修改评级业务失败", null, MessageUtils.ERROR, false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			message = MessageUtils.build("修改评级业务失败", null, MessageUtils.ERROR, false);
			throw new FragmentEventException(e);
		}
		res.addMessage(message);
		return res;
	}

	public HtmlFragmentResponse updateStates(HtmlFragmentRequest req) throws FragmentEventException {
		HtmlFragmentResponse res = getResponse();
		List<StateChangeModel> models = null;
		try {
			MessageWidget message = null;
			List<String> ids = req.getIDS();
			if (ids == null) {
				String rateId = req.getHttpServletRequest().getParameter("rateId");
				if (StringUtils.isNotEmpty(rateId)) {
					models = new ArrayList<StateChangeModel>(1);
					StateChangeModel model = new StateChangeModel(rateId, Constants.BEFORE_SUBMIT_STATE, new Date());
					models.add(model);
				}
			} else {
				models = new ArrayList<StateChangeModel>(ids.size());
				for (String id : ids) {
					StateChangeModel model = new StateChangeModel(id, Constants.BEFORE_SUBMIT_STATE, new Date());
					models.add(model);
				}
			}
			if (models == null) {
				message = MessageUtils.build("请选择数据", null, MessageUtils.ERROR, false);
			} else {
				boolean isSuccess = bizService.updateStates(models);
				if (isSuccess) {
					String tabId = req.getHttpServletRequest().getParameter(Constants.TABID);
					if (StringUtils.isEmpty(tabId)) {
						tabId = getName();
					}
					message = MessageUtils.build("上报评级业务成功", tabId, MessageUtils.SUCCESS, false);
				} else {
					message = MessageUtils.build("上报评级业务失败", null, MessageUtils.ERROR, false);
				}
			}
			res.addMessage(message);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FragmentEventException(e);
		}
		return res;
	}

	private Map<String, Pair<String, String>> handleUploadFile(RatingBiz ratingBiz, HttpServletRequest request) {

		Map<String, Pair<String, String>> folders = new HashMap<String, Pair<String, String>>();
		String targetFileName = null;
		String raw = ratingBiz.getRateGzApScanFile();
		if (StringUtils.isNotEmpty(raw)) {
			if (uploadService.isTargetExist(rateGzApScanFile, raw, request)) {
				targetFileName = UploadService.formatFileNameWithDate(raw);
				ratingBiz.setRateGzApScanFile(targetFileName);
			}
			UploadService.putFile(folders, request, rateGzApScanFile, raw, targetFileName);
		}
		raw = ratingBiz.getRateScanFile();
		if (StringUtils.isNotEmpty(raw)) {
			if (uploadService.isTargetExist(rateScanFile, raw, request)) {
				targetFileName = UploadService.formatFileNameWithDate(raw);
				ratingBiz.setRateScanFile(targetFileName);
			}
			UploadService.putFile(folders, request, rateScanFile, raw, targetFileName);
		}
		raw = ratingBiz.getRateScanReceipt();
		if (StringUtils.isNotEmpty(raw)) {
			if (uploadService.isTargetExist(rateScanReceipt, raw, request)) {
				targetFileName = UploadService.formatFileNameWithDate(raw);
				ratingBiz.setRateScanReceipt(targetFileName);
			}
			UploadService.putFile(folders, request, rateScanReceipt, raw, targetFileName);
		}
		return folders;
	}

	@Override
	public String getName() {
		return Constants.RATINGBIZ_ACTION_NAME;
	}
}

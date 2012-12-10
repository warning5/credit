package com.credit.base;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String INDUSTRYTYPE = "industryType";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String AREA = "area";
	public static final String RATELEVEL = "rateLevel";
	public static final String REGION_VALUE = "value";
	public static final String RATEID = "rateId";
	public static final String FIRMNATURE = "firmNature";
	public static final String FIRMREGISTYPE = "firmRegisType";

	public static final String RATINGBIZ_ACTION_NAME = "ratingBiz";
	public static final String COMMENT_ACTION_NAME = "comment";
	public static final String FIRM_ACTION_NAME = "firm";
	public static final String AGC_ACTION_NAME = "agc";
	public static final String AGC_FINACIAL_ACTION_NAME = "agcFinacial";
	public static final String AGC_PRO_ACTION_NAME = "agcPro";
	public static final String AFTER_RATINGBIZ_ACTION_NAME = "afterRatingBiz";
	public static final String BEFORE_HANDLEBIZ_ACTION_NAME = "beforeHandleBiz";
	public static final String AFTER_HANDLEBIZ_ACTION_NAME = "afterHandleBiz";

	public static final String SEARCHFORM_NAME = "searchForm";

	public static final String IDENTITY = "identity";
	public static final String ACTION_SUFFIX = "action";

	public static final String TABID = "tabId";
	public static final String ADMIN_USER = "admin";

	public static final int BEFORE_NEW_STATE = 0;
	public static final int BEFORE_SUBMIT_STATE = 1;
	public static final int BEFORE_APPROVAL_STATE = 2;
	public static final int BEFORE_BACK_STATE = 3;
	public static final int REPORT_SAVE_STATE = 4;
	public static final int REPORT_SUBMIT_STATE = 5;
	public static final int REPORT_APPROVAL_STATE = 6;
	public static final int REPORT_BACK_STATE = 7;

	public static Map<Integer, String> states = new HashMap<Integer, String>();

	static {
		states.put(BEFORE_NEW_STATE, "新增");
		states.put(BEFORE_SUBMIT_STATE, "上报");
		states.put(BEFORE_APPROVAL_STATE, "批准");
		states.put(BEFORE_BACK_STATE, "退回");
		states.put(REPORT_SAVE_STATE, "后报备");
		states.put(REPORT_SUBMIT_STATE, "后报备提交");
		states.put(REPORT_APPROVAL_STATE, "后报备批准");
		states.put(REPORT_BACK_STATE, "后报备退回");
	}
}

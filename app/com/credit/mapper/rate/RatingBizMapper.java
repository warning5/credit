package com.credit.mapper.rate;

import java.util.List;

import com.credit.actions.rate.HandleBizBean;
import com.credit.base.BaseMapper;
import com.credit.model.rate.RatingBiz;
import com.credit.model.rate.ReportChangeModel;
import com.credit.model.rate.StateChangeModel;

public interface RatingBizMapper extends BaseMapper {

	int insert(RatingBiz record);

	RatingBiz selectByPrimaryKey(String rateId);

	List<RatingBiz> selectAll();

	int update(RatingBiz record);

	void deleteBizs(List<String> ids);

	void updateBizState(StateChangeModel model);

	void approve(StateChangeModel model);

	void back(HandleBizBean model);

	String getBackReason(String rateId);

	void updateBizReportKey(ReportChangeModel model);

}
package com.credit.mapper.rate;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.credit.actions.rate.HandleBizBean;
import com.credit.base.BaseMapper;
import com.credit.model.rate.ListRatingReportBiz;
import com.credit.model.rate.RatingReportBiz;
import com.credit.model.rate.StateChangeModel;

public interface RatingReportBizMapper extends BaseMapper {

	void deleteRatingReportBizs(List<String> ids);

	int insert(RatingReportBiz record);

	RatingReportBiz selectByPrimaryKey(String reportGlobalId);

	int update(RatingReportBiz record);

	void uploadReport(StateChangeModel model);

	void approve(StateChangeModel model);

	void back(HandleBizBean model);

	String getBackReason(String rateId);

	<T> List<ListRatingReportBiz> searchLimitWithComment(T bo, RowBounds rowBounds);

	<T> int allCountWithComment(T bo);

}
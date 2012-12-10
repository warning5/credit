package com.credit.mapper.rate;

import java.util.List;

import com.credit.base.BaseMapper;
import com.credit.model.rate.AgcBase;
import com.credit.model.rate.AgcNameModel;

public interface AgcBaseMapper extends BaseMapper {

	int insert(AgcBase record);

	AgcBase selectByPrimaryKey(String agcId);

	int updateAgc(AgcBase agcBase);

	void deleteAgcs(List<String> agcIds);

	AgcNameModel getAgcName(String agcId);
}
package com.credit.mapper.rate;

import java.util.List;

import com.credit.base.BaseMapper;
import com.credit.model.rate.AgcProfessional;

public interface AgcProfessionalMapper extends BaseMapper {

	int updateAgcProfessional(AgcProfessional agcProfessional);

	void deleteAgcProfessionals(List<String> agcProfessionalIds);

	int insert(AgcProfessional record);

	AgcProfessional selectByPrimaryKey(String agcProId);

}
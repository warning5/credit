package com.credit.mapper.rate;

import java.util.List;

import com.credit.base.BaseMapper;
import com.credit.model.rate.AgcFinacial;

public interface AgcFinacialMapper extends BaseMapper {

	int insert(AgcFinacial agcFinacial);

	int updateAgcFinacial(AgcFinacial agcFinacial);

	void deleteAgcFinacials(List<String> agcFinacialIds);

	AgcFinacial selectByPrimaryKey(String agcFinacialId);
}
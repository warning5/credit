package com.credit.mapper.rate;

import java.util.List;

import com.credit.base.BaseMapper;
import com.credit.model.rate.Firm;

public interface FirmMapper extends BaseMapper {

	int insert(Firm record);

	Firm selectByPrimaryKey(String firmId);

	int updateFirm(Firm record);

	void deleteFirms(List<String> firmIds);
}
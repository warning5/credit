/**
 * 
 */
package com.credit.service.rate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluecloud.persistence.util.PrimaryKey;
import com.credit.mapper.rate.AgcBaseMapper;
import com.credit.model.rate.AgcBase;
import com.credit.model.rate.AgcNameModel;
import com.credit.model.rate.ListAgcBase;
import com.credit.org.bo.Company;
import com.credit.org.service.OrganizationService;
import com.credit.util.CreditConstants.Organization;

@Service
public final class AgcService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private AgcBaseMapper mapper;
	@Autowired
	private OrganizationService organizationService;

	public boolean saveAgcBase(AgcBase agcBase) {
		try {
			agcBase.setAgcId(PrimaryKey.getID());
			mapper.insert(agcBase);
			Company company = new Company();
			company.setDescription(agcBase.getAgcCnName());
			company.setId(agcBase.getAgcId());
			company.setName(agcBase.getAgcCnName());
			company.setParent(Organization.Agc.getType());
			organizationService.saveCompany(company);
		} catch (Exception e) {
			log.error(String.format("insert agc failure,agcName=%s", agcBase.getAgcCnName()), e);
			return false;
		}
		return true;
	}

	public boolean deleteAgcBases(List<String> agcs) {
		if (agcs == null || agcs.isEmpty()) {
			return false;
		}
		try {
			this.mapper.deleteAgcs(agcs);
		} catch (Exception e) {
			log.error("delete agc failure. agcIds=" + agcs, e);
			return false;
		}
		return true;
	}

	public boolean updateAgc(AgcBase agcBase) {

		try {
			mapper.updateAgc(agcBase);
		} catch (Exception e) {
			log.error("update agcBase '" + agcBase.getAgcId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public AgcBase getAgcBase(String agcId) {
		try {
			return mapper.selectByPrimaryKey(agcId);
		} catch (Exception e) {
			log.error("get agc failure.agcId=" + agcId, e);
		}
		return null;
	}

	public AgcNameModel getAgcName(String agcId) {
		try {
			AgcNameModel agcNameModel = mapper.getAgcName(agcId);
			agcNameModel.setAgcId(agcId);
			return agcNameModel;
		} catch (Exception e) {
			log.error("get agc name failure.agcId=" + agcId, e);
		}
		return null;
	}

	public List<ListAgcBase> getAgcBases(ListAgcBase listAgcBase, int offset, int limit) {
		if (listAgcBase == null) {
			return getAgcBases(offset, limit);
		}
		return mapper.searchLimit(listAgcBase, new RowBounds(offset, limit));
	}

	public List<ListAgcBase> getAgcBases(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}
}

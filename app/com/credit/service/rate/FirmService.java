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
import com.credit.mapper.rate.FirmMapper;
import com.credit.model.rate.Firm;
import com.credit.model.rate.ListFirm;
import com.credit.org.bo.Company;
import com.credit.org.service.OrganizationService;
import com.credit.util.CreditConstants.Organization;

@Service
public final class FirmService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private FirmMapper mapper;
	@Autowired
	private OrganizationService organizationService;

	public boolean saveFirm(Firm firm) {
		try {
			firm.setFirmId(PrimaryKey.getID());
			mapper.insert(firm);
			Company company = new Company();
			company.setDescription(firm.getFirmCnName());
			company.setId(firm.getFirmId());
			company.setName(firm.getFirmCnName());
			company.setParent(Organization.Firm.getType());
			organizationService.saveCompany(company);
		} catch (Exception e) {
			log.error(String.format("insert firm failure,firmName=%s", firm.getFirmCnName()), e);
			return false;
		}
		return true;
	}

	public boolean deleteFirms(List<String> firmIds) {
		if (firmIds == null || firmIds.isEmpty()) {
			return false;
		}
		try {
			this.mapper.deleteFirms(firmIds);
		} catch (Exception e) {
			log.error("delete firms failure. firmIds=" + firmIds, e);
			return false;
		}
		return true;
	}

	public boolean updateFirm(Firm firm) {
		try {
			mapper.updateFirm(firm);
		} catch (Exception e) {
			log.error("update firm '" + firm.getFirmId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public Firm getFirm(String firmId) {
		try {
			return mapper.selectByPrimaryKey(firmId);
		} catch (Exception e) {
			log.error("get firm failure.firmId=" + firmId, e);
		}
		return null;
	}

	public List<ListFirm> getFirms(ListFirm listFirm, int offset, int limit) {
		if (listFirm == null) {
			return getFirms(offset, limit);
		}
		return mapper.searchLimit(listFirm, new RowBounds(offset, limit));
	}

	public List<ListFirm> getFirms(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}
}

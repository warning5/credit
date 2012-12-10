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
import com.credit.mapper.rate.AgcProfessionalMapper;
import com.credit.model.rate.AgcProfessional;
import com.credit.model.rate.ListAgcProfessional;

@Service
public final class AgcProfessionalService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private AgcProfessionalMapper mapper;

	public boolean saveAgcProfessional(AgcProfessional agcProfessional) {
		try {
			agcProfessional.setAgcProId(PrimaryKey.getID());
			mapper.insert(agcProfessional);
		} catch (Exception e) {
			log.error(String.format("insert agc professional failure,agcProId=%s", agcProfessional.getAgcProId()), e);
			return false;
		}
		return true;
	}

	public boolean deleteAgcProfessionals(List<String> agcProfessionals) {
		if (agcProfessionals == null || agcProfessionals.isEmpty()) {
			return false;
		}
		try {
			this.mapper.deleteAgcProfessionals(agcProfessionals);
		} catch (Exception e) {
			log.error("delete agc finacials failure. agcProIds=" + agcProfessionals, e);
			return false;
		}
		return true;
	}

	public boolean updateAgcProfessional(AgcProfessional agcProfessional) {

		try {
			mapper.updateAgcProfessional(agcProfessional);
		} catch (Exception e) {
			log.error("update agc professional '" + agcProfessional.getAgcProId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public AgcProfessional getAgcProfessional(String agcProfessional) {
		try {
			return mapper.selectByPrimaryKey(agcProfessional);
		} catch (Exception e) {
			log.error("get agc finacial failure.agcProfessional=" + agcProfessional, e);
		}
		return null;
	}

	public List<ListAgcProfessional> getAgcProfessionals(ListAgcProfessional listAgcProfessional, int offset, int limit) {
		if (listAgcProfessional == null) {
			return getAgcProfessionals(offset, limit);
		}
		return mapper.searchLimit(listAgcProfessional, new RowBounds(offset, limit));
	}

	public List<ListAgcProfessional> getAgcProfessionals(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}
	
	public int allCount(ListAgcProfessional listAgcProfessional) {
		return mapper.allCount(listAgcProfessional);
	}
}

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
import com.credit.mapper.rate.AgcFinacialMapper;
import com.credit.model.rate.AgcFinacial;
import com.credit.model.rate.ListAgcFinacial;

@Service
public final class AgcFinacialService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private AgcFinacialMapper mapper;

	public boolean saveAgcFinacial(AgcFinacial agcFinacial) {
		try {
			agcFinacial.setAgcFinaceId(PrimaryKey.getID());
			mapper.insert(agcFinacial);
		} catch (Exception e) {
			log.error(String.format("insert agc finacial failure,agcFinaceId=%s", agcFinacial.getAgcFinaceId()), e);
			return false;
		}
		return true;
	}

	public boolean deleteAgcFinacials(List<String> agcFinacials) {
		if (agcFinacials == null || agcFinacials.isEmpty()) {
			return false;
		}
		try {
			this.mapper.deleteAgcFinacials(agcFinacials);
		} catch (Exception e) {
			log.error("delete agc finacials failure. agcFinacials=" + agcFinacials, e);
			return false;
		}
		return true;
	}

	public boolean updateAgcFinacial(AgcFinacial agcFinacial) {

		try {
			mapper.updateAgcFinacial(agcFinacial);
		} catch (Exception e) {
			log.error("update agc finacial '" + agcFinacial.getAgcFinaceId() + "' failure.", e);
			return false;
		}
		return true;
	}

	public AgcFinacial getAgcFinacial(String agcFinacialId) {
		try {
			return mapper.selectByPrimaryKey(agcFinacialId);
		} catch (Exception e) {
			log.error("get agc finacial failure.agcFinacialId=" + agcFinacialId, e);
		}
		return null;
	}

	public List<ListAgcFinacial> getAgcFinacials(ListAgcFinacial listAgcFinacial, int offset, int limit) {
		if (listAgcFinacial == null) {
			return getAgcFinacials(offset, limit);
		}
		return mapper.searchLimit(listAgcFinacial, new RowBounds(offset, limit));
	}

	public List<ListAgcFinacial> getAgcFinacials(int offset, int limit) {
		return mapper.listLimit(new RowBounds(offset, limit));
	}

	public int allCount(ListAgcFinacial listAgcFinacial) {
		return mapper.allCount(listAgcFinacial);
	}
}

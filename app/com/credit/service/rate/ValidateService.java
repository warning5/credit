package com.credit.service.rate;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.credit.base.Constants;
import com.credit.http.user.AgcUser;
import com.credit.http.user.IOrgUser;
import com.credit.http.user.OnlineUser;
import com.credit.http.user.SupervisorUser;
import com.credit.model.rate.AgcNameModel;
import com.credit.util.CreditConstants;

@Service
public class ValidateService {

	private static Log log = LogFactory.getLog(ValidateService.class);
	
	@Autowired
	protected AgcService agcService;
	
	public String validateAgcUser(HtmlFragmentRequest request, String pageItem, boolean search) {
		OnlineUser onlineUser = (OnlineUser) request.getHttpServletRequest().getSession()
				.getAttribute(CreditConstants.ONLINEUSER);
		String identity = null;
		if (onlineUser == null) {
			log.error("online user is null");
		} else {
			IOrgUser orgUser = onlineUser.getOrgUser();
			if (orgUser instanceof AgcUser) {
				identity = orgUser.getIdentify();
				if (StringUtils.isNotEmpty(identity)) {
					request.getHttpServletRequest().setAttribute(Constants.IDENTITY, identity);
					if (search) {
						AgcNameModel agcNameModel = agcService.getAgcName(identity);
						if (agcNameModel != null)
							request.getHttpServletRequest().setAttribute(pageItem + "_" + "agcCnName",
									agcNameModel.getAgcCnName());
					}
				} else {
					log.error("find agc user logon,but has no identity.");
				}
			} else if (orgUser instanceof SupervisorUser) {
				log.debug("supervisor user manage agc function.");
			} else {
				log.error("invalid user try to use agc managment.");
			}
		}
		return identity;
	}
}

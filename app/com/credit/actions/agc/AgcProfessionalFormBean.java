package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.AgcProfessional;

@NoMapSuperClass
public class AgcProfessionalFormBean extends FragmentBean {

	private AgcProfessional agcProfessional;

	public AgcProfessional getAgcProfessional() {
		return agcProfessional;
	}

	public void setAgcProfessional(AgcProfessional agcProfessional) {
		this.agcProfessional = agcProfessional;
	}

}

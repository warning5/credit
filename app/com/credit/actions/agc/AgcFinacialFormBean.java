package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.AgcFinacial;

@NoMapSuperClass
public class AgcFinacialFormBean extends FragmentBean {

	private AgcFinacial agcFinacial;

	public AgcFinacial getAgcFinacial() {
		return agcFinacial;
	}

	public void setAgcFinacial(AgcFinacial agcFinacial) {
		this.agcFinacial = agcFinacial;
	}

}

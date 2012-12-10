package com.credit.actions.agc;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.AgcBase;

@NoMapSuperClass
public class AgcBaseFormBean extends FragmentBean {

	private AgcBase agc;

	public AgcBase getAgc() {
		return agc;
	}

	public void setAgc(AgcBase agc) {
		this.agc = agc;
	}

}

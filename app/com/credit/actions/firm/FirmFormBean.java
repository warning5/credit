package com.credit.actions.firm;

import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.external.beans.NoMapSuperClass;
import com.credit.model.rate.Firm;

@NoMapSuperClass
public class FirmFormBean extends FragmentBean {

	private Firm firm;

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

}

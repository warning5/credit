/**
 * 
 */
package com.credit.actions.agc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bluecloud.mvc.external.FragmentAction;
import com.credit.service.rate.DicService;
import com.credit.service.rate.ValidateService;

public abstract class AbstractAgcAction extends FragmentAction {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	protected DicService dicService;
	@Autowired
	protected ValidateService validateService;

}

package com.bluecloud.mvc.core;

import javax.servlet.ServletConfig;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.external.Action;

public class ActionDepositoryFactory {

	public static ActionDepository getActionDepository(ServletConfig config) {

		if (config == null) {
			return new FragmentActionDepositoryImpl();
		} else {
			WebApplicationContext context=WebApplicationContextUtils.getRequiredWebApplicationContext(config
					.getServletContext());
			return new SpringActionDepository(context.getBeansOfType(Action.class));
		}
	}
}

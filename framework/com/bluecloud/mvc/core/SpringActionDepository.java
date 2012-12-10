package com.bluecloud.mvc.core;

import java.util.Map;

import com.bluecloud.mvc.api.ActionDepository;
import com.bluecloud.mvc.external.Action;

public class SpringActionDepository implements ActionDepository {

	private Map<String, Action> actions;

	public SpringActionDepository(Map<String, Action> actions) {
		this.actions = actions;
	}

	@Override
	public Action getAction(String name) {
		return actions.get(name);
	}

	@Override
	public void load(ClassLoader classLoader) throws Exception {

	}

}

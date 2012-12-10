/*
 * Copyright 2010 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bluecloud.ioc.lifecycle;

import com.bluecloud.ioc.classloader.ClassHandler;
import com.bluecloud.ioc.core.ComponentContext;
import com.bluecloud.ioc.core.ComponentDepender;
import com.bluecloud.ioc.core.ComponentObject;
import com.bluecloud.ioc.core.KernelComponentContext;
import com.bluecloud.ioc.exception.KernelComponentLifecycleException;
import com.bluecloud.ioc.metadata.ComponentMetadata;
import com.bluecloud.ioc.metadata.ServiceMetadata;
import com.bluecloud.ioc.metadata.ServicesMetadata;

/**
 * Component生命周期的循环依赖阶段
 * 
 * @author <a href="mailto:hanlu0808@gmail.com">Hanlu</a>
 * 
 */
public class ComponentDependLifecycle implements ComponentLifecycle {
	private ComponentDepender depender;
	private ComponentContext context;

	public ComponentDependLifecycle(ComponentDepender depender,
			ComponentContext context) {
		this.depender = depender;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.css.sword.esb.server.microkernel.lifecycle.ComponentLifecycle#invoke
	 * (com.css.sword.esb.server.microkernel.core.ComponentObject,
	 * com.css.sword.esb.server.microkernel.lifecycle.ComponentLifecycle)
	 */
	public void invoke(ComponentObject component, ComponentLifecycle lifecycle)
			throws KernelComponentLifecycleException {
		ComponentMetadata componentMetadata = component.getComponentMetadate();
		try {
			Object componentObject = component.getObject();
			if (componentObject != null) {
				new ComponentReadyLifecycle(depender, context).invoke(
						component, lifecycle);
				((KernelComponentContext) context)
						.removeDependComponent(component);
				return;
			}
			// 是否存在Constructor依赖注入，如果存在，Constructor注入依赖。
			if (componentMetadata.getConstructor() != null) {
				componentObject = depender.injectConstructor(componentMetadata,
						context);
			} else {
				componentObject = ClassHandler.getClass(componentMetadata
						.getClazz(), true);
			}
			// 是否存在属性依赖
			for (ServiceMetadata service : componentMetadata.getServices()) {
				depender.inject(componentObject, service, context);
			}
			// 是否存在属性列表依赖
			for (ServicesMetadata services : componentMetadata.getListService()) {
				depender.injectList(componentObject, services, context);
			}
			component.setDependObject(componentObject);
			((KernelComponentContext) context).addDependComponent(component);
			lifecycle.invoke(component, this);
		} catch (Exception e) {
			throw new KernelComponentLifecycleException(e);
		}
	}
}

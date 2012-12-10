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
package com.bluecloud.ioc.exception;

/**
 * @author <a href="mailto:hanlu0808@gmail.com">Hanlu</a>
 *
 */
public class KernelComponentBuilderException extends KernelException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -605887399266162759L;

	/**
	 * 
	 */
	public KernelComponentBuilderException() {
		
	}

	/**
	 * @param message
	 */
	public KernelComponentBuilderException(String message) {
		super(message);
		
	}

	/**
	 * @param cause
	 */
	public KernelComponentBuilderException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KernelComponentBuilderException(String message, Throwable cause) {
		super(message, cause);
		
	}

}

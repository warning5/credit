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
package com.bluecloud.ioc.source;

import java.io.File;

import com.bluecloud.ioc.exception.KernelSourceException;


/**
 * 包含jar包的文件URL资源，{@code JarPathSource.resource()}返回URL[]
 * @author <a href="mailto:hanlu0808@gmail.com">Hanlu</a>
 *
 */
public class JarPathSource extends URLSource {

	/**
	 * @param jarDir
	 * @throws KernelSourceException 
	 */
	public JarPathSource(File jarDir) throws KernelSourceException {
		super(new JarFileSource(jarDir));
	}
}

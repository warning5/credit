/**
 * 
 */
package com.credit.http;

import java.io.IOException;
import java.io.Reader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;

import com.bluecloud.persistence.BCPersistence;

/**
 * @author Hanlu
 *2012-9-14
 */
@Deprecated
public class CreditListener implements ServletContextListener {

	private Log log=LogFactory.getLog(CreditListener.class);

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		BCPersistence.destroy();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String resource = "Configuration.xml";
		log.debug("加载持久层配置文件:"+resource);
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			log.debug("初始化持久层框架...");
			BCPersistence.init(reader);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}

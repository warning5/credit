package com.credit.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Utils {

	private static Log logger = LogFactory.getLog(Utils.class);

	public static File formatPath(String webPath, String folder) {
		File temp = new File(folder);
		boolean ok = false;
		if (temp.isAbsolute() && temp.exists()) {
			return temp;
		}
		if (!ok) {
			temp = new File(webPath, folder);
			if (temp.exists()) {
				return temp;
			} else {
				try {
					FileUtils.forceMkdir(temp);
					return temp;
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	public static String getWebPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}
}

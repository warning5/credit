package com.credit.actions;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.widgets.MessageWidget;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.util.BaseConfig;
import com.credit.util.MessageUtils;
import com.credit.util.Utils;

/**
 * @author Hanlu
 * @author panye
 * 
 */
@Controller("download")
public class DownloadAction extends FragmentAction {
	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private BaseConfig baseConfig;

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {

		HtmlFragmentResponse res = getResponse();

		MessageWidget message = null;
		String key = request.getHttpServletRequest().getParameter("folder");
		String fileName = request.getHttpServletRequest().getParameter("file");
		try {
			fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			log.error(e1.getMessage(), e1);
		}
		if (StringUtils.isEmpty(fileName)) {
			message = MessageUtils.build("非有效文件", null, MessageUtils.ERROR, false);
			res.addMessage(message);
			res.forward("html/noFile.html");
			return res;
		}
		File file = getFile(key, request.getHttpServletRequest(), fileName);

		if (!file.exists()) {
			message = MessageUtils.build("文件不存在", null, MessageUtils.ERROR, false);
			res.addMessage(message);
			res.forward("html/noFile.html");
			return res;
		} else {

			try {
				res.download(file, fileName);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new HtmlFragmentException(e);
			}
		}
		return res;
	}

	private File getFile(String key, HttpServletRequest request, String fileName) {
		String folder = baseConfig.getConfig(key);
		if (StringUtils.isNotEmpty(folder)) {
			final String webPath = Utils.getWebPath(request);
			File targetPath = Utils.formatPath(webPath, folder);
			File filePath = new File(targetPath, fileName);
			return filePath;
		}
		return null;
	}

	@Override
	protected FragmentBeanRegister regBean() {
		return null;
	}

	@Override
	public String getName() {
		return "download";
	}

}

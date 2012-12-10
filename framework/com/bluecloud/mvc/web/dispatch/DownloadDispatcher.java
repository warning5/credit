/**
 * 
 */
package com.bluecloud.mvc.web.dispatch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FragmentActionHandler;
import com.bluecloud.mvc.exception.HtmlFragmentDispatcherException;
import com.bluecloud.mvc.web.http.HtmlFragmentDispatcher;

/**
 * @author Hanlu
 * 
 */
public class DownloadDispatcher extends HtmlFragmentDispatcher {
	private Log log = LogFactory.getLog(getClass());
	private FileInputStream fis;
	private String fileName;

	/**
	 * @param path
	 */
	public DownloadDispatcher(String path) {
		super(path);
	}

	public DownloadDispatcher(FileInputStream fileInputStream, String fileName) {
		this(null);
		this.fis = fileInputStream;
		this.fileName = fileName;
	}

	@Override
	public void exce(FragmentActionHandler fragmentHandler) throws HtmlFragmentDispatcherException {
		HttpServletRequest req = fragmentHandler.getRrequest().getHttpServletRequest();
		HttpServletResponse res = fragmentHandler.getResponse().getHttpServletResponse();
		String str = req.getHeader("USER-AGENT");
		try {
			if (str != null) {
				if (-1 != str.indexOf("MSIE")) {
					this.fileName = URLEncoder.encode(this.fileName, "UTF-8");
				} else if (-1 != str.indexOf("Mozilla")) {
					this.fileName = new String(this.fileName.getBytes("UTF-8"), "ISO8859-1");
				}
			}
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			log.error(localUnsupportedEncodingException.getMessage(), localUnsupportedEncodingException);
			throw new HtmlFragmentDispatcherException(localUnsupportedEncodingException);
		}
		res.setContentType("application/x-download;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment;fileName=\"" + this.fileName + "\"");
		ServletOutputStream localServletOutputStream = null;
		try {
			localServletOutputStream = res.getOutputStream();
			byte[] arrayOfByte = new byte[1024];
			int i = 0;
			while ((i = this.fis.read(arrayOfByte)) > 0)
				localServletOutputStream.write(arrayOfByte, 0, i);
			this.fis.close();
			localServletOutputStream.flush();
		} catch (Exception localException2) {
			log.error(localException2.getMessage(), localException2);
			throw new HtmlFragmentDispatcherException(localException2);
		} finally {
			if (localServletOutputStream != null) {
				try {
					localServletOutputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

}

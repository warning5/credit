package com.credit.service.rate;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.base.Pair;
import com.credit.http.user.OnlineUser;
import com.credit.rbac.bo.User;
import com.credit.util.BaseConfig;
import com.credit.util.CreditConstants;
import com.credit.util.Utils;

@Service
public class UploadService {

	private static Log log = LogFactory.getLog(UploadService.class);
	@Autowired
	private BaseConfig baseConfig;
	public static final String UPLOAD_TEMP = "_temp";
	static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	String webPath = null;

	/**
	 * 
	 * key:folder value:fileName
	 * 
	 * @param folders
	 */
	public void afterUpload(Map<String, Pair<String, String>> folders, final HttpServletRequest request) {

		if (StringUtils.isEmpty(webPath)) {
			webPath = Utils.getWebPath(request);
		}
		final String userIdentify = getUserIdentity(request);

		for (final Entry<String, Pair<String, String>> entry : folders.entrySet()) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					String targetPath = baseConfig.getConfig(entry.getKey());
					if (StringUtils.isEmpty(targetPath)) {
						log.error("can't find copy target folder by key " + targetPath);
						return;
					}
					File source = Utils.formatPath(webPath,
							baseConfig.getConfig(entry.getKey() + UploadService.UPLOAD_TEMP));
					if (!source.exists()) {
						source.mkdir();
					}
					File target = Utils.formatPath(webPath, targetPath);

					Pair<String, String> pair = entry.getValue();

					String fileName = pair.getFirst();

					if (StringUtils.isNotEmpty(fileName)) {
						int index = fileName.indexOf("_" + userIdentify);
						if (index > 0) {
							File destFile = null;
							File srcFile = null;
							try {
								srcFile = new File(source, fileName);
								if (srcFile.exists()) {
									String targetFileName = StringUtils.isEmpty(pair.getSecond()) ? fileName.substring(
											0, index) : pair.getSecond();
									destFile = new File(target, targetFileName);
									FileUtils.copyFile(srcFile, destFile);
								}
							} catch (IOException e) {
								log.error("copy file [" + srcFile + "] to [" + destFile + "] failure");
							}
						}
					}

					File[] tempFiles = source.listFiles(new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							if (name.endsWith("_" + userIdentify)) {
								return true;
							}
							return false;
						}
					});
					for (File f : tempFiles) {
						if (!f.delete()) {
							log.error("delete temp file " + f + " failure.");
						}
					}
				}
			});
			thread.setName("copy file " + entry.getValue());
			thread.setDaemon(true);
			thread.start();
		}
	}

	public static String getUserIdentity(HttpServletRequest request) {
		OnlineUser onlineUser = (OnlineUser) request.getSession().getAttribute(CreditConstants.ONLINEUSER);
		User user = onlineUser.getUser();
		return user.getUsername();
	}

	public static String formatFileNameWithDate(String fileName) {

		int index = fileName.lastIndexOf(".");
		String raw = fileName.substring(0, index) + "-" + format.format(new Date()) + fileName.substring(index);
		return raw;
	}

	public boolean isTargetExist(String key, String fileName, HttpServletRequest request) {
		String folder = baseConfig.getConfig(key);
		if (StringUtils.isNotEmpty(folder)) {
			final String webPath = Utils.getWebPath(request);
			File targetPath = Utils.formatPath(webPath, folder);
			File target = new File(targetPath, fileName);
			if (target.exists()) {
				return true;
			}
		}
		return false;
	}

	public boolean isSourceExist(String key, String fileName, HttpServletRequest request) {
		String folder = baseConfig.getConfig(key + UploadService.UPLOAD_TEMP);
		if (StringUtils.isNotEmpty(folder)) {
			final String webPath = Utils.getWebPath(request);
			File sourcePath = Utils.formatPath(webPath, folder);
			File source = new File(sourcePath, fileName + "_" + getUserIdentity(request));
			if (source.exists()) {
				return true;
			}
		}
		return false;
	}

	public void removeFile(String key, String fileName, HttpServletRequest request) {
		String folder = baseConfig.getConfig(key);
		if (StringUtils.isNotEmpty(folder)) {
			String webPath = Utils.getWebPath(request);
			File path = Utils.formatPath(webPath, folder);
			File src = new File(path, fileName);
			if (src.exists()) {
				File target = new File(path, fileName + "_" + formatFileNameWithDate(fileName) + "_delete");
				// if (f.delete())
				// log.debug("delete file " + f + " successful.");
				src.renameTo(target);
			}
		}
	}

	public void removeTargetFile(String key, String fileName) {
		String folder = baseConfig.getConfig(key);
		if (StringUtils.isNotEmpty(folder)) {
			File path = Utils.formatPath(webPath, folder);
			File src = new File(path, fileName);
			if (src.exists()) {
				if (src.delete())
					log.debug("delete file " + src + " successful.");
			}
		}
	}

	public static void putFile(Map<String, Pair<String, String>> folders, HttpServletRequest request, String folder,
			String fileName, String targetFileName) {
		folders.put(folder, new Pair<String, String>(fileName + "_" + getUserIdentity(request), targetFileName));
	}
}

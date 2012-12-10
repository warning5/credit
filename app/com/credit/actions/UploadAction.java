package com.credit.actions;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.api.FileUploaded;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.service.rate.UploadService;
import com.credit.util.BaseConfig;
import com.credit.util.Utils;

@Controller("upload")
public class UploadAction extends FragmentAction {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private BaseConfig baseConfig;

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest req) throws HtmlFragmentException {
		HtmlFragmentResponse res = getResponse();
		String message = null;

		Map<String, FileUploaded> uploadMap = req.getFileUploaded();
		if (uploadMap == null) {
			message = "{\"error\":\"请选择数据\"}";
		} else {
			HttpServletRequest request = req.getHttpServletRequest();
			final String folder = req.getUploadParameter().get("folder")[0];
			for (Entry<String, FileUploaded> entry : uploadMap.entrySet()) {
				File file = Utils.formatPath(request.getSession().getServletContext().getRealPath("/"),
						baseConfig.getConfig(folder + UploadService.UPLOAD_TEMP));
				if (file == null) {
					log.warn("can't upload file,because of folder is not exist.");
					message = "{\"error\":\"上传失败\"}";
				}
				String rawFile = entry.getValue().getFilename();
				String fileName = rawFile + "_" + UploadService.getUserIdentity(request);
				File saveFile = new File(file, fileName);
				try {
					if (saveFile.exists()) {
						rawFile = UploadService.formatFileNameWithDate(rawFile);
						fileName = rawFile + "_" + UploadService.getUserIdentity(request);
						saveFile = new File(file, fileName);
					}
					FileUtils.writeByteArrayToFile(saveFile, entry.getValue().getContent());
					message = "{\"success\": \"true\",\"fileName\":\"" + rawFile + "\"}";
				} catch (IOException e) {
					log.error("write upload file " + entry.getKey() + " fialue.");
					message = "{\"error\":\"上传失败\"}";
				}
			}
			res.getData().asJson(message);
		}
		return res;
	}

	@Override
	protected FragmentBeanRegister regBean() {
		return null;
	}

	@Override
	public String getName() {
		return "upload";
	}

}

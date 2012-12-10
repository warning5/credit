/**
 * 
 */
package com.bluecloud.mvc.web.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bluecloud.mvc.api.FileUploaded;
import com.bluecloud.mvc.api.FragmentBeanBuilder;
import com.bluecloud.mvc.core.FragmentBeanBuilderImpl;
import com.bluecloud.mvc.exception.FragmentBeanBuilderException;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.web.data.DataObject;
import com.bluecloud.mvc.web.data.RequestData;

/**
 * @author Hanlu
 * 
 */
public final class BCWebRequest implements HtmlFragmentRequest {

	Log log = LogFactory.getLog(getClass());
	private HttpServletRequest req;
	private RequestData reqData;
	private String eventName;
	private FragmentBeanRegister beans;
	private FragmentBeanBuilder builder;
	private boolean isSet;
	private static volatile boolean uplaodSet = false;
	private int MAX_UPLOAD_FILE_SIZE = 10 * 1000 * 1000;
	private static String DEFAULT_ENCODING = "UTF-8";

	final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
	final Map<String, FileUploaded> fileUploadedMap = new HashMap<String, FileUploaded>();

	public BCWebRequest(HttpServletRequest req) {
		this.req = req;
		if (!uplaodSet) {
			String _upload_size = (String) req.getSession().getServletContext()
					.getInitParameter("MAX_UPLOAD_FILE_SIZE");
			if (StringUtils.isNotEmpty(_upload_size)) {
				String size = _upload_size.substring(0, _upload_size.length() - 1);
				if (_upload_size.toLowerCase().endsWith("m")) {
					MAX_UPLOAD_FILE_SIZE = Integer.parseInt(size) * 1000 * 1000;
				} else if (_upload_size.toLowerCase().endsWith("k")) {
					MAX_UPLOAD_FILE_SIZE = Integer.parseInt(size) * 1000;
				}
			}
			uplaodSet = true;
		}
		builder = new FragmentBeanBuilderImpl();
		try {
			handleUploadFile(req);
		} catch (ServletException e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentRequest#getActionName()
	 */
	@Override
	public String getActionName() {
		String sp = req.getServletPath();
		if (sp.startsWith("/") || sp.startsWith("\\")) {
			sp = sp.substring(1);
		}
		sp = sp.substring(0, sp.indexOf("."));
		if (sp.indexOf("_") > -1) {
			sp = sp.substring(0, sp.indexOf("_"));
		}
		return sp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentRequest#getEvent()
	 */
	@Override
	public String getEventName() {
		if (isSet) {
			return eventName;
		}
		String sp = req.getServletPath();
		sp = sp.substring(0, sp.indexOf("."));
		if (sp.indexOf("_") > -1) {
			eventName = sp.substring(sp.indexOf("_") + 1, sp.length());
		} else {
			eventName = null;
		}
		return eventName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#setEventName(java.lang
	 * .String)
	 */
	@Override
	public void setEventName(String eventName, boolean isSet) {
		this.isSet = isSet;
		this.eventName = eventName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#getHttpServletRequest()
	 */
	@Override
	public HttpServletRequest getHttpServletRequest() {
		return this.req;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#getForm(java.lang.String)
	 */
	@Override
	public FragmentBean getForm(String beanName) throws HtmlFragmentRequestException {
		if (beans == null) {
			return null;
		}
		FragmentBean bean = beans.find(beanName);
		DataObject data = reqData.getData(beanName);
		if (data == null) {
			data = reqData.getData("FWebForm");
		}
		if (data == null) {
			return null;
		}
		Map<String, String> formData = data.data();
		try {
			builder.inject(bean, formData);
		} catch (FragmentBeanBuilderException e) {
			throw new HtmlFragmentRequestException(e);
		}
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bluecloud.mvc.web.http.HtmlFragmentRequest#getReqData()
	 */
	@Override
	public RequestData getReqData() {
		return reqData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#setData(com.bluecloud.
	 * mvc.web.http.RequestData)
	 */
	@Override
	public void setData(RequestData requestData) {
		requestData.parse(this);
		this.reqData = requestData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#setBeans(com.bluecloud
	 * .mvc.external.FragmentBeanRegister)
	 */
	@Override
	public void setBeans(FragmentBeanRegister beans) {
		this.beans = beans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluecloud.mvc.web.http.HtmlFragmentRequest#getIDS(java.lang.String)
	 */
	@Override
	public List<String> getIDS(String beanName) {
		DataObject data = reqData.getData(beanName);
		if (data == null) {
			return this.getIDS();
		} else {
			return this.getIDS(data);
		}
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private List<String> getIDS(DataObject data) {
		if (data == null) {
			return null;
		}
		Map<String, String> idsData = data.data();
		String ids = idsData.get("ids");
		if (ids == null) {
			return null;
		}
		String[] s = ids.split(",");
		List<String> list = new ArrayList<String>(s.length);
		for (String id : s) {
			list.add(id);
		}
		return list;
	}

	@Override
	public List<String> getIDS() {
		DataObject data = reqData.getData("FWebForm");
		return this.getIDS(data);
	}

	@Override
	public Map<String, String[]> getUploadParameter() {
		return parameterMap;
	}

	@Override
	public Map<String, FileUploaded> getFileUploaded() {
		return fileUploadedMap;
	}

	private void handleUploadFile(HttpServletRequest request) throws ServletException {

		RequestContext requestContext = new ServletRequestContext(request);
		if (ServletFileUpload.isMultipartContent(requestContext)) {
			// 创建基于磁盘的文件工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置直接存储文件的极限大小，一旦超过则写入临时文件以节约内存。默认为 1024 字节
			factory.setSizeThreshold(MAX_UPLOAD_FILE_SIZE);
			// 创建上传处理器，可以处理从单个 HTML 上传的多个上传文件。
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 最大允许上传的文件大小 5M
			upload.setSizeMax(MAX_UPLOAD_FILE_SIZE);
			upload.setHeaderEncoding(DEFAULT_ENCODING);
			try {
				// 处理上传
				List items = upload.parseRequest(requestContext);
				// 由于提交了表单字段信息，需要进行循环区分。
				for (Object item : items) {
					FileItem fileItem = (FileItem) item;
					if (fileItem.isFormField()) {
						// 表单内容
						parameterMap
								.put(fileItem.getFieldName(), new String[] { fileItem.getString(DEFAULT_ENCODING) });
					} else {
						// 如果不是表单内容，取出 multipart。
						// 上传文件路径和文件、扩展名。
						String sourcePath = fileItem.getName();
						// 获取真实文件名
						String fileName = new File(sourcePath).getName();
						// 读到内存成 FileUpload 对象
						FileUploaded fileUploaded = new FileUploaded(fileName, fileItem.get());
						// invocationContext.addFileUploaded(fileItem.getFieldName(),
						// fileUploaded);
						fileUploadedMap.put(fileItem.getFieldName(), fileUploaded);
					}
				}
				Map param = request.getParameterMap();
				if (param != null) {
					parameterMap.putAll(param);
				}

			} catch (FileUploadException e) {
				throw new ServletException("File upload failed!", e);
			} catch (UnsupportedEncodingException e) {
				throw new ServletException("File upload failed,unsupported encoding.", e);
			}
		}
	}
}

/**
 * 
 */
package com.bluecloud.mvc.web.http;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bluecloud.mvc.api.FileUploaded;
import com.bluecloud.mvc.exception.HtmlFragmentRequestException;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.external.beans.FragmentBean;
import com.bluecloud.mvc.web.data.RequestData;

/**
 * @author Hanlu
 * 
 */
public interface HtmlFragmentRequest {

	/**
	 * 
	 * @return
	 */
	String getActionName();

	/**
	 * 
	 * @return
	 */
	String getEventName();

	/**
	 * 
	 * @return
	 */
	HttpServletRequest getHttpServletRequest();

	/**
	 * 
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	FragmentBean getForm(String beanName) throws HtmlFragmentRequestException;

	/**
	 * 
	 * @return
	 */
	RequestData getReqData();

	/**
	 * 
	 * @param requestData
	 */
	void setData(RequestData requestData);

	/**
	 * 
	 * @param beans
	 */
	void setBeans(FragmentBeanRegister beans);

	/**
	 * 
	 * @param name
	 * @param isSet
	 */
	void setEventName(String name, boolean isSet);

	/**
	 * 
	 * @return
	 */
	List<String> getIDS();

	Map<String, String[]> getUploadParameter();

	Map<String, FileUploaded> getFileUploaded();

	/**
	 * 
	 * @param beanName
	 * @return
	 */
	List<String> getIDS(String beanName);
}

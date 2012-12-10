/**
 * 
 */
package com.bluecloud.mvc.web.data;

import javax.servlet.http.HttpServletRequest;

import com.bluecloud.mvc.web.http.BCWebRequest;

/**
 * @author Hanlu
 *
 */
public class AjaxData extends RequestData {

	/**
	 * 
	 */
	public AjaxData() {
	}

	/* (non-Javadoc)
	 * @see com.bluecloud.mvc.web.data.RequestData#doParse(com.bluecloud.mvc.web.http.BCWebRequest)
	 */
	@Override
	protected void doParse(BCWebRequest req) {
		HttpServletRequest request=req.getHttpServletRequest();
		String formName=request.getParameter("FWebForm");
		if(formName!=null&&!formName.trim().equals("")&&!formName.equals("undefined")){
			formName=formName.trim();
			DataObject datas=new DataObject();
			String reqData=request.getParameter("FWebFormData");
			if(reqData!=null){
				String[] formData=reqData.split("#");
				for(String data:formData){
					String[] beanData=data.split("=");
					if(beanData.length>=2){
						datas.add(beanData[0], beanData[1]);
					}else{
						datas.add(beanData[0], null);
					}
				}
				dos.put(formName, datas);
			}
		}
	}

}

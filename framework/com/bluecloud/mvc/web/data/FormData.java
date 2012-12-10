/**
 * 
 */
package com.bluecloud.mvc.web.data;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.bluecloud.mvc.web.http.BCWebRequest;

/**
 * @author Hanlu
 *2012-9-11
 */
public class FormData extends RequestData{

	/**
	 * 
	 */
	public FormData() {
	}
	/*
	 * (non-Javadoc)
	 * @see com.bluecloud.mvc.web.http.RequestData#doParse(com.bluecloud.mvc.web.http.BCWebRequest)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void doParse(BCWebRequest req) {
		Map<?, ?> params=req.getHttpServletRequest().getParameterMap();
		Iterator<?> entries= params.entrySet().iterator();
		DataObject data=new DataObject();
		while(entries.hasNext()){
			Map.Entry entry = (Entry) entries.next();
			data.add(entry.getKey().toString(),((String[]) entry.getValue())[0]);
		}
		HttpServletRequest request=req.getHttpServletRequest();
		String formName=request.getParameter("FWebForm");
		if(formName!=null&&!formName.trim().equals("")&&!formName.equals("undefined")){
			dos.put(formName, data);
		}else{
			dos.put("FWebForm", data);
		}
	}
	

}

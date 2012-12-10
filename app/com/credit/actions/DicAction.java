package com.credit.actions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bluecloud.mvc.exception.FragmentEventException;
import com.bluecloud.mvc.exception.HtmlFragmentException;
import com.bluecloud.mvc.external.FragmentAction;
import com.bluecloud.mvc.external.FragmentBeanRegister;
import com.bluecloud.mvc.web.http.HtmlFragmentRequest;
import com.bluecloud.mvc.web.http.HtmlFragmentResponse;
import com.credit.base.Constants;
import com.credit.base.Pair;
import com.credit.service.rate.DicService;

@Controller("dic")
public class DicAction extends FragmentAction {

	@Autowired
	private DicService dicService;

	public HtmlFragmentResponse city(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String value = request.getHttpServletRequest().getParameter(Constants.REGION_VALUE);
		if (StringUtils.isNotEmpty(value) && !value.equals("-1")) {
			List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>(dicService.getCityMap().size() + 1);
			String all = request.getHttpServletRequest().getParameter("all");
			if (StringUtils.isNotEmpty(all) && all.equals("1")) {
				list.add(new Pair<Integer, String>(-1, "所有城市"));
			}
			list.addAll(dicService.getCityMap().get(value));
			res.getData().asJson(toRegionJson(list));
		}
		return res;
	}

	public HtmlFragmentResponse area(HtmlFragmentRequest request) throws FragmentEventException {
		HtmlFragmentResponse res = this.getResponse();
		String value = request.getHttpServletRequest().getParameter(Constants.REGION_VALUE);
		if (StringUtils.isNotEmpty(value) && !value.equals("-1")) {
			List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>(dicService.getAreaMap().size() + 1);
			String all = request.getHttpServletRequest().getParameter("all");
			if (StringUtils.isNotEmpty(all) && all.equals("1")) {
				list.add(new Pair<Integer, String>(-1, "所有地区"));
			}
			list.addAll(dicService.getAreaMap().get(value));
			res.getData().asJson(toRegionJson(list));
		}
		return res;
	}

	@Override
	public HtmlFragmentResponse execute(HtmlFragmentRequest request) throws HtmlFragmentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FragmentBeanRegister regBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "dic";
	}

	private String toRegionJson(List<Pair<Integer, String>> pairs) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0, size = pairs.size(); i < size; i++) {
			builder.append("[");
			builder.append("\"");
			builder.append(pairs.get(i).getFirst());
			builder.append("\"");
			builder.append(",");
			builder.append("\"");
			builder.append(pairs.get(i).getSecond());
			builder.append("\"");
			builder.append("]");
			if (i + 1 != size) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}

}

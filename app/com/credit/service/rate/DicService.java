package com.credit.service.rate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluecloud.mvc.external.widgets.SelectWidget;
import com.bluecloud.mvc.external.widgets.impl.DefaultSelectWidget;
import com.credit.actions.rate.SelectOperationBean;
import com.credit.base.AreaDic;
import com.credit.base.CityDic;
import com.credit.base.Dictionary;
import com.credit.base.IndustryDic;
import com.credit.base.Pair;
import com.credit.base.ProvinceDic;
import com.credit.base.RateLevelDic;
import com.credit.base.RegionDic;
import com.credit.mapper.rate.AreaDicMapper;
import com.credit.mapper.rate.CityDicMapper;
import com.credit.mapper.rate.IndustryDicMapper;
import com.credit.mapper.rate.ProvinceDicMapper;
import com.credit.mapper.rate.RateLevelDicMapper;

@Service
public class DicService {

	protected List<IndustryDic> dicIndustry;
	protected Map<String, String> mapIndustry;
	protected List<RateLevelDic> dicRateLevel;
	protected Map<String, String> rateLevelMap;
	protected List<ProvinceDic> province;
	protected Map<String, String> mapProvince;

	protected Map<String, List<Pair<Integer, String>>> cityMap = new HashMap<String, List<Pair<Integer, String>>>();
	protected Map<String, String> cityAllMap;
	protected Map<String, List<Pair<Integer, String>>> areaMap = new HashMap<String, List<Pair<Integer, String>>>();
	protected Map<String, String> areaAllMap;

	private List<SelectOperationBean> industryBeans = null;
	private List<SelectOperationBean> provinceBeans = null;

	private static Map<Integer, String> proCertiStatusMap = new HashMap<Integer, String>();
	private static Map<Integer, String> proCertiTypeMap = new HashMap<Integer, String>();

	static {
		proCertiStatusMap.put(1, "有效");
		proCertiStatusMap.put(2, "无效");

		proCertiTypeMap.put(1, "房地产");
		proCertiTypeMap.put(2, "金融");
		proCertiTypeMap.put(3, "IT");
		proCertiTypeMap.put(4, "医疗");
	}

	@Autowired
	private IndustryDicMapper dicIndustryMapper;

	@Autowired
	private CityDicMapper cityDicMapper;

	@Autowired
	private AreaDicMapper areaDicMapper;
	@Autowired
	private ProvinceDicMapper provinceDicMapper;
	@Autowired
	private RateLevelDicMapper rateLevelDicMapper;

	@PostConstruct
	public void loadDic() {
		dicIndustry = dicIndustryMapper.selectAll();
		mapIndustry = convert2Map(dicIndustry);
		province = provinceDicMapper.selectAll();
		mapProvince = convert2Map(province);
		List<CityDic> city = cityDicMapper.selectAll();
		cityAllMap = new HashMap<String, String>(city.size());
		for (RegionDic rDic : city) {
			if (cityMap.containsKey(rDic.getFather())) {
				cityMap.get(rDic.getFather()).add(new Pair<Integer, String>(rDic.getCode(), rDic.getName()));
			} else {
				List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
				list.add(new Pair<Integer, String>(rDic.getCode(), rDic.getName()));
				cityMap.put(rDic.getFather(), list);
			}
			cityAllMap.put(rDic.getCode().toString(), rDic.getName());
		}
		List<AreaDic> area = areaDicMapper.selectAll();
		areaAllMap = new HashMap<String, String>(area.size());
		for (AreaDic rDic : area) {
			if (areaMap.containsKey(rDic.getFather())) {
				areaMap.get(rDic.getFather()).add(new Pair<Integer, String>(rDic.getCode(), rDic.getName()));
			} else {
				List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
				list.add(new Pair<Integer, String>(rDic.getCode(), rDic.getName()));
				areaMap.put(rDic.getFather(), list);
			}
			areaAllMap.put(rDic.getCode().toString(), rDic.getName());
		}

		dicRateLevel = rateLevelDicMapper.selectAll();
		rateLevelMap = convert2Map(dicRateLevel);
	}

	public Map<String, String> getRateLevelMap() {
		return rateLevelMap;
	}

	public List<RateLevelDic> getDicRateLevel() {
		return dicRateLevel;
	}

	private Map<String, String> convert2Map(List<? extends Dictionary> dictionary) {
		Map<String, String> map = new LinkedHashMap<String, String>(dictionary.size());
		for (Dictionary dic : dictionary) {
			map.put(dic.getCode().toString(), dic.getName());
		}
		return map;
	}

	public Map<String, List<Pair<Integer, String>>> getCityMap() {
		return cityMap;
	}

	public Map<String, String> getProvinceMap() {
		return mapProvince;
	}

	public Map<String, List<Pair<Integer, String>>> getAreaMap() {
		return areaMap;
	}

	public List<ProvinceDic> getProvinceList() {
		return province;
	}

	public Map<String, String> getIndustryMap() {
		return mapIndustry;
	}

	public List<IndustryDic> getDicIndustryList() {
		return dicIndustry;
	}

	public Map<String, String> getCityAllMap() {
		return cityAllMap;
	}

	public Map<String, String> getAreaAllMap() {
		return areaAllMap;
	}

	public String getRegion(String provinceId, String cityId, String areaId) {
		String province = getProvinceMap().get(provinceId);
		String city = getCityAllMap().get(cityId);
		String area = getAreaAllMap().get(areaId);
		StringBuilder builder = new StringBuilder();
		builder.append(province);
		builder.append("/");
		builder.append(city);
		builder.append("/");
		builder.append(area);
		return builder.toString();
	}

	public SelectWidget getProvinceSelectWidget(String key) {
		if (provinceBeans == null) {
			provinceBeans = new ArrayList<SelectOperationBean>(getProvinceMap().size());
			for (Entry<String, String> entry : getProvinceMap().entrySet()) {
				SelectOperationBean o = new SelectOperationBean(entry.getKey(), entry.getValue());
				provinceBeans.add(o);
			}
		}
		return new DefaultSelectWidget(key, provinceBeans);
	}

	@SuppressWarnings("unchecked")
	public SelectWidget getCitySelectWidget(String key, String parent) {
		List<Pair<Integer, String>> citys = getCityMap().get(parent);
		if (citys == null) {
			return new DefaultSelectWidget(key, Collections.EMPTY_LIST);
		}
		ArrayList<SelectOperationBean> cityBeans = new ArrayList<SelectOperationBean>(citys.size());
		for (Pair<Integer, String> entry : citys) {
			SelectOperationBean o = new SelectOperationBean(entry.getFirst().toString(), entry.getSecond());
			cityBeans.add(o);
		}
		return new DefaultSelectWidget(key, cityBeans);
	}

	@SuppressWarnings("unchecked")
	public SelectWidget getAreaSelectWidget(String key, String parent) {
		List<Pair<Integer, String>> areas = getAreaMap().get(parent);
		if (areas == null) {
			return new DefaultSelectWidget(key, Collections.EMPTY_LIST);
		}
		ArrayList<SelectOperationBean> areabeans = new ArrayList<SelectOperationBean>(areas.size());
		for (Pair<Integer, String> entry : areas) {
			SelectOperationBean o = new SelectOperationBean(entry.getFirst().toString(), entry.getSecond());
			areabeans.add(o);
		}
		return new DefaultSelectWidget(key, areabeans);
	}

	public SelectWidget getIndustryTypeSelectWidget(String key) {

		if (industryBeans == null) {

			industryBeans = new ArrayList<SelectOperationBean>(getIndustryMap().size());
			for (Entry<String, String> entry : getIndustryMap().entrySet()) {
				SelectOperationBean o = new SelectOperationBean(entry.getKey(), entry.getValue());
				industryBeans.add(o);
			}
		}
		return new DefaultSelectWidget(key, industryBeans);
	}

	public Map<Integer, String> getProCertiStatusMap() {
		return proCertiStatusMap;
	}

	public Map<Integer, String> getProCertiTypeMap() {
		return proCertiTypeMap;
	}
}

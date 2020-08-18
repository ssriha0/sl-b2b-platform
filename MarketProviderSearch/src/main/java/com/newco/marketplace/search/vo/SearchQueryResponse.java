package com.newco.marketplace.search.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchQueryResponse {	
	List<BaseResponseVO>  baseResponseVO;
	Map<String, Object> property;
	
	public SearchQueryResponse(List<BaseResponseVO> baseResponseVO) {
		super();
		this.baseResponseVO = baseResponseVO;
		property = new HashMap<String, Object>();
	}
	
	public List<BaseResponseVO> getBaseResponseVO() {
		return baseResponseVO;
	}
	public void setBaseResponseVO(List<BaseResponseVO> baseResponseVO) {
		this.baseResponseVO = baseResponseVO;
	}

	public Object getProperty(String key) {
		return property.get(key);
	}

	public void setProperty(String key, Object value) {
		this.property.put(key, value);
	}
	
	
}

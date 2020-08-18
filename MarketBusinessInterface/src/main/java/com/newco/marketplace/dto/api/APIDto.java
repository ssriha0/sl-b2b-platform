package com.newco.marketplace.dto.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIDto {
	private List<APIGroup> groupList;
	private Map<Integer, String> idUrlMap;
	private Map<String, Integer> urlIdMap;
	
	public APIDto() {
		idUrlMap = new HashMap<Integer, String>();
		urlIdMap = new HashMap<String, Integer>();
	}
	
	public List<APIGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<APIGroup> groupList) {
		this.groupList = groupList;
	}

	public void putMapping(Integer id, String url) {
		idUrlMap.put(id, url);
		urlIdMap.put(url, id); 
	}
	
	public String getMapping(Integer id) {
		return idUrlMap.get(id);		
	}
	
	public Map<Integer, String> getIdUrlMap() {
		return idUrlMap;
	}

	public void setIdUrlMap(Map<Integer, String> idUrlMap) {
		this.idUrlMap = idUrlMap;
	}

	public Integer getMapping(String url) {
		if (url == null)
			return null;
		return urlIdMap.get(url);		
	}
	
}

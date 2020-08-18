package com.newco.marketplace.dto.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.vo.api.APISecurityVO;



public class APIApplicationDTO implements Serializable{
	private String consumerKey;
	private String consumerName;
	private String consumerPassword;
	private HashMap<String, Boolean> allowedUrlsMap;
	private HashMap<Integer, APIUrlPermission> apiIdMap;
	private HashMap<String, APIGroup> apiGroupMap;
	private List<APIGroup> apiGroupList;
	private boolean internalConsumer;

	/**
	 * Used By UI
	 * @param voList
	 */
	private APIApplicationDTO() {
		this.allowedUrlsMap = new HashMap<String, Boolean>();
		this.apiGroupMap =  new HashMap<String, APIGroup>();
		this.apiIdMap =  new HashMap<Integer, APIUrlPermission>();
	}

	public APIApplicationDTO(APISecurityVO vo) {
		this();
		if (vo != null)  {
			this.consumerKey = vo.getConsumerKey();
			this.consumerPassword = vo.getConsumerPassword();
			this.internalConsumer = (vo.getInternalConsumer() == 0 ? false:true);
			this.consumerName = vo.getConsumerName();
		}
	}

	/**
	 * Used By security layer
	 * @param voList
	 */
	public APIApplicationDTO(List<APISecurityVO> voList) {
		this();
		if (voList != null && voList.size() > 0) {
			this.consumerKey = voList.get(0).getConsumerKey();
			this.consumerPassword = voList.get(0).getConsumerPassword();
			this.internalConsumer = (voList.get(0).getInternalConsumer() == 0 ? false:true);
			for (APISecurityVO vo: voList) {
				String url = vo.getUrl();
				APIUrlPermission pDto = this.apiIdMap.get(vo.getApiId());
				if (pDto == null) {
					pDto = new APIUrlPermission(url, vo.getApiId());
					pDto.setGroupName(vo.getGroupName());
					this.apiIdMap.put(vo.getApiId(), pDto);
					
					String httpMethod = null;
					if (vo.getHttpMethod() != null)
						httpMethod = vo.getHttpMethod().toUpperCase();
					
					  final String key = httpMethod + "/"+ url;
					
					this.allowedUrlsMap.put(key, true);
				}
				pDto.setHttpMethod(vo.getHttpMethod());
			}
		}
	}

	public List<APIGroup>  getApiGroupList() {
		if (apiGroupList == null) {
			apiGroupList= new ArrayList<APIGroup>();
			for (Integer id:apiIdMap.keySet()) {
				APIUrlPermission pDto = apiIdMap.get(id);
				String groupName = pDto.getGroupName();
				APIGroup group = apiGroupMap.get(groupName);
				if (group == null) {
					group = new APIGroup(groupName);
					apiGroupMap.put(groupName, group);
				}
				group.addPermission(apiIdMap.get(id));
			}

			for (String k:apiGroupMap.keySet()) {
				apiGroupList.add(apiGroupMap.get(k));
			}
		}
		return apiGroupList;
	}

	public HashMap<Integer, APIUrlPermission> getApiIdMap() {
		return apiIdMap;
	}

	public void setApiIdMap(HashMap<Integer, APIUrlPermission> apiIdMap) {
		this.apiIdMap = apiIdMap;
	}

	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerPassword() {
		return consumerPassword;
	}
	public void setConsumerPassword(String consumerPassword) {
		this.consumerPassword = consumerPassword;
	}

	public boolean isInternalConsumer() {
		return internalConsumer;
	}
	public void setInternalConsumer(boolean internalConsumer) {
		this.internalConsumer = internalConsumer;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public boolean isUrlAllowed(String url, String httpMethod) {
		String key = getURLKey(url, httpMethod);
		Boolean p = allowedUrlsMap.get(key);
		if (p != null && p == true)
		  return true;
		return false;
	}
	
	public HashMap<String, Boolean> getAllowedUrlsMap() {
		return allowedUrlsMap;
	}

	public void setAllowedUrlsMap(HashMap<String, Boolean> allowedUrlsMap) {
		this.allowedUrlsMap = allowedUrlsMap;
	}
	
	public static String getURLKey(String url, String httpMethod) {
		if (httpMethod != null && url != null)
			return httpMethod.toUpperCase() + "/"+ url;
		else
			return null;
	}
	
	

}

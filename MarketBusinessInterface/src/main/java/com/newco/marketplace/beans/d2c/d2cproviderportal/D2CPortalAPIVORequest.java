package com.newco.marketplace.beans.d2c.d2cproviderportal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.vo.provider.TimeSlotDTO;

public class D2CPortalAPIVORequest {
	private String skuId;
	private String zipCode;
	private String date;
	private String firmId;
	private String buyerId;
	private Map<String, String> requestFromGet;
	private Map<String, String> requestFromPost;
	private Map<String, Object> properties;
	private int count;
	private boolean marketIndexFlag;
	private String corelationId;
	
	private Map<Integer, TimeSlotDTO> prefIdAndStartEndDateTimeSlotMap;
	
	//SLT-2848 START
	private List<Integer> firmIdsFromDb;
	
	public List<Integer> getFirmIdsFromDb() {
		return firmIdsFromDb;
	}

	public void setFirmIdsFromDb(List<Integer> firmIdsFromDb) {
		this.firmIdsFromDb = new ArrayList<>(firmIdsFromDb);
	}
	//SLT-2848 END
	
	public String getCorelationId() {
		return corelationId;
	}

	public void setCorelationId(String corelationId) {
		this.corelationId = corelationId;
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Map<String, String> getRequestFromGet() {
		return requestFromGet;
	}

	public void setRequestFromGet(Map<String, String> requestFromGet) {
		this.requestFromGet = requestFromGet;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Object getProperty(String key) {
		return properties.get(key.toUpperCase());
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, String> getRequestFromPost() {
		return requestFromPost;
	}

	public void setRequestFromPost(Map<String, String> requestFromPost) {
		this.requestFromPost = requestFromPost;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isMarketIndexFlag() {
		return marketIndexFlag;
	}

	public void setMarketIndexFlag(boolean marketIndexFlag) {
		this.marketIndexFlag = marketIndexFlag;
	}
	
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Map<Integer, TimeSlotDTO> getPrefIdAndStartEndDateTimeSlotMap() {
		return prefIdAndStartEndDateTimeSlotMap;
	}

	public void setPrefIdAndStartEndDateTimeSlotMap(Map<Integer, TimeSlotDTO> prefIdAndStartEndDateTimeSlotMap) {
		this.prefIdAndStartEndDateTimeSlotMap = prefIdAndStartEndDateTimeSlotMap;
	}

}

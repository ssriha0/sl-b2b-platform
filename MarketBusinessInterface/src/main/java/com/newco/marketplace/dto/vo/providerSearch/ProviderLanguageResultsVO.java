package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;


public class ProviderLanguageResultsVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8731454166679298640L;
	private int providerResourceId;
	private int providerLanguageId;
	
	
	public int getProviderLanguageId() {
		return providerLanguageId;
	}
	public void setProviderLanguageId(int providerLanguageId) {
		this.providerLanguageId = providerLanguageId;
	}
	public int getProviderResourceId() {
		return providerResourceId;
	}
	public void setProviderResourceId(int providerResourceId) {
		this.providerResourceId = providerResourceId;
	}
	
	@Override
	public String toString() {
	    return ("<ProviderLanguageResultVO>" + providerResourceId + "" + providerLanguageId + "</ProviderLanguageResultVO>");
	        
	}	
}

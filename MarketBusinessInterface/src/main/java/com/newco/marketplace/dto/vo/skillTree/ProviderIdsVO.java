package com.newco.marketplace.dto.vo.skillTree;

import java.util.ArrayList;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class ProviderIdsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4643198629949954047L;
	ArrayList<Integer> providerIds;
	private List<String> performancelevels;
	private Integer spnId; 

	/**
	 * @return ArrayList
	 */
	public ArrayList<Integer> getProviderIds() {
		return providerIds;
	}

	/**
	 * @param skillNodeIds
	 * 
	 */
	public void setProviderIds(ArrayList<Integer> providerIds) {
		this.providerIds = providerIds;
	}

	/**
	 * @return the performancelevels
	 */
	public List<String> getPerformancelevels() {
		return performancelevels;
	}

	/**
	 * @param performancelevels the performancelevels to set
	 */
	public void setPerformancelevels(List<String> performancelevels) {
		this.performancelevels = performancelevels;
	}

	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}

	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
}

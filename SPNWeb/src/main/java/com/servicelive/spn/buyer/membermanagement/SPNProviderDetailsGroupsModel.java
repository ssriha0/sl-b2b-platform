package com.servicelive.spn.buyer.membermanagement;

import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.spn.core.SPNBaseModel;

public class SPNProviderDetailsGroupsModel extends SPNBaseModel {

	private static final long serialVersionUID = 20100202L;

	private List<LookupPerformanceLevel> performanceLevels;
	private Integer networkGroup;
	
	/**
	 * @return the performanceLevels
	 */
	public List<LookupPerformanceLevel> getPerformanceLevels() {
		return performanceLevels;
	}

	/**
	 * @param performanceLevels the performanceLevels to set
	 */
	public void setPerformanceLevels(List<LookupPerformanceLevel> performanceLevels) {
		this.performanceLevels = performanceLevels;
	}

	/**
	 * @return the networkGroup
	 */
	public Integer getNetworkGroup() {
		return networkGroup;
	}

	/**
	 * @param networkGroup the networkGroup to set
	 */
	public void setNetworkGroup(Integer networkGroup) {
		this.networkGroup = networkGroup;
	}

	
	
}

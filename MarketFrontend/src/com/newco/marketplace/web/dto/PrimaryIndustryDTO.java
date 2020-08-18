package com.newco.marketplace.web.dto;

import java.util.List;

/**
 * 
 * @author rranja1
 *
 */
public class PrimaryIndustryDTO  extends SerializedBaseDTO{
	private static final long serialVersionUID = 1L;
	private List<Integer> primaryIndustryIds;

	public List<Integer> getPrimaryIndustryIds() {
		return primaryIndustryIds;
	}

	public void setPrimaryIndustryIds(List<Integer> primaryIndustryIds) {
		this.primaryIndustryIds = primaryIndustryIds;
	}
}

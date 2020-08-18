package com.newco.marketplace.dto.vo.provider;

import java.util.List;

/**
 * This class would act as the VO class for getFirmDetails Service
 * @author neenu_manoharan
 *
 */
public class FirmDetailRequestVO {
	
	private List<String> firmId;
	private List<String> filter;

	public List<String> getFirmId() {
		return firmId;
	}

	public void setFirmId(List<String> firmId) {
		this.firmId = firmId;
	}

	public List<String> getFilter() {
		return filter;
	}

	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	
	

	
}

package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

public class SOCompleteHSRPartsPanelServicesDTO extends SerializedBaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private List<SOCompleteHSRPartsPanelDTO> hsrpartsList = new ArrayList<SOCompleteHSRPartsPanelDTO>();

	public List<SOCompleteHSRPartsPanelDTO> getHsrpartsList() {
		return hsrpartsList;
	}

	public void setHsrpartsList(List<SOCompleteHSRPartsPanelDTO> hsrpartsList) {
		this.hsrpartsList = hsrpartsList;
	}

}

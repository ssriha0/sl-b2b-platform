package com.newco.marketplace.dto.response.providerSkills;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.webservices.base.response.ABaseResponse;



public class GetProviderSkillTreeResponse extends ABaseResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2629769972423665533L;

	private boolean hasError = false;
	
	private ArrayList <SkillNodeVO> serviceResponse;

	public ArrayList<SkillNodeVO> getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(ArrayList<SkillNodeVO> serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

}

package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.webservices.response.WSProcessResponse;

public class UpdatePartsResponse extends WSProcessResponse  {
	
	private List<UpdatePartResponse> responses = new ArrayList<UpdatePartResponse>();

	public List<UpdatePartResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<UpdatePartResponse> responses) {
		this.responses = responses;
	}

}

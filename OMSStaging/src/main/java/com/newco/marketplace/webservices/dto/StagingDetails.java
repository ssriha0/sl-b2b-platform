package com.newco.marketplace.webservices.dto;

import java.io.Serializable;
import java.util.List;

import com.newco.marketplace.webservices.dto.BaseDTO;
import com.newco.marketplace.webservices.dao.ShcOrder;



public class StagingDetails extends BaseDTO implements Serializable {
	
	private static final long serialVersionUID = -5136018315483585453L;
	private List<ShcOrder> stageServiceOrder;
	
	
	public List<ShcOrder> getStageServiceOrder() {
		return stageServiceOrder;
	}

	public void setStageServiceOrder(List<ShcOrder> stageServiceOrder) {
		this.stageServiceOrder = stageServiceOrder;
	}

}

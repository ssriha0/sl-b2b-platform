package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.TripChargeVO;

public class AdminTripChargeDTO extends SerializedBaseDTO
{
	private static final long serialVersionUID = -4633278136973471974L;

	public AdminTripChargeDTO()
	{		
	}
	
	private List<TripChargeVO> tripCharges;

	public List<TripChargeVO> getTripCharges() {
		return tripCharges;
	}

	public void setTripCharges(List<TripChargeVO> tripCharges) {
		this.tripCharges = tripCharges;
	}
	
}

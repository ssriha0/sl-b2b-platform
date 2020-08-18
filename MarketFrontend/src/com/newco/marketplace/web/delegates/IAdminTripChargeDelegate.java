package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.dto.vo.TripChargeVO;

public interface IAdminTripChargeDelegate {

	
	// Lookup items
	public List<TripChargeVO> getAllTripCharges(Integer buyerId);
	
	public void saveTripCharge(TripChargeVO tripCharge);
}

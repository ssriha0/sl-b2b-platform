package com.newco.marketplace.web.delegates;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.business.businessImpl.tripcharge.ITripChargeBO;
import com.newco.marketplace.dto.vo.TripChargeVO;

public class AdminTripChargeDelegate implements IAdminTripChargeDelegate {

	private ITripChargeBO tripChargeBO;
	
	public List<TripChargeVO> getAllTripCharges(Integer buyerId) {
		
		List<TripChargeVO> tripCharges = new ArrayList<TripChargeVO>();
		
		
			try
			{
				if(tripChargeBO != null)
					if(tripChargeBO.getAll(buyerId) != null)
						tripCharges = tripChargeBO.getAll(buyerId);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		return tripCharges;
	}

	public void saveTripCharge(TripChargeVO tripCharge) {

		try
		{
			if(tripChargeBO != null)
				tripChargeBO.save(tripCharge);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ITripChargeBO getTripChargeBO() {
		return tripChargeBO;
	}

	public void setTripChargeBO(ITripChargeBO tripChargeBO) {
		this.tripChargeBO = tripChargeBO;
	}


}

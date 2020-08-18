package com.newco.marketplace.business.businessImpl.tripcharge;

import java.util.List;

import com.newco.marketplace.dto.vo.TripChargeVO;

public interface ITripChargeBO {

	public List<TripChargeVO> getAll(Integer buyerId);
	
	public TripChargeVO get(Integer buyerId, Integer skillNodeId);
	
	public void save(TripChargeVO tripChargeVO);
}

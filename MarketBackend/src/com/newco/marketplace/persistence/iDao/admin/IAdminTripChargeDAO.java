package com.newco.marketplace.persistence.iDao.admin;

import java.util.List;

import com.newco.marketplace.dto.vo.TripChargeVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface IAdminTripChargeDAO
{
	public List<TripChargeVO> getAll( TripChargeVO vo )throws DataServiceException;
	public TripChargeVO get( TripChargeVO vo )throws DataServiceException;
	public void save( TripChargeVO tripCharge ) throws DataServiceException;
	public Object insert(TripChargeVO tripCharge)throws DataServiceException;
	
	/**
	 * selects max(trip_charge) from tripCharge table for given buyer id and mainCategoryIds
	 * 
	 * @param buyerID
	 * @param mainCategoryIds
	 * @return Double trip charge
	 * @throws DataServiceException
	 */
	public Double getHighestByCategoryIds(Integer buyerID, List<Integer> mainCategoryIds) throws DataServiceException;
}

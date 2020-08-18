package com.newco.marketplace.persistence.daoImpl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.TripChargeVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.admin.IAdminTripChargeDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class AdminTripChargeDAO 
	extends ABaseImplDao 
	implements IAdminTripChargeDAO {
	
	private static final Logger logger = Logger.getLogger(AdminTripChargeDAO.class);
	
	public List<TripChargeVO> getAll( TripChargeVO vo )
		throws DataServiceException
	{
		return (List<TripChargeVO>) queryForList( "get_trip_charge_list_with_main_categery_name.query", vo );
	}
	public TripChargeVO get( TripChargeVO vo )
		throws DataServiceException
{
	return (TripChargeVO) queryForObject( "get_trip_charge_with_main_categery_name.query", vo );
}
	public void save( TripChargeVO tripCharge ) 
		throws DataServiceException
	{
		update( "trip_charge.update", tripCharge );	
	}
	public Object insert(TripChargeVO tripCharge)
		throws DataServiceException
	{
		return insert( "trip_charge.insert", tripCharge );
	}
	
	public Double getHighestByCategoryIds(Integer buyerID, List<Integer> mainCategoryIds) throws DataServiceException {
		
		Map<String, Object> highestByCategoryIdsParamMap = new HashMap<String, Object>();
		highestByCategoryIdsParamMap.put("buyerID", buyerID);
		highestByCategoryIdsParamMap.put("mainCategoryIds", mainCategoryIds);
		
		Double highestTripChargeByCategoryIds = null;
		try {
			highestTripChargeByCategoryIds = (Double)queryForObject( "tripChargeMap.getHighestByCategoryIds", highestByCategoryIdsParamMap);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while retrieving highest trip charge by category ids; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
		
		return highestTripChargeByCategoryIds;
	}
}

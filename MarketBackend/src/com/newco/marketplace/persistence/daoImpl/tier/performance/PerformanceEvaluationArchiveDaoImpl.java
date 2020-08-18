package com.newco.marketplace.persistence.daoImpl.tier.performance;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.tier.performance.PerformanceEvaluationFetchDao;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.domain.tier.performance.vo.PerformanceVO;

public class PerformanceEvaluationArchiveDaoImpl extends ABaseImplDao implements PerformanceEvaluationFetchDao{
	
	/**
	 * Get the performance values for the selected criteria
	 * @param providerIds
	 * @param criteriaId
	 * @return
	 * @throws DataServiceException
	 */
	public List<PerformanceVO> getPerformaceValues(List<String> providerIds,String criteria) throws DataServiceException{
		List<PerformanceVO> perfList = new ArrayList<PerformanceVO>();
		try{
			if(Constants.CSAT.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getCSATForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedCountForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_IF_ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedIfRoutedCountForBuyers.query",providerIds);
			}else if(Constants.ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRoutedCountForBuyers.query",providerIds);
			}else if (Constants.COMPLETED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getCompletedRateForBuyers.query",providerIds);
			}else if(Constants.RELEASED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getReleasedCountForBuyers.query",providerIds);
			}else if(Constants.REJECTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRejectedCountForBuyers.query",providerIds);
			}else if(Constants.RESCHEDULED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getRescheduledCountForBuyers.query",providerIds);
			}else if(Constants.RESPONSE.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getResponseRateForBuyers.query",providerIds);
			}else if(Constants.IVR.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getIVRRateForBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_BY_FIRM.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedCountForFirmBuyers.query",providerIds);
			}else if(Constants.ACCEPTED_BY_FIRM_IF_ROUTED.equalsIgnoreCase(criteria)){
				perfList = (List<PerformanceVO>) queryForList("getAcceptedIfRoutedCountForFirmBuyers.query",providerIds);
			}
		}
		catch (Exception e) {
			logger.info("Exception occured in fetching records"+ e.getMessage());
			return perfList;
		}
		return perfList;
	}
	
	
	
}


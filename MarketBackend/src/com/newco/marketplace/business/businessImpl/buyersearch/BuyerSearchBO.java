/**
 * 
 */
package com.newco.marketplace.business.businessImpl.buyersearch;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.iBusiness.buyersearch.IBuyerSearchBO;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO;
import com.newco.marketplace.dto.vo.buyersearch.AgentVisibilityWidgetVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.buyersearch.BuyerSearchDao;
import com.newco.marketplace.utils.TimeUtils;

/**
 * @author HOZA
 *
 */
public class BuyerSearchBO extends ABaseBO implements IBuyerSearchBO {
	
	private BuyerSearchDao buyerSearchDao;

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyersearch.IBuyerSearchBO#getBuyerListForAdmin(com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO, com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public List<BuyerResultForAdminVO> getBuyerListForAdmin(
			BuyerResultForAdminVO searchCriteria, CriteriaMap criteriaMap)
			throws BusinessServiceException {
		String businessName = searchCriteria.getBusinessName();
		if (businessName != null){
			searchCriteria.setBusinessName(businessName + "%");
		}
		String userName = searchCriteria.getUsername();
		if (userName != null){
			searchCriteria.setUsername(userName + "%");
		}
		List<BuyerResultForAdminVO> buyers = null;
		try{
			SortCriteria sortCriteria = (SortCriteria) criteriaMap
							.get(OrderConstants.SORT_CRITERIA_KEY);
			searchCriteria.setSortColumnName(sortCriteria.getSortColumnName());
			searchCriteria.setSortOrder(sortCriteria.getSortOrder());	

			buyers = getBuyerSearchDao().getMatchingBuyersForAdminSearch(searchCriteria);
		}catch(DataServiceException dse){
			logger.error("Could not retrieve a list of matching providers - database error. ", dse);
			throw new BusinessServiceException(
			"Could not retrieve a list of matching providers - database error. ",
			dse);
		}
		return buyers;
	}
	
	
	public BuyerResultForAdminVO getBuyerDetails(BuyerResultForAdminVO searchCriteria) throws BusinessServiceException {
		BuyerResultForAdminVO buyer = null;
		try{
			buyer = getBuyerSearchDao().getBuyerDetails(searchCriteria);
		}catch(DataServiceException dse){
			logger.error("Could not retrieve buyer Info - database error. ", dse);
			throw new BusinessServiceException(	"Could not retrieve buyer Info - database error. ", dse);
		}
		return buyer;
	}

	public AgentVisibilityWidgetVO getSearchResultsForProWidget(String soId,boolean isBuyer,Integer buyerId)
			throws BusinessServiceException {
		AgentVisibilityWidgetVO providerWidget = null;
		try {
			providerWidget = getBuyerSearchDao()
					.getSearchResultsForProWidget(soId,isBuyer,buyerId);
			if (providerWidget != null)
				convertGMTToGivenTimeZone(providerWidget);

		} catch (DataServiceException dse) {
			logger.error("Could not retrieve ResultsForProWidget - database error. ",
					dse);
			throw new BusinessServiceException(
					"Could not retrieve ResultsForProWidget - database error. ", dse);
		}
		return providerWidget;
	}

	private static void convertGMTToGivenTimeZone(
			AgentVisibilityWidgetVO providerWidget) {
		if (providerWidget.getServiceStartDate() != null
				&& providerWidget.getServiceTimeStart() != null) {
			HashMap<String, Object> startDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(providerWidget
							.getServiceStartDate(), providerWidget
							.getServiceTimeStart(), providerWidget
							.getServiceLocationTimezone());
			if (startDateTimeMap != null && !startDateTimeMap.isEmpty()) {
				providerWidget.setServiceStartDate((Timestamp) startDateTimeMap
						.get(OrderConstants.GMT_DATE));
				providerWidget.setServiceTimeStart((String) startDateTimeMap
						.get(OrderConstants.GMT_TIME));
			}
		}

		if (providerWidget.getServiceEndDate() != null
				&& providerWidget.getServiceTimeEnd() != null) {
			HashMap<String, Object> endDateTimeMap = TimeUtils
					.convertGMTToGivenTimeZone(providerWidget
							.getServiceEndDate(), providerWidget
							.getServiceTimeEnd(), providerWidget
							.getServiceLocationTimezone());
			if (endDateTimeMap != null && !endDateTimeMap.isEmpty()) {
				providerWidget.setServiceEndDate((Timestamp) endDateTimeMap
						.get(OrderConstants.GMT_DATE));
				providerWidget.setServiceTimeEnd((String) endDateTimeMap
						.get(OrderConstants.GMT_TIME));
			}
		}
	}

	public BuyerSearchDao getBuyerSearchDao() {
		return buyerSearchDao;
	}

	public void setBuyerSearchDao(BuyerSearchDao buyerSearchDao) {
		this.buyerSearchDao = buyerSearchDao;
	}

}

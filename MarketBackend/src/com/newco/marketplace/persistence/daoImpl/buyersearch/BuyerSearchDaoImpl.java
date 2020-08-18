/**
 * 
 */
package com.newco.marketplace.persistence.daoImpl.buyersearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO;
import com.newco.marketplace.dto.vo.buyersearch.AgentVisibilityWidgetVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.buyersearch.BuyerSearchDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author HOZA
 *
 */
public class BuyerSearchDaoImpl extends ABaseImplDao implements BuyerSearchDao, OrderConstants {
	
	private static final Logger logger = Logger.getLogger(BuyerSearchDaoImpl.class.getName());
	private static final String SORT_COLUMN_KEY = "sort_column_key";
	private static final String SORT_ORDER_KEY = "sort_order_key";

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.buyersearch.BuyerSearchDao#getMatchingProvidersForAdminSearch(com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO)
	 */
	public List<BuyerResultForAdminVO> getMatchingBuyersForAdminSearch(
			BuyerResultForAdminVO buyerCriteria) throws DataServiceException {
		Map<String, String> sort = ensureSortBuyerSearch(buyerCriteria.getSortColumnName(), buyerCriteria.getSortOrder());
		buyerCriteria.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		buyerCriteria.setSortOrder(sort.get(SORT_ORDER_KEY));
		return queryForList("buyerSearchForAdmin.query", buyerCriteria);
		
	}
	
	public BuyerResultForAdminVO getBuyerDetails(BuyerResultForAdminVO buyerCriteria) throws DataServiceException {
		
		return (BuyerResultForAdminVO) queryForObject("AdminWidgetdetails.query", buyerCriteria.getSoId());
		
	}
	
	public AgentVisibilityWidgetVO getSearchResultsForProWidget(String soId,boolean isBuyer,Integer buyerID)
	throws DataServiceException {

		AgentVisibilityWidgetVO agentVisibilityWidget = null;

		if (StringUtils.isNotEmpty(soId)
				&& !StringUtils.equals(soId, OrderConstants.NULL_STRING)) {
			String providerCount=null;
			String providerFirmCount=null;		
			agentVisibilityWidget=new AgentVisibilityWidgetVO();
			agentVisibilityWidget.setStrSoId(soId);
			agentVisibilityWidget.setBuyerInd(isBuyer);
			if (isBuyer){
				agentVisibilityWidget.setBuyerId(buyerID);
				
				}
			 providerCount = (String) queryForObject(
					"AgentVisibilityProviderCount.query", agentVisibilityWidget );
			 providerFirmCount = (String) queryForObject(
					"AgentVisibilityProviderFirmCount.query",agentVisibilityWidget);
			
			if(new Integer(providerCount) > 0) {
				agentVisibilityWidget = (AgentVisibilityWidgetVO) queryForObject(
					"AgentVisibilityProgressBar.query", soId);
			}
			if (agentVisibilityWidget == null) {
				agentVisibilityWidget = new AgentVisibilityWidgetVO();
			}
			agentVisibilityWidget.setStrProviderCount(providerCount);
			agentVisibilityWidget.setStrFirmProviderCount(providerFirmCount);
		}
		return agentVisibilityWidget;
	}
	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @return map 
	 * 
	 */
	//NOT Used at this moment
	protected Map<String, String> ensureSortBuyerSearch (String sortColumn, String sortOrder) {
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
			if("MsId".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_BUYER_ID);
			}else if("MsCompanyType".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_BUYER_COMPANYNAME);
			}else if("MsFundingType".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_FUNDING_TYPE);
			}else if("MsPrimIndustry".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_PRIMARYINDUSTRY);
			}else if("MsMarket".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_MARKET);
			}else if("MsLastActivityTime".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_AS_LASTACTIVITY);
			}else if("MsState".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_STATE);
			}else if("MsZip".equalsIgnoreCase(sortColumn)){
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_MS_ZIP);
			}else {
				sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_BUYER_ID);
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
				sortOrderSet = true;
			}
		} else {
			sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_BUYER_ID);
			sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			sortOrderSet = true;
		}
		
		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ){
				sort.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}

}

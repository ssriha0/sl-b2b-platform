/**
 * 
 */
package com.newco.marketplace.persistence.iDao.buyersearch;

import java.util.List;

import com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO;
import com.newco.marketplace.dto.vo.buyersearch.AgentVisibilityWidgetVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author HOZA
 *
 */
public interface BuyerSearchDao {
	
	public List<BuyerResultForAdminVO> getMatchingBuyersForAdminSearch(BuyerResultForAdminVO buyerCriteria) throws DataServiceException;
	
	public BuyerResultForAdminVO getBuyerDetails(BuyerResultForAdminVO buyerCriteria) throws DataServiceException;
	
	public AgentVisibilityWidgetVO getSearchResultsForProWidget(String soId,boolean isBuyer,Integer buyerID) throws DataServiceException;

}

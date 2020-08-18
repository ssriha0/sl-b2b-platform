/**
 * 
 */
package com.newco.marketplace.business.iBusiness.buyersearch;

import java.util.List;

import com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO;
import com.newco.marketplace.dto.vo.buyersearch.AgentVisibilityWidgetVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * @author HOZA
 *
 */
public interface IBuyerSearchBO {
	public List<BuyerResultForAdminVO> getBuyerListForAdmin(BuyerResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException;
	
	public BuyerResultForAdminVO getBuyerDetails(BuyerResultForAdminVO searchCriteria) throws BusinessServiceException;
	
	public AgentVisibilityWidgetVO getSearchResultsForProWidget(String soId,boolean isBuyer,Integer buyerID) throws BusinessServiceException;
}

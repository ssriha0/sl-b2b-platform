package com.servicelive.spn.dao.auditor;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;

/**
 * @author hoza
 *
 */
public interface SPNAuditorDao {

	/**
	 * fetches the list of notification type for a provider & spn
	 * @param searchVO
	 * @return Integer
	 * @throws 
	 */
	List<Integer> getListOfNotificationType(SPNAuditorSearchResultVO searchVO, Integer buyerId) throws DataServiceException;

	/**
	 * fetches firms based on member compliance status
	 * @param auditorCriteriaVO
	 * @return List<SPNAuditorSearchResultVO>
	 * @throws 
	 */
	List<SPNAuditorSearchResultVO> getProviderFirms(SPNAuditorSearchCriteriaVO auditorCriteriaVO) throws DataServiceException;

	Integer getAdminResourceId(String username) throws DataServiceException;

}

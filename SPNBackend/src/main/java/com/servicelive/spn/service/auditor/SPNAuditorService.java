package com.servicelive.spn.service.auditor;

import java.util.List;

import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

/**
 * Auditor Search for provider firms 
 * @author sldev
 *
 */

public interface SPNAuditorService {	

	//sets the member compliance status of providers
	List<SPNAuditorSearchResultVO> getAuditorMemberStatus(List<SPNAuditorSearchResultVO> searchResultlist, Integer buyerId) throws BusinessServiceException;

	//fetches firms based on member compliance status
	List<SPNAuditorSearchResultVO> getProviderFirms(SPNAuditorSearchCriteriaVO auditorCriteriaVO) throws BusinessServiceException;

	Integer getAdminResourceId(String username) throws BusinessServiceException;
	
}

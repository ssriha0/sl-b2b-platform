package com.servicelive.spn.service.impl.auditor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.servicelive.domain.spn.detached.SPNAuditorSearchCriteriaVO;
import com.servicelive.domain.spn.detached.SPNAuditorSearchResultVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.dao.auditor.SPNAuditorDao;
import com.servicelive.spn.service.auditor.SPNAuditorService;

/**
 * Auditor Search for provider firms 
 * @author sldev
 *
 */

public class SPNAuditorServiceImpl implements SPNAuditorService{
	
	private Log logger = LogFactory.getLog(this.getClass());	
	private SPNAuditorDao spnAuditorDao;

	//sets the member compliance status of providers
	public  List<SPNAuditorSearchResultVO> getAuditorMemberStatus(List<SPNAuditorSearchResultVO> searchResultlist, Integer buyerId) throws BusinessServiceException
	{
		for(SPNAuditorSearchResultVO searchVO:searchResultlist){
			if(null != searchVO && SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER.equals(searchVO.getMembershipStatusId())){
				//get the count of notification type 7
				try{
					List<Integer> notifications = spnAuditorDao.getListOfNotificationType(searchVO, buyerId);
					if(null != notifications && notifications.size() != 0){
						int flag = 0;
						for(Integer type: notifications){
							if(7 == type){
								flag = 1;
								searchVO.setMemberStatus(SPNConstants.ACTION_REQUIRED);
								break;
							}
						}
						if(0 == flag){
							searchVO.setMemberStatus(SPNConstants.ATTENTION_NEEDED);
						}
					}
					
				}catch(DataServiceException e){
					logger.debug("Exception in SPNAuditorServiceImpl.getAuditorMemberStatus due to "+e.getMessage());
					throw new BusinessServiceException(e.getMessage(),e);
				}
					
			}						
		}
		return searchResultlist;	
	}
	

	//fetches firms based on member compliance status
	public List<SPNAuditorSearchResultVO> getProviderFirms(SPNAuditorSearchCriteriaVO auditorCriteriaVO) throws BusinessServiceException
	{
		try{
			return spnAuditorDao.getProviderFirms(auditorCriteriaVO);
		}catch(DataServiceException e){
			logger.debug("Exception in SPNAuditorServiceImpl.getAuditorMemberStatus due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}
	
	public Integer getAdminResourceId(String username)throws BusinessServiceException {
		try{
			return spnAuditorDao.getAdminResourceId(username);
		}catch(DataServiceException e){
			logger.debug("Exception in SPNAuditorServiceImpl.getAdminResourceId due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}
	
	public SPNAuditorDao getSpnAuditorDao() {
		return spnAuditorDao;
	}


	public void setSpnAuditorDao(SPNAuditorDao spnAuditorDao) {
		this.spnAuditorDao = spnAuditorDao;
	}
}

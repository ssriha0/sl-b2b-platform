package com.newco.marketplace.persistence.iDao.mobile;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;


/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * DAO layer for Mobile phase v3.1 API's
 */
public interface INewMobileGenericDao {
	
	/**
	 * @param soId
	 * @param action
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateServiceOrderStatus(String soId, String action)throws DataServiceException;
	
	/**
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public MobileSOReleaseVO fetchAssignmentTypeAndStatus(MobileSOReleaseVO releaseRequestVO)throws DataServiceException;
    /**
     * 
     * @param reasonCode
     * @return
     * @throws DataServiceException
     */
	public String fetchReasonDesc(String reasonCode)throws DataServiceException;

	/**
	 * @Description:Method to check for the Manage User Profile permission
	 * @param providerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean isManageTeamPermission(Integer resourceId) throws DataServiceException;

	/**
	 * @Description:Method to fetch the documentId list 
	 * @param teamMemberVOList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<TeamMemberDocumentVO> getDocumentIdList(List<Integer> resourceIdList)throws DataServiceException;
	
	/**
	 * @param soId
	 * @param action
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateScheduleStatus(String soId, String action)throws DataServiceException;
	/**
	 * @param soId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isAppoinmentIn3Day(String soId) throws DataServiceException;
	
	/**
	 * @param soId
	 * @return ServiceOrder
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws DataServiceException ;
   
}

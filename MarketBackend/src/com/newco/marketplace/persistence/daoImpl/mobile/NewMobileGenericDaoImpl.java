package com.newco.marketplace.persistence.daoImpl.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.mobile.INewMobileGenericDao;
import com.newco.marketplace.vo.mobile.v2_0.MobileSOReleaseVO;
import com.newco.marketplace.vo.provider.TeamMemberDocumentVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Infosys
 * $Revision: 1.0 $Date: 2015/12/16
 * DAO layer for Mobile phase v3.1 API's
 */
public class NewMobileGenericDaoImpl extends ABaseImplDao implements INewMobileGenericDao{
	
	/**
	 * @param soId
	 * @param action
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateServiceOrderStatus(String soId, String action)throws DataServiceException{
		
		String validSoId = null;
		if(StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(action)){
			Map<String,String> parameterMap = new HashMap<String,String>();
			parameterMap.put("soId", soId);
			parameterMap.put("action", action);
			try{
				validSoId = (String) queryForObject("newMobileGeneric.validateServiceOrderStatus.query", parameterMap);
			}catch (Exception e) {
				throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->validateServiceOrderStatus()", e);
			}
			if(StringUtils.isNotBlank(validSoId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	public MobileSOReleaseVO fetchAssignmentTypeAndStatus(MobileSOReleaseVO mobileSOReleaseVO)throws DataServiceException{
		MobileSOReleaseVO releaseVO =  null;
		try {
			releaseVO = (MobileSOReleaseVO) queryForObject("newMobileGeneric.fetchAssignmentTypeAndStatus.query",mobileSOReleaseVO.getSoId());
			if(null != releaseVO){
				mobileSOReleaseVO.setAssignmentType(releaseVO.getAssignmentType());
				mobileSOReleaseVO.setStatusId(releaseVO.getStatusId());
				mobileSOReleaseVO.setReleaseResourceId(releaseVO.getReleaseResourceId());
				mobileSOReleaseVO.setReleaseByName(releaseVO.getReleaseByName());
			}
		} catch (Exception e) {
			throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->fetchAssignmentTypeAndStatus()", e);
		}
		return mobileSOReleaseVO;
	}
	/**
	 * 
	 */
	public String fetchReasonDesc(String reasonCode)throws DataServiceException{
		String reasonDescription=null;
		try{
			reasonDescription=(String) queryForObject("newMobileGeneric.fetchReasonDesc.query",reasonCode);
		}
		catch (Exception e) {
			throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->fetchReasonDesc()", e);
		}
		return reasonDescription;
	}

	/**
	 * @Description:Method to check for the Manage User Profile permission
	 * @param providerId
	 * @return boolean
	 * @throws BusinessServiceException
	 */
	public boolean isManageTeamPermission(Integer resourceId)throws DataServiceException {
		int count=0;
		try{
			count=(Integer) queryForObject("newMobileGeneric.isManageTeamPermission.query",resourceId);
			if(count>0){
				return true;
			}
		}
		catch (Exception e) {
			throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->isManageTeamPermission()", e);
		}
		return false;
	}

	/**
	 * @Description:Method to fetch the documentId list 
	 * @param teamMemberVOList
	 * @return
	 * @throws BusinessServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<TeamMemberDocumentVO> getDocumentIdList(List<Integer> resourceIdList)throws DataServiceException {
		List<TeamMemberDocumentVO> teamMemberDocumentIdList = null;
		try {
			teamMemberDocumentIdList = (List<TeamMemberDocumentVO>) queryForList("newMobileGeneric.fetchDocumentId.query", resourceIdList);
		} catch (Exception exception) {
			throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->getDocumentIdList()", exception);
		}
		return teamMemberDocumentIdList;
	}
	
	/**
	 * @param soId
	 * @param action
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean validateScheduleStatus(String soId, String action)throws DataServiceException{
		
		String validSoId = null;
		if(StringUtils.isNotBlank(soId) && StringUtils.isNotBlank(action)){
			Map<String,String> parameterMap = new HashMap<String,String>();
			parameterMap.put("soId", soId);
			parameterMap.put("action", action);
			try{
				validSoId = (String) queryForObject("newMobileGeneric.validateScheduleStatus.query", parameterMap);
			}catch (Exception e) {
				throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->validateScheduleStatus()", e);
			}
			if(StringUtils.isNotBlank(validSoId)){
				return true;
			}
		}
		return false;
	}
	/**
	 * @param soId
	 * @return boolean
	 * @throws DataServiceException
	 */
	public boolean isAppoinmentIn3Day(String soId) throws DataServiceException{
		String validSoId = null;
		if(StringUtils.isNotBlank(soId)){
			try{
				validSoId = (String) queryForObject("newMobileGeneric.isAppoinmentIn3Day.query", soId);
			}catch (Exception e) {
				throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->isAppoinmentIn3Day()", e);
			}
			if(StringUtils.isNotBlank(validSoId)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param soId
	 * @return ServiceOrder
	 * @throws DataServiceException
	 */
	public ServiceOrder getServiceOrderAssignmentAndScheduleDetails(String soId) throws DataServiceException  {
		ServiceOrder serviceOrder = null;
		try{
			serviceOrder = (ServiceOrder)queryForObject("mobileGeneric.getServiceOrderAssignmentAndScheduleDetails.query", soId);
		}catch (Exception e) {
			throw new DataServiceException("Exception Occured in NewMobileGenericDaoImpl-->getServiceOrderAssignmentAndScheduleDetails():"+e);
		}
		return serviceOrder;
	}
}

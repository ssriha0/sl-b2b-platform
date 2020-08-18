package com.newco.marketplace.business.businessImpl.so.order;

import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.incident.IncidentPartVO;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.incident.IncidentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.IIncidentDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.business.ABaseBO;

public class IncidentBO extends ABaseBO implements IIncidentBO {
	
	private IIncidentDao incidentDao;
	private IServiceOrderBO serviceOrderBO;
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#createIncidentHistoryRecord(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public void createIncidentHistoryRecord(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException {
		try {
			incidentDao.insert(incidentTrackingVO);
		} catch (DataServiceException dsEx) {
			throw new BusinessServiceException("Unexpected error in IncidentBO.createIncidentHistoryRecord()", dsEx);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#getIncidentHistoryRecords(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public List<IncidentTrackingVO> getIncidentHistoryRecords(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException {
		
		List<IncidentTrackingVO> incidentHistoryRecords;
		try {
			incidentHistoryRecords = incidentDao.query(incidentTrackingVO);
		} catch (DataServiceException dsEx) {
			throw new BusinessServiceException("Unexpected error in IncidentBO.incidentResponseSent()", dsEx);
		}
		
		return incidentHistoryRecords;
	}
	
	public List<IncidentEventVO> getIncidentEvents(String clientIncidentID)throws BusinessServiceException
	{
		List<IncidentEventVO> list;
		try
		{
			list =  incidentDao.getIncidentEventsByClientIncidentID(clientIncidentID);
		}
		catch (DataServiceException dsEx)
		{
			throw new BusinessServiceException("Unexpected error in IncidentBO.getIncidentEvents()", dsEx);
		}
		
		return list;
	}

	public List<IncidentPartVO> getIncidentPartsByIncidentID(int incidentID) throws BusinessServiceException
	{
		List<IncidentPartVO> list;
		
		try
		{
			list =  incidentDao.getIncidentPartsByIncidentID(incidentID);
		}
		catch (DataServiceException dsEx)
		{
			throw new BusinessServiceException("Unexpected error in IncidentBO.getIncidentParts()", dsEx);
		}
		
		return list;		
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#processIncidentResponse(com.newco.marketplace.dto.vo.incident.IncidentResponseVO, com.newco.marketplace.auth.SecurityContext)
	 */
	public void processIncidentResponse(IncidentResponseVO incidentResponseVO, SecurityContext securityContext) throws BusinessServiceException {
		try {
			boolean isEmailToBeSent = true;
			ProcessResponse processResponse = serviceOrderBO.processAddNote(incidentResponseVO.getResourceId(), incidentResponseVO.getRoleId(), incidentResponseVO.getSoId(),
					incidentResponseVO.getSubject(), incidentResponseVO.getNoteDescription(), incidentResponseVO.getNoteTypeId(),
					incidentResponseVO.getCreatedByName(), incidentResponseVO.getModifiedBy(), incidentResponseVO.getEntityId(), incidentResponseVO.getPrivateInd(),
					isEmailToBeSent, false, securityContext);
			if (processResponse.isError()) {
				String message = "Processing of incident response failed for client incident: " + incidentResponseVO.getClientIncidentId()
									+ " due to error: " + processResponse.getMessages().get(0);
				throw new BusinessServiceException(message);
			}
		} catch (com.newco.marketplace.exception.core.BusinessServiceException coreBusinessEx) {
			throw new BusinessServiceException("Unexpected error in IncidentBO.processIncidentResponse()", coreBusinessEx);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#getIncidents(com.newco.marketplace.dto.vo.incident.IncidentVO)
	 */
	public List<IncidentVO> getIncidents(IncidentVO incidentVO) throws BusinessServiceException {
		List<IncidentVO> incidents = null;
		try {
			incidents = incidentDao.getIncidents(incidentVO);
		} catch (DataServiceException dsEx) {
			// no action - no business rule defined, exception should have been logged at root
		}
		return incidents;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#getLastTrackingForIncident(java.lang.Integer, com.newco.marketplace.auth.SecurityContext)
	 */
	public IncidentTrackingVO getLastTrackingForIncident(Integer incidentId, SecurityContext securityContext) throws BusinessServiceException {
		IncidentTrackingVO lastTracking =new IncidentTrackingVO();
		try {
			lastTracking.setIncidentId(incidentId);
			lastTracking = incidentDao.getLastIncidentUpdateSentToBuyer(lastTracking);
			
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		
		return lastTracking;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#getLastTrackingForIncidentAndSubstatus(java.lang.Integer, java.lang.String)
	 */
	public IncidentTrackingVO getLastTrackingForIncidentAndSubstatus(Integer incidentId, String subStatusDesc) throws BusinessServiceException {
		IncidentTrackingVO lastTracking =new IncidentTrackingVO();
		try {
			lastTracking.setIncidentId(incidentId);
			lastTracking.setBuyerSubstatusDesc(subStatusDesc);
			lastTracking = incidentDao.getLastIncidentUpdateSentToBuyer(lastTracking);
			
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return lastTracking;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#getLastTrackingForSoAndSubstatus(java.lang.String, java.lang.String)
	 */
	public IncidentTrackingVO getLastTrackingForSoAndSubstatus(String soId,
			String subStatusDesc) throws BusinessServiceException {
		IncidentTrackingVO lastTracking =new IncidentTrackingVO();
		try {
			lastTracking.setSoId(soId);
			lastTracking.setBuyerSubstatusDesc(subStatusDesc);
			lastTracking = incidentDao.getLastIncidentUpdateSentToBuyer(lastTracking);
			
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return lastTracking;
	}
	
	public IncidentTrackingVO getLastTrackingForOutputFile(String outputFileName)
	throws BusinessServiceException {
		IncidentTrackingVO lastTracking =new IncidentTrackingVO();
		try {
			lastTracking.setOutputFile(outputFileName);
			lastTracking = incidentDao.getLastIncidentUpdateSentToBuyer(lastTracking);
			
		} catch (Exception e) {
			throw new BusinessServiceException(e);
		}
		return lastTracking;
	}
		
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO#updateIncidentTrackingWithACKId(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public void updateIncidentTrackingWithACKId(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException {
		try {
			incidentDao.updateIncidentTrackingWithAckId(incidentTrackingVO);
		} catch (Exception e) {
			throw new BusinessServiceException("Exception while updating incident tracking with ACK of incident " + 
					incidentTrackingVO.getClientIncidentId() + " and status '"+ incidentTrackingVO.getBuyerSubstatusDesc()
					+ "'" + e);
		}
	}

	public IIncidentDao getIncidentDao() {
		return incidentDao;
	}
	
	public void setIncidentDao(IIncidentDao incidentDao) {
		this.incidentDao = incidentDao;
	}	
	
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
}

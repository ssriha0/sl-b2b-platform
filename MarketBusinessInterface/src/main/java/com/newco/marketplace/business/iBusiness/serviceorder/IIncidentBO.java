package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.incident.IncidentPartVO;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.incident.IncidentVO;
import com.newco.marketplace.exception.BusinessServiceException;

public interface IIncidentBO {

	/**
	 * Inserts into so_incident_tracking table
	 * 
	 * @param incidentVO
	 * @throws BusinessServiceException in case of an unexpected error
	 */
	public void createIncidentHistoryRecord(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException;
	
	/**
	 * Queries from so_incident_tracking table the incidents based on query criteria in given object
	 * 
	 * @param incidentVO
	 * @return List of IncidentTrackingVO matching given query criteria
	 * @throws BusinessServiceException in case of an unexpected error
	 */
	public List<IncidentTrackingVO> getIncidentHistoryRecords(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException;
	
	public List<IncidentEventVO> getIncidentEvents(String clientIncidentID) throws BusinessServiceException;

	public List<IncidentPartVO> getIncidentPartsByIncidentID(int incidentID) throws BusinessServiceException;
	
	/**
	 * Processes the incident response sent from incident tracker; create notes on given SO; triggers AOP for out file generation
	 * @throws BusinessServiceException - in case of an unexpected error; or business rule failure
	 */
	public void processIncidentResponse(IncidentResponseVO incidentResponseVO, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * Gets the last tracking information for the specified incident
	 * 
	 * @param incidentId
	 * @return IncidentTrackingVO
	 * @throws BusinessServiceException - in case of an unexpected error
	 */
	public IncidentTrackingVO getLastTrackingForIncident(Integer incidentId, SecurityContext securityContext) throws BusinessServiceException;
	
	/**
	 * Returns incidents based on criteria given in input VO
	 * @param incidentVO
	 * @return List of IncidentVO
	 * @throws BusinessServiceException - in case of an unexpected error
	 */
	public List<IncidentVO> getIncidents(IncidentVO incidentVO) throws BusinessServiceException;

	/**
	 * Returns last incident tracking record for a given incident and substatus 
	 * @param incidentId
	 * @param substatusDesc
	 * @return of IncidentVO
	 * @throws BusinessServiceException - in case of an unexpected error
	 */
	public IncidentTrackingVO getLastTrackingForIncidentAndSubstatus(Integer incidentId, String subStatusDesc)throws BusinessServiceException;
	
	/**
	 * Returns the tracking record for the given output file name
	 * @param outputFileName
	 * @return of IncidentVO
	 * @throws BusinessServiceException - in case of an unexpected error
	 */
	public IncidentTrackingVO getLastTrackingForSoAndSubstatus(String soId, String subStatusDesc)throws BusinessServiceException;
	
	public IncidentTrackingVO getLastTrackingForOutputFile(String outputFileName)throws BusinessServiceException;

	public void updateIncidentTrackingWithACKId(IncidentTrackingVO incidentTrackingVO) throws BusinessServiceException;
}
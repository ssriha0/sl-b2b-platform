package com.newco.marketplace.persistence.iDao.so.order;

import java.util.List;

import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.incident.IncidentPartVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.incident.IncidentVO;
import com.newco.marketplace.exception.DataServiceException;

public interface IIncidentDao {

	/**
	 * Inserts into so_incident_tracking table
	 * 
	 * @param incidentVO
	 * @throws DataServiceException in case of a SQL error
	 */
	public void insert(IncidentTrackingVO incidentVO) throws DataServiceException;
	
	/**
	 * Queries from so_incident_tracking table the incidents based on query criteria in given object
	 * 
	 * @param incidentVO
	 * @return List of IncidentTrackingVO matching given query criteria
	 * @throws DataServiceException in case of a SQL error
	 */
	public List<IncidentTrackingVO> query(IncidentTrackingVO incidentVO) throws DataServiceException;
	
	public List<IncidentEventVO> getIncidentEventsByClientIncidentID(String clientIncidentID) throws DataServiceException;
	
	public List<IncidentPartVO> getIncidentPartsByIncidentID(int incidentID) throws DataServiceException;

	/**
	 * Returns the last incident update sent to buyer for the provided condition
	 * @param IncidentTrackingVO containing the condition for selection
	 * @return IncidentTrackingVO The last tracking record
	 * @throws DataServiceException - in case of an error
	 */
	public IncidentTrackingVO getLastIncidentUpdateSentToBuyer(IncidentTrackingVO incidentTracking) throws DataServiceException;
	
	/**
	 * Queries Incident table based on criteria given in input IncidentVO
	 * @param incidentVO
	 * @return List of IncidentVO
	 * @throws DataServiceException - in case of an error
	 */
	public List<IncidentVO> getIncidents(IncidentVO incidentVO) throws DataServiceException;
	
	/**
	 * updates the incidentTracking table with the acknowledgementId
	 * @param incidentTrackingVO
	 * @return void
	 * @throws DataServiceException - in case of an error
	 */
	public void updateIncidentTrackingWithAckId(IncidentTrackingVO incidentTrackingVo) throws DataServiceException;

}
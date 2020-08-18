package com.newco.marketplace.persistence.iDao.so.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.incident.IncidentPartVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.incident.IncidentVO;
import com.newco.marketplace.exception.DataServiceException;
import com.sears.os.dao.impl.ABaseImplDao;

public class IncidentDao extends ABaseImplDao implements IIncidentDao {

	private static final Logger logger = Logger.getLogger(IncidentDao.class);

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IncidentDao#insert(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public void insert(IncidentTrackingVO incidentTrackingVO)
			throws DataServiceException {
		try {
			insert("soIncidentTracking.insert", incidentTrackingVO);
		} catch (Exception ex) {
			String strError = "Unexpected data error in IncidentDaoImpl.insert()";
			logger.error(strError + incidentTrackingVO, ex);
			throw new DataServiceException(strError, ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IncidentDao#query(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public List<IncidentTrackingVO> query(IncidentTrackingVO incidentTrackingVO)
			throws DataServiceException {
		List<IncidentTrackingVO> incidents = null;
		try {
			incidents = (List<IncidentTrackingVO>)queryForList("soIncidentTracking.query", incidentTrackingVO);
		} catch (Exception ex) {
			String strError = "Unexpected data error in IncidentDaoImpl.query()";
			logger.error(strError + incidentTrackingVO, ex);
			throw new DataServiceException(strError, ex);
		}
		return incidents;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IIncidentDao#getLastIncidentUpdateSentToBuyer(com.newco.marketplace.dto.vo.incident.IncidentTrackingVO)
	 */
	public IncidentTrackingVO getLastIncidentUpdateSentToBuyer(IncidentTrackingVO incidentTracking) throws DataServiceException {
		return (IncidentTrackingVO)queryForObject("soIncidentTracking.queryLastResponseSentToBuyer", incidentTracking);
	}

	public void updateIncidentTrackingWithAckId(IncidentTrackingVO incidentTrackingVO) throws DataServiceException {
		
		try {
			update("soIncidentTracking.updateAck",incidentTrackingVO);
		} catch (Exception e) {
			throw new DataServiceException("Unexpected data error in incidentDaoImpl.updateIncidentTrackingWithAckId" + e.toString());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IIncidentDao#getIncidents(com.newco.marketplace.dto.vo.incident.IncidentVO)
	 */
	public List<IncidentVO> getIncidents(IncidentVO incidentVO) throws DataServiceException {
		List<IncidentVO> incidents;
		try {
			incidents = (List<IncidentVO>)queryForList("soIncident.query", incidentVO);
		} catch (Exception ex) {
			String message = "Error while retrieving incidents in IncidentDao.getIncidents";
			logger.error(message, ex);
			throw new DataServiceException(message, ex);
		}
		return incidents;
	}
	

	public List<IncidentEventVO> getIncidentEventsByClientIncidentID(String clientIncidentID) throws DataServiceException
	{
		List<IncidentEventVO> list = null;

		try
		{
			list = queryForList("incidentevent.query", clientIncidentID);
			List<IncidentEventVO> infoNotes = queryForList("incidentnotes.query", clientIncidentID);
			if (list == null) {
				list = new ArrayList<IncidentEventVO>();
			}
			list.addAll(infoNotes);
		}
		catch (Exception ex)
		{

			throw new DataServiceException("Exception occurred in IncidentDao.getIncidentEventsByClientIncidentID()", ex);
		}
		return list;
	}	
	
	public List<IncidentPartVO> getIncidentPartsByIncidentID(int incidentID) throws DataServiceException
	{
		List<IncidentPartVO> list = null;

		try
		{
			list = queryForList("incidentparts.query", new Integer(incidentID));
		}
		catch (Exception ex)
		{

			throw new DataServiceException("Exception occurred in IncidentDao.getIncidentPartsByIncidentID()", ex);
		}
		return list;		
	}

}

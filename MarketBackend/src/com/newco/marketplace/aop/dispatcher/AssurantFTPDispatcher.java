package com.newco.marketplace.aop.dispatcher;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;

public class AssurantFTPDispatcher extends BaseOutFileDispatcher { 
	
	private static final String ASSURANT_END_POINT_KEY = "assurant_FTP_Endpoint";
	private static final Logger logger = Logger.getLogger(AssurantFTPDispatcher.class);
	
	private IApplicationPropertiesDao applicationPropertiesDAO;
	private IIncidentBO incidentBO;
	
	public boolean sendAlert(AlertTask task, String payload, String fileName) {
		
		try {
			if (null == task || null == payload || null == fileName) {
				throw new Exception("Null values for alert tasks - check db");
			}
			// Get FTP Connection Info for ESB Server
			ApplicationPropertiesVO endPoint = applicationPropertiesDAO.query(ASSURANT_END_POINT_KEY);
	
			// FTP the file to ESB Server
			String[] endPointData = endPoint.getAppValue().split(",");
			String user = endPointData[0];
			String password = endPointData[1];
			String address = endPointData[2];
			String directory = endPointData[4];
			logger.debug("Attempting to send payload via scp on server");
		
			IncidentTrackingVO incidentTracking = incidentBO.getLastTrackingForOutputFile(directory + "/" + fileName);
			if (incidentTracking == null) {
				// FTP file to ESB Server (as INPROG file; to avoid partially FTPed file from being picked up)
				sendSCP(user, password, address, payload, fileName + OrderConstants.FILE_EXTENSION_INPROG, directory);
				// Rename file name from filename.OUT.INPROG to filename.OUT
				sendMV(user, password, address, fileName, directory);
				createSoIncidentTrackingRecord(task, fileName, directory);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * @param task
	 * @param fileName
	 * @param directory
	 */
	private void createSoIncidentTrackingRecord(AlertTask task,
			String fileName, String directory) {
		// Insert a record in Incident Tracking table
		IncidentTrackingVO incidentTrackingVO = populateFromTemplateInputValue(task.getTemplateInputValue());
		if (incidentTrackingVO != null) {
			Timestamp fileSentTime = new Timestamp(System.currentTimeMillis());
			incidentTrackingVO.setResponseSentDate(fileSentTime);
			incidentTrackingVO.setOutputFile(directory + "/" + fileName);
			try {
				incidentBO.createIncidentHistoryRecord(incidentTrackingVO);
			} catch (BusinessServiceException bsEx) {
				// Do nothing; no business rule; exception should already have logged at root
			}
		}
		
		// Make sure every Assurant FTPed file has at least one second of gap in created timestamp
		try {
			Thread.sleep(OrderConstants.ONE_SECOND);
		} catch (InterruptedException e) {
			// Do nothing for this unexpected condition; just proceed
		}
	}



	private IncidentTrackingVO populateFromTemplateInputValue(String templateInputValue) {
		
		final String SO_ID = "SO_ID=";
		final String SL_INCIDENT_ID = OrderConstants.SL_INCIDENT_REFERNECE_KEY + "=";
		final String BUYER_SUBSTATUS_ASSOC_ID = OrderConstants.BUYER_SUBSTATUS_ASSOC_ID + "=";
		final String BUYER_SUBSTATUS_DESC = OrderConstants.BUYER_SUBSTATUS_DESC + "=";
		
		int soIdFoundIndex = templateInputValue.indexOf(SO_ID);
		int incidentIdFoundIndex = templateInputValue.indexOf(SL_INCIDENT_ID);
		int buyerSubstatusAssocIdFoundIndex = templateInputValue.indexOf(BUYER_SUBSTATUS_ASSOC_ID);
		int buyerSubstatusDescFoundIndex = templateInputValue.indexOf(BUYER_SUBSTATUS_DESC);
		
		String foundIds = new StringBuilder("soIdFoundIndex [")
			.append(soIdFoundIndex).append("] incidentIdFoundIndex [").append(incidentIdFoundIndex)
			.append("] buyerSubstatusAssocIdFoundIndex [").append(buyerSubstatusAssocIdFoundIndex)
			.append("] buyerSubstatusDescFoundIndex [").append(buyerSubstatusDescFoundIndex).append("]").toString();
		logger.info(foundIds);
		if (soIdFoundIndex == -1 || incidentIdFoundIndex == -1 || buyerSubstatusDescFoundIndex == -1) {
			logger.error("Mandatory information missing for logging tracking record! " + foundIds);
			return null;
		}
		
		int soIdStartIndex = soIdFoundIndex + SO_ID.length();
		int incidentIdStartIndex = incidentIdFoundIndex + SL_INCIDENT_ID.length();
		int buyerSubstatusAssocIdStartIndex = buyerSubstatusAssocIdFoundIndex + BUYER_SUBSTATUS_ASSOC_ID.length();
		int buyerSubstatusDescStartIndex = buyerSubstatusDescFoundIndex + BUYER_SUBSTATUS_DESC.length();
		
		String soId = templateInputValue.substring(soIdStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, soIdStartIndex));
		String incidentId = templateInputValue.substring(incidentIdStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, incidentIdStartIndex));
		String buyerSubstatusAssocId = templateInputValue.substring(buyerSubstatusAssocIdStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, buyerSubstatusAssocIdStartIndex));
		String buyerSubstatusDesc = templateInputValue.substring(buyerSubstatusDescStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, buyerSubstatusDescStartIndex));
		
		IncidentTrackingVO incidentTrackingVO = new IncidentTrackingVO();
		incidentTrackingVO.setSoId(soId);
		incidentTrackingVO.setIncidentId(new Integer(incidentId));
		if (buyerSubstatusAssocIdFoundIndex != -1 && StringUtils.isNotBlank(buyerSubstatusAssocId)) {
			incidentTrackingVO.setBuyerSubstatusAssocId(new Integer(buyerSubstatusAssocId));
		}
		incidentTrackingVO.setBuyerSubstatusDesc(buyerSubstatusDesc);
		
		return incidentTrackingVO;
	}

	public IApplicationPropertiesDao getApplicationPropertiesDAO() {
		return applicationPropertiesDAO;
	}

	public void setApplicationPropertiesDAO(
			IApplicationPropertiesDao applicationPropertiesDAO) {
		this.applicationPropertiesDAO = applicationPropertiesDAO;
	}

	public boolean sendAlert(AlertTask task, String payload) {
		return sendAlert(task, payload, "test.csv");
	}

	public IIncidentBO getIncidentBO() {
		return incidentBO;
	}

	public void setIncidentBO(IIncidentBO incidentBO) {
		this.incidentBO = incidentBO;
	}

}

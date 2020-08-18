/**
 * 
 */
package com.newco.marketplace.aop.dispatcher;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderOutFileTrackingBO;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;

/**
 * @author hoza
 *
 */
public class HSRFTPDispatcher extends BaseOutFileDispatcher {
	private static final String HSR_END_POINT_KEY = "HSR_FTP_ENDPOINT";
	private static final Logger logger = Logger.getLogger(HSRFTPDispatcher.class);
	
	private IApplicationPropertiesDao applicationPropertiesDAO;
	private IServiceOrderOutFileTrackingBO serviceOrderOutFileTrackingBO;
	/* (non-Javadoc)
	 * @see com.newco.marketplace.aop.dispatcher.BaseOutFileDispatcher#sendAlert(com.newco.marketplace.business.businessImpl.alert.AlertTask, java.lang.String)
	 */
	@Override
	public boolean sendAlert(AlertTask task, String payload) {
		return sendAlert( task,  payload, "test.hsr"); 
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.aop.dispatcher.BaseOutFileDispatcher#sendAlert(com.newco.marketplace.business.businessImpl.alert.AlertTask, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendAlert(AlertTask task, String payload, String fileName) {
		try {
			if (null == task || null == payload || null == fileName) {
				throw new Exception("Null values for alert tasks - check db");
			}
			// Get FTP Connection Info for ESB Server
			ApplicationPropertiesVO endPoint = applicationPropertiesDAO.query(HSR_END_POINT_KEY);
	
			// FTP the file to ESB Server
			String[] endPointData = endPoint.getAppValue().split(",");
			String user = endPointData[0];
			String password = endPointData[1];
			String address = endPointData[2];
			String directory = endPointData[4];
			logger.debug("Attempting to send payload via scp: " + fileName + " on server: " + address);
			//We want to make sure we deploy the file than insert the record
			//But what if the insert into the tracking tables fails.. and HSR already pickued up 
			//our file ? Where we will get data from
			ServiceOrderOutFileTrackingVO trackingVO = getPopulatedVOFromTemplateInputValue(address,task.getTemplateInputValue(),payload);
			if(trackingVO != null){
				try {
					// FTP file to ESB Server (as INPROG file; to avoid partially FTPed file from being picked up)
				sendSCP(user, password, address, payload, fileName + OrderConstants.FILE_EXTENSION_INPROG, directory);
					// Rename file name from filename.OUT.INPROG to filename.OUT
				sendMV(user, password, address, fileName, directory);
					//createSoIncidentTrackingRecord(task, fileName, directory);
					
					serviceOrderOutFileTrackingBO.insertTrackingRecord(trackingVO);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					return false;
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	private ServiceOrderOutFileTrackingVO getPopulatedVOFromTemplateInputValue(String address,String templateInputValue, String payLoad) {
		
		final String SOID = AOPConstants.AOP_SO_ID+"=";
		final String FILENAME = "FILENAME"+"=";
	
		
		int soIdFoundIndex = templateInputValue.indexOf(SOID);
		int fileNameFoundIndex = templateInputValue.indexOf(FILENAME);
		
		
		
		if (soIdFoundIndex == -1 || fileNameFoundIndex == -1 ) {
			logger.error("Mandatory information missing for logging tracking record! So ID index found " + soIdFoundIndex + " Filename Index found = "+ fileNameFoundIndex);
			return null;
		}
		
		int soIdStartIndex = soIdFoundIndex + SOID.length();
		int fileNameStartIndex = fileNameFoundIndex + FILENAME.length();
	
		
		String soId = templateInputValue.substring(soIdStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, soIdStartIndex));
		String fileName = templateInputValue.substring(fileNameStartIndex, templateInputValue.indexOf(AOPConstants.AOP_PARAMS_SEPARATATOR, fileNameStartIndex));
		
		if(soId == null && fileName == null ){
			logger.error("Mandatory information SO ID and FILE name is NEEDED  for logging tracking record! We Found SO ID =" + soId);
			return null;
		}
		
		ServiceOrderOutFileTrackingVO trackingVO = new ServiceOrderOutFileTrackingVO();
		trackingVO.setModifiedBy(AOPConstants.AOP_SYSTEM);
		trackingVO.setTransactionType(AOPConstants.AOP_SYSTEM_OUTFILE_TYPE);
		trackingVO.setOutFileName(address+ "/" + fileName);
		trackingVO.setOutFileFragment(payLoad);
		trackingVO.setSoId(soId);
		
		return trackingVO;
	}

	/**
	 * @return the applicationPropertiesDAO
	 */
	public IApplicationPropertiesDao getApplicationPropertiesDAO() {
		return applicationPropertiesDAO;
	}

	/**
	 * @param applicationPropertiesDAO the applicationPropertiesDAO to set
	 */
	public void setApplicationPropertiesDAO(
			IApplicationPropertiesDao applicationPropertiesDAO) {
		this.applicationPropertiesDAO = applicationPropertiesDAO;
	}

	/**
	 * @return the serviceOrderOutFileTrackingBO
	 */
	public IServiceOrderOutFileTrackingBO getServiceOrderOutFileTrackingBO() {
		return serviceOrderOutFileTrackingBO;
	}

	/**
	 * @param serviceOrderOutFileTrackingBO the serviceOrderOutFileTrackingBO to set
	 */
	public void setServiceOrderOutFileTrackingBO(
			IServiceOrderOutFileTrackingBO serviceOrderOutFileTrackingBO) {
		this.serviceOrderOutFileTrackingBO = serviceOrderOutFileTrackingBO;
	}

}

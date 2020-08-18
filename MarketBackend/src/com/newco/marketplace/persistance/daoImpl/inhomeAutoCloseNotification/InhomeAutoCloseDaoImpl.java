package com.newco.marketplace.persistance.daoImpl.inhomeAutoCloseNotification;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;
import com.newco.marketplace.persistence.iDao.inhomeAutoCloseNotification.IInhomeAutoCloseDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class InhomeAutoCloseDaoImpl extends ABaseImplDao implements IInhomeAutoCloseDao {
	
	private static final Logger LOGGER = Logger.getLogger(InhomeAutoCloseDaoImpl.class.getName());
	//Method to fetch the records in the table so_inhome_auto_close with failure status.
		@SuppressWarnings({ "unchecked"})
		public List<InHomeAutoCloseVO> fetchRecords() throws DataServiceException {
			
			List<InHomeAutoCloseVO> notificationVOList=null;
			try{
				notificationVOList = (List<InHomeAutoCloseVO>)queryForList("getListOfFailureRecords.query");
			}
			catch(Exception e){
				LOGGER.info("Exception occurred in fetchRecords: "+e.getMessage());
				throw new DataServiceException("Exception occurred in fetchRecords: "+e.getMessage(),e);
			}
			return notificationVOList;
		}
	
		//Get property value for a corresponding key from application_constants table. 
		public String getRecipientIdFromDB(String appKey)throws DataServiceException{
			String value = null;
			try{
				value=(String) queryForObject("getRecipientId.query",appKey);
			}
			catch(Exception e){
				LOGGER.info("Exception occurred in getPropertyFromDB: "+e.getMessage());
				throw new DataServiceException("Exception occurred in getPropertyFromDB: "+e.getMessage(),e);
			}
			return value;
		}
		
		
		public void setEmailIndicator(List<String> failureList) throws DataServiceException{
			try{
				update("updateAutoCloseEmailInd.query", failureList);
			}catch(Exception e){
				LOGGER.info("Exception occurred in setEmailIndicator: "+e.getMessage());
			}
		}

}

package com.newco.marketplace.persistence.daoImpl.inhomeautoclose;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;
import com.newco.marketplace.persistence.iDao.inhomeautoclose.InHomeAutoCloseProcessDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class InHomeAutoCloseProcessDaoImpl extends ABaseImplDao implements InHomeAutoCloseProcessDao {
	
	private static final Logger LOGGER = Logger.getLogger(InHomeAutoCloseProcessDaoImpl.class.getName());
	
	 //get the list of orders to be autoclosed from so_inhome_autoclose table	
	public List<InHomeAutoCloseProcessVO> getListOfServiceOrdersToAutoClose() throws DataServiceException{
		List<InHomeAutoCloseProcessVO> serviceOrderList = new ArrayList<InHomeAutoCloseProcessVO>();
		try{
			LOGGER.info("inside InHomeAutoCloseProcessDaoImpl");

			serviceOrderList =(List<InHomeAutoCloseProcessVO>) queryForList("getListOfServiceOrdersToAutoClose.query",null);			
		}
		catch(Exception e){
			logger.info("Exception occurred in getListOfServiceOrdersToAutoClose"+e);
			throw new DataServiceException("Exception occurred in getListOfServiceOrdersToAutoClose :"+e.getMessage(),e);
		} 
		return serviceOrderList;
	}
		
	// get the number of retries value from application properties
	public HashMap<String,String>getAutoCloseConstants() throws DataServiceException{
		 HashMap<String, String> autoCloseConstantsMap = new HashMap <String, String>();
			try{
				LOGGER.info("inside InHomeAutoCloseProcessDaoImpl");

				autoCloseConstantsMap = (HashMap<String,  String>) queryForMap("getAutoCloseConstants.query",null,"app_constant_key","app_constant_value");			
			}
			catch(Exception e){
				logger.info("Exception occurred in getAutoCloseConstants"+e);
				throw new DataServiceException("Exception occurred in getAutoCloseConstants :"+e.getMessage(),e);
			} 
			return autoCloseConstantsMap;
		
	}
	
	// update the no of reties and status in so_inhome_autoclose  table.
	public void updateAutoCloseInfo(InHomeAutoCloseProcessVO inHomeAutoCloseProcessVO) throws DataServiceException{
			try{
				LOGGER.info("inside InHomeAutoCloseProcessDaoImpl");
				update("updateAutoCloseInfo.query",inHomeAutoCloseProcessVO);			
			}
			catch(Exception e){
				logger.info("Exception occurred in updateAutoCloseInfo method"+e);
				throw new DataServiceException("Exception occurred in updateAutoCloseInfo method :"+e.getMessage(),e);
			} 
			
		
	}
	
	// check whether any of the finance transaction is pending for a service order.
	public Integer countOfPendingFinanceTransaction(String soId) throws DataServiceException{
		
			try {
				return (Integer) queryForObject("countOfFullfillmentRecords.query", soId);
					
		} 
		catch(Exception e){
			logger.info("Exception occurred in isPendingFinanceTransaction method"+e);
			throw new DataServiceException("Exception occurred in isPendingFinanceTransaction method :"+e.getMessage(),e);
		} 
		
	}

	
	
	//To insert Note into so_notes 
	public void insertAddNote(ServiceOrderNote note)throws DataServiceException {
			try {
			insert("soNoteFailure.insert", note);
			} 
			catch(Exception e){
				logger.info("Exception occurred in insertAddNote method"+e);
				throw new DataServiceException("Exception occurred in insertAddNote method :"+e.getMessage(),e);
			} 
			
		}
	//update the selected entries for auto close as in progress in so_inhome_autoclose table
	public void updateAsInprogress(List<Integer> autoCloseIds) throws DataServiceException{
		try {
			update("updateAsInprogress.update",autoCloseIds);
			} 
			catch(Exception e){
				logger.info("Exception occurred in updateAsInprogress method"+e);
				throw new DataServiceException("Exception occurred in updateAsInprogress method :"+e.getMessage(),e);
			} 
		
	}

	
	
				

}

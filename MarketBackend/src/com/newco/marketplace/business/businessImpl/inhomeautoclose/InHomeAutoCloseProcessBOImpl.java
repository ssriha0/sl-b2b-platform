package com.newco.marketplace.business.businessImpl.inhomeautoclose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclose.InHomeAutoCloseProcessBO;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;
import com.newco.marketplace.persistence.iDao.inhomeautoclose.InHomeAutoCloseProcessDao;

public class InHomeAutoCloseProcessBOImpl implements InHomeAutoCloseProcessBO {
	private InHomeAutoCloseProcessDao inhomeAutoCloseProcessDao;
	
	private static final Logger LOGGER = Logger.getLogger(InHomeAutoCloseProcessBOImpl.class.getName());
	
	
	//get the list of orders to be autoclosed
	public List<InHomeAutoCloseProcessVO> getListOfServiceOrdersToAutoClose() throws BusinessServiceException{
		try {
			LOGGER.info("inside InHomeAutoCloseProcessBOImpl");
			List<InHomeAutoCloseProcessVO> serviceOrderList= new ArrayList<InHomeAutoCloseProcessVO>();
			serviceOrderList=inhomeAutoCloseProcessDao.getListOfServiceOrdersToAutoClose();
			return serviceOrderList;
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in getListOfServiceOrdersToAutoClose method  " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception getListOfServiceOrdersToAutoClose method");
		}
	}

	
	// get the number of retries value from application properties
	public HashMap<String,String>getAutoCloseConstants() throws BusinessServiceException{
		try {
			LOGGER.info("inside InHomeAutoCloseProcessBOImpl");
			HashMap<String, String> autoCloseConstantsMap= new HashMap<String, String>();
			autoCloseConstantsMap=inhomeAutoCloseProcessDao.getAutoCloseConstants();
			return autoCloseConstantsMap;
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in  getAutoCloseConstants method " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception getListOfServiceOrdersToAutoClose method");
		}
	}
 
	
	
	// update the no of reties and status in so_inhome_autoclose  table.
	public void updateAutoCloseInfo(InHomeAutoCloseProcessVO inHomeAutoCloseProcessVO) throws BusinessServiceException{
		try {
			LOGGER.info("inside InHomeAutoCloseProcessBOImpl");
			inhomeAutoCloseProcessDao.updateAutoCloseInfo(inHomeAutoCloseProcessVO);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in  updateAutoCloseInfo method " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in updateAutoCloseInfo method");
		}
	}

	// check whether any of the finance transaction is pending.
	public Boolean isPendingFinanceTransaction(String soId) throws BusinessServiceException{
		Boolean vlEntryExists = false;
		try{
			Integer countOfRecords = inhomeAutoCloseProcessDao.countOfPendingFinanceTransaction(soId); 
			if (countOfRecords != null &&  countOfRecords.intValue() > 0 )
			{
				vlEntryExists = true;	
			}
			return vlEntryExists;
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in  isPendingFinanceTransaction method " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in isPendingFinanceTransaction method");
		}
		
	}
  
	// add so note for failure in autoclose
	public void insertAddNote(ServiceOrderNote note) throws BusinessServiceException{		 
		try{
		inhomeAutoCloseProcessDao.insertAddNote(note);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in  insertAddNote method " + dse.getMessage(), dse);
			throw new BusinessServiceException("Exception in insertAddNote method");
		}
		
	}
	 //update the selected entries for auto close as in progress
	public void updateAsInprogress(List<Integer> autoCloseIds) throws BusinessServiceException{
			try{
				if(null!=autoCloseIds && autoCloseIds.size()>0){
		inhomeAutoCloseProcessDao.updateAsInprogress(autoCloseIds);
				}
				} catch (DataServiceException dse) {
			LOGGER.error("Exception in  updateAsInprogress method " + dse.getMessage(), dse);
					throw new BusinessServiceException("Exception in updateAsInprogress method");
				}			
		}


	public InHomeAutoCloseProcessDao getInhomeAutoCloseProcessDao() {
		return inhomeAutoCloseProcessDao;
	}

	public void setInhomeAutoCloseProcessDao(
			InHomeAutoCloseProcessDao inhomeAutoCloseProcessDao) {
		this.inhomeAutoCloseProcessDao = inhomeAutoCloseProcessDao;
	}
	
	
	
	
	

}

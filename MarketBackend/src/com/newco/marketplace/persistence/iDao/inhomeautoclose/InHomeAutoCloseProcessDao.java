package com.newco.marketplace.persistence.iDao.inhomeautoclose;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;


import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;

public interface InHomeAutoCloseProcessDao {
	

	
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	 //get the list of orders to be autoclosed from so_inhome_autoclose table
	public List<InHomeAutoCloseProcessVO> getListOfServiceOrdersToAutoClose() throws DataServiceException;
	// get the number of retries value from application properties
	public HashMap<String,String>getAutoCloseConstants() throws DataServiceException;
	// update the no of reties and status in so_inhome_autoclose  table.
	public void updateAutoCloseInfo(InHomeAutoCloseProcessVO inHomeAutoCloseProcessVO) throws DataServiceException;
	// check whether any of the finance transaction is pending for a service order.
	public Integer countOfPendingFinanceTransaction(String soId) throws DataServiceException;	
	//To insert Note into so_notes 
	public void insertAddNote(ServiceOrderNote note) throws DataServiceException;
	//update the selected entries for auto close as in progress in so_inhome_autoclose table
	public void updateAsInprogress(List<Integer> autoCloseIds) throws DataServiceException;


}

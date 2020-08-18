package com.newco.marketplace.inhomeautoclose;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeautoclose.vo.InHomeAutoCloseProcessVO;


public interface InHomeAutoCloseProcessBO {

	//get the list of orders to be autoclosed
	public List<InHomeAutoCloseProcessVO> getListOfServiceOrdersToAutoClose() throws BusinessServiceException;
	// get the number of retries value from application properties
	public HashMap<String,String>getAutoCloseConstants() throws BusinessServiceException;
	// update the no of reties and status in so_inhome_autoclose  table.
	public void updateAutoCloseInfo(InHomeAutoCloseProcessVO inHomeAutoCloseProcessVO) throws BusinessServiceException;
	// check whether any of the finance transaction is pending.
	public Boolean isPendingFinanceTransaction(String soId) throws BusinessServiceException;
	// add so note for failure in autoclose
	public void insertAddNote(ServiceOrderNote note) throws BusinessServiceException;
	  //update the selected entries for auto close as in progress
	public void updateAsInprogress(List<Integer> autoCloseIds) throws BusinessServiceException;

	
}

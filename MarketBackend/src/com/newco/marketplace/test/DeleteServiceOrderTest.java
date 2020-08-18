package com.newco.marketplace.test;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

public class DeleteServiceOrderTest extends SLBase {
	
	public DeleteServiceOrderTest() {
		super();
	}

	public void testDeleteSingleServiceOrder() throws DataServiceException, BusinessServiceException{
		String soId="152-3386-5058-18";
		String groupId = null;
		String status=_iServiceOrderDao.deleteServiceOrder(soId,groupId);
		display("status is " + status);
		//this.assertEquals("true", status);
		SecurityContext securityContext = null;
		processResponse=_iServiceOrderBO.deleteDraftSO(soId,securityContext);
		display("status is " + processResponse.getCode());
	}

	public void testDeletGroupedServiceOrder() throws DataServiceException, BusinessServiceException{
		String soId="180-1751-4152-14";
		String groupId = "165-1083-9747-10";
		String status=_iServiceOrderDao.deleteServiceOrder(soId,groupId);
		display("status is " + status);
		//this.assertEquals("true", status);
		SecurityContext securityContext = null;
		processResponse=_iServiceOrderBO.deleteDraftSO(soId,securityContext);
		display("status is " + processResponse.getCode());
	}
	
}

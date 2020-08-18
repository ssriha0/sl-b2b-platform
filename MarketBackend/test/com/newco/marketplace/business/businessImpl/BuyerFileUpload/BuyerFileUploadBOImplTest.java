package com.newco.marketplace.business.businessImpl.BuyerFileUpload;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerFileUpload.IBuyerFileUploadDao;
import static org.mockito.Mockito.*;
public class BuyerFileUploadBOImplTest  {

	private IBuyerFileUploadDao buyerFileUploadDao;
	private BuyerFileUploadBOImpl fileuploadBO;
	
@Test
public void testGetValidZipCode() throws DataServiceException{
	
	String zip  ="60193";
	boolean isValid = false;
	fileuploadBO = new BuyerFileUploadBOImpl();
	buyerFileUploadDao=mock(IBuyerFileUploadDao.class);
	fileuploadBO.setBuyerFileUploadDao(buyerFileUploadDao);
	try {
			when(buyerFileUploadDao.getValidZipCode(zip)).thenReturn(true);
			isValid = fileuploadBO.getValidZipCode(zip);
		
	} catch (BusinessServiceException e) {
		e.printStackTrace();
	}
	assertTrue(isValid);
}
}

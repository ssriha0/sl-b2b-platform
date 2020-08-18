package com.servicelive.lookup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.servicelive.common.BusinessServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.lookup.LookupBO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.lookup.dao.ILookupSupplierDao;

public class LookupBOTest {
   private static Logger logger = Logger.getLogger(LookupBOTest.class);
   private ILookupSupplierDao lookupSupplierDao;
   private IAccountDao accountDao;
   private LookupBO lookupBO;
   @Test
   public void lookupBuyerTest(){
	   lookupBO = new LookupBO();
	   lookupSupplierDao = mock(ILookupSupplierDao.class);
	   accountDao = mock(IAccountDao.class);
	   lookupBO.setLookupSupplierDao(lookupSupplierDao);
	   lookupBO.setAccountDao(accountDao);
	   SLAccountVO buyerLookupVO = new SLAccountVO() ;
	    try {
	    	when(lookupBO.getActiveAccountDetails(512353L)).thenReturn(buyerLookupVO);
		  }catch (BusinessServiceException e){
			logger.error("Exception in getting Active Account Details:"+e.getMessage());
		   }
	     Assert.assertNotNull("Requested Account Deatils not found",buyerLookupVO);
      }
   }


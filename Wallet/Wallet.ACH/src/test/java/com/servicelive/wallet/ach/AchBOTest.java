package com.servicelive.wallet.ach;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.ach.dao.IAchBatchRequestDao;
import com.servicelive.wallet.ach.vo.AutoFundingVO;

public class AchBOTest{
	private AchBO bo;
	private IAchBatchRequestDao achBatchRequestDao;
	
	@Test
	public void testIsEntityAutoFunded(){
		bo = new AchBO();
		achBatchRequestDao=mock(IAchBatchRequestDao.class);
		bo.setAchBatchRequestDao(achBatchRequestDao);
		Long entityId = 10202L;
		Integer entityTypeId = 20;
		
		boolean isEntityAutoFunded = false;
		AutoFundingVO autoFundingVO = new AutoFundingVO();
		try {
			when(achBatchRequestDao.getAutoFundingVO(entityId, entityTypeId)).thenReturn(autoFundingVO);
			isEntityAutoFunded = bo.isEntityAutoFunded(entityId, entityTypeId);
		} catch (SLBusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(isEntityAutoFunded);
	}

}

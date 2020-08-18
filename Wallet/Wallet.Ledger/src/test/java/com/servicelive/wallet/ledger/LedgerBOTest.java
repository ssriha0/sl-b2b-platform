package com.servicelive.wallet.ledger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.ledger.dao.ITransactionDao;

public class LedgerBOTest{

private LedgerBO bo;
private ITransactionDao transactionDao;

@Test
public void testGetBuyerTotalDeposit(){
	bo = new LedgerBO();
	transactionDao = mock(ITransactionDao.class);
	bo.setTransactionDao(transactionDao);
	Long buyerId = 1000L;
	double amount = 0.00;
	
	try {
		when(transactionDao.getTotalDepositByEntityId(buyerId, 10)).thenReturn(2456.00);
		amount = bo.getBuyerTotalDeposit(buyerId);
	} catch (DataServiceException e) {
		e.printStackTrace();
	}
	 catch (SLBusinessServiceException e) {
		e.printStackTrace();
	}
	Assert.assertEquals(2456.00, amount);
}

}

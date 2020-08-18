/**
 * 
 */
package com.newco.test;

import org.junit.Test;

import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.test.BaseSpringTestCase;

/**
 * @author nsanzer
 * 
 */
public class AccountingTransactionMananagementBOTest extends BaseSpringTestCase {
	private ILedgerFacilityBO transBo;
	private ServiceOrderDao serviceOrderDao;
	/**
	 * Test method for
	 * {@link com.newco.marketplace.business.businessImpl.ledger.AccountingTransactionMananagementBO#closeServiceOrderLedgerAction(com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO)}
	 * .
	 * @throws BusinessServiceException 
	 * @throws DataServiceException 
	 */
	@Test
	public void testCloseServiceOrderLedgerAction() throws BusinessServiceException, DataServiceException {
		transBo = (ILedgerFacilityBO)getApplicationContext().getBean("accountingTransactionManagementBO");
		serviceOrderDao = (ServiceOrderDao)getApplicationContext().getBean("serviceOrderDao");
		ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder("555-3731-8037-17");
		MarketPlaceTransactionVO marketVO = new MarketPlaceTransactionVO();
		marketVO.setServiceOrder(serviceOrder);
		marketVO.setUserTypeID(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
		marketVO.setBuyerID(1000);
		marketVO.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_RELEASE_SO_PAYMENT);
		marketVO.setVendorId(30070);
		marketVO.setUserName("nsanzer");
		marketVO.setAccountId(new Long(319083913));
		marketVO.setAutoACHInd("true");
		// Transaction Band-aid: don't enter close transactions if they already exist for this SO
		boolean	blnPaySuccess = transBo.closeServiceOrderLedgerAction(marketVO);
		assertEquals("", "");
	}

	public ILedgerFacilityBO getTransBo() {
		return transBo;
	}

	public void setTransBo(ILedgerFacilityBO transBo) {
		this.transBo = transBo;
	}

}

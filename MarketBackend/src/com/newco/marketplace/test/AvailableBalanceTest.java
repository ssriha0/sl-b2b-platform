package com.newco.marketplace.test;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public class AvailableBalanceTest extends BaseSpringTestCase{
	public void testAvailableBalance() {
		System.out.println("loading applicationContext.xml");
		try {
			ILedgerFacilityBO iLedgerFacilityBO = (ILedgerFacilityBO)getApplicationContext().getBean("accountingTransactionManagementBO");
			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(1000);
			avo.setRoleType("BUYER");
//			double availableBalance = iLedgerFacilityBO.getAvailableBalance(avo);
//			double currentBalance = iLedgerFacilityBO.getCurrentBalance(avo);
//			System.out.println("Available Balance checking="+availableBalance  + "Current Balance=="+currentBalance);

			checkAvailableFunds(avo);
//			checkRouteOrphanOrderTest(avo);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void checkRouteOrphanOrderTest(AjaxCacheVO avo) throws com.newco.marketplace.exception.BusinessServiceException, BusinessServiceException {
		//Check Enough Funds	
		IRouteOrderGroupBO routeGroup = (IRouteOrderGroupBO)MPSpringLoaderPlugIn.getCtx().getBean("routeOrderGroupBO");
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBO");
		ServiceOrderSearchResultsVO orphanResultVO = new ServiceOrderSearchResultsVO();
		orphanResultVO.setSoId("152-9114-4685-15");
		ServiceOrder so = serviceOrderBO.getServiceOrder("152-9114-4685-15");
		routeGroup.routeIndividualOrder(orphanResultVO, null);
		System.out.println("OrderRouted");
	}

	private static void checkAvailableFunds(AjaxCacheVO avo)
			throws BusinessServiceException {
		//Check Enough Funds	
		IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBO");
		ServiceOrder so = serviceOrderBO.getServiceOrder("189-9090-5136-80");
		FundingVO fundVo = serviceOrderBO.checkBuyerFundsForIncreasedSpendLimit(so, 1000);
		System.out.println("Enough funds = "+fundVo.isEnoughFunds()  + "\nAmount to fund ="+fundVo.getAmtToFund() + "\nAvailable balance ="+fundVo.getAvailableBalance());
		
	}
}

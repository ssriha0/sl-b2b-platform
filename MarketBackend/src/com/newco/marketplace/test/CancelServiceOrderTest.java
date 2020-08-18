package com.newco.marketplace.test;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class CancelServiceOrderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			ProcessResponse response = null;
			IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("soBOAOP");
			ILedgerFacilityBO iLedgerFacilityBO = null;
			int statusCd = 150;
			double availableBalance = 0.0;
			double currentBalance = 0.0;
			SecurityContext securityContext = null;	
			try{
			if (statusCd == 150)
			{
				iLedgerFacilityBO = (ILedgerFacilityBO)MPSpringLoaderPlugIn.getCtx().getBean("accountingTransactionManagementBO");
				AjaxCacheVO avo = new AjaxCacheVO();
				avo.setCompanyId(3);
				avo.setRoleType("BUYER");
				availableBalance = iLedgerFacilityBO.getAvailableBalance(avo);
				currentBalance = iLedgerFacilityBO.getCurrentBalance(avo);
				System.out.println("availableBalance : " + availableBalance);
				System.out.println("currentBalance : " + currentBalance);
				
				
				response = serviceOrderBO.processCancelSOInAccepted(111, "131-8183-0864-10","Cancel Test", "Ujwala Sawant",securityContext);
				availableBalance = iLedgerFacilityBO.getAvailableBalance(avo);
				currentBalance = iLedgerFacilityBO.getCurrentBalance(avo);
				System.out.println("availableBalance : " + availableBalance);
				System.out.println("currentBalance : " + currentBalance);
			}
			else{
				System.out.println("in else : ");
				response = serviceOrderBO.processCancelRequestInActive(111, "131-8067-5878-94", 5434.00, "Ujwala Sawant",securityContext);
			}
				
			
			ArrayList arr = (ArrayList)response.getMessages();
			for (int i=0;i<arr.size();i++){
				System.out.println("message : " + arr.get(i));
			}
			}catch(Exception ex){
				System.out.println("Example : " + ex.getMessage());
				ex.printStackTrace();
			}
	}
}

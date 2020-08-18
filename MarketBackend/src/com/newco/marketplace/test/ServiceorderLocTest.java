package com.newco.marketplace.test;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.buyer.BuyerBOImpl;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.exception.core.DataServiceException;

public class ServiceorderLocTest {

	/**
	 * @param args
	 * @throws DataServiceException 
	 */
	public static void main(String[] args) {
		Buyer buyer=new Buyer();
		buyer.setBuyerId(110);
		
		BuyerResource buyerResource = new BuyerResource();
		buyerResource.setBuyerId(110);
		buyerResource.setContactId(11287);
		BuyerBOImpl buyerbo = (BuyerBOImpl)MPSpringLoaderPlugIn.getCtx().getBean("buyerBo");
	
			ContactLocationVO contLoc = null;
		
				contLoc = buyerbo.getBuyerContactLocationVO(110);
				System.out.println("list size contact:" + contLoc.getListContact().size());
				System.out.println("primary mobile no:" + contLoc.getBuyerPrimaryContact().getCellNo());
				//System.out.println("list size location:" + contLoc.getListLocation().size());
				System.out.println("business name:" + contLoc.getBuyerPrimaryContact().getBusinessName());
			
				contLoc = buyerbo.getBuyerResContactLocationVO(110, 11287);
				System.out.println("list size contact:" + contLoc.getListContact().size());
				System.out.println("primary mobile no:" + contLoc.getBuyerPrimaryContact().getCellNo());
				//System.out.println("list size location:" + contLoc.getListLocation().size());
				System.out.println("business name:" + contLoc.getBuyerPrimaryContact().getBusinessName());

				System.out.println("achtester-->Loaded applicationContext.xml");
	}

}

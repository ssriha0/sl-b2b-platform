package com.newco.marketplace.business.businessImpl.document;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerSOTemplateVO;
import com.newco.marketplace.persistence.iDao.so.IBuyerSOTemplateDAO;

public class DocumentBOTest {

	private static final Logger LOGGER = Logger.getLogger(DocumentBOTest.class);
	private IBuyerSOTemplateDAO templateDAO;
	private DocumentBO documentBO;
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	BuyerSOTemplateVO buyerSOTemplateVO = null;
	List<BuyerSOTemplateVO> buyerSOTemplates = new ArrayList<BuyerSOTemplateVO>();
	BuyerSOTemplateDTO buyerSOTemplateDTO = null;
	String titleTest = "Sears Buyer Logo";

	@Before
	public void setUp() {
		documentBO = new DocumentBO();
		templateDAO = mock(IBuyerSOTemplateDAO.class);
		buyerSOTemplateBO = mock(IBuyerSOTemplateBO.class);
		documentBO.setTemplateDAO(templateDAO);
		documentBO.setBuyerSOTemplateBO(buyerSOTemplateBO);

		String templateDateTest = "<buyerTemplate><mainServiceCategory>200</mainServiceCategory><title>Home Appliance, Cooking Gas</title><partsSuppliedBy>1</partsSuppliedBy><overview>Home Appliance Retail Installation Service Order.  NOTICE TO CUSTOMER: A city permit may be required for the installation of a water heater or other installed appliance within your city limits. If a permit is required, you will be billed separately for the cost of the permit. The Sears Authorized contractor who performs the installation will request the permit from your city.  Please contact your municipality following installation for inspection requirements.</overview><terms>This Service Order is subject to, and incorporates by reference, that certain Authorized Servicer Agreement or other agreement (the &apos;Agreement&apos;), between Provider Firm and Sears; provided that only the charges set forth in this Service Order (and not those in the Agreement) shall apply to this Service Order (and Provider Firm agrees that by accepting this Service Order, Provider is agreeing to such change).  Service Provider understands and agrees that Sears may not have purchased ServiceLive Bucks prior to the posting of this Service Order.  This Service Order?s closing total is subject to the 10% Service Order Fee charged by ServiceLive, Inc.</terms><specialInstructions></specialInstructions><spnId>88</spnId><spnPercentageMatch>97</spnPercentageMatch><altBuyerContactId>11943</altBuyerContactId><documentTitles><string>Estimate Form</string><string>Handling Payments for Add-On Services_rev 4</string><string>Sears SPN SO Checklist_rev 3</string><string>Estimate Process - Provider Instructions</string><string>Sears Damage Policy Acknowledgement - Installation</string></documentTitles><documentLogo>Sears Buyer Logo</documentLogo><confirmServiceTime>1</confirmServiceTime><isNewSpn>true</isNewSpn><autoAccept>1</autoAccept><autoAcceptDays>120</autoAcceptDays><autoAcceptTimes>50</autoAcceptTimes></buyerTemplate>";
		buyerSOTemplateVO = new BuyerSOTemplateVO();
		buyerSOTemplateVO.setBuyerID(1000);
		buyerSOTemplateVO.setMainServiceCategory(200);
		buyerSOTemplateVO.setTemplateData(templateDateTest);
		buyerSOTemplateVO.setTemplateID(32);
		buyerSOTemplateVO
				.setTemplateName("3rd Party ? NEW ? Home Appliance ? Dish/Disp/Refrige");

		buyerSOTemplates.add(buyerSOTemplateVO);

		buyerSOTemplateDTO = new BuyerSOTemplateDTO();
		buyerSOTemplateDTO.setDocumentLogo(titleTest);
	}

	@Test
	public void testValidateLogoPresent() {

		boolean result = false;
		String title = "Sears Buyer Logo";
		Integer buyerId = 1000;
		try {
			when(templateDAO.loadBuyerSoTemplates(buyerId)).thenReturn(
					buyerSOTemplates);
			when(
					buyerSOTemplateBO
							.getBuyerSOTemplateXMLAsDTO(buyerSOTemplateVO
									.getTemplateData())).thenReturn(
					buyerSOTemplateDTO);
			result = documentBO.validateLogoPresent(buyerId, title);
			assertTrue(result);
		} catch (Exception e) {
			LOGGER.error("Exception in Junit execution for validateLogoPresent"
					+ e.getMessage());
		}
	}
}

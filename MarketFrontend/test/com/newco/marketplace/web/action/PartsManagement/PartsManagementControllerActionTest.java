package com.newco.marketplace.web.action.PartsManagement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.servicelive.partsManagement.services.impl.PartsManagementServiceImpl;

public class PartsManagementControllerActionTest {
	private PartsManagementControllerAction action;
	private PartsManagementServiceImpl partsManagementService;

	@Ignore
	@Test
	public void savePartDetails() {
		Boolean autoAdjudicationInd = null;
		String invoicePricingModel = null;
		InvoicePartsVO partVO = null;
		action = new PartsManagementControllerAction();
		action.setPartsManagementService(partsManagementService);

		partsManagementService = mock(PartsManagementServiceImpl.class);

		partVO = new InvoicePartsVO();
		partVO.setSoId("578-7195-7273-18");

		autoAdjudicationInd = partsManagementService.getAutoAdjudicationInd();
		Assert.assertNotNull(autoAdjudicationInd);

		when(
				partsManagementService.getInvoicePartsPricingModel(partVO
						.getSoId())).thenReturn("COST_PLUS");
		invoicePricingModel = partsManagementService
				.getInvoicePartsPricingModel(partVO.getSoId());
		Assert.assertNotNull(invoicePricingModel);
	}
}

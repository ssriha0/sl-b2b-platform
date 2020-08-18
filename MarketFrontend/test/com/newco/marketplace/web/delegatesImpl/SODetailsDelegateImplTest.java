package com.newco.marketplace.web.delegatesImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.document.DocumentBO;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.BuyerDocumentTypeVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.utils.OFUtils;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This is the test class to test completeSO() method
 *
 */
@Ignore
public class SODetailsDelegateImplTest {
	
	private SODetailsDelegateImpl detailsDelegateImpl = null;
	private IDocumentBO documentBO = null;
	private IServiceOrderBO serviceOrderBo = null;
	private OFHelper ofHelper = null;
	private SOCompleteCloseDTO soCompDto = null;
	
	@Before
	public void setUp() throws Exception {
		
		detailsDelegateImpl = new SODetailsDelegateImpl();
		documentBO = mock(DocumentBO.class);
		serviceOrderBo = mock(ServiceOrderBO.class);
		ofHelper = mock(OFHelper.class);
		
		detailsDelegateImpl.setDocumentBO(documentBO);
		detailsDelegateImpl.setServiceOrderBo(serviceOrderBo);
		detailsDelegateImpl.setHelperOF(ofHelper);
	
		List<BuyerDocumentTypeVO> buyerDocumentTypeVOList = new ArrayList<BuyerDocumentTypeVO>();
		
		BuyerReferenceVO modelVO = new BuyerReferenceVO();
		modelVO.setReferenceType(InHomeNPSConstants.MODEL);
		modelVO.setReferenceValue("Model 123");
		modelVO.setRequired(1);
		
		BuyerReferenceVO serialVO = new BuyerReferenceVO();
		serialVO.setReferenceType(InHomeNPSConstants.SERIAL_NUMBER);
		serialVO.setReferenceValue("Serial 123");
		serialVO.setRequired(1);
		
		ArrayList<BuyerReferenceVO> buyerRefs = new ArrayList<BuyerReferenceVO>();
		buyerRefs.add(modelVO);
		buyerRefs.add(serialVO);
		
        SOPartLaborPriceReasonVO partReasonVO = new SOPartLaborPriceReasonVO();
        partReasonVO.setSoId("550-0822-9850-23");
        partReasonVO.setPriceType("Parts");
        
        SOPartLaborPriceReasonVO laborReasonVO = new SOPartLaborPriceReasonVO();
        laborReasonVO.setSoId("550-0822-9850-23");
        laborReasonVO.setPriceType("Labor");
            
        List<SOPartLaborPriceReasonVO> reasonVO = new ArrayList<SOPartLaborPriceReasonVO>();
        reasonVO.add(partReasonVO);
        reasonVO.add(laborReasonVO);
            
        soCompDto = new SOCompleteCloseDTO();
    	soCompDto.setBuyerID("3000");
    	soCompDto.setSoId("550-0822-9850-23");
    	soCompDto.setResComments("Completed");
    	soCompDto.setBuyerRefs(buyerRefs);
    	soCompDto.setModelError(true);
    	soCompDto.setSerialError(true);
    	soCompDto.setSoPartLaborPriceReason(reasonVO);
            
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setBuyerId(3000L);
		serviceOrder.setSoId("550-0822-9850-23");
		serviceOrder.setSOWorkflowControls(new SOWorkflowControls());
		serviceOrder.getSOWorkflowControls().setInvalidModelSerialInd("BOTH");
		serviceOrder.setWfStateId(160);
		serviceOrder.setCustomReferences(OFUtils.mapCustomRefs(buyerRefs));
		serviceOrder.setResolutionDs("Completed");
		serviceOrder.setSoPartLaborPriceReason(OFUtils.mapPartLaborPriceReason(soCompDto, serviceOrder));
		serviceOrder.setFinalPriceParts(new BigDecimal("0.00"));
        
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_ADDONPRICE,"0.0"));
		parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_AUTOCLOSE,""));
		
		OrderFulfillmentResponse ordrFlflResponse = new OrderFulfillmentResponse();
		
		when(documentBO.retrieveDocTypesByBuyerId(3000, 1)).thenReturn(buyerDocumentTypeVOList);
		when(ofHelper.isNewSo("550-0822-9850-23")).thenReturn(true);
		when(ofHelper.getServiceOrder("550-0822-9850-23")).thenReturn(serviceOrder);
		when(ofHelper.runOrderFulfillmentProcess("550-0822-9850-23",SignalType.COMPLETE_ORDER,serviceOrder,null,parameters)).thenReturn(ordrFlflResponse);
		when(documentBO.checkIfMobileSignatureDocumentExists("550-0822-9850-23")).thenReturn(false);
	}
	
	@Test
	public void testCompleteSO() throws BusinessServiceException{
		
		String responseCode = detailsDelegateImpl.completeSO(soCompDto).getCode();
		Assert.assertEquals("00", responseCode);
			
	}
	
}

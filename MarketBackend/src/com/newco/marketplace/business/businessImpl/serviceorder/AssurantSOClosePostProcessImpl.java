package com.newco.marketplace.business.businessImpl.serviceorder;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.client.IClientInvoiceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOClosePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.ClientConstants;

public class AssurantSOClosePostProcessImpl implements ISOClosePostProcess {
	
	private static final Logger logger = Logger.getLogger(AssurantSOClosePostProcessImpl.class);

	public void execute(String serviceOrderId, Integer buyerId) throws BusinessServiceException {
		IServiceOrderBO bo = null;
		IClientInvoiceBO clientInvoiceBO = null;
		
		ServiceOrder serviceOrder;
		//get the service order business object from the context
		try {
			bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
			clientInvoiceBO = (IClientInvoiceBO) MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.CLIENT_INVOICE_BUSINESS_OBJECT);
		} catch (BeansException e) {
			logger.error(e.getMessage(), e);
		}
		
/*		serviceOrder = bo.getServiceOrder(serviceOrderId);
		if (serviceOrder != null) {
			clientInvoiceBO.processCloseServiceOrder(serviceOrder);
		}*/
		//invoice the client the Service order
		clientInvoiceBO.invoiceCloseAndPay(serviceOrderId, ClientConstants.ASSURANT_CLIENT_NAME);
	}

}

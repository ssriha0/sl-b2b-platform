package com.newco.marketplace.business.businessImpl.serviceorder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.client.IClientInvoiceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOCancelPostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.ClientConstants;

public class AssurantSOCancelPostProcessImpl implements ISOCancelPostProcess {
	
	private static final Logger logger = Logger.getLogger(AssurantSOCancelPostProcessImpl.class);

	public void execute(ServiceOrder so) throws BusinessServiceException {
		IClientInvoiceBO clientInvoiceBO = null;
		try {
			clientInvoiceBO = (IClientInvoiceBO) MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.CLIENT_INVOICE_BUSINESS_OBJECT);
		} catch (BeansException e) {
			logger.error(e.getMessage(), e);
		}
		if (so != null) {
			clientInvoiceBO.invoiceAssurantCancelRequest(so.getSoId(), ClientConstants.ASSURANT_CLIENT_NAME);
		}
		
	}

	private ServiceOrder getServiceOrder(String soID) {
		ServiceOrder serviceOrder = null;
		IServiceOrderBO bo = null;
		//get the service order business object from the context
		try {
			bo = (IServiceOrderBO) MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		} catch (BeansException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			serviceOrder = bo.getServiceOrder(soID);
		}
		catch (Exception e) {
			logger.error("Could not find so for ID: " + (StringUtils.isNotBlank(soID) ? soID : "null") + " ", e);
		}
		return serviceOrder;
	}
	
	public void execute(String serviceOrderId) throws BusinessServiceException {
		execute(getServiceOrder(serviceOrderId));
	}
	
	public void execute(String serviceOrderId, Integer buyerId) throws BusinessServiceException {
		execute(getServiceOrder(serviceOrderId));
	}

}

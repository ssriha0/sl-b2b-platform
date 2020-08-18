package com.servicelive.orderfulfillment.command;

import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import com.servicelive.orderfulfillment.ServiceOrderBO;
import com.servicelive.orderfulfillment.domain.ClientInvoiceOrder;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class CreateInvoiceforAssurantCancelCmd extends SOCommand {
	private static final int SKU_CANCEL = 2;
	private static final int SKU_FULL = 1;
    //private IClientInvoiceOrderBO clientInvoiceOrderBO;
    private ServiceOrderBO serviceOrderBO;
	


	@Override
	public void execute(Map<String, Object> processVariables) {
		ServiceOrder so = getServiceOrder(processVariables);
		invoiceAssurantCancelRequest( so, so.getCreatorUserName());

	}
	public void invoiceAssurantCancelRequest( ServiceOrder serviceOrder, String clientName){		
	try {			
			String incidentID = serviceOrder.getCustomRefValue(SOCustomReference.CREF_INCIDENTID);			
			incidentID = !StringUtils.isEmpty(incidentID) ? incidentID : null;				
			if(isIncidentInvoiced(incidentID)){
				serviceOrderBO.saveClientInvoiceOrder(incidentID, serviceOrder.getSoId(), SKU_CANCEL, 1);
			}
		} catch (Exception e){
			logger.error("Error Invoicing in InvoiceAssurantCancelCmd");
		}

	}
	
	/**
	 * This method checks if the Incident can be invoiced for SKU_CANCEL.
	 * It checks if it was invoiced(sku_full/sku_cancel) before
	 * @param incidentID
	 * @return boolean
	 */
	public boolean isIncidentInvoiced(String incidentID){
		boolean canInvoice=false;
		// check if the incident was SKU_FULL invoiced before
		ClientInvoiceOrder  incidentInvoicedFull = wasIncidentInvoiced(incidentID, SKU_FULL);
		if(incidentInvoicedFull == null){
			// check if the incident was SKU_CANCEL invoiced before
			ClientInvoiceOrder  incidentInvoicedCancel = wasIncidentInvoiced(incidentID, SKU_CANCEL);
			if(incidentInvoicedCancel == null){
				canInvoice=true;
			}
		}
		return canInvoice;
	}
	
	/**
	 * method to check if the incident for that sku was invoiced before
	 * @param incidentId
	 * @param skuId
	 * @return
	 */
	public ClientInvoiceOrder wasIncidentInvoiced(String incidentId , int skuId) {
		ClientInvoiceOrder clientInvoiceOrder = null;
		try {
			clientInvoiceOrder = serviceOrderBO.getClientInvoiceForIncident(incidentId, skuId);
		}catch(NoResultException ne ){
			return null;
		}catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return clientInvoiceOrder;
	}
	
	
	public void setServiceOrderBO(ServiceOrderBO serviceOrderBO){
		this.serviceOrderBO = serviceOrderBO;
	}
}

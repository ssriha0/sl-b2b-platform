package com.newco.marketplace.business.iBusiness.client;

import java.util.List;

import com.newco.marketplace.dto.vo.client.ClientInvoiceOrderVO;
import com.newco.marketplace.dto.vo.incident.IncidentEventVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IClientInvoiceBO {
	
	public ProcessResponse saveClientInvoiceOrder(ClientInvoiceOrderVO invoice) throws BusinessServiceException;
	
	public ProcessResponse processCloseServiceOrder(ServiceOrder so);
	
	public ProcessResponse processCancelServiceOrder(ServiceOrder so);

	public ProcessResponse invoiceCloseAndPay( String soId, String incidentID, int clientId);
	public ProcessResponse invoiceCloseAndPay(String soId, String clientName);
	public ProcessResponse invoiceIncidentEvent(String soId, String incidentID, int clientId);
	public ProcessResponse invoiceAssurantCancelRequest( String soId, String clientName);
	public ProcessResponse saveClientInvoiceOrder(String incidentID, String soId, int skuId, int clientId)throws BusinessServiceException;
	public List<ServiceOrderCustomRefVO> checkIncidentForOtherSO(String incidentID, String soId);
	public ClientInvoiceOrderVO wasIncidentInvoiced(String incidentId , int skuId) ;
	

}

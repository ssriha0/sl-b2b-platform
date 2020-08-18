package com.servicelive.esb.service;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.newco.marketplace.process.SOFileProcessingBridge;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RouteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;
//import com.newco.marketplace.webservices.sei.serviceorder.ServiceOrderSEILocator;
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;
import com.servicelive.esb.constant.MarketESBConstant;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.springframework.beans.factory.BeanFactory;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.integration.IIntegrationServiceCoordinator;
import com.servicelive.esb.integration.bo.IIntegrationBO;

/**
 * The class is responsible to consume the ServiceLive webservice.
 * @author pbhinga
 *
 */
public class SLOrderServiceImpl implements SLOrderService {

	// private ServiceOrderSEILocator orderServiceLocator;
	private SOFileProcessingBridge soFileProcessingBridge;
	private String serviceOrderEndPointUrl;
	private Logger logger = Logger.getLogger(SLOrderServiceImpl.class);
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	
	// Sl-21931
	private IIntegrationServiceCoordinator integrationServiceCoordinator;
	private Integer buyer_id = 1000;
	
	
	protected BeanFactory getBeanFactory() throws ActionLifecycleException {
		return SpringUtil.factory;
	}
	
	public IIntegrationServiceCoordinator getIntegrationServiceCoordinator() {
		return integrationServiceCoordinator;
	}

	public void setIntegrationServiceCoordinator(
			IIntegrationServiceCoordinator integrationServiceCoordinator) {
		this.integrationServiceCoordinator = integrationServiceCoordinator;
	}

	
	/**
	 * @return the soFileProcessingBridge
	 */
	public SOFileProcessingBridge getSoFileProcessingBridge() {
		return soFileProcessingBridge;
	}

	/**
	 * @param soFileProcessingBridge the soFileProcessingBridge to set
	 */
	public void setSoFileProcessingBridge(
			SOFileProcessingBridge soFileProcessingBridge) {
		this.soFileProcessingBridge = soFileProcessingBridge;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#createDraft(com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest)
	 */
	public CreateDraftResponse createDraft(CreateDraftRequest createDraftReq)
			throws Exception {
		CreateDraftResponse createDraftResponse = null;
		
		// SL-21931
		// String omsUserId = (String) resourceBundle.getObject(MarketESBConstant.Client.OMS + "_USER_ID");
		String omsUserId = new String();
		logger.info("Buyer Id Service Order "+ createDraftReq.getBuyerId());
		
		if (this.getIntegrationServiceCoordinator() == null) {
			this.setIntegrationServiceCoordinator((IIntegrationServiceCoordinator) this.getBeanFactory().getBean("integrationServiceCoordinator"));
		}

		if (this.getIntegrationServiceCoordinator().getIntegrationBO() == null) {
			this.getIntegrationServiceCoordinator().setIntegrationBO((IIntegrationBO) this.getBeanFactory().getBean("integrationBO"));
		}
		
		if(null == createDraftReq.getBuyerId())
		{
			omsUserId=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
		}
		else
		{
			omsUserId=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(createDraftReq.getBuyerId());
		}
		
		
		StringBuilder info = new StringBuilder(); 
		
		//For OMS order show the order number and unit number in the log
		if (logger.isInfoEnabled()) {
			info.append("Invoking web service to create draft ");
			if (omsUserId.equals(createDraftReq.getUserId())) {
				info.append("for order: " + createDraftReq.getOrderNumber()
						+ " and unit: " + createDraftReq.getUnitNumber());
			}
			logger.info(info.toString());
		}
		
		//Set the Endpoint url for WebService
		try {
			//orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(this.getServiceOrderEndPointUrl());
			//createDraftResponse = orderServiceLocator.getServiceOrderSEIHttpPort().createDraft(createDraftReq);
			createDraftResponse = soFileProcessingBridge.createDraft(createDraftReq);
		} catch(Exception e) {
			String error = "Error invoking ServiceLive webservice to create a Service Order Draft.";
			logger.error(error, e);
			throw e;		
		}
		
		return createDraftResponse;
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#createDraft(com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest)
	 */
	public CreateDraftResponse updateOrder(CreateDraftRequest createDraftReq)
			throws Exception {
		CreateDraftResponse createDraftResponse = null;
		
		try {
			
			createDraftResponse = soFileProcessingBridge.updateOrder(createDraftReq);
		} catch(Exception e) {
			String error = "Error invoking ServiceLive webservice to create a Service Order Draft.";
			logger.error(error, e);
			throw e;		
		}
		
		return createDraftResponse;
	}
	
	/**
	 * @return
	 */
	/*public ServiceOrderSEILocator getOrderServiceLocator() {
		return orderServiceLocator;
	}*/

	/**
	 * @param orderService
	 */
	/*public void setOrderServiceLocator(ServiceOrderSEILocator orderService) {
		this.orderServiceLocator = orderService;
	}*/

	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#getServiceOrderEndPointUrl()
	 */
	public String getServiceOrderEndPointUrl() {
		return serviceOrderEndPointUrl;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#setServiceOrderEndPointUrl(java.lang.String)
	 */
	public void setServiceOrderEndPointUrl(String serviceOrderEndPointUrl) {
		this.serviceOrderEndPointUrl = serviceOrderEndPointUrl;
	}

	public RouteResponse routeOrder(RouteRequest routeReq) throws Exception {
		RouteResponse routeResponse = null;
		logger.info("****Invoking the ServiceLive web service to Post Service Order...");
		
		//Set the Endpoint url for WebService
		try {
			//orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(this.getServiceOrderEndPointUrl());
			//routeResponse = orderServiceLocator.getServiceOrderSEIHttpPort().routeServiceOrder(routeReq);
			routeResponse = soFileProcessingBridge.routeServiceOrder(routeReq);
		} catch(Exception e) {
			throw new Exception("Error invoking ServiceLive webservice to post  a Service Order.");			
		}
		
		return routeResponse;
	}
	
	public ClientServiceOrderNoteResponse addClientNote(ClientServiceOrderNoteRequest request) throws Exception {
		ClientServiceOrderNoteResponse response = null;
		logger.info("****Invoking the SOFileProcessingBridge to create client note...");
		
		//Set the Endpoint url for WebService
//		try {
			//orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(this.getServiceOrderEndPointUrl());
			//response = orderServiceLocator.getServiceOrderSEIHttpPort().addClientServiceOrderNote(request);
			response = soFileProcessingBridge.addClientServiceOrderNote(request);
		
//		} catch(Exception e) {
//			throw new Exception("Error invoking SOFileProcessingBridge to create a Client Note.");			
//		}
		
		return response;
	}
	
	
	public  GetServiceOrderResponse getServiceOrder(String soId) 	throws Exception {
		GetServiceOrderResponse getServiceOrderResponse = null;
		logger.info(new StringBuffer("****Invoking the SOFileProcessingBridge ")
			.append("to retrieve ServiceOrder"));
		
		//Set the Endpoint url for WebService
		try {
			/*orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(
					this.getServiceOrderEndPointUrl());
			getServiceOrderResponse = 
				orderServiceLocator.getServiceOrderSEIHttpPort().getServiceOrder(soId);
			*/
			getServiceOrderResponse = soFileProcessingBridge.getServiceOrder(soId);
			
			if(null != getServiceOrderResponse){
				logger.info(new StringBuffer("MSB: getServiceOrderResponse ID : ")
						.append(getServiceOrderResponse.getOrderStatus().getSoId()));
			}else{
				logger.info(new StringBuffer("getServiceOrderResponse")
						.append("is null.........................."));
			}
		} catch(Exception e) {
			logger.info("e>>>>>>>>>>>>"+e);
			String error = "Error invoking ServiceLive " +
					"webservice to retrieve a Service Order .";
			logger.error(error, e);
			throw e;		
		}
		
		return getServiceOrderResponse;
}

	/* (non-Javadoc)
	 * @see com.servicelive.esb.service.SLOrderService#updateIncidentTrackingWithAck(com.newco.marketplace.webservices.dto.serviceorder.SoIncidentTrackingRequest)
	 */
	public UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(
			UpdateIncidentTrackingRequest request) throws Exception {
		UpdateIncidentTrackingResponse response = null;
		logger.info("****Invoking the ServiceLive web service to update so incident tracking with acknowldegement...");
		
		//Set the Endpoint url for WebService
		try {
			/*orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(this.getServiceOrderEndPointUrl());
			response = orderServiceLocator.getServiceOrderSEIHttpPort().updateIncidentTrackingWithAck(request);
			*/
			response = soFileProcessingBridge.updateIncidentTrackingWithAck(request);
		
		} catch(Exception e) {
			String error = "Error invoking ServiceLive webservice to update so incident tracking with acknowldgement.";
			logger.error(error, e);
			throw e;			
		}
		
		return response;
	}
	/**
	 * This calls the web service to get ServiceOrderId and Status Using CustomReference
	 * @param getSOStatusRequest
	 * @return GetServiceOrderResponse
	 */
	public  GetSOStatusResponse getSOStatus(GetSOStatusRequest getSOStatusRequest ) throws Exception {
		GetSOStatusResponse getSOStatusResponse = null;
		logger.info(new StringBuffer("****Invoking the SOFileProcessingBridge ")
										.append("to retrieve ServiceOrder Status").toString());
		
		//Set the Endpoint url for WebService
		try {
			/*orderServiceLocator.setServiceOrderSEIHttpPortEndpointAddress(
					this.getServiceOrderEndPointUrl());
			getSOStatusResponse = 
				orderServiceLocator.getServiceOrderSEIHttpPort().getSOStatus(getSOStatusRequest);
			*/	
			getSOStatusResponse = soFileProcessingBridge.getSOStatus(getSOStatusRequest);
			
			if(null!= getSOStatusResponse){
				logger.info(new StringBuffer("MSB: getSOStatusResponse ID : ")
					.append(getSOStatusResponse.getSLServiceOrderId()).toString());
			}else{
				logger.info(new StringBuffer("getSOStatusResponse is null..........................")
					.toString());
			}
		} catch(Exception e) {			
			String error = "Error invoking ServiceLive " +
					"webservice to retrieve a Service Order .";
			logger.error(error, e);
			throw e;		
		}		
		return getSOStatusResponse;
	}
	
}

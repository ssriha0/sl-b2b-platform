package com.newco.marketplace.business.businessImpl.client;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.client.IClientInvoiceBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.client.ClientInvoiceOrderVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.client.IClientInvoiceDAO;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;

public class ClientInvoiceBOImpl implements IClientInvoiceBO, ServiceConstants {
	
	private static final Logger logger = Logger.getLogger(ClientInvoiceBOImpl.class);
	private static final String ASSOCIATED_REFERENCE_KEY = "ASSOCIATED INCIDENT";
	private static final String INCIDENT_REFERNECE_KEY = "INCIDENTID";
	private static final int SKU_FULL = 1;
	private static final int SKU_ZERO = 3;
	private static final int SKU_CANCEL = 2;
	
	/*// ACTION_FROM_CLOSE_AND_PAY reflects the close and pay Service of the SO.
	private static final String ACTION_FROM_CLOSE_AND_PAY = "CLOSE_AND_PAY_REQUEST";
	// ACTION_FROM_ASSURANT_CANCEL_REQUEST is a webservice call where assurant requests for a cancellation of SO which would be always on a draft and posted state.
	private static final String ACTION_FROM_ASSURANT_CANCEL_REQUEST = "ASSURANT_CANCEL_REQUEST";
	//From the Incident tracker link user could choose cacncellation by provider or service order denied or cancellation by assurant. 
	private static final String ACTION_FROM_INCIDENT_EVENT_TRACKER = "INCIDENT_TRACKER_CANCEL_REQUEST";
	// From the service live if cancel or void or delete service order buttons were clicked
	private static final String ACTION_FROM_SERVICE_LIVE = "SERVICE_LIVE_CANCEL_OR_VOID_OR_DELETE_SERVICE_ORDER_REQUEST";*/
	
	
	private IClientInvoiceDAO clientInvoiceDAO;
	private IServiceOrderBO serviceOrderBO;

	

	public ProcessResponse saveClientInvoiceOrder(ClientInvoiceOrderVO invoice) throws BusinessServiceException {
		ProcessResponse response = new ProcessResponse();
		try {
			clientInvoiceDAO.saveClientInvoice(invoice);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	public ProcessResponse saveClientInvoiceOrder(String incidentID, String soId, int skuId, int clientId)throws BusinessServiceException {
		ProcessResponse response = null;
		try {
			ClientInvoiceOrderVO invoice = new ClientInvoiceOrderVO();
			invoice.setSku(skuId);
			invoice.setServiceOrderID(soId);
			invoice.setClientID(new Integer(clientId));
			invoice.setIncidentID(incidentID);
			response = saveClientInvoiceOrder(invoice);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}
	
	public ProcessResponse processCancelServiceOrder(ServiceOrder so) {
		ProcessResponse response = new ProcessResponse();
		boolean wasInvoiced = wasInvoiced(so.getSoId());
		//ALL INCIDENT CANCELS ARE BILLIBLE - at least right now
		//CODE LEFT BELOW FOR ASSOCIATED INCIDENT TRACKING
		//String associatedIncidentID = getAssociatedIncident(so);
		//boolean isWithin90DaysOfClose = isWithin90DaysOfClose(associatedIncidentID, so.getBuyer().getBuyerId(), so.getCancelledDate());
		
		//if this order has no associated incidentID and has never been invoiced before for 215
		//invoice 40
		//if (StringUtils.isBlank(associatedIncidentID) && !wasInvoiced) {
		//	clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_CANCEL)));
		//}
		//if the order has an associated incidentID and its closed date > 90 days back 
		//and the incident was never invoiced
		//invoice 40
		//else if (!isWithin90DaysOfClose && !wasInvoiced) {
		//	clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_CANCEL)));
		//}
		//if the order has an associated incidentID and its closed date <= 90 days back 
		//and the incident was never invoiced
		//invoice 0
		if (!wasInvoiced){
			clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_CANCEL)));
		}
		else {
			clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_ZERO)));
		}
		return response;
	}
	
	public ProcessResponse processCloseServiceOrder(ServiceOrder so) {
		boolean wasInvoiced = wasInvoiced(so.getSoId());
		String associatedIncidentID = getAssociatedIncident(so);
		boolean isWithin90DaysOfClose = isWithin90DaysOfClose(associatedIncidentID, so.getBuyer().getBuyerId(), so.getClosedDate());
		
		//if this order has no associated incidentID and has never been invoiced before
		//invoice 215
		if (StringUtils.isBlank(associatedIncidentID) && !wasInvoiced) {
			clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_FULL)));
		}
		//if the order has an associated incidentID and its closed date > 90 days back 
		//and the incident was never invoiced
		//invoice 215
		else if (!isWithin90DaysOfClose && !wasInvoiced) {
			clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_FULL)));
		}
		//if the order has an associated incidentID and its closed date <= 90 days back 
		//and the incident was never invoiced
		//invoice 0
		else {
			clientInvoiceDAO.saveClientInvoice(getClientInvoiceOrderVO(so, new Integer(SKU_ZERO)));
		}
		return null;
	}
	
	private boolean isWithin90DaysOfClose(String associatedIncidentID, Integer buyerID, Timestamp testDate) {
		boolean isWithin90DaysOfClose = false;
		if (StringUtils.isNotBlank(associatedIncidentID)) {
			//see if the incident was closed within 90 days of today
			try {
				//The associated incident id for this order will be an incidentID on the
				ServiceOrder soAssocitated = serviceOrderBO.getByCustomReferenceTypeValue(INCIDENT_REFERNECE_KEY, associatedIncidentID, buyerID);
				if (null != soAssocitated && null != soAssocitated.getClosedDate()) {
					long daysBetween = DateUtils.getDaysBetweenDates(soAssocitated.getClosedDate(), testDate);
					if (daysBetween <= 90) {
						isWithin90DaysOfClose = true;
					}
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return isWithin90DaysOfClose;
	}
	
	private String getAssociatedIncident(ServiceOrder so) {
		String associatedIncidentID = null;
		if (so.getCustomRefs() != null && so.getCustomRefs().size() > 0) {
			for (ServiceOrderCustomRefVO reference : so.getCustomRefs()) {
				if (StringUtils.equals(reference.getRefType(), ASSOCIATED_REFERENCE_KEY)) {
					associatedIncidentID = reference.getRefValue();
					break;
				}
			}
		}
		return associatedIncidentID;
	}
	
	
	
	private boolean wasInvoiced(String soID) {
		boolean found = false;
		ClientInvoiceOrderVO clientInvoiceOrderVO = null;
		try {
			clientInvoiceOrderVO = clientInvoiceDAO.getClientInvoiceForSoID(soID);
			if (null != clientInvoiceOrderVO) {
				found = true;
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return found;
	}
	/**
	 * method to check if the incident for that sku was invoiced before
	 * @param incidentId
	 * @param skuId
	 * @return
	 */
	public ClientInvoiceOrderVO wasIncidentInvoiced(String incidentId , int skuId) {
		ClientInvoiceOrderVO clientInvoiceVO = null;
		try {
			clientInvoiceVO = clientInvoiceDAO.getClientInvoiceForIncident(incidentId, skuId);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return clientInvoiceVO;
	}

	private ClientInvoiceOrderVO getClientInvoiceOrderVO(ServiceOrder so, Integer skuID) {
		ClientInvoiceOrderVO clientInvoiceOrderVO = new ClientInvoiceOrderVO();
		clientInvoiceOrderVO.setClientID(getClientForName("Assurant"));
		clientInvoiceOrderVO.setCreatedDate(new Date());
		clientInvoiceOrderVO.setServiceOrderID(so.getSoId());
		clientInvoiceOrderVO.setSku(skuID);
		return clientInvoiceOrderVO;
	}
	
	

	/*public ProcessResponse createInvoiceRecord(IncidentEventVO incidentEventVO, String actionName)throws BusinessServiceException{
		return createInvoiceRecord(incidentEventVO, null, actionName);
	}
			
	
	public ProcessResponse createInvoiceRecord(IncidentEventVO incidentEventVO, ServiceOrder so, String actionName, SecurityContext sc) throws BusinessServiceException{
		ProcessResponse processResponse = null;
		if(StringUtils.equals(actionName, ACTION_FROM_CLOSE_AND_PAY)){
			processResponse = invoiceCloseAndPay(incidentEventVO, so);
		} else if (StringUtils.equalsIgnoreCase(actionName, ACTION_FROM_INCIDENT_EVENT_TRACKER)){
			processResponse = processInvoiceIncidentRequest(incidentEventVO);
		} else if (StringUtils.equals(actionName, ACTION_FROM_ASSURANT_CANCEL_REQUEST)){
			processResponse = processInvoiceAssurantRequest(incidentEventVO);
		} else if (StringUtils.equals(actionName, ACTION_FROM_SERVICE_LIVE)){
			processResponse = processInvoiceServiceLiveRequest(incidentEventVO);
		}
		return processResponse;
	}*/
	

	
	
	public List<ServiceOrderCustomRefVO> checkIncidentForOtherSO(String incidentID, String soId){
		List soList = null;
		
		try {
			List<Integer> status = new ArrayList<Integer>();
			status.add(new Integer(OrderConstants.CLOSED_STATUS));
			status.add(new Integer(OrderConstants.COMPLETED_STATUS));
			status.add(new Integer(OrderConstants.CANCELLED_STATUS));
			status.add(new Integer(OrderConstants.VOIDED_STATUS));
			status.add(new Integer(OrderConstants.DELETED_STATUS));
			soList = serviceOrderBO.getServiceOrdersByCustomRefValue(INCIDENT_REFERNECE_KEY, incidentID, soId, status);
			
		} catch( Exception e){
			logger.debug("Exception thrown in method checkIncidentForOtherSO incidentID = " + incidentID + ", soID = " + soId);
			
		}
		return soList;
		
	}
	
	public ProcessResponse invoiceCloseAndPay(String soId, String clientName){
		return invoiceCloseAndPay(soId,null,getClientForName(clientName));
	}
	
	/**
	 * Method to Invoice a Close and Pay
	 * @param incidentID
	 * @param soId
	 * @return
	 */
	public ProcessResponse invoiceCloseAndPay( String soId, String incidentID, int clientId){
		ProcessResponse response = null;
		
		if(null == incidentID){
			ServiceOrderCustomRefVO customRefVO = serviceOrderBO.getCustomRefValue(INCIDENT_REFERNECE_KEY, soId);
			// incident Id represents the client incident id
			incidentID = !StringUtils.isEmpty(customRefVO.getRefValue()) ? customRefVO.getRefValue() : null;
		} 
		
		if(null == incidentID){
			response.setCode(USER_ERROR_MSG);
			response.setMessage(new String("The Service order does not have a incident id."));
			return response;
		}
		
		try {
			
			// check if the incident was invoiced before
			ClientInvoiceOrderVO  incidentInvoiced = wasIncidentInvoiced(incidentID, SKU_FULL);
			if(incidentInvoiced == null){
				List otherSO = checkIncidentForOtherSO(incidentID, soId);
				if(otherSO == null || otherSO.size() == 0){
					response = saveClientInvoiceOrder(incidentID, soId, SKU_FULL, clientId);
				}
			} else {
				//check if the SO invoiced is > 90 days
				ServiceOrder invoicedSO = serviceOrderBO.getServiceOrderStatusAndCompletdDate(incidentInvoiced.getServiceOrderID());
				if(invoicedSO != null && invoicedSO.getCompletedDate() != null){
					long daysBetween = DateUtils.getDaysBetweenDates(invoicedSO.getCompletedDate(), new Date());
					if(daysBetween > 90){
						response = saveClientInvoiceOrder(incidentID, soId, SKU_FULL, clientId);
					}
				}
			}
			
		} catch (Exception e){
			logger.error("Error on invoice Close and Pay Action for soId = " + soId);
		}
			
		return response;
	}
	
	
	public ProcessResponse invoiceIncidentEvent(String soId, String incidentID, int clientId){
		ProcessResponse response = null;
		try {
			ServiceOrder so = serviceOrderBO.getServiceOrderStatusAndCompletdDate(soId);
			if(so != null){
				if(so.getWfStateId() == OrderConstants.DRAFT_STATUS || so.getWfStateId() == OrderConstants.ROUTED_STATUS){
					if(canInvoiceSkuCancel(incidentID)){
						response = saveClientInvoiceOrder(incidentID, soId, SKU_CANCEL, clientId);
					}
				} else {
					response = invoiceCloseAndPay( soId, incidentID, clientId);
				}
			}
		} catch (Exception e){
			logger.error("Error Invoicing Incident Event tracker");
		}
		
		return response;
		
	}
	
	public ProcessResponse invoiceAssurantCancelRequest( String soId, String clientName){
		ProcessResponse response = null;
		try {
			String incidentID = null;
			
				ServiceOrderCustomRefVO customRefVO = serviceOrderBO.getCustomRefValue(INCIDENT_REFERNECE_KEY, soId);
				// incident Id represents the client incident id
				incidentID = !StringUtils.isEmpty(customRefVO.getRefValue()) ? customRefVO.getRefValue() : null;
			
			if(null == incidentID){
				response.setCode(USER_ERROR_MSG);
				response.setMessage(new String("The Service order does not have a incident id."));
				return response;
			}
			if(canInvoiceSkuCancel(incidentID)){
				response = saveClientInvoiceOrder(incidentID, soId, SKU_CANCEL, getClientForName(clientName));
			}
			
		} catch (Exception e){
			logger.error("Error Invoicing Incident Event tracker");
		}
		
		return response;
		
	}
	/**
	 * This method checks if the Incident can be invoiced for SKU_CANCEL.
	 * It checks if it was invoiced(sku_full/sku_cancel) before
	 * @param incidentID
	 * @return boolean
	 */
	private boolean canInvoiceSkuCancel(String incidentID){
		boolean canInvoice=false;
		// check if the incident was SKU_FULL invoiced before
		ClientInvoiceOrderVO  incidentInvoicedFull = wasIncidentInvoiced(incidentID, SKU_FULL);
		if(incidentInvoicedFull == null){
			// check if the incident was SKU_CANCEL invoiced before
			ClientInvoiceOrderVO  incidentInvoicedCancel = wasIncidentInvoiced(incidentID, SKU_CANCEL);
			if(incidentInvoicedCancel == null){
				canInvoice=true;
			}
		}
		return canInvoice;
	}

	private Integer getClientForName(String clientName) {
		return clientInvoiceDAO.getClientIDForName(clientName);
	}

	public IClientInvoiceDAO getClientInvoiceDAO() {
		return clientInvoiceDAO;
	}

	public void setClientInvoiceDAO(IClientInvoiceDAO clientInvoiceDAO) {
		this.clientInvoiceDAO = clientInvoiceDAO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

}

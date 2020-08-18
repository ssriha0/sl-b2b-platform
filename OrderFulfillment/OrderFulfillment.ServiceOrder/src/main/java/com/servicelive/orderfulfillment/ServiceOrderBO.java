package com.servicelive.orderfulfillment;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.client.SimpleRestClient;
import com.servicelive.common.util.SerializationHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderValidationException;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;
import com.servicelive.orderfulfillment.domain.ClientInvoiceOrder;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.SOTaskHistory;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.jbpm.TransientVariable;
import com.servicelive.orderfulfillment.jbpm.TransitionContext;
import com.servicelive.orderfulfillment.logging.IServiceOrderLogging;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.ordertype.IServiceOrderType;
import com.servicelive.orderfulfillment.ordertype.ServiceOrderType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.signal.ISignal;
import com.servicelive.orderfulfillment.signal.ProcessSignalStep;
import com.servicelive.orderfulfillment.signal.Signal;

// ServiceOrderBO
// coordinates the following:
// - order persistence
// - jBPM
// - signal dispatch (configuration and calling)
public class ServiceOrderBO extends BaseServiceOrderBO {
	private Logger logger = Logger.getLogger(getClass());

	private Map<String, IServiceOrderLogging> soLoggingMap;
	private ServiceOrderNoteUtil noteUtil;

	protected QuickLookupCollection quickLookupCollection;


	public String createServiceOrder(
			Identification idfn, 
			ServiceOrder so, 
			Map<String,Object> pvars) 
	{
		// validate service order we received
		if (so == null) 
			throw new ServiceOrderException("Undefined Service Order.");
        
		/**Non w2 priority 2 : This method will check if there any entry for Draft Order
		 Creation Logging in History by No Matching Car Rule Or By Recall provider Not available.*/
		boolean isLoggedDraft = validateLoggingForDraft(so.getSoId());
        if(!isLoggedDraft){
		  // add a record to SO history
          logger.info("No Logging present in for Draft logging with No matching Rule or Recall Provider");	
		  soLoggingMap.get("createDraftSOLogging").logServiceOrder(so, createLogDataMap(idfn,so));
        }
		int taskSeqNum = 1;
		if(null != so.getTasks()){
			for(SOTask task: so.getTasks()){
				if(so.isCreatedViaAPI()){
					task.setAutoInjectedInd(1);
				}
				task.setTaskSeqNum(taskSeqNum);
				task.setTaskId(null);
				taskSeqNum++;
			}
		}
		//save service order
		//For Bid Order if custom ref present along with template there,so fails.
		if(so.isCreatedViaAPI()){
		if(null != so.getCustomReferences()){
		    for(SOCustomReference custRefList:so.getCustomReferences()){
		     logger.info("cust Ref id"+ custRefList.getBuyerRefTypeId());
		     logger.info("Cust Ref Name"+ custRefList.getBuyerRefTypeName());
		     logger.info("Cust Ref value"+custRefList.getBuyerRefValue());
		   }
		}else{
		  logger.info("Cust Ref List is Null");
		  }
		}
		
		// Fix for order creation
		if (null != so.getAddons() && !so.getAddons().isEmpty()) {
			for (SOAddon addon : so.getAddons()) {
				if (null == addon.getSkuGroupType()) {
					addon.setSkuGroupType(new Integer(0));
				}
			}
		}
		
		serviceOrderDao.save(so);
		logger.info("isCreateWithOutTasks()::"+so.isCreateWithOutTasks());
		//Update SO Task history with so_id
		if(null != so.getTasks()&& (!(so.isCreateWithOutTasks()))){
			logger.info("inside creation of task price history for first time");
			for(SOTask task: so.getTasks()){
				
				// to create task price history for first time if price type is task level.
				 if(so.getBuyerId()!=1000 && so.getPriceType().equals("TASK_LEVEL") && task.getTaskHistory().isEmpty()){
					PricingUtil.addTaskPriceHistory(task,"SYSTEM","SYSTEM");
		
				 }
				
				if(null != task.getTaskHistory()){
					for(SOTaskHistory taskHistory: task.getTaskHistory()){
						taskHistory.setSoId(so.getSoId());
						serviceOrderDao.save(taskHistory);
					}
				}
			
			}
		}
		
		// create new WF instance			
		IServiceOrderType serviceOrderType = ServiceOrderType.getServiceOrderType(so.getBuyerId(), idfn.getRoleId());
		String pid = workflowManager.createNewWFInstance(so.getSoId(), serviceOrderType, pvars);
		logger.debug("created jbpm process for order(" + so.getSoId() + ") and Process Id = " + pid);

		// save instance id and newOF flag
		serviceOrderProcessDao.save(
				new ServiceOrderProcess(so.getSoId(), pid, serviceOrderType.getSoTypeName())
		);
        createSOLocationGIS(so);
        
		logger.debug("done saving order and order process map");
		return so.getSoId();
	}

   

	private void createSOLocationGIS(ServiceOrder so){
        if(null != so.getServiceLocation() && null != so.getPriceModel() && so.getPriceModel().equals(PriceModelType.BULLETIN)){
            serviceOrderDao.saveSOLocationGIS(so.getServiceLocation().getLocationId());
        }
    }

	// ServiceOrderBO::processOrderSignal
	// ----------------------------------
	// main responsibility is to coordinate Signal Object, jBPM and Order Dao
	// this method ensures that order state and jBPM state are transactionally coordinated
	// this method should create transaction boundary and ensure that jBPM and Dao join it
	// -----------------------------------
	// It specifically deals with the following responsibilities:
	//  - instantiate signal bean 
	//  - retrieve order
	//  - call processSignal on the bean
	//  - transition jBPM if signal is state dependent
	//  - save order
	//  ----------------------------------
	public void	processOrderSignal(
					String soId, 
					SignalType signalType,
					Identification callerId,
					SOElement soElement,
					Map<String,Serializable> miscParams
				) 
	{
		String pss="";

		try {
			long startTime = System.currentTimeMillis();
			// get process record from the database
			pss = ProcessSignalStep.PSS_GETPROCESSRECORD;
			ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcessWithLock(soId);
			if (sop == null) 
				throw new ServiceOrderException("Order record isn't found in so-process table");
			if (!sop.isNewSo()) 
				throw new ServiceOrderException("Order has been demoted");
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Got ServiceOrderProcess with lock. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));

			// get process id for the order
			pss = ProcessSignalStep.PSS_GETWFID;
			String pid = sop.getProcessId();
			if (pid == null) 
				throw new ServiceOrderException("Process id isn't found for the order");

			// get signal bean from the map of signals
			pss = ProcessSignalStep.PSS_GETSIGNAL;
			ISignal signalObj = Signal.getSignal(signalType, sop.getSoType(), soElement);
			if (signalObj==null) 
				throw new ServiceOrderException("Signal wasn't found in the signal map");
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Got Process map Id and Signal Object. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));

			// get service order from the DB
			pss = ProcessSignalStep.PSS_GETSO;
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
			if (so==null) 
				throw new ServiceOrderException("Order record isn't found in the database");
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Got ServiceOrder . Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));

			// is the caller authorized to send this signal?
			// Authorization logic depends on user type and id from Identification and
			// ServiceOrder because many rules use order info. I.e., ROUTED_PROVIDER, ACCEPTED_PROVIDER, BUYER, ...
			pss = ProcessSignalStep.PSS_AUTHORIZE;
			signalObj.authorize(callerId, so);

			// can this signal be processed? ask jBPM 
			pss = ProcessSignalStep.PSS_TRANSITIONCHECK;
			String transitionName = signalObj.getTransitionName();
			if (signalObj.isStateDependent()){
                if(!workflowManager.waitForTransition(pid, transitionName, 1))
						throw new TransitionNotAvailableException("Order workflow is in a state where "+	signalObj.getName()+" signal is prohibited");
			}
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Done with checking if Transition is available. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));

			// SO logging
			pss = ProcessSignalStep.PSS_SOLOG;
			signalObj.logServiceOrder(so, soElement, callerId, miscParams);
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Done with creating logging. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));
            
            // give access to misc parameters
            signalObj.accessMiscParams(miscParams, callerId, so);
            
            logger.info("Before Calling Signal  %1$s for soId %2$s substatus :"+ so.getWfSubStatusId()+"Sub status String:"+ so.getWfSubStatus());
			// process signal
			pss = ProcessSignalStep.PSS_SIGNALPROCESS;
            //the process method calls validate internally. If validation fails an ServiceOrderException should be thrown.
			signalObj.process(soElement, so);
			
			// SL-21744 
			// call relay notification webhook
			relayOutboundNotification(signalType, so);
			
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Done with signal.process. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));
            logger.info("Afeter Calling Signal,before command  %1$s for soId %2$s substatus :"+ so.getWfSubStatusId()+"Sub status String:"+ so.getWfSubStatus());
			// now it's time to transition jBPM
			// this will fire all commands that are associated with the transition
            Map<String, TransientVariable> transientVars = new HashMap<String,TransientVariable>();
            transientVars.put(ProcessVariableUtil.PVKEY_IDENTIFICATION, new TransientVariable(callerId));
			pss = ProcessSignalStep.PSS_WFTRANSITION;
			if (signalObj.isStateDependent())
				workflowManager.takeTransition(new TransitionContext(pid, transitionName, miscParams,transientVars));
            logger.info(String.format("processOrderSignal>>> %1$s for soId %2$s. Done with jBPM transition. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));
 
            logger.info("Afeter Calling command,before saving  %1$s for soId %2$s substatus :"+ so.getWfSubStatusId()+"Sub status String:"+ so.getWfSubStatus());
            // save order
			pss = ProcessSignalStep.PSS_SAVESO;
			//logger.info("getSoProviderInvoiceParts size: "+so.getSoProviderInvoiceParts().size());
			serviceOrderDao.update(so);
			  logger.info("Afetr saving data in DB : %1$s for soId %2$s substatus :"+ so.getWfSubStatusId()+"Sub status String:"+ so.getWfSubStatus());
			// testing of transactions. will both SO domain and jBPM roll back?
			//throw new ServiceOrderException("We've got a problem");
            logger.info(String.format("Done with processOrderSignal>>> %1$s for soId %2$s. Time elapsed %3$d", signalType.name(), soId, System.currentTimeMillis() - startTime));
		}
		catch (ServiceOrderException soe) {
			//SL-20926
			//If validation error occurred due to addon issue, 
			//throw ServiceOrderValidationException without modifying the error message
			if(null != soe && soe instanceof ServiceOrderValidationException){
				ServiceOrderValidationException sove =  (ServiceOrderValidationException)soe;
				if(null != sove && null != sove.getValidationErrors() && 
						OrderConstants.ADDON_COMPLETE_ERROR.equalsIgnoreCase(sove.getValidationErrors().get(0))){
					logger.info("Inside ServiceOrderValidationException2"+sove.getValidationErrors().get(0));
					throw sove;
				}
	        }
			soe.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". soId="+soId);
			throw soe;
		}
		catch (Exception e)	{
			ServiceOrderException soe = new ServiceOrderException();
			soe.addError(">> "+signalType.name()+" signal processing failed performing "+pss+". soId="+soId);
			soe.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			logger.error(">> General exception stack trace:", e);
			logger.error(">> End of stack trace:");
			throw soe;
		}
	}

	/**
	 * 
	 * @param serviceOrder
	 * @return
	 */
	private boolean isRelayServicesNotificationNeeded(
			ServiceOrder serviceOrder) {
		logger.info("Entering ServiceOrderBO.isRelayServicesNotificationNeeded()...");
		ApplicationFlagLookup applicationFlagLookup = quickLookupCollection.getApplicationFlagLookup();
		if (!applicationFlagLookup.isInitialized()) {
			throw new ServiceOrderException(
					"Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
		}
		String relayServicesNotifyFlag = applicationFlagLookup
				.getPropertyValue("relay_services_notify_flag");
		Long buyerId = serviceOrder.getBuyerId();
		if  ((OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID == buyerId || 
				OrderfulfillmentConstants.TECHTALK_SERVICES_BUYER_ID == buyerId) && ("ON").equals(relayServicesNotifyFlag)) {
			return true;
		} else {
			return false;
		}
	}



	// SL-21744 send relay notification
	/**
	 * 
	 * @param signalType
	 * @param so
	 */
	private void relayOutboundNotification(SignalType signalType, ServiceOrder so) {
		if ((signalType.equals(SignalType.PROVIDER_REPORT_PROBLEM) || signalType.equals(SignalType.BUYER_REPORT_PROBLEM)) && isRelayServicesNotificationNeeded(so)) {
			sendNotification(so, OrderfulfillmentConstants.ORDER_STATE_CHANGED_TO_PROBLEM, null);
		} else {
			logger.info("signalType: " + signalType);
		}
	}

	/**
	 * 
	 * @param serviceOrder
	 * @param eventType
	 * @param requestMap
	 */
	private void sendNotification(ServiceOrder serviceOrder, String eventType, Map<String, String> requestMap) {

		logger.info("Entering RelayOutboundNotification sendNotification.");
		String URL = null;
		SimpleRestClient client = null;
		int responseCode = 0;
		try {
			if (StringUtils.isNotBlank(serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL))) {
				URL = serviceOrder.getCustomRefValue(OrderfulfillmentConstants.CALL_BACK_URL);
				client = new SimpleRestClient(URL, "", "", false);

				logger.info("URL for Webhooks:" + URL);
				StringBuffer request = new StringBuffer();
				request.append(OrderfulfillmentConstants.SERVICEPROVIDER);
				request.append(OrderfulfillmentConstants.EQUALS);
				request.append(OrderfulfillmentConstants.SERVICELIVE);
				request.append(OrderfulfillmentConstants.AND);
				request.append(OrderfulfillmentConstants.EVENT);
				request.append(OrderfulfillmentConstants.EQUALS);
				request.append(eventType.toLowerCase());

				if (null != requestMap && requestMap.size() > 0) {
					for (Map.Entry<String, String> keyValue : requestMap.entrySet()) {
						request.append(OrderConstants.AND).append(keyValue.getKey()).append(OrderConstants.EQUALS)
								.append(keyValue.getValue());
					}
				}

				logger.info("Request for Webhooks with service order id:" + serviceOrder.getSoId());
				logger.info("Request for Webhooks:" + request);

				responseCode = client.post(request.toString());
				// logging the request, response and soId in db
				serviceOrderDao.loggingRelayServicesNotification(serviceOrder.getSoId(), request.toString(), responseCode);

				logger.info("Response for Webhooks with service order id:" + serviceOrder.getSoId());
				logger.info("Response Code from Webhooks:" + responseCode);
			}

		} catch (Exception e) {
			logger.error("Exception occurred in RelayOutboundNotificationCmd.execute() due to " + e);
		}

		logger.info("Leaving RelayOutboundNotification sendNotification.");

	}


	public ServiceOrderProcess getServiceOrderProcess(String soId){
		return serviceOrderProcessDao.getServiceOrderProcess(soId);
	}
	
	@Transactional(readOnly = true)
	public ServiceOrder getServiceOrder(String soId) {
		ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
        if(null == so) throw new ServiceOrderException("Service order not found for the so_id " + soId);
		//we need to get all the lazy loaded properties in case this object is going to be serialized later
        try {
            SerializationHelper.forceOrmLoadUsingJAXBSerialization(so);
        } catch (JAXBException e) {
            throw new ServiceOrderException("Error occurred when trying to load all lazy loaded data for xml serialization");
        }
        return so;
	}

	@Transactional(readOnly=true)
	public List<ServiceOrder> getPendingServiceOrders() {
		List<ServiceOrder> sos = serviceOrderDao.getPendingServiceOrders();
		for( ServiceOrder so : sos ) {
            try {
                SerializationHelper.forceOrmLoadUsingJAXBSerialization(so);
            } catch (JAXBException e) {
                throw new ServiceOrderException("Error occurred when trying to load all lazy loaded data for xml serialization");
            }
		}
    	return sos;
	}

	private HashMap<String,Object> createLogDataMap(Identification id, SOElement request) {
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("id", id);
		dataMap.put("request", request);
		return dataMap;
	}

	public boolean isProcessFinished(String soId) {
		ServiceOrderProcess sop = getServiceOrderProcess(soId);
		return (sop.isFinished());
	}
	
	public boolean isOrderUpdatable(String soId) {
		ServiceOrderProcess sop = getServiceOrderProcess(soId);
		return sop.isUpdatable();
	}

	@Transactional(readOnly = true)
	public Boolean isTransitionAvailable(String soId, SignalType signalType) {
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);	
		String pid = sop.getProcessId();
		// get signal bean from the map of signals
		ISignal signalObj = Signal.getSignal(signalType, sop.getSoType(), null);
		String transitionName = signalObj.getTransitionName();
		return workflowManager.isTransitionAvailable(pid, transitionName);
	}


	public void addSOLogging(String soId, String loggingType, Identification id){
		if (soLoggingMap.containsKey(loggingType)){
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
			soLoggingMap.get(loggingType).logServiceOrder(so, createLogDataMap(id, null));
			serviceOrderDao.update(so);
		}
	}

    public void setCarOrderIndicatorForProcess(String soId) {
        ServiceOrderProcess serviceOrderProcess = serviceOrderProcessDao.getServiceOrderProcess(soId);
        serviceOrderProcess.setCarOrder(true);
    }

	public void setSoLoggingMap(Map<String, IServiceOrderLogging> soLoggingMap) {
		this.soLoggingMap = soLoggingMap;
	}

	public void addSONote(String soId, String noteType, Map<String, Object> dataMap) {
		ServiceOrder so = serviceOrderDao.getServiceOrder(soId);
		SONote note = noteUtil.getNewNote(so, noteType, dataMap);
		serviceOrderDao.save(note);
	}
	
	public void updatePOSCancellationIndicator(String soId , ServiceOrder serviceOrder) {
		logger.debug("updatePOSCancellationIndicator START");
		logger.debug("updatePOSCancellationIndicator -- SOID -- "+soId);

		SOWorkflowControls soWorkflowControls = null; 
		// soWorkflowControls = serviceOrder.getSOWorkflowControls();		
		soWorkflowControls = serviceOrderProcessDao.getSOWorkflowControls(soId);
		if(soWorkflowControls != null)
		{	
			logger.debug("updatePOSCancellationIndicator -- SOID -- "+soId+"already available in workflow controls");
			soWorkflowControls.setPosCancellationIndicator(1);
			logger.debug("updated POSCancellationIndicator -- SOID -- "+soId);			
		}
		else if(soWorkflowControls == null) 
		{			
			logger.debug("updatePOSCancellationIndicator -- SOID -- "+soId+"already not available in workflow controls");			
			soWorkflowControls = new SOWorkflowControls(); 

			logger.debug("set service order details in soWorkflowControls");
			soWorkflowControls.setServiceOrder(serviceOrder);						
			soWorkflowControls.setSealedBidIndicator(null);
			soWorkflowControls.setAutoAcceptRescheduleRequestIndicator(null);
			soWorkflowControls.setAutoAcceptRescheduleRequestDays(null);
			soWorkflowControls.setAutoAcceptRescheduleRequestCount(null);
			soWorkflowControls.setPosCancellationIndicator(1);
			logger.debug("updated POSCancellationIndicator -- SOID -- "+soId);						
		}	
		logger.debug("Setting workflow control details in service order");
		serviceOrder.setSOWorkflowControls(soWorkflowControls);
		logger.debug("Updating workflow indicator details in database");
	    serviceOrderDao.update(serviceOrder);
		logger.debug("updatePOSCancellationIndicator END");	    
	}	

	public void setNoteUtil(ServiceOrderNoteUtil noteUtil) {
		this.noteUtil = noteUtil;
	}

    public void markServiceOrderNotGroupSearchable(String soId) {
        ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
        sop.setGroupingSearchable(false);
        serviceOrderProcessDao.update(sop);
    }
    
	public void saveClientInvoiceOrder(String incidentID, String soId, int skuId, int clientId)throws BusinessServiceException {
		try {
			ClientInvoiceOrder clientInvoiceOrder = new  ClientInvoiceOrder();
			clientInvoiceOrder.setClientIncidentId(incidentID);			
			clientInvoiceOrder.setSkuId(skuId);
			clientInvoiceOrder.setSoId(soId);
			clientInvoiceOrder.setCreatedDate(new Date());
			clientInvoiceOrder.setClientId(clientId+"");		
			serviceOrderDao.saveClientInvoice(clientInvoiceOrder);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
    
	public ClientInvoiceOrder getClientInvoiceForIncident(String incidentId, Integer skuId){
		return serviceOrderDao.getClientInvoiceForIncident(incidentId,skuId);
	}
    
	public void updateSONotes(String soId,ServiceOrder serviceOrder) {
		logger.debug("updateSONotes start");
		String note = "The current Service order status "+serviceOrder.getWfStatus().toUpperCase()+" for "+soId+" is un-changed. " +
    			"Reason: Cancellation request cannot be processed in the current status. Please take appropriate action.";
		List<SONote> sonote = serviceOrderDao.getNote(serviceOrder,note);
		
		if(sonote.isEmpty()){
			SONote soNote = new SONote();
			soNote.setServiceOrder(serviceOrder);
	    	soNote.setCreatedDate(new Date());
	    	soNote.setSubject("Service Order Update Received");
	    	soNote.setRoleId(3); 	
	    	soNote.setNote(note);
	    	soNote.setCreatedByName("System");
	    	soNote.setModifiedDate(new Date());
	    	soNote.setModifiedBy("System");
	    	soNote.setNoteTypeId(3);
	    	soNote.setEntityId(14L);
	    	soNote.setPrivate(true);
	    	try {
	    		serviceOrderDao.save(soNote);
	    		
	    	}
	    	catch(Exception e){
	    		logger.error(e.getMessage(), e);
	    	} 
		}
		
    	logger.debug("updateSONotes end");
    }

	public void updateSOLogging(String soId, ServiceOrder serviceOrder) {
		logger.debug("updateSOLogging start");
		String log = "The current Service order status "+serviceOrder.getWfStatus().toUpperCase()+" for "+soId+" is un-changed. " +
				"Reason: Cancellation request cannot be processed in the current status. Please take appropriate action.";
		List<SOLogging> solog = serviceOrderDao.getLog(serviceOrder,log);
		
		if(solog.isEmpty()) {
			SOLogging soLog = new SOLogging();
			soLog.setServiceOrder(serviceOrder);
			soLog.setActionId(267);
			soLog.setChgComment(log);
			soLog.setCreatedDate(new Date());
			soLog.setModifiedDate(new Date());
			soLog.setModifiedBy("SYSTEM");
			soLog.setCreatedByName("SYSTEM");
			soLog.setRoleId(6);
			soLog.setEntityId(0L);
			try {
	    		serviceOrderDao.save(soLog);
	    	}
	    	catch(Exception e){
	    		logger.error(e.getMessage(), e);
	    	} 
		}
		
    	logger.debug("updateSOLogging end");
	}
    
	/**@Description: check for logs exits for No Matching CAR rule and Recall provider Not Available
	 * @param soId
	 * @return
	 */
	private boolean validateLoggingForDraft(String soId) {
		boolean isExists =false;
		try{
			isExists = serviceOrderDao.checkNoMatchCarRuleOrRecallProviderExists(soId);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceOrderException("Error occurred when trying to check Draft logging exists for the Order");
		}
	    return isExists;
	}
	public Double getCancelAmount(Integer buyerId) {
		Double cancelFee = serviceOrderDao.getCancelAmount(buyerId);
		return cancelFee;
	}



	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}



	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

/*	 Commenting code SL-20167 : updating purchase amount for canceled tasks
 * public void updatePurchaseAmount(String sku, BigDecimal purchaseAmount,String soId) {
		try {
    		serviceOrderDao.updatePurchaseAmount(sku, purchaseAmount,soId);
    	}
    	catch(Exception e){
    		logger.error(e.getMessage(), e);
    	}
		
	}  */  
}

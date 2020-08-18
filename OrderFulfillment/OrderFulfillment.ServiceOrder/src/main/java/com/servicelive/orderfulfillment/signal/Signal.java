package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.client.SimpleRestClient;
import com.servicelive.orderfulfillment.common.ServiceOrderValidationException;
import com.servicelive.orderfulfillment.signal.forwarder.ISignalForwarder;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.authorization.IServiceOrderAuthorization;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.logging.IServiceOrderLogging;
import com.servicelive.orderfulfillment.lookup.ApplicationFlagLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class Signal implements ISignal {
	protected final Logger logger = Logger.getLogger(getClass());
	static private HashMap<String,ISignal> signals = new HashMap<String,ISignal>();
	// signal properties configured in the context
	private String name;
	private String transitionName;
	private boolean stateDependent;
	private IServiceOrderLogging serviceOrderLogging;
	private IServiceOrderAuthorization serviceOrderAuthorization;
    private ISignalForwarder signalForwarder;
	protected IServiceOrderDao serviceOrderDao;
	private final static String signalNameDelimiter = ":";
	protected QuickLookupCollection quickLookup;
	
	// main signal processing flow controller
	// should facilitate the following:
	// - parse request and extract signal source and value bean
	// - validate received data
	// - update so
	// - 
    public void process(SOElement soe, ServiceOrder so) {

        List<String> validationErrors = validate(soe, so);
        if (validationErrors.isEmpty()) {
            update(soe, so);
        } else {
            throw new ServiceOrderValidationException(validationErrors);
        }
    }
    public void processLead(LeadElement leadElement,LeadHdr leadHdr){
    	updateLead(leadElement,leadHdr);
    }

    /**
     * Template method, to be over-ridden by specialized signal
     * handlers that have knowledge what "valid" data means in their
     * invocation context.
     * @param soe The input to the Signal received from the requestor.
     * @return A non-empty list containing descriptive error messages.
     */
	protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
		return Collections.emptyList();
	}

    /**
     * Template method to be over-ridden by specialized signal handlers
     * The generic implementation does not do anything. If you feel
     * you need to add code here, hold that thought, and create a class
     * that over-rides this method.
     * This class is not abstract because this functionality can be used
     * for signals that do not provide any special functionality through
     * signals, but used only to invoke JBPM processes.
     * @param element
     * @param so
     */
	protected void update(SOElement element, ServiceOrder so) {
        //Tempalate method - can not declare abstract as Signal is generic handler.
	}
	protected void updateLead(LeadElement element, LeadHdr leadHdr) {
        //Tempalate method - can not declare abstract as Signal is generic handler.
	}
	
	// list of rules to check for authorized sources is injected
	// here we implement the rules and check if we are called by an
	// authorized party
	public void authorize(Identification id, ServiceOrder so) throws ServiceOrderException {
		this.serviceOrderAuthorization.authorize(id, so);
	}

	public void logServiceOrder(ServiceOrder so, SOElement request, Identification id, Map<String,Serializable> miscParams) throws ServiceOrderException {
		HashMap<String,Object> logMap = getLogMap(request, id, so);
		logMap.put("id",id);
		logMap.put("request", request);
		logMap.put("SO_ID", so.getSoId());
		logMap.put("params", miscParams);
		this.serviceOrderLogging.logServiceOrder(so,logMap);
	}
	
	protected HashMap<String,Object> getLogMap(SOElement request, Identification id, ServiceOrder target) throws ServiceOrderException {
		return new HashMap<String,Object>();
	}
	
	/**
	 * Override this method in the Signal if you need to use misc parameters. 
	 */
	public void accessMiscParams(Map<String,Serializable> miscParams, Identification id, ServiceOrder so){
		
	}
	
	// map of all signals in the system
	// is used to lookup signal bean by name
	static public ISignal getSignal(SignalType signalType, String soType, SOElement element) {
		String signalName = signalType.name();

		if (soType != null && soType.length() > 0) // lookup is for a specified SO type 
		{
			// is the name of the signal order type specific?
			// generally it's not supposed to be used for signals, but mostly for commands
			// we will just validate and then proceed as normal
			int uind = signalName.indexOf(signalNameDelimiter);
			if ( uind > 0) {
				// name is order type specific. specified SO type better be the same
				if (!signalName.startsWith(soType))
					throw new ServiceOrderException("Specific signal: " + signalName + " was used for type: " + soType);
				else
					signalName=signalName.substring(uind+1); // strip away the type
			}

			// first let's try to lookup SO type specific signal
			String type_signalName = soType + signalNameDelimiter + signalName;
			if (signals.containsKey(type_signalName)) {  // Found a specific signal for the order type
				signalName = type_signalName; //lets make the type name as a normal signal name to get the signal
			}
		}

		// none of the SO type specific lookups were successful. let's lookup the generic name
        ISignal signal = signals.get(signalName);

        //Check if there is a forwarding signal property present
        //This property is used to determine if this signal needs to be forwarded to some other signal based on the data
        if(null != signal.getSignalForwarder()){
            signal = signal.getSignalForwarder().getForwardedSignal(signal, element);
	}

		return signal;
	}
	
	// map of all signals in the system
	// is used to lookup signal bean by name
	static public ISignal getLeadSignal(SignalType signalType, LeadElement element) {
		String signalName = signalType.name();

		/*if (soType != null && soType.length() > 0) // lookup is for a specified SO type 
		{
			// is the name of the signal order type specific?
			// generally it's not supposed to be used for signals, but mostly for commands
			// we will just validate and then proceed as normal
			int uind = signalName.indexOf(signalNameDelimiter);
			if ( uind > 0) {
				// name is order type specific. specified SO type better be the same
				if (!signalName.startsWith(soType))
					throw new ServiceOrderException("Specific signal: " + signalName + " was used for type: " + soType);
				else
					signalName=signalName.substring(uind+1); // strip away the type
			}

			// first let's try to lookup SO type specific signal
			String type_signalName = soType + signalNameDelimiter + signalName;
			if (signals.containsKey(type_signalName)) {  // Found a specific signal for the order type
				signalName = type_signalName; //lets make the type name as a normal signal name to get the signal
			}
		}*/

		// none of the SO type specific lookups were successful. let's lookup the generic name
        ISignal signal = signals.get(signalName);

        //Check if there is a forwarding signal property present
        //This property is used to determine if this signal needs to be forwarded to some other signal based on the data
       /* if(null != signal.getSignalForwarder()){
            signal = signal.getSignalForwarder().getForwardedSignal(signal, element);
        }*/

		return signal;
	}
	
	// check if relay notification is required
		public boolean isRelayServicesNotificationAllowed(ServiceOrder serviceOrder) {
			String soId = null != serviceOrder ? serviceOrder.getSoId() : "";
			logger.info("Entering isRelayServicesNotificationAllowed() for so id - " + soId);
			ApplicationFlagLookup applicationFlagLookup = quickLookup.getApplicationFlagLookup();
			if (!applicationFlagLookup.isInitialized()) {
				throw new ServiceOrderException("Unable to lookup ApplicationFlags. ApplicationFlagLookup not initialized.");
			}
			String relayServicesNotifyFlag = applicationFlagLookup.getPropertyValue("relay_services_notify_flag");
			Long buyerId = serviceOrder.getBuyerId();
			if ((OrderfulfillmentConstants.RELAY_SERVICES_BUYER_ID == buyerId || 
					OrderfulfillmentConstants.TECHTALK_SERVICES_BUYER_ID == buyerId) && ("ON").equals(relayServicesNotifyFlag)) {
				logger.info("Exiting isRelayServicesNotificationAllowed() with value true. for so id - " + soId);
				return true;
			} else {
				logger.info("Exiting isRelayServicesNotificationAllowed() with value false for so id - " + soId);
				return false;
			}
		}
		
		// send relay notification with metadata
		public void sendRelayNotification(ServiceOrder serviceOrder, String eventType, Map<String, String> requestMap) {
			String soId = null != serviceOrder ? serviceOrder.getSoId() : "";
			logger.info("Entering Signal sendRelayNotification. " + soId);
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
				logger.error("Exception occurred in Signal sendRelayNotification due to " + e);
			}

			logger.info("Leaving Signal sendRelayNotification. " + soId);

		}

	// getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		//TODO change the HASHMAP to use ENUM as key
		signals.put(name, this);
	}
	// transition name - generally the same as signal name but with some exceptions
	public String getTransitionName() {
		if (this.transitionName == null) return name;
		return this.transitionName;
	}
	public boolean isStateDependent() {
		return stateDependent;
	}
	public void setStateDependent(boolean stateDependent) {
		this.stateDependent = stateDependent;
	}

	public void setServiceOrderLogging(IServiceOrderLogging soLogging) {
		this.serviceOrderLogging = soLogging;
	}
	public void setServiceOrderAuthorization(
			IServiceOrderAuthorization serviceOrderAuthorization) {
		this.serviceOrderAuthorization = serviceOrderAuthorization;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

    public IServiceOrderDao getServiceOrderDao() {
        return serviceOrderDao;
    }

    public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
        this.serviceOrderDao = serviceOrderDao;
    }

    public ISignalForwarder getSignalForwarder() {
        return signalForwarder;
    }

    public void setSignalForwarder(ISignalForwarder signalForwarder) {
        this.signalForwarder = signalForwarder;
    }
	public QuickLookupCollection getQuickLookup() {
		return quickLookup;
	}
	public void setQuickLookup(QuickLookupCollection quickLookup) {
		this.quickLookup = quickLookup;
	}
}
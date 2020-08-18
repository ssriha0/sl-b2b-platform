package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.util.Map;

import com.servicelive.orderfulfillment.authorization.IServiceOrderAuthorization;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.logging.IServiceOrderLogging;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.signal.forwarder.ISignalForwarder;

public interface ISignal 
{
	public void process(SOElement soe, ServiceOrder so);
	public void processLead(LeadElement leadElement, LeadHdr lead);	
	public void authorize(Identification id, ServiceOrder so) throws ServiceOrderException;
	public void logServiceOrder(ServiceOrder so, SOElement request, Identification id, Map<String,Serializable> miscParams) throws ServiceOrderException;
	
	public String getTransitionName();
	public String getName();
	public boolean isStateDependent();
	
	public void setStateDependent(boolean stateDependent);
	public void setServiceOrderLogging(IServiceOrderLogging soLogging) ;
	public void setServiceOrderAuthorization(IServiceOrderAuthorization serviceOrderAuthorization);
	public void setTransitionName(String transitionName);

    public ISignalForwarder getSignalForwarder();
    public void accessMiscParams(Map<String,Serializable> miscParams, Identification id, ServiceOrder so);

    public boolean isRelayServicesNotificationAllowed(ServiceOrder serviceOrder);
    public void sendRelayNotification(ServiceOrder serviceOrder, String eventType, Map<String, String> requestMap);
}

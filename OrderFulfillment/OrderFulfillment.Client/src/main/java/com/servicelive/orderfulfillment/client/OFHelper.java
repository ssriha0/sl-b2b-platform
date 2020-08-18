package com.servicelive.orderfulfillment.client;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.orderfulfillment.client.utils.Configuration;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateLeadRequest;


public class OFHelper {
	
	private static  final Logger logger = Logger.getLogger(OFHelper.class);
	private OrderFulfillmentRestClient restClient;
	@PersistenceContext
	private EntityManager entityManager;
	
	public OFHelper(){
	    if(Configuration.isOrderFulfillmentEnabled())
	        restClient = new OrderFulfillmentRestClient();
	}
	
	@Transactional(readOnly=true)
	public boolean isNewSo(String soId){
	    if(!Configuration.isOrderFulfillmentEnabled()) return false;
	    
	    Query query = entityManager.createNativeQuery("SELECT new_so FROM so_process_map WHERE so_id=?");
	    query.setParameter(1, soId);
	    try{
	        Boolean result = (Boolean)query.getSingleResult();
	        return result;
	    }catch(NoResultException nre){
	        return false;
	    }	    
	}
	
	@Transactional(readOnly=true)
	public boolean isNewGroup(String groupId){
	    if(!Configuration.isOrderFulfillmentEnabled()) return false;
	    
	    Query query = entityManager.createNativeQuery("SELECT new_so FROM so_process_map WHERE so_group_id=? LIMIT 1");
	    query.setParameter(1, groupId);
	    try{
	        Boolean result = (Boolean)query.getSingleResult();
	        return result;
	    }catch(NoResultException nre){
	        return false;
	    }	    
	}
	
	public ServiceOrder getServiceOrder(String soId){
	    if(!Configuration.isOrderFulfillmentEnabled()){
	        logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
	        return null;
	    }
		return restClient.getServiceOrder(soId);
	}
	
	public List<ServiceOrder> getPendingServiceOrders() {
		if(!Configuration.isOrderFulfillmentEnabled()) {
			logger.error("Call made to OF when OF is disabled.  Please enable to continue.  Returning null.");
			return null;
		}
		return restClient.getPendingServiceOrders().getPendingServiceOrders();
	}
	
	public OrderFulfillmentResponse runOrderFulfillmentProcess(String soId, SignalType signalType, SOElement soe, Identification idfn) {
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
		return runOrderFulfillmentProcess(soId, signalType, soe, idfn, null);
	}
	
	public OrderFulfillmentResponse runOrderFulfillmentProcess(String soId, SignalType signalType, SOElement soe, Identification idfn, List<Parameter> processParameters) {
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		request.setElement(soe);
		request.setIdentification(idfn);
		if (processParameters != null) request.setMiscParameters(processParameters);
		return runOrderFulfillmentProcess(soId, signalType, request);
	}

	public OrderFulfillmentResponse runOrderFulfillmentProcess(String soId, SignalType signalType, OrderFulfillmentRequest request){
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
	    OrderFulfillmentResponse response = restClient.processOrderSignal(soId, signalType, request);
		return response;
	}
	
	public LeadResponse runLeadFulfillmentProcess(String leadId, SignalType signalType, OrderFulfillmentLeadRequest request){
	    
		LeadResponse response = restClient.processLeadSignal(leadId, signalType, request);
		return response;
	}
	
	public OrderFulfillmentResponse runOrderFulfillmentGroupProcess(String groupId, SignalType signalType, SOElement soe, Identification idfn){
		return runOrderFulfillmentGroupProcess(groupId, signalType, soe, idfn, null);
	}
	
	public OrderFulfillmentResponse runOrderFulfillmentGroupProcess(String groupId, SignalType signalType, SOElement soe, Identification idfn, List<Parameter> processParameters){
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		request.setElement(soe);
		request.setIdentification(idfn);
		if (processParameters != null) request.setMiscParameters(processParameters);
		return runOrderFulfillmentGroupProcess(groupId, signalType, request);
	}
	
	public OrderFulfillmentResponse runOrderFulfillmentGroupProcess(String groupId, SignalType signalType, OrderFulfillmentRequest request){
		return restClient.processGroupSignal(groupId, signalType, request);
	}
	
	public OrderFulfillmentResponse createServiceOrder(CreateOrderRequest request){
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
	    OrderFulfillmentResponse response = restClient.createServiceOrder(request);
		return response;
	}
	
	public OrderFulfillmentResponse createServiceOrder(ServiceOrder so, Identification idfn, String buyerState){
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
		CreateOrderRequest request = new CreateOrderRequest();
		request.setServiceOrder(so);
		request.setIdentification(idfn);
		request.setBuyerState(buyerState);
		
		return createServiceOrder(request);
	}
	
	/**
	 * Create lead
	 */
	public LeadResponse createLead(CreateLeadRequest request){
	    if(!Configuration.isOrderFulfillmentEnabled()){
            logger.error("Call made to OF when OF is disabled. Please enable to continue. Returning null.");
            return null;
        }
	    LeadResponse response = restClient.createLead(request);
		return response;
	}

    public boolean isOFEnabled() {
        return Configuration.isOrderFulfillmentEnabled();
    }
    /**
     * Checks the subsystem properties to see if the role type
     * is allowed to perform the actions in OrderFulfillment. The
     * role Id corresponds to values as specified in 
     * <code>com.newco.marketplace.interfaces.OrderConstants</code>
     * 
     * @param buyerRoleId
     * @return
     */
    public boolean isBuyerAllowed(int buyerRoleId){
        boolean returnVal = true;
        if(StringUtils.isNotBlank(Configuration.getAllowedBuyerRoles())){
            returnVal = StringUtils.contains(Configuration.getAllowedBuyerRoles(), String.valueOf(buyerRoleId));
        }
        return returnVal;
    }

	public boolean isSignalAvailable(String soId, SignalType signalType) {
		return restClient.isSignalAvailable(soId, signalType);
	}

	/**
	 * Determines whether or not order preparation step is required during
	 * service order creation for requests made by the supplied buyer role
	 * 
	 * @param buyerRoleId
	 * @return
	 */
	public boolean isOrderPrepRequiredForRole(Integer buyerRoleId) {
		if (buyerRoleId == null) throw new IllegalArgumentException("The supplied buyerRoleId must not be null; it was null in this invocation.");
		return buyerRoleId == RoleType.Buyer.getId();
	}
}

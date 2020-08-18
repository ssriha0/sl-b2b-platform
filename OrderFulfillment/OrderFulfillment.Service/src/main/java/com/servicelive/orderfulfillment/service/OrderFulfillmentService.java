package com.servicelive.orderfulfillment.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicelive.orderfulfillment.common.*;
import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.ProcessingBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.buyer.OrderBuyerCollection;
import com.servicelive.orderfulfillment.serviceinterface.IOrderFulfillmentBO;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.PendingServiceOrdersResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.vo.CacheRefreshResponse;

/**
 * 
 * @author Yunus Burhani
 *
 * URL architecture for OF service:
 *---------------------------------
 * /orders - order collection
 *   POST /orders - create new item
 *
 *"/orders/{soId} - individual order"
 *"GET /orders/{soId} - get order"
 *"PUT /orders/{soId} - edit order"
 *"DELETE /orders/{soId} - delete order"
 *
 *"/group/{groupId} - Group order"
 *"GET /group/{groupId} - get order"
 *"PUT /group/{groupId} - edit order"
 *"DELETE /group/{groupId} - delete order"
 *
 * /signals - collection of signals
 *   GET /signals - access to meta-data (future)
 *
 * "/signals/{signalName} - specific signal"
 *   "GET /signals/{signalName} - access to meta-data (future)"
 *
 * "/orders/{soId}/signals/{signalName} - specific signal for order"
 *   "POST /orders/{soId}/signals/{signalName} - post a signal"
 *
 * purpose of this class is to map URLs, query string parameters and request body to calls
 * to ServiceOrderBO
 */
@Path("/")
public class OrderFulfillmentService implements IOrderFulfillmentBO {
	private Logger logger = Logger.getLogger(getClass());
	
	private ProcessingBO processingBO;
    private QuickLookupCollection qckLkup;
    private ControllerForRemoteServiceStartupDependentInitializer remoteServiceStartupInitializer;
    private OrderBuyerCollection orderBuyers;


    @GET
    @Path("/cache/refresh/{cacheName}")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public CacheRefreshResponse refreshCache(@PathParam("cacheName") String cacheName) {

        /* TO BE IMPLEMENTED */

        CacheRefreshResponse response = new CacheRefreshResponse();
        response.setMessage("Cache name: " + cacheName);
        return response;

    }

    @GET
    @Path("/cache/refreshAll")
    @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public CacheRefreshResponse refreshAll() {

        CacheRefreshResponse response = new CacheRefreshResponse();
        try {
            orderBuyers.initializeAllOrderBuyers();
            qckLkup.initializeLocalNodeLookups();
            remoteServiceStartupInitializer.runInitializers();

            response.setMessage("All caches refreshed");
        } catch (Exception e) {
            response.addError(e.getMessage());
            logger.error(e);
        }


        return response;

    }
		
	// order creation API
	@POST
	@Path("/orders")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse createServiceOrder(CreateOrderRequest request) {
		return performCreateServiceOrder(request);
	}
	
	// lead creation API
	@POST
	@Path("/lead")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LeadResponse createLead(CreateLeadRequest request) {
		LeadResponse leadResponse = new LeadResponse();
		leadResponse = performLead(request);
		return leadResponse;
	}
	
	// generic signal processing API
	@POST
	@Path("/orders/{soId}/signals/{signalName}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse processOrderSignal(
			@PathParam("soId") String soId,
			@PathParam("signalName") SignalType signalName,
			OrderFulfillmentRequest request) {
		return performProcessOrderSignal(soId,signalName,request);
	}
	
	// generic lead signal processing API
	@POST
	@Path("/lead/{leadId}/signals/{signalName}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public LeadResponse processLeadSignal(
			@PathParam("leadId") String leadId,
			@PathParam("signalName") SignalType signalName,
			OrderFulfillmentLeadRequest request) {
		return performProcessLeadSignal(leadId,signalName,request);
	}
	
	// generic signal processing API
	@POST
	@Path("/group/{groupId}/signals/{signalName}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse processGroupSignal(
			@PathParam("groupId") String groupId,
			@PathParam("signalName") SignalType signalType,
			OrderFulfillmentRequest request) {
		return performProcessGroupSignal(groupId,signalType,request);
	}

	// edit order - preserves REST semantics, but internally calls generic signal processing
	// can be also called via generic API method
	@PUT
	@Path("/orders/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse editServiceOrder(
			@PathParam("soId") String soId,
			OrderFulfillmentRequest request) {
		return performProcessOrderSignal(soId,SignalType.EDIT_ORDER,request);
	}
	
	// delete order - preserves REST semantics, but internally calls generic signal processing
	// can be also called via generic API method
	@DELETE
	@Path("/orders/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse deleteServiceOrder(@PathParam("soId") String soId) 
	{
		return performProcessOrderSignal(soId,SignalType.CANCEL_ORDER,null);
	}
	
	// get order - preserves REST semantics
	@GET
	@Path("/orders/{soId}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ServiceOrder getServiceOrder(@PathParam("soId") String soId) {
		
		return processingBO.getServiceOrder(soId);
	}
	
	// get order - preserves REST semantics
	@GET
	@Path("/orders/{soId}/signals/{signalName}")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public OrderFulfillmentResponse isSignalAvailable(@PathParam("soId") String soId, @PathParam("signalName") SignalType signalType) {
		
		return processingBO.isSignalAvailable(soId, signalType);
	}
	
	@GET
	@Path("/pending-orders")
	@Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public PendingServiceOrdersResponse getPendingServiceOrders() {

		PendingServiceOrdersResponse resp = new PendingServiceOrdersResponse();
		List<ServiceOrder> sos = processingBO.getPendingServiceOrders();
		resp.setPendingServiceOrders(sos);
		return resp;
	}
    
	public void setProcessingBO(ProcessingBO processingBO) {
		this.processingBO = processingBO;
	}

    public void setQckLkup(QuickLookupCollection qckLkup) {
        this.qckLkup = qckLkup;
    }

    public void setRemoteServiceStartupInitializer(ControllerForRemoteServiceStartupDependentInitializer remoteServiceStartupInitializer) {
        this.remoteServiceStartupInitializer = remoteServiceStartupInitializer;
    }

    public void setOrderBuyers(OrderBuyerCollection orderBuyers) {
        this.orderBuyers = orderBuyers;
    }

    OrderFulfillmentResponse performCreateServiceOrder(CreateOrderRequest request)
	{
		OrderFulfillmentResponse response = new OrderFulfillmentResponse();
    	long start = System.currentTimeMillis();

		try {
			logger.info("[performCreateServiceOrder]>>> Entering");

			Map<String,Object> pvars = new HashMap<String,Object>();

			pvars.put(ProcessVariableUtil.PVKEY_USER_NAME, request.getIdentification().getUsername());
			// buyer state is needed for many wallet operations - adding to process vars
			pvars.put(ProcessVariableUtil.PVKEY_BUYER_STATE_CODE, request.getBuyerState());
			
			String soId = processingBO.executeCreateWithGroups(
								request.getIdentification(),
								request.getServiceOrder(),
								pvars
							);
			
			response.setSoId(soId);		
			
			logger.info("[performCreateServiceOrder]>>> Exiting. Newly assigned soId="+soId);
		} catch (ServiceOrderValidationException sov) {
			logger.error(">>><<<<Service Order Exception:");
			response.addError(">>performCreateServiceOrder failed due to validations:");
			response.addErrors(sov.getValidationErrors());
			for (String msg : response.getErrors())	{
				logger.error(msg);
			}
		} catch (ServiceOrderException soe) {
			response.addError(">> performCreateServiceOrder failed");
			response.addErrors(soe.getErrors());
			for (String msg : response.getErrors())	{
				logger.error(msg);
			}
		} catch (Exception e)	{
			response.addError(">> performCreateServiceOrder failed");
			response.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			for (String msg : response.getErrors()) {
				logger.error(msg);
			}
			logger.error(">> General exception stack trace:");
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
			logger.error(">> End of stack trace:");
		}		
    	long end = System.currentTimeMillis();
		logger.info("[performCreateServiceOrder in OrderFulfillmentService]>>> Exiting. Time taken="+(end-start));

		return response;
	}
    
    private  LeadResponse performLead( CreateLeadRequest request){
		LeadResponse leadResponse = new LeadResponse();
		try {
			logger.info("[performLead]>>> Entering");
			Map<String,Object> pvars = new HashMap<String,Object>();
			pvars.put(ProcessVariableUtil.PVKEY_USER_NAME, request.getIdentification().getUsername());			
			String leadId = processingBO.executeCreateLead(request.getIdentification(),request.getLeadHdr(),pvars);			
			leadResponse.setLeadId(leadId);			
			logger.info("[performLead]>>> Exiting. Newly assigned leadId="+leadId);
		} catch (LeadException le){
			leadResponse.addError(">> performLead failed");
			leadResponse.addErrors(le.getErrors());
			for (String msg : leadResponse.getErrors())	{
				logger.error(msg);
			}
		} catch (Exception e){
			leadResponse.addError(">> performLead failed");
			leadResponse.addError(">> General exception. Message:"+e.getMessage()+" Cause:"+e.getCause());
			for (String msg : leadResponse.getErrors()) {
				logger.error(msg);
			}
			logger.error(">> General exception stack trace:");
			for (StackTraceElement ste : e.getStackTrace()) {
				logger.error(ste.toString());
			}
			logger.error(">> End of stack trace:");
		}		
		return leadResponse;
	}
	
	public OrderFulfillmentResponse	performProcessOrderSignal(
				String soId, 
				SignalType signalType, 
				OrderFulfillmentRequest request
			) {
        OrderFulfillmentResponse response = new OrderFulfillmentResponse();
        boolean deadLockDetected = false;
        int attempts = 0;
        do {
            try {
                logger.debug("[performProcessOrderSignal]>>> Entering. Signal name=" + signalType + " soId=" + soId);

                processingBO.executeProcessSignalWithGroups(soId,signalType,request.getIdentification(),request.getElement(), convertToMap(request.getMiscParameters()));
                response.setSoId(soId);
                logger.debug("[performProcessOrderSignal]>>> " + signalType + " for soId " + soId + ". Exiting");
                break;
            } catch (ServiceOrderDeadLockException sde) {
                deadLockDetected = true;
                attempts++;
                logger.warn(String.format("Deadlock detected. Retrying transaction. Attempt no. %1$d retries for service order %2$s",attempts, soId));
            } catch(TransitionNotAvailableException tnae){
                response.addErrors(tnae.getErrors());
                response.setSignalAvailable(false);
                logger.error(response.getErrorMessage());
                logger.error("logging stack trace", tnae);
                break; //Transition is not available - non recoverable error.
            } catch(ServiceOrderValidationException tnae){
                response.addErrors(tnae.getValidationErrors());
                logger.error(response.getErrorMessage());
                logger.error("logging stack trace", tnae);
                break; //Validation errors occurred - non recoverable.
            } catch (ServiceOrderException soe) {
                response.addErrors(soe.getErrors());
                logger.error(response.getErrorMessage());
                logger.error("logging stack trace", soe);
                break; //SericeOrderException - non recoverable error. Let the client know.
            }
            if (attempts > 5) {
                deadLockDetected = false;
                String msgGiveUp = String.format("Couldn't resolve deadlock after %1$d retries for service order %2$s.. Giving up.", attempts, soId);
                logger.error(msgGiveUp);
                response.addError(msgGiveUp);
            }
        } while (deadLockDetected);

        if(0 < attempts && 5 > attempts){
            logger.warn(String.format("Deadlock resolved after %1$d attempts for service order %2$s.",attempts, soId));
        }

        return response;
    }
	
	public LeadResponse	performProcessLeadSignal(
			String leadId, 
			SignalType signalType, 
			OrderFulfillmentLeadRequest request
		) {
	    LeadResponse response = new LeadResponse();
	    boolean deadLockDetected = false;
	    int attempts = 0;
	    do {
	        try {
	            logger.debug("[performProcessOrderSignal]>>> Entering. Signal name=" + signalType + " leadId=" + leadId);
	            processingBO.executeProcessLeadSignal(leadId,signalType,request.getIdentification(),request.getElement(), convertToMap(request.getMiscParameters()));
	            response.setLeadId(leadId);
	            logger.debug("[performProcessOrderSignal]>>> " + signalType + " for soId " + leadId + ". Exiting");
	            break;
	        } catch (LeadDeadLockException lde) {
	            deadLockDetected = true;
	            attempts++;
	            logger.warn(String.format("Deadlock detected. Retrying transaction. Attempt no. %1$d retries for lead %2$s",attempts, leadId));
	        } catch(TransitionNotAvailableException tnae){
	            response.addErrors(tnae.getErrors());
	            response.setSignalAvailable(false);
	            logger.error(response.getErrorMessage());
	            logger.error("logging stack trace", tnae);
	            break; //Transition is not available - non recoverable error.
	        } catch(ServiceOrderValidationException tnae){
	            response.addErrors(tnae.getValidationErrors());
	            logger.error(response.getErrorMessage());
	            logger.error("logging stack trace", tnae);
	            break; //Validation errors occurred - non recoverable.
	        } catch (LeadException le) {
	            response.addErrors(le.getErrors());
	            logger.error(response.getErrorMessage());
	            logger.error("logging stack trace", le);
	            break; //SericeOrderException - non recoverable error. Let the client know.
	        }
	        if (attempts > 5) {
	            deadLockDetected = false;
	            String msgGiveUp = String.format("Couldn't resolve deadlock after %1$d retries for service order %2$s.. Giving up.", attempts, leadId);
	            logger.error(msgGiveUp);
	            response.addError(msgGiveUp);
	        }
	    } while (deadLockDetected);
	
	    if(0 < attempts && 5 > attempts){
	        logger.warn(String.format("Deadlock resolved after %1$d attempts for service order %2$s.",attempts,leadId));
	    }

    return response;
}
	
	public OrderFulfillmentResponse performProcessGroupSignal(String groupId, 
			SignalType signalType, 
			OrderFulfillmentRequest request){
		
		
        OrderFulfillmentResponse response = new OrderFulfillmentResponse();
        boolean deadLockDetected = false;
        int attempts = 0;
        do {
            try {
                logger.debug("[performProcessOrderSignal]>>> Entering. Signal name=" + signalType + " groupId=" + groupId);
                if(signalType.equals(SignalType.UN_GROUP_ORDERS)){
                    processingBO.manualUngroupOrders(groupId, request.getIdentification());
                } else if(signalType.equals(SignalType.GROUP_ORDERS)){
                    if(groupId.equals("-")) groupId = "";
                    groupId = processingBO.manualGroupOrders(groupId, request.getElement(), request.getIdentification());
                    response.setGroupId(groupId);
                } else {
                processingBO.executeProcessGroupSignal(groupId, signalType,request.getIdentification(),request.getElement(), convertToMap(request.getMiscParameters()));
                }
                logger.debug("[performProcessOrderSignal]>>> " + signalType + " for groupId " + groupId + ". Exiting");
                break;
            } catch (ServiceOrderDeadLockException sde) {
                deadLockDetected = true;
                attempts++;
                logger.warn(String.format("Deadlock detected. Retrying transaction. Attempt no. %1$d retries for group order %2$s",attempts, groupId));
            } catch(TransitionNotAvailableException tnae){
                response.addErrors(tnae.getErrors());
                response.setSignalAvailable(false);
                logger.error(response.getErrorMessage());
                logger.error("logging stack trace", tnae);
                break;
            } catch(ServiceOrderValidationException tnae){
                response.addErrors(tnae.getValidationErrors());
                logger.error(response.getErrorMessage());
                logger.error("logging stack trace", tnae);
                break; //Validation errors occurred - non recoverable.
            } catch (ServiceOrderException soe) {
                response.addErrors(soe.getErrors());
                for (String msg : response.getErrors()) {
                    logger.error(msg);
                }
                logger.error("logging stack trace", soe);
                break; //SericeOrderException - non recoverable error. Let the client know.
            }
            if (attempts > 5) {
                deadLockDetected = false;
                String msgGiveUp = String.format("Couldn't resolve deadlock after %1$d retries for group order %2$s.. Giving up.", attempts, groupId);
                logger.error(msgGiveUp);
                response.addError(msgGiveUp);
            }
        } while (deadLockDetected);

        if(0 < attempts && 5 > attempts){
            logger.warn(String.format("Deadlock resolved after %1$d attempts for group order %2$s.",attempts, groupId));
        }

        return response;

	}
	
	protected Map<String, Serializable> convertToMap(List<Parameter> processParams) {
	    Map<String, Serializable> returnVal = new HashMap<String, Serializable>();
	    if (processParams != null){
		    for(Parameter param:processParams){
		        returnVal.put(param.getName(), param.getValue());
		    }
	    }
        return returnVal;
    }
	
}

/**
 * 
 */
package com.servicelive.orderfulfillment.remote.test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.TestException;

import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOScheduleDate;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * @author Mustafa Motiwala
 *
 */
public class TestDelegate {
    private static final Log log = LogFactory.getLog(TestDelegate.class);
    
    public String createServiceOrder(DataFactory dataFactory, OFHelper ofHelper) {
        log.info("Begin Method.");
        CreateOrderRequest requestCreateOrder = dataFactory.newCreateOrderFulfillmentRequest();
        OrderFulfillmentResponse response = ofHelper.createServiceOrder(requestCreateOrder);
        log.info("Created ServiceOrder:" + response.getSoId());
        String soId=response.getSoId();
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        sleep(5);
        log.info("End Method.");
        return soId;
    }

    public String createServiceOrdersFromFile(String fileName, DataFactory dataFactory, OFHelper ofHelper, int delay){
    	SOElementCollection collection = SOJaxBReader.readServiceOrders(fileName);
    	String soId = null;
    	for (SOElement soe : collection.getElements()){
    		ServiceOrder so = (ServiceOrder) soe;
            CreateOrderRequest requestCreateOrder = new CreateOrderRequest();
            requestCreateOrder.setServiceOrder(so);
            requestCreateOrder.setIdentification(dataFactory.newBuyerIdentification(so.getBuyerId()));
            requestCreateOrder.setBuyerState(so.getServiceLocation().getState());
            OrderFulfillmentResponse response = ofHelper.createServiceOrder(requestCreateOrder);
            soId = response.getSoId();
            if(delay > 0){
	            try {
					Thread.sleep(delay * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
    	}    	
    	return soId;
    }
    
    public String createServiceOrderForAutoRouting(DataFactory dataFactory, OFHelper ofHelper) {
        CreateOrderRequest requestCreateOrder = dataFactory.newCreateOrderFulfillmentRequest();

        ServiceOrder serviceOrder = requestCreateOrder.getServiceOrder();
        SOCustomReference customReference = new SOCustomReference();
        customReference.setBuyerRefTypeId(50);
        customReference.setBuyerRefValue("Garage Door Opener");
        serviceOrder.addCustomReference(customReference);

        OrderFulfillmentResponse response = ofHelper.createServiceOrder(requestCreateOrder);

        String soId=response.getSoId();
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }

        return soId;
    }

    public void editServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId) {
        ServiceOrder serviceOrder = dataFactory.getServiceOrderForEdit(soId);
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.EDIT_ORDER, serviceOrder, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            throw new TestException(response.getErrorMessage());
        }
    }

    public void setServiceOrderSpendingLimit(DataFactory dataFactory, OFHelper ofHelper, String soId) {
        ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
        serviceOrder.setSpendLimitLabor(new BigDecimal("0.00"));
        serviceOrder.setSpendLimitParts(new BigDecimal("0.00"));
        serviceOrder.setPriceModel(PriceModelType.ZERO_PRICE_BID);
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.EDIT_ORDER, serviceOrder, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            throw new TestException(response.getErrorMessage());
        }
    }

    public List<RoutedProvider> postServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId){
        log.info("Begin Method.");
        //In the interest of time reducing RoutedProviders to always 2.
//        int nCount = new Random().nextInt(10);
        int nCount = 2;
        log.debug(String.format("Posting service order: %1$s to %2$d number of providers",soId, nCount));
        int i=0;
        SOElementCollection soec = new SOElementCollection();
        List<RoutedProvider> returnVal = new ArrayList<RoutedProvider>();
        do{
            i++;
            ServiceProvider provider = dataFactory.getServiceProvider();
            RoutedProvider routedProvider = new RoutedProvider();
            routedProvider.setVendorId(provider.getProviderFirm().getId());
            routedProvider.setProviderResourceId(provider.getId().longValue());
            soec.addElement(routedProvider);
            returnVal.add(routedProvider);
        }while(i<nCount);
        OrderFulfillmentResponse response= ofHelper.runOrderFulfillmentProcess(soId,SignalType.POST_ORDER, soec, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
//        log.info("Request completed. Sleeping to allow state transition on server.");
//        sleep(25);
        log.info("End Method.");
        return returnVal;
    }

    public void cancelServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, String cancellationComment) {
        List<Parameter> procParms = new ArrayList<Parameter>();
        procParms.add(new Parameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, cancellationComment));
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CANCEL_ORDER, null, dataFactory.newBuyerIdentification(), procParms);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }

    public void rejectServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, List<RoutedProvider> routedProviders) {
        RoutedProvider routedProvider = routedProviders.get(new Random().nextInt(routedProviders.size()));
        rejectServiceOrder(dataFactory, ofHelper, soId, routedProvider);
    }

    public void rejectServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, RoutedProvider routedProvider) {
        routedProvider.setProviderRespReasonId(1);
        routedProvider.setProviderResponse(ProviderResponseType.REJECTED);
        SOElementCollection soeCollectionRoutedProviders = new SOElementCollection();
        soeCollectionRoutedProviders.addElement(routedProvider);
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.REJECT_ORDER, soeCollectionRoutedProviders, dataFactory.newProviderIdentification(routedProvider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }
    
    public void cancelPendingWalletTransaction(DataFactory dataFactory, OFHelper ofHelper, String soId) {
    	OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADMIN_CANCEL_PENDING_TRANSACTION, null, dataFactory.newAdminIdentification());
    	if( !response.getErrors().isEmpty() ) {
    		log.error(response.getErrorMessage());
    		throw new TestException(response.getErrorMessage());
    	}
    }

    public void voidServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId) {
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CANCEL_ORDER, null, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }

    public void requestServiceOrderCancellation(DataFactory dataFactory, OFHelper ofHelper, String soId, String cancellationAmount) {
        List<Parameter> procParms = new ArrayList<Parameter>();
        procParms.add(new Parameter(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT, cancellationAmount));
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CANCEL_ORDER, null, dataFactory.newBuyerIdentification(), procParms);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }
    
    public List<ServiceOrder> getPendingServiceOrders(OFHelper ofHelper) {
    	return ofHelper.getPendingServiceOrders();
    }

    public RoutedProvider acceptServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, List<RoutedProvider> routedProviders) {
        //Create the Request Element:
        RoutedProvider rp = routedProviders.get(new Random().nextInt(routedProviders.size()));
        acceptServiceOrder(dataFactory, ofHelper, soId, rp);
        return rp;
    }

    public void acceptServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, RoutedProvider provider ){
        ServiceOrder so = new ServiceOrder();
        so.setSoId(soId);
        so.setAcceptedProviderId(provider.getVendorId().longValue());
        so.setAcceptedProviderResourceId(provider.getProviderResourceId());
        so.setSoTermsCondId(10);
        so.setProviderSOTermsCondInd(1);
        so.setProviderTermsCondDate(Calendar.getInstance().getTime());
        so.addContact(dataFactory.newProviderSOContact(provider.getProviderResourceId()));
        so.addLocation(dataFactory.newProviderSOLocation());

        //Fire the signal:
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ACCEPT_ORDER, so, dataFactory.newProviderIdentification(provider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void acceptGroupOrder(DataFactory dataFactory, OFHelper ofHelper, String groupId, RoutedProvider provider ){
        ServiceOrder so = new ServiceOrder();
        so.setAcceptedProviderId(provider.getVendorId().longValue());
        so.setAcceptedProviderResourceId(provider.getProviderResourceId());
        so.setSoTermsCondId(10);
        so.setProviderSOTermsCondInd(1);
        so.setProviderTermsCondDate(Calendar.getInstance().getTime());
        so.addContact(dataFactory.newProviderSOContact(provider.getProviderResourceId()));
        SOLocation providerLocation = dataFactory.newProviderSOLocation();
        so.addLocation(providerLocation);
        OrderFulfillmentRequest request = new OrderFulfillmentRequest();
        request.setElement(so);
        request.setIdentification(dataFactory.newProviderIdentification(provider));
        request.addMiscParameter(OrderfulfillmentConstants.PVKEY_ACCEPTED_PROVIDER_STATE, providerLocation.getState());
        //Fire the signal:
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentGroupProcess(groupId, SignalType.ACCEPT_GROUP, request);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }


    private static final int COND_PROVIDER_IDX = 0;

    public void createConditionalOffer(DataFactory dataFactory, OFHelper ofHelper, String soId, List<RoutedProvider> routedProviders) {
        RoutedProvider routedProvider = routedProviders.get(COND_PROVIDER_IDX);
        routedProvider.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);
        Calendar calTime = Calendar.getInstance();
        calTime.add(Calendar.MONTH, 1);
        routedProvider.setOfferExpirationDate(calTime.getTime());
        routedProvider.setProviderRespReasonId(8);
        routedProvider.setTotalHours(10d);
        routedProvider.setIncreaseSpendLimit(new BigDecimal("1000.00"));
        routedProvider.setProviderRespComment("take it or leave it");

        SOSchedule soSchedule = dataFactory.newSOSchedule();
        routedProvider.setSchedule(new SOScheduleDate());
        routedProvider.getSchedule().setServiceDate1(soSchedule.getServiceDate1());
        routedProvider.getSchedule().setServiceDate2(soSchedule.getServiceDate2());
        routedProvider.getSchedule().setServiceTimeStart(soSchedule.getServiceTimeStart());
        routedProvider.getSchedule().setServiceTimeEnd(soSchedule.getServiceTimeEnd());

        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CREATE_CONDITIONAL_OFFER, routedProvider, dataFactory.newProviderIdentification(routedProvider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }

    public void buyerAcceptServiceOrderConditionally(DataFactory dataFactory, OFHelper ofHelper, String soId, List<RoutedProvider> routedProviders) {
        RoutedProvider routedProvider = routedProviders.get(COND_PROVIDER_IDX);
        ServiceOrder so = ofHelper.getServiceOrder(soId);

        so.setSoTermsCondId(9);
        so.setProviderSOTermsCondInd(1);
        so.setProviderTermsCondDate(Calendar.getInstance().getTime());
        so.setAcceptedProviderId(routedProvider.getVendorId().longValue());
        so.setAcceptedProviderResourceId(routedProvider.getProviderResourceId());

        so.addContact(dataFactory.newProviderSOContact(routedProvider.getProviderResourceId()));
        so.addLocation(dataFactory.newProviderSOLocation());

        //Fire the signal:
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ACCEPT_CONDITIONAL_OFFER, so, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

	public void releaseOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, RoutedProvider routedProvider) {
		routedProvider.setProviderResponse(ProviderResponseType.RELEASED);
		routedProvider.setProviderRespReasonId(null);
		routedProvider.setProviderRespComment("releasing the order");
		Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		routedProvider.setProviderRespDate(providerRespDate);
        //Fire the signal:
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_RELEASE_ORDER, routedProvider, dataFactory.newProviderIdentification(routedProvider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
	}
	
    public void buyerRequestReschedule(DataFactory dataFactory, OFHelper helper, String soId){
        log.info("Begin Method");
        SOSchedule reschedule = new SOSchedule();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 1);
        reschedule.setServiceDate1(now.getTime());
        now.add(Calendar.MONTH, 1);
        reschedule.setServiceDate2(now.getTime());
        reschedule.setServiceDateTypeId(SOScheduleType.DATERANGE);
        reschedule.setServiceTimeStart("12:00 AM");
        reschedule.setServiceTimeEnd("12:00 PM");
        OrderFulfillmentResponse response = helper.runOrderFulfillmentProcess(soId, SignalType.BUYER_REQUEST_RESCHEDULE, reschedule, dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void buyerAcceptReschedule(DataFactory dataFactory,OFHelper ofHelper, String soId) {
        log.info("Begin Method");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_ACCEPT_RESCHEDULE, new SOSchedule(), dataFactory.newBuyerIdentification());
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void providerRequestReschedule(DataFactory dataFactory,OFHelper ofHelper, String soId, RoutedProvider provider) {
        log.info("Begin Method");
        SOSchedule reschedule = new SOSchedule();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, 1);
        reschedule.setServiceDate1(now.getTime());
        now.add(Calendar.MONTH, 1);
        reschedule.setServiceDate2(now.getTime());
        reschedule.setServiceDateTypeId(SOScheduleType.DATERANGE);
        reschedule.setServiceTimeStart("12:00 AM");
        reschedule.setServiceTimeEnd("12:00 PM");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_REQUEST_RESCHEDULE, reschedule, dataFactory.newProviderIdentification(provider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void providerAcceptReschedule(DataFactory dataFactory,OFHelper ofHelper, String soId, RoutedProvider acceptedProvider) {
        log.info("Begin Method");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_ACCEPT_RESCHEDULE, new SOSchedule(), dataFactory.newProviderIdentification(acceptedProvider));
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void providerReportProblem(DataFactory dataFactory,OFHelper ofHelper, String soId, RoutedProvider acceptedProvider) {
        log.info("Begin Method");
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newProviderIdentification(acceptedProvider));
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, "Testing Problem Creation for Provider using RemoteTesting framework.");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_REPORT_PROBLEM, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void providerResolveProblem(DataFactory dataFactory,OFHelper ofHelper, String soId, RoutedProvider acceptedProvider) {
        log.info("Begin Method");
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newProviderIdentification(acceptedProvider));
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, "Testing Problem Resolution for Provider using RemoteTesting framework.");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.PROVIDER_RESOLVE_PROBLEM, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void buyerReportProblem(DataFactory dataFactory,OFHelper ofHelper, String soId) {
        log.info("Begin Method");
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newBuyerIdentification());
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, "Testing Problem Creation for Buyer using RemoteTesting framework.");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_REPORT_PROBLEM, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

     public void buyerResolveProblem(DataFactory dataFactory,OFHelper ofHelper, String soId) {
        log.info("Begin Method");
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newBuyerIdentification());
        ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, "Testing Problem Resolution for Buyer using RemoteTesting framework.");
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.BUYER_RESOLVE_PROBLEM, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }
     
    public void activateServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId){
        log.info("Begin Method");
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newAdminIdentification());
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ACTIVATE_ORDER, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }
    
    public void completeServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId, RoutedProvider acceptedProvider ) {
        ServiceOrder so = new ServiceOrder();
        so.setSoId(soId);
        so.setResolutionDs("job well done. I should get paid now");
        so.setFinalPriceParts(new BigDecimal(new Random().nextInt(500)));
        so.setFinalPriceLabor(new BigDecimal(new Random().nextInt(500)));
        
        //Fire the signal:
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newProviderIdentification(acceptedProvider));
        ofRequest.setElement(so);
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.COMPLETE_ORDER, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }

    public void recallOrderCompletion(DataFactory dataFactory, OFHelper ofHelper, String soId, RoutedProvider acceptedProvider) {
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newProviderIdentification(acceptedProvider));
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.RECALL_COMPLETION, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }

    public void addBuyerNote(DataFactory dataFactory, OFHelper ofHelper, String soId) {
        SONote soNote = dataFactory.newBuyerNote();
        List<Parameter> procParms = new ArrayList<Parameter>();
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.ADD_NOTE, soNote, dataFactory.newBuyerIdentification(), procParms);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
    }

    public void closeServiceOrder(DataFactory dataFactory, OFHelper ofHelper, String soId){
        ServiceOrder so = new ServiceOrder();
        so.setSoId(soId);
        so.setFinalPriceParts(new BigDecimal(new Random().nextInt(500)));
        so.setFinalPriceLabor(new BigDecimal(new Random().nextInt(500)));
        
        //Fire the signal:
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newBuyerIdentification());
        ofRequest.setElement(so);
        OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(soId, SignalType.CLOSE_ORDER, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
        log.info("End Method.");
    }
    
    private void sleep(int seconds){
        try{
            Thread.sleep(seconds*1000);
        }catch(InterruptedException ie){/*resume*/}
    }

	public void providerOnSiteVisit(DataFactory dataFactory, OFHelper ofHelper,
			String serviceOrderId, RoutedProvider acceptedProvider) {
	    SOOnSiteVisit onSiteVisit = new SOOnSiteVisit();
        
	    Calendar acDate = Calendar.getInstance();
        acDate.add(Calendar.MONTH, new Random().nextInt(12));
        acDate.add(Calendar.DAY_OF_MONTH, new Random().nextInt(28));
	    
	    onSiteVisit.setArrivalDate(acDate.getTime());
	    onSiteVisit.setArrivalInputMethod(acceptedProvider.getProviderResourceId().intValue());
	    onSiteVisit.setDeleteIndicator(0);
	    onSiteVisit.setDepartureCondition("27");
	    onSiteVisit.setDepartureDate(acDate.getTime());
	    onSiteVisit.setDepartureInputMethod(acceptedProvider.getProviderResourceId().intValue());
	    onSiteVisit.setDepartureResourceId(acceptedProvider.getProviderResourceId());
	    onSiteVisit.setIvrCreateDate(null);
	    onSiteVisit.setResourceId(acceptedProvider.getProviderResourceId());
        
        OrderFulfillmentRequest  ofRequest = new OrderFulfillmentRequest();
        ofRequest.setIdentification(dataFactory.newProviderIdentification(acceptedProvider));
        ofRequest.setElement(onSiteVisit);
	    OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(serviceOrderId, SignalType.ON_SITE_VISIT, ofRequest);
        boolean hasErrors = !response.getErrors().isEmpty();
        if(hasErrors){
            log.error(response.getErrorMessage());
            throw new TestException(response.getErrorMessage());
        }
	}

    public void changeSubStatus(DataFactory dataFactory, OFHelper ofHelper, String serviceOrderId, Integer subStatusId){
        ServiceOrder tmpSO=new ServiceOrder();
        tmpSO.setWfSubStatusId(subStatusId);
        ofHelper.runOrderFulfillmentProcess(serviceOrderId, SignalType.UPDATE_SUBSTATUS, tmpSO, dataFactory.newBuyerIdentification());
    }

}



package com.newco.batch.serviceorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOEventBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.InOutVO;
import com.newco.marketplace.dto.vo.serviceorder.SOEventVO;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.relayservicesnotification.service.IRelayServiceNotification;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.v2_0.SOTripVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Parameter;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

public class SOEventProcessor extends ABatchProcess {

	private ISOEventBO eventBO;
	private IOnSiteVisitBO visitBO;
	private IServiceOrderBO serviceOrderBO;
    private OFHelper ofHelper;
	private IRelayServiceNotification relayNotificationService;
	private static final Logger logger = Logger.getLogger(SOEventProcessor.class.getName());
	
	@SuppressWarnings("unchecked")
	@Override
	public int execute() throws BusinessServiceException {
		try {
			String[] outEvent={"so_id","created_date","event_type_id"},outVisit={"so_id","arrival_date"};
			SOEventVO inSOEventVO=new SOEventVO(),outSOEventVO=new SOEventVO();
			InOutVO inOutVO=new InOutVO(inSOEventVO, outSOEventVO),inOutVisit=new InOutVO(null, outVisit),inOutwhereVisit=new InOutVO(null, null);
			SOOnsiteVisitVO lastVisit=null;
			inSOEventVO.setProcessInd("new");
			outSOEventVO.setProcessInd("start");				
			eventBO.updateSOEvent(inOutVO);				
			inOutVO=new InOutVO(inSOEventVO, outEvent);
			inSOEventVO.setProcessInd("start");
			List<SOEventVO> resultSOEventVO=eventBO.selectSOEventVO(inOutVO);
			logger.info("Recors:");
			String prevSoId=null,errorSoId=null;
			List<SOOnsiteVisitVO> resultVisit = null;
			Integer status=null;
			SecurityContext securityContext = SecurityUtil.getSystemSecurityContext();
			if (securityContext.getCompanyId() != null){
				Integer buyerId = securityContext.getCompanyId();
				ServiceOrderUtil.enrichSecurityContext(securityContext, buyerId);
			}

			inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
			ServiceOrder so=null;
			for(SOEventVO event : resultSOEventVO ){
				if(prevSoId==null || !prevSoId.equals(event.getServiceOrderID())){
					prevSoId=event.getServiceOrderID();
					logger.info("IVR..SO for IVR:"+event.getServiceOrderID());
					so=	serviceOrderBO.getServiceOrder(prevSoId);
					if(so!=null)
						status=so.getWfStateId();
					if(OrderConstants.ACCEPTED_STATUS==status.intValue()){
						boolean isError = false;
						if (ofHelper.isNewSo(prevSoId)){
					        Identification id = new Identification();
					        id.setEntityType(EntityType.SLADMIN);
					        id.setUsername(securityContext.getSlAdminUName());
					        id.setRoleId(securityContext.getAdminRoleId());
							OrderFulfillmentResponse response = ofHelper.runOrderFulfillmentProcess(event.getServiceOrderID(), SignalType.ACTIVATE_ORDER, null, id);
							isError = response.isError();
						} else {
							ProcessResponse pr=serviceOrderBO.activateAcceptedSO(prevSoId, securityContext);
							isError = !pr.getCode().equals(ServiceConstants.VALID_RC);
						}
						logger.info("IVR..SO for IVR: Accepted"+event.getServiceOrderID());
						if(isError){
							inSOEventVO=new SOEventVO();
							inSOEventVO.setServiceOrderID(prevSoId);
							inSOEventVO.setProcessInd("start");
							outSOEventVO.setProcessInd("new");	
							inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
							eventBO.updateSOEvent(inOutVO);	
							errorSoId=prevSoId;	
							continue;
						}
						status=OrderConstants.ACTIVE_STATUS;
						logger.info("IVR..Made to active :"+event.getServiceOrderID());
					}else if(status==null || (OrderConstants.ACTIVE_STATUS!=status.intValue()  && OrderConstants.PROBLEM_STATUS!=status.intValue())){
						inSOEventVO=new SOEventVO();
						inSOEventVO.setServiceOrderID(prevSoId);
						inSOEventVO.setProcessInd("start");
						outSOEventVO.setProcessInd("invalid state");	
						inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
						eventBO.updateSOEvent(inOutVO);	
						errorSoId=prevSoId;
					}else{errorSoId=null;}
					logger.info("IVR..Selecting IVR pre records"+event.getServiceOrderID());
					SOOnsiteVisitVO inSOOnSiteVisitVO=new SOOnsiteVisitVO();
					inSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
					inOutVisit.setIn(inSOOnSiteVisitVO);
					resultVisit=visitBO.selectSOOnSiteVisit(inOutVisit);	
					lastVisit=null;
					if(resultVisit!=null && !resultVisit.isEmpty()){
						lastVisit = resultVisit.get(resultVisit.size()-1);
					}
				}
				if(errorSoId!=null){continue;}
				logger.info("IVR..Is there exception"+event.getServiceOrderID());
				event.setCreateDate(new Date(TimeUtils.convertToGMT(new Timestamp(event.getCreateDate().getTime()), so.getServiceLocationTimeZone()).getTime()));
				logger.info("IVR..No exception"+event.getServiceOrderID());
				SOOnsiteVisitVO outSOOnSiteVisitVO=new SOOnsiteVisitVO();					
				if(event.getEventTypeID().equals(new Long(1))){
					logger.info("IVR..Event type is 1"+event.getServiceOrderID());
					outSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
					outSOOnSiteVisitVO.setArrivalDate(event.getCreateDate());
					outSOOnSiteVisitVO.setArrivalInputMethod(1);
					outSOOnSiteVisitVO.setResourceId(securityContext.getVendBuyerResId());
					outSOOnSiteVisitVO.setCreatedDate(event.getCreateDate());
					outSOOnSiteVisitVO.setIvrcreatedate(event.getCreateDate());
					//R12.0 IVR arrival reason code update
					outSOOnSiteVisitVO.setArrivalReason(OrderConstants.ARRIVAL_DEPARTURE_REASONCODE_IVR);
					visitBO.insert(outSOOnSiteVisitVO);
					logger.info("IVR..Inserted  row :"+event.getServiceOrderID());
					//R12.0 Create new trip on IVR arrival
					Long arrivalVisitId = 0L;
					if(null != outSOOnSiteVisitVO.getVisitId()){
						arrivalVisitId=outSOOnSiteVisitVO.getVisitId();
					}
					
					createOrUpdateTrip(so,event,arrivalVisitId,OrderConstants.IVR_ARRIVAL);
					
					//Update the webhook event for every checkin
					createWebhookEvent(so, event, OrderConstants.ORDER_CHECKED_IN_BY_PROVIDER);
					
					if(OrderConstants.ACTIVE_STATUS==status.intValue()){
						serviceOrderBO.updateSOSubStatus(event.getServiceOrderID(), OrderConstants.PROVIDER_ONSITE, securityContext);
						logger.info("IVR..Updated substatus :"+event.getServiceOrderID());
					}
				}
				else{
					outSOOnSiteVisitVO.setDepartureDate(event.getCreateDate());
					outSOOnSiteVisitVO.setDepartureInputMethod(1);	
					outSOOnSiteVisitVO.setDepartureResourceId(securityContext.getVendBuyerResId());
					outSOOnSiteVisitVO.setDepartureCondition(Constants.EVENT_SUBSTATUS_MAP.get(event.getEventReasonCode()));
					//R12.0 IVR departure reason code update
					outSOOnSiteVisitVO.setDepartureReason(OrderConstants.ARRIVAL_DEPARTURE_REASONCODE_IVR);
					logger.info("IVR..Other event type :"+event.getServiceOrderID());
					if(lastVisit!=null && lastVisit.getDepartureDate()==null && lastVisit.getArrivalDate().before(event.getCreateDate())){
						logger.info("IVR..Inside update..");
						SOOnsiteVisitVO inSOOnSiteVisitVO=new SOOnsiteVisitVO();
						inSOOnSiteVisitVO.setVisitId(lastVisit.getVisitId());
						inOutwhereVisit.setIn(inSOOnSiteVisitVO);
						inOutwhereVisit.setOut(outSOOnSiteVisitVO);
						visitBO.updateSOOnSiteVisit(inOutwhereVisit);
						logger.info("IVR..Updated onsite visit :"+event.getServiceOrderID());
						//R12.0 Update current trip on IVR departure
						Long departureVisitId = 0L;
						if(null != lastVisit.getVisitId()){
							departureVisitId=lastVisit.getVisitId();
						}
						createOrUpdateTrip(so,event,departureVisitId,OrderConstants.IVR_DEPARTURE_UPDATE);
						
						//Update the webhook event for every check-out
						createWebhookEvent(so, event, OrderConstants.ORDER_CHECKED_OUT_BY_PROVIDER);
					}
					else{						
						outSOOnSiteVisitVO.setSoId(event.getServiceOrderID());
						outSOOnSiteVisitVO.setCreatedDate(event.getCreateDate());
						outSOOnSiteVisitVO.setIvrcreatedate(event.getCreateDate());	
						//R12.0 IVR departure reason code update
						lastVisit=visitBO.insert(outSOOnSiteVisitVO);
						logger.info("IVR..Inserted onsite visit :"+event.getServiceOrderID());
						Long departureVisitId = 0l;
						if(null != lastVisit.getVisitId()){
							departureVisitId=lastVisit.getVisitId();
						}
						//R12.0 Create new trip on IVR departure
						//When only departure entry to so_onsite_visit table, create a new trip with only the departure details.
						createOrUpdateTrip(so,event,departureVisitId,OrderConstants.IVR_DEPARTURE_INSERT);
						
						//Update the webhook event for every check-out
						createWebhookEvent(so, event, OrderConstants.ORDER_CHECKED_OUT_BY_PROVIDER);
					}
					if(event.getEventReasonCode().intValue()!=OrderConstants.NOTAPPLICABLE.intValue()){
						logger.info("IVR..Inserted reason code :"+event.getServiceOrderID());
						int subStatusId = outSOOnSiteVisitVO.getDepartureCondition() != null ? outSOOnSiteVisitVO.getDepartureCondition().intValue() : 0;
						logger.info("IVR.. Substatus :"+subStatusId+", for "+event.getServiceOrderID());
						if(ofHelper.isNewSo(event.getServiceOrderID())){
							logger.info("IVR.. New SO :"+event.getServiceOrderID());
					        Identification id = new Identification();
					        id.setEntityType(EntityType.PROVIDER);
					        id.setUsername(securityContext.getUsername());
					        id.setRoleId(securityContext.getRoleId());
					        //SL-18992 Setting the company id to fix production issue related with IVR
					        if(null!=so.getAcceptedVendorId())
					        {
					        id.setCompanyId(so.getAcceptedVendorId().longValue());
					        }
					        else
					        {
					        id.setCompanyId(null);	
					        }
					        id.setResourceId(Long.parseLong(event.getResourceID()));
					        List<Parameter> parameters = new ArrayList<Parameter>();
					        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, "Depart Issues"));
					        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_PROBLEM_SUBSTATUS_ID, String.valueOf(subStatusId)));
					        //SL-18992 Setting the PVKEY_PROBLEM_DESC variable depending on the reason type id
					        //which is problem type column of so_workflow_controls to fix production issue related with IVR
					        String problemDesc="";
					        if(null!=event.getEventReasonCode())
					        {
					        problemDesc=Constants.EVENT_REASON_CODE.get(event.getEventReasonCode());
					        }				     
					        parameters.add(new Parameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC,problemDesc));
							ofHelper.runOrderFulfillmentProcess(event.getServiceOrderID(), SignalType.PROVIDER_REPORT_PROBLEM, null, id, parameters);
							logger.info("Report a problem Done"+event.getServiceOrderID());
						}else {
							logger.info("Report a problem for old SO"+event.getServiceOrderID());
							serviceOrderBO.reportProblem(event.getServiceOrderID(), subStatusId, "Depart Issues", securityContext.getVendBuyerResId(), securityContext.getRoleId(), "IVR update", securityContext.getUsername(), false,securityContext);
							logger.info("Report a problem for old SO Done"+event.getServiceOrderID());
						}
					}else{
						logger.info("Updating substatus to job done"+event.getServiceOrderID());
						serviceOrderBO.updateSOSubStatus(event.getServiceOrderID(), OrderConstants.JOB_DONE, securityContext);
						logger.info("Updating substatus to job done.. Done"+event.getServiceOrderID());
					}						
				}
				lastVisit=outSOOnSiteVisitVO;
				inSOEventVO=new SOEventVO();
				inSOEventVO.setProcessInd("start");
				inSOEventVO.setEventID(event.getEventID());
				outSOEventVO.setProcessInd("processed");	
				inOutVO=new InOutVO(inSOEventVO, outSOEventVO);
				logger.info("Updating to processed"+event.getServiceOrderID());
				eventBO.updateSOEvent(inOutVO);				
				logger.info("Updating to processed..Done"+event.getServiceOrderID());
			}
		} catch (BusinessServiceException e) {
			logger.error("processOnSiteVisits failed", e);
			throw new BusinessServiceException("processOnSiteVisits failed", e);
		}
		return 0;
	}
	
	/**
	 * Method to create webhook event for check-in or check-out by provider
	 * @param so
	 * @param event
	 * @param orderCheckedInByProvider
	 */
	private void createWebhookEvent(ServiceOrder so, SOEventVO event, String eventName)  throws BusinessServiceException{
		if (so.getBuyerId() != null){
			logger.info("so.getBuyerId :"+so.getBuyerId());
			boolean	relayServicesNotifyFlag=relayNotificationService.isRelayServicesNotificationNeeded(so.getBuyerId(), event.getServiceOrderID());
			logger.info("relayServicesNotifyFlag :"+relayServicesNotifyFlag);
			logger.info("eventName :"+eventName);
			if(relayServicesNotifyFlag){
				relayNotificationService.sentNotificationRelayServices(eventName,event.getServiceOrderID());
			}
		}
		
	}

	/**
	 * Method create/update the trip on IVR flow. 
	 * 
	 * Trip logic as below
	 * Check In from IVR --> Create New Trip with tripNo=currentTripNo+1 with only arrival entries and trip status OPEN
	 * Check Out from IVR
	 *  1. if only departure entry is inserted in so_onsite_visit --> Create New Trip with tripNo= currentTripNo+1 with only departure entries and trip status ENDED 
	 *  2. if departure entry is updated against the existing so_onsite_visit arrival entry 
	 *  	a. if no OPEN trip present --> Create New Trip with tripNo= currentTripNo+1 with only departure entries and trip status ENDED 
	 *  	b. if an OPEN trip is present --> update the existing trip's departure entries and trip status ENDED.
	 */
	private void createOrUpdateTrip(ServiceOrder so, SOEventVO event, Long visitId,String mode)  throws BusinessServiceException{
		SOTripVO trip = new SOTripVO();
		mapSOTrip(so,event,visitId,mode,trip);
		//Insert to so_onsite_visit flow on Arrival and departure only entry while departure 
		if(OrderConstants.IVR_ARRIVAL.equals(mode) || OrderConstants.IVR_DEPARTURE_INSERT.equals(mode)){
			trip.setTripNo(trip.getTripNo()+1);
			visitBO.insertNewTripIVR(trip);
		}
		else if(OrderConstants.IVR_DEPARTURE_UPDATE.equals(mode)){
			if(0== trip.getTripNo()){
				//No trip entry, but have a valid so_onsite_visit entry arrival, hence open a departure only trip with ENDED status
				//this scenario might not occur after data migration
				trip.setTripNo(trip.getTripNo()+1);
				visitBO.insertNewTripIVR(trip);
			}else{
				String latestTripStatus = visitBO.findlatestTripStatus(event.getServiceOrderID(),trip.getTripNo());
				if(OrderConstants.TRIP_STATUS_OPEN.equals(latestTripStatus)){
					//latest trip status is open, update the trip status to ENDED
					visitBO.updateTripIVR(trip);
				}else{
					//latest trip status is not open, hence open a departure only trip with ENDED status
					trip.setTripNo(trip.getTripNo()+1);
					visitBO.insertNewTripIVR(trip);
				}
			}
		}
	}
	
	/**
	 * Create the SOTripVO object
	 * @param so
	 * @param event
	 * @param visitId
	 * @param mode
	 * @param trip
	 * @return SOTripVO
	 * @throws BusinessServiceException
	 */
	private SOTripVO mapSOTrip(ServiceOrder so,SOEventVO event,Long visitId,String mode, SOTripVO trip) throws BusinessServiceException{
		Integer latestTrip = null;
		String userName = null;
		//common Trip attributes --START
		latestTrip = visitBO.fetchLatestTripSO(event.getServiceOrderID());
		if(null == latestTrip){
			trip.setTripNo(0);
		}else{
			trip.setTripNo(latestTrip.intValue());
		}
		trip.setSoId(event.getServiceOrderID());
		trip.setModifiedDate(event.getCreateDate());
		if(StringUtils.isNotEmpty(event.getResourceID())){
			userName = visitBO.fetchUserName(event.getResourceID());
		}
		trip.setCurrentApptStartDate(so.getServiceDateGMT1());
		trip.setCurrentApptEndDate(so.getServiceDateGMT2());
		trip.setCurrentApptStartTime(so.getServiceTimeStartGMT());
		trip.setCurrentApptEndTime(so.getServiceTimeEndGMT());
		
		trip.setCreatedDate(event.getCreateDate());
		trip.setModifiedDate(event.getCreateDate());
		trip.setCreatedBy(userName);
		trip.setModifiedBy(userName);
		//common Trip attributes --END
		
		//Create new trip on arrival --START
		if(OrderConstants.IVR_ARRIVAL.equals(mode)){
			trip.setTripStatus(OrderConstants.TRIP_STATUS_OPEN);
			trip.setTripStartVisitId(visitId);
			trip.setTripStartSource(OrderConstants.ARRIVAL_DEPARTURE_SOURCE_IVR);
		}
		//Create new trip on arrival --END
		
		//Create or update trip on departure with only departure entry OR Update departure --START
		else if(OrderConstants.IVR_DEPARTURE_INSERT.equals(mode) || OrderConstants.IVR_DEPARTURE_UPDATE.equals(mode)){
			trip.setTripStatus(OrderConstants.TRIP_STATUS_CLOSED);
			trip.setTripEndVisitId(visitId);
			trip.setTripEndSource(OrderConstants.ARRIVAL_DEPARTURE_SOURCE_IVR);
		}
		//Create or update trip on departure with only departure entry OR Update departure --END
		return trip;
	}

	@Override
	public void setArgs(String[] args) {
		//do nothing
	}

	public void setEventBO(ISOEventBO eventBO) {
		this.eventBO = eventBO;
	}

	public void setVisitBO(IOnSiteVisitBO visitBO) {
		this.visitBO = visitBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	public IRelayServiceNotification getRelayNotificationService() {
		return relayNotificationService;
	}

	public void setRelayNotificationService(
			IRelayServiceNotification relayNotificationService) {
		this.relayNotificationService = relayNotificationService;
	}

}

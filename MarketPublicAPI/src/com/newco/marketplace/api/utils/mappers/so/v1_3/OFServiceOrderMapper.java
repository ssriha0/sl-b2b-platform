package com.newco.marketplace.api.utils.mappers.so.v1_3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Phone;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.Skus;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.create.v1_3.SOCreateRequest;
import com.newco.marketplace.api.beans.so.create.v1_3.ServiceOrderBean;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.provider.SkillAssignBOImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOServiceDatetimeSlot;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.CarrierType;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;

public class OFServiceOrderMapper extends OFMapper {
	private Logger logger = Logger.getLogger(OFServiceOrderMapper.class);
	
	private IBuyerBO buyerBO;
	private PromoBO promoBO;
	private IServiceOrderBO serviceOrderBO;
	private SkillAssignBOImpl skillAssignBOImpl;
	private SimpleDateFormat sdfToTime = new SimpleDateFormat("hh:mm a");
	private ILookupBO lookupBO;
	private ArrayList<ServiceTypesVO> serviceTypes = null;
	private ArrayList<LookupVO> lookUpVOList = null;
	private ArrayList<LookupVO> shippingCarrierList = null;
	
	/**
	 * This method is for mapping CreateSORequest Object to ServiceOrder Object.
	 * 
	 * @param request  ServiceOrderBean
	 * @param securityContext SecurityContext
	 * @throws DataException
	 * @return serviceOrder
	 */
	public CreateOrderRequest createServiceOrder(SOCreateRequest createRequest, SecurityContext securityContext) throws BusinessServiceException, ValidationException {

		logger.info("Entering OFServiceOrderMapper.createServiceOrder()");

		CreateOrderRequest orderRequest = new CreateOrderRequest();
		ServiceOrder serviceOrder = new ServiceOrder();
		ServiceOrderBean request = createRequest.getServiceOrder();


		// buyer setup
		mapBuyerInformation(orderRequest, serviceOrder, securityContext,createRequest.getIdentification());

		//map rest of service order
		mapServiceOrder(serviceOrder, request, securityContext);

		orderRequest.setServiceOrder(serviceOrder);
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification
		= createBuyerIdentFromSecCtx(securityContext);

		/* SL-16834 :If the buyer resource id is present in the request,
		 * set the buyer resource id and the contact id of the buyer resource
		 * while creating the service order. */
		if(null!=createRequest.getIdentification() && null!= createRequest.getIdentification().getId() &&
				PublicAPIConstant.BUYER_RESOURCE_ID.equalsIgnoreCase
				(createRequest.getIdentification().getType())){

			identification.setResourceId((long)createRequest.getIdentification().getId());
		}
		orderRequest.setIdentification(identification);
		serviceOrder.setCreatedViaAPI(true);
		logger.info("Exiting OFServiceOrderMapper.createServiceOrder()");
		return orderRequest;
	}

	private void mapBuyerInformation(CreateOrderRequest orderRequest, ServiceOrder serviceOrder, 
			SecurityContext securityContext,Identification identification) throws BusinessServiceException{

		if (securityContext.getCompanyId() == null) {
			logger.error("The company ID is null in the Security Context");
			throw new NullPointerException(CommonUtility.getMessage(
					PublicAPIConstant.COMPANY_ERROR_CODE));
		}
		serviceOrder.setBuyerId(new Long(securityContext.getCompanyId()));

		// Set Buyer Resource Id (Buyer associate who is creating the Service Order)
		if (securityContext.getVendBuyerResId() == null) {
			logger.error("The VendBuyerResource Id is null "
					+ "in the Security Context");
			throw new NullPointerException(CommonUtility.getMessage(
					PublicAPIConstant.VENDOR_BUYERRESOURCE_ERROR_CODE));
		}

		/* SL-16834 :If the buyer resource id is present in the request,
		 * set the buyer resource id and the contact id of the buyer resource
		 * while creating the service order. */
		if(null!=identification && null!= identification.getId() &&
				PublicAPIConstant.BUYER_RESOURCE_ID.equalsIgnoreCase(identification.getType())){
			serviceOrder.setBuyerResourceId((long)(identification.getId()));

			// SL-16834 :Get the contact id of the buyer resource and set the id.
			ContactLocationVO contactLocationVO=new ContactLocationVO();
			contactLocationVO=buyerBO.getBuyerResContactLocationVO
					(securityContext.getCompanyId(),identification.getId());
			Integer buyerContactId=0;
			if(contactLocationVO!=null&&contactLocationVO.getBuyerPrimaryContact()!=null){
				buyerContactId=contactLocationVO.getBuyerPrimaryContact().getContactId();
			}
			serviceOrder.setBuyerContactId(buyerContactId);
		}else{
			serviceOrder.setBuyerResourceId(new Long(securityContext.getVendBuyerResId()));
			// Set Buyer Resource Contact id as SO buyerContactId
			serviceOrder.setBuyerContactId(securityContext.getRoles().getContactId());
		}	

		try {
			Buyer buyer = buyerBO.getBuyer(securityContext.getCompanyId());
			serviceOrder.setFundingTypeId(buyer.getFundingTypeId());
			serviceOrder.setPostingFee(new BigDecimal(buyer.getPostingFee()));
			serviceOrder.setCancellationFee(new BigDecimal(buyer.getCancellationFee()));
			Double postingfee = promoBO.retrievePromoPostingFeeByRoleId(securityContext.getRoleId());
			if(postingfee != null){
				serviceOrder.setPostingFee(new BigDecimal(postingfee));
			}
		} catch (Exception e) {
			logger.error("Error happened when trying to get the funding type for buyer", e);
			throw new BusinessServiceException(e);
		}
		//get buyer state
		try {
			ContactLocationVO buyerContact = buyerBO.getBuyerContactLocationVO(securityContext.getCompanyId());
			orderRequest.setBuyerState(buyerContact.getBuyerPrimaryLocation().getState());
		} catch (Exception e) {
			logger.error("Error happened when trying to get the funding type for buyer", e);
			throw new BusinessServiceException(e);
		}

		serviceOrder.setCreatorUserName(securityContext.getUsername());
	}
	
	private void mapServiceOrder(ServiceOrder serviceOrder, ServiceOrderBean request, SecurityContext securityContext) throws ValidationException{

		List<String> validationErrors = new ArrayList<String>();
		SOContact pickUpContact = null;

		// For mapping General Section
		mapGeneralSection(serviceOrder, request.getGeneralSection(), validationErrors);

		// For mapping Scope Of Work
		mapScopeOfWork(serviceOrder, request.getScopeOfWork(),serviceOrder.getBuyerId(),validationErrors);

		// For mapping ServiceLocation
		validateZipAndState(PublicAPIConstant.SERVICE,request.getServiceLocation().getZip(),request.getServiceLocation().getState(),validationErrors);

		//set serviceOrder timezone
		serviceOrder.setServiceLocationTimeZone(LocationUtils.getTimeZone(request.getServiceLocation().getZip()));
		
		//R12_1
		//SL-20177 : Invalid zip code validation was failing due to empty time zone
		//Setting the time zone as default value "EST"
		if(null==serviceOrder.getServiceLocationTimeZone()){
			serviceOrder.setServiceLocationTimeZone(OrderConstants.EST_ZONE);
		}
		TimeZone timezone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		int offset = Math.round(timezone.getOffset((new Date()).getTime()) / (1000 * 60 * 60));
		serviceOrder.setServiceDateTimezoneOffset(new Integer(offset));

		mapServiceLocation(serviceOrder, request.getServiceLocation());

		if(null != request.getScheduleServiceSlot()){
			if ( null != request.getSchedule()){
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_SCHEDULE_SLOTS));
			}
			// For mapping Schedule Details
			mapServiceOrderSchedule(serviceOrder, request.getScheduleServiceSlot(), validationErrors);
			Collections.sort(serviceOrder.getSoServiceDatetimeSlot());
			Schedule schedule = new Schedule();
			for(ServiceDatetimeSlot serviceDatetimeSlot : request.getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot()){
				
				if("1".equals(serviceDatetimeSlot.getPreferenceInd())){
					schedule.setServiceDateTime1(serviceDatetimeSlot.getServiceStartDate());
					schedule.setServiceDateTime2(serviceDatetimeSlot.getServiceEndDate());
				}
			}
			//int size = request.getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot().size();
			//schedule.setServiceDateTime1(request.getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot().get(0).getServiceStartDate());
			//schedule.setServiceDateTime2(request.getScheduleServiceSlot().getServiceDatetimeSlots().getServiceDatetimeSlot().get(0).getServiceEndDate());
			schedule.setConfirmWithCustomer(request.getScheduleServiceSlot().getConfirmWithCustomer());
			schedule.setReschedule(request.getScheduleServiceSlot().getReschedule());
			schedule.setScheduleType(request.getScheduleServiceSlot().getScheduleType());
			schedule.setServiceLocationTimezone(request.getScheduleServiceSlot().getServiceLocationTimezone());
			request.setSchedule(schedule);
		} else if ( null == request.getSchedule()){
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_SCHEDULE));
			throw new ValidationException(validationErrors);
		}
		// For mapping Schedule Details
		mapServiceOrderSchedule(serviceOrder, request.getSchedule(),validationErrors);

		// For mapping Pricing Details
		mapServiceOrderPricing(serviceOrder, request.getPricing(),validationErrors);

		// For mapping Contact Details
		pickUpContact = mapServiceOrderContacts(serviceOrder, request.getContacts(), validationErrors);

		// For mapping Parts Details
		mapServiceOrderParts(serviceOrder, request.getParts(), pickUpContact, validationErrors);

		// For mapping Custom References
		mapServiceOrderCustomRef(serviceOrder, request.getCustomReferences(), request.getTemplate(), securityContext.getCompanyId());

		if(!validationErrors.isEmpty()){
			throw new ValidationException(validationErrors);
		}
	}
	
	/**
	 * This method is for mapping the General Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param generalSection
	 *            GeneralSection
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder, GeneralSection generalSection, List<String> validationErrors) {

		logger.info("Mapping: General Section --->Starts");
		serviceOrder.setSowTitle(generalSection.getTitle());
		serviceOrder.setSowDs(generalSection.getOverview());
		serviceOrder.setBuyerTermsCond(generalSection.getBuyerTerms());
		serviceOrder.setProviderInstructions(generalSection.getSpecialInstructions());
		boolean isSoUniqueSoFeature = buyerBO.validateFeatureSet(serviceOrder.getBuyerId(), BuyerFeatureConstants.UNIQUE_ORDER);
		if (isSoUniqueSoFeature && StringUtils.isNotBlank(generalSection.getBuyerSoId())) {
			String soId = null;
			if(50 < generalSection.getBuyerSoId().length()){
				// provide proper error
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_UNIQUE_ID));
			}
			try {
				soId = serviceOrderBO.isUniqueSo(serviceOrder.getBuyerId().intValue(), generalSection.getBuyerSoId());
			} catch (DataServiceException e) {
				logger.error("Exception Occurred while calling serviceOrderBO.isUniqueSo.");
				e.printStackTrace();
			}
			if(null != soId && 0 < soId.trim().length()){
				ArrayList<Object> argumentList = new ArrayList<Object>();
				argumentList.add(soId);
				argumentList.add(generalSection.getBuyerSoId());
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.SERVICEORDER_NOT_UNIQUE, argumentList));
			}
			serviceOrder.setBuyerSoId(generalSection.getBuyerSoId());
		}
	}
	
	/**
	 * This method is for mapping the Scope Of Work Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param scopeOfWork ScopeOfWork
	 * @param buyerId Integer
	 * @param validationErrors 
	 * @return void
	 * @throws BusinessServiceException 
	 */
	private void mapScopeOfWork(ServiceOrder serviceOrder, ScopeOfWork scopeOfWork, Long buyerId, List<String> validationErrors) {

		logger.info("Mapping: Scope Of Work --->Starts");
		validateSkuAndTask(scopeOfWork.getTasks(),scopeOfWork.getSkus(),validationErrors);
		if(!validationErrors.isEmpty()){
			return;
		}
		try{
			//fetching the root node id for a particular node id
			Integer rootNodeId = skillAssignBOImpl.getRootNodeId(Integer.parseInt(scopeOfWork.getMainCategoryID()));
			serviceOrder.setPrimarySkillCatId(rootNodeId);
		} catch (BusinessServiceException e) {
			logger.error("Exception Occurred while getting root node Id using node Id");
		}

		List<SOTask> tasks = new ArrayList<SOTask>();
		if(null!=scopeOfWork.getSkus()&& null!=scopeOfWork.getSkus().getSkuList()){//orders from BIP
			logger.info("Converting SKU's to tasks");
			for(String sku : scopeOfWork.getSkus().getSkuList()){
				SOTask task = new SOTask();
				logger.debug("Converting SKU-->"+sku);
				task.setExternalSku(sku);
				tasks.add(task);
			}
			//make the first task as primary
			if(tasks.size() > 0) tasks.get(0).setPrimaryTask(true);
			serviceOrder.setTasks(tasks);
		}else{//B2C orders
			logger.info("Mapping Task details  starts----");
			Tasks reqTasks=scopeOfWork.getTasks();
			if (null!=reqTasks && null!=reqTasks.getTaskList()) {
				Iterator<Task> taskList = reqTasks.getTaskList().iterator();
				while (taskList.hasNext()) {
					Task task = (Task) taskList.next();
					SOTask serviceOrderTask = new SOTask();
					logger.info("Mapping Task details for-->"+task.getTaskName());
					serviceOrderTask.setTaskName(task.getTaskName());
					logger.debug("Mapping Task details for Category-->"+task.getCategoryID());
					logger.debug("Mapping Task details---->"+task.getTaskName());
					if (null != task.getCategoryID()) {
						serviceOrderTask.setSkillNodeId(Integer.parseInt(task.getCategoryID()));
						serviceOrderTask.setServiceTypeId(getServiceTypeId(serviceOrder.getPrimarySkillCatId(), task.getServiceType()));
					}

					serviceOrderTask.setTaskComments(task.getTaskComment());
					tasks.add(serviceOrderTask);
				}
				/* Set the first task as primary task.
				 * Set whether tasks are present*/
				if(tasks.size() > 0){
					tasks.get(0).setPrimaryTask(true);
					serviceOrder.setTasksPresent(true);
				}
				serviceOrder.setTasks(tasks);
			}
		}

	}
	
	/**
	 * This method is for validating the zipCode and State
	 * 
	 * @param locationType String
	 * @param zip String
	 * @param state String
	 */
	private void validateZipAndState(String locationType,String zip,String state,List<String> validationErrors) {

		int zipValidation = LocationUtils.checkIfZipAndStateValid(zip,state);
		switch (zipValidation) {
		case Constants.LocationConstants.ZIP_NOT_VALID:
			if(PublicAPIConstant.SERVICE.equals(locationType)){
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.ZIPCODEINVALID_SERVICE));
			}else{
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.ZIPCODEINVALID_PICKUP));
			}
			break;

		case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			if(PublicAPIConstant.SERVICE.equals(locationType)){
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STATEINVALID_SERVICE));
			}else{
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STATEINVALID_PICKUP));
			}
			break;

		}
	}
	
	/**
	 * This method is for mapping the ServiceLocation Section of Create Request
	 * to Service Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param location Location
	 * @return void
	 */
	private void mapServiceLocation(ServiceOrder serviceOrder, Location location) {
		logger.info("Mapping: Service Location--->Starts");

		SOLocation serviceLocation = new SOLocation();
		String locationType = location.getLocationType();

		if (locationType != null) {
			LocationClassification locationClass = null;
			try {
				locationClass = LocationClassification.valueOf(locationType.toUpperCase());
			} catch (IllegalArgumentException e) {}
			serviceLocation.setSoLocationClassId(locationClass);
		}
		serviceLocation.setLocationName(location.getLocationName());
		serviceLocation.setStreet1(location.getAddress1());
		serviceLocation.setStreet2(location.getAddress2());
		serviceLocation.setCity(location.getCity());
		serviceLocation.setState(location.getState());
		serviceLocation.setZip(location.getZip());
		serviceLocation.setLocationNote(location.getNote());
		//add service location
		serviceLocation.setSoLocationTypeId(LocationType.SERVICE);
		serviceOrder.addLocation(serviceLocation);
		serviceLocation.setCreatedDate(new Timestamp(DateUtils.getCurrentDate()
				.getTime()));

	}
	
	/**
	 * This method is for mapping the Schedule Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param schedule schedule
	 */
	private void mapServiceOrderSchedule(ServiceOrder serviceOrder,
			Schedule schedule,List<String> validationErrors) {
		logger.info("Mapping: ServiceOrder Schedule --->Starts");

		SOSchedule soSchedule = new SOSchedule();
		if (null != schedule.getServiceDateTime1()
				&& !schedule.getServiceDateTime1().equals("")) {
			soSchedule.setServiceDate1(new Timestamp(DateUtils
					.defaultFormatStringToDate(schedule.getServiceDateTime1())
					.getTime()));
			// setting the servicestartTime
			Date serviceStartTime = null;
			try {
				serviceStartTime = CommonUtility.sdfToDate.parse(schedule
						.getServiceDateTime1());
			} catch (ParseException e) {
				logger.error("Exception Occurred while setting Service "
						+ "Start Time");
			}
			String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
			soSchedule.setServiceTimeStart(serviceStartTimeStr);
		}

		if (PublicAPIConstant.DATETYPE_FIXED.equalsIgnoreCase((schedule.getScheduleType()))) {
			soSchedule.setServiceDate2(null);
			soSchedule.setServiceTimeEnd(null);
			soSchedule.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
		} else {
			if (PublicAPIConstant.DATETYPE_PREFERENCES.equalsIgnoreCase((schedule.getScheduleType()))) {
				soSchedule.setServiceDateTypeId(SOScheduleType.PREFERENCE);
			}else{
				soSchedule.setServiceDateTypeId(SOScheduleType.DATERANGE);
			}
			if (null != schedule.getServiceDateTime2()
					&& !schedule.getServiceDateTime2().equals("")) {
				soSchedule.setServiceDate2(new Timestamp(DateUtils
						.defaultFormatStringToDate(
								schedule.getServiceDateTime2()).getTime()));
				// setting the serviceEndTime
				Date serviceEndDate = null;
				try {
					serviceEndDate = CommonUtility.sdfToDate.parse(schedule
							.getServiceDateTime2());
				} catch (ParseException e) {
					logger.error("Parse Exception Occurred while "
							+ "setting ServiceEndDate");
				}
				String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
				soSchedule.setServiceTimeEnd(serviceEndTimeStr);

			}else{
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.ENDDATE_REQUIRED));
				return;
			}
		}
		serviceOrder.setSchedule(soSchedule);
		validateSODates(serviceOrder,validationErrors);
		if(!validationErrors.isEmpty()){
			return;
		}		
		//serviceOrderBO.GivenTimeZoneToGMT(serviceOrder);
		if (PublicAPIConstant.CONFIRM_CUSTOMER_TRUE.equalsIgnoreCase(schedule
				.getConfirmWithCustomer())) {		
			serviceOrder.setProviderServiceConfirmInd(1);
		} else if (PublicAPIConstant.CONFIRM_CUSTOMER_FALSE.equalsIgnoreCase(schedule
				.getConfirmWithCustomer())) {			
			serviceOrder.setProviderServiceConfirmInd(0);
		}		
	}
	
	/**
	 * This method is for mapping the Schedule Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param scheduleServiceSlot
	 *            schedule
	 */
	private void mapServiceOrderSchedule(ServiceOrder serviceOrder, ScheduleServiceSlot scheduleServiceSlot, List<String> validationErrors) {
		logger.info("Mapping: ServiceOrder Schedule --->Starts");
		if (null != scheduleServiceSlot && null != scheduleServiceSlot.getServiceDatetimeSlots()
				&& null != scheduleServiceSlot.getServiceDatetimeSlots().getServiceDatetimeSlot()) {
			
			SOServiceDatetimeSlot serviceDatetimeSlot = null;
			logger.info("Total time slots received : " + scheduleServiceSlot.getServiceDatetimeSlots().getServiceDatetimeSlot().size());
			for (com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot serviceDatetimeSlotTemp : scheduleServiceSlot
					.getServiceDatetimeSlots().getServiceDatetimeSlot()) {
				serviceDatetimeSlot = new SOServiceDatetimeSlot();
				
				Date serviceStartTime = null;
				Date serviceEndTime = null;
				
				try {
					serviceDatetimeSlot.setServiceStartDate(new Timestamp(DateUtils.defaultFormatStringToDate(
							serviceDatetimeSlotTemp.getServiceStartDate()).getTime()));
					serviceDatetimeSlot.setServiceEndDate(new Timestamp(DateUtils.defaultFormatStringToDate(
							serviceDatetimeSlotTemp.getServiceEndDate()).getTime()));

					serviceStartTime = CommonUtility.sdfToDate.parse(serviceDatetimeSlotTemp.getServiceStartDate());
					serviceEndTime = CommonUtility.sdfToDate.parse(serviceDatetimeSlotTemp.getServiceEndDate());
				} catch (ParseException e) {
					logger.error("Start date : " + serviceDatetimeSlotTemp.getServiceStartDate());
					logger.error("End date : " + serviceDatetimeSlotTemp.getServiceEndDate());
					logger.error("Exception Occurred while setting Service Start Time or End Time ", e);
				}
				String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
				String serviceEndTimeStr = sdfToTime.format(serviceEndTime);

				serviceDatetimeSlot.setServiceStartTime(serviceStartTimeStr);
				serviceDatetimeSlot.setServiceEndTime(serviceEndTimeStr);
				serviceDatetimeSlot.setPreferenceInd((StringUtils.isNotBlank(serviceDatetimeSlotTemp.getPreferenceInd()) && StringUtils
						.isNumeric(serviceDatetimeSlotTemp.getPreferenceInd())) ? Integer.parseInt(serviceDatetimeSlotTemp
						.getPreferenceInd()) : null);

				serviceOrder.addSOServiceDatetimeSlots(serviceDatetimeSlot);
			}
			
			logger.info("total slots added : " + serviceOrder.getSoServiceDatetimeSlot().size());

			// validateSODates(serviceOrder, soTitlesMap);
			if (!validationErrors.isEmpty()) {
				return;
			}
			// serviceOrderBO.GivenTimeZoneToGMTForSlots(serviceOrder);
			if (PublicAPIConstant.CONFIRM_CUSTOMER_TRUE.equalsIgnoreCase(scheduleServiceSlot.getConfirmWithCustomer())) {
				serviceOrder.setProviderServiceConfirmInd(1);
			} else {
				serviceOrder.setProviderServiceConfirmInd(0);
			}
		}
	}

	/**
	 * This method is for mapping the Pricing Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder 
	 * @param pricing
	 * @param validationErrors 
	 */
	private void mapServiceOrderPricing(ServiceOrder serviceOrder, Pricing pricing, List<String> validationErrors) {
		logger.info("Mapping: Pricing --->Starts");

		String priceModel = pricing.getPriceModel();

		if (StringUtils.equalsIgnoreCase(Constants.PriceModel.BULLETIN, priceModel)) {
			serviceOrder.setPriceModel(PriceModelType.BULLETIN);
		} else if(StringUtils.equalsIgnoreCase(Constants.PriceModel.ZERO_PRICE_BID, priceModel)){
			serviceOrder.setPriceModel(PriceModelType.ZERO_PRICE_BID);
			/**SL-19728:Prevent Zero Price Bid for No funded Buyer*/
			boolean isNonFunded = buyerBO.validateFeatureSet(serviceOrder.getBuyerId(),BuyerFeatureConstants.NON_FUNDED);
			logger.info("IsNonFunded : "+ isNonFunded);
	         if(null!= serviceOrder.getBuyerId() && isNonFunded){
	        	 validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.BUYER_NOT_AUTHORIZED));
	        	 return;
			  }
			
		}else{
			serviceOrder.setPriceModel(PriceModelType.NAME_PRICE);
		}

		if (null != pricing.getLaborSpendLimit()
				&& (!"".equals(pricing.getLaborSpendLimit()))
				&& (!"0".equals(pricing.getLaborSpendLimit()))
				&& (!"0.0".equals(pricing.getLaborSpendLimit()))) {
			BigDecimal spendLimitLabor =  new BigDecimal(pricing.getLaborSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);
			serviceOrder.setSpendLimitLabor(spendLimitLabor);
		}else{
			if(null==serviceOrder.getSpendLimitLabor()){
				serviceOrder.setSpendLimitLabor(PricingUtil.ZERO);
			}
		}
		if (null != pricing.getPartsSpendLimit()
				&& (!"".equals(pricing.getPartsSpendLimit()))
				&& (!"0".equals(pricing.getPartsSpendLimit()))
				&& (!"0.0".equals(pricing.getPartsSpendLimit()))){
			BigDecimal spendLimitParts =  new BigDecimal(pricing.getPartsSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);
			serviceOrder.setSpendLimitParts(spendLimitParts);
		}else{
			serviceOrder.setSpendLimitParts(PricingUtil.ZERO);
		}
		serviceOrder.setPrice(mapPricing(pricing, serviceOrder));

		if (null != pricing.getFundingSourceId()) {
			serviceOrder.setAccountId(new Integer(pricing.getFundingSourceId()));
		} 
	}

	/**
	 * This method is for mapping Contacts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param contacts
	 * @return void
	 */
	private SOContact mapServiceOrderContacts(ServiceOrder serviceOrder, Contacts contacts,List<String> validationErrors) {

		logger.info("Mapping: Contact Details--->Starts");
		SOContact pickUpContact = null;
		if (null != contacts.getBuyerSupportResID()
				&& (!"0".equals(contacts.getBuyerSupportResID()))) {
			try {
				Integer buyerResId = new Integer(contacts
						.getBuyerSupportResID());
				int buyerId=serviceOrder.getBuyerId().intValue();

				ContactLocationVO contactLocationVO=new ContactLocationVO();
				contactLocationVO=buyerBO.getBuyerResContactLocationVO(buyerId,buyerResId);
				Integer buyerContactId=0;
				if(contactLocationVO!=null&&contactLocationVO.getBuyerPrimaryContact()!=null){
					buyerContactId=contactLocationVO.getBuyerPrimaryContact().getContactId();
					logger.info("buyerContactId------>"+buyerContactId);
					serviceOrder.setBuyerContactId(buyerContactId);
					if(buyerContactId==0 || null== buyerContactId){
						validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.BUYER_RESOURCEID_NOT_VALID));
					}
				}else{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.BUYER_RESOURCEID_NOT_VALID));
				}
			} catch (Exception e) {
				logger.error("Exception Occurred while setting buyerContactid");
			}
		}
		if ((null != contacts && null != contacts.getContactList())&& validationErrors.isEmpty()) {
			Iterator<com.newco.marketplace.api.beans.so.Contact> contactList = 
					contacts.getContactList().iterator();

			while (contactList.hasNext()) {
				com.newco.marketplace.api.beans.so.Contact contactRequst = (
						com.newco.marketplace.api.beans.so.Contact) contactList.next();

				if (contactRequst.getContactLocnType().equalsIgnoreCase(PublicAPIConstant.SERVICE)) {
					//add service contact
					SOContact contact = mapContactDetails(contactRequst);
					if(null != serviceOrder.getServiceLocation()){
						contact.setBusinessName(serviceOrder.getServiceLocation().getLocationName());
					}
					contact.addContactLocation(ContactLocationType.SERVICE);
					serviceOrder.addContact(contact);
				} else if (contactRequst.getContactLocnType().equalsIgnoreCase(PublicAPIConstant.END_USER)) {
					//add end user contact
					SOContact contact = mapContactDetails(contactRequst);
					contact.addContactLocation(ContactLocationType.END_USER);
					serviceOrder.addContact(contact);
				}else if (contactRequst.getContactLocnType().equalsIgnoreCase(
						PublicAPIConstant.PICKUP)) {
					pickUpContact = mapContactDetails(contactRequst);
					pickUpContact.addContactLocation(ContactLocationType.PICKUP);
				}
			}
		}
		return pickUpContact;
	}
   
	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param parts
	 * @param pickUpContact
	 * @return void
	 */
	private List<String>  mapServiceOrderParts(ServiceOrder serviceOrder, Parts parts,
			SOContact pickUpContact, List<String> validationErrors) {

		logger.info("Mapping: Parts --->Starts");
		List<SOPart> partVoList = new ArrayList<SOPart>();
		SOPart partVo = new SOPart();
		if (null != parts && null != parts.getPartList()) {
			Iterator<com.newco.marketplace.api.beans.so.Part> partsList = parts
					.getPartList().iterator();
			while (partsList.hasNext()) {
				com.newco.marketplace.api.beans.so.Part reqPart = (
						com.newco.marketplace.api.beans.so.Part) partsList	.next();
				partVo.setManufacturer(reqPart.getManufacturer());
				partVo.setModelNumber(reqPart.getModel());
				partVo.setPartDs(reqPart.getDescription());
				partVo.setQuantity(reqPart.getQuantity());
				// Dimension
				if (null != reqPart.getDimensions()) {
					partVo.setHeight(reqPart.getDimensions().getHeight());
					partVo.setLength(reqPart.getDimensions().getLength());
					partVo.setWidth(reqPart.getDimensions().getWidth());
					partVo.setWeight(reqPart.getDimensions().getWeight());
					if (PublicAPIConstant.STANDARD_ENGLISH
							.equalsIgnoreCase((reqPart.getDimensions()
									.getMeasurementType()))) {
						partVo.setMeasurementStandard(Integer
								.parseInt(OrderConstants.ENGLISH));
					} else if (PublicAPIConstant.STANDARD_METRIC
							.equalsIgnoreCase(reqPart.getDimensions()
									.getMeasurementType())) {
						partVo.setMeasurementStandard(Integer
								.parseInt(OrderConstants.METRIC));
					}
				}

				Carrier shippingCarrier = new Carrier();
				Carrier returnCarrier = new Carrier();

				shippingCarrier.setCarrierName(reqPart.getShipCarrier());
				shippingCarrier.setTrackingNumber(reqPart.getShipTracking());
				shippingCarrier.setCarrierId(getShippingCarrierId(reqPart
						.getShipCarrier()));
				returnCarrier.setCarrierName(reqPart.getReturnCarrier());
				returnCarrier.setTrackingNumber(reqPart.getReturnTracking());
				returnCarrier.setCarrierId(getShippingCarrierId(reqPart
						.getReturnCarrier()));
				if(returnCarrier.getCarrierId() != null)
					partVo.setReturnCarrierId(CarrierType.fromId(returnCarrier.getCarrierId()));
				if(shippingCarrier.getCarrierId() != null)
					partVo.setShipCarrierId(CarrierType.fromId(shippingCarrier.getCarrierId()));

				partVo.setProviderBringPartInd(Boolean.parseBoolean(reqPart
						.getRequiresPickup()));
				if (partVo.getProviderBringPartInd()) {
					if (reqPart.getPickupLocation() != null) {
						Location pickupLocation = reqPart.getPickupLocation();
						validateZipAndState(PublicAPIConstant.PICKUP,pickupLocation.getZip(),pickupLocation.getState(),validationErrors);
						if(!validationErrors.isEmpty()){
							return validationErrors;
						}

						SOLocation soPickupLocation = new SOLocation();
						if (pickupLocation.getLocationType() != null) {
							LocationClassification locationClass = null;
							try {
								locationClass = LocationClassification.valueOf(pickupLocation.getLocationType());
							} catch (IllegalArgumentException e) {}
							soPickupLocation.setSoLocationClassId(locationClass);
						}
						soPickupLocation.setCreatedDate(new Timestamp(DateUtils
								.getCurrentDate().getTime()));
						soPickupLocation.setLocationName(pickupLocation
								.getLocationName());
						soPickupLocation.setStreet1(pickupLocation
								.getAddress1());
						soPickupLocation.setStreet2(pickupLocation
								.getAddress2());
						soPickupLocation.setCity(pickupLocation.getCity());
						soPickupLocation.setState(pickupLocation.getState());
						soPickupLocation.setZip(pickupLocation.getZip());
						soPickupLocation.setLocationNote(pickupLocation.getNote());
						soPickupLocation.setSoLocationTypeId(LocationType.PICKUP);
						partVo.setPickupLocation(soPickupLocation);
					}
					if (pickUpContact != null) {
						partVo.setPickupContact(pickUpContact);

					} else {
						partVo.setPickupContact(new SOContact());
					}
				}
				partVoList.add(partVo);
			}
			serviceOrder.setParts(partVoList);
		}
		return validationErrors;
	}

	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder  ServiceOrder
	 * @param templateName 
	 * @return void
	 */
	private void mapServiceOrderCustomRef(ServiceOrder serviceOrder, CustomReferences customReferences, String templateName, Integer ownerId) {
		logger.info("Mapping: Customer Reference --->Starts");

		List<SOCustomReference> soCustomRefsVoList = new ArrayList<SOCustomReference>();
		if (null != customReferences && null != customReferences.getCustomRefList()) {
			for (CustomReference customRef : customReferences.getCustomRefList()) {
				//changes for SL-20772 --START
				String refType = customRef.getName();
				SOCustomReference customRefVO = new SOCustomReference();
				if (ownerId.intValue() == PublicAPIConstant.BUYER_9000.intValue()
						&& (PublicAPIConstant.SHOPIFY_ORDER_SKU_PRICE1
								.equalsIgnoreCase(refType)
								|| PublicAPIConstant.SHOPIFY_ORDER_SKU_PRICE2
										.equalsIgnoreCase(refType)
								|| PublicAPIConstant.SHOPIFY_ORDER_SKU_PRICE3
										.equalsIgnoreCase(refType) || PublicAPIConstant.SHOPIFY_ORDER_SKU_PRICE4
									.equalsIgnoreCase(refType))&& StringUtils.isNotBlank(customRef.getValue())) {
					customRefVO.setBuyerRefValue(customRef.getValue().replace(PublicAPIConstant.TARGET_STRING,PublicAPIConstant.REPLACEMENT_STRING));
				}
				else{
					customRefVO.setBuyerRefValue(customRef.getValue());
				}
				//changes for SL-20772 --END
				customRefVO.setBuyerRefTypeName(customRef.getName());
				customRefVO.setBuyerRefTypeId(getCustomRefTypeId(ownerId+"", customRef.getName()));
				soCustomRefsVoList.add(customRefVO);
			}
			//see if the template name is not blank and add it as custom reference
			if(StringUtils.isNotBlank(templateName)){
				SOCustomReference customRefVO = new SOCustomReference();
				customRefVO.setBuyerRefValue(templateName);
				customRefVO.setBuyerRefTypeName(SOCustomReference.CREF_TEMPLATE_NAME);
				customRefVO.setBuyerRefTypeId(getCustomRefTypeId(ownerId+"", SOCustomReference.CREF_TEMPLATE_NAME));
				soCustomRefsVoList.add(customRefVO);
			}
			if (!soCustomRefsVoList.isEmpty()) {
				serviceOrder.setCustomReferences(soCustomRefsVoList);
			}
		}
	}
	
	/**
	 * This method is for validating whether both sku and task present
	 * 
	 * @param tasks Tasks
	 * @param skus Skus
	 * @param validationErrors HashMap<String, Object> 
	 * @return soCreateResponse
	 */
	private void validateSkuAndTask(Tasks tasks, Skus skus, List<String> validationErrors) {
		if(null!=tasks&&null!=skus){
			logger.error("Both Task and SKU Details present");
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.TASK_SKU_ERROR_CODE));
		}
	}
	
	/**
	 * This method is for getting ServiceType Id
	 * 
	 * @param mainCategoryId Integer
	 * @param serviceType String
	 * @return Integer
	 */
	private Integer getServiceTypeId(Integer mainCategoryId, String serviceType) {
		logger.info("Inside createServiceOrder--->Start");
		try {
			/* SL-16563 : Commented the null check to populate the serviceTypes
			 * every time. Added the null check for for loop.*/
			// if (serviceTypes == null) {				
			serviceTypes = skillAssignBOImpl
					.getServiceTypes(mainCategoryId);
			// }
			if(null!= serviceTypes)	{
				for (com.newco.marketplace.vo.provider.ServiceTypesVO serviceTypesVO : serviceTypes) {
					if (serviceTypesVO.getDescription().equalsIgnoreCase(
							serviceType)) {
						return serviceTypesVO.getServiceTypeId();
					}
				}
			}

		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting "
					+ " ServiceTypeId " + e);
		}
		return null;
	}
	
	
	/**
	 * This method is for validating Service Order dates
	 * 
	 * @param serviceOrder ServiceOrder
	 * HashMap<String, Object> soTitlesMap
	 * @return void
	 */
	private void validateSODates(ServiceOrder serviceOrder, List<String> validationErrors) {
		java.util.Date today = new java.util.Date();
		java.sql.Date now = new java.sql.Date(today.getTime());
		SOSchedule soSchedule = serviceOrder.getSchedule();
		Timestamp newStartTimeCombined = new Timestamp(TimeUtils
				.combineDateAndTime((Timestamp) soSchedule.getServiceDate1(),
						soSchedule.getServiceTimeStart(),
						serviceOrder.getServiceLocationTimeZone()).getTime());
		if (null!=soSchedule.getServiceDate2()) {
			Timestamp newEndTimeCombined = new Timestamp(TimeUtils
					.combineDateAndTime((Timestamp) soSchedule.getServiceDate2(),
							soSchedule.getServiceTimeEnd(),
							serviceOrder.getServiceLocationTimeZone()).getTime());

			if ( SOScheduleType.DATERANGE==soSchedule.getServiceDateTypeId()) {
				if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {

					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STARTDATE_ENDDATE));
					return;

				}
			}
		}
		if (newStartTimeCombined.compareTo(now) < 0) {
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STARTDATE_IMPROPER));
			return;
		}
	}
	
	/**
	 * Helper method to converting the pricing object to SOPricing for OF.
	 * @param pricing
	 * @return SOPricing object for the OF Domain.
	 */
	private SOPrice mapPricing(Pricing pricing, ServiceOrder so) {
		SOPrice returnVal = new SOPrice();
		returnVal.setServiceOrder(so);
		BigDecimal laborSpendLimit = new BigDecimal(pricing.getLaborSpendLimit());
		returnVal.setOrigSpendLimitLabor(laborSpendLimit);
		BigDecimal partsSpendLimit = new BigDecimal(pricing.getPartsSpendLimit());
		returnVal.setOrigSpendLimitParts(partsSpendLimit);
		returnVal.setDiscountedSpendLimitLabor(laborSpendLimit);
		returnVal.setDiscountedSpendLimitParts(partsSpendLimit);
		return returnVal;
	}
	
	/**
	 * This method is for mapping Contact Details.
	 * 
	 * @param requestContact Contact
	 * @return contact
	 */
	private SOContact mapContactDetails(
			com.newco.marketplace.api.beans.so.Contact requestContact) {
		logger.info("Inside mapContactDetails--->Start");
		SOContact contact = new SOContact();
		contact.setFirstName(requestContact.getFirstName());
		contact.setLastName(requestContact.getLastName());
		contact.setEmail(requestContact.getEmail());
		Set<SOPhone> phoneVOList = new HashSet<SOPhone>();
		if (requestContact.getPrimaryPhone() != null && requestContact.getPrimaryPhone().getNumber() != null) {
			phoneVOList.add(mapPhoneDetails(requestContact.getPrimaryPhone(), 1));
		}
		if (requestContact.getAltPhone() != null && requestContact.getAltPhone().getNumber() != null) {
			phoneVOList.add(mapPhoneDetails(requestContact.getAltPhone(), 2));
		}
		contact.setPhones(phoneVOList);
		contact.setCreatedDate(new Timestamp(DateUtils.getCurrentDate()
				.getTime()));
		return contact;
	}
	
	private Integer getCustomRefTypeId(String buyerId, String customRefType) {
		logger.info("Inside getCustomRefTypeId--->Start");
		try {

			ArrayList<LuBuyerRefVO> customRefList = lookupBO.getBuyerRef(buyerId);
			for (LuBuyerRefVO customRef : customRefList) {
				if (customRef.getRefType().trim().equalsIgnoreCase(customRefType.trim())) {
					return customRef.getRefTypeId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting "
					+ " CustomerRefId " + "Carrier Details" + e);
		}
		return null;
	}

	/**
	 * This method is for mapping Phone Details.
	 * 
	 * @param phone Phone
	 * @param phoneTypeId int
	 * @return phoneVO
	 */
	private SOPhone mapPhoneDetails(Phone phone, int phoneTypeId) {
		logger.info("Inside mapPhoneDetails--->Start");
		SOPhone phoneVO = new SOPhone();
		phoneVO.setPhoneNo(StringUtils.isEmpty(phone.getNumber()) ? null
				: phone.getNumber().replace("-", ""));
		phoneVO.setPhoneExt(phone.getExtension());
		phoneVO.setPhoneClass(PhoneClassification.fromId(getPhoneTypeId(phone.getPhoneType())));
		phoneVO.setPhoneType(PhoneType.fromId(phoneTypeId));
		return phoneVO;
	}
	
	/**
	 * This method is for getting the Shipping Carrier Id.
	 * 
	 * @param carrierType  String
	 * @return int
	 */
	private Integer getShippingCarrierId(String carrierType) {
		logger.info("Inside getShippingCarrierId--->Start");
		try {
			shippingCarrierList = lookupBO.getShippingCarrier();
			for (LookupVO shippingCarrier : shippingCarrierList) {
				if (shippingCarrier.getDescr().equalsIgnoreCase(carrierType)) {
					return shippingCarrier.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while Setting the Shipping "
					+ "Carrier Details" + e);
		}
		return null;
	}

	/**
	 * This method is for getting Phone TypeId.
	 * 
	 * @param phoneType  String
	 * @return Integer
	 */
	private Integer getPhoneTypeId(String phoneType) {
		logger.info("Inside getPhoneTypeId--->Start " + phoneType);
		if(lookupBO == null) logger.warn("lookupBO is null");
		try {
			if (lookUpVOList == null) {
				lookUpVOList = lookupBO.getPhoneTypes();
			}
			for (LookupVO lookupVO : lookUpVOList) {
				if (phoneType.equalsIgnoreCase(lookupVO.getDescr())) {
					return lookupVO.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting  PhoneTypeId "
					+ e.getMessage(), e);
		}
		return null;
	}
	
	/**SL-20400 : Creating response to show duplicate service order for InHome Buyer (3000)
	 * @param servOrder
	 * @return
	 */
	public SOCreateResponse createDuplicateSOResponse(com.newco.marketplace.dto.vo.serviceorder.ServiceOrder servOrder) {
		SOCreateResponse soCreateResponse = new SOCreateResponse();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId(servOrder.getSoId());
		if (null!=orderStatus.getSoId() && !orderStatus.getSoId().equalsIgnoreCase(PublicAPIConstant.INVALID_SOID)) {
			
				if(StringUtils.isNotBlank(servOrder.getStatus()))
				{	
					orderStatus.setStatus(servOrder.getStatus());
				}else{
					orderStatus.setStatus("");
				}
			
				orderStatus.setSubstatus("");
			
				if(null!=servOrder.getCreatedDate())
				{
					orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date(servOrder.getCreatedDate().getTime())));
				}
			
			soCreateResponse.setOrderstatus(orderStatus);
			
			results=Results.getSuccess(ResultsCode.DRAFT_CREATED.getMessage());
		}else {
			results=Results.getError(ResultsCode.DRAFT_NOT_CREATED.getMessage(), 
					ResultsCode.DRAFT_NOT_CREATED.getCode());
		}
		
		soCreateResponse.setResults(results);
		return soCreateResponse;
	}
	
	/**
	 * This method is for createSOResponse xml from ProcessResponse object.
	 * 
	 * @return soCreateResponse
	 */
	public SOCreateResponse createSOResponseMapping(String soId,List<String> invalidDocumentList) {
		logger.info("Inside createSOResponseMapping--->Start");
		SOCreateResponse soCreateResponse = new SOCreateResponse();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId(soId);
		if (!orderStatus.getSoId().equalsIgnoreCase(PublicAPIConstant.INVALID_SOID)) {

			orderStatus.setStatus(PublicAPIConstant.DRAFT);
			orderStatus.setSubstatus("");
			orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
			soCreateResponse.setOrderstatus(orderStatus);
			results=Results.getSuccess(ResultsCode.DRAFT_CREATED.getMessage());
			if (!invalidDocumentList.isEmpty()) {
				logger.info("Mapping the Invalid Documents with error List");
				errorResult.setCode(ServiceConstants.WARNING_RC);
				String errorMessage =CommonUtility.getMessage(PublicAPIConstant.DOCUMENT_ERROR_CODE);
				int count = 0;
				for (String fileName : invalidDocumentList) {
					errorMessage = errorMessage + fileName ;
					count++;
					if(count != invalidDocumentList.size()){
						errorMessage = errorMessage + "  ::  ";
					}
				}
				results.setError(Results.getError(errorMessage,
						ResultsCode.WARNING.getCode()).getError());
			}

		} else {
			results=Results.getError(ResultsCode.DRAFT_NOT_CREATED.getMessage(), 
					ResultsCode.DRAFT_NOT_CREATED.getCode());
		}
		soCreateResponse.setResults(results);
		return soCreateResponse;
	}

	public IBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public PromoBO getPromoBO() {
		return promoBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public SkillAssignBOImpl getSkillAssignBOImpl() {
		return skillAssignBOImpl;
	}

	public void setSkillAssignBOImpl(SkillAssignBOImpl skillAssignBOImpl) {
		this.skillAssignBOImpl = skillAssignBOImpl;
	}

	public SimpleDateFormat getSdfToTime() {
		return sdfToTime;
	}

	public void setSdfToTime(SimpleDateFormat sdfToTime) {
		this.sdfToTime = sdfToTime;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public ArrayList<ServiceTypesVO> getServiceTypes() {
		return serviceTypes;
	}

	public void setServiceTypes(ArrayList<ServiceTypesVO> serviceTypes) {
		this.serviceTypes = serviceTypes;
	}

	public ArrayList<LookupVO> getLookUpVOList() {
		return lookUpVOList;
	}

	public void setLookUpVOList(ArrayList<LookupVO> lookUpVOList) {
		this.lookUpVOList = lookUpVOList;
	}

	public ArrayList<LookupVO> getShippingCarrierList() {
		return shippingCarrierList;
	}

	public void setShippingCarrierList(ArrayList<LookupVO> shippingCarrierList) {
		this.shippingCarrierList = shippingCarrierList;
	}

}

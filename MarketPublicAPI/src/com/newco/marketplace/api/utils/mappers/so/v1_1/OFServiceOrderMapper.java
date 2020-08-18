package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.beans.so.create.v1_1.SOCreateRequest;
import com.newco.marketplace.api.beans.so.edit.SOEditResponse;
import com.newco.marketplace.api.beans.so.update.SOUpdateRequest;
import com.newco.marketplace.api.beans.so.update.SOUpdateResponse;
import com.newco.marketplace.api.beans.so.update.SoUpdateContact;
import com.newco.marketplace.api.beans.so.update.SoUpdateLocation;
import com.newco.marketplace.api.beans.so.update.SpendLimitDecrease;
import com.newco.marketplace.api.beans.so.update.SpendLimitIncrease;
import com.newco.marketplace.api.common.Identification;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.provider.SkillAssignBOImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.business.iBusiness.reasonCode.IManageReasonCodeBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuBuyerRefVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.provider.ServiceTypesVO;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOSchedule;
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
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;


public class OFServiceOrderMapper extends OFMapper {
	private Logger logger = Logger.getLogger(OFServiceOrderMapper.class);
	private SkillAssignBOImpl skillAssignBOImpl;
	private ILookupBO lookupBO;
	private ArrayList<LookupVO> lookUpVOList = null;
	private List<LookupVO> lookUpVOLists = null;
	private ArrayList<ServiceTypesVO> serviceTypes = null;
	private ArrayList<LookupVO> shippingCarrierList = null;
	private SimpleDateFormat sdfToTime = new SimpleDateFormat("hh:mm a");
	private IBuyerBO buyerBO;
	private PromoBO promoBO;
	private IManageReasonCodeBO manageReasonCodeBO;
	private IServiceOrderBO serviceOrderBO;
	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}
	private OFHelper ofHelper;


	public IManageReasonCodeBO getManageReasonCodeBO() {
		return manageReasonCodeBO;
	}

	public void setManageReasonCodeBO(IManageReasonCodeBO manageReasonCodeBO) {
		this.manageReasonCodeBO = manageReasonCodeBO;
	}

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

	public OrderFulfillmentRequest editServiceOrder(String soId, ServiceOrderBean request, SecurityContext securityContext) throws ValidationException{
		logger.info("Entering OFServiceOrderMapper.editServiceOrder()");
		OrderFulfillmentRequest orderRequest = new OrderFulfillmentRequest();

		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(soId);
		try
		{
		mapBuyerInformationForEdit(serviceOrder, securityContext);
		}
		catch(Exception e)
		{
		logger.info("error while setting Buyer Info"+e);	
		}
		mapServiceOrder(serviceOrder, request, securityContext);
		serviceOrder.setCreatedViaAPI(true);
		serviceOrder.setBuyerId((long)securityContext.getCompanyId());
		Integer buyerRoleId = securityContext.getRoleId();
		if (ofHelper.isOrderPrepRequiredForRole(buyerRoleId)) {
			serviceOrder.setOrderPrepRequired(true);
		}
		orderRequest.setElement(serviceOrder);
		orderRequest.setIdentification(createBuyerIdentFromSecCtx(securityContext));

		logger.info("Exiting OFServiceOrderMapper.editServiceOrder()");
		return orderRequest;
	}

	private void mapServiceOrder(ServiceOrder serviceOrder, ServiceOrderBean request, SecurityContext securityContext) throws ValidationException{

		List<String> validationErrors = new ArrayList<String>();
		SOContact pickUpContact = null;

		// For mapping General Section
		mapGeneralSection(serviceOrder, request.getGeneralSection());

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
	//	/**
	//	 * 
	//	 * 
	//	 * @param companyId Integer
	//	 * @param templateName String
	//	 * @return soTitlesMap HashMap<String, Object>
	//	 */
	//	public List<String> mapTemplateName(Integer companyId,
	//			String templateName, ServiceOrder serviceOrder) {
	//
	//		List<String> fileTitles = null;
	//		BuyerSOTemplateDTO templateDto = buyerSOTemplateBO.loadBuyerSOTemplate(
	//				companyId, templateName);
	//		if (null != templateDto) {
	//
	//			if (null != templateDto.getTerms()) {
	//				serviceOrder.setBuyerTermsCond(templateDto.getTerms());
	//			}
	//
	//			if (null != templateDto.getOverview()) {
	//				serviceOrder.setSowDs(templateDto.getOverview() + "\n"
	//						+ serviceOrder.getSowDs());
	//			}
	//
	//			if (null != templateDto.getTitle()) {
	//				serviceOrder.setSowTitle(templateDto.getTitle()
	//						+ "-"
	//						+ (serviceOrder.getSowTitle() != null ? serviceOrder
	//								.getSowTitle() : ""));
	//			}
	//
	//			if (null != templateDto.getPartsSuppliedBy()) {
	//				serviceOrder.setPartsSupplier(PartSupplierType.fromId(templateDto.getPartsSuppliedBy()));
	//			}
	//
	//			if (null != templateDto.getSpecialInstructions()) {
	//				serviceOrder.setProviderInstructions(serviceOrder
	//						.getProviderInstructions()
	//						+ templateDto.getSpecialInstructions());
	//			}
	//
	//			if (null != templateDto.getConfirmServiceTime()) {
	//				serviceOrder.setProviderServiceConfirmInd(templateDto
	//						.getConfirmServiceTime());
	//			}
	//
	//			if(null != templateDto.getDocumentLogo()){
	//				mapDocumentLogotoSO(serviceOrder,templateDto);
	//			}
	//			
	//			BuyerSOTemplateContactDTO contactDto = templateDto
	//					.getAltBuyerContact();
	//			SOContact contact = new SOContact();
	//			contact.setBusinessName(contactDto.getCompanyName());
	//			contact.setContactTypeId(ContactType.ALTERNATE);
	//			contact.setEmail(contactDto.getEmail());
	//			contact.setFirstName(contactDto.getIndividualName());
	//			//set service alt contact
	//			serviceOrder.addContact(contact);
	//			fileTitles = templateDto.getDocumentTitles();
	//		}
	//		
	//		return fileTitles;
	//	}


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
   
	
	private void mapBuyerInformationForEdit( ServiceOrder serviceOrder, 
			SecurityContext securityContext) throws BusinessServiceException{

		if (securityContext.getCompanyId() == null) {
			logger.error("The company ID is null in the Security Context");
			throw new NullPointerException(CommonUtility.getMessage(
					PublicAPIConstant.COMPANY_ERROR_CODE));
		}
		serviceOrder.setBuyerId(new Long(securityContext.getCompanyId()));

		
			serviceOrder.setBuyerResourceId(new Long(securityContext.getVendBuyerResId()));
			// Set Buyer Resource Contact id as SO buyerContactId
			serviceOrder.setBuyerContactId(securityContext.getRoles().getContactId());
		

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
		

		serviceOrder.setCreatorUserName(securityContext.getUsername());
	}
	
	/**
	 * @param serviceorder 
	 * @param templateDto
	 * @return
	 * It gets the corresponding document id
	 */
	//	private ServiceOrder mapDocumentLogotoSO(ServiceOrder serviceorder,BuyerSOTemplateDTO templateDto) {				
	//					
	//			DocumentVO documentVO = null;
	//			try {
	//					documentVO = documentBO.retrieveDocumentByTitleAndEntityID(
	//										Constants.DocumentTypes.BUYER,templateDto.getDocumentLogo(),
	//										serviceorder.getBuyerId().intValue());
	//			}
	//			catch(com.newco.marketplace.exception.core.BusinessServiceException e) {
	//				logger.error("MarketPublicAPI logomapping() while retrieving documents",e);
	//			}
	//			if (documentVO != null && documentVO.getDocumentId() != null) {
	//				serviceorder.setLogoDocumentId(documentVO.getDocumentId());
	//			}
	//			return serviceorder;	
	//	}
	//	

	/**
	 * This method is for mapping the General Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder ServiceOrder
	 * @param generalSection  GeneralSection
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder,
			GeneralSection generalSection) {

		logger.info("Mapping: General Section --->Starts");
		serviceOrder.setSowTitle(generalSection.getTitle());
		serviceOrder.setSowDs(generalSection.getOverview());
		serviceOrder.setBuyerTermsCond(generalSection.getBuyerTerms());
		serviceOrder.setProviderInstructions(generalSection
				.getSpecialInstructions());

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
			soSchedule.setServiceDateTypeId(SOScheduleType.DATERANGE);
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


	public OrderFulfillmentRequest mapServiceOrderPrice(Pricing pricing, SecurityContext securityContext) throws BusinessServiceException {
		ServiceOrder serviceOrder = new ServiceOrder();
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		if (pricing != null) {
			if (null != pricing.getLaborSpendLimit()
					&& (!"".equals(pricing.getLaborSpendLimit()))
					&& (!"0".equals(pricing.getLaborSpendLimit()))
					&& (!"0.0".equals(pricing.getLaborSpendLimit()))) {
				BigDecimal spendLimitLabor =  new BigDecimal(pricing.getLaborSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);
				serviceOrder.setSpendLimitLabor(spendLimitLabor);
			}
			if (null != pricing.getPartsSpendLimit()
					&& (!"".equals(pricing.getPartsSpendLimit()))
					&& (!"0".equals(pricing.getPartsSpendLimit()))
					&& (!"0.0".equals(pricing.getPartsSpendLimit()))){
				BigDecimal spendLimitParts =  new BigDecimal(pricing.getPartsSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);
				serviceOrder.setSpendLimitParts(spendLimitParts);
			}
		}
		serviceOrder.setPrice(mapPricing(pricing, serviceOrder));
		request.setElement(serviceOrder);
		request.setIdentification(createBuyerIdentFromSecCtx(securityContext));
		return request;
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

	/**
	 * This method is for getting the CustomerrefTypeId.
	 * 
	 * @param buyerId  String
	 * @param customRefType String
	 * @return Integer
	 */
	//	private Integer getCustomRefTypeId(String buyerId, String customRefType) {
	//		logger.info("Inside getCustomRefTypeId--->Start");
	//		try {
	//			if (customRefList == null) {
	//				customRefList = lookupBO.getBuyerRef(buyerId);
	//			}
	//			for (LuBuyerRefVO customRef : customRefList) {
	//				if (customRef.getRefType().equalsIgnoreCase(customRefType)) {
	//					return customRef.getRefTypeId();
	//				}
	//			}
	//		} catch (Exception e) {
	//			logger.error("Data Exception Occurred while getting "
	//					+ " CustomerRefId " + "Carrier Details" + e);
	//		}
	//		return null;
	//	}

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
		phoneVO.setPhoneClass(PhoneClassification.fromId( getPhoneTypeId(phone.getPhoneType())));
		phoneVO.setPhoneType(PhoneType.fromId(phoneTypeId));
		return phoneVO;
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

	public SOEditResponse editSOResponseMapping(OrderFulfillmentResponse ofResponse) {
		logger.info("Inside editSOResponseMapping--->Start");
		SOEditResponse soEditResponse = new SOEditResponse();


		if (ofResponse.isError()) {
			Results errorResults = Results.getError(ofResponse.getErrorMessage(),
					ResultsCode.EDIT_FAILURE.getCode());
			soEditResponse.setResults(errorResults);
		}else{
			Results results = Results.getSuccess(ResultsCode.EDIT_SUCCESSFUL
					.getMessage());
			soEditResponse.setResults(results);
		}

		return soEditResponse;
	}

	public SOUpdateResponse updateSOResponseMapping(OrderFulfillmentResponse ofResponse) {
		logger.info("Inside updateSOResponseMapping--->Start");
		SOUpdateResponse soUpdateResponse=new SOUpdateResponse();


		if (ofResponse.isError()) {
			Results errorResults = Results.getError(ofResponse.getErrorMessage(),
					ResultsCode.UPDATE_FAILURE.getCode());
			soUpdateResponse.setResults(errorResults);
		}else{
			Results results = Results.getSuccess(ResultsCode.UPDATE_SUCCESSFUL
					.getMessage());
			soUpdateResponse.setResults(results);
		}

		return soUpdateResponse;
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


	public void setSkillAssignBOImpl(SkillAssignBOImpl skillAssignBOImpl) {
		this.skillAssignBOImpl = skillAssignBOImpl;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public void setBuyerBO(IBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public void setPromoBO(PromoBO promoBO) {
		this.promoBO = promoBO;
	}
	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
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
	 * Check the custom ref
	 * @param buyerId
	 * @param customRefType
	 * @return
	 */
	private LuBuyerRefVO getCustomRefType(String buyerId, String customRefType) {
		logger.info("Inside getCustomRefType--->Start");
		try {

			LuBuyerRefVO customRef = lookupBO.getBuyerRef(buyerId,customRefType.trim());
			if(null!= customRef){
				return customRef;
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while getting "
					+ " CustomerRefId " + "Carrier Details" + e);
		}
		return null;
	}

	/**
	 * @param svcOrdrId
	 * @param request
	 * @param securityContext
	 * @return
	 * @throws ValidationException
	 * This method is to update the details in the UPDATE API call
	 */
	public OrderFulfillmentRequest updateServiceOrder(String svcOrdrId,
			SOUpdateRequest request,
			SecurityContext securityContext,BuyerResource buyerResource,boolean isAdmin) throws ValidationException{
		logger.info("Entering OFServiceOrderMapper.updateServiceOrder()");
		OrderFulfillmentRequest orderRequest = new OrderFulfillmentRequest();
		String modifiedByName = "";
		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder=(ServiceOrder)ofHelper.getServiceOrder(svcOrdrId);		
			//SL-21086
			//Mapping substatus 
			List<String> validationErrors =  new ArrayList<String>();
			String soSubstatus = request.getSoSubstatus();
			if(StringUtils.isNotBlank(soSubstatus))
			{
				soSubstatus = soSubstatus.trim();
				if((OrderConstants.ACCEPTED_STATUS == serviceOrder.getWfStateId().intValue()) || (OrderConstants.ACTIVE_STATUS == serviceOrder.getWfStateId().intValue()) 
						|| (OrderConstants.PROBLEM_STATUS == serviceOrder.getWfStateId().intValue()) || (OrderConstants.COMPLETED_STATUS == serviceOrder.getWfStateId().intValue()))
				{
					
					if(!(OrderConstants.REVISIT_NEEDED_SUBSTATUS_ID.equals(serviceOrder.getWfSubStatusId()) || 
							 OrderConstants.PENDING_CLAIM_SUBSTATUS_ID.equals(serviceOrder.getWfSubStatusId()) || 
							OrderConstants.WORK_STARTED_SUBSTATUS_ID.equals(serviceOrder.getWfSubStatusId()) || 
							OrderConstants.NOT_HOME_REVISIT_NEEDED_SUBSTATUS_ID.equals(serviceOrder.getWfSubStatusId())))
					{
						Integer subStatusId=validateSoSubstatus(serviceOrder,soSubstatus);
						
						if(null!=subStatusId)
						{
							serviceOrder.setWfSubStatusId(subStatusId);
							StringBuilder loggingCommentForUpdateSubstatus = new StringBuilder("");
							loggingCommentForUpdateSubstatus.append(PublicAPIConstant.UPDATE_SO_SUBSTATUS);
							loggingCommentForUpdateSubstatus.append(soSubstatus);
							orderRequest.addMiscParameter("UPDATE_API_SO_SUBSTATUS_LOG_COMMENT",loggingCommentForUpdateSubstatus.toString());
						}else
						{
							validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_SUBSTATUS_FOR_CURRENT_STATE));	
						}
					}else{
							validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.SUBSTATUS_UPDATE_NOT_ALLOWED));	
					}
					
				}else
				{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_SO_STATE));	
				}
				
				if(!validationErrors.isEmpty()){
					throw new ValidationException(validationErrors);
				}
			}//Prevent unwanted sub status update
			else{
				serviceOrder.setWfSubStatusId(null);
			}
	
		StringBuilder loggingComment = mapToUpdate(serviceOrder, request, orderRequest, securityContext,buyerResource,isAdmin);
		com.newco.marketplace.dto.vo.serviceorder.Contact contact = null;
		orderRequest.addMiscParameter("isSpendLimitIncreased", "false");
		orderRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON, "");
		if(null!=request.getSpendLimitIncrease()){

			orderRequest.setParameter("isSpendLimitIncreased", "spendLimit");			
			if(null!=serviceOrder.getSpendLimitReasonId()){
				orderRequest.setParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON, serviceOrder.getSpendLimitReasonId().toString());
			}
		}
		if(null!=serviceOrder.getBuyerId() && 3333==serviceOrder.getBuyerId().intValue() && null!=request.getSpendLimitDecrease()){
			if(serviceOrder.isSpendLimitDecrease()){
				orderRequest.setParameter("isSpendLimitIncreased", "spendLimitDecreaseForRelay");	
			}
			if(null!=serviceOrder.getSpendLimitReasonId()){
				orderRequest.setParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON, serviceOrder.getSpendLimitReasonId().toString());
			}
		}
		
		// Get the first name and last name of the buyer resource 
		ContactLocationVO contactLocationVO=new ContactLocationVO();
		contactLocationVO=buyerBO.getBuyerResContactLocationVO
				(securityContext.getCompanyId(),buyerResource.getResourceId());
		
		contact = contactLocationVO.getBuyerPrimaryContact();
	
		// LoginCredentialVO roles = securityContext.getRoles();
		if(null!= contact && 
				null!=contact.getFirstName().trim()&& 
				null!=contact.getLastName().trim()){
			modifiedByName = (contact.getFirstName().trim()+" "+contact.getLastName().trim());
		}
		if(null!=buyerResource.getResourceId()){
			orderRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID,buyerResource.getResourceId().toString());
		}
		orderRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,modifiedByName);
		
		//Code for Priority 1 (Update Overview,SO location,SO contact)
		updateServiceLocationContact(request, serviceOrder,orderRequest);	

		com.newco.marketplace.api.beans.so.update.SOSchedule schedule=request.getSchedule();
		if((OrderConstants.ROUTED_STATUS == serviceOrder.getWfStateId().intValue() 
				|| OrderConstants.DRAFT_STATUS == serviceOrder.getWfStateId().intValue()) &&
				null!=schedule)
		{ 
			updateServiceOrderDates(serviceOrder, validationErrors, schedule);
		}
		
		orderRequest.setElement(serviceOrder);
		
		// Get the buyer resource details
		orderRequest.setIdentification(createBuyerIdentFromSecCtxResource(securityContext,buyerResource,contact));
		logger.info("Exiting OFServiceOrderMapper.updateServiceOrder()");
		return orderRequest;
	}

private void updateServiceOrderDates(ServiceOrder serviceOrder,
			List<String> validationErrors, com.newco.marketplace.api.beans.so.update.SOSchedule schedule)
			throws ValidationException {
		
		com.servicelive.orderfulfillment.domain.SOSchedule soSchedule=serviceOrder.getSchedule();
		if (null != schedule.getServiceDateTime1()
				&& !schedule.getServiceDateTime1().equals("")) {

			soSchedule.setServiceDate1(DateUtils
					.defaultFormatStringToDate(schedule.getServiceDateTime1()));
			
			// setting the servicestartTime
			Date serviceStartTime = null;
			try {
				serviceStartTime = CommonUtility.sdfToDate.parse(schedule
						.getServiceDateTime1());
			} catch (ParseException e) {
				logger.error("Exception Occurred while setting Service "
						+ "Start Time in OFServiceOrderMapper");
			}
			String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
			soSchedule.setServiceTimeStart(serviceStartTimeStr);
		}
		
		if (schedule.getScheduleType() != null) {
			if (PublicAPIConstant.DATETYPE_FIXED.equalsIgnoreCase((schedule
					.getScheduleType()))) {
				soSchedule.setServiceDate2(null);
				soSchedule.setServiceTimeEnd(null);
				soSchedule.setServiceDateTypeId(1);
			} else {
				soSchedule.setServiceDateTypeId(2);
				if (null != schedule.getServiceDateTime2()
						&& !schedule.getServiceDateTime2().equals("")) {
					soSchedule.setServiceDate2(DateUtils
							.defaultFormatStringToDate(
									schedule.getServiceDateTime2()));
					// setting the serviceEndTime
					Date serviceEndDate = null;
					try {
						serviceEndDate = CommonUtility.sdfToDate.parse(schedule
								.getServiceDateTime2());
					} catch (ParseException e) {
						logger.error("Parse Exception Occurred while "
								+ "setting ServiceEndDate in OFServiceOrderMapper");
					}
					String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
					soSchedule.setServiceTimeEnd(serviceEndTimeStr);
				}
			}
		}
		serviceOrder.setSchedule(soSchedule);
		
		validateDates(serviceOrder,validationErrors);
		
		if(!validationErrors.isEmpty()){

			throw new ValidationException(validationErrors);
		}
		
		serviceOrder.setServiceLocationTimeZone(LocationUtils.getTimeZone(serviceOrder.getServiceLocation().getZip()));
		if(null==serviceOrder.getServiceLocationTimeZone()){
			serviceOrder.setServiceLocationTimeZone(OrderConstants.EST_ZONE);
		}
		TimeZone timezone = TimeZone.getTimeZone(serviceOrder.getServiceLocationTimeZone());
		int offset = Math.round(timezone.getOffset((new Date()).getTime()) / (1000 * 60 * 60));
		serviceOrder.setServiceDateTimezoneOffset(new Integer(offset));
	}

	/**
	 * @param request
	 * @param serviceOrder
	 */
	private void updateServiceLocationContact(SOUpdateRequest request,
			ServiceOrder serviceOrder,OrderFulfillmentRequest orderRequest) throws ValidationException {
		
		if(StringUtils.isNotBlank(request.getOverView()) || null!=request.getLocation() || null!=request.getContact())
		{
		
			if(!(OrderConstants.COMPLETED_STATUS == serviceOrder.getWfStateId() || OrderConstants.CLOSED_STATUS == serviceOrder.getWfStateId()))
			{
			//Mapping service order overview
			mapSoOverview(request, serviceOrder,orderRequest);
			
			//Mapping service order location details
			mapSoLocation(request, serviceOrder,orderRequest);
			
			//Mapping service order contact details
			mapSoContact(request, serviceOrder,orderRequest);	
			}else{
				List<String> validationErrors = new ArrayList<String>();	
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_SO_CON_LOC_UPDATE));		
				if(!validationErrors.isEmpty()){
					throw new ValidationException(validationErrors);
				}
			}	
		}
	}

	private void mapSoContact(SOUpdateRequest request, ServiceOrder serviceOrder,OrderFulfillmentRequest orderRequest) {
		//Mapping service order contact details
		SOContact soContact = serviceOrder.getServiceContact();
		if(null!=soContact)
		{
			SoUpdateContact contacts= request.getContact();
			
			if(null!=contacts)
			{
				
				if(StringUtils.isNotBlank(contacts.getFirstName()))	
				{
					soContact.setFirstName(contacts.getFirstName());
				}
			
				if(StringUtils.isNotBlank(contacts.getLastName()))
				{
					soContact.setLastName(contacts.getLastName());
				}
			
				if(StringUtils.isNotBlank(contacts.getEmail()))	
				{
					soContact.setEmail(contacts.getEmail());
				}
				
				Set<SOPhone> setPhone =new HashSet<SOPhone>();
				
				SOPhone phone1 =new SOPhone();
				
				if(null!=contacts.getPrimaryPhoneNo())
				{
					if(StringUtils.isNotBlank(contacts.getPrimaryPhoneNo().getNumber()))
					{
						phone1.setPhoneNo(contacts.getPrimaryPhoneNo().getNumber());
					}	
				
					if(StringUtils.isNotBlank(contacts.getPrimaryPhoneNo().getExtension()))
					{
						phone1.setPhoneExt(contacts.getPrimaryPhoneNo().getExtension());
					}
					
				
					if(StringUtils.isNotBlank(contacts.getPrimaryPhoneNo().getPhoneType()))
					{
						phone1.setPhoneClass(PhoneClassification.fromId( getPhoneTypeId(contacts.getPrimaryPhoneNo().getPhoneType())));
					}
			
					if(null!=phone1)
					{
					phone1.setPhoneType(PhoneType.PRIMARY);
					setPhone.add(phone1);
					}
				}
				
				SOPhone phone2 =new SOPhone();
				
				if(null!=contacts.getAltPhoneNo())
				{	
					if((StringUtils.isNotBlank(contacts.getAltPhoneNo().getNumber())))
					{
						phone2.setPhoneNo(contacts.getAltPhoneNo().getNumber());
					}
				
					if(StringUtils.isNotBlank(contacts.getAltPhoneNo().getExtension()))
					{
						phone2.setPhoneExt(contacts.getAltPhoneNo().getExtension());
					}

					
				
					if(StringUtils.isNotBlank(contacts.getAltPhoneNo().getPhoneType()))
					{
						phone2.setPhoneClass(PhoneClassification.fromId( getPhoneTypeId(contacts.getAltPhoneNo().getPhoneType())));
					}
					
					if(null!=phone2)
					{
						phone2.setPhoneType(PhoneType.ALTERNATE);
						setPhone.add(phone2);
					}
				}	
				
				if(null!=setPhone)
				{	
					soContact.setPhones(setPhone);
				}

				
					soContact.addContactLocation(ContactLocationType.SERVICE);
					List<SOContact> contactList = new ArrayList<SOContact>();
					contactList.add(soContact);
					serviceOrder.setContacts(contactList);
				
				if(null!=request.getSpendLimitIncrease())
				{
					orderRequest.setParameter("isSpendLimitIncreased", "spendLimitUpdateLocCon");

				}else if(null!=serviceOrder.getBuyerId() && 3333==serviceOrder.getBuyerId().intValue() 
						&& null!=request.getSpendLimitDecrease())
				{
					if(serviceOrder.isSpendLimitDecrease()){
						orderRequest.setParameter("isSpendLimitIncreased", "spendLimitDecreaseUpdateLocCon");
					}
				}				
				else{
					orderRequest.setParameter("isSpendLimitIncreased", "updateLocCon");
				}
			}
		}
	}

	private void mapSoLocation(SOUpdateRequest request,
			ServiceOrder serviceOrder,OrderFulfillmentRequest orderRequest) {
		//Mapping service order location details
				SOLocation soLocation = serviceOrder.getServiceLocation();
				if(null!=soLocation)
				{
					SoUpdateLocation locations= request.getLocation();
					if(null!=locations)
					{
						if(StringUtils.isNotBlank(locations.getLocationType()))
						{
							if(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL.equalsIgnoreCase(locations.getLocationType()))
							{
								soLocation.setSoLocationClassId(LocationClassification.COMMERCIAL);
							}else if(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL.equalsIgnoreCase(locations.getLocationType()))
							{
								soLocation.setSoLocationClassId(LocationClassification.RESIDENTIAL);
							}
							
						}
						
						if(StringUtils.isNotBlank(locations.getAddress1()))
						{
							soLocation.setStreet1(locations.getAddress1());
						}
						
						if(StringUtils.isNotBlank(locations.getAddress2()))
						{
							soLocation.setStreet2(locations.getAddress2());
						}
						
						if(StringUtils.isNotBlank(locations.getCity()))
						{
							soLocation.setCity(locations.getCity());
						}
						
						if(StringUtils.isNotBlank(locations.getLocationName()))
						{
							soLocation.setLocationName(locations.getLocationName());
						}
						
						if(StringUtils.isNotBlank(locations.getNote()))
						{
							soLocation.setLocationNote(locations.getNote());
						}
						

							List<SOLocation> locationsList = new ArrayList<SOLocation>();
							soLocation.setSoLocationTypeId(LocationType.SERVICE);
							locationsList.add(soLocation);
							serviceOrder.setLocations(locationsList);
						
						
						if(null!=request.getSpendLimitIncrease())
						{
							orderRequest.setParameter("isSpendLimitIncreased", "spendLimitUpdateLocCon");
						}else{
							orderRequest.setParameter("isSpendLimitIncreased", "updateLocCon");
						}
					}
				}
	}

	private void mapSoOverview(SOUpdateRequest request,
			ServiceOrder serviceOrder,OrderFulfillmentRequest orderRequest) {
		//Mapping service order overview
		if(StringUtils.isNotBlank(request.getOverView()))
		{	
		serviceOrder.setSowDs(request.getOverView());
			if(null!=request.getSpendLimitIncrease())
			{
				orderRequest.setParameter("isSpendLimitIncreased", "spendLimitUpdateLocCon");
			}else{
				orderRequest.setParameter("isSpendLimitIncreased", "updateLocCon");
			}
		}
	}
	
	/**
	 * @param buyerId
	 * @param resourceId
	 * @return BuyerResource
	 * @throws Exception
	 */
	public BuyerResource getBuyerResource(int buyerId,Integer resourceId){		
		BuyerResource buyerResource = null;
		try {
			buyerResource = buyerBO.getBuyerResource(buyerId,resourceId);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Data Exception Occurred while getting "
					+ " buyer resource details " + e);
		}
		return buyerResource;
	}
	
	public boolean isResourceAbuyerAdmin(String userName){
		boolean isAdmin = false;
		try {
			isAdmin = buyerBO.isBuyerResourceBuyerAdmin(userName);
		}catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.error("Data Exception Occurred while getting "
					+ " buyer  details " + e);
		}
		return isAdmin;
	}

	/**
	 * @param serviceOrder
	 * @param request
	 * @param securityContext
	 * @throws ValidationException
	 * This method calls the respective mapping methods for update API call

	 */
	public StringBuilder mapToUpdate(ServiceOrder serviceOrder,
			SOUpdateRequest request,OrderFulfillmentRequest orderRequest, SecurityContext securityContext
			,BuyerResource buyerResource,boolean isAdmin) throws ValidationException {
		StringBuilder loggingCommentForSpendLimit = new StringBuilder("");
		StringBuilder loggingCommentForCustRef = new StringBuilder("");
		StringBuilder loggingCommentForUpdateOrder = new StringBuilder("");
		orderRequest.addMiscParameter("UPDATE_API_SPEND_LOG_COMMENT","");
		orderRequest.addMiscParameter("UPDATE_API_CUST_LOG_COMMENT","");
		orderRequest.addMiscParameter("UPDATE_API_SERVICE_LOC_CONTACT_LOG_COMMENT","");
		int buyerId=securityContext.getCompanyId();
		List<String> validationErrors = new ArrayList<String>();
		if(serviceOrder!=null){
			if(null==request.getSpendLimitIncrease()&& null==request.getSpendLimitDecrease() &&null==request.getCustomReferences() && StringUtils.isBlank(request.getOverView()) && null==request.getLocation() && null==request.getContact() && StringUtils.isBlank(request.getSoSubstatus()) && null==request.getSchedule()){
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_FIELDS ));
			}else if(null!=request.getSpendLimitIncrease() && null!=request.getSpendLimitDecrease()){
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_FIELDS ));
			}
			else{
				
				if(null!=request.getSpendLimitIncrease()){
						
					
					if(isSpendLimitIncrease(serviceOrder)){												
						loggingCommentForSpendLimit = mapSpendLimit(serviceOrder, request.getSpendLimitIncrease(),
								buyerId,validationErrors,buyerResource,isAdmin);
						
						
						if(null!=loggingCommentForSpendLimit){
							orderRequest.setParameter("UPDATE_API_SPEND_LOG_COMMENT",loggingCommentForSpendLimit.toString());
						}
					}
					else{
		
						validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_SPEND_LIMIT));							
					}
				

				}				
								
				if (null != request.getSpendLimitDecrease()) {

					if (isSpendLimitIncrease(serviceOrder)) {

						loggingCommentForSpendLimit = mapSpendLimitRelay(serviceOrder, request.getSpendLimitDecrease(),
								buyerId, validationErrors, buyerResource, isAdmin);

						if (null != loggingCommentForSpendLimit) {
							orderRequest.setParameter("UPDATE_API_SPEND_LOG_COMMENT",
									loggingCommentForSpendLimit.toString());
						}
					} else {
						validationErrors
								.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_SPEND_LIMIT_RELAY));

					}

				}

				if (null != request.getCustomReferences()) {

					// if(isCutsomerReferenceEditable(serviceOrder)){

						loggingCommentForCustRef = mapServiceOrderCustomRefForUpdate(serviceOrder, request.getCustomReferences(),buyerId,validationErrors);
						if(null!=loggingCommentForCustRef){
							orderRequest.setParameter("UPDATE_API_CUST_LOG_COMMENT",loggingCommentForCustRef.toString());
						}
					// }
					//else{
					//	validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_CUST_REF));		

					//}
				}
				
				if(StringUtils.isNotBlank(request.getOverView()) || null!=request.getLocation() || null!=request.getContact())
				{
					loggingCommentForUpdateOrder.append(PublicAPIConstant.UPDATE_SO_CON_LOC_COMMENTS);
					orderRequest.setParameter("UPDATE_API_SERVICE_LOC_CONTACT_LOG_COMMENT",loggingCommentForUpdateOrder.toString());
				}
				
				if(!(validationErrors.isEmpty())
						&& (validationErrors.contains(PublicAPIConstant.INVALID_STATE_SPEND_LIMIT)
								|| validationErrors.contains(PublicAPIConstant.INVALID_STATE_SPEND_LIMIT_RELAY))
						&& validationErrors.contains(PublicAPIConstant.INVALID_STATE_CUST_REF)){
					validationErrors.clear();
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_STATE_FOR_UPDATE));
				}
			}

		}
		else{

			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.RETRIEVE_ERROR_CODE ));		

		}

		if(!validationErrors.isEmpty()){

			throw new ValidationException(validationErrors);
		}
		loggingCommentForSpendLimit.append(loggingCommentForCustRef);
		return loggingCommentForSpendLimit;
	}


	/**
	 * @param serviceOrder
	 * @return
	 * returns true if service order is in a state to update customer reference for update API call
	 * 
	 */
	private boolean isCutsomerReferenceEditable(ServiceOrder serviceOrder) {
		int statusId = serviceOrder.getWfStateId();
		return statusId == OrderConstants.ACCEPTED_STATUS || 
			   statusId == OrderConstants.PROBLEM_STATUS || 
			   statusId==OrderConstants.ACTIVE_STATUS || 
			   statusId==OrderConstants.COMPLETED_STATUS || 
			   statusId==OrderConstants.CLOSED_STATUS;
	}

	/**
	 * @param serviceOrder
	 * @return
	 * returns true if service order is in a state to have spend limit increase for update API call
	 * 
	 */
	private boolean isSpendLimitIncrease(ServiceOrder serviceOrder) {
		int statusId = serviceOrder.getWfStateId();
		return statusId == OrderConstants.ACCEPTED_STATUS|| statusId == OrderConstants.PROBLEM_STATUS|| statusId==OrderConstants.ACTIVE_STATUS;
	}


	/**
	 * @param serviceOrder
	 * @param spendLimitIncrease
	 * @param buyerId
	 * @param validationErrors
	 * To map spend limit to service order for new API call update
	 */

	public StringBuilder mapSpendLimit(ServiceOrder serviceOrder,
			SpendLimitIncrease spendLimitIncrease,int buyerId,List<String> validationErrors,
			BuyerResource buyerResource,boolean isAdmin) {
		StringBuilder loggingComment = new StringBuilder("");
		String reason;
		String notes;
		BigDecimal originalMaxPrice;
		BigDecimal spendLimitLabor=PricingUtil.ZERO;
		BigDecimal spendLimitParts=PricingUtil.ZERO;
		BigDecimal originalSpendLimitLabor=PricingUtil.ZERO;
		BigDecimal originalSpendLimitParts=PricingUtil.ZERO;
		BigDecimal finalMaxPrice;
		DecimalFormat priceFormatter = new DecimalFormat("#0.00");
		originalMaxPrice=serviceOrder.getSpendLimitLabor().add(serviceOrder.getSpendLimitParts());
		if(null!=serviceOrder.getSpendLimitLabor()){
			originalSpendLimitLabor = serviceOrder.getSpendLimitLabor();
		}
		if(null!=serviceOrder.getSpendLimitParts()){
			originalSpendLimitParts = serviceOrder.getSpendLimitParts();
		}
		if (StringUtils.isNotBlank(spendLimitIncrease.getLaborSpendLimit())
				&& (!StringUtils.equals(spendLimitIncrease.getLaborSpendLimit(),"0.00"))) {
			spendLimitLabor =  new BigDecimal(spendLimitIncrease.getLaborSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);

		}else{
			if(null==serviceOrder.getSpendLimitLabor()){
				serviceOrder.setSpendLimitLabor(PricingUtil.ZERO);
			}
		}
		if (StringUtils.isNotBlank(spendLimitIncrease.getPartsSpendLimit())
				&& (!StringUtils.equals(spendLimitIncrease.getPartsSpendLimit(),"0.00"))){
			spendLimitParts =  new BigDecimal(spendLimitIncrease.getPartsSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);

		}else{
			serviceOrder.setSpendLimitParts(PricingUtil.ZERO);
		}
		finalMaxPrice=spendLimitLabor.add(spendLimitParts);
		
		// Validate whether the buyer resource have enough spend limit
		// Admin can do what ever he/she wants - Do they have some max limit? - Yes 
		// Do the same for Admin as well - 0.00 means NO LIMIT
		
		if((!isAdmin) || (isAdmin && buyerResource.getMaxSpendLimitPerSO() > 0.00) ){
			BigDecimal maxLimitAllowed = new BigDecimal(buyerResource.getMaxSpendLimitPerSO());
			BigDecimal difference = new BigDecimal(0.00);
			// Find out the difference between current price and updated price
			if(finalMaxPrice.compareTo(originalMaxPrice)>0){			
				difference = finalMaxPrice.subtract(originalMaxPrice);
			}
			if(difference.compareTo(maxLimitAllowed)>0){
				// Error! 
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.SPEND_LIMIT_EXCEEDED));
				return loggingComment;
			}
		}
		
		if(finalMaxPrice.compareTo(originalMaxPrice)>0){
			serviceOrder.setSpendLimitLabor(spendLimitLabor);
			serviceOrder.setSpendLimitParts(spendLimitParts);
			String priceAfterFormatting = "";
			if(originalSpendLimitLabor.compareTo(spendLimitLabor)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitLabor.doubleValue());
				loggingComment.append("Labor Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			if(originalSpendLimitParts.compareTo(spendLimitParts)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitParts.doubleValue());
				loggingComment.append("Materials Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			priceAfterFormatting = priceFormatter.format(finalMaxPrice.doubleValue());
			loggingComment.append("Total SpendLimit  ====>  $"+priceAfterFormatting+"<br>");
		}
		else{
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_PRICE));
		}
		reason=spendLimitIncrease.getReason();
		notes=spendLimitIncrease.getNotes();
		if(StringUtils.isNotBlank(reason)){
			if(StringUtils.equals(reason, PublicAPIConstant.OTHER)){
				if(StringUtils.isNotBlank(notes)){
					serviceOrder.setSpendLimitIncrComment(notes);
					loggingComment.append("Reason  ====>  "+notes+".<br>");
				}
				else{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_NOTE));
				}
			}
			else{
				Integer reasonCodeId=manageReasonCodeBO.isAReasonCode(buyerId, OrderConstants.TYPE_SPEND_LIMIT, reason);
				if(null!=reasonCodeId && reasonCodeId.intValue()!=0){
					serviceOrder.setSpendLimitIncrComment(reason);
					loggingComment.append("Reason  ====>  "+reason+".<br>");
					serviceOrder.setSpendLimitReasonId(reasonCodeId);
				}
				else{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_REASON_CODE));
				}
			}
		}
		else{
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_REASON_CODE));
		}
		serviceOrder.setPrice(mapPricing(spendLimitIncrease, serviceOrder));
		return loggingComment;
	}


	
	
	public StringBuilder mapSpendLimitRelay(ServiceOrder serviceOrder,
			SpendLimitDecrease spendLimitIncrease,int buyerId,List<String> validationErrors,
			BuyerResource buyerResource,boolean isAdmin) {
		StringBuilder loggingComment = new StringBuilder("");
		String reason;
		String notes;
		BigDecimal originalMaxPrice;
		BigDecimal spendLimitLabor=PricingUtil.ZERO;
		BigDecimal spendLimitParts=PricingUtil.ZERO;
		BigDecimal originalSpendLimitLabor=PricingUtil.ZERO;
		BigDecimal originalSpendLimitParts=PricingUtil.ZERO;
		BigDecimal finalMaxPrice;
		DecimalFormat priceFormatter = new DecimalFormat("#0.00");
		originalMaxPrice=serviceOrder.getSpendLimitLabor().add(serviceOrder.getSpendLimitParts());
		if(null!=serviceOrder.getSpendLimitLabor()){
			originalSpendLimitLabor = serviceOrder.getSpendLimitLabor();
		}
		if(null!=serviceOrder.getSpendLimitParts()){
			originalSpendLimitParts = serviceOrder.getSpendLimitParts();
		}
		if (StringUtils.isNotBlank(spendLimitIncrease.getLaborSpendLimit())
				&& (!StringUtils.equals(spendLimitIncrease.getLaborSpendLimit(),"0.00"))) {
			spendLimitLabor =  new BigDecimal(spendLimitIncrease.getLaborSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);

		}else{
			if(null==serviceOrder.getSpendLimitLabor()){
				serviceOrder.setSpendLimitLabor(PricingUtil.ZERO);
			}
		}
		if (StringUtils.isNotBlank(spendLimitIncrease.getPartsSpendLimit())
				&& (!StringUtils.equals(spendLimitIncrease.getPartsSpendLimit(),"0.00"))){
			spendLimitParts =  new BigDecimal(spendLimitIncrease.getPartsSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);

		}else{
			serviceOrder.setSpendLimitParts(PricingUtil.ZERO);
		}
		finalMaxPrice=spendLimitLabor.add(spendLimitParts);
		
		// Validate whether the buyer resource have enough spend limit
		// Admin can do what ever he/she wants - Do they have some max limit? - Yes 
		// Do the same for Admin as well - 0.00 means NO LIMIT
		
		/*if((!isAdmin) || (isAdmin && buyerResource.getMaxSpendLimitPerSO() > 0.00) ){
			BigDecimal maxLimitAllowed = new BigDecimal(buyerResource.getMaxSpendLimitPerSO());
			BigDecimal difference = new BigDecimal(0.00);
			// Find out the difference between current price and updated price
			if(finalMaxPrice.compareTo(originalMaxPrice)>0){			
				difference = finalMaxPrice.subtract(originalMaxPrice);
			}
			if(difference.compareTo(maxLimitAllowed)>0){
				// Error! 
				validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.SPEND_LIMIT_EXCEEDED));
				return loggingComment;
			}
		}*/
		
		/*if(finalMaxPrice.compareTo(originalMaxPrice)>0){
			serviceOrder.setSpendLimitLabor(spendLimitLabor);
			serviceOrder.setSpendLimitParts(spendLimitParts);
			String priceAfterFormatting = "";
			if(originalSpendLimitLabor.compareTo(spendLimitLabor)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitLabor.doubleValue());
				loggingComment.append("Labor Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			if(originalSpendLimitParts.compareTo(spendLimitParts)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitParts.doubleValue());
				loggingComment.append("Materials Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			priceAfterFormatting = priceFormatter.format(finalMaxPrice.doubleValue());
			loggingComment.append("Total SpendLimit  ====>  $"+priceAfterFormatting+"<br>");
		}else*/ if(finalMaxPrice.compareTo(originalMaxPrice)<0){
			serviceOrder.setSpendLimitDecrease(true); 
			serviceOrder.setSpendLimitLabor(spendLimitLabor);
			serviceOrder.setSpendLimitParts(spendLimitParts);
			String priceAfterFormatting = "";
			if(originalSpendLimitLabor.compareTo(spendLimitLabor)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitLabor.doubleValue());
				loggingComment.append("Labor Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			if(originalSpendLimitParts.compareTo(spendLimitParts)!=0){
				priceAfterFormatting = priceFormatter.format(spendLimitParts.doubleValue());
				loggingComment.append("Materials Spend Limit Changed to  ====>  $"+priceAfterFormatting+"<br>");
			}
			priceAfterFormatting = priceFormatter.format(finalMaxPrice.doubleValue());
			loggingComment.append("Total SpendLimit  ====>  $"+priceAfterFormatting+"<br>");
		}
		else{
			//
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_PRICE_RELAY));
		}
		reason=spendLimitIncrease.getReason();
		notes=spendLimitIncrease.getNotes();
		if(StringUtils.isNotBlank(reason)){
			if(StringUtils.equals(reason, PublicAPIConstant.OTHER)){
				if(StringUtils.isNotBlank(notes)){
					serviceOrder.setSpendLimitIncrComment(notes);
					loggingComment.append("Reason  ====>  "+notes+".<br>");
				}
				else{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_NOTE_RELAY));
				}
			}
			else{
				Integer reasonCodeId=manageReasonCodeBO.isAReasonCode(buyerId, OrderConstants.TYPE_SPEND_LIMIT, reason);
				if(null!=reasonCodeId && reasonCodeId.intValue()!=0){
					serviceOrder.setSpendLimitIncrComment(reason);
					loggingComment.append("Reason  ====>  "+reason+".<br>");
					serviceOrder.setSpendLimitReasonId(reasonCodeId);
				}
				else{
					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.INVALID_REASON_CODE_RELAY));
				}
			}
		}
		else{
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.NO_REASON_CODE_RELAY));
		}
		serviceOrder.setPrice(mapPricing(spendLimitIncrease, serviceOrder));
		return loggingComment;
	}

	/**
	 * @param spendLimitIncrease
	 * @param serviceOrder
	 * @return
	 * method to map price to SO Price
	 */

	public SOPrice mapPricing(SpendLimitIncrease spendLimitIncrease,
			ServiceOrder serviceOrder) {

		SOPrice returnVal = new SOPrice();
		returnVal.setServiceOrder(serviceOrder);
		BigDecimal laborSpendLimit = new BigDecimal(spendLimitIncrease.getLaborSpendLimit());
		returnVal.setOrigSpendLimitLabor(laborSpendLimit);
		BigDecimal partsSpendLimit = new BigDecimal(spendLimitIncrease.getPartsSpendLimit());
		returnVal.setOrigSpendLimitParts(partsSpendLimit);
		returnVal.setDiscountedSpendLimitLabor(laborSpendLimit);
		returnVal.setDiscountedSpendLimitParts(partsSpendLimit);
		
		// set tax and discount
		returnVal.setDiscountPercentLaborSL(StringUtils.isEmpty(spendLimitIncrease.getLaborDiscount()) ? PricingUtil.ZERO : new BigDecimal(spendLimitIncrease.getLaborDiscount()));
		returnVal.setDiscountPercentPartsSL(StringUtils.isEmpty(spendLimitIncrease.getPartsDiscount()) ? PricingUtil.ZERO : new BigDecimal(spendLimitIncrease.getPartsDiscount()));
		returnVal.setTaxPercentLaborSL(StringUtils.isEmpty(spendLimitIncrease.getLaborTax()) ? PricingUtil.ZERO : new BigDecimal(spendLimitIncrease.getLaborTax()));
		returnVal.setTaxPercentPartsSL(StringUtils.isEmpty(spendLimitIncrease.getPartsTax()) ? PricingUtil.ZERO : new BigDecimal(spendLimitIncrease.getPartsTax()));
				
		return returnVal;
	}

	
	public SOPrice mapPricing(SpendLimitDecrease spendLimitDecrease,
			ServiceOrder serviceOrder) {

		SOPrice returnVal = new SOPrice();
		returnVal.setServiceOrder(serviceOrder);
		BigDecimal laborSpendLimit = new BigDecimal(spendLimitDecrease.getLaborSpendLimit());
		returnVal.setOrigSpendLimitLabor(laborSpendLimit);
		BigDecimal partsSpendLimit = new BigDecimal(spendLimitDecrease.getPartsSpendLimit());
		returnVal.setOrigSpendLimitParts(partsSpendLimit);
		returnVal.setDiscountedSpendLimitLabor(laborSpendLimit);
		returnVal.setDiscountedSpendLimitParts(partsSpendLimit);
		
		// set tax and discount
		returnVal.setDiscountPercentLaborSL(StringUtils.isEmpty(spendLimitDecrease.getLaborDiscount()) ? PricingUtil.ZERO : new BigDecimal(spendLimitDecrease.getLaborDiscount()));
		returnVal.setDiscountPercentPartsSL(StringUtils.isEmpty(spendLimitDecrease.getPartsDiscount()) ? PricingUtil.ZERO : new BigDecimal(spendLimitDecrease.getPartsDiscount()));
		returnVal.setTaxPercentLaborSL(StringUtils.isEmpty(spendLimitDecrease.getLaborTax()) ? PricingUtil.ZERO : new BigDecimal(spendLimitDecrease.getLaborTax()));
		returnVal.setTaxPercentPartsSL(StringUtils.isEmpty(spendLimitDecrease.getPartsTax()) ? PricingUtil.ZERO : new BigDecimal(spendLimitDecrease.getPartsTax()));
				
		return returnVal;
	}

	
	
	/**
	 * @param serviceOrder
	 * @param customReferences
	 * @param ownerId
	 * 
	 * maps the customer reference for update API call
	 */
	private StringBuilder mapServiceOrderCustomRef(ServiceOrder serviceOrder, CustomReferences customReferences,
			Integer ownerId, List<String> validationErrors) {
		StringBuilder loggingComment = new StringBuilder("");
		List<SOCustomReference> soCustomRefsVoList = new ArrayList<SOCustomReference>();
		List<SOCustomReference> existingCustomRefs = serviceOrder.getCustomReferences();
		
		// Create a map with key value with existing custom references
		Map<String,String> existingRefmap = new HashMap<String, String>();
		if (existingCustomRefs != null) {
            for (SOCustomReference customRef : existingCustomRefs) {
                if (StringUtils.isNotBlank(customRef.getBuyerRefTypeName()) &&
                		StringUtils.isNotBlank(customRef.getBuyerRefValue())){
                	existingRefmap.put(customRef.getBuyerRefTypeName().toLowerCase(), customRef.getBuyerRefValue());
                }
            }
        }
		
		if (null != customReferences && null != customReferences.getCustomRefList()) {
			for (CustomReference customRef : customReferences.getCustomRefList()) {
				SOCustomReference customRefVO = new SOCustomReference();
				String customRefValue = customRef.getValue().trim();
				customRefVO.setBuyerRefValue(customRefValue);
				String customRefName = customRef.getName().trim();
				customRefVO.setBuyerRefTypeName(customRefName);
				LuBuyerRefVO buyerRefVO = getCustomRefType(ownerId+"", customRefName);
				
				if(null != buyerRefVO && null!=buyerRefVO.getRefTypeId()){
					// Check whether the custom reference is editable
					if(buyerRefVO.getEditable() == 1){
						customRefVO.setBuyerRefTypeId(buyerRefVO.getRefTypeId());
					soCustomRefsVoList.add(customRefVO);
						
						// Get the value if it is already present in the service order.
						String existingCustomRefValue = null; 
						existingCustomRefValue = existingRefmap.get(customRefName.toLowerCase());
						if(null!=existingCustomRefValue && !existingCustomRefValue.equals(customRefValue)){
							loggingComment.append("The reference field "+customRefName+" has been updated");
							if(null!=existingCustomRefValue){
								loggingComment.append(" from "+existingCustomRefValue);
							}
							loggingComment.append(" to "+customRefValue+"<br>");
						}
					} else if(buyerRefVO.getEditable()==0){
						validationErrors.add(customRef.getName()+" "+ CommonUtility.getMessage(PublicAPIConstant.NOTEDITABLE_CUST_REF_VALUE));
					}
				}
				else{
					validationErrors.add(customRef.getName()+" "+ CommonUtility.getMessage(PublicAPIConstant.INVALID_CUST_REF_VALUE));
				}

			}

			if (!soCustomRefsVoList.isEmpty()) {
				serviceOrder.setCustomReferences(soCustomRefsVoList);
			}
		}
		return loggingComment;
	}

	
	/**
	 * @param serviceOrder
	 * @param customReferences
	 * @param ownerId
	 * 
	 * maps the customer reference for update API call
	 */
	private StringBuilder mapServiceOrderCustomRefForUpdate(ServiceOrder serviceOrder, CustomReferences customReferences,
			Integer ownerId, List<String> validationErrors) {
		StringBuilder loggingComment = new StringBuilder("");
		List<SOCustomReference> soCustomRefsVoList = new ArrayList<SOCustomReference>();
		List<SOCustomReference> existingCustomRefs = serviceOrder.getCustomReferences();
		List<ServiceOrderCustomRefVO> updatedCustRefs = new ArrayList<ServiceOrderCustomRefVO>();
		// Create a map with key value with existing custom references
		Map<String,String> existingRefmap = new HashMap<String, String>();
		if (existingCustomRefs != null) {
            for (SOCustomReference customRef : existingCustomRefs) {
                if (StringUtils.isNotBlank(customRef.getBuyerRefTypeName()) &&
                		StringUtils.isNotBlank(customRef.getBuyerRefValue())){
                	existingRefmap.put(customRef.getBuyerRefTypeName().toLowerCase(), customRef.getBuyerRefValue());
                }
            }
        }
		
		if (null != customReferences && null != customReferences.getCustomRefList()) {
			for (CustomReference customRef : customReferences.getCustomRefList()) {
				ServiceOrderCustomRefVO custRefVO = new ServiceOrderCustomRefVO();
				SOCustomReference customRefVO = new SOCustomReference();
				String customRefValue = customRef.getValue().trim();
				customRefVO.setBuyerRefValue(customRefValue);
				String customRefName = customRef.getName().trim();
				customRefVO.setBuyerRefTypeName(customRefName);
				LuBuyerRefVO buyerRefVO = getCustomRefType(ownerId+"", customRefName);
				
				if(null != buyerRefVO && null!=buyerRefVO.getRefTypeId()){
					// Check whether the custom reference is editable
					if(buyerRefVO.getEditable() == 1){
						customRefVO.setBuyerRefTypeId(buyerRefVO.getRefTypeId());
					    soCustomRefsVoList.add(customRefVO);
						
						// Get the value if it is already present in the service order.
						String existingCustomRefValue = null; 
						existingCustomRefValue = existingRefmap.get(customRefName.toLowerCase());
						custRefVO.setRefTypeId(buyerRefVO.getRefTypeId());
						custRefVO.setRefValue(customRefValue);
						custRefVO.setsoId(serviceOrder.getSoId());
						if(null!=serviceOrder.getBuyerId()){
							custRefVO.setModifiedBy(serviceOrder.getBuyerId().toString());
						}
						if(null!=existingCustomRefValue ){
							if(!existingCustomRefValue.equals(customRefValue)){
								updatedCustRefs.add(custRefVO);
								if(null!=existingCustomRefValue){
									loggingComment.append("The reference field "+customRefName+" has been updated");
									loggingComment.append(" from "+existingCustomRefValue);
								}
								loggingComment.append(" to "+customRefValue+"<br>");
							}
						}else{
							loggingComment.append("The reference field "+customRefName+" has been updated to "+ customRefValue+"<br>");
							updatedCustRefs.add(custRefVO);
						}
					} else if(buyerRefVO.getEditable()==0){
						validationErrors.add(customRef.getName()+" "+ CommonUtility.getMessage(PublicAPIConstant.NOTEDITABLE_CUST_REF_VALUE));
					}
				}
				else{
					validationErrors.add(customRef.getName()+" "+ CommonUtility.getMessage(PublicAPIConstant.INVALID_CUST_REF_VALUE));
				}

			}
			int statusId = serviceOrder.getWfStateId();
			if(validationErrors.isEmpty() && (statusId==OrderConstants.CLOSED_STATUS
					|| statusId == OrderConstants.DELETED_STATUS
					|| statusId == OrderConstants.VOIDED_STATUS
					|| statusId == OrderConstants.CANCELLED_STATUS)){
				for(ServiceOrderCustomRefVO custRef: updatedCustRefs){
					serviceOrderBO.insertSoCustomReference(custRef);
				}
			}
			if (!soCustomRefsVoList.isEmpty()) {
				serviceOrder.setCustomReferences(soCustomRefsVoList);
			}
		}
		return loggingComment;
	}
	/**
	 * @param serviceOrder
	 * @param comment
	 * 
	 * Method to update Custom reference for closed service orders
	 */
	public OrderFulfillmentResponse updateSOCustRefLogging(ServiceOrder serviceOrder, OrderFulfillmentRequest request){
		OrderFulfillmentResponse ofResponse=new OrderFulfillmentResponse();
		com.servicelive.orderfulfillment.serviceinterface.vo.Identification identification = request.getIdentification();
		String comment = (String)request.getParameter("UPDATE_API_CUST_LOG_COMMENT");
		try{
			if(StringUtils.isNotBlank(comment)){
				Date newDate = new Date();
				SoLoggingVo logging = new SoLoggingVo();
				logging.setActionId(271);
				logging.setServiceOrderNo(serviceOrder.getSoId());
				if(null!=identification.getEntityId()){
					int entityId = identification.getEntityId().intValue();
					logging.setEntityId(entityId);
				}
				if(null!=identification){
					logging.setModifiedBy(identification.getUsername());
					logging.setCreatedByName(identification.getFullname());
				}
				
				logging.setRoleId(7);
				logging.setCreatedDate(newDate);
				logging.setModifiedDate(newDate);
				logging.setComment(comment);
				serviceOrderBO.insertSoLogging(logging);
			}
		}catch(Exception e){
			ofResponse.addError("Error while updating Service order.");
		}
		return ofResponse;
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
	 * This method is for getting Phone TypeId.
	 * 
	 * @param phoneType  String
	 * @return Integer
	 */
	private Integer validateSoSubstatus(ServiceOrder serviceOrder,String soSubStatus) {
		try {		
				lookUpVOLists = lookupBO.getSubStatusList(serviceOrder.getWfStateId());
			for (LookupVO lookupVO : lookUpVOLists) {
				if (soSubStatus.equalsIgnoreCase(lookupVO.getDescr())) {
					return lookupVO.getId();
				}
			}
		} catch (Exception e) {
			logger.error("Data Exception Occurred while validating So Substatus"
					+ e.getMessage(), e);
		}
		return null;
	}
	
		
	private void validateDates(ServiceOrder serviceOrder, List<String> validationErrors) {
		java.util.Date now = new java.util.Date();
		
		Timestamp currentDateTime = new
                Timestamp(now.getTime());
		
		com.servicelive.orderfulfillment.domain.SOSchedule soSchedule = serviceOrder.getSchedule();
		
		Timestamp timeStampStartDate = new
                Timestamp(soSchedule.getServiceDate1().getTime());
		
		Timestamp newStartTimeCombined = new Timestamp(TimeUtils
				.combineDateAndTime(timeStampStartDate,
						soSchedule.getServiceTimeStart(),
						serviceOrder.getServiceLocationTimeZone()).getTime());
		if (null!=soSchedule.getServiceDate2()) {
			Timestamp timeStampEndDate = new
	                Timestamp(soSchedule.getServiceDate2().getTime());
			
			Timestamp newEndTimeCombined = new Timestamp(TimeUtils
					.combineDateAndTime(timeStampEndDate,
							soSchedule.getServiceTimeEnd(),
							serviceOrder.getServiceLocationTimeZone()).getTime());

			if ( SOScheduleType.DATERANGE==soSchedule.getServiceDateTypeId()) {
				if (newStartTimeCombined.compareTo(newEndTimeCombined) >= 0) {

					validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STARTDATE_ENDDATE));
					return;

				}
			}
		}
		if (newStartTimeCombined.compareTo(currentDateTime) < 0) {
			validationErrors.add(CommonUtility.getMessage(PublicAPIConstant.STARTDATE_IMPROPER));
			return;
		}

	}
}

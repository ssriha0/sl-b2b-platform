package com.newco.marketplace.web.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.promo.PromoBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.utils.DateUtils;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.TierRouteProviders;
import com.servicelive.orderfulfillment.domain.type.CarrierType;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.EntityType;
import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;

/**
 * User: Mustafa Motiwala Date: Mar 11, 2010 Time: 5:19:49 PM
 */
public class OFServiceOrderMapper {
	private Logger logger = Logger.getLogger(OFServiceOrderMapper.class);
	private IBuyerBO buyerBO;
	private PromoBO promoBO;
	private IBuyerFeatureSetBO featureSetBO;

	/**
	 * @param srcServiceOrder
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	public CreateOrderRequest createOrderRequest(
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder srcServiceOrder,
			SecurityContext securityContext) throws BusinessServiceException {

		logger.info("Begin Method...");
		CreateOrderRequest returnVal = new CreateOrderRequest();
		ServiceOrder dstServiceOrder = new ServiceOrder();

		// buyer setup
		mapBuyerInformation(dstServiceOrder, srcServiceOrder, securityContext);
		SPNetHeaderVO spnHdr = new SPNetHeaderVO();
		if (null != srcServiceOrder.getSpnId()
				&& !srcServiceOrder.getSpnId().equals(0)) {
			spnHdr = buyerBO.getSpnDetails(srcServiceOrder.getSpnId());
		}
		if (null != srcServiceOrder.getSoWrkFlowControls()) {
			mapSOWorkFlowControls(dstServiceOrder, srcServiceOrder, spnHdr);
		} else {
			logger.info("srcServiceOrder.getSoWrkFlowControls() IS NULL!!!");
		}
		dstServiceOrder.setPriceModel(PriceModelType.NAME_PRICE);

		returnVal.setIdentification(OFUtils
				.createOFIdentityFromSecurityContext(securityContext));
		returnVal.setServiceOrder(dstServiceOrder);

		logger.info("End Method...");
		return returnVal;
	}

	/**
	 * @param request
	 * @param securityContext
	 * @return
	 * @throws BusinessServiceException
	 */
	public OrderFulfillmentRequest editServiceOrder(
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request,
			SecurityContext securityContext, String perfCriteriaLevel)
			throws BusinessServiceException {
		logger.info("Begin Method...");
		OrderFulfillmentRequest returnVal = new OrderFulfillmentRequest();

		ServiceOrder serviceOrder = new ServiceOrder();
		serviceOrder.setSoId(request.getSoId());

		mapServiceOrder(serviceOrder, request, securityContext,
				perfCriteriaLevel);
		serviceOrder.setSpnId(request.getSpnId());
		returnVal.setElement(serviceOrder);
		returnVal.setIdentification(OFUtils
				.createOFIdentityFromSecurityContext(securityContext));

		logger.info("End Method...");
		return returnVal;
	}

	private void mapServiceOrder(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request,
			SecurityContext securityContext, String perfCriteriaLevel)
			throws BusinessServiceException {
		// For mapping General Section
		mapGeneralSection(serviceOrder, request);

		// For mapping Scope Of Work
		mapScopeOfWork(serviceOrder, request, serviceOrder.getBuyerId(),
				securityContext);

		// For mapping ServiceLocation
		validateZipAndState(request.getServiceLocation().getZip(), request
				.getServiceLocation().getState());

		// set serviceOrder timezone
		serviceOrder.setServiceLocationTimeZone(LocationUtils
				.getTimeZone(request.getServiceLocation().getZip()));
		TimeZone timezone = TimeZone.getTimeZone(serviceOrder
				.getServiceLocationTimeZone());
		int offset = Math.round(timezone.getOffset(System.currentTimeMillis())
				/ (1000 * 60 * 60));
		serviceOrder.setServiceDateTimezoneOffset(offset);

		mapServiceLocation(serviceOrder, request);

		// For mapping Schedule Details
		mapServiceOrderSchedule(serviceOrder, request);

		// For mapping Pricing Details
		mapServiceOrderPricing(serviceOrder, request);

		// For mapping Contact Details
		mapServiceOrderContacts(serviceOrder, request);

		// For mapping Parts Details
		if (request.getPartsSupplier() != null)
			serviceOrder.setPartsSupplier(PartSupplierType.fromId(request
					.getPartsSupplier()));
		mapServiceOrderParts(serviceOrder, request.getParts(),
				request.getPartsSupplier());
		
		serviceOrder.setBuyerId(request.getBuyer().getBuyerId().longValue());
		// For mapping Custom References
		mapServiceOrderCustomRef(serviceOrder, request.getCustomRefs());

		// For mapping Add Ons
		mapServiceOrderAddOns(serviceOrder, request.getUpsellInfo());

		if (null != request.getSoWrkFlowControls()) {
			SPNetHeaderVO spnHdr = new SPNetHeaderVO();
			if (null != request.getSpnId() && !request.getSpnId().equals(0)
					&& !request.getSpnId().equals(-1)) {
				spnHdr = buyerBO.getSpnDetails(request.getSpnId());
			}
			mapSOWorkFlowControls(serviceOrder, request, spnHdr);
			// For mapping RoutedProviders
			if (request.getSoWrkFlowControls().getTierRouteInd() == null
					|| !(request.getSoWrkFlowControls().getTierRouteInd())) {
				mapRoutedResources(serviceOrder, request.getRoutedResources(),
						request.getSpnId());
			} else {
				if (null == perfCriteriaLevel) {
					perfCriteriaLevel = (spnHdr.getPerfCriteriaLevel() == null ? "N/A"
							: spnHdr.getPerfCriteriaLevel());
				}
				mapTierEligibleProviders(serviceOrder,
						request.getRoutedResources(), request.getSpnId(),
						perfCriteriaLevel, request.getBuyer().getBuyerId());
			}
		}
	}

	private void mapSOWorkFlowControls(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request,
			com.newco.marketplace.dto.vo.spn.SPNetHeaderVO spnHdr) {
		logger.info("MAPPING SOWORKFLOWCONTROLS");
		SOWorkflowControls soWorkflowControls = new SOWorkflowControls();
		soWorkflowControls.setServiceOrder(serviceOrder);
		soWorkflowControls.setTierRouteInd(request.getSoWrkFlowControls()
				.getTierRouteInd());
		soWorkflowControls.setPerformanceScore(request.getSoWrkFlowControls()
				.getPerformanceScore());
		// SL-19569: setting sealed bid ind in so_workflow_controls since this
		// got deleted in R7.0
		soWorkflowControls.setSealedBidIndicator(request.getSealedBidInd());
		if (spnHdr != null
				&& request.getSoWrkFlowControls().getTierRouteInd()
						.equals(true)) {
			soWorkflowControls.setMpOverFlow(spnHdr.getIsMPOverflow());
		}
		if (featureSetBO.validateFeature(request.getBuyer().getBuyerId(),
				BuyerFeatureConstants.NON_FUNDED)) {
			soWorkflowControls.setNonFundedInd(true);
		}
		//R12_0 setting invoice_parts_ind as 'NO_PARTS_ADDED' for HSR Buyer
		logger.info("before setting InvoicePartsInd indicator");
		if(request.getBuyer().getBuyerId().equals(OrderConstants.BUYERIDOFHSR)){
			soWorkflowControls.setInvoicePartsInd("NO_PARTS_ADDED");
			logger.info("after setting InvoicePartsInd indicator");
		}
		serviceOrder.setSOWorkflowControls(soWorkflowControls);
	}

	private void mapRoutedResources(
			ServiceOrder serviceOrder,
			List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> srcRoutedProviderList,
			Integer spnId) {
		List<RoutedProvider> dstRoutedProviderList = new ArrayList<RoutedProvider>();
		logger.info("provList size()" + srcRoutedProviderList.size());
		for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider srcRoutedProvider : srcRoutedProviderList) {
			RoutedProvider dstRoutedProvider = new RoutedProvider();
			dstRoutedProvider.setProviderResourceId(srcRoutedProvider
					.getResourceId().longValue());
			dstRoutedProvider.setVendorId(srcRoutedProvider.getVendorId());
			dstRoutedProvider.setPriceModel(serviceOrder.getPriceModel());

			// logger added for debugging purpose
			if (null != srcRoutedProvider.getSpnId()) {
				logger.info("spnId associated with the existing routedproviders:"
						+ srcRoutedProvider.getSpnId());
			}

			if (null != spnId) {
				dstRoutedProvider.setSpnId(spnId);
			}
			dstRoutedProvider.setPerfScore(srcRoutedProvider.getPerfScore());
			dstRoutedProviderList.add(dstRoutedProvider);
		}
		serviceOrder.setRoutedResources(dstRoutedProviderList);
	}

	private void mapTierEligibleProviders(
			ServiceOrder serviceOrder,
			List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> srcRoutedProviderList,
			Integer spnId, String perfCriteriaLevel, Integer buyerId) {
		List<TierRouteProviders> dstRoutedProviderList = new ArrayList<TierRouteProviders>();
		logger.info("provList size()" + srcRoutedProviderList.size());
		List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> eligibleProviderList = eliminateMarketPlaceProviders(srcRoutedProviderList);
		List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> routedProviderList = rankMembers(
				eligibleProviderList, perfCriteriaLevel, buyerId);

		for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider srcRoutedProvider : routedProviderList) {
			TierRouteProviders dstRoutedProvider = new TierRouteProviders();
			dstRoutedProvider.setProviderResourceId(srcRoutedProvider
					.getResourceId());
			dstRoutedProvider.setVendorId(srcRoutedProvider.getVendorId());
			if (null != spnId) {
				dstRoutedProvider.setSpnId(spnId);
			}
			dstRoutedProvider.setPerformanceScore(srcRoutedProvider
					.getPerfScore());
			dstRoutedProvider.setFirmPerformanceScore(srcRoutedProvider
					.getFirmPerfScore());
			dstRoutedProvider.setRank(Integer.parseInt(srcRoutedProvider
					.getRank()));
			dstRoutedProviderList.add(dstRoutedProvider);
		}
		serviceOrder.setTierRoutedResources(dstRoutedProviderList);
	}

	private void mapBuyerInformation(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request,
			SecurityContext securityContext) throws BusinessServiceException {
		serviceOrder.setBuyerId(request.getBuyer().getBuyerId().longValue());
		serviceOrder.setBuyerResourceId(request.getBuyerResourceId()
				.longValue());
		serviceOrder.setBuyerContactId(request.getBuyer().getContactId());
		serviceOrder.setCreatorUserName(securityContext.getUsername());
		try {
			Buyer buyer = buyerBO.getBuyer(securityContext.getCompanyId());
			serviceOrder.setBuyerContactId(buyerBO
					.getByerAdminContactIdbyResourceId(request
							.getBuyerResourceId()));
			serviceOrder.setFundingTypeId(buyer.getFundingTypeId());
			serviceOrder.setPostingFee(new BigDecimal(buyer.getPostingFee()));
			serviceOrder.setCancellationFee(new BigDecimal(buyer
					.getCancellationFee()));
			Double postingfee = promoBO
					.retrievePromoPostingFeeByRoleId(securityContext
							.getRoleId());
			if (postingfee != null) {
				serviceOrder.setPostingFee(new BigDecimal(postingfee));
			}
			if (featureSetBO.validateFeature(buyer.getBuyerId(),
					BuyerFeatureConstants.TASK_LEVEL)) {
				serviceOrder.setPriceType(SOPriceType.TASK_LEVEL.name());
			}
		} catch (Exception e) {
			logger.error(
					"Error happened when trying to get the funding type for buyer",
					e);
			throw new BusinessServiceException(e);
		}
	}

	/**
	 * This method is for mapping the General Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request) {
		logger.info("Mapping: General Section --->Starts");
		serviceOrder.setSowTitle(request.getSowTitle());
		serviceOrder.setSowDs(request.getSowDs());
		serviceOrder.setBuyerTermsCond(request.getBuyerTermsCond());
		serviceOrder.setProviderInstructions(request.getProviderInstructions());
		logger.info("Mapping: General Section --->Ends");
	}

	/**
	 * This method is for mapping the Scope Of Work Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param request
	 *            ScopeOfWork
	 * @param buyerId
	 *            Integer
	 * @return void
	 * @throws BusinessServiceException
	 */
	private void mapScopeOfWork(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request,
			Long buyerId, SecurityContext securityContext) {

		logger.info("Mapping: Scope Of Work --->Starts");
		serviceOrder.setPrimarySkillCatId(request.getPrimarySkillCatId());
		List<SOTask> tasks = new ArrayList<SOTask>();
		// setting information from sku if it is selected.
		if (request.isSkuTaskIndicator() == true) {
			for (ServiceOrderTask srcTask : request.getSkus()) {
				SOTask dstTask = new SOTask();
				dstTask.setTaskId(srcTask.getTaskId());
				dstTask.setTaskName(srcTask.getTaskName());
				dstTask.setTaskComments(srcTask.getTaskComments());
				dstTask.setSkillNodeId(srcTask.getSkillNodeId());
				dstTask.setServiceTypeId(srcTask.getServiceTypeId());
				if (null != srcTask.getPrice()) {
					dstTask.setPrice(new BigDecimal(srcTask.getPrice()
							.toString()));
				}
				if (null != srcTask.getFinalPrice()) {
					dstTask.setFinalPrice(new BigDecimal(srcTask
							.getFinalPrice().toString()));
				}
				if (null != srcTask.getRetailPrice()) {
					dstTask.setRetailPrice(new BigDecimal(srcTask
							.getRetailPrice()));
				}
				if (null != srcTask.getSellingPrice()) {
					dstTask.setSellingPrice(new BigDecimal(srcTask
							.getSellingPrice()));
				}
				if (null != srcTask.getSequenceNumber()) {
					dstTask.setSequenceNumber(srcTask.getSequenceNumber());
				}
				if (null != srcTask.getSku()) {
					dstTask.setExternalSku(srcTask.getSku());
				}
				if (null != srcTask.getTaskType() && srcTask.getTaskType() == 1) {
					// setPermitTask(true)
					dstTask.setTaskType(OrderConstants.PERMIT_TASK);
				}
				dstTask.setPrimaryTask(srcTask.isPrimaryTask());
				if (null != srcTask.getSortOrder()) {
					dstTask.setSortOrder(srcTask.getSortOrder());
				}
				if (null == srcTask.getTaskId()) {
					dstTask.setAutoInjectedInd(0); // set auto injected
													// indicator false for tasks
													// created through frontend
				} else {
					dstTask.setAutoInjectedInd(srcTask.getAutoInjectedInd());
				}
				dstTask.setPrimaryTask(srcTask.isPrimaryTask());
				dstTask.setSkuId(srcTask.getSkuId());
				dstTask.setServiceOrder(serviceOrder);
				tasks.add(dstTask);
			}
		} else {
			for (ServiceOrderTask srcTask : request.getTasks()) {
				SOTask dstTask = new SOTask();
				dstTask.setTaskId(srcTask.getTaskId());
				dstTask.setTaskName(srcTask.getTaskName());
				dstTask.setTaskComments(srcTask.getTaskComments());
				dstTask.setSkillNodeId(srcTask.getSkillNodeId());
				dstTask.setServiceTypeId(srcTask.getServiceTypeId());
				if (null != srcTask.getPrice()) {
					dstTask.setPrice(new BigDecimal(srcTask.getPrice()
							.toString()));
				}
				if (null != srcTask.getFinalPrice()) {
					dstTask.setFinalPrice(new BigDecimal(srcTask
							.getFinalPrice().toString()));
				}
				if (null != srcTask.getRetailPrice()) {
					dstTask.setRetailPrice(new BigDecimal(srcTask
							.getRetailPrice()));
				}
				if (null != srcTask.getSellingPrice()) {
					dstTask.setSellingPrice(new BigDecimal(srcTask
							.getSellingPrice()));
				}
				if (null != srcTask.getSequenceNumber()) {
					dstTask.setSequenceNumber(srcTask.getSequenceNumber());
				}
				if (null != srcTask.getSku()) {
					dstTask.setExternalSku(srcTask.getSku());
				}
				if (null != srcTask.getTaskType() && srcTask.getTaskType() == 1) {
					// setPermitTask(true)
					dstTask.setTaskType(OrderConstants.PERMIT_TASK);
				}
				dstTask.setPrimaryTask(srcTask.isPrimaryTask());
				if (null != srcTask.getSortOrder()) {
					dstTask.setSortOrder(srcTask.getSortOrder());
				}
				if (null == srcTask.getTaskId()) {
					dstTask.setAutoInjectedInd(0); // set auto injected
													// indicator false for tasks
													// created through frontend
				} else {
					dstTask.setAutoInjectedInd(srcTask.getAutoInjectedInd());
				}
				dstTask.setPrimaryTask(srcTask.isPrimaryTask());
				dstTask.setServiceOrder(serviceOrder);
				tasks.add(dstTask);
			}
		}
		serviceOrder.setTasks(tasks);
		serviceOrder.setTaskLevelPricing(request.isTaskLevelPricingInd());
		if (request.isTaskLevelPricingInd()) {
			try {
				if (securityContext != null) {
					if (securityContext.getVendBuyerResId() != null) {
						serviceOrder.setModifiedBy(securityContext
								.getVendBuyerResId().toString());
					}
				}
				serviceOrder.setModifiedDate(new Date());
				serviceOrder.setCreatedDate(new Date());

			} catch (Exception e) {
				logger.error("Error while fetching buyer details");
			}
		}

	}

	/**
	 * This method is for mapping the ServiceLocation Section of Create Request
	 * to Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param request
	 *            Location
	 * @return void
	 */
	private void mapServiceLocation(ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder request) {
		logger.info("Mapping: Service Location--->Starts");

		SOLocation serviceLocation = new SOLocation();
		serviceLocation.setSoLocationClassId(LocationClassification
				.fromId(request.getServiceLocation().getLocnClassId()));
		serviceLocation.setStreet1(request.getServiceLocation().getStreet1());
		serviceLocation.setStreet2(request.getServiceLocation().getStreet2());
		serviceLocation.setAptNumber(request.getServiceLocation().getAptNo());
		serviceLocation.setCity(request.getServiceLocation().getCity());
		serviceLocation.setState(request.getServiceLocation().getState());
		serviceLocation.setZip(request.getServiceLocation().getZip());
		serviceLocation.setZip4(request.getServiceLocation().getZip4());
		serviceLocation.setLocationNote(request.getServiceLocation()
				.getLocnNotes());
		// added for SL-16791
		serviceLocation.setLocationName(request.getServiceLocation()
				.getLocnName());
		// add service location
		serviceLocation.setSoLocationTypeId(LocationType.SERVICE);
		serviceOrder.addLocation(serviceLocation);
		serviceLocation.setCreatedDate(new Timestamp(DateUtils.getCurrentDate()
				.getTime()));
	}

	/**
	 * This method is for mapping the Schedule Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param srcServiceOrder
	 *            srcServiceOrder
	 */
	private void mapServiceOrderSchedule(
			ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder srcServiceOrder)
			throws BusinessServiceException {
		logger.info("Mapping: ServiceOrder Schedule --->Starts");

		SOSchedule soSchedule = new SOSchedule();
		soSchedule.setServiceDate1(srcServiceOrder.getServiceDate1());
		soSchedule.setServiceTimeStart(srcServiceOrder.getServiceTimeStart());
		soSchedule.setServiceDateTypeId(SOScheduleType.fromId(srcServiceOrder
				.getServiceDateTypeId()));
		if (srcServiceOrder.getServiceDate2() != null
				&& !srcServiceOrder.getServiceDate2().equals("")) {
			soSchedule.setServiceDate2(srcServiceOrder.getServiceDate2());
		} else {
			soSchedule.setServiceDateTypeId(SOScheduleType.SINGLEDAY.getId());
		}
		soSchedule.setServiceTimeEnd(srcServiceOrder.getServiceTimeEnd());
		serviceOrder.setSchedule(soSchedule);
		serviceOrder.setProviderServiceConfirmInd(srcServiceOrder
				.getProviderServiceConfirmInd());

		List<String> validationErrors = soSchedule.validate();

		if (!validationErrors.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder();
			for (String error : validationErrors) {
				errorMessage.append(String.format("%1$s\n", error));
			}
			throw new BusinessServiceException(errorMessage.toString());
		}
		logger.info("Mapping: ServiceOrder Schedule --->Ends");
	}

	/**
	 * This method is for mapping the Pricing Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 * @param srcServiceOrder
	 */
	private void mapServiceOrderPricing(
			ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder srcServiceOrder) {
		logger.info("Mapping: Pricing --->Starts");

		PriceModelType priceModel = PriceModelType.valueOf(srcServiceOrder
				.getPriceModel());

		BigDecimal spendLimitLabor = new BigDecimal(
				srcServiceOrder.getSpendLimitLabor()).setScale(2,
				RoundingMode.HALF_EVEN);
		BigDecimal spendLimitParts = new BigDecimal(
				srcServiceOrder.getSpendLimitParts()).setScale(2,
				RoundingMode.HALF_EVEN);
		BigDecimal totalPermitPrice = new BigDecimal(
				srcServiceOrder.getTotalPermitPrice()).setScale(2,
				RoundingMode.HALF_EVEN);
		SOPrice price = new SOPrice();
		SOWorkflowControls soWorkflowControls = new SOWorkflowControls();

		price.setServiceOrder(serviceOrder);
		price.setOrigSpendLimitLabor(spendLimitLabor);
		price.setOrigSpendLimitParts(spendLimitParts);
		price.setDiscountedSpendLimitLabor(spendLimitLabor);
		price.setDiscountedSpendLimitParts(spendLimitParts);
		price.setInitPostedSpendLimitLabor(spendLimitLabor);
		price.setInitPostedSpendLimitParts(spendLimitParts);

		soWorkflowControls.setServiceOrder(serviceOrder);
		if (srcServiceOrder.getSealedBidInd() != null) {
			soWorkflowControls.setSealedBidIndicator(srcServiceOrder
					.getSealedBidInd());
		}
		serviceOrder.setSOWorkflowControls(soWorkflowControls);
		serviceOrder.setPrice(price);
		serviceOrder.setPriceModel(priceModel);
		if (null == srcServiceOrder.getFundingTypeId())
			serviceOrder.setFundingTypeId(null);
		else
			serviceOrder.setFundingTypeId(srcServiceOrder.getFundingTypeId());
		serviceOrder.setSpendLimitLabor(spendLimitLabor);
		serviceOrder.setSpendLimitParts(spendLimitParts);
		//SL-20527 : Setting Total Permit Price 
		serviceOrder.setTotalPermitPrice(totalPermitPrice);

		if (null != srcServiceOrder.getPostingFee())
			serviceOrder.setPostingFee(new BigDecimal(srcServiceOrder
					.getPostingFee()).setScale(2, RoundingMode.HALF_EVEN));
		if (StringUtils.isNotBlank(srcServiceOrder.getAccountId())) {
			serviceOrder.setAccountId(new Integer(srcServiceOrder
					.getAccountId()));
		}
		if (srcServiceOrder.isShareContactInd() != null) {
			serviceOrder.setShareContactIndicator(srcServiceOrder
					.isShareContactInd());
		}

		logger.info("Mapping: Pricing --->Ends");
	}

	/**
	 * This method is for mapping Contacts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param srcServiceOrder
	 */
	private void mapServiceOrderContacts(
			ServiceOrder serviceOrder,
			com.newco.marketplace.dto.vo.serviceorder.ServiceOrder srcServiceOrder) {

		logger.info("Mapping: Contact Details--->Starts");

		/* Map ServiceOrder Contact: */
		if (null != srcServiceOrder.getServiceContact()
				&& StringUtils.isNotBlank(srcServiceOrder.getServiceContact()
						.getFirstName())) {
			SOContact serviceContact = mapContactDetails(srcServiceOrder
					.getServiceContact());
			serviceContact.setContactTypeId(ContactType.PRIMARY);
			serviceContact.addContactLocation(ContactLocationType.SERVICE);
			serviceOrder.addContact(serviceContact);
		}
		/*
		 * Map EndUser Contact - if FirstName is present, we are interested in
		 * the details.
		 */
		if (null != srcServiceOrder.getEndUserContact()
				&& StringUtils.isNotBlank(srcServiceOrder.getEndUserContact()
						.getFirstName())) {
			SOContact endUserContact = mapContactDetails(srcServiceOrder
					.getEndUserContact());
			endUserContact.setContactTypeId(ContactType.ALTERNATE); // Added for
																	// Alternate
																	// Contact
																	// Id
			endUserContact.addContactLocation(ContactLocationType.END_USER);
			serviceOrder.addContact(endUserContact);
		}

		if (srcServiceOrder.getSelectedAltBuyerContact() != null
				&& srcServiceOrder.getSelectedAltBuyerContact().getContactId() != null) {
			Integer altContactId = srcServiceOrder.getSelectedAltBuyerContact()
					.getContactId();

			try {
				SOContact altBuyerContact = mapContactDetails(buyerBO
						.getBuyerContactByContactId(altContactId));
				altBuyerContact.setContactTypeId(ContactType.ALTERNATE); // Added
																			// for
																			// Alternate
																			// Contact
																			// Id
				altBuyerContact
						.addContactLocation(ContactLocationType.BUYER_SUPPORT);
				// altBuyerContact.getSoContactLocations().get(0).setCreatedDate(new
				// Date());
				serviceOrder.addContact(altBuyerContact);
				serviceOrder.setBuyerContactId(altContactId);
			} catch (BusinessServiceException e) {
				logger.error("Error: Alternate contact ID not found");
			}
        	
        }
        
	/*	SL-19820
	 * if (null != ServletActionContext.getContext().getSession().get(
				"templateAltBuyerContactId")) {
			Integer templateAltBuyerContactId = (Integer) ServletActionContext
					.getContext().getSession().get("templateAltBuyerContactId");
			if (null != templateAltBuyerContactId) {
				serviceOrder.setBuyerContactId(templateAltBuyerContactId);
			}
		} */  
       // SL-19820
		if (null!=serviceOrder.getSoId() && null != ServletActionContext.getContext().getSession().get(
				"templateAltBuyerContactId_"+serviceOrder.getSoId())) {
			Integer templateAltBuyerContactId = (Integer) ServletActionContext
					.getContext().getSession().get("templateAltBuyerContactId_"+serviceOrder.getSoId());
			if (null != templateAltBuyerContactId) {
				serviceOrder.setBuyerContactId(templateAltBuyerContactId);
			}
		}    
    }

	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param parts
	 * @param partsSupplier
	 * @throws BusinessServiceException
	 */
	private void mapServiceOrderParts(ServiceOrder serviceOrder,
			List<Part> parts, Integer partsSupplier)
			throws BusinessServiceException {

		logger.info("Mapping: Parts --->Starts");
		List<SOPart> soPartList = new ArrayList<SOPart>();

		if (parts == null) {
			return;
		}

		for (Part partVo : parts) {
			SOPart soPart = new SOPart();

			soPart.setManufacturer(partVo.getManufacturer());
			soPart.setModelNumber(partVo.getModelNumber());
			soPart.setPartDs(partVo.getPartDs());
			soPart.setQuantity(partVo.getQuantity());

			soPart.setHeight(partVo.getHeight());
			soPart.setLength(partVo.getLength());
			soPart.setWidth(partVo.getWidth());
			soPart.setWeight(partVo.getWeight());
			soPart.setMeasurementStandard(partVo.getMeasurementStandard());

			soPart.setProductName(partVo.getProductLine());
			soPart.setSerialNumber(partVo.getSerialNumber());
			soPart.setOrderNumber(partVo.getOrderNumber());
			soPart.setPurchaseOrderNumber(partVo.getPurchaseOrderNumber());
			soPart.setPartStatusInt(partVo.getPartStatusId());
			soPart.setAdditionalPartInfo(partVo.getAdditionalPartInfo());
			soPart.setManufacturerPartNumber(partVo.getManufacturerPartNumber());
			soPart.setVendorPartNumber(partVo.getVendorPartNumber());
			soPart.setShipDate(partVo.getShipDate());

			if (partVo.getShippingCarrier() != null) {
				soPart.setShipCarrierId(CarrierType.fromId(partVo
						.getShippingCarrier().getCarrierId()));
				soPart.setShipTrackNo(partVo.getShippingCarrier()
						.getTrackingNumber());
				soPart.setShipDate(partVo.getShipDate());
			}
			if (partVo.getReturnCarrier() != null) {
				soPart.setReturnCarrierId(CarrierType.fromId(partVo
						.getReturnCarrier().getCarrierId()));
				soPart.setReturnTrackNo(partVo.getReturnCarrier()
						.getTrackingNumber());
			}
			if (partVo.getCoreReturnCarrier() != null) {
				soPart.setCoreReturnCarrierId(partVo.getCoreReturnCarrier()
						.getCarrierId());
				soPart.setCoreReturnTrackNo(partVo.getCoreReturnCarrier()
						.getTrackingNumber());
			}

			soPart.setAlternatePartReference1(partVo.getAltPartRef1());
			soPart.setAlternatePartReference2(partVo.getAltPartRef2());

			soPart.setProviderBringPartInd(partVo.isProviderBringPartInd());
			if (partsSupplier == 2) { // provider brings parts
				soPart.setProviderBringPartInd(true);
			} else if (partsSupplier == 1) { // buyer brings parts
				soPart.setProviderBringPartInd(false);
				if (partVo.getPickupLocation() != null) {
					SoLocation pickupLocation = partVo.getPickupLocation();
					SOLocation soPickupLocation = new SOLocation();
					validateZipAndState(pickupLocation.getZip(),
							pickupLocation.getState());
					if (pickupLocation.getLocnClassId() != null) {
						soPickupLocation
								.setSoLocationClassId(LocationClassification
										.fromId(pickupLocation.getLocnClassId()));
					}
					soPickupLocation.setSoLocationTypeId(LocationType.PICKUP);
					soPickupLocation.setCreatedDate(new Timestamp(DateUtils
							.getCurrentDate().getTime()));
					soPickupLocation.setLocationName(pickupLocation
							.getLocnName());
					soPickupLocation.setStreet1(pickupLocation.getStreet1());
					soPickupLocation.setStreet2(pickupLocation.getStreet2());
					soPickupLocation.setCity(pickupLocation.getCity());
					soPickupLocation.setState(pickupLocation.getState());
					soPickupLocation.setZip(pickupLocation.getZip());
					soPickupLocation.setZip4(pickupLocation.getZip4());
					soPickupLocation.setLocationNote(pickupLocation
							.getLocnNotes());
					soPart.setPickupLocation(soPickupLocation);
				}
				if (partVo.getPickupContact() != null) {
					Contact pickupContact = partVo.getPickupContact();
					SOContact soPickupContact = mapContactDetails(pickupContact);
					soPickupContact
							.addContactLocation(ContactLocationType.PICKUP);
					soPart.setPickupContact(soPickupContact);
				}
			}
			soPartList.add(soPart);
		}

		serviceOrder.setParts(soPartList);
		logger.info("Mapping: Parts --->End");

	}

	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @return void
	 */
	private void mapServiceOrderCustomRef(ServiceOrder serviceOrder,
			List<ServiceOrderCustomRefVO> customReferences) {
		logger.info("Mapping: Customer Reference --->Starts");

		List<BuyerReferenceVO> buyerCustomReferenceList = null;

		try {
			buyerCustomReferenceList = buyerBO
					.getBuyerCustomReferenceWithDisplayNoValue(serviceOrder
							.getBuyerId().intValue());

		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.info("Exception in mapping custom reference with empty value --->"
					+ e);
		}

		List<SOCustomReference> soCustomRefList = new ArrayList<SOCustomReference>();

		for (ServiceOrderCustomRefVO customRefVO : customReferences) {

			SOCustomReference soCustomRef = new SOCustomReference();
			soCustomRef.setBuyerRefValue(customRefVO.getRefValue());
			soCustomRef.setBuyerRefTypeId(customRefVO.getRefTypeId());
			soCustomRef.setServiceOrder(serviceOrder);
			soCustomRefList.add(soCustomRef);
		}
		List<SOCustomReference> tempSoCustomRefList =new ArrayList<SOCustomReference>();
		
		tempSoCustomRefList =soCustomRefList;
		int temp=0;
	if(null!=buyerCustomReferenceList && !buyerCustomReferenceList.isEmpty()){	
		for(BuyerReferenceVO buyerRefVO:buyerCustomReferenceList)
		{
			if(null!=tempSoCustomRefList && !tempSoCustomRefList.isEmpty()){
			for(SOCustomReference soCustomList : tempSoCustomRefList)
			{
				if((buyerRefVO.getBuyerRefTypeId().equals(soCustomList.getBuyerRefTypeId()))){
					temp=1;
					break;				
				}
				else
				{
					temp=0;
				}
			}
			if(temp==0)
			{
				SOCustomReference soCustomRef = new SOCustomReference();
				soCustomRef.setBuyerRefValue("");
				soCustomRef.setBuyerRefTypeId(buyerRefVO.getBuyerRefTypeId());
				soCustomRef.setServiceOrder(serviceOrder);
				soCustomRefList.add(soCustomRef);
			}
			}
			else
			{
				SOCustomReference soCustomRef = new SOCustomReference();
				soCustomRef.setBuyerRefValue("");
				soCustomRef.setBuyerRefTypeId(buyerRefVO.getBuyerRefTypeId());
				soCustomRef.setServiceOrder(serviceOrder);
				soCustomRefList.add(soCustomRef);
			}
		}
	}
		serviceOrder.setCustomReferences(soCustomRefList);
		logger.info("Mapping: Customer Reference --->Ends");
	}

	/**
	 * This method is for mapping the Add Ons Section of Create Request to
	 * Service Order.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @return void
	 */
	private void mapServiceOrderAddOns(ServiceOrder serviceOrder,
			List<ServiceOrderAddonVO> upsellInfo) {
		logger.info("Inside mapServiceOrderAddOns---> Start");

		List<SOAddon> soAddOns = new ArrayList<SOAddon>();

		if (upsellInfo == null) {
			return;
		}

		for (ServiceOrderAddonVO addOn : upsellInfo) {
			SOAddon soAddOn = new SOAddon();
			soAddOn.setSku(addOn.getSku());
			soAddOn.setDescription(addOn.getDescription());
			Double retailPrice = addOn.getRetailPrice();
			soAddOn.setRetailPrice(BigDecimal.valueOf(retailPrice));
			soAddOn.setQuantity(addOn.getQuantity());
			soAddOn.setCoverage(addOn.getCoverage());
			soAddOn.setMargin(addOn.getMargin());
			soAddOn.setMiscInd(addOn.isMiscInd());
			soAddOn.setScopeOfWork(addOn.getScopeOfWork());
			Double serviceFee = addOn.getServiceFee();
			soAddOn.setServiceFee(BigDecimal.valueOf(serviceFee));
			soAddOn.setTaxPercentage(Double.valueOf(addOn.getTaxPercentage()));
			soAddOns.add(soAddOn);
		}

		serviceOrder.setAddons(soAddOns);

		logger.info("Inside mapServiceOrderAddOns---> End");
	}

	/**
	 * This method is for mapping Contact Details.
	 * 
	 * @param contact
	 * @return a new SOContact object with values copied from the input. Returns
	 *         null if input is null.
	 */
	private SOContact mapContactDetails(Contact contact) {
		logger.info("Inside mapContactDetails---> Start");
		SOContact returnVal = new SOContact();
		returnVal.setBusinessName(contact.getBusinessName());
		returnVal.setFirstName(contact.getFirstName());
		returnVal.setLastName(contact.getLastName());
		returnVal.setEmail(contact.getEmail());
		returnVal.setEntityId(contact.getResourceId()); // Added for contact id
		returnVal.setBusinessName(contact.getBusinessName()); // Added for
																// contact id
		if (null != contact.getEntityTypeId()) {
			if (contact.getEntityTypeId() == 10)
				returnVal.setEntityType(EntityType.BUYER); //
			else if (contact.getEntityTypeId() == 20)
				returnVal.setEntityType(EntityType.PROVIDER);
			else if (contact.getEntityTypeId() == 30)
				returnVal.setEntityType(EntityType.MARKETPLACE);
			else if (contact.getEntityTypeId() == 0)
				returnVal.setEntityType(EntityType.UNKNOWN);
		} else {
			returnVal.setEntityType(EntityType.UNKNOWN);
		}

		if (contact.getPhones() != null) {
			Set<SOPhone> phoneVOList = mapPhoneDetails(returnVal,
					contact.getPhones());
			returnVal.setPhones(phoneVOList);
		}

		returnVal.setCreatedDate(new Date());
		logger.info("Inside mapContactDetails---> End");
		return returnVal;
	}

	/**
	 * This method is for mapping Phone Details.
	 */
	private Set<SOPhone> mapPhoneDetails(SOContact contact,
			List<PhoneVO> phoneVOList) {
		logger.info("Inside mapPhoneDetails--->Start");
		Set<SOPhone> returnVal = new HashSet<SOPhone>();
		for (PhoneVO phoneVO : phoneVOList) {
			if (StringUtils.isBlank(phoneVO.getPhoneNo()))
				continue;
			SOPhone phone = new SOPhone();
			phone.setPhoneNo(phoneVO.getPhoneNo());
			phone.setPhoneExt(phoneVO.getPhoneExt());
			phone.setPhoneClass(PhoneClassification.fromId(phoneVO.getClassId()));
			phone.setPhoneType(PhoneType.fromId(phoneVO.getPhoneType()));
			phone.setContact(contact);
			returnVal.add(phone);
		}
		logger.info("Inside mapPhoneDetails--->End");
		return returnVal;
	}

	/**
	 * This method is for validating the zipCode and State
	 * 
	 * @param zip
	 *            String
	 * @param state
	 *            String
	 */
	private void validateZipAndState(String zip, String state)
			throws BusinessServiceException {
		int zipValidation = LocationUtils.checkIfZipAndStateValid(zip, state);
		switch (zipValidation) {
		case Constants.LocationConstants.ZIP_NOT_VALID:
			throw new BusinessServiceException(
					"Invalid zipcode on the service order!");
		case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
			throw new BusinessServiceException("Zipcode & state do not match.");
		}
	}

	private List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> eliminateMarketPlaceProviders(
			List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> srcRoutedProviderList) {
		List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> returnList = new ArrayList<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider>();
		logger.info("Entered eliminateMarketPlaceProviders"
				+ srcRoutedProviderList.size());

		for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider provider : srcRoutedProviderList) {
			// both perfscore null => marketplace providers
			if (provider.getPerfScore() == null
					&& provider.getFirmPerfScore() == null) {
				// do nothing
			} else {
				// add to the list if SPN member
				returnList.add(provider);
			}
		}
		logger.info("List size after eliminating map provs" + returnList.size());
		return returnList;
	}

	// to rank the members based o performance score
	private List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> rankMembers(
			List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> srcRoutedProviderList,
			String criteriaLevel, Integer buyerId) {

		// to get the elements with the same scores
		List<Integer> duplicateList = getDuplicates(srcRoutedProviderList,
				criteriaLevel);
		// get the date of last completed order for prov/firm with same score
		try {
			if (null != duplicateList && duplicateList.size() > 0) {
				List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> dateList = buyerBO
						.getCompletedDate(duplicateList, criteriaLevel, buyerId);
				if (null != dateList && dateList.size() > 0) {
					for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider provider : srcRoutedProviderList) {
						for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider dto : dateList) {
							if (null != criteriaLevel
									&& criteriaLevel.equals("PROVIDER")) {
								if (null != provider.getResourceId()
										&& null != dto.getResourceId()
										&& provider.getResourceId().intValue() == dto
												.getResourceId().intValue()) {
									provider.setCompletedDate(dto
											.getCompletedDate());
								}
							} else {
								if (null != provider.getVendorId()
										&& null != dto.getVendorId()
										&& provider.getVendorId().intValue() == dto
												.getVendorId().intValue()) {
									provider.setCompletedDate(dto
											.getCompletedDate());
								}
							}
						}
					}
				}
			}
			// sort the list based on score, date and name
			Comparator<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> compare = null;
			if (null != criteriaLevel && criteriaLevel.equals("FIRM")) {
				// if there are duplicates, we need to sort the list based on
				// last order completed date and provider name along with firm
				// performance score with perf score as primary sort factor,
				// date 2nd, and name 3rd
				if (null != duplicateList && duplicateList.size() > 0) {
					compare = com.newco.marketplace.dto.vo.serviceorder.RoutedProvider
							.getComparator(
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.FIRM_SCORE_DESCENDING,
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.DATE_ASCENDING,
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.FIRM_NAME_ASCENDING);
				}
				// if there aren't any duplicates, we need to sort the list
				// based only on firm performance score
				else if (null != duplicateList && duplicateList.size() == 0) {
					compare = com.newco.marketplace.dto.vo.serviceorder.RoutedProvider
							.getComparator(com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.FIRM_SCORE_DESCENDING);
				}
			} else {
				// if there are duplicates, we need to sort the list based on
				// last order completed date and provider name along with
				// provider performance score with perf score as primary sort
				// factor, date 2nd, and name 3rd
				if (null != duplicateList && duplicateList.size() > 0) {
					compare = com.newco.marketplace.dto.vo.serviceorder.RoutedProvider
							.getComparator(
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.SCORE_DESCENDING,
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.DATE_ASCENDING,
									com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.PROVIDER_NAME_ASCENDING);
				}
				// if there aren't any duplicates, we need to sort the list
				// based only on provider performance score
				else if (null != duplicateList && duplicateList.size() == 0) {
					compare = com.newco.marketplace.dto.vo.serviceorder.RoutedProvider
							.getComparator(com.newco.marketplace.dto.vo.serviceorder.RoutedProvider.SortParameter.SCORE_DESCENDING);
				}
			}
			try {
				Collections.sort(srcRoutedProviderList, compare);
			} catch (Exception e) {
				logger.info("Exception while sorting" + e);
			}
			List<Integer> vendorIds = new ArrayList<Integer>();
			for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider sortedList : srcRoutedProviderList) {
				if (!vendorIds.contains(sortedList.getVendorId())) {
					vendorIds.add(sortedList.getVendorId());
				}
			}
			// set the rank
			if (null != criteriaLevel && criteriaLevel.equals("FIRM")) {
				if (null != srcRoutedProviderList
						&& srcRoutedProviderList.size() > 0) {
					Integer rank = 0;
					for (Integer id : vendorIds) {
						rank = rank + 1;
						for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider pro : srcRoutedProviderList) {
							if (null != pro && null != id
									&& id.equals(pro.getVendorId())) {
								pro.setRank(rank.toString());
							}
						}
					}
				}
			} else {
				if (null != srcRoutedProviderList
						&& srcRoutedProviderList.size() > 0) {
					Integer rank = 0;
					for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider coverage : srcRoutedProviderList) {
						if (null != coverage) {
							rank = rank + 1;
							coverage.setRank(rank.toString());
						}
					}
				}
			}

		} catch (Exception e) {
			logger.info("Exception in OFServiceOrderMapper rankMembers() due to "
					+ e);
		}
		return srcRoutedProviderList;
	}

	// to get the elements with the same scores
	private List<Integer> getDuplicates(
			List<com.newco.marketplace.dto.vo.serviceorder.RoutedProvider> coveragelist,
			String criteriaLevel) {
		List<Integer> duplicateList = new ArrayList<Integer>();
		for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider list : coveragelist) {
			int count = 0;
			for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider provider : coveragelist) {
				if (null != criteriaLevel && criteriaLevel.equals("PROVIDER")) {
					if (null != list.getPerfScore()
							&& null != provider.getPerfScore()
							&& list.getPerfScore().doubleValue() == provider
									.getPerfScore().doubleValue()) {
						count = count + 1;
					}
				} else {
					if (null != list.getFirmPerfScore()
							&& null != provider.getFirmPerfScore()
							&& list.getFirmPerfScore().doubleValue() == provider
									.getFirmPerfScore().doubleValue()
							&& !list.getVendorId().equals(
									provider.getVendorId())) {
						count = count + 1;
					}
				}
			}
			if (count > 1) {
				for (com.newco.marketplace.dto.vo.serviceorder.RoutedProvider provider : coveragelist) {
					if (null != criteriaLevel
							&& criteriaLevel.equals("PROVIDER")) {
						if (null != list.getPerfScore()
								&& null != provider.getPerfScore()
								&& list.getPerfScore().doubleValue() == provider
										.getPerfScore().doubleValue()) {
							if (!duplicateList.contains(provider
									.getResourceId())) {
								duplicateList.add(provider.getResourceId());
							}
						}
					} else {
						if (null != list.getFirmPerfScore()
								&& null != provider.getFirmPerfScore()
								&& list.getFirmPerfScore().doubleValue() == provider
										.getFirmPerfScore().doubleValue()
								&& !list.getVendorId().equals(
										provider.getVendorId())) {
							if (!duplicateList.contains(provider.getVendorId())) {
								duplicateList.add(provider.getVendorId());
							}
						}
					}
				}
			}
		}
		return duplicateList;
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

	public IBuyerFeatureSetBO getFeatureSetBO() {
		return featureSetBO;
	}

	public void setFeatureSetBO(IBuyerFeatureSetBO featureSetBO) {
		this.featureSetBO = featureSetBO;
	}

}

package com.newco.marketplace.api.utils.mappers.so.v1_7;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.AttachmentType;
import com.newco.marketplace.api.beans.so.BusinessPhone;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Dimensions;
import com.newco.marketplace.api.beans.so.Estimate;
import com.newco.marketplace.api.beans.so.EstimateDetails;
import com.newco.marketplace.api.beans.so.EstimateFirmDetails;
import com.newco.marketplace.api.beans.so.EstimateHistory;
import com.newco.marketplace.api.beans.so.EstimateHistoryDetails;
import com.newco.marketplace.api.beans.so.EstimateItemsHistory;
import com.newco.marketplace.api.beans.so.EstimateLocation;
import com.newco.marketplace.api.beans.so.EstimateOtherServiceHistory;
import com.newco.marketplace.api.beans.so.EstimateOtherServicesHistory;
import com.newco.marketplace.api.beans.so.EstimatePart;
import com.newco.marketplace.api.beans.so.EstimatePartHistory;
import com.newco.marketplace.api.beans.so.EstimateParts;
import com.newco.marketplace.api.beans.so.EstimatePartsHistory;
import com.newco.marketplace.api.beans.so.EstimatePricingDetails;
import com.newco.marketplace.api.beans.so.EstimateProviderDetails;
import com.newco.marketplace.api.beans.so.Estimates;
import com.newco.marketplace.api.beans.so.FileNames;
import com.newco.marketplace.api.beans.so.FirmContact;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.History;
import com.newco.marketplace.api.beans.so.InvoiceParts;
import com.newco.marketplace.api.beans.so.JobCode;
import com.newco.marketplace.api.beans.so.JobCodes;
import com.newco.marketplace.api.beans.so.LaborTaskHistory;
import com.newco.marketplace.api.beans.so.LaborTasks;
import com.newco.marketplace.api.beans.so.LaborTasksHistory;
import com.newco.marketplace.api.beans.so.Labortask;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.LogEntry;
import com.newco.marketplace.api.beans.so.Note;
import com.newco.marketplace.api.beans.so.Notes;
import com.newco.marketplace.api.beans.so.OtherService;
import com.newco.marketplace.api.beans.so.OtherServices;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.PartsDatas;
import com.newco.marketplace.api.beans.so.PaymentDetails;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Reschedule;
import com.newco.marketplace.api.beans.so.RoutedProviders;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.ServiceOrderReview;
import com.newco.marketplace.api.beans.so.Skus;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.retrieve.TimeOnSites;
import com.newco.marketplace.api.beans.so.retrieve.v1_7.OrderStatus;
import com.newco.marketplace.api.beans.so.retrieve.v1_7.RetrieveServiceOrder;
import com.newco.marketplace.api.beans.so.retrieve.v1_7.SOGetResponse;
import com.newco.marketplace.api.beans.so.retrieve.v1_7.ServiceOrders;
import com.newco.marketplace.api.beans.so.timeonsite.SOTimeOnSiteRequest;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.onSiteVisit.IOnSiteVisitBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.EstimateHistoryTaskVO;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateLocationVO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.addons.AddOn;
import com.newco.marketplace.dto.vo.addons.AddOns;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDateTimeSlots;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo2;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.JobCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PartDatasVO;
import com.newco.marketplace.dto.vo.serviceorder.PaymentDetailsVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.SOOnsiteVisitVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.so.order.ServiceOrderRescheduleVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;

/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SORetrieveResponse Object.
 * @author ssuresh
 * version 1.7
 *
 */
/**
 * @author ssasi0
 *
 */
/**
 * @author ssasi0
 *
 */
/**
 * @author ssasi0
 *
 */
public class SORetrieveMapperV1_7 {

	private Logger logger = Logger.getLogger(SORetrieveMapperV1_7.class);

	private IServiceOrderBO serviceOrderBO;
	private IOnSiteVisitBO timeOnSiteVisitBO;

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IOnSiteVisitBO getTimeOnSiteVisitBO() {
		return timeOnSiteVisitBO;
	}

	public void setTimeOnSiteVisitBO(IOnSiteVisitBO timeOnSiteVisitBO) {
		this.timeOnSiteVisitBO = timeOnSiteVisitBO;
	}

	/**
	 * @Description:This will add the Provider Firm details if response filter
	 *                   Routed Providers is present
	 * @param response
	 * @param serviceOrder
	 * @param responseFilters
	 * @return
	 */
	public RetrieveServiceOrder mapAcceptedFirmDetails(RetrieveServiceOrder response, ServiceOrder serviceOrder) {

		if (null != serviceOrder.getFirmContact() && null != serviceOrder.getFirmContact().getVendorId()) {
			FirmContact providerFirmDetails = new FirmContact();

			com.newco.marketplace.dto.vo.provider.FirmContact firmContact = serviceOrder.getFirmContact();

			// SL-21529
			Location firmLocation = null;
			providerFirmDetails = mapProviderFirmDetails(providerFirmDetails, firmContact);
			if (null != serviceOrder.getAcceptedResource()
					&& null != serviceOrder.getAcceptedResource().getResourceId()) {
				providerFirmDetails.setResponse(PublicAPIConstant.PROVIDER_ASSIGNED);

			} else {
				providerFirmDetails.setResponse(PublicAPIConstant.PROVIDER_NOT_ASSIGNED);
			}
			// SLT-1688: FirmId should be displayed for all buyers.
			providerFirmDetails.setFirmId(firmContact.getVendorId());

			// For buyer 3333, set the accepted firmId
			if (PublicAPIConstant.RELAY_SERVICES_BUYER_ID.equals(serviceOrder.getBuyer().getBuyerId())
					|| PublicAPIConstant.TECHTALK_SERVICES_BUYER_ID.equals(serviceOrder.getBuyer().getBuyerId())) {

				// SL-21529
				if (null != firmContact.getFirmLocation()) {
					firmLocation = new Location();
					firmLocation.setAddress1(firmContact.getFirmLocation().getAddress1());
					firmLocation.setAddress2(firmContact.getFirmLocation().getAddress2());
					firmLocation.setCity(firmContact.getFirmLocation().getCity());
					firmLocation.setState(firmContact.getFirmLocation().getState());
					firmLocation.setZip(firmContact.getFirmLocation().getZip());
				}

			}
			// SL-21529
			providerFirmDetails.setFirmLocation(firmLocation);
			response.setAcceptedProviderFirmContact(providerFirmDetails);
		}
		return response;
	}

	/**
	 * @Description:mapping provider firm contact details to the response object
	 * @param providerFirmDetails
	 * @param firmContact
	 * @return
	 */
	private FirmContact mapProviderFirmDetails(FirmContact providerFirmDetails,
			com.newco.marketplace.dto.vo.provider.FirmContact firmContact) {
		providerFirmDetails.setFirmName(firmContact.getFirmName());
		/**
		 * Trimming the admin name as it is a combination of first name+ last
		 * name. leading/Trailing white space can be present in admin name
		 */
		if (StringUtils.isNotBlank(firmContact.getAdminName())) {
			providerFirmDetails.setAdminName(firmContact.getAdminName().trim());
		}
		providerFirmDetails.setEmail(firmContact.getEmail());
		/** Formatting the phone no in xxx-xxx-xxxx format */
		String primaryPhoneNo = SOPDFUtils.formatPhoneNumber(firmContact.getAdminPrimaryPhone());
		String alternatePhoneNo = SOPDFUtils.formatPhoneNumber(firmContact.getAdminAlternatePhone());
		String businessPhoneNo = SOPDFUtils.formatPhoneNumber(firmContact.getNumber());
		BusinessPhone businessPhone = new BusinessPhone();
		businessPhone.setNumber(businessPhoneNo);
		businessPhone.setExtension(firmContact.getExtension());
		providerFirmDetails.setBusinessPhone(businessPhone);
		providerFirmDetails.setAdminPrimaryPhone(primaryPhoneNo);
		providerFirmDetails.setAdminAlternatePhone(alternatePhoneNo);
		return providerFirmDetails;
	}

	/**
	 * B2C Changes
	 * 
	 * @Description:This will map Estimate Details
	 * @param response
	 * @param serviceOrder
	 * @return
	 */
	public RetrieveServiceOrder mapEstimateDetails(RetrieveServiceOrder response, ServiceOrder serviceOrder) {

		if (null != serviceOrder.getEstimateList() && !(serviceOrder.getEstimateList().isEmpty())) {

			Iterator<EstimateVO> estimateList = serviceOrder.getEstimateList().iterator();

			logger.info("inside mapEstimateDetails :::" + serviceOrder.getEstimateList().size());
			Estimates estimates = new Estimates();
			List<Estimate> estimateListResponse = new ArrayList<Estimate>();
			while (estimateList.hasNext()) {

				Estimate estimate = new Estimate();
				EstimateVO estimateVO = estimateList.next();
				if (null != estimateVO) {
					estimate.setEstimationId(estimateVO.getEstimationId());
					estimate.setEstimationRefNo(estimateVO.getEstimationRefNo());
					if (null != estimateVO.getEstimationDate()) {
						estimate.setEstimateDate(DateUtils.formatDate(estimateVO.getEstimationDate()));
					}
					if (null != estimateVO.getEstimationExpiryDate()) {
						estimate.setEstimationExpiryDate(DateUtils.formatDate(estimateVO.getEstimationExpiryDate()));
					}
					if (StringUtils.isNotBlank(estimateVO.getStatus())
							&& (estimateVO.getStatus().equals(MPConstants.ACCEPTED_STATUS)
									|| estimateVO.getStatus().equals(MPConstants.DECLINED_STATUS))) {
						Date responseDate = estimateVO.getResponseDate();
						if (null != responseDate) {
							// Convert response Date to service location
							// timezone and then to yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate = TimeUtils.convertTimeFromOneTimeZoneToOther(ts, MPConstants.SERVER_TIMEZONE,
									serviceOrder.getServiceLocationTimeZone());
							estimate.setResponseDate(DateUtils.formatDate(responseDate));
							logger.info("Accepted/Declined Date" + estimate.getResponseDate());
						}
					}
					// mapping vendor and provider details
					estimate.setFirmDetails(mapFirmDetails(estimateVO.getVendorId(), estimateVO.getFirmLocation()));
					estimate.setProviderDetails(
							mapProviderDetails(estimateVO.getResourceId(), estimateVO.getProviderLocation()));

					/*
					 * estimate.setDescription(estimateVO.getDescription());
					 * estimate.setAcceptSource(estimateVO.getAcceptSource());
					 * estimate.setTripCharge(estimateVO.getTripCharge());
					 */
					estimate.setStatus(estimateVO.getStatus());
					/*
					 * estimate.setRespondedCustomerName(estimateVO.
					 * getRespondedCustomerName());
					 * if(null!=estimateVO.getResponseDate()){
					 * estimate.setResponseDate(DateUtils.formatDate(estimateVO.
					 * getResponseDate())); }
					 */
					EstimateDetails estimateDetails = new EstimateDetails();

					LaborTasks laborTasks = new LaborTasks();
					EstimateParts estimateParts = new EstimateParts();
					OtherServices otherServices = new OtherServices();
					List<Labortask> laborTaskList = new ArrayList<Labortask>();
					List<EstimatePart> estimatePartList = new ArrayList<EstimatePart>();
					// SL-21387
					List<OtherService> otherServicelist = new ArrayList<OtherService>();

					List<EstimateTaskVO> estimateItems = estimateVO.getEstimateTasks();

					// mapping labor tasks
					if (null != estimateItems && !(estimateItems.isEmpty())) {
						logger.info("inside mapEstimateDetails :::" + estimateItems.size());

						for (EstimateTaskVO estimateTaskVO : estimateItems) {

							if (null != estimateTaskVO) {
								Labortask labortask = new Labortask();
								labortask.setItemId(estimateTaskVO.getItemId());
								labortask.setTaskSeqNo(estimateTaskVO.getTaskSeqNumber());
								labortask.setTaskName(estimateTaskVO.getTaskName());
								labortask.setDescription(estimateTaskVO.getDescription());
								// twoDecimalFormat.format(serviceOrder.getSpendLimitLabor())
								if (null != estimateTaskVO.getUnitPrice())
									labortask.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
								labortask.setQuantity(estimateTaskVO.getQuantity());
								if (null != estimateTaskVO.getTotalPrice())
									labortask.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
								labortask.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
								laborTaskList.add(labortask);
							}
						}
					}

					// mapping part items
					estimateItems = estimateVO.getEstimateParts();
					if (null != estimateItems && !(estimateItems.isEmpty())) {
						for (EstimateTaskVO estimateTaskVO : estimateItems) {

							if (null != estimateTaskVO) {
								EstimatePart estimatePart = new EstimatePart();
								estimatePart.setItemId(estimateTaskVO.getItemId());
								estimatePart.setPartSeqNo(estimateTaskVO.getPartSeqNumber());
								estimatePart.setPartNo(estimateTaskVO.getPartNumber());
								estimatePart.setPartName(estimateTaskVO.getPartName());
								estimatePart.setDescription(estimateTaskVO.getDescription());
								if (null != estimateTaskVO.getUnitPrice())
									estimatePart.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
								estimatePart.setQuantity(estimateTaskVO.getQuantity());
								if (null != estimateTaskVO.getTotalPrice())
									estimatePart.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
								estimatePart.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
								estimatePartList.add(estimatePart);
							}
						}
					}

					// mapping other estimate services items
					estimateItems = estimateVO.getEstimateOtherEstimateServices();
					if (null != estimateItems && !(estimateItems.isEmpty())) {
						for (EstimateTaskVO estimateTaskVO : estimateItems) {
							if (null != estimateTaskVO) {
								OtherService otherService = new OtherService();
								otherService.setItemId(estimateTaskVO.getItemId());
								otherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
								otherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
								otherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
								otherService.setDescription(estimateTaskVO.getDescription());
								if (null != estimateTaskVO.getUnitPrice())
									otherService.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
								otherService.setQuantity(estimateTaskVO.getQuantity());
								if (null != estimateTaskVO.getTotalPrice())
									otherService.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
								otherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
								otherServicelist.add(otherService);
							}
						}
					}

					if (null != laborTaskList && !(laborTaskList.isEmpty())) {
						laborTasks.setLabortask(laborTaskList);
						estimateDetails.setLaborTasks(laborTasks);
					}

					if (null != estimatePartList && !(estimatePartList.isEmpty())) {
						estimateParts.setPart(estimatePartList);
						estimateDetails.setParts(estimateParts);
					}
					// SL-21387
					if (null != otherServicelist && !(otherServicelist.isEmpty())) {
						otherServices.setOtherService(otherServicelist);
						estimateDetails.setOtherServices(otherServices);
					}

					if (null != estimateDetails && (null != estimateDetails.getLaborTasks()
							|| null != estimateDetails.getParts() || null != estimateDetails.getOtherServices())) {
						estimate.setEstimateDetails(estimateDetails);
					}
					EstimatePricingDetails estimatePricingDetails = new EstimatePricingDetails();
					Double subTotal = 0.0d;
					if (null != estimateVO.getTotalLaborPrice())
						estimatePricingDetails
								.setTotalLaborPrice(String.format("%.2f", estimateVO.getTotalLaborPrice()));
					if (null != estimateVO.getTotalPartsPrice())
						estimatePricingDetails
								.setTotalPartsPrice(String.format("%.2f", estimateVO.getTotalPartsPrice()));
					// SL-21387
					if (null != estimateVO.getTotalOtherServicePrice())
						estimatePricingDetails.setTotalOtherServicePrice(
								String.format("%.2f", estimateVO.getTotalOtherServicePrice()));
					DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
					if (null != estimateVO.getTotalLaborPrice()) {
						subTotal = subTotal + estimateVO.getTotalLaborPrice().doubleValue();
					}
					if (null != estimateVO.getTotalPartsPrice()) {
						subTotal = subTotal + estimateVO.getTotalPartsPrice().doubleValue();
					}
					logger.info("subtotal before formatting:" + subTotal);
					if (null != subTotal) {
						estimatePricingDetails.setSubTotal(String.format("%.2f", subTotal));
					}
					// SL-21387
					if (null != estimateVO.getTotalOtherServicePrice()) {
						subTotal = subTotal + estimateVO.getTotalOtherServicePrice().doubleValue();
					}
					logger.info("subtotal after formatting:" + subTotal);
					estimatePricingDetails.setDiscountType(estimateVO.getDiscountType());
					if (null != estimateVO.getDiscountedPercentage()) {
						estimatePricingDetails
								.setDiscountedPercentage(String.format("%.2f", estimateVO.getDiscountedPercentage()));
					}
					if (null != estimateVO.getDiscountedAmount()) {
						estimatePricingDetails
								.setDiscountedAmount(String.format("%.2f", estimateVO.getDiscountedAmount()));
					}
					if (null != estimateVO.getTaxRate()) {
						estimatePricingDetails.setTaxRate(String.format("%.2f", estimateVO.getTaxRate()));
					}
					estimatePricingDetails.setTaxType(estimateVO.getTaxType());
					if (null != estimateVO.getTaxPrice()) {
						estimatePricingDetails.setTaxPrice(String.format("%.2f", estimateVO.getTaxPrice()));
					}
					if (null != estimateVO.getTotalPrice()) {
						estimatePricingDetails.setTotalPrice(String.format("%.2f", estimateVO.getTotalPrice()));
					}

					// SL-21939

					estimatePricingDetails.setLaborDiscountType(estimateVO.getLaborDiscountType());
					estimatePricingDetails.setPartsDiscountType(estimateVO.getPartsDiscountType());

					if (null != estimateVO.getLaborDiscountedPercentage()) {
						estimatePricingDetails.setLaborDiscountedPercentage(
								String.format("%.2f", estimateVO.getLaborDiscountedPercentage()));

					}
					if (null != estimateVO.getLaborDiscountedAmount()) {
						estimatePricingDetails
								.setLaborDiscountedAmount(String.format("%.2f", estimateVO.getLaborDiscountedAmount()));
					}
					if (null != estimateVO.getLaborTaxRate()) {
						estimatePricingDetails.setLaborTaxRate(String.format("%.2f", estimateVO.getLaborTaxRate()));
					}
					if (null != estimateVO.getLaborTaxPrice()) {
						estimatePricingDetails.setLaborTaxPrice(String.format("%.2f", estimateVO.getLaborTaxPrice()));
					}

					if (null != estimateVO.getPartsDiscountedPercentage()) {
						estimatePricingDetails.setPartsDiscountedPercentage(
								String.format("%.2f", estimateVO.getPartsDiscountedPercentage()));
					}
					if (null != estimateVO.getPartsDiscountedAmount()) {
						estimatePricingDetails
								.setPartsDiscountedAmount(String.format("%.2f", estimateVO.getPartsDiscountedAmount()));
					}
					if (null != estimateVO.getPartsTaxRate()) {
						estimatePricingDetails.setPartsTaxRate(String.format("%.2f", estimateVO.getPartsTaxRate()));
					}
					if (null != estimateVO.getPartsTaxPrice()) {
						estimatePricingDetails.setPartsTaxPrice(String.format("%.2f", estimateVO.getPartsTaxPrice()));
					}

					estimate.setPricing(estimatePricingDetails);
					estimate.setComments(estimateVO.getComments());
					estimate.setLogoDocumentId(estimateVO.getLogoDocumentId());
					// mapping history details for the estimate
					estimate.setEstimateHistoryDetails(mapHistoryDetails(estimateVO.getEstimateHistoryList(),
							estimateVO.getEstimateTasksHistory(), estimateVO.getEstimatePartsHistory(),
							estimateVO.getEstimateOtherServicesHistory(), serviceOrder.getServiceLocationTimeZone()));

					estimateListResponse.add(estimate);
				}
			}
			estimates.setEstimates(estimateListResponse);

			response.setEstimateFlag(true);
			response.setEstimates(estimates);
		} else {
			response.setEstimateFlag(false);
		}
		return response;
	}

	/**
	 * mapping provider details
	 * 
	 * @param resourceId
	 * @param providerLocation
	 * @return
	 */
	private EstimateProviderDetails mapProviderDetails(Integer resourceId, EstimateLocationVO providerLocation) {
		EstimateProviderDetails estimateProviderDetails = null;
		if (null != resourceId || null != providerLocation) {
			estimateProviderDetails = new EstimateProviderDetails();
			estimateProviderDetails.setResourceId(resourceId);
			estimateProviderDetails.setLocation(mapLocationDetails(providerLocation));
		}
		return estimateProviderDetails;
	}

	/**
	 * mapping vendor details
	 * 
	 * @param vendorId
	 * @param firmLocation
	 * @return
	 */
	private EstimateFirmDetails mapFirmDetails(Integer vendorId, EstimateLocationVO firmLocation) {
		EstimateFirmDetails estimateFirmDetails = null;
		if (null != vendorId || null != firmLocation) {
			estimateFirmDetails = new EstimateFirmDetails();
			estimateFirmDetails.setVendorId(vendorId);
			estimateFirmDetails.setLocation(mapLocationDetails(firmLocation));
		}
		return estimateFirmDetails;
	}

	/**
	 * mapping location details of vendor and provider
	 * 
	 * @param location
	 * @return
	 */
	private EstimateLocation mapLocationDetails(EstimateLocationVO location) {
		EstimateLocation estimateLocation = null;
		if (null != location) {
			estimateLocation = new EstimateLocation();
			estimateLocation.setAddress1(location.getStreet_1());
			estimateLocation.setAddress2(location.getStreet_2());
			estimateLocation.setCity(location.getCity());
			estimateLocation.setState(location.getState_cd());
			estimateLocation.setZip(location.getZip());
			if (StringUtils.isNotBlank(location.getZip4())) {
				estimateLocation.setZip4(location.getZip4());
			}
		}
		return estimateLocation;
	}

	/**
	 * mapping review details
	 * 
	 * @param response
	 * @param serviceOrder
	 * @return
	 */
	public RetrieveServiceOrder mapReviewDetails(RetrieveServiceOrder response, List<ReviewVO> reviewVOList) {
		ServiceOrderReview review = null;
		if (null != reviewVOList && !reviewVOList.isEmpty()) {
			review = new ServiceOrderReview();
			for (ReviewVO reviewVO : reviewVOList) {
				if (null != reviewVO) {
					if (reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.CLEANLINESS)) {
						review.setCleanliness(reviewVO.getScore());
					} else if (reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.TIMELINESS)) {
						review.setTimeliness(reviewVO.getScore());
					} else if (reviewVO.getQuestion()
							.equalsIgnoreCase(PublicAPIConstant.ProviderReviews.COMMUNICATION)) {
						review.setCommunication(reviewVO.getScore());
					} else if (reviewVO.getQuestion()
							.equalsIgnoreCase(PublicAPIConstant.ProviderReviews.PROFESSIONALISM)) {
						review.setProfessionalism(reviewVO.getScore());
					} else if (reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.QUALITY)) {
						review.setQuality(reviewVO.getScore());
					} else if (reviewVO.getQuestion().equalsIgnoreCase(PublicAPIConstant.ProviderReviews.VALUE)) {
						review.setValue(reviewVO.getScore());
					}
				}
			}
			ReviewVO reviewElement = reviewVOList.get(PublicAPIConstant.INTEGER_ZERO);
			if (null != reviewElement) {
				DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
				if (null != reviewElement.getReviewRatingObj()) {
					review.setOverallRating(df.format(reviewElement.getReviewRatingObj()));
				}
				review.setComment(reviewElement.getReviewComment());
				if (null != reviewElement.getReviewDateObj()) {
					review.setDate(DateUtils.formatDate(reviewElement.getReviewDateObj()));
				}
			}
		}
		response.setReview(review);
		return response;
	}

	/**
	 * mapping estimate history details
	 * 
	 * @param estimateHistoryList
	 * @param estimatePartList
	 * @param estimateTaskList
	 * @param otherServicelist
	 * @param timezone
	 * @return
	 */
	private EstimateHistoryDetails mapHistoryDetails(List<EstimateHistoryVO> estimateHistoryList,
			List<EstimateHistoryTaskVO> estimateTaskList, List<EstimateHistoryTaskVO> estimatePartList,
			List<EstimateHistoryTaskVO> estimateOtherServiceList, String timezone) {

		Map<Integer, List<EstimateHistoryTaskVO>> laborTaskMap = new HashMap<Integer, List<EstimateHistoryTaskVO>>();
		Map<Integer, List<EstimateHistoryTaskVO>> partTaskMap = new HashMap<Integer, List<EstimateHistoryTaskVO>>();
		Map<Integer, List<EstimateHistoryTaskVO>> otherTaskMap = new HashMap<Integer, List<EstimateHistoryTaskVO>>();

		if (null != estimateTaskList && estimateTaskList.size() > 0) {
			for (EstimateHistoryTaskVO estimateHistoryTaskVO : estimateTaskList) {
				if (laborTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())) {
					List<EstimateHistoryTaskVO> history = laborTaskMap
							.get(estimateHistoryTaskVO.getEstimationHistoryId());
					history.add(estimateHistoryTaskVO);
					laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);
				} else {
					List<EstimateHistoryTaskVO> history = new ArrayList<EstimateHistoryTaskVO>();
					history.add(estimateHistoryTaskVO);
					laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

				}
			}
		}

		if (null != estimatePartList && estimatePartList.size() > 0) {
			for (EstimateHistoryTaskVO estimateHistoryTaskVO : estimatePartList) {
				if (partTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())) {
					List<EstimateHistoryTaskVO> history = partTaskMap
							.get(estimateHistoryTaskVO.getEstimationHistoryId());
					history.add(estimateHistoryTaskVO);
					partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

				} else {
					List<EstimateHistoryTaskVO> history = new ArrayList<EstimateHistoryTaskVO>();
					history.add(estimateHistoryTaskVO);
					partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

				}
			}
		}

		if (null != estimateOtherServiceList && estimateOtherServiceList.size() > 0) {
			for (EstimateHistoryTaskVO estimateHistoryTaskVO : estimateOtherServiceList) {
				if (otherTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())) {
					List<EstimateHistoryTaskVO> history = otherTaskMap
							.get(estimateHistoryTaskVO.getEstimationHistoryId());
					history.add(estimateHistoryTaskVO);
					otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

				} else {
					List<EstimateHistoryTaskVO> history = new ArrayList<EstimateHistoryTaskVO>();
					history.add(estimateHistoryTaskVO);
					otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

				}
			}
		}

		EstimateHistoryDetails estimateHistoryDetails = null;
		if (null != estimateHistoryList && !estimateHistoryList.isEmpty()) {
			estimateHistoryDetails = new EstimateHistoryDetails();
			List<EstimateHistory> historyList = new ArrayList<EstimateHistory>();
			for (EstimateHistoryVO estimateHistoryVO : estimateHistoryList) {
				if (null != estimateHistoryVO) {
					EstimateHistory estimateHistory = new EstimateHistory();
					estimateHistory.setEstimationHistoryId(estimateHistoryVO.getEstimationHistoryId());
					estimateHistory.setEstimationId(estimateHistoryVO.getEstimationId());
					estimateHistory.setEstimationRefNo(estimateHistoryVO.getEstimationRefNo());
					if (null != estimateHistoryVO.getEstimationDate()) {
						estimateHistory.setEstimateDate(DateUtils.formatDate(estimateHistoryVO.getEstimationDate()));
					}
					if (StringUtils.isNotBlank(estimateHistoryVO.getStatus())
							&& (estimateHistoryVO.getStatus().equals(MPConstants.ACCEPTED_STATUS)
									|| estimateHistoryVO.getStatus().equals(MPConstants.DECLINED_STATUS))) {
						Date responseDate = estimateHistoryVO.getResponseDate();
						if (null != responseDate) {
							// Convert response Date to service location
							// timezone and then to yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate = TimeUtils.convertTimeFromOneTimeZoneToOther(ts, MPConstants.SERVER_TIMEZONE,
									timezone);
							estimateHistory.setResponseDate(DateUtils.formatDate(responseDate));
							logger.info("Accepted/Declined Date" + estimateHistory.getResponseDate());
						}
					}

					if (null != estimateHistoryVO.getEstimationExpiryDate()) {
						estimateHistory.setEstimationExpiryDate(
								DateUtils.formatDate(estimateHistoryVO.getEstimationExpiryDate()));
					}

					// mapping vendor and provider details
					estimateHistory.setFirmDetails(
							mapFirmDetails(estimateHistoryVO.getVendorId(), estimateHistoryVO.getFirmLocation()));
					estimateHistory.setProviderDetails(mapProviderDetails(estimateHistoryVO.getResourceId(),
							estimateHistoryVO.getProviderLocation()));

					estimateHistory.setStatus(estimateHistoryVO.getStatus());

					// mapping pricing details
					estimateHistory.setPricing(mapPricingDetails(estimateHistoryVO));

					estimateHistory.setComments(estimateHistoryVO.getComments());
					estimateHistory.setLogoDocumentId(estimateHistoryVO.getLogoDocumentId());
					estimateHistory.setAction(estimateHistoryVO.getAction());

					mapItemsHistory(laborTaskMap.get(estimateHistory.getEstimationHistoryId()),
							partTaskMap.get(estimateHistory.getEstimationHistoryId()),
							otherTaskMap.get(estimateHistory.getEstimationHistoryId()), estimateHistory);

					historyList.add(estimateHistory);
				}
			}
			estimateHistoryDetails.setEstimateHistory(historyList);
			// mapping Item details
			// estimateHistoryDetails.setEstimateItemsHistory(mapItemsHistory(estimateTaskList,estimatePartList));
		}

		return estimateHistoryDetails;
	}

	/**
	 * mapping pricing details
	 * 
	 * @param estimateHistoryVO
	 * @return
	 */
	private EstimatePricingDetails mapPricingDetails(EstimateHistoryVO estimateHistoryVO) {
		EstimatePricingDetails estimatePricingDetails = new EstimatePricingDetails();
		Double subTotal = 0.0d;
		if (null != estimateHistoryVO.getTotalLaborPrice())
			estimatePricingDetails.setTotalLaborPrice(String.format("%.2f", estimateHistoryVO.getTotalLaborPrice()));
		if (null != estimateHistoryVO.getTotalPartsPrice())
			estimatePricingDetails.setTotalPartsPrice(String.format("%.2f", estimateHistoryVO.getTotalPartsPrice()));
		// SL-21387
		if (null != estimateHistoryVO.getTotalOtherServicePrice())
			estimatePricingDetails
					.setTotalOtherServicePrice(String.format("%.2f", estimateHistoryVO.getTotalOtherServicePrice()));

		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
		if (null != estimateHistoryVO.getTotalLaborPrice()) {
			subTotal = subTotal + estimateHistoryVO.getTotalLaborPrice().doubleValue();
		}
		if (null != estimateHistoryVO.getTotalPartsPrice()) {
			subTotal = subTotal + estimateHistoryVO.getTotalPartsPrice().doubleValue();
		}
		// SL-21387
		if (null != estimateHistoryVO.getTotalOtherServicePrice()) {
			subTotal = subTotal + estimateHistoryVO.getTotalOtherServicePrice().doubleValue();
		}

		if (null != subTotal) {
			estimatePricingDetails.setSubTotal(String.format("%.2f", subTotal));
		}
		estimatePricingDetails.setDiscountType(estimateHistoryVO.getDiscountType());
		if (null != estimateHistoryVO.getDiscountedPercentage())
			estimatePricingDetails
					.setDiscountedPercentage(String.format("%.2f", estimateHistoryVO.getDiscountedPercentage()));
		if (null != estimateHistoryVO.getDiscountedAmount())
			estimatePricingDetails.setDiscountedAmount(String.format("%.2f", estimateHistoryVO.getDiscountedAmount()));
		if (null != estimateHistoryVO.getTaxRate())
			estimatePricingDetails.setTaxRate(String.format("%.2f", estimateHistoryVO.getTaxRate()));
		estimatePricingDetails.setTaxType(estimateHistoryVO.getTaxType());
		if (null != estimateHistoryVO.getTaxPrice())
			estimatePricingDetails.setTaxPrice(String.format("%.2f", estimateHistoryVO.getTaxPrice()));
		if (null != estimateHistoryVO.getTotalPrice())
			estimatePricingDetails.setTotalPrice(String.format("%.2f", estimateHistoryVO.getTotalPrice()));
		// SL-21939

		estimatePricingDetails.setLaborDiscountType(estimateHistoryVO.getLaborDiscountType());
		estimatePricingDetails.setPartsDiscountType(estimateHistoryVO.getPartsDiscountType());

		if (null != estimateHistoryVO.getLaborDiscountedPercentage()) {
			estimatePricingDetails.setLaborDiscountedPercentage(
					String.format("%.2f", estimateHistoryVO.getLaborDiscountedPercentage()));

		}
		if (null != estimateHistoryVO.getLaborDiscountedAmount()) {
			estimatePricingDetails
					.setLaborDiscountedAmount(String.format("%.2f", estimateHistoryVO.getLaborDiscountedAmount()));
		}
		if (null != estimateHistoryVO.getLaborTaxRate()) {
			estimatePricingDetails.setLaborTaxRate(String.format("%.2f", estimateHistoryVO.getLaborTaxRate()));
		}
		if (null != estimateHistoryVO.getLaborTaxPrice()) {
			estimatePricingDetails.setLaborTaxPrice(String.format("%.2f", estimateHistoryVO.getLaborTaxPrice()));
		}

		if (null != estimateHistoryVO.getPartsDiscountedPercentage()) {
			estimatePricingDetails.setPartsDiscountedPercentage(
					String.format("%.2f", estimateHistoryVO.getPartsDiscountedPercentage()));
		}
		if (null != estimateHistoryVO.getPartsDiscountedAmount()) {
			estimatePricingDetails
					.setPartsDiscountedAmount(String.format("%.2f", estimateHistoryVO.getPartsDiscountedAmount()));
		}
		if (null != estimateHistoryVO.getPartsTaxRate()) {
			estimatePricingDetails.setPartsTaxRate(String.format("%.2f", estimateHistoryVO.getPartsTaxRate()));
		}
		if (null != estimateHistoryVO.getPartsTaxPrice()) {
			estimatePricingDetails.setPartsTaxPrice(String.format("%.2f", estimateHistoryVO.getPartsTaxPrice()));
		}

		return estimatePricingDetails;
	}

	/**
	 * mapping labor and part items
	 * 
	 * @param estimateTaskList
	 * @param estimatePartList
	 * @return
	 */
	private void mapItemsHistory(List<EstimateHistoryTaskVO> estimateTaskList,
			List<EstimateHistoryTaskVO> estimatePartList, List<EstimateHistoryTaskVO> otherServicelist,
			EstimateHistory estimateHistory) {
		EstimateItemsHistory estimateItems = null;
		List<LaborTaskHistory> laborTaskList = null;
		List<EstimatePartHistory> partList = null;
		List<EstimateOtherServiceHistory> estimateOtherServiceHistoryList = null;

		if ((null != estimateTaskList && !estimateTaskList.isEmpty())
				|| (null != estimatePartList && !estimatePartList.isEmpty())) {
			estimateItems = new EstimateItemsHistory();
			// mapping labor tasks
			if (null != estimateTaskList && !(estimateTaskList.isEmpty())) {
				laborTaskList = new ArrayList<LaborTaskHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : estimateTaskList) {

					if (null != estimateTaskVO) {
						LaborTaskHistory labortask = new LaborTaskHistory();
						labortask.setItemId(estimateTaskVO.getItemId());
						labortask.setTaskSeqNo(estimateTaskVO.getTaskSeqNumber());
						labortask.setTaskName(estimateTaskVO.getTaskName());
						labortask.setDescription(estimateTaskVO.getDescription());
						if (null != estimateTaskVO.getUnitPrice())
							labortask.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
						labortask.setQuantity(estimateTaskVO.getQuantity());
						if (null != estimateTaskVO.getTotalPrice())
							labortask.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
						labortask.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						labortask.setAction(estimateTaskVO.getAction());
						laborTaskList.add(labortask);
					}
				}
			}

			// mapping part items
			if (null != estimatePartList && !(estimatePartList.isEmpty())) {
				partList = new ArrayList<EstimatePartHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : estimatePartList) {

					if (null != estimateTaskVO) {
						EstimatePartHistory estimatePart = new EstimatePartHistory();
						estimatePart.setItemId(estimateTaskVO.getItemId());
						estimatePart.setPartSeqNo(estimateTaskVO.getPartSeqNumber());
						estimatePart.setPartNo(estimateTaskVO.getPartNumber());
						estimatePart.setPartName(estimateTaskVO.getPartName());
						estimatePart.setDescription(estimateTaskVO.getDescription());
						if (null != estimateTaskVO.getUnitPrice()) {
							estimatePart.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
						}
						estimatePart.setQuantity(estimateTaskVO.getQuantity());
						if (null != estimateTaskVO.getTotalPrice()) {
							estimatePart.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
						}
						estimatePart.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						estimatePart.setAction(estimateTaskVO.getAction());
						partList.add(estimatePart);
					}
				}
			}
			// SL-21387 mapping other estimate service details
			if (null != otherServicelist && !(otherServicelist.isEmpty())) {
				estimateOtherServiceHistoryList = new ArrayList<EstimateOtherServiceHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : otherServicelist) {

					if (null != estimateTaskVO) {
						EstimateOtherServiceHistory estimateOtherService = new EstimateOtherServiceHistory();
						estimateOtherService.setItemId(estimateTaskVO.getItemId());
						estimateOtherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
						estimateOtherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
						estimateOtherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
						estimateOtherService.setDescription(estimateTaskVO.getDescription());
						if (null != estimateTaskVO.getUnitPrice()) {
							estimateOtherService.setUnitPrice(String.format("%.2f", estimateTaskVO.getUnitPrice()));
						}
						estimateOtherService.setQuantity(estimateTaskVO.getQuantity());
						if (null != estimateTaskVO.getTotalPrice()) {
							estimateOtherService.setTotalPrice(String.format("%.2f", estimateTaskVO.getTotalPrice()));
						}
						estimateOtherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						estimateOtherService.setAction(estimateTaskVO.getAction());
						estimateOtherServiceHistoryList.add(estimateOtherService);
					}
				}
			}
			if (null != laborTaskList && !(laborTaskList.isEmpty())) {
				LaborTasksHistory laborTasks = new LaborTasksHistory();
				laborTasks.setLabortask(laborTaskList);
				estimateItems.setLaborTasks(laborTasks);
			}

			if (null != estimatePartList && !(estimatePartList.isEmpty())) {
				EstimatePartsHistory estimateParts = new EstimatePartsHistory();
				estimateParts.setPart(partList);
				estimateItems.setParts(estimateParts);
				// SL-21387
				if (null != estimateOtherServiceHistoryList && !(estimateOtherServiceHistoryList.isEmpty())) {
					EstimateOtherServicesHistory estimateOtherServices = new EstimateOtherServicesHistory();
					estimateOtherServices.setOtherService(estimateOtherServiceHistoryList);
					estimateItems.setOtherServices(estimateOtherServices);
				}
			}
			estimateHistory.setEstimateItemsHistory(estimateItems);
		}
	}

	public RetrieveServiceOrder adaptRequest(ServiceOrder serviceOrder, List<String> responseFilter)
			throws DataServiceException {

		Integer rescheduleType = null;
		Integer scheduleType = null;
		ScheduleServiceSlot scheduleServiceSlot = null;
		RetrieveServiceOrder retrieveSOResponse = new RetrieveServiceOrder();
		// Results results = new Results();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		OrderStatus orderStatus = new OrderStatus();
		GeneralSection sectionGeneral = new GeneralSection();
		Location serviceLocation = new Location();
		Schedule schedule = new Schedule();
		ScopeOfWork scopeOfWork = new ScopeOfWork();
		Pricing pricing = new Pricing();
		Contacts contacts = new Contacts();
		AttachmentType attachments = new AttachmentType();
		Parts parts = new Parts();
		CustomReferences customReferences = new CustomReferences();
		Notes notes = new Notes();
		History history = new History();
		RoutedProviders routedProviders = new RoutedProviders();
		String timeZone = serviceOrder.getServiceLocationTimeZone();
		JobCodes jobCodes = new JobCodes();
		PaymentDetails paymentDetails = new PaymentDetails();
		InvoiceParts invoiceParts = new InvoiceParts();

		// Date date = null;
		// try {
		// date = (java.util.Date) TimeUtils.combineDateTime(
		// serviceOrder.getServiceDate1(), serviceOrder
		// .getServiceTimeStart());
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		/*
		 * TimeZone timezone1 =
		 * TimeZone.getTimeZone(TimeUtils.getStandardTimezone(timeZone));;
		 */
		TimeZone timezone1 = TimeZone.getTimeZone(timeZone);
		// date format for converting date to string.

		// For mapping order status Section

		mapOrderStatus(serviceOrder, orderStatus);
		if (null != responseFilter) {
			// For mapping General Section
			if (responseFilter.contains(PublicAPIConstant.GENERAL)) {
				mapGeneralSection(serviceOrder, sectionGeneral);
				retrieveSOResponse.setSectionGeneral(sectionGeneral);
			}

			// For mapping Scope Of Work
			if (responseFilter.contains(PublicAPIConstant.SCOPEOFWORK)) {
				mapScopeOfWork(serviceOrder, scopeOfWork);
				retrieveSOResponse.setScopeOfWork(scopeOfWork);
			}

			// For mapping ServiceLocation
			if (responseFilter.contains(PublicAPIConstant.SERVICELOCATION)) {
				mapServiceLocation(serviceOrder, serviceLocation);
				retrieveSOResponse.setServiceLocation(serviceLocation);
			}

			// For mapping Schedule Details
			if (responseFilter.contains(PublicAPIConstant.SCHEDULE)) {
				mapServiceOrderSchedule(serviceOrder, schedule, timezone1);
				retrieveSOResponse.setSchedule(schedule);
			}

			// For mapping Pricing Details
			if (responseFilter.contains(PublicAPIConstant.PRICING)) {
				mapServiceOrderPricing(serviceOrder, pricing);
				retrieveSOResponse.setPricing(pricing);
			}

			// For mapping Contact Details
			if (responseFilter.contains(PublicAPIConstant.CONTACTS)) {
				mapServiceOrderContacts(serviceOrder, contacts);
				retrieveSOResponse.setContacts(contacts);
			}

			// For mapping Attachment Details
			if (responseFilter.contains(PublicAPIConstant.ATTACHMENTS)) {
				mapServiceOrderDocuments(serviceOrder, attachments, retrieveSOResponse);

			}

			// For mapping Parts Details
			if (responseFilter.contains(PublicAPIConstant.PARTS)) {
				mapServiceOrderParts(serviceOrder, parts, retrieveSOResponse);

			}

			// For mapping Custom References
			if (responseFilter.contains(PublicAPIConstant.CUSTOMREFERENCES)) {
				mapServiceOrderCustomRef(serviceOrder, customReferences, retrieveSOResponse);

			}

			// For mapping Notes
			if (responseFilter.contains(PublicAPIConstant.NOTES)) {
				mapServiceOrderNotes(serviceOrder, notes, retrieveSOResponse);

			}

			// For mapping History
			if (responseFilter.contains(PublicAPIConstant.HISTORY)) {
				mapServiceOrderHistory(serviceOrder, history, retrieveSOResponse);

			}

			// For mapping Routed Resources
			if (responseFilter.contains(PublicAPIConstant.ROUTEDPROVIDERS)) {
				mapServiceOrderRoutedResources(serviceOrder, routedProviders, retrieveSOResponse);

			}

			// For mapping JobCode
			if (responseFilter.contains(PublicAPIConstant.JOBCODES)) {
				mapServiceOrderJobCodes(serviceOrder, jobCodes, retrieveSOResponse);

			}

			// For mapping PaymentDetais
			if (responseFilter.contains(PublicAPIConstant.PAYMENTDETAILS)) {
				mapServiceOrderPaymentDetails(serviceOrder, paymentDetails, retrieveSOResponse);

			}

			// For mapping InvoiceParts
			if (responseFilter.contains(PublicAPIConstant.INVOICEPARTS)) {
				mapServiceOrderInvoiceParts(serviceOrder, invoiceParts, retrieveSOResponse);

			}
		}

		Results results = Results.getSuccess(ResultsCode.RETRIEVE_RESULT_CODE.getMessage());
		retrieveSOResponse.setResults(results);
		retrieveSOResponse.setOrderstatus(orderStatus);

		// SLT-1038 Added check for scheduleType before setting scheduleTimeSlot
		// tag.
		scheduleType = serviceOrder.getServiceDateTypeId();
		if (scheduleType != null && scheduleType.equals(3)) {
			if (responseFilter.contains(PublicAPIConstant.SCHEDULE) && null != serviceOrder.getServiceDatetimeSlots()
					&& serviceOrder.getServiceDatetimeSlots().size() > 0) {
				scheduleServiceSlot = new ScheduleServiceSlot();
				mapServiceOrderTimeSlots(serviceOrder, scheduleServiceSlot, timezone1);
				retrieveSOResponse.setScheduleServiceSlot(scheduleServiceSlot);
			}
		}
		return retrieveSOResponse;
	}

	/**
	 * This method is for mapping the RoutedProvider Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param routedProviders
	 *            RoutedProviders
	 * @return void
	 */
	private void mapServiceOrderRoutedResources(ServiceOrder serviceOrder, RoutedProviders routedProviders,
			RetrieveServiceOrder retrieveSOResponse) {
		Timestamp expireDateTimeCombined = null;

		logger.info("Mapping: Customer Reference --->Starts");
		List<com.newco.marketplace.api.beans.so.RoutedProvider> routedProList = new ArrayList<com.newco.marketplace.api.beans.so.RoutedProvider>();

		if (null != serviceOrder.getRoutedResources() && (!serviceOrder.getRoutedResources().isEmpty())) {
			Iterator<RoutedProvider> soRouteList = serviceOrder.getRoutedResources().iterator();
			while (soRouteList.hasNext()) {
				Pricing pricing = new Pricing();
				Schedule schedule = new Schedule();
				com.newco.marketplace.api.beans.so.RoutedProvider routedProvider = new com.newco.marketplace.api.beans.so.RoutedProvider();
				RoutedProvider soRoutedProvider = soRouteList.next();
				routedProvider.setResourceId(
						(null != soRoutedProvider.getResourceId()) ? soRoutedProvider.getResourceId() : new Integer(0));
				routedProvider.setCompanyID(
						(null != soRoutedProvider.getVendorId()) ? soRoutedProvider.getVendorId() : new Integer(0));

				if (null != soRoutedProvider.getCreatedDate()) {
					routedProvider.setCreatedDate(CommonUtility.sdfToDate.format(soRoutedProvider.getCreatedDate()));
				}
				routedProvider.setResponse((StringUtils.isEmpty(soRoutedProvider.getProviderRespDescription())) ? null
						: soRoutedProvider.getProviderRespDescription());
				if (null != routedProvider.getResponse()
						&& routedProvider.getResponse().equalsIgnoreCase(PublicAPIConstant.ACCEPTED)) {

					if (null != serviceOrder.getAcceptedResource()
							&& null != serviceOrder.getAcceptedResource().getResourceContact()) {
						com.newco.marketplace.api.beans.so.Contact providerContact = new com.newco.marketplace.api.beans.so.Contact();
						providerContact = mapContactDetails(serviceOrder.getAcceptedResource().getResourceContact(),
								null, PublicAPIConstant.PROVIDER);
						routedProvider.setProviderContact(providerContact);
					}

				}
				routedProvider.setComment(StringUtils.isEmpty(soRoutedProvider.getProviderRespComment()) ? null
						: soRoutedProvider.getProviderRespComment());
				if (null != soRoutedProvider.getConditionalExpirationDate()) {
					if (null != soRoutedProvider.getConditionalExpirationTime()) {
						expireDateTimeCombined = new Timestamp(
								TimeUtils.combineDateAndTime(soRoutedProvider.getConditionalExpirationDate(),
										soRoutedProvider.getConditionalExpirationTime(),
										serviceOrder.getServiceLocationTimeZone()).getTime());
					} else {
						expireDateTimeCombined = soRoutedProvider.getConditionalExpirationDate();
					}
					routedProvider.setOfferExpiration(CommonUtility.sdfToDate.format(expireDateTimeCombined));
				}
				getSpendDetails(pricing, soRoutedProvider, routedProvider);
				getScheduleDetails(schedule, soRoutedProvider, serviceOrder.getServiceLocationTimeZone(),
						routedProvider);

				routedProList.add(routedProvider);
			}
			routedProviders.setRoutedProviders(routedProList);
			retrieveSOResponse.setRoutedProviders(routedProviders);
		}

	}

	/**
	 * This method is for getting the spend details for Routed Resource.
	 *
	 * @param pricing
	 *            Pricing
	 * @param routedProvider
	 *            RoutedProvider
	 * @return void
	 */
	private void getSpendDetails(Pricing pricing, RoutedProvider soRoutedProvider,
			com.newco.marketplace.api.beans.so.RoutedProvider routedProvider) {

		if (null != soRoutedProvider.getConditionalSpendLimit()) {
			pricing.setLaborSpendLimit(soRoutedProvider.getConditionalSpendLimit().toString());
			pricing.setPartsSpendLimit("0.0");
			routedProvider.setIncreaseSpend(pricing);
		}

	}

	/**
	 * This method is for getting the Schedule details for Routed Resource.
	 *
	 * @param schedule
	 *            Schedule
	 * @param routedProvider
	 *            RoutedProvider
	 * @param timezone
	 *            String
	 * @return void
	 */
	private void getScheduleDetails(Schedule schedule, RoutedProvider soRoutedProvider, String timezone,
			com.newco.marketplace.api.beans.so.RoutedProvider routedProvider) {
		Timestamp startDateTimeCombined = null;
		Timestamp endDateTimeCombined = null;
		if (null != soRoutedProvider.getConditionalChangeDate1()) {
			startDateTimeCombined = new Timestamp(
					TimeUtils.combineDateAndTime(soRoutedProvider.getConditionalChangeDate1(),
							soRoutedProvider.getConditionalStartTime(), timezone).getTime());
			schedule.setServiceDateTime1(CommonUtility.sdfToDate.format(startDateTimeCombined));
			schedule.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);
			routedProvider.setRescheduleDates(schedule);
		}
		if (null != soRoutedProvider.getConditionalChangeDate2()) {
			endDateTimeCombined = new Timestamp(
					TimeUtils.combineDateAndTime(soRoutedProvider.getConditionalChangeDate1(),
							soRoutedProvider.getConditionalStartTime(), timezone).getTime());
			schedule.setServiceDateTime2(CommonUtility.sdfToDate.format(endDateTimeCombined));
			schedule.setScheduleType(PublicAPIConstant.DATETYPE_RANGE);
			routedProvider.setRescheduleDates(schedule);
		}
	}

	/**
	 * This method is for mapping the Notes Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param notes
	 *            Notes
	 * @return void
	 */
	private void mapServiceOrderNotes(ServiceOrder serviceOrder, Notes notes, RetrieveServiceOrder retrieveSOResponse) {
		logger.info("Mapping: ServiceOrderNotes --->Starts");
		List<Note> noteList = new ArrayList<Note>();

		if (null != serviceOrder.getSoNotes() && (!serviceOrder.getSoNotes().isEmpty())) {
			Iterator<ServiceOrderNote> soNoteList = serviceOrder.getSoNotes().iterator();
			while (soNoteList.hasNext()) {
				Note note = new Note();
				ServiceOrderNote serviceOrderNote = soNoteList.next();
				note.setRoleId((null != serviceOrderNote.getRoleId()) ? serviceOrderNote.getRoleId() : new Integer(0));
				note.setEntityID(
						(null != serviceOrderNote.getEntityId()) ? serviceOrderNote.getEntityId() : new Integer(0));
				note.setCreatedDate((null != serviceOrderNote.getCreatedDate())
						? CommonUtility.sdfToDate.format(serviceOrderNote.getCreatedDate()) : "");
				note.setCreatedByName(StringUtils.isEmpty(serviceOrderNote.getCreatedByName()) ? ""
						: serviceOrderNote.getCreatedByName());
				note.setSubject(
						StringUtils.isEmpty(serviceOrderNote.getSubject()) ? "" : serviceOrderNote.getSubject());
				note.setNoteBody(StringUtils.isEmpty(serviceOrderNote.getNote()) ? "" : serviceOrderNote.getNote());
				if (1 == serviceOrderNote.getPrivateId()) {
					note.setPrivateInd(PublicAPIConstant.TRUE);
				}
				if (null != serviceOrderNote.getNoteTypeId() && 1 == serviceOrderNote.getNoteTypeId())
					note.setNoteType(PublicAPIConstant.retrieveSO.SUPPORT_NOTE);
				else if (null != serviceOrderNote.getNoteTypeId() && 2 == serviceOrderNote.getNoteTypeId())
					note.setNoteType(PublicAPIConstant.retrieveSO.GENERAL_NOTE);
				else if (null != serviceOrderNote.getNoteTypeId() && 3 == serviceOrderNote.getNoteTypeId())
					note.setNoteType(PublicAPIConstant.retrieveSO.CLAIM_NOTE);
				noteList.add(note);
			}
			if (null != noteList && noteList.size() > 0) {
				notes.setNotes(noteList);
				retrieveSOResponse.setNotes(notes);
			}
		}
	}

	/**
	 * This method is for mapping the History Section for service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param history
	 *            History
	 * @return void
	 * @throws DataServiceException
	 */
	private void mapServiceOrderHistory(ServiceOrder serviceOrder, History history,
			RetrieveServiceOrder retrieveSOResponse) throws DataServiceException {
		logger.info("Mapping: ServiceOrder History --->Starts");
		List<SoLoggingVo> soLogList = serviceOrderBO.getSoLogDetails(serviceOrder.getSoId());
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		if (null != soLogList && (!soLogList.isEmpty())) {
			Iterator<SoLoggingVo> soLogs = soLogList.iterator();
			while (soLogs.hasNext()) {
				SoLoggingVo soLoggingVo = soLogs.next();
				LogEntry logEntry = new LogEntry();
				logEntry.setRoleId((null != soLoggingVo.getRoleId()) ? soLoggingVo.getRoleId() : new Integer(0));
				logEntry.setEntityID((null != soLoggingVo.getEntityId()) ? soLoggingVo.getEntityId() : new Integer(0));
				logEntry.setCreatedDate((null != soLoggingVo.getCreatedDate())
						? CommonUtility.sdfToDate.format(soLoggingVo.getCreatedDate()) : "");
				logEntry.setCreatedByName(
						StringUtils.isEmpty(soLoggingVo.getCreatedByName()) ? "" : soLoggingVo.getCreatedByName());
				logEntry.setAction(StringUtils.isEmpty(soLoggingVo.getActionName()) ? "" : soLoggingVo.getActionName());
				logEntry.setComment(StringUtils.isEmpty(soLoggingVo.getComment()) ? "" : soLoggingVo.getComment());
				logEntry.setValue(StringUtils.isEmpty(soLoggingVo.getNewValue()) ? "" : soLoggingVo.getNewValue());
				logEntries.add(logEntry);
			}
			history.setLogEntries(logEntries);
			retrieveSOResponse.setHistory(history);
		}

	}

	/**
	 * This method is for mapping the Parts Section of Create Request for
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Parts
	 *            parts
	 * @return void
	 */
	private void mapServiceOrderParts(ServiceOrder serviceOrder, Parts parts, RetrieveServiceOrder retrieveSOResponse) {

		logger.info("Mapping: Parts --->Starts");
		List<com.newco.marketplace.api.beans.so.Part> partList = new ArrayList<com.newco.marketplace.api.beans.so.Part>();
		if (null != serviceOrder.getParts() && (!serviceOrder.getParts().isEmpty())) {
			Iterator<Part> soPartsList = serviceOrder.getParts().iterator();
			while (soPartsList.hasNext()) {
				com.newco.marketplace.api.beans.so.Part part = new com.newco.marketplace.api.beans.so.Part();
				Part soPart = (Part) soPartsList.next();
				part.setManufacturer(StringUtils.isEmpty(soPart.getManufacturer()) ? "" : soPart.getManufacturer());
				part.setPartId(soPart.getPartId());
				part.setModel(StringUtils.isEmpty(soPart.getModelNumber()) ? "" : soPart.getModelNumber());
				part.setDescription(StringUtils.isEmpty(soPart.getPartDs()) ? "" : soPart.getPartDs());
				part.setQuantity(StringUtils.isEmpty(soPart.getQuantity()) ? "1" : soPart.getQuantity());

				// Dimension
				Dimensions dimensions = new Dimensions();
				dimensions.setHeight(StringUtils.isEmpty(soPart.getHeight()) ? "0" : soPart.getHeight());
				dimensions.setLength(StringUtils.isEmpty(soPart.getLength()) ? "0" : soPart.getLength());
				dimensions.setWidth(StringUtils.isEmpty(soPart.getWidth()) ? "0" : soPart.getWidth());
				dimensions.setWeight(StringUtils.isEmpty(soPart.getWeight()) ? "0" : soPart.getWeight());

				if (null != soPart.getMeasurementStandard()
						&& OrderConstants.ENGLISH.equalsIgnoreCase((soPart.getMeasurementStandard().toString()))) {
					dimensions.setMeasurementType(PublicAPIConstant.STANDARD_ENGLISH);
				} else if (null != soPart.getMeasurementStandard()
						&& OrderConstants.METRIC.equalsIgnoreCase(soPart.getMeasurementStandard().toString())) {
					dimensions.setMeasurementType(PublicAPIConstant.STANDARD_METRIC);
				}
				part.setDimensions(dimensions);

				Carrier shippingCarrier = soPart.getShippingCarrier();
				Carrier returnCarrier = soPart.getReturnCarrier();
				if (null != shippingCarrier) {
					part.setShipCarrier(StringUtils.isEmpty(shippingCarrier.getCarrierName()) ? ""
							: shippingCarrier.getCarrierName());
					part.setShipTracking(StringUtils.isEmpty(shippingCarrier.getTrackingNumber()) ? ""
							: shippingCarrier.getTrackingNumber());
				}
				if (null != returnCarrier) {
					part.setReturnCarrier(
							StringUtils.isEmpty(returnCarrier.getCarrierName()) ? "" : returnCarrier.getCarrierName());
					part.setReturnTracking(StringUtils.isEmpty(returnCarrier.getTrackingNumber()) ? ""
							: returnCarrier.getTrackingNumber());
				}
				if (null != soPart.isProviderBringPartInd()) {
					part.setRequiresPickup(soPart.isProviderBringPartInd() ? "true" : "false");
				} else {
					part.setRequiresPickup("false");
				}

				if (null != soPart.getPickupLocation()) {

					SoLocation soPickupLocation = soPart.getPickupLocation();
					Location pickupLocation = new Location();
					int locationTypeId = 0;
					if (null != soPickupLocation.getLocnClassId()) {
						locationTypeId = soPickupLocation.getLocnClassId();
					}

					if (locationTypeId == 1) {

						pickupLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL);

					} else if (locationTypeId == 2) {

						pickupLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL);

					}
					pickupLocation.setLocationName(
							StringUtils.isEmpty(soPickupLocation.getLocnName()) ? "" : soPickupLocation.getLocnName());
					pickupLocation.setAddress1(
							StringUtils.isEmpty(soPickupLocation.getStreet1()) ? "" : soPickupLocation.getStreet1());
					pickupLocation.setAddress2(soPickupLocation.getStreet2());
					pickupLocation
							.setCity(StringUtils.isEmpty(soPickupLocation.getCity()) ? "" : soPickupLocation.getCity());
					pickupLocation.setState(
							StringUtils.isEmpty(soPickupLocation.getState()) ? "" : soPickupLocation.getState());
					pickupLocation
							.setZip(StringUtils.isEmpty(soPickupLocation.getZip()) ? "" : soPickupLocation.getZip());
					pickupLocation.setNote(soPickupLocation.getLocnNotes());
					part.setPickupLocation(pickupLocation);

				}
				partList.add(part);
			}
			parts.setPartList(partList);
			retrieveSOResponse.setParts(parts);
		}

	}

	/**
	 * This method is for mapping the General Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param generalSection
	 *            GeneralSection
	 * @return void
	 */
	private void mapGeneralSection(ServiceOrder serviceOrder, GeneralSection generalSection) {

		logger.info("Mapping: General Section --->Starts");

		generalSection.setBuyerTerms(StringUtils.isEmpty(serviceOrder.getBuyerTermsCond()) ? ""
				: ServiceLiveStringUtils.removeHTML(serviceOrder.getBuyerTermsCond()));
		generalSection.setOverview(StringUtils.isEmpty(serviceOrder.getSowDs()) ? ""
				: ServiceLiveStringUtils.removeHTML(serviceOrder.getSowDs()));
		generalSection
				.setSpecialInstructions(ServiceLiveStringUtils.removeHTML(serviceOrder.getProviderInstructions()));
		generalSection.setTitle(StringUtils.isEmpty(serviceOrder.getSowTitle()) ? "" : serviceOrder.getSowTitle());

		generalSection.setPreviousState(serviceOrder.getLastStatus());

		List<SOOnsiteVisitVO> SOTimeOnSiteList = null;
		try {
			SOTimeOnSiteList = timeOnSiteVisitBO.getTimeOnSiteResults(serviceOrder.getSoId());
		} catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}
		List<SOTimeOnSiteRequest> timeOnSiteList = new ArrayList<SOTimeOnSiteRequest>();
		TimeOnSites timeOnSites = new TimeOnSites();

		if (null != SOTimeOnSiteList && (!SOTimeOnSiteList.isEmpty())) {
			Iterator<SOOnsiteVisitVO> soOnsiteVisitVO = SOTimeOnSiteList.iterator();
			while (soOnsiteVisitVO.hasNext()) {
				SOTimeOnSiteRequest soTimeOnSite = new SOTimeOnSiteRequest();
				SOOnsiteVisitVO SOOnsiteVisit = soOnsiteVisitVO.next();

				java.util.Date arrivalDate, departureDate;

				if (null != SOOnsiteVisit.getArrivalDate()) {
					arrivalDate = (java.util.Date) SOOnsiteVisit.getArrivalDate();
					soTimeOnSite
							.setArrivalDateTime(CommonUtility.sdfToDate.format(new Timestamp(arrivalDate.getTime())));
				}

				if (null != SOOnsiteVisit.getDepartureDate()) {
					departureDate = (java.util.Date) SOOnsiteVisit.getDepartureDate();
					soTimeOnSite.setDepartureDateTime(
							CommonUtility.sdfToDate.format(new Timestamp(departureDate.getTime())));
				}

				timeOnSiteList.add(soTimeOnSite);
			}
			timeOnSites.setTimeOnSites(timeOnSiteList);
			generalSection.setTimeOnSites(timeOnSites);
		}

	}

	/**
	 * This method is for mapping the Scope Of Work Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param scopeOfWork
	 *            ScopeOfWork
	 * @return void
	 */
	private void mapScopeOfWork(ServiceOrder serviceOrder, ScopeOfWork scopeOfWork) {

		logger.info("Mapping: Scope Of Work --->Starts");

		scopeOfWork.setMainCategoryID(
				(null != serviceOrder.getPrimarySkillCatId()) ? serviceOrder.getPrimarySkillCatId().toString() : "");
		List<Task> tasks = new ArrayList<Task>();
		Tasks allTasks = new Tasks();
		if (serviceOrder.getTasks() != null && serviceOrder.getTasks().size() > 0) {
			Iterator<ServiceOrderTask> soTaskList = serviceOrder.getTasks().iterator();
			while (soTaskList.hasNext()) {
				ServiceOrderTask soTask = (ServiceOrderTask) soTaskList.next();
				Task task = new Task();
				task.setTaskId(soTask.getTaskId());
				task.setTaskName(StringUtils.isEmpty(soTask.getTaskName()) ? "" : soTask.getTaskName());
				task.setCategoryID((null != soTask.getSkillNodeId()) ? soTask.getSkillNodeId().toString() : "");
				task.setServiceType(StringUtils.isEmpty(soTask.getServiceType()) ? "" : soTask.getServiceType());
				task.setTaskComment(ServiceLiveStringUtils.removeHTML(soTask.getTaskComments()));
				tasks.add(task);
			}
			allTasks.setTaskList(tasks);
			scopeOfWork.setTasks(allTasks);
		}

	}

	/**
	 * This method is for mapping Contact Details.
	 *
	 * @param soContact
	 *            Contact
	 * @param soAltContact
	 *            Contact
	 * @param contactType
	 *            String
	 * @return contact
	 */
	private com.newco.marketplace.api.beans.so.Contact mapContactDetails(Contact soContact, Contact soAltContact,
			String contactType) {
		logger.info("Inside mapContactDetails--->Start");
		com.newco.marketplace.api.beans.so.Contact contact = new com.newco.marketplace.api.beans.so.Contact();
		com.newco.marketplace.api.beans.so.Phone phone = null;
		contact.setContactLocnType(contactType);
		contact.setFirstName(StringUtils.isEmpty(soContact.getFirstName()) ? "" : soContact.getFirstName());
		if ("".equals(contact.getFirstName())) {
			return null;
		}
		contact.setLastName(StringUtils.isEmpty(soContact.getLastName()) ? "" : soContact.getLastName());
		contact.setEmail(StringUtils.isEmpty(soContact.getEmail()) ? null : soContact.getEmail());

		phone = mapPhoneDetails(soContact);
		if ("".equals(phone.getNumber())) {
			phone = null;
		}
		contact.setPrimaryPhone(phone);
		if (null != soAltContact) {
			phone = mapPhoneDetails(soAltContact);
			if ("".equals(phone.getNumber())) {
				phone = null;
			}
			contact.setAltPhone(phone);
		}
		if ("".equals(contact.getFirstName())) {
			return null;
		}
		return contact;

	}

	/**
	 * This method is for getting the phoneType from ClassId.
	 *
	 * @param classId
	 *            String
	 * @return String
	 */
	private String getPhoneType(String classId) {
		if (OrderConstants.PHONE_CLASS_HOME.equalsIgnoreCase(classId)) {// Home
			return PublicAPIConstant.HOME;
		} else if (OrderConstants.PHONE_CLASS_MOBILE.equalsIgnoreCase(classId)) {// Mobile
			return PublicAPIConstant.MOBILE;
		} else if (OrderConstants.PHONE_CLASS_WORK.equalsIgnoreCase(classId)) {// Work
			return PublicAPIConstant.WORK;
		} else if (OrderConstants.PHONE_CLASS_PAGER.equalsIgnoreCase(classId)) {// Pager
			return PublicAPIConstant.PAGER;
		} else if (OrderConstants.PHONE_CLASS_OTHER.equalsIgnoreCase(classId)) {// Fax
			return PublicAPIConstant.OTHER;
		}
		return PublicAPIConstant.OTHER;
	}

	/**
	 * This method is for mapping Phone Details.
	 *
	 * @param contact
	 *            Contact
	 *
	 * @return Phone
	 */
	private com.newco.marketplace.api.beans.so.Phone mapPhoneDetails(Contact contact) {
		logger.info("Inside mapPhoneDetails--->Start");
		com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
		phone.setNumber((StringUtils.isEmpty(contact.getPhoneNo()) ? ""
				: UIUtils.formatPhoneNumber(contact.getPhoneNo().replaceAll("-", ""))));
		if (!"".equals(phone.getNumber())) {
			phone.setPhoneType(getPhoneType(contact.getPhoneClassId()));
		} else {
			phone.setPhoneType("");
		}
		phone.setExtension(StringUtils.isEmpty(contact.getPhoneNoExt()) ? "" : contact.getPhoneNoExt());
		return phone;
	}

	/**
	 * This method is for mapping the ServiceLocation Section of Response xml
	 * from Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param location
	 *            Location
	 * @return void
	 */

	private void mapServiceLocation(ServiceOrder serviceOrder, Location serviceLocation) {
		logger.info("Mapping: Service Location--->Starts");
		int locationTypeId = 0;
		SoLocation soLocation = serviceOrder.getServiceLocation();
		if (null != soLocation.getLocnClassId()) {
			locationTypeId = soLocation.getLocnClassId();
		}

		if (locationTypeId == 1) {

			serviceLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_COMMERCIAL);

		} else if (locationTypeId == 2) {

			serviceLocation.setLocationType(PublicAPIConstant.LOCATION_TYPE_RESIDENTIAL);

		}
		serviceLocation.setLocationName(StringUtils.isEmpty(soLocation.getLocnName()) ? "" : soLocation.getLocnName());
		serviceLocation.setAddress1(StringUtils.isEmpty(soLocation.getStreet1()) ? "" : soLocation.getStreet1());
		serviceLocation.setAddress2(soLocation.getStreet2());
		serviceLocation.setCity(StringUtils.isEmpty(soLocation.getCity()) ? "" : soLocation.getCity());
		serviceLocation.setState(StringUtils.isEmpty(soLocation.getState()) ? "" : soLocation.getState());
		serviceLocation.setZip(StringUtils.isEmpty(soLocation.getZip()) ? "" : soLocation.getZip());
		serviceLocation.setNote(soLocation.getLocnNotes());
	}

	/**
	 * This method is for mapping the Parts Section of Create Request to Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param customReferences
	 *            CustomReferences
	 * @return void
	 */
	private void mapServiceOrderCustomRef(ServiceOrder serviceOrder, CustomReferences customReferences,
			RetrieveServiceOrder retrieveSOResponse) {
		logger.info("Mapping: Customer Reference --->Starts");
		List<CustomReference> customReferenceList = new ArrayList<CustomReference>();

		if (null != serviceOrder.getCustomRefs() && (!serviceOrder.getCustomRefs().isEmpty())) {
			Iterator<ServiceOrderCustomRefVO> soCustomRefsVoList = serviceOrder.getCustomRefs().iterator();
			while (soCustomRefsVoList.hasNext()) {
				CustomReference customReference = new CustomReference();
				ServiceOrderCustomRefVO serviceOrderCustomRefVO = soCustomRefsVoList.next();
				customReference.setName(StringUtils.isEmpty(serviceOrderCustomRefVO.getRefType()) ? ""
						: serviceOrderCustomRefVO.getRefType());
				customReference.setValue(StringUtils.isEmpty(serviceOrderCustomRefVO.getRefValue()) ? ""
						: serviceOrderCustomRefVO.getRefValue());
				customReferenceList.add(customReference);
			}
			customReferences.setCustomRefList(customReferenceList);
			retrieveSOResponse.setCustomReferences(customReferences);
		}

	}

	/**
	 * This method is for mapping the JobCodes of Response xml from Service
	 * Order.
	 * 
	 * @param serviceOrder
	 * @param jobCodes
	 * @param retrieveSOResponse
	 */
	private void mapServiceOrderJobCodes(ServiceOrder serviceOrder, JobCodes jobCodes,
			RetrieveServiceOrder retrieveSOResponse) {
		logger.info("Mapping: Job Codes --->Starts");
		List<JobCode> jobCodesList = new ArrayList<JobCode>();

		if (null != serviceOrder.getJobCodes() && (!serviceOrder.getJobCodes().isEmpty())) {
			for (JobCodeVO jobCodeVO : serviceOrder.getJobCodes()) {
				JobCode jobCode = new JobCode();
				jobCode.setJobCodeId(jobCodeVO.getJobCodeId());
				jobCode.setCoverageCode(PublicAPIConstant.COVERAGE_CODE);
				
				if(null != serviceOrder.getPrimarySkillCatId() && PublicAPIConstant.SKILL_CATEGORY_IDS_REPAIR_PRICE.contains(serviceOrder.getPrimarySkillCatId())){
					jobCode.setChargeAmount(PublicAPIConstant.REPAIR_CHARGE_AMOUNT);
				}else{
					jobCode.setChargeAmount(PublicAPIConstant.CHARGE_AMOUNT);
				}

				jobCode.setRelatedFlag(PublicAPIConstant.RELATED_FLAG);
				jobCode.setSequence(PublicAPIConstant.SEQUENCE);
				jobCodesList.add(jobCode);
			}
		}
		jobCodes.setJobCode(jobCodesList);
		retrieveSOResponse.setJobCodes(jobCodes);

	}

	/**
	 * This method is for mapping the JobCodes of Response xml from Service
	 * Order.
	 * 
	 * @param serviceOrder
	 * @param paymentDetails
	 * @param retrieveSOResponse
	 */
	private void mapServiceOrderPaymentDetails(ServiceOrder serviceOrder, PaymentDetails paymentDetails,
			RetrieveServiceOrder retrieveSOResponse) {
		// TODO Auto-generated method stub
		logger.info("Mapping: PaymentDetails --->Starts");
		if (null != serviceOrder.getPaymentDetails()) {
			PaymentDetailsVO paymentDetailsVO = new PaymentDetailsVO();
			paymentDetailsVO = serviceOrder.getPaymentDetails();
			paymentDetails.setAmountAuthorized(paymentDetailsVO.getAmountAuthorized());
			paymentDetails.setCardExpireMonth(paymentDetailsVO.getCardExpireMonth());
			paymentDetails.setCardExpireYear(paymentDetailsVO.getCardExpireYear());
			paymentDetails.setCcNumber(paymentDetailsVO.getCcNumber());
			paymentDetails.setCcType(paymentDetailsVO.getCcType());
			paymentDetails.setCheckNumber(paymentDetailsVO.getCheckNumber());
			paymentDetails.setMaskedAccNumber(paymentDetailsVO.getMaskedAccNumber());
			paymentDetails.setPaymentType(paymentDetailsVO.getPaymentType());
			paymentDetails.setPreAuthNumber(paymentDetailsVO.getPreAuthNumber());
		}
		mappaymenttype(paymentDetails);
		retrieveSOResponse.setPaymentDetails(paymentDetails);
	}

	private void mappaymenttype(PaymentDetails paymentDetails) {
		if (null != paymentDetails && null != paymentDetails.getPaymentType()) {
			if (MPConstants.PAYMENT_TYPE_CHECK.equalsIgnoreCase(paymentDetails.getPaymentType())) {
				paymentDetails.setPaymentType(MPConstants.CHECK);
			} else {
				paymentDetails.setPaymentType(MPConstants.CREDIT_CARD);
				if (null != paymentDetails && StringUtils.isNotBlank(paymentDetails.getMaskedAccNumber())) {
					paymentDetails.setCcNumber(formatCardNumber(paymentDetails.getMaskedAccNumber()));
				}
			}
		}
	}

	private String formatCardNumber(String maskedAccNumber) {

		int last = maskedAccNumber.length();
		int start = last - MPConstants.VISIBLE_NO_OF_DIGITS_CREDIT_CARD;
		String value = "";
		for (int i = 0; i < start; i++) {
			value = value + "*";
		}
		maskedAccNumber = value + maskedAccNumber.substring(start, last);
		return maskedAccNumber;

	}

	/**
	 * This method is for mapping the InvoiceParts of Response xml from Service
	 * Order.
	 * 
	 * @param serviceOrder
	 * @param InvoiceParts
	 * @param retrieveSOResponse
	 */
	private void mapServiceOrderInvoiceParts(ServiceOrder serviceOrder, InvoiceParts invoiceParts,
			RetrieveServiceOrder retrieveSOResponse) {
		List<PartsDatas> partsDatas = new ArrayList<PartsDatas>();
		if (null != serviceOrder.getInvoicePartsdata() && (!serviceOrder.getInvoicePartsdata().isEmpty())) {
			for (PartDatasVO PartDatasVO : serviceOrder.getInvoicePartsdata()) {
				PartsDatas partData = new PartsDatas();
				partData.setPartCoverageCode(PartDatasVO.getPartCoverageCode());
				partData.setPartDivNo(PartDatasVO.getPartDivNo());
				partData.setPartPartNo(PartDatasVO.getPartPartNo());
				partData.setPartPlsNo(PartDatasVO.getPartPlsNo());
				partData.setPartPrice(PartDatasVO.getPartPrice());
				partData.setPartQty(PartDatasVO.getPartQty());
				partData.setPartStatus(PartDatasVO.getPartStatus());
				mapPartCoverageCode(partData);
				partsDatas.add(partData);
			}
		}
		// mapPartCoverageCode(partsDatas);
		invoiceParts.setPartsDatas(partsDatas);
		retrieveSOResponse.setInvoiceParts(invoiceParts);
	}

	/*
	 * private void mapPartCoverageCode(List<PartsDatas> partsDatas) { if(null
	 * != partsDatas){ for(PartsDatas partData:partsDatas){ if(null !=
	 * partData.getPartCoverageCode() &&
	 * StringUtils.isNotBlank(PublicAPIConstant.coverageCodes().get((partData.
	 * getPartCoverageCode())))){
	 * partData.setPartCoverageCode(PublicAPIConstant.coverageCodes().get((
	 * partData.getPartCoverageCode()))); }
	 * 
	 * } }
	 * 
	 * }
	 */
	private void mapPartCoverageCode(PartsDatas partData) {
		if (null != partData.getPartCoverageCode()
				&& StringUtils.isNotBlank(PublicAPIConstant.coverageCodes().get((partData.getPartCoverageCode())))) {
			partData.setPartCoverageCode(PublicAPIConstant.coverageCodes().get((partData.getPartCoverageCode())));
		} /*
			 * else{ partData.setPartCoverageCode(PublicAPIConstant.NO_DATA); }
			 */

	}

	/**
	 * This method is for mapping the Schedule Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Schedule
	 *            schedule
	 * @return void
	 */
	private void mapServiceOrderSchedule(ServiceOrder serviceOrder, Schedule schedule, TimeZone timeZone) {

		try {
			Reschedule reschedule = new Reschedule();
			schedule.setConfirmWithCustomer(("1".equals(serviceOrder.getProviderServiceConfirmInd()))
					? PublicAPIConstant.CONFIRM_CUSTOMER_TRUE : PublicAPIConstant.CONFIRM_CUSTOMER_FALSE);

			// SLT-1038 / SLT-1499 START
			/* Added check for preferences. */
			if (1 == serviceOrder.getServiceDateTypeId()) {
				schedule.setScheduleType(PublicAPIConstant.DATETYPE_FIXED);
			} else if (2 == serviceOrder.getServiceDateTypeId()) {
				schedule.setScheduleType(PublicAPIConstant.DATETYPE_RANGE);
			} else {
				schedule.setScheduleType(PublicAPIConstant.DATETYPE_PREFERENCES);
			}
			// SLT-1038 / SLT-1499 END

			java.util.Date serviceDate1, serviceDate2;
			logger.info("serviceLocTimeZone:" + timeZone);

			if (null != serviceOrder.getServiceDate1()) {
				serviceDate1 = (java.util.Date) TimeUtils.combineDateTime(serviceOrder.getServiceDate1(),
						serviceOrder.getServiceTimeStart(), timeZone);
				logger.info("before conversion serviceDate1 : " + serviceDate1);
				schedule.setServiceDateTime1(
						CommonUtility.sdfToDateWithZone.format(new Timestamp(serviceDate1.getTime())));
				logger.info("after conversion serviceDate1 : " + schedule.getServiceDateTime1());
			}
			if (null != serviceOrder.getServiceDate2()) {
				serviceDate2 = (java.util.Date) TimeUtils.combineDateTime(serviceOrder.getServiceDate2(),
						serviceOrder.getServiceTimeEnd(), timeZone);
				schedule.setServiceDateTime2(
						CommonUtility.sdfToDateWithZone.format(new Timestamp(serviceDate2.getTime())));
			}

			if (null != serviceOrder.getRescheduleServiceDateTypeId()) {
				reschedule.setRescheduleType((1 == serviceOrder.getRescheduleServiceDateTypeId())
						? PublicAPIConstant.DATETYPE_FIXED : PublicAPIConstant.DATETYPE_RANGE);
			}

			java.util.Date rescheduleServiceDate1, rescheduleServiceDate2;

			if (null != serviceOrder.getRescheduleServiceDate1()) {
				rescheduleServiceDate1 = (java.util.Date) TimeUtils.combineDateTime(
						serviceOrder.getRescheduleServiceDate1(), serviceOrder.getRescheduleServiceTimeStart(),
						timeZone);
				reschedule.setRescheduleServiceDateTime1(
						CommonUtility.sdfToDateWithZone.format(new Timestamp(rescheduleServiceDate1.getTime())));
			}
			if (null != serviceOrder.getRescheduleServiceDate2()) {
				rescheduleServiceDate2 = (java.util.Date) TimeUtils.combineDateTime(
						serviceOrder.getRescheduleServiceDate2(), serviceOrder.getRescheduleServiceTimeEnd(), timeZone);
				reschedule.setRescheduleServiceDateTime2(
						CommonUtility.sdfToDateWithZone.format(new Timestamp(rescheduleServiceDate2.getTime())));
			}

			ServiceOrderRescheduleVO rescheduleDetails = null;
			String role = null;
			String comment = null;
			rescheduleDetails = serviceOrderBO.getRescheduleRequestInfo(serviceOrder.getSoId());
			if (rescheduleDetails != null && rescheduleDetails.getUserRole() != null) {
				if (rescheduleDetails.getUserRole() == 3 || rescheduleDetails.getUserRole() == 5) {
					role = PublicAPIConstant.retrieveSO.BUYER;
				} else if (rescheduleDetails.getUserRole() == 1) {
					role = PublicAPIConstant.retrieveSO.PROVIDER;
				}

				// setting latest reschedule provider comment
				List<SoLoggingVo2> soLogList = serviceOrderBO.getSoRescheduleLogDetailsAnyRoles(serviceOrder.getSoId());
				if (null != soLogList && soLogList.size() > 0) {
					comment = soLogList.get(0).getComment();

					try {
						reschedule.setRescheduleCreatedDateTime(
								CommonUtility.sdfToDate.format(soLogList.get(0).getCreatedDate()));
					} catch (Exception e) {
						logger.error("Exception Occurred with Date", e);
					}
				}
			}

			reschedule.setRescheduledBy(role);
			reschedule.setComment(comment);
			schedule.setReschedule(reschedule);
			schedule.setServiceLocationTimezone(serviceOrder.getServiceLocationTimeZone());
			schedule.setServiceDateTimezoneOffset(serviceOrder.getServiceDateTimezoneOffset()+"");
		} catch (Exception e) {
			logger.error("Exception Occurred", e);
		}
	}

	/**
	 * This method is for mapping the Attachments Section of Create Request for
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param attachments
	 *            Attachments
	 * @return void
	 */
	private void mapServiceOrderDocuments(ServiceOrder serviceOrder, AttachmentType attachments,
			RetrieveServiceOrder retrieveSOResponse) {

		logger.info("Mapping: Document --->Starts");
		List<FileNames> fileNameList = new ArrayList<FileNames>();

		if (null != serviceOrder.getSoDocuments() && serviceOrder.getSoDocuments().size() > 0) {
			for (SODocument soDocument : serviceOrder.getSoDocuments()) {
				FileNames fileNames = new FileNames();
				fileNames.setFile(soDocument.getFileName());
				if (PublicAPIConstant.VIDEO_TYPE == soDocument.getDocCategoryId()) {
					fileNames.setVideo(PublicAPIConstant.TRUE);
					String videoUrl = PropertiesUtils.getFMPropertyValue(Constants.AppPropConstants.VIDEO_BASE_URL);
					videoUrl = videoUrl.replace(PublicAPIConstant.DELIMITER, soDocument.getFileName());
					fileNames.setVideoUrl(videoUrl);
				}
				fileNameList.add(fileNames);
			}

			attachments.setFileNames(fileNameList);

			retrieveSOResponse.setAttachments(attachments);
		}

	}

	/**
	 * This method is for mapping the Pricing Section of Response xml from
	 * Service Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Pricing
	 *            pricing
	 * @return void
	 */
	private void mapServiceOrderPricing(ServiceOrder serviceOrder, Pricing pricing) {
		logger.info("Mapping: Pricing --->Starts");
		pricing.setLaborSpendLimit((null != serviceOrder.getSpendLimitLabor())
				? String.format("%.2f", serviceOrder.getSpendLimitLabor()) : "0.00");
		pricing.setPartsSpendLimit((null != serviceOrder.getSpendLimitParts())
				? String.format("%.2f", serviceOrder.getSpendLimitParts()) : "0.00");
		pricing.setFinalPriceForLabor((null != serviceOrder.getLaborFinalPrice())
				? String.format("%.2f", serviceOrder.getLaborFinalPrice()) : "0.00");
		pricing.setFinalPriceForParts((null != serviceOrder.getPartsFinalPrice())
				? String.format("%.2f", serviceOrder.getPartsFinalPrice()) : "0.00");
		// SL-21945
		if (null != serviceOrder.getSoPrice()) {
			pricing.setPartsDiscount((null != serviceOrder.getSoPrice().getPartsDiscount())
					? String.format("%.2f", serviceOrder.getSoPrice().getPartsDiscount()) : "0.00");
			pricing.setLaborDiscount((null != serviceOrder.getSoPrice().getLaborDiscount())
					? String.format("%.2f", serviceOrder.getSoPrice().getLaborDiscount()) : "0.00");
			pricing.setPartsTax((null != serviceOrder.getSoPrice().getPartsTax())
					? String.format("%.4f", serviceOrder.getSoPrice().getPartsTax()) : "0.0000");
			pricing.setLaborTax((null != serviceOrder.getSoPrice().getLaborTax())
					? String.format("%.4f", serviceOrder.getSoPrice().getLaborTax()) : "0.0000");
		}
		pricing.setPriceModel(serviceOrder.getPriceModel());

		// SL-21732
		if (null != serviceOrder.getSoPartLaborPriceReason()) {
			for (SOPartLaborPriceReasonVO soPartLaborPriceReason : serviceOrder.getSoPartLaborPriceReason()) {
				if (soPartLaborPriceReason != null && soPartLaborPriceReason.getPriceType().equals("Labor")) {
					pricing.setLaborPriceChangeReasonCode(StringUtils.isEmpty(soPartLaborPriceReason.getReasonCodeId())
							? null : soPartLaborPriceReason.getReasonCodeId());
					pricing.setLaborPriceChangeComments(StringUtils.isEmpty(soPartLaborPriceReason.getReasonComments())
							? null : soPartLaborPriceReason.getReasonComments());
					pricing.setLaborPriceChangeBy(StringUtils.isEmpty(soPartLaborPriceReason.getModifiedBy()) ? null
							: soPartLaborPriceReason.getModifiedBy());
				}
				if (soPartLaborPriceReason != null && soPartLaborPriceReason.getPriceType().equals("Parts")) {
					pricing.setPartsPriceChangeReasonCode(StringUtils.isEmpty(soPartLaborPriceReason.getReasonCodeId())
							? null : soPartLaborPriceReason.getReasonCodeId());
					pricing.setPartsPriceChangeComments(StringUtils.isEmpty(soPartLaborPriceReason.getReasonComments())
							? null : soPartLaborPriceReason.getReasonComments());
					pricing.setPartsPriceChangeBy(StringUtils.isEmpty(soPartLaborPriceReason.getModifiedBy()) ? null
							: soPartLaborPriceReason.getModifiedBy());
				}
			}
		}
	}

	/**
	 * This method is for mapping Contacts Section of Response xml from Service
	 * Order.
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param Contacts
	 *            contacts
	 * @return void
	 */
	private void mapServiceOrderContacts(ServiceOrder serviceOrder, Contacts contacts) {

		logger.info("Mapping: Contact Details--->Starts");
		List<com.newco.marketplace.api.beans.so.Contact> contactList = new ArrayList<com.newco.marketplace.api.beans.so.Contact>();
		com.newco.marketplace.api.beans.so.Contact respContact = new com.newco.marketplace.api.beans.so.Contact();
		if (null != serviceOrder.getServiceContact()) {
			// SLT-1537 : Missing alt contact
			if (null != serviceOrder.getAltServiceContact()) {
				respContact = mapContactDetails(serviceOrder.getServiceContact(), serviceOrder.getAltServiceContact(),
						PublicAPIConstant.SERVICE);
			} else if (null != serviceOrder.getServiceContactAlt()) {
				respContact = mapContactDetails(serviceOrder.getServiceContact(), serviceOrder.getServiceContactAlt(),
						PublicAPIConstant.SERVICE);
			}
			if (null != respContact) {
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getEndUserContact()) {
			respContact = mapContactDetails(serviceOrder.getEndUserContact(), serviceOrder.getAltEndUserContact(),
					PublicAPIConstant.END_USER);
			if (null != respContact) {
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getAltBuyerResource()
				&& null != serviceOrder.getAltBuyerResource().getBuyerResContact()) {
			Contact buyerSupportContact = serviceOrder.getAltBuyerResource().getBuyerResContact();
			if (buyerSupportContact != null) {
				com.newco.marketplace.api.beans.so.Contact contact = new com.newco.marketplace.api.beans.so.Contact();
				contact.setContactLocnType(PublicAPIConstant.BUYER_SUPPORT);
				contact.setFirstName(
						buyerSupportContact.getFirstName() != null ? buyerSupportContact.getFirstName() : "");
				if (!"".equals(contact.getFirstName())) {
					contact.setLastName(
							buyerSupportContact.getLastName() != null ? buyerSupportContact.getLastName() : "");
					contact.setEmail(StringUtils.isEmpty(buyerSupportContact.getEmail()) ? null
							: buyerSupportContact.getEmail());

					Map<String, String> phoneList = new LinkedHashMap<String, String>(5);

					if (null != buyerSupportContact.getPhoneNo() && (!"".equals(buyerSupportContact.getPhoneNo()))) {
						phoneList.put(PublicAPIConstant.WORK, buyerSupportContact.getPhoneNo());
					}
					if (null != buyerSupportContact.getCellNo() && (!"".equals(buyerSupportContact.getCellNo()))) {
						phoneList.put(PublicAPIConstant.MOBILE, buyerSupportContact.getCellNo());
					}
					if (null != buyerSupportContact.getHomeNo() && (!"".equals(buyerSupportContact.getHomeNo()))) {
						phoneList.put(PublicAPIConstant.HOME, buyerSupportContact.getHomeNo());
					}
					if (null != buyerSupportContact.getPagerText()
							&& (!"".equals(buyerSupportContact.getPagerText()))) {
						phoneList.put(PublicAPIConstant.PAGER, buyerSupportContact.getPagerText());
					}
					if (null != buyerSupportContact.getFaxNo() && (!"".equals(buyerSupportContact.getFaxNo()))) {
						phoneList.put(PublicAPIConstant.OTHER, buyerSupportContact.getFaxNo());
					}
					if (phoneList.size() > 0) {
						Iterator<String> phoneTypes = phoneList.keySet().iterator();

						while (phoneTypes.hasNext()) {
							if (phoneList.size() >= 2) {
								String phoneType = phoneTypes.next();
								String phoneNumber = phoneList.get(phoneType);
								if (!PublicAPIConstant.WORK.equals(phoneType)) {
									contact.setPrimaryPhone(mapBuyerSupportPhoneDetails(phoneNumber, null, phoneType));
								} else {
									contact.setPrimaryPhone(mapBuyerSupportPhoneDetails(phoneNumber,
											buyerSupportContact.getPhoneNoExt(), phoneType));
								}
								phoneType = phoneTypes.next();
								phoneNumber = phoneList.get(phoneType);
								contact.setAltPhone(mapBuyerSupportPhoneDetails(phoneNumber, null, phoneType));

							} else {
								String phoneType = phoneTypes.next();
								String phoneNumber = phoneList.get(phoneType);
								if (!PublicAPIConstant.WORK.equals(phoneType)) {
									contact.setPrimaryPhone(mapBuyerSupportPhoneDetails(phoneNumber, null, phoneType));
								} else {
									contact.setPrimaryPhone(mapBuyerSupportPhoneDetails(phoneNumber,
											buyerSupportContact.getPhoneNoExt(), phoneType));
								}
							}

							break;
						}
					}
					contactList.add(contact);
				}

			}

		}
		if (null != serviceOrder.getVendorResourceContact()) {
			respContact = mapContactDetails(serviceOrder.getVendorResourceContact(), null, PublicAPIConstant.PROVIDER);
			if (null != respContact) {
				if (null != serviceOrder.getServiceproviderContactOnQuickLinks()
						&& null != serviceOrder.getServiceproviderContactOnQuickLinks().getAlternatePhoneNo()) {
					com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
					phone.setNumber((StringUtils
							.isEmpty(serviceOrder.getServiceproviderContactOnQuickLinks().getAlternatePhoneNo()) ? ""
									: UIUtils.formatPhoneNumber(serviceOrder.getServiceproviderContactOnQuickLinks()
											.getAlternatePhoneNo().replaceAll("-", ""))));
					phone.setPhoneType(PublicAPIConstant.WORK);
					respContact.setAltPhone(phone);
				}
				contactList.add(respContact);
			}
		}
		if (null != serviceOrder.getParts()) {
			Iterator<Part> soPartsList = serviceOrder.getParts().iterator();
			while (soPartsList.hasNext()) {
				Part part = soPartsList.next();
				if (null != part.getPickupContact()) {
					respContact = mapContactDetails(part.getPickupContact(), part.getAltPickupContact(),
							PublicAPIConstant.PICKUP);
					if (null != respContact) {
						contactList.add(respContact);
					}
				}
			}

		}
		contacts.setContactList(contactList);

	}

	/**
	 * This method is for mapping BuyerSupport Phone Details.
	 *
	 * @param contact
	 *            Contact
	 *
	 * @return Phone
	 */
	private com.newco.marketplace.api.beans.so.Phone mapBuyerSupportPhoneDetails(String number, String ext,
			String type) {
		logger.info("Inside BuyerSupport mapPhoneDetails--->Start");
		com.newco.marketplace.api.beans.so.Phone phone = new com.newco.marketplace.api.beans.so.Phone();
		phone.setNumber((StringUtils.isEmpty(number) ? "" : UIUtils.formatPhoneNumber(number.replaceAll("-", ""))));
		phone.setPhoneType(type);

		phone.setExtension(StringUtils.isEmpty(ext) ? "" : ext);
		return phone;
	}

	/**
	 * This method is for mapping the orderstatus Response
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param orderStatus
	 *            OrderStatus
	 * @return void
	 */
	private void mapOrderStatus(ServiceOrder serviceOrder, OrderStatus orderStatus) {

		if (null != serviceOrder.getCreatedDate()) {
			orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(serviceOrder.getCreatedDate()));
		}
		if (null != serviceOrder.getRoutedDate()) {
			orderStatus.setPostedDate(CommonUtility.sdfToDate.format(serviceOrder.getRoutedDate()));
		}
		orderStatus.setSoId(StringUtils.isEmpty(serviceOrder.getSoId()) ? "" : serviceOrder.getSoId());
		orderStatus.setStatus(StringUtils.isEmpty(serviceOrder.getStatus()) ? "" : serviceOrder.getStatus());
		orderStatus.setSubstatus(StringUtils.isEmpty(serviceOrder.getSubStatus()) ? "" : serviceOrder.getSubStatus());

		// SLT-4147
		if (null != serviceOrder.getCompletedDate()) {
			orderStatus.setCompletedDate(CommonUtility.sdfToDate.format(serviceOrder.getCompletedDate()));
		}
		// SLT-4177
		if (null != serviceOrder.getResolutionDs()) {
			orderStatus.setResolutionComments(serviceOrder.getResolutionDs());
		}

		ProblemResolutionSoVO pbResVo = null;

		if (!StringUtils.isEmpty(serviceOrder.getBuyerSoId())) {
			orderStatus.setBuyerSoId(serviceOrder.getBuyerSoId());
		}

		try {
			pbResVo = serviceOrderBO.getProblemDesc(serviceOrder.getSoId());
		} catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}

		if (null != pbResVo)
			orderStatus.setProblemDescription(pbResVo.getPbComment());
	}

	private void mapServiceOrderTimeSlots(ServiceOrder serviceOrder, ScheduleServiceSlot scheduleServiceSlot,
			TimeZone timeZone) {
		logger.info("Entering into mapServiceOrderTimeSlots of  SORetrieveMapperV1_4");
		try {
			ServiceDateTimeSlots serviceDateTimeSlotsForXML = new ServiceDateTimeSlots();

			List<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot> serviceDatetimeSlots = new ArrayList<com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot>();
			for (com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot serviceDatetimeSlt : serviceOrder
					.getServiceDatetimeSlots()) {
				com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot serviceDatetimeSlot = new com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot();
				if (null != serviceDatetimeSlt && null != serviceDatetimeSlt.getServiceStartDate()) {
					java.util.Date serviceStartDate = (java.util.Date) TimeUtils.combineDateTime(
							serviceDatetimeSlt.getServiceStartDate(), serviceDatetimeSlt.getServiceStartTime(),
							timeZone);
					// serviceStartDate =
					// TimeChangeUtil.convertDateBetweenTimeZones(serviceStartDate,
					// timeZone, TimeChangeUtil.GMT_TIME_ZONE);
					serviceDatetimeSlot.setServiceStartDate(
							CommonUtility.sdfToDateWithZone.format(new Timestamp(serviceStartDate.getTime())));
				}
				if (null != serviceDatetimeSlt && null != serviceDatetimeSlt.getServiceEndDate()) {
					java.util.Date serviceEndDate = (java.util.Date) TimeUtils.combineDateTime(
							serviceDatetimeSlt.getServiceEndDate(), serviceDatetimeSlt.getServiceEndTime(), timeZone);
					// serviceEndDate =
					// TimeChangeUtil.convertDateBetweenTimeZones(serviceEndDate,
					// timeZone, TimeChangeUtil.GMT_TIME_ZONE);
					serviceDatetimeSlot.setServiceEndDate(
							CommonUtility.sdfToDateWithZone.format(new Timestamp(serviceEndDate.getTime())));
				}
				serviceDatetimeSlot.setSlotSeleted("1".equals(serviceDatetimeSlt.getSlotSelected())
						? PublicAPIConstant.SLOT_SELETED_TRUE : PublicAPIConstant.SLOT_SELETED_FALSE);
				serviceDatetimeSlot.setPreferenceInd(serviceDatetimeSlt.getPreferenceInd());
				serviceDatetimeSlots.add(serviceDatetimeSlot);
			}

			serviceDateTimeSlotsForXML.setServiceDatetimeSlot(serviceDatetimeSlots);
			scheduleServiceSlot.setServiceDatetimeSlots(serviceDateTimeSlotsForXML);
			scheduleServiceSlot.setConfirmWithCustomer(("1".equals(serviceOrder.getProviderServiceConfirmInd()))
					? PublicAPIConstant.CONFIRM_CUSTOMER_TRUE : PublicAPIConstant.CONFIRM_CUSTOMER_FALSE);

			// SLT- 1106 start
			// Commenting the below code as scheduleServiceSlot is only
			// applicable for preferences.
			/*
			 * if(1 == serviceOrder.getServiceDateTypeId()){
			 * scheduleServiceSlot.setScheduleType(PublicAPIConstant.
			 * DATETYPE_FIXED); }else if(3 ==
			 * serviceOrder.getServiceDateTypeId()){
			 * scheduleServiceSlot.setScheduleType(PublicAPIConstant.
			 * DATETYPE_PREFERENCES); }else{
			 * scheduleServiceSlot.setScheduleType(PublicAPIConstant.
			 * DATETYPE_FIXED); }
			 */
			// Setting schedule type as preferences.
			scheduleServiceSlot.setScheduleType(PublicAPIConstant.DATETYPE_PREFERENCES);
			// SLT- 1106 end
			logger.info("Exiting mapServiceOrderTimeSlots of  SORetrieveMapperV1_4");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception Occurred" + e.getMessage());

		}

	}

	public RetrieveServiceOrder mapUpsellInfo(RetrieveServiceOrder response, List<ServiceOrderAddonVO> AddonVOList) {
		logger.info("Entering into mapUpsellInfo of  SORetrieveMapperV1_4");

		AddOns addOnList = new AddOns();
		List<AddOn> addons = new ArrayList<AddOn>();
		try {

			if (null != AddonVOList && !AddonVOList.isEmpty()) {

				for (ServiceOrderAddonVO serviceOrderAddonVO : AddonVOList) {
					AddOn addOn = new AddOn();

					if (null != serviceOrderAddonVO && serviceOrderAddonVO.getQuantity() > 0) {

						addOn.setAddonSKU(serviceOrderAddonVO.getSku());
						// addOn.setCoverageType(serviceOrderAddonVO.getCoverage());

						addOn.setCustomerCharge(
								serviceOrderAddonVO.getRetailPrice() * serviceOrderAddonVO.getQuantity());

						addOn.setDescription(serviceOrderAddonVO.getDescription());
						addOn.setMargin(String.valueOf(serviceOrderAddonVO.getMargin()));

						addOn.setQty(serviceOrderAddonVO.getQuantity());
						if (serviceOrderAddonVO.getAddonId() != null) {
							addOn.setSoAddonId(serviceOrderAddonVO.getAddonId().toString());
						}
						addOn.setTaxPercentage(serviceOrderAddonVO.getTaxPercentage());
						addons.add(addOn);
					}

				}
				addOnList.setAddon(addons);
				response.setAddOns(addOnList);
			}

			logger.info("Exiting mapUpsellInfo of  SORetrieveMapperV1_4");

		} catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}

		return response;
	}

	public RetrieveServiceOrder mapScopeOfWorkWithSku(RetrieveServiceOrder response, ServiceOrder serviceOrder) {
		logger.info("Entering into Mapping: Scope Of Work of SORetrieveMapperV1_4");
		ScopeOfWork scopeOfWork = response.getScopeOfWork();
		List<String> skus = new ArrayList<String>();
		Skus allSkus = new Skus();
		Iterator<ServiceOrderTask> soTaskList = serviceOrder.getTasks().iterator();
		while (soTaskList.hasNext()) {
			ServiceOrderTask soTask = (ServiceOrderTask) soTaskList.next();
			skus.add(StringUtils.isEmpty(soTask.getSku()) ? "" : soTask.getSku());
		}
		allSkus.setSkuList(skus);
		scopeOfWork.setSkus(allSkus);
		response.setScopeOfWork(scopeOfWork);
		logger.info("Exiting mapScopeOfWorkWithSku of  SORetrieveMapperV1_4");
		return response;
	}

	/**
	 * This method is for mapping the SOGetResponse Section for service Order.
	 *
	 * @param soResponses
	 *            List<RetrieveServiceOrder>
	 *
	 * @return SOGetResponse
	 */
	public SOGetResponse mapResponse(List<RetrieveServiceOrder> soResponses) {
		SOGetResponse soGetResponse = new SOGetResponse();
		ServiceOrders serviceOrders = new ServiceOrders();
		serviceOrders.setServiceorders(soResponses);
		Iterator<RetrieveServiceOrder> soResponseList = soResponses.iterator();
		Results retrieveResults = new Results();
		int successCount = 0;
		while (soResponseList.hasNext()) {

			RetrieveServiceOrder response = (RetrieveServiceOrder) soResponseList.next();
			Results results = response.getResults();
			if (results.getResult() != null
					&& results.getResult().get(0).getCode().equals(ResultsCode.SUCCESS.getCode())) {
				successCount = successCount + 1;

			}
		}
		if (successCount > 0) {
			retrieveResults = Results.getSuccess(successCount + " " + ResultsCode.SO_RETRIEVE_SOS_FOUND.getMessage());
		} else {
			retrieveResults = Results.getError(ResultsCode.SO_RETRIEVE_SOS_NOTFOUND.getMessage(),
					ResultsCode.FAILURE.getCode());
		}
		soGetResponse.setServiceOrders(serviceOrders);
		soGetResponse.setResults(retrieveResults);
		return soGetResponse;
	}

}

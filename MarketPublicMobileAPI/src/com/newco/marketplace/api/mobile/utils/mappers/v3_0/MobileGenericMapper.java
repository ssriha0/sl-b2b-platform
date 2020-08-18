package com.newco.marketplace.api.mobile.utils.mappers.v3_0;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.forgetUnamePwd.SecurityQuestion;
import com.newco.marketplace.api.beans.forgetUnamePwd.UserDetail;
import com.newco.marketplace.api.beans.forgetUnamePwd.UserDetails;
import com.newco.marketplace.api.beans.searchCriteria.Markets;
import com.newco.marketplace.api.beans.searchCriteria.OrderStatus;
import com.newco.marketplace.api.beans.searchCriteria.OrderStatuses;
import com.newco.marketplace.api.beans.searchCriteria.SearchCriterias;
import com.newco.marketplace.api.beans.searchCriteria.ServiceProvider;
import com.newco.marketplace.api.beans.searchCriteria.ServiceProviders;
import com.newco.marketplace.api.beans.searchCriteria.SubStatus;
import com.newco.marketplace.api.beans.searchCriteria.SubStatuses;
import com.newco.marketplace.api.beans.so.CounterOfferResources;
import com.newco.marketplace.api.beans.so.DepositionCodeDTO;
import com.newco.marketplace.api.beans.so.assignSO.AssignVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.AddOnVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.BuyerRefVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.DocumentDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.InvoiceSupplierVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.InvoiceVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.MerchandiseVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.NoteVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.PartVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RetrieveSODetailsMobileVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SOTripChangeLogVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SupportNoteVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.TaskDetailVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.TripVO;
import com.newco.marketplace.api.beans.so.submitReschedule.SORescheduleVO;
import com.newco.marketplace.api.beans.so.viewDashboard.DashBoardCountVO;
import com.newco.marketplace.api.beans.so.viewDashboard.MobileDashboardVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.criteria.vo.SearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoSearchCriteriaVO;
import com.newco.marketplace.api.criteria.vo.SoStatusVO;
import com.newco.marketplace.api.mobile.beans.Code;
import com.newco.marketplace.api.mobile.beans.Codes;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.ProviderSOList;
import com.newco.marketplace.api.mobile.beans.ReasonCode;
import com.newco.marketplace.api.mobile.beans.ReasonCodesList;
import com.newco.marketplace.api.mobile.beans.Filter.AppointmentDateCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.Filter;
import com.newco.marketplace.api.mobile.beans.Filter.FilterSearchCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.Filters;
import com.newco.marketplace.api.mobile.beans.Filter.GetFilterResponse;
import com.newco.marketplace.api.mobile.beans.Filter.Market;
import com.newco.marketplace.api.mobile.beans.Filter.MarketCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.ServiceProName;
import com.newco.marketplace.api.mobile.beans.Filter.ServiceProNameCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.StatusCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.StatusValue;
import com.newco.marketplace.api.mobile.beans.Filter.SubStatusCriteria;
import com.newco.marketplace.api.mobile.beans.Filter.SubStatusValue;
import com.newco.marketplace.api.mobile.beans.GetReasonCodes.GetReasonCodesResponse;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderRequest;
import com.newco.marketplace.api.mobile.beans.assign.AssignServiceOrderResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.CounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferRequest;
import com.newco.marketplace.api.mobile.beans.counterOffer.WithdrawCounterOfferResponse;
import com.newco.marketplace.api.mobile.beans.depositioncode.DispositionCodeDetail;
import com.newco.marketplace.api.mobile.beans.depositioncode.DispositionCodeList;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.AssignedResource;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.EligibleProvider;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.EligibleProviders;
import com.newco.marketplace.api.mobile.beans.eligibleProviders.GetEligibleProviderResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.AdditionalVerificationDetails;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ForgetUnamePwdServiceResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.SecurityQuestAnsDetails;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsRequest;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.ValidateSecQuestAnsResponse;
import com.newco.marketplace.api.mobile.beans.forgetUNamePwd.VerificationDetails;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.BidDetails;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.PickUpLocationDetails;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.RecievedOrder;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.RecievedOrdersResponse;
import com.newco.marketplace.api.mobile.beans.getRecievedOrders.RecievedServiceOrders;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSORequest;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.MobileRejectSOResponse;
import com.newco.marketplace.api.mobile.beans.rejectServiceOrder.Resource;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleRespondRequest;
import com.newco.marketplace.api.mobile.beans.rescheduleRespond.RescheduleResponse;
import com.newco.marketplace.api.mobile.beans.saveFilter.FilterCriterias;
import com.newco.marketplace.api.mobile.beans.saveFilter.SaveFilterRequest;
import com.newco.marketplace.api.mobile.beans.so.accept.MobileSOAcceptResponse;
import com.newco.marketplace.api.mobile.beans.so.search.CustInfo;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.MobileSOSearchResponse;
import com.newco.marketplace.api.mobile.beans.so.search.OrderDetail;
import com.newco.marketplace.api.mobile.beans.so.search.OrderDetails;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchCriteria;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchRequest;
import com.newco.marketplace.api.mobile.beans.so.search.advance.MobileSOAdvanceSearchResponse;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.AddOn;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.AddOnList;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.AlternateServiceLocation;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Appointment;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Bid;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.BidList;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.BiddingDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Buyer;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.BuyerReference;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.BuyerReferences;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.CounterOfferDetail;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.CounterOfferDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Document;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Documents;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetail;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.GeneralSection;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.InvoiceDocuments;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.InvoicePart;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.InvoicePartsList;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.InvoiceSupplier;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.InvoiceSuppliers;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.LatestTrip;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Merchandise;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Merchandises;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Note;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Notes;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Part;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Parts;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.PickUpLocation;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ProblemDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Provider;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ReceivedOrderEstimateDetail;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ReceivedOrderEstimateDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.RescheduleDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.RetrieveSODetailsMobile;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOTrip;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOTripChangeLog;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOTripChangeLogs;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOTrips;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Scope;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ServiceLocation;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.ServiceOrderDetails;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SupportNote;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SupportNotes;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Task;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.Tasks;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleRequest;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.MobileSOSubmitRescheduleResponse;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.RescheduleVo;
import com.newco.marketplace.api.mobile.beans.submitReschedule.v3.SORescheduleInfo;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.MobileSOSubmitWarrantyHomeReasonCodeResponse;
import com.newco.marketplace.api.mobile.beans.submitWarrantyHomeReason.v3.WarrantyHomeReasonInfo;
import com.newco.marketplace.api.mobile.beans.viewDashboard.Tab;
import com.newco.marketplace.api.mobile.beans.viewDashboard.Tabs;
import com.newco.marketplace.api.mobile.beans.viewDashboard.ViewDashboardResponse;
import com.newco.marketplace.api.mobile.beans.vo.AcceptVO;
import com.newco.marketplace.api.mobile.beans.vo.CounterOfferVO;
import com.newco.marketplace.api.mobile.beans.vo.ForgetUnamePwdVO;
import com.newco.marketplace.api.mobile.beans.vo.ProviderParamVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersCriteriaVO;
import com.newco.marketplace.api.mobile.beans.vo.RecievedOrdersVO;
import com.newco.marketplace.api.mobile.beans.vo.UserDetailVO;
import com.newco.marketplace.api.mobile.beans.vo.WarrantyHomeReasonInfoVO;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.utils.convertors.DateConvertor;
import com.newco.marketplace.api.mobile.utils.mappers.ProviderSearchSOMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.BidDetailsVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.ServiceOrderBidModel;
import com.newco.marketplace.dto.vo.dateTimeSlots.ScheduleServiceSlot;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.reasonCode.ReasonCodeVO;
import com.newco.marketplace.dto.vo.serviceorder.FilterCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.FiltersVO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.vo.InHomeRescheduleVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.ProviderSearchSO;
import com.newco.marketplace.vo.mobile.v2_0.MobileSORejectVO;
import com.newco.marketplace.vo.mobile.v2_0.ResourceIds;
import com.newco.marketplace.vo.mobile.v2_0.SOAdvanceSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchCriteriaVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultVO;
import com.newco.marketplace.vo.mobile.v2_0.SOSearchResultsVO;
import com.newco.marketplace.vo.mobile.v2_0.SecQuestAnsRequestVO;
import com.newco.marketplace.vo.ordermanagement.so.RescheduleVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
/* $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/04/10
 * for mapping phase 2 mobile APIs request and response
 *
 */
/**
 * @author Infosys
 *
 */
/**
 * @author Reen_Jossy
 *
 */
public class MobileGenericMapper extends ProviderSearchSOMapper{

	private static final Logger LOGGER = Logger.getLogger(MobileGenericMapper.class);
	private static final String responseDateFormat = "yyyy-MM-dd";
	private IMobileGenericBO mobileGenericBO;
	/*private IBuyerOutBoundNotificationService  buyerOutBoundNotificationService;
	private IBuyerOutBoundNotificationJMSService buyerOutBoundNotificationJMSService;*/
	private OFHelper ofHelper;
	/**
	 * @param providers
	 * @param assignedProvider 
	 * @return
	 * 
	 * for mapping getSORouteList API response
	 * 
	 */
	public GetEligibleProviderResponse  mapEligibleProviderResponse(List<ProviderResultVO>  providers, com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.eligibleproviders.EligibleProvider assignedProvider){
		List<EligibleProvider> providerDetails = new ArrayList<EligibleProvider>();
		if(null != providers){
			for(ProviderResultVO provider : providers){
				EligibleProvider eligibleProvider = new EligibleProvider();
				eligibleProvider.setResourceId(provider.getResourceId());
				eligibleProvider.setDistancefromSOLocation(provider.getDistanceFromBuyer());
				eligibleProvider.setProviderFirstName(provider.getProviderFirstName());
				eligibleProvider.setProviderLastName(provider.getProviderLastName());
				eligibleProvider.setProviderRespid(provider.getProviderRespid());
				providerDetails.add(eligibleProvider);
			}
		}

		GetEligibleProviderResponse eligibleProviderResponse =  new GetEligibleProviderResponse();
		EligibleProviders eligibleProviders = new EligibleProviders();
		eligibleProviders.setEligibleProviderList(providerDetails);
		eligibleProviderResponse.setEligibleProviders(eligibleProviders);
		if(null != assignedProvider){
			AssignedResource assignedResource = new AssignedResource();
			assignedResource.setAssignedResourceId(assignedProvider.getResourceId());
			if(null != assignedProvider.getProviderFirstName()){
				assignedResource.setAssignedResourceName(assignedProvider.getProviderFirstName()+" "+assignedProvider.getProviderLastName());
			}
			eligibleProviderResponse.setAssignedResource(assignedResource);
		}
		Results results = Results.getSuccess();
		if(null == providers || (null != providers && 0 == providers.size())){
			results = Results.getError(ResultsCode.NO_PROVIDERS_AVAILABLE.getMessage(), ResultsCode.NO_PROVIDERS_AVAILABLE.getCode());
		}
		eligibleProviderResponse.setResults(results);
		return eligibleProviderResponse;
	}


	/**
	 * @param mobileSOSearchRequest
	 * @param providerId
	 * @param firmId
	 * @param roleId 
	 * @return
	 * 	 * 	 * for mapping search so request
	 */
	public SOSearchCriteriaVO mapSoSearchRequest(MobileSOSearchRequest mobileSOSearchRequest,
			Integer providerId, Integer firmId, Integer roleId) {
		// MobileSOSearchCriteria soSearchCriteria = mobileSOSearchRequest.getSearchCriteria();

		SOSearchCriteriaVO soSearchCriteriaVO = null;
		soSearchCriteriaVO = mapSOSearchCriteriaVO(mobileSOSearchRequest,providerId,roleId);
		// soSearchCriteriaVO = mapSearchDates(soSearchCriteriaVO,soSearchCriteria);
		soSearchCriteriaVO.setFirmId(firmId);
		return soSearchCriteriaVO;
	}

	/**
	 * @param soSearchCriteriaVO
	 * @param soSearchCriteria
	 * @return
	 * 		// setting the createdStartDate and createEndDate
	 */
	private SOAdvanceSearchCriteriaVO mapSearchDates(
			SOAdvanceSearchCriteriaVO soSearchCriteriaVO,
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest) {
		MobileSOAdvanceSearchCriteria advanceSearchCriteria = mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria();
		String startRangeTimes = null;
		String endRangeTimes = null;
		String startRange = null;
		String endRange = null;
		SimpleDateFormat sdfToString = new SimpleDateFormat(
				PublicMobileAPIConstant.SEARCH_CRITERIA_INPUT_FORMAT);
		SimpleDateFormat sdfFromString = new SimpleDateFormat(
				PublicMobileAPIConstant.SEARCH_CRITERIA_SEARCH_FORMAT);

		if(null != advanceSearchCriteria.getAppointment()){
			com.newco.marketplace.api.mobile.beans.so.search.advance.Appointment appointment = advanceSearchCriteria.getAppointment();
			soSearchCriteriaVO.setAppointmentCriteriaType(appointment.getAppointmentFilter());
			if(MPConstants.APPOINTMENT_VALUE_RANGE.equals(appointment.getAppointmentFilter())){
				startRange = appointment.getStartRange();
				endRange = appointment.getEndRange();
			}

		}
		try {
			if(null!=startRange && StringUtils.isNotBlank(startRange)){
				Date startDate = null;
				startDate = sdfFromString.parse(startRange);
				startRangeTimes = sdfToString.format(startDate);
			}
			if(null!=endRange && StringUtils.isNotBlank(endRange)){
				Date endDate = null;
				endDate = sdfFromString.parse(endRange);
				endRangeTimes = sdfToString.format(endDate);
			}	
			soSearchCriteriaVO.setAppointmentStartRange(startRangeTimes);
			soSearchCriteriaVO.setAppointmentEndRange(endRangeTimes);


		} catch (Exception e) {
			LOGGER.info("Exception Occurred while setting Service Start Time and End time: "+e.getMessage());
		}
		return soSearchCriteriaVO;
	}


	/**
	 * @param mobileSOSearchRequest
	 * @param securityContext
	 * @param roleId 
	 * @return
	 * 
	 * map the search parameters
	 */
	private SOSearchCriteriaVO mapSOSearchCriteriaVO(
			MobileSOSearchRequest mobileSOSearchRequest,
			Integer resourceId, Integer roleId) {

		MobileSOSearchCriteria soSearchCriteria = mobileSOSearchRequest.getSearchCriteria();
		Integer pageSize = mobileSOSearchRequest.getPageSize();
		Integer pageNo = mobileSOSearchRequest.getPageNo();
		SOSearchCriteriaVO soSearchCriteriaVO = new SOSearchCriteriaVO();

		pageSize = ((null != pageSize) ? pageSize
				: PublicMobileAPIConstant.DEFAULT_PAGE_SIZE);

		pageNo = ((null != pageNo) ? pageNo :  PublicMobileAPIConstant.DEFAULT_PAGE_NUMBER);

		soSearchCriteriaVO.setPageSize(pageSize);
		soSearchCriteriaVO.setPageNo(pageNo);

		soSearchCriteriaVO
		.setPageLimit((pageSize * (pageNo - 1)) );


		if(null != soSearchCriteria.getZipCodes()){
			soSearchCriteriaVO.setZipCodes(soSearchCriteria.getZipCodes().getValue());
		}
		/*	if(null != soSearchCriteria.getCityNames()){
			soSearchCriteriaVO.setCityNames(soSearchCriteria.getCityNames().getValue());
		}*/
		if(null != soSearchCriteria.getCustNames()){
			soSearchCriteriaVO.setCustNames(soSearchCriteria.getCustNames().getValue());
		}
		if(null != soSearchCriteria.getCustPhones()){
			soSearchCriteriaVO.setCustPhones(soSearchCriteria.getCustPhones().getValue());
			List<String> custPhonesFormatted = formatCustPhones(soSearchCriteria.getCustPhones().getValue());
			if(null != custPhonesFormatted && ! custPhonesFormatted.isEmpty()){
				soSearchCriteriaVO.setCustPhonesFormatted(custPhonesFormatted);
			}
		}

		if(null != soSearchCriteria.getSoIds()){
			soSearchCriteriaVO.setSoIds(soSearchCriteria.getSoIds().getValue());
		}
		soSearchCriteriaVO.setAcceptedResourceId(resourceId);

		soSearchCriteriaVO.setRoleId(roleId);
		return soSearchCriteriaVO;

	}


	/**
	 * @param value
	 * @return
	 */
	private List<String> formatCustPhones(List<String> custPhones) {

		List<String> custPhonesFormatted = null;
		if(!custPhones.isEmpty()){
			custPhonesFormatted = new ArrayList<String>();
			for(String custPhone : custPhones){

				if(StringUtils.isNotBlank(custPhone)){
					custPhonesFormatted.add(SOPDFUtils.formatPhoneNumber(custPhone));
				}
			}
		}
		return custPhonesFormatted;
	}


	/**
	 * @param soSearchResultsVO
	 * @return
	 * 
	 * to map search API response
	 * 
	 */
	public MobileSOSearchResponse mapProviderSOSearchResponse(
			SOSearchResultsVO soSearchResultsVO) {

		List<SOSearchResultVO> searchResultVOs = soSearchResultsVO.getSearchResultVOs();
		MobileSOSearchResponse mobileSOSearchResponse = new MobileSOSearchResponse();
		Results results= Results.getSuccess();
		mobileSOSearchResponse.setResults(results);
		OrderDetails orderDetails = new OrderDetails();
		OrderDetail orderDetail =null;
		List<OrderDetail> orderDetailValues = new ArrayList<OrderDetail>();
		if(null!= searchResultVOs && !searchResultVOs.isEmpty()){
			for(SOSearchResultVO searchResultVO : searchResultVOs){
				orderDetail = mapGeneralOrderDetails(searchResultVO);
				/*if(searchResultVO.isGroupInd()){
					orderDetail = mapGroupedSODetails(searchResultVO,orderDetail);
				}*/
				orderDetail = mapServiceOrderDates(searchResultVO,orderDetail);
				orderDetailValues.add(orderDetail);
			}
		}
		orderDetails.setOrderDetail(orderDetailValues);
		mobileSOSearchResponse.setTotalOrderCountFetched(orderDetailValues.size());
		mobileSOSearchResponse.setTotalOrderCount(soSearchResultsVO.getTotalSOCount());
		mobileSOSearchResponse.setOrderDetails(orderDetails);
		return mobileSOSearchResponse;

	}



	/**
	 * @param searchResultVO
	 * @param orderDetail
	 * @return
	 */
	private OrderDetail mapServiceOrderDates(SOSearchResultVO searchResultVO,
			OrderDetail orderDetail) {
		Timestamp servciceStartDate = searchResultVO.getAppointStartDate();
		Timestamp serviceEndDate = searchResultVO.getAppointEndDate();
		String serviceWindowStartTime = searchResultVO.getServiceTimeStart();
		String serviceWindowEndTime = searchResultVO.getServiceTimeEnd();
		String timeZone = searchResultVO.getServiceLocationTimezone();
		String date = null;
		HashMap<String, Object> apptStartDate = null;
		HashMap<String, Object> apptEndDate = null;

		if(null != servciceStartDate && StringUtils.isNotEmpty(serviceWindowStartTime)){
			orderDetail.setServiceLocationTimezone(TimeUtils.getTimeZoneForAPI(servciceStartDate,timeZone));
			apptStartDate =  TimeUtils.convertGMTToGivenTimeZone(servciceStartDate, serviceWindowStartTime, timeZone);
			if(null != apptStartDate){
				if(apptStartDate.containsKey(OrderConstants.GMT_DATE)){
					date = apptStartDate.get(OrderConstants.GMT_DATE).toString();
					date = DateUtils.getDateAndTimeFromString(date,responseDateFormat);
					orderDetail.setAppointStartDate(date);
				}
				if(apptStartDate.containsKey(OrderConstants.GMT_TIME)){
					orderDetail.setServiceTimeStart(apptStartDate.get(OrderConstants.GMT_TIME).toString());
				}
			}

		}
		if(null != serviceEndDate && StringUtils.isNotEmpty(serviceWindowEndTime)){
			apptEndDate =  TimeUtils.convertGMTToGivenTimeZone(serviceEndDate, serviceWindowEndTime, timeZone);
			if(null != apptEndDate){
				if(apptEndDate.containsKey(OrderConstants.GMT_DATE)){
					date = apptEndDate.get(OrderConstants.GMT_DATE).toString();
					date = DateUtils.getDateAndTimeFromString(date,responseDateFormat);
					orderDetail.setAppointEndDate(date);
				}
				if(apptEndDate.containsKey(OrderConstants.GMT_TIME)){
					orderDetail.setServiceTimeEnd(apptEndDate.get(OrderConstants.GMT_TIME).toString());
				}
			}
		}	
		//R15_2_1 CC-897
		if(OrderConstants.ORDER_STATUS_RECIEVED.equals(searchResultVO.getSoStatus())){
			DateFormat requiredDateFormat = new SimpleDateFormat(MPConstants.REQUIRED_DATE_FORMAT);
			Date receivedDate;
			try {
				receivedDate = requiredDateFormat.parse(searchResultVO.getRoutedDate());
				orderDetail.setRecievedDate(requiredDateFormat.format(receivedDate));
			} catch (ParseException e) {
				LOGGER.error("ParseException occured while setting the received Date");
				e.printStackTrace();
			} catch(Exception e){
				LOGGER.error("Exception occured while setting the received Date");
			}
		}
		return orderDetail;
	}


	/**
	 * @param searchResultVO
	 * @return
	 * map order details
	 */
	private OrderDetail mapGeneralOrderDetails(SOSearchResultVO searchResultVO) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setAcceptedResource(searchResultVO.getAcceptedResource());
		orderDetail.setAcceptedVendor(searchResultVO.getAcceptedVendor());
		orderDetail.setAcceptedResourceId(searchResultVO.getAcceptedResourceId());
		orderDetail.setAcceptedVendorId(searchResultVO.getAcceptedVendorId());
		orderDetail.setAssignmentType(searchResultVO.getAssignmentType());
		orderDetail.setBusinessName(searchResultVO.getBusinessName());
		//R15_2_1 CC-897
		orderDetail.setBuyerName(searchResultVO.getBusinessName());
		orderDetail.setBuyerId(searchResultVO.getBuyerId());
		orderDetail.setSoId(searchResultVO.getSoId());
		orderDetail.setGroupId(searchResultVO.getGroupId());
		orderDetail.setTitle(searchResultVO.getTitle());
		orderDetail.setSoStatus(searchResultVO.getSoStatus());
		orderDetail.setSoSubStatus(searchResultVO.getSoSubStatus());
		//SL-20838
		//To check whether it is firm or provider's followupFlag
		if((StringUtils.isBlank(searchResultVO.getAssignmentType()) || searchResultVO.getAssignmentType().equals(MPConstants.FIRM))){
			if((MPConstants.ONE).equals(searchResultVO.getFirmFollowUpFlag())){
				orderDetail.setFollowUpFlag(true);
			}else{
				orderDetail.setFollowUpFlag(false);
			}
		}
		else if(searchResultVO.getAssignmentType().equals(MPConstants.PROVIDER)){
			if((MPConstants.ONE).equals(searchResultVO.getProviderFollowUpFlag())){
				orderDetail.setFollowUpFlag(true);
			}else{
				orderDetail.setFollowUpFlag(false);
			}
		}
		//R16_2_0_1: SL-21264: including price model
		orderDetail.setPriceModel(searchResultVO.getPriceModel());
		
		mapCustomerInfo(orderDetail,searchResultVO);
		return orderDetail;
	}


	/**
	 * @param orderDetail
	 * @param searchResultVO
	 */
	private void mapCustomerInfo(OrderDetail orderDetail,
			SOSearchResultVO searchResultVO) {
		CustInfo custInfo = null;
		String street1 = searchResultVO.getStreet1();
		String street2 = searchResultVO.getStreet2();
		String city = searchResultVO.getCity();
		String state = searchResultVO.getStateCd();
		String zip = searchResultVO.getZip();
		String endCustName = searchResultVO.getEndCustomerName();

		if(null != searchResultVO){
			custInfo = new CustInfo();
			if(StringUtils.isNotEmpty(street1)){
				custInfo.setStreet1(street1);
			}
			if(StringUtils.isNotEmpty(street2)){
				custInfo.setStreet2(street2);
			}
			if(StringUtils.isNotEmpty(city)){
				custInfo.setCity(city);
			}
			if(StringUtils.isNotEmpty(state)){
				custInfo.setState(state);
			}
			if(StringUtils.isNotEmpty(zip)){
				custInfo.setZip(zip);
			}
			if(StringUtils.isNotEmpty(endCustName)){
				custInfo.setEndCustomerName(endCustName);
			}
		}
		orderDetail.setCustInfo(custInfo);
	}


	/**
	 * @param searchResultVO
	 * @param orderDetail
	 * @return
	 * to map grouped order details
	 */
	/*private OrderDetail mapGroupedSODetails(SOSearchResultVO searchResultVO,
			OrderDetail orderDetail) {

		List<OrderDetail> childOrderDetailsList = new ArrayList<OrderDetail>();
		List<SOSearchResultVO> childOrderRetrievedDetails = null;
		OrderDetail childOrderDetail = null;
		childOrderRetrievedDetails = searchResultVO.getChildOrders();
		ChildOrderDetails childOrderDetails = null;
		if(null != childOrderRetrievedDetails && !childOrderRetrievedDetails.isEmpty()){
			childOrderDetails = new ChildOrderDetails();
			for(SOSearchResultVO soSearchResultVO : childOrderRetrievedDetails){
				childOrderDetail = mapGeneralOrderDetails(soSearchResultVO);
				childOrderDetailsList.add(childOrderDetail);

			}
			orderDetail.setTitle(searchResultVO.getParentGroupTitle());
			orderDetail.setGroupInd(Boolean.TRUE);
			childOrderDetails.setChildOrderDetail(childOrderDetailsList);
			orderDetail.setChildOrderDetails(childOrderDetails);
		}
		return orderDetail;
	}*/

	/**
	 * Method to Map the assign API response
	 * @return
	 */
	public AssignServiceOrderResponse mapAssignSOProviderResponse(ProcessResponse processResponse) {
		AssignServiceOrderResponse response;
		if(processResponse == null) {
			//Create error response when ProcessResponse is null.
			response = AssignServiceOrderResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {

			response = new AssignServiceOrderResponse(Results.getSuccess(
					processResponse.getMessage()));	

		}  else if (ResultsCode.ASSIGN_SO_SUCCESS.getCode().equalsIgnoreCase(processResponse.getCode())
				||ResultsCode.SO_REASSIGN_SUCCESS.getCode().equalsIgnoreCase(processResponse.getCode())){
			
			response = new AssignServiceOrderResponse(Results.getSuccess(processResponse.getCode(),
					processResponse.getMessage()));	
		}
		else {
			LOGGER.error("Error occured in Accepting SO, error code: " 
					+ processResponse.getCode() 
					+ ", message: " + processResponse.getMessage());
			response = new AssignServiceOrderResponse(Results.getError(processResponse.getMessage(), processResponse.getCode()));
		}
		return response;	
	}
	/**
	 * 
	 * @param request
	 * @return
	 * Method to Map the request parameters to a ReportProblemVO
	 */
	public AssignVO mapAssignSORequest(AssignServiceOrderRequest request,Integer roleId,Integer resourceId) {
		AssignVO assignVO = new AssignVO();
		assignVO.setSoId(request.getSoId());
		assignVO.setResourceId(request.getResourceId());
		assignVO.setFirmId(Integer.parseInt(request.getFirmId()));
		assignVO.setReassignComment(request.getReassignComment());
		assignVO.setRequestFor(request.getRequestFor());
		assignVO.setRoleId(roleId);
		assignVO.setUrlResourceId(resourceId);
		return assignVO;
	}

	/*public ReportProblemVO mapResolveProblemRequest(ResolveProblemOnSORequest request) {
		ReportProblemVO problemVO = new ReportProblemVO();
		problemVO.setSoId(request.getSoId());
		problemVO.setResourceId(request.getResourceId());
		problemVO.setResolutionComments(request.getResolutionComments());
		problemVO.setType(MPConstants.RESOLVE_PROBLRM);
		return problemVO;
	}*/

	/**
	 * Method to map the CounterOfferRequest parameters to CounterOfferVO
	 * @param request
	 * @param soId
	 * @param so
	 * @param providerResourceId
	 * @param firmId
	 * @param groupInd
	 * @param groupId
	 * @return CounterOfferVO
	 */
	public CounterOfferVO mapCounterOfferRequest(CounterOfferRequest request, String soId, ServiceOrder so, 
			Integer providerResourceId, String firmId/*, String groupInd, String groupId*/,int roleId) {
		SimpleDateFormat sdfTime = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Timestamp conditionalExpirationDate = null;
		CounterOfferVO offerVO = new CounterOfferVO();
		if(null!=request){
			offerVO.setSoId(soId);
			offerVO.setSo(so);
			offerVO.setProviderResourceId(providerResourceId);
			offerVO.setOfferExpirationDate(request.getOfferExpirationDate());
			offerVO.setResourceIds(request.getResourceIds());
			offerVO.setServiceDateTime1(request.getServiceDateTime1());
			offerVO.setServiceDateTime2(request.getServiceDateTime2());
			offerVO.setSpendLimit(request.getSpendLimit());
			offerVO.setFirmId(firmId);
			offerVO.setReasonCodes(request.getReasonCodes());
			/*offerVO.setGroupInd(groupInd);
			offerVO.setGroupId(groupId);*/
			offerVO.setRoleId(roleId);
		}
		if (null != offerVO.getServiceDateTime1()) {
			try {
				//set serviceDate1 in date format
				offerVO.setServiceDate1(new Timestamp(DateUtils.defaultFormatStringToDate(offerVO.getServiceDateTime1()).getTime()));
				//set serviceStartTime
				Date serviceStartDate = CommonUtility.sdfToDate.parse(offerVO.getServiceDateTime1());
				String serviceStartTimeStr = sdfTime.format(serviceStartDate);
				serviceStartTimeStr = serviceStartTimeStr.substring(11,
						serviceStartTimeStr.length());
				offerVO.setServiceTimeStart(serviceStartTimeStr);
			} catch (ParseException e) {//ignoring this exception here as this is handled in validate method
				LOGGER.error("ParseException Occurred while setting serviceStartDate..");
			}catch (Exception e) {
				LOGGER.error("Exception Occurred while setting serviceStartDate..");
			}
		}	

		if (null != offerVO.getServiceDateTime1() && null != offerVO.getServiceDateTime2()) {
			try {
				//set serviceDate2 in date format
				offerVO.setServiceDate2(new Timestamp(DateUtils.defaultFormatStringToDate(offerVO.getServiceDateTime2()).getTime()));
				// set serviceEndTime
				Date serviceEndDate = CommonUtility.sdfToDate.parse(offerVO.getServiceDateTime2());
				String serviceEndTimeStr = sdfTime.format(serviceEndDate);
				serviceEndTimeStr = serviceEndTimeStr.substring(11,
						serviceEndTimeStr.length());
				offerVO.setServiceTimeEnd(serviceEndTimeStr);
			} catch (ParseException e) {//ignoring this exception here as this is handled in validate method
				LOGGER.error("ParseException Occurred while setting serviceEndDate..");
			}catch (Exception e) {
				LOGGER.error("Exception Occurred while setting serviceEndDate..");
			}
		}

		if (null != offerVO.getOfferExpirationDate()) {
			try {
				//set offerExpirationDate in date format
				offerVO.setConditionalExpirationDate(new Timestamp(DateUtils.defaultFormatStringToDate(offerVO.getOfferExpirationDate()).getTime()));
				//set conditionalExpirationTime
				Date offerExpirationDate = CommonUtility.sdfToDate.parse(offerVO.getOfferExpirationDate());
				String offerExpirationTimeStr = sdfTime.format(offerExpirationDate);
				offerExpirationTimeStr = offerExpirationTimeStr.substring(11,
						offerExpirationTimeStr.length());
				offerVO.setConditionalExpirationTime(offerExpirationTimeStr);
				
				conditionalExpirationDate = new Timestamp(
						TimeUtils
								.combineDateAndTime(
										Timestamp
												.valueOf(offerVO.getConditionalExpirationDate().toString()),offerExpirationTimeStr,so.getServiceLocationTimeZone()).getTime());
				
				offerVO.setConditionalExpirationDate(conditionalExpirationDate);
			} catch (ParseException e) {//ignoring this exception here as this is handled in validate method
				LOGGER.error("ParseException Occurred while setting offerExpirationDate..");
			}catch (Exception e) {
				LOGGER.error("Exception Occurred while setting offerExpirationDate..");
			}
		}

		//if resource list is null, place counter offer for the logged in resourceId
		if(null == offerVO.getResourceIds() || null == offerVO.getResourceIds().getResourceId() 
				|| offerVO.getResourceIds().getResourceId().isEmpty()){
			CounterOfferResources resourceIds = new  CounterOfferResources();
			List<Integer> resourceId = new ArrayList<Integer>();
			resourceId.add(providerResourceId);
			resourceIds.setResourceId(resourceId);

			offerVO.setResourceIds(resourceIds);
		}
		return offerVO;
	}

	/**
	 * Method to map the CounterOfferVO parameters to CounterOfferResponse
	 * 
	 * @param response
	 * @param pResponse
	 * @return CounterOfferResponse
	 */
	public CounterOfferResponse mapCounterOfferResponse(CounterOfferResponse response, ProcessResponse pResponse) {
		Results results = new Results();
		if(ServiceConstants.VALID_RC.equals(pResponse.getCode())){
			results = Results.getSuccess(ResultsCode.COUNTER_OFFER_SUBMITTED.getMessage());
		}else{
			String errorMessage="";
			if(StringUtils.isNotBlank(pResponse.getMessage()) && pResponse.getMessage().contains("Please enter an expiration date in the future.")){
				errorMessage=pResponse.getMessage();
			}else{
				errorMessage=ResultsCode.COUNTER_OFFER_ERROR.getMessage();
			}
			results = Results.getError(errorMessage, ResultsCode.COUNTER_OFFER_ERROR.getCode());
		}
		response.setResults(results);
		return response;
	}

	/**
	 * Method to map the CounterOfferVO parameters to WithdrawCounterOfferResponse
	 * 
	 * @param response
	 * @param pResponse
	 * @return CounterOfferResponse
	 */
	public WithdrawCounterOfferResponse mapCounterOfferResponse(WithdrawCounterOfferResponse response, ProcessResponse pResponse) {
		Results results = new Results();
		if(ServiceConstants.VALID_RC.equals(pResponse.getCode())){
			results = Results.getSuccess(ResultsCode.COUNTER_OFFER_WITHDRAWN.getMessage());
		}else{
			results = Results.getError(ResultsCode.COUNTER_OFFER_WITHDRAWN_ERROR.getMessage(), ResultsCode.COUNTER_OFFER_WITHDRAWN_ERROR.getCode());
		}
		response.setResults(results);
		return response;
	}

	/**@Description : Map the individual reasonCodesVo list to ReasonCode 
	 * @param reasonCodeVoList
	 * @param reasonType
	 * @return
	 */
	public ReasonCode mapReasonCode(List<ReasonCodeVO>reasonCodeVoList,String reasonType){
		/*//*1 .Create Codes codes object
		 *   2. Set codesList to codes object
		 *   3 .create ReasonCode reasonCodes object
		 *   4. Set codes to reasonCodes object
		 *   5. Set reasonType to reasonCodes object  
		 */
		// Mapping reasonCodes VO to Codes Vo
		Code code =null;
		List<Code> codesList = new ArrayList<Code>();
		if(null!=reasonCodeVoList && !(reasonCodeVoList.isEmpty()) ){ 
			for(ReasonCodeVO reasoncodeVO:reasonCodeVoList){
				code = new Code();
				code.setReasonCodeId(reasoncodeVO.getReasonCodeId());
				code.setReasonCode(reasoncodeVO.getReasonCode());
				codesList.add(code);
			}
		}
		Codes codesObj = new Codes();
		codesObj.setCode(codesList);
		ReasonCode reasonCodes = new ReasonCode();
		reasonCodes.setCodes(codesObj);
		reasonCodes.setReasonCodeType(reasonType);
		return reasonCodes;
	}

	
	/**
	 * @param submitRescheduleRequest
	 * @param timeZone
	 * @return
	 */
	public SOSchedule mapRescheduleRequest(MobileSOSubmitRescheduleRequest submitRescheduleRequest, String timeZone) {

		
		SOSchedule returnVal = new SOSchedule();
	    
	    //populate the broken up date and time
		RescheduleVo rescheduleVO = populateInfoFields(submitRescheduleRequest.getSoRescheduleInfo());
	    
	    //retrieve values 
		String rescheduleType = submitRescheduleRequest.getSoRescheduleInfo().getScheduleType();
        if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DATETYPE_FIXED,rescheduleType)){
	        returnVal.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
	    }else if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DATETYPE_RANGE,rescheduleType)){
	        returnVal.setServiceDateTypeId(SOScheduleType.DATERANGE);
	    }
	    returnVal.setServiceDate1(rescheduleVO.getServiceDate1());
	    returnVal.setServiceTimeStart(rescheduleVO.getServiceTimeStart());
	    if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DATETYPE_RANGE,rescheduleType)){//setting the end date only if the date type is range
	    	returnVal.setServiceDate2(rescheduleVO.getServiceDate2());
	    	returnVal.setServiceTimeEnd(rescheduleVO.getServiceTimeEnd());
	    }
	    
	    if(null!=rescheduleVO.getReasonCode()){
        returnVal.setReason(rescheduleVO.getReasonCode().toString());
	    }
	    returnVal.setCreatedViaAPI(true);
	    return returnVal;
		
	}
	
	public WarrantyHomeReasonInfoVO mapWarrantyHomeReason(WarrantyHomeReasonInfo reasonInfo){
		LOGGER.info("Entering into mapWarrantyHomeReason() of MobileGenericMapper");
		LOGGER.info("Home warranty Reason code :"+reasonInfo.getReasonCode());
		WarrantyHomeReasonInfoVO warrantyHomeReasonInfoVO = new WarrantyHomeReasonInfoVO();
		warrantyHomeReasonInfoVO.setReasonCode(reasonInfo.getReasonCode());
		warrantyHomeReasonInfoVO.setComments(reasonInfo.getComments());
		LOGGER.info("Exiting from mapWarrantyHomeReason() of MobileGenericMapper");
		return warrantyHomeReasonInfoVO;
	}
	
	/**
	 * @param rescheduleInfo
	 * @return
	 */
	private RescheduleVo populateInfoFields(SORescheduleInfo rescheduleInfo) {
		RescheduleVo rescheduleVO = new RescheduleVo();
		
		SimpleDateFormat sdfToTime = new SimpleDateFormat("hh:mm a");
		LOGGER.info("Entering SORescheduleMapper.mapServiceOrder()");
		rescheduleVO.setServiceDate1(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime1()).getTime()));
		// setting the servicestartTime
		Date serviceStartTime = null;
		try {
			serviceStartTime = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime1());
		} catch (ParseException e) {
			LOGGER.error("Exception Occurred while setting Service "
					+ "Start Time");
		}

		String serviceStartTimeStr = sdfToTime.format(serviceStartTime);
		rescheduleVO.setServiceTimeStart(serviceStartTimeStr);
		
		if(null!=rescheduleInfo.getServiceDateTime2()){
			rescheduleVO.setServiceDate2(new Timestamp(DateUtils.defaultFormatStringToDate(rescheduleInfo.getServiceDateTime2()).getTime()));
			// setting the serviceEndTime
			Date serviceEndDate = null;
			try {
				serviceEndDate = CommonUtility.sdfToDate.parse(rescheduleInfo.getServiceDateTime2());
			} catch (ParseException e) {
				LOGGER.error("Parse Exception Occurred while "
						+ "setting ServiceEndDate");
			}
			String serviceEndTimeStr = sdfToTime.format(serviceEndDate);
			rescheduleVO.setServiceTimeEnd(serviceEndTimeStr);
		}
		rescheduleVO.setReasonCode(rescheduleInfo.getReasonCode());//setting the reason code from request to VO
		LOGGER.info("Leaving SORescheduleMapper.mapServiceOrder()");
		return rescheduleVO;
		
	}
	
	/**
	 * @param rescheduleVOFromDB
	 * @param soSchedule
	 * @return
	 */
	public InHomeRescheduleVO mapInHomeRescheduleVO(com.newco.marketplace.vo.ordermanagement.so.RescheduleVO rescheduleVOFromDB,SOSchedule soSchedule){
		InHomeRescheduleVO notificationServiceVO = new InHomeRescheduleVO();
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		try {
			notificationServiceVO.setRescheduleDate1(formatDate(format,soSchedule.getServiceDate1())+" "+soSchedule.getServiceTimeStart());
			if(SOScheduleType.DATERANGE == soSchedule.getServiceDateTypeId()){
				notificationServiceVO.setRescheduleDate2(formatDate(format,soSchedule.getServiceDate2())+" "+soSchedule.getServiceTimeEnd());
			}
		} catch (Exception e) {
			LOGGER.error(e);
		}
		notificationServiceVO.setServiceDate1(rescheduleVOFromDB.getServiceDate1());
		notificationServiceVO.setServiceDate2(rescheduleVOFromDB.getServiceDate2());
		notificationServiceVO.setStartTime(rescheduleVOFromDB.getStartTime());
		notificationServiceVO.setEndTime(rescheduleVOFromDB.getEndTime());
		return notificationServiceVO;
	}
	
	/**
	 * @param rescheduleVOFromDB
	 * @param soSchedule
	 * @return
	 */
	public StringBuffer mapReschedulePeriod(com.newco.marketplace.vo.ordermanagement.so.RescheduleVO rescheduleVOFromDB,SOSchedule soSchedule){
		StringBuffer reschedulePeriod = new StringBuffer();
		String format = OrderConstants.RESCHEDULE_DATE_FORMAT;
		HashMap<String, Object> rescheduleStartDate = null;
    	HashMap<String, Object> rescheduleEndDate = null;
    	try{
    		if(null != rescheduleVOFromDB && null != rescheduleVOFromDB.getRescheduleServiceStartDate()){
    			if (null != rescheduleVOFromDB.getRescheduleServiceStartDate() && null != rescheduleVOFromDB.getRescheduleServiceTime1()) {
    				rescheduleStartDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVOFromDB.getRescheduleServiceStartDate(), rescheduleVOFromDB.getRescheduleServiceTime1(), rescheduleVOFromDB.getServiceLocnTimeZone());
            		if (null != rescheduleStartDate && !rescheduleStartDate.isEmpty()) {
            			reschedulePeriod.append("<br/>Provider edited the reschedule request from ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE)));
            			reschedulePeriod.append(" ");
            			reschedulePeriod.append((String) rescheduleStartDate.get(OrderConstants.GMT_TIME));
            		}
    			}
            	if(null != rescheduleVOFromDB.getRescheduleServiceEndDate() && null != rescheduleVOFromDB.getRescheduleServiceTime2()){
            		rescheduleEndDate = TimeUtils.convertGMTToGivenTimeZone(rescheduleVOFromDB.getRescheduleServiceEndDate(), rescheduleVOFromDB.getRescheduleServiceTime2(), rescheduleVOFromDB.getServiceLocnTimeZone());
            		if (null != rescheduleEndDate && !rescheduleEndDate.isEmpty()) {
            			reschedulePeriod.append(" - ");
            			reschedulePeriod.append(formatDate(format, (Date) rescheduleEndDate.get(OrderConstants.GMT_DATE)));
                		reschedulePeriod.append(" ");
                		reschedulePeriod.append((String) rescheduleEndDate.get(OrderConstants.GMT_TIME));
            		}
            	}
            	reschedulePeriod.append(" "+getTimeZone(formatDate(format, (Date) rescheduleStartDate.get(OrderConstants.GMT_DATE))+" "+(String) rescheduleStartDate.get(OrderConstants.GMT_TIME), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, rescheduleVOFromDB.getServiceLocnTimeZone()));
            	if(null != soSchedule.getServiceDate1() && null != soSchedule.getServiceTimeStart()){
            		reschedulePeriod.append(" to "+formatDate(format,soSchedule.getServiceDate1())+" "+soSchedule.getServiceTimeStart());
            	}
            	if(null != soSchedule.getServiceDate2() && null != soSchedule.getServiceTimeEnd()){
            		reschedulePeriod.append(" - "+formatDate(format,soSchedule.getServiceDate2())+" "+soSchedule.getServiceTimeEnd());
            	}
            	reschedulePeriod.append(" "+getTimeZone(formatDate(format,soSchedule.getServiceDate1())+" "+soSchedule.getServiceTimeStart(), OrderConstants.RESCHEDULE_DATE_TIME_STAMP_FORMAT1, rescheduleVOFromDB.getServiceLocnTimeZone()));
            }
    	}
    	catch(Exception e){
    		LOGGER.error("Exception occured in edit reschedule:"+e);
	    }
		
		return reschedulePeriod;
	}
	
	public String getTimeZone(String modifiedDate, String format, String timeZone){
		Date newDate = null;
		String actualTimeZone = timeZone.substring(0, 3);
		String dayLightTimeZone = timeZone.substring(4, 7);
		try {
			newDate = new SimpleDateFormat(format, Locale.ENGLISH).parse(modifiedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.info("Parse Exception SORescheduleService.java "+ e);
		}
        TimeZone gmtTime = TimeZone.getTimeZone(actualTimeZone);
        if(gmtTime.inDaylightTime(newDate)){
        	return "("+dayLightTimeZone+")";
        }
        return "("+actualTimeZone+")";
   }
	
	private String formatDate(String format, Date date){
		DateFormat formatter = new SimpleDateFormat(format);
		String formattedDate = null;
		try {
			formattedDate = formatter.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;
	}
	public ForgetUnamePwdVO mapForgetUnamePwdService(final ForgetUnamePwdServiceRequest request) throws BusinessServiceException{
		ForgetUnamePwdVO forgetUnamePwdVO = new ForgetUnamePwdVO();
		forgetUnamePwdVO.setRequestFor(request.getRequestFor());
		forgetUnamePwdVO.setEmail(request.getEmail());
		forgetUnamePwdVO.setUserName(request.getUserName());
		if (null != request.getUserId()){
			forgetUnamePwdVO.setUserId(String.valueOf(request.getUserId()));
		}	
		try{
			if(PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
				forgetUnamePwdVO = mobileGenericBO.loadUserProfile(forgetUnamePwdVO);
			}
		}
		catch(Exception e){
			LOGGER.error("Fatal error occured in MobileGenericMapper.mapForgetUnamePwdService:username:"+forgetUnamePwdVO.getUserName());
			LOGGER.error("Fatal error occured in MobileGenericMapper.mapForgetUnamePwdService():"+e.getMessage());
			//error in forget username
			throw new BusinessServiceException(e);
		}
		return forgetUnamePwdVO;
	}

	public RescheduleVO mapRescheduleRespondRequest(RescheduleRespondRequest respondRequest,APIRequestVO apiVO) {
		RescheduleVO rescheduleVO = new RescheduleVO();
		rescheduleVO.setSoId(apiVO.getSOId());
		rescheduleVO.setRescheduleRespondType(respondRequest.getResponseType());
		rescheduleVO.setResourceId(apiVO.getProviderResourceId());
		rescheduleVO.setUrlRoleId(apiVO.getRoleId());
		rescheduleVO.setFirmId(apiVO.getProviderId());
		return rescheduleVO;
	}

	/**Description : This method will be called for creating error response for RecheduleRespond API
	 * @return
	 */
	public RescheduleResponse createErrorResponse(String resceduleType) {
		ArrayList<Object> respondRescheduleType = new ArrayList<Object>();
		respondRescheduleType.add(resceduleType);
		RescheduleResponse response = new RescheduleResponse();
		Results results =Results.getError(ResultsCode.RESPOND_RESCHEDULE_NOT_ALLOWED.getMessage(respondRescheduleType), ResultsCode.RESPOND_RESCHEDULE_NOT_ALLOWED.getCode());
		response.setResults(results);
		return response;
	}
	public RescheduleResponse createSuccessResponse(String respondType) {
		ArrayList<Object> respondRescheduleType = new ArrayList<Object>();
		respondRescheduleType.add(respondType);
		RescheduleResponse response = new RescheduleResponse();
		Results results =Results.getSuccess(ResultsCode.RESPOND_RESCHEDULE_SUCCESS.getMessage(respondRescheduleType));
		response.setResults(results);
		return response;
	}
	public RescheduleResponse createErrorResponse(RescheduleVO rescheduleVo) {
		RescheduleResponse response = new RescheduleResponse();
		Results results =Results.getError(rescheduleVo.getValidationCode().getMessage(),rescheduleVo.getValidationCode().getCode());
		response.setResults(results);
		return response;
	}

	public RescheduleResponse createErrorResponseForOFError(String strSuccessCode, String message) {
		RescheduleResponse rescheduleResponse = new RescheduleResponse();
		Results results =Results.getError(message,strSuccessCode);
		rescheduleResponse.setResults(results);
		return rescheduleResponse;
	}

/*	*//**
	 * @param updateScheduleDetailsRequest
	 * @param modifiedByName 
	 * @param soId 
	 * @param providerId 
	 * @return
	 * method for mapping update schedule detail request
	 * 
	 *//*
	public UpdateScheduleVO mapUpdateScheduleRequest(
			UpdateScheduleDetailsRequest updateScheduleDetailsRequest, String soId, String modifiedByName, String providerId) {
		UpdateScheduleVO updateScheduleVO = new UpdateScheduleVO();
		updateScheduleVO.setSoId(soId);
		updateScheduleVO.setProviderId(Integer.parseInt(providerId));
		updateScheduleVO.setSource(updateScheduleDetailsRequest.getSource());
		if(null != updateScheduleDetailsRequest.getCustNotAvailableReasonCode()){
			updateScheduleVO.setReasonId(updateScheduleDetailsRequest.getCustNotAvailableReasonCode().toString());
		}
		if(null != updateScheduleDetailsRequest.getCustAvailableResponseReasonCode()){
			updateScheduleVO.setCustAvailableRespCode(updateScheduleDetailsRequest.getCustAvailableResponseReasonCode());
		}

		updateScheduleVO.setEta(updateScheduleDetailsRequest.getEta());
		updateScheduleVO.setCustomerAvailableFlag(updateScheduleDetailsRequest.getCustomerAvailableFlag());
		updateScheduleVO.setSpecialInstructions(updateScheduleDetailsRequest.getSpecialInstructions());
		updateScheduleVO.setSoNotes(updateScheduleDetailsRequest.getSoLocNotes());
		updateScheduleVO.setServiceTimeStart(updateScheduleDetailsRequest.getServiceTimeStart());
		updateScheduleVO.setServiceTimeEnd(updateScheduleDetailsRequest.getServiceTimeEnd());
		updateScheduleVO.setModifiedByName(modifiedByName);
		updateScheduleVO.setServiceDateStart(updateScheduleDetailsRequest.getStartDate());
		updateScheduleVO.setServiceDateEnd(updateScheduleDetailsRequest.getEndDate());
		updateScheduleVO.setServiceTimeZone(updateScheduleDetailsRequest.getServiceTimeZone());

		return updateScheduleVO;
	}*/

	public  Date combineDateAndTime(Timestamp ts, String tm, String zone){
		if(zone == null){
			zone = "EST";
		}
		Calendar cal = null;
		int year = 0;
		int month = 0;
		int dayOfMonth = 0;
		int hours = 0;
		int min = 0;
		int sec = 0;
		String timeType = null;
		DateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf_yyyymmdd.format(ts);
		if (dt != null && tm != null && tm.length()==8 && zone != null) {
			try {
				year = Integer.parseInt(dt.substring(0, 4));
				month = Integer.parseInt(dt.substring(5, 7));
				dayOfMonth = Integer.parseInt(dt.substring(8, 10));

				hours = Integer.parseInt(tm.substring(0, 2));
				min = Integer.parseInt(tm.substring(3, 5));
				sec = Integer.parseInt("00");
				timeType = tm.substring(6, 8);

			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			TimeZone tz = TimeZone.getTimeZone(zone);

			cal = new GregorianCalendar(year, month-1, dayOfMonth,
					hours, min, sec);

			cal.setTimeZone(tz);
			if(timeType.equalsIgnoreCase("AM")){
				cal.set(Calendar.AM_PM,Calendar.AM);
			}else if(timeType.equalsIgnoreCase("PM")){
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
		}
		return cal.getTime();

	}
	public MobileSOSubmitRescheduleResponse createSubmitRescheduleResponse(ProcessResponse processResponse) {

		MobileSOSubmitRescheduleResponse response = null;
		if(processResponse == null) {			
			response = MobileSOSubmitRescheduleResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		}
		else if (processResponse.getCode() == PublicAPIConstant.REQUEST_ACCEPTED) {				
			response = new MobileSOSubmitRescheduleResponse(Results.getSuccess(
					ResultsCode.RESCHEDULE_REQUEST_ACCEPTED.getMessage()));
		}
		else if(processResponse.getCode() == PublicAPIConstant.REQUEST_PROCESSED){
			response = new MobileSOSubmitRescheduleResponse(Results.getSuccess(
					ResultsCode.RESCHEDULE_REQUEST_PROCESSED.getMessage()));
		}
		else if(processResponse.getCode() == PublicAPIConstant.REQUEST_REJECTED){
			response = new MobileSOSubmitRescheduleResponse(Results.getSuccess(
					ResultsCode.RESCHEDULE_REQUEST_REJECTED.getMessage()));					
		} 	
		else 
		{
			LOGGER.error("Error occured in Accepting SO, error code: " 
					+ processResponse.getCode() 
					+ ", message: " + processResponse.getMessage());
			response = new MobileSOSubmitRescheduleResponse(Results.getError(processResponse.getMessage(),"3060"));
		}
		return response;	
	}

	public MobileSOSubmitRescheduleResponse createErrorResponse(MobileSOSubmitRescheduleResponse submitRescheduleResponse) {
		MobileSOSubmitRescheduleResponse response = null;
		if(null != submitRescheduleResponse.getResults()){
			response = new MobileSOSubmitRescheduleResponse();
			response.setResults(submitRescheduleResponse.getResults());
		}
		return response;
	}

	/**
	 * @param request
	 * @param soId
	 * @param groupId
	 * @return
	 */
	public MobileSORejectVO mapRejectRequestToVO(MobileRejectSORequest request,
			APIRequestVO apiRequestVO,String groupId) {
		MobileSORejectVO rejectVO = new MobileSORejectVO();
		Integer roleId = apiRequestVO.getRoleId();
		Integer providerResId = apiRequestVO.getProviderResourceId();
		String soId = apiRequestVO.getSOId();
		rejectVO.setSoId(soId);
		rejectVO.setGroupId(groupId);
		rejectVO.setRoleId(roleId);
		rejectVO.setResourceId(providerResId);
		rejectVO.setFirmId(apiRequestVO.getProviderId());

		if(StringUtils.isNotBlank(request.getReasonCommentDesc())){
			rejectVO.setReasonCommentDesc(request.getReasonCommentDesc());
		}

		if (null != (request.getReasonId())){
			rejectVO.setReasonId(request.getReasonId());
		}
		if (null != request.getResourceList() && null!=request.getResourceList().getResourceId() && !request.getResourceList().getResourceId().isEmpty()) {
			Resource resource = request.getResourceList();
			List<Integer> resourceIds = resource.getResourceId();
			ResourceIds providerIds = new ResourceIds();
			providerIds.setResourceId(resourceIds);
			rejectVO.setResourceList(providerIds);
		}
		return rejectVO;
	}


	/**
	 * Creates success response.
	 * @param rejectResponse {@link ProcessResponse}
	 * @return {@link MobileRejectSOResponse}
	 * */
	public MobileRejectSOResponse createRejectSuccessResponse(
			ProcessResponse rejectResponse) {
		MobileRejectSOResponse rejectSOResponse = new MobileRejectSOResponse();
		Results results = Results.getSuccess(rejectResponse.getMessage());
		rejectSOResponse.setResults(results);
		return rejectSOResponse;
	}

	/**
	 * Creates error response when request parameter validation failed.
	 * @param rejectResponse {@link ProcessResponse}
	 * @return {@link MobileRejectSOResponse}
	 * */
	public MobileRejectSOResponse createRejectErrorResponse(
			ProcessResponse rejectResponse) {
		MobileRejectSOResponse rejectSOResponse = new MobileRejectSOResponse();
		Results results = null;
		if (rejectResponse.getCode().equals(
				PublicMobileAPIConstant.OF_ERROR_CODE)) {
			results = Results.getError(
					ResultsCode.REJECT_SO_FAILED.getMessage(),
					ResultsCode.REJECT_SO_FAILED.getCode());
		} else {
			results = Results.getError(rejectResponse.getMessage(),
					rejectResponse.getCode());
		}
		rejectSOResponse.setResults(results);
		return rejectSOResponse;
	}

	/**@Description: Create generic  error response for getSoList API
	 * @return
	 */
	public GetProviderSOListResponse createErrorResponse() {
		GetProviderSOListResponse response = new GetProviderSOListResponse();
		Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		response.setResults(results);
		return response;
	}
	/**@Description: Create generic  error response for getSoList API
	 * @return
	 */
	public ViewDashboardResponse createErrorResponseForViewDashBoard(ResultsCode resultsCode) {
		ViewDashboardResponse response = new ViewDashboardResponse();
		Results results = null;
		if(null==resultsCode){
			results=Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}else{
			results=Results.getError(resultsCode.getMessage(),resultsCode.getCode());
		}
		response.setResults(results);
		return response;
	}

	/**@Description: Create specific error response for getSoList API
	 * @param resultsCode
	 * @return
	 */
	public GetProviderSOListResponse createGetSoListErrorResponse(ResultsCode resultsCode) {
		GetProviderSOListResponse response = new GetProviderSOListResponse();
		Results results = Results.getError(resultsCode.getMessage(),resultsCode.getCode());
		response.setResults(results);
		return response;
	}

	public ProviderParamVO mapGetSoListRequest(APIRequestVO apiVO) throws BusinessServiceException {
		// Declaring variables
		String pageNo =null;
		String pageSize =null;
		Map<String, String> requestMap = null;
		Integer pageNoInt = null;
		Integer pageSizeInt = null;
		String soStatus = null;
		ProviderParamVO providerParamVO = null;
		try{

			if(null!= apiVO.getRequestFromGetDelete()){
				requestMap = apiVO.getRequestFromGetDelete();
				//Getting pageNo from req Map
				pageNo= requestMap.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_NUMBER);
				if(StringUtils.isBlank(pageNo)){
					// Setting Default Value as 1
					pageNoInt = MPConstants.DEFAULT_PAGE_NO;
				}else if(StringUtils.isNumeric(pageNo)){
					pageNoInt = Integer.parseInt(pageNo);
				}
				//Getting pageSize from req Map
				pageSize = requestMap.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_SIZE);
				if(StringUtils.isBlank(pageSize)){
					// Setting Default Value as 250
					pageSizeInt = MPConstants.DEFAULT_PAGE_SIZE;
				}else if(StringUtils.isNumeric(pageSize)){
					pageSizeInt = Integer.parseInt(pageSize);
				}
				soStatus = requestMap.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.SO_STATUS);
				if(StringUtils.isBlank(soStatus)){
					//Setting Default Value as ACTIVE
					soStatus = MPConstants.ACTIVE_STATUS;
				}

			}// Setting Default values in case the parameter map is not present in request.
			else{
				pageNoInt = MPConstants.DEFAULT_PAGE_NO;
				pageSizeInt = MPConstants.DEFAULT_PAGE_SIZE;
				soStatus = MPConstants.ACTIVE_STATUS;
			}
			//Setting Sorting flag and Sort By Column using constructor.
			providerParamVO = new ProviderParamVO(MPConstants.SORT_FLAG,MPConstants.SORT_BY);
			//Setting values obtained  from reqMap of API Vo
			providerParamVO.setPageNo(pageNoInt);
			providerParamVO.setPageSize(pageSizeInt);
			providerParamVO.setSoStatus(soStatus);
			//Setting firmId,providerId,roleId
			providerParamVO.setVendorId(apiVO.getProviderIdInteger());
			providerParamVO.setResourceId(apiVO.getProviderResourceId());
			providerParamVO.setRoleId(apiVO.getRoleId());
		}catch (Exception e) {
			LOGGER.error("Exception in mapping getProviderSOListService"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerParamVO;
	}

	/*public ForgetUnamePwdService1Response mapResetPasswordResponse(ForgetUnamePwdService1Response response, ForgetUnamePwdVO forgetUnamePwdVO){
		if (StringUtils.isNotBlank(forgetUnamePwdVO.getQuestionId())){
			SecurityQuestion securityQuestion = new SecurityQuestion();
			securityQuestion.setQuestionId(Integer.parseInt(forgetUnamePwdVO.getQuestionId()));
			securityQuestion.setQuestionText(forgetUnamePwdVO.getQuestionTxt());
			response.setSecurityQuestion(securityQuestion);
		}
		response.setResults(forgetUnamePwdVO.getResults());
		return response;
	}*/

	public ForgetUnamePwdServiceResponse mapUserDetailsListResponse(ForgetUnamePwdServiceResponse response, ForgetUnamePwdVO forgetUnamePwdVO){
		 List<UserDetail> userDetailsList = new ArrayList<UserDetail>();
		if(forgetUnamePwdVO.isMultipleUserExists()){
			//List<UserDetail> userDetailsList = new ArrayList<UserDetail>();
			for(UserDetailVO userDetailVO: forgetUnamePwdVO.getUserDetailVOs()){
				UserDetail userDetail = new UserDetail();
				userDetail.setContactName(userDetailVO.getUserFirstName());
				userDetail.setUserId(userDetailVO.getUserId());
				userDetailsList.add(userDetail);
			}
			UserDetails userDetails = new UserDetails(userDetailsList);
			response.setUserDetails(userDetails);
		}
		
		
		if(!(forgetUnamePwdVO.isMultipleUserExists()) && StringUtils.isNotBlank(forgetUnamePwdVO.getQuestionId()) ){
				SecurityQuestion securityQuestion = new SecurityQuestion();
				securityQuestion.setQuestionId(Integer.parseInt(forgetUnamePwdVO.getQuestionId()));
				securityQuestion.setQuestionText(forgetUnamePwdVO.getQuestionTxt());
				response.setSecurityQuestion(securityQuestion);
				// setting requestFor object as userName for both  userName with email and userName with userId scenarios.
				if (forgetUnamePwdVO.getRequestFor().equalsIgnoreCase(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_EMAIL) || forgetUnamePwdVO.getRequestFor().equalsIgnoreCase(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID)){
					forgetUnamePwdVO.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_USERNAME);
				}
				
				UserDetail userDetail = new UserDetail();
				userDetail.setContactName(forgetUnamePwdVO.getUserFirstName());
				if(StringUtils.isNotBlank(forgetUnamePwdVO.getUserId()) && PublicMobileAPIConstant.REQUEST_FOR_USERNAME.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
					userDetail.setUserId(Integer.parseInt(forgetUnamePwdVO.getUserId()));
				}
				userDetail.setEmail(forgetUnamePwdVO.getEmail());
				if (PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
					userDetail.setUserName(forgetUnamePwdVO.getUserName());
				}
				userDetailsList.add(userDetail);
				UserDetails userDetails = new UserDetails(userDetailsList);
				response.setUserDetails(userDetails);
				
				response.setRequestFor(forgetUnamePwdVO.getRequestFor());
		}
		
		if (null!= forgetUnamePwdVO.getResults() && null != forgetUnamePwdVO.getResults().getError() 
				&& null!= forgetUnamePwdVO.getResults().getError().get(0) 
				&& null!= forgetUnamePwdVO.getResults().getError().get(0).getCode() 
				&& ResultsCode.COUNT_EXCEEDED_SHOW_VERIFICATION_ZIP.getCode().equalsIgnoreCase(forgetUnamePwdVO.getResults().getError().get(0).getCode())){
					response = mapUserId(response, forgetUnamePwdVO);
				}

		response.setResults(forgetUnamePwdVO.getResults());

		return response;
	}

	/**@Description: This method maps the reasonCodesTypes 
	 * passed as query @param to a list of string
	 * @param reasonCodeType
	 * @return
	 */
	public List<String> mapReasonCodesTypeToList(String reasonCodeType) {
		List<String> reasonLists = new ArrayList<String>();
		if (StringUtils.isNotBlank(reasonCodeType)) {
			StringTokenizer strTok = new StringTokenizer(reasonCodeType,PublicMobileAPIConstant.SO_SEPERATOR, false);
			int noOfTokens = strTok.countTokens();
			for (int i = 0; i < noOfTokens; i++) {
				String reasonType = new String(strTok.nextToken());
				reasonLists.add(reasonType);
			}
		}
		return reasonLists;
	}

	public GetReasonCodesResponse createReasonCodeSuccessResponse(List<ReasonCode> reasonCodeList) {
		GetReasonCodesResponse response = new GetReasonCodesResponse();
		ReasonCodesList reasonCodesList = new ReasonCodesList();
		reasonCodesList.setReasonCode(reasonCodeList);
		response.setResults(Results.getSuccess());
		response.setReasonCodesList(reasonCodesList);
		return response;
	}

	public GetReasonCodesResponse createErrorResponseReasonCode(ResultsCode invalidReasoncodeType) {
		GetReasonCodesResponse response = new GetReasonCodesResponse();
		Results invalidResponse=new Results();
		invalidResponse = Results.getError(invalidReasoncodeType.getMessage(),invalidReasoncodeType.getCode());
		response.setResults(invalidResponse);
		return response;
	}

	public  MobileSOAcceptResponse createAcceptResponse(final ProcessResponse processResponse) {
		MobileSOAcceptResponse response;
		if(processResponse == null) {
			//Create error response when ProcessResponse is null.
			response = MobileSOAcceptResponse
					.getInstanceForError(ResultsCode.INTERNAL_SERVER_ERROR);
		} else if (processResponse.getCode() == ServiceConstants.VALID_RC) {
			//Create Success response when Valid code is set in process response 
			response = new MobileSOAcceptResponse(Results.getSuccess(
					ResultsCode.SO_ACCEPT_SUCCESS.getMessage()));					
		} else {
			LOGGER.error("Error occured in Accepting SO, error code: " 
					+ processResponse.getCode() 
					+ ", message: " + processResponse.getMessage());
			response = new MobileSOAcceptResponse(Results.getError(processResponse.getMessage(), processResponse.getCode()));
		}
		return response;
	}
	/*public ViewDashboardResponse mapViewDashboardResponse(MobileDashboardVO dashboardVO) {

		ViewDashboardResponse dashboardResponse = new ViewDashboardResponse();
		DashBoardDetails dashBoardDetails = new DashBoardDetails();
		ServiceOrderStatistics serviceOrderStatistics = new ServiceOrderStatistics();
		ProviderRatings ratings = new ProviderRatings();
		ProviderRegistrationStatus providerRegistrationStatus = new ProviderRegistrationStatus();
		ProviderBackgroundCheck providerBackgroundChecks = new ProviderBackgroundCheck();

		serviceOrderStatistics.setAccepted(dashboardVO.getAccepted());
		serviceOrderStatistics.setBidRequest(dashboardVO.getBid());
		serviceOrderStatistics.setBulletinBoard(dashboardVO.getBulletinBoard());
		serviceOrderStatistics.setPendingCancel(dashboardVO.getPendingCancel());
		serviceOrderStatistics.setPendingReschedule(dashboardVO.getPendingReschedule());
		serviceOrderStatistics.setProblem(dashboardVO.getProblem());
		serviceOrderStatistics.setReceived(dashboardVO.getReceived());
		serviceOrderStatistics.setToday(dashboardVO.getTodays());
		serviceOrderStatistics.setPosted(dashboardVO.getPosted());
		serviceOrderStatistics.setDrafted(dashboardVO.getDrafted());
		serviceOrderStatistics.setTotalOrders(dashboardVO.getTotalOrders());

		ratings.setCurrentRating(dashboardVO.getCurrentRating());
		ratings.setLifeTimeRating(dashboardVO.getLifetimeRating());
		ratings.setTotalRatings(dashboardVO.getNumRatingsReceived());

		providerRegistrationStatus.setApproved(dashboardVO.getNumTechniciansApproved());
		providerRegistrationStatus.setUnapproved(dashboardVO.getNumTechniciansUnapproved());

		providerBackgroundChecks.setInProgress(dashboardVO.getBcInProcess());
		providerBackgroundChecks.setNotCleared(dashboardVO.getBcNotCleared());
		providerBackgroundChecks.setNotStarted(dashboardVO.getBcNotStarted());
		providerBackgroundChecks.setPendingSubmission(dashboardVO.getBcPendingSubmission());
		providerBackgroundChecks.setReCertificationDue(dashboardVO.getBcRecertificationDue());
		providerBackgroundChecks.setCleared(dashboardVO.getBcClear());

		dashBoardDetails.setServiceOrderStatistics(serviceOrderStatistics);
		dashBoardDetails.setRatings(ratings);
		dashBoardDetails.setProviderRegistrationStatus(providerRegistrationStatus);
		dashBoardDetails.setProviderBackgroundChecks(providerBackgroundChecks);
		dashBoardDetails.setFirmRegistrationStatus(dashboardVO.getFirmStatus());

		Results results = Results.getSuccess();
		dashboardResponse.setResults(results);		
		dashboardResponse.setDashBoardDetails(dashBoardDetails);

		return dashboardResponse;
	}*/

	public AcceptVO mapAcceptSoRequest(APIRequestVO apiVO, String groupId,Integer resourceIdRequest, boolean acceptByFirmInd) {
		Integer  firmId =null;
		Integer acceptedResourceId =null;
		AcceptVO acceptVO = new AcceptVO();
		if(StringUtils.isNumeric(apiVO.getProviderId())){
			firmId = Integer.parseInt(apiVO.getProviderId());
		} 
		if(!acceptByFirmInd){
			acceptedResourceId = resourceIdRequest;
		}
		acceptVO.setAcceptedResourceId(acceptedResourceId);
		acceptVO.setFirmId(firmId);
		acceptVO.setSoId(apiVO.getSOId());
		acceptVO.setGroupId(groupId);
		acceptVO.setResourceIdUrl(apiVO.getProviderResourceId());
		acceptVO.setResourceIdRequest(resourceIdRequest);
		acceptVO.setAcceptByFirmInd(acceptByFirmInd);
		acceptVO.setRoleId(apiVO.getRoleId());
		return acceptVO;
	}

	public GetProviderSOListResponse mapProviderResponse(List<ProviderSOSearchVO> providerSOList,ProviderParamVO providerParamVO,Integer soCount) throws Exception {
		GetProviderSOListResponse response = new GetProviderSOListResponse();
		ProviderSOList serviceOrderList = new ProviderSOList();
		List<ProviderSearchSO> providerSearchSOList = new ArrayList<ProviderSearchSO>();
		ProviderSearchSO serviceOrder = null;
		try{
			if(null!= providerSOList && !(providerSOList.isEmpty())){
				for(ProviderSOSearchVO  serviceOrderVO : providerSOList){
					serviceOrder = new ProviderSearchSO();
					//Mapping soId,soTitle,Status and SubStatus
					mapServiceOrderDetails(serviceOrderVO,serviceOrder);
					//Mapping service window Date,time,timezone
					mapServiceDateInfo(serviceOrderVO,serviceOrder);
					//Mapping End Customer Contact Details
					mapEndCustomerInfo(serviceOrderVO,serviceOrder);
					//Mapping Buyer name,Logo
					mapBuyerDetails(serviceOrderVO,serviceOrder,providerParamVO);
					//Mapping Server Time and ETA
					mapServerTimeandETA(serviceOrderVO,serviceOrder);
					//Adding to Final List
					providerSearchSOList.add(serviceOrder);

				}
				//Setting So Details
				serviceOrderList.setProviderSearchSO(providerSearchSOList);

			}
			//Setting Total Count
			//Setting in Response.
			//Setting Success Results
			response.setResults(Results.getSuccess());

			serviceOrderList.setTotalSOcount(soCount);
			response.setServiceOrderList(serviceOrderList);


		}catch (Exception e) {
			LOGGER.error("Exception in Mapping Results toi response Object"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return response;
	}

	/**@Description: Mapping Server Time and ETA
	 * @param serviceOrderVO
	 * @param serviceOrder
	 */
	private void mapServerTimeandETA(ProviderSOSearchVO result,ProviderSearchSO providerSearchSO) {
		// Set Schedule Status
		providerSearchSO.setScheduleStatus(result.getScheduleStatus());
		if (StringUtils.isBlank(result.getMerchStatus())) {
			providerSearchSO.setMerchStatus(NO);
		} else {
			providerSearchSO.setMerchStatus(YES);
		}
		// Set Server Date Convert based on the timeZone.
		String serverDateTime = DateConvertor.toString(new Date(),"yyyy-MM-dd hh:mm:ss",OrderConstants.UTC_ZONE);
		if (null != serverDateTime) {
			providerSearchSO.setServerDateTime(serverDateTime);
		}
		if (StringUtils.isNotBlank(result.getEta())) {
			providerSearchSO.setEta(result.getEta());
		}
	}

	/**@param providerParamVO 
	 * @Description: Mapping Buyer Details
	 * @param serviceOrderVO
	 * @param serviceOrder
	 */
	private void mapBuyerDetails(ProviderSOSearchVO result,ProviderSearchSO providerSearchSO, ProviderParamVO providerParamVO) {
		providerSearchSO.setBuyerId(result.getBuyerId());
		// Set Buyer Name
		if (StringUtils.isNotBlank(result.getBuyerName())) {
			providerSearchSO.setBuyerName(result.getBuyerName());
		} 
	}

	/**@Description: Mapping End Customer Address
	 * @param serviceOrderVO
	 * @param serviceOrder
	 */
	private void mapEndCustomerInfo(ProviderSOSearchVO result,ProviderSearchSO providerSearchSO) {
		// Set the Customer First Name
		if (StringUtils.isNotBlank(result.getCustFirstName())) {
			providerSearchSO.setCustomerFirstName(result.getCustFirstName());
		}
		// Set the Customer Last Name
		if (StringUtils.isNotBlank(result.getCustLastName())) {
			providerSearchSO.setCustomerLastName(result.getCustLastName());
		}
		// Set City
		if (StringUtils.isNotBlank(result.getCity())) {
			providerSearchSO.setCity(result.getCity());
		} 
		// Set State
		if (StringUtils.isNotBlank(result.getState())) {
			providerSearchSO.setState(result.getState());
		}
		// Set Zip
		if (StringUtils.isNotBlank(result.getZip())) {
			providerSearchSO.setZip(result.getZip());
		} 
		// Set Street Address 1
		if (StringUtils.isNotBlank(result.getAddress1())) {
			providerSearchSO.setStreetAddress1(result.getAddress1());
		} 

		// Set Street Address 2
		if (StringUtils.isNotBlank(result.getAddress2())) {
			providerSearchSO.setStreetAddress2(result.getAddress2());
		} 

	}


	/**@Description: Mapping So Schedule Details
	 * @param serviceOrderVO
	 * @param serviceOrder
	 */
	private void mapServiceDateInfo(ProviderSOSearchVO result,ProviderSearchSO providerSearchSO) {
		// Start Date
		if (null != result.getServiceStartDate()) {
			providerSearchSO.setServiceStartDate(result.getServiceStartDate());
		}
		// End Date
		if (result.getDateType().equals(Integer.parseInt(RANGE_DATE))) {
			providerSearchSO.setServiceEndDate(result.getServiceEndDate());
		}
		// Set Service Start Time
		providerSearchSO.setServiceWindowStartTime(result.getServiceTime1());

		// Set Service End Time
		if (StringUtils.isBlank(result.getServiceTime2())) {
			providerSearchSO.setServiceTime(providerSearchSO.getServiceWindowStartTime());
		} else {
			providerSearchSO.setServiceWindowEndTime(result.getServiceTime2());
			providerSearchSO.setServiceTime(providerSearchSO.getServiceWindowStartTime()+ " To "+ providerSearchSO.getServiceWindowEndTime());
		}

		// Set timeZone
		if (StringUtils.isNotBlank(result.getServiceLocationTimeZone())) {
			providerSearchSO.setTimeZone(result.getServiceLocationTimeZone());
		} 
	}


	/**@Description: Mapping So Details
	 * @param result
	 * @param providerSearchSO
	 */
	private void mapServiceOrderDetails(ProviderSOSearchVO result,ProviderSearchSO providerSearchSO) {
		// Set SO Title
		if (StringUtils.isNotBlank(result.getSoTitle())) {
			providerSearchSO.setSoTitle(result.getSoTitle());
		} 
		// Set SO Id
		providerSearchSO.setSoId(result.getSoId());
		// Set SO Status
		if (StringUtils.isNotBlank(result.getSoStatus())) {
			providerSearchSO.setSoStatus(result.getSoStatus());
		} 
		// Set SO SubStatus
		if (StringUtils.isNotBlank(result.getSoSubStatus())) {
			providerSearchSO.setSoSubStatus(mapSubstatus(result.getSoSubStatus()));
		}
		if(null != result.getFollowupFlag()){
			providerSearchSO.setFollowupFlag(result.getFollowupFlag());
		}
		// Set Accepted provider/vendor information
		if(StringUtils.isNotBlank(result.getAcceptedResource())){
			providerSearchSO.setAcceptedResource(result.getAcceptedResource());
		}
		if(StringUtils.isNotBlank(result.getAcceptedVendor())){
			providerSearchSO.setAcceptedVendor(result.getAcceptedVendor());
		}
		if(StringUtils.isNotBlank(result.getAssignmentType())){
			providerSearchSO.setAssignmentType(result.getAssignmentType());
		}
		providerSearchSO.setAcceptedResourceId(result.getAcceptedResourceId());
		providerSearchSO.setAcceptedVendorId(result.getAcceptedVendorId());
	}


	public RecievedOrdersCriteriaVO mapRecievedOrdersCriteria(int roleId,String firmId,int resourceId,String pageNo,String pageSize, String bidOnly){
		boolean bidOnlyInd = false;
		RecievedOrdersCriteriaVO criteriaVO = new RecievedOrdersCriteriaVO();
		//Convert firm Id to integer
		int firmIdInteger = 0;
		try{
			firmIdInteger = Integer.parseInt(firmId);
		}catch (NumberFormatException e) {
			firmIdInteger=0;
		}
		criteriaVO.setFirmId(firmIdInteger);
		criteriaVO.setResourceId(resourceId);
		criteriaVO.setRoleId(roleId);
		//if roleId = 3, set isAdmin flag=true
		if(roleId == 3){
			criteriaVO.setAdmin(true);
		}

		int pageNoIntValue = MPConstants.DEFAULT_PAGE_NO;
		int pageSizeIntValue = MPConstants.DEFAULT_PAGE_SIZE;
		int pageLimit = 0;

		//Calculate page limit
		//By default pageNo = 0 and pageLimit = 20
		//if pageNo and pageSize exists in URL, calculate pageLimit as pageSize*(pageNo - 1)
		if(StringUtils.isNotBlank(pageNo) && StringUtils.isNotBlank(pageSize)){
			try{
				pageNoIntValue = Integer.parseInt(pageNo);
				pageSizeIntValue = Integer.parseInt(pageSize);
			}catch (NumberFormatException e) {
				pageNoIntValue = MPConstants.DEFAULT_PAGE_NO;
				pageSizeIntValue = MPConstants.DEFAULT_PAGE_SIZE;
			}
		}
		pageLimit  = pageSizeIntValue * (pageNoIntValue - 1);
		
		if(StringUtils.isNotBlank(bidOnly)){
			bidOnlyInd = Boolean.parseBoolean(bidOnly);
			criteriaVO.setBidOnlyInd(bidOnlyInd);
		} 

		criteriaVO.setPageNo(pageNoIntValue);
		criteriaVO.setPageLimit(pageLimit);
		criteriaVO.setPageSize(pageSizeIntValue);

		return criteriaVO;
	}
	public RecievedOrdersResponse mapRecievedOrders(Integer countOfSO, List<RecievedOrdersVO> recievedOrdersList, boolean bidOnlyInd,Map<String,String> orderTypeMap,
			Map<String,List<EstimateVO>> estimateMap){
		RecievedOrdersResponse recievedOrdersResponse = new RecievedOrdersResponse();
		RecievedServiceOrders recievedServiceOrders = new RecievedServiceOrders();
		List<RecievedOrder> recievedOrders = new ArrayList<RecievedOrder>();
		recievedServiceOrders.setTotalSOCount(countOfSO);
		if(null != recievedOrdersList){
			recievedOrdersResponse = new RecievedOrdersResponse();

			for(RecievedOrdersVO recievedOrdersVO: recievedOrdersList){
				RecievedOrder recievedOrder = new RecievedOrder();
				mapRecievedOrderBasicDetails(recievedOrder,recievedOrdersVO,bidOnlyInd);
				mapPriceDetails(recievedOrder,recievedOrdersVO);
				mapRecievedOrderDateTime(recievedOrder,recievedOrdersVO);
				mapRecievedOrderBuyerDetails(recievedOrder,recievedOrdersVO);
				mapRecievedOrderPickupLocnDetails(recievedOrder,recievedOrdersVO);
				if(bidOnlyInd){
					mapBidDetailsIfAny(recievedOrder,recievedOrdersVO);
				}
				// map Relay details
				mapRelayDetails(recievedOrder, orderTypeMap, estimateMap);				
				recievedOrders.add(recievedOrder);
			}
			recievedServiceOrders.setRecievedOrders(recievedOrders);
		}
		recievedOrdersResponse.setRecievedServiceOrders(recievedServiceOrders);
		return recievedOrdersResponse;
	}
	
	/**
	 * @param recievedOrder
	 * @param recievedOrdersVO
	 */
	private void mapBidDetailsIfAny(RecievedOrder recievedOrder,
			RecievedOrdersVO recievedOrdersVO) {
		// TODO Auto-generated method stub
		if(null != recievedOrdersVO.getBidCount() && recievedOrdersVO.getBidCount().intValue()>0)
		{
			BidDetails bidDetails = new BidDetails();
			bidDetails.setSealedBidInd(recievedOrdersVO.isSealedBidInd());
			bidDetails.setBidPrice(recievedOrdersVO.getCurrentBid());
			bidDetails.setBidRangeMin(recievedOrdersVO.getBidRangeMin());
			bidDetails.setBidRangeMax(recievedOrdersVO.getBidRangeMax());
			bidDetails.setTotalBids(recievedOrdersVO.getBidCount());
			recievedOrder.setBidDetails(bidDetails);
		}
		
	}
	
	private void mapRelayDetails(RecievedOrder recievedOrder,Map<String,String> orderTypeMap,
			Map<String,List<EstimateVO>> estimateMap
			) {
		if(null != recievedOrder)
		{
			if(null!=orderTypeMap && null!=orderTypeMap.get(recievedOrder.getSoId()))
			recievedOrder.setRelayOrderType(orderTypeMap.get(recievedOrder.getSoId()));
			
			if(null!=estimateMap && null!=estimateMap.get(recievedOrder.getSoId())){

			ReceivedOrderEstimateDetails estimateDetails=new ReceivedOrderEstimateDetails();
			List<ReceivedOrderEstimateDetail> receivedOrderEstimateDetailList=new ArrayList<ReceivedOrderEstimateDetail>();
			List<EstimateVO>estimationList=estimateMap.get(recievedOrder.getSoId());
			for(EstimateVO estimateVO:estimationList){
				ReceivedOrderEstimateDetail receivedOrderEstimateDetail=new ReceivedOrderEstimateDetail();
				receivedOrderEstimateDetail.setEstimationId(estimateVO.getEstimationId());
				receivedOrderEstimateDetail.setEstimationNo(estimateVO.getEstimationRefNo());
				receivedOrderEstimateDetail.setEstimationStatus(estimateVO.getStatus());
				receivedOrderEstimateDetail.setResourceId(estimateVO.getResourceId());
				receivedOrderEstimateDetailList.add(receivedOrderEstimateDetail);
			}	
			estimateDetails.setEstimate(receivedOrderEstimateDetailList);
			recievedOrder.setEstimateDetails(estimateDetails);
			recievedOrder.setEstimationFlag(true);
			}else{
				recievedOrder.setEstimationFlag(false);	
			}
		}		
	}
	
	

	private void mapRecievedOrderBasicDetails(RecievedOrder recievedOrder, RecievedOrdersVO recievedOrdersVO, boolean bidOnlyInd){
		recievedOrder.setSoId(recievedOrdersVO.getSoId());
		if (bidOnlyInd){
			recievedOrder.setOrderType(PublicMobileAPIConstant.BID_REQUEST);
		}
		else {
			recievedOrder.setOrderType(recievedOrdersVO.getOrderType());
		}
		recievedOrder.setSoTitle(recievedOrdersVO.getSoTitle());
		recievedOrder.setSoStatus(recievedOrdersVO.getSoStatus());
		recievedOrder.setFollowUpFlag(recievedOrdersVO.getFollowUpFlag());
		recievedOrder.setCity(recievedOrdersVO.getCity());
		recievedOrder.setState(recievedOrdersVO.getState());
		recievedOrder.setZip(recievedOrdersVO.getZip());
		recievedOrder.setRoutedProvider(recievedOrdersVO.getRoutedProvider());
	}

	//sum up the spendLimit and spemndLimit parts and do a rounding
	private void mapPriceDetails(RecievedOrder recievedOrder, RecievedOrdersVO recievedOrdersVO){
		BigDecimal spendLimit = new BigDecimal(0);
		BigDecimal spendLimitParts = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);

		Double soPrice = 0.00;

		if(null != recievedOrdersVO.getSpendLimit()){
			spendLimit = recievedOrdersVO.getSpendLimit();
		}

		if(null != recievedOrdersVO.getSpendLimitParts()){
			spendLimitParts = recievedOrdersVO.getSpendLimitParts();
		}
		totalPrice = spendLimit.add(spendLimitParts);
		soPrice = MoneyUtil.getRoundedMoney(totalPrice.doubleValue());
		recievedOrder.setSoPrice(soPrice);

	}

	private void mapRecievedOrderDateTime(RecievedOrder recievedOrder, RecievedOrdersVO recievedOrdersVO){

		DateFormat requiredDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Timestamp servciceStartDate = recievedOrdersVO.getServiceStartDate();
		Timestamp serviceEndDate = recievedOrdersVO.getServiceEndDate();
		String serviceWindowStartTime = recievedOrdersVO.getServiceWindowStartTime();
		String serviceWindowEndTime = recievedOrdersVO.getServiceWindowEndTime();
		String timeZone = recievedOrdersVO.getTimeZone();

		if(null != servciceStartDate && StringUtils.isNotEmpty(serviceWindowStartTime)){
			recievedOrder.setTimeZone(TimeUtils.getTimeZoneForAPI(servciceStartDate,timeZone));
			HashMap<String, Object> apptStartDate = null;
			apptStartDate =  TimeUtils.convertGMTToGivenTimeZone(servciceStartDate, serviceWindowStartTime, timeZone);
			if(null != apptStartDate){
				if(apptStartDate.containsKey(OrderConstants.GMT_DATE)){
					recievedOrder.setServiceStartDate(requiredDateFormat.format(apptStartDate.get(OrderConstants.GMT_DATE)));
				}
				if(apptStartDate.containsKey(OrderConstants.GMT_TIME)){
					recievedOrder.setServiceWindowStartTime(apptStartDate.get(OrderConstants.GMT_TIME).toString());
				}
			}
		}
		if(null != serviceEndDate && StringUtils.isNotEmpty(serviceWindowEndTime)){
			HashMap<String, Object> apptEndDate = null;
			apptEndDate =  TimeUtils.convertGMTToGivenTimeZone(serviceEndDate, serviceWindowEndTime, timeZone);
			if(null != apptEndDate){
				if(apptEndDate.containsKey(OrderConstants.GMT_DATE)){
					recievedOrder.setServiceEndDate(requiredDateFormat.format(apptEndDate.get(OrderConstants.GMT_DATE)));
				}
				if(apptEndDate.containsKey(OrderConstants.GMT_TIME)){
					recievedOrder.setServiceWindowEndTime(apptEndDate.get(OrderConstants.GMT_TIME).toString());
				}
			}
		}

		if(null != recievedOrdersVO.getRecievedDate()){
			recievedOrder.setRecievedDate(requiredDateFormat.format(recievedOrdersVO.getRecievedDate()));
		}
	}

	private void mapRecievedOrderBuyerDetails(RecievedOrder recievedOrder, RecievedOrdersVO recievedOrdersVO){
		recievedOrder.setBuyerId(recievedOrdersVO.getBuyerId());
		recievedOrder.setBuyerName(recievedOrdersVO.getBuyerName());
	}

	private void mapRecievedOrderPickupLocnDetails(RecievedOrder recievedOrder, RecievedOrdersVO recievedOrdersVO){
		recievedOrder.setProductAvailability(recievedOrdersVO.getProductAvailability());

		if(MPConstants.PRODUCT_AVAILABILITY_APPLICABLE.equals(recievedOrdersVO.getProductAvailability())){
			PickUpLocationDetails pickUpLocationDetails = new PickUpLocationDetails();
			pickUpLocationDetails.setAddress1(recievedOrdersVO.getPickupAddress1());
			pickUpLocationDetails.setAddress2(recievedOrdersVO.getPickupAddress2());
			pickUpLocationDetails.setCity(recievedOrdersVO.getPickupCity());
			pickUpLocationDetails.setState(recievedOrdersVO.getPickupState());
			pickUpLocationDetails.setZip(recievedOrdersVO.getPickupZip());
			pickUpLocationDetails.setPickUpDate(recievedOrdersVO.getPickupDate());

			recievedOrder.setPickUpLocationDetails(pickUpLocationDetails);
		}
	}

	/**
	 * Method to map the so details to SoDetailsVO
	 * @param apiVO
	 * @return SoDetailsVO
	 */
	public SoDetailsVO mapSoDetails(APIRequestVO apiVO) {
		SoDetailsVO detailsVO = new SoDetailsVO();

		detailsVO.setFirmId(apiVO.getProviderId());
		detailsVO.setProviderId(apiVO.getProviderResourceId());
		detailsVO.setRoleId(apiVO.getRoleId());
		detailsVO.setSoId(apiVO.getSOId());
		return detailsVO;

	}

	/**
	 * Method to map the response of getSODetailsPro
	 * @param detailsMobileVO
	 * @return
	 */
	public RetrieveSODetailsMobile mapGetSODetailsResponse(RetrieveSODetailsMobileVO detailsMobileVO,RescheduleDetailsVO rescheduleDetailsVO, 
			List<ProviderResultVO> counteredResourceDetailsList,  List<EstimateVO> estimationlist,BidDetailsVO bidDetailsVO) {

		RetrieveSODetailsMobile detailsMobile = new RetrieveSODetailsMobile();
		ServiceOrderDetails orderDetails = new ServiceOrderDetails();		

		LatestTrip latestTrip = new LatestTrip();
		GeneralSection generalSection = new GeneralSection();
		Appointment appointment = new Appointment();
		Buyer buyer = new Buyer();
		Provider provider = new Provider();
		ServiceLocation serviceLocation = new ServiceLocation();
		AlternateServiceLocation alternateServiceLocation = new AlternateServiceLocation();
		Scope scope = new Scope();
		AddOnList addOnList = new AddOnList();
		Parts parts = new Parts();
		Merchandises merchandises = new Merchandises();
		InvoicePartsList invoicePartsList = new InvoicePartsList();
		Documents documents = new Documents();
		BuyerReferences buyerReferences = new BuyerReferences();
		Notes notes = new Notes();
		SupportNotes supportNotes = new SupportNotes();
		SOTrips soTrips = new  SOTrips();
		RescheduleDetails rescheduleDetails = new RescheduleDetails();
		CounterOfferDetails counterOfferDetails = new CounterOfferDetails();
		//For mapping estimate details
		com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetails estimateDetails = 
				new com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetails();
		//For mapping bid details
		BiddingDetails bidDetails = new BiddingDetails();
		ScheduleServiceSlot scheduleServiceSlot = null;
				
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()&& null !=detailsMobileVO.getSoDetails().getCurrentTripNo()){
			orderDetails.setCurrentTripNo(detailsMobileVO.getSoDetails().getCurrentTripNo());
		}
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()&& detailsMobileVO.getSoDetails().isFollowupFlag()){
			orderDetails.setFollowupFlag(detailsMobileVO.getSoDetails().isFollowupFlag());
		}
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()&& null !=detailsMobileVO.getSoDetails().getBuyerRefPresentInd()){
			orderDetails.setBuyerRefPresentInd(detailsMobileVO.getSoDetails().getBuyerRefPresentInd());
		}

		latestTrip= mapLatestTripDetails(detailsMobileVO);
		generalSection = mapGeneralSection(detailsMobileVO);
		appointment= mapAppointmentDetails(detailsMobileVO);
		buyer = mapBuyerDetails(detailsMobileVO);
		provider= mapProviderDetails(detailsMobileVO);
		serviceLocation = mapServiceLocationDetails(detailsMobileVO);
		alternateServiceLocation = mapAlternateServiceLocation(detailsMobileVO);
		scope = mapScopeDetails(detailsMobileVO);
		addOnList = mapAddOnDetails(detailsMobileVO);
		parts = mapPartDetails(detailsMobileVO);		
		merchandises = mapMerchandiseDetails(detailsMobileVO);

		invoicePartsList = mapInvoicePartsList(detailsMobileVO);

		documents = mapDocumentDetails(detailsMobileVO);
		buyerReferences = mapBuyerReferences(detailsMobileVO);
		notes=mapNotesForSO(detailsMobileVO);
		supportNotes = mapSupportNotes(detailsMobileVO);
		soTrips = mapSOTrips(detailsMobileVO);
		rescheduleDetails = mapRescheduleDetails(rescheduleDetailsVO);
		counterOfferDetails=mapCounterOfferDetails(counteredResourceDetailsList);
		//For mapping estimate details
		estimateDetails = mapEstimateDetails(estimationlist,detailsMobileVO.getTimeZone());
		
		//For mapping bid details
		bidDetails = mapBidDetails(bidDetailsVO);
		
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()){
			orderDetails.setLatestTrip(latestTrip);						
			orderDetails.setOrderDetails(generalSection);						
			orderDetails.setAppointment(appointment);
			orderDetails.setBuyer(buyer);
			orderDetails.setProvider(provider);
			orderDetails.setServiceLocation(serviceLocation);
			orderDetails.setAlternateServiceLocation(alternateServiceLocation);
			orderDetails.setScope(scope);
			orderDetails.setAddonList(addOnList);
			orderDetails.setParts(parts);
			orderDetails.setMerchandises(merchandises);
			orderDetails.setInvoicePartsList(invoicePartsList);
			orderDetails.setDocuments(documents);
			orderDetails.setBuyerReferences(buyerReferences);
			orderDetails.setNotes(notes);
			orderDetails.setSupportNotes(supportNotes);
			orderDetails.setTripDetails(soTrips);
			orderDetails.setRescheduleDetails(rescheduleDetails);
			orderDetails.setCounterOfferDetails(counterOfferDetails);
			if(null != estimateDetails && null != estimateDetails.getEstimate() 
					&& !estimateDetails.getEstimate().isEmpty()){
				orderDetails.setEstimateFlag(true);
			}
			if(orderDetails.isEstimateFlag()){
				orderDetails.setEstimateDetails(estimateDetails);
			}
			
			if(null!=detailsMobileVO.getSoDetails().getOrderDetails() && StringUtils.isNotBlank(detailsMobileVO.getSoDetails().getOrderDetails().getPriceModel()))
			{
				orderDetails.setPriceModel(detailsMobileVO.getSoDetails().getOrderDetails().getPriceModel());
			}
			
			if(null!=bidDetails)
			{
				orderDetails.setBidDetails(bidDetails);
			}
			detailsMobile.setSoDetails(orderDetails);
		}
		if(null != detailsMobileVO.getSoDetails().getScheduleServiceSlot()){
			scheduleServiceSlot = detailsMobileVO.getSoDetails().getScheduleServiceSlot();
			detailsMobile.getSoDetails().setScheduleServiceSlot(scheduleServiceSlot);
			
		}
		return detailsMobile;
	}	

	/**
	 * Method to map the estimation details	of an SO
	 * @param estimationlist
	 * @param timeZone 
	 * @return
	 */
	private com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetails mapEstimateDetails
			(List<EstimateVO> estimationlist, String timeZone) {
		
		com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetails estimateDetails = null;
		if(null != estimationlist && !estimationlist.isEmpty()){
			estimateDetails = new com.newco.marketplace.api.mobile.beans.soDetails.v3_0.EstimateDetails();
			List<EstimateDetail> estimates = new ArrayList<EstimateDetail>();
			for(EstimateVO estimateVO : estimationlist){
				if(null != estimateVO){
					EstimateDetail estimate = new EstimateDetail();
					estimate.setEstimationId(estimateVO.getEstimationId());
					if(StringUtils.isNotBlank(estimateVO.getEstimationRefNo())){
						estimate.setEstimationNo(estimateVO.getEstimationRefNo());
					}
					if(StringUtils.isNotBlank(estimateVO.getStatus())){
						estimate.setEstimationStatus(estimateVO.getStatus());
					}
					if(StringUtils.isNotBlank(estimateVO.getStatus()) &&
							(estimateVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) || estimateVO.getStatus().equals(MPConstants.DECLINED_STATUS))){
						Date responseDate = estimateVO.getResponseDate();
						if(null!= responseDate){
							//Convert response Date to service location timezone  and then to  yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate =TimeUtils.convertTimeFromOneTimeZoneToOther(ts,MPConstants.SERVER_TIMEZONE,timeZone);
							estimate.setResponseDate(DateUtils.formatDate(responseDate));
							LOGGER.info("Accepted/Declined Date"+ estimate.getResponseDate());
						}
						
					}
					
					Date estimateExpiryDate = estimateVO.getEstimationExpiryDate();
					if(null!= estimateExpiryDate){
						estimate.setEstimationExpiryDate(DateUtils.formatDate(estimateExpiryDate));
					}
					
					estimates.add(estimate);
				}
			}
			estimateDetails.setEstimate(estimates);
		}
		return estimateDetails;
	}
	
	/**
	 * Method to map the bid details of an SO
	 * @param bidDetailsVO
	 * @return
	 */
     private BiddingDetails mapBidDetails
			(BidDetailsVO bidDetailsVO) {
		
    	 BiddingDetails bidDetails = null;
		if(null != bidDetailsVO){
			bidDetails = new BiddingDetails();
			if(null!=bidDetailsVO.getSealedInd() && bidDetailsVO.getSealedInd()){
				bidDetails.setSealedInd(true);
			}
			if(!bidDetails.isSealedInd()){
				bidDetails.setTotalBids(bidDetailsVO.getNumberOfBids());
			}
			
			if(null!=bidDetailsVO.getNumberOfBids() && bidDetailsVO.getNumberOfBids().intValue()>MPConstants.INT_ZERO){
				if(!bidDetails.isSealedInd()){
					DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);
					if(null!=bidDetailsVO.getLowBid()){
						bidDetails.setMinimumBid(df.format(bidDetailsVO.getLowBid()));
					}
					if(null!=bidDetailsVO.getHighBid()){
						bidDetails.setMaximumBid(df.format(bidDetailsVO.getHighBid()));
					}
				}
				
				//mapping details of current bid
				if(null!=bidDetailsVO.getMyCurrentBid())
				{
					bidDetails.setCurrentBid(mapIndividualBidDetails(bidDetailsVO.getMyCurrentBid()));
				}
				//mapping details of previous bid
				if(null!=bidDetailsVO.getMyPreviousBid())
				{	
					bidDetails.setPreviousBid(mapIndividualBidDetails(bidDetailsVO.getMyPreviousBid()));
				}
				//mapping details of other bids from my company
				List<ServiceOrderBidModel> otherBidsFromMyCompany = bidDetailsVO.getOtherBidsFromMyCompany();
				boolean myCompanyInd;
				if(!(otherBidsFromMyCompany.isEmpty())){
					myCompanyInd = true;
					bidDetails.setOtherBidsFromMyCompany(mapBidListDetails(otherBidsFromMyCompany,myCompanyInd));
				}
				//mapping details of all other bids
				List<ServiceOrderBidModel> allOtherBids = bidDetailsVO.getAllOtherBids();
				if(!(allOtherBids.isEmpty()) && !(bidDetails.isSealedInd())){
					myCompanyInd = false;
					bidDetails.setAllOtherBids(mapBidListDetails(allOtherBids,myCompanyInd));
				}
			}	
		}
		return bidDetails;
	}
     
     /**
 	 * Method to map the individual bid details of an SO
 	 * @param ServiceOrderBidModel
 	 * @return Bid
 	 */
     private Bid mapIndividualBidDetails(ServiceOrderBidModel bidModel){
    	    Bid bid = new Bid();
			bid.setBidDate(formatDates(bidModel.getDateOfBid()));
			bid.setExpirationDate(bidModel.getBidExpirationDatepicker());
			DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);
			if(null!=bidModel.getTotal()){
				bid.setTotalBidPrice(df.format(bidModel.getTotal()));
			}
			if(null!= bidModel.getCalculatedHourlyRate() && bidModel.getCalculatedHourlyRate().doubleValue()!=MPConstants.MINUS_ONE){
				bid.setHourlyRate(df.format(bidModel.getCalculatedHourlyRate()));
			}
			if(null!=bidModel.getTotalHours())
			{
				bid.setEstimatedTime(bidModel.getTotalHours().intValue());
			}
			if(null!=bidModel.getTotalLabor()){
				bid.setTotalLabor(df.format(bidModel.getTotalLabor()));
			}
			if(null!=bidModel.getPartsMaterials()){
				bid.setMaterialEstimate(df.format(bidModel.getPartsMaterials()));
			}
			
			//mapping new service date when the new date is specific
			if(StringUtils.isNotBlank(bidModel.getNewDateBySpecificDate())&& StringUtils.isNotBlank(bidModel.getNewDateByRangeFrom())
					&& StringUtils.isNotBlank(bidModel.getNewDateByRangeTo())){
				bid.setNewServiceDateType(MPConstants.SPECIFIC);
				bid.setNewServiceStartDate(bidModel.getNewDateBySpecificDate());
				bid.setNewServiceStartTime(bidModel.getNewDateByRangeFrom());
				bid.setNewServiceEndTime(bidModel.getNewDateByRangeTo());
			}
			//mapping new service date when the new date is range
			else if (StringUtils.isNotBlank(bidModel.getNewDateByRangeFrom())&& StringUtils.isNotBlank(bidModel.getNewDateByRangeTo())){
				bid.setNewServiceDateType(MPConstants.BID_RANGE);
				bid.setNewServiceStartDate(bidModel.getNewDateByRangeFrom());
				bid.setNewServiceEndDate(bidModel.getNewDateByRangeTo());
			}
			bid.setComment(bidModel.getComment());
			return bid;
     }
     
     /**
  	 * Method to map the bid list details of an SO
  	 * @param ServiceOrderBidModel
  	 * @return Bid
  	 */
     private BidList mapBidListDetails(List<ServiceOrderBidModel> otherBids, boolean myCompanyInd){
    	 BidList bidList = new BidList();
    	 List<Bid> allOtherBidsList = new ArrayList<Bid>();
			for(ServiceOrderBidModel bidModel:otherBids){
				if(null!=bidModel){
					Bid bid = mapIndividualBidDetails(bidModel);
					bid.setBidPlacedResource(bidModel.getBidderResourceId());
					if(!myCompanyInd){
						bid.setBidPlacedVendor(bidModel.getBidderVendorId());
					}
					bid.setBidder(bidModel.getBidder());
					allOtherBidsList.add(bid);
				}
			}
			bidList.setBid(allOtherBidsList);
			return bidList;
     }
	
	/**
	 * Method to map the latest trip details	
	 * @param detailsMobileVO
	 * @return
	 */
	private LatestTrip mapLatestTripDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		LatestTrip latestTrip = new LatestTrip();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()&& null != detailsMobileVO.getSoDetails().getLatestTrip()){
			latestTrip.setTripNo(detailsMobileVO.getSoDetails().getLatestTrip().getTripNo());
			latestTrip.setTripStatus(detailsMobileVO.getSoDetails().getLatestTrip().getTripStatus());
			latestTrip.setCheckInTime(detailsMobileVO.getSoDetails().getLatestTrip().getCheckInTime());
			latestTrip.setCheckOutTime(detailsMobileVO.getSoDetails().getLatestTrip().getCheckOutTime());
		}
		return latestTrip;				
	}
	
	private RescheduleDetails mapRescheduleDetails(RescheduleDetailsVO rescheduleDetailsVO){
		RescheduleDetails rescheduleDetails = new RescheduleDetails();
		if(null!=rescheduleDetailsVO && null!= rescheduleDetailsVO.getRescheduleServiceDate1()){
			rescheduleDetails.setRescheduleServiceDate1(rescheduleDetailsVO.getRescheduleTimeZoneDate1());
			rescheduleDetails.setRescheduleServiceDate2(rescheduleDetailsVO.getRescheduleTimeZoneDate2());
			rescheduleDetails.setRescheduleServiceTime1(rescheduleDetailsVO.getRescheduleServiceTime1());
			rescheduleDetails.setRescheduleServiceTime2(rescheduleDetailsVO.getRescheduleServiceTime2());
			rescheduleDetails.setTimeZone(rescheduleDetailsVO.getTimeZone());
			rescheduleDetails.setReasonCodeDescription(splitReasonForReschedule(rescheduleDetailsVO.getReasonCodeDescription()));
			rescheduleDetails.setRescheduleInitiatedBy(rescheduleDetailsVO.getRescheduleInitiatedBy());
			rescheduleDetails.setRescheduleInitiatedDate(rescheduleDetailsVO.getRescheduleInitiatedDate());
			rescheduleDetails.setRoleId(rescheduleDetailsVO.getRoleId());
		}
		return rescheduleDetails;
	}
	
	private CounterOfferDetails mapCounterOfferDetails(List<ProviderResultVO> counteredResourceDetailsList){
		CounterOfferDetails counterOfferDetails = new CounterOfferDetails();
		List<CounterOfferDetail> counteredResourceList = new ArrayList<CounterOfferDetail>();
		if(null != counteredResourceDetailsList && counteredResourceDetailsList.size()>0){
			counterOfferDetails.setCounteredResources(counteredResourceDetailsList.size());
			for(ProviderResultVO counteredResource: counteredResourceDetailsList){
				CounterOfferDetail counterOfferDetail = new CounterOfferDetail();
				counterOfferDetail.setResourceId(counteredResource.getResourceId());
				counterOfferDetail.setProviderFirstName(counteredResource.getProviderFirstName());
				counterOfferDetail.setProviderLastName(counteredResource.getProviderLastName());
				counterOfferDetail.setDistanceFromBuyer(counteredResource.getDistanceFromBuyer());
				counteredResourceList.add(counterOfferDetail);
			}
			counterOfferDetails.setCounteredResourceList(counteredResourceList);
		}
		return counterOfferDetails;
	}
	
	/**This method is to obtain only the reason description from logging table
	 * @param reasonCodeDescription
	 * @return
	 */
	private String splitReasonForReschedule(String reasonCodeDescription){
		String reason = null;
		if(null!=reasonCodeDescription){
			String[] reasonList1 = null;
			String[] reasonList2 = null;
				reasonList1 = reasonCodeDescription.split(MPConstants.REASON_SPLIT);
				reasonList2 = reasonList1[1].split(MPConstants.COMMENTS_SPLIT);
				reason = reasonList2[0];
		}
		return reason;
	}
	/**
	 * method to map the general service order details
	 * @param detailsMobileVO
	 * @return
	 */
	private GeneralSection mapGeneralSection(RetrieveSODetailsMobileVO detailsMobileVO){
		GeneralSection generalSection = new GeneralSection();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getOrderDetails()){
			generalSection.setSoId(detailsMobileVO.getSoDetails().getOrderDetails().getSoId());
			if(detailsMobileVO.getSoDetails().getOrderDetails().getSoStatus().equals(PublicAPIConstant.SO_STATUS_ROUTED)){
				generalSection.setSoStatus(PublicAPIConstant.SO_STATUS_RECEIVED);
			}
			else{
				generalSection.setSoStatus(detailsMobileVO.getSoDetails().getOrderDetails().getSoStatus());
			}
			
			generalSection.setSoSubStatus(detailsMobileVO.getSoDetails().getOrderDetails().getSoSubStatus());
	
			generalSection.setSoTitle(detailsMobileVO.getSoDetails().getOrderDetails().getSoTitle());
			
			Integer buyerId = detailsMobileVO.getSoDetails().getBuyer().getBuyerUserId();			
			LOGGER.info("buyer Id"+buyerId);
			
			if(MPConstants.RELAY_SERVICES_BUYER_ID.equals(buyerId)){				
				LOGGER.info("**********Overview 3333" +detailsMobileVO.getSoDetails().getOrderDetails().getOverView()+"********");
			    generalSection.setOverView(detailsMobileVO.getSoDetails().getOrderDetails().getOverView());
				
			    LOGGER.info("**********BuyerTerms and Conditions 3333" +detailsMobileVO.getSoDetails().getOrderDetails().getBuyerTerms()+"********");
				generalSection.setBuyerTerms(detailsMobileVO.getSoDetails().getOrderDetails().getBuyerTerms());
						
				LOGGER.info("**********Special Instructions 3333" +detailsMobileVO.getSoDetails().getOrderDetails().getSpecialInstuctions()+"********");
				generalSection.setSpecialInstuctions(detailsMobileVO.getSoDetails().getOrderDetails().getSpecialInstuctions());			
			}else{
				//SL-20728 : removing html tags
				generalSection.setOverView(ServiceLiveStringUtils.removeHTML(detailsMobileVO.getSoDetails().getOrderDetails().getOverView()));
				//SL-20728 : removing html tags
				LOGGER.info("**********BuyerTerms and Conditions before conversion:" +detailsMobileVO.getSoDetails().getOrderDetails().getBuyerTerms()+"********");
				generalSection.setBuyerTerms(ServiceLiveStringUtils.removeHTML(detailsMobileVO.getSoDetails().getOrderDetails().getBuyerTerms()));
				LOGGER.info("**********BuyerTerms and Conditions after conversion:" +detailsMobileVO.getSoDetails().getOrderDetails().getBuyerTerms()+"********");
				//SL-20728 : removing html tags
			
				generalSection.setSpecialInstuctions(ServiceLiveStringUtils.removeHTML(detailsMobileVO.getSoDetails().getOrderDetails().getSpecialInstuctions()));
			}
			mapSoPrice(generalSection, detailsMobileVO);

		}
		return generalSection;
	}
	/**
	 * Method to map the appointment details
	 * @param detailsMobileVO
	 * @return
	 */
	private Appointment mapAppointmentDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		Appointment appointment = new Appointment();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getAppointment()){
			appointment.setMaxTimeWindow(detailsMobileVO.getSoDetails().getAppointment().getMaxTimeWindow());
			appointment.setMinTimeWindow(detailsMobileVO.getSoDetails().getAppointment().getMinTimeWindow());
			appointment.setScheduleStatus(detailsMobileVO.getSoDetails().getAppointment().getScheduleStatus());
			appointment.setServiceEndDate(detailsMobileVO.getSoDetails().getAppointment().getServiceEndDate());
			appointment.setServiceStartDate(detailsMobileVO.getSoDetails().getAppointment().getServiceStartDate());
			appointment.setServiceWindowEndTime(detailsMobileVO.getSoDetails().getAppointment().getServiceWindowEndTime());
			appointment.setServiceWindowStartTime(detailsMobileVO.getSoDetails().getAppointment().getServiceWindowStartTime());
			appointment.setTimeZone(detailsMobileVO.getSoDetails().getAppointment().getTimeZone());
		}
		return appointment;
	}
	/**
	 * method to map the buyer details
	 * @param detailsMobileVO
	 * @return
	 */
	private Buyer mapBuyerDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		Buyer buyer= new Buyer();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getBuyer()){
			buyer.setBusinessName(detailsMobileVO.getSoDetails().getBuyer().getBusinessName());
			buyer.setFirstName(detailsMobileVO.getSoDetails().getBuyer().getFirstName());
			buyer.setLastName(detailsMobileVO.getSoDetails().getBuyer().getLastName());
			buyer.setBuyerUserId(detailsMobileVO.getSoDetails().getBuyer().getBuyerUserId());
			buyer.setResourceId(detailsMobileVO.getSoDetails().getBuyer().getResourceId());
			buyer.setStreetAddress1(detailsMobileVO.getSoDetails().getBuyer().getStreetAddress1());
			buyer.setStreetAddress2(detailsMobileVO.getSoDetails().getBuyer().getStreetAddress2());
			buyer.setCity(detailsMobileVO.getSoDetails().getBuyer().getCity());
			buyer.setState(detailsMobileVO.getSoDetails().getBuyer().getState());
			buyer.setZip(detailsMobileVO.getSoDetails().getBuyer().getZip());
			buyer.setPrimaryphone(detailsMobileVO.getSoDetails().getBuyer().getPrimaryphone());
			buyer.setAlternatePhone(detailsMobileVO.getSoDetails().getBuyer().getAlternatePhone());
			buyer.setFax(detailsMobileVO.getSoDetails().getBuyer().getFax());
			buyer.setEmail(detailsMobileVO.getSoDetails().getBuyer().getEmail());
			buyer.setMinTimeWindow(detailsMobileVO.getSoDetails().getBuyer().getMinTimeWindow());
			buyer.setMaxTimeWindow(detailsMobileVO.getSoDetails().getBuyer().getMaxTimeWindow());
		}
		return buyer;		
	}
	/**
	 * method to map the provider details
	 * @param detailsMobileVO
	 * @return
	 */
	private Provider mapProviderDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		Provider provider = new Provider();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getProvider()){
			provider.setProviderFirstName(detailsMobileVO.getSoDetails().getProvider().getProviderFirstName());
			provider.setProviderLastName(detailsMobileVO.getSoDetails().getProvider().getProviderLastName());
			provider.setProviderId(detailsMobileVO.getSoDetails().getProvider().getProviderId());
			provider.setFirmId(detailsMobileVO.getSoDetails().getProvider().getFirmId());
			provider.setFirmName(detailsMobileVO.getSoDetails().getProvider().getFirmName());
			provider.setStreetAddress1(detailsMobileVO.getSoDetails().getProvider().getStreetAddress1());
			provider.setStreetAddress2(detailsMobileVO.getSoDetails().getProvider().getStreetAddress2());
			provider.setCity(detailsMobileVO.getSoDetails().getProvider().getCity());
			provider.setState(detailsMobileVO.getSoDetails().getProvider().getState());
			provider.setZip(detailsMobileVO.getSoDetails().getProvider().getZip());
			provider.setProviderPrimaryPhone(detailsMobileVO.getSoDetails().getProvider().getProviderPrimaryPhone());
			provider.setProviderAltPhone(detailsMobileVO.getSoDetails().getProvider().getProviderAltPhone());
			provider.setFirmPrimaryPhone(detailsMobileVO.getSoDetails().getProvider().getFirmPrimaryPhone());
			provider.setFirmAltPhone(detailsMobileVO.getSoDetails().getProvider().getFirmAltPhone());
			provider.setPrimaryEmail(detailsMobileVO.getSoDetails().getProvider().getPrimaryEmail());
			provider.setAlternateEmail(detailsMobileVO.getSoDetails().getProvider().getAlternateEmail());
			provider.setSmsNumber(detailsMobileVO.getSoDetails().getProvider().getSmsNumber());
		}
		return provider;
	}
	/**
	 * method to map the servicelocation details
	 * @param detailsMobileVO
	 * @return
	 */
	private ServiceLocation mapServiceLocationDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		ServiceLocation serviceLocation = new ServiceLocation();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getServiceLocation()){
			serviceLocation.setCustomerFirstName(detailsMobileVO.getSoDetails().getServiceLocation().getCustomerFirstName());
			serviceLocation.setCustomerLastName(detailsMobileVO.getSoDetails().getServiceLocation().getCustomerLastName());
			serviceLocation.setStreetAddress1(detailsMobileVO.getSoDetails().getServiceLocation().getStreetAddress1());
			serviceLocation.setStreetAddress2(detailsMobileVO.getSoDetails().getServiceLocation().getStreetAddress2());
			serviceLocation.setCity(detailsMobileVO.getSoDetails().getServiceLocation().getCity());
			serviceLocation.setState(detailsMobileVO.getSoDetails().getServiceLocation().getState());
			serviceLocation.setZip(detailsMobileVO.getSoDetails().getServiceLocation().getZip());
			serviceLocation.setPrimaryPhone(detailsMobileVO.getSoDetails().getServiceLocation().getPrimaryPhone());
			serviceLocation.setAlternatePhone(detailsMobileVO.getSoDetails().getServiceLocation().getAlternatePhone());
			serviceLocation.setEmail(detailsMobileVO.getSoDetails().getServiceLocation().getEmail());
			serviceLocation.setServiceLocationNotes(detailsMobileVO.getSoDetails().getServiceLocation().getServiceLocationNotes());
			serviceLocation.setSoContactId(detailsMobileVO.getSoDetails().getServiceLocation().getSoContactId());
		}
		return serviceLocation;
	}
	/**
	 * method to map the altenateservicelocation details
	 * @param detailsMobileVO
	 * @return
	 */
	private AlternateServiceLocation mapAlternateServiceLocation(RetrieveSODetailsMobileVO detailsMobileVO){
		AlternateServiceLocation  alternateServiceLocation = new AlternateServiceLocation();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getAlternateServiceLocation()){
			alternateServiceLocation.setCustomerFirstName(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getCustomerFirstName());
			alternateServiceLocation.setCustomerLastName(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getCustomerLastName());
			alternateServiceLocation.setStreetAddress1(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getStreetAddress1());
			alternateServiceLocation.setStreetAddress2(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getStreetAddress2());
			alternateServiceLocation.setCity(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getCity());
			alternateServiceLocation.setState(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getState());
			alternateServiceLocation.setZip(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getZip());
			alternateServiceLocation.setPrimaryPhone(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getPrimaryPhone());
			alternateServiceLocation.setAlternatePhone(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getAlternatePhone());
			alternateServiceLocation.setEmail(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getEmail());
			alternateServiceLocation.setSoContactId(detailsMobileVO.getSoDetails().getAlternateServiceLocation().getSoContactId());
		}
		return alternateServiceLocation;
	}
	/**
	 * method to map the maincategory and tasks of the service order
	 * @param detailsMobileVO
	 * @return
	 */
	private Scope mapScopeDetails(RetrieveSODetailsMobileVO detailsMobileVO){
		Scope scope = new Scope();
		Tasks tasks = new Tasks();

		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getScope()){
			if(null != detailsMobileVO.getSoDetails().getScope().getMainServiceCategory()){
				scope.setMainServiceCategory(detailsMobileVO.getSoDetails().getScope().getMainServiceCategory());
			}
			
			Integer buyerId = detailsMobileVO.getSoDetails().getBuyer().getBuyerUserId();			
			LOGGER.info("buyer Id"+buyerId);
			
			
			if( null != detailsMobileVO.getSoDetails().getScope().getTasks().getTask()){
				List <TaskDetailVO> taskDetails = detailsMobileVO.getSoDetails().getScope().getTasks().getTask();
				List <Task> taskResponse = new ArrayList<Task>();
				if(null != taskDetails && !taskDetails.isEmpty()){
					for(TaskDetailVO taskDetail : taskDetails){
						Task task = new Task();
						task.setTaskId(taskDetail.getTaskId());
						task.setTaskName(taskDetail.getTaskName());
						task.setTaskStatus(taskDetail.getTaskStatus());
						task.setTaskCategory(taskDetail.getTaskCategory());
						task.setTasksubCategory(taskDetail.getTaskCategory());
						task.setTaskSkill(taskDetail.getTaskSkill());
						
						if(PublicAPIConstant.RELAY_SERVICES_BUYER_ID.equals(buyerId)){				
							task.setTaskComments(taskDetail.getTaskComments());
						}else{
							//SL-20728 : removing html tags
							task.setTaskComments(ServiceLiveStringUtils.removeHTML(taskDetail.getTaskComments()));
						}						
						task.setTaskType(taskDetail.getTaskType());
						task.setCustPrePaidAmount(taskDetail.getCustPrePaidAmount());
						taskResponse.add(task);
					}
				}

				tasks.setTask(taskResponse);
				scope.setTasks(tasks);
			}
		}
		return scope;
	}
	/**
	 * Method to map the AddOn List
	 * @param detailsMobileVO
	 * @return
	 */
	private AddOnList mapAddOnDetails(RetrieveSODetailsMobileVO detailsMobileVO) {
		AddOnList addOnList = new AddOnList();

		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getAddonList()&& 
				null != detailsMobileVO.getSoDetails().getAddonList().getAddon()){
			List<AddOnVO>addOnVOs = detailsMobileVO.getSoDetails().getAddonList().getAddon();
			List<AddOn> addOns = new ArrayList<AddOn>();
			if(null != addOnVOs && !addOnVOs.isEmpty()){
				for(AddOnVO addOnDetail:addOnVOs){
					AddOn addOn = new AddOn();
					addOn.setSoAddonId(addOnDetail.getSoAddonId());
					addOn.setAddonSKU(addOnDetail.getAddonSKU());
					addOn.setDescription(addOnDetail.getDescription());
					addOn.setCustomerCharge(addOnDetail.getCustomerCharge());
					addOn.setMiscInd(addOnDetail.getMiscInd());
					addOn.setQty(addOnDetail.getQty());
					addOn.setEditable(addOnDetail.getEditable());
					addOn.setProviderPayment(addOnDetail.getProviderPayment());
					addOn.setMargin(addOnDetail.getMargin());
					addOn.setQuantity(addOnDetail.getQuantity());
					addOn.setCoverageType(addOnDetail.getCoverageType());
					addOn.setTaxPercentage(addOnDetail.getTaxPercentage());
					addOns.add(addOn);
				}
			}
			addOnList.setAddon(addOns);	
		}
		return addOnList;
	}
	/**
	 * Method to map the Part details
	 * @param detailsMobileVO
	 * @return
	 */
	private Parts mapPartDetails(RetrieveSODetailsMobileVO detailsMobileVO) {

		Parts parts = new Parts();		
		PickUpLocation pickUpLocation = new PickUpLocation();
		List<Part>partDetail = new ArrayList<Part>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getParts()){
			if(null != detailsMobileVO.getSoDetails().getParts().getPart()){
				List<PartVO>partVOs = detailsMobileVO.getSoDetails().getParts().getPart();
				if(null != partVOs && !partVOs.isEmpty()){
					for(PartVO partVO : partVOs){
						Part part = new Part();
						part.setPartId(partVO.getPartId());
						part.setPartName(partVO.getPartName());
						part.setManufacturer(partVO.getManufacturer());
						part.setModelNumber(partVO.getModelNumber());
						part.setSerialNumber(partVO.getSerialNumber());
						part.setOemNumber(partVO.getOemNumber());
						part.setOrderNumber(partVO.getOrderNumber());
						part.setPurchaseOrderNumber(partVO.getPurchaseOrderNumber());
						part.setSize(partVO.getSize());
						part.setWeight(partVO.getWeight());
						part.setVendorPartNumber(partVO.getVendorPartNumber());
						part.setPartType(partVO.getPartType());
						part.setQty(partVO.getQty());
						part.setPartStatus(partVO.getPartStatus());
						part.setPartDescription(partVO.getPartDescription());
						part.setAdditionalPartInfo(partVO.getAdditionalPartInfo());
						part.setShippingCarrier(partVO.getShippingCarrier());
						part.setShippingTrackingNumber(partVO.getShippingTrackingNumber());
						part.setShipDate(partVO.getShipDate());
						part.setCoreReturnCarrier(partVO.getCoreReturnCarrier());
						part.setCoreReturnTrackingNumber(partVO.getCoreReturnTrackingNumber());
						part.setCoreReturnDate(partVO.getCoreReturnDate());
						part.setPickupLocationAvailability(partVO.getPickupLocationAvailability());
						if(null !=partVO.getPickupLocation()){
							pickUpLocation.setPickupLocationName(partVO.getPickupLocation().getPickupLocationName());
							pickUpLocation.setPickupLocationStreet1(partVO.getPickupLocation().getPickupLocationStreet1());
							pickUpLocation.setPickupLocationStreet2(partVO.getPickupLocation().getPickupLocationStreet2());
							pickUpLocation.setPickupLocationCity(partVO.getPickupLocation().getPickupLocationCity());
							pickUpLocation.setPickupLocationState(partVO.getPickupLocation().getPickupLocationState());
							pickUpLocation.setPickupLocationZip(partVO.getPickupLocation().getPickupLocationZip());
							part.setPickupLocation(pickUpLocation);
						}
						partDetail.add(part);
					}
				}
			}
			if(null != detailsMobileVO.getSoDetails().getParts().getCountofParts()){
				parts.setCountofParts(detailsMobileVO.getSoDetails().getParts().getCountofParts());
			}
			parts.setPart(partDetail);
		}
		return parts;
	}
	/**
	 * Method to map the merchandise details for the SO
	 * @param detailsMobileVO
	 * @return
	 */
	private Merchandises mapMerchandiseDetails(RetrieveSODetailsMobileVO detailsMobileVO) {
		Merchandises merchandises = new Merchandises();

		List<Merchandise>merchandiseList = new ArrayList<Merchandise>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getMerchandises()){

			if(null != detailsMobileVO.getSoDetails().getMerchandises().getMerchandise()){
				List<MerchandiseVO>merchandiseVOs = detailsMobileVO.getSoDetails().getMerchandises().getMerchandise();
				if(null !=merchandiseVOs && !merchandiseVOs.isEmpty()){
					for(MerchandiseVO merchandiseVO :merchandiseVOs){
						Merchandise merchandise = new Merchandise();
						merchandise.setPartId(merchandiseVO.getPartId());
						merchandise.setManufacturer(merchandiseVO.getManufacturer());
						merchandise.setModelNumber(merchandiseVO.getModelNumber());
						merchandise.setSerialNumber(merchandiseVO.getSerialNumber());
						merchandiseList.add(merchandise);
					}
				}
			}
			merchandises.setMerchandise(merchandiseList);
		}
		return merchandises;
	}
	/**
	 * method to map the invoice part's details
	 * @param detailsMobileVO
	 * @return
	 */
	private InvoicePartsList mapInvoicePartsList(RetrieveSODetailsMobileVO detailsMobileVO) {
		InvoicePartsList invoicePartsList = new InvoicePartsList();	

		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getInvoicePartsList()){
			List<InvoicePart> invoiceParts = new ArrayList<InvoicePart>();


			if(null != detailsMobileVO.getSoDetails().getInvoicePartsList().getInvoicePart()){
				List<InvoiceVO> invoiceVOs = detailsMobileVO.getSoDetails().getInvoicePartsList().getInvoicePart();
				if(null != invoiceVOs && !invoiceVOs.isEmpty()){
					for(InvoiceVO invoiceVO:invoiceVOs){
						List<InvoiceSupplier>suppliers = new ArrayList<InvoiceSupplier>();
						List<InvoiceSupplierVO> supplierVOs = new ArrayList<InvoiceSupplierVO>();

						List<Document>documents = new ArrayList<Document>();
						List<DocumentDetailsVO>documentDetailsVOs = new ArrayList<DocumentDetailsVO>();
						InvoicePart invoicePart = new InvoicePart();
						InvoiceSuppliers invoiceSuppliers = new InvoiceSuppliers();
						InvoiceDocuments invoiceDocuments = new InvoiceDocuments();	

						invoicePart.setInvoicePartId(invoiceVO.getInvoicePartId());
						invoicePart.setPartCoverage(invoiceVO.getPartCoverage());
						invoicePart.setPartSource(invoiceVO.getPartSource());
						invoicePart.setPartNumber(invoiceVO.getPartNumber());
						invoicePart.setPartDescription(invoiceVO.getPartDescription());
						invoicePart.setInvoiceNumber(invoiceVO.getInvoiceNumber());
						invoicePart.setUnitCost(invoiceVO.getUnitCost());
						invoicePart.setRetailPrice(invoiceVO.getRetailPrice());
						invoicePart.setQty(invoiceVO.getQty());
						invoicePart.setNonSearsSource(invoiceVO.getNonSearsSource());
						invoicePart.setDivisionNumber(invoiceVO.getDivisionNumber());
						invoicePart.setSourceNumber(invoiceVO.getSourceNumber());
						invoicePart.setCategory(invoiceVO.getCategory());
						invoicePart.setPartStatus(invoiceVO.getPartStatus());
						invoicePart.setIsManual(invoiceVO.getIsManual());
						if(null != invoiceVO.getInvoiceSuppliers().getInvoiceSupplier()){						
							supplierVOs = invoiceVO.getInvoiceSuppliers().getInvoiceSupplier();
							if(null !=supplierVOs && !supplierVOs.isEmpty()){
								for(InvoiceSupplierVO supplierVO :supplierVOs){
									InvoiceSupplier invoiceSupplier = new InvoiceSupplier();
									invoiceSupplier.setSupplierName(supplierVO.getSupplierName());
									invoiceSupplier.setAddress1(supplierVO.getAddress1());
									invoiceSupplier.setAddress2(supplierVO.getAddress2());
									invoiceSupplier.setCity(supplierVO.getCity());
									invoiceSupplier.setState(supplierVO.getState());
									invoiceSupplier.setZip(supplierVO.getZip());
									invoiceSupplier.setPhone(supplierVO.getPhone());
									invoiceSupplier.setFax(supplierVO.getFax());
									invoiceSupplier.setLatitude(supplierVO.getLatitude());
									invoiceSupplier.setLongitude(supplierVO.getLongitude());
									suppliers.add(invoiceSupplier);
								}
							}
						}
						invoiceSuppliers.setInvoiceSupplier(suppliers);
						invoicePart.setInvoiceSuppliers(invoiceSuppliers);
						if(null !=invoiceVO.getInvoiceDocuments().getDocument()){
							documentDetailsVOs =invoiceVO.getInvoiceDocuments().getDocument();
							if(null != documentDetailsVOs && !documentDetailsVOs.isEmpty()){
								for(DocumentDetailsVO documentDetailsVO : documentDetailsVOs){
									Document document = new Document();
									document.setDocumentId(documentDetailsVO.getDocumentId());
									document.setDocumentType(documentDetailsVO.getDocumentType());
									document.setDocumentDescription(documentDetailsVO.getDocumentDescription());
									document.setFileType(documentDetailsVO.getFileType());
									document.setFileName(documentDetailsVO.getFileName());
									document.setUploadedBy(documentDetailsVO.getUploadedBy());
									document.setUploadedbyName(documentDetailsVO.getUploadedbyName());
									document.setUploadDateTime(documentDetailsVO.getUploadDateTime());
									document.setDocSize(documentDetailsVO.getDocSize());
									documents.add(document);
								}
							}
						}
						invoiceDocuments.setDocument(documents);
						invoicePart.setInvoiceDocuments(invoiceDocuments);
						invoiceParts.add(invoicePart);
					}
				}
			}
			invoicePartsList.setInvoicePart(invoiceParts);
			invoicePartsList.setNoPartsRequiredInd(detailsMobileVO.getSoDetails().getInvoicePartsList().getNoPartsRequiredInd());
		}
		return invoicePartsList;
	}
	/**
	 * Method to map the documnet details of the SO
	 * @param detailsMobileVO
	 * @return
	 */
	private Documents mapDocumentDetails(RetrieveSODetailsMobileVO detailsMobileVO) {
		Documents documents = new Documents();	
		List<Document>documentList = new ArrayList<Document>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getDocuments()){

			if(null != detailsMobileVO.getSoDetails().getDocuments().getDocument()){
				List<DocumentDetailsVO>documentDetailsVOs = detailsMobileVO.getSoDetails().getDocuments().getDocument();		
				if(null !=documentDetailsVOs && !documentDetailsVOs.isEmpty()){
					for(DocumentDetailsVO documentDetailsVO : documentDetailsVOs){
						Document document = new Document();
						document.setDocumentId(documentDetailsVO.getDocumentId());
						document.setDocumentType(documentDetailsVO.getDocumentType());
						document.setDocumentDescription(documentDetailsVO.getDocumentDescription());
						document.setFileType(documentDetailsVO.getFileType());
						document.setFileName(documentDetailsVO.getFileName());
						document.setUploadedBy(documentDetailsVO.getUploadedBy());
						document.setUploadedbyName(documentDetailsVO.getUploadedbyName());
						document.setUploadDateTime(documentDetailsVO.getUploadDateTime());
						document.setDocSize(documentDetailsVO.getDocSize());
						documentList.add(document);
					}
				}
			}
			documents.setDocument(documentList);
			if(null != detailsMobileVO.getSoDetails().getDocuments().getMaxuploadlimitPerDoc()){
				documents.setMaxuploadlimitPerDoc(detailsMobileVO.getSoDetails().getDocuments().getMaxuploadlimitPerDoc());
			}
			if(null!=detailsMobileVO.getSoDetails().getDocuments().getMaxUploadLimitPerSO()){
				documents.setMaxUploadLimitPerSO(detailsMobileVO.getSoDetails().getDocuments().getMaxUploadLimitPerSO());
			}
			if(null!=detailsMobileVO.getSoDetails().getDocuments().getRemaininguploadLimitPerSO()){
				documents.setRemaininguploadLimitPerSO(detailsMobileVO.getSoDetails().getDocuments().getRemaininguploadLimitPerSO());
			}
		}
		return documents;
	}
	/**
	 * method to map the buyerreferences of the SO
	 * @param detailsMobileVO
	 * @return
	 */
	private BuyerReferences mapBuyerReferences(RetrieveSODetailsMobileVO detailsMobileVO) {
		BuyerReferences buyerReferences = new BuyerReferences();	
		List<BuyerReference>references = new ArrayList<BuyerReference>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getBuyerReferences()){
			if(null != detailsMobileVO.getSoDetails().getBuyerReferences().getBuyerReference()){
				List<BuyerRefVO>buyerRefVOs = detailsMobileVO.getSoDetails().getBuyerReferences().getBuyerReference();
				if(null !=buyerRefVOs && !buyerRefVOs.isEmpty()){
					for(BuyerRefVO buyerRefVO : buyerRefVOs){
						BuyerReference buyerReference = new BuyerReference();
						buyerReference.setRefName(buyerRefVO.getRefName());
						buyerReference.setRefValue(buyerRefVO.getRefValue());
						references.add(buyerReference);
					}
				}
			}
			buyerReferences.setBuyerReference(references);
		}
		return buyerReferences;
	}
	/**
	 * method to map the notes of SO
	 * @param detailsMobileVO
	 * @return
	 */
	private Notes mapNotesForSO(RetrieveSODetailsMobileVO detailsMobileVO) {
		Notes notes = new Notes();	
		List<Note>noteList = new ArrayList<Note>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getNotes()){
			if(null!=detailsMobileVO.getSoDetails().getNotes().getNote()){
				List<NoteVO>noteVOs = detailsMobileVO.getSoDetails().getNotes().getNote();
				if(null !=noteVOs && !noteVOs.isEmpty()){
					for(NoteVO noteVO : noteVOs){
						Note note= new Note();
						note.setNoteId(noteVO.getNoteId());
						note.setNoteType(noteVO.getNoteType());
						note.setNoteSubject(noteVO.getNoteSubject());
						note.setNoteBody(noteVO.getNoteBody());
						note.setAuthor(noteVO.getAuthor());
						note.setCreatedDate(noteVO.getCreatedDate());
						noteList.add(note);
					}
				}
			}
			notes.setNote(noteList);
		}
		return notes;
	}
	/**
	 * method to map the support notes of the SO
	 * @param detailsMobileVO
	 * @return
	 */
	private SupportNotes mapSupportNotes(RetrieveSODetailsMobileVO detailsMobileVO) {
		SupportNotes supportNotes = new SupportNotes();
		List<SupportNote>supportNoteList = new ArrayList<SupportNote>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getSupportNotes()){
			if(null != detailsMobileVO.getSoDetails().getSupportNotes().getSupportNote()){
				List<SupportNoteVO>supportNoteVOs =detailsMobileVO.getSoDetails().getSupportNotes().getSupportNote();
				if(null != supportNoteVOs && !supportNoteVOs.isEmpty()){
					for(SupportNoteVO supportNoteVO:supportNoteVOs){
						SupportNote supportNote = new SupportNote();
						supportNote.setNoteId(supportNoteVO.getNoteId());
						supportNote.setNoteSubject(supportNoteVO.getNoteSubject());
						supportNote.setNote(supportNoteVO.getNote());
						supportNoteList.add(supportNote);
					}
				}
			}
			supportNotes.setSupportNote(supportNoteList);
		}
		return supportNotes;
	}
	/**
	 * method to map the so trip details
	 * @param detailsMobileVO
	 * @return
	 */
	private SOTrips mapSOTrips(RetrieveSODetailsMobileVO detailsMobileVO) {
		SOTrips soTrips = new SOTrips();			
		List<SOTrip>soTripList=new ArrayList<SOTrip>();
		if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails()
				&& null != detailsMobileVO.getSoDetails().getTripDetails()){
			if(null != detailsMobileVO.getSoDetails().getTripDetails().getTrip()){
				List<TripVO>tripVOs=detailsMobileVO.getSoDetails().getTripDetails().getTrip();
				if(null !=tripVOs && !tripVOs.isEmpty()){
					for(TripVO tripVO : tripVOs){
						SOTripChangeLogs soTripChangeLogs = new SOTripChangeLogs();	
						SOTrip soTrip = new SOTrip();
						List<SOTripChangeLog>changeLogs= new ArrayList<SOTripChangeLog>();
						List<SOTripChangeLogVO>changeLogVOs = new ArrayList<SOTripChangeLogVO>();

						soTrip.setTripNo(tripVO.getTripNo());
						soTrip.setCurrentApptStartDate(tripVO.getCurrentApptStartDate());
						soTrip.setCurrentApptEndDate(tripVO.getCurrentApptEndDate());
						soTrip.setCurrentApptStartTime(tripVO.getCurrentApptStartTime());
						soTrip.setCurrentApptEndTime(tripVO.getCurrentApptEndTime());
						soTrip.setCheckInTime(tripVO.getCheckInTime());
						soTrip.setCheckOutTime(tripVO.getCheckOutTime());
						soTrip.setProviderName(tripVO.getProviderName());
						soTrip.setTripStatus(tripVO.getTripStatus());
						soTrip.setTripStartSource(tripVO.getTripStartSource());
						soTrip.setTripEndSource(tripVO.getTripEndSource());
						soTrip.setNextApptStartDate(tripVO.getNextApptStartDate());
						soTrip.setNextApptEndDate(tripVO.getNextApptEndDate());
						soTrip.setNextApptStartTime(tripVO.getNextApptStartTime());
						soTrip.setNextApptEndTime(tripVO.getNextApptEndTime());
						soTrip.setRevisitReason(tripVO.getRevisitReason());
						soTrip.setWorkStartedIndicator(tripVO.getWorkStartedIndicator());
						soTrip.setMerchandiseDeliveredIndicator(tripVO.getMerchandiseDeliveredIndicator());
						soTrip.setRevisitComments(tripVO.getRevisitComments());
						changeLogVOs=tripVO.getChangeLogs().getChangeLog();
						if(null != changeLogVOs && !changeLogVOs.isEmpty()){
							for(SOTripChangeLogVO logVO :changeLogVOs){
								SOTripChangeLog soTripChangeLog = new SOTripChangeLog();
								soTripChangeLog.setChangeComments(logVO.getChangeComments());
								soTripChangeLog.setUpdatedComponent(logVO.getUpdatedComponent());
								changeLogs.add(soTripChangeLog);						
							}			
						}
						soTripChangeLogs.setChangeLog(changeLogs);
						soTrip.setChangeLogs(soTripChangeLogs);	
						soTripList.add(soTrip);
					}
				}
			}
			soTrips.setTrip(soTripList);
		}
		return soTrips;
	}


	
	/**
	 * @param mobileSOAdvanceSearchRequest
	 * @param providerId
	 * @param firmId
	 * @param roleId
	 * @return
	 */
	public SOAdvanceSearchCriteriaVO mapSoAdvanceSearchRequest(
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest,
			Integer providerId, Integer firmId, Integer roleId) {
		// MobileSOSearchCriteria soSearchCriteria = mobileSOSearchRequest.getSearchCriteria();

		SOAdvanceSearchCriteriaVO soSearchCriteriaVO = null;
		soSearchCriteriaVO = mapSOAdvanceSearchCriteriaVO(mobileSOAdvanceSearchRequest,providerId,roleId);
		soSearchCriteriaVO = mapSearchDates(soSearchCriteriaVO,mobileSOAdvanceSearchRequest);
		soSearchCriteriaVO.setFirmId(firmId);
		return soSearchCriteriaVO;
	}


	/**
	 * @param mobileSOAdvanceSearchRequest
	 * @param providerId
	 * @param roleId
	 * @return
	 */
	private SOAdvanceSearchCriteriaVO mapSOAdvanceSearchCriteriaVO(
			MobileSOAdvanceSearchRequest mobileSOAdvanceSearchRequest,
			Integer providerId, Integer roleId) {

		MobileSOAdvanceSearchCriteria soSearchCriteria = mobileSOAdvanceSearchRequest.getAdvanceSearchCriteria();
		Integer pageSize = mobileSOAdvanceSearchRequest.getPageSize();
		Integer pageNo = mobileSOAdvanceSearchRequest.getPageNo();
		SOAdvanceSearchCriteriaVO advanceSearchCriteriaVO = new SOAdvanceSearchCriteriaVO();

		pageSize = ((null != pageSize) ? pageSize
				: PublicMobileAPIConstant.DEFAULT_PAGE_SIZE);

		pageNo = ((null != pageNo) ? pageNo :  PublicMobileAPIConstant.DEFAULT_PAGE_NUMBER);

		advanceSearchCriteriaVO.setPageSize(pageSize);
		advanceSearchCriteriaVO.setPageNo(pageNo);

		advanceSearchCriteriaVO
		.setPageLimit((pageSize * (pageNo - 1)) );


		if(null != soSearchCriteria.getStatuses()){
			advanceSearchCriteriaVO.setStatusIds(soSearchCriteria.getStatuses().getValue());
		}
		if(null != soSearchCriteria.getSubStatuses()){
			List<Integer> subStatuses = soSearchCriteria.getSubStatuses().getValue();
			// When substatus = no substatus, search for orders with substatus id as NULL also
			if(!subStatuses.isEmpty()){
				if(subStatuses.contains(MPConstants.NO_SUBSTATUS)){
					advanceSearchCriteriaVO.setNoSubStatusInd(true);
				}
			}
			advanceSearchCriteriaVO.setSubStatusIds(soSearchCriteria.getSubStatuses().getValue());
		} 
		if(null != soSearchCriteria.getMarkets()){
			advanceSearchCriteriaVO.setMarketIds(soSearchCriteria.getMarkets().getValue());
		}
		if(null != soSearchCriteria.getServiceProIds()){
			advanceSearchCriteriaVO.setServiceProIds(soSearchCriteria.getServiceProIds().getValue());
		}
		if(null != soSearchCriteria.getFlaggedOnlyInd()){
			advanceSearchCriteriaVO.setFlaggedInd(soSearchCriteria.getFlaggedOnlyInd());
		}
		if(null != soSearchCriteria.getUnAssignedInd()){
			advanceSearchCriteriaVO.setUnAssignedInd(soSearchCriteria.getUnAssignedInd());
		}
		advanceSearchCriteriaVO.setAcceptedResourceId(providerId);

		advanceSearchCriteriaVO.setRoleId(roleId);
		return advanceSearchCriteriaVO;

	}


	/**
	 * @param soSearchResultsVO
	 * @return
	 */
	public MobileSOAdvanceSearchResponse mapProviderSOAdvanceSearchResponse(
			SOSearchResultsVO soSearchResultsVO) {

		List<SOSearchResultVO> searchResultVOs = soSearchResultsVO.getSearchResultVOs();
		MobileSOAdvanceSearchResponse mobileSOSearchResponse = new MobileSOAdvanceSearchResponse();
		Results results= Results.getSuccess();
		mobileSOSearchResponse.setResults(results);
		OrderDetails orderDetails = new OrderDetails();
		OrderDetail orderDetail =null;
		List<OrderDetail> orderDetailValues = new ArrayList<OrderDetail>();
		if(null!= searchResultVOs && !searchResultVOs.isEmpty()){
			for(SOSearchResultVO searchResultVO : searchResultVOs){
				orderDetail = mapGeneralOrderDetails(searchResultVO);
				/*if(searchResultVO.isGroupInd()){
					orderDetail = mapGroupedSODetails(searchResultVO,orderDetail);
				}*/
				orderDetail = mapServiceOrderDates(searchResultVO,orderDetail);

				orderDetailValues.add(orderDetail);
			}
		}
		orderDetails.setOrderDetail(orderDetailValues);
		mobileSOSearchResponse.setTotalOrderCountFetched(orderDetailValues.size());
		mobileSOSearchResponse.setTotalOrderCount(soSearchResultsVO.getTotalSOCount());
		mobileSOSearchResponse.setOrderDetails(orderDetails);
		return mobileSOSearchResponse;

	}
	/**
	 * @Description: Mapping Filter Details to the response
	 * @param filterDetailsMap
	 */
	public GetFilterResponse mapFilterDetailsResponse(Map<Integer, FiltersVO> filterDetailsMap){

		GetFilterResponse response =new GetFilterResponse();

		Filters filters =new Filters();
		List<Filter> filterList = new ArrayList<Filter>();

		Filter filter = null;
		Set<Integer> filterIdSet = null;
		FiltersVO filtersVO=null;

		Integer filterId =null;

		if(null !=filterDetailsMap && ! filterDetailsMap.isEmpty()){
			filterIdSet=filterDetailsMap.keySet();
			List<Integer> filterIdList =new ArrayList<Integer>(filterIdSet);
			if(null != filterIdList && ! filterIdList.isEmpty()){
				for(int i=0;i < filterIdList.size();i++){
					filterId = filterIdList.get(i);
					filtersVO = filterDetailsMap.get(filterId);
					filter = mapFilterValues(filtersVO,filterId);
					filterList.add(filter);
				}
				//  code to sort filter list based on filterName
				sortFilterList(filterList);
			}
			response.setResults(Results.getSuccess());
			filters.setFilter(filterList);
			response.setFilters(filters);
		}

		return response; 
	}

	/**
	 * @Description:Method to sort the filterList based on filterName
	 * @param filterList
	 */
	private void sortFilterList(List<Filter> filterList) {
		if(null !=filterList && filterList.size() > 0){
			Collections.sort(filterList, new Comparator<Filter>() {

				public int compare(Filter o1,Filter o2){
					int compareValue=0;
					if(StringUtils.isNotEmpty(o1.getFilterName()) &&  StringUtils.isNotEmpty(o2.getFilterName())){
						compareValue = o1.getFilterName().compareTo(o2.getFilterName());
					}
					return compareValue;
				}
			});
		} 
	}


	/**
	 * @param filtersVO
	 * @param filterId
	 * @return
	 * to map filter values
	 */
	private Filter mapFilterValues(FiltersVO filtersVO, Integer filterId) {
		Filter filter = new Filter();
		Map<String,List<FilterCriteriaVO>> searchCriteriaMap =null;
		FilterSearchCriteria filterSearchCriteria =null;
		Map<String,Object> multipleValueFilters = new HashMap<String, Object>();
		//set name,id
		filter.setFilterId(filterId.toString());
		filter.setFilterName(filtersVO.getFilterName());

		searchCriteriaMap = filtersVO.getCriteriaMap();
		filterSearchCriteria = new FilterSearchCriteria();

		filterSearchCriteria = mapSingleFilterValues(
				filterSearchCriteria,searchCriteriaMap);
		multipleValueFilters = mapMultipleFilterValues(searchCriteriaMap);

		if(multipleValueFilters != null){
			filterSearchCriteria = mapFilterValuesToResponse(filterSearchCriteria,multipleValueFilters);	
		}
		//set criteriaResponse to filter.
		filter.setSearchCriterias(filterSearchCriteria);
		return filter;
	}
	/**
	 * @Description:Method to map filter criterias
	 * @param filterSearchCriteria
	 * @param multipleValueFilters
	 * @return filterSearchCriteria
	 */
	@SuppressWarnings("unchecked")
	private FilterSearchCriteria mapFilterValuesToResponse(
			FilterSearchCriteria filterSearchCriteria,
			Map<String, Object> multipleValueFilters) {

		MarketCriteria criteriaValueMarket = new MarketCriteria();
		StatusCriteria criteriaValueStatus = new StatusCriteria();
		ServiceProNameCriteria criteriaValueServiceProName=new ServiceProNameCriteria();
		SubStatusCriteria criteriaValueSubStatus = new SubStatusCriteria();
		List<Market> criteriaValueMarketList =new ArrayList<Market>();
		List<StatusValue> criteriaValueStatusList =new ArrayList<StatusValue>();
		List<SubStatusValue> criteriaValuesubStatusList =new ArrayList<SubStatusValue>();
		List<ServiceProName> criteriaValueserviceProNameList=new ArrayList<ServiceProName>();

		criteriaValueMarketList = (List<Market>) multipleValueFilters.get(MPConstants.MARKET_CRITERIA_LIST);
		criteriaValueStatusList = (List<StatusValue>) multipleValueFilters.get(MPConstants.STATUS_CRITERIA_LIST);
		criteriaValuesubStatusList = (List<SubStatusValue>) multipleValueFilters.get(MPConstants.SUBSTATUS_CRITERIA_LIST);
		criteriaValueserviceProNameList = (List<ServiceProName>) multipleValueFilters.get(MPConstants.PROVIDER_RESOURCE_CRITERIA_LIST);

		if(null !=criteriaValueMarketList && ! criteriaValueMarketList.isEmpty()){
			criteriaValueMarket.setMarket(criteriaValueMarketList);
		}
		if(null !=criteriaValueStatusList && ! criteriaValueStatusList.isEmpty()){
			criteriaValueStatus.setOrderStatus(criteriaValueStatusList);
		}
		if(null !=criteriaValuesubStatusList && ! criteriaValuesubStatusList.isEmpty()){
			criteriaValueSubStatus.setSubStatus(criteriaValuesubStatusList);
		}
		if(null !=criteriaValueserviceProNameList && ! criteriaValueserviceProNameList.isEmpty()){
			criteriaValueServiceProName.setServiceProName(criteriaValueserviceProNameList);
		}
		// Checking presence of each criteria and set into response.
		if(null != criteriaValueMarket 
				&& null!=criteriaValueMarket.getMarket() 
				&&!(criteriaValueMarket.getMarket().isEmpty())){
			filterSearchCriteria.setMarkets(criteriaValueMarket);
		}
		if(null != criteriaValueStatus 
				&& null!=criteriaValueStatus.getOrderStatus()
				&&!(criteriaValueStatus.getOrderStatus().isEmpty())){
			filterSearchCriteria.setOrderStatuses(criteriaValueStatus);
		}
		if(null != criteriaValueSubStatus 
				&& null!=criteriaValueSubStatus.getSubStatus() 
				&&!(criteriaValueSubStatus.getSubStatus().isEmpty())){
			filterSearchCriteria.setOrderSubstatuses(criteriaValueSubStatus);
		}
		if(null != criteriaValueServiceProName 
				&& null!=criteriaValueServiceProName.getServiceProName() 
				&&!(criteriaValueServiceProName.getServiceProName().isEmpty())){
			filterSearchCriteria.setServiceProNames(criteriaValueServiceProName);
		}
		return filterSearchCriteria;
	}
	/**
	 * @param searchCriteriaMap 
	 * @return
	 * @Description:map filter values that can have list of search values at a time
	 */
	private Map<String,Object> mapMultipleFilterValues(
			Map<String, List<FilterCriteriaVO>> searchCriteriaMap) {

		List<Market> criteriaValueMarketList =new ArrayList<Market>();
		List<StatusValue> criteriaValueStatusList =new ArrayList<StatusValue>();
		List<SubStatusValue> criteriaValuesubStatusList =new ArrayList<SubStatusValue>();
		List<ServiceProName> criteriaValueserviceProNameList=new ArrayList<ServiceProName>();
		Map<String,Object> multipleValueFilters = new HashMap<String,Object>();
		List<FilterCriteriaVO> criteriaVOList = null;

		if(searchCriteriaMap.get(MPConstants.MARKET)!=null){

			criteriaVOList =   searchCriteriaMap.get(MPConstants.MARKET) ;
			criteriaValueMarketList = mapMarketValue(criteriaVOList);
		}
		if(searchCriteriaMap.get(MPConstants.ORDERSTATUS)!=null){

			criteriaVOList =   searchCriteriaMap.get(MPConstants.ORDERSTATUS) ;
			criteriaValueStatusList = mapStatusValue(criteriaVOList);
		}
		if(searchCriteriaMap.get(MPConstants.ORDERSUBSTATUS)!=null){

			criteriaVOList =   searchCriteriaMap.get(MPConstants.ORDERSUBSTATUS) ;
			criteriaValuesubStatusList = mapSubStatusValue(criteriaVOList);
		}
		if(searchCriteriaMap.get(MPConstants.PROVIDER_RESOURCE)!=null){

			criteriaVOList =   searchCriteriaMap.get(MPConstants.PROVIDER_RESOURCE) ;
			criteriaValueserviceProNameList = mapProviderResourceValue(criteriaVOList);
		}
		multipleValueFilters.put(MPConstants.PROVIDER_RESOURCE_CRITERIA_LIST, criteriaValueserviceProNameList);
		multipleValueFilters.put(MPConstants.SUBSTATUS_CRITERIA_LIST, criteriaValuesubStatusList);
		multipleValueFilters.put(MPConstants.STATUS_CRITERIA_LIST, criteriaValueStatusList);
		multipleValueFilters.put(MPConstants.MARKET_CRITERIA_LIST, criteriaValueMarketList);

		return multipleValueFilters;
	}
	/** 
	 * @Description:Method to map provider names 
	 * @param criteriaVOList
	 * @return
	 */
	private List<ServiceProName> mapProviderResourceValue(
			List<FilterCriteriaVO> criteriaVOList) {

		List<ServiceProName> criteriaValueserviceProNameList =new ArrayList<ServiceProName>();
		ServiceProName serviceProName = null;

		if(null !=criteriaVOList && ! criteriaVOList.isEmpty()){

			for(FilterCriteriaVO criteriaVOForList :criteriaVOList){

				serviceProName=new ServiceProName();

				if(StringUtils.isNotBlank(criteriaVOForList.getSearchCriteriaValueId())
						&&(StringUtils.isNumeric(criteriaVOForList.getSearchCriteriaValueId()))){
					serviceProName.setId(Integer.parseInt(criteriaVOForList.getSearchCriteriaValueId()));
				}
				serviceProName.setValue(criteriaVOForList.getSearchCriteriaValueString());
				criteriaValueserviceProNameList.add(serviceProName);
			}
		}
		return criteriaValueserviceProNameList;

	}


	/** 
	 * @Description:Method to map substatus values
	 * @param criteriaVOList
	 * @return
	 */
	private List<SubStatusValue> mapSubStatusValue(List<FilterCriteriaVO> criteriaVOList) {
		List<SubStatusValue> criteriaValuesubStatusList =new ArrayList<SubStatusValue>();
		SubStatusValue subStatus = null;

		if(null !=criteriaVOList && ! criteriaVOList.isEmpty()){

			for(FilterCriteriaVO criteriaVOForList: criteriaVOList){
				subStatus=new SubStatusValue();
				if(StringUtils.isNotBlank(criteriaVOForList.getSearchCriteriaValueId())
						&&(StringUtils.isNumeric(criteriaVOForList.getSearchCriteriaValueId()))){
					subStatus.setId(Integer.parseInt(criteriaVOForList.getSearchCriteriaValueId()));
				}
				subStatus.setValue(criteriaVOForList.getSearchCriteriaValueString());
				criteriaValuesubStatusList.add(subStatus);
			}
		}
		return criteriaValuesubStatusList;

	}


	/**
	 * Method to map status values
	 * @param criteriaVOList
	 * @return
	 */
	private List<StatusValue> mapStatusValue(List<FilterCriteriaVO> criteriaVOList) {
		List<StatusValue> criteriaValueStatusList =new ArrayList<StatusValue>();
		StatusValue status = null;

		if(null !=criteriaVOList && ! criteriaVOList.isEmpty()){

			for(FilterCriteriaVO criteriaVOForList: criteriaVOList){
				status=new StatusValue();
				if(StringUtils.isNotBlank(criteriaVOForList.getSearchCriteriaValueId())
						&&(StringUtils.isNumeric(criteriaVOForList.getSearchCriteriaValueId()))){
					status.setId(Integer.parseInt(criteriaVOForList.getSearchCriteriaValueId()));
				}
				status.setValue(criteriaVOForList.getSearchCriteriaValueString());
				criteriaValueStatusList.add(status);
			}
		}
		return criteriaValueStatusList;

	}


	/**
	 * @Description:Method to map market values
	 * @param criteriaVOList
	 * @return
	 */
	private List<Market> mapMarketValue(List<FilterCriteriaVO> criteriaVOList) {
		List<Market> criteriaValueMarketList =new ArrayList<Market>();
		Market market = null;

		if(null !=criteriaVOList && ! criteriaVOList.isEmpty()){

			for(FilterCriteriaVO criteriaVOForList: criteriaVOList){
				market = new Market();
				if(StringUtils.isNotBlank(criteriaVOForList.getSearchCriteriaValueId())
						&&(StringUtils.isNumeric(criteriaVOForList.getSearchCriteriaValueId()))){
					market.setId(Integer.parseInt(criteriaVOForList.getSearchCriteriaValueId()));
				}
				market.setValue(criteriaVOForList.getSearchCriteriaValueString());
				criteriaValueMarketList.add(market);
			}
		}
		return criteriaValueMarketList;

	}


	/**
	 * @param filterSearchCriteria
	 * @param searchCriteriaMap 
	 * @param criteriaVO
	 * @param searchCriteriaMap 
	 * @return
	 * @Description:map filter values that can have only a single value set at a time eg:-unassigned,flagged and appointment
	 */
	private FilterSearchCriteria mapSingleFilterValues(FilterSearchCriteria filterSearchCriteria, Map<String, List<FilterCriteriaVO>> searchCriteriaMap) {

		AppointmentDateCriteria criteriaValueAppointmentDate=null;
		FilterCriteriaVO criteriaVO =null;
		List<FilterCriteriaVO> criteriaVOList = null;
		if(searchCriteriaMap.get(MPConstants.ORDER_FLAGGED)!=null){
			criteriaVOList =   searchCriteriaMap.get(MPConstants.ORDER_FLAGGED) ;
			if(null != criteriaVOList && !criteriaVOList.isEmpty()){
				criteriaVO = criteriaVOList.get(0);
				//Getting first Object 
				if(null!= criteriaVO && null!=criteriaVO.getFlaggedSo()){
					filterSearchCriteria.setFlagged(criteriaVO.getFlaggedSo());
				}
			}
		}
		if(searchCriteriaMap.get(MPConstants.ORDER_UNASSIGNED)!=null){

			criteriaVOList =   searchCriteriaMap.get(MPConstants.ORDER_UNASSIGNED) ;
			if(null != criteriaVOList && !criteriaVOList.isEmpty()){
				criteriaVO = criteriaVOList.get(0);
				if(null!= criteriaVO && null!=criteriaVO.getUnAssigned() ){
					filterSearchCriteria.setUnAssigned(criteriaVO.getUnAssigned());
				}
			}

		}
		if(searchCriteriaMap.get(MPConstants.APPOINTMENT_TYPE)!=null){
			criteriaVOList =   searchCriteriaMap.get(MPConstants.APPOINTMENT_TYPE) ;
			if(null != criteriaVOList && !criteriaVOList.isEmpty()){
				criteriaVO = criteriaVOList.get(0);
				if(null!=criteriaVO && StringUtils.isNotBlank(criteriaVO.getSearchCriteriaValueId()) ){
					criteriaValueAppointmentDate=new AppointmentDateCriteria();
					criteriaValueAppointmentDate.setAppointmentType(criteriaVO.getSearchCriteriaValueId());
				}
			}
		}
		if(null !=criteriaValueAppointmentDate &&StringUtils.isNotBlank(criteriaValueAppointmentDate.getAppointmentType())
				&& criteriaValueAppointmentDate.getAppointmentType().equals(MPConstants.APPOINTMENT_VALUE_RANGE)){
			if(searchCriteriaMap.get(MPConstants.APPOINTMENT_START_DATE)!=null){
				criteriaVOList =   searchCriteriaMap.get(MPConstants.APPOINTMENT_START_DATE) ;
				if(null != criteriaVOList && !criteriaVOList.isEmpty()){
					criteriaVO = criteriaVOList.get(0);
					if(null!=criteriaVO && StringUtils.isNotBlank(criteriaVO.getSearchCriteriaValueId()) ){
						criteriaValueAppointmentDate.setAppointmentStartDate(criteriaVO.getSearchCriteriaValueId());
					}
				}
			}
			if(searchCriteriaMap.get(MPConstants.APPOINTMENT_END_DATE)!=null){
				criteriaVOList =   searchCriteriaMap.get(MPConstants.APPOINTMENT_END_DATE) ;
				if(null != criteriaVOList && !criteriaVOList.isEmpty()){
					criteriaVO = criteriaVOList.get(0);
					if(null!=criteriaVO && StringUtils.isNotBlank(criteriaVO.getSearchCriteriaValueId()) ){
						criteriaValueAppointmentDate.setAppointmentEndDate(criteriaVO.getSearchCriteriaValueId());
					}
				}
			}
		} 
		if(null != criteriaValueAppointmentDate 
				&& null!=criteriaValueAppointmentDate.getAppointmentType()){
			filterSearchCriteria.setAppointmentDates(criteriaValueAppointmentDate);
		}
		return filterSearchCriteria;
	}


	public FiltersVO mapSaveFilterRequest(SaveFilterRequest request, Integer resourceId){
		FiltersVO filtersVO = new FiltersVO();
		List<FilterCriteriaVO> filterCriteriasToSave = new ArrayList<FilterCriteriaVO>();

		List<Integer> markets = null;
		List<Integer> serviceProIds = null;
		List<Integer> statuses = null;
		List<Integer> subStatuses = null;


		filtersVO.setResourceId(resourceId);
		filtersVO.setFilterName(request.getFilterName());
		if(null != request.getFilterId()){
			filtersVO.setFilterId(request.getFilterId());
		}

		FilterCriterias filterCriteriasFromRequest =  request.getFilterCriterias();

		if(null != filterCriteriasFromRequest){
			if(null != filterCriteriasFromRequest.getMarkets()){
				markets = filterCriteriasFromRequest.getMarkets().getValue();
			}

			if(null != filterCriteriasFromRequest.getServiceProIds()){
				serviceProIds = filterCriteriasFromRequest.getServiceProIds().getValue();
			}

			if(null != filterCriteriasFromRequest.getStatuses()){
				statuses = filterCriteriasFromRequest.getStatuses().getValue();
			}

			if(null != filterCriteriasFromRequest.getSubStatuses()){
				subStatuses = filterCriteriasFromRequest.getSubStatuses().getValue();
			}
			//Market
			filterCriteriasToSave = mapMultipleValueFilterCriteria(filterCriteriasToSave, MPConstants.MARKET, markets, request.getFilterId());
			//resourceId
			filterCriteriasToSave = mapMultipleValueFilterCriteria(filterCriteriasToSave, MPConstants.PROVIDER_RESOURCE, serviceProIds, request.getFilterId());
			//status
			filterCriteriasToSave = mapMultipleValueFilterCriteria(filterCriteriasToSave, MPConstants.STATUS, statuses, request.getFilterId());
			//sub status
			filterCriteriasToSave = mapMultipleValueFilterCriteria(filterCriteriasToSave, MPConstants.SUB_STATUS, subStatuses, request.getFilterId());
			//is flagged
			filterCriteriasToSave = mapFlaggedFilterCriteria(filterCriteriasToSave, filterCriteriasFromRequest, request.getFilterId());
			//is unassigned
			filterCriteriasToSave = mapUnassignedFilterCriteria(filterCriteriasToSave, filterCriteriasFromRequest, request.getFilterId());
			//Appointment
			filterCriteriasToSave = mapAppointment(filterCriteriasToSave, filterCriteriasFromRequest.getAppointment(), request.getFilterId());

			filtersVO.setCriteriaList(filterCriteriasToSave);
		}


		return filtersVO;
	}
	/**
	 * @Description:map filter values that can have list of search values at a time to a list
	 * @param filterCriteriasToSave
	 * @param criteria
	 * @param criteriaList
	 * @param filterId
	 * @return List<FilterCriteriaVO>
	 */
	private List<FilterCriteriaVO> mapMultipleValueFilterCriteria(
			List<FilterCriteriaVO> filterCriteriasToSave, String criteria,
			List<Integer> criteriaList, Integer filterId) {
		FilterCriteriaVO criteriaVO = null;
		if(null != criteriaList && criteriaList.size()>0){
			for(Integer criteriaValue: criteriaList){
				criteriaVO = new FilterCriteriaVO();
				if(null != filterId){
					criteriaVO.setFilterId(filterId);
				}
				criteriaVO.setSearchCriteria(criteria);
				criteriaVO.setSearchCriteriaValueId(criteriaValue.toString());
				filterCriteriasToSave.add(criteriaVO);

			}
		}
		return filterCriteriasToSave;
	}

	private List<FilterCriteriaVO> mapFlaggedFilterCriteria(
			List<FilterCriteriaVO> filterCriteriasToSave,
			FilterCriterias filterCriteriasFromRequest, Integer filterId) {
		FilterCriteriaVO criteriaVO = null;
		if(null != filterCriteriasFromRequest.getFlaggedOnlyInd()){
			criteriaVO = new FilterCriteriaVO();
			if(null != filterId){
				criteriaVO.setFilterId(filterId);
			}
			criteriaVO.setSearchCriteria(MPConstants.ORDER_FLAGGED);
			if(filterCriteriasFromRequest.getFlaggedOnlyInd()){
				criteriaVO.setSearchCriteriaValueId(MPConstants.FLAG_VALUE);
			}else{
				criteriaVO.setSearchCriteriaValueId(MPConstants.UNFLAG_VALUE);
			}
			filterCriteriasToSave.add(criteriaVO);
		}
		return filterCriteriasToSave;

	}

	private List<FilterCriteriaVO> mapUnassignedFilterCriteria(
			List<FilterCriteriaVO> filterCriteriasToSave,
			FilterCriterias filterCriteriasFromRequest, Integer filterId) {
		FilterCriteriaVO criteriaVO = null;
		if(null != filterCriteriasFromRequest.getUnAssignedInd()){
			criteriaVO = new FilterCriteriaVO();
			if(null != filterId){
				criteriaVO.setFilterId(filterId);
			}
			criteriaVO.setSearchCriteria(MPConstants.ORDER_UNASSIGNED);
			if(filterCriteriasFromRequest.getUnAssignedInd()){
				criteriaVO.setSearchCriteriaValueId(MPConstants.UNASSIGNED_VALUE);
			}else{
				criteriaVO.setSearchCriteriaValueId(MPConstants.ASSIGNED_VALUE);
			}
			filterCriteriasToSave.add(criteriaVO);
		}
		return filterCriteriasToSave;

	}

	private List<FilterCriteriaVO> mapAppointment(
			List<FilterCriteriaVO> filterCriteriasToSave,
			com.newco.marketplace.api.mobile.beans.so.search.advance.Appointment appointment, Integer filterId) {
		FilterCriteriaVO criteriaVO = null;
		if(null != appointment){
			criteriaVO = new FilterCriteriaVO();
			if(null != filterId){
				criteriaVO.setFilterId(filterId);
			}
			criteriaVO.setSearchCriteria(MPConstants.APPOINTMENT_TYPE);
			criteriaVO.setSearchCriteriaValueId(appointment.getAppointmentFilter());
			filterCriteriasToSave.add(criteriaVO);

			if((MPConstants.APPOINTMENT_VALUE_RANGE).equals(appointment.getAppointmentFilter())){
				criteriaVO = new FilterCriteriaVO();
				if(null != filterId){
					criteriaVO.setFilterId(filterId);
				}
				criteriaVO.setSearchCriteria(MPConstants.APPOINTMENT_START_DATE);
				criteriaVO.setSearchCriteriaValueId(appointment.getStartRange());
				filterCriteriasToSave.add(criteriaVO);

				criteriaVO = new FilterCriteriaVO();
				if(null != filterId){
					criteriaVO.setFilterId(filterId);
				}
				criteriaVO.setSearchCriteria(MPConstants.APPOINTMENT_END_DATE);
				criteriaVO.setSearchCriteriaValueId(appointment.getEndRange());
				filterCriteriasToSave.add(criteriaVO);
			}
		}
		return filterCriteriasToSave;
	}


	/**@Description: Map request values to VO.
	 * @param apiVO
	 * @return MobileDashboardVO
	 */
	public MobileDashboardVO mapDashBoardcountRequest(APIRequestVO apiVO) {
		MobileDashboardVO dashboardVO=null;
		if(null!= apiVO.getRoleId() && MPConstants.ROLE_LEVEL_ONE.equals(apiVO.getRoleId())){
			//This default constructor populate wf state id list without received status.
			dashboardVO = new MobileDashboardVO();
		}else{
			//This default constructor populate wf state id list with received status.
			dashboardVO = new MobileDashboardVO(apiVO.getRoleId());
		}
		if(null!= apiVO.getProviderId()){
			dashboardVO.setFirmId(Integer.parseInt(apiVO.getProviderId()));
		}
		if(null!= apiVO.getProviderResourceId()){
			dashboardVO.setResourceId(apiVO.getProviderResourceId());
		}
		dashboardVO.setRoleId(apiVO.getRoleId());
		return dashboardVO;
	}


	public ViewDashboardResponse mapViewDashboardResponse(MobileDashboardVO dashboardVO, Integer roleId) {
		Tabs tabs =  new Tabs();
		List<Tab> tabList = new ArrayList<Tab>();
		ViewDashboardResponse response =new ViewDashboardResponse();;
		List<String> tabNamesAdded = new ArrayList<String>();
		if(null !=dashboardVO.getCountVO() && !dashboardVO.getCountVO().isEmpty()){
			for(DashBoardCountVO countVO: dashboardVO.getCountVO()){
				Tab tab = new Tab();
				tabNamesAdded.add(countVO.getTabName());

				tab.setTabName(countVO.getTabName());
				tab.setTabCount( null!=countVO.getTabCount() ? countVO.getTabCount():0);
				tabList.add(tab);
			}
			if((tabNamesAdded.size() < MPConstants.LEVEL_NOT_ONE_MAX_TAB_LIST && !roleId.equals(MPConstants.ROLE_LEVEL_ONE))
					||(tabNamesAdded.size() < MPConstants.LEVEL_ONE_MAX_TAB_LIST && roleId.equals(MPConstants.ROLE_LEVEL_ONE))){
				tabList = mapZeroTabCounts(tabNamesAdded,tabList,roleId);
			}			
		}else{
			// Default all to 0
			tabList = mapZeroTabCounts(tabNamesAdded,tabList,roleId);
		}
		sortTabName(tabList);
		tabs.setTab(tabList);
		response.setTabs(tabs);
		response.setResults(Results.getSuccess());
		return response;
	}
	/**
	 * @param tabNamesAdded
	 * @param tabList
	 * @param roleId 
	 * @return
	 * map zero count for tabs that are not fetched from DB
	 */
	protected List<Tab> mapZeroTabCounts(List<String> tabNamesAdded,
			List<Tab> tabList, Integer roleId) {

		if(!tabNamesAdded.containsAll(MPConstants.LEVEL_NOT_ONE_TAB_LIST) && !roleId.equals(MPConstants.ROLE_LEVEL_ONE)){
			if(!tabNamesAdded.contains(MPConstants.RECIEVED_TAB)){
				mapZeroCount(tabList,MPConstants.RECIEVED_TAB);
			}
			mapNonRecievedTabs(tabNamesAdded,tabList);
		}
		else if(!tabNamesAdded.containsAll(MPConstants.LEVEL_ONE_TAB_LIST) && roleId.equals(MPConstants.ROLE_LEVEL_ONE)){
			mapNonRecievedTabs(tabNamesAdded,tabList);
		}
		return tabList;
	}


	/**
	 * @param tabNamesAdded
	 * @param tabList
	 */
	protected void mapNonRecievedTabs(List<String> tabNamesAdded,
			List<Tab> tabList) {
		if(!tabNamesAdded.contains(MPConstants.ACCEPTED_TAB)){
			mapZeroCount(tabList,MPConstants.ACCEPTED_TAB);
		}
		if(!tabNamesAdded.contains(MPConstants.ACTIVE_TAB)){
			mapZeroCount(tabList,MPConstants.ACTIVE_TAB);
		}
		if(!tabNamesAdded.contains(MPConstants.PROBLEM_TAB)){
			mapZeroCount(tabList,MPConstants.PROBLEM_TAB);
		}
	}


	/**
	 * @param tabList
	 */
	protected void mapZeroCount(List<Tab> tabList,String tabName) {
		// TODO Auto-generated method stub
		Tab tab = new Tab();
		tab.setTabName(tabName);
		tab.setTabCount(MPConstants.ZERO_COUNT);
		tabList.add(tab);
	}


	/**
	 * method to map the Search Criteria response
	 * @param soSearchCriteriaVO
	 * @return
	 */
	public SearchCriterias mapGetSearchCriteriaResponse(SoSearchCriteriaVO soSearchCriteriaVO) {

		SearchCriterias searchCriterias = new SearchCriterias();
		OrderStatuses orderStatuses = new OrderStatuses();	
		Markets markets = new Markets();		
		ServiceProviders serviceProviders = new ServiceProviders();

		List<SearchCriteriaVO> searchCriteriaVOs = soSearchCriteriaVO.getSearchCriteriaVOs();
		Map<Integer , List<SoStatusVO>> statusMapVOs = soSearchCriteriaVO.getStatusMap();

		List<com.newco.marketplace.api.beans.searchCriteria.Market>marketList = new ArrayList<com.newco.marketplace.api.beans.searchCriteria.Market>() ;
		List<ServiceProvider>providerList = new ArrayList<ServiceProvider>() ;	

		List<Integer>marketTempList = new ArrayList<Integer>() ;
		List<Integer>providerTempList = new ArrayList<Integer>() ;		

		List<OrderStatus>wfStates = new ArrayList<OrderStatus>();	
		if(null != soSearchCriteriaVO && null!=soSearchCriteriaVO.getSearchCriteriaVOs()){
			for(SearchCriteriaVO searchCriteriaVO : searchCriteriaVOs){
				com.newco.marketplace.api.beans.searchCriteria.Market market = new com.newco.marketplace.api.beans.searchCriteria.Market();
				ServiceProvider providerDetails = new ServiceProvider();
				if(null !=searchCriteriaVO){
					market.setId(searchCriteriaVO.getMarketId());
					market.setDescr(searchCriteriaVO.getMarketName());	

					if(!marketTempList.contains(searchCriteriaVO.getMarketId())){
						marketTempList.add(searchCriteriaVO.getMarketId());
						marketList.add(market);
					}
					providerDetails.setId(searchCriteriaVO.getResourceId());
					providerDetails.setDescr(searchCriteriaVO.getRoutedProvider());

					if(!providerTempList.contains(searchCriteriaVO.getResourceId())){
						providerTempList.add(searchCriteriaVO.getResourceId());
						providerList.add(providerDetails);
					}
				}
			}
			sortMarketList(marketList);
			sortProviderList(providerList);
			markets.setMarket(marketList);
			serviceProviders.setServiceProvider(providerList);
			searchCriterias.setMarkets(markets);
			searchCriterias.setServiceProviders(serviceProviders);
			if(null!=statusMapVOs){
				List<SoStatusVO>receivedList = statusMapVOs.get(OrderConstants.ROUTED_STATUS);
				OrderStatus wfStateReceived = new OrderStatus();
				if(!receivedList.isEmpty()){
					wfStateReceived= mapSoStatus(receivedList);
					wfStates.add(wfStateReceived);
				}				

				List<SoStatusVO>acceptedList = statusMapVOs.get(OrderConstants.ACCEPTED_STATUS);
				OrderStatus wfStateAccepted = new OrderStatus();
				if(!acceptedList.isEmpty()){
					wfStateAccepted= mapSoStatus(acceptedList);	
					wfStates.add(wfStateAccepted);
				}

				List<SoStatusVO>activeList = statusMapVOs.get(OrderConstants.ACTIVE_STATUS);
				OrderStatus wfStateActive = new OrderStatus();
				if(!activeList.isEmpty()){
					wfStateActive= mapSoStatus(activeList);
					wfStates.add(wfStateActive);
				}

				List<SoStatusVO>problemList = statusMapVOs.get(OrderConstants.PROBLEM_STATUS);
				OrderStatus wfStateProblem = new OrderStatus();
				if(!problemList.isEmpty()){
					wfStateProblem= mapSoStatus(problemList);
					wfStates.add(wfStateProblem);
				}			
			}
			orderStatuses.setOrderStatus(wfStates);
			searchCriterias.setOrderStatuses(orderStatuses);
		}
		return searchCriterias;
	}
	/**
	 * method to sort provider name in alphabetical order
	 * @param providerList
	 */
	private void sortProviderList(List<ServiceProvider> providerList) {
		if(null !=providerList && providerList.size() > 0){
			Collections.sort(providerList, new Comparator<ServiceProvider>() {

				public int compare(ServiceProvider o1,ServiceProvider o2){
					int compareValue=0;
					if(StringUtils.isNotEmpty(o1.getDescr()) &&  StringUtils.isNotEmpty(o2.getDescr())){
						compareValue = o1.getDescr().compareTo(o2.getDescr());
					}
					return compareValue;
				}
			});
		} 

	}
	/**
	 * method to sort substatuses in alphabetical order
	 * @param subStatusList
	 */
	private void sortSubStatusList(List<SubStatus> subStatusList) {
		if(null !=subStatusList && subStatusList.size() > 0){
			Collections.sort(subStatusList, new Comparator<SubStatus>() {

				public int compare(SubStatus o1,SubStatus o2){
					int compareValue=0;
					if(StringUtils.isNotEmpty(o1.getDescr()) &&  StringUtils.isNotEmpty(o2.getDescr())){
						compareValue = o1.getDescr().compareTo(o2.getDescr());
					}
					return compareValue;
				}
			});
		} 

	}


	/**
	 * Method to map the substatuses corresponding to each main status in alphabetical order
	 * @param statuses
	 * @return
	 */
	private OrderStatus mapSoStatus(List<SoStatusVO>statuses) {

		SubStatuses subStatuses = new SubStatuses();		
		OrderStatus wfState = new OrderStatus();
		if(null != statuses){

			wfState.setId(statuses.get(0).getSoStatusId());
			wfState.setDescr(statuses.get(0).getSoStatus());
			List<SubStatus>subStatusList = new ArrayList<SubStatus>();

			for(SoStatusVO statusVO : statuses){

				SubStatus subStatus = new SubStatus();
				subStatus.setId(statusVO.getSoSubStatusId());
				subStatus.setDescr(statusVO.getSoSubStatus());
				subStatusList.add(subStatus);
			}
			sortSubStatusList(subStatusList);
			subStatuses.setSubStatus(subStatusList);

			wfState.setSubStatuses(subStatuses);
		}
		return wfState;
	}
	/**
	 * method to sort market name
	 * @param marketList
	 */
	private void sortMarketList(List<com.newco.marketplace.api.beans.searchCriteria.Market>marketList) {
		if(null !=marketList && marketList.size() > 0){
			Collections.sort(marketList, new Comparator<com.newco.marketplace.api.beans.searchCriteria.Market>() {

				public int compare(com.newco.marketplace.api.beans.searchCriteria.Market o1,com.newco.marketplace.api.beans.searchCriteria.Market o2){
					int compareValue=0;
					if(StringUtils.isNotEmpty(o1.getDescr()) &&  StringUtils.isNotEmpty(o2.getDescr())){
						compareValue = o1.getDescr().compareTo(o2.getDescr());
					}
					return compareValue;
				}
			});
		} 
	}

	private void mapSoPrice(GeneralSection generalSection, RetrieveSODetailsMobileVO detailsMobileVO){
		BigDecimal spendLimit = new BigDecimal(0);
		BigDecimal spendLimitParts = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);

		Double soPrice = 0.00;

		if(null != detailsMobileVO.getSoDetails().getOrderDetails().getSpendLimitLabour()){
			spendLimit = detailsMobileVO.getSoDetails().getOrderDetails().getSpendLimitLabour();
		}

		if(null !=  detailsMobileVO.getSoDetails().getOrderDetails().getSpendLimitParts()){
			spendLimitParts = detailsMobileVO.getSoDetails().getOrderDetails().getSpendLimitParts();
		}
		totalPrice = spendLimit.add(spendLimitParts);
		soPrice = MoneyUtil.getRoundedMoney(totalPrice.doubleValue());
		generalSection.setSoPrice(soPrice);

	}

	/**
	 * @Description:Method to sort the tabname 
	 * @param filterList
	 */
	protected void sortTabName(List<Tab> tabList) {
		if(null !=tabList && tabList.size() > 0){
			Collections.sort(tabList, new Comparator<Tab>() {

				public int compare(Tab o1,Tab o2){
					int compareValue=0;
					if(StringUtils.isNotEmpty(o1.getTabName()) &&  StringUtils.isNotEmpty(o2.getTabName())){
						compareValue = o1.getTabName().compareTo(o2.getTabName());
					}
					return compareValue;
				}
			});
		} 
	}
	/**
	 * @param response
	 * @param validateErrors
	 * @param soId
	 * @param reschedule
	 * @param reason
	 * @param comment
	 * @return
	 */
	public MobileSOSubmitRescheduleResponse mapOFResponse(OrderFulfillmentResponse response, String soId,SOSchedule reschedule,String reason,String comment){
		MobileSOSubmitRescheduleResponse returnVal = new MobileSOSubmitRescheduleResponse();
		RescheduleVO rescheduleVo = null;
		String code="";
		 if(null!= response){
			if(!response.getErrors().isEmpty()){
	            returnVal.setResults(Results.getError("You can not reschedule this order. Please contact your admin.", ResultsCode.FAILURE.getCode()));
			}else{
				Boolean autoAccept=false;
				String message="The reschedule request is pending approval from buyer.";
				code="0001";
				//when the serviceDate1 in request as well as serviceDate1 in so_hdr are same, this means that autoaccept is enabled
				com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder = ofHelper.getServiceOrder(soId);
			if(null!=serviceOrder.getSchedule()){
				LOGGER.info("serviceOrder.getSchedule().getServiceDate1()"+serviceOrder.getSchedule().getServiceDate1());
				LOGGER.info("reschedule.getServiceDate1()"+reschedule.getServiceDate1());
				LOGGER.info("serviceOrder.getSchedule().getServiceTimeStart()"+serviceOrder.getSchedule().getServiceTimeStart());
				LOGGER.info("reschedule.getServiceTimeStart()"+reschedule.getServiceTimeStart());
				if(null!=reschedule.getServiceDateTypeId()){
					if(reschedule.getServiceDateTypeId().equals(serviceOrder.getSchedule().getServiceDateTypeId())&&
            			serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
            			serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){
					LOGGER.info("Entering auto accept true check in api");

            		if((reschedule.getServiceDateTypeId().equals(SOScheduleType.DATERANGE)&& 
            				serviceOrder.getSchedule().getServiceDate2()!=null&&
            						serviceOrder.getSchedule().getServiceDate2().equals(reschedule.getServiceDate2())&&
            						serviceOrder.getSchedule().getServiceTimeEnd().equals(reschedule.getServiceTimeEnd()))||
            						reschedule.getServiceDateTypeId().equals(SOScheduleType.SINGLEDAY)){
    					LOGGER.info("Entering auto accept true check in api 1");
            			autoAccept=true;
            		}
            	}
			}else if(serviceOrder.getSchedule().getServiceDate1().equals(reschedule.getServiceDate1())&&
            			serviceOrder.getSchedule().getServiceTimeStart().equals(reschedule.getServiceTimeStart())){
					LOGGER.info("Entering auto accept true check in api 1");
            		autoAccept=true;
            	}
				LOGGER.info("Auto Accept:"+autoAccept);
				if(!autoAccept){
					LOGGER.info("Not auto accept in api");
					//autoaccept is off and reschedule request is not rejected
            		if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
            			message="The reschedule request is pending approval from buyer.";
            			code="0001";
            		}else{
            			//autoaccept is off and reschedule request is rejected
						returnVal.setResults(Results.getError("You can not reschedule this order. Please contact your admin.", ResultsCode.FAILURE.getCode()));
						message="";
						code="";
            		}
            	}else if(serviceOrder.getReschedule()!=null&&serviceOrder.getReschedule().getServiceDate1()!=null){
            		autoAccept=false;
            		message="The reschedule request is pending approval from buyer.";
            		code="0001";
            	}else{
            		//if auto accept is on and reschedule date and time coulumns in so_hdr is null. This means reschedule date is mapped to serviceDate1 column in so_hdr
            		message="Appointment has been updated successfully.";
            		code = "0000";
            		rescheduleVo = mapServiceOrderDetailsForReschedule(serviceOrder);
            		mobileGenericBO.sendBuyerOutBoundNotification( /*response,*/ soId,/*reschedule,*/reason,comment,rescheduleVo);
            	}
            	}
				if(StringUtils.isNotBlank(message) && StringUtils.isNotBlank(code)){
					returnVal.setResults(Results.getSuccess(code,message));
				}
			}
		}
		return returnVal;
	}

	/**
	 * @param serviceOrder
	 * @return
	 */
	private RescheduleVO mapServiceOrderDetailsForReschedule(com.servicelive.orderfulfillment.domain.ServiceOrder serviceOrder){
		RescheduleVO rescheduleVO = new RescheduleVO();
		
		rescheduleVO.setBuyerId(serviceOrder.getBuyerId().intValue());
		rescheduleVO.setSoId(serviceOrder.getSoId());
		rescheduleVO.setServiceLocnTimeZone(serviceOrder.getServiceLocationTimeZone());
		if(null!=serviceOrder.getSchedule()){
			rescheduleVO.setDBServiceDate1(serviceOrder.getSchedule().getServiceDate1());
			rescheduleVO.setDBServiceDate2(serviceOrder.getSchedule().getServiceDate2());
			rescheduleVO.setStartTime(serviceOrder.getSchedule().getServiceTimeStart());
			rescheduleVO.setEndTime(serviceOrder.getSchedule().getServiceTimeEnd());
		}
		
		return rescheduleVO;
	}
	
	
	/**
	 * @param securityContext
	 * @return
	 */
	public Identification createOFIdentityFromSecurityContext(SecurityContext securityContext) {
		int loginRoleId = securityContext.getRoles().getRoleId();
		switch (loginRoleId) {
		case OrderConstants.BUYER_ROLEID :
		case OrderConstants.SIMPLE_BUYER_ROLEID :
			return getIdentification(securityContext, EntityType.BUYER);

		case OrderConstants.PROVIDER_ROLEID :
			return getIdentification(securityContext, EntityType.PROVIDER);

		case OrderConstants.NEWCO_ADMIN_ROLEID :
			return getIdentification(securityContext, EntityType.SLADMIN);
		}
		return null;
	}
	/**
	 * @param securityContext
	 * @param entityType
	 * @return
	 */
	private Identification getIdentification(SecurityContext securityContext, EntityType entityType){
		Identification id = new Identification();
		id.setEntityType(entityType);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}
	
	/**
	 * Method to map the WithdrawCounterOfferRequest parameters to CounterOfferVO
	 * 
	 * @param request
	 * @param soId
	 * @param so
	 * @param providerResourceId
	 * @param firmId
	 * @param groupInd
	 * @param groupId
	 * @return CounterOfferVO
	 */
	public CounterOfferVO mapWithdrawCounterOfferRequest(APIRequestVO apiVO,WithdrawCounterOfferRequest request,ServiceOrder so) {
		CounterOfferVO offerVO = new CounterOfferVO();
		offerVO.setSoId(apiVO.getSOId());
		offerVO.setSo(so);
		offerVO.setProviderResourceId(apiVO.getProviderResourceId());
		offerVO.setResourceIds(request.getResourceIds());
		offerVO.setFirmId(apiVO.getProviderId());
		offerVO.setRoleId(apiVO.getRoleId());
		return offerVO;
	}
	
	
	/**
	 * Method creates mapper for Withdraw Counter Offer OF
	 * @param offerVO
	 * @return RoutedProvider
	 */
	public List<com.servicelive.orderfulfillment.domain.RoutedProvider> withdrawConditionalOfferMapper(CounterOfferVO offerVO) {
		LOGGER.info("Start -- MobileGenericBOImpl.withdrawConditionalOffer()");
		List<com.servicelive.orderfulfillment.domain.RoutedProvider> routedProvList = new ArrayList<com.servicelive.orderfulfillment.domain.RoutedProvider>();
		for(Integer resourceId : offerVO.getResourceIds().getResourceId()){
			//setting RoutedProvider object for each resource
			com.servicelive.orderfulfillment.domain.RoutedProvider routedProvider = new com.servicelive.orderfulfillment.domain.RoutedProvider();
			routedProvider.setProviderResourceId((long)resourceId);
			routedProvider.setVendorId(Integer.parseInt(offerVO.getFirmId()));
			routedProvider.setProviderResponse(ProviderResponseType.WITHDRAW_CONDITIONAL_OFFER);
			routedProvList.add(routedProvider);
		}
		LOGGER.info("End -- MobileGenericBOImpl.withdrawConditionalOffer()");
		return routedProvList;
	}
	
	public String setOFResults(OrderFulfillmentResponse response,ProcessResponse processResponse) throws BusinessServiceException{
		String returnVal = "";
		try{
			processResponse = OFMapper.mapProcessResponse(response);
			List<String> arrMsgList = processResponse.getMessages();
			for(String msg:arrMsgList){
				returnVal = processResponse.getCode() + msg + System.getProperty("line.separator","\n");
			}
		}catch (Exception e) {
			LOGGER.error("Exception in mapping of response to process response"+ e.getMessage());
			throw new BusinessServiceException(e.getCause());
		}
		return returnVal;

	}
	
	
	public OFHelper getOfHelper() {
		return ofHelper;
	}


	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}

	


	/**
	 * @param validateSecQuestAnsRequest 
	 * @return
	 * @throws com.newco.marketplace.exception.core.BusinessServiceException 
	 * @throws BusinessServiceException 
	 */
	public SecQuestAnsRequestVO mapSecQuestAnsRequest(ValidateSecQuestAnsRequest validateSecQuestAnsRequest) throws com.newco.marketplace.exception.core.BusinessServiceException {
		SecQuestAnsRequestVO questAnsRequestVO = null;
			if(null != validateSecQuestAnsRequest){
				questAnsRequestVO =  new SecQuestAnsRequestVO();
				questAnsRequestVO.setAdditionalVerification(false);
				questAnsRequestVO.setEmail(validateSecQuestAnsRequest.getEmail());
				questAnsRequestVO.setRequestFor(validateSecQuestAnsRequest.getRequestFor());
				if(null != validateSecQuestAnsRequest.getUserDetails()){
					com.newco.marketplace.api.mobile.beans.forgetUNamePwd.UserDetails userDetails = validateSecQuestAnsRequest.getUserDetails();
					questAnsRequestVO.setUserName(userDetails.getUserName());
					questAnsRequestVO.setUserId(userDetails.getUserId());
				}

				questAnsRequestVO = mobileGenericBO.getExistingUserProfileDetails(questAnsRequestVO);
				
				if(null != validateSecQuestAnsRequest.getVerificationDetails()){
					VerificationDetails verificationDetails = validateSecQuestAnsRequest.getVerificationDetails();
					if(null != verificationDetails.getSecurityQuestAnsDetails()){
						SecurityQuestAnsDetails questAnsDetails = verificationDetails.getSecurityQuestAnsDetails();
						questAnsRequestVO.setUserAnswer(questAnsDetails.getUserAnswer());
					}
					if(null != verificationDetails.getAdditionalVerificationDetails()){
						AdditionalVerificationDetails additionalVerificationDetails = verificationDetails.getAdditionalVerificationDetails();
						if(null != additionalVerificationDetails.getUserPhoneNumber()){
							questAnsRequestVO.setAdditionalVerification(true);
						}
						questAnsRequestVO.setUserPhoneNumber(additionalVerificationDetails.getUserPhoneNumber());
						questAnsRequestVO.setUserCompanyName(additionalVerificationDetails.getUserCompanyName());
						questAnsRequestVO.setUserZipCode(additionalVerificationDetails.getUserZipCode());
					}
				}
				
				
			}
		return questAnsRequestVO;
	}


	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}


	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}


	/**
	 * @param secQuestAnsRequestVO
	 * @return
	 */
	public ValidateSecQuestAnsResponse validateSecQuestAnsResponse(
			SecQuestAnsRequestVO secQuestAnsRequestVO) {
		
		ValidateSecQuestAnsResponse validateSecQuestAnsResponse = null;		
		Results results = null;
		if(null != secQuestAnsRequestVO && secQuestAnsRequestVO.isSuccess()){
			if(null != secQuestAnsRequestVO.getSuccess()){
				results = secQuestAnsRequestVO.getSuccess();
			}
			else{
				results = Results.getSuccess();
			}
			validateSecQuestAnsResponse = new ValidateSecQuestAnsResponse();
			validateSecQuestAnsResponse.setResults(results);
			
		}
		else{
			LOGGER.info("Error Occured While Processing Request");
			results = secQuestAnsRequestVO.getError();
			validateSecQuestAnsResponse = new ValidateSecQuestAnsResponse();
			validateSecQuestAnsResponse.setResults(results);
		}
		return validateSecQuestAnsResponse;
	}
	 
	
	/** SL-20452:Forget UName/Password Service1: method to map userId in case of additional verification needed
	 * @param response
	 * @param forgetUnamePwdVO
	 * @return
	 */
	public ForgetUnamePwdServiceResponse mapUserId(ForgetUnamePwdServiceResponse response, ForgetUnamePwdVO forgetUnamePwdVO){
		        List<UserDetail> userDetailsList = new ArrayList<UserDetail>();
				// setting requestFor object as userName for both  userName with email and userName with userId scenarios.
				if (forgetUnamePwdVO.getRequestFor().equalsIgnoreCase(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_EMAIL) || forgetUnamePwdVO.getRequestFor().equalsIgnoreCase(PublicMobileAPIConstant.REQUEST_FOR_USERNAME_WITH_USERID)){
					forgetUnamePwdVO.setRequestFor(PublicMobileAPIConstant.REQUEST_FOR_USERNAME);
				}
				
				UserDetail userDetail = new UserDetail();
				userDetail.setContactName(forgetUnamePwdVO.getUserFirstName());
				if(StringUtils.isNotBlank(forgetUnamePwdVO.getUserId()) && PublicMobileAPIConstant.REQUEST_FOR_USERNAME.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
					userDetail.setUserId(Integer.parseInt(forgetUnamePwdVO.getUserId()));
				}
				userDetail.setEmail(forgetUnamePwdVO.getEmail());
				userDetailsList.add(userDetail);
				UserDetails userDetails = new UserDetails(userDetailsList);
				response.setUserDetails(userDetails);
				if (PublicMobileAPIConstant.REQUEST_FOR_PASSWORD.equalsIgnoreCase(forgetUnamePwdVO.getRequestFor())){
					userDetail.setUserName(forgetUnamePwdVO.getUserName());
				}
				response.setRequestFor(forgetUnamePwdVO.getRequestFor());

		return response;
	}
	
	public  RetrieveSODetailsMobile mapProblemDetails(RetrieveSODetailsMobile serviceOrderResponse, ProblemResolutionSoVO pbResolutionVo){
		if(null != pbResolutionVo){
			ProblemDetails problemDetails = new ProblemDetails();
			ServiceOrderDetails serviceOrderDetails = serviceOrderResponse.getSoDetails();
			problemDetails.setProblemType(pbResolutionVo.getSubStatusDesc());
			problemDetails.setProblemComments(pbResolutionVo.getPbComment());
			serviceOrderDetails.setProblemDetails(problemDetails);
			serviceOrderResponse.setSoDetails(serviceOrderDetails);
		}
		return serviceOrderResponse;
		
	}
	
	/**@Description: Convert Date to yyyy-mm-dd hh:mm aa format
	 * @param DateString
	 * @return
	 * @throws ParseException 
	 */
	public static String formatDates(Date date){
		String DateTime =null;
		SimpleDateFormat outPut = new SimpleDateFormat(PublicMobileAPIConstant.DATE_TIME_FORMAT);
		try {
			DateTime = outPut.format(date);	
		} catch (Exception e) {
			LOGGER.error("Exception in formatting Date"+e);
		}
		return DateTime;
		
	}


	public MobileSOSubmitWarrantyHomeReasonCodeResponse mapSaveWarrantyHomeReasonsResponse(String message) {
		LOGGER.info("Entering MobileGenericMapper.mapSaveWarrantyHomeReasonsResponse ");
		MobileSOSubmitWarrantyHomeReasonCodeResponse returnVal = new MobileSOSubmitWarrantyHomeReasonCodeResponse();
		if(message.equalsIgnoreCase("success")){
			returnVal.setResults(Results.getSuccess("0000","Reason Code Submitted Successfully"));
			
		}else{
			returnVal.setResults(Results.getError(message, ResultsCode.FAILURE.getCode()));
		}
		LOGGER.info("Exiting MobileGenericMapper.mapSaveWarrantyHomeReasonsResponse");
		return returnVal;
		
	}
	
	public DispositionCodeList mapToDispositoinCodeListVo(List<DepositionCodeDTO> despositionCodeListDTO){
		List<DispositionCodeDetail> dipositionCodeListVO = new ArrayList<DispositionCodeDetail>();
		for(DepositionCodeDTO despositionCodeDTO :despositionCodeListDTO){
			DispositionCodeDetail dispositionCodeDetail = new DispositionCodeDetail();
			dispositionCodeDetail.setCodeId(despositionCodeDTO.getDepositionCode());
			dispositionCodeDetail.setDescription(despositionCodeDTO.getDescr());
			dispositionCodeDetail.setClientCharged(despositionCodeDTO.isClientChargedInd());
			dipositionCodeListVO.add(dispositionCodeDetail);
		}
		DispositionCodeList despositionCodeList = new DispositionCodeList();
		despositionCodeList.setDispositionCodeDetail(dipositionCodeListVO);
		return despositionCodeList;
	}
}

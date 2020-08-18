package com.newco.marketplace.api.mobile.services.v3_0;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.soDetails.vo.RescheduleDetailsVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.RetrieveSODetailsMobileVO;
import com.newco.marketplace.api.beans.so.soDetails.vo.SoDetailsVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.RetrieveSODetailsMobile;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v3_0.MobileGenericMapper;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.BidDetailsVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.dto.vo.ServiceOrderBidModel;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_0.MobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityFilter;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatus;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityType;
import com.servicelive.activitylog.domain.BidActivity;

/**
 * This class would act as a Servicer class for Provider Retrieve Service Order
 * 
 * @author Infosys
 * @version 3.0
 */
@APIResponseClass(SOGetMobileDetailsResponse.class)
public class SODetailsRetrieveService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(SODetailsRetrieveService.class);
	private IMobileGenericBO mobileGenericBO;
	private MobileGenericMapper mobileGenericMapper;
	private MobileGenericValidator mobileGenericValidator;	
	private IActivityLogHelper helper;
	
	public SODetailsRetrieveService() {
				super();
	}
 /**
  * This method will fetch the details of Service Orders 
  * @param apiVO
  * @return IAPIResponse
  */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		
		SOGetMobileDetailsResponse soGetResponse = new SOGetMobileDetailsResponse();
		//boolean isAuthorizedToViewSODetails = true;
		RetrieveSODetailsMobile serviceOrderResponse =null;
		RetrieveSODetailsMobileVO detailsMobileVO = null;
		SoDetailsVO detailsVO = new SoDetailsVO();
		Results results = new Results();
		RescheduleDetailsVO rescheduleDetailsVO = new RescheduleDetailsVO();
		List<ProviderResultVO> counteredResourceDetailsList = new ArrayList<ProviderResultVO>();
		List<EstimateVO> estimationlist = null;
		BidDetailsVO bidDetailsVO = null;
		
		try {
		detailsVO = mobileGenericMapper.mapSoDetails(apiVO);
		soGetResponse = mobileGenericValidator.validateSODetails(detailsVO);
		String zipCode = null;

			if (null !=soGetResponse.getResults()) {
				return soGetResponse;
			}
			else{		
				if(null != detailsVO){
					detailsMobileVO = mobileGenericBO.getServiceOrderDetails(detailsVO);
					if(null!=detailsMobileVO && null!=detailsMobileVO.getSoDetails() && null!= detailsMobileVO.getSoDetails().getServiceLocation()){
						zipCode = detailsMobileVO.getSoDetails().getServiceLocation().getZip();
					}
					//Code to fetch the reschedule details of an so
					rescheduleDetailsVO = mobileGenericBO.getSORescheduleDetails(apiVO.getSOId(),zipCode);
					//Code to fetch the counter offer resource details of an so
					if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails() 
							&& null != detailsMobileVO.getSoDetails().getOrderDetails() 
							&& StringUtils.isNotBlank(detailsMobileVO.getSoDetails().getOrderDetails().getSoStatus()) 
							&& detailsMobileVO.getSoDetails().getOrderDetails().getSoStatus().equals(PublicAPIConstant.SO_STATUS_ROUTED)){
						counteredResourceDetailsList = mobileGenericBO.getCounteredResourceDetailsList(detailsVO.getSoId(),detailsVO.getFirmId());
					}
				
				//Fetch the estimation details of the SO
				Integer vendorId=Integer.parseInt(apiVO.getProviderId());
				estimationlist = mobileGenericBO.getEstimationDetails(apiVO.getSOId(),vendorId); 
				
				//Fetch bid details of the SO
					if(null != detailsMobileVO && null != detailsMobileVO.getSoDetails() 
							&& null!=detailsMobileVO.getSoDetails().getOrderDetails() 
							&& StringUtils.isNotBlank(detailsMobileVO.getSoDetails().getOrderDetails().getPriceModel())
							&& Constants.PriceModel.ZERO_PRICE_BID.equals(detailsMobileVO.getSoDetails().getOrderDetails().getPriceModel()))
					{
						bidDetailsVO = getBidDetails(apiVO.getSOId(),apiVO.getProviderId(),apiVO.getProviderResourceId());
					}
				}	
				serviceOrderResponse = mobileGenericMapper.mapGetSODetailsResponse(detailsMobileVO,rescheduleDetailsVO,counteredResourceDetailsList, estimationlist,bidDetailsVO);
				
				//R16_1 fetch Problem Details for SO with status 170
				//Problem details will be fetched only for orders having substatus not null
				//Problem type - Order substatus description
				//Problem description - Report Problem comments
				if(null != serviceOrderResponse && null != serviceOrderResponse.getSoDetails() 
						&& null != serviceOrderResponse.getSoDetails().getOrderDetails() 
						&& MPConstants.SO_STATUS_PROBLEM.equals( serviceOrderResponse.getSoDetails().getOrderDetails().getSoStatus())){
					ProblemResolutionSoVO pbResolutionVo = mobileGenericBO.getProblemDesc(detailsVO.getSoId());
					serviceOrderResponse = mobileGenericMapper.mapProblemDetails(serviceOrderResponse,pbResolutionVo);
				}
				
				// R16_1 changes end here
				
				// Returning error if Service Order is not found.
				if (null == serviceOrderResponse) {
					results = Results.getError(
							ResultsCode.INVALID_SO_ID
									.getMessage(),ResultsCode.INVALID_SO_ID.getCode());
				} 
				else {
					results = Results
							.getSuccess(ResultsCode.RETRIEVE_RESULT_CODE_SUCCESS
									.getMessage());
				}	
				soGetResponse.setServiceOrder(serviceOrderResponse);
			} 
						

		}catch(BusinessServiceException ex){
			ex.printStackTrace();
			LOGGER.error("Exception in execute method of SODetailsRetrieveService-->"+ ex.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Exception in execute method of SODetailsRetrieveService-->"+ e.getMessage());
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		if(null != results){
			soGetResponse.setResults(results);
		}
		return soGetResponse;
	}	
	
	/**Fetch the bid details of an SO
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public BidDetailsVO getBidDetails(String soId,String firmId,Integer routedResourceId) throws BusinessServiceException{
		BidDetailsVO allBids = new BidDetailsVO();
		try{
			List<ActivityLog> allBidsForOrder = retrieveAllBidsForOrder(soId);
			Map<Long, String> providerNamesMap = mobileGenericBO.retrieveRoutedProvidersNameMap(soId);
			Boolean sealedInd=mobileGenericBO.fetchSealedBidIndicator(soId);
			if(null!=sealedInd)
			{
				allBids.setSealedInd(sealedInd);
			}
			
			ServiceOrderBidModel currentBidFromCurrentProvider = null;
			List<ServiceOrderBidModel> pastBidsFromCurrentProvider = new ArrayList<ServiceOrderBidModel>();
			List<ServiceOrderBidModel> bidsFromOtherProvidersInMyCompany = new ArrayList<ServiceOrderBidModel>();
			List<ServiceOrderBidModel> bidsFromProvidersInOtherCompanies = new ArrayList<ServiceOrderBidModel>();
			double minimumBid = Double.MAX_VALUE;
			double maximumBid = Double.MIN_VALUE;
			int numberOfCurrentBids = 0;
			Integer companyId = Integer.parseInt(firmId);

			for (ActivityLog activity : allBidsForOrder) {
				if (activity instanceof BidActivity) {
					BidActivity bid = (BidActivity) activity;
					ServiceOrderBidModel bidModel = this.createBidModelFromActivityLog(bid);
					if (bidIsFromCurrentProvidersCompany(companyId, bid)) {
						if (bidIsFromCurrentProvider(routedResourceId, bid)) {
							// For the current provider, show the current bid, even if it has expired
							if (bidIsCurrentEvenIfExpired(bid)) {
								// Find if this bid is more recent that the current bid
								if (currentBidFromCurrentProvider == null || bidModel.getDateOfBid().after(currentBidFromCurrentProvider.getDateOfBid())) {
									if (currentBidFromCurrentProvider != null) {
										pastBidsFromCurrentProvider.add(currentBidFromCurrentProvider);
									}
									currentBidFromCurrentProvider = bidModel;
								} else {
									pastBidsFromCurrentProvider.add(bidModel);
								}
							}
							else {
								pastBidsFromCurrentProvider.add(bidModel);
							}
						}
						else {
							if (bidIsCurrent(bid)) {
								bidsFromOtherProvidersInMyCompany.add(bidModel);
							}
						}
						String providerName = providerNamesMap.get(bid.getProviderResourceId());
						if (providerName != null) {
							bidModel.setBidder(providerName);
						}
					}
					else {
						if (bidIsCurrent(bid)) {
							bidsFromProvidersInOtherCompanies.add(bidModel);
						}
						bidModel.setBidder(Constants.PROVIDER_BID);
					}
					
					if (bidIsCurrent(bid)) {
						numberOfCurrentBids++;
						minimumBid = Math.min(bid.getBidTotal(), minimumBid);
						maximumBid = Math.max(bid.getBidTotal(), maximumBid);
					}				
				}
			}
			
			if (numberOfCurrentBids == 0) {
				minimumBid = 0;
				maximumBid = 0;
			}
			
			allBids.setNumberOfBids(numberOfCurrentBids);		
			allBids.setHighBid(maximumBid);
			allBids.setLowBid(minimumBid);
			allBids.setMyCurrentBid(currentBidFromCurrentProvider);
			allBids.setMyPreviousBids(pastBidsFromCurrentProvider);
			allBids.setOtherBidsFromMyCompany(bidsFromOtherProvidersInMyCompany);
			allBids.setAllOtherBids(bidsFromProvidersInOtherCompanies);
			if (!pastBidsFromCurrentProvider.isEmpty()) {
				allBids.setMyPreviousBid(pastBidsFromCurrentProvider.get(Constants.INT_ZERO));
			}
		}
		catch(BusinessServiceException e){
			throw new BusinessServiceException(e);
		}
		return allBids;
	}
	
	
	private List<ActivityLog> retrieveAllBidsForOrder(String orderId) {
		ActivityFilter activityFilter = new ActivityFilter();
		activityFilter.addOrderId(orderId);
		activityFilter.addActivityType(ActivityType.Bid);
		List<ActivityLog> orderBids = this.helper.findActivities(activityFilter);
		return orderBids;

	}
	
	
	private ServiceOrderBidModel createBidModelFromActivityLog(ActivityLog log)
	{
		ServiceOrderBidModel  bidModel = new ServiceOrderBidModel();

		BidActivity b = (BidActivity)log;
		bidModel.setBidder(b.getCreatedBy());
		bidModel.setDateOfBid(b.getCreatedOn());
		if(b.getExpirationDate() != null)
		{
			DateFormat df = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
			bidModel.setBidExpirationDatepicker(df.format(b.getExpirationDate()));
						
			if (b.getExpirationDate().before(Calendar.getInstance().getTime())) {
				bidModel.setBidExpired(true);
			} else {
				bidModel.setBidExpired(false);
			}
		}
		bidModel.setCalculatedHourlyRate(b.getLaborRate());
		bidModel.setTotal(b.getBidTotal());
		bidModel.setComment(b.getComment());
		bidModel.setPartsMaterials(b.getMaterialsCost());
		Double totalLabor = 0.0d;
		if (b.getMaximumLaborCost() != null) {
			if(b.getLaborRate() != null && b.getLaborEffortInHours() != null) {
				totalLabor = b.getLaborRate() * b.getLaborEffortInHours();
				bidModel.setLaborRateIsHourly(true);				
			}else{
				totalLabor = b.getMaximumLaborCost();
				bidModel.setLaborRateIsHourly(false);
			}			
		}
		bidModel.setTotalLabor(totalLabor);
		bidModel.setTotalHours(b.getLaborEffortInHours());
		
		// Service Request Date
		setBidModelDateChanges(bidModel, b.getServiceDateFrom(), b.getServiceDateTill());		
		
		bidModel.setBidderResourceId(b.getProviderResourceId().intValue());
		bidModel.setBidderVendorId(b.getProviderId().intValue());
		return bidModel;
	}
	private boolean bidIsFromCurrentProvider(Integer routedResourceId,
			BidActivity bid) {
		Long providerResourceId = bid.getProviderResourceId();
		boolean bidIsFromCurrentProvider = providerResourceId != null && providerResourceId.intValue() == routedResourceId.intValue();
		return bidIsFromCurrentProvider;
	}

	private boolean bidIsFromCurrentProvidersCompany(Integer companyId,
			BidActivity bid) {
		Long providerId = bid.getProviderId();
		boolean bidIsFromCurrentProvidersCompany = providerId != null && providerId.intValue() == companyId.intValue();
		return bidIsFromCurrentProvidersCompany;
	}
	
	private boolean bidIsCurrentEvenIfExpired(BidActivity bid) {
		ActivityStatus currentStatus = bid.getCurrentStatus();
		boolean bidIsCurrent = false;
		if (currentStatus != null) {
			ActivityStatusType currentStatusType = currentStatus.getStatus();
			bidIsCurrent = ActivityStatusType.ENABLED.equals(currentStatusType) || ActivityStatusType.EXPIRED.equals(currentStatusType);
		}
		return bidIsCurrent;
	}
	
	private boolean bidIsCurrent(BidActivity bid) {
		ActivityStatus currentStatus = bid.getCurrentStatus();
		boolean bidIsCurrent = false;
		if (currentStatus != null) {
			ActivityStatusType currentStatusType = currentStatus.getStatus();
			bidIsCurrent = ActivityStatusType.ENABLED.equals(currentStatusType);
		}
		return bidIsCurrent;
	}

	private void setBidModelDateChanges(ServiceOrderBidModel bid, Date startDate, Date endDate)
	{
		
		if(bid == null)
			return;
		
		// Service Request Date
		if(startDate != null && endDate != null)
		{
			DateFormat formatter = new SimpleDateFormat( Constants.DATE_FORMAT );        
	        String fromDate = formatter.format(startDate);				
	        String toDate = formatter.format(endDate);
	        
	        // Different dates then set normally
	        if(!fromDate.equalsIgnoreCase(toDate))
	        {
	        	bid.setNewDateByRangeFrom(fromDate);		        
	        	bid.setNewDateByRangeTo(toDate);
	        }
	        // Set 'specificDate' as 'yyyy-MM-dd'
	        // Set 'fromDate' as 'hh:mm aa'
	        // Set 'toDate' as 'hh:mm aa'
	        else
	        {
	        	DateFormat fmt = new SimpleDateFormat( Constants.DATE_FORMAT );
				DateFormat fmt1 = new SimpleDateFormat( Constants.TIME_FORMAT );        
				DateFormat fmt2 = new SimpleDateFormat( Constants.TIME_FORMAT  );        

				fromDate = fmt1.format(startDate);
				toDate = fmt2.format(endDate);
				bid.setNewDateBySpecificDate(fmt.format(startDate));
				bid.setNewDateByRangeFrom(fromDate);
				bid.setNewDateByRangeTo(toDate);
	        }
		}
		else if(startDate != null)
		{
			DateFormat formatter = new SimpleDateFormat( Constants.DATE_FORMAT );        
	        String newDate = formatter.format(startDate);				
			bid.setNewDateBySpecificDate(newDate);
		}
		
		
	}

	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}

	public MobileGenericMapper getMobileGenericMapper() {
		return mobileGenericMapper;
	}

	public void setMobileGenericMapper(MobileGenericMapper mobileGenericMapper) {
		this.mobileGenericMapper = mobileGenericMapper;
	}
	public MobileGenericValidator getMobileGenericValidator() {
		return mobileGenericValidator;
	}
	public void setMobileGenericValidator(
			MobileGenericValidator mobileGenericValidator) {
		this.mobileGenericValidator = mobileGenericValidator;
	}

	public IActivityLogHelper getHelper()
	{
		return helper;
	}

	public void setHelper(IActivityLogHelper helper)
	{
		this.helper = helper;
	}
	

}
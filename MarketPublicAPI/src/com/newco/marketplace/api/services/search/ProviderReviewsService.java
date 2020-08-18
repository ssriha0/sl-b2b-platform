package com.newco.marketplace.api.services.search;


import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.search.providerProfile.Review;
import com.newco.marketplace.api.beans.search.providerProfile.Reviews;
import com.newco.marketplace.api.beans.search.providerreviews.ProviderReviewsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.DataTypes;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.persistence.daoImpl.survey.SurveyDAOImpl;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.SimpleCache;

/**
 * This class acts as a service class for GetProviderReviews API.
 * 
 * API     : /serviceproviders/{providerId}/reviews
 * APIType : Get
 * Request Processor  : {@link SearchRequestProcessor#getProviderReviews}
 * Spring Bean Name   : providerReviewsService
 * Spring Context XML : apiApplicationContext.xml
 * 
 * @author Shekhar Nirkhe
 * @since 10/10/2009
 *
 */

@Namespace("http://www.servicelive.com/namespaces/searchprovider")
@APIResponseClass(ProviderReviewsResponse.class)
public class ProviderReviewsService extends BaseService {
	
	private String DEFAULT_DATE_FORMAT = "MMddyyyy";
	private IProviderInfoPagesBO backendBO;
	
	private Logger logger = Logger.getLogger(ProviderReviewsService.class);
	/**
	 * This method is for adding funds to  wallet.
	 * 
	 * @param fromDate String,toDate String
	 * @return String
	 */
	
	public ProviderReviewsService () {
		super();		
		addMoreClass(Reviews.class);
		addMoreClass(Review.class);
		addRequiredURLParam(APIRequestVO.PROVIDER_RESOURCE_ID, DataTypes.INTEGER);
		addOptionalGetParam("beginDate", DataTypes.DATE);
		addOptionalGetParam("endDate", DataTypes.DATE);
		addOptionalGetParam("sort", DataTypes.STRING);
		addOptionalGetParam("order", DataTypes.STRING);
		addOptionalGetParam("pageSize", DataTypes.INTEGER);
		addOptionalGetParam("pageNum", DataTypes.INTEGER);
		addOptionalGetParam("maxRating", DataTypes.DOUBLE);
		addOptionalGetParam("minRating", DataTypes.DOUBLE);
	}	
	
	
	/**
	 * Implement your logic here.
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {		
		logger.info("Entering execute method");	
		ProviderReviewsResponse response =
			new ProviderReviewsResponse();
		Map<String,String> requestMap = apiVO.getRequestFromGetDelete();
		String startDate = (String)requestMap.get(PublicAPIConstant.ProviderReviews.BEGIN_DATE.toLowerCase()); 
		String endDate  = (String)requestMap.get(PublicAPIConstant.ProviderReviews.END_DATE.toLowerCase());
		Double maxRating=null;
		Double minRating=null;
		String max_Rating=requestMap.get(PublicAPIConstant.ProviderReviews.MAXRATING.toLowerCase());
		String min_Rating=requestMap.get(PublicAPIConstant.ProviderReviews.MINRATING.toLowerCase());
		if(null!=max_Rating)
		maxRating = Double.parseDouble(max_Rating); 
		if(null!=min_Rating)
		minRating  = Double.parseDouble(min_Rating);
		if (endDate == null) {
			endDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),DEFAULT_DATE_FORMAT);//   todaysDate()
		}
		
		if (startDate == null) {
			startDate = getCalculatedHistoryDate(endDate);
		}
				
		java.util.Date sDate = DateUtils.getDateFromString(startDate, DEFAULT_DATE_FORMAT);
		java.util.Date eDate = DateUtils.getDateFromString(endDate, DEFAULT_DATE_FORMAT);
		
		//increment day by 1 to get full day coverage
		Calendar c = Calendar.getInstance();
		c.setTime(eDate);			
		c.add(Calendar.DATE, 1);
		eDate = c.getTime();
		
		if (sDate.getTime() > eDate.getTime()) {
			response.setResults(Results.getError("Invalid Date Range", 
					ResultsCode.INVALID_OR_MISSING_PARAM.getCode()));
			response.getReviews().setReviewsTotal(0);
			return response;
		}
		
		int pageSize = PublicAPIConstant.ProviderReviews.DEFAULT_PAGE_SIZE;
		int pageNumber = 1;
		
		String tempStr =  requestMap.get(PublicAPIConstant.ProviderReviews.PAGE_SIZE.toLowerCase());
		if (tempStr != null)
			pageSize = Integer.parseInt(tempStr);
		
		tempStr =  requestMap.get(PublicAPIConstant.ProviderReviews.PAGE_NUM.toLowerCase());
		if (tempStr != null)
			pageNumber = Integer.parseInt(tempStr);
		
		int beginIndex = (pageNumber - 1) * pageSize;
		int endIndex = beginIndex + pageSize; 
		
		// SQL is using between so endIndex has to be decreased by -1
		if (endIndex > 0) {
			endIndex --;
		}
		
		String sortColumn = requestMap.get(PublicAPIConstant.ProviderReviews.SORT.toLowerCase());
		String order = requestMap.get(PublicAPIConstant.ProviderReviews.ORDER.toLowerCase());
		
		if (StringUtils.isBlank(sortColumn)) {
			sortColumn = "date";
		}
		
		if (StringUtils.isBlank(order)) {
			order = "desc";
		}
		
		Integer providerResourceId = Integer.parseInt((String)apiVO.getProperty(APIRequestVO.PROVIDER_RESOURCE_ID));
		
		List<CustomerFeedbackVO> cList;
		try {
			Integer count  =  getReviewsCount(providerResourceId);
			response.getReviews().setReviewsTotal(count);
			
			cList = backendBO.getCustomerFeedbacks(providerResourceId, beginIndex, 
					endIndex, sortColumn, order, sDate, eDate,maxRating,minRating);
			
			for (CustomerFeedbackVO vo: cList) {
				com.newco.marketplace.search.types.CustomerFeedback vo1 = 
					new com.newco.marketplace.search.types.CustomerFeedback(vo); 			
			    Review review =  new Review(vo1);
			    response.getReviews().addReview(review);
			}
			if (cList.size() == 0) {
				String msg = "No reviews found";
				response.setResults(Results.getSuccess(msg));
			} else {
				response.setResults(Results.getSuccess());
			}
			
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {			
			e.printStackTrace();
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}		
		
		logger.info("Exiting execute method");	
		return response;
	}

	/**
	 * This method goes to backend to read review count for a given provider. 
	 * It usage {@link SurveyDAOImpl#getVendorResourceFeedbackCount} DAO to read counts 
	 * from supplier.survey_response_hdr table. It also make use of read only cache with
	 * 10 minutes refresh time.
	 * 
	 * @param providerId
	 * @return
	 * @throws com.newco.marketplace.exception.BusinessServiceException
	 */
	private Integer getReviewsCount(Integer providerId) 
		throws com.newco.marketplace.exception.BusinessServiceException {
		final String key = "/API/reviewCount/providerId/" + providerId;
		Integer count = (Integer)SimpleCache.getInstance().get(key);
		if (count == null) {
			count  =  backendBO.getCustomerFeedbacksCount(providerId);
			//FIXME: Commenting the following code since security context is cached incorrectly 
			//SimpleCache.getInstance().put(key, count, SimpleCache.TEN_MINUTES);
		}		
		return count;
	}
	
	public IProviderInfoPagesBO getBackendBO() {
		return backendBO;
	}


	public void setBackendBO(IProviderInfoPagesBO backendBO) {
		this.backendBO = backendBO;
	}
	
	/**
	 * This method is to get the date for a given date with year/month/days backwards
	 * 
	 * @param dateStr String
	 * @return String
	 */
	private String getCalculatedHistoryDate(String givenDate){
		java.util.Date date = DateUtils.getDateFromString(givenDate, DEFAULT_DATE_FORMAT);
		java.util.Date dateRes = DateUtils.calcDateBasedOnDateNInterval(date,
																		(new Integer(PublicAPIConstant.ProviderReviews.HISTORY_PERIOD_YEAR)),
																		PublicAPIConstant.ProviderReviews.HISTORY_PERIOD_YEAR_STRING);
       String strDate =  DateUtils.getFormatedDate(dateRes, DEFAULT_DATE_FORMAT);	
       return strDate;
	}	
}




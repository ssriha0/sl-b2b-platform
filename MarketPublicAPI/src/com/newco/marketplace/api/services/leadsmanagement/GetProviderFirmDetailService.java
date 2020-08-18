package com.newco.marketplace.api.services.leadsmanagement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.leadsmanagement.LeadManagementMapper;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Credentials;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmCompleteDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FullFirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetProviderFirmDetailRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.GetProviderFirmDetailResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Insurances;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Policy;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.PostInsurance;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.PostReview;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Reviewes;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ScheduleAppointmentRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Service;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Services;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.SubCredentials;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.SubService;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmCredentialVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ServiceRootVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ServiceVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.InsurancePolicyVO;




public class GetProviderFirmDetailService extends BaseService{
	private static Logger logger = Logger.getLogger(PostLeadService.class);
	private ILeadProcessingBO leadProcessingBO;
	private LeadManagementMapper leadManagementMapper;

	
	public GetProviderFirmDetailService()
	{
		super(PublicAPIConstant.GET_PROVIDER_FIRM_DETAIL_REQUEST_XSD,
				PublicAPIConstant.GET_PROVIDER_FIRM_DETAIL_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.GET_PROVIDER_FIRM_DETAIL_RESPONSE_SCHEMALOCATION,
				GetProviderFirmDetailRequest.class, GetProviderFirmDetailResponse.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		GetProviderFirmDetailRequest getProviderFirmDetailRequest = (GetProviderFirmDetailRequest) apiVO
				.getRequestFromPostPut();
		GetProviderFirmDetailResponse response = new GetProviderFirmDetailResponse();
		
				List<String> firmIdList=new ArrayList<String>();
				if(null !=getProviderFirmDetailRequest && null !=getProviderFirmDetailRequest.getFirmIds().getFirmId()){
					for(String firmId:getProviderFirmDetailRequest.getFirmIds().getFirmId()){
						firmIdList.add(firmId);
					}
				}
				List<Integer> firmList =new ArrayList<Integer>();
				for(String f:firmIdList){
					firmList.add(Integer.valueOf(f));
				}
		 Map companyProfileVOMap=new HashMap(); 
		 try
		 {
		 companyProfileVOMap =leadProcessingBO.getCompanyProfileDetails(firmList);
		 }
		 catch (BusinessServiceException e)
		 {
				logger.info("Exception in getting  companyProfileVOMap "+e.getMessage());
				e.printStackTrace();
		}
		 Map firmDeatilsMap=new HashMap();
		 try
		 {
		
			 firmDeatilsMap =leadProcessingBO.getProviderFirmDetails(firmList);
		 }
			
		 catch (BusinessServiceException e) 
		 {
					
			 logger.info("Exception in getting  firmDetailsMap "+e.getMessage());
				e.printStackTrace();
			}
		
		Map firmCredentialVOs=null;
		try{
		    firmCredentialVOs=leadProcessingBO.getFirmCredentials(firmIdList);
		  }
	    catch (Exception e) {
            logger.info("Exception in getting firm Credentials: "+e.getMessage());
		  }
		Map firmServiceVOMap=null;
		try{
			firmServiceVOMap=leadProcessingBO.getFirmServices(firmIdList);
		  }
	    catch (Exception e) {
            logger.info("Exception in getting firm services: "+e.getMessage());
		  }
		
		List<FirmCredentialVO> fCredentialVOs=new ArrayList<FirmCredentialVO>();
		List<FirmServiceVO> firmServiceVOList =new ArrayList<FirmServiceVO>();
		FirmServiceVO  firmServiceVO=new FirmServiceVO();
		FullFirmDetails firmDetails=new FullFirmDetails();
		List<FirmCompleteDetails> firmCompleteDetails = new ArrayList<FirmCompleteDetails>();
       	for(Integer firmID:firmList)
		{
       		//Initialize root class
       		FirmCompleteDetails  firmDetail=new FirmCompleteDetails();
    		
       		//Initializing all the lists
       		CompanyProfileVO companyProfileVO=null;
       		List<InsurancePolicyVO> insurancePolicyVOList=null;
       		if(null != companyProfileVOMap){
       		   companyProfileVO=(CompanyProfileVO) companyProfileVOMap.get(firmID);
       		   if(null!= companyProfileVO && null != companyProfileVO.getInsurancePolicies()){
       		   insurancePolicyVOList=companyProfileVO.getInsurancePolicies();
              	}
       		}
       		
       		FirmDetailsVO firmDetailsVO=null;
       		List<ReviewVO> reviewVOList=null;
       		if(null !=firmDeatilsMap){
    		 firmDetailsVO = (FirmDetailsVO) firmDeatilsMap.get(firmID);
    		 if(null !=firmDetailsVO && null !=firmDetailsVO.getReviewVO()){
    		 reviewVOList=firmDetailsVO.getReviewVO();
       		}
       		}
    		
    		List<PostReview> postReviewList =new  ArrayList<PostReview>();
       		List<PostInsurance> postInsuranceList = new ArrayList<PostInsurance>();
    		List<SubCredentials>subCredentialList=new ArrayList<SubCredentials>();
    		
    
    		//setting Firm Details
    		if(null != firmID && null!= companyProfileVO )
    		{
    		
       		firmDetail.setFirmId(firmID.toString());
    		firmDetail.setFirmName(companyProfileVO.getBusinessName());
    		firmDetail.setFirmOverview(companyProfileVO.getBusinessDesc());
    		String firmOwner=companyProfileVO.getFirstName()+ " " + companyProfileVO.getLastName();
    		firmDetail.setFirmOwner(firmOwner);
    		//SL-20917 : setting number of employees 
			if (null!=companyProfileVO.getNoOfEmployees()) {
				firmDetail.setNumberEmployees(companyProfileVO.getNoOfEmployees());	
			}
            String yearsOfService=companyProfileVO.getYearsInBusiness();
	        
	        if(StringUtils.isNotBlank(yearsOfService))
	        {
	        	firmDetail.setYearsOfService(Double.parseDouble(yearsOfService));
	        }
    		}
    		if(null != firmID && null!= firmDetailsVO )
    		{
            if(null != firmDetailsVO.getRating()){
       			firmDetail.setFirmRating(firmDetailsVO.getRating());
			}
			
    	   		
    		//setting Review Details
            if( null != reviewVOList && !reviewVOList.isEmpty())
            {
   		     for(ReviewVO reVo:reviewVOList)
   		     {
						PostReview postReview = new PostReview();
						postReview.setAuthor(reVo.getReviewerName());
						postReview.setComment(reVo.getReviewComment());
						String reviewRatingString = reVo.getReviewRating();
						if (StringUtils.isNotBlank(reviewRatingString)) {
							postReview.setRating(Double
									.parseDouble(reviewRatingString));
						}
						DateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String dateString = reVo.getReviewdDate();
						Date date = null;
						try {
							if (StringUtils.isNotBlank(dateString)) {
								date = formatter.parse(dateString);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						postReview.setDate(date);

						postReviewList.add(postReview);
						Reviewes reviews = new Reviewes();
						reviews.setReview(postReviewList);
						firmDetail.setReviews(reviews);
					}
           }
    		}   
    		Policy policy=new Policy();
    		String freeEstimates=companyProfileVO.getFreeEstimate();
    		if(StringUtils.isNotBlank(freeEstimates)){
    		policy.setFreeEstimates(Boolean.valueOf(freeEstimates));
    		}
    		String warrantyLabormonths=companyProfileVO.getWarrPeriodLabor();
    		String warrantyPartsMonths=companyProfileVO.getWarrPeriodParts();
    		//Getting the warranty labor as an integer
			if (StringUtils.isNotBlank(warrantyLabormonths)) {
				policy.setWarrantyLaborMonths(PublicAPIConstant
						.WARRANTY_PERIOD().get(warrantyLabormonths));
			}
			//Getting the warranty parts as an integer
			if (StringUtils.isNotBlank(warrantyPartsMonths)) {
				policy.setWarrantyPartsMonths(PublicAPIConstant
						.WARRANTY_PERIOD().get(warrantyPartsMonths));
			}
       		
       		
       		firmDetail.setPolicy(policy);
       		
       		//setting insurance Details
       	if(null != insurancePolicyVOList && !insurancePolicyVOList.isEmpty())
       	{
            for(InsurancePolicyVO insurancePolVO:insurancePolicyVOList)
       		{
       			PostInsurance postInsurance=new PostInsurance();
       			postInsurance.setName(insurancePolVO.getName());
       			postInsurance.setVerified(insurancePolVO.getIsVerified());
       			if(null != insurancePolVO.getPolicyamount()){
    			postInsurance.setAmount(insurancePolVO.getPolicyamount().intValue());
    			}
    			if(null != insurancePolVO.getIsVerified() && insurancePolVO.getIsVerified() == true)
    			{
    			postInsurance.setVerificationDate(insurancePolVO.getInsVerifiedDate());
    			}
    			postInsuranceList.add(postInsurance);
    			
    		}
       		Insurances insurances=new Insurances();
       		insurances.setPostInsurance(postInsuranceList);
       		firmDetail.setInsurances(insurances);
       	}
       		//setting Credentials Detail
            fCredentialVOs=null;
       	
          	fCredentialVOs = (List<FirmCredentialVO>) firmCredentialVOs.get(firmID
					.toString());
			if (null !=fCredentialVOs && !fCredentialVOs.isEmpty()) {
				
				Credentials credentials = new Credentials();
				for (FirmCredentialVO vo :fCredentialVOs) {
					// the credentials contains two as "Company".
					SubCredentials subCredentials = new SubCredentials();
					subCredentials.setStatus(vo.getStatus());
					subCredentials.setCredentialType("COMPANY");
					subCredentials.setCategory(vo.getCategory());
					subCredentials.setName(vo.getName());
					subCredentials.setSource(vo.getSource());
					subCredentials.setType(vo.getType());
					subCredentialList.add(subCredentials);

				}
				credentials.setSubCredentials(subCredentialList);
				firmDetail.setCredentials(credentials);
			}
       		//setting Services Details
			firmServiceVO=null;
			if (null != firmServiceVOMap) {
				firmServiceVOList = (List<FirmServiceVO>) firmServiceVOMap
						.get(firmID.toString());
				if (null != firmServiceVOList && 0 < firmServiceVOList.size()) {
					Services services = new Services();
					List<SubService> subServiceList = new ArrayList<SubService>();
					for (FirmServiceVO firmService : firmServiceVOList) {
						SubService subService = new SubService();
						subService.setProjectType(firmService.getProject());
						subService.setServiceCategory(firmService
								.getRootCategory());
						subService.setServiceScope(firmService
								.getServiceScope());
						subServiceList.add(subService);
					}
					services.setSubService(subServiceList);
					firmDetail.setServices(services);
				}
			}
       		firmCompleteDetails.add(firmDetail);
		}
    	firmDetails.setFirmCompleteDetails(firmCompleteDetails);
    	response.setResults(Results.getSuccess());
    	response.setFirmDetailsList(firmDetails);
		return response;
    	}

	 public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public LeadManagementMapper getLeadManagementMapper() {
		return leadManagementMapper;
	}

	public void setLeadManagementMapper(LeadManagementMapper leadManagementMapper) {
		this.leadManagementMapper = leadManagementMapper;
	}

	private String getYearsInBusiness(Date busStartDate)
		{
			long numMilBusStart;
			long numMilToday;
			long dateDiff = 0;
			float numYears;
			String numYearsStr;
			Date todayDate = new Date();

			numMilBusStart = busStartDate.getTime();
			numMilToday = todayDate.getTime();

			dateDiff = numMilToday - numMilBusStart;

			numYears = (float) dateDiff / 1000 / 60 / 60 / 24 / 365;
			numYearsStr = String.valueOf(numYears);

			return numYearsStr;
		}
	
}
	



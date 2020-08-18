package com.newco.marketplace.api.services.leadsdetailmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.annotation.Namespace;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.leadsmanagement.LeadManagementValidator;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Address;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CustomerDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadDetailsResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadFirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadFirmDetailsList;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Location;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Locations;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadCustomerDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadVO;
import com.newco.marketplace.utils.UIUtils;

@Namespace("http://www.servicelive.com/namespaces/leadFirmDetails")
@APIResponseClass(LeadDetailsResponse.class)
public class LeadFirmDetailService extends BaseService{
	

	private static Logger logger = Logger.getLogger(LeadFirmDetailService.class);
    private static final String XML_DATE_FORMAT="yyyy-MM-dd";
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	
	public LeadFirmDetailService(){
	      super();
	}
	
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LeadDetailsResponse response = new LeadDetailsResponse();
		String leadId = apiVO.getleadId();
		Integer buyerId = apiVO.getBuyerIdInteger();
		leadId = leadManagementValidator.validateleadId(leadId);
		// Check if the lead id is blank
		if (StringUtils.isBlank(leadId)) {
			return createErrorResponse(ResultsCode.LEAD_NOT_FOUND.getMessage(),
					ResultsCode.LEAD_NOT_FOUND.getCode());
		}
		//check if the buyer id is null
		if (null == buyerId) {
			return createErrorResponse(
					ResultsCode.INVALID_BUYER_ID.getMessage(),
					ResultsCode.INVALID_BUYER_ID.getCode());
		}
		//check if the buyer id is valid
		if (null != buyerId) {
			buyerId = leadManagementValidator.validateBuyerId(buyerId);
			if (null == buyerId) {
				return createErrorResponse(
						ResultsCode.INVALID_BUYER_ID.getMessage(),
						ResultsCode.INVALID_BUYER_ID.getCode());
			}
		}
		// Check if the buyer id and lead id is associated
		if (null != buyerId && StringUtils.isNotBlank(leadId)) {
			buyerId = leadManagementValidator.validateLeadBuyerAssociation(leadId, buyerId);
			if (null == buyerId) {
				return createErrorResponse(
						ResultsCode.INVALID_LEAD_BUYER_ASSOCIATION.getMessage(),
						ResultsCode.INVALID_LEAD_BUYER_ASSOCIATION.getCode());

			}
		}
		/** Getting Lead Details along with buyer Details */
		/*
		<leadId>,<buyerId>,<buyerName>,<jobType>,<serviceType>,<serviceCategory>,<leadStatus>,<urgency>,
		<description>,<servicePreferredDate>,<servicePreferredStartTime>,<servicePreferredEndTime>,<timeZone>
		*/
		SLLeadVO slLeadVO = new SLLeadVO();
		LeadCustomerDetailsVO leadCustomerDetailsVO = new LeadCustomerDetailsVO();

		try {
			slLeadVO = leadProcessingBO.getLead(leadId);
			leadCustomerDetailsVO = leadProcessingBO.getLeadCustomerDetails(leadId);
			List<FirmDetailsVO> firmDetailsVOList = leadProcessingBO.getLeadFirmDetails(leadId);
			setLeadDetailsResponse(response,slLeadVO,leadCustomerDetailsVO,firmDetailsVOList);
			response.setResults(Results.getSuccess(ResultsCode.SUCCESS.getMessage()));
		} catch (Exception e) {
			return createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR
					.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}
		return response;

	}
	private void setLeadDetailsResponse(LeadDetailsResponse response,SLLeadVO slLeadVO,LeadCustomerDetailsVO leadCustomerDetailsVO,List<FirmDetailsVO> firmDetailsVOList) {
		// TODO Auto-generated method stub
		if(null != slLeadVO){
			//if(StringUtils.isNotBlank(slLeadVO.getSlLeadId())){
				response.setLeadId(slLeadVO.getSlLeadId());
			//}
			//if(0 != slLeadVO.getBuyerId()){
				response.setBuyerId(slLeadVO.getBuyerId());
			//}

			if(StringUtils.isNotBlank(slLeadVO.getSkill())){
				response.setServiceType(slLeadVO.getSkill());
			}
			if(StringUtils.isNotBlank(slLeadVO.getClientProjectType())){
				response.setJobType(slLeadVO.getClientProjectType());
			}
			if(null != slLeadVO.getServiceDate()){
				String formattedDateString=new SimpleDateFormat(XML_DATE_FORMAT).format(slLeadVO.getServiceDate());
				response.setServicePreferredDate(formattedDateString);
			}
			if(StringUtils.isNotBlank(slLeadVO.getServiceStartTime())){
				response.setServicePreferredStartTime(slLeadVO.getServiceStartTime());
			}
			if(StringUtils.isNotBlank(slLeadVO.getServiceEndTime())){
				response.setServicePreferredEndTime(slLeadVO.getServiceEndTime());		
			}
			if(StringUtils.isNotBlank(slLeadVO.getUrgencyOfService())){
				response.setUrgency(slLeadVO.getUrgencyOfService());
			}
			if(StringUtils.isNotBlank(slLeadVO.getServiceTimeZone())){
				response.setTimeZone(slLeadVO.getServiceTimeZone());
			}
			if(StringUtils.isNotBlank(slLeadVO.getLeadWfStatus())){
				response.setLeadStatus(slLeadVO.getLeadWfStatus());
			}
			if(StringUtils.isNotBlank(slLeadVO.getServiceCategoryDesc())){
				response.setServiceCategory(slLeadVO.getServiceCategoryDesc());
			}
			/*
			if(StringUtils.isNotBlank(slLeadVO.getClientProjectDesc())){
				response.setDescription(slLeadVO.getClientProjectDesc());
			}
			*/
			if(StringUtils.isNotBlank(slLeadVO.getProjectDescription())){
				response.setProjectDescription(slLeadVO.getProjectDescription());
			}
			if(StringUtils.isNotBlank(slLeadVO.getSecondaryProjects())){
				response.setSecondaryProjects(slLeadVO.getSecondaryProjects());
			}
		}

		if(null != leadCustomerDetailsVO){
			CustomerDetails customerDetails = new CustomerDetails();
			Address address = new Address();
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getFirstName())){
				customerDetails.setFirstName(leadCustomerDetailsVO.getFirstName());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getLastName())){
				customerDetails.setLastName(leadCustomerDetailsVO.getLastName());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getMiddleName())){
				customerDetails.setMiddleName(leadCustomerDetailsVO.getMiddleName());
			}
		
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getPhone())){
				customerDetails.setPhone(UIUtils.formatPhoneNumber(leadCustomerDetailsVO.getPhone()));
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getEmail())){
				customerDetails.setEmail(leadCustomerDetailsVO.getEmail());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getStreet())){
				address.setStreet(leadCustomerDetailsVO.getStreet());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getCity())){
				address.setCity(leadCustomerDetailsVO.getCity());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getState())){
				address.setState(leadCustomerDetailsVO.getState());
			}
			if(StringUtils.isNotBlank(leadCustomerDetailsVO.getZip())){
				address.setZip(leadCustomerDetailsVO.getZip());
			}
			if(null != address){
				customerDetails.setAddress(address);
			}
			response.setCustomerDetails(customerDetails);
		}
		 
		//FirmList
		LeadFirmDetailsList firmDetailList = new LeadFirmDetailsList();
		List<LeadFirmDetails> leadFirmDetailsLst =  new ArrayList<LeadFirmDetails>();
		
		if(null != firmDetailsVOList && firmDetailsVOList.size() > 0){					
			for(FirmDetailsVO firmDetailsVO:firmDetailsVOList){
				LeadFirmDetails leadFirmDetails = new LeadFirmDetails();
				if(StringUtils.isNotBlank(firmDetailsVO.getFirmId())){
					leadFirmDetails.setFirmId(Integer.parseInt(firmDetailsVO.getFirmId()));
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getFirmName())){
					leadFirmDetails.setFirmName(firmDetailsVO.getFirmName());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getFirmOwner())){
					leadFirmDetails.setFirmOwner(firmDetailsVO.getFirmOwner());
				}
				if(null != firmDetailsVO.getBusinessStartDate()){
					String yearsOfService = getYearsInBusiness(firmDetailsVO.getBusinessStartDate());
					if (StringUtils.isNotBlank(yearsOfService)) {
						leadFirmDetails.setYearsOfService(Double.parseDouble(yearsOfService));
					}
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getEmail())){
					leadFirmDetails.setEmail(firmDetailsVO.getEmail());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getPhoneNo())){
					leadFirmDetails.setPhoneNo(UIUtils.formatPhoneNumber(firmDetailsVO.getPhoneNo()));
				}
				
				Location location  = new Location();
				
				if(StringUtils.isNotBlank(firmDetailsVO.getLocationTypeDesc())){
					location.setLocationType(firmDetailsVO.getLocationTypeDesc());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getAddress())){
					location.setStreet(firmDetailsVO.getAddress());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getState())){
					location.setState(firmDetailsVO.getState());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getCity())){
					location.setCity(firmDetailsVO.getCity());
				}
				if(StringUtils.isNotBlank(firmDetailsVO.getZip())){
					location.setZip(firmDetailsVO.getZip());
				}
				if(null != location){
					List<Location> locationlst = new ArrayList<Location>();
					locationlst.add(location);
					Locations locations = new Locations();
					locations.setLocation(locationlst);
					leadFirmDetails.setLocations(locations);
				}
				leadFirmDetailsLst.add(leadFirmDetails);
			}
		}
		if(null != leadFirmDetailsLst && leadFirmDetailsLst.size()>0){
			firmDetailList.setFirmDetail(leadFirmDetailsLst);
			response.setFirmDetailList(firmDetailList);
		}	
	}
	private String getYearsInBusiness(Date busStartDate) {
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

	private LeadDetailsResponse createErrorResponse(String message, String code){
		LeadDetailsResponse createResponse = new LeadDetailsResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	
	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}
	
}

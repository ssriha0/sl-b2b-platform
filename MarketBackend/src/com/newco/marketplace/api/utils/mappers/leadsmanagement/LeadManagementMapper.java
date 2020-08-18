package com.newco.marketplace.api.utils.mappers.leadsmanagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.Contact;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FetchProviderFirmResponse;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmDetails;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmIdPrice;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.FirmReview;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPingData;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPingRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPostData;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LMSPostRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.LeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.ProviderFirms;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadMatchingProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadPostedProVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadStatisticsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.util.constants.SOPDFConstants;

public class LeadManagementMapper {

	public static String LMS_SOURCE = "SHS";
	public static String RESIDENTIAL = "Residential";
	public static String COMPETITIVE = "Competitive";
	public static String LMS_API_ACTION = "customSlleadsPingPostLead";
	public static String LMS_API_PING_MODE = "ping";
	public static String LMS_API_POST_MODE = "post";
	public static String LMS_API_TYPE = "193";
	
	private String lmsApiKey;
	
	public static final Map<String, String> URGENCY_OF_SERVICE_MAP = new HashMap<String, String>();
	static {
		URGENCY_OF_SERVICE_MAP.put("SAME_DAY", "Same Day");
		URGENCY_OF_SERVICE_MAP.put("NEXT_DAY", "Next Day");
		URGENCY_OF_SERVICE_MAP.put("AFTER_TOMORROW", "Other");
	}

	public static final Map<String, String> SKILL_MAP = new HashMap<String, String>();
	static {
		SKILL_MAP.put("INSTALL", "Installation ");
		SKILL_MAP.put("REPAIR", "Repair");
	}

	public List<LeadPostedProVO> setTestProviders(
			FetchProviderFirmResponse response) {
		List<LeadPostedProVO> providersList = new ArrayList<LeadPostedProVO>();
		List<FirmDetails> firmdetailsList = response.getFirmDetailsList()
				.getFirmDetailsList();
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		if (null != firmdetailsList && !firmdetailsList.isEmpty()) {
			for (FirmDetails firm : firmdetailsList) {
				LeadPostedProVO postedProvider = new LeadPostedProVO();
				postedProvider.setSlLeadId(response.getSlLeadId());
				postedProvider.setFirmRank(firm.getFirmRank());
				postedProvider.setServiceLiveFirmId(firm.getFirmId());
				Double slRating = null;
				if (null != firm.getFirmRating()) {
					slRating = firm.getFirmRating();
				}
				postedProvider.setServiceLiveFirmRating(slRating);
				postedProvider.setServiceLiveFirmDistance(firm
						.getFirmdistance());
				postedProvider.setCreatedDate(createdDate);
				postedProvider.setModifiedDate(createdDate);
				// setting review details
				if (null != firm.getFirmReviews()
						&& null != firm.getFirmReviews().getFirmReview()
						&& null != firm.getFirmReviews().getFirmReview().get(0)) {
					FirmReview source = firm.getFirmReviews().getFirmReview()
							.get(0);
					if(null != source){
						postedProvider.setReviewComment(source.getComment());
						postedProvider.setReviewerName(source.getReviewerName());
						Timestamp reviewedDate=null;
						if(null!= source.getDate()){
						reviewedDate = new Timestamp(source.getDate().getTime());}
						postedProvider.setCommentedDate(reviewedDate);
						if(null!= source.getRating()){
						postedProvider.setRevieweRating(source.getRating());
						}
					}
					providersList.add(postedProvider);
				}

			}

		}
		return providersList;
	}

	public FetchProviderFirmResponse setTestPostResponse(
			FetchProviderFirmResponse response, String leadId) {
		/* Dummy Response to test api */
		Contact contact1 = new Contact();
		contact1.setEmail("test@hotmail.com");
		contact1.setPhone("6307273356");
		FirmDetails firmdetails1 = new FirmDetails();
		firmdetails1.setContact(contact1);
		firmdetails1.setFirmId(10201);
		firmdetails1.setFirmOwner("Newco Inc.");
		List<FirmDetails> firmdetailsList1 = new ArrayList<FirmDetails>();
		firmdetailsList1.add(firmdetails1);
		ProviderFirms providerFirms = new ProviderFirms();
		providerFirms.setFirmDetailsList(firmdetailsList1);
		// response.setResponseCode("0000");
		response.setLeadId(leadId);
		response.setFirmDetailsList(providerFirms);
		return response;
	}

	public List<LeadMatchingProVO> setPostedproviders(LeadRequest leadRequest,
			String slLeadId) {
		List<String> vendorIdList = new ArrayList<String>();
		List<LeadMatchingProVO> resultList = new ArrayList<LeadMatchingProVO>();
		vendorIdList = leadRequest.getFirmIds().getFirmId();
		List<FirmIdPrice> firmIdPriceList = leadRequest.getFirmIdPriceList();
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		if (null != vendorIdList && !vendorIdList.isEmpty()) {
			for (String vendorId : vendorIdList) {
				LeadMatchingProVO postedPro = new LeadMatchingProVO();
				postedPro.setSlLeadId(slLeadId);
				postedPro.setServiceLiveFirmId(Integer.parseInt(vendorId));
				//postedPro.setLmsFirmId(gen());
				postedPro.setPostedInd(true);
				postedPro.setCreatedDate(createdDate);
				postedPro.setModifiedDate(createdDate);
				/*
				 * lu_lead_firm_status refer 1:Matched 2:UnMatched
				 */
				postedPro.setLeadFirmStatus(1);
				//setting lms firm id
				if(null!=firmIdPriceList && !firmIdPriceList.isEmpty()){
					for(FirmIdPrice firm :firmIdPriceList){
						if(null!= firm){
						  if(vendorId.equalsIgnoreCase(firm.getSlFirmId())){
							if(StringUtils.isNotBlank(firm.getId())){
							   postedPro.setLmsFirmId(Integer.parseInt(firm.getId()));
							}
							if(StringUtils.isNotBlank(firm.getPrice())){
							   postedPro.setLeadPrice(Double.parseDouble(firm.getPrice()));
							}
						  }
					   }
					}
				}else{
					postedPro.setLmsFirmId(gen());
				}
				resultList.add(postedPro);
			}
		}
		return resultList;
	}

	public int gen() {
		Random r = new Random(System.currentTimeMillis());
		return (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
	}

	public LeadStatisticsVO setLeadStatistics(LeadStatisticsVO statisticsVO) {
		// Method to update an instance of leadStatistics
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		statisticsVO
				.setDataFlowDirection(PublicAPIConstant.DataFlowDirection.DATA_FLOW_SL_TO_CLIENT);
		statisticsVO.setCreatedDate(createdDate);
		statisticsVO.setModifiedDate(createdDate);
		statisticsVO.setCreatedBy("SL");
		statisticsVO.setModifiedBy("SL");
		statisticsVO.setRequestDate(null);
		statisticsVO.setResponseDate(createdDate);
		return statisticsVO;
	}

	public LMSPingRequest mapClientToLMS(
			FetchProviderFirmRequest fetchProviderFirmRequest) {
		LMSPingData pingData = new LMSPingData(LMS_SOURCE,
				fetchProviderFirmRequest.getCustomerZipCode(), RESIDENTIAL,
				SKILL_MAP.get(fetchProviderFirmRequest.getSkill()),
				fetchProviderFirmRequest.getLmsProjectDescription(),
				URGENCY_OF_SERVICE_MAP.get(fetchProviderFirmRequest
						.getUrgencyOfService()), COMPETITIVE);
		LMSPingRequest pingRequest = new LMSPingRequest(lmsApiKey,
				LMS_API_ACTION, LMS_API_PING_MODE, LMS_API_TYPE, pingData);
		return pingRequest;
	}

	public LMSPostRequest mapClientToLMS(LeadRequest leadRequest) {
		/*Map<String, Double> firmPriceMap = leadRequest.getLMSFirmIdPriceMap();
		List<FirmIdPrice> firmIdPriceList = new ArrayList<FirmIdPrice>();
		firmPriceMap.put("1", 10.00);
		firmPriceMap.put("5", 20.00);
		for (String firmId : leadRequest.getLMSFirmIdPriceMap().keySet()) {
			FirmIdPrice firmIdPrice = new FirmIdPrice(firmId,
					firmPriceMap.get(firmId).toString());
			firmIdPriceList.add(firmIdPrice);
		}*/
		//Method To format Phone No in Us format
		String unformattedPhoneNo= leadRequest.getCustContact().getContact().getPhone();
		String formattedPhoneNo="";
		if(StringUtils.isNotBlank(unformattedPhoneNo)){
			formattedPhoneNo=formatPhoneNumber(unformattedPhoneNo);
		}
		LMSPostData postData = new LMSPostData(LMS_SOURCE, "Test Page",
				"255.255.255.0", leadRequest.getCustContact().getFirstName(), leadRequest.getCustContact().getLastName(), leadRequest.getCustContact().getContact().getAddress(), leadRequest.getCustContact().getContact().getCity(),
				leadRequest.getCustContact().getContact().getState(), leadRequest.getCustContact().getContact().getCustomerZipCode(), formattedPhoneNo, leadRequest.getCustContact().getContact().getEmail(), leadRequest.getProjectDescription(),
				leadRequest.getSecondaryProjects(), "Sears Home Service");
		LMSPostRequest postRequest = new LMSPostRequest(lmsApiKey,
				LMS_API_ACTION, LMS_API_POST_MODE,leadRequest.getLMSLeadId(),LMS_API_TYPE, postData, leadRequest.getFirmIdPriceList());
		
		return postRequest;
	}
	/**
	 * Method to format the phone number(0000000000) to 000-000-0000 
	 * @param phoneNumber
	 * @return
	 */
	public static String formatPhoneNumber(String phoneNumber){
			String formattedPhone = StringUtils.EMPTY;
			if(null!=phoneNumber){
				if(phoneNumber.length()>2 && !phoneNumber.contains(SOPDFConstants.HYPHEN)){
					formattedPhone=phoneNumber.substring(0, 3);
					if(phoneNumber.length()>5){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3, 6);
					}else if(phoneNumber.length()>3){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3);
					}
					if(phoneNumber.length()>6){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(6);
					}
				}else{
					formattedPhone=phoneNumber;
				}
			}
			return formattedPhone;
	}

	public SLLeadNotesVO mapSLLeadAddNoteRequest(
			LeadAddNoteRequest leadAddNoteRequest, SecurityContext securityContext) {
		// TODO Auto-generated method stub

		if(null!=leadAddNoteRequest.getLeadNote()){
			SLLeadNotesVO leadNotesVO = new SLLeadNotesVO();
			leadNotesVO.setEntityId(leadAddNoteRequest.getVendorBuyerId());
			leadNotesVO.setNoteCategory(leadAddNoteRequest.getLeadNote().getNoteCategory());
			leadNotesVO.setSlLeadId(leadAddNoteRequest.getLeadId());
			leadNotesVO.setNote(leadAddNoteRequest.getLeadNote().getNoteBody());
			leadNotesVO.setCreatedBy(securityContext.getUsername());
			leadNotesVO.setModifiedBy(securityContext.getUsername());
			leadNotesVO.setCreatedDate(new Date());
			leadNotesVO.setModifiedDate(new Date());
			leadNotesVO.setRoleId(securityContext.getRoleId());
			leadNotesVO.setNoteType(leadAddNoteRequest.getLeadNote().getNoteType());
			return leadNotesVO;
		}
		
		return null;
		
	}

	public String getLmsApiKey() {
		return lmsApiKey;
	}

	public void setLmsApiKey(String lmsApiKey) {
		this.lmsApiKey = lmsApiKey;
	}
	
	/************************************************B2C*****************************************/
	public LMSPostRequest mapClientToLMS(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest) {
		/*Map<String, Double> firmPriceMap = leadRequest.getLMSFirmIdPriceMap();
		List<FirmIdPrice> firmIdPriceList = new ArrayList<FirmIdPrice>();
		firmPriceMap.put("1", 10.00);
		firmPriceMap.put("5", 20.00);
		for (String firmId : leadRequest.getLMSFirmIdPriceMap().keySet()) {
			FirmIdPrice firmIdPrice = new FirmIdPrice(firmId,
					firmPriceMap.get(firmId).toString());
			firmIdPriceList.add(firmIdPrice);
		}*/
		//Method To format Phone No in Us format
		String unformattedPhoneNo= leadRequest.getCustContact().getContact().getPhone();
		String formattedPhoneNo="";
		if(StringUtils.isNotBlank(unformattedPhoneNo)){
			formattedPhoneNo=formatPhoneNumber(unformattedPhoneNo);
		}
		LMSPostData postData = new LMSPostData(LMS_SOURCE, "Test Page",
				"255.255.255.0", leadRequest.getCustContact().getFirstName(), leadRequest.getCustContact().getLastName(), leadRequest.getCustContact().getContact().getAddress(), leadRequest.getCustContact().getContact().getCity(),
				leadRequest.getCustContact().getContact().getState(), leadRequest.getCustContact().getContact().getCustomerZipCode(), formattedPhoneNo, leadRequest.getCustContact().getContact().getEmail(), leadRequest.getProjectDescription(),
				leadRequest.getSecondaryProjects(), "Sears Home Service");
		LMSPostRequest postRequest = new LMSPostRequest(lmsApiKey,
				LMS_API_ACTION, LMS_API_POST_MODE,leadRequest.getLMSLeadId(),LMS_API_TYPE, postData, leadRequest.getFirmIdPriceList());
		
		return postRequest;
	}
	public List<LeadMatchingProVO> setPostedproviders(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			String slLeadId) {
		List<String> vendorIdList = new ArrayList<String>();
		List<LeadMatchingProVO> resultList = new ArrayList<LeadMatchingProVO>();
		vendorIdList = leadRequest.getFirmIds().getFirmId();
		List<FirmIdPrice> firmIdPriceList = leadRequest.getFirmIdPriceList();
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		if (null != vendorIdList && !vendorIdList.isEmpty()) {
			for (String vendorId : vendorIdList) {
				LeadMatchingProVO postedPro = new LeadMatchingProVO();
				postedPro.setSlLeadId(slLeadId);
				postedPro.setServiceLiveFirmId(Integer.parseInt(vendorId));
				//postedPro.setLmsFirmId(gen());
				postedPro.setPostedInd(true);
				postedPro.setCreatedDate(createdDate);
				postedPro.setModifiedDate(createdDate);
				/*
				 * lu_lead_firm_status refer 1:Matched 2:UnMatched
				 */
				postedPro.setLeadFirmStatus(1);
				//setting lms firm id
				if(null!=firmIdPriceList && !firmIdPriceList.isEmpty()){
					for(FirmIdPrice firm :firmIdPriceList){
						if(null!= firm){
						  if(vendorId.equalsIgnoreCase(firm.getSlFirmId())){
							if(StringUtils.isNotBlank(firm.getId())){
							   postedPro.setLmsFirmId(Integer.parseInt(firm.getId()));
							}
							if(StringUtils.isNotBlank(firm.getPrice())){
							   postedPro.setLeadPrice(Double.parseDouble(firm.getPrice()));
							}
						  }
					   }
					}
				}else{
					postedPro.setLmsFirmId(gen());
				}
				resultList.add(postedPro);
			}
		}
		return resultList;
	}
	
	public LMSPingRequest mapClientToLMS(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmRequest fetchProviderFirmRequest) {
		LMSPingData pingData = new LMSPingData(LMS_SOURCE,
				fetchProviderFirmRequest.getCustomerZipCode(), RESIDENTIAL,
				SKILL_MAP.get(fetchProviderFirmRequest.getSkill()),
				fetchProviderFirmRequest.getLmsProjectDescription(),
				URGENCY_OF_SERVICE_MAP.get(fetchProviderFirmRequest
						.getUrgencyOfService()), COMPETITIVE);
		LMSPingRequest pingRequest = new LMSPingRequest(lmsApiKey,
				LMS_API_ACTION, LMS_API_PING_MODE, LMS_API_TYPE, pingData);
		return pingRequest;
	}
	
	public List<LeadPostedProVO> setTestProviders(
			com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FetchProviderFirmResponse response) {
		List<LeadPostedProVO> providersList = new ArrayList<LeadPostedProVO>();
		List<com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails> firmdetailsList = response.getFirmDetailsList()
				.getFirmDetailsList();
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		if (null != firmdetailsList && !firmdetailsList.isEmpty()) {
			for (com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmDetails firm : firmdetailsList) {
				LeadPostedProVO postedProvider = new LeadPostedProVO();
				postedProvider.setSlLeadId(response.getSlLeadId());
				postedProvider.setFirmRank(firm.getFirmRank());
				postedProvider.setServiceLiveFirmId(firm.getFirmId());
				Double slRating = null;
				if (null != firm.getFirmRating()) {
					slRating = firm.getFirmRating();
				}
				postedProvider.setServiceLiveFirmRating(slRating);
				postedProvider.setServiceLiveFirmDistance(firm
						.getFirmdistance());
				postedProvider.setCreatedDate(createdDate);
				postedProvider.setModifiedDate(createdDate);
				// setting review details
				if (null != firm.getFirmReviews()
						&& null != firm.getFirmReviews().getFirmReview()
						&& null != firm.getFirmReviews().getFirmReview().get(0)) {
					com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.FirmReview source = firm.getFirmReviews().getFirmReview()
							.get(0);
					if(null != source){
						postedProvider.setReviewComment(source.getComment());
						postedProvider.setReviewerName(source.getReviewerName());
						Timestamp reviewedDate=null;
						if(null!= source.getDate()){
						reviewedDate = new Timestamp(source.getDate().getTime());}
						postedProvider.setCommentedDate(reviewedDate);
						if(null!= source.getRating()){
						postedProvider.setRevieweRating(source.getRating());
						}
					}
					providersList.add(postedProvider);
				}

			}

		}
		return providersList;
	}
	
	public List<LeadMatchingProVO> mapPostedProviders(com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.v2_0.LeadRequest leadRequest,
			String slLeadId) {
		List<String> vendorIdList = new ArrayList<String>();
		List<LeadMatchingProVO> resultList = new ArrayList<LeadMatchingProVO>();
		Date today = new Date(System.currentTimeMillis());
		Timestamp createdDate = new Timestamp(today.getTime());
		vendorIdList = leadRequest.getFirmIds().getFirmId();
		
		if (null != vendorIdList && !vendorIdList.isEmpty()) {
			for (String vendorId : vendorIdList) {
				LeadMatchingProVO postedPro = new LeadMatchingProVO();
				postedPro.setSlLeadId(slLeadId);
				postedPro.setServiceLiveFirmId(Integer.parseInt(vendorId));
				postedPro.setPostedInd(true);
				postedPro.setCreatedDate(createdDate);
				postedPro.setModifiedDate(createdDate);
				// lu_lead_firm_status refer 1:Matched 2:UnMatched
				postedPro.setLeadFirmStatus(1);
				// setting lms firm id as null
				postedPro.setLmsFirmId(null);
				// setting lead price as 0.0$
				postedPro.setLeadPrice(new Double(0.0));
				resultList.add(postedPro);
			}
		}
		return resultList;
	}
}

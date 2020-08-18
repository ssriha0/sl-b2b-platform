package com.newco.marketplace.api.utils.mappers.d2c;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.firmDetail.Credential;
import com.newco.marketplace.api.beans.firmDetail.Credentials;
import com.newco.marketplace.api.beans.firmDetail.FirmContact;
import com.newco.marketplace.api.beans.firmDetail.FirmLocation;
import com.newco.marketplace.api.beans.firmDetail.FirmService;
import com.newco.marketplace.api.beans.firmDetail.FirmServices;
import com.newco.marketplace.api.beans.firmDetail.GetFirmDetailsRequest;
import com.newco.marketplace.api.beans.firmDetail.Insurance;
import com.newco.marketplace.api.beans.firmDetail.Insurances;
import com.newco.marketplace.api.beans.firmDetail.LastCompletedProject;
import com.newco.marketplace.api.beans.firmDetail.PolAndProc;
import com.newco.marketplace.api.beans.firmDetail.PolAndProcList;
import com.newco.marketplace.api.beans.firmDetail.Review;
import com.newco.marketplace.api.beans.firmDetail.Reviews;
import com.newco.marketplace.api.beans.firmDetail.Warranty;
import com.newco.marketplace.api.beans.firmDetail.WarrantyList;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.FirmDetailsMapper;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CFirmDetails;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CFirms;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CProviderAPIVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ReviewVO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailRequestVO;
import com.newco.marketplace.dto.vo.provider.FirmDetailsResponseVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.LastClosedOrderVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.WarrantyVO;

public class D2CFirmDetailsMapper {
	private final Logger logger = Logger.getLogger(FirmDetailsMapper.class);

	/**
	 * Method to map the getFirmDetails Request
	 * @param getFirmDetailRequest
	 * @param requestFilter
	 * @return
	 */
		FirmDetailRequestVO detailRequestVO = new FirmDetailRequestVO();
		public FirmDetailRequestVO mapFirmDetailRequest(GetFirmDetailsRequest getFirmDetailRequest,String requestFilter) {
		List<String> firmList = new ArrayList<String>();
		List<String> filterList = new ArrayList<String>();
		List<String> fullFilterList = null;
		if(null !=getFirmDetailRequest && null !=getFirmDetailRequest.getFirmIds().getFirmId()){
			Set<String> vendorSet = new HashSet<String>(getFirmDetailRequest.getFirmIds().getFirmId());
			if(null != vendorSet){
				firmList.addAll(vendorSet);
			}
		}								
		if (StringUtils.isNotBlank(requestFilter)) { 
			StringTokenizer strTok = new StringTokenizer(requestFilter,PublicAPIConstant.SEPERATOR_PIPE, false);
			if(null!=strTok){
				int noOfTokens = strTok.countTokens();
				for (int i = 0; i < noOfTokens; i++) {
					String token = strTok.nextToken();
					if(StringUtils.isNotBlank(token)){
						String filter = new String(token.toLowerCase());
						//setting all the criterias in case if user has given 'full' as filter criteria
						if(PublicAPIConstant.FULL.equalsIgnoreCase(filter)){
							fullFilterList = PublicAPIConstant.getFirmFilters();
							break;
						}
						if(!filterList.contains(filter)){
							filterList.add(filter);
						}
					}
					
				}
			}
		}
		else{
			filterList.add(PublicAPIConstant.BASIC);
		}		
		if(null != fullFilterList && !fullFilterList.isEmpty()){
			detailRequestVO.setFilter(fullFilterList);
		}else{
			detailRequestVO.setFilter(filterList);
		}
		detailRequestVO.setFirmId(firmList);
		return detailRequestVO;

	}
	/**
	 * Method to map the getFirmDetails Response
	 * @param firmDetailsResponseVO
	 * @param firmDetailRequestVO.getFirmId() 
	 * @return
	 */
	public D2CGetFirmDetailsResponse mapFirmDetailsResponse(
			FirmDetailsResponseVO firmDetailsResponseVO,
			FirmDetailRequestVO firmDetailRequestVO,
			Map<String, D2CProviderAPIVO> d2cProviderAPIVOMap) {

		D2CGetFirmDetailsResponse response =new D2CGetFirmDetailsResponse();
		D2CFirms firms = new D2CFirms();
		List<D2CFirmDetails>firmList = new ArrayList<D2CFirmDetails>();
		D2CFirmDetails firmDetails = null;
		LastCompletedProject lastCompletedProject = null;
		FirmContact contact = null;	
		WarrantyList warrantyList = null;
		PolAndProcList polAndProcList = null;
		Insurances insurancesList = null;
		Credentials credentialsList = null;
		FirmServices firmServices = null;
		Reviews reviews = null;
		if(null != firmDetailRequestVO){
			for(String firmId:firmDetailRequestVO.getFirmId()){
				
				//method to map the basic firm details to the response
				firmDetails = mapBasicFirmResponse(firmDetailsResponseVO,firmId,d2cProviderAPIVOMap.get(firmId));
				
				if(null!= firmDetailRequestVO.getFilter() && !firmDetailRequestVO.getFilter().isEmpty()){
					for(String filter : firmDetailRequestVO.getFilter()){
						//method to map the last closed order details of the firm
						if(PublicAPIConstant.LASTORDER.equalsIgnoreCase(filter)){
							lastCompletedProject = mapLastCompletedOrder(firmDetailsResponseVO,firmId);
							firmDetails.setLastCompletedProject(lastCompletedProject);
						}
						//method to map the contact details of the firm
						else if (PublicAPIConstant.CONTACT.equalsIgnoreCase(filter)){
							contact = mapFirmContactDetails(firmDetailsResponseVO,firmId);
							firmDetails.setContact(contact);
						}
						//method to map the warranty details of a firm
						else if(PublicAPIConstant.WARRANTY.equalsIgnoreCase(filter)){
							warrantyList = mapWarrantyDetails(firmDetailsResponseVO,firmId);
							firmDetails.setWarrantyList(warrantyList);
						}
						//method to map the policy details of a firm
						else if(PublicAPIConstant.POLICY.equalsIgnoreCase(filter)){
							polAndProcList = mapPolicyDetails(firmDetailsResponseVO,firmId);
							firmDetails.setPolAndProcList(polAndProcList);
						}
						//method to map the insurances details of a firm
						else if(PublicAPIConstant.INSURANCES.equalsIgnoreCase(filter)){
							insurancesList = mapInsuranceDetails(firmDetailsResponseVO,firmId);
							if(null !=insurancesList){
								firmDetails.setInsurances(insurancesList);
							}
						}
						//method to map the credentials details of a firm
						else if(PublicAPIConstant.CREDENTIALS.equalsIgnoreCase(filter)){
							credentialsList = mapCredentialDetails(firmDetailsResponseVO,firmId);
							if(null!=credentialsList){
								firmDetails.setCredentials(credentialsList);
							}
						}
						// method to map the service details of a firm
						else if(PublicAPIConstant.SERVICES.equalsIgnoreCase(filter)){
							firmServices = mapServiceDetails(firmDetailsResponseVO,firmId);
							if(null !=firmServices){
								firmDetails.setServices(firmServices);
							}
						}
						//method to map the review details of a firm
						else if(PublicAPIConstant.REVIEWS.equalsIgnoreCase(filter)){
							reviews = mapReviewDetails(firmDetailsResponseVO,firmId);
							if(null !=reviews){
								firmDetails.setReviews(reviews);
							}
						}
					}
				}
				firmList.add(firmDetails);
			}
		}

		firms.setFirmDetails(firmList);
		response.setFirms(firms);
		return response;
	}

	/**
	 * method to map the basic firm details
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private D2CFirmDetails mapBasicFirmResponse(
			FirmDetailsResponseVO firmDetailsResponseVO, String firmId,
			D2CProviderAPIVO d2cProviderAPIVO) {

		Map<String,BasicFirmDetailsVO> basicDetailsVOs = firmDetailsResponseVO.getBasicDetailsVOs();
		D2CFirmDetails firmDetails = null;
		BasicFirmDetailsVO detailsVO = null;
		if(null != basicDetailsVOs){
			detailsVO = basicDetailsVOs.get(firmId);
		}
		if (null != detailsVO) {
			firmDetails = new D2CFirmDetails();
			FirmLocation location = new FirmLocation();
			Double yearsOfService = 0.00;
			if (null != detailsVO.getStartDate()) {
				yearsOfService = Double.valueOf(getYearsInBusiness(detailsVO.getStartDate()));
			}
			firmDetails.setFirmId(detailsVO.getFirmId());
			firmDetails.setBusinessName(detailsVO.getBusinessName());
			// SL-21446-Adding company logo
			firmDetails.setPrice(d2cProviderAPIVO.getPrice());
			firmDetails.setDailyLimit(d2cProviderAPIVO.getDailyLimit());
			firmDetails.setAcceptedSoCount(null != d2cProviderAPIVO.getAcceptedCount() ? d2cProviderAPIVO.getAcceptedCount() : 0);
			firmDetails.setCompanyLogoUrl(detailsVO.getCompanyLogoUrl());
			firmDetails.setOverView(detailsVO.getOverView());
			firmDetails.setOptIn(d2cProviderAPIVO.getOptIn());
			if (StringUtils.isNotBlank(detailsVO.getFirstName())) {
				if (StringUtils.isNotBlank(detailsVO.getLastName())) {
					firmDetails.setFirmOwner(detailsVO.getFirstName() + " " + detailsVO.getLastName());
				} else {
					firmDetails.setFirmOwner(detailsVO.getFirstName());
				}
			}

			DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
			if (null != detailsVO.getFirmAggregateRating()) {
				firmDetails.setFirmAggregateRating(Double.valueOf(df.format(detailsVO.getFirmAggregateRating())));
			}

			firmDetails.setReviewCount(detailsVO.getReviewCount());
			firmDetails.setNumberOfEmployees(detailsVO.getNumberOfEmployees());
			DecimalFormat dfrmt = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);
			if (null != yearsOfService) {
				firmDetails.setYearsOfService(Double.valueOf(dfrmt.format(yearsOfService)));
			}
			if (null != detailsVO.getMinHourlyRate()) {
				if (null != detailsVO.getMaxHourlyRate()) {
					firmDetails.setHourlyRate("$" + detailsVO.getMinHourlyRate() + " - $" + detailsVO.getMaxHourlyRate());
				} else {
					firmDetails.setHourlyRate("$" + detailsVO.getMinHourlyRate());
				}
			}

			location.setStreet1(detailsVO.getStreet1());
			location.setStreet2(detailsVO.getStreet2());
			location.setCity(detailsVO.getCity());
			location.setState(detailsVO.getState());
			location.setZip(detailsVO.getZip());
			location.setZip4(detailsVO.getZip4());
			if (null != location) {
				firmDetails.setLocation(location);
			}

			// Time to accept 0.00 --> NA
			if (detailsVO.getFirmAverageTimeToAccept() > 0.00) {
				firmDetails.setFirmAverageTimeToAccept(dfrmt.format(detailsVO.getFirmAverageTimeToAccept()) + " hrs");
			} else {
				firmDetails.setFirmAverageTimeToAccept("NA");
			}

			// Average arrival percentage 0.00 --> 0.00 %
			firmDetails.setFirmAverageArrivalWindow(dfrmt.format(detailsVO.getFirmAverageArrivalWindow()) + " %");
			
			// bind rank to response
			firmDetails.setProviderRank(d2cProviderAPIVO.getProviderRank());
			
		}
		
		return firmDetails;
		
	}
	/**
	 * method to map the last completed project of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private LastCompletedProject mapLastCompletedOrder(FirmDetailsResponseVO firmDetailsResponseVO,String firmId){

		Map<String,LastClosedOrderVO> lastClosedOrderVOs = firmDetailsResponseVO.getLastClosedOrderVOs();
		String closedDate = null;
		LastClosedOrderVO closedOrderVO = null;
		LastCompletedProject lastCompletedProject = null;
		if(null != lastClosedOrderVOs){
			closedOrderVO = lastClosedOrderVOs.get(firmId);
		}
		if(null != closedOrderVO){
			lastCompletedProject = new LastCompletedProject();
			lastCompletedProject.setSoId(closedOrderVO.getSoId());
			lastCompletedProject.setTitle(closedOrderVO.getTitle());
			if(StringUtils.isNotBlank(closedOrderVO.getOverView())){
				lastCompletedProject.setOverview(ServiceLiveStringUtils.removeHTML(closedOrderVO.getOverView()));
			}
			lastCompletedProject.setCity(closedOrderVO.getCity());
			lastCompletedProject.setState(closedOrderVO.getState());
			lastCompletedProject.setZip(closedOrderVO.getZip());
			if(null !=closedOrderVO.getClosedDate()){
				try {
					closedDate = DateUtils.dateToDefaultFormatString(closedOrderVO.getClosedDate());
				} catch (ParseException e) {
					logger.error("exception in formatting closed date in mapclosedorder(");
					e.printStackTrace();
				}
				lastCompletedProject.setClosedDate(closedDate);
			}
		}
		return lastCompletedProject;
	}
	/**
	 * method to map the contact details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private FirmContact mapFirmContactDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {
		Map<String,BasicFirmDetailsVO> basicDetailsVOs = firmDetailsResponseVO.getBasicDetailsVOs();

		BasicFirmDetailsVO detailsVO = null;
		FirmContact firmContact = null;
		if(null != basicDetailsVOs){
			detailsVO = basicDetailsVOs.get(firmId);
		}
		if(null != detailsVO){
			firmContact = new FirmContact();
			firmContact.setEmail(detailsVO.getEmail());
			firmContact.setAltEmail(detailsVO.getAltEmail());
			firmContact.setWeb(detailsVO.getWeb());
			if(StringUtils.isNotBlank(detailsVO.getBusinessPhone())){
				firmContact.setBusinessPhone(UIUtils.formatPhoneNumber(detailsVO.getBusinessPhone()));
			}

			firmContact.setBusinessPhoneExt(detailsVO.getBusinessPhoneExt());
			firmContact.setBusinessFax(detailsVO.getBusinessFax());
		}
		return firmContact;
	}
	/**
	 * method to map the warranty details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private WarrantyList mapWarrantyDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {

		Map<String,WarrantyVO> warrantPolicyVOs = firmDetailsResponseVO.getWarrantPolicyVOs();
		WarrantyList warrantyList = null;
		List<Warranty> list = new ArrayList<Warranty>(); 
		WarrantyVO warrantyVO = null;
		if(null != warrantPolicyVOs){
			warrantyVO = warrantPolicyVOs.get(firmId);
		}
		if(null != warrantyVO){
			warrantyList = new WarrantyList();
			if(StringUtils.isNotBlank(warrantyVO.getFreeEstimate())){
				Warranty warranty = new Warranty();
				warranty.setWarrantyType(PublicAPIConstant.FREE_ESTIMATE);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getFreeEstimate())){
					warranty.setWarrantyValue(PublicAPIConstant.YES);
				}
				else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getFreeEstimate())){
					warranty.setWarrantyValue(PublicAPIConstant.NO);
				}
				else{
					warranty.setWarrantyValue(PublicAPIConstant.NOT_APPLICABLE);
				}
				warranty.setWarrantyDays(PublicAPIConstant.NOT_APPLICABLE);
				list.add(warranty);
			}
			if(StringUtils.isNotBlank(warrantyVO.getWarrOfferedLabor())){
				Warranty warranty = new Warranty();
				warranty.setWarrantyType(PublicAPIConstant.WARRANTY_LABOR);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getWarrOfferedLabor())){
					warranty.setWarrantyValue(PublicAPIConstant.YES);
					if(StringUtils.isNotBlank(warrantyVO.getWarrPeriodLabor())){
						warranty.setWarrantyDays(warrantyVO.getWarrPeriodLabor());
					}
				}
				else{
					warranty.setWarrantyValue(PublicAPIConstant.NO);
					warranty.setWarrantyDays(PublicAPIConstant.NOT_APPLICABLE);
				}
				list.add(warranty);
			}
			if(StringUtils.isNotBlank(warrantyVO.getWarrOfferedParts())){
				Warranty warranty = new Warranty();
				warranty.setWarrantyType(PublicAPIConstant.WARRANTY_PARTS);

				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getWarrOfferedParts())){
					warranty.setWarrantyValue(PublicAPIConstant.YES);
					if(StringUtils.isNotBlank(warrantyVO.getWarrPeriodParts())){
						warranty.setWarrantyDays(warrantyVO.getWarrPeriodParts());
					}
				}
				else{
					warranty.setWarrantyValue(PublicAPIConstant.NO);
					warranty.setWarrantyDays(PublicAPIConstant.NOT_APPLICABLE);
				}
				list.add(warranty);
			}
			warrantyList.setWarranty(list);
		}
		return warrantyList;
	}
	/**
	 * method to map the policy and procedure details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private PolAndProcList mapPolicyDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {
		Map<String,WarrantyVO> warrantPolicyVOs = firmDetailsResponseVO.getWarrantPolicyVOs();

		PolAndProcList policyList = null;
		List<PolAndProc> list = new ArrayList<PolAndProc>(); 
		WarrantyVO warrantyVO = null;
		if(null != warrantPolicyVOs){
			warrantyVO = warrantPolicyVOs.get(firmId);
		}
		if(null != warrantyVO){
			policyList = new PolAndProcList();
			if(StringUtils.isNotBlank(warrantyVO.getConductDrugTest())){
				PolAndProc polAndProc = new PolAndProc();
				polAndProc.setPolicyType(PublicAPIConstant.DRUG_TEST);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getConductDrugTest())){
					polAndProc.setPolicyValue(PublicAPIConstant.YES);
					polAndProc.setPolicyPlanValue(null);
				}
				else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getConductDrugTest())){
					polAndProc.setPolicyValue(PublicAPIConstant.NO);
					polAndProc.setPolicyPlanType(PublicAPIConstant.CONSIDER_DRUG_TEST);
					if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getConsiderDrugTest())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.YES);
					}
					else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getConsiderDrugTest())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.NO);
					}

				}
				list.add(polAndProc);
			}
			if(StringUtils.isNotBlank(warrantyVO.getHasEthicsPolicy())){
				PolAndProc polAndProc = new PolAndProc();
				polAndProc.setPolicyType(PublicAPIConstant.WORK_ENV);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getHasEthicsPolicy())){
					polAndProc.setPolicyValue(PublicAPIConstant.YES);
					polAndProc.setPolicyPlanValue(null);
				}
				else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getHasEthicsPolicy())){
					polAndProc.setPolicyValue(PublicAPIConstant.NO);
					polAndProc.setPolicyPlanType(PublicAPIConstant.CONSIDER_WORK_ENV);
					if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getConsiderEthicPolicy())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.YES);
					}
					else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getConsiderEthicPolicy())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.NO);
					}

				}
				list.add(polAndProc);
			}
			if(StringUtils.isNotBlank(warrantyVO.getRequireBadge())){
				PolAndProc polAndProc = new PolAndProc();
				polAndProc.setPolicyType(PublicAPIConstant.REQUIRE_BDGE);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getRequireBadge())){
					polAndProc.setPolicyValue(PublicAPIConstant.YES);
					polAndProc.setPolicyPlanValue(null);
				}
				else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getRequireBadge())){
					polAndProc.setPolicyValue(PublicAPIConstant.NO);
					polAndProc.setPolicyPlanType(PublicAPIConstant.CONSIDER_BAGDE);
					if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getConsiderBadge())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.YES);
					}
					else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getConsiderBadge())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.NO);
					}

				}
				list.add(polAndProc);
			}
			if(StringUtils.isNotBlank(warrantyVO.getRequireUsDoc())){
				PolAndProc polAndProc = new PolAndProc();
				polAndProc.setPolicyType(PublicAPIConstant.CITIZEN_PROOF);
				if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getRequireUsDoc())){
					polAndProc.setPolicyValue(PublicAPIConstant.YES);
					polAndProc.setPolicyPlanValue(null);
				}
				else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getRequireUsDoc())){
					polAndProc.setPolicyValue(PublicAPIConstant.NO);
					polAndProc.setPolicyPlanType(PublicAPIConstant.CONSIDER_CITIZEN_PROOF);
					if(PublicAPIConstant.ONE.equalsIgnoreCase(warrantyVO.getConsiderImplPolicy())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.YES);
					}
					else if(PublicAPIConstant.ZERO.equalsIgnoreCase(warrantyVO.getConsiderImplPolicy())){
						polAndProc.setPolicyPlanValue(PublicAPIConstant.NO);
					}

				}
				list.add(polAndProc);
			}
			policyList.setPolAndProc(list);
		}
		return policyList;
	}

	/**
	 * method to map the insurance details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 * @throws ParseException 
	 */
	private Insurances mapInsuranceDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId)  {

		Map<String,List<LicensesAndCertVO>> insurancePolicyVOs = firmDetailsResponseVO.getInsuranceDetailsVOs();
		List<LicensesAndCertVO> insurancePolicyVOList = insurancePolicyVOs.get(firmId);
		List<Insurance> insurancelist = new ArrayList<Insurance>();
		Insurances insurances = null;
		String verificationDate = null;
		if(null != insurancePolicyVOList && !insurancePolicyVOList.isEmpty())
		{
			insurances = new Insurances();
			for(LicensesAndCertVO insurancePolVO:insurancePolicyVOList)
			{
				Insurance insurance = new Insurance();
				insurance.setName(insurancePolVO.getCredTypeDesc());
				if(StringUtils.isNotBlank(insurancePolVO.getWfStatus()) && PublicAPIConstant.APPROVED.equalsIgnoreCase(insurancePolVO.getWfStatus())){
					insurance.setVerified(PublicAPIConstant.TRUE);
					try{
						if(null != insurancePolVO.getInsModifiedDate()){
							verificationDate = DateUtils.dateToDefaultFormatString(insurancePolVO.getInsModifiedDate());
							insurance.setVerificationDate(verificationDate);
						}
					}
					catch(Exception e){
						logger.error("exception occured in mapInsuranceDetails"+e.getMessage());
					}
				}else{
					insurance.setVerified(PublicAPIConstant.FALSE);
				}
				if(null !=insurancePolVO.getAmount()){

					DecimalFormat dfrmt = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WITH_ZEROS);
					if(null != insurancePolVO.getAmount()){
						insurance.setAmount(Double.valueOf(dfrmt.format(insurancePolVO.getAmount())));
					}
				}
				insurancelist.add(insurance);
			}
			insurances.setInsurance(insurancelist);
		}
		return insurances;
	}
	/**
	 * method to map the credential details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private Credentials mapCredentialDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {

		Map<String,List<LicensesAndCertVO>> credentialDetailsVOs = firmDetailsResponseVO.getCredentialDetailsVOs();
		List<LicensesAndCertVO> credentialVOList = credentialDetailsVOs.get(firmId);
		List<Credential> credentialList = new ArrayList<Credential>();
		Credentials credetials = null;
		String issueDate = null;
		String expiryDate = null;
		if(null != credentialVOList && !credentialVOList.isEmpty())
		{
			credetials = new Credentials();
			for(LicensesAndCertVO credentialVO:credentialVOList)
			{
				Credential credential = new Credential();
				credential.setCredentialType(PublicAPIConstant.COMPANY_CREDENTIAL_TYPE);
				credential.setType(credentialVO.getCredTypeDesc());
				credential.setCategory(credentialVO.getCategoryTypeDesc());
				credential.setSource(credentialVO.getSource());
				credential.setName(credentialVO.getLicenseName());
				credential.setStatus(credentialVO.getWfStatus());
				credential.setNo(credentialVO.getCredentialNum());
				if(null !=credentialVO.getIssueDate()){
					try {
						issueDate = DateUtils.dateToDefaultFormatString(credentialVO.getIssueDate());
					} catch (ParseException e) {
						logger.error("exception in formatting issue date in mapcredentials(");
						e.printStackTrace();
					}
					credential.setIssueDate(issueDate);
				}
				if(null !=credentialVO.getExpirationDate()){
					try {
						expiryDate = DateUtils.dateToDefaultFormatString(credentialVO.getExpirationDate());
					} catch (ParseException e) {
						logger.error("exception in formatting expiry date in mapcredentials(");
						e.printStackTrace();
					}
					credential.setExpiryDate(expiryDate);
				}

				credentialList.add(credential);
			}
			credetials.setCredential(credentialList);
		}
		return credetials;
	}
	/**
	 * method to map the service details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private FirmServices mapServiceDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {
		Map<String,List<FirmServiceVO>> firmServiceVOs = firmDetailsResponseVO.getFirmServiceVOs();
		List<FirmServiceVO>serviceVOList = firmServiceVOs.get(firmId);
		List<FirmService> serviceList = new ArrayList<FirmService>();
		FirmServices firmServices = null;

		if(null != serviceVOList && !serviceVOList.isEmpty()){
			firmServices = new FirmServices();
			for(FirmServiceVO serviceVO: serviceVOList){
				FirmService firmService = new FirmService();
				firmService.setProjectType(serviceVO.getProject());
				firmService.setServiceCategory(serviceVO.getRootCategory());
				serviceList.add(firmService);
			}
			firmServices.setService(serviceList);
		}
		return firmServices;
	}
	/**
	 * method to map the review details of a firm
	 * @param firmDetailsResponseVO
	 * @param firmId
	 * @return
	 */
	private Reviews mapReviewDetails(FirmDetailsResponseVO firmDetailsResponseVO, String firmId) {
		Map<String,List<ReviewVO>> reviewVOs = firmDetailsResponseVO.getReviewVOs();
		List<ReviewVO> reviewVOList = reviewVOs.get(firmId);
		Reviews reviews = null;
		String reviewDate = null;
		List<Review>reviewList = new ArrayList<Review>();
		if(null!=reviewVOList && !reviewVOList.isEmpty()){
			reviews = new Reviews();
			for(ReviewVO reviewVO: reviewVOList){
				Review review = new Review();
				review.setAuthor(reviewVO.getReviewerName());
				review.setComment(reviewVO.getReviewComment());

				if(null != reviewVO.getReviewDateObj()){
					try {
						reviewDate = DateUtils.dateToDefaultFormatString(reviewVO.getReviewDateObj());
					} catch (ParseException e) {
						logger.error("exception in formatting review date in mapReviewDetails(");
						e.printStackTrace();
					}
					review.setDate(reviewDate);
				}
				DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_THREE_DECIMAL);
				if(null != reviewVO.getReviewRatingObj()){
					review.setRating(Double.valueOf(df.format(reviewVO.getReviewRatingObj())));
				}

				reviewList.add(review);
			}
			reviews.setReview(reviewList);
		}
		return reviews;
	}
	/**
	 * method to find the number of years of service of a firm
	 * @param busStartDate
	 * @return
	 */
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


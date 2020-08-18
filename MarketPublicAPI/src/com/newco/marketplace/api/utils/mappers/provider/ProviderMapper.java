
package com.newco.marketplace.api.utils.mappers.provider;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsResponse;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersResponse;
import com.newco.marketplace.api.beans.hi.account.firm.create.v1_0.CreateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedFirms;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedInvalidFirm;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedInvalidProvider;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ApprovedProviders;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.BackgroundCheck;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.Credential;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InvalidFirms;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.InvalidProviders;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.Provider;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ProviderFirm;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ProviderFirmStatus;
import com.newco.marketplace.api.beans.hi.account.firm.v1_0.ProviderStatus;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
/**
 * This class is a mapper class for mapping  provider/firm information.
 * 
 * 
 * @version 1.0
 */
public class ProviderMapper {
	 
	private Logger LOGGER = Logger.getLogger(ProviderMapper.class);
	public static Integer ONE=1;
	public static Integer ZERO=0;
	public static String  YES= "Yes"; 
	ArrayList<String> list= null;
	
    public ProviderRegistrationVO mapCreateFirmRequest(APIRequestVO apiVO) throws Exception{

		CreateFirmAccountRequest createFirmAccountRequest= (CreateFirmAccountRequest) apiVO
				.getRequestFromPostPut();
		ProviderRegistrationVO providerRegistrationVO=new ProviderRegistrationVO();
		// setting password
		String password= AdminUtil.generatePassword();
		providerRegistrationVO.setPassword(password);
		//password = CryptoUtil.encrypt(password);
		//setting  legal Business Name
		providerRegistrationVO.setLegalBusinessName(createFirmAccountRequest.getLegalBusinessName());
		//setting  primary Industry
		providerRegistrationVO.setPrimaryIndustry(createFirmAccountRequest.getPrimaryIndustry());
		// ??other primary service
		providerRegistrationVO.setOtherPrimaryService(null);
		// setting name details
		if(null!=createFirmAccountRequest.getNameDetails()){
			providerRegistrationVO.setLastName(createFirmAccountRequest.getNameDetails().getLastName().trim());
			providerRegistrationVO.setFirstName(createFirmAccountRequest.getNameDetails().getFirstName().trim());
			if(StringUtils.isNotBlank(createFirmAccountRequest.getNameDetails().getMiddleName())){			
				providerRegistrationVO.setMiddleName(createFirmAccountRequest.getNameDetails().getMiddleName().trim());
			}
			if(StringUtils.isNotBlank(createFirmAccountRequest.getNameDetails().getNameSuffix())){		
				providerRegistrationVO.setNameSuffix(createFirmAccountRequest.getNameDetails().getNameSuffix().trim());
			}
		} 
		//setting Job title
		if(StringUtils.isNotBlank(createFirmAccountRequest.getJobTitle())){			
		providerRegistrationVO.setJobTitle(createFirmAccountRequest.getJobTitle());
		}
		//setting Role within company
		providerRegistrationVO.setRoleWithinCom(createFirmAccountRequest.getRoleWithinCom());
		//setting Email and  Alternate Email
		providerRegistrationVO.setEmail(createFirmAccountRequest.getEmail());
		providerRegistrationVO.setAltEmail(createFirmAccountRequest.getAltEmail());
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getBusinessStructure())){		
		providerRegistrationVO.setBusStructure(createFirmAccountRequest.getBusinessStructure());
		}
		providerRegistrationVO.setBusinessStartDate(createFirmAccountRequest.getBusinessStartedDate());
		
	   
	   if (createFirmAccountRequest.getBusinessStartedDate() != null)
			{
			try {
				SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
			   	java.util.Date issueParsedDate;
				issueParsedDate = issueDateformater.parse(createFirmAccountRequest.getBusinessStartedDate());
				providerRegistrationVO.setBusStartDt(issueParsedDate);

			} catch (ParseException e) {
				LOGGER.info(" ParseException"+e);
			}
		}
	   
	   
		if(StringUtils.isNotBlank(createFirmAccountRequest.getDunsNumber())){		
		providerRegistrationVO.setDunsNo(createFirmAccountRequest.getDunsNumber());
		}
		if(StringUtils.isNotBlank(createFirmAccountRequest.getForeignOwnedPercentage()))
		{
			providerRegistrationVO.setIsForeignOwned("1");
			providerRegistrationVO.setForeignOwnedPct(createFirmAccountRequest.getForeignOwnedPercentage());
			/*if(("0").equals(createFirmAccountRequest.getForeignOwnedPercentage())){
				providerRegistrationVO.setIsForeignOwned("0");	
			}
			else{
				providerRegistrationVO.setIsForeignOwned("1");
			}*/
		}
		else
		{
			providerRegistrationVO.setIsForeignOwned("0");
		}
		
		providerRegistrationVO.setCompanySize(createFirmAccountRequest.getCompanySize());
		providerRegistrationVO.setAnnualSalesRevenue(createFirmAccountRequest.getAnnualSalesRevenue());
		providerRegistrationVO.setDescription(createFirmAccountRequest.getBusinessDesc());
		
		//setting service call
		if(null!=createFirmAccountRequest.getServiceCall()){
		providerRegistrationVO.setServiceCall(createFirmAccountRequest.getServiceCall().toString());
        }
		//setting firm Type and sub contractor Id
		if(StringUtils.isNotBlank(createFirmAccountRequest.getFirmType())){
		providerRegistrationVO.setFirmType(createFirmAccountRequest.getFirmType());
		}
		if(StringUtils.isNotBlank(createFirmAccountRequest.getSubContractorId())){
		providerRegistrationVO.setSubContractId(createFirmAccountRequest.getSubContractorId());
		}
		// setting main business phone and extension 
		if(StringUtils.isNotBlank(createFirmAccountRequest.getMainBusiPhoneNo())){
			providerRegistrationVO.setMainBusiPhoneNo1(removeHyphenFromNumber(createFirmAccountRequest.getMainBusiPhoneNo()));
		}
		if(StringUtils.isNotBlank(createFirmAccountRequest.getMainBusinessExtn())){
		providerRegistrationVO.setMainBusinessExtn(createFirmAccountRequest.getMainBusinessExtn());
		}
		//setting username
		if(StringUtils.isNotBlank(createFirmAccountRequest.getUserName())){
		providerRegistrationVO.setUserName(createFirmAccountRequest.getUserName().trim());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getdBAName())){
		providerRegistrationVO.setDBAName(createFirmAccountRequest.getdBAName());
		}
		if(StringUtils.isNotBlank(createFirmAccountRequest.getWebsiteAddress())){
		providerRegistrationVO.setWebsiteAddress(createFirmAccountRequest.getWebsiteAddress());
		}
		// setting the referral details
		if(StringUtils.isNotBlank(createFirmAccountRequest.getHowDidYouHear())){
		providerRegistrationVO.setHowDidYouHear(createFirmAccountRequest.getHowDidYouHear());
		}
		if(StringUtils.isNotBlank(createFirmAccountRequest.getReferralCode())){
		providerRegistrationVO.setPromotionCode(createFirmAccountRequest.getReferralCode());
		}
		if(null!=createFirmAccountRequest.getBusinessFax()){
        providerRegistrationVO.setBusinessFax1(removeHyphenFromNumber(createFirmAccountRequest.getBusinessFax()));
		}
		// setting business address
		if(null!=createFirmAccountRequest.getBusinessAddress()){
			providerRegistrationVO.setBusinessStreet1(createFirmAccountRequest.getBusinessAddress().getBusinessStreet1());
			if(StringUtils.isNotBlank(createFirmAccountRequest.getBusinessAddress().getBusinessStreet2())){
			providerRegistrationVO.setBusinessStreet2(createFirmAccountRequest.getBusinessAddress().getBusinessStreet2());
			}
			providerRegistrationVO.setBusinessCity(createFirmAccountRequest.getBusinessAddress().getBusinessCity());
			providerRegistrationVO.setBusinessState(createFirmAccountRequest.getBusinessAddress().getBusinessState());
			providerRegistrationVO.setBusinessZip(createFirmAccountRequest.getBusinessAddress().getBusinessZip());
			if(StringUtils.isNotBlank(createFirmAccountRequest.getBusinessAddress().getBusinessAprt())){
			providerRegistrationVO.setBusinessAprt(createFirmAccountRequest.getBusinessAddress().getBusinessAprt());
			}
		}
		// setting mailing address
		if(null!=createFirmAccountRequest.getMailingAddress()){
			providerRegistrationVO.setMailingStreet1(createFirmAccountRequest.getMailingAddress().getmailingStreet1());
			if(StringUtils.isNotBlank(createFirmAccountRequest.getMailingAddress().getmailingStreet2())){
			providerRegistrationVO.setMailingStreet2(createFirmAccountRequest.getMailingAddress().getmailingStreet2());
			}
			providerRegistrationVO.setMailingCity(createFirmAccountRequest.getMailingAddress().getmailingCity());
			providerRegistrationVO.setMailingState(createFirmAccountRequest.getMailingAddress().getmailingState());
			providerRegistrationVO.setMailingZip(createFirmAccountRequest.getMailingAddress().getmailingZip());
			if(StringUtils.isNotBlank(createFirmAccountRequest.getMailingAddress().getmailingAprt())){
			providerRegistrationVO.setMailingAprt(createFirmAccountRequest.getMailingAddress().getmailingAprt());
			}
		}
		
		
		//Setting roles for primary resource
		if(null!=createFirmAccountRequest.getRoles() && null!=createFirmAccountRequest.getRoles().getRole() && !createFirmAccountRequest.getRoles().getRole().isEmpty())
		{
			List<String> role= new ArrayList<String>();
			role=createFirmAccountRequest.getRoles().getRole();
			
			if(role.contains("Owner/Principal")){
				providerRegistrationVO.setOwnerInd(ONE);
			}else{
				providerRegistrationVO.setOwnerInd(ZERO);
			}
			if(role.contains("Dispatcher/Scheduler")){
				providerRegistrationVO.setDispatchInd(ONE);
			}else{
				providerRegistrationVO.setDispatchInd(ZERO);
			}
			if(role.contains("Manager")){
				providerRegistrationVO.setManagerInd(ONE);
			}else{
				providerRegistrationVO.setManagerInd(ZERO);
			}
			if(role.contains("Service Provider")){
				providerRegistrationVO.setSproInd(ONE);
			}else{
				providerRegistrationVO.setSproInd(ZERO);
			}

			if(role.contains("Other")){
				providerRegistrationVO.setOtherInd(ONE);
			}else{
				providerRegistrationVO.setOtherInd(ZERO);
			}
		}
		mapWarrantyInformation(createFirmAccountRequest,providerRegistrationVO);
		mapInsuranceDetailsCreate(createFirmAccountRequest,providerRegistrationVO);
		mapLicenseInformationCreate(createFirmAccountRequest,providerRegistrationVO);		
		mapW9InformationCreate(createFirmAccountRequest,providerRegistrationVO);  
		
        return providerRegistrationVO;

	}
	
	public ProviderRegistrationVO mapUpdateFirmRequest(UpdateFirmAccountRequest updateFirmAccountRequest) throws Exception{
	
		ProviderRegistrationVO providerRegistrationVO =new ProviderRegistrationVO();
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getPrimaryIndustry()))
		{
			providerRegistrationVO.setPrimaryIndustry(updateFirmAccountRequest.getPrimaryIndustry());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessStructure()))
		{
			providerRegistrationVO.setBusStructure(updateFirmAccountRequest.getBusinessStructure());
		}
	
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessStartedDate()))
		{
			try {
				SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date issueParsedDate;
				issueParsedDate = issueDateformater.parse(updateFirmAccountRequest.getBusinessStartedDate());
				providerRegistrationVO.setBusStartDt(issueParsedDate);

			} catch (ParseException e) {
				LOGGER.info(" ParseException"+e);
				throw e;
			}
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getDunsNumber()))
		{
			providerRegistrationVO.setDunsNo(updateFirmAccountRequest.getDunsNumber());
		}
	
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getForeignOwnedPercentage()))
		{
			providerRegistrationVO.setIsForeignOwned("1");
			providerRegistrationVO.setForeignOwnedPct(updateFirmAccountRequest.getForeignOwnedPercentage());
			
			/*if(("0").equals(updateFirmAccountRequest.getForeignOwnedPercentage())){
				providerRegistrationVO.setIsForeignOwned("0");	
			}
			else{
				providerRegistrationVO.setIsForeignOwned("1");
			}*/
		}
		else
		{
		//	providerRegistrationVO.setIsForeignOwned("0");
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getCompanySize()))
		{
			providerRegistrationVO.setCompanySize(updateFirmAccountRequest.getCompanySize());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getAnnualSalesRevenue()))
		{
			providerRegistrationVO.setAnnualSalesRevenue(updateFirmAccountRequest.getAnnualSalesRevenue());
		}
		
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getWebsiteAddress()))
		{
			providerRegistrationVO.setWebsiteAddress(updateFirmAccountRequest.getWebsiteAddress());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessDesc()))
		{
			providerRegistrationVO.setDescription(updateFirmAccountRequest.getBusinessDesc());
		}
		
		//setting Job title
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getJobTitle()))
		{
			providerRegistrationVO.setJobTitle(updateFirmAccountRequest.getJobTitle());
		}
		
		//setting Email and Alternate Email
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getEmail()))
		{
			providerRegistrationVO.setEmail(updateFirmAccountRequest.getEmail());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getAltEmail()))
		{
			providerRegistrationVO.setAltEmail(updateFirmAccountRequest.getAltEmail());
		}
		
		// setting main business phone and extension 
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getMainBusiPhoneNo())){
		providerRegistrationVO.setMainBusiPhoneNo1(removeHyphenFromNumber(updateFirmAccountRequest.getMainBusiPhoneNo()));
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getMainBusinessExtn())){
			providerRegistrationVO.setMainBusinessExtn(updateFirmAccountRequest.getMainBusinessExtn());
		}
		
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessFax())){
		providerRegistrationVO.setBusinessFax1(removeHyphenFromNumber(updateFirmAccountRequest.getBusinessFax()));
		}
	
		// setting business address
		if(null!=updateFirmAccountRequest.getBusinessAddress()){
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessStreet1())){
		    providerRegistrationVO.setBusinessStreet1(updateFirmAccountRequest.getBusinessAddress().getBusinessStreet1());
		}	
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessStreet2())){
			providerRegistrationVO.setBusinessStreet2(updateFirmAccountRequest.getBusinessAddress().getBusinessStreet2());
		}	
	
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessCity())){
			providerRegistrationVO.setBusinessCity(updateFirmAccountRequest.getBusinessAddress().getBusinessCity());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessState())){
			  providerRegistrationVO.setBusinessState(updateFirmAccountRequest.getBusinessAddress().getBusinessState());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessState())){
			providerRegistrationVO.setBusinessZip(updateFirmAccountRequest.getBusinessAddress().getBusinessZip());
		}

		if(StringUtils.isNotBlank(updateFirmAccountRequest.getBusinessAddress().getBusinessAprt())){
			providerRegistrationVO.setBusinessAprt(updateFirmAccountRequest.getBusinessAddress().getBusinessAprt());
		}
		
		
		}
		// setting mailing address
		if(null!=updateFirmAccountRequest.getMailingAddress()){
			
			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingStreet1())){
				providerRegistrationVO.setMailingStreet1(updateFirmAccountRequest.getMailingAddress().getmailingStreet1());
			}	
			
			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingStreet2())){
				providerRegistrationVO.setMailingStreet2(updateFirmAccountRequest.getMailingAddress().getmailingStreet2());
			}
			

			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingStreet2())){
				providerRegistrationVO.setMailingCity(updateFirmAccountRequest.getMailingAddress().getmailingCity());
			}
		
			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingState())){
				providerRegistrationVO.setMailingState(updateFirmAccountRequest.getMailingAddress().getmailingState());
			}
		
			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingZip())){
				 providerRegistrationVO.setMailingZip(updateFirmAccountRequest.getMailingAddress().getmailingZip());
			}
		
			if(StringUtils.isNotBlank(updateFirmAccountRequest.getMailingAddress().getmailingAprt())){
				providerRegistrationVO.setMailingAprt(updateFirmAccountRequest.getMailingAddress().getmailingAprt());
			}
		}
		
		//Setting roles for primary resource
		if(null!=updateFirmAccountRequest.getRoleWithinCom() && null!=updateFirmAccountRequest.getRoleWithinCom().getRole() && !updateFirmAccountRequest.getRoleWithinCom().getRole().isEmpty())
		{
			List<String> role= new ArrayList<String>();
			role=updateFirmAccountRequest.getRoleWithinCom().getRole();
			
			if(role.contains("Owner/Principal"))
			{
				providerRegistrationVO.setOwnerInd(ONE);
			}
		
			
			if(role.contains("Dispatcher/Scheduler"))
			{
				providerRegistrationVO.setDispatchInd(ONE);
			}
			
			if(role.contains("Manager"))
			{
				providerRegistrationVO.setManagerInd(ONE);
			}
		
			
			if(role.contains("Service Provider"))
			{
				providerRegistrationVO.setSproInd(ONE);
			}
			
			
			if(role.contains("Other"))
			{
				providerRegistrationVO.setOtherInd(ONE);
			}
			
		}
		
		mapWarrantyInformationUpdate(updateFirmAccountRequest,providerRegistrationVO);
		
		mapLicenseInformationUpdate(updateFirmAccountRequest,providerRegistrationVO);
		
		mapInsuranceDetailsUpdate(updateFirmAccountRequest,providerRegistrationVO);
		
		mapW9InformationUpdate(updateFirmAccountRequest,providerRegistrationVO);
		
		
		return providerRegistrationVO;
		
	}
	
	
	public void mapInsuranceDetailsUpdate(UpdateFirmAccountRequest updateFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO) throws ParseException{	   
		   //For mapping general liability insurance details
			
		if(null!=updateFirmAccountRequest.getInsuranceDetails()){
			providerRegistrationVO.setInsurancePresent(true);
		}
		
			if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()
					&&  (ONE).equals(updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()) ){
		   mapGeneralLiabilityUpdate(updateFirmAccountRequest,providerRegistrationVO);
		   providerRegistrationVO.setGeneralLInd(true);
			}
			else if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()
					&&  (ZERO).equals(updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()) ){
		   providerRegistrationVO.setGeneralLInd(false);
			}
			 //For mapping vehicle liability insurance details
			if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()
					&&  (ONE).equals(updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()) ){
		   mapVehicleLiabilityUpdate(updateFirmAccountRequest,providerRegistrationVO);
		   providerRegistrationVO.setVehicleLInd(true);

			}
			else if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()
					&&  (ZERO).equals(updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()) ){
		   providerRegistrationVO.setVehicleLInd(false);

			}
		   
		   //For mapping workman compensation insurance details
			if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()
					&&  (ONE).equals(updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()) ){
		   mapWorkmanCompensationUpdate(updateFirmAccountRequest, providerRegistrationVO);
		   providerRegistrationVO.setWorkmanCInd(true);
			}
			else if(null!=updateFirmAccountRequest.getInsuranceDetails() && null!=updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()
					&&  (ZERO).equals(updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()) ){
		   providerRegistrationVO.setWorkmanCInd(false);
			}
	}
	
	public void mapInsuranceDetailsCreate(CreateFirmAccountRequest createFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO){
		try {	   
		   //For mapping general liability insurance details
			
			if(null!=createFirmAccountRequest.getInsuranceDetails()){
				providerRegistrationVO.setInsurancePresent(true);
			}
			
			if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()
					&&  (ONE).equals(createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()) ){
		   mapGeneralLiabilityCreate(createFirmAccountRequest,providerRegistrationVO);
		   providerRegistrationVO.setGeneralLInd(true);
			} 
			else if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()
					&&  (ZERO).equals(createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceInd()) ){
		   providerRegistrationVO.setGeneralLInd(false);
			} 
			
			
		   //For mapping vehicle liability insurance details
			if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()
					&&  (ONE).equals(createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()) ){
		   mapVehicleLiabilityCreate(createFirmAccountRequest,providerRegistrationVO);
		   providerRegistrationVO.setVehicleLInd(true);
			}
			else if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()
					&&  (ZERO).equals(createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityInd()) ){
		   providerRegistrationVO.setVehicleLInd(false);
			}
			
			
		   //For mapping workman compensation insurance details
			if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()
					&&  (ONE).equals(createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()) ){
		   mapWorkmanCompensationCreate(createFirmAccountRequest, providerRegistrationVO);
		   providerRegistrationVO.setWorkmanCInd(true);
			}
			else if(null!=createFirmAccountRequest.getInsuranceDetails() && null!=createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()
					&&  (ZERO).equals(createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceInd()) ){
		   providerRegistrationVO.setWorkmanCInd(false);
			}
		   
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.info("ParseException in mapInsuranceDetails method of ProviderMapper.java class",e);
		}
		
	}


	private void mapGeneralLiabilityUpdate(
			UpdateFirmAccountRequest updateFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
		
		
		providerRegistrationVO.setGeneralLiabilityAmount(updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount());
		if(null!=updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityDetails())
		{
			 CredentialProfile generalLiability = new CredentialProfile();
			   generalLiability.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
			   generalLiability.setCredentialCategoryId(OrderConstants.GL_CREDENTIAL_CATEGORY_ID);
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getCarrierName()))
			   {
				   generalLiability.setCredentialSource(updateFirmAccountRequest
							.getInsuranceDetails().getGeneralLiabilityDetails().getCarrierName().trim());
			   }
			   
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getAgencyName()))
			   {
				   generalLiability.setCredentialName(updateFirmAccountRequest
							.getInsuranceDetails().getGeneralLiabilityDetails().getAgencyName().trim());
			   }
			   
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getPolicyNumber()))
			   {
				   generalLiability.setCredentialNumber(updateFirmAccountRequest
							.getInsuranceDetails().getGeneralLiabilityDetails().
						getPolicyNumber().trim());
			   }
			
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getAgencyState()))
			   {
				   generalLiability.setCredentialState(updateFirmAccountRequest
							.getInsuranceDetails().getGeneralLiabilityDetails().
						getAgencyState().trim());
			   }
			 
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getIssueDate()))
			   {
				   String issueDate = "";
			   
			   String issDate = updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getIssueDate();
			   
			   if (issDate != null)
					{
				   	issueDate = issDate;
				   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
				   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
				    generalLiability.setCredentialIssueDate(issueDateResult);
					}
			   } 
			   
			   if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getExpirationDate()))
			   { 
			   String expirationDate = "";
			   String expDate = updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails().getExpirationDate();
			   
			   if (expDate != null)
			   {	expirationDate = expDate;
			        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date expirationParsedDate;
					
						expirationParsedDate = expirationDateformater
								.parse(expirationDate);
					
			        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
			        
			        generalLiability.setCredentialExpirationDate(expirationDateResult); 
			   }
			}	
			   
			 if(StringUtils.isNotBlank(updateFirmAccountRequest
						.getInsuranceDetails().getGeneralLiabilityDetails()
					.getAgencyCounty()))
			   {
				   generalLiability.setCredentialCounty(updateFirmAccountRequest
							.getInsuranceDetails().getGeneralLiabilityDetails()
						.getAgencyCounty().trim());
			   }
			 
			 if(StringUtils.isNotBlank(updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount()))
			  
			{
				 String sRetVal = convertStringToDollar(updateFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount());
			   generalLiability.setCredAmount(sRetVal);
		    }
			   
				   providerRegistrationVO.setGeneralLiability(generalLiability);
		}
	  
	}
	
	
	public void  mapW9InformationUpdate(UpdateFirmAccountRequest updateFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO) throws ParseException{

		LocationVO address =new LocationVO();
		W9RegistrationVO w9RegistrationVO =new W9RegistrationVO();
		
		if(null!=updateFirmAccountRequest.getW9Information())
		{
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getLegalBusinessName())){
			w9RegistrationVO.setLegalBusinessName(updateFirmAccountRequest.getW9Information().getLegalBusinessName());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getBusinessName())){
			w9RegistrationVO.setDoingBusinessAsName(updateFirmAccountRequest.getW9Information().getBusinessName());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getTaxStatus()))
		{
			providerRegistrationVO.setTaxStatus(updateFirmAccountRequest.getW9Information().getTaxStatus());
			LookupVO taxStatus =new LookupVO();
			w9RegistrationVO.setTaxStatus(taxStatus);
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getAddress1())){
			address.setStreet1(updateFirmAccountRequest.getW9Information().getAddress1());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getAddress2())){
			address.setStreet2(updateFirmAccountRequest.getW9Information().getAddress2());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getApartNo())){
			address.setAptNo(updateFirmAccountRequest.getW9Information().getApartNo());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getCity())){
			address.setCity(updateFirmAccountRequest.getW9Information().getCity());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getState())){
			address.setState(updateFirmAccountRequest.getW9Information().getState());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getZip())){
			address.setZip(updateFirmAccountRequest.getW9Information().getZip());
		}
		
		w9RegistrationVO.setAddress(address);
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getSecurityNoType())){
				if(updateFirmAccountRequest.getW9Information().getSecurityNoType().equals("EIN"))
				{
					w9RegistrationVO.setTaxPayerTypeId(1);
				}
				else if(updateFirmAccountRequest.getW9Information().getSecurityNoType().equals("SSN"))
				{
					w9RegistrationVO.setTaxPayerTypeId(2);
				}
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getTaxPayerNo())){
			w9RegistrationVO.setEin(updateFirmAccountRequest.getW9Information().getTaxPayerNo());
		}
		

		if(null!=updateFirmAccountRequest.getW9Information().getTaxExemptInd() && updateFirmAccountRequest.getW9Information().getTaxExemptInd().equals(ONE)){
			w9RegistrationVO.setIsTaxExempt(true);
		}
		if(null!=updateFirmAccountRequest.getW9Information().getTaxExemptInd() && updateFirmAccountRequest.getW9Information().getTaxExemptInd().equals(ZERO)){
			w9RegistrationVO.setIsTaxExempt(false);
		}
		if(null!=updateFirmAccountRequest.getW9Information().getLegalTaxCheck() && updateFirmAccountRequest.getW9Information().getLegalTaxCheck().equals(ONE)){
			w9RegistrationVO.setIsPenaltyIndicatiorCertified(true);
		}
		if(null!=updateFirmAccountRequest.getW9Information().getLegalTaxCheck() && updateFirmAccountRequest.getW9Information().getLegalTaxCheck().equals(ZERO)){
			w9RegistrationVO.setIsPenaltyIndicatiorCertified(false);
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getSecurityNoType()) && updateFirmAccountRequest.getW9Information().getSecurityNoType().equals("SSN") && StringUtils.isNotBlank(updateFirmAccountRequest.getW9Information().getDateOfBirth())){
			
			  String w9Dob = "";
			   String w9DateOfBirth = updateFirmAccountRequest.getW9Information().getDateOfBirth();
			   if (w9DateOfBirth != null)
			   {	w9Dob = w9DateOfBirth;
			        SimpleDateFormat w9Dateformater = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date w9DateParsedDate;
					
						w9DateParsedDate = w9Dateformater
								.parse(w9Dob);
						
						w9RegistrationVO.setDateOfBirth(w9DateParsedDate);
			   }	
		}
		
		providerRegistrationVO.setW9RegistrationVO(w9RegistrationVO);
		}
		
	}
	
	
	
	
	public void  mapW9InformationCreate(CreateFirmAccountRequest createFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO) {

		LocationVO address =new LocationVO();
		W9RegistrationVO w9RegistrationVO =new W9RegistrationVO();
		
		if(null!=createFirmAccountRequest.getW9Information())
		{
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getLegalBusinessName())){
			w9RegistrationVO.setLegalBusinessName(createFirmAccountRequest.getW9Information().getLegalBusinessName());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getBusinessName())){
			w9RegistrationVO.setDoingBusinessAsName(createFirmAccountRequest.getW9Information().getBusinessName());					
		}	
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getTaxStatus())){
			providerRegistrationVO.setTaxStatus(createFirmAccountRequest.getW9Information().getTaxStatus());
			LookupVO taxStatus=new LookupVO();
			w9RegistrationVO.setTaxStatus(taxStatus);			
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getAddress1())){
			address.setStreet1(createFirmAccountRequest.getW9Information().getAddress1());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getAddress2())){
			address.setStreet2(createFirmAccountRequest.getW9Information().getAddress2());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getApartNo())){
			address.setAptNo(createFirmAccountRequest.getW9Information().getApartNo());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getCity())){
			address.setCity(createFirmAccountRequest.getW9Information().getCity());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getState())){
			address.setState(createFirmAccountRequest.getW9Information().getState());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getZip())){
			address.setZip(createFirmAccountRequest.getW9Information().getZip());
		}
		
		w9RegistrationVO.setAddress(address);
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getSecurityNoType())){
				if(createFirmAccountRequest.getW9Information().getSecurityNoType().equals("EIN"))
				{
					w9RegistrationVO.setTaxPayerTypeId(1);
				}
				else if(createFirmAccountRequest.getW9Information().getSecurityNoType().equals("SSN"))
				{
					w9RegistrationVO.setTaxPayerTypeId(2);
				}
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getTaxPayerNo())){
			w9RegistrationVO.setEin(createFirmAccountRequest.getW9Information().getTaxPayerNo());
		}
		

		if(null!=createFirmAccountRequest.getW9Information().getTaxExemptInd() && createFirmAccountRequest.getW9Information().getTaxExemptInd().equals(ONE)){
			w9RegistrationVO.setIsTaxExempt(true);
		}
		if(null!=createFirmAccountRequest.getW9Information().getTaxExemptInd() && createFirmAccountRequest.getW9Information().getTaxExemptInd().equals(ZERO)){
			w9RegistrationVO.setIsTaxExempt(false);
		}
		if(null!=createFirmAccountRequest.getW9Information().getLegalTaxCheck() && createFirmAccountRequest.getW9Information().getLegalTaxCheck().equals(ONE)){
			w9RegistrationVO.setIsPenaltyIndicatiorCertified(true);
		}
		if(null!=createFirmAccountRequest.getW9Information().getLegalTaxCheck() && createFirmAccountRequest.getW9Information().getLegalTaxCheck().equals(ZERO)){
			w9RegistrationVO.setIsPenaltyIndicatiorCertified(false);
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest.getW9Information().getSecurityNoType()) &&
				createFirmAccountRequest.getW9Information().getSecurityNoType().equals("SSN") && StringUtils.isNotBlank(createFirmAccountRequest.getW9Information()
						.getDateOfBirth())){
			
			try{
			  String w9Dob = "";
			   String w9DateOfBirth = createFirmAccountRequest.getW9Information().getDateOfBirth();
			   if (w9DateOfBirth != null)
			   {	w9Dob = w9DateOfBirth;
			        SimpleDateFormat w9Dateformater = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date w9DateParsedDate;
					
						w9DateParsedDate = w9Dateformater
								.parse(w9Dob);
						
						w9RegistrationVO.setDateOfBirth(w9DateParsedDate);
			   }	
			}catch(ParseException e){
				LOGGER.info(""+e);
			}
		}
		
		providerRegistrationVO.setW9RegistrationVO(w9RegistrationVO);
		}
		
	}
	private void mapGeneralLiabilityCreate(
			CreateFirmAccountRequest createFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
		
		providerRegistrationVO.setGeneralLiabilityAmount(createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount());
		
		
		if(null!=createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails()){
		CredentialProfile generalLiability =new CredentialProfile();

		  // generalLiability.setVendorCredId(Integer.parseInt((createFirmAccountRequest
			//	.getInsuranceDetails().getGeneralLiabilityDetails().getVendorCredentialId())));
		   generalLiability.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
		   generalLiability.setCredentialCategoryId(OrderConstants.GL_CREDENTIAL_CATEGORY_ID);
		   
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().getCarrierName())){
		   generalLiability.setCredentialSource(createFirmAccountRequest
				.getInsuranceDetails().getGeneralLiabilityDetails().getCarrierName().trim());
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().getAgencyName())){
		   generalLiability.setCredentialName(createFirmAccountRequest
				.getInsuranceDetails().getGeneralLiabilityDetails().getAgencyName().trim());
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().
				getPolicyNumber())){	   
		   generalLiability.setCredentialNumber(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().
				getPolicyNumber().trim());
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().
				getAgencyState())){
		   generalLiability.setCredentialState(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().
				getAgencyState().trim());
		   }
		
		   String issueDate = "";
		   String issDate = createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().getIssueDate();
		   
		   if (issDate != null)
				{
			   	issueDate = issDate;
			   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
			   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
			   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
			    generalLiability.setCredentialIssueDate(issueDateResult);
				}
		   
		   String expirationDate = "";
		   String expDate = createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails().getExpirationDate();
		   
		   if (expDate != null)
		   {	expirationDate = expDate;
		        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date expirationParsedDate;
				
					expirationParsedDate = expirationDateformater
							.parse(expirationDate);
				
		        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
		        
		        generalLiability.setCredentialExpirationDate(expirationDateResult); 
		   }
			
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails()
				.getAgencyCounty())){
		   generalLiability.setCredentialCounty(createFirmAccountRequest
					.getInsuranceDetails().getGeneralLiabilityDetails()
				.getAgencyCounty().trim());
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount())){
		   String sRetVal = convertStringToDollar(createFirmAccountRequest.getInsuranceDetails().getGeneralLiabilityInsuranceAmount());
		   generalLiability.setCredAmount(sRetVal);
		   }
		   
		   providerRegistrationVO.setGeneralLiability(generalLiability);
		}
	}
	
	private void mapVehicleLiabilityUpdate(
			UpdateFirmAccountRequest updateFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
		
		
		providerRegistrationVO.setVehicleLiabilityAmount(updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount()); 

		

		if(null!=updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityDetails())
		{
			CredentialProfile vehicleLiability =new CredentialProfile();

		vehicleLiability.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
		vehicleLiability.setCredentialCategoryId(OrderConstants.AL_CREDENTIAL_CATEGORY_ID);
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getCarrierName()))
		{
			vehicleLiability.setCredentialSource(updateFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().getCarrierName().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyName()))
		{
			vehicleLiability.setCredentialName(updateFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyName().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getPolicyNumber()))
		{
			vehicleLiability.setCredentialNumber(updateFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().
				getPolicyNumber().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyState()))
		{
			vehicleLiability.setCredentialState(updateFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().
				getAgencyState().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getIssueDate()))
		{
			 String issueDate = "";
			   String issDate = updateFirmAccountRequest
						.getInsuranceDetails().getVehicleLiabilityDetails().getIssueDate();
			   
			   if (issDate != null)
					{
				   	issueDate = issDate;
				   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
				   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
				   	vehicleLiability.setCredentialIssueDate(issueDateResult);
					}	
		}
		
	
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getExpirationDate()))
		{
			 String expirationDate = "";
			   String expDate = updateFirmAccountRequest
						.getInsuranceDetails().getVehicleLiabilityDetails().getExpirationDate();
			   
			   if (expDate != null)
			   {	expirationDate = expDate;
			        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date expirationParsedDate;
					
						expirationParsedDate = expirationDateformater
								.parse(expirationDate);
					
			        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
			        
			        vehicleLiability.setCredentialExpirationDate(expirationDateResult); 
			   }	
		}
		  
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyCounty()))
		{
			 vehicleLiability.setCredentialCounty(updateFirmAccountRequest
						.getInsuranceDetails().getVehicleLiabilityDetails()
					.getAgencyCounty().trim());
		}
		  
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount()))
		{
			 String sRetVal = convertStringToDollar(updateFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount());
			   vehicleLiability.setCredAmount(sRetVal);
		}
		  
  
			   providerRegistrationVO.setVehicleLiability(vehicleLiability);
		}	   
	}
	
	
	private void mapVehicleLiabilityCreate(
			CreateFirmAccountRequest createFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
				
		providerRegistrationVO.setVehicleLiabilityAmount(createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount()); 

		if(null!=createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails()){
		
		CredentialProfile vehicleLiability =new CredentialProfile();

		//vehicleLiability.setVendorCredId(Integer.parseInt((createFirmAccountRequest
		//			.getInsuranceDetails().getVehicleLiabilityDetails().getVendorCredentialId())));
		vehicleLiability.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
		vehicleLiability.setCredentialCategoryId(OrderConstants.AL_CREDENTIAL_CATEGORY_ID);
		
		if(StringUtils.isNotBlank(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getCarrierName()))
		{
		vehicleLiability.setCredentialSource(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getCarrierName().trim());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyName()))
		{
		vehicleLiability.setCredentialName(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().getAgencyName().trim());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().
			getPolicyNumber()))
		{
		vehicleLiability.setCredentialNumber(createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().
				getPolicyNumber().trim());
		}
		
		if(StringUtils.isNotBlank(createFirmAccountRequest
				.getInsuranceDetails().getVehicleLiabilityDetails().
			getAgencyState()))
		{
		vehicleLiability.setCredentialState(createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().
				getAgencyState().trim());
		}
		
		   String issueDate = "";
		   String issDate = createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().getIssueDate();
		   
		   if (issDate != null)
				{
			   	issueDate = issDate;
			   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
			   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
			   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
			   	vehicleLiability.setCredentialIssueDate(issueDateResult);
				}
		   
		   String expirationDate = "";
		   String expDate = createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails().getExpirationDate();
		   
		   if (expDate != null)
		   {	expirationDate = expDate;
		        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date expirationParsedDate;
				
					expirationParsedDate = expirationDateformater
							.parse(expirationDate);
				
		        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
		        
		        vehicleLiability.setCredentialExpirationDate(expirationDateResult); 
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails()
				.getAgencyCounty()))
			{
		   vehicleLiability.setCredentialCounty(createFirmAccountRequest
					.getInsuranceDetails().getVehicleLiabilityDetails()
				.getAgencyCounty().trim());
			}
		   if(StringUtils.isNotBlank(createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount())){
		   String sRetVal = convertStringToDollar(createFirmAccountRequest.getInsuranceDetails().getVehicleLiabilityAmount());
		   vehicleLiability.setCredAmount(sRetVal);
		   }
		   providerRegistrationVO.setVehicleLiability(vehicleLiability);
		}
	}
	
	private void mapWorkmanCompensationUpdate(
			UpdateFirmAccountRequest updateFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
		
		providerRegistrationVO.setWorkmanCompensationAmount(updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount()); 

		if(null!=updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationDetails())
		{
			CredentialProfile workmanCompensation =new CredentialProfile();

		workmanCompensation.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
		workmanCompensation.setCredentialCategoryId(OrderConstants.WC_CREDENTIAL_CATEGORY_ID);
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getCarrierName()))
		{
			workmanCompensation.setCredentialSource(updateFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getCarrierName().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getAgencyName()))
		{
			workmanCompensation.setCredentialName(updateFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getAgencyName().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getPolicyNumber()))
		{
			workmanCompensation.setCredentialNumber(updateFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().
				getPolicyNumber().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getAgencyState()))
		{
			workmanCompensation.setCredentialState(updateFirmAccountRequest
						.getInsuranceDetails().getWorkmanCompensationDetails().
					getAgencyState().trim());
		}
		

		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getIssueDate()))
		{
			 String issueDate = "";
			   String issDate = updateFirmAccountRequest
						.getInsuranceDetails().getWorkmanCompensationDetails().getIssueDate();
			   
			   if (issDate != null)
					{
				   	issueDate = issDate;
				   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
				   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
				   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
				   	workmanCompensation.setCredentialIssueDate(issueDateResult);
					}
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getExpirationDate()))
		{
			 String expirationDate = "";
			   String expDate = updateFirmAccountRequest
						.getInsuranceDetails().getWorkmanCompensationDetails().getExpirationDate();
			   
			   if (expDate != null)
			   {	expirationDate = expDate;
			        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
			        java.util.Date expirationParsedDate;
					
						expirationParsedDate = expirationDateformater
								.parse(expirationDate);
					
			        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
			        
			        workmanCompensation.setCredentialExpirationDate(expirationDateResult); 
			   }
		}
		  
		if(StringUtils.isNotBlank(updateFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails()
			.getAgencyCounty()))
		{
			 workmanCompensation.setCredentialCounty(updateFirmAccountRequest
						.getInsuranceDetails().getWorkmanCompensationDetails()
					.getAgencyCounty().trim());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount()))
		{
			  String sRetVal = convertStringToDollar(updateFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount());
			   workmanCompensation.setCredAmount(sRetVal);
		}
		  
			   providerRegistrationVO.setWorkmanCompensation(workmanCompensation);	 
		}		   
	}
	
	
	private void mapWorkmanCompensationCreate(
			CreateFirmAccountRequest createFirmAccountRequest,
			ProviderRegistrationVO providerRegistrationVO)
			throws ParseException {
		
		providerRegistrationVO.setWorkmanCompensationAmount(createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount()); 

		if(null!=createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationDetails()){
		CredentialProfile workmanCompensation =new CredentialProfile();

		//workmanCompensation.setVendorCredId(Integer.parseInt((createFirmAccountRequest
		//		.getInsuranceDetails().getWorkmanCompensationDetails().getVendorCredentialId())));
		workmanCompensation.setCredentialTypeId(OrderConstants.CREDENTIAL_TYPE_ID);
		workmanCompensation.setCredentialCategoryId(OrderConstants.WC_CREDENTIAL_CATEGORY_ID);
		
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getCarrierName()))
			{
		workmanCompensation.setCredentialSource(createFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getCarrierName().trim());
			}
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getAgencyName()))
			{
		workmanCompensation.setCredentialName(createFirmAccountRequest
				.getInsuranceDetails().getWorkmanCompensationDetails().getAgencyName().trim());
			}
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().
				getPolicyNumber()))
			{	   
		workmanCompensation.setCredentialNumber(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().
				getPolicyNumber().trim());
			}
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().
				getAgencyState()))
			{
		workmanCompensation.setCredentialState(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().
				getAgencyState().trim());
			}
		
		   String issueDate = "";
		   String issDate = createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getIssueDate();
		   
		   if (issDate != null)
				{
			   	issueDate = issDate;
			   	SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
			   	java.util.Date issueParsedDate = issueDateformater.parse(issueDate);
			   	Timestamp issueDateResult = new Timestamp(issueParsedDate.getTime());
			   	workmanCompensation.setCredentialIssueDate(issueDateResult);
				}
		   
		   String expirationDate = "";
		   String expDate = createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails().getExpirationDate();
		   
		   if (expDate != null)
		   {	expirationDate = expDate;
		        SimpleDateFormat expirationDateformater = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date expirationParsedDate;
				
					expirationParsedDate = expirationDateformater
							.parse(expirationDate);
				
		        Timestamp expirationDateResult = new Timestamp(expirationParsedDate.getTime());
		        
		        workmanCompensation.setCredentialExpirationDate(expirationDateResult); 
		   }
		   if(StringUtils.isNotBlank(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails()
				.getAgencyCounty()))
			{	
		   workmanCompensation.setCredentialCounty(createFirmAccountRequest
					.getInsuranceDetails().getWorkmanCompensationDetails()
				.getAgencyCounty().trim());
			}
		   if(StringUtils.isNotBlank(createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount())){
		   String sRetVal = convertStringToDollar(createFirmAccountRequest.getInsuranceDetails().getWorkmanCompensationInsuranceAmount());
		   workmanCompensation.setCredAmount(sRetVal);
		   }
		   providerRegistrationVO.setWorkmanCompensation(workmanCompensation);
		}
	}
	
	public void  mapWarrantyInformation(CreateFirmAccountRequest createFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO){

		WarrantyVO warrantyVO=new  WarrantyVO();
		// charge Indicator for project estimate
		if(null!=createFirmAccountRequest.getProjectEstimatesChargeInd()){
			warrantyVO.setFreeEstimate(createFirmAccountRequest.getProjectEstimatesChargeInd().toString());
		}
		
		if(null!=createFirmAccountRequest.getWarrantyOnLabor()){
			
		warrantyVO.setWarrOfferedLabor("1");
		// warranty labor period
		warrantyVO.setWarrPeriodLabor(createFirmAccountRequest.getWarrantyOnLabor());
		}else
		{
			warrantyVO.setWarrOfferedLabor("0");
		}
			
		if(null!=createFirmAccountRequest.getWarrantyOnParts()){
		warrantyVO.setWarrOfferedParts("1");	
		// warranty parts period
		warrantyVO.setWarrPeriodParts(createFirmAccountRequest.getWarrantyOnParts());
		}else{
			warrantyVO.setWarrOfferedParts("0");
		}
		//implement drug test policy
		if(null!=createFirmAccountRequest.getDrugTestingPolicyInd() && createFirmAccountRequest.getDrugTestingPolicyInd().equals(ZERO)){
			warrantyVO.setConductDrugTest(createFirmAccountRequest.getDrugTestingPolicyInd().toString());
		//consider the implement drug test policy
		if(null!=createFirmAccountRequest.getDrugTestingPolicyRequired()){
			warrantyVO.setConsiderDrugTest(createFirmAccountRequest.getDrugTestingPolicyRequired().toString());
		}
		}else if(null!=createFirmAccountRequest.getDrugTestingPolicyInd() && createFirmAccountRequest.getDrugTestingPolicyInd().equals(ONE))
			{
			warrantyVO.setConductDrugTest(createFirmAccountRequest.getDrugTestingPolicyInd().toString());
			}
		
		//has ethic policy
		if(null!=createFirmAccountRequest.getWorkEnvironmentPolicyInd() && createFirmAccountRequest.getWorkEnvironmentPolicyInd().equals(ZERO)){
			warrantyVO.setHasEthicsPolicy(createFirmAccountRequest.getWorkEnvironmentPolicyInd().toString());
		//consider ethic policy
		if(null!=createFirmAccountRequest.getWorkEnvironmentPolicyRequired()){
			warrantyVO.setConsiderEthicPolicy(createFirmAccountRequest.getWorkEnvironmentPolicyRequired().toString());
		}
		}else if(null!=createFirmAccountRequest.getWorkEnvironmentPolicyInd() && createFirmAccountRequest.getWorkEnvironmentPolicyInd().equals(ONE))
		{
			warrantyVO.setHasEthicsPolicy(createFirmAccountRequest.getWorkEnvironmentPolicyInd().toString());
		}
		
		//require US doc
		if(null!=createFirmAccountRequest.getEmployeeCitizenShipProofInd() && createFirmAccountRequest.getEmployeeCitizenShipProofInd().equals(ZERO)){
			warrantyVO.setRequireUsDoc(createFirmAccountRequest.getEmployeeCitizenShipProofInd().toString());
		// consider impl policy
		if(null!=createFirmAccountRequest.getEmployeeCitizenShipProofRequired()){
			warrantyVO.setConsiderImplPolicy(createFirmAccountRequest.getEmployeeCitizenShipProofRequired().toString());
		}
		}else if(null!=createFirmAccountRequest.getEmployeeCitizenShipProofInd() && createFirmAccountRequest.getEmployeeCitizenShipProofInd().equals(ONE))
		{
			warrantyVO.setRequireUsDoc(createFirmAccountRequest.getEmployeeCitizenShipProofInd().toString());

		}
		
		// require badge
		if(null!=createFirmAccountRequest.getCrewWearBadgesInd() && createFirmAccountRequest.getCrewWearBadgesInd().equals(ZERO)){
			warrantyVO.setRequireBadge(createFirmAccountRequest.getCrewWearBadgesInd().toString());
		//consider badge
		if(null!=createFirmAccountRequest.getCrewWearBadgesRequired()){
			warrantyVO.setConsiderBadge(createFirmAccountRequest.getCrewWearBadgesRequired().toString());
			}
		}else if(null!=createFirmAccountRequest.getCrewWearBadgesInd() && createFirmAccountRequest.getCrewWearBadgesInd().equals(ONE))
		{
			warrantyVO.setRequireBadge(createFirmAccountRequest.getCrewWearBadgesInd().toString());
		}
		providerRegistrationVO.setWarrantyVO(warrantyVO);
	}
	
	
	public void  mapWarrantyInformationUpdate(UpdateFirmAccountRequest updateFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO){

		WarrantyVO warrantyVO=new  WarrantyVO();
		// charge Indicator for project estimate
		if(null!=updateFirmAccountRequest.getProjectEstimatesChargeInd()){
			warrantyVO.setFreeEstimate(updateFirmAccountRequest.getProjectEstimatesChargeInd().toString());
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getWarrantyOnLabor())){
			warrantyVO.setWarrOfferedLabor("1");
			// warranty labor period
			warrantyVO.setWarrPeriodLabor(updateFirmAccountRequest.getWarrantyOnLabor());
		}else
		{
			warrantyVO.setWarrOfferedLabor("0");
		}
		
		if(StringUtils.isNotBlank(updateFirmAccountRequest.getWarrantyOnParts())){
		warrantyVO.setWarrOfferedParts("1");	
		// warranty parts period
		warrantyVO.setWarrPeriodParts(updateFirmAccountRequest.getWarrantyOnParts());
		}else{
			warrantyVO.setWarrOfferedParts("0");
		}
		
		//implement drug test policy
		if(null!=updateFirmAccountRequest.getDrugTestingPolicyInd() && updateFirmAccountRequest.getDrugTestingPolicyInd().equals(ZERO)){
			warrantyVO.setConductDrugTest(updateFirmAccountRequest.getDrugTestingPolicyInd().toString());
		//consider the implement drug test policy
		if(null!=updateFirmAccountRequest.getDrugTestingPolicyRequired()){
			warrantyVO.setConsiderDrugTest(updateFirmAccountRequest.getDrugTestingPolicyRequired().toString());
			}
		}else if(null!=updateFirmAccountRequest.getDrugTestingPolicyInd() && updateFirmAccountRequest.getDrugTestingPolicyInd().equals(ONE))
			{
			warrantyVO.setConductDrugTest(updateFirmAccountRequest.getDrugTestingPolicyInd().toString());
		}
		//has ethic policy
		if(null!=updateFirmAccountRequest.getWorkEnvironmentPolicyInd() && updateFirmAccountRequest.getWorkEnvironmentPolicyInd().equals(ZERO)){
			warrantyVO.setHasEthicsPolicy(updateFirmAccountRequest.getWorkEnvironmentPolicyInd().toString());
		//consider ethic policy
		if(null!=updateFirmAccountRequest.getWorkEnvironmentPolicyRequired()){
			warrantyVO.setConsiderEthicPolicy(updateFirmAccountRequest.getWorkEnvironmentPolicyRequired().toString());
			}
		}else if(null!=updateFirmAccountRequest.getWorkEnvironmentPolicyInd() && updateFirmAccountRequest.getWorkEnvironmentPolicyInd().equals(ONE))
		{
			warrantyVO.setHasEthicsPolicy(updateFirmAccountRequest.getWorkEnvironmentPolicyInd().toString());
		}
		//require US doc
		if(null!=updateFirmAccountRequest.getEmployeeCitizenShipProofInd() && updateFirmAccountRequest.getEmployeeCitizenShipProofInd().equals(ZERO)){
			warrantyVO.setRequireUsDoc(updateFirmAccountRequest.getEmployeeCitizenShipProofInd().toString());
		// consider impl policy
		if(null!=updateFirmAccountRequest.getEmployeeCitizenShipProofRequired()){
			warrantyVO.setConsiderImplPolicy(updateFirmAccountRequest.getEmployeeCitizenShipProofRequired().toString());
			}
		}else if(null!=updateFirmAccountRequest.getEmployeeCitizenShipProofInd() && updateFirmAccountRequest.getEmployeeCitizenShipProofInd().equals(ONE))
		{
			warrantyVO.setRequireUsDoc(updateFirmAccountRequest.getEmployeeCitizenShipProofInd().toString());
		}
		// require badge
		if(null!=updateFirmAccountRequest.getCrewWearBadgesInd() && updateFirmAccountRequest.getCrewWearBadgesInd().equals(ZERO)){
			warrantyVO.setRequireBadge(updateFirmAccountRequest.getCrewWearBadgesInd().toString());
		//consider badge
		if(null!=updateFirmAccountRequest.getCrewWearBadgesRequired()){
			warrantyVO.setConsiderBadge(updateFirmAccountRequest.getCrewWearBadgesRequired().toString());
			}
		}else if(null!=updateFirmAccountRequest.getCrewWearBadgesInd() && updateFirmAccountRequest.getCrewWearBadgesInd().equals(ONE))
		{
			warrantyVO.setRequireBadge(updateFirmAccountRequest.getCrewWearBadgesInd().toString());
		}
		providerRegistrationVO.setWarrantyVO(warrantyVO);
	}
	
public void  mapLicenseInformationCreate(CreateFirmAccountRequest createFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO) throws Exception{
		
		
	if(null!=createFirmAccountRequest.getLicenseAndCertifications()){
		providerRegistrationVO.setLicensePresent(true);
	}
	
	
	if(null!=createFirmAccountRequest.getLicenseAndCertifications() && null!=createFirmAccountRequest.getLicenseAndCertifications().getLicenseCertificationInd()
			&& (ZERO).equals(createFirmAccountRequest.getLicenseAndCertifications().getLicenseCertificationInd())){
		providerRegistrationVO.setLicenseNotNeeded(true);
	}
	
	
		if(null!=createFirmAccountRequest.getLicenseAndCertifications() && null!=createFirmAccountRequest.getLicenseAndCertifications().getLicenseCertificationInd()
				&& (ONE).equals(createFirmAccountRequest.getLicenseAndCertifications().getLicenseCertificationInd())){
			
			if(null!= createFirmAccountRequest.getLicenseAndCertifications().getCredentials() && 
					null!=createFirmAccountRequest.getLicenseAndCertifications().getCredentials().getCredential()){
			
			String format="yyyy-MM-dd";
			List<LicensesAndCertVO> licensesList=new ArrayList<LicensesAndCertVO>();
			List<Credential> credentails=(List<Credential>) createFirmAccountRequest.getLicenseAndCertifications().getCredentials().getCredential();
			if(null!=credentails)
			{ 
			for(Credential credential:credentails){
				LicensesAndCertVO licensesAndCertVO=new LicensesAndCertVO();
				 if(StringUtils.isNotBlank(credential.getCredentialType())){
				licensesAndCertVO.setCredTypeDesc(credential.getCredentialType());
			     }
				 if(StringUtils.isNotBlank(credential.getCredentialCategory())){
				licensesAndCertVO.setCategoryTypeDesc(credential.getCredentialCategory());
				 }
				 if(StringUtils.isNotBlank(credential.getLicenseCertName())){
				licensesAndCertVO.setLicenseName(credential.getLicenseCertName());
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialIssuer())){
				licensesAndCertVO.setIssuerOfCredential(credential.getCredentialIssuer());
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialNumber())){
				licensesAndCertVO.setCredentialNum(credential.getCredentialNumber());
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialCity())){
				licensesAndCertVO.setCity(credential.getCredentialCity());
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialState())){
				licensesAndCertVO.setStateId(credential.getCredentialState());
				}
				 if(StringUtils.isNotBlank(credential.getCredentialCounty())){
				licensesAndCertVO.setCounty(credential.getCredentialCounty());
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialIssueDate())){
				licensesAndCertVO.setIssueDate(convertDate(credential.getCredentialIssueDate(),format));
				 }
				 if(StringUtils.isNotBlank(credential.getCredentialExpirationDate())){
				licensesAndCertVO.setExpirationDate(convertDate(credential.getCredentialExpirationDate(),format));
				 }
				licensesList.add(licensesAndCertVO);
			}
			providerRegistrationVO.setLicensesList(licensesList);
			}
			
			
		}
		}	
	}
	
	public void  mapLicenseInformationUpdate(UpdateFirmAccountRequest updateFirmAccountRequest,ProviderRegistrationVO providerRegistrationVO) throws Exception{
		
		
		if(null!=updateFirmAccountRequest.getLicenseAndCertifications()){
			providerRegistrationVO.setLicensePresent(true);
		}
 
	   if(null!=updateFirmAccountRequest.getLicenseAndCertifications()){
		
		String format="yyyy-MM-dd";
		List<LicensesAndCertVO> licensesList=new ArrayList<LicensesAndCertVO>();
		List<LicensesAndCertVO> editCredential = new ArrayList<LicensesAndCertVO>();

		if(null!= updateFirmAccountRequest.getLicenseAndCertifications().getCredentials() && null!=updateFirmAccountRequest.getLicenseAndCertifications().getCredentials().getCredential())
		{
		List<Credential> credentials=(List<Credential>) updateFirmAccountRequest.getLicenseAndCertifications().getCredentials().getCredential();
		if(null!=credentials)
		{
			licensesList=new ArrayList<LicensesAndCertVO>();
		    editCredential = new ArrayList<LicensesAndCertVO>();
		for(Credential credential:credentials){
			LicensesAndCertVO licensesAndCertVO=new LicensesAndCertVO();
			
			if(StringUtils.isNotBlank(credential.getCredentialType()))
			{
				licensesAndCertVO.setCredTypeDesc(credential.getCredentialType());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCategory()))
			{
				licensesAndCertVO.setCategoryTypeDesc(credential.getCredentialCategory());
			}
		
			if(StringUtils.isNotBlank(credential.getLicenseCertName()))
			{
				licensesAndCertVO.setLicenseName(credential.getLicenseCertName());
			}
		
			if(StringUtils.isNotBlank(credential.getCredentialIssuer()))
			{
				licensesAndCertVO.setIssuerOfCredential(credential.getCredentialIssuer());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialNumber()))
			{
				licensesAndCertVO.setCredentialNum(credential.getCredentialNumber());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCity()))
			{
				licensesAndCertVO.setCity(credential.getCredentialCity());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialState()))
			{
				licensesAndCertVO.setStateId(credential.getCredentialState());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialCounty()))
			{
				licensesAndCertVO.setCounty(credential.getCredentialCounty());
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialIssueDate()))
			{
				licensesAndCertVO.setIssueDate(convertDate(credential.getCredentialIssueDate(),format));
			}
			
			if(StringUtils.isNotBlank(credential.getCredentialExpirationDate()))
			{
				licensesAndCertVO.setExpirationDate(convertDate(credential.getCredentialExpirationDate(),format));
			}
			
			if(StringUtils.isNotBlank(credential.getVendorCredentialId()))
			{
				licensesAndCertVO.setVendorCredId(Integer.parseInt(credential.getVendorCredentialId()));
				editCredential.add(licensesAndCertVO);	
			}
			else
			{
				licensesList.add(licensesAndCertVO);
			}
			
		}
		providerRegistrationVO.setEditLicensesList(editCredential);
		providerRegistrationVO.setLicensesList(licensesList);
	  }	
	}
  }		
}

	
	private final Date convertDate(final String date1,final String format) throws Exception{
	       Date dateObj1=null;
	      try {
	      if(date1!=null){
	        DateFormat dateFormat = new SimpleDateFormat(format);
	        dateObj1 = dateFormat.parse(date1);
	      
	        }
	      }//end of try
	      catch (Exception errorexcep) {
	        //logger.log(ExceptionConstants.ERROR,"The Parse Error in compare date is:" + errorexcep);
	    	  System.out.println("-------------------Exception ex in parsing the date in mapper-----");
	    	  throw errorexcep;
	      }//end of catch
	      return dateObj1;
	  }
	
	
	
	public  String removeHyphenFromNumber(String number){
		String formattedNumber = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(number)){
			formattedNumber=StringUtils.remove(number, SOPDFConstants.HYPHEN);
		}
		return formattedNumber;
	}
	
	private String convertStringToDollar(String sAmount) {
		String retVal = "";
		double dConversion = Double.parseDouble(sAmount);

		retVal = java.text.NumberFormat.getCurrencyInstance().format(
				dConversion);

		//logger.info(retVal);
		retVal = retVal.replace("$", "");
		//logger.info(retVal);
		retVal = retVal.replace(",", "");
		//logger.info(retVal);

		return retVal;
	}
	

	public  ApproveFirmsResponse setTestResponse() {
		ApproveFirmsResponse response = new ApproveFirmsResponse();
		Results result = Results.getSuccess();
		response.setResults(result);
		ApprovedFirms approvedFirms = new ApprovedFirms();
		List<ApprovedInvalidFirm> approvedFirmList = new ArrayList<ApprovedInvalidFirm>();
		InvalidFirms invalidFirms = new InvalidFirms();
		List<ApprovedInvalidFirm> invalidFirmList = new ArrayList<ApprovedInvalidFirm>();
		/** Setting valid Firms */
		ApprovedInvalidFirm approvedFirm1 = new ApprovedInvalidFirm();
		approvedFirm1.setFirmId(10202);
		approvedFirm1.setMessage("Firm Status Updated Successfully");
		ApprovedInvalidFirm approvedFirm2 = new ApprovedInvalidFirm();
		approvedFirm2.setFirmId(10205);
		approvedFirm2.setMessage("Firm Status Updated Successfully");
		approvedFirmList.add(approvedFirm1);
		approvedFirmList.add(approvedFirm2);
		approvedFirms.setApprovedFirm(approvedFirmList);
		/** Setting invalid Firms */
		ApprovedInvalidFirm invalidFirm1 = new ApprovedInvalidFirm();
		invalidFirm1.setFirmId(10202);
		invalidFirm1.setMessage("The firm is not in the Pending Approval/Registration Completed state");
		ApprovedInvalidFirm invalidFirm12 = new ApprovedInvalidFirm();
		invalidFirm12.setFirmId(10205);
		invalidFirm12.setMessage("The firm is not in the Pending Approval/Registration Completed state");
		invalidFirmList.add(invalidFirm1);
		invalidFirmList.add(invalidFirm12);
		invalidFirms.setInvalidFirm(invalidFirmList);
		response.setApprovedFirms(approvedFirms);
		response.setInvalidFirms(invalidFirms);
		return response;
	}


	/**@Description: Setting Error Response in case of errors/exception.
	 * @param resultsCode
	 * @return ApproveFirmsResponse
	 */
	public ApproveFirmsResponse createErrorResponse(ResultsCode resultsCode) {
		ApproveFirmsResponse response = new ApproveFirmsResponse();
		Results result = null;
		//Setting Internal Server Error In case of Exception
		if(null== resultsCode){
			result = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}//Setting Specific Error Message for validation errors.
		else{
			result =Results.getError(resultsCode.getMessage(), resultsCode.getCode());
		}
		response.setResults(result);
		return response;
	}
	
	/**@Description: Setting Error Response in case of errors/exception.
	 * @param resultsCode
	 * @return ApproveProvidersResponse
	 */
	public ApproveProvidersResponse createErrorResponseForProvider(ResultsCode resultsCode) {
		ApproveProvidersResponse response = new ApproveProvidersResponse();
		Results result = null;
		//Setting Internal Server Error In case of Exception
		if(null== resultsCode){
			result = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		}//Setting Specific Error Message for validation errors.
		else{
			result =Results.getError(resultsCode.getMessage(), resultsCode.getCode());
		}
		response.setResults(result);
		return response;
	}


	/**@Description: Mapping Request to VO object
	 * @param request
	 * @return List<ApproveFirmsVO>
	 */
	public List<ApproveFirmsVO> mapApproveFirmRequest(ApproveFirmsRequest request) {
		ApproveFirmsVO firmsVO = null;
		List<ApproveFirmsVO> approveFirmsList = null;
		if(null!= request && null!= request.getProviderFirms() 
			   && null!=request.getProviderFirms().getProviderFirm()
			   && !(request.getProviderFirms().getProviderFirm().isEmpty())){
			   approveFirmsList = new ArrayList<ApproveFirmsVO>();
			   List<ProviderFirm> providerFirmList = request.getProviderFirms().getProviderFirm();
			   for(ProviderFirm providerFirm : providerFirmList){
				   firmsVO = new ApproveFirmsVO();
				   //Setting FirmId
				   firmsVO.setFirmId(providerFirm.getProviderFirmId());
				   if(null!= providerFirm.getProviderFirmStatus()){
					   ProviderFirmStatus providerFirmStatus = providerFirm.getProviderFirmStatus();
				       //Setting Status to update
					   firmsVO.setFirmStatus(providerFirmStatus.getFirmStatus());
					   if(null!= providerFirmStatus.getEmailIndicator()&& providerFirmStatus.getEmailIndicator().equalsIgnoreCase(YES)){
						   firmsVO.setEmailIndicator(true);
					   }else{
						   firmsVO.setEmailIndicator(false);
					   }
				       //Setting Reason Codes for Status change if it exists.
				       if(null!= providerFirmStatus.getReasonCodes() && 
				    		   null!=providerFirmStatus.getReasonCodes().getReasonCode()
				    		   &&!(providerFirmStatus.getReasonCodes().getReasonCode().isEmpty())){
				    	        List<String> resaonCodeList = providerFirmStatus.getReasonCodes().getReasonCode();
				    	        String[] reasonCodes = resaonCodeList.toArray(new String[resaonCodeList.size()]);
				    	        firmsVO.setReasonCodes(reasonCodes);
				    	       }
				            }
				         approveFirmsList.add(firmsVO);
			   }
		}
		return approveFirmsList;
	}
	
	/**@Description : Creating Response for Status Change
	 * @param validFirmsList
	 * @param object
	 * @return
	 */
	public ApproveFirmsResponse createResponse(List<ApproveFirmsVO> validFirmsList,List<ApproveFirmsVO> inValidFirmsList) {
		//Declaring Varaiables
		ApprovedFirms approvedFirms = null;
		InvalidFirms invalidFirms = null;
		Results result =null;
		//Initializing Response
		ApproveFirmsResponse response =new ApproveFirmsResponse();
		//Valid And Invalid Firms
		if( null!= validFirmsList && !validFirmsList.isEmpty() && null!= inValidFirmsList && !inValidFirmsList.isEmpty()){
			approvedFirms = new ApprovedFirms();
			invalidFirms = new InvalidFirms();
			List<ApprovedInvalidFirm> approvedFirm = setResponse(validFirmsList);
			List<ApprovedInvalidFirm> invalidFirm = setErrorResponse(inValidFirmsList);
			approvedFirms.setApprovedFirm(approvedFirm);
			invalidFirms.setInvalidFirm(invalidFirm);
			response.setInvalidFirms(invalidFirms);
			response.setApprovedFirms(approvedFirms);
			result = Results.getSuccess(ResultsCode.STATUS_CHANGE_PARTIAL_SUCCESS.getCode(),ResultsCode.STATUS_CHANGE_PARTIAL_SUCCESS.getMessage());
			response.setResults(result);
		}
		//All Firms are valid and Updated Successfully
		else if(null!=validFirmsList && !validFirmsList.isEmpty()){
			approvedFirms = new ApprovedFirms();
			List<ApprovedInvalidFirm> approvedFirm = setResponse(validFirmsList);
			approvedFirms.setApprovedFirm(approvedFirm);
			result = Results.getSuccess(ResultsCode.STATUS_CHANGE_SUCCESS_ALL.getCode(),ResultsCode.STATUS_CHANGE_SUCCESS_ALL.getMessage());
			response.setApprovedFirms(approvedFirms);
			response.setResults(result);
		} //All Firms  are invalid
		else if(null!= inValidFirmsList && !inValidFirmsList.isEmpty()){
			invalidFirms = new InvalidFirms();
			List<ApprovedInvalidFirm> invalidFirm = setErrorResponse(inValidFirmsList);
			invalidFirms.setInvalidFirm(invalidFirm);
			result = Results.getError(ResultsCode.INVALID_FIRM_ID_ALL.getMessage(),ResultsCode.INVALID_FIRM_ID_ALL.getCode());
			response.setInvalidFirms(invalidFirms);
			response.setResults(result);
		}
		return response;
	  }

	/**@Description : Creating Response for Status Change
	 * @param validFirmsList
	 * @param object
	 * @return
	 */
	public ApproveProvidersResponse createResponseForProvider(List<ApproveProvidersVO> validProvidersList,List<ApproveProvidersVO> invalidProvidersList) {
		//Declaring Varaiables
		ApprovedProviders approvedProviders = null;
		InvalidProviders invalidProviders = null;
		Results result =null;
		//Initializing Response
		ApproveProvidersResponse response =new ApproveProvidersResponse();
		//Valid And Invalid Provider
		if( null!= validProvidersList && !validProvidersList.isEmpty() && null!= invalidProvidersList && !invalidProvidersList.isEmpty()){
			approvedProviders = new ApprovedProviders();
			invalidProviders = new InvalidProviders();
			List<ApprovedInvalidProvider> approvedProvider = setResponseProvider(validProvidersList);
			List<ApprovedInvalidProvider> invalidProvider = setErrorResponseForProvider(invalidProvidersList);
			approvedProviders.setApprovedProvider(approvedProvider);
			invalidProviders.setInvalidProvider(invalidProvider);
			response.setInvalidProviders(invalidProviders);
			response.setApprovedProviders(approvedProviders);
			result = Results.getSuccess(ResultsCode.PROVIDER_STATUS_CHANGE_PARTIAL_SUCCESS.getCode(),ResultsCode.PROVIDER_STATUS_CHANGE_PARTIAL_SUCCESS.getMessage());
			response.setResults(result);
		}
		//All Provider are valid and Updated Successfully
		else if(null!=validProvidersList && !validProvidersList.isEmpty()){
			approvedProviders = new ApprovedProviders();
			List<ApprovedInvalidProvider> approvedProvider = setResponseProvider(validProvidersList);
			approvedProviders.setApprovedProvider(approvedProvider);
			result = Results.getSuccess(ResultsCode.PROVIDER_STATUS_CHANGE_SUCCESS_ALL.getCode(),ResultsCode.PROVIDER_STATUS_CHANGE_SUCCESS_ALL.getMessage());
			response.setApprovedProviders(approvedProviders);
			response.setResults(result);
		} //All Provider  are invalid
		else if(null!= invalidProvidersList && !invalidProvidersList.isEmpty()){
			invalidProviders = new InvalidProviders();
			List<ApprovedInvalidProvider> invalidProvider = setErrorResponseForProvider(invalidProvidersList);
			invalidProviders.setInvalidProvider(invalidProvider);
			result = Results.getError(ResultsCode.INVALID_PROVIDER_ID_ALL.getMessage(),ResultsCode.INVALID_PROVIDER_ID_ALL.getCode());
			response.setInvalidProviders(invalidProviders);
			response.setResults(result);
		}
		return response;
	  }


	/**
	 * @param firmsList
	 * @return
	 */
	private List<ApprovedInvalidFirm> setResponse(List<ApproveFirmsVO> firmsList) {
	     List<ApprovedInvalidFirm> firmList =new ArrayList<ApprovedInvalidFirm>();
			 for(ApproveFirmsVO firmVo:firmsList ){
				 ApprovedInvalidFirm firm = new ApprovedInvalidFirm();
				 if(null!= firmVo){
					 firm.setFirmId(firmVo.getFirmId()); 
					 firm.setMessage(ResultsCode.STATUS_CHANGE_SUCCESS_IND.getMessage());
					 firmList.add(firm);
				 }
			 }
		return firmList;
	    }

	/**
	 * @param firmsList
	 * @return
	 */
	private List<ApprovedInvalidProvider> setResponseProvider(List<ApproveProvidersVO> providersList) {
	     List<ApprovedInvalidProvider> providerList =new ArrayList<ApprovedInvalidProvider>();
			 for(ApproveProvidersVO providerVo:providersList ){
				 ApprovedInvalidProvider provider = new ApprovedInvalidProvider();
				 if(null!= providerVo){
					 provider.setProviderId(providerVo.getProviderId()); 
					 provider.setMessage(ResultsCode.PROVIDER_STATUS_CHANGE_SUCCESS_IND.getMessage());
					 providerList.add(provider);
				 }
			 }
		return providerList;
	    }
	/**
	 * @param firmsList
	 * @return
	 */
	private List<ApprovedInvalidFirm> setErrorResponse(List<ApproveFirmsVO> firmsList) {
	     List<ApprovedInvalidFirm> firmList =new ArrayList<ApprovedInvalidFirm>();
			 for(ApproveFirmsVO firmVo:firmsList ){
				 ApprovedInvalidFirm firm = new ApprovedInvalidFirm();
				 if(null!= firmVo){
					 firm.setFirmId(firmVo.getFirmId()); 
					 if(null!= firmVo.getValidationCode()){
						 firm.setMessage(firmVo.getValidationCode().getMessage());
					 }
					 firmList.add(firm);
				 }
			 }
		return firmList;
	    }
	
	/**
	 * @param providersList
	 * @return
	 */
	private List<ApprovedInvalidProvider> setErrorResponseForProvider(List<ApproveProvidersVO> providersList) {
	     List<ApprovedInvalidProvider> providerList =new ArrayList<ApprovedInvalidProvider>();
			 for(ApproveProvidersVO providerVo:providersList ){
				 ApprovedInvalidProvider provider = new ApprovedInvalidProvider();
				 if(null!= providerVo){
					 provider.setProviderId(providerVo.getProviderId()); 
					 if(null!= providerVo.getValidationCode()){
						 provider.setMessage(providerVo.getValidationCode().getMessage());
					 }
					 providerList.add(provider);
				 }
			 }
		return providerList;
	    }
	/**@Description: Mapping Request to VO object
	 * @param request
	 * @return List<ApproveProvidersVO>
	 */
	public List<ApproveProvidersVO> mapApproveProviderRequest(ApproveProvidersRequest request)throws Exception {
		ApproveProvidersVO providersVO = null;
		List<ApproveProvidersVO> approveProvidersList = null;
		if(null!= request && null!= request.getProviders() 
			   && null!=request.getProviders().getProvider()
			   && !(request.getProviders().getProvider().isEmpty())){
			approveProvidersList = new ArrayList<ApproveProvidersVO>();
			   List<Provider> providerList = request.getProviders().getProvider();
			   for(Provider provider : providerList){
				   providersVO = new ApproveProvidersVO();
				   //Setting providerId
				   providersVO.setProviderId(provider.getProviderId());
				   if(null!= provider.getProviderStatus()){
					   ProviderStatus providerStatus = provider.getProviderStatus();
				       //Setting Status to update
					   providersVO.setStatus(providerStatus.getStatus());
					   if(null!= providerStatus.getEmailIndicator()&& providerStatus.getEmailIndicator().equalsIgnoreCase(ProviderConstants.YES)){
						   providersVO.setEmailIndicator(true);
					   }else{
						   providersVO.setEmailIndicator(false);
					   }
				       //Setting Reason Codes for Status change if it exists.
				       if(null!= providerStatus.getReasonCodes() && 
				    		   null!=providerStatus.getReasonCodes().getReasonCode()
				    		   &&!(providerStatus.getReasonCodes().getReasonCode().isEmpty())){
				    	        List<String> reasonCodeList = providerStatus.getReasonCodes().getReasonCode();
				    	        list = new ArrayList<String>();
				    	        list.addAll(reasonCodeList);
				    	        HashSet hs = new HashSet(list);
				    			list.clear();
				    			List<String> resultList = new ArrayList<String>(hs);
				    	        String[] reasonCodes = resultList.toArray(new String[resultList.size()]);
				    	        providersVO.setReasonCodes(reasonCodes);
				    	       }
				            }
				   if(null!= provider.getBackgroundCheck()){
					   BackgroundCheck backgroundCheck = provider.getBackgroundCheck();
					   providersVO.setBackgroundCheckStatus(backgroundCheck.getBackgroundCheckStatus());
					   
					   java.util.Date issueParsedDateGorVerification=null;
						if(StringUtils.isNotBlank(backgroundCheck.getVerificationDate()))
						{
							try {
								SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
								
								issueParsedDateGorVerification = issueDateformater.parse(backgroundCheck.getVerificationDate());
								providersVO.setVerificationDate(issueParsedDateGorVerification);

							} catch (ParseException e) {
								LOGGER.error(" ParseException"+e);
								throw e;
							}
						}
					   if(StringUtils.isNotBlank(backgroundCheck.getReverificationDate()))
						{
							try {
								SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
								java.util.Date issueParsedDate;
								issueParsedDate = issueDateformater.parse(backgroundCheck.getReverificationDate());
								providersVO.setReverificationDate(issueParsedDate);

							} catch (ParseException e) {
								LOGGER.error(" ParseException"+e);
								throw e;
							}
						}
					   if(StringUtils.isNotBlank(backgroundCheck.getRequestDate()))
						{
							try {
								SimpleDateFormat issueDateformater = new SimpleDateFormat("yyyy-MM-dd");
								java.util.Date issueParsedDate;
								issueParsedDate = issueDateformater.parse(backgroundCheck.getRequestDate());
								providersVO.setRequestDate(issueParsedDate);

							} catch (ParseException e) {
								LOGGER.error(" ParseException"+e);
								throw e;
							}
						}else{
							providersVO.setRequestDate(issueParsedDateGorVerification);
						}
					  
				   }
				   approveProvidersList.add(providersVO);
			   }
		}
		return approveProvidersList;
	}
	
	}
	
	

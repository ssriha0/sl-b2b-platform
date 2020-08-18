package com.servicelive.shipregistration.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.IResourceLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorFinanceDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorPolicyDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.FinanceProfile;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorLocation;
import com.newco.marketplace.vo.provider.VendorPolicy;
import com.newco.marketplace.vo.provider.VendorResource;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.shipregistration.ISHIPProviderRegistrationService;
import com.servicelive.shipregistration.dao.ISHIPRegistrationDao;
import org.apache.commons.lang.StringUtils;

public class SHIPProviderRegistrationService implements ISHIPProviderRegistrationService{
	
	private ISHIPRegistrationDao registrationDao;
	private IVendorHdrDao vendorHdrDao;
	private IContactDao contactDao;
	private IUserProfileDao userProfileDao;
	private SecurityDAO securityDao;
	private ILocationDao locationDao;
	private IVendorLocationDao vendorLocationDao;
	private IVendorResourceDao vendorResourceDao;
	private IResourceLocationDao resourceLocationDao;
	private IVendorPolicyDao vendorPolicyDao;
	private IVendorFinanceDao vendorFinanceDao;
	private IActivityRegistryDao activityRegistryDao;
	private IAuditBO auditBO;
	private IProviderEmailBO providerEmailBO;
	private IApplicationProperties applicationProperties;
	private static final int ADDRESS_TYPE_HOME = 3;
	private static final Logger logger = Logger
	.getLogger(SHIPProviderRegistrationService.class.getName());
	
	public String getApplicationProperty(String key){
		String value = null;
		try{
			value = applicationProperties.getPropertyFromDB(key);
		}catch(Exception e){
		}
		return value;
	}
	public Integer getSLIndustry(String productDesc){
		Integer slIndustryId = null;
		try{
			slIndustryId = registrationDao.getSLIndustry(productDesc);
		}catch(com.newco.marketplace.exception.core.DataServiceException e){
			logger.info("Exception in getting industryId for product description"+productDesc+ "exception:"+e.getMessage());
		}
		return slIndustryId;
	}
	public Integer getVendorIdForSubContractorForResource(Integer subContractorId)
	{
		Integer vendorId = null;
		try {
			vendorId = registrationDao.getVendorIdForSubContractorForResource(subContractorId);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.info("Exception in getting vendorid for the subcontractorId"+subContractorId);
			e.printStackTrace();
		}
		return vendorId;
	}
	public Integer getVendorIdForFirm(Integer subContractorId){
		Integer vendorId = null;
		try{
			vendorId = registrationDao.getVendorIdForFirm(subContractorId);
		}catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.info("Exception in getting vendorid for the subcontractorId"+subContractorId);
			e.printStackTrace();
		}
		return vendorId;
		
	}
	public List<Integer> getVendorIdForSubContractorCrewIdForResource(ProviderRegistrationVO registrationVO) {
		List<Integer> vendorIdList = null;
		try{
			vendorIdList=registrationDao.getVendorIdForProviderCrewId(registrationVO);
		}catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.info("Exception in getting vendorid for the subcontractorId"+registrationVO.getSubContractorId()
					+"and subcontractorCrewId"+registrationVO.getSubContractorCrewId()+"combination");
			e.printStackTrace();
		}
		return vendorIdList;
	}
	public ProviderRegistrationVO doRegisterResource(
			ProviderRegistrationVO providerRegistrationVO) throws AuditException
	{
		// Create the contact record
		ProviderRegistrationVO prvRegResponse = new ProviderRegistrationVO();
		Contact contact = new Contact();
		VendorResource vendorResource = new VendorResource();
		try {
			
			logger.info("Saving contact for registering business for provider");
			contact.setLastName(providerRegistrationVO.getLastName());
			contact.setFirstName(providerRegistrationVO.getFirstName());
			contact.setHonorific("");
			contactDao.insert(contact);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving contact details due to"
							+ dae.getMessage());
			
		}
		try {
			logger.info("Saving vendor resource for registering business for provider");

			vendorResource.setVendorId(providerRegistrationVO.getVendorId());
			prvRegResponse.setVendorId(providerRegistrationVO.getVendorId());
			vendorResource.setContactId(contact.getContactId());
			vendorResource
					.setWfStateId(ProviderConstants.TEAM_MEMBER_APPLICATION_STATE_INCOMPLETE);
			vendorResource
					.setBackgroundStateId(ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_NOT_STARTED);
			vendorResource.setPrimaryInd(0);
			//Added for setting Market Place Indicator into the vendor Resource Table
			//Modified by Offshore - Covansys
			vendorResource.setMktPlaceInd(1);
			vendorResource.setDispatchId(0);
			vendorResource.setAdminInd(0);
			/*Setting resource Indicator as 1 for Provider*/
			logger.info("inserting resource indicator as 1 for provider");
			vendorResource.setResourceInd(1);
			vendorResource.setUserName(null);
			
			//Setting manager_ind, other_ind, service_provider_ind as 0
			vendorResource.setManagerInd(0);
			vendorResource.setOtherInd(0);
			vendorResource.setSproInd(0);
			
			vendorResource = vendorResourceDao.insert(vendorResource);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Vendor Resource Details type id details due to"
							+ dae.getMessage());
			
		}
		/*insering 6 rows into vendor_activity_registry for provider 
		  other than firm as per new requirement change by onsite*/
		logger.info("Insering 6 records from 7 to 12 for provider in " +
				" vendor_activity_registry table as per new requirement change");
		String resourceId = vendorResource.getResourceId().toString();
		try {
			activityRegistryDao.insertResourceActivityStatus(resourceId);
		} catch (Exception e) {
			logger.info("Exception in insering into vendor_activity_rgistry for provider using resource id"+ e.getMessage());
			e.printStackTrace();
		}
		//Method to insert into audit_task table for provider as per new requirement
		logger.info("Inserting into audit task for provider");
		try {
			getAuditBO().auditVendorResource(vendorResource.getResourceId(), RESOURCE_INCOMPLETE);
			getAuditBO().auditResourceBackgroundCheck(vendorResource.getResourceId(), RESOURCE_BACKGROUND_CHECK_INCOMPLETE);
 
	     } catch (AuditException ae) {
		logger.info("[RegistrationBOImpl] - saveRegistration() - Audit Exception Occured for audit record: saveRegistration()"
				 + ae.getMessage());
		throw ae;
	    }
		prvRegResponse.setSubContractorCrewId(providerRegistrationVO.getSubContractorCrewId());
		prvRegResponse.setSubContractorId(providerRegistrationVO.getSubContractorId());
		prvRegResponse.setVendorContactResourceId(vendorResource.getResourceId());
		/*This details are set back in response to log success file 
		 * */
		prvRegResponse.setFirstName(providerRegistrationVO.getFirstName());
		prvRegResponse.setLastName(providerRegistrationVO.getLastName());
	    return prvRegResponse;
	}
	public ProviderRegistrationVO doRegisterFirm(
			ProviderRegistrationVO providerRegistrationVO)
			throws DBException, DataAccessException, DataServiceException,
			EmailSenderException, AuditException {

		ProviderRegistrationVO prvRegResponse = new ProviderRegistrationVO();

		VendorHdr tempVendorHeader = null;
		VendorHdr vendorHeader = new VendorHdr();
		Contact contact = new Contact();
		UserProfile userProfile = new UserProfile();
		VendorResource vendorResource = new VendorResource();
		Location location = new Location();
		ResourceLocation resourceLocation = new ResourceLocation();
		String password= AdminUtil.generatePassword();
		providerRegistrationVO.setPassword(password);
		prvRegResponse.setPassword(password);
		password = CryptoUtil.encrypt(password);
		logger.info("Entering ProviderRegistrationResponse.doRegister()");
		try {
			logger.info("Saving vendor header for registering business for firm");
			vendorHeader.setSourceSystemId("3");
			vendorHeader.setBusinessName(providerRegistrationVO
					.getDBAName());
			vendorHeader.setVendorStatusId(1);
			if(providerRegistrationVO.getPrimaryIndustry()!=null&&providerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				vendorHeader.setPrimaryIndustryId(Integer.parseInt(providerRegistrationVO.getPrimaryIndustry()));
			}
			tempVendorHeader = vendorHdrDao.insert(vendorHeader);
			prvRegResponse.setVendorId(tempVendorHeader.getVendorId());
		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() due to"
					+ dae.getMessage());
			throw dae;
		}

		// Create the contact record
		try {
			logger.info("Saving contact for registering business for firm");
			logger.info("setLastName  : " + providerRegistrationVO.getLastName());
			logger.info("setFirstName : " + providerRegistrationVO.getFirstName());
			logger.info("setMi : " + providerRegistrationVO.getMiddleName());
			logger.info("setEmail : " + providerRegistrationVO.getEmail());
			logger.info("setPhoneNo : " + providerRegistrationVO.getMainBusiPhoneNo());
			
			contact.setLastName(providerRegistrationVO.getLastName());
			contact.setFirstName(providerRegistrationVO.getFirstName());
			contact.setMi(providerRegistrationVO.getMiddleName());
			contact.setEmail(providerRegistrationVO.getEmail());
			contact.setHonorific("");
			//Getting an error
			//contact.setPhoneNo(providerRegistrationVO.getMainBusiPhoneNo().toString());
			if(null != providerRegistrationVO.getMainBusiPhoneNo()){
				contact.setPhoneNo(Long.toString(providerRegistrationVO.getMainBusiPhoneNo()));
			}
			if(null != providerRegistrationVO.getMainBusiMobileNo()){
				contact.setCellNo(Long.toString(providerRegistrationVO.getMainBusiMobileNo()));
			}
			
			
			prvRegResponse.setEmail(providerRegistrationVO.getEmail());
			contactDao.insert(contact);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving contact details due to"
							+ dae.getMessage());
			throw dae;
		}

		// Create the user_profile.
		try {
			logger.info("Saving user profile for registering business for firm");
			logger.info("Username generated on passing vo is"+generateUsername(providerRegistrationVO));
			userProfile.setUserName(generateUsername(providerRegistrationVO));
			
			//R11_2
			//Trimming username before saving in the database
			if(StringUtils.isNotBlank(userProfile.getUserName())){
				userProfile.setUserName(userProfile.getUserName().trim());
			}
			
			userProfile.setContactId(contact.getContactId());
			if (password != null) {
				userProfile.setPassword(password);
			}
			userProfile.setPasswordFlag(1);
			//prvRegResponse.setUserName(providerRegistrationVO.getUserName());
			
			logger.info("Username generated fetching from vo is:"+providerRegistrationVO.getUserName());
			userProfile.setAnswerTxt("");
			userProfile.setQuestionId(0);

			userProfile.setRoleId(OrderConstants.PROVIDER_ROLEID);
			userProfile.setRoleName(MPConstants.ROLE_PROVIDER_ADMIN);
			userProfileDao.insert(userProfile);
			//Updating user_profile table with the vendor id 
			Integer vendorId=vendorHeader.getVendorId();
			String userName=userProfile.getUserName();
			logger.info("Values of table before updating user_profile vendorId"+vendorId+"userName"+userName);
			userProfileDao.updateUserProfile(vendorId,userName);
			logger.info("After updating table in R 6.0 with user name with vendor"+vendorId);
			/*Set the username in response,getting it from userProfile*/
			prvRegResponse.setUserName(userProfile.getUserName());
			providerRegistrationVO.setUserName(userProfile.getUserName());
			/*
			 *   Added by GL and Friends
			 *   insert this user as an admin, pay attention to that this will insert a user as an admin
			 *
			 */

			securityDao.insertAdminProfile(userProfile);



		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
							+ dae.getMessage());
			throw dae;
		}
		catch (com.newco.marketplace.exception.core.DataServiceException dse) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
							+ dse.getMessage());
			throw new DBException(dse.getMessage());
		}

		try {
			logger.info("Saving location for registering business for firm");
			location.setLocnTypeId(ADDRESS_TYPE_HOME);
			locationDao.insert(location);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		// Create vendor resource
		try {
			logger.info("Saving vendor resource for registering business for firm");

			vendorResource.setVendorId(vendorHeader.getVendorId());
			vendorResource.setContactId(contact.getContactId());
			vendorResource
					.setWfStateId(ProviderConstants.TEAM_MEMBER_APPLICATION_STATE_INCOMPLETE);
			vendorResource
					.setBackgroundStateId(ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_NOT_STARTED);
			vendorResource.setPrimaryInd(1);
			//Added for setting Market Place Indicator into the vendor Resource Table
			//Modified by Offshore - Covansys
			vendorResource.setMktPlaceInd(1);
			vendorResource.setDispatchId(0);
			vendorResource.setUserName(userProfile.getUserName());
			vendorResource.setAdminInd(1);
			/*Setting resource Indicator as 0 for FIRM*/
			logger.info("inserting resource indicator as zero for FIRM");
			vendorResource.setResourceInd(0);
			
			//Setting manager_ind, other_ind, service_provider_ind as 0
			vendorResource.setManagerInd(0);
			vendorResource.setOtherInd(0);
			vendorResource.setSproInd(0);
			vendorResource = vendorResourceDao.insert(vendorResource);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Vendor Resource Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}
		// create vendor Resource location record
		try {
			logger.info("Saving Resource location for registering business for firm");
			resourceLocation.setLocationId(location.getLocnId());
			resourceLocation.setResourceId(vendorResource.getResourceId());
			this.resourceLocationDao.insert(resourceLocation);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Resource location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		// create Bcak Ground Ckeck location record
		try {
			logger.info("Saving Resource location for registering business for firm");


			// set the backGround Status
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
	        tmbcVO.setResourceId(vendorResource.getResourceId().toString());
	        tmbcVO.setWfEntity("Team Member Background Check");
	        tmbcVO.setBackgroundCheckStatus("Not Started");
	        vendorResourceDao.updateBackgroundCheckStatus(tmbcVO);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving backGround Check location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		try {
			logger.info("Saving vendor header for registering business for firm");

			/**
			 * Values not entered at this point of time
			 */
			vendorHeader.setBusinessTypeId(null);
			vendorHeader.setCompanySizeId(null);
			if (null==providerRegistrationVO.getDBAName() || providerRegistrationVO.getDBAName().trim().length()==0){
				vendorHeader.setDbaName(providerRegistrationVO.getLegalBusinessName());	
			}
			else{
				vendorHeader.setDbaName(providerRegistrationVO.getDBAName());
			}
			
			vendorHeader.setDunsNo(null);
			vendorHeader.setEinNo(null);
			//set the Encrypted einNo
			vendorHeader.setEinNoEnc(null);
			vendorHeader.setVendorId(tempVendorHeader.getVendorId());
			vendorHeader.setWebAddress(providerRegistrationVO
					.getWebsiteAddress());

			// vendorHeader.setBusinessStartDate("");
			vendorHeader.setReferralId(13);

			vendorHeader.setPromotionCode("SHIP");

			vendorHeader.setCompanySizeId(null);
			vendorHeader.setNoCredInd(null);

			vendorHeader.setTaxStatus(null);
			vendorHeader.setForeignOwnedInd(null);
			vendorHeader.setForeignOwnedPct(null);

			/**
			 * Added Vendor Business Phone number, Extension and Fax Number
			 */
			if( null != providerRegistrationVO.getMainBusiPhoneNo()){
				vendorHeader.setBusinessPhone(providerRegistrationVO.getMainBusiPhoneNo().toString());
			}
			if(providerRegistrationVO.getPrimaryIndustry()!=null&&providerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				vendorHeader.setPrimaryIndustryId(Integer.parseInt(providerRegistrationVO.getPrimaryIndustry()));
				vendorHeader.setOtherPrimaryService(providerRegistrationVO.getOtherPrimaryService());
			}
			vendorHeader.setProviderMaxWithdrawalLimit(
					Double.valueOf(PropertiesUtils.getFMPropertyValue(
							Constants.AppPropConstants.PROVIDER_MAX_WITHDRAWAL)));

			vendorHeader.setProviderMaxWithdrawalNo(
					Integer.valueOf(PropertiesUtils.getFMPropertyValue(
							Constants.AppPropConstants.PROVIDER_MAX_WITHDRAWAL_NO)));
			//updating account_contact_id with contactId,primary resource_id with resourceId
			logger.info("Updating vendor header with values:"+contact.getContactId()+"for account_contact_id");
			vendorHeader.setAccountContactId(contact.getContactId());
			logger.info("Updating vendorHdr with resource id :"+ vendorResource.getResourceId()+"as primary_resource_id");
			vendorHeader.setPrimaryResourceId(vendorResource.getResourceId());
			vendorHdrDao.update(vendorHeader);

			VendorLocation vendorLocation = new VendorLocation();
			location = new Location();
			location.setStreet1(providerRegistrationVO.getBusinessStreet1());
			location.setStreet2(providerRegistrationVO.getBusinessStreet2());
			location.setCity(providerRegistrationVO.getBusinessCity());
			location.setStateCd(providerRegistrationVO.getBusinessState());

			location.setZip(providerRegistrationVO.getBusinessZip());
			location.setLocnTypeId(1);

			location = locationDao.insert(location);

			vendorLocation.setVendorId(vendorHeader.getVendorId());
			vendorLocation.setLocationId(location.getLocnId());
			vendorLocationDao.insert(vendorLocation);
			// insert mailing address

			location = new Location();
			location.setStreet1(providerRegistrationVO.getBusinessStreet1());
			location.setStreet2(providerRegistrationVO.getBusinessStreet2());
			location.setCity(providerRegistrationVO.getBusinessCity());
			location.setStateCd(providerRegistrationVO.getBusinessState());

			location.setZip(providerRegistrationVO.getBusinessZip());
			location.setLocnTypeId(2);
			// Mailing address

			location = locationDao.insert(location);

			VendorLocation vendorLocation2 = new VendorLocation();
			vendorLocation2.setVendorId(vendorHeader.getVendorId());
			vendorLocation2.setLocationId(location.getLocnId());
			vendorLocationDao.insert(vendorLocation2);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		prvRegResponse.setVendorContactResourceId(vendorResource.getResourceId());
		prvRegResponse.setSubContractorCrewId(null);
		prvRegResponse.setSubContractorId(providerRegistrationVO.getSubContractorId());
		// Inserting Into vendor Policy
		VendorPolicy vendorPolicy = new VendorPolicy();
		try {
			logger.info("Saving vendor Policy for registering business for firm");
			vendorPolicy.setVendorId(vendorHeader.getVendorId());
			// following are hard Coded
			vendorPolicy.setWarPeriod(0);
			vendorPolicy.setWarrentyMessuare(null);

			vendorPolicyDao.insert(vendorPolicy);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Policy  details due to"
							+ dae.getMessage());
			throw dae;
		}


		// Inserting Into vendor Finanace
		FinanceProfile vendorFinance = new FinanceProfile();
		try {
			logger.info("Saving Vendor Finance for registering business for firm");
			vendorFinance.setVendorId(vendorHeader.getVendorId());
			vendorFinanceDao.insert(vendorFinance);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Finance  details due to"
							+ dae.getMessage());
			throw dae;
		}


		try{
			Integer vendorId = prvRegResponse.getVendorId();
			activityRegistryDao.insertActivityStatus(vendorId.toString());

			String resourceId = vendorResource.getResourceId().toString();
			activityRegistryDao.insertResourceActivityStatus(resourceId);

		} catch (Exception exp) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while registring vendor activity"
							+ exp.getMessage());
			throw new DBException("SQL Exception @RegistrationBOImpl.doRegister() while registring vendor activity", exp);
		}

		try {
				getAuditBO().auditVendorHeader(prvRegResponse.getVendorId(), VENDOR_INCOMPLETE);
				getAuditBO().auditVendorResource(prvRegResponse.getVendorContactResourceId(), RESOURCE_INCOMPLETE);
				getAuditBO().auditResourceBackgroundCheck(prvRegResponse.getVendorContactResourceId(), RESOURCE_BACKGROUND_CHECK_INCOMPLETE);

		} catch (AuditException ae) {
			logger.info("[RegistrationBOImpl] - saveRegistration() - Audit Exception Occured for audit record: saveRegistration()"
					 + ae.getMessage());
			throw ae;
		}
		//Updating vendor_credential table for insurance details
		
		logger.info("Inserting insurance Details");
		//Insert General Liability Insurance gets inserted even if 3 fields are null
		CredentialProfile generalliabilityInsurance=new CredentialProfile();
		generalliabilityInsurance.setVendorId(prvRegResponse.getVendorId());
		generalliabilityInsurance.setCredentialTypeId(6);
		generalliabilityInsurance.setCredentialCategoryId(41);
		generalliabilityInsurance.setCredentialSource(providerRegistrationVO.getlInsuranceCompany());
		//Setting wf stateId to 13 to load insurance Tab for Firm
		generalliabilityInsurance.setWfStateId(13);
		//Added null check
		//if(null != providerRegistrationVO.getlPolicyNumber()){
			generalliabilityInsurance.setCredentialNumber(providerRegistrationVO.getlPolicyNumberVal());
		
		//}
		if(null!=providerRegistrationVO.getlExpirationDate()){
		    Timestamp time=	new Timestamp(providerRegistrationVO.getlExpirationDate().getTime());
		    logger.info("Timestamp generated:"+ time);
		    generalliabilityInsurance.setCredentialExpirationDate(time);
		}

		if( !StringUtils.isBlank(generalliabilityInsurance.getCredentialSource()) 
				|| (null != generalliabilityInsurance.getCredentialExpirationDate() 
						&& !StringUtils.isBlank(generalliabilityInsurance.getCredentialExpirationDate().toString()))
					|| !StringUtils.isBlank(generalliabilityInsurance.getCredentialNumber())){
				
			//if(null != generalliabilityInsurance.getCredentialSource()||
			//		null != generalliabilityInsurance.getCredentialExpirationDate()||
			//		     null != generalliabilityInsurance.getCredentialNumber()){
			try {
				logger.info("insering General Liability insuarance Details");
				registrationDao.insertInsuranceTypes(generalliabilityInsurance);
				logger.info("Updating General Liability insurance indicator in vendor_hdr");
				registrationDao.updateInsuranceInd(prvRegResponse.getVendorId(),41);
			} catch (com.newco.marketplace.exception.core.DataServiceException e1) {
				logger.info("Exception inserting into vendor_credential table for General Liability insurance type"+e1.getMessage());
				e1.printStackTrace();
			}
		}
		//Insert Auto Liability Insurance gets inserted even if 3 fields are null
		CredentialProfile autoLiabilityInsurance=new CredentialProfile();
		autoLiabilityInsurance.setVendorId(prvRegResponse.getVendorId());
		autoLiabilityInsurance.setCredentialTypeId(6);
		autoLiabilityInsurance.setCredentialCategoryId(42);
		autoLiabilityInsurance.setCredentialSource(providerRegistrationVO.getaInsuranceCompany());
		//Setting wf stateId to 13 to load insurance Tab for Firm
		autoLiabilityInsurance.setWfStateId(13);
		//if(null != providerRegistrationVO.getaPolicyNumber()){
			autoLiabilityInsurance.setCredentialNumber(providerRegistrationVO.getaPolicyNumberVal());
		//}
		if(null !=providerRegistrationVO.getaExpirationDate() ){
			 Timestamp time=	new Timestamp(providerRegistrationVO.getaExpirationDate().getTime());
			 logger.info("Timestamp generated:"+ time);
		     autoLiabilityInsurance.setCredentialExpirationDate(time);
		}
		
		if( !StringUtils.isBlank(autoLiabilityInsurance.getCredentialSource()) 
				|| (null != autoLiabilityInsurance.getCredentialExpirationDate() 
						&& !StringUtils.isBlank(autoLiabilityInsurance.getCredentialExpirationDate().toString()))
					|| !StringUtils.isBlank(autoLiabilityInsurance.getCredentialNumber())){
		//if( null!=autoLiabilityInsurance.getCredentialSource()||
		//		        null!=autoLiabilityInsurance.getCredentialExpirationDate()||
		//		               null != autoLiabilityInsurance.getCredentialNumber()){
		try {
			logger.info("insering Auto Liability insuarance Details");
			registrationDao.insertInsuranceTypes(autoLiabilityInsurance);
			logger.info("Updating Auto Liability insurance indicator in vendor_hdr");
			registrationDao.updateInsuranceInd(prvRegResponse.getVendorId(),42);
		} catch (com.newco.marketplace.exception.core.DataServiceException e2) {
			logger.info("Exception inserting into vendor_credential table for Auto Liability insurance type"+e2.getMessage());
			e2.printStackTrace();
		 }
		}
		//Insert Workers Compensation Insurance gets inserted even if 3 fields are null
		CredentialProfile workmansCompensation=new CredentialProfile();
		workmansCompensation.setVendorId(prvRegResponse.getVendorId());
		workmansCompensation.setCredentialTypeId(6);
		workmansCompensation.setCredentialCategoryId(43);
		workmansCompensation.setCredentialSource(providerRegistrationVO.getWcInsuranceCompany());
		//Setting wf stateId to 13 to load insurance Tab for Firm
		workmansCompensation.setWfStateId(13);
		//if(null != providerRegistrationVO.getlPolicyNumber()){
			workmansCompensation.setCredentialNumber(providerRegistrationVO.getWcPolicyNumberVal());
		//}
		if(null!=providerRegistrationVO.getWcExpirationDate()){
			 Timestamp time=	new Timestamp(providerRegistrationVO.getWcExpirationDate().getTime());
			 logger.info("Timestamp generated:"+ time);
		     workmansCompensation.setCredentialExpirationDate(time);
		}
		if( !StringUtils.isBlank(workmansCompensation.getCredentialSource()) 
				|| (null != workmansCompensation.getCredentialExpirationDate() && StringUtils.isBlank(workmansCompensation.getCredentialExpirationDate().toString()))
					|| !StringUtils.isBlank(workmansCompensation.getCredentialNumber())){
		//if( null!= workmansCompensation.getCredentialExpirationDate()||
		//		null!=workmansCompensation.getCredentialSource()||
		//		     null!= workmansCompensation.getCredentialNumber()){
		try {
			logger.info("insering workmans compensation insuarance Details");
			registrationDao.insertInsuranceTypes(workmansCompensation);
			logger.info("Updating workmans compensation insurance indicator in vendor_hdr");
			registrationDao.updateInsuranceInd(prvRegResponse.getVendorId(),43);
		} catch (com.newco.marketplace.exception.core.DataServiceException e3) {
			logger.info("Exception inserting into vendor_credential table for workmans compensation  insurance type"+e3.getMessage());
			e3.printStackTrace();
		}
		}
		//Method to sent mail via response
		try{
		       doSendFinalEmailMessage(providerRegistrationVO);
		}catch (EmailSenderException e) {
			   logger.info("Email is not sent:"+e.getMessage());
		}

		return prvRegResponse;

	}

	private String generateUsername(ProviderRegistrationVO providerRegistrationVO) {
		String resultName = "";
		Integer numStart = 6;
		Integer numToAdd = 1;
		String username = providerRegistrationVO.getLastName().concat(providerRegistrationVO.getFirstName().substring(0, 1));
		String userNameToAppend = username + numStart;
		List<Integer> userNameNumberList = new ArrayList<Integer>();
		logger.info("username from excel using lastname"+ providerRegistrationVO.getLastName()
				    + "and firstletter of firstname"+ providerRegistrationVO.getFirstName() + "is:" + username);
		logger.info("Appended username is:" + userNameToAppend);
		try {
			String isExistUserName = registrationDao.getUserName(username);
			logger.info("Result for username:" + isExistUserName);
			if (null == isExistUserName) {
				logger.info("Username:" + username+ "not exist in DB.So we can use it");
				resultName = username;
				return resultName;
			} else {
				List<String> names = registrationDao.getUsernameLike(userNameToAppend);
				logger.info("Size of user name list with 6 appended"+ names.size());
				if (null != names && names.size() >= 1) {
					for (String nameFromDB : names) {
						logger.info("Name from DB is:" + nameFromDB);
						String userNameFromDB = nameFromDB;
						logger.info("User name found from DB:" + userNameFromDB);
						if (null != userNameFromDB && (userNameFromDB.length() - userNameToAppend.length()) >= 1) {
							String numVal = userNameFromDB.substring(username.length() + 1);
							logger.info("Num value after substring:" + numVal);
							try{
							userNameNumberList.add(Integer.parseInt(numVal));}
							catch (NumberFormatException e) {
								continue;
							}
						}
					}
				} else {
					logger.info("Entered in else condition with name size"+ names.size());
					if (null != names && names.size() == 0) {
						logger.info("If there is user name without ending numbe 6 then directly return user name with added 61");
						logger.info("Adding first user name with number 61"+ userNameToAppend);
						resultName = userNameToAppend + numToAdd.toString();
						return resultName;
					}

				}
				logger.info("list size of usename with number:"+ userNameNumberList.size());
				if (null != userNameNumberList && userNameNumberList.size() > 0) {
					Integer maxUserNumber = Collections.max(userNameNumberList);
					logger.info("Maximum user number is: " + maxUserNumber);
					numToAdd = maxUserNumber + 1;
					logger.info("Maximum user number after incrementing by 1 : "+ numToAdd);
					resultName = userNameToAppend + numToAdd.toString();
					return resultName;
				}else{
					resultName = userNameToAppend + numToAdd.toString();
					return resultName;
				}
			}
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			logger.info("Exception in creating username" + e.getMessage());
		}
		logger.info("Returning username:" + resultName);
		return resultName;
	}

	public void insertSubContractorInfo(
			ProviderRegistrationVO providerRegistrationVO)
	throws BusinessServiceException{
		try{
			registrationDao.insertSubContractorInfo(providerRegistrationVO);
		} catch (com.newco.marketplace.exception.core.DataServiceException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param providerRegistrationVO
	 * @return
	 * @throws EmailSenderException
	 * @throws DBException
	 */
	private void doSendFinalEmailMessage(ProviderRegistrationVO providerRegistrationVO)
			throws EmailSenderException,DBException{
		// send the confirmation email
		try {
			logger.info("----State is Valid I m sending Password and username to you-----");
			providerEmailBO.sendConfirmationMail(providerRegistrationVO.getUserName(), providerRegistrationVO.getPassword(),
						providerRegistrationVO.getEmail(),providerRegistrationVO.getFirstName());
				providerRegistrationVO.setValidateState(false);
		}
			catch (Throwable t) {
			logger.info("throwale thrown sending email");
			t.printStackTrace();
			throw new EmailSenderException(t);

		}
	}
	public String  getValidBusinessStateCd(ProviderRegistrationVO registrationVO) {
		String stateCd=null;
		try{
		stateCd=registrationDao.getValidBusinessStateCd(registrationVO.getBusinessState());
		  }
		catch (com.newco.marketplace.exception.core.DataServiceException e) {
		logger.info("Exception in validating business State"+e.getMessage());
		}
		return stateCd;
	}
	
	public ISHIPRegistrationDao getRegistrationDao() {
		return registrationDao;
	}
	public void setRegistrationDao(ISHIPRegistrationDao registrationDao) {
		this.registrationDao = registrationDao;
	}
	
	
	public SecurityDAO getSecurityDao() {
		return securityDao;
	}
	public void setSecurityDao(SecurityDAO securityDao) {
		this.securityDao = securityDao;
	}
	
	public IContactDao getContactDao() {
		return contactDao;
	}
	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}
	public IUserProfileDao getUserProfileDao() {
		return userProfileDao;
	}
	public void setUserProfileDao(IUserProfileDao userProfileDao) {
		this.userProfileDao = userProfileDao;
	}
	public ILocationDao getLocationDao() {
		return locationDao;
	}
	public void setLocationDao(ILocationDao locationDao) {
		this.locationDao = locationDao;
	}
	public IVendorLocationDao getVendorLocationDao() {
		return vendorLocationDao;
	}
	public void setVendorLocationDao(IVendorLocationDao vendorLocationDao) {
		this.vendorLocationDao = vendorLocationDao;
	}
	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}
	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}
	public IResourceLocationDao getResourceLocationDao() {
		return resourceLocationDao;
	}
	public void setResourceLocationDao(IResourceLocationDao resourceLocationDao) {
		this.resourceLocationDao = resourceLocationDao;
	}
	public IVendorPolicyDao getVendorPolicyDao() {
		return vendorPolicyDao;
	}
	public void setVendorPolicyDao(IVendorPolicyDao vendorPolicyDao) {
		this.vendorPolicyDao = vendorPolicyDao;
	}
	public IVendorFinanceDao getVendorFinanceDao() {
		return vendorFinanceDao;
	}
	public void setVendorFinanceDao(IVendorFinanceDao vendorFinanceDao) {
		this.vendorFinanceDao = vendorFinanceDao;
	}
	public IActivityRegistryDao getActivityRegistryDao() {
		return activityRegistryDao;
	}
	public void setActivityRegistryDao(IActivityRegistryDao activityRegistryDao) {
		this.activityRegistryDao = activityRegistryDao;
	}
	public IAuditBO getAuditBO() {
		return auditBO;
	}
	public void setAuditBO(IAuditBO auditBO) {
		this.auditBO = auditBO;
	}
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	public IVendorHdrDao getVendorHdrDao() {
		return vendorHdrDao;
	}
	public void setVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		this.vendorHdrDao = vendorHdrDao;
	}
	public IProviderEmailBO getProviderEmailBO() {
		return providerEmailBO;
	}
	public void setProviderEmailBO(IProviderEmailBO providerEmailBO) {
		this.providerEmailBO = providerEmailBO;
	}
	
	
	
}

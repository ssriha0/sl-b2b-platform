package com.newco.marketplace.business.businessImpl.provider;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.daoImpl.leadsmanagement.LeadManagementDaoFactory;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.IContactMethodPrefDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IResourceLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorContactDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorFinanceDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorPolicyDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.FinanceProfile;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorLocation;
import com.newco.marketplace.vo.provider.VendorPolicy;
import com.newco.marketplace.vo.provider.VendorResource;


/**
 * $Revision: 1.29 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 */

/*
 * Maintenance History: see bottom of file
 */
public class RegistrationBOImpl extends ABaseBO implements IRegistrationBO {

	private static final Logger logger = Logger
			.getLogger(RegistrationBOImpl.class.getName());
	private IContactDao iContactDao;
	private IContactMethodPrefDao iContactMethodPrefDao;
	private ILocationDao iLocationDao;
	private IResourceLocationDao iResourceLocationDao;
	private ILookupDAO iLookupDAO;
	private LookupDao commonLookkupDAO; 
	private IUserProfileDao iUserProfileDao;
	private IVendorContactDao iVendorContactDao;
	private IVendorHdrDao iVendorHdrDao;
	private IVendorLocationDao iVendorLocationDao;
	private IVendorResourceDao iVendorResourceDao;
	private String defaultSourceId;
	private IProviderEmailBO iProviderEmailBO;
	private IVendorFinanceDao iVendorFinanceDao;
	private IVendorPolicyDao iVendorPolicyDao;
	private IActivityRegistryDao activityRegistryDao;
	private IZipDao zipDao;
	private SecurityDAO securityDao;
	private IAuditBO auditBO;
	private LeadManagementDaoFactory leadManagementDaoFactory;
	
	public String getDefaultSourceId() {
		return defaultSourceId;
	}

	private static final int ADDRESS_TYPE_HOME = 3;

	public RegistrationBOImpl(IContactDao iContactDao,
			IContactMethodPrefDao iContactMethodPrefDao,
			ILocationDao iLocationDao,
			IResourceLocationDao iResourceLocationDao, ILookupDAO iLookupDAO,
			IUserProfileDao iUserProfileDao,
			IVendorContactDao iVendorContactDao, IVendorHdrDao iVendorHdrDao,
			IVendorLocationDao iVendorLocationDao,
			IVendorResourceDao iVendorResourceDao,
			IProviderEmailBO iProviderEmailBO,
			IVendorFinanceDao iVendorFinanceDao,
			IVendorPolicyDao iVendorPolicyDao,
			IActivityRegistryDao activityRegistryDao,
			IZipDao zipDao,
			LookupDao commonLookkupDAO
			) {

		this.iContactDao = iContactDao;
		this.iContactMethodPrefDao = iContactMethodPrefDao;
		this.iLocationDao = iLocationDao;
		this.iResourceLocationDao = iResourceLocationDao;
		this.iLookupDAO = iLookupDAO;
		this.iUserProfileDao = iUserProfileDao;
		this.iVendorContactDao = iVendorContactDao;
		this.iVendorHdrDao = iVendorHdrDao;
		this.iVendorLocationDao = iVendorLocationDao;
		this.iVendorResourceDao = iVendorResourceDao;
		this.iProviderEmailBO = iProviderEmailBO;
		this.iVendorFinanceDao = iVendorFinanceDao;
		this.iVendorPolicyDao = iVendorPolicyDao;
		this.activityRegistryDao = activityRegistryDao;
		this.zipDao = zipDao;
		this.commonLookkupDAO = commonLookkupDAO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IRegistrationBO#loadRegistration(com.newco.marketplace.vo.provider.ProviderRegistrationVO)
	 */
	public ProviderRegistrationVO loadRegistration(
			ProviderRegistrationVO providerRegistrationVO)
			throws BusinessServiceException {

		try {
			providerRegistrationVO.setHowDidYouHearList(iLookupDAO.loadReferrals());
			providerRegistrationVO.setPrimaryIndList(iLookupDAO.loadPrimaryIndustry());
			providerRegistrationVO.setRoleWithinCompany(commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID));
		} catch (DBException ex) {
			logger.info("DB Exception @RegistrationBOImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @RegistrationBOImpl.loadRegistration() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @RegistrationBOImpl.loadRegistration() due to "
							+ ex.getMessage());
		}
		return providerRegistrationVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IRegistrationBO#saveRegistration(com.newco.marketplace.vo.provider.ProviderRegistrationVO)
	 */
	
	public ProviderRegistrationVO saveRegistration(
			ProviderRegistrationVO providerRegistrationVO)
			throws BusinessServiceException, DuplicateUserException {

		// if the request is empty, throw it back
		if (providerRegistrationVO == null) {
			logger.info("Registration request object was null.  Unable to register");
			throw new BusinessServiceException(
					"Request Can not be modified,Please Make Sure your request object has required values");
		}
		try {
			if (isUserIDFound(providerRegistrationVO.getUserName())) {
				String userIdError = ProviderConstants.ERROR_DUPLICATE_USER_ID;		
									throw new DuplicateUserException(userIdError+" User ID ("+
											providerRegistrationVO.getUserName()
											+ ") has been already used.");
								}
							} catch (DBException dae) {
			logger.info("DataAccessException thrown while looking up userId("
					+ providerRegistrationVO.getUserName() + ").");
			throw new BusinessServiceException(
					"DataAccessException thrown while looking up userId("
							+ providerRegistrationVO.getUserName() + ").", dae);
		}
		/**
		 * Commented for the Story:Enhancement-- Allow multiple user IDs to one email address [id=24957] 
		 */
//				try {
//							if (isEmailIDFound(providerRegistrationVO.getEmail())) {
//								String userEmailError = ProviderConstants.ERROR_DUPLICATE_USER_EMAIL;				
//								throw new DuplicateUserException(userEmailError+" The EMAIL ID("+
//										providerRegistrationVO.getEmail()+") has already been used.");
//								}
//							
//						} catch (DBException dae) {
//							logger.info("DataAccessException thrown while looking up EMAIL ID ("
//									+ providerRegistrationVO.getEmail() + ").");
//							throw new BusinessServiceException(
//									"DataAccessException thrown while looking up getEmail("
//											+ providerRegistrationVO.getEmail() + ").", dae);
//				}


		try {
			providerRegistrationVO = doRegister(providerRegistrationVO);

		} catch (DBException dae) {
			throw new BusinessServiceException(
					"DB Layer Exception Occurred while inserting the values",
					dae);
		} catch (EmailSenderException ese) {
			throw new BusinessServiceException(
					"Exception Occured While Sending the E-mail", ese);

		} catch (Exception e) {
			throw new BusinessServiceException(
					"General Exception Occured while Registering", e);

		}

		return providerRegistrationVO;
	}

	/**
	 * @param userID
	 * @return
	 * @throws DBException
	 */
	private boolean isUserIDFound(String userID) throws DBException {
		UserProfile findUserProfile = new UserProfile();
		UserProfile returnUserProfile = null;
		findUserProfile.setUserName(userID);
		
		returnUserProfile = iUserProfileDao.query(findUserProfile);
		return ((returnUserProfile == null) ? false : true);

	}

	
	/**
	 * @param email
	 * @return
	 * @throws DBException
	 */
	private boolean isEmailIDFound(String email) throws DBException {
		Contact findContact = new Contact();
		Contact returnContact = new Contact();
		findContact.setEmail(email);
		returnContact = iContactDao.queryPvalidateEmail(findContact);
		return ((returnContact == null) ? false : true);
	}

	/**
	 * @param providerRegistrationVO
	 * @return
	 * @throws DBException
	 * @throws DataAccessException
	 * @throws DataServiceException
	 * @throws EmailSenderException
	 * @throws AuditException
	 */
	private ProviderRegistrationVO doRegister(
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
		password = CryptoUtil.encrypt(password);
		logger.info("Entering ProviderRegistrationResponse.doRegister()");
		try {
			logger.info("Saving vendor header for registering business");
			vendorHeader.setSourceSystemId(getDefaultSourceId());
			vendorHeader.setBusinessName(providerRegistrationVO
					.getLegalBusinessName());
			vendorHeader.setVendorStatusId(1);
			if(providerRegistrationVO.getPrimaryIndustry()!=null&&providerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				vendorHeader.setPrimaryIndustryId(Integer.parseInt(providerRegistrationVO.getPrimaryIndustry()));
				vendorHeader.setOtherPrimaryService(providerRegistrationVO.getOtherPrimaryService());
			}
			tempVendorHeader = iVendorHdrDao.insert(vendorHeader);
			prvRegResponse.setVendorId(tempVendorHeader.getVendorId());
		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() due to"
					+ dae.getMessage());
			throw dae;
		}

		// Create the contact record
		try {
			logger.info("Saving contact for registering business");
			contact.setLastName(providerRegistrationVO.getLastName());
			contact.setFirstName(providerRegistrationVO.getFirstName());
			contact.setMi(providerRegistrationVO.getMiddleName());
			contact.setSuffix(providerRegistrationVO.getNameSuffix());

			//Added Job title
			contact.setTitle(providerRegistrationVO.getJobTitle());
			//Modified Role within company
			contact.setRole(providerRegistrationVO.getRoleWithinCom());
			//Added Alternate Email
			contact.setAltEmail(providerRegistrationVO.getAltEmail());

			contact.setEmail(providerRegistrationVO.getEmail());
			contact.setHonorific("");
			
			//Added to insert Business phone number into CONTACT table
			//author - bnatara
			//Added a new check for registration from API as part of SL-18865.
			if(providerRegistrationVO.isRegisterProviderUsingAPI()){
				contact.setPhoneNo(providerRegistrationVO.getMainBusiPhoneNo1());
			}else{
				String phone1 = providerRegistrationVO.getMainBusiPhoneNo1();
				String phone2 = providerRegistrationVO.getMainBusiPhoneNo2();
				String phone3 = providerRegistrationVO.getMainBusiPhoneNo3();
				
				if (phone1 != null && phone1.trim().length() > 0
				&&	phone2 != null && phone2.trim().length() > 0
				&&	phone3 != null && phone3.trim().length() > 0)
				{
					contact.setPhoneNo(phone1.trim() + phone2.trim() + phone3.trim());
				}
			}
			
			
			if (providerRegistrationVO.getMainBusinessExtn() !=  null
			&&	providerRegistrationVO.getMainBusinessExtn().trim().length() > 0)
			{
				contact.setExt(providerRegistrationVO.getMainBusinessExtn().trim());
			}
			
			iContactDao.insert(contact);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving contact details due to"
							+ dae.getMessage());
			throw dae;
		}

		// Create the user_profile.
		try {
			logger.info("Saving user profile for registering business");
			userProfile.setUserName(providerRegistrationVO.getUserName());
			userProfile.setContactId(contact.getContactId());


			if (password != null) {
				userProfile.setPassword(password);
			}
			userProfile.setPasswordFlag(1);


			prvRegResponse.setPassword(userProfile.getPassword());
			prvRegResponse.setUserName(providerRegistrationVO.getUserName());

			userProfile.setAnswerTxt("");
			userProfile.setQuestionId(0);

			userProfile.setRoleId(OrderConstants.PROVIDER_ROLEID);
			userProfile.setRoleName(MPConstants.ROLE_PROVIDER_ADMIN);
			iUserProfileDao.insert(userProfile);

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
			logger.info("Saving location for registering business");
			location.setLocnTypeId(ADDRESS_TYPE_HOME);
			iLocationDao.insert(location);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		// Create vendor resource
		try {
			logger.info("Saving vendor resource for registering business");

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
			
			//Setting manager_ind, other_ind, service_provider_ind as 0
			vendorResource.setManagerInd(0);
			vendorResource.setOtherInd(0);
			vendorResource.setSproInd(0);
			
			vendorResource = iVendorResourceDao.insert(vendorResource);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Vendor Resource Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}
		// create vendor Resource location record
		try {
			logger.info("Saving Resource location for registering business");
			resourceLocation.setLocationId(location.getLocnId());
			resourceLocation.setResourceId(vendorResource.getResourceId());
			this.iResourceLocationDao.insert(resourceLocation);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Resource location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		// create Bcak Ground Ckeck location record
		try {
			logger.info("Saving Resource location for registering business");


			// set the backGround Status
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
	        tmbcVO.setResourceId(vendorResource.getResourceId().toString());
	        tmbcVO.setWfEntity("Team Member Background Check");
	        tmbcVO.setBackgroundCheckStatus("Not Started");
	        iVendorResourceDao.updateBackgroundCheckStatus(tmbcVO);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving backGround Check location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		try {
			logger.info("Saving vendor header for registering business");

			/**
			 * Values not entered at this point of time
			 */
			vendorHeader.setBusinessTypeId(null);
			vendorHeader.setCompanySizeId(null);
			/*Commented as part of IPR SL-16934*/
			/*
			if (null==providerRegistrationVO.getDBAName() || providerRegistrationVO.getDBAName().trim().length()==0){
				vendorHeader.setDbaName(providerRegistrationVO.getLegalBusinessName());	
			}
			else{
				vendorHeader.setDbaName(providerRegistrationVO.getDBAName());
			}
			*/
			if(!StringUtils.isBlank(providerRegistrationVO.getDBAName())){
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
			vendorHeader.setReferralId(new Integer(providerRegistrationVO
					.getHowDidYouHear()));

			vendorHeader.setPromotionCode(providerRegistrationVO
					.getPromotionCode());

			vendorHeader.setCompanySizeId(null);
			vendorHeader.setNoCredInd(null);

			vendorHeader.setTaxStatus(null);
			vendorHeader.setForeignOwnedInd(null);
			vendorHeader.setForeignOwnedPct(null);

			/**
			 * Added Vendor Business Phone number, Extension and Fax Number
			 */
			vendorHeader.setBusinessPhone(providerRegistrationVO.getMainBusiPhoneNo1()
					+ providerRegistrationVO.getMainBusiPhoneNo2()
					+ providerRegistrationVO.getMainBusiPhoneNo3());
			vendorHeader.setBusPhoneExtn(providerRegistrationVO.getMainBusinessExtn());
			vendorHeader.setBusinessFax(providerRegistrationVO.getBusinessFax1()
					+ providerRegistrationVO.getBusinessFax2()
					+ providerRegistrationVO.getBusinessFax3());
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
			iVendorHdrDao.update(vendorHeader);

			VendorLocation vendorLocation = new VendorLocation();
			location = new Location();
			location.setStreet1(providerRegistrationVO.getBusinessStreet1());
			location.setStreet2(providerRegistrationVO.getBusinessStreet2());
			location.setCity(providerRegistrationVO.getBusinessCity());
			location.setStateCd(providerRegistrationVO.getBusinessState());

			location.setZip(providerRegistrationVO.getBusinessZip());
			location.setAptNo(providerRegistrationVO.getBusinessAprt());
			location.setLocnTypeId(1);

			location = iLocationDao.insert(location);

			vendorLocation.setVendorId(vendorHeader.getVendorId());
			vendorLocation.setLocationId(location.getLocnId());
			iVendorLocationDao.insert(vendorLocation);
			// insert mailing address

			location = new Location();
			location.setStreet1(providerRegistrationVO.getMailingStreet1());
			location.setStreet2(providerRegistrationVO.getMailingStreet2());
			location.setCity(providerRegistrationVO.getMailingCity());
			location.setStateCd(providerRegistrationVO.getMailingState());

			location.setZip(providerRegistrationVO.getMailingZip());
			location.setAptNo(providerRegistrationVO.getMailingAprt());
			location.setLocnTypeId(2);
			// Mailing address

			location = iLocationDao.insert(location);

			VendorLocation vendorLocation2 = new VendorLocation();
			vendorLocation2.setVendorId(vendorHeader.getVendorId());
			vendorLocation2.setLocationId(location.getLocnId());
			iVendorLocationDao.insert(vendorLocation2);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		prvRegResponse.setVendorContactResourceId(vendorResource
				.getResourceId());

		// Inserting Into vendor Policy
		VendorPolicy vendorPolicy = new VendorPolicy();
		try {
			logger.info("Saving vendor Policy for registering business");
			vendorPolicy.setVendorId(vendorHeader.getVendorId());
			// following are hard Coded
			vendorPolicy.setWarPeriod(0);
			vendorPolicy.setWarrentyMessuare(null);

			iVendorPolicyDao.insert(vendorPolicy);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Policy  details due to"
							+ dae.getMessage());
			throw dae;
		}


		// Inserting Into vendor Finanace
		FinanceProfile vendorFinance = new FinanceProfile();
		try {
			logger.info("Saving Vendor Finance for registering business");
			vendorFinance.setVendorId(vendorHeader.getVendorId());
			iVendorFinanceDao.insert(vendorFinance);

		}
		catch (DBException dae) {
			logger.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Finance  details due to"
							+ dae.getMessage());
			throw dae;
		}


		try{
			Integer vendorId = prvRegResponse.getVendorId();
			activityRegistryDao.insertActivityStatus(vendorId.toString());

			String resourceId = prvRegResponse.getVendorContactResourceId().toString();
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
		/**
		 * Need to be changed - EMail is not sent as per the code
		 */
		System.out.println("Not sending mail through velocity context:::::::::::::::::::::::::");
		// Commented since configured through cheetah mail.
		//uncommented
		prvRegResponse=doSendFinalEmailMessage(providerRegistrationVO);
		prvRegResponse.setVendorId(tempVendorHeader.getVendorId());
		prvRegResponse.setVendorContactResourceId(vendorResource
				.getResourceId());

		return prvRegResponse;

	}

	/**
	 * @param providerRegistrationVO
	 * @return
	 * @throws EmailSenderException
	 * @throws DBException
	 */
	private ProviderRegistrationVO doSendFinalEmailMessage(
			ProviderRegistrationVO providerRegistrationVO)
			throws EmailSenderException,DBException{
		// send the confirmation email
		try {
			if(!isSelectedStateValid(providerRegistrationVO.getBusinessState())){
				logger.info("----State is Valid I m sending Password and username to you-----");
				iProviderEmailBO.sendConfirmationMail(providerRegistrationVO
						.getUserName(), providerRegistrationVO.getPassword(),
						providerRegistrationVO.getEmail(),providerRegistrationVO.getFirstName());
				providerRegistrationVO.setValidateState(false);
			}else{
				//Send the Different Email from different Template
				logger.info("----State is InValid I m not sending Password but username -----");
				providerRegistrationVO.setValidateState(true);
				iProviderEmailBO.sendConfirmationMailForInValidState(providerRegistrationVO.getUserName(), providerRegistrationVO.getPassword(),	providerRegistrationVO.getEmail());
			}

		}
		catch (DBException dbe){
			logger.info("DB Exception while sending an e-mail Exception thrown sending email");
			throw dbe;
		}
		catch (Throwable t) {
			logger.info("throwale thrown sending email");
			t.printStackTrace();
			throw new EmailSenderException(t);

		}
		
		return providerRegistrationVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IRegistrationBO#loadZipSet(com.newco.marketplace.vo.provider.ProviderRegistrationVO)
	 */
	public ProviderRegistrationVO loadZipSet(
			ProviderRegistrationVO providerRegistrationVO)
			throws BusinessServiceException {

		try {

			List stateTypeList = null;
			if (providerRegistrationVO.getStateType() != null
			&& 	providerRegistrationVO.getStateType().length() > 0
			&&  providerRegistrationVO.getStateType().equalsIgnoreCase("business"))
			{
				stateTypeList = zipDao.queryList(providerRegistrationVO.getBusinessState());
			}

			if (providerRegistrationVO.getStateType() != null
			&& 	providerRegistrationVO.getStateType().length() > 0
			&&  providerRegistrationVO.getStateType().equalsIgnoreCase("mail"))
							{
				stateTypeList = zipDao.queryList(providerRegistrationVO.getMailingState());
			}
			providerRegistrationVO.setStateTypeList(stateTypeList);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @RegistrationBOImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @RegistrationBOImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		return providerRegistrationVO;
	}
  //TODO - un commnet once CRM details are finalized SL 16943 Method to call CRM API
	/*public CreateProviderAccountResponse doCrmRegistration(String request)
	{
		CreateProviderAccountResponse prvRegResponse = new CreateProviderAccountResponse();
		prvRegResponse=getLeadManagementDaoFactory().getLeadDAO(CRMDAO).doCrmRegistration(request);
		return prvRegResponse;
	}*/
	
	public LeadManagementDaoFactory getLeadManagementDaoFactory() {
		return leadManagementDaoFactory;
	}

	public void setLeadManagementDaoFactory(
			LeadManagementDaoFactory leadManagementDaoFactory) {
		this.leadManagementDaoFactory = leadManagementDaoFactory;
	}

	public SecurityDAO getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(SecurityDAO securityDao) {
		this.securityDao = securityDao;
	}

	public IAuditBO getAuditBO() {
		return auditBO;
	}

	public void setAuditBO(IAuditBO auditBO) {
		this.auditBO = auditBO;
	}
	
	
	public void setCommonLookkupDAO(LookupDao commonLookkupDAO) {
		this.commonLookkupDAO = commonLookkupDAO;
	}

	/***
	 * returns true if the passed state is active else returns true
	 * @param selectedState
	 * @return
	 * @throws DBException
	 */
	private boolean isSelectedStateValid(String selectedState) throws DBException{
		boolean stateActive=true;
	   try{
		   LookupVO vo=iLookupDAO.isStateActive(selectedState);
		   if(vo!=null&& vo.getId()!=null){
			   stateActive=false; 
		   }
	   }catch(DBException dbe){
		   logger
			.info("General Exception @RegistrationBOImpl.isSelectedStateValid() due to"
					+ dbe.getMessage());
		   throw dbe;
	   }
	   return stateActive;
	}
	
	/***
	 * returns true if the passed state is active else returns true
	 * @param selectedState
	 * @return
	 */
	public boolean validateUserName(String userName){
		boolean duplicate = false;
		try{
			duplicate = isUserIDFound(userName);
			
		}catch(DBException dbe){
			logger.info("General Exception @RegistrationBOImpl.validateUserName() due to"
						+ dbe.getMessage());
		}
		return duplicate;
	}

	public List getStates(){
		List states= null;
		try{
			states=iLookupDAO.loadStates();
		}
		catch(DBException dbe){
			logger.info("General Exception @RegistrationBOImpl.getStates() due to"
						+ dbe.getMessage());
		}
		  return states;
	}
}
/*
 * Maintenance History
 * $Log: RegistrationBOImpl.java,v $
 * Revision 1.29  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.24.6.2  2008/04/23 11:42:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.28  2008/04/23 07:04:53  glacy
 * Shyam: Re-merge of I19_FreeTab branch to HEAD.
 *
 * Revision 1.25.6.1  2008/04/21 13:16:59  paugus2
 * Provider registration issue
 * 
 * Revision 1.27  2008/04/23 05:01:45  hravi
 * Reverting to build 247.
 * 
 * Revision 1.24.6.1  2008/04/01 21:52:55  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.25  2008/03/27 18:58:37  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.24.10.1  2008/03/10 18:39:53  mhaye05
 * updated to use common LookupDao class and handle role_id in lu_company_role table
 *
 * Revision 1.24  2008/02/26 18:20:57  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.23  2008/02/21 03:26:47  hrajago
 * Sears00048036
 *
 * Revision 1.22.2.1  2008/02/22 00:43:05  hrajago
 * Added to insert Business phone number into CONTACT table
 *
 * Revision 1.22  2008/02/15 02:41:04  hrajago
 * Allow multiple user IDs to one email address
 *
 * Revision 1.20  2008/02/11 21:31:02  mhaye05
 * removed statesList attributes
 *
 * Revision 1.19  2008/02/07 18:48:25  mhaye05
 * updated constants class
 *
 * Revision 1.18  2008/02/06 20:08:34  hravi
 *  duplicate error (email and user id)
 *
 * Revision 1.17  2008/02/06 19:38:53  mhaye05
 * merged with Feb4_release branch
 *
 * Revision 1.16  2008/02/06 12:49:11  hrajago
 * Added code to apt_no in the DB and added null condition for primary industry before parsing
 *
 * Revision 1.15  2008/02/05 22:28:41  mhaye05
 * removed commented out code
 *
 * Revision 1.14.6.3  2008/02/06 17:38:22  mhaye05
 * all transactions are not handled by Spring AOP.  no more beginWork!!
 *
 * Revision 1.14.6.2  2008/02/06 13:02:14  hrajago
 * Added null check for primary industry, added code to insert apart no in location table
 * 
 * Revision 1.14.6.1  2008/02/05 21:57:47  mhaye05
 * cleaned up class
 *
 */

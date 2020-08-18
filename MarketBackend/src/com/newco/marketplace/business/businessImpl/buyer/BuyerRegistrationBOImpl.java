package com.newco.marketplace.business.businessImpl.buyer;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.businessImpl.provider.RegistrationBOImpl;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerEmailBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.dto.vo.buyer.BuyerUserProfile;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerCancellationPostingFeeVO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceDao;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceLocationDao;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerUserProfileDao;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.newco.marketplace.vo.provider.TemplateVo;
import com.newco.marketplace.vo.simple.CreateServiceOrderCreateAccountVO;
import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;
import com.newco.marketplace.vo.buyer.BuyerResourceVO;


/**
 * @author nsanzer
 *
 */
public class BuyerRegistrationBOImpl extends ABaseBO implements IBuyerRegistrationBO{
	
	private static final Logger logger = Logger
		.getLogger(RegistrationBOImpl.class.getName());
	private ContactDao iContactDao;
	private ILocationDao iLocationDao;
	private IBuyerResourceLocationDao iBuyerResourceLocationDao;
	private ILookupDAO iLookupDAO;
	private LookupDao commonLookkupDAO; 
	private IBuyerUserProfileDao iBuyerUserProfileDao;
	private BuyerDao iBuyerDao;
	private IBuyerResourceDao iBuyerResourceDao;
	private String defaultSourceId;
	private IBuyerEmailBO iBuyerEmailBO;
	private IActivityRegistryDao activityRegistryDao;
	private IZipDao zipDao;
	private SecurityDAO securityDao;
	private IAuditBO auditBO;
	private ITemplateDao templateDao = null;
	/** walletInterface. */
	private IWalletBO wallet;
	private WalletRequestBuilder walletReqBuilder = new WalletRequestBuilder();


	public String getDefaultSourceId() {
		return defaultSourceId;
	}
	public BuyerRegistrationBOImpl(ContactDao iContactDao,
			ILocationDao iLocationDao,
			IBuyerResourceLocationDao iResourceLocationDao,
			ILookupDAO iLookupDAO,
			IBuyerUserProfileDao iBuyerUserProfileDao,
			BuyerDao iBuyerDao,
			IBuyerResourceDao iBuyerResourceDao,
			IBuyerEmailBO iBuyerEmailBO,
			IActivityRegistryDao activityRegistryDao,
			IZipDao zipDao,
			LookupDao commonLookkupDAO) {

		this.iContactDao = iContactDao;
		this.iLocationDao = iLocationDao;
		this.iBuyerResourceLocationDao = iResourceLocationDao;
		this.iLookupDAO = iLookupDAO;
		this.iBuyerUserProfileDao = iBuyerUserProfileDao;
		this.iBuyerDao = iBuyerDao;
		this.iBuyerResourceDao = iBuyerResourceDao;
		this.iBuyerEmailBO = iBuyerEmailBO;
		this.activityRegistryDao = activityRegistryDao;
		this.zipDao = zipDao;
		this.commonLookkupDAO = commonLookkupDAO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO#loadRegistration(com.newco.marketplace.vo.provider.ProviderRegistrationVO)
	 */
	public BuyerRegistrationVO loadRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException {

		try {
			buyerRegistrationVO.setTermsAndConditions((iLookupDAO.loadTermsConditions("Buyer Agreement")).getDescr());
			buyerRegistrationVO.setHowDidYouHearList(iLookupDAO.loadReferrals());
			buyerRegistrationVO.setPrimaryIndList(iLookupDAO.loadBuyerPrimaryIndustry());
			buyerRegistrationVO.setRoleWithinCompanyList(commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID));
			buyerRegistrationVO.setBusinessStructureList(iLookupDAO.loadBusinessTypes());
			buyerRegistrationVO.setAnnualSalesRevenueList(iLookupDAO.loadSalesVolume());
			buyerRegistrationVO.setStateTypeList(iLookupDAO.loadStates());
			buyerRegistrationVO.setSizeOfCompanyList(iLookupDAO.loadCompanySize());
			
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
		return buyerRegistrationVO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO#loadData(com.newco.marketplace.vo.provider.ProviderRegistrationVO)
	 */
	public BuyerRegistrationVO loadData(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException {

		try {
			Integer buyerId = buyerRegistrationVO.getBuyerId();
			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerId);
			buyer = iBuyerDao.loadData(buyer);
			Integer locIdBus = buyer.getPriLocnId();
			Integer locIdMailing = buyer.getBillLocnId();
			Location locationBusiness = new Location(); 
			Location locationMailing = new Location();
			locationBusiness.setLocnId(locIdBus);
			locationMailing.setLocnId(locIdMailing);
			locationBusiness = iLocationDao.query(locationBusiness);
			if(locIdBus != null && locIdMailing!= null && locIdMailing.equals(locIdBus))
			{
				locationMailing = locationBusiness;
				buyerRegistrationVO.setMailAddressChk(true);
			}
			else if(locIdMailing != null && locIdMailing != 0)
			{
				locationMailing = iLocationDao.query(locationMailing);
				buyerRegistrationVO.setMailAddressChk(false);
			}
			buyerRegistrationVO = formatPhoneNumbers(buyer,buyerRegistrationVO);
			buyerRegistrationVO.setBuyerId(buyer.getBuyerId());
			buyerRegistrationVO.setBusinessName(buyer.getBusinessName());
			buyerRegistrationVO.setPrimaryIndustry(buyer.getPrimaryIndustry());
			buyerRegistrationVO.setPhoneExtn(buyer.getBusExtn());
			if(buyer.getPrimaryIndustry()!=null && buyer.getPrimaryIndustry().trim().length()>0){
				buyer.setPrimaryIndustry(buyer.getPrimaryIndustry());
			buyerRegistrationVO.setBusinessStructure(buyer.getBusinessType());
			buyerRegistrationVO.setSizeOfCompany(buyer.getCompanySize());
			buyerRegistrationVO.setAnnualSalesRevenue(buyer.getSalesVolume());
			buyerRegistrationVO.setWebsiteAddress(buyer.getWebAddress());
			}
			//Sets primary location details into VO
			buyerRegistrationVO.setBusinessStreet1(locationBusiness.getStreet1());
			buyerRegistrationVO.setBusinessStreet2(locationBusiness.getStreet2());
			buyerRegistrationVO.setBusinessCity(locationBusiness.getCity());
			buyerRegistrationVO.setBusinessState(locationBusiness.getStateCd());
			buyerRegistrationVO.setBusinessZip(locationBusiness.getZip());
			buyerRegistrationVO.setBusinessAprt(locationBusiness.getAptNo());
			
			// Sets mailing location details into VO
			buyerRegistrationVO.setMailingStreet1(locationMailing.getStreet1());
			buyerRegistrationVO.setMailingStreet2(locationMailing.getStreet2());
			buyerRegistrationVO.setMailingCity(locationMailing.getCity());
			buyerRegistrationVO.setMailingState(locationMailing.getStateCd());
			buyerRegistrationVO.setMailingZip(locationMailing.getZip());
			buyerRegistrationVO.setMailingAprt(locationMailing.getAptNo());
			
		}catch (DBException ex) {
			logger.info("DB Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @BuyerRegistrationBOImpl.loadData() due to "
							+ ex.getMessage());
		}
		return buyerRegistrationVO;
	}

	public BuyerRegistrationVO loadListData(
			BuyerRegistrationVO buyerRegistrationVO,Integer buyerId)
			throws BusinessServiceException {

		try {
			Buyer tempBuyer = new Buyer();
			tempBuyer.setBuyerId(buyerId);
			tempBuyer = iBuyerDao.loadData(tempBuyer);
			if(tempBuyer!=null)
			{
				String businessDate = tempBuyer.getBusinessStarted();
				if(businessDate!=null && !businessDate.equals(""))
				{
					String strDate[] = businessDate.split(" ");
					if(strDate!=null && strDate.length>0)
						buyerRegistrationVO.setBusinessStarted(strDate[0]);
					else
						buyerRegistrationVO.setBusinessStarted("");
				}
			}
			buyerRegistrationVO.setHowDidYouHearList(iLookupDAO.loadReferrals());
			buyerRegistrationVO.setPrimaryIndList(iLookupDAO.loadBuyerPrimaryIndustry());
			buyerRegistrationVO.setRoleWithinCompanyList(commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID));
			buyerRegistrationVO.setBusinessStructureList(iLookupDAO.loadBusinessTypes());
			buyerRegistrationVO.setAnnualSalesRevenueList(iLookupDAO.loadSalesVolume());
			buyerRegistrationVO.setStateTypeList(iLookupDAO.loadStates());
			buyerRegistrationVO.setSizeOfCompanyList(iLookupDAO.loadCompanySize()); 
			buyerRegistrationVO.setTermsAndConditions(iLookupDAO.loadTermsConditions("Buyer Agreement").getDescr());
		
// Changes for for Buyer Admin Name starts SL-20461-->
			
			logger.info("buyerRegistrationVO Buyer Id"+buyerId);
		if (StringUtils.isNumeric(buyerId.toString())) {
			
				List tempList = iLookupDAO.loadBuyerList(buyerId);
				buyerRegistrationVO.setBuyerList(tempList);
				
				logger.info("buyerRegistrationVO.getBuyerList"
						+ buyerRegistrationVO.getBuyerList());
				
		}
			List<BuyerResourceVO> result =(List<BuyerResourceVO>)iLookupDAO.loadOldBuyerDetails(buyerId);
			
			logger.info("buyerRegistrationVO.getBuyerResourceVO"+result);
			if(null!=result)
			{
				
				for (BuyerResourceVO resource : result) {
					
				
			buyerRegistrationVO.setCurrentAdminResourceId(resource.getResourceId().toString());
			buyerRegistrationVO.setCurrentAdminFirstname(resource.getFirstName().toString());
			buyerRegistrationVO.setCurrentAdminLastName(resource.getLastName().toString());
			buyerRegistrationVO.setCurrentAdminUserName(resource.getUserName().toString());
			logger.info("buyerRegistrationVO.getBuyerResourceVO CurrentAdminResourceId"+buyerRegistrationVO.getCurrentAdminUserName());
				}
			}
			
			
			// Changes for for Buyer Admin Name ends SL-20461 -->
			
			
		}catch (DBException ex) {
			logger.info("DB Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();	
			logger.info("General Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @BuyerRegistrationBOImpl.loadData() due to "
							+ ex.getMessage());
		}
		return buyerRegistrationVO;
	}
	
	public BuyerRegistrationVO loadListData(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException {
		try {
			buyerRegistrationVO.setHowDidYouHearList(iLookupDAO.loadReferrals());
			buyerRegistrationVO.setPrimaryIndList(iLookupDAO.loadBuyerPrimaryIndustry());
			buyerRegistrationVO.setRoleWithinCompanyList(commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID));
			buyerRegistrationVO.setBusinessStructureList(iLookupDAO.loadBusinessTypes());
			buyerRegistrationVO.setAnnualSalesRevenueList(iLookupDAO.loadSalesVolume());
			buyerRegistrationVO.setStateTypeList(iLookupDAO.loadStates());
			buyerRegistrationVO.setSizeOfCompanyList(iLookupDAO.loadCompanySize()); 
			buyerRegistrationVO.setTermsAndConditions(iLookupDAO.loadTermsConditions("Buyer Agreement").getDescr());
			buyerRegistrationVO.setServiceLiveBucksText(iLookupDAO.loadTermsConditions("ServiceLive Bucks").getDescr());
		}catch (DBException ex) {
			logger.info("DB Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();	
			logger.info("General Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @BuyerRegistrationBOImpl.loadData() due to "
							+ ex.getMessage());
		}
		return buyerRegistrationVO;
	}
	
	public void loadAccount(CreateServiceOrderCreateAccountVO accountVO) throws BusinessServiceException {
		try {
			List<LookupVO> lookupVOList = iLookupDAO.loadSimpleBuyerTermsConditions();
			for (LookupVO lookupVO : lookupVOList) {
				String termsCondType = lookupVO.getType();
				if (termsCondType.equalsIgnoreCase("Buyer Agreement")) {
					accountVO.setBuyerTermsAndConditionText(lookupVO.getDescr());
				} else if (termsCondType.equalsIgnoreCase("ServiceLive Bucks")) {
					accountVO.setSlBucksText(lookupVO.getDescr());
					if(lookupVO.getId() != null && StringUtils.isNumeric(lookupVO.getId())){
						accountVO.setSlBucksAgreeId(new Integer(lookupVO.getId()));
					}
				}
			}
		} catch (DBException dbEx) {
			logger.info("DB Exception @BuyerRegistrationBOImpl.loadAccount() due to" + dbEx.getMessage());
			throw new BusinessServiceException(dbEx.getMessage(), dbEx);
		}
	}
	
	public boolean updateBuyerCompanyProfile(BuyerRegistrationVO buyerRegistrationVO) 
			throws BusinessServiceException
	{
		try
		{
			Buyer buyer = new Buyer();
			Location location = new Location();
			int resultBuyer = 0;
			int resultBusLocation = 0;
			int resultMailingLocation = 0;
			int buyerAdmin =0,finalResult =0;   //code change for buyer admin change SL-20461
			buyer.setBuyerId(buyerRegistrationVO.getBuyerId());
			buyer.setBusinessName(buyerRegistrationVO.getBusinessName());
			buyer.setBusinessStarted(buyerRegistrationVO.getBusinessStarted());
			buyer.setPrimaryIndustry(buyerRegistrationVO.getPrimaryIndustry());
			buyer.setBusPhoneNo(buyerRegistrationVO.getPhoneAreaCode()+ buyerRegistrationVO.getPhonePart1()+ buyerRegistrationVO.getPhonePart2());
			buyer.setBusExtn(buyerRegistrationVO.getPhoneExtn());
			if(buyerRegistrationVO.getPrimaryIndustry()!=null && buyerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				buyer.setPrimaryIndustry(buyerRegistrationVO.getPrimaryIndustry());
			}
			buyer.setBusFaxNo(buyerRegistrationVO.getFaxAreaCode()+ buyerRegistrationVO.getFaxPart1()+ buyerRegistrationVO.getFaxPart2());
			buyer.setHowDidYouHear(buyerRegistrationVO.getHowDidYouHear());
			buyer.setCreatedDate(new Date(System.currentTimeMillis()));
			buyer.setModifiedDate(new Date(System.currentTimeMillis()));
		    buyer.setModifiedBy(buyerRegistrationVO.getUserName());
		    buyer.setBusinessType(buyerRegistrationVO.getBusinessStructure());
		    buyer.setCompanySize(buyerRegistrationVO.getSizeOfCompany());
		    buyer.setSalesVolume(buyerRegistrationVO.getAnnualSalesRevenue());
		    buyer.setWebAddress(buyerRegistrationVO.getWebsiteAddress());
		    buyer.setUserName(buyerRegistrationVO.getUserName());
		    Buyer currentBuyer = iBuyerDao.loadData(buyer);	    
		    resultBuyer = iBuyerDao.updateBuyerCompanyProfile(buyer);
			//update business address
			location = new Location();
			location.setLocnId(currentBuyer.getPriLocnId());
			location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_BUSINESS);
			location.setStreet1(buyerRegistrationVO.getBusinessStreet1());
			location.setStreet2(buyerRegistrationVO.getBusinessStreet2());
			location.setCity(buyerRegistrationVO.getBusinessCity());
			location.setStateCd(buyerRegistrationVO.getBusinessState());
			location.setZip(buyerRegistrationVO.getBusinessZip());
			location.setAptNo(buyerRegistrationVO.getBusinessAprt());
			resultBusLocation = iLocationDao.update(location);
			if (currentBuyer.getBillLocnId()!= null && currentBuyer.getBillLocnId() > 0)
			{
				// update mailing address
				location = new Location();
				location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_MAILING);
				if(buyerRegistrationVO.isMailAddressChk())
				{
					Buyer buyerMailing = new Buyer();
					buyerMailing.setBuyerId(currentBuyer.getBuyerId());
					buyerMailing.setBillLocnId(currentBuyer.getPriLocnId());
					resultMailingLocation = iBuyerDao.updateBuyerMailing(buyerMailing);
				}
				else if(currentBuyer.getPriLocnId().equals(currentBuyer.getBillLocnId()))
				{
					location.setStreet1(buyerRegistrationVO.getMailingStreet1());
					location.setStreet2(buyerRegistrationVO.getMailingStreet2());
					location.setCity(buyerRegistrationVO.getMailingCity());
					location.setStateCd(buyerRegistrationVO.getMailingState());
					location.setZip(buyerRegistrationVO.getMailingZip());
					location.setAptNo(buyerRegistrationVO.getMailingAprt());
					location = iLocationDao.insert(location);
					int locIdMailing = location.getLocnId();
					Buyer buyerMailing = new Buyer();
					buyerMailing.setBuyerId(currentBuyer.getBuyerId());
					buyerMailing.setBillLocnId(locIdMailing);
					iBuyerDao.updateBuyerMailing(buyerMailing);
					//Inserting mailing address record
					ResourceLocation resourceLocation = new ResourceLocation();
					resourceLocation.setLocationId(locIdMailing);
					resourceLocation.setBuyerId(buyer.getBuyerId());
					resourceLocation.setCreatedDate(new Date(System.currentTimeMillis()));
					resourceLocation.setModifiedDate(new Date(System.currentTimeMillis()));
					resourceLocation.setModifiedBy(buyerRegistrationVO.getUserName());
					this.iBuyerResourceLocationDao.insert(resourceLocation);
					resultMailingLocation = 1;
				}
				else
				{
					location.setLocnId(currentBuyer.getBillLocnId());
					location.setStreet1(buyerRegistrationVO.getMailingStreet1());
					location.setStreet2(buyerRegistrationVO.getMailingStreet2());
					location.setCity(buyerRegistrationVO.getMailingCity());
					location.setStateCd(buyerRegistrationVO.getMailingState());
					location.setZip(buyerRegistrationVO.getMailingZip());
					location.setAptNo(buyerRegistrationVO.getMailingAprt());
					resultMailingLocation = iLocationDao.update(location);
				}
			}
			else if(buyerRegistrationVO.getMailingZip()!= null && !buyerRegistrationVO.getMailingZip().equals("") && resultMailingLocation == 0)
			{
				if(buyerRegistrationVO.isMailAddressChk())
				{
					Buyer buyerMailing = new Buyer();
					buyerMailing.setBuyerId(currentBuyer.getBuyerId());
					buyerMailing.setBillLocnId(currentBuyer.getPriLocnId());
					resultMailingLocation = iBuyerDao.updateBuyerMailing(buyerMailing);
				}
				else
				{					
					location.setStreet1(buyerRegistrationVO.getMailingStreet1());
					location.setStreet2(buyerRegistrationVO.getMailingStreet2());
					location.setCity(buyerRegistrationVO.getMailingCity());
					location.setStateCd(buyerRegistrationVO.getMailingState());
					location.setZip(buyerRegistrationVO.getMailingZip());
					location.setAptNo(buyerRegistrationVO.getMailingAprt());
					location = iLocationDao.insert(location);
					int locIdMailing = location.getLocnId();
					Buyer buyerMailing = new Buyer();
					buyerMailing.setBuyerId(currentBuyer.getBuyerId());
					buyerMailing.setBillLocnId(locIdMailing);
					iBuyerDao.updateBuyerMailing(buyerMailing);
					//Inserting mailing address record
					ResourceLocation resourceLocation = new ResourceLocation();
					resourceLocation.setLocationId(locIdMailing);
					resourceLocation.setBuyerId(buyer.getBuyerId());
					resourceLocation.setCreatedDate(new Date(System.currentTimeMillis()));
					resourceLocation.setModifiedDate(new Date(System.currentTimeMillis()));
					resourceLocation.setModifiedBy(buyerRegistrationVO.getUserName());
					this.iBuyerResourceLocationDao.insert(resourceLocation);
					resultMailingLocation = 1;
				}
			}
			else
			{
				resultMailingLocation = 1;
			}
		    if(resultBuyer >0 && resultBusLocation>0 && resultMailingLocation>0 )
		    {
		    	finalResult = 1; //SL-20461 change
		    }
		    
		    //Code forBuyer admin name change starts Sl-20461
		    
		    if(finalResult>0 &&(null!=buyerRegistrationVO.getNewAdminUserName()) )
		    {
		    if(null!=buyerRegistrationVO.getNewAdminUserName())
		    {
		    	
		    	if(this.iBuyerDao.updateBuyerAdmin(buyerRegistrationVO))
		    		{
		    		return true;
		    		}
		    	
		    }
		    }
		    else if(finalResult>0)
		    {
		    	return true;
		    }
		    
		  
		    //Code forBuyer admin name change ends SL-20461
		}
		catch (DataServiceException ex) {
			logger.info("DB Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerRegistrationBOImpl.loadData() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @BuyerRegistrationBOImpl.loadData() due to "
							+ ex.getMessage());
		}
	    return false;
	}
	
	public BuyerRegistrationVO updateSimpleBuyerInformation (
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException {
		
		Integer buyerResourceId = buyerRegistrationVO.getBuyerContactResourceId();
		if(buyerResourceId == 0) {
			logger.error("Cannot modify simple buyer information without buyerResourceId");
			throw new BusinessServiceException("Cannot modify simple buyer information without buyerResourceId");
		}
		
		try {
			BuyerResource buyerResource = iBuyerResourceDao.getBuyerResourceInfo(buyerResourceId);
			buyerRegistrationVO.setBuyerId(buyerResource.getBuyerId());
			buyerRegistrationVO.setContactId(buyerResource.getContactId());
			buyerRegistrationVO.setLocationId(buyerResource.getLocnId());
		
		} catch (DBException e) {
			logger.error("Error in retriving buyer resource information for buyer resourceId: " + buyerResourceId);
			throw new BusinessServiceException("Error in retriving buyer resource information for " +
					"buyer resourceId: " + buyerResourceId + ", exception:\n" + e.getMessage());
		}
		
		boolean isDirty = false;
		Contact buyerContact = new Contact();
		buyerContact.setContactId(buyerRegistrationVO.getContactId());
		
		if(StringUtils.isNotEmpty(buyerRegistrationVO.getEmail())) {
			buyerContact.setEmail(buyerRegistrationVO.getEmail());
			isDirty = true;
		}
		if(StringUtils.isNotEmpty(buyerRegistrationVO.getFirstName())) {
			buyerContact.setFirstName(buyerRegistrationVO.getFirstName());
			isDirty = true;
		}
		if(StringUtils.isNotEmpty(buyerRegistrationVO.getLastName())) {
			buyerContact.setLastName(buyerRegistrationVO.getLastName());
			isDirty = true;
		}
		
		String phone1 = buyerRegistrationVO.getBusPhoneNo1();
		String phone2 = buyerRegistrationVO.getBusPhoneNo2();
		if (StringUtils.isNotEmpty(phone1)) {
			buyerContact.setPhoneNo(phone1.trim());
			isDirty=true;
		}
		if(StringUtils.isNotEmpty(phone2)) {
			buyerContact.setCellNo(phone2.trim());
			isDirty = true;
		}
		
		try {
			if(isDirty) {
				iContactDao.updateSimpleBuyer(buyerContact);
			}
		} catch(DataServiceException e) {
			logger.error("Error in updating contact information for buyer " +
					"resource, resourceId: " + buyerResourceId);
			throw new BusinessServiceException("Error in updating contact information " +
					"for buyer resource, resourceId: " + buyerResourceId + 
					", exception:\n" + e.getMessage());
		}
		
		try {
			Location location = new Location();
			location.setLocnId(buyerRegistrationVO.getLocationId());
			isDirty = false;
			
			location.setStreet1(buyerRegistrationVO.getBusinessStreet1());
			location.setStreet2(buyerRegistrationVO.getBusinessStreet2());
			location.setCity(buyerRegistrationVO.getBusinessCity());
			location.setStateCd(buyerRegistrationVO.getBusinessState());
			location.setZip(buyerRegistrationVO.getBusinessZip());
			iLocationDao.update(location);
		} catch(DBException e) {
			logger.error("Error in updating location information for buyer " +
					"resource, resourceId: " + buyerResourceId);
			throw new BusinessServiceException("Error in updating location information " +
					"for buyer resource, resourceId: " + buyerResourceId + 
					", exception:\n" + e.getMessage());
		}
		
		//Saving additional fields as part of FinCen
		try{
			Buyer buyer = new Buyer();
			buyer.setBuyerId(buyerRegistrationVO.getBuyerId());
			buyer.setEinNoEnc(buyerRegistrationVO.getEinNo());
			buyer.setDob(buyerRegistrationVO.getDateOfBirth());
			buyer.setAltIDDocType(buyerRegistrationVO.getAltIdType());
			buyer.setAltIDCountryIssue(buyerRegistrationVO.getAltIdCountry());
			if(buyerRegistrationVO.getSsnInd() != null){
				if(buyerRegistrationVO.getSsnInd().equals("0")){
					iBuyerDao.updateBuyerEIN(buyer);
				}else if(buyerRegistrationVO.getSsnInd().equals("1")){
					iBuyerDao.updateBuyerSSN(buyer);
				}else if(buyerRegistrationVO.getSsnInd().equals("2")){
					iBuyerDao.updateBuyerAltId(buyer);
				}
			}
		}catch(DataServiceException e){
			logger.error("Error in updating additional information for buyer " +
					"resource, resourceId: " + buyerResourceId);
			throw new BusinessServiceException("Error in updating additional information " +
					"for buyer resource, resourceId: " + buyerResourceId + 
					", exception:\n" + e.getMessage());
		}
		return buyerRegistrationVO;
	}
	
	public BuyerRegistrationVO saveSimpleBuyerRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException, DuplicateUserException {
		buyerRegistrationVO.setFundingTypeId(CommonConstants.CONSUMER_FUNDING_TYPE);
		return saveBuyerRegistration(buyerRegistrationVO);
	}
		
		public BuyerRegistrationVO saveProfBuyerRegistration(
				BuyerRegistrationVO buyerRegistrationVO)
				throws BusinessServiceException, DuplicateUserException {
			buyerRegistrationVO.setFundingTypeId(CommonConstants.PRE_FUNDING_TYPE); 
			return saveBuyerRegistration(buyerRegistrationVO);
		}
	
	public BuyerRegistrationVO saveBuyerRegistration(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException, DuplicateUserException {

		// if the request is empty, throw it back
		if (buyerRegistrationVO == null) {
			logger.error("Registration request object was null.  Unable to register");
			throw new BusinessServiceException(
					"Request Can not be modified,Please Make Sure your request object has required values");
		}
		try {
			if (isUserIDFound(buyerRegistrationVO.getUserName())) {
				String userIdError = ProviderConstants.ERROR_DUPLICATE_USER_ID;		
									throw new DuplicateUserException(userIdError+" User ID ("+
											buyerRegistrationVO.getUserName()
											+ ") has been already used.");
								}
							} catch (DBException dae) {
			logger.error("DataAccessException thrown while looking up userId("
					+ buyerRegistrationVO.getUserName() + ").");
			throw new BusinessServiceException(
					"DataAccessException thrown while looking up userId("
							+ buyerRegistrationVO.getUserName() + ").", dae);
		}
		try {
			buyerRegistrationVO = doRegister(buyerRegistrationVO);

		}catch (SLBusinessServiceException dae) {
				logger.error("Exception Occurred while activating buyer during registration",dae);
				throw new BusinessServiceException(
						"Exception Occurred while activating buyer during registration",
						dae);
		} 
		catch (DBException dae) {
			logger.error("DB Layer Exception Occurred while inserting the values",dae);
			throw new BusinessServiceException(
					"DB Layer Exception Occurred while inserting the values",
					dae);
		} catch (EmailSenderException ese) {
			logger.error("Exception Occured While Sending the E-mail",ese);
			throw new BusinessServiceException(
					"Exception Occured While Sending the E-mail", ese);

		} catch (Exception e) {
			logger.error("General Exception Occured while Registering",e);
			throw new BusinessServiceException(
					"General Exception Occured while Registering", e);

		}

		return buyerRegistrationVO;
	}
	
	/**
	 * @param userID
	 * @return
	 * @throws DBException
	 */
	private boolean isUserIDFound(String userID) throws DBException {
		BuyerUserProfile findUserProfile = new BuyerUserProfile();
		BuyerUserProfile returnUserProfile = null;
		findUserProfile.setUserName(userID);
		
		returnUserProfile = iBuyerUserProfileDao.query(findUserProfile);
		return ((returnUserProfile == null) ? false : true);

	}
	
	/**
	 * @param buyerRegistrationVO
	 * @return
	 * @throws DBException
	 * @throws DataAccessException
	 * @throws DataServiceException
	 * @throws EmailSenderException
	 * @throws AuditException
	 */
	private BuyerRegistrationVO doRegister(
			BuyerRegistrationVO buyerRegistrationVO)
			throws DBException, DataAccessException, DataServiceException,
			EmailSenderException, AuditException, SLBusinessServiceException {

		BuyerRegistrationVO buyerRegResponse = new BuyerRegistrationVO();

		Buyer tempBuyer = null;
		Buyer buyer = new Buyer();
		Contact contact = new Contact();
		BuyerUserProfile userProfile = new BuyerUserProfile();
		BuyerResource buyerResource = new BuyerResource();
		ResourceLocation resourceLocation = new ResourceLocation();
		Location location = new Location();
		String password= AdminUtil.generatePassword();
		buyerRegistrationVO.setPassword(password);
		password = CryptoUtil.encrypt(password);
		int locIdBus = 0;
		int locIdMailing = 0;
		
		buyerRegistrationVO.setRoleId(buyerRegistrationVO.isSimpleBuyerInd() ? OrderConstants.SIMPLE_BUYER_ROLEID : OrderConstants.BUYER_ROLEID);
		logger.info("Entering BuyerRegistrationResponse.doRegister()");
		// Create the contact record
		contact = createUserContactRecord(buyerRegistrationVO, contact);
		
		// Create the user_profile.
		createUserProfile(buyerRegistrationVO, buyerRegResponse, contact, userProfile, password);
		
		// Create the buyer table record.
		tempBuyer = createBuyerRecord(buyerRegistrationVO, buyerRegResponse, tempBuyer, buyer);
		
		// Create Buyer Cancellation Refund Fee Records
		if (OrderConstants.PREFUNDED == tempBuyer.getFundingTypeId()) {
			createBuyerCancellationRefundRecord(buyerRegistrationVO, tempBuyer.getBuyerId(),tempBuyer.getFundingTypeId());
		}
		
		// Create the location table record.
		try {
			location = createLocationRecord(buyerRegistrationVO);
			location = iLocationDao.insert(location);
			locIdBus = location.getLocnId();
			if(buyerRegistrationVO.getMailingZip() != null && !buyerRegistrationVO.getMailingZip().equals(""))
			{
				locIdMailing = createMailingLocationRecord(buyerRegistrationVO,	location);
			}

		}
		catch (DBException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving location type id details due to"
							+ dae.getMessage());
			throw dae;
		}

		// Create buyer resource
		buyerResource = createBuyerResourceRecord(buyerRegistrationVO, buyer, contact, userProfile, buyerResource, locIdBus, locIdMailing);

		
		// create Buyer Resource location record
		createBuyerResourceLocationRecord(buyerRegistrationVO, buyer, buyerResource, resourceLocation, locIdBus, locIdMailing);
		
		// update buyer table IDs
		updateBuyerRecordIDs(buyerRegistrationVO, buyer, contact, locIdBus,	locIdMailing);

		//activate the buyer valueLink V1 and V2 account.
		WalletVO request = walletReqBuilder.activateBuyer(new Long(tempBuyer.getBuyerId()), tempBuyer.getFundingTypeId(), location.getStateCd());
		WalletResponseVO response = wallet.activateBuyer(request);
		if(response.isError()){
			//TODO: Throw an error or just let the other activation check take care of the matter.
			logger.error("Problem activating buyer V1 and V2 during registration.  BuyerID:" + buyer.getBuyerId());
		}
		
		buyerRegistrationVO.setBuyerContactResourceId(buyerResource
				.getResourceId());
		buyerRegResponse=doSendFinalEmailMessage(buyerRegistrationVO);
		buyerRegResponse.setBuyerId(tempBuyer.getBuyerId());
		//SLT-1444 : Auto configure buyer/market/adjustment rates mapping during Buyer Registration
		iBuyerDao.initialiseMarkets(buyerRegResponse.getBuyerId());
		//SLT-1805 :Insert default entry for all the feature sets at buyer registration
		iBuyerDao.addBuyerFeatureSetWithDefaultValue(buyerRegResponse.getBuyerId());
		
		return buyerRegResponse;

	}

	/**
	 * Description:  When registering buyer, this method updates buyer table with FK ids from other associated inserts.
	 * @param buyerRegistrationVO
	 * @param buyer
	 * @param contact
	 * @param locIdBus
	 * @param locIdMailing
	 * @throws DataServiceException
	 */
	private void updateBuyerRecordIDs(BuyerRegistrationVO buyerRegistrationVO, Buyer buyer, Contact contact, int locIdBus, int locIdMailing) throws DataServiceException {
		try {
			logger.info("Saving buyer info for registering buyers");
			buyer.setContactId(contact.getContactId());
			if(buyerRegistrationVO.isSimpleBuyerInd()) buyer.setAccountContactId(contact.getContactId());
			buyer.setUserName(buyerRegistrationVO.getUserName());
			buyer.setPriLocnId(locIdBus);
			if(locIdMailing != 0)
				buyer.setBillLocnId(locIdMailing);
			buyer.setBuyerId(buyer.getBuyerId());
			iBuyerDao.update(buyer);
		}
		catch (DataServiceException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving location Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}
	}

	/**
	 * Description:  When registering buyer, this method persists their buyer_resource_location record information.
	 * @param buyerRegistrationVO
	 * @param buyer
	 * @param buyerResource
	 * @param resourceLocation
	 * @param locIdBus
	 * @param locIdMailing
	 * @throws DBException
	 */
	private void createBuyerResourceLocationRecord( BuyerRegistrationVO buyerRegistrationVO, Buyer buyer,
			BuyerResource buyerResource, ResourceLocation resourceLocation,
			int locIdBus, int locIdMailing) throws DBException {
		try {
			logger.info("Saving Resource location for registering buyer");
			//Inserting business address record
			resourceLocation.setLocationId(locIdBus);
			resourceLocation.setBuyerId(buyer.getBuyerId());
			resourceLocation.setResourceId(buyerResource.getResourceId());
			resourceLocation.setDefaultLocnInd(BuyerConstants.DEFAULT_LOCATION_YES);
			resourceLocation.setCreatedDate(new Date(System.currentTimeMillis()));
			resourceLocation.setModifiedDate(new Date(System.currentTimeMillis()));
			resourceLocation.setModifiedBy(buyerResource.getUserName());
			this.iBuyerResourceLocationDao.insert(resourceLocation);
			this.iBuyerResourceLocationDao.insertBuyerResourceLocation(resourceLocation);
			if(locIdMailing != 0)
			{
				//Inserting mailing address record
				resourceLocation.setLocationId(locIdMailing);
				resourceLocation.setBuyerId(buyer.getBuyerId());
				resourceLocation.setCreatedDate(new Date(System.currentTimeMillis()));
				resourceLocation.setModifiedDate(new Date(System.currentTimeMillis()));
				resourceLocation.setModifiedBy(buyerResource.getUserName());
				this.iBuyerResourceLocationDao.insert(resourceLocation);
			}

		}
		catch (DBException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving Buyer Resource location " +
					"details type id details due to"
							+ dae.getMessage());
			throw dae;
		}
	}
	
	
	/**
	 * Description:  When registering buyer, this method persists their buyer_resource record information.
	 * @param buyerRegistrationVO
	 * @param buyer
	 * @param contact
	 * @param userProfile
	 * @param buyerResource
	 * @param locIdBus
	 * @param locIdMailing
	 * @return
	 * @throws DBException
	 */
	private BuyerResource createBuyerResourceRecord(
			BuyerRegistrationVO buyerRegistrationVO, Buyer buyer,
			Contact contact, BuyerUserProfile userProfile,
			BuyerResource buyerResource, int locIdBus, int locIdMailing)
			throws DBException {
		try {
			logger.info("Saving buyer resource for registering buyers");

			buyerResource.setBuyerId(buyer.getBuyerId());
			buyerResource.setContactId(contact.getContactId());
			buyerResource.setUserName(userProfile.getUserName());
			buyerResource.setLocnId(locIdMailing != 0 ? locIdMailing : locIdBus);
			buyerResource.setCompanyRoleId(userProfile.getRoleId());
			buyerResource.setCreatedDate(new Date(System.currentTimeMillis()));
			buyerResource.setModifiedDate(new Date(System.currentTimeMillis()));
			buyerResource.setModifiedBy(userProfile.getUserName());
			buyerResource.setTermCondAcceptedDate(new Date(System.currentTimeMillis()));
			buyerResource.setTermCondId(BuyerConstants.TERM_COND_ID);
			buyerResource.setTermCondInd(BuyerConstants.TERM_COND_IND);
			buyerResource = iBuyerResourceDao.insert(buyerResource);
		}
		catch (DBException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving Buyer Resource" +
			" Details type id details due to"
							+ dae.getMessage());
			throw dae;
		}
		return buyerResource;
	}
	
	
	/**
	 * Description:  When registering buyer, this method creates their location record information.
	 * @param buyerRegistrationVO
	 * @return
	 */
	private Location createLocationRecord(BuyerRegistrationVO buyerRegistrationVO) {
		Location location;
		location = setLocationType(buyerRegistrationVO);
		location.setStreet1(buyerRegistrationVO.getBusinessStreet1());
		location.setStreet2(buyerRegistrationVO.getBusinessStreet2());
		location.setCity(buyerRegistrationVO.getBusinessCity());
		location.setStateCd(buyerRegistrationVO.getBusinessState());
		location.setZip(buyerRegistrationVO.getBusinessZip());
		location.setAptNo(buyerRegistrationVO.getBusinessAprt());
		return location;
	}

	
	/**
	 * Description: When registering buyer, this method persists their mailing location information.
	 * @param buyerRegistrationVO
	 * @param location
	 * @return
	 * @throws DBException
	 */
	private int createMailingLocationRecord(BuyerRegistrationVO buyerRegistrationVO, Location location) throws DBException {
		int locIdMailing;
		// insert mailing address
		location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_MAILING);
		location = new Location();
		location.setStreet1(buyerRegistrationVO.getMailingStreet1());
		location.setStreet2(buyerRegistrationVO.getMailingStreet2());
		location.setCity(buyerRegistrationVO.getMailingCity());
		location.setStateCd(buyerRegistrationVO.getMailingState());
		location.setZip(buyerRegistrationVO.getMailingZip());
		location.setAptNo(buyerRegistrationVO.getMailingAprt());
		location = iLocationDao.insert(location);
		locIdMailing = location.getLocnId();
		return locIdMailing;
	}
	
	
	/**
	 * Description:  When registering buyer, this method sets the location type to be saved.
	 * @param buyerRegistrationVO
	 * @return
	 */
	private Location setLocationType(BuyerRegistrationVO buyerRegistrationVO) {
		Location location;
		logger.info("Saving location for registering buyers("
				+ buyerRegistrationVO.getBusinessName() + ").");
		//Inserting the business address into Location table
		location = new Location();
		// Changes for simple buyer support, where location name is available
		if (buyerRegistrationVO.getLocName() == null) {
			location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_BUSINESS);
		} else {
			location.setLocnName(buyerRegistrationVO.getLocName());
			if ("true".equalsIgnoreCase(buyerRegistrationVO.getHomeAddressInd()) || 
					BuyerConstants.LOCATION_NAME_HOME.equalsIgnoreCase(buyerRegistrationVO.getLocName())) {
				location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_HOME);
			} else {
				location.setLocnTypeId(BuyerConstants.LOCATION_TYPE_OTHER);
			}
		}
		return location;
	}
	
	
	/**
	 * Description:  When registering buyer, this method persists their buyer table information.
	 * @param buyerRegistrationVO
	 * @param buyerRegResponse
	 * @param tempBuyer
	 * @param buyer
	 * @return
	 * @throws DataServiceException
	 */
	private Buyer createBuyerRecord(BuyerRegistrationVO buyerRegistrationVO, BuyerRegistrationVO buyerRegResponse, Buyer tempBuyer, Buyer buyer) throws DataServiceException {
		try {
			logger.info("Saving in buyer for registering buyer");			
			if(buyerRegistrationVO.isSimpleBuyerInd()){
				StringBuffer sb = new StringBuffer();
				String fName=buyerRegistrationVO.getFirstName();				
				String lName=buyerRegistrationVO.getLastName();				
				if(StringUtils.isNotEmpty(lName)){
					sb.append(lName);
				}
				if(StringUtils.isNotEmpty(fName)){
					sb.append(", ");
					sb.append(fName);
				}
				String busNameForSimple=sb.toString();				
				buyer.setBusinessName(busNameForSimple);
			}else{				
			buyer.setBusinessName(buyerRegistrationVO.getBusinessName());
			}
			buyer.setBusinessStarted(buyerRegistrationVO.getBusinessStarted());
			buyer.setPrimaryIndustry(buyerRegistrationVO.getPrimaryIndustry());
			if (buyerRegistrationVO.getPhoneAreaCode() != null) {
				buyer.setBusPhoneNo(buyerRegistrationVO.getPhoneAreaCode()+ buyerRegistrationVO.getPhonePart1()+ buyerRegistrationVO.getPhonePart2());
			}
			buyer.setBusExtn(buyerRegistrationVO.getPhoneExtn());
			if(buyerRegistrationVO.getPrimaryIndustry()!=null && buyerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				buyer.setPrimaryIndustry(buyerRegistrationVO.getPrimaryIndustry());
			}
			if (buyerRegistrationVO.getFaxAreaCode() != null) {
				buyer.setBusFaxNo(buyerRegistrationVO.getFaxAreaCode()+ buyerRegistrationVO.getFaxPart1()+ buyerRegistrationVO.getFaxPart2());
			}
			//Set funding type properly.
			if (buyerRegistrationVO.isSimpleBuyerInd()){
				buyer.setFundingTypeId(OrderConstants.CONSUMER_FUNDED);
			}else{
				buyer.setFundingTypeId(OrderConstants.PREFUNDED);
			}
			buyer.setAggregateRatingScore(new Double(0));
			buyer.setHowDidYouHear(buyerRegistrationVO.getHowDidYouHear());
			buyer.setPromotionCode(buyerRegistrationVO.getPromotionCode());
			buyer.setCreatedDate(new Date(System.currentTimeMillis()));
			buyer.setModifiedDate(new Date(System.currentTimeMillis()));
		    buyer.setModifiedBy(buyerRegistrationVO.getUserName());
		    buyer.setBusinessType(buyerRegistrationVO.getBusinessStructure());
		    buyer.setCompanySize(buyerRegistrationVO.getSizeOfCompany());
		    buyer.setSalesVolume(buyerRegistrationVO.getAnnualSalesRevenue());
		    buyer.setWebAddress(buyerRegistrationVO.getWebsiteAddress());
		    buyer.setUserName(buyerRegistrationVO.getUserName());
		    buyer.setPostingFee(buyerRegistrationVO.isSimpleBuyerInd() ? 
		    					new Double(PropertiesUtils.getPropertyValue(BuyerConstants.SIMPLE_BUYER_POSTING_FEE)) :
		    					new Double(PropertiesUtils.getPropertyValue(BuyerConstants.PRO_BUYER_POSTING_FEE)));
		    buyer.setCancellationFee(buyerRegistrationVO.isSimpleBuyerInd() ?
		    					new Double(PropertiesUtils.getPropertyValue(BuyerConstants.SIMPLE_BUYER_CANCELLATION_FEE)) :
		    					new Double(PropertiesUtils.getPropertyValue(BuyerConstants.PRO_BUYER_CANCELLATION_FEE)));
		    buyer.setServiceLiveBucksInd(BuyerConstants.TERM_COND_IND.toString());
		    buyer.setServiceLiveBucksAcceptedDate(new Date(System.currentTimeMillis()));
		    buyer.setTermsAndCondition(BuyerConstants.TERM_COND_IND.toString());
		    buyer.setTermsAndConditionAcceptedDate(new Date(System.currentTimeMillis()));
		    buyer.setTermsAndConditionId(BuyerConstants.TERM_COND_ID.toString());
		    tempBuyer = iBuyerDao.insert(buyer);

		    buyerRegResponse.setBuyerId(tempBuyer.getBuyerId());
		}
		catch (DataServiceException dae) {
			logger.error("SQL Exception @BuyerRegistrationBOImpl.doRegister() due to"
					+ dae.getMessage());
			throw dae;
		}
		return tempBuyer;
	}
	
	/**
	 * Description: When registering buyer, this method insert with posting fee
	 * for 3 different scenario
	 * 
	 * @param buyerRegistrationVO
	 * @throws DBException
	 */
	private void createBuyerCancellationRefundRecord(BuyerRegistrationVO buyerRegistrationVO, Integer buyerId,
			Integer fundingTypeId) throws DataServiceException {
		try {

			logger.info("Inserting Buyer Cancellation Fee Details");

			double postingFee = buyerRegistrationVO.isSimpleBuyerInd()
					? new Double(PropertiesUtils.getPropertyValue(BuyerConstants.SIMPLE_BUYER_POSTING_FEE))
					: new Double(PropertiesUtils.getPropertyValue(BuyerConstants.PRO_BUYER_POSTING_FEE));

			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			java.util.Date parsedEffectiveDate = dateFormatUTC.parse(dateFormat.format(date));
			java.sql.Timestamp effectiveDate = new java.sql.Timestamp(parsedEffectiveDate.getTime());

			List<BuyerCancellationPostingFeeVO> buyerCancellationRefundList = new ArrayList<>();

			buyerCancellationRefundList.add(new BuyerCancellationPostingFeeVO(buyerId, fundingTypeId,
					BuyerConstants.ACTIVE_SO_CANCELLATION_REFUND, postingFee, BuyerConstants.Void_SO_Bus_Trans_Id,
					effectiveDate));

			buyerCancellationRefundList.add(new BuyerCancellationPostingFeeVO(buyerId, fundingTypeId,
					BuyerConstants.CANCELLATION_WITHIN_24_HOURS, BuyerConstants.ZERO_CANCELLATION_REFUND,
					BuyerConstants.Cancel_SO_With_Penalty_Bus_Trans_Id, effectiveDate));

			buyerCancellationRefundList.add(new BuyerCancellationPostingFeeVO(buyerId, fundingTypeId,
					BuyerConstants.CANCELLATION_OUTSIDE_24_HOURS, BuyerConstants.ZERO_CANCELLATION_REFUND,
					BuyerConstants.Cancel_SO_Without_Penalty_Bus_Trans_Id, effectiveDate));

			logger.info("Refund Posting Fee Data : " + buyerCancellationRefundList);

			iBuyerDao.insertBuyerCancellationRefund(buyerCancellationRefundList);

		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Exception in createBuyerCancellationRefundRecord : " + e);
		}
	}
	
		
	/**
	 * Description:  When registering buyer, this method persists their user_profile information
	 * @param buyerRegistrationVO
	 * @param buyerRegResponse
	 * @param contact
	 * @param userProfile
	 * @param password
	 * @throws DBException
	 */
	private void createUserProfile(BuyerRegistrationVO buyerRegistrationVO,	BuyerRegistrationVO buyerRegResponse, Contact contact, BuyerUserProfile userProfile, String password) throws DBException {
		try {
			logger.info("Saving user profile for registering buyers");
			userProfile.setUserName(buyerRegistrationVO.getUserName());
			userProfile.setContactId(contact.getContactId());


			if (password != null) {
				userProfile.setPassword(password);
			}
			userProfile.setPasswordFlag(1);


			buyerRegResponse.setPassword(userProfile.getPassword());
			buyerRegResponse.setUserName(buyerRegistrationVO.getUserName());

			userProfile.setAnswerTxt("");
			userProfile.setQuestionId(0);
			userProfile.setCreatedDate(new Timestamp(System.currentTimeMillis()).toString());
			userProfile.setModifiedDate(new Timestamp(System.currentTimeMillis()).toString());
			userProfile.setModifiedBy(buyerRegistrationVO.getUserName());
			userProfile.setRoleId(buyerRegistrationVO.isSimpleBuyerInd() ? OrderConstants.SIMPLE_BUYER_ROLEID : OrderConstants.BUYER_ROLEID);
			userProfile.setRoleName(buyerRegistrationVO.isSimpleBuyerInd() ? MPConstants.ROLE_SIMPLE_BUYER : MPConstants.ROLE_BUYER);
			userProfile.setActiveInd(1);
			userProfile.setPromotionalMailInd(buyerRegistrationVO.getPromotionalMailInd());
			iBuyerUserProfileDao.insert(userProfile);

			/*
			 *   insert this user as an admin, will insert the user as an admin
			*/

			securityDao.insertBuyerAdminProfile(userProfile);
		}
		catch (DBException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
							+ dae.getMessage());
			throw dae;
		}
		catch (com.newco.marketplace.exception.core.DataServiceException dse) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
							+ dse.getMessage());
			throw new DBException(dse.getMessage());
		}
	}
	
	
	/**
	 * Description:  When registering buyer, this method persists their contact information
	 * @param buyerRegistrationVO
	 * @param contact
	 * @return
	 * @throws DataServiceException
	 */
	private Contact createUserContactRecord(BuyerRegistrationVO buyerRegistrationVO, Contact contact) throws DataServiceException {
		try {
			logger.info("Saving contact for adding a buyer");
			contact.setLastName(buyerRegistrationVO.getLastName());
			contact.setFirstName(buyerRegistrationVO.getFirstName());
			contact.setMi(buyerRegistrationVO.getMiddleName());
			contact.setSuffix(buyerRegistrationVO.getNameSuffix());
			//Modified Role within company
			contact.setCompanyRoleId(Integer.parseInt(buyerRegistrationVO.getRoleWithinCom()));
			//Added Job title
			contact.setTitle(buyerRegistrationVO.getJobTitle());

			//Added Alternate Email
			contact.setAltEmail(buyerRegistrationVO.getAltEmail());

			contact.setEmail(buyerRegistrationVO.getEmail());
			contact.setHonorific("");
			
			if(buyerRegistrationVO.isApiFlag()) {
				contact.setPhoneNo(buyerRegistrationVO.getBusPhoneNo1());
				logger.info("API-PHONE-PRIMARY-SET");
			} else {
				logger.info("NOT setting thru API-PHONE-PRIMARY: ");
				//Added to insert Business phone number into CONTACT table
				//author - paugus2
				String phone1 = buyerRegistrationVO.getBusPhoneNo1();
				String phone2 = buyerRegistrationVO.getBusPhoneNo2();
				String phone3 = buyerRegistrationVO.getBusPhoneNo3();
				
				if (phone1 != null && phone1.trim().length() > 0
				&&	phone2 != null && phone2.trim().length() > 0
				&&	phone3 != null && phone3.trim().length() > 0)
				{
					contact.setPhoneNo(phone1.trim() + phone2.trim() + phone3.trim());
				}
			}
			
			if (buyerRegistrationVO.getPhoneExtn() !=  null
			&&	buyerRegistrationVO.getPhoneExtn().trim().length() > 0)
			{
				contact.setPhoneNoExt(buyerRegistrationVO.getPhoneExtn().trim());
			}
			
			if (buyerRegistrationVO.getFaxAreaCode() != null) {
				contact.setFaxNo(buyerRegistrationVO.getFaxAreaCode()+ buyerRegistrationVO.getFaxPart1()+ buyerRegistrationVO.getFaxPart2());
			}
			
			if(buyerRegistrationVO.isApiFlag()) {
				contact.setCellNo(buyerRegistrationVO.getMobPhoneNo1());
				logger.info("API-PHONE-ALTERNATE-SET");
			} else {
				logger.info("NOT setting thru API-PHONE-ALTERNATE: ");
				if (buyerRegistrationVO.getMobPhoneNo1() != null) {
					contact.setCellNo(buyerRegistrationVO.getMobPhoneNo1()+ buyerRegistrationVO.getMobPhoneNo2() + buyerRegistrationVO.getMobPhoneNo3());
				}
			}
			
			contact.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			contact.setModifiedDate(new Timestamp(System.currentTimeMillis()));
			contact.setModifiedBy(buyerRegistrationVO.getUserName());
			
			contact = iContactDao.insert(contact);
		}
		catch (DataServiceException dae) {
			logger.error("SQL Exception @RegistrationBOImpl.doRegister() while saving contact details due to"
							+ dae.getMessage());
			throw dae;
		}
		return contact;
	}

	public BuyerRegistrationVO loadZipSet(
			BuyerRegistrationVO buyerRegistrationVO)
			throws BusinessServiceException {

		try {

			List stateTypeList = null;
			if (buyerRegistrationVO.getStateType() != null
			&& 	buyerRegistrationVO.getStateType().length() > 0
			&&  buyerRegistrationVO.getStateType().equalsIgnoreCase("business"))
			{
				stateTypeList = zipDao.queryList(buyerRegistrationVO.getBusinessState());
			}

			if (buyerRegistrationVO.getStateType() != null
			&& 	buyerRegistrationVO.getStateType().length() > 0
			&&  buyerRegistrationVO.getStateType().equalsIgnoreCase("mail"))
							{
				stateTypeList = zipDao.queryList(buyerRegistrationVO.getMailingState());
			}
			buyerRegistrationVO.setStateTypeList(stateTypeList);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerRegistrationBOImpl.loadZipSet() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @BuyerRegistrationBOImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		return buyerRegistrationVO;
	}
	
	public List<LocationVO> loadLocations(String stateCode)
			throws BusinessServiceException {

		List<LocationVO> locationVOList = null;
		try {
			locationVOList = (List<LocationVO>)zipDao.queryList(stateCode);
		} catch (Exception ex) {
			logger.info("General Exception @BuyerRegistrationBOImpl.loadLocations() due to" + ex.getMessage());
			throw new BusinessServiceException("General Exception @BuyerRegistrationBOImpl.loadLocations() due to " + ex.getMessage());
		}
		return locationVOList;
	}
	
	/**
	 * @param buyerRegistrationVO
	 * @return
	 * @throws EmailSenderException
	 * @throws DBException
	 */
	private BuyerRegistrationVO doSendFinalEmailMessage(
			BuyerRegistrationVO buyerRegistrationVO)
			throws EmailSenderException,DBException{
		// send the confirmation email
		TemplateVo template;
		if(isSelectedStateValid(buyerRegistrationVO.getBusinessState())) {
			logger.info("----State is Valid I m sending Password and username to you-----");
			buyerRegistrationVO.setValidateState(false);
		}
		else {
			logger.info("----State is InValid I m not sending Password but username -----");
			buyerRegistrationVO.setValidateState(true);
		}
		if (buyerRegistrationVO.getRoleId() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			template = getTemplateDao().getTemplate(MPConstants.TEMPLATE_NAME_SIMPLE_BUYER_REGISTRATION);
		} else {
			// assume this must be a Pro buyer!
			template = getTemplateDao().getTemplate(MPConstants.TEMPLATE_NAME_BUYER_COMPANY_REGISTRATION);
		}
		// The below code is commented since the email is not sent through velocity context
	/*	try {
			iBuyerEmailBO.sendConfirmationMail(template,  buyerRegistrationVO.getUserName(), buyerRegistrationVO.getPassword(),
			buyerRegistrationVO.getEmail(),buyerRegistrationVO.getAltEmail());
		} catch (MessagingException e) {
			throw new EmailSenderException("Unable to send registration email",e);
		} catch (IOException e) {
			throw new EmailSenderException("Unable to send registration email",e);
		}*/
		return buyerRegistrationVO;
	}

		/***
		 * returns true if the passed state is active else returns true
		 * @param selectedState
		 * @return
		 * @throws DBException
		 */
		private boolean isSelectedStateValid(String selectedState) throws DBException{
			boolean stateValid = false;
		   try{
			   LookupVO vo=iLookupDAO.isStateActive(selectedState);
			   if(vo != null && vo.getId() != null){
				   stateValid = true;
			   }
		   }catch(DBException dbe){
			   logger
				.info("General Exception @BuyerRegistrationBOImpl.isSelectedStateValid() due to"
						+ dbe.getMessage());
			   throw dbe;
		   }
		   return stateValid;
	}
	
	public BuyerRegistrationVO formatPhoneNumbers(Buyer buyer,BuyerRegistrationVO buyerRegistrationVO)
	{
		try
		{
			String faxNo = buyer.getBusFaxNo();
			String phoneNo = buyer.getBusPhoneNo(); 
			if (faxNo != null 
					&& 	!faxNo.equals("")
					&&	faxNo.length() == 10)
					{
						buyerRegistrationVO.setFaxAreaCode(faxNo.substring(0,3));
						buyerRegistrationVO.setFaxPart1(faxNo.substring(3,6));
						buyerRegistrationVO.setFaxPart2(faxNo.substring(6,10));
					}
			if(phoneNo != null 
					&& 	!phoneNo.equals("")
					&&	phoneNo.length() == 10)
					{
						buyerRegistrationVO.setPhoneAreaCode(phoneNo.substring(0,3));
						buyerRegistrationVO.setPhonePart1(phoneNo.substring(3,6));
						buyerRegistrationVO.setPhonePart2(phoneNo.substring(6,10));
					}
			return buyerRegistrationVO;
		}
		catch(Exception e)
		{
			logger.info("Error in @BuyerRegistrationBOImpl.formatPhoneNumbers" + e);
		}
		return null;
	}
	
	public void SaveBlackoutBuyerLead(BuyerRegistrationVO buyerRegistrationVO)
	throws BusinessServiceException {
		try {
			iBuyerDao.saveBlackoutBuyerLead(buyerRegistrationVO);
		} catch (DataServiceException e) {
			logger.error("Error occured @ BuyerRegistrationBOImpl.SaveBlackoutBuyerLead ");
			throw new BusinessServiceException("Error occured @ BuyerRegistrationBOImpl.SaveBlackoutBuyerLead ");
		}

	}

	public boolean checkBlackoutState(String stateCd)
			throws BusinessServiceException {
		boolean flag = false;
		try {
			flag = iBuyerDao.checkBlackoutState(stateCd);
		} catch (DataServiceException e) {
			logger.error("Error occured @ BuyerRegistrationBOImpl.SaveBlackoutBuyerLead ");
			throw new BusinessServiceException("Error occured @ BuyerRegistrationBOImpl.checkBlackoutState");
		}
		
		return flag;
	}
	
	public List<String> getBlackoutStates() throws BusinessServiceException {
		List<String> blackoutStates = new ArrayList<String>();
		try{
			blackoutStates = iBuyerDao.getBlackoutStateCodes();
		}catch (DataServiceException e) {
			logger.error("Error occured @ BuyerRegistrationBOImpl.getBlackoutStates");
			throw new BusinessServiceException("Error occured @ BuyerRegistrationBOImpl.getBlackoutStates");
		}
		return blackoutStates;
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
	
	public ITemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}
	
	public IWalletBO getWallet() {
		return wallet;
	}
	public void setWallet(IWalletBO wallet) {
		this.wallet = wallet;
	}
	public WalletRequestBuilder getWalletReqBuilder() {
		return walletReqBuilder;
	}
	public void setWalletReqBuilder(WalletRequestBuilder walletReqBuilder) {
		this.walletReqBuilder = walletReqBuilder;
	}

	
}

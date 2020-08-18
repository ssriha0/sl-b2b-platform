package com.newco.marketplace.web.delegatesImpl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import com.newco.marketplace.business.businessImpl.alert.Address;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerPersIdentificationBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.ledger.ICreditCardBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerTransactionBO;
import com.newco.marketplace.business.iBusiness.location.ISimpleBuyerLocationBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.simple.CreateServiceOrderCreateAccountVO;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.IFinanceManagerDelegate;
import com.newco.marketplace.web.delegates.ISOWizardPersistDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.buyer.BuyerRegistrationDTO;
import com.newco.marketplace.web.dto.homepage.HomepageFormDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderCreateAccountDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderEditAccountDTO;
import com.newco.marketplace.web.dto.simple.CreditCardDTO;
import com.newco.marketplace.web.utils.BuyerRegistrationMapper;
import com.newco.marketplace.web.utils.CreateServiceOrderCreateAccountMapper;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.SSoWSessionFacility;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;

public class CreateServiceOrderDelegateImpl implements
		ICreateServiceOrderDelegate {

	private IBuyerRegistrationBO buyerRegistrationBO;
	private CreateServiceOrderCreateAccountMapper accountMapper;
	private BuyerRegistrationMapper buyerRegistrationMapper;
	private IBuyerBO buyerBo;
	private IBuyerPersIdentificationBO buyerPIIBo;
	private IDocumentBO documentBO;
	private ICreditCardBO creditCardBO;
	private ISimpleBuyerLocationBO simpleBuyerLocationBO;
	private ILedgerTransactionBO ledgerTransactionBO;
	private ISOWizardPersistDelegate isoWizardPersistDelegate;
	private IFinanceManagerDelegate financeManagerDelegate;
	private ISOWizardFetchDelegate fetchDelegate;
	
	private static final Logger logger = Logger.getLogger(CreateServiceOrderDelegateImpl.class.getName());
	
	public CreateServiceOrderDelegateImpl(IDocumentBO documentBOArg, IBuyerRegistrationBO buyerRegistrationBO,
			CreateServiceOrderCreateAccountMapper accountMapper, BuyerRegistrationMapper buyerRegistrationMapper, 
			IBuyerBO buyerBo){
		this.documentBO = documentBOArg;
		this.buyerRegistrationBO = buyerRegistrationBO;
		this.accountMapper = accountMapper;
		this.buyerRegistrationMapper = buyerRegistrationMapper;
		this.buyerBo = buyerBo;
	}
	
	public List<SODocumentDTO> getDocumentListBySOId(String soId, Integer categoryId, Integer roleId, Integer userId) throws Exception{
		List<SODocumentDTO> docList = new ArrayList<SODocumentDTO>();
		List<DocumentVO> docVOList = documentBO.retrieveServiceOrderDocumentsBySOIdAndCategory(soId, categoryId, roleId, userId);
		if(docVOList!= null){
			for (DocumentVO docVO : docVOList){
				docList.add(ObjectMapper.convertDocumentVOToDTO(docVO));					
			}			
		}

		return docList;
	}
	
	public List<SODocumentDTO> getTemporaryDocumentListBySimpleBuyerId(String simpleBuyerId, Integer categoryId) throws Exception{
		List<SODocumentDTO> docList = new ArrayList<SODocumentDTO>();
		List<DocumentVO> docVOList = documentBO.retrieveTemporaryDocumentsBySimpleBuyerIdAndCategory(simpleBuyerId, categoryId);
		if(docVOList!= null){
			for (DocumentVO docVO : docVOList){
				docList.add(ObjectMapper.convertDocumentVOToDTO(docVO));					
			}			
		}

		return docList;
	}
	
	public List<SODocumentDTO> getPhotoDocListBySOId(String soId, Integer categoryId, Integer roleId, Integer userId) throws Exception{
		List<SODocumentDTO> photoList = new ArrayList<SODocumentDTO>();
		List<DocumentVO> photoDocVOList = documentBO.retrieveServiceOrderDocumentsBySOIdAndCategory(soId, categoryId, roleId, userId);
		if(photoDocVOList!= null){
			for (DocumentVO docVO : photoDocVOList){
				photoList.add(ObjectMapper.convertDocumentVOToDTO(docVO));					
			}
		}
		return photoList;
	}
	
	public ProcessResponse insertSODocument(DocumentVO documentVO) throws Exception{

		ProcessResponse pr = new ProcessResponse();
		try{
			pr = documentBO.insertServiceOrderDocument(documentVO);
		}catch(Exception e){
			logger.info("Error inserting SO Document, ignoring exception",e);
		}
		return pr;
	}
	
	public ProcessResponse insertTempSODocument(DocumentVO documentVO) throws Exception{

		ProcessResponse pr = new ProcessResponse();
		try{
			pr = documentBO.insertTemporarySimpleBuyerServiceOrderDocument(documentVO);
		}catch(Exception e){
			logger.info("Error inserting SO Document, ignoring exception",e);
		}
		return pr;
	}

	public ProcessResponse deleteSODocument(DocumentVO documentVO) throws Exception{
		ProcessResponse pr = new ProcessResponse();
		try{
			Integer documentId  = documentVO.getDocumentId();
			Integer roleId = documentVO.getRoleId();
			Integer userId = documentVO.getEntityId();
			
			pr = documentBO.deleteServiceOrderDocument(documentId, roleId, "ProPortal", userId);
		
		}catch(Exception e){
			logger.info("Error deleting SO Document for Simple Buyer, ignoring exception",e);
			throw new Exception("Error deleting SO Document for Simple Buyer, ignoring exception" , e);
		}

		return pr;
	}
	
	public ProcessResponse deleteTempSODocument(String simpleBuyerId, Integer docId) throws Exception{
		ProcessResponse pr = new ProcessResponse();
		try{
		
			documentBO.deleteTemporarySimpleBuyerDocument(simpleBuyerId, docId);
		
		}catch(Exception e){
			logger.info("Error deleting SO Document for Simple Buyer, ignoring exception",e);
			throw new Exception("Error deleting SO Document for Simple Buyer, ignoring exception" , e);
		}

		return pr;
	}

	public HomepageFormDTO getHomepageData() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void loadAccount(CreateServiceOrderCreateAccountDTO accountDTO) throws DelegateException {
		try {
			CreateServiceOrderCreateAccountVO accountVO = new CreateServiceOrderCreateAccountVO();
			buyerRegistrationBO.loadAccount(accountVO);
			accountMapper.convertVOtoDTO(accountDTO, accountVO);
		} catch (BusinessServiceException bsEx) {
			throw new DelegateException("Business Service @CreateServiceOrderDelegateImpl.loadAccount() due to " + bsEx.getMessage(), bsEx);
		}
	}

	public List<com.newco.marketplace.vo.provider.LocationVO> loadLocations(String stateCode)
			throws DelegateException {
		
		List<com.newco.marketplace.vo.provider.LocationVO> locationVOList = null;
		try {
			locationVOList = buyerRegistrationBO.loadLocations(stateCode);
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @CreateServiceOrderDelegateImpl.loadLocations() due to " + ex.getMessage());
		} catch (Exception ex) {
			throw new DelegateException("General Exception @CreateServiceOrderDelegateImpl.loadLocations() due to " + ex.getMessage());
		}
		
		return locationVOList;
	}
	
	public boolean isBlackoutState(String stateCode) throws DelegateException {
		boolean flag = false;
		try{
			flag = buyerRegistrationBO.checkBlackoutState(stateCode);
			
		}catch(BusinessServiceException bse){
			throw new DelegateException("BusinessServiceException@CreateSerieOrderDelegateImpl.isBlackoutState");
		}catch(Exception e){
			throw new DelegateException("General Exception @CreateSerieOrderDelegateImpl.isBlackoutState");
		}
		return flag; //buyerRegistrationBO.
	}
	
	public void saveBuyerLead(CreateServiceOrderCreateAccountDTO buyerDTO) throws DelegateException {
		try{
			BuyerRegistrationDTO buyerRegistrationDTO = buyerDTO.convertToBuyerRegistrationDTO();
			
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO, buyerRegistrationDTO);
			
			buyerRegistrationVO.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
			buyerRegistrationBO.SaveBlackoutBuyerLead(buyerRegistrationVO);
		}catch(BusinessServiceException bse){
			throw new DelegateException("BusinessServiceException@CreateSerieOrderDelegateImpl.saveBuyerLead");
		}catch(Exception e){
			throw new DelegateException("General Exception@CreateSerieOrderDelegateImpl.saveBuyerLead");
		}
	}

	public CreateServiceOrderCreateAccountDTO saveSimpleBuyerRegistration(CreateServiceOrderCreateAccountDTO accountDTO)
			throws DelegateException, DuplicateUserException {
		try {
			
			BuyerRegistrationDTO buyerRegistrationDTO = accountDTO.convertToBuyerRegistrationDTO();
			
			BuyerRegistrationVO buyerRegistrationVO = new BuyerRegistrationVO();
			buyerRegistrationVO = buyerRegistrationMapper.convertDTOtoVO(buyerRegistrationVO, buyerRegistrationDTO);
			
			buyerRegistrationVO.setRoleId(OrderConstants.SIMPLE_BUYER_ROLEID);
			
			
			buyerRegistrationVO =  buyerRegistrationBO.saveSimpleBuyerRegistration(buyerRegistrationVO);
			buyerRegistrationDTO = buyerRegistrationMapper.convertVOtoDTO(buyerRegistrationDTO,buyerRegistrationVO);
			
			accountDTO.populateFromBuyerRegistrationDTO(buyerRegistrationDTO);
			
		} catch (BusinessServiceException ex) {
			throw new DelegateException("Business Service @CreateServiceOrderDelegateImpl.saveSimpleBuyerRegistration() due to " + ex.getMessage(), ex);
		}catch (DuplicateUserException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new DelegateException("General Exception @CreateServiceOrderDelegateImpl.saveSimpleBuyerRegistration() due to " + ex.getMessage(), ex);
		}
		
		return accountDTO;
	}
	
	public SOWContactLocationDTO loadBuyerPrimaryLocation(Integer buyerId, Integer buyerResourceId) throws DelegateException {
		ContactLocationVO contactLocationVO = buyerBo.getBuyerResDefaultLocationVO(buyerResourceId);
		com.newco.marketplace.dto.vo.LocationVO locationVO = null;
		if(contactLocationVO != null){
			locationVO = contactLocationVO.getBuyerPrimaryLocation();		
		}
		SOWContactLocationDTO contactLocationDTO = CreateServiceOrderCreateAccountMapper.mapLocationVOTODto(locationVO);
		return contactLocationDTO;
	}

	private Map<String,Object> authenticateCreditCard(CreateServiceOrderAddFundsDTO addFundsDTO, Integer buyerId, String userName)throws DelegateException{
		Map<String,Object> resultMap = null;
		CreditCardVO creditCardVOResult = null;
		try {
			Buyer buyer = buyerBo.getBuyer(buyerId);
			CreditCardVO creditCardVO = CreateServiceOrderCreateAccountMapper.buildCreditCardVO(addFundsDTO, buyerId);
			creditCardVO.setUserName(userName);
			creditCardVO.setSendFulfillment(false);
			if(buyer != null && buyer.getFundingTypeId() != null){
				creditCardVO.setFundingTypeId(buyer.getFundingTypeId().toString());
			}	
			String roleType = "";
			if(OrderConstants.SIMPLE_BUYER_ROLEID == buyer.getRoleId().intValue()){
				roleType = OrderConstants.SIMPLE_BUYER;
			}else if(OrderConstants.BUYER_ROLEID == buyer.getRoleId().intValue()){
				roleType = OrderConstants.BUYER;
			}
			if(addFundsDTO != null && addFundsDTO.getUseExistingCard()) {
				
				creditCardVOResult = creditCardBO.authorizeCardTransaction(creditCardVO, addFundsDTO.getSoId(),roleType);
			} else {
				
				CreditCardVO existingCreditCardVO = 
					CreateServiceOrderCreateAccountMapper.buildExistingCreditCardVO(addFundsDTO, buyerId);
				// deactivate existing credit card
				if(existingCreditCardVO != null && addFundsDTO.getCheckboxSaveThisCard()) {
					existingCreditCardVO.setActive_ind(Boolean.FALSE);
					boolean isDeactivated = creditCardBO.updateDeactivateCreditCardAccountInfo(existingCreditCardVO);
				} 
				
				creditCardVOResult = creditCardBO.saveAndAuthorizeCardTransaction(creditCardVO, addFundsDTO.getSoId(),roleType);
				//above function does both saving and calling the authorized no need to call this. creditCardVOResult = creditCardBO.authorizeCardTransaction(creditCardVO, addFundsDTO.getSoId());
			}
			
			resultMap = new HashMap<String, Object>();
			resultMap.put("accountId", creditCardVOResult.getCardId());
			if(creditCardVOResult == null){
				resultMap.put("isAuthSuccess", Boolean.FALSE);				
				resultMap.put("authMessage", "Authentication failed");
			}else if(creditCardVOResult != null
					&& !creditCardVOResult.isAuthorized()){
				resultMap.put("isAuthSuccess", Boolean.FALSE);		
				if(StringUtils.isNotBlank(creditCardVOResult.getAnsiResponseCode())){
					resultMap.put("authMessage", creditCardVOResult.getAnsiResponseCode());
				}else if(StringUtils.isNotBlank(creditCardVOResult.getCidResponseCode())){
					resultMap.put("authMessage", creditCardVOResult.getCidResponseCode());
				}else{
					resultMap.put("authMessage", "Authentication failed");
				}
			}else if(creditCardVOResult != null
					&& creditCardVOResult.isAuthorized()){
				resultMap.put("isAuthSuccess", Boolean.TRUE);
				resultMap.put("authMessage", creditCardVOResult.getAuthorizationCode());
			}
		}catch (Exception e) {
			logger.error("Exception occured in CreateServiceOrderDelegateImpl --> authenticateCreditCard() "
					+" due to "+e.getMessage(), e);
			throw new DelegateException("Error authenticating credit card ",e);		
		}	
		return resultMap;
	}

	public Map<String, Object> postSimpleBuyerServiceOrder(CreateServiceOrderAddFundsDTO addFundsDTO, Integer buyerId, String userName)throws DelegateException{
		Map<String, Object> creditCardMap = null;
		Boolean isPostFail = Boolean.TRUE;
		
		try {
			if(addFundsDTO != null
					&& addFundsDTO.getTransactionAmount() != null
					&& addFundsDTO.getTransactionAmount().doubleValue() > 0.0){
				creditCardMap = authenticateCreditCard(addFundsDTO, buyerId, userName);
				Map<String, Object> sessionMap = 
					ActionContext.getContext().getSession();
				sessionMap.put("simpleBuyerAccountId", creditCardMap.get("accountId").toString());
				if(creditCardMap != null
						&& ((Boolean)creditCardMap.get("isAuthSuccess")).booleanValue() == true) {
					isPostFail = SSoWSessionFacility.getInstance().evaluateAndPostSOWBean(isoWizardPersistDelegate, financeManagerDelegate, fetchDelegate);
				}
			} else {
				isPostFail = SSoWSessionFacility.getInstance().evaluateAndPostSOWBean(isoWizardPersistDelegate, financeManagerDelegate,fetchDelegate);
				if(!isPostFail){
					creditCardMap = new HashMap<String, Object>();					
					creditCardMap.put("isAuthSuccess", Boolean.TRUE);
					creditCardMap.put("authMessage", "Successfully posted service order");					
				}
			}						
		} catch (DelegateException e) {
			logger.error("Exception occured in CreateServiceOrderDelegateImpl --> postSimpleBuyerServiceOrder() "
					+" due to "+e.getMessage(),e);
			throw new DelegateException("Error authenticating credit card ",e);
		}catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Exception occured in CreateServiceOrderDelegateImpl --> postSimpleBuyerServiceOrder() "
					+" due to "+e.getMessage(),e);
			throw new DelegateException("Error posting service order ",e);
		}
				
		return creditCardMap;
	}
	
	public CreditCardDTO getActiveCreditCardDetails(Integer entityId) throws DelegateException {
		CreditCardVO creditCardVO = null;
		CreditCardDTO creditCardDTO = null;
		try {
			creditCardVO = creditCardBO.getActiveCreditCardDetails(entityId);
			creditCardDTO = CreateServiceOrderCreateAccountMapper.mapCreditCardVoToDTO(creditCardVO);
		}catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Exception occured in CreateServiceOrderDelegateImpl --> getActiveCreditCardDetails() "
					+" due to "+e.getMessage());
			throw new DelegateException("Error loading credit card details ",e);
		}
		return creditCardDTO;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate#getBuyerResourceInfo(java.lang.Integer)
	 */
	public CreateServiceOrderEditAccountDTO getBuyerResourceInfo(Integer buyerResId){
		CreateServiceOrderEditAccountDTO csoEditAccountDTO = new CreateServiceOrderEditAccountDTO();
		try {
			Contact contactInfo = simpleBuyerLocationBO.getBuyerResourceInfo(buyerResId);
			if (contactInfo != null){
				csoEditAccountDTO.setAddress(new Address());
				csoEditAccountDTO.setLocId(contactInfo.getLocationId());
				csoEditAccountDTO.setBuyerResId(contactInfo.getResourceId());
				csoEditAccountDTO.setContactId(contactInfo.getContactId());
				csoEditAccountDTO.setFirstName(contactInfo.getFirstName());
				csoEditAccountDTO.setLastName(contactInfo.getLastName());
				csoEditAccountDTO.setEmail(contactInfo.getEmail());
				csoEditAccountDTO.setPrimaryPhone(contactInfo.getPhoneNo());
				csoEditAccountDTO.setSecondaryPhone(contactInfo.getCellNo());
				csoEditAccountDTO.getAddress().setStreetNumber(contactInfo.getStreet_1());
				csoEditAccountDTO.getAddress().setStreetName(contactInfo.getStreet_2());
				csoEditAccountDTO.getAddress().setAptNumber(contactInfo.getAptNo());
				csoEditAccountDTO.getAddress().setCity(contactInfo.getCity());
				csoEditAccountDTO.getAddress().setState(contactInfo.getStateCd());
				csoEditAccountDTO.getAddress().setZipCode(contactInfo.getZip());
			}
		} catch (DBException e) {
			logger.info("Error retrieving buyer resource",e);
		}
		return csoEditAccountDTO;
	}
	
	public ICreditCardBO getCreditCardBO() {
		return creditCardBO;
	}

	public void setCreditCardBO(ICreditCardBO creditCardBO) {
		this.creditCardBO = creditCardBO;
	}

	public ISimpleBuyerLocationBO getSimpleBuyerLocationBO() {
		return simpleBuyerLocationBO;
	}

	public void setSimpleBuyerLocationBO(
			ISimpleBuyerLocationBO simpleBuyerLocationBO) {
		this.simpleBuyerLocationBO = simpleBuyerLocationBO;
	}

	public void deleteLocationByLocationId(Integer locId)
			throws DelegateException {
		// TODO Auto-generated method stub
		
	}

	public List<com.newco.marketplace.dto.vo.LocationVO> loadLocationsByResourceId(
			Integer buyerResId) throws DelegateException {
		List<com.newco.marketplace.dto.vo.LocationVO> locVOs = simpleBuyerLocationBO.getBuyerResourceLocationList(buyerResId);
		return locVOs;
	}

	public void saveLocation(com.newco.marketplace.dto.vo.LocationVO location,
			Integer buyerResId) throws DelegateException {
		LocationVO locVO = simpleBuyerLocationBO.insertBuyerResLoc(location, buyerResId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate#updateBuyerResourceInfo(com.newco.marketplace.web.dto.simple.CreateServiceOrderEditAccountDTO)
	 */
	public void updateBuyerResourceInfo(
			CreateServiceOrderEditAccountDTO csoEditAccountDTO) {
		
		try {
			Contact contactInfo = new Contact();
			contactInfo.setContactId(csoEditAccountDTO.getContactId());
			contactInfo.setPhoneNo(csoEditAccountDTO.getPrimaryPhone());
			contactInfo.setCellNo(csoEditAccountDTO.getSecondaryPhone());
			contactInfo.setEmail(csoEditAccountDTO.getEmail());
			simpleBuyerLocationBO.updateBuyerResourceInfo(contactInfo);
		} catch (DBException e) {
			logger.info("Error updating buyer resource",e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate#loadBuyerPrimaryContact(java.lang.Integer, java.lang.Integer)
	 */
	public Contact loadBuyerPrimaryContact(
			Integer buyerId, Integer buyerResourceId) throws DelegateException {
		ContactLocationVO contactLocationVO = buyerBo.getBuyerResContactLocationVO(buyerId, buyerResourceId);
		com.newco.marketplace.dto.vo.LocationVO locationVO = null;
		if(contactLocationVO != null){
			return contactLocationVO.getBuyerPrimaryContact();		
		}
		return null;
	}

	public boolean checkTaxPayerIdRequired(Integer buyerId, Double totalSpendLimit){
		String ein = null;
		boolean result = Boolean.FALSE;
		try {
			ein = buyerBo.getBuyerEIN(buyerId);
			
			if(!StringUtils.isNotBlank(ein)){
				result = getLedgerTransactionBO().isDepositOverLimit(buyerId, totalSpendLimit);	
			}
		} catch (BusinessServiceException e) {
			logger.error("Error getting buyer ein number");
			result = Boolean.FALSE;
		}		
		return result;
	}
	public void saveTaxPayerId(Integer buyerId, String ein) throws DelegateException{
		try {
			buyerBo.updateBuyerEIN(buyerId, ein);
			//fetch buyer business name and user name as input for saving EIN in PII history
			Buyer buyer = buyerBo.getBuyerName(buyerId);
			String businessName = null;
			String userName = null;
			if (buyer != null){
				businessName = buyer.getBusinessName();
				userName = buyer.getUserName();
			}
			//save EIN information in PII history
			buyerPIIBo.saveBuyerEIN(buyerId, ein, businessName, userName);
			
		} catch (BusinessServiceException e) {
			logger.error("Error saving tax payer id. "+e.getMessage());
			throw new DelegateException("Error saving tax payer id",e);
		}
	}
	//old code
	public void saveSSN(Integer buyerId, String ssn) throws DelegateException{
		try {
			buyerBo.updateBuyerSSN(buyerId, ssn);
		} catch (BusinessServiceException e) {
			logger.error("Error saving ssn id. "+e.getMessage());
			throw new DelegateException("Error saving ssn id",e);
		}
	}
	
	public void saveSSNId(Integer buyerId, String ssn, String dob) throws DelegateException{
		try {
			buyerBo.updateBuyerSSNId(buyerId, ssn, dob);
			//fetch buyer business name and user name as input for saving SSN in PII history
			Buyer buyer = buyerBo.getBuyerName(buyerId);
			String businessName = null;
			String userName = null;
			if (buyer != null){
				businessName = buyer.getBusinessName();
				userName = buyer.getUserName();
			}
			//save SSN information in PII history
			buyerPIIBo.saveBuyerSSN(buyerId, ssn, dob, businessName, userName);
			
		} catch (BusinessServiceException e) {
			logger.error("Error saving ssn id. "+e.getMessage());
			throw new DelegateException("Error saving ssn id",e);
		}
	}
	
	//save alternate tax id information	
	public void saveAltTaxId(Integer buyerId, String documentType, String documentNo, String country, String dob) throws DelegateException{
		try {
			buyerBo.updateBuyerAltId(buyerId, documentType, documentNo, country, dob);
			
			//fetch buyer business name and user name as input for saving Alternate tax id in PII history
			Buyer buyer = buyerBo.getBuyerName(buyerId);
			String businessName = null;
			String userName = null;
			if (buyer != null){
				businessName = buyer.getBusinessName();
				userName = buyer.getUserName();
			}
			//prepare input for saving Alternate tax id information in PII history
			Buyer buyerVO = new Buyer();
			buyerVO.setBuyerId(buyerId);
			buyerVO.setAltIDDocType(documentType);
			buyerVO.setEinNoEnc(documentNo);
			buyerVO.setAltIDCountryIssue(country);
			buyerVO.setDob(dob);
			
			buyerVO.setBusinessName(businessName);
			buyerVO.setUserName(userName);
			
			//save Alternate tax id information in PII history
			buyerPIIBo.saveBuyerAltId(buyerVO);
			
		} catch (BusinessServiceException e) {
			logger.error("Error saving ssn id. "+e.getMessage());
			throw new DelegateException("Error saving ssn id",e);
		}
	}
	
	public List<String> getBlackoutStates() throws DelegateException {
		
		List<String> blackoutStates = new ArrayList<String>();
		try {
			blackoutStates = buyerRegistrationBO.getBlackoutStates();
		} catch(BusinessServiceException e) {
			logger.debug("Exception thrown retrieving Blackout States", e);
			e.printStackTrace();
		}
		
		return blackoutStates;		
	}
	
	public void saveDocuments(String simpleBuyerId, String soId, Integer entityId) {
		try {
			getDocumentBO().persistSimpleBuyerDocuments(simpleBuyerId, soId, entityId);
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Ignoring no business rule.", e);
		}
	}

	public ILedgerTransactionBO getLedgerTransactionBO() {
		return ledgerTransactionBO;
	}

	public void setLedgerTransactionBO(ILedgerTransactionBO ledgerTransactionBO) {
		this.ledgerTransactionBO = ledgerTransactionBO;
	}

	public ISOWizardPersistDelegate getIsoWizardPersistDelegate() {
		return isoWizardPersistDelegate;
	}

	public void setIsoWizardPersistDelegate(
			ISOWizardPersistDelegate isoWizardPersistDelegate) {
		this.isoWizardPersistDelegate = isoWizardPersistDelegate;
	}
	
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public IFinanceManagerDelegate getFinanceManagerDelegate()
	{
		return financeManagerDelegate;
	}

	public void setFinanceManagerDelegate(
			IFinanceManagerDelegate financeManagerDelegate)
	{
		this.financeManagerDelegate = financeManagerDelegate;
	}

	public IBuyerRegistrationBO getbuyerRegistrationBO() {
		return buyerRegistrationBO;
	}

	public void setbuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	}
	
	public ISOWizardFetchDelegate getFetchDelegate() {
		return fetchDelegate;
	}
	public void setFetchDelegate(ISOWizardFetchDelegate fetchDelegate) {
		this.fetchDelegate = fetchDelegate;
	}

	public IBuyerPersIdentificationBO getBuyerPIIBo() {
		return buyerPIIBo;
	}

	public void setBuyerPIIBo(IBuyerPersIdentificationBO buyerPIIBo) {
		this.buyerPIIBo = buyerPIIBo;
	}
	
}

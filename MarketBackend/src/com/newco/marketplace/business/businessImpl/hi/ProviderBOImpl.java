package com.newco.marketplace.business.businessImpl.hi;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.audit.email.IAuditEmailBO;
import com.newco.marketplace.business.iBusiness.hi.IProviderBO;
import com.newco.marketplace.business.iBusiness.provider.IAuditBO;
import com.newco.marketplace.business.iBusiness.provider.IInsuranceTypePolicyBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderEmailBO;
import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AdminConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.daoImpl.alert.AlertDaoImpl;
import com.newco.marketplace.persistence.daoImpl.template.TemplateDaoImpl;
import com.newco.marketplace.persistence.iDao.audit.AuditDao;
import com.newco.marketplace.persistence.iDao.audit.IAuditEmailDao;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IAuditDao;
import com.newco.marketplace.persistence.iDao.provider.IBusinessinfoDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.IContactMethodPrefDao;
import com.newco.marketplace.persistence.iDao.provider.ICredentialDao;
import com.newco.marketplace.persistence.iDao.provider.ILicensesAndCertDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IResourceLocationDao;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorContactDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorFinanceDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorNotesDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorPolicyDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao;
import com.newco.marketplace.persistence.iDao.provider.IWarrantyDao;
import com.newco.marketplace.persistence.iDao.provider.IWorkflowDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.hi.provider.ApproveFirmsVO;
import com.newco.marketplace.vo.hi.provider.ApproveProvidersVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckHistoryProviderVO;
import com.newco.marketplace.vo.hi.provider.BackgroundCheckProviderVO;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;
import com.newco.marketplace.vo.hi.provider.ProviderRegistrationVO;
import com.newco.marketplace.vo.hi.provider.ReasonCodeVO;
import com.newco.marketplace.vo.provider.AuditEmailVo;
import com.newco.marketplace.vo.provider.AuditTemplateMappingVo;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.BusinessinfoVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.FinanceProfile;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorLocation;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.newco.marketplace.vo.provider.WorkflowStateVO;
import com.newco.marketplace.webservices.base.Template;



public class ProviderBOImpl implements IProviderBO {
	private static final Logger LOGGER = Logger.getLogger(ProviderBOImpl.class.getName());
	private static final int ADDRESS_TYPE_HOME = 3;
	private List businessStructureList;
	private List stateList ;
	private boolean errorOccured = false;
	private IContactDao iContactDao;
	private IContactMethodPrefDao iContactMethodPrefDao;
	private ILocationDao iLocationDao;
	private IResourceLocationDao iResourceLocationDao;
	private ILookupDAO iLookupDAO;
	private IUserProfileDao iUserProfileDao;
	private IVendorContactDao iVendorContactDao;
	private IVendorHdrDao iVendorHdrDao;
	private IVendorLocationDao iVendorLocationDao;
	private IVendorResourceDao iVendorResourceDao;
	private IProviderEmailBO iProviderEmailBO;
	private IVendorFinanceDao iVendorFinanceDao;
	private IVendorPolicyDao iVendorPolicyDao;
	private IActivityRegistryDao activityRegistryDao;
	private IAuditBO auditBO;
	private SecurityDAO securityDao;
	private String defaultSourceId;
	private IRegistrationBO providerRegistrationBO;
	private IZipDao zipDao;
	private LookupDao commonLookkupDAO; 
	private IWarrantyDao iWarrantyDao;
	private ICredentialDao credentialDao;
	private IInsuranceTypePolicyBO iInsurancePolicyBO;
	private ILicensesAndCertDao iLicensesAndCertDao;
	/**Added for ApproveFirm start*/
	private AuditDao auditDao;
	private IVendorNotesDao vendorNotesDao;
	private IAuditEmailDao auditEmailDao;
	private ITemplateDao templateDao;
	private AlertDaoImpl alertDao;
	private TemplateDaoImpl templateDaoImpl;
	private VelocityEngine velocityEngine = null;
	private JavaMailSender mailSender = null;
	private HashMap<Integer, AuditTemplateMappingVo> auditEmailTemplateMappings = null;
	/**Added for ApproveFirm end*/
	private IBusinessinfoDao iBusinessinfoDao;
	private  IW9RegistrationDao  w9RegistrationDao;
	private List<WorkflowStateVO> workflowStates = null;
	
	private Cryptography cryptography;
	private IAuditEmailBO auditEmailBean = null;
	
	//Added for approve provider
	private IWorkflowDao workflowDao;
	private IAuditDao iauditDao;
	private static final MessageResources messages = MessageResources.getMessageResources("BizLocalStrings");
	
	
	public IAuditEmailBO getAuditEmailBean() {
		return auditEmailBean;
	}

	public void setAuditEmailBean(IAuditEmailBO auditEmailBean) {
		this.auditEmailBean = auditEmailBean;
	}

	public String getDefaultSourceId() {
		return defaultSourceId;
	}

	public void setDefaultSourceId(String defaultSourceId) {
		this.defaultSourceId = defaultSourceId;
	}

	public IWarrantyDao getiWarrantyDao() {
		return iWarrantyDao;
	}

	public void setiWarrantyDao(IWarrantyDao iWarrantyDao) {
		this.iWarrantyDao = iWarrantyDao;
	}
	// validate the firm registration details send via create Firm API
	public ProviderRegistrationVO validateFirmDetails(ProviderRegistrationVO providerRegistrationVO) {
		errorOccured=false;
		validatePrimaryIndustry(providerRegistrationVO);
		if(!errorOccured && null!=providerRegistrationVO.getBusStartDt()){
			validateBusinessStartDate(providerRegistrationVO);	
		}
		if(!errorOccured){ 
			validateBusinessStructure(providerRegistrationVO);	
		}
		if(!errorOccured){
			validateForeignOwned(providerRegistrationVO);	
		}
		if(!errorOccured){
			validateCompanySize(providerRegistrationVO);	
		}
		if(!errorOccured){
			validateAnnualReveneue(providerRegistrationVO);
		}
		if(!errorOccured){
			validateBusinessState(providerRegistrationVO);
		}
		if(!errorOccured){
			validateBusinessStateZip(providerRegistrationVO);
		}
		if(!errorOccured){
			validateMailingState(providerRegistrationVO);
		}
		if(!errorOccured){
			validateMailingStateZip(providerRegistrationVO);
		}
		if(!errorOccured){
			validateRoleWithinCom(providerRegistrationVO);
		}
		if(!errorOccured){
			validateUserName(providerRegistrationVO);
		}
		if(!errorOccured){
			validateHowDidYouHear(providerRegistrationVO);
		}
		if(!errorOccured){
			validateWarrantyPeriods(providerRegistrationVO);
		}
		if(!errorOccured)
		{
			validateWarrantyInfo(providerRegistrationVO);
		}
		if(!errorOccured){
			validateLicenseCreateFirm(providerRegistrationVO);
		}
		if(!errorOccured && null!=providerRegistrationVO.getLicensesList()){
			validateLicense(providerRegistrationVO,providerRegistrationVO.getLicensesList());
		}

		if(!errorOccured){
			validateGeneralLiabilityState(providerRegistrationVO,providerRegistrationVO.getGeneralLiability());
		}

		if(!errorOccured){
			validateVehicleLiabilityState(providerRegistrationVO,providerRegistrationVO.getVehicleLiability());
		}

		if(!errorOccured){
			validateWorkmanCompensationState(providerRegistrationVO,providerRegistrationVO.getWorkmanCompensation());
		}

		if(!errorOccured &&null!=providerRegistrationVO.getW9RegistrationVO()){
			validateW9Info(providerRegistrationVO);
		}

		return providerRegistrationVO;

	} 
	// validate firm registartion deatils send via Update Firm API
	public ProviderRegistrationVO validateUpdateFirmDetails(ProviderRegistrationVO providerRegistrationVO) {

		errorOccured=false;
		if(null!=providerRegistrationVO.getPrimaryIndustry())
		{
			validatePrimaryIndustry(providerRegistrationVO); 
		}

		if(!errorOccured && null!=providerRegistrationVO.getBusStartDt()){
			validateBusinessStartDate(providerRegistrationVO);	
		}

		if(!errorOccured && null!=providerRegistrationVO.getBusStructure()){
			validateBusinessStructure(providerRegistrationVO);	
		}
		if(!errorOccured && null!=providerRegistrationVO.getForeignOwnedPct()){
			validateForeignOwned(providerRegistrationVO);	
		}
		if(!errorOccured && null!=providerRegistrationVO.getCompanySize()){
			validateCompanySize(providerRegistrationVO);	
		}
		if(!errorOccured && null!=providerRegistrationVO.getAnnualSalesRevenue()){
			validateAnnualReveneue(providerRegistrationVO);
		}
		if(!errorOccured && null!=providerRegistrationVO.getBusinessState()){
			validateBusinessState(providerRegistrationVO);
		}
		if(!errorOccured && null!=providerRegistrationVO.getBusinessZip() && null!=providerRegistrationVO.getBusinessState()){
			validateBusinessStateZip(providerRegistrationVO);
		}
		if(!errorOccured && null!=providerRegistrationVO.getMailingState()){
			validateMailingState(providerRegistrationVO);
		}
		if(!errorOccured && null!=providerRegistrationVO.getMailingZip() && null!=providerRegistrationVO.getMailingState()){
			validateMailingStateZip(providerRegistrationVO);
		}

		if(!errorOccured){
			validateWarrantyPeriods(providerRegistrationVO);
		}

		if(!errorOccured)
		{
			validateWarrantyInfo(providerRegistrationVO);
		}

		if(!errorOccured && null!=providerRegistrationVO.getLicensesList()){
			validateLicense(providerRegistrationVO,providerRegistrationVO.getLicensesList());
		}

		if(!errorOccured && null!=providerRegistrationVO.getEditLicensesList()){
			validateLicense(providerRegistrationVO,providerRegistrationVO.getEditLicensesList());
		}

		if(!errorOccured){
			validateGeneralLiabilityState(providerRegistrationVO,providerRegistrationVO.getGeneralLiability());
		}


		if(!errorOccured){
			validateVehicleLiabilityState(providerRegistrationVO,providerRegistrationVO.getVehicleLiability());
		}

		if(!errorOccured){
			validateWorkmanCompensationState(providerRegistrationVO,providerRegistrationVO.getWorkmanCompensation());
		}

		if(!errorOccured &&null!=providerRegistrationVO.getW9RegistrationVO()){
			validateW9Info(providerRegistrationVO);
		}

		return providerRegistrationVO;
	} 

	// validate Business Start Date
	private ProviderRegistrationVO validateBusinessStartDate(ProviderRegistrationVO providerRegistrationVO)
	{
		try
		{

			Date currentDate = new Date();		
			Date dateFrom = providerRegistrationVO.getBusStartDt();			
			if(null!=dateFrom && dateFrom.after(currentDate))
			{				
				Results results = Results.getError(ResultsCode.INVALID_BUSINESS_DATE_FIRM
						.getMessage(), ResultsCode.INVALID_BUSINESS_DATE_FIRM
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}

		}
		catch(Exception e)
		{
			errorOccured = true;
			LOGGER.error("Exception in validateBusinessStartDate:"+e);
		}
		return providerRegistrationVO;
	}


	// validate License
	public ProviderRegistrationVO validateLicenseCreateFirm(ProviderRegistrationVO providerRegistrationVO)
	{
		if(providerRegistrationVO.isLicensePresent() && !providerRegistrationVO.isLicenseNotNeeded()){

			if(null==providerRegistrationVO.getLicensesList() || providerRegistrationVO.getLicensesList().size()==0){
				Results results = Results.getError(ResultsCode.INVALIDE_FIRM_LICENSE
						.getMessage(), ResultsCode.INVALIDE_FIRM_LICENSE
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		}
		return providerRegistrationVO;		
	}

	// validate License
	public ProviderRegistrationVO validateLicense(ProviderRegistrationVO providerRegistrationVO,List<LicensesAndCertVO> licensesAndCertVOlist){
		try{
			LicensesAndCertVO objLicensesAndCertVO=new LicensesAndCertVO();
			List stateList = iLicensesAndCertDao.getMapState();
			List credTypes=iLookupDAO.getCredentialTypes();
			if(null!=licensesAndCertVOlist){
				for(LicensesAndCertVO licensesAndCertVO:licensesAndCertVOlist){

					if(null!=providerRegistrationVO.getVendorId())
					{
						licensesAndCertVO.setVendorId(providerRegistrationVO.getVendorId());
					}
					if(licensesAndCertVO.getVendorCredId() > 0 && !iLicensesAndCertDao.isVendorCredentialIdExist(licensesAndCertVO))
					{
						Results results = Results.getError(ResultsCode.INVALID_LICENSE_VENDOR_CREDENTIAL_ID_FIRM
								.getMessage(), ResultsCode.INVALID_LICENSE_VENDOR_CREDENTIAL_ID_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
						return providerRegistrationVO;
					}

					String credTypeExist =null;
					if(!errorOccured)
					{
						credTypeExist=validateLookUpValues(credTypes,licensesAndCertVO.getCredTypeDesc());	

						if(credTypeExist!=null){
							licensesAndCertVO.setCredentialTypeId(Integer.parseInt(credTypeExist));
						}
						else{
							Results results = Results.getError(ResultsCode.INVALID_CREDENTIAL_TYPE_FIRM
									.getMessage(), ResultsCode.INVALID_CREDENTIAL_TYPE_FIRM
									.getCode());
							providerRegistrationVO.setResults(results);
							errorOccured = true;
							return providerRegistrationVO;
						}

					}
					if(!errorOccured){
						List credCategoryTypes=iLookupDAO.getCredentailCategoriesByType(Integer.parseInt(credTypeExist));

						String categoryTypeExist=validateLookUpValues(credCategoryTypes,licensesAndCertVO.getCategoryTypeDesc());
						if(categoryTypeExist!=null){
							licensesAndCertVO.setCategoryId(Integer.parseInt(categoryTypeExist));
						}
						else{
							Results results = Results.getError(ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM
									.getMessage(), ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM
									.getCode());
							providerRegistrationVO.setResults(results);
							errorOccured = true;
							return providerRegistrationVO;
						}
					}

					if(!errorOccured){
						String stateExist=validateStateValues(stateList,licensesAndCertVO.getStateId());

						if(stateExist!=null){
							licensesAndCertVO.setStateId(stateExist);
						}
						else{
							Results results = Results.getError(ResultsCode.INVALID_LICENSE_STATE_FIRM
									.getMessage(), ResultsCode.INVALID_LICENSE_STATE_FIRM
									.getCode());
							providerRegistrationVO.setResults(results);
							errorOccured = true;
							return providerRegistrationVO;
						}
					}

					if(!errorOccured)
					{
						Date expirationDate = licensesAndCertVO.getExpirationDate();		
						Date issueDate = licensesAndCertVO.getIssueDate();			
						if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate))
						{				
							Results results = Results.getError(ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM
									.getMessage(), ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM
									.getCode());
							providerRegistrationVO.setResults(results);
							errorOccured=true;
							return providerRegistrationVO;
						}
					}

				}
			}

		}catch(Exception e){
			errorOccured = true;
			LOGGER.error("Exception in validateLicense:",e);
		}
		return providerRegistrationVO;
	}


	// retrieve look up id for a String
	private String validateLookUpValues(List valueList, String value) {
		Iterator itr = valueList.iterator();
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getId();
			if (null!=value && value.equals(validValue)) {
				return id;
			}
		}
		return null;
	}




	// validate BusinessStructure
	public ProviderRegistrationVO validateBusinessStructure(ProviderRegistrationVO providerRegistrationVO){
		try {
			businessStructureList =iLookupDAO.loadBusinessTypes();
			String businessTypeExist=validateLookUpValues(businessStructureList,providerRegistrationVO.getBusStructure());	
			if(businessTypeExist!=null){
				providerRegistrationVO.setBusStructure(businessTypeExist);
			}		
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error("Exception in validateBusinessStructure"+e);
		}		 
		return providerRegistrationVO;
	}


	// validate W9Info
	public ProviderRegistrationVO validateW9Info(ProviderRegistrationVO providerRegistrationVO){
		try {

			if(null==businessStructureList){
				businessStructureList =iLookupDAO.loadBusinessTypes();
			}
			Integer businessTypeExistInt =null;
			String businessTypeExist=validateLookUpValues(businessStructureList,providerRegistrationVO.getTaxStatus());	
			if(businessTypeExist!=null){
				businessTypeExistInt=Integer.parseInt(businessTypeExist);
				providerRegistrationVO.getW9RegistrationVO().getTaxStatus().setId(businessTypeExistInt);
			}	


			if(!errorOccured){
				if(null!=businessTypeExistInt && businessTypeExistInt.equals(Constants.S_CORPORATION))
				{
					if(providerRegistrationVO.getW9RegistrationVO().getTaxPayerTypeId() == Constants.SSN)
					{
						Results results = Results.getError(ResultsCode.INVALID_W9_TAX_PAYER_ID_EIN_FIRM
								.getMessage(), ResultsCode.INVALID_W9_TAX_PAYER_ID_EIN_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
					}
				}
			}

			if(!errorOccured){
				if(null!=businessTypeExistInt && businessTypeExistInt.equals(Constants.INDIVIDUAL))
				{
					if(providerRegistrationVO.getW9RegistrationVO().getTaxPayerTypeId() == Constants.EIN)
					{
						Results results = Results.getError(ResultsCode.INVALID_W9_TAX_PAYER_ID_SSN_FIRM
								.getMessage(), ResultsCode.INVALID_W9_TAX_PAYER_ID_SSN_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
					}
				}
			}

			if(!errorOccured){
				if(null==stateList ){
					stateList = providerRegistrationBO.getStates();
				}
				String stateExist=validateStateValues(stateList,providerRegistrationVO.getW9RegistrationVO().getAddress().getState());

				if(stateExist!=null){
					providerRegistrationVO.getW9RegistrationVO().getAddress().setState(stateExist);
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_STATE_W9_FIRM
							.getMessage(), ResultsCode.INVALID_STATE_W9_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;
				}
			}

			if(!errorOccured)
			{
				providerRegistrationVO=validateW9StateInfo(providerRegistrationVO);
			}




			if(!errorOccured){
				if(null!=providerRegistrationVO.getW9RegistrationVO().getEin())
				{
					String ein=providerRegistrationVO.getW9RegistrationVO().getEin();
					String tinRestrPattern = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
					if(tinRestrPattern!= null && !"".equals(tinRestrPattern) && tinRestrPattern.contains(ein)){
						Results results = Results.getError(ResultsCode.INVALID_TAXPAYER_ID_FIRM
								.getMessage(), ResultsCode.INVALID_TAXPAYER_ID_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;				
					}
				}	
			}

			if(!errorOccured){
				if(null!=providerRegistrationVO.getW9RegistrationVO().getEin() && providerRegistrationVO.getW9RegistrationVO().getTaxPayerTypeId() == Constants.SSN 
						&& null == providerRegistrationVO.getW9RegistrationVO().getDateOfBirth())
				{


					Results results = Results.getError(ResultsCode.MANDATORY_W9_DATE_OF_BIRTH_FIRM
							.getMessage(), ResultsCode.MANDATORY_W9_DATE_OF_BIRTH_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;				
				}	
			}

			if(!errorOccured){
				if(null!=providerRegistrationVO.getW9RegistrationVO().getEin() && providerRegistrationVO.getW9RegistrationVO().getTaxPayerTypeId() == Constants.SSN 
						&& null!= providerRegistrationVO.getW9RegistrationVO().getDateOfBirth())
				{
					Date date=new Date();
					Date dateOfBirth=providerRegistrationVO.getW9RegistrationVO().getDateOfBirth();
					if(dateOfBirth.after(date))
					{
						Results results = Results.getError(ResultsCode.INVALID_W9_DATE_OF_BIRTH_FIRM
								.getMessage(), ResultsCode.INVALID_W9_DATE_OF_BIRTH_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;		
					}
				}	
			}


			if(!errorOccured){
				if(null!=providerRegistrationVO.getW9RegistrationVO().getEin())
				{
					try{
						String unencryptedEin = providerRegistrationVO.getW9RegistrationVO().getEin();
						CryptographyVO cryptographyVO = new CryptographyVO();
						cryptographyVO.setInput(unencryptedEin);
						cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
						cryptographyVO = cryptography.encryptKey(cryptographyVO);
						String einNo = cryptographyVO.getResponse();
						if(null!=einNo)
						{
							providerRegistrationVO.getW9RegistrationVO().setEin(einNo);
						}
					}catch(Exception e){
						errorOccured = true;
						LOGGER.info("Error in Encrypting EIN/SSN: "+e);
					}
				}
			}
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error(" Exception while encrypting data"+e);
		}		
		return providerRegistrationVO;
	}



	// validate ForeignOwned
	public ProviderRegistrationVO validateForeignOwned(ProviderRegistrationVO providerRegistrationVO)

	{	
		try {
			List foreignOwnedList =iLookupDAO.loadForeignOwnedPercent();
			String foreignOwnedPctExist=validateLookUpValues(foreignOwnedList,providerRegistrationVO.getForeignOwnedPct());	
			if(foreignOwnedPctExist!=null){
				providerRegistrationVO.setForeignOwnedPct(foreignOwnedPctExist);
			}		
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error(" Exception in validating ForeignOwned"+e);
		}		
		return providerRegistrationVO;
	}


	// validate CompanySize
	public ProviderRegistrationVO validateCompanySize(ProviderRegistrationVO providerRegistrationVO)
	{	
		try {
			List companySizeList =iLookupDAO.loadCompanySize();
			String companySizeExist=validateLookUpValues(companySizeList,providerRegistrationVO.getCompanySize());	
			if(companySizeExist!=null){
				providerRegistrationVO.setCompanySize(companySizeExist);
			}		
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error("Exception in validating CompanySize"+e);
		}		
		return providerRegistrationVO;
	}

	// validate AnnualReveneue
	public ProviderRegistrationVO validateAnnualReveneue(ProviderRegistrationVO providerRegistrationVO)
	{	
		try {
			List annualReveneueList =iLookupDAO.loadSalesVolume();
			String annualReveneueExist=validateLookUpValues(annualReveneueList,providerRegistrationVO.getAnnualSalesRevenue());	
			if(annualReveneueExist!=null){
				providerRegistrationVO.setAnnualSalesRevenue(annualReveneueExist);
			}		
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error(" Exception in validating AnnualReveneue"+e);
		}		
		return providerRegistrationVO;
	}

	// validate WarrantyPeriods
	public ProviderRegistrationVO validateWarrantyPeriods(ProviderRegistrationVO providerRegistrationVO)
	{	
		try {
			List warrantyList =null;
			String warrantyExist =null;
			if((null!=providerRegistrationVO.getWarrantyVO() && null!=providerRegistrationVO.getWarrantyVO().getWarrPeriodLabor()))
			{
				warrantyList =iLookupDAO.loadWarrantyPeriods();

				warrantyExist=validateLookUpValues(warrantyList,providerRegistrationVO.getWarrantyVO().getWarrPeriodLabor());	
				if(warrantyExist!=null){
					providerRegistrationVO.getWarrantyVO().setWarrPeriodLabor(warrantyExist);
				}
			}

			if(!errorOccured && null!=providerRegistrationVO.getWarrantyVO() && null!=providerRegistrationVO.getWarrantyVO().getWarrPeriodParts()){
				if(null==warrantyList)
				{
					warrantyList=iLookupDAO.loadWarrantyPeriods();
				}
				warrantyExist=validateLookUpValues(warrantyList,providerRegistrationVO.getWarrantyVO().getWarrPeriodParts());	
				if(warrantyExist!=null){
					providerRegistrationVO.getWarrantyVO().setWarrPeriodParts(warrantyExist);
				}
			}
		} catch (DBException e) {
			errorOccured = true;
			LOGGER.error(" Exception in validate WarrantyPeriods"+e);
		}		
		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateWarrantyInfo(ProviderRegistrationVO providerRegistrationVO)
	{	
		try {

			if(null!=providerRegistrationVO.getWarrantyVO().getConductDrugTest() && providerRegistrationVO.getWarrantyVO().getConductDrugTest().equals("0"))
			{
				if(null==providerRegistrationVO.getWarrantyVO().getConsiderDrugTest())
				{
					Results results = Results.getError(ResultsCode.INVALID_STATE_WARRANTY_DRUG_FIRM
							.getMessage(), ResultsCode.INVALID_STATE_WARRANTY_DRUG_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;
				}

			}

			if(!errorOccured)
			{
				if(null!=providerRegistrationVO.getWarrantyVO().getHasEthicsPolicy() && providerRegistrationVO.getWarrantyVO().getHasEthicsPolicy().equals("0"))
				{
					if(null==providerRegistrationVO.getWarrantyVO().getConsiderEthicPolicy())
					{
						Results results = Results.getError(ResultsCode.INVALID_STATE_WARRANTY_ETHICS_FIRM
								.getMessage(), ResultsCode.INVALID_STATE_WARRANTY_ETHICS_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
					}

				}

			}

			if(!errorOccured)
			{
				if(null!=providerRegistrationVO.getWarrantyVO().getRequireUsDoc() && providerRegistrationVO.getWarrantyVO().getRequireUsDoc().equals("0"))
				{
					if(null==providerRegistrationVO.getWarrantyVO().getConsiderImplPolicy())
					{
						Results results = Results.getError(ResultsCode.INVALID_STATE_WARRANTY_CITIZEN_FIRM
								.getMessage(), ResultsCode.INVALID_STATE_WARRANTY_CITIZEN_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
					}

				}

			}

			if(!errorOccured)
			{
				if(null!=providerRegistrationVO.getWarrantyVO().getRequireBadge() && providerRegistrationVO.getWarrantyVO().getRequireBadge().equals("0"))
				{
					if(null==providerRegistrationVO.getWarrantyVO().getConsiderBadge())
					{
						Results results = Results.getError(ResultsCode.INVALID_STATE_WARRANTY_BADGES_FIRM
								.getMessage(), ResultsCode.INVALID_STATE_WARRANTY_BADGES_FIRM
								.getCode());
						providerRegistrationVO.setResults(results);
						errorOccured = true;
					}

				}

			}


		} catch (Exception e) {
			errorOccured = true;
			LOGGER.info(""+e);
		}		
		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateRoleWithinCom(ProviderRegistrationVO providerRegistrationVO) {

		try{
			List roleWithinCompany = commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID);
			String roleWithinCompanyExist = validateRoleValues(roleWithinCompany,providerRegistrationVO.getRoleWithinCom());
			if(roleWithinCompanyExist!=null){
				providerRegistrationVO.setRoleWithinCom(roleWithinCompanyExist);
			}

		}catch(Exception e){
			errorOccured = true;
			LOGGER.info(""+e);
		}
		return providerRegistrationVO;

	}



	public ProviderRegistrationVO validateBusinessState(ProviderRegistrationVO providerRegistrationVO) {
		stateList = providerRegistrationBO.getStates();
		String businessStateExist = validateStateValues(stateList,providerRegistrationVO.getBusinessState());
		if(businessStateExist!=null){
			providerRegistrationVO.setBusinessState(businessStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_BUSINESS_STATE_FIRM
					.getMessage(), ResultsCode.INVALID_BUSINESS_STATE_FIRM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
		}
		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateMailingState(ProviderRegistrationVO providerRegistrationVO) {

		if(null==stateList ){
			stateList = providerRegistrationBO.getStates();
		}
		String mailStateExist = validateStateValues(stateList,providerRegistrationVO.getMailingState());
		if(mailStateExist!=null){
			providerRegistrationVO.setMailingState(mailStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_MAILING_STATE_FIRM
					.getMessage(), ResultsCode.INVALID_MAILING_STATE_FIRM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
		}
		return providerRegistrationVO;
	}


	public ProviderRegistrationVO validateGeneralLiabilityState(ProviderRegistrationVO providerRegistrationVO,CredentialProfile generalLiability) {


		if(providerRegistrationVO.isInsurancePresent() && null!=providerRegistrationVO.getGeneralLInd() && providerRegistrationVO.getGeneralLInd()){
			if(StringUtils.isBlank(providerRegistrationVO.getGeneralLiabilityAmount())) 
			{
				Results results = Results.getError(ResultsCode.INVALID_GENERAL_LIABILITY_AMT
						.getMessage(), ResultsCode.INVALID_GENERAL_LIABILITY_AMT
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		}

		if(null!=generalLiability){ 
			if(!errorOccured)
			{	
				if(null==stateList ){
					stateList = providerRegistrationBO.getStates();
				}
				String credentialStateExist = validateStateValues(stateList,generalLiability.getCredentialState());
				if(credentialStateExist!=null){
					generalLiability.setCredentialState(credentialStateExist);
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_GENERAL_LIABILITY_STATE_FIRM
							.getMessage(), ResultsCode.INVALID_GENERAL_LIABILITY_STATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;
				}
			}

			if(!errorOccured)
			{
				Date expirationDate = generalLiability.getCredentialExpirationDate();		
				Date issueDate = generalLiability.getCredentialIssueDate();			
				if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_FIRM
							.getMessage(), ResultsCode.INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results); 
					errorOccured=true;
				}
			}

			if(!errorOccured)
			{
				Date todayDate = new Date();		
				Date expirationDate = generalLiability.getCredentialExpirationDate();				
				if(null!=expirationDate && null!=todayDate && expirationDate.before(todayDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getMessage(), ResultsCode.INVALID_GENERAL_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured=true;
				}
			}

		}

		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateVehicleLiabilityState(ProviderRegistrationVO providerRegistrationVO,CredentialProfile vehicleLiability) {

		if(providerRegistrationVO.isInsurancePresent() && null!=providerRegistrationVO.getVehicleLInd() && providerRegistrationVO.getVehicleLInd()){
			if(StringUtils.isBlank(providerRegistrationVO.getVehicleLiabilityAmount()))
			{
				Results results = Results.getError(ResultsCode.INVALID_VEHICLE_LIABILITY_AMT
						.getMessage(), ResultsCode.INVALID_VEHICLE_LIABILITY_AMT
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		}
		if(null!=vehicleLiability){

			if(!errorOccured)
			{
				if(null==stateList ){
					stateList = providerRegistrationBO.getStates();
				}
				String credentialStateExist = validateStateValues(stateList,vehicleLiability.getCredentialState());
				if(credentialStateExist!=null){
					vehicleLiability.setCredentialState(credentialStateExist);
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_VEHICLE_LIABILITY_STATE_FIRM
							.getMessage(), ResultsCode.INVALID_VEHICLE_LIABILITY_STATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;
				}
			}	

			if(!errorOccured)
			{
				Date expirationDate = vehicleLiability.getCredentialExpirationDate();		
				Date issueDate = vehicleLiability.getCredentialIssueDate();			
				if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_FIRM
							.getMessage(), ResultsCode.INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured=true;
				}
			}

			if(!errorOccured)
			{
				Date todayDate = new Date();		
				Date expirationDate = vehicleLiability.getCredentialExpirationDate();				
				if(null!=expirationDate && null!=todayDate && expirationDate.before(todayDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getMessage(), ResultsCode.INVALID_VEHICLE_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured=true;
				}
			}
		}
		return providerRegistrationVO;
	}


	public ProviderRegistrationVO validateWorkmanCompensationState(ProviderRegistrationVO providerRegistrationVO,CredentialProfile workmanCompensation) {

		if(providerRegistrationVO.isInsurancePresent() && null!=providerRegistrationVO.getWorkmanCInd() && providerRegistrationVO.getWorkmanCInd()){
			if(StringUtils.isBlank(providerRegistrationVO.getWorkmanCompensationAmount()))
			{
				Results results = Results.getError(ResultsCode.INVALID_WORKMAN_LIABILITY_AMT
						.getMessage(), ResultsCode.INVALID_WORKMAN_LIABILITY_AMT
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		}

		if(null!=workmanCompensation){
			if(!errorOccured)
			{		
				if(null==stateList ){
					stateList = providerRegistrationBO.getStates();
				}
				String credentialStateExist = validateStateValues(stateList,workmanCompensation.getCredentialState());
				if(credentialStateExist!=null){
					workmanCompensation.setCredentialState(credentialStateExist);
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_WORKMANS_COMPENSATION_STATE_FIRM
							.getMessage(), ResultsCode.INVALID_WORKMANS_COMPENSATION_STATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured = true;
				}
			}

			if(!errorOccured)
			{
				Date expirationDate = workmanCompensation.getCredentialExpirationDate();		
				Date issueDate = workmanCompensation.getCredentialIssueDate();			
				if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_FIRM
							.getMessage(), ResultsCode.INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured=true;
				}
			}

			if(!errorOccured)
			{
				Date todayDate = new Date();		
				Date expirationDate = workmanCompensation.getCredentialExpirationDate();				
				if(null!=expirationDate && null!=todayDate && expirationDate.before(todayDate))
				{				
					Results results = Results.getError(ResultsCode.INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getMessage(), ResultsCode.INVALID_WORKMANS_INSURANCE_EXPIRATION_DATE_PAST_FIRM
							.getCode());
					providerRegistrationVO.setResults(results);
					errorOccured=true;
				}
			}
		}	

		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validatePrimaryIndustry(ProviderRegistrationVO providerRegistrationVO) {
		try{
			List primaryIndList = iLookupDAO.loadPrimaryIndustry();
			String primaryIndExist = validateValues(primaryIndList,providerRegistrationVO.getPrimaryIndustry());
			if(primaryIndExist!=null){
				providerRegistrationVO.setPrimaryIndustry(primaryIndExist);
			}
			else{
				Results results = Results.getError(ResultsCode.INVALID_PRIMARY_INDUSTRY_FIRM
						.getMessage(), ResultsCode.INVALID_PRIMARY_INDUSTRY_FIRM
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;

			}
		}catch(Exception e){
			errorOccured = true;
			LOGGER.info(""+e);
		}
		return providerRegistrationVO;

	}




	public ProviderRegistrationVO validateHowDidYouHear(ProviderRegistrationVO providerRegistrationVO) {
		try{
			if(null!=providerRegistrationVO.getHowDidYouHear()){
				List howDidYouHearList = iLookupDAO.loadReferrals();
				String howDidiYouHearExist = validateValues(howDidYouHearList,providerRegistrationVO.getHowDidYouHear());
				if(howDidiYouHearExist!=null){
					providerRegistrationVO.setHowDidYouHear(howDidiYouHearExist);
				}
			}			

		}catch(Exception e){
			errorOccured = true;
			LOGGER.info(""+e);
		}

		return providerRegistrationVO;
	}

	public static boolean isNumeric(String s) {   
		return java.util.regex.Pattern.matches("\\d+", s);   
	}

	public ProviderRegistrationVO validateLookUpValues(
			ProviderRegistrationVO loadValues,
			ProviderRegistrationVO providerRegistrationVO) {
		List howDidYouHearList = loadValues.getHowDidYouHearList();
		List primaryIndList = loadValues.getPrimaryIndList();
		List roleWithinCompany = loadValues.getRoleWithinCompany();
		List stateList = providerRegistrationBO.getStates();
		String howDidiYouHearExist = validateValues(howDidYouHearList,providerRegistrationVO.getHowDidYouHear());
		String primaryIndExist = validateValues(primaryIndList,providerRegistrationVO.getPrimaryIndustry());
		String roleWithinCompanyExist = validateRoleValues(roleWithinCompany,providerRegistrationVO.getRoleWithinCom());
		String businessStateExist = validateStateValues(stateList,providerRegistrationVO.getBusinessState());
		String mailStateExist = validateStateValues(stateList,providerRegistrationVO.getMailingState());
		if(howDidiYouHearExist!=null){
			providerRegistrationVO.setHowDidYouHear(howDidiYouHearExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_HOW_DID_YOU_HEAR
					.getMessage(), ResultsCode.INVALID_HOW_DID_YOU_HEAR
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;

		}
		if(primaryIndExist!=null){
			providerRegistrationVO.setPrimaryIndustry(primaryIndExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_PRIMARY_INDUSTRY
					.getMessage(), ResultsCode.INVALID_PRIMARY_INDUSTRY
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;

		}
		if(roleWithinCompanyExist!=null){
			providerRegistrationVO.setRoleWithinCom(roleWithinCompanyExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_ROLE_WITH_COM
					.getMessage(), ResultsCode.INVALID_ROLE_WITH_COM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;
		}
		if(businessStateExist!=null){
			providerRegistrationVO.setBusinessState(businessStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_BUSINESS_STATE
					.getMessage(), ResultsCode.INVALID_BUSINESS_STATE
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;
		}
		if(mailStateExist!=null){
			providerRegistrationVO.setMailingState(mailStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_MAILING_STATE
					.getMessage(), ResultsCode.INVALID_MAILING_STATE
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;
		}
		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateLuValuesForUpdate(
			ProviderRegistrationVO loadValues,
			ProviderRegistrationVO providerRegistrationVO) {
		List primaryIndList = loadValues.getPrimaryIndList();
		List stateList = providerRegistrationBO.getStates();

		String primaryIndExist = validateValues(primaryIndList,providerRegistrationVO.getPrimaryIndustry());
		String businessStateExist = validateStateValues(stateList,providerRegistrationVO.getBusinessState());
		String mailStateExist = validateStateValues(stateList,providerRegistrationVO.getMailingState());

		if(primaryIndExist!=null){
			providerRegistrationVO.setPrimaryIndustry(primaryIndExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_PRIMARY_INDUSTRY_FIRM
					.getMessage(), ResultsCode.INVALID_PRIMARY_INDUSTRY_FIRM
					.getCode());
			providerRegistrationVO.setResults(results); 
			errorOccured = true;
			return providerRegistrationVO;

		}

		if(businessStateExist!=null){
			providerRegistrationVO.setBusinessState(businessStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_BUSINESS_STATE_FIRM
					.getMessage(), ResultsCode.INVALID_BUSINESS_STATE_FIRM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;
		}
		if(mailStateExist!=null){
			providerRegistrationVO.setMailingState(mailStateExist);
		}
		else{
			Results results = Results.getError(ResultsCode.INVALID_MAILING_STATE_FIRM
					.getMessage(), ResultsCode.INVALID_MAILING_STATE_FIRM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;
			return providerRegistrationVO;
		}
		return providerRegistrationVO;
	}


	private String validateStateValues(List stateList, String state) {
		Iterator itr = stateList.iterator();
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getType();
			if (state.equals(validValue)) {
				return id;
			}
		}
		return null;
	}

	private String validateRoleValues(List roleWithinCompany,
			String roleWithinCom) {
		Iterator itr = roleWithinCompany.iterator();
		while (itr.hasNext()) {
			com.newco.marketplace.dto.vo.LookupVO lookupVO = ((com.newco.marketplace.dto.vo.LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			Integer id = lookupVO.getId();
			if (roleWithinCom.equals(validValue)) {
				return id.toString();
			}
		}
		return null;
	}

	private String validateValues(List list, String descr) {
		Iterator itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getId();
			if (descr.equals(validValue)) {
				return id;
			}
		}
		return null;
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
	public ProviderRegistrationVO createFirm(
			ProviderRegistrationVO providerRegistrationVO )
					throws DBException, DataAccessException, DataServiceException,
					EmailSenderException, AuditException {

		//ProviderRegistrationVO prvRegResponse = new ProviderRegistrationVO();
		try{
			VendorHdr tempVendorHeader = null;
			VendorHdr vendorHeader = new VendorHdr();
			Contact contact = new Contact();
			UserProfile userProfile = new UserProfile();
			VendorResource vendorResource = new VendorResource();
			Location location = new Location();
			ResourceLocation resourceLocation = new ResourceLocation();
			List<Integer> activityIds=new ArrayList<Integer>();
			// create Password for Firm
			String passwordValue= AdminUtil.generatePassword();
			providerRegistrationVO.setPassword(passwordValue);
			String password = CryptoUtil.encrypt(passwordValue);
			// save details in vendor_hdr table.
			tempVendorHeader = saveVendorHdr(providerRegistrationVO,tempVendorHeader, vendorHeader);
			// save details in contact tables
			saveContact(providerRegistrationVO, contact);
			// save details in user profile tables
			saveUserProfile(providerRegistrationVO,contact,
					userProfile, password);
			// save details in vendor resource table
			vendorResource = saveVendorResource(vendorHeader, contact, userProfile,
					vendorResource,providerRegistrationVO);
			// save resource location details
			saveResourceLocation(vendorResource, location, resourceLocation); 
			// save background check detAils for primary resource
			saveBackgroundCheckInfo(vendorResource);
			// save address and other general information
			saveAddressDetailsAndInfo(providerRegistrationVO, tempVendorHeader,
					vendorHeader, location);
			//providerRegistrationVO.setVendorContactResourceId(vendorResource
			//		.getResourceId());	
			providerRegistrationVO.setVendorId(tempVendorHeader.getVendorId());
			providerRegistrationVO.setVendorContactResourceId(vendorResource
					.getResourceId());
			// save detAils in vendor_finance table
			saveVendorFinance(vendorHeader,providerRegistrationVO);
			// insert activity registry
			saveActivityRegistry(providerRegistrationVO);
			// save audit Information
			saveAuditInfo(providerRegistrationVO);	
			providerRegistrationVO.setPassword(passwordValue);
			// send email for firm registration
			providerRegistrationVO = sendEmail(providerRegistrationVO);
			// save details in vendor_policy details
			saveVendorPolicy(providerRegistrationVO);
			// save license details
			saveLicenseAndCert(providerRegistrationVO);
			// save insurance details
			saveInsuranceDetails(providerRegistrationVO);
			// save W9 information
			saveW9Info(providerRegistrationVO);	
			providerRegistrationVO.setCreatedViaAPI(true);
			// update License and Insurance indicators
			updateVendorLicenseAndInsInd(providerRegistrationVO);
			activityIds.add(Constants.ACTIVITY_ID_BUSINESS_INFO);
			if(providerRegistrationVO.isInsurancePresent()){
				activityIds.add(Constants.ACTIVITY_INURANCE_INFO);                   
			}

			activityIds.add(Constants.ACTIVITY_LICENSE_INFO);

			// update activity registry
			updateVendorActivityIds(activityIds,providerRegistrationVO.getVendorId());
			Results results = new Results();		
			List<Result> resultList = new ArrayList<Result> ();
			Result result = new Result();
			result.setCode(ResultsCode.CREATE_FIRM_SUCCESS.getCode());
			result.setMessage(ResultsCode.CREATE_FIRM_SUCCESS.getMessage());
			resultList.add(result);
			results.setResult(resultList);	
			providerRegistrationVO.setResults(results);
		}catch(Exception ex){
			LOGGER.info("Exception in createFirm of ProviderBOImpl: "+ex);
		}

		return providerRegistrationVO; 

	}

	private void updateVendorLicenseAndInsInd(ProviderRegistrationVO providerRegistrationVO){
		VendorHdr vendorHdr=new VendorHdr();
		vendorHdr.setVendorId(providerRegistrationVO.getVendorId());

		if(providerRegistrationVO.isLicensePresent()){
			if(providerRegistrationVO.isLicenseNotNeeded()){
				vendorHdr.setNoCredInd(true);	
			}else{
				vendorHdr.setNoCredInd(false);
			}
		}else{
			if(providerRegistrationVO.isCreatedViaAPI()){
				vendorHdr.setNoCredInd(true);	
			}

		}


		if(providerRegistrationVO.isInsurancePresent()){

			if(null!=providerRegistrationVO.getVehicleLInd() && providerRegistrationVO.getVehicleLInd())	{
				vendorHdr.setVLI(Constants.ONE);
				vendorHdr.setVLIAmount(providerRegistrationVO.getVehicleLiabilityAmount());
			}else{
				vendorHdr.setVLI(Constants.ZERO);	
			}

			if(null!=providerRegistrationVO.getGeneralLInd() && providerRegistrationVO.getGeneralLInd())	{
				vendorHdr.setCBGLI(Constants.ONE);
				vendorHdr.setCBGLIAmount(providerRegistrationVO.getGeneralLiabilityAmount());
			}else{
				vendorHdr.setCBGLI(Constants.ZERO);

			}

			if(null!=providerRegistrationVO.getWorkmanCInd() && providerRegistrationVO.getWorkmanCInd() )	{
				vendorHdr.setWCI(Constants.ONE);
				vendorHdr.setWCIAmount(providerRegistrationVO.getWorkmanCompensationAmount());
			}else{
				vendorHdr.setWCI(Constants.ZERO);
			}

		}

		try {
			if(providerRegistrationVO.isLicensePresent() || providerRegistrationVO.isInsurancePresent()){
				iVendorHdrDao.updateLicenseAndInsuranceInd(vendorHdr);
			}else if(!providerRegistrationVO.isLicensePresent()  && providerRegistrationVO.isCreatedViaAPI()){
				iVendorHdrDao.updateLicenseAndInsuranceInd(vendorHdr);	
			}


		} catch (Exception e) {
			LOGGER.info("exception in updateVendorActivityIds"+e);
		}
	}

	private void updateVendorActivityIds(List <Integer> activityIds,Integer vendorId){
		try {
			activityRegistryDao.updateActivityStatusIds(vendorId, activityIds);
		} catch (Exception e) {
			LOGGER.info("exception in updateVendorActivityIds"+e);

		}
	}

	private ProviderRegistrationVO sendEmail(
			ProviderRegistrationVO providerRegistrationVO)
					throws EmailSenderException, DBException {
		ProviderRegistrationVO prvRegResponse;
		/**
		 * Need to be changed - EMail is not sent as per the code
		 */
		System.out.println("Not sending mail through velocity context:::::::::::::::::::::::::");
		// Commented since configured through cheetah mail.
		//uncommented
		prvRegResponse=doSendFinalEmailMessage(providerRegistrationVO);
		return prvRegResponse;
	}

	private void saveW9Info(ProviderRegistrationVO providerRegistrationVO) {
		try{

			saveW9Registration(providerRegistrationVO);
		}catch(Exception e){
			LOGGER.info(""+e);
		}
	}

	private void saveLicenseAndCert(
			ProviderRegistrationVO providerRegistrationVO) {
		try{
			saveLicense(providerRegistrationVO);
		}catch(Exception e){
			LOGGER.info(""+e);
		}
	}

	private void saveInsuranceDetails(
			ProviderRegistrationVO providerRegistrationVO) {
		try{
			saveGeneralLiabilityInsurance(providerRegistrationVO);
			saveVehicleLiabilityInsurance(providerRegistrationVO);
			saveWorkmanCompensationInsurance(providerRegistrationVO);




		}catch(Exception e){
			LOGGER.info(""+e);  
		}
	}

	private void saveAuditInfo(ProviderRegistrationVO prvRegResponse)
			throws AuditException {
		try {
			getAuditBO().auditVendorHeader(prvRegResponse.getVendorId(), VENDOR_INCOMPLETE);
			getAuditBO().auditVendorResource(prvRegResponse.getVendorContactResourceId(), RESOURCE_INCOMPLETE);
			getAuditBO().auditResourceBackgroundCheck(prvRegResponse.getVendorContactResourceId(), RESOURCE_BACKGROUND_CHECK_INCOMPLETE);

		} catch (AuditException ae) {
			LOGGER.info("[RegistrationBOImpl] - saveRegistration() - Audit Exception Occured for audit record: saveRegistration()"
					+ ae.getMessage());
			throw ae;
		}
	}

	private void saveActivityRegistry(ProviderRegistrationVO prvRegResponse)
			throws DBException {
		try{
			Integer vendorId = prvRegResponse.getVendorId();
			activityRegistryDao.insertActivityStatus(vendorId.toString());

			String resourceId = prvRegResponse.getVendorContactResourceId().toString();
			activityRegistryDao.insertResourceActivityStatus(resourceId);

		} catch (Exception exp) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while registring vendor activity"
					+ exp.getMessage());
			throw new DBException("SQL Exception @RegistrationBOImpl.doRegister() while registring vendor activity", exp);
		}
	}

	private void saveVendorFinance(VendorHdr vendorHeader,ProviderRegistrationVO providerRegistrationVO) throws DBException {
		// Inserting Into vendor Finance
		FinanceProfile vendorFinance = new FinanceProfile();
		try {
			LOGGER.info("Saving Vendor Finance for registering business");
			vendorFinance.setVendorId(vendorHeader.getVendorId());
			if(null!=providerRegistrationVO.getAnnualSalesRevenue()){
				vendorFinance.setAnnualSalesVolume(Integer.parseInt(providerRegistrationVO.getAnnualSalesRevenue())); 
			}
			iVendorFinanceDao.insert(vendorFinance);
		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Finance  details due to"
					+ dae.getMessage());
			throw dae;
		}
	}

	private void saveVendorPolicy(ProviderRegistrationVO providerRegistrationVO) {
		// Inserting Into vendor Policy
		try {
			LOGGER.info("Saving vendor Policy for registering business");

			WarrantyVO warrantyVO=new  WarrantyVO();
			warrantyVO=providerRegistrationVO.getWarrantyVO();
			warrantyVO.setVendorID(providerRegistrationVO.getVendorId().toString());
			iWarrantyDao.insert(warrantyVO);


		}
		catch (Exception dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Saving vendor Policy  details due to"
					+ dae.getMessage());
		}
	}

	private void saveAddressDetailsAndInfo(
			ProviderRegistrationVO providerRegistrationVO,
			VendorHdr tempVendorHeader, VendorHdr vendorHeader,
			Location location) throws DBException {
		try {
			LOGGER.info("Saving vendor header for registering business");

			/**
			 * Values not entered at this point of time
			 */
			if(null!=providerRegistrationVO.getBusStructure()){
				vendorHeader.setBusinessTypeId(Integer.parseInt(providerRegistrationVO.getBusStructure()));
			}
			vendorHeader.setCompanySizeId(null);

			if(!StringUtils.isBlank(providerRegistrationVO.getDBAName())){
				vendorHeader.setDbaName(providerRegistrationVO.getDBAName());
			}
			vendorHeader.setDunsNo(providerRegistrationVO.getDunsNo());
			vendorHeader.setEinNo(null);
			//set the Encrypted einNo
			vendorHeader.setEinNoEnc(null);
			vendorHeader.setVendorId(tempVendorHeader.getVendorId());
			vendorHeader.setWebAddress(providerRegistrationVO
					.getWebsiteAddress());
			// need to check??? 
			// vendorHeader.setBusinessStartDate("");
			if(null!=providerRegistrationVO.getHowDidYouHear()){
				vendorHeader.setReferralId(new Integer(providerRegistrationVO
						.getHowDidYouHear()));
			}			

			vendorHeader.setPromotionCode(providerRegistrationVO
					.getPromotionCode());
			if(null!=providerRegistrationVO.getCompanySize()){
				vendorHeader.setCompanySizeId(Integer.parseInt(providerRegistrationVO.getCompanySize()));
			}
			vendorHeader.setNoCredInd(null);
			vendorHeader.setTaxStatus(null);
			if(null!=providerRegistrationVO.getIsForeignOwned()){
				vendorHeader.setForeignOwnedInd(Integer.parseInt(providerRegistrationVO.getIsForeignOwned()));
			}
			if(null!=providerRegistrationVO.getForeignOwnedPct()){
				vendorHeader.setForeignOwnedPct(Integer.parseInt(providerRegistrationVO.getForeignOwnedPct()));
			}
			Timestamp dateResult = new Timestamp(providerRegistrationVO.getBusStartDt().getTime());
			vendorHeader.setBusinessStartDate(dateResult);

			/**
			 * Added Vendor Business Phone number, Extension and Fax Number
			 */ // nee to remove
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


			vendorHeader.setFirmType(providerRegistrationVO.getFirmType());
			vendorHeader.setSubContractorId(providerRegistrationVO.getSubContractId());
			vendorHeader.setBusinessDesc(providerRegistrationVO.getDescription());

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
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location Details type id details due to"
					+ dae.getMessage());
			throw dae;
		}
	}

	private void saveBackgroundCheckInfo(VendorResource vendorResource)
			throws DBException {
		// create Back Ground Ckeck location record
		try {
			LOGGER.info("Saving Resource location for registering business");


			// set the backGround Status
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
			tmbcVO.setResourceId(vendorResource.getResourceId().toString());
			tmbcVO.setWfEntity("Team Member Background Check");
			tmbcVO.setBackgroundCheckStatus("Not Started");
			iVendorResourceDao.updateBackgroundCheckStatus(tmbcVO);

		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving backGround Check location Details type id details due to"
					+ dae.getMessage());
			throw dae;
		}
	}

	private void saveResourceLocation(VendorResource vendorResource,
			Location location, ResourceLocation resourceLocation)
					throws DBException {
		try {
			LOGGER.info("Saving location for registering business");
			location.setLocnTypeId(ADDRESS_TYPE_HOME);
			iLocationDao.insert(location);

		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving location type id details due to"
					+ dae.getMessage());
			throw dae;
		}
		// create vendor Resource location record
		try {
			LOGGER.info("Saving Resource location for registering business");
			resourceLocation.setLocationId(location.getLocnId());
			resourceLocation.setResourceId(vendorResource.getResourceId());
			this.iResourceLocationDao.insert(resourceLocation);

		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Resource location Details type id details due to"
					+ dae.getMessage());
			throw dae;
		}
	}

	private VendorResource saveVendorResource(VendorHdr vendorHeader,
			Contact contact, UserProfile userProfile,
			VendorResource vendorResource,ProviderRegistrationVO providerRegistrationVO) throws DBException {
		// Create vendor resource
		try {
			LOGGER.info("Saving vendor resource for registering business");

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


			if(null!=providerRegistrationVO.getOwnerInd()){
				vendorResource.setOwnerInd(providerRegistrationVO.getOwnerInd());	
			}else{
				vendorResource.setOwnerInd(new Integer(0));
			}
			if(null!=providerRegistrationVO.getDispatchInd()){
				vendorResource.setDispatchInd(providerRegistrationVO.getDispatchInd());
			}else{
				vendorResource.setDispatchInd(new Integer(0));
			}
			if(null!=providerRegistrationVO.getManagerInd()){
				vendorResource.setManagerInd(providerRegistrationVO.getManagerInd());
			}else{
				vendorResource.setManagerInd(new Integer(0));
			}
			if(null!=providerRegistrationVO.getSproInd()){
				vendorResource.setSproInd(providerRegistrationVO.getSproInd());
			}else{
				vendorResource.setSproInd(new Integer(0));
			}
			if(null!=providerRegistrationVO.getOtherInd()){
				vendorResource.setOtherInd(providerRegistrationVO.getOtherInd());
			}else{
				vendorResource.setOtherInd(new Integer(0));
			}

			

			vendorResource = iVendorResourceDao.insert(vendorResource);

		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving Vendor Resource Details type id details due to"
					+ dae.getMessage());
			throw dae;
		}
		return vendorResource;
	}

	private void saveUserProfile(ProviderRegistrationVO providerRegistrationVO,Contact contact,
			UserProfile userProfile, String password) throws DBException {
		// Create the user_profile.
		try {
			LOGGER.info("Saving user profile for registering business");
			userProfile.setUserName(providerRegistrationVO.getUserName());
			userProfile.setContactId(contact.getContactId());


			if (password != null) {
				userProfile.setPassword(password);
			}
			userProfile.setPasswordFlag(1);


			providerRegistrationVO.setPassword(userProfile.getPassword());
			providerRegistrationVO.setUserName(providerRegistrationVO.getUserName());

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
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
					+ dae.getMessage());
			throw dae;
		}
		catch (com.newco.marketplace.exception.core.DataServiceException dse) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving user Profile details due to"
					+ dse.getMessage());
			throw new DBException(dse.getMessage());
		}
	}

	private void saveContact(ProviderRegistrationVO providerRegistrationVO,
			Contact contact) throws DBException {
		// Create the contact record
		try {
			LOGGER.info("Saving contact for registering business");
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
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() while saving contact details due to"
					+ dae.getMessage());
			throw dae;
		}
	}

	private VendorHdr saveVendorHdr(
			ProviderRegistrationVO providerRegistrationVO,VendorHdr tempVendorHeader,
			VendorHdr vendorHeader) throws DBException {
		LOGGER.info("Entering ProviderRegistrationResponse.doRegister()");
		try {
			LOGGER.info("Saving vendor header for registering business");
			vendorHeader.setSourceSystemId(getDefaultSourceId());
			vendorHeader.setBusinessName(providerRegistrationVO
					.getLegalBusinessName());
			vendorHeader.setVendorStatusId(1);
			if(providerRegistrationVO.getPrimaryIndustry()!=null&&providerRegistrationVO.getPrimaryIndustry().trim().length()>0){
				vendorHeader.setPrimaryIndustryId(Integer.parseInt(providerRegistrationVO.getPrimaryIndustry()));
				vendorHeader.setOtherPrimaryService(providerRegistrationVO.getOtherPrimaryService());
			}
			tempVendorHeader = iVendorHdrDao.insert(vendorHeader);

			providerRegistrationVO.setVendorId(tempVendorHeader.getVendorId());
		}
		catch (DBException dae) {
			LOGGER.info("SQL Exception @RegistrationBOImpl.doRegister() due to"
					+ dae.getMessage());
			throw dae;
		}
		return tempVendorHeader;
	}


	private void saveW9Registration(
			ProviderRegistrationVO providerRegistrationVO) throws Exception {
		// Update w9 Information
		W9RegistrationVO w9RegistrationVO = new W9RegistrationVO();
		try {
			LOGGER.info("Saving w9 Information for registering business");
			w9RegistrationVO=providerRegistrationVO.getW9RegistrationVO();
			w9RegistrationVO.setVendorId(providerRegistrationVO.getVendorId());	
			w9RegistrationVO.setVersion(new Integer(1));
			w9RegistrationDao.insert(w9RegistrationVO);

		}catch (Exception e) {
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() while updating w9 Information  details due to"
					+ e.getMessage());
			throw e;
		}
	}


	public ProviderRegistrationVO updateFirm(
			ProviderRegistrationVO providerRegistrationVO) throws Exception {

		ProviderRegistrationVO prvRegResponse = new ProviderRegistrationVO();
		List<Integer> activityIds=new ArrayList<Integer>();

		BusinessinfoVO objBusinessinfoDetails =new BusinessinfoVO();
		objBusinessinfoDetails.setVendorId(providerRegistrationVO.getVendorId().toString());
		objBusinessinfoDetails =iBusinessinfoDao.getData(objBusinessinfoDetails);
		if(StringUtils.isNotBlank(objBusinessinfoDetails.getResID())){
			providerRegistrationVO.setVendorContactResourceId(Integer.parseInt(objBusinessinfoDetails.getResID()));
		}


		//For updating firm details in vendor_hdr table and vendor_w9
		updateVendorHdr(providerRegistrationVO);

		//For updating business address,mailing address and contact details
		updateAddressDetails(providerRegistrationVO, objBusinessinfoDetails);

		//For updating vendor finance
		updateVendorFinance(providerRegistrationVO);

		//For updating vendor resource table info for primary resource 
		updateVendorResource(providerRegistrationVO);		

		//For updating vendor Policy
		updateVendorPolicy(providerRegistrationVO);

		//For updating w9 Information
		updateW9Information(providerRegistrationVO);		

		//For Inserting or Updating License and Certification details
		updateLicenseAndCert(providerRegistrationVO);

		//For Inserting or Updating Insurance details
		updateInsuranceDetails(providerRegistrationVO);

		if(providerRegistrationVO.isInsurancePresent()){
			activityIds.add(Constants.ACTIVITY_INURANCE_INFO);
		}
		if(providerRegistrationVO.isLicensePresent()){
			activityIds.add(Constants.ACTIVITY_LICENSE_INFO);
		}

		if(null!=activityIds && activityIds.size()>0){
			updateVendorActivityIds(activityIds,providerRegistrationVO.getVendorId());
		}
		updateVendorLicenseAndInsInd(providerRegistrationVO);

		Results results = new Results();		
		List<Result> resultList = new ArrayList<Result> ();
		Result result = new Result();
		result.setCode(ResultsCode.UDPATE_FIRM_SUCCESS.getCode());
		result.setMessage(ResultsCode.UDPATE_FIRM_SUCCESS.getMessage());
		resultList.add(result);
		results.setResult(resultList);	

		providerRegistrationVO.setResults(results);

		return prvRegResponse;

	}

	private void updateInsuranceDetails(
			ProviderRegistrationVO providerRegistrationVO) {
		//Inserting or Updating Insurance details
		try{
			CredentialProfile credentialProfile=null;
			//Inserting or Updating general liability insurance
			if(null!=providerRegistrationVO.getGeneralLiability())
			{
				providerRegistrationVO.getGeneralLiability().setVendorId(providerRegistrationVO.getVendorId());
				credentialProfile =credentialDao.isInsuranceExist(providerRegistrationVO.getGeneralLiability());
				if(null!=credentialProfile)
				{
					providerRegistrationVO.getGeneralLiability().setCredentialId(credentialProfile.getVendorCredId());
					updateGeneralLiabilityInsurance(providerRegistrationVO);
				}
				else
				{
					saveGeneralLiabilityInsurance(providerRegistrationVO);
				}
			}


			//Inserting or Updating vehicle liability insurance
			if(null!=providerRegistrationVO.getVehicleLiability())
			{
				providerRegistrationVO.getVehicleLiability().setVendorId(providerRegistrationVO.getVendorId());
				credentialProfile= credentialDao.isInsuranceExist(providerRegistrationVO.getVehicleLiability());
				if(null!=credentialProfile)
				{
					providerRegistrationVO.getVehicleLiability().setCredentialId(credentialProfile.getVendorCredId());
					updateVehicleLiabilityInsurance(providerRegistrationVO);
				}
				else
				{
					saveVehicleLiabilityInsurance(providerRegistrationVO);
				}

			}


			//Inserting or Updating workman compensation insurance
			if(null!=providerRegistrationVO.getWorkmanCompensation())
			{
				providerRegistrationVO.getWorkmanCompensation().setVendorId(providerRegistrationVO.getVendorId());
				credentialProfile= credentialDao.isInsuranceExist(providerRegistrationVO.getWorkmanCompensation());
				if(null!=credentialProfile)
				{
					providerRegistrationVO.getWorkmanCompensation().setCredentialId(credentialProfile.getVendorCredId());
					updateWorkmanCompensationInsurance(providerRegistrationVO);
				}
				else
				{
					saveWorkmanCompensationInsurance(providerRegistrationVO);
				}

			}

		}catch(Exception e){
			LOGGER.info(""+e);  
		}
	}

	private void updateLicenseAndCert(
			ProviderRegistrationVO providerRegistrationVO) {
		//Inserting or Updating License and Certification details
		try{

			//Inserting License and Certification details
			if(null!=providerRegistrationVO.getLicensesList() && !providerRegistrationVO.getLicensesList().isEmpty())
			{
				saveLicense(providerRegistrationVO);
			}

			//Updating License and Certification details
			if(null!=providerRegistrationVO.getEditLicensesList() && !providerRegistrationVO.getEditLicensesList().isEmpty())
			{
				updateLicense(providerRegistrationVO);
			}
		}catch(Exception e){
			LOGGER.info(""+e);
		}
	}

	private void updateW9Information(
			ProviderRegistrationVO providerRegistrationVO) throws Exception {
		// Update w9 Information
		W9RegistrationVO w9RegistrationVO = new W9RegistrationVO();
		try {
			LOGGER.info("Saving w9 Information for registering business");
			w9RegistrationVO=providerRegistrationVO.getW9RegistrationVO();


			if (null!=w9RegistrationVO)
			{	
				w9RegistrationVO.setVendorId(providerRegistrationVO.getVendorId());
				if(w9RegistrationDao.isW9Exists(providerRegistrationVO.getVendorId()))
				{	
					int versionNo = 0 ;
					W9RegistrationVO temp = w9RegistrationDao.get(providerRegistrationVO.getVendorId());
					versionNo = temp.getVersion() + 1;
					w9RegistrationVO.setVersion(versionNo);
					w9RegistrationDao.update(w9RegistrationVO);
				}else
				{
					w9RegistrationVO.setVersion(new Integer(1));
					w9RegistrationDao.insert(w9RegistrationVO);
				}
			}
			else{
				if(null!=providerRegistrationVO.getVendorId() && StringUtils.isNotBlank(providerRegistrationVO.getBusStructure())){
					BusinessinfoVO objBusinessinfoVO=new BusinessinfoVO();
					objBusinessinfoVO.setVendorId(providerRegistrationVO.getVendorId().toString());
					objBusinessinfoVO.setBusStructure(providerRegistrationVO.getBusStructure());
					iBusinessinfoDao.updateW9ForVendorHdr(objBusinessinfoVO);	
				}
			}

		}catch (Exception e) {
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() while updating w9 Information  details due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void updateVendorPolicy(
			ProviderRegistrationVO providerRegistrationVO) throws Exception {
		// Update vendor Policy
		WarrantyVO warrantyVO = new WarrantyVO();
		try {
			LOGGER.info("Saving vendor Policy for registering business");

			warrantyVO=providerRegistrationVO.getWarrantyVO();
			if(null!=warrantyVO)
			{
				warrantyVO.setVendorID(providerRegistrationVO.getVendorId().toString());
				iWarrantyDao.updateWarrantyPartialData(warrantyVO);

			}	
		}
		catch (Exception e) {
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() while saving Saving vendor Policy  details due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void updateVendorResource(
			ProviderRegistrationVO providerRegistrationVO) throws DBException {
		//Updating vendor resource table info for primary resource 

		BusinessinfoVO objBusinessinfoVO =new BusinessinfoVO();

		objBusinessinfoVO.setVendorId(providerRegistrationVO.getVendorId().toString());

		if(null!=providerRegistrationVO.getOwnerInd())
		{
			objBusinessinfoVO.setOwnerInd(providerRegistrationVO.getOwnerInd());
		}else
		{
			objBusinessinfoVO.setOwnerInd(-1);
		}

		if(null!=providerRegistrationVO.getDispatchInd())
		{
			objBusinessinfoVO.setDispatchInd(providerRegistrationVO.getDispatchInd());
		}else
		{
			objBusinessinfoVO.setDispatchInd(-1);
		}

		if(null!=providerRegistrationVO.getSproInd())
		{
			objBusinessinfoVO.setSproInd(providerRegistrationVO.getSproInd());
		}
		else
		{
			objBusinessinfoVO.setSproInd(-1);
		}


		if(null!=providerRegistrationVO.getManagerInd())
		{
			objBusinessinfoVO.setManagerInd(providerRegistrationVO.getManagerInd());
		}
		else
		{
			objBusinessinfoVO.setManagerInd(-1);
		}

		if(null!=providerRegistrationVO.getOtherInd())
		{
			objBusinessinfoVO.setOtherInd(providerRegistrationVO.getOtherInd());
		}
		else
		{
			objBusinessinfoVO.setOtherInd(-1);
		}

		try {


			iBusinessinfoDao.updateVendorResource(objBusinessinfoVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void updateVendorFinance(
			ProviderRegistrationVO providerRegistrationVO) throws DBException {
		BusinessinfoVO objBusinessinfoVO =new BusinessinfoVO();
		objBusinessinfoVO.setVendorId(providerRegistrationVO.getVendorId().toString()); 
		objBusinessinfoVO.setAnnualSalesRevenue(providerRegistrationVO.getAnnualSalesRevenue());
		//For updating vendor finance
		try {
			iBusinessinfoDao.updateVendorFinance(objBusinessinfoVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void updateAddressDetails(
			ProviderRegistrationVO providerRegistrationVO,
			BusinessinfoVO objBusinessinfoDetails) throws DBException {

		BusinessinfoVO objBusinessinfoVO =new BusinessinfoVO();

		objBusinessinfoVO.setVendorId(providerRegistrationVO.getVendorId().toString());
		objBusinessinfoVO.setLocnId(objBusinessinfoDetails.getLocnId());
		objBusinessinfoVO.setLocnIdB(objBusinessinfoDetails.getLocnIdB());

		objBusinessinfoVO.setBusinessStreet1(providerRegistrationVO.getBusinessStreet1());
		objBusinessinfoVO.setBusinessStreet2(providerRegistrationVO.getBusinessStreet2());
		objBusinessinfoVO.setBusinessState(providerRegistrationVO.getBusinessState());
		objBusinessinfoVO.setBusinessCity(providerRegistrationVO.getBusinessCity());
		objBusinessinfoVO.setBusinessZip(providerRegistrationVO.getBusinessZip());
		objBusinessinfoVO.setBusinessAprt(providerRegistrationVO.getBusinessAprt());

		objBusinessinfoVO.setMailingStreet1(providerRegistrationVO.getMailingStreet1());
		objBusinessinfoVO.setMailingStreet2(providerRegistrationVO.getMailingStreet2());
		objBusinessinfoVO.setMailingState(providerRegistrationVO.getMailingState());
		objBusinessinfoVO.setMailingCity(providerRegistrationVO.getMailingCity());
		objBusinessinfoVO.setMailingZip(providerRegistrationVO.getMailingZip());
		objBusinessinfoVO.setMailingAprt(providerRegistrationVO.getMailingAprt());

		objBusinessinfoVO.setJobTitle(providerRegistrationVO.getJobTitle());
		objBusinessinfoVO.setEmail(providerRegistrationVO.getEmail());
		objBusinessinfoVO.setAltEmail(providerRegistrationVO.getAltEmail());

		//For updating business address,mailing address and contact details
		try {
			iBusinessinfoDao.updateVendorAddressDetails(objBusinessinfoVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void updateVendorHdr(
			ProviderRegistrationVO providerRegistrationVO) throws DBException {
		//For updating firm details in vendor_hdr table and vendor_w9
		BusinessinfoVO objBusinessinfoVO =new BusinessinfoVO ();

		objBusinessinfoVO.setVendorId(providerRegistrationVO.getVendorId().toString());

		objBusinessinfoVO.setBusStructure(providerRegistrationVO.getBusStructure());
		objBusinessinfoVO.setDunsNo(providerRegistrationVO.getDunsNo());
		objBusinessinfoVO.setCompanySize(providerRegistrationVO.getCompanySize());
		objBusinessinfoVO.setIsForeignOwned(providerRegistrationVO.getIsForeignOwned());
		objBusinessinfoVO.setForeignOwnedPct(providerRegistrationVO.getForeignOwnedPct());
		objBusinessinfoVO.setPrimaryIndustry(providerRegistrationVO.getPrimaryIndustry());
		objBusinessinfoVO.setDescription(providerRegistrationVO.getDescription());
		objBusinessinfoVO.setBusinessPhone(providerRegistrationVO.getMainBusiPhoneNo1());
		objBusinessinfoVO.setBusPhoneExtn(providerRegistrationVO.getMainBusinessExtn()); 
		objBusinessinfoVO.setBusinessFax(providerRegistrationVO.getBusinessFax1());
		objBusinessinfoVO.setWebAddress(providerRegistrationVO.getWebsiteAddress());
		objBusinessinfoVO.setBusStartDt(providerRegistrationVO.getBusStartDt());
		//objBusinessinfoVO.setFirmType(providerRegistrationVO.getFirmType());

		try {
			iBusinessinfoDao.updateVendorHdr(objBusinessinfoVO);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			LOGGER.info("SQL Exception @ProviderBOImpl.updateFirm() due to"
					+ e.getMessage());
			throw e;
		}
	}

	private void saveGeneralLiabilityInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile generalLiability =providerRegistrationVO.getGeneralLiability();
		if(null!=generalLiability){
			generalLiability.setVendorId(providerRegistrationVO.getVendorId().intValue());
			CredentialProfile generalLiabilityInfo = new CredentialProfile();
			generalLiabilityInfo = credentialDao.insertInsuranceTypes(generalLiability);
			updateInsuranceAmountInVendorHdr(generalLiability);
			//Sets the new Credential ID for insertion
			if (generalLiabilityInfo != null) {
				generalLiability.setCredentialId(generalLiabilityInfo.getCredentialId());
				iInsurancePolicyBO.auditVendorCredentialsInsurance(generalLiability.getVendorId(), generalLiability.getCurrentDocumentID(), generalLiability.getCredentialId());

			}
		}

	}


	private void updateGeneralLiabilityInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile generalLiability =providerRegistrationVO.getGeneralLiability();

		if(null!=generalLiability){
			generalLiability.setVendorId(providerRegistrationVO.getVendorId().intValue());

			CredentialProfile generalLiabilityInfo = new CredentialProfile();
			credentialDao.updateInsurance(generalLiability);
			updateInsuranceAmountInVendorHdr(generalLiability);

			generalLiability.setCredentialId(generalLiabilityInfo.getCredentialId());
			iInsurancePolicyBO.auditVendorCredentialsInsurance(generalLiability.getVendorId(), generalLiability.getCurrentDocumentID(), generalLiability.getCredentialId());
		}

	}

	private void saveVehicleLiabilityInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile vehicleLiability =providerRegistrationVO.getVehicleLiability();

		if(null!=vehicleLiability){

			CredentialProfile vehicleLiabilityInfo = new CredentialProfile();
			vehicleLiability.setVendorId(providerRegistrationVO.getVendorId().intValue());
			vehicleLiabilityInfo = credentialDao.insertInsuranceTypes(vehicleLiability);
			updateInsuranceAmountInVendorHdr(vehicleLiability);
			//Sets the new Credential ID for insertion
			if (vehicleLiabilityInfo != null) {
				vehicleLiability.setCredentialId(vehicleLiabilityInfo.getCredentialId());
				iInsurancePolicyBO.auditVendorCredentialsInsurance(vehicleLiability.getVendorId(), vehicleLiability.getCurrentDocumentID(), vehicleLiability.getCredentialId());

			}
		}

	}

	private void updateVehicleLiabilityInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile vehicleLiability =providerRegistrationVO.getVehicleLiability();
		if(null!=vehicleLiability){
			CredentialProfile vehicleLiabilityInfo = new CredentialProfile();
			vehicleLiability.setVendorId(providerRegistrationVO.getVendorId().intValue());
			credentialDao.updateInsurance(vehicleLiability);
			updateInsuranceAmountInVendorHdr(vehicleLiability);

			vehicleLiability.setCredentialId(vehicleLiabilityInfo.getCredentialId());
			iInsurancePolicyBO.auditVendorCredentialsInsurance(vehicleLiability.getVendorId(), vehicleLiability.getCurrentDocumentID(), vehicleLiability.getCredentialId());

		}

	}

	private void saveWorkmanCompensationInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile workmanCompensation =providerRegistrationVO.getWorkmanCompensation();	

		if(null!=workmanCompensation){
			workmanCompensation.setVendorId(providerRegistrationVO.getVendorId().intValue());

			CredentialProfile workmanCompensationInfo = new CredentialProfile();
			workmanCompensationInfo = credentialDao.insertInsuranceTypes(workmanCompensation);
			updateInsuranceAmountInVendorHdr(workmanCompensation);
			//Sets the new Credential ID for insertion
			if (workmanCompensationInfo != null) {
				workmanCompensation.setCredentialId(workmanCompensationInfo.getCredentialId());
				iInsurancePolicyBO.auditVendorCredentialsInsurance(workmanCompensation.getVendorId(), workmanCompensation.getCurrentDocumentID(), workmanCompensation.getCredentialId());
			}
		}

	}

	private void updateWorkmanCompensationInsurance(ProviderRegistrationVO providerRegistrationVO) throws Exception{


		CredentialProfile workmanCompensation =providerRegistrationVO.getWorkmanCompensation();	

		if(null!=workmanCompensation){

			CredentialProfile workmanCompensationInfo = new CredentialProfile();
			workmanCompensation.setVendorId(providerRegistrationVO.getVendorId().intValue());

			credentialDao.updateInsurance(workmanCompensation);
			updateInsuranceAmountInVendorHdr(workmanCompensation);

			workmanCompensation.setCredentialId(workmanCompensationInfo.getCredentialId());
			iInsurancePolicyBO.auditVendorCredentialsInsurance(workmanCompensation.getVendorId(), workmanCompensation.getCurrentDocumentID(), workmanCompensation.getCredentialId());
		}

	}





	public void  saveLicense(ProviderRegistrationVO providerRegistrationVO)
			throws Exception {

		List<LicensesAndCertVO> licensesList=providerRegistrationVO.getLicensesList();
		if(null!=licensesList && licensesList.size()>0){
			for(LicensesAndCertVO objLicensesAndCertVO:licensesList){
				objLicensesAndCertVO.setVendorId(providerRegistrationVO.getVendorId()); 
				saveLicenseCredential(objLicensesAndCertVO);
			}
		}

	}

	public void  saveLicenseCredential(LicensesAndCertVO objLicensesAndCertVO)
			throws Exception{

		LicensesAndCertVO dbLicensesAndCertVO = null;
		int docId = 0;

		try
		{
			dbLicensesAndCertVO = iLicensesAndCertDao.save(objLicensesAndCertVO);

			// Insert auditing
			AuditVO auditVO = null;
			try
			{
				auditVO = new AuditVO(objLicensesAndCertVO.getVendorId(), 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);
				//defect 60672 when insertinnew stuff here alway set the reviewed by to null so that power Auditor can look at the Audit TASK
				auditVO.setReviewedBy("");
				// to allow Audit update
				if (docId > 0)
				{
					auditVO.setDocumentId(docId);
				}

				if (dbLicensesAndCertVO != null && dbLicensesAndCertVO.getVendorCredId() > 0) {
					auditVO.setAuditKeyId(dbLicensesAndCertVO.getVendorCredId());
					LOGGER.info("audit key id in license is+++++"+auditVO.getAuditKeyId());
				}
				auditVO.setViaAPI(true);
				auditBO.auditVendorCredentials(auditVO);

			} catch (Exception e) {
				LOGGER.info("[LicensesAndCertBOImpl] - save() - Audit Exception Occured for audit record:" + auditVO.toString(), e);
				throw new BusinessServiceException(e);
			}

		}catch(Exception a_Ex)
		{
			throw new BusinessServiceException(a_Ex);
		}


	}


	public void updateLicense(ProviderRegistrationVO providerRegistrationVO)
			throws Exception {

		List<LicensesAndCertVO> licensesList=providerRegistrationVO.getEditLicensesList();
		for(LicensesAndCertVO objLicensesAndCertVO:licensesList){
			LicensesAndCertVO dbLicensesAndCertVO = null;
			int docId = 0;

			try
			{  
				
				if(null== objLicensesAndCertVO.getIssuerOfCredential())
				{
					objLicensesAndCertVO.setIssuerOfCredential(StringUtils.EMPTY);
				}
				
				if(null == objLicensesAndCertVO.getCredentialNum())
				{
					objLicensesAndCertVO.setCredentialNum(StringUtils.EMPTY);
				}
				
				if(null == objLicensesAndCertVO.getCity())
				{
					objLicensesAndCertVO.setCity(StringUtils.EMPTY);
				}
				
				if(null == objLicensesAndCertVO.getCounty())
				{
					objLicensesAndCertVO.setCounty(StringUtils.EMPTY);
				}
				
				objLicensesAndCertVO.setVendorId(providerRegistrationVO.getVendorId()); 
				objLicensesAndCertVO.setUpdateStateId(objLicensesAndCertVO.getStateId());
				if(StringUtils.isBlank(objLicensesAndCertVO.getStateId())){
					objLicensesAndCertVO.setUpdateStateId(null);	
				}
				
				
				dbLicensesAndCertVO = iLicensesAndCertDao.updateLicense(objLicensesAndCertVO);  

				// Insert auditing
				AuditVO auditVO = null;
				try
				{
					auditVO = new AuditVO(objLicensesAndCertVO.getVendorId(), 0, AuditStatesInterface.VENDOR_CREDENTIAL, AuditStatesInterface.VENDOR_CREDENTIAL_PENDING_APPROVAL);

					auditVO.setReviewedBy("");

					if (docId > 0)
					{
						auditVO.setDocumentId(docId);
					}

					if (dbLicensesAndCertVO != null && dbLicensesAndCertVO.getVendorCredId() > 0) {
						auditVO.setAuditKeyId(dbLicensesAndCertVO.getVendorCredId());
						LOGGER.info("audit key id in license is+++++"+auditVO.getAuditKeyId());
					}
					auditBO.auditVendorCredentials(auditVO);

				} catch (Exception e) {
					LOGGER.info("[ProviderBOImpl] - updateLicense() - Audit Exception Occured for audit record:" + auditVO.toString(), e);
					throw new BusinessServiceException(e);
				}

			}catch(Exception e)
			{
				throw new BusinessServiceException(e);
			}

		}
	}


	private void updateInsuranceAmountInVendorHdr(CredentialProfile credentialProfile)throws BusinessServiceException
	{
		if(credentialProfile.getCredentialCategoryId()>0){
			if(credentialProfile.getCredentialCategoryId()==OrderConstants.GL_CREDENTIAL_CATEGORY_ID){
				try{
					iVendorHdrDao.updateCBGLInsurance(credentialProfile);
				} catch (DBException ex) {
					LOGGER
					.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
							+ ex.getMessage());
					throw new BusinessServiceException(ex.getMessage());
				}	
			}
			if(credentialProfile.getCredentialCategoryId()==OrderConstants.AL_CREDENTIAL_CATEGORY_ID){
				try{
					iVendorHdrDao.updateVLInsurance(credentialProfile);
				} catch (DBException ex) {
					LOGGER
					.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
							+ ex.getMessage());
					throw new BusinessServiceException(ex.getMessage());
				}	
			}
			if(credentialProfile.getCredentialCategoryId()==OrderConstants.WC_CREDENTIAL_CATEGORY_ID){
				try{
					iVendorHdrDao.updateWCInsurance(credentialProfile);
				} catch (DBException ex) {
					LOGGER
					.info("DB Exception @InsurancePolicyBOImpl.updateInsuranceAmountInVendorHdr() due to"
							+ ex.getMessage());
					throw new BusinessServiceException(ex.getMessage());
				}	
			}
		}
	}


	private ProviderRegistrationVO doSendFinalEmailMessage(
			ProviderRegistrationVO providerRegistrationVO)
					throws EmailSenderException,DBException{
		// send the confirmation email
		try {
			if(!isSelectedStateValid(providerRegistrationVO.getBusinessState())){
				LOGGER.info("----State is Valid I m sending Password and username to you-----");
				iProviderEmailBO.sendConfirmationMail(providerRegistrationVO
						.getUserName(), providerRegistrationVO.getPassword(),
						providerRegistrationVO.getEmail(),providerRegistrationVO.getFirstName());
				providerRegistrationVO.setValidateState(false);
			}else{
				//Send the Different Email from different Template
				LOGGER.info("----State is InValid I m not sending Password but username -----");
				providerRegistrationVO.setValidateState(true);
				iProviderEmailBO.sendConfirmationMailForInValidState(providerRegistrationVO.getUserName(), providerRegistrationVO.getPassword(),	providerRegistrationVO.getEmail());
			}

		}
		catch (DBException dbe){
			LOGGER.info("DB Exception while sending an e-mail Exception thrown sending email");
			throw dbe;
		}
		catch (Throwable t) {
			LOGGER.info("throwale thrown sending email");
			t.printStackTrace();
			throw new EmailSenderException(t);

		}

		return providerRegistrationVO;
	}


	private ProviderRegistrationVO validatemainBusiPhoneNo(ProviderRegistrationVO request) {
		String busPhone1 = null;

		try {
			busPhone1 = (request.getMainBusiPhoneNo1() != null) ? request.getMainBusiPhoneNo1().trim() : "";

			Results results = isValidNumber(busPhone1,ProviderConstants.BUSINESS_NO);
			if(results!=null){
				request.setResults(results);
			}

		} catch (Exception a_Ex) {
			errorOccured = true;
			a_Ex.printStackTrace();
			Results results = Results.getError(ResultsCode.MAINBUSINESSPHONE
					.getMessage(), ResultsCode.MAINBUSINESSPHONE
					.getCode());
			request.setResults(results);
		}
		return request;
	}

	private ProviderRegistrationVO validateFaxNumber(ProviderRegistrationVO request) {
		String busFax1 = null;


		try {
			busFax1 = (request.getBusinessFax1() != null) ? request.getBusinessFax1().trim() : "";

			if (null != busFax1 && busFax1.trim().length() > 0) {
				Results results = isValidNumber(busFax1,ProviderConstants.FAX_NO);
				if(results!=null){
					request.setResults(results);
				}
			}
		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			Results results = Results.getError(ResultsCode.BUSINESSFAX
					.getMessage(), ResultsCode.BUSINESSFAX
					.getCode());
			request.setResults(results);
			errorOccured = true;
		}
		return request;
	}

	private ProviderRegistrationVO validateOtherPrimaryServices(ProviderRegistrationVO request) {
		if (StringUtils.isNotBlank(request.getPrimaryIndustry())
				&& ProviderConstants.OTHER_PRIMARY_SERVICES_VALUE
				.equals(request.getPrimaryIndustry())
				&& (StringUtils.isBlank(request
						.getOtherPrimaryService()))) {

			Results results = Results.getError(ResultsCode.PRIMARYINDUSTRY
					.getMessage(), ResultsCode.PRIMARYINDUSTRY
					.getCode());
			request.setResults(results);
			errorOccured = true;
		}
		return request;
	}


	public ProviderRegistrationVO loadRegistration(
			ProviderRegistrationVO providerRegistrationVO)
					throws BusinessServiceException {

		try {
			providerRegistrationVO.setHowDidYouHearList(iLookupDAO.loadReferrals());
			providerRegistrationVO.setPrimaryIndList(iLookupDAO.loadPrimaryIndustry());
			providerRegistrationVO.setRoleWithinCompany(commonLookkupDAO.loadCompanyRole(OrderConstants.PROVIDER_ROLEID));
		} catch (DBException ex) {
			LOGGER.info("DB Exception @RegistrationBOImpl.loadRegistration() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info("General Exception @RegistrationBOImpl.loadRegistration() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @RegistrationBOImpl.loadRegistration() due to "
							+ ex.getMessage());
		}
		return providerRegistrationVO;
	}

	public ProviderRegistrationVO validateBusinessStateZip(ProviderRegistrationVO providerRegistrationVO){
		try {
			providerRegistrationVO.setStateType("business");
			providerRegistrationVO = loadZipSet(providerRegistrationVO);
			List stateTypeList = providerRegistrationVO.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					providerRegistrationVO.getBusinessZip())) {
				Results results = Results.getError(ResultsCode.INVALID_BUSINESSZIP_FIRM
						.getMessage(), ResultsCode.INVALID_BUSINESSZIP_FIRM
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		} catch (Exception a_Ex) {
			errorOccured = true;
			LOGGER.info("--------- Exception inside validateBusinessStateZip ---",
					a_Ex);

		}
		return providerRegistrationVO;
	}


	public ProviderRegistrationVO validateW9StateInfo(ProviderRegistrationVO providerRegistrationVO){
		try {

			providerRegistrationVO.setStateType("other");
			providerRegistrationVO = loadZipSet(providerRegistrationVO);
			List stateTypeList = providerRegistrationVO.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					providerRegistrationVO.getW9RegistrationVO().getAddress().getZip())) {
				Results results = Results.getError(ResultsCode.INVALID_STATEZIP_W9_FIRM
						.getMessage(), ResultsCode.INVALID_STATEZIP_W9_FIRM
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		} catch (Exception a_Ex) {
			errorOccured = true;
			LOGGER.info("--------- Exception inside validatew9StateInfo ---",
					a_Ex);

		}
		return providerRegistrationVO;
	}


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

			if (providerRegistrationVO.getStateType() != null
					&& 	providerRegistrationVO.getStateType().length() > 0
					&&  providerRegistrationVO.getStateType().equalsIgnoreCase("other"))
			{
				stateTypeList = zipDao.queryList(providerRegistrationVO.getW9RegistrationVO().getAddress().getState());
			}

			providerRegistrationVO.setStateTypeList(stateTypeList);

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info("General Exception @RegistrationBOImpl.loadZipSet() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @RegistrationBOImpl.loadZipSet() due to "
							+ ex.getMessage());
		}
		return providerRegistrationVO;
	}

	private ProviderRegistrationVO validateMailingStateZip(ProviderRegistrationVO providerRegistrationVO){
		try {
			providerRegistrationVO.setStateType("mail");
			providerRegistrationVO = loadZipSet(providerRegistrationVO);
			List stateTypeList = providerRegistrationVO.getStateTypeList();
			if (!validateStateZip(stateTypeList,
					providerRegistrationVO.getMailingZip())) {
				Results results = Results.getError(ResultsCode.INVALID_MAILINGZIP_FIRM
						.getMessage(), ResultsCode.INVALID_MAILINGZIP_FIRM
						.getCode());
				providerRegistrationVO.setResults(results);
				errorOccured = true;
			}
		} catch (Exception e) {
			errorOccured = true;
			LOGGER.info(""+e);
		}
		return providerRegistrationVO;
	}





	private boolean isSelectedStateValid(String selectedState) throws DBException{
		boolean stateActive=true;
		try{
			LookupVO vo=iLookupDAO.isStateActive(selectedState);
			if(vo!=null&& vo.getId()!=null){
				stateActive=false; 
			}
		}catch(DBException dbe){
			LOGGER
			.info("General Exception @RegistrationBOImpl.isSelectedStateValid() due to"
					+ dbe.getMessage());
			throw dbe;
		}
		return stateActive;
	}




	private boolean validateStateZip(List list, String zip) {
		Iterator itr = list.iterator();
		boolean valid = false;
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();

			if (zip.equals(validZip)) {
				return true;
			}
		}
		return false;
	}

	private ProviderRegistrationVO validateUserName(ProviderRegistrationVO providerRegistrationVO){
		boolean isDulplicate = providerRegistrationBO.validateUserName(providerRegistrationVO.getUserName());
		if(isDulplicate){
			Results results = Results.getError(ResultsCode.USERNAME_EXISTS_FIRM
					.getMessage(), ResultsCode.USERNAME_EXISTS_FIRM
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;		
		}


		if(!errorOccured && StringUtils.isNotBlank(providerRegistrationVO.getUserName())
				&& (providerRegistrationVO.getUserName().trim().length()<8 || providerRegistrationVO.getUserName().trim().length()>30)){

			Results results = Results.getError(ResultsCode.INVALID_USER_NAME
					.getMessage(), ResultsCode.INVALID_USER_NAME
					.getCode());
			providerRegistrationVO.setResults(results);
			errorOccured = true;			
		}		



		return providerRegistrationVO;
	}

	private Results isValidNumber(String busPhone1, String type) {


		try {
			if (busPhone1 == null || busPhone1.length() <= 0) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBER
							.getMessage(), ResultsCode.PHONENUMBER
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER
							.getMessage(), ResultsCode.FAXNUMBER
							.getCode());
				}
				errorOccured = true;	
				return results;
			}


			else if (busPhone1.trim().length() <= 0 || busPhone1.trim().equals("")){
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBER
							.getMessage(), ResultsCode.PHONENUMBER
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER
							.getMessage(), ResultsCode.FAXNUMBER
							.getCode());
				}
				errorOccured = true;	
				return results;
			}


			// Validating for the NUMBER type
			try {
				long numInt1 = Long.parseLong(busPhone1.trim());

			} catch (NumberFormatException a_Ex) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERVALI
							.getMessage(), ResultsCode.PHONENUMBERVALI
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID
							.getMessage(), ResultsCode.FAXNUMBER_INVALID
							.getCode());
				}		

				errorOccured = true;	
				return results;
			} catch (Exception a_Ex) {
				Results results = null;
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERVALI
							.getMessage(), ResultsCode.PHONENUMBERVALI
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID
							.getMessage(), ResultsCode.FAXNUMBER_INVALID
							.getCode());
				}	

				errorOccured = true;	
				return results;
			}

			// validation for length
			if (busPhone1.trim().length() != 10) {
				Results results = new Results();
				if(ProviderConstants.BUSINESS_NO.equals(type)){
					results = Results.getError(ResultsCode.PHONENUMBERLENGTH
							.getMessage(), ResultsCode.PHONENUMBERLENGTH
							.getCode());
				}
				else if(ProviderConstants.FAX_NO.equals(type)){
					results = Results.getError(ResultsCode.FAXNUMBER_INVALID_LENGTH
							.getMessage(), ResultsCode.FAXNUMBER_INVALID_LENGTH
							.getCode());
				}
				errorOccured = true;
				return results;
			}

		} catch (Exception a_Ex) {
			a_Ex.printStackTrace();
			Results results = null;
			if(ProviderConstants.BUSINESS_NO.equals(type)){
				results = Results.getError(ResultsCode.PHONENUMBERVALI
						.getMessage(), ResultsCode.PHONENUMBERVALI
						.getCode());
			}
			else if(ProviderConstants.FAX_NO.equals(type)){
				results = Results.getError(ResultsCode.FAXNUMBER_INVALID
						.getMessage(), ResultsCode.FAXNUMBER_INVALID
						.getCode());
			}

			errorOccured = true;	
			return results;
		}

		return null;
	}


	private ProviderRegistrationVO validateEmailId(
			ProviderRegistrationVO response, String email, String type) {
		String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(email);
		if(!m.find()) {
			if(type.equals(ProviderConstants.EMAIL_ID)){
				Results results = Results.getError(ResultsCode.INVALID_EMAIL
						.getMessage(), ResultsCode.INVALID_EMAIL
						.getCode());
				response.setResults(results);
			}
			else if(type.equals(ProviderConstants.ALTER_EMAIL_ID)){
				Results results = Results.getError(ResultsCode.INVALID_ALTERNATE_EMAIL
						.getMessage(), ResultsCode.INVALID_ALTERNATE_EMAIL
						.getCode());
				response.setResults(results);
			}
			errorOccured = true;
		}
		return response;
	}

	/**@Description: validating firms for status change.
	 * @param approveFirmsVOList
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ApproveFirmsVO> validateFirms(List<ApproveFirmsVO> approveFirmsVOList,Integer adminResourceId) throws BusinessServiceException  {
		List<ApproveFirmsVO> validFirms =new ArrayList<ApproveFirmsVO>();
		try{
			if(null!= approveFirmsVOList&& !approveFirmsVOList.isEmpty()){
				//validating admin Resource 
				boolean isAdmin = validateAdminResource(adminResourceId);
				if(!isAdmin){
					return validFirms;
				}
				//Validating HI firm Or Not
				approveFirmsVOList = validateExistingFirm(approveFirmsVOList);

				if(null!= approveFirmsVOList&& !approveFirmsVOList.isEmpty()){
					//Removing Invalid firms for further processing
					validFirms = removeInvalidOrValidFirms(approveFirmsVOList,ProviderConstants.VALID_FIRM); 
				}
				if(null!= validFirms&& !validFirms.isEmpty()){
					//Validating firm wf state for status change
					approveFirmsVOList = validateFirmStatus(validFirms,approveFirmsVOList);
				}
				if(null!= approveFirmsVOList&& !approveFirmsVOList.isEmpty()){
					validFirms.clear();
					//Removing Invalid firms for further processing
					validFirms = removeInvalidOrValidFirms(approveFirmsVOList,ProviderConstants.VALID_FIRM);
				}
				//Validating reasonCodes for Status change
				if(null!= validFirms&& !validFirms.isEmpty()){
					approveFirmsVOList = validateReasonCodeForStatusCahnege(validFirms,approveFirmsVOList);
				}
			}

		}catch (DataServiceException e) {
			LOGGER.error("Exception in validating Firm "+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return approveFirmsVOList;
	}

	
	/**
	 * @param adminResourceId
	 * @return
	 * @throws BusinessServiceException 
	 */
	private boolean validateAdminResource(Integer adminResourceId) throws BusinessServiceException {
		boolean isAdmin= false;
		String userName ="";
		try{
			userName = iVendorHdrDao.getAdminUserName(adminResourceId);
			if(StringUtils.isNotBlank(userName)){
				isAdmin =true;
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating admin Resource "+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return isAdmin;
	}

	/**@Description: Removing Invalid/valid  firms from Parent and move to a new List.
	 * @param approveFirmsVOList
	 * @return
	 */
	public List<ApproveFirmsVO> removeInvalidOrValidFirms(List<ApproveFirmsVO> approveFirmsVOList,String indicator) {
		List<ApproveFirmsVO> validOrInvalidFirms = new ArrayList<ApproveFirmsVO>();
		if(null!= approveFirmsVOList&&!(approveFirmsVOList.isEmpty())){
			for(ApproveFirmsVO firmsVO : approveFirmsVOList){
				if(null!= firmsVO && null!= firmsVO.getValidationCode())
					if(indicator.equalsIgnoreCase(ProviderConstants.VALID_FIRM)){
						if(firmsVO.getValidationCode().equals(ResultsCode.SUCCESS)){
							validOrInvalidFirms.add(firmsVO);
						}
					}else{
						if(!firmsVO.getValidationCode().equals(ResultsCode.SUCCESS)){
							validOrInvalidFirms.add(firmsVO);
						}
					}
			}
		}
		return validOrInvalidFirms;
	}

	/**@Description: validating firms existing or not
	 * @param approveFirmsVOList
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveFirmsVO> validateExistingFirm(List<ApproveFirmsVO> approveFirmsVOList)throws DataServiceException {
		try{
			List<Integer> firmIdList = iVendorHdrDao.getVendorType(approveFirmsVOList);
			// Check the existence of Hi vendor in provided List
			if(null!= firmIdList &&!(firmIdList.isEmpty())){
				for(ApproveFirmsVO approveFirmsVO : approveFirmsVOList){
					if(firmIdList.contains(approveFirmsVO.getFirmId())){
						approveFirmsVO.setEligibleForStatusChange(true);
						approveFirmsVO.setValidationCode(ResultsCode.SUCCESS);
					}else{
						// Setting error code for invalid HI firm.
						approveFirmsVO.setEligibleForStatusChange(false);
						approveFirmsVO.setValidationCode(ResultsCode.INVALID_HI_FIRM_ID);
					}
				}

			}else{
				for(ApproveFirmsVO approveFirmsVO : approveFirmsVOList){
					approveFirmsVO.setEligibleForStatusChange(false);
					approveFirmsVO.setValidationCode(ResultsCode.INVALID_HI_FIRM_ID);
				}
			}

		}catch (com.newco.marketplace.exception.core.DataServiceException e) {
			LOGGER.error("Exception in validating Firm +validateExistingFirm()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return approveFirmsVOList;
	}
	
	
	/**@Description: validating firms for status change.
	 * @param approveFirmsVOList
	 * @param approveFirmsVOList2 
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveFirmsVO> validateFirmStatus(List<ApproveFirmsVO> validFirms, List<ApproveFirmsVO> approveFirmsVOList)throws DataServiceException {
		boolean isEligibleForStatusChange =false;
		try{
			if(null!= validFirms &&!(validFirms.isEmpty())){
				for(ApproveFirmsVO approveFirmsVO : validFirms){
					//Check the provider is eligible for status Change(1.h.vendor_bucks_ind =1 && terms_cond_id =1)
					isEligibleForStatusChange = iVendorHdrDao.checkIfEligibleForStatusChange(approveFirmsVO.getFirmId());
					if(isEligibleForStatusChange){
						Integer currentwfStatus=iVendorHdrDao.getVendorWFStateId(approveFirmsVO.getFirmId());
						Integer requestedWfStatus = iVendorHdrDao.getWfStateIdForStatus(approveFirmsVO.getFirmStatus());
						if(null!=currentwfStatus && null!= requestedWfStatus &&(requestedWfStatus.equals(currentwfStatus))){
							approveFirmsVO.setEligibleForStatusChange(false);
							approveFirmsVO.setValidationCode(ResultsCode.INVALID_CURRENT_STATUS);
						}else{
							approveFirmsVO.setEligibleForStatusChange(true);
							approveFirmsVO.setValidationCode(ResultsCode.SUCCESS);
						}

					}else{
						//Firm is Not Eligible for Status Change.Setting Error Code.
						approveFirmsVO.setEligibleForStatusChange(false);
						approveFirmsVO.setValidationCode(ResultsCode.INVALID_FIRM_STATUS); 
					}
				}
			}
		} catch (DBException e) {
			LOGGER.error("Exception in validating Firm + validateFirmStatus()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}catch (Exception e) {
			LOGGER.error("Exception in validating Firm + validateFirmStatus()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		//Updating Parent List with error codes.
		approveFirmsVOList = updateParentList(approveFirmsVOList,validFirms);
		return approveFirmsVOList;
	}
	


	/**@Description : Validating Reason Codes with existing codes from DB.
	 * @param validFirms
	 * @param approveFirmsVOList
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveFirmsVO> validateReasonCodeForStatusCahnege(List<ApproveFirmsVO> validFirms,List<ApproveFirmsVO> approveFirmsVOList) throws DataServiceException {
		try{
			if(null!= validFirms &&!(validFirms.isEmpty())){
				for(ApproveFirmsVO approveFirmsVO : validFirms){
					if(null!= approveFirmsVO.getReasonCodes()){
						List<String> reasonCodeList = Arrays.asList(approveFirmsVO.getReasonCodes());
						if(null== reasonCodeList || reasonCodeList.isEmpty()){
							approveFirmsVO.setEligibleForStatusChange(false);
							approveFirmsVO.setValidationCode(ResultsCode.REQUIRED_REASON_CODE);
						}//Validating the reason code exists in DB
						else{
							approveFirmsVO = auditDao.getReasonCodesForStatus(approveFirmsVO);
							if(null!= approveFirmsVO &&
									null!= approveFirmsVO.getReasonCodeVoList()
									&&!(approveFirmsVO.getReasonCodeVoList().isEmpty())){
								List<Integer> reasonCodesList = new ArrayList<Integer>(); 
								for(String code:reasonCodeList){ 
									for(ReasonCodeVO reasonCode:approveFirmsVO.getReasonCodeVoList()){
										if(code.equals(reasonCode.getReasonCodeValue())){
											reasonCodesList.add(reasonCode.getReasonCodeId());
										}
									}
								}
								if(null!=reasonCodesList &&!(reasonCodesList.isEmpty()) ){
									if(reasonCodesList.size()== reasonCodeList.size()){
										approveFirmsVO.setEligibleForStatusChange(true);
										approveFirmsVO.setValidationCode(ResultsCode.SUCCESS);
										approveFirmsVO.setReasonCodeList(reasonCodesList);
									}else{
										approveFirmsVO.setEligibleForStatusChange(false);
										approveFirmsVO.setValidationCode(ResultsCode.INVALID_REASON_CODE); 
									}
								}else{
									approveFirmsVO.setEligibleForStatusChange(false);
									approveFirmsVO.setValidationCode(ResultsCode.INVALID_REASON_CODE); 
								}
							}else{
								approveFirmsVO.setEligibleForStatusChange(false);
								approveFirmsVO.setValidationCode(ResultsCode.INVALID_REASON_CODE); 
							}
						}
					}else{
						approveFirmsVO.setEligibleForStatusChange(false);
						approveFirmsVO.setValidationCode(ResultsCode.REQUIRED_REASON_CODE);
					}
				}
			}

		}catch (Exception e) {
			LOGGER.error("Exception in validating Firm + validateReasonCodeForStatusCahnege()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		//Updating Parent List with error codes.
		approveFirmsVOList = updateParentList(approveFirmsVOList,validFirms);
		return approveFirmsVOList;
	}


	
	/**@Description: Updating Parent List from Child list.
	 * @param approveFirmsVOList
	 * @param validFirms
	 * @return
	 */
	private List<ApproveFirmsVO> updateParentList(List<ApproveFirmsVO> approveFirmsVOList,List<ApproveFirmsVO> validFirms) {
		if(null!= approveFirmsVOList && !(approveFirmsVOList.isEmpty())
				&& null!= validFirms &&!(validFirms.isEmpty())){
			for(ApproveFirmsVO validFirm :validFirms){
				for(ApproveFirmsVO parentVO : approveFirmsVOList){
					if(validFirm.getFirmId().equals(parentVO.getFirmId())){
						if(!ResultsCode.SUCCESS.equals(validFirm.getValidationCode())){
							parentVO.setValidationCode(validFirm.getValidationCode());
						}
					}
				}
			}

		}
		return approveFirmsVOList;
	}
	
	
	
	public List<ApproveFirmsVO> updateWFStatusAndReasonCodes(List<ApproveFirmsVO> validFirmsList,Integer adminResourceId) throws BusinessServiceException {
		//Declaring Variables for Status Update
		com.newco.marketplace.vo.audit.AuditVO auditVO = null;
		com.newco.marketplace.vo.audit.AuditVO chkRecordExists = null;
		com.newco.marketplace.vo.audit.AuditVO updateAuditTask = null;
		VendorHdr vendorHdr = null;
		VendorNotesVO vendorNotesVO = null;
		try{
			if(null!=validFirmsList &&!validFirmsList.isEmpty() ){
				for(ApproveFirmsVO providerFirm : validFirmsList){
					if(providerFirm.isEligibleForStatusChange()){
						auditVO = buildAuditVOForCompany(providerFirm,adminResourceId);
						chkRecordExists = auditDao.queryWfStateReasonCd(auditVO);
						if(null== chkRecordExists){
							providerFirm.setValidationCode(ResultsCode.INVALID_FIRM_STATUS);
						}else{
							updateAuditTask = setAuditTaskInfo(chkRecordExists,auditVO);
							vendorHdr = setVendorHdr(auditVO);
							vendorNotesVO = setVendorNotes(auditVO);
							//Deleting ReasonCodes and Inserting new for status change.
							String[] reasonCodes = auditVO.getReasonCodeIds();
							if(null!= reasonCodes){
								auditDao.deleteReasonCdForResource(updateAuditTask);
								for(int i=0; i<reasonCodes.length; i++) {
									if(Integer.parseInt(reasonCodes[i]) != -1){
										updateAuditTask.setReasonId(Integer.parseInt(reasonCodes[i]));                                        	
										auditDao.insertReasonCdForResource(updateAuditTask);
									}
								}
							}
							//Updating audit task
							auditDao.updateStateReasonCdForResource(updateAuditTask);
							//Updating vendor_ hdr
							iVendorHdrDao.updateWFStateId(vendorHdr);
							//Sending Email for Status Change
							if(providerFirm.isEmailIndicator()){
								sendEmailForStatusCahnge(auditVO);
							}
							//Add notes for status change
							vendorNotesDao.insert(vendorNotesVO);
						}
					}
				}
			}	
		}catch (Exception e) {
			LOGGER.error("Exception in updating provider firm Wf status and ReasonCodes"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return validFirmsList;
	}

	
	public List<ApproveProvidersVO> updateProviderWFStatusAndReasonCodes(List<ApproveProvidersVO> validProvidersList,Integer adminResourceId) throws BusinessServiceException {
		//Declaring Variables for Status Update
		com.newco.marketplace.vo.audit.AuditVO auditVO = null;
		VendorResource updateVendorResource = null;
		com.newco.marketplace.vo.audit.AuditVO chkRecordExists = null;
		com.newco.marketplace.vo.audit.AuditVO updateAuditTask = null;
		VendorHdr vendorHdr = null;
		VendorNotesVO vendorNotesVO = null;
		try{
			if(null!=validProvidersList &&!validProvidersList.isEmpty() ){
				for(ApproveProvidersVO provider : validProvidersList){
					if(provider.isEligibleForStatusChange()){
					   auditVO = buildAuditVOForProvider(provider,adminResourceId);
					   chkRecordExists = auditDao.queryWfStateReasonCdTM(auditVO);
					   if(null== chkRecordExists){
						   provider.setValidationCode(ResultsCode.INVALID_PROVIDER_STATUS);
					   }else{
						   updateAuditTask = setAuditTaskInfo(chkRecordExists,auditVO);
						   updateVendorResource = setVendorResource(auditVO);
						   vendorHdr = setVendorHdr(auditVO);
						   vendorNotesVO = setVendorNotesForProvider(auditVO);
			                  //Deleting ReasonCodes and Inserting new for status change.
							   String[] reasonCodes = auditVO.getReasonCodeIds();
							   if(null!= reasonCodes){
								   auditDao.deleteReasonCdForResource(updateAuditTask);
							       for(int i=0; i<reasonCodes.length; i++) {
                                      if(Integer.parseInt(reasonCodes[i]) != -1){
                                        updateAuditTask.setReasonId(Integer.parseInt(reasonCodes[i]));                                        	
                                   	    auditDao.insertReasonCdForResource(updateAuditTask);
                                   }
                                 }
							   }
							   
						   //Updating audit task
						   auditDao.updateStateReasonCdForResource(updateAuditTask);
						   //Updating vendor_ hdr
						   iVendorResourceDao.updateWFState(updateVendorResource);
						   //updating background check --START
						  if(provider.isNeedDBUpdatedForBgInfo()){
							  	provider.setFirmId(auditVO.getVendorId()); //setting vendorId for sl_pro_background_chk update
							  	GeneralInfoVO generalInfoVO = new GeneralInfoVO();
								BackgroundCheckProviderVO backgroundCheckProviderVO = setBackgroundCheckData(provider);
								//sl_pro_background_check update
								iVendorHdrDao.updateBackgroundCheckStatus(backgroundCheckProviderVO); 
								//vendor_resource update
								iVendorHdrDao.updateBackgroundCheckStatusInVendorResource(provider);
								//sl_pro_background_check_history update
								generalInfoVO = savingBackGroundInformation(provider,generalInfoVO);
								//audit_task update
								generalInfoVO = auditResourceBackgroundCheck(provider.getProviderId(),provider.getBackgroundCheckStatus(),generalInfoVO);
								//activity_registry update
								if(!StringUtils.equalsIgnoreCase(PublicAPIConstant.BG_STATUS_NOT_STARTED,provider.getBackgroundCheckStatus())){
								   activityRegistryDao.updateResourceActivityStatus(provider.getProviderId().toString(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK); 
								}else{
								   activityRegistryDao.updateResourceActivityStatus(provider.getProviderId().toString(),ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,PublicAPIConstant.INTEGER_ZERO); 
								}
						  }
						   //updating background check --END
						   //Sending Email for Status Change
						   if(provider.isEmailIndicator()){
							   getAuditEmailBean().sendAuditEmail (AuditStatesInterface.RESOURCE, auditVO);
						   }
						   //Add notes for status change
						   vendorNotesDao.insert(vendorNotesVO);
					   }
				   }
			}
		  }	
		}catch (Exception e) {
			LOGGER.error("Exception in updating provider firm Wf status and ReasonCodes"+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return validProvidersList;
	}
	private GeneralInfoVO savingBackGroundInformation(ApproveProvidersVO providerVO, GeneralInfoVO generalInfoVO) throws BusinessServiceException {
    	try{
    		Integer backgroundCheckId =providerVO.getBgCheckId();
    		if(null!=backgroundCheckId ){
    			generalInfoVO.setBcCheckId(backgroundCheckId);
    			generalInfoVO.setResourceId((providerVO.getProviderId()).toString());
    			//updating back ground history
    			generalInfoVO.setChangedComment(Constants.BACKGROUND_CHECK_STATUS_UPDATE+ providerVO.getCurrentbackgroundCheckStatus()+" to " +providerVO.getBackgroundCheckStatus());
    			iVendorResourceDao.insertBcHistory(generalInfoVO);
    		}
    	}catch (Exception e) {
			LOGGER.error("Exception in saving background info of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
	
	/**@Description : Sending email on status Change
	 * @param auditVO
	 * @throws com.newco.marketplace.exception.core.BusinessServiceException 
	 * @throws AuditException 
	 */
	private void sendEmailForStatusCahnge(com.newco.marketplace.vo.audit.AuditVO auditVO) throws com.newco.marketplace.exception.core.BusinessServiceException, AuditException {
		try {
			auditVO.setAuditBusinessName(iVendorHdrDao.getCompanyName(auditVO.getVendorId()));
			AuditTemplateMappingVo templateMapping = getAuditEmailTemplateMappings().get(auditVO.getWfStatusId());
			if (templateMapping != null) {
				AuditEmailVo auditEmailVo = new AuditEmailVo();
				auditEmailVo.setTargetStateId(templateMapping.getTargetStateId());
				auditEmailVo.setTargetStateName(templateMapping.getTargetStateName());
				auditEmailVo.setTemplateMapping(templateMapping);
				auditEmailVo.setWorkflowEntity(auditEmailVo.getTemplateMapping().getWorkflowEntity());
				auditEmailVo.setVendorId(auditVO.getVendorId());
				auditEmailVo.setResourceId(auditVO.getResourceId());
				////Adding this for EMAIL change in Addressing Name for rebranding									
				AuditEmailVo newAuditEmailVoPrimeContact = getAuditEmailDao().getResourceNamePrimeContact(auditEmailVo.getVendorId() );
				StringBuffer sbPrimeContact = new StringBuffer();
				sbPrimeContact.append(newAuditEmailVoPrimeContact.getFirstName() + " ");

				if(StringUtils.isNotBlank(newAuditEmailVoPrimeContact.getMiddleName())){
					sbPrimeContact.append(newAuditEmailVoPrimeContact.getMiddleName() + " ");
				}

				sbPrimeContact.append(newAuditEmailVoPrimeContact.getLastName() + " ");
				auditEmailVo.setFirstNameLastName(sbPrimeContact.toString());
				////ENd of changes for Rebranding
				LOGGER.info("******************************* Getting auditemailDao " + auditEmailVo);
				auditEmailVo.setTo(getAuditEmailDao().getEmailAddressFromVendorId(auditEmailVo.getVendorId()));
				if (auditEmailVo.getTemplateMapping() != null) {
					auditEmailVo.setSubject(auditEmailVo.getTemplateMapping().getTemplateSubject());
				}
				auditEmailVo.setLogoPath("/images/icon_logo.gif");
				LOGGER.info("******************************* email: \n"+ auditEmailVo);
				LOGGER.info("******************************* entity: "+ auditEmailVo.getWorkflowEntity());
				//assign company name to auditEmailVo if it is not null
				if(null != auditVO.getAuditBusinessName()){
					auditEmailVo.setBusinessName(auditVO.getAuditBusinessName());
				}
				populateReasonCodeDescription(auditEmailVo, auditVO);
				// finally send the email
				if(auditEmailVo.getTemplateMapping().getWorkflowEntity().equals(VENDOR)){
					HashMap<String, Object> map = new AOPHashMap();
					map.put(AOPConstants.PRIME_CONTACT, auditEmailVo.getFirstNameLastName().trim());
					map.put(AOPConstants.AOP_USER_EMAIL, auditEmailVo.getTo());
					map.put(AOPConstants.AOP_TEMPLATE_ID, templateMapping.getTemplateId());
					map.put(AOPConstants.REASONDESCRIPTION, auditEmailVo.getReasonDescription());
					map.put(AOPConstants.BUSINESS_NAME, auditEmailVo.getBusinessName());
					map.put(AOPConstants.TARGETSTATENAME, auditEmailVo.getTargetStateName());
					AlertTask alertTask = getEmailAlertTask(map);
					alertDao.addAlertToQueue(alertTask);
				}

			}
		} catch (DBException e) {
			LOGGER.error("Excepetion in sending email for status change"+ e.getMessage());
			throw new com.newco.marketplace.exception.core.BusinessServiceException(e.getMessage());
		}catch (com.newco.marketplace.exception.core.DataServiceException  e) {
			LOGGER.error("Error in AuditEmailBusinessBean due to "+e.getMessage());
		}
		catch (AuditException e) {
			LOGGER.error("Excepetion in sending email for status change"+ e.getMessage());
			throw new com.newco.marketplace.exception.core.BusinessServiceException(e.getMessage());
		}

	}

	/**@Description: Adding Notes for status change
	 * @param auditVO
	 * @return
	 */
	private VendorNotesVO setVendorNotes(com.newco.marketplace.vo.audit.AuditVO auditVO) {
		StringBuilder noteBody = new StringBuilder();
		VendorNotesVO vendorNotesVO = new VendorNotesVO();
		vendorNotesVO.setVendorId(auditVO.getVendorId());
		noteBody.append("Changed provider firm status To "+auditVO.getWfState()+ " by ApproveFirm API");
		if(auditVO.isSendEmailNotice()){
			noteBody.append(" Email sent");
		}else{
			noteBody.append(" Email not sent");
		}
		vendorNotesVO.setNote(noteBody.toString());
		vendorNotesVO.setModifiedBy(auditVO.getReviewedBy());
		return vendorNotesVO;
	}

	/**@Description: Setting Vendor Hdr for updation
	 * @param auditVO
	 * @return
	 */
	private VendorHdr setVendorHdr(com.newco.marketplace.vo.audit.AuditVO auditVO) {
		VendorHdr vendorHdr = new VendorHdr();
		vendorHdr.setVendorId(auditVO.getVendorId());
		vendorHdr.setVendorStatusId(auditVO.getWfStatusId());
		return vendorHdr;
	}
	private VendorResource setVendorResource(com.newco.marketplace.vo.audit.AuditVO auditVO) {
		VendorResource updateVendorResource = new VendorResource();
		updateVendorResource.setVendorId(auditVO.getVendorId());
    	updateVendorResource.setResourceId(auditVO.getResourceId());
    	updateVendorResource.setWfStateId(auditVO.getWfStateId());
		return updateVendorResource;
	}
	
	/**@Description: Set review Comments and auditTask Id.
	 * @param chkRecordExists
	 * @param auditVO
	 * @return
	 */
	private com.newco.marketplace.vo.audit.AuditVO setAuditTaskInfo(com.newco.marketplace.vo.audit.AuditVO chkRecordExists, com.newco.marketplace.vo.audit.AuditVO auditVO) {
		auditVO.setAuditTaskId(chkRecordExists.getAuditTaskId());
		auditVO.setReviewComments("");
		return auditVO;
	}

	/**@Description: Populating Details to be updated in vendor_hdr
	 * @param providerFirm
	 * @param adminResourceId 
	 * @return
	 * @throws com.newco.marketplace.exception.core.DataServiceException 
	 */
	private com.newco.marketplace.vo.audit.AuditVO buildAuditVOForCompany(ApproveFirmsVO providerFirm, Integer adminResourceId) throws com.newco.marketplace.exception.core.DataServiceException {
		com.newco.marketplace.vo.audit.AuditVO auditVO = new com.newco.marketplace.vo.audit.AuditVO();
		auditVO.setAuditKeyId(providerFirm.getFirmId());
		auditVO.setAuditLinkId(AdminConstants.AUDIT_LINK_ID_COMPANY);		
		auditVO.setResourceId(Integer.valueOf(0));
		auditVO.setReviewedBy(AdminConstants.SYSTEM_USER);
		if(null!= adminResourceId){
			String userName = iVendorHdrDao.getAdminUserName(adminResourceId);
			auditVO.setReviewedBy(userName);
		}
		auditVO.setSendEmailNotice(providerFirm.isEmailIndicator());
		auditVO.setVendorId(providerFirm.getFirmId());		
		auditVO.setWfState(providerFirm.getFirmStatus());		
		auditVO.setWfStateId(providerFirm.getWfStatus());
		auditVO.setWfStatusId(providerFirm.getWfStatus());
		if(null!= providerFirm.getReasonCodeList() &&!providerFirm.getReasonCodeList().isEmpty()){
			int size = providerFirm.getReasonCodeList().size();
			List<Integer> reasonCodeInt = providerFirm.getReasonCodeList();
			String[] reasonCodes = new String[size];
			for(int i=0;i < size;i++){
				Integer code = reasonCodeInt.get(i);
				reasonCodes[i] = code.toString();
			}
			auditVO.setReasonCodeIds(reasonCodes);

		}
		return auditVO;
	}

	/**@Description: Populating Details to be updated in vendor_hdr
	 * @param provider
	 * @param adminResourceId 
	 * @return
	 * @throws com.newco.marketplace.exception.core.DataServiceException 
	 */
	private com.newco.marketplace.vo.audit.AuditVO buildAuditVOForProvider(ApproveProvidersVO provider, Integer adminResourceId) throws com.newco.marketplace.exception.core.DataServiceException {
		com.newco.marketplace.vo.audit.AuditVO auditVO = new com.newco.marketplace.vo.audit.AuditVO();
		auditVO.setAuditKeyId(provider.getProviderId());
		auditVO.setAuditLinkId(AdminConstants.AUDIT_LINK_ID_PROVIDER);		
		auditVO.setResourceId(provider.getProviderId());
		auditVO.setReviewedBy(AdminConstants.SYSTEM_USER);
		auditVO.setWfEntity(AdminConstants.TEAM_MEMBER);
		if(null!= adminResourceId){
			String userName = iVendorHdrDao.getAdminUserName(adminResourceId);
			auditVO.setReviewedBy(userName);
		}
		if(null!= provider.getProviderId()){
			auditVO.setVendorId(iVendorHdrDao.getVendorIdForResource(provider.getProviderId()));	
		}
		auditVO.setSendEmailNotice(provider.isEmailIndicator());
			
		auditVO.setWfState(provider.getStatus());		
		auditVO.setWfStateId(provider.getWfStatus());
		auditVO.setWfStatusId(provider.getWfStatus());
		if(null!= provider.getReasonCodeList() &&!provider.getReasonCodeList().isEmpty()){
			int size = provider.getReasonCodeList().size();
			 List<Integer> reasonCodeInt = provider.getReasonCodeList();
			 String[] reasonCodes = new String[size];
			 for(int i=0;i < size;i++){
				 Integer code = reasonCodeInt.get(i);
				 reasonCodes[i] = code.toString();
			 }
		    auditVO.setReasonCodeIds(reasonCodes);
				
		}
		return auditVO;
	}
	
	/**
	 * @return
	 * @throws AuditException
	 */
	private HashMap<Integer, AuditTemplateMappingVo> getAuditEmailTemplateMappings() throws AuditException{
		try {
			if (auditEmailTemplateMappings == null) {
				auditEmailTemplateMappings = new HashMap<Integer, AuditTemplateMappingVo> ();
				for (AuditTemplateMappingVo atmVo: getTemplateDao().getAuditEmailTemplateMappings()) {
					auditEmailTemplateMappings.put (atmVo.getTargetStateId(), atmVo);
				}
			} 
		}catch (DBException e) {
			throw new AuditException("Could not get the template mappings", e);
		}
		return auditEmailTemplateMappings;
	}

	/*private void sendAuditEmail(AuditEmailVo auditEmailVo) throws BusinessServiceException {
        String templateString = null;
        StringWriter sw = new StringWriter();
        templateString = auditEmailVo.getTemplateMapping().getTemplateSource();
        String reasonDesc = getReasonDescriptionSentence(auditEmailVo);
        if(reasonDesc != null){
        	auditEmailVo.setReasonDescriptionParsed(reasonDesc);
        }
        VelocityContext vContext = new VelocityContext();
        LOGGER.info("******************************** Audit email mapping found ! --- VContext " + vContext);
        vContext.put("PrimeContact", auditEmailVo.getFirstNameLastName().trim() );
        vContext.put("auditEmailVO", auditEmailVo);
        vContext.put("auditTemplateVO", auditEmailVo.getTemplateMapping());
        try 
        {
            velocityEngine.evaluate(vContext, sw, "velocity template", templateString);
            if (sw == null) {
                LOGGER.info("Could not generate email text"); 
            }else{
            getEmailTemplateBean().sendGenericEmailWithLogo(auditEmailVo.getTo(),
             auditEmailVo.getTemplateMapping().getTemplateFrom(),
                    auditEmailVo.getTemplateMapping().getTemplateSubject(), sw.toString());
            }
        } 
	    catch( IOException ioException){
        	throw new BusinessServiceException("Problems with velocity", ioException);
	    }
        catch (Exception e) {
        	throw new BusinessServiceException("Error in sendGenericEmailWithLogo()", e);
        }
    }// send email alert
	 */	
	private void populateReasonCodeDescription(AuditEmailVo auditEmailVo,com.newco.marketplace.vo.audit.AuditVO auditVo) {
		LOGGER.info("******************************* REASON " + auditEmailVo);
		String reasonCodeDescription = null;
		try {
			if(auditVo.getReasonCodeIds() != null){
				for (String reasonCode : auditVo.getReasonCodeIds()) {
					reasonCodeDescription = getAuditEmailDao().getReasonCodeDescription(reasonCode);
					auditEmailVo.addReason(reasonCode, reasonCodeDescription);
					LOGGER.info("------------------------ Reason: ---- " + reasonCode   + "                         " + reasonCodeDescription);
				}	           
				LOGGER.info("*******************************" + auditEmailVo.getReasonDescriptionLogger());
				LOGGER.info("*******************************" + auditEmailVo.getFirstNameLastName());
				LOGGER.info("*******************************" + auditEmailVo.getFirstNameLastName());
			}
		} catch (Exception e) {
			LOGGER.info("Caught Exception and ignoring",e);
		}
	}

	/**
	 * @param auditEmailVo
	 * @return
	 *//*
	private String getReasonDescriptionSentence(AuditEmailVo auditEmailVo) {
		VelocityContext vContext = new VelocityContext();
		vContext.put("auditEmailVO", auditEmailVo);
		vContext.put("PrimeContact", auditEmailVo.getFirstNameLastName().trim() );
		Writer sw = new StringWriter();
		try {
			velocityEngine.evaluate(vContext, sw , "", auditEmailVo.getReasonDescription());
		} catch(Exception e) {
			LOGGER.error("Reason Description evaluation failed " + auditEmailVo.getReasonDescriptionLOGGER(),e);
		}

		return sw.toString();
	}*/

	/**@Description :Method to get the alert task object
	 * @param aopHashMap
	 * @return
	 */
	private AlertTask getEmailAlertTask(Map<String, Object> aopHashMap) {		
		Integer templateId  = (Integer)(aopHashMap.get(AOPConstants.AOP_TEMPLATE_ID));
		Template template = getTemplateDetail(templateId);
		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		if(template.getTemplateFrom()!=null){
			alertTask.setAlertFrom(template.getTemplateFrom());
		}else{
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		}
		alertTask.setAlertTo((String)aopHashMap.get(AOPConstants.AOP_USER_EMAIL));
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(aopHashMap.toString());
		return alertTask; 	
	}
	private Template getTemplateDetail(Integer templateId){
		Template template = null;
		try {
			template = new Template();
			template.setTemplateId(templateId);
			template = templateDaoImpl.query(template);
		}
		catch(Exception dse){
			LOGGER.error("AuditEmailBOImpl-->getTemplateDetail-->DataServiceException-->", dse);
		}
		return template;
	}

	
	/*changes for approve provider*/
	

	/**@Description: validating providers for status change.
	 * @param approveProvidersVOList
	 * @return
	 * @throws BusinessServiceException 
	 */
	public List<ApproveProvidersVO> validateProviders(List<ApproveProvidersVO> approveProvidersVOList, Integer adminResourceId)throws BusinessServiceException{
        List<ApproveProvidersVO> validProviders =new ArrayList<ApproveProvidersVO>();
		try{
			if(null!= approveProvidersVOList&& !approveProvidersVOList.isEmpty()){
				//validating admin Resource 
				boolean isAdmin = validateAdminResource(adminResourceId);
				if(!isAdmin){
					return validProviders;
				}
				//Validating HI provider Or Not
	        	approveProvidersVOList = validateExistingProvider(approveProvidersVOList);
	        	
	        	if(null!= approveProvidersVOList&& !approveProvidersVOList.isEmpty()){
	        	  //Removing Invalid providers for further processing
	        		validProviders = removeInvalidOrValidProviders(approveProvidersVOList,ProviderConstants.VALID_FIRM); 
	        	}
	        	if(null!= validProviders&& !validProviders.isEmpty()){
	        	  //Validating Provider wf state for status change
	        		approveProvidersVOList = validateProviderStatus(validProviders,approveProvidersVOList);
	        	}
	        	if(null!= approveProvidersVOList&& !approveProvidersVOList.isEmpty()){
	        		validProviders.clear();
	        	    //Removing Invalid Provider for further processing
	        		validProviders = removeInvalidOrValidProviders(approveProvidersVOList,ProviderConstants.VALID_FIRM);
	        	}
	        	//Validating reasonCodes for Status change
	        	if(null!= validProviders&& !validProviders.isEmpty()){
	        		approveProvidersVOList = validateProviderReasonCodeForStatusChange(validProviders,approveProvidersVOList);
	        	}
	        	if(null!= approveProvidersVOList&& !approveProvidersVOList.isEmpty()){
	        		validProviders.clear();
	        	    //Removing Invalid Provider for further processing
	        		validProviders = removeInvalidOrValidProviders(approveProvidersVOList,ProviderConstants.VALID_FIRM);
	        	}
	        	if(null!= validProviders&& !validProviders.isEmpty()){
	        		approveProvidersVOList = validateBackgroundCheck(validProviders,approveProvidersVOList);
	        	}
			}
			
		}catch (DataServiceException e) {
			LOGGER.error("Exception in validating Provider "+ e.getMessage());
			throw new BusinessServiceException(e);
		}
		return approveProvidersVOList;
	}
	/**@Description: validating providers existing or not
	 * @param approveProvidersVOList
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveProvidersVO> validateExistingProvider(List<ApproveProvidersVO> approveProvidersVOList)throws DataServiceException {
		try{
			List<Integer> providerIdList = iVendorHdrDao.getResourceType(approveProvidersVOList);
			// Check the existence of Hi vendor in provided List
			if(null!= providerIdList &&!(providerIdList.isEmpty())){
				for(ApproveProvidersVO approveProvidersVO : approveProvidersVOList){
					if(providerIdList.contains(approveProvidersVO.getProviderId())){
						approveProvidersVO.setEligibleForStatusChange(true);
						approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
					}else{
						// Setting error code for invalid HI provider.
						approveProvidersVO.setEligibleForStatusChange(false);
						approveProvidersVO.setValidationCode(ResultsCode.INVALID_HI_PROVIDER_ID);
					}
				}
			  
			}else{
				for(ApproveProvidersVO approveProvidersVO : approveProvidersVOList){
					approveProvidersVO.setEligibleForStatusChange(false);
					approveProvidersVO.setValidationCode(ResultsCode.INVALID_HI_PROVIDER_ID);
				}
			}
			
		}catch (com.newco.marketplace.exception.core.DataServiceException e) {
			LOGGER.error("Exception in validating provider +validateExistingProvider()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return approveProvidersVOList;
	}
	/**@Description: Removing Invalid/valid  providers from Parent and move to a new List.
	 * @param approveProvidersVOList
	 * @return
	 */
	public List<ApproveProvidersVO> removeInvalidOrValidProviders(List<ApproveProvidersVO> approveProvidersVOList,String indicator) {
		List<ApproveProvidersVO> validOrInvalidProviders = new ArrayList<ApproveProvidersVO>();
			if(null!= approveProvidersVOList&&!(approveProvidersVOList.isEmpty())){
				for(ApproveProvidersVO providersVO : approveProvidersVOList){
			          if(null!= providersVO && null!= providersVO.getValidationCode())
			        	if(indicator.equalsIgnoreCase(ProviderConstants.VALID_FIRM)){
			        	   if(providersVO.getValidationCode().equals(ResultsCode.SUCCESS)){
			        		   validOrInvalidProviders.add(providersVO);
			        	      }
			            }else{
			            	if(!providersVO.getValidationCode().equals(ResultsCode.SUCCESS)){
			            		validOrInvalidProviders.add(providersVO);
			        	      }
			            }
				   }
			   }
		return validOrInvalidProviders;
	}

	/**@Description: validating Provider for status change.
	 * @param validProviders
	 * @param approveProvidersVOList 
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveProvidersVO> validateProviderStatus(List<ApproveProvidersVO> validProviders, List<ApproveProvidersVO> approveProvidersVOList)throws DataServiceException {
		boolean isEligibleForStatusChange =false;
		try{
			if(null!= validProviders &&!(validProviders.isEmpty())){
				for(ApproveProvidersVO approveProvidersVO : validProviders){
						isEligibleForStatusChange = iVendorHdrDao.checkIfProviderEligibleForStatusChange(approveProvidersVO.getProviderId());
					 if(isEligibleForStatusChange){
						// if(isBackgroundCheckEligible){
						  Integer currentwfStatus=iVendorHdrDao.getProviderWFStateId(approveProvidersVO.getProviderId());
						  Integer requestedWfStatus = iVendorHdrDao.getProviderWfStateIdForStatus(approveProvidersVO.getStatus());
						  if(null!=currentwfStatus && null!= requestedWfStatus &&(requestedWfStatus.equals(currentwfStatus))){
							  approveProvidersVO.setEligibleForStatusChange(false);
							  approveProvidersVO.setValidationCode(ResultsCode.INVALID_CURRENT_PROVIDER_STATUS);
						  }else{
							  approveProvidersVO.setEligibleForStatusChange(true);
							  approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
						  }
					  }else{
						   //Provider is Not Eligible for Status Change.Setting Error Code.
						  approveProvidersVO.setEligibleForStatusChange(false);
						  approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_STATUS); 
					  }
		         }
			}
		 } catch (DBException e) {
			LOGGER.error("Exception in validating Provider + validateProviderStatus()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}catch (Exception e) {
			LOGGER.error("Exception in validating Provider + validateProviderStatus()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		//Updating Parent List with error codes.
		approveProvidersVOList = updateProviderParentList(approveProvidersVOList,validProviders);
		return approveProvidersVOList;
	}
	//method to check background check status in approve providers
	private List<ApproveProvidersVO> validateBackgroundCheck(List<ApproveProvidersVO> validProviders, List<ApproveProvidersVO> approveProvidersVOList)throws DataServiceException {
		String providerStatus = null;
		String backgroundCheck = null;
		Integer dbBackgroundCheckStatus = null;
		ApproveProvidersVO fetchBackgroundCheckVO=new ApproveProvidersVO();
		try{
			if(null!= validProviders &&!(validProviders.isEmpty())){
				for(ApproveProvidersVO approveProvidersVO : validProviders){
						providerStatus = approveProvidersVO.getStatus();
						//back groundcheck status from request
						backgroundCheck = approveProvidersVO.getBackgroundCheckStatus();
						fetchBackgroundCheckVO = iVendorHdrDao.getBackgroundCheckDataForProvider(approveProvidersVO.getProviderId());
						approveProvidersVO.setDbBackgroundCheckId(fetchBackgroundCheckVO.getDbBackgroundCheckId());
						approveProvidersVO.setBgCheckId(fetchBackgroundCheckVO.getBgCheckId());
						//obtaining backgroundcheckId from database
						dbBackgroundCheckStatus = approveProvidersVO.getDbBackgroundCheckId();
						// Provider Status requested is	'Approved (market ready)'
						if(ProviderConstants.APPROVED_STATUS.equalsIgnoreCase(providerStatus)){
							//Bg Status = Clear in  request.
							if(StringUtils.isNotBlank(backgroundCheck) && ProviderConstants.BACKGROUNDCHECKSTATUS_CLEAR.equalsIgnoreCase(backgroundCheck)){
									approveProvidersVO.setCurrentbackgroundCheckStatus(Constants.backgroundCheckMap().get(dbBackgroundCheckStatus));
									approveProvidersVO.setBackgroundCheckStatus(ProviderConstants.BACKGROUNDCHECKSTATUS_CLEAR);
									approveProvidersVO.setNeedDBUpdatedForBgInfo(true);//flag for DB update
									approveProvidersVO.setEligibleForStatusChange(true);
									approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
							}//Bg Status != Clear in  request.
							else if(StringUtils.isNotBlank(backgroundCheck) && !ProviderConstants.BACKGROUNDCHECKSTATUS_CLEAR.equalsIgnoreCase(backgroundCheck)){
								   //Since the provider is not in 'CLEAR' status.Hence we are returning error Response.
									approveProvidersVO.setEligibleForStatusChange(false);
									approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_BG_STATUS);
							}else{
								// Bg status is Not present in Request .Check current BG status in DB is Clear or Not
								if(ProviderConstants.BACKGROUNDCHECKSTATUS_CLEAR_ID.equals(dbBackgroundCheckStatus)){
									approveProvidersVO.setEligibleForStatusChange(true);
									approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
								}else{
									 //Since the provider is not in 'CLEAR' status.Hence we are returning error Response.
									approveProvidersVO.setEligibleForStatusChange(false);
									approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_BG_STATUS);
								}
							}
								
						}
						//Provider Status requested is either of ('Pending Approval','Declined','Out of Compliance','Temporarily Suspended','Terminated')
						else{
							approveProvidersVO.setCurrentbackgroundCheckStatus(Constants.backgroundCheckMap().get(dbBackgroundCheckStatus));
							approveProvidersVO.setBackgroundCheckStatus(backgroundCheck);
							
							if(StringUtils.isNotBlank(backgroundCheck) && null!=approveProvidersVO.getVerificationDate() && null!=approveProvidersVO.getReverificationDate())
							{
								approveProvidersVO.setNeedDBUpdatedForBgInfo(true);//flag for DB update
							}	
							approveProvidersVO.setEligibleForStatusChange(true);
							approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
							 
						}
					}
				}
			} catch (Exception e) {
			LOGGER.error("Exception in validating Provider + validateProviderStatus()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		//Updating Parent List with error codes.
		approveProvidersVOList = updateProviderParentList(approveProvidersVOList,validProviders);
		return approveProvidersVOList;
	}
	
	
	
	/**@Description : Validating Reason Codes with existing codes from DB.
	 * @param validProviders
	 * @param approveProvidersVOList
	 * @return
	 * @throws DataServiceException
	 */
	private List<ApproveProvidersVO> validateProviderReasonCodeForStatusChange(List<ApproveProvidersVO> validProviders,List<ApproveProvidersVO> approveProvidersVOList) throws DataServiceException {
		try{
		if(null!= validProviders &&!(validProviders.isEmpty())){
			for(ApproveProvidersVO approveProvidersVO : validProviders){
					if(null!= approveProvidersVO.getReasonCodes()){
						   List<String> reasonCodeList = Arrays.asList(approveProvidersVO.getReasonCodes());
					       if(null== reasonCodeList || reasonCodeList.isEmpty()){
					    	   approveProvidersVO.setEligibleForStatusChange(false);
					    	   approveProvidersVO.setValidationCode(ResultsCode.REQUIRED_PROVIDER_REASON_CODE);
					       }//Validating the reason code exists in DB
					       else{
					    	   approveProvidersVO = auditDao.getProviderReasonCodesForStatus(approveProvidersVO);
					    	   if(null!= approveProvidersVO &&
					    			   null!= approveProvidersVO.getReasonCodeVoList()
					    			   &&!(approveProvidersVO.getReasonCodeVoList().isEmpty())){
					    		         List<Integer> reasonCodesList = new ArrayList<Integer>(); 
					    		           for(String code:reasonCodeList){ 
					    		    		  for(ReasonCodeVO reasonCode:approveProvidersVO.getReasonCodeVoList()){
					    		    		     if(code.equals(reasonCode.getReasonCodeValue())){
					    		    		    	 reasonCodesList.add(reasonCode.getReasonCodeId());
					    		    		     }
					    		    	  }
					    		       }
					    		        if(null!=reasonCodesList &&!(reasonCodesList.isEmpty()) ){
					    		        	if(reasonCodesList.size()== reasonCodeList.size()){
					    		        		approveProvidersVO.setEligibleForStatusChange(true);
					    		        		approveProvidersVO.setValidationCode(ResultsCode.SUCCESS);
					    		        		approveProvidersVO.setReasonCodeList(reasonCodesList);
					    		        	}else{
					    		        		approveProvidersVO.setEligibleForStatusChange(false);
					    		        		approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_REASON_CODE); 
					    		        	}
					    		        }else{
					    		        	approveProvidersVO.setEligibleForStatusChange(false);
					    		        	approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_REASON_CODE); 
					    		        }
					    	   }else{
					    		   approveProvidersVO.setEligibleForStatusChange(false);
					    		   approveProvidersVO.setValidationCode(ResultsCode.INVALID_PROVIDER_REASON_CODE); 
					    	   }
					       }
					 }else{
						 approveProvidersVO.setEligibleForStatusChange(false);
						 approveProvidersVO.setValidationCode(ResultsCode.REQUIRED_PROVIDER_REASON_CODE);
				   }
			  }
			}
		  
		}catch (Exception e) {
			LOGGER.error("Exception in validating Provider + validateProviderReasonCodeForStatusChange()"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		//Updating Parent List with error codes.
		approveProvidersVOList = updateProviderParentList(approveProvidersVOList,validProviders);
		return approveProvidersVOList;
	}
	
	
	/**@Description: Updating Parent List from Child list.
	 * @param approveProvidersVOList
	 * @param validProviders
	 * @return
	 */
	private List<ApproveProvidersVO> updateProviderParentList(List<ApproveProvidersVO> approveProvidersVOList,List<ApproveProvidersVO> validProviders) {
		if(null!= approveProvidersVOList && !(approveProvidersVOList.isEmpty())
				  && null!= validProviders &&!(validProviders.isEmpty())){
			      for(ApproveProvidersVO validProvider :validProviders){
				      for(ApproveProvidersVO parentVO : approveProvidersVOList){
				    	  if(validProvider.getProviderId().equals(parentVO.getProviderId())){
				    		  if(!ResultsCode.SUCCESS.equals(validProvider.getValidationCode())){
				    			  parentVO.setValidationCode(validProvider.getValidationCode());
				    		  }
				    	  }
				      }
			      }
			
		}
		return approveProvidersVOList;
	}
	
	private VendorNotesVO setVendorNotesForProvider(com.newco.marketplace.vo.audit.AuditVO auditVO) throws DBException {
		StringBuilder noteBody = new StringBuilder();
		VendorNotesVO vendorNotesVO = new VendorNotesVO();
		vendorNotesVO.setVendorId(auditVO.getVendorId());
		noteBody.append("Changed team member status to "+auditVO.getWfState()+ " by ApproveProvider API");
		noteBody.append(" Resource Name "+  iVendorResourceDao.getResourceName(auditVO.getResourceId()));
		if(auditVO.isSendEmailNotice()){
			noteBody.append(" Email sent");
		}else{
			noteBody.append(" Email not sent");
		}
		vendorNotesVO.setNote(noteBody.toString());
		vendorNotesVO.setModifiedBy(auditVO.getReviewedBy());
		return vendorNotesVO;
	}
	
	private BackgroundCheckProviderVO  setBackgroundCheckData(ApproveProvidersVO provider){
		BackgroundCheckProviderVO backgroundCheckProviderVO = new BackgroundCheckProviderVO();
		backgroundCheckProviderVO.setFirmId(provider.getFirmId());
		backgroundCheckProviderVO.setBgRequestType(ProviderConstants.BG_REQUEST_TYPE);
		backgroundCheckProviderVO.setProviderId(provider.getProviderId());
		backgroundCheckProviderVO.setVerificationDate(provider.getVerificationDate());
		backgroundCheckProviderVO.setReverificationDate(provider.getReverificationDate());
		backgroundCheckProviderVO.setRequestDate(provider.getRequestDate());
		backgroundCheckProviderVO.setBgcheckId(provider.getBgCheckId());
		backgroundCheckProviderVO.setBgStatus(Constants.backgroundCheckStatusMap().get(provider.getBackgroundCheckStatus()));
		return backgroundCheckProviderVO;
	}
	
	/*changes for audit_task*/
	
	 public GeneralInfoVO auditResourceBackgroundCheck(int resourceId, String state, GeneralInfoVO generalInfoVO) throws AuditException {
		 com.newco.marketplace.vo.provider.AuditVO auditVO= null;
	         auditVO = new AuditVO(0, resourceId, PublicAPIConstant.RESOURCE_BACKGROUND_CHECK, state);
	        auditVO.setAuditKeyId(resourceId);
	        try {
	            auditResourceBackgroundCheck(auditVO);
	        } catch (AuditException e) {
	        	LOGGER.info("Caught Exception and ignoring",e);
	        }
			return generalInfoVO;
	    }
	 public void auditResourceBackgroundCheck(AuditVO auditVO) throws AuditException {

		    if (auditVO.getWfState() == null) {
	            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
	        }
	        if (auditVO.getAuditKeyId() == null) {
	            auditVO.setAuditKeyId(auditVO.getResourceId());
	        }

	        try{
	            auditVO.setVendorId(iauditDao.getVendorIdByResourceId(auditVO.getResourceId()));
	        }catch (DBException e) {
	            LOGGER.info(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }

	        for (WorkflowStateVO wfState : getWorkflowStates()) {
	            if (wfState.getWfEntity().equals(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK) && wfState.getWfState().equals(auditVO.getWfState())) {
	                auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());
	            }
	        }

	        try{
	            int rows_updated = iauditDao.updateBackGroundCheckByResourceId(auditVO);
	            if (rows_updated == 0) {
	                // there was no row for this vendor id
	                auditVO.setWfEntity(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK);
	                this.createAudit(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK, auditVO);
	            }
	        } catch (DBException e) {
	            LOGGER.info(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	       /* try {
	        	// R11_0 SL-19667 update
	        	if(null== auditVO.getRecertificationInd()){
	        		getAuditDao().updateResourceBackgroundCheckStatus(auditVO);
	        	}else if(auditVO.getRecertificationInd()){
	                int rows=iVendorResourceDao.recertify(auditVO.getResourceId().toString());
	                if(rows>0){
                     //insert background check history
		                GeneralInfoVO generalInfoVO=new GeneralInfoVO();
		                generalInfoVO.setChangedComment(MPConstants.RECERT_SUB);
		                generalInfoVO.setResourceId(auditVO.getResourceId().toString());  
		                iVendorResourceDao.insertBcHistory(generalInfoVO);
	                	}
	            }else{
	                // update background_state_id as '28'-Pending submission
	                int rowsUpdated=iVendorResourceDao.updateBackgroundCheckStatus(auditVO.getResourceId().toString());
	                if(rowsUpdated>0){
	                    //insert background check history
		                GeneralInfoVO generalInfoVO=new GeneralInfoVO();
		                generalInfoVO.setChangedComment(MPConstants.NEW_SCREENING);
		                generalInfoVO.setResourceId(auditVO.getResourceId().toString());
		                iVendorResourceDao.insertBcHistory(generalInfoVO);
	                 }
	            }
	         } catch (DBException e) {
	            LOGGER.info(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }*/
	        LOGGER.debug("[AuditBusinessBean]- auditResourceBackgroundCheck*********************************************\n" + auditVO);
	    }
	 
	 public List<WorkflowStateVO> getWorkflowStates() {

	        if (workflowStates == null) {
	            try {
	                workflowStates = getWorkflowDao().getStateTableMappings();
	            } catch (DBException e) {
	                LOGGER.info("[DBException] " + StackTraceHelper.getStackTrace(e));
	                new AuditException("[AuditBusinessBean] Could not cache the work flow states");
	            }
	        }
	        return workflowStates;
	    }// getWorkflowStates()
	 
	

	public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException {

	        for (WorkflowStateVO wfState : getWorkflowStates()) {        
	            if (wfState.getWfEntity().equals(wfEntity) && wfState.getWfState().equals(auditVO.getWfState())) {
	            	auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());                
	            }
	        }

	        try {        	
	        	iauditDao.insert(auditVO);
	        } catch (DBException dse) {
	            LOGGER.info("[DBException] " + StackTraceHelper.getStackTrace(dse));
	            final String error = messages.getMessage("biz.select.failed");
	            throw new AuditException(error, dse);
	        }

	        LOGGER.info("[AuditBusinessBean]*************************************************createAudit\n" + auditVO);
	    }// end method
	
	
	public IContactDao getiContactDao() {
		return iContactDao;
	}
	public void setiContactDao(IContactDao iContactDao) {
		this.iContactDao = iContactDao;
	}
	public IContactMethodPrefDao getiContactMethodPrefDao() {
		return iContactMethodPrefDao;
	}
	public void setiContactMethodPrefDao(IContactMethodPrefDao iContactMethodPrefDao) {
		this.iContactMethodPrefDao = iContactMethodPrefDao;
	}
	public ILocationDao getiLocationDao() {
		return iLocationDao;
	}
	public void setiLocationDao(ILocationDao iLocationDao) {
		this.iLocationDao = iLocationDao;
	}
	public IResourceLocationDao getiResourceLocationDao() {
		return iResourceLocationDao;
	}
	public void setiResourceLocationDao(IResourceLocationDao iResourceLocationDao) {
		this.iResourceLocationDao = iResourceLocationDao;
	}
	public ILookupDAO getiLookupDAO() {
		return iLookupDAO;
	}
	public void setiLookupDAO(ILookupDAO iLookupDAO) {
		this.iLookupDAO = iLookupDAO;
	}
	public IUserProfileDao getiUserProfileDao() {
		return iUserProfileDao;
	}
	public void setiUserProfileDao(IUserProfileDao iUserProfileDao) {
		this.iUserProfileDao = iUserProfileDao;
	}
	public IVendorContactDao getiVendorContactDao() {
		return iVendorContactDao;
	}
	public void setiVendorContactDao(IVendorContactDao iVendorContactDao) {
		this.iVendorContactDao = iVendorContactDao;
	}
	public IVendorHdrDao getiVendorHdrDao() {
		return iVendorHdrDao;
	}
	public void setiVendorHdrDao(IVendorHdrDao iVendorHdrDao) {
		this.iVendorHdrDao = iVendorHdrDao;
	}
	public IVendorLocationDao getiVendorLocationDao() {
		return iVendorLocationDao;
	}
	public void setiVendorLocationDao(IVendorLocationDao iVendorLocationDao) {
		this.iVendorLocationDao = iVendorLocationDao;
	}
	public IVendorResourceDao getiVendorResourceDao() {
		return iVendorResourceDao;
	}
	public void setiVendorResourceDao(IVendorResourceDao iVendorResourceDao) {
		this.iVendorResourceDao = iVendorResourceDao;
	}
	public IProviderEmailBO getiProviderEmailBO() {
		return iProviderEmailBO;
	}
	public void setiProviderEmailBO(IProviderEmailBO iProviderEmailBO) {
		this.iProviderEmailBO = iProviderEmailBO;
	}
	public IVendorFinanceDao getiVendorFinanceDao() {
		return iVendorFinanceDao;
	}
	public void setiVendorFinanceDao(IVendorFinanceDao iVendorFinanceDao) {
		this.iVendorFinanceDao = iVendorFinanceDao;
	}
	public IVendorPolicyDao getiVendorPolicyDao() {
		return iVendorPolicyDao;
	}
	public void setiVendorPolicyDao(IVendorPolicyDao iVendorPolicyDao) {
		this.iVendorPolicyDao = iVendorPolicyDao;
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

	public SecurityDAO getSecurityDao() {
		return securityDao;
	}



	public void setSecurityDao(SecurityDAO securityDao) {
		this.securityDao = securityDao;
	}

	public IBusinessinfoDao getiBusinessinfoDao() {
		return iBusinessinfoDao;
	}

	public void setiBusinessinfoDao(IBusinessinfoDao iBusinessinfoDao) {
		this.iBusinessinfoDao = iBusinessinfoDao;
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}



	public IRegistrationBO getProviderRegistrationBO() {
		return providerRegistrationBO;
	}

	public void setProviderRegistrationBO(IRegistrationBO providerRegistrationBO) {
		this.providerRegistrationBO = providerRegistrationBO;
	}

	public IZipDao getZipDao() {
		return zipDao;
	}

	public void setZipDao(IZipDao zipDao) {
		this.zipDao = zipDao;
	}

	public LookupDao getCommonLookkupDAO() {
		return commonLookkupDAO;
	}

	public void setCommonLookkupDAO(LookupDao commonLookkupDAO) {
		this.commonLookkupDAO = commonLookkupDAO;
	}

	public ICredentialDao getCredentialDao() {
		return credentialDao;
	}

	public void setCredentialDao(ICredentialDao credentialDao) {
		this.credentialDao = credentialDao;
	}

	public IInsuranceTypePolicyBO getiInsurancePolicyBO() {
		return iInsurancePolicyBO;
	}

	public void setiInsurancePolicyBO(IInsuranceTypePolicyBO iInsurancePolicyBO) {
		this.iInsurancePolicyBO = iInsurancePolicyBO;
	}

	public ILicensesAndCertDao getiLicensesAndCertDao() {
		return iLicensesAndCertDao;
	}

	public void setiLicensesAndCertDao(ILicensesAndCertDao iLicensesAndCertDao) {
		this.iLicensesAndCertDao = iLicensesAndCertDao;
	}

	public IW9RegistrationDao getW9RegistrationDao() {
		return w9RegistrationDao;
	}

	public IVendorNotesDao getVendorNotesDao() {
		return vendorNotesDao;
	}


	public void setVendorNotesDao(IVendorNotesDao vendorNotesDao) {
		this.vendorNotesDao = vendorNotesDao;
	}


	public AuditDao getAuditDao() {
		return auditDao;
	}


	public void setAuditDao(AuditDao auditDao) {
		this.auditDao = auditDao;
	}


	public ITemplateDao getTemplateDao() {
		return templateDao;
	}


	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}


	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}


	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	public JavaMailSender getMailSender() {
		return mailSender;
	}


	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setW9RegistrationDao(IW9RegistrationDao w9RegistrationDao) {
		this.w9RegistrationDao = w9RegistrationDao;
	}


	public AlertDaoImpl getAlertDao() {
		return alertDao;
	}


	public void setAlertDao(AlertDaoImpl alertDao) {
		this.alertDao = alertDao;
	}


	public IAuditEmailDao getAuditEmailDao() {
		return auditEmailDao;
	}


	public void setAuditEmailDao(IAuditEmailDao auditEmailDao) {
		this.auditEmailDao = auditEmailDao;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public TemplateDaoImpl getTemplateDaoImpl() {
		return templateDaoImpl;
	}

	public void setTemplateDaoImpl(TemplateDaoImpl templateDaoImpl) {
		this.templateDaoImpl = templateDaoImpl;
	}

	public IAuditDao getIauditDao() {
		return iauditDao;
	}

	public void setIauditDao(IAuditDao iauditDao) {
		this.iauditDao = iauditDao;
	}

	public void setWorkflowStates(List<WorkflowStateVO> workflowStates) {
		this.workflowStates = workflowStates;
	}

	
	
	
	 public IWorkflowDao getWorkflowDao() {
			return workflowDao;
		}

		public void setWorkflowDao(IWorkflowDao workflowDao) {
			this.workflowDao = workflowDao;
		}

}
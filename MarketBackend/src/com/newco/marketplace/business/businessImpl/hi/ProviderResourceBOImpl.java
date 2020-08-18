package com.newco.marketplace.business.businessImpl.hi;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.hi.IProviderResourceBO;
import com.newco.marketplace.business.iBusiness.provider.IActivityRegistryBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.persistence.daoImpl.provider.ResourceLocationDaoImpl;
import com.newco.marketplace.persistence.iDao.lookup.LookupDao;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IAuditDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao;
import com.newco.marketplace.persistence.iDao.provider.IResourceScheduleDao;
import com.newco.marketplace.persistence.iDao.provider.ISkillAssignDao;
import com.newco.marketplace.persistence.iDao.provider.ITeamCredentialsDao;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IWorkflowDao;
import com.newco.marketplace.persistence.iDao.provider.IZipDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.hi.provider.ProviderAccountVO;
import com.newco.marketplace.vo.hi.provider.ProviderSkillVO;
import com.newco.marketplace.vo.hi.provider.SkillDetailVO;
import com.newco.marketplace.vo.provider.AuditVO;
import com.newco.marketplace.vo.provider.BackgroundCheckVO;
import com.newco.marketplace.vo.provider.GeneralInfoVO;
import com.newco.marketplace.vo.provider.LanguageVO;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.LookupVO;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.provider.ResourceLocation;
import com.newco.marketplace.vo.provider.SkillAssignVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.vo.provider.UserProfile;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.vo.provider.WorkflowStateVO;



public class ProviderResourceBOImpl implements IProviderResourceBO{
	private static final Logger LOGGER  =Logger.getLogger(ProviderResourceBOImpl.class);
	private static final MessageResources messages = MessageResources.getMessageResources("BizLocalStrings");
	private List stateList ;
	private List serviceAreaList;
	private List bgStatusList;
	private ILookupDAO iLookupDAO;
	private LookupDao lookupDao;
	private IZipDao zipDao;
	private IContactDao iContactDao;
	private IVendorResourceDao iVendorResourceDao;
	private IResourceScheduleDao iResourceScheduleDao;
	private ILocationDao iLocationDao;
	private IUserProfileDao iUserProfileDao;
	private IActivityRegistryDao activityRegistryDao;
	private IAuditDao auditDao;
	private IWorkflowDao workflowDao;
	private ITeamCredentialsDao iTeamCredentialsDao;
	private ISkillAssignDao iSkillAssignDao; 
	private List<WorkflowStateVO> workflowStates = null;
	private IMarketPlaceDao iMarketPlaceDao;
	private  ResourceLocationDaoImpl resourceLocationDaoImpl;
	private IVendorHdrDao iVendorHdrDao;
	private IActivityRegistryBO activityRegistryBO;
	private  boolean errorOccured =false;
	
	/**@Description : Validate request for creating provider Account.
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProviderAccountVO validateCreateProviderAccount(ProviderAccountVO providerVO) throws BusinessServiceException {
		 errorOccured = false;
		 try{
			 // Validating Provider Firm 
			 if(StringUtils.isNotBlank(providerVO.getVendorId()) && StringUtils.isNumeric(providerVO.getVendorId())){
				 validateProviderFirm(providerVO);
				  if(errorOccured){
					 return  providerVO;
				  }
			  }
			 //Validating SSN
			  if(!errorOccured && StringUtils.isNotBlank(providerVO.getSsn()) && (StringUtils.isNumeric(providerVO.getSsn()))&&(providerVO.getSsn().trim().length()==9)){
				   validateSSN(providerVO);
			  }
			  //Validate Dispatcher State
	          if(!errorOccured && StringUtils.isNotBlank(providerVO.getDispAddState())){
	        	  validateState(providerVO);
	          }
	          //Validate State- Zip combination matching or not
	          if(!errorOccured && StringUtils.isNotBlank(providerVO.getDispAddZip())){
	        	  validateStateZip(providerVO);
	          }
	          //Validate UserName
	          if(!errorOccured && StringUtils.isNotBlank(providerVO.getUserName())){
	        	  validateUserName(providerVO);
	          }
	          //Validate Secondary Contact
	          if(!errorOccured && StringUtils.isNotBlank(providerVO.getSecondaryContact())){
	        	  validateSecondaryContact(providerVO);
	          }
	          //Validate Start/End Time in availability
	          if(!errorOccured){
	        	  validateAvailablity(providerVO);
	          }
	          //validate credential Details.
	          if(!errorOccured){
	        	  validateCredential(providerVO);
	          }
	          //Mapping Look up values if all validations are successful.
	          if(!errorOccured){
	        	  mapLookUpValues(providerVO);
	          }
	          
				
	    }catch (Exception e) {
			 LOGGER.error("Exception in validating Request for creating provider account"+ e.getMessage());
			 throw new BusinessServiceException(e.getMessage());
		 }
		 return providerVO;   
	}

	public ProviderAccountVO validateUpdateProviderDetails(
			ProviderAccountVO providerAccountVO) throws Exception {
		
		errorOccured=false;
		
		 //Validate Dispatcher State
      	if(StringUtils.isNotBlank(providerAccountVO.getDispAddState()))
      	{
      		validateState(providerAccountVO);
      	}
      	
        //Validate State- Zip combination matching or not
        if(!errorOccured && null!=providerAccountVO.getDispAddState() && null!=providerAccountVO.getDispAddZip()){
      	  validateStateZip(providerAccountVO);
        }
       
        //Validate Secondary Contact
        if(!errorOccured && StringUtils.isNotBlank(providerAccountVO.getSecondaryContact())){
      	  validateSecondaryContact(providerAccountVO);
        }
        
        //Validate Start/End Time in availability
        if(!errorOccured){
      	  validateAvailablity(providerAccountVO);
        }
        
        //validate credential details.
		if(!errorOccured && null!=providerAccountVO.getLicensesList() && !providerAccountVO.getLicensesList().isEmpty()){
			validateUpdateLicense(providerAccountVO,providerAccountVO.getLicensesList());
		}
		
		 //validate update credential details.
		if(!errorOccured && null!=providerAccountVO.getEditLicensesList() && !providerAccountVO.getEditLicensesList().isEmpty()){
			validateUpdateLicense(providerAccountVO,providerAccountVO.getEditLicensesList());
		}
		
		 //Mapping Look up values if all validations are successful.
        if(!errorOccured){
      	  mapLookUpValues(providerAccountVO);
       }
		
		return providerAccountVO;
	}
	
	
	
	/**@Description : Validating SSN
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateSSN(ProviderAccountVO providerVO) throws BusinessServiceException {
		GeneralInfoVO generalInfoVO =new GeneralInfoVO();
		boolean isExists = false;
		try{
			// Encrypting ssn number
			String resourceSsn = providerVO.getSsn();
			generalInfoVO.setSsn(CryptoUtil.encryptKeyForSSNAndPlusOne(resourceSsn));			
			generalInfoVO.setResourceId(null);
			generalInfoVO.setVendorId(providerVO.getVendorId());
			isExists = iVendorResourceDao.isSameResourceExist(generalInfoVO);
			if(!isExists){
				//setting last 4 digits of ssn.
				String ssnLast4 = resourceSsn.substring(5);
				providerVO.setSsnLast4(ssnLast4);
				providerVO.setSsn(CryptoUtil.encryptKeyForSSNAndPlusOne(resourceSsn));
			}else{
				errorOccured =true;
				Results result = Results.getError(ResultsCode.INVALID_SSN.getMessage(), ResultsCode.INVALID_SSN.getCode());
				providerVO.setResults(result);
			}
			
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating SSN in validateSSN()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
	}
	
	/**@Description: Validate Dispatcher State
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateState(ProviderAccountVO providerVO) throws BusinessServiceException {
		try{
			if(null == stateList){
				stateList = iLookupDAO.loadStates();
			 }
			String stateExist = validateStateValues(stateList,providerVO.getDispAddState());
			if(StringUtils.isNotBlank(stateExist)){
				providerVO.setDispAddState(stateExist);
			}else{
				errorOccured =true;
				Results result = Results.getError(ResultsCode.INVALID_DISPATCH_STATE.getMessage(), ResultsCode.INVALID_DISPATCH_STATE.getCode());
				providerVO.setResults(result);
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating state in validateState()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description : Validate State- Zip combination matching or not
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateStateZip(ProviderAccountVO providerVO) throws BusinessServiceException {
		List stateTypeList = null;
		boolean isValidStateZip;
		try{
			stateTypeList = zipDao.queryList(providerVO.getDispAddState());
			if(null!= stateTypeList && !stateTypeList.isEmpty()){
				isValidStateZip = validateStateZip(stateTypeList, providerVO.getDispAddZip());
				if(!isValidStateZip){
					errorOccured =true;
					Results result = Results.getError(ResultsCode.INVALID_DISPATCH_STATE_ZIP.getMessage(), ResultsCode.INVALID_DISPATCH_STATE_ZIP.getCode());
					providerVO.setResults(result);
			   }
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating state zip combination  in validateStateZip()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	
	/**@Description : Validate UserName exists in DB or NOt
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateUserName(ProviderAccountVO providerVO)throws BusinessServiceException {
		GeneralInfoVO tempGInfoVO = null;
		boolean isValidLength ;
		try{
			    isValidLength = validateUserNameLength(providerVO.getUserName());
				if(isValidLength){
						tempGInfoVO = iVendorResourceDao.getVendorResourceByUserId(providerVO.getUserName().trim());
						if(null != tempGInfoVO){
							errorOccured =true;
							Results result = Results.getError(ResultsCode.USER_NAME_EXISTS.getMessage(), ResultsCode.USER_NAME_EXISTS.getCode());
							providerVO.setResults(result);
						}
				}else{
					errorOccured =true;
					Results result = Results.getError(ResultsCode.INVALID_USER_NAME_LENGTH.getMessage(), ResultsCode.INVALID_USER_NAME_LENGTH.getCode());
					providerVO.setResults(result);
				}
			
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating userName   in validateUserName()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	
	/**@Description : Validate Secondary Contact
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateSecondaryContact(ProviderAccountVO providerVO) throws BusinessServiceException{
		try{
			if(StringUtils.isNotBlank(providerVO.getSecondaryContact())){
				String secondaryContact = providerVO.getSecondaryContact();
				if(secondaryContact.equals(PublicAPIConstant.ALTERNATE_CONTACT) && StringUtils.isBlank(providerVO.getAltEmail())){
					errorOccured =true;
					Results result = Results.getError(ResultsCode.INVALID_SECONDARY_CONTACT.getMessage(), ResultsCode.INVALID_SECONDARY_CONTACT.getCode());
					providerVO.setResults(result);
				}else if(secondaryContact.equals(PublicAPIConstant.SMS_CONTACT) && StringUtils.isBlank(providerVO.getSmsAddress())){
					errorOccured =true;
					Results result = Results.getError(ResultsCode.INVALID_SECONDARY_CONTACT.getMessage(), ResultsCode.INVALID_SECONDARY_CONTACT.getCode());
					providerVO.setResults(result);
				}
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating secondary contact   in validateSecondaryContact()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description : Validate Start/End Time in availability
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateAvailablity(ProviderAccountVO providerVO)throws BusinessServiceException {
		try{
			
			if(providerVO.isDuplicateWeekPresent())
			{
				errorOccured =true;
				Results result = Results.getError(ResultsCode.DUPLICATE_WEEKDAYS.getMessage(), ResultsCode.DUPLICATE_WEEKDAYS.getCode());
				providerVO.setResults(result);
				return providerVO;
			}
			else
			{	

			if(StringUtils.isNotBlank(providerVO.getSun24Ind()) && StringUtils.isNotBlank(providerVO.getSunNaInd())){
				if(StringUtils.equals(providerVO.getSun24Ind(), providerVO.getSunNaInd()) && providerVO.getSun24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getSunStart() || null== providerVO.getSunEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getSunStart() && null!= providerVO.getSunEnd()){
						if(validateStartEndTime(providerVO.getSunStart(),providerVO.getSunEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getMon24Ind()) && StringUtils.isNotBlank(providerVO.getMonNaInd())){
				if(StringUtils.equals(providerVO.getMon24Ind(), providerVO.getMonNaInd()) && providerVO.getMon24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getMonStart() || null== providerVO.getMonEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getMonStart() && null!= providerVO.getMonEnd()){
						if(validateStartEndTime(providerVO.getMonStart(),providerVO.getMonEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getTue24Ind()) && StringUtils.isNotBlank(providerVO.getTueNaInd())){
				if(StringUtils.equals(providerVO.getTue24Ind(), providerVO.getTueNaInd()) && providerVO.getTue24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getTueStart() || null== providerVO.getTueEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getTueStart() && null!= providerVO.getTueEnd()){
						if(validateStartEndTime(providerVO.getTueStart(),providerVO.getTueEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getWed24Ind()) && StringUtils.isNotBlank(providerVO.getWedNaInd())){
				if(StringUtils.equals(providerVO.getWed24Ind(), providerVO.getWedNaInd()) && providerVO.getWed24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getWedStart() || null== providerVO.getWedEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getWedStart() && null!= providerVO.getWedEnd()){
						if(validateStartEndTime(providerVO.getWedStart(),providerVO.getWedEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getThu24Ind()) && StringUtils.isNotBlank(providerVO.getThuNaInd())){
				if(StringUtils.equals(providerVO.getThu24Ind(), providerVO.getThuNaInd()) && providerVO.getThu24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getThuStart() || null== providerVO.getThuEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getThuStart() && null!= providerVO.getThuEnd()){
						if(validateStartEndTime(providerVO.getThuStart(),providerVO.getThuEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getFri24Ind()) && StringUtils.isNotBlank(providerVO.getFriNaInd())){
				if(StringUtils.equals(providerVO.getFri24Ind(), providerVO.getFriNaInd()) && providerVO.getFri24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getFriStart() || null== providerVO.getFriEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getFriStart() && null!= providerVO.getFriEnd()){
						if(validateStartEndTime(providerVO.getFriStart(),providerVO.getFriEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
			if(StringUtils.isNotBlank(providerVO.getSat24Ind()) && StringUtils.isNotBlank(providerVO.getSatNaInd())){
				if(StringUtils.equals(providerVO.getSat24Ind(), providerVO.getSatNaInd()) && providerVO.getSat24Ind().equals(PublicAPIConstant.ZERO)){
					if(null== providerVO.getSatStart() || null== providerVO.getSatEnd()){
						errorOccured =true;
						Results result = Results.getError(ResultsCode.INVALID_START_END_TIME.getMessage(), ResultsCode.INVALID_START_END_TIME.getCode());
						providerVO.setResults(result);
						return providerVO;
				
					}else if(null!=providerVO.getSatStart() && null!= providerVO.getSatEnd()){
						if(validateStartEndTime(providerVO.getSatStart(),providerVO.getSatEnd(),providerVO))
						{
							errorOccured =true;
							return providerVO;
						}
					}
				}
				
			}
		  }	
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating availability in validateAvailablity()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}


	/**@Description:Validating start time and end time in availability
	 * @param startTime
	 * @param endTime
	 * @param providerVO
	 * @return
	 */
	private boolean validateStartEndTime(Date startTime,Date endTime,ProviderAccountVO providerVO) {
		if(startTime.after(endTime) || startTime.compareTo(endTime)==0)
		{
			Results result = Results.getError(ResultsCode.INVALID_START_TIME_GREATER_END_TIME.getMessage(), ResultsCode.INVALID_START_TIME_GREATER_END_TIME.getCode());
			providerVO.setResults(result);
			return true;
		}
		return false;
	}
	
	/**@Description: Mapping Look Up values for BG info,Languages.secondary contact and Servcie radius.
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO mapLookUpValues(ProviderAccountVO providerVO) throws BusinessServiceException{
		try{
			//Mapping service radius	
			providerVO = mapLookUpServiceRadius(providerVO);
			//Mapping BG Status
			providerVO =mapLookUpBgStatus(providerVO);
			//Mapping secondary Contact
			providerVO =mapLookUpSecondaryContact(providerVO);
			//Map Languages
			providerVO =mapLookUpLanguages(providerVO);
			
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in mapping look up values in mapLookUpValues()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description : Map Look up value for the service radius.
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO mapLookUpServiceRadius(ProviderAccountVO providerVO) throws BusinessServiceException{
		try{
			
		if(StringUtils.isNotBlank(providerVO.getDispAddGeographicRange())){
			if(null == serviceAreaList){
				serviceAreaList = iLookupDAO.loadServiceAreaRadius();
			 }
			LOGGER.error("Geographical Range from Request: "+ providerVO.getDispAddGeographicRange());
			String geographicalRange = validateServiceRadiusValues(serviceAreaList,providerVO.getDispAddGeographicRange());
			LOGGER.error("Geographical Range from LOOK UP: "+ providerVO.getGeographicalRange());
			if(StringUtils.isNotBlank(geographicalRange) && StringUtils.isNumeric(geographicalRange)){
				providerVO.setDispAddGeographicRange(geographicalRange);
			}
		}	
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in mapping look up values for service radius()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description : Mapping BG information
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO mapLookUpBgStatus(ProviderAccountVO providerVO) throws BusinessServiceException{
		try{
			if(StringUtils.isNotBlank(providerVO.getBackgroundCheckStatus())){
				if(null== bgStatusList){
				bgStatusList=lookupDao.getBackgroundStatuses();
			}
			providerVO.setBgStatusString(providerVO.getBackgroundCheckStatus());
			String wfStatusId = validateLookUp(bgStatusList,providerVO.getBackgroundCheckStatus());
			providerVO.setBackgroundCheckStatus(wfStatusId);
		}	
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in mapping look up values for bg Status"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description: Mapping secondary contact.
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO mapLookUpSecondaryContact(ProviderAccountVO providerVO) throws BusinessServiceException{
		try{
			
			if(StringUtils.isNotBlank(providerVO.getSecondaryContact())){
				Integer secondaryContact= PublicAPIConstant.secondaryContactMap().get(providerVO.getSecondaryContact());
				if( null!=secondaryContact ){
					providerVO.setSecondaryContact1(secondaryContact);
				}
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in mapping look up values for SocondaryContact"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description : Mapping Languages look up value.
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO mapLookUpLanguages(ProviderAccountVO providerVO) throws BusinessServiceException{
		try {
			if(null!=providerVO.getLanguageList() && !providerVO.getLanguageList().isEmpty()){
			ArrayList<LanguageVO> languageList = new ArrayList<LanguageVO>();
			List languages=lookupDao.getLanguages();
	        for(int i=0;i<providerVO.getLanguageList().size();i++){
               Integer languageId=validateLookUpValues(languages,providerVO.getLanguageList().get(i));
				if(null!=languageId){
					Long langId = new Long (languageId);
					LanguageVO languageVO = new LanguageVO();
					languageVO.setLanguageId(langId);
					languageList.add(languageVO);
				}
			}
			providerVO.setLanguages(languageList);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in mapping languages for the resource:",e);
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	
	/**@Description : Validate Credential Details
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateCredential(ProviderAccountVO providerVO) throws BusinessServiceException{
		List<TeamCredentialsVO> licensesList=null;
		try{
			licensesList= providerVO.getLicensesList();
			if(providerVO.isLicensecredentialInd() && null!=licensesList && !licensesList.isEmpty()){
				providerVO = validateLicense(providerVO, licensesList);
				providerVO.setNoCredInd(false);
			}else if(providerVO.isLicensecredentialInd() && null == licensesList){
				Results results = Results.getError(ResultsCode.INVALID_LICENSE_CREDENTIALS.getMessage(),
                        ResultsCode.INVALID_LICENSE_CREDENTIALS.getCode());
				providerVO.setResults(results);
				errorOccured = true;
			}else if(!providerVO.isLicensecredentialInd()){
				providerVO.setNoCredInd(true);
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating Credentils for the provider"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
		
	}
	/**@Description: Validating Provider Firm
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private ProviderAccountVO validateProviderFirm(ProviderAccountVO providerVO) throws BusinessServiceException {
		Integer resultId = null;
		Integer vendorId = Integer.valueOf(providerVO.getVendorId());
		try{
			resultId =iVendorHdrDao.getVendorWFStateId(vendorId);
			if(null== resultId){
				errorOccured =true;
				Results result = Results.getError(ResultsCode.INVALID_FIRMID.getMessage(), ResultsCode.INVALID_FIRMID.getCode());
				providerVO.setResults(result);
				return providerVO;
			}
		}catch (Exception e) {
			errorOccured =true;
			LOGGER.error("Exception in validating provider Firm Id"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return providerVO;
	}
	/**@Description : Create Provider Profile from the valid request
	 * @param providerVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public ProviderAccountVO createProviderProfile(ProviderAccountVO providerVO)throws BusinessServiceException {
		ProviderAccountVO resultVO =null;
		GeneralInfoVO generalInfoVO = null;
		try{
			//Saving contact information of the provider
			generalInfoVO = saveContactInformation(providerVO);
			//Saving Resource Location Details
			generalInfoVO = saveResourceLocation(providerVO,generalInfoVO);
			//Save resource details in vendor_resource table.
			generalInfoVO = saveVendorResource(providerVO,generalInfoVO);
			//Updating resource id generated to result vo for response mapping.
			if(null != generalInfoVO && StringUtils.isNotBlank(generalInfoVO.getResourceId())){
				resultVO = new ProviderAccountVO();
				resultVO.setVendorId(providerVO.getVendorId());
				resultVO.setResourceId(generalInfoVO.getResourceId());
			}else{
				throw new BusinessServiceException("Exception in registering firm while saving vendor_resource details");
			}
			//Save Vendor resource loaction in resource loaction table.
			generalInfoVO = saveResourceLocation(generalInfoVO);
			//Save user profile Details
			generalInfoVO = saveUserProfile(generalInfoVO);
			//Saving Resource schedule details.
			generalInfoVO = saveResourceSchedule(providerVO,generalInfoVO);
			//Saving Languages
			generalInfoVO = saveLanguages(providerVO,generalInfoVO);
			//Saving Credentails for the resource
			generalInfoVO = saveCredentials(providerVO,generalInfoVO);
			//Saving Activity Registry for all tabs with 0
			generalInfoVO = saveActivityRegistry(generalInfoVO);
			//Saving Back ground Information
			generalInfoVO = savingBackGroundInformation(providerVO,generalInfoVO);
			//Added for logging Purpose
			Integer resourceId = Integer.parseInt(generalInfoVO.getResourceId());
			LOGGER.info("Resource Id generated :"+resourceId);
			//Audit task for new resource added.
			generalInfoVO = auditVendorResource(resourceId,PublicAPIConstant.RESOURCE_INCOMPLETE, generalInfoVO);
			//audit atsk for background info
			generalInfoVO = auditResourceBackgroundCheck(resourceId,providerVO.getBgStatusString(),generalInfoVO);
			//updating actvity registry 
			generalInfoVO = updateActivityRegistry(providerVO,generalInfoVO);
			//Sending Mails to new provider added.
			sendMailToNewUser(providerVO);
			
		}catch (Exception e) {
			LOGGER.error("Exception in creating provider account in createProviderProfile()"+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return resultVO;
	}
	
	public ProviderAccountVO updateProvider(ProviderAccountVO providerAccountVO) throws Exception {
		
		GeneralInfoVO tempGeneralInfoVO = new GeneralInfoVO();
		tempGeneralInfoVO.setResourceId(providerAccountVO.getResourceId());
		tempGeneralInfoVO = iVendorResourceDao.get(tempGeneralInfoVO);
		
		//Updating contact table
		updateContact(providerAccountVO,tempGeneralInfoVO);
		
		//Updating location table
		updateLocation(providerAccountVO,tempGeneralInfoVO);
		
		//Updating vendor resource
		updateVendorResource(providerAccountVO,tempGeneralInfoVO);
		
		//Updating resource schedule
		updateResourceSchedule(providerAccountVO,tempGeneralInfoVO);
		
		//Updating background details
		updateBackgroundDetails(providerAccountVO);
	
		//Updating Languages
		updateLanguages(providerAccountVO);
		
		//Updating license details
		updateLicenseAndCert(providerAccountVO);
		
		if(StringUtils.isNotBlank(tempGeneralInfoVO.getVendorId()))
		{
			providerAccountVO.setVendorId(tempGeneralInfoVO.getVendorId());
		}
	
		return providerAccountVO;
	}
	

	/**@Description Updating contact details of the provider
	 * @param providerAccountVO
	 * @param tempGeneralInfoVO
	 * @return
	 * @throws BusinessServiceException
	 */
      private GeneralInfoVO updateContact(ProviderAccountVO providerAccountVO,GeneralInfoVO tempGeneralInfoVO)throws BusinessServiceException {
	  try{
	   if (tempGeneralInfoVO.getContactId() > 0) {
		//new method
		MarketPlaceVO marketPlaceVO =new MarketPlaceVO();

		marketPlaceVO.setContactID(Integer.toString(tempGeneralInfoVO.getContactId()));
		marketPlaceVO.setOtherJobTitle(providerAccountVO.getOtherJobTitle());
		marketPlaceVO.setBusinessPhone(providerAccountVO.getBusinessPhone());
		marketPlaceVO.setBusinessExtn(providerAccountVO.getBusinessExtn());
		marketPlaceVO.setMobilePhone(providerAccountVO.getMobilePhone());
		marketPlaceVO.setEmail(providerAccountVO.getEmail());
		marketPlaceVO.setAltEmail(providerAccountVO.getAltEmail());
		marketPlaceVO.setPrimaryContact(providerAccountVO.getPrimaryContact());
		marketPlaceVO.setSecondaryContact1(providerAccountVO.getSecondaryContact1());
		marketPlaceVO.setSmsAddress(providerAccountVO.getSmsAddress());
		
		if((StringUtils.isNotBlank(marketPlaceVO.getContactID())) && (StringUtils.isNotBlank(marketPlaceVO.getOtherJobTitle()) 
				|| StringUtils.isNotBlank(marketPlaceVO.getBusinessPhone()) || StringUtils.isNotBlank(marketPlaceVO.getEmail()) 
				|| StringUtils.isNotBlank(marketPlaceVO.getAltEmail()) || null!=marketPlaceVO.getSecondaryContact1()
				|| StringUtils.isNotBlank(marketPlaceVO.getSmsAddress()) || StringUtils.isNotBlank(marketPlaceVO.getMobilePhone()) || StringUtils.isNotBlank(marketPlaceVO.getBusinessExtn())))
		{
			iMarketPlaceDao.updateContactDetails(marketPlaceVO);
			//Updates Activity Registry Table
			activityRegistryDao.updateResourceActivityStatus(tempGeneralInfoVO.getResourceId(), 
															ActivityRegistryConstants.RESOURCE_MARKETPLACE);
		}
	}	

	} catch(Exception e)
	{
		LOGGER.error("Exception in updating contact details of the provider"+e.getMessage());
		throw new BusinessServiceException(e.getMessage());
	}
	return tempGeneralInfoVO;
}


	/**@Description Updating location details of the provider
	 * @param providerAccountVO
	 * @param tempGeneralInfoVO
	 * @return
	 * @throws BusinessServiceException
	 */
    private GeneralInfoVO updateLocation(ProviderAccountVO providerAccountVO,GeneralInfoVO tempGeneralInfoVO)
		 throws BusinessServiceException {
	try{
		if (tempGeneralInfoVO.getLocationId() > 0) {
		mapLocationForUpdate(providerAccountVO, tempGeneralInfoVO);
		
		if(StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddStreet1()) 
				 && StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddState()) 
				&& StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddCity()) && StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddZip()))
		{
			if(null == tempGeneralInfoVO.getDispAddStreet2())
			{
				tempGeneralInfoVO.setDispAddStreet2(StringUtils.EMPTY);
			}
		
			if(null == tempGeneralInfoVO.getDispAddApt())
			{
				tempGeneralInfoVO.setDispAddApt(StringUtils.EMPTY);
			}
			
			iLocationDao.update(tempGeneralInfoVO);
		}
		
	}else{
		//Code added to update dispatch address for a primary resource.
		mapLocationForUpdate(providerAccountVO, tempGeneralInfoVO);
		if(StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddStreet1()) 
				 && StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddState()) 
				&& StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddCity()) && StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddZip()))
		{
			iLocationDao.insert(tempGeneralInfoVO);
		}
		
	}
	}catch(Exception e)
	{
		LOGGER.error("Exception in updating location details of the provider"+e.getMessage());
		throw new BusinessServiceException(e.getMessage());
	}
	return tempGeneralInfoVO;
}

	/**
	 * @param providerAccountVO
	 * @param tempGeneralInfoVO
	 */
	private void mapLocationForUpdate(ProviderAccountVO providerAccountVO,
			GeneralInfoVO tempGeneralInfoVO) {
		tempGeneralInfoVO.setLocnTypeId(PublicAPIConstant.ADDRESS_TYPE_WORK);
		tempGeneralInfoVO.setDispAddStreet1(providerAccountVO.getDispAddStreet1());
		tempGeneralInfoVO.setDispAddStreet2(providerAccountVO.getDispAddStreet2());
		tempGeneralInfoVO.setDispAddState(providerAccountVO.getDispAddState());
		tempGeneralInfoVO.setDispAddCity(providerAccountVO.getDispAddCity());
		tempGeneralInfoVO.setDispAddApt(providerAccountVO.getDispAddApt());
		tempGeneralInfoVO.setDispAddZip(providerAccountVO.getDispAddZip());
	}

    /**@Description Updating resource  details of the provider
     * @param providerAccountVO
     * @param tempGeneralInfoVO
     * @throws BusinessServiceException
     */
    private void updateVendorResource(ProviderAccountVO providerAccountVO,GeneralInfoVO tempGeneralInfoVO)
		throws BusinessServiceException {
	
	try{
		if(providerAccountVO.getOwnerInd() == PublicAPIConstant.INTEGER_ONE)
	{
		tempGeneralInfoVO.setOwnerInd(providerAccountVO.getOwnerInd());
	}
	
	if(providerAccountVO.getDispatchInd() == PublicAPIConstant.INTEGER_ONE)
	{
		tempGeneralInfoVO.setDispatchInd(providerAccountVO.getDispatchInd());
	}
	
	if(providerAccountVO.getAdminInd()== PublicAPIConstant.INTEGER_ONE && tempGeneralInfoVO.getAdminInd() == PublicAPIConstant.INTEGER_ZERO)
	{
		tempGeneralInfoVO.setAdminInd(providerAccountVO.getAdminInd());
	}
	
	if(providerAccountVO.getSproInd() == PublicAPIConstant.INTEGER_ONE && tempGeneralInfoVO.getSproInd() == PublicAPIConstant.INTEGER_ZERO)
	{
		tempGeneralInfoVO.setSproInd(providerAccountVO.getSproInd());
	}
	
	if(providerAccountVO.getManagerInd()== PublicAPIConstant.INTEGER_ONE)
	{
		tempGeneralInfoVO.setManagerInd(providerAccountVO.getManagerInd());
	}
	
	if(providerAccountVO.getOtherInd()== PublicAPIConstant.INTEGER_ONE)
	{
		tempGeneralInfoVO.setOtherInd(providerAccountVO.getOtherInd());
	}

	if(providerAccountVO.getResourceInd() ==PublicAPIConstant.INTEGER_ONE && tempGeneralInfoVO.getResourceInd() ==PublicAPIConstant.INTEGER_ZERO)
	{
		tempGeneralInfoVO.setResourceInd(providerAccountVO.getResourceInd());
	}else if(providerAccountVO.getResourceInd() ==PublicAPIConstant.INTEGER_ZERO && tempGeneralInfoVO.getResourceInd() ==PublicAPIConstant.INTEGER_ONE)
	{
		tempGeneralInfoVO.setResourceInd(providerAccountVO.getResourceInd());
	}
	tempGeneralInfoVO.setMarketPlaceInd(providerAccountVO.getMarketPlaceInd());
	
	if(StringUtils.isNotBlank(providerAccountVO.getHourlyRateString()))
	{
		tempGeneralInfoVO.setHourlyRate(Double.parseDouble(providerAccountVO.getHourlyRateString()));
	}	
	
	tempGeneralInfoVO.setDispAddGeographicRange(providerAccountVO.getDispAddGeographicRange());
	
	if(null!=tempGeneralInfoVO || StringUtils.isNotBlank(tempGeneralInfoVO.getMarketPlaceInd()) || StringUtils.isNotBlank(tempGeneralInfoVO.getDispAddGeographicRange()))
	{
		iVendorResourceDao.update(tempGeneralInfoVO);
	}
	}catch(Exception e)
	{
		LOGGER.error("Exception in updating resource  details of the provider"+e.getMessage());
		throw new BusinessServiceException(e.getMessage());
	  }
    }



    /**Description Updating user resource schedule  details of the provider
     * @param providerAccountVO
     * @param tempGeneralInfoVO
     * @throws BusinessServiceException
     */
    private void updateResourceSchedule(ProviderAccountVO providerAccountVO,GeneralInfoVO tempGeneralInfoVO)
		throws BusinessServiceException {
	try{
	tempGeneralInfoVO.setSun24Ind(providerAccountVO.getSun24Ind());
	tempGeneralInfoVO.setSunNaInd(providerAccountVO.getSunNaInd());
	tempGeneralInfoVO.setSunStart(providerAccountVO.getSunStart());
	tempGeneralInfoVO.setSunEnd(providerAccountVO.getSunEnd());
	
	tempGeneralInfoVO.setMon24Ind(providerAccountVO.getMon24Ind());
	tempGeneralInfoVO.setMonNaInd(providerAccountVO.getMonNaInd());
	tempGeneralInfoVO.setMonStart(providerAccountVO.getMonStart());
	tempGeneralInfoVO.setMonEnd(providerAccountVO.getMonEnd());
	
	tempGeneralInfoVO.setTue24Ind(providerAccountVO.getTue24Ind());
	tempGeneralInfoVO.setTueNaInd(providerAccountVO.getTueNaInd());
	tempGeneralInfoVO.setTueStart(providerAccountVO.getTueStart());
	tempGeneralInfoVO.setTueEnd(providerAccountVO.getTueEnd());
	
	tempGeneralInfoVO.setWed24Ind(providerAccountVO.getWed24Ind());
	tempGeneralInfoVO.setWedNaInd(providerAccountVO.getWedNaInd());
	tempGeneralInfoVO.setWedStart(providerAccountVO.getWedStart());
	tempGeneralInfoVO.setWedEnd(providerAccountVO.getWedEnd());
	
	tempGeneralInfoVO.setThu24Ind(providerAccountVO.getThu24Ind());
	tempGeneralInfoVO.setThuNaInd(providerAccountVO.getThuNaInd());
	tempGeneralInfoVO.setThuStart(providerAccountVO.getThuStart());
	tempGeneralInfoVO.setThuEnd(providerAccountVO.getThuEnd());
	
	tempGeneralInfoVO.setFri24Ind(providerAccountVO.getFri24Ind());
	tempGeneralInfoVO.setFriNaInd(providerAccountVO.getFriNaInd());
	tempGeneralInfoVO.setFriStart(providerAccountVO.getFriStart());
	tempGeneralInfoVO.setFriEnd(providerAccountVO.getFriEnd());
	
	tempGeneralInfoVO.setSat24Ind(providerAccountVO.getSat24Ind());
	tempGeneralInfoVO.setSatNaInd(providerAccountVO.getSatNaInd());
	tempGeneralInfoVO.setSatStart(providerAccountVO.getSatStart());
	tempGeneralInfoVO.setSatEnd(providerAccountVO.getSatEnd());

	if(StringUtils.isNotBlank(tempGeneralInfoVO.getSun24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getSunNaInd()) || (null!=tempGeneralInfoVO.getSunStart() && null!=tempGeneralInfoVO.getSunEnd())
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getMon24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getMonNaInd()) || (null!=tempGeneralInfoVO.getMonStart() && null!=tempGeneralInfoVO.getMonEnd())
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getTue24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getTueNaInd()) || (null!=tempGeneralInfoVO.getTueStart() && null!=tempGeneralInfoVO.getTueEnd()) 
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getWed24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getWedNaInd()) || (null!=tempGeneralInfoVO.getWedStart() && null!=tempGeneralInfoVO.getWedEnd()) 
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getThu24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getThuNaInd()) || (null!=tempGeneralInfoVO.getThuStart() && null!=tempGeneralInfoVO.getThuEnd()) 
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getFri24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getFriNaInd()) || (null!=tempGeneralInfoVO.getFriStart() && null!=tempGeneralInfoVO.getFriEnd()) 
			|| StringUtils.isNotBlank(tempGeneralInfoVO.getSat24Ind()) || StringUtils.isNotBlank(tempGeneralInfoVO.getSatNaInd()) || (null!=tempGeneralInfoVO.getSatStart() && null!=tempGeneralInfoVO.getSatEnd()))
	{	
	iResourceScheduleDao.deleteResourceSchdedule(tempGeneralInfoVO);
	iResourceScheduleDao.insert(tempGeneralInfoVO);
	}
	}catch(Exception e)
	{
		LOGGER.error("Exception in updating user resource schedule  details of the provider"+e.getMessage());
		throw new BusinessServiceException(e.getMessage());
	}
}
  
        /**@Description : Saving Resource Contact Details.
     	 * @param providerVO
     	 * @return
     	 * @throws BusinessServiceException
     	 */
    	private GeneralInfoVO saveContactInformation(ProviderAccountVO providerVO) throws BusinessServiceException{
		GeneralInfoVO generalInfoVO = null;
		GeneralInfoVO tempGeneralInfoVO = new GeneralInfoVO();
		try{
			tempGeneralInfoVO.setFirstName(providerVO.getFirstName());
			tempGeneralInfoVO.setLastName(providerVO.getLastName());
			tempGeneralInfoVO.setMiddleName(providerVO.getMiddleName());
			tempGeneralInfoVO.setSuffix(providerVO.getSuffix());
			tempGeneralInfoVO.setOtherJobTitle(providerVO.getOtherJobTitle());
			tempGeneralInfoVO.setPhoneNumber(providerVO.getBusinessPhone());
			tempGeneralInfoVO.setPhoneNumberExt(providerVO.getBusinessExtn());
			tempGeneralInfoVO.setMobileNumber(providerVO.getMobilePhone());
			tempGeneralInfoVO.setEmail(providerVO.getEmail());
			tempGeneralInfoVO.setAltemail(providerVO.getAltEmail());
			tempGeneralInfoVO.setSmsAddress(providerVO.getSmsAddress());
			tempGeneralInfoVO.setSecondaryContact1(providerVO.getSecondaryContact1());
			generalInfoVO =iContactDao.insertContact(tempGeneralInfoVO);
			
		}catch (Exception e) {
			LOGGER.error("Exception in saving contact details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
    
    
	/**@Description Updating background info of the provider
	 * @param providerAccountVO
	 * @throws BusinessServiceException
	 */
	private void updateBackgroundDetails(ProviderAccountVO providerAccountVO)
			throws BusinessServiceException {
		
	try{	
		if(null!=providerAccountVO.getBackgroundCheckStatus() && StringUtils.isNotBlank(providerAccountVO.getBackgroundVerificationDate()) && StringUtils.isNotBlank(providerAccountVO.getBackgroundRecertificationDate()))
		{
		BackgroundCheckVO backgroundCheckVO=iContactDao.getBackgroundCheckInfoByResourceId(providerAccountVO.getResourceId());
		
		if(null!=backgroundCheckVO && null!=backgroundCheckVO.getBcCheckId())
		{
			Integer bgCheckId =backgroundCheckVO.getBcCheckId();
			
			TMBackgroundCheckVO tMBackgroundCheckVO = new TMBackgroundCheckVO();
			tMBackgroundCheckVO.setBackgroundVerificationDate(providerAccountVO.getBackgroundVerificationDate());
			tMBackgroundCheckVO.setBackgroundRecertificationDate(providerAccountVO.getBackgroundRecertificationDate());
			tMBackgroundCheckVO.setBackgroundStateId(Integer.parseInt(providerAccountVO.getBackgroundCheckStatus()));
			
			if(StringUtils.isNotBlank(providerAccountVO.getRequestDate()))
			{
				tMBackgroundCheckVO.setBackgroundRequestDate(providerAccountVO.getRequestDate());
			}
			else
			{
				tMBackgroundCheckVO.setBackgroundVerificationDate(providerAccountVO.getBackgroundVerificationDate());
			}
			tMBackgroundCheckVO.setBgCheckId(bgCheckId);
		
			if(StringUtils.isNotBlank(tMBackgroundCheckVO.getBackgroundVerificationDate()) && StringUtils.isNotBlank(tMBackgroundCheckVO.getBackgroundRecertificationDate()) && null!=tMBackgroundCheckVO.getBackgroundStateId())
			{
			iContactDao.updateBackgroundDetails(tMBackgroundCheckVO);
			}
			
			if(null!=backgroundCheckVO.getBackgroundCheckStateId())	
			{	
			String previousState =Constants.backgroundCheckMap().get(backgroundCheckVO.getBackgroundCheckStateId());
			
			//Updating background history table
			GeneralInfoVO generalInformation=new GeneralInfoVO();
			generalInformation.setChangedComment(Constants.BACKGROUND_CHECK_STATUS_UPDATE +previousState + " to " + providerAccountVO.getBgStatusString());
			generalInformation.setResourceId(providerAccountVO.getResourceId().toString());   
			iVendorResourceDao.insertBcHistory(generalInformation);
			}
			
			//Updating background activity registry details
			if(!providerAccountVO.getBgStatusString().equals(PublicAPIConstant.BG_STATUS_NOT_STARTED))
			{
				activityRegistryDao.updateResourceActivityStatus(providerAccountVO.getResourceId().toString(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
			}else{
				activityRegistryDao.updateResourceActivityStatus(providerAccountVO.getResourceId().toString(), ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,PublicAPIConstant.INTEGER_ZERO);
			}
				
			if(StringUtils.isNotBlank(providerAccountVO.getBgStatusString()))
			{
				GeneralInfoVO generalInformation=new GeneralInfoVO();
				generalInformation.setResourceId(providerAccountVO.getResourceId().toString());   
				auditResourceBackgroundCheck(Integer.parseInt(providerAccountVO.getResourceId()),providerAccountVO.getBgStatusString(),generalInformation);
			}

			}
		}
		}catch(Exception e)
		{
			LOGGER.error("Exception in updating background info of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
	
	/**@Description Inserting or Updating License and Certification details
	 * @param providerAccountVO
	 * @throws BusinessServiceException
	 */
	private void updateLicenseAndCert(
			ProviderAccountVO providerAccountVO) throws BusinessServiceException {
		
		try{
			
			//Inserting License and Certification details
			if(null!=providerAccountVO.getLicensesList() && !providerAccountVO.getLicensesList().isEmpty())
				{
					saveLicense(providerAccountVO);
				}
			
			//Updating License and Certification details
			if(null!=providerAccountVO.getEditLicensesList() && !providerAccountVO.getEditLicensesList().isEmpty())
				{
					updateLicense(providerAccountVO);
				}
			}catch(Exception e){
				LOGGER.error("Exception in saving/updating credential  details of the provider"+e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
	}
	
	/**@Description Inserting License and Certification details
	 * @param providerAccountVO
	 * @throws Exception
	 */
	public void  saveLicense(ProviderAccountVO providerAccountVO)throws Exception {
        List<TeamCredentialsVO> licensesList=providerAccountVO.getLicensesList();
		if(null!= licensesList && !(licensesList.isEmpty())){
			for(TeamCredentialsVO teamCredentialsVO:licensesList){
				teamCredentialsVO.setResourceId(Integer.parseInt(providerAccountVO.getResourceId())); 
				saveLicenseCredential(teamCredentialsVO);
			}
		}
		updateNoCredIndProvider(providerAccountVO);
	}
	
	
	/**@Description Updating License and Certification details
	 * @param providerAccountVO
	 * @throws Exception
	 */
	public void updateLicense(ProviderAccountVO providerAccountVO)throws Exception {
        int docId=0;
		try {
			List<TeamCredentialsVO> licensesList=providerAccountVO.getEditLicensesList();
			if(null!= licensesList && !(licensesList.isEmpty())){
			   for(TeamCredentialsVO objTeamCredentialsVO:licensesList){
				   
				   
				   	if(null== objTeamCredentialsVO.getIssuerName())
				   	{
				   		objTeamCredentialsVO.setIssuerName(StringUtils.EMPTY);
				   	}
				   	
				   	if(null == objTeamCredentialsVO.getCredentialNumber())
				   	{
				   		objTeamCredentialsVO.setCredentialNumber(StringUtils.EMPTY);
				   	}
				   	
				   	if(null == objTeamCredentialsVO.getCity())
				   	{
				   		objTeamCredentialsVO.setCity(StringUtils.EMPTY);
				   	}
				   	
				   	
				   	if(null == objTeamCredentialsVO.getCounty())
				   	{
				   		objTeamCredentialsVO.setCounty(StringUtils.EMPTY);
				   	}
				   
			        iTeamCredentialsDao.updateCredential(objTeamCredentialsVO);
			        activityRegistryDao.updateResourceActivityStatus(objTeamCredentialsVO.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);

					AuditVO auditVO = null;
					try {
						 auditVO = new AuditVO(objTeamCredentialsVO.getVendorId(), 0, AuditStatesInterface.RESOURCE_CREDENTIAL, AuditStatesInterface.RESOURCE_CREDENTIAL_PENDING_APPROVAL);
						if (docId > 0){
						    auditVO.setDocumentId(docId);
						}
						if (objTeamCredentialsVO.getResourceCredId() > 0) {
						auditVO.setAuditKeyId(objTeamCredentialsVO.getResourceCredId());
						}
		
						if (objTeamCredentialsVO != null)
							auditVO.setResourceId(objTeamCredentialsVO.getResourceId());
		
						auditResourceCredentials(auditVO);
						} catch (Exception e) {
							LOGGER.error("[ProviderResourceBOImpl] - updateLicense() - Audit Exception Occured for audit record:" + auditVO.toString()
						+ StackTraceHelper.getStackTrace(e));
						}
		
					}
			}
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in updating credential  details of the provider"+dse.getMessage());
			throw new BusinessServiceException(dse);
		}catch (Exception dse) {
			LOGGER.error("Exception in updating credential  details of the provider"+dse.getMessage());
			throw new BusinessServiceException(dse);
		}
	}
   
	
	/**
	 * @param providerAccountVO
	 * @throws Exception
	 * @throws BusinessServiceException
	 */
	private void updateNoCredInd(ProviderAccountVO providerAccountVO) throws Exception, BusinessServiceException{
		VendorResource vendorResource =new VendorResource();
	    vendorResource.setResourceId(Integer.parseInt(providerAccountVO.getResourceId()));
	    try{
			if(providerAccountVO.isLicensePresent()){
			   vendorResource.setNoCredInd(false);
			}else{
			   vendorResource.setNoCredInd(true);
			}
		    iVendorResourceDao.updateResourceNoCred(vendorResource);
	    }catch (Exception e) {
	    	LOGGER.error("Exception in saving No Cred Indicator  of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
	
	/**@Description Updating NoCredInd of the provider in vendor resource table.
	 * @param providerAccountVO
	 * @throws BusinessServiceException
	 */
	private void updateNoCredIndProvider(ProviderAccountVO providerAccountVO) throws BusinessServiceException{
		VendorResource vendorResource =new VendorResource();
	    vendorResource.setResourceId(Integer.parseInt(providerAccountVO.getResourceId()));
	    try{
			if(providerAccountVO.isLicensePresent()){
			   vendorResource.setNoCredInd(false);
			   iVendorResourceDao.updateResourceNoCred(vendorResource);
			}
		   
	    }catch (Exception e) {
	    	LOGGER.error("Exception in updating No Cred Indicator  of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
	}
		
	
	/**@Description Updating Language Details of the provider
	 * @param providerAccountVO
	 * @throws BusinessServiceException
	 */
	private void updateLanguages(ProviderAccountVO providerAccountVO) throws BusinessServiceException {
		
	try{	
		if(null!=providerAccountVO.getLanguages())
		{
			 Long resourceId = null;
			 if(StringUtils.isNotBlank(providerAccountVO.getResourceId())){
				 resourceId = Long.parseLong(providerAccountVO.getResourceId());
			 }
			LanguageVO objLanguageVO = new LanguageVO();
			objLanguageVO.setResourceId(resourceId);
			iSkillAssignDao.deleteOldLanguages(objLanguageVO);
			
			java.util.Date today = new java.util.Date();
	        java.sql.Date now = new java.sql.Date(today.getTime());
			for(int i=0; i<providerAccountVO.getLanguages().size(); i++)
			{	
				LanguageVO languageVO = new LanguageVO();
				languageVO.setResourceId(resourceId);
				languageVO.setLanguageId(providerAccountVO.getLanguages().get(i).getLanguageId());
				
		        languageVO.setCreatedDate(now);
		        languageVO.setModifiedDate(now);
				
					iSkillAssignDao.insertLanguage(languageVO);
			}	
		}
	}catch(Exception e)
	{
		LOGGER.error("Caught Exception: in updating Language Details of the provider ",e);
		throw new BusinessServiceException(e.getMessage());
	}
	}

    
	/**@Description : Saving Resource Location Details
	 * @param providerVO
	 * @param contactVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private GeneralInfoVO saveResourceLocation(ProviderAccountVO providerVO, GeneralInfoVO contactVO) throws BusinessServiceException{
		GeneralInfoVO generalInfoVO = null;
		GeneralInfoVO tempGeneralInfoVO = new GeneralInfoVO();
		try{
			tempGeneralInfoVO.setLocnTypeId(PublicAPIConstant.ADDRESS_TYPE_WORK);
			tempGeneralInfoVO.setDispAddStreet1(providerVO.getDispAddStreet1());
			tempGeneralInfoVO.setDispAddStreet2(providerVO.getDispAddStreet2());
			tempGeneralInfoVO.setDispAddCity(providerVO.getDispAddCity());
			tempGeneralInfoVO.setDispAddZip(providerVO.getDispAddZip());
			tempGeneralInfoVO.setDispAddApt(providerVO.getDispAddApt());
			tempGeneralInfoVO.setDispAddState(providerVO.getDispAddState());
			generalInfoVO = iLocationDao.insert(tempGeneralInfoVO);
			generalInfoVO.setContactId(contactVO.getContactId());
		}catch (Exception e) {
			LOGGER.error("Exception in saving location details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
	/**@Description : Saving location details in resource_location table.
	 * @param generalInfoVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private GeneralInfoVO saveResourceLocation(GeneralInfoVO generalInfoVO) throws BusinessServiceException {
		ResourceLocation location =null;
		try{
			if(StringUtils.isNotBlank(generalInfoVO.getResourceId()) && generalInfoVO.getLocationId() > 0 ){
			  location = new ResourceLocation(Integer.parseInt(generalInfoVO.getResourceId()), generalInfoVO.getLocationId());
			  resourceLocationDaoImpl.insert(location);
			}
		}catch (Exception e) {
			LOGGER.error("Exception in saving resource_location details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
    /**@Description : saving resource details .
     * @param providerVO
     * @param contactLocationVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO saveVendorResource(ProviderAccountVO providerVO,GeneralInfoVO contactLocationVO) throws BusinessServiceException {
    	GeneralInfoVO generalInfoVO = null;
		GeneralInfoVO tempGeneralInfoVO = new GeneralInfoVO();
    	try{
    		tempGeneralInfoVO.setVendorId(providerVO.getVendorId());
    		tempGeneralInfoVO.setOwnerInd(providerVO.getOwnerInd());
    		tempGeneralInfoVO.setDispatchInd(providerVO.getDispatchInd());
    		tempGeneralInfoVO.setManagerInd(providerVO.getManagerInd());
    		tempGeneralInfoVO.setResourceInd(providerVO.getResourceInd());
    		tempGeneralInfoVO.setAdminInd(providerVO.getAdminInd());
    		tempGeneralInfoVO.setOtherInd(providerVO.getOtherInd());
    		tempGeneralInfoVO.setMarketPlaceInd(providerVO.getMarketPlaceInd());
    		tempGeneralInfoVO.setSproInd(providerVO.getSproInd());
    		tempGeneralInfoVO.setSsn(providerVO.getSsn());
    		tempGeneralInfoVO.setSsnLast4(providerVO.getSsnLast4());
    		tempGeneralInfoVO.setUserName(providerVO.getUserName());
    		tempGeneralInfoVO.setHourlyRate(providerVO.getHourlyRate());
    		tempGeneralInfoVO.setDispAddGeographicRange(providerVO.getDispAddGeographicRange());
    		tempGeneralInfoVO.setContactId(contactLocationVO.getContactId());
    		tempGeneralInfoVO.setLocationId(contactLocationVO.getLocationId());
    		tempGeneralInfoVO.setSubContractorCrewId(providerVO.getSubContractorCrewId());
    		generalInfoVO = iVendorResourceDao.insertDetails(tempGeneralInfoVO);
    		generalInfoVO.setContactId(contactLocationVO.getContactId());
    		generalInfoVO.setLocationId(contactLocationVO.getLocationId());
    		generalInfoVO.setUserName(providerVO.getUserName());
		}catch (Exception e) {
			LOGGER.error("Exception in saving resource  details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
    /**@Description : Save user profile Details
     * @param providerVO
     * @param generalInfoVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO saveUserProfile(GeneralInfoVO generalInfoVO) throws BusinessServiceException {
    	 if(StringUtils.isNotBlank(generalInfoVO.getUserName())){
    	   UserProfile userProfile = new UserProfile();
	    	try{
				userProfile.setUserName(generalInfoVO.getUserName());
				userProfile.setContactId(generalInfoVO.getContactId());
				userProfile.setPasswordFlag(1);
				userProfile.setAnswerTxt("");
				userProfile.setQuestionId(0);
				userProfile.setRoleId(1);
				iUserProfileDao.insertTeamMemberUserProfile(userProfile);	
	    	  }
		   catch (Exception e) {
    	    LOGGER.error("Exception in saving user profile details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		  }
    	}
		return generalInfoVO;
		
	}
    
    /**@Description : Saving resource schedule Details.
     * @param providerVO
     * @param generalInfoVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO saveResourceSchedule(ProviderAccountVO providerVO,GeneralInfoVO generalInfoVO) throws BusinessServiceException {
    	generalInfoVO =mapScheduleToGeneralInfoVO(providerVO,generalInfoVO);
    	try{
			iResourceScheduleDao.insert(generalInfoVO);
		}catch (Exception e) {
			LOGGER.error("Exception in saving user resource schedule  details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
	private GeneralInfoVO mapScheduleToGeneralInfoVO(ProviderAccountVO providerVO, GeneralInfoVO generalInfoVO) {
		
		generalInfoVO.setSun24Ind(providerVO.getSun24Ind());
		generalInfoVO.setSunNaInd(providerVO.getSunNaInd());
		generalInfoVO.setSunStart(providerVO.getSunStart());
		generalInfoVO.setSunEnd(providerVO.getSunEnd());
		
		generalInfoVO.setMon24Ind(providerVO.getMon24Ind());
		generalInfoVO.setMonNaInd(providerVO.getMonNaInd());
		generalInfoVO.setMonStart(providerVO.getMonStart());
		generalInfoVO.setMonEnd(providerVO.getMonEnd());

		generalInfoVO.setTue24Ind(providerVO.getTue24Ind());
		generalInfoVO.setTueNaInd(providerVO.getTueNaInd());
		generalInfoVO.setTueStart(providerVO.getTueStart());
		generalInfoVO.setTueEnd(providerVO.getTueEnd());
		
		generalInfoVO.setWed24Ind(providerVO.getWed24Ind());
		generalInfoVO.setWedNaInd(providerVO.getWedNaInd());
		generalInfoVO.setWedStart(providerVO.getWedStart());
		generalInfoVO.setWedEnd(providerVO.getWedEnd());
		
		generalInfoVO.setThu24Ind(providerVO.getThu24Ind());
		generalInfoVO.setThuNaInd(providerVO.getThuNaInd());
		generalInfoVO.setThuStart(providerVO.getThuStart());
		generalInfoVO.setThuEnd(providerVO.getThuEnd());
		
		generalInfoVO.setFri24Ind(providerVO.getFri24Ind());
		generalInfoVO.setFriNaInd(providerVO.getFriNaInd());
		generalInfoVO.setFriStart(providerVO.getFriStart());
		generalInfoVO.setFriEnd(providerVO.getFriEnd());
		
		generalInfoVO.setSat24Ind(providerVO.getSat24Ind());
		generalInfoVO.setSatNaInd(providerVO.getSatNaInd());
		generalInfoVO.setSatStart(providerVO.getSatStart());
		generalInfoVO.setSatEnd(providerVO.getSatEnd());
		
		return generalInfoVO;
	}


	/**@Description : Saving Credentails for the resource
	 * @param providerVO
	 * @param generalInfoVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private GeneralInfoVO saveCredentials(ProviderAccountVO providerVO,GeneralInfoVO generalInfoVO) throws BusinessServiceException {
		List<TeamCredentialsVO> licensesList =null;
		try{
			licensesList = providerVO.getLicensesList();
			//Saving credentials information.
			if(null!= licensesList && !licensesList.isEmpty()){
				for(TeamCredentialsVO teamCredentialsVO:licensesList){
					teamCredentialsVO.setResourceId(Integer.parseInt(generalInfoVO.getResourceId())); 
					saveLicenseCredential(teamCredentialsVO);
					providerVO.setLicensePresent(true);
				}
			}
			providerVO.setResourceId(generalInfoVO.getResourceId());
			updateNoCredInd(providerVO);
			
		}catch (Exception e) {
			LOGGER.error("Exception in saving credential  details of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
    /**@Description : Saving Activity Registry for all tabs with 0
     * @param generalInfoVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO saveActivityRegistry(GeneralInfoVO generalInfoVO) throws BusinessServiceException {
		try{
		   // inserting default value in activity registry
		   activityRegistryDao.insertResourceActivityStatus(generalInfoVO.getResourceId());
		}catch (Exception e) {
			LOGGER.error("Exception in saving actvity registry of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
    	
		return generalInfoVO;
	}

    /**@description : Saving Back ground Information.
     * @param providerVO
     * @param generalInfoVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO savingBackGroundInformation(ProviderAccountVO providerVO, GeneralInfoVO generalInfoVO) throws BusinessServiceException {
    	try{
    		Integer backgroundCheckId =insertPlusOneKeyForBackgroundCheck(providerVO, generalInfoVO.getResourceId());
    		if(null!=backgroundCheckId && StringUtils.isNotBlank(providerVO.getBackgroundCheckStatus())){
    			Integer backStatusId = Integer.parseInt(providerVO.getBackgroundCheckStatus());
    			if(null !=backStatusId){
    				generalInfoVO.setBcStateId(backStatusId);
    			}
    			generalInfoVO.setBcCheckId(backgroundCheckId);
    			iContactDao.updateBcCheck(generalInfoVO);
    			//updating back ground history
    			generalInfoVO.setChangedComment(Constants.BACKGROUND_CHECK_STATUS_SET + providerVO.getBgStatusString()+"");
    			iVendorResourceDao.insertBcHistory(generalInfoVO);
    		}
    	}catch (Exception e) {
			LOGGER.error("Exception in saving background info of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
    
    /**@Description : Savingf Languages.
     * @param providerVO
     * @param generalInfoVO
     * @return
     * @throws BusinessServiceException
     */
    private GeneralInfoVO saveLanguages(ProviderAccountVO providerVO,GeneralInfoVO generalInfoVO) throws BusinessServiceException{
    	Long resourceId = null;
    	try{
			if(null!=providerVO.getLanguages()){
				 if(StringUtils.isNotBlank(generalInfoVO.getResourceId())){
					 resourceId = Long.parseLong(generalInfoVO.getResourceId());
				 }
						
				java.util.Date today = new java.util.Date();
		        java.sql.Date now = new java.sql.Date(today.getTime());
				for(int i=0; i<providerVO.getLanguages().size(); i++){	
					LanguageVO languageVO = new LanguageVO();
					languageVO.setResourceId(resourceId);
					languageVO.setLanguageId(providerVO.getLanguages().get(i).getLanguageId());
					languageVO.setCreatedDate(now);
			        languageVO.setModifiedDate(now);
					try {
						iSkillAssignDao.insertLanguage(languageVO);
					} catch (Exception e) {
						LOGGER.error("Caught Exception: in saving Language Details of the provider ",e);
						throw e;
					}
				}	
			}
		}catch (Exception e) {
			LOGGER.error("Exception in saving Languages info of the provider"+e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return generalInfoVO;
	}
	/**
	 * @param userName
	 * @return
	 */
	private boolean validateUserNameLength(String userName) {
		boolean isValidLength = false;
		if(StringUtils.isNotBlank(userName)){
			String userNameTrimmed = userName.trim();
			if(userNameTrimmed.length() <= 100 && userNameTrimmed.length() >= 8){
				isValidLength =true;
			}
		}else{
			//UserName is not present in request,no need to validate.
			isValidLength =true;
		}
		return isValidLength;
	}

	/**
	 * @param stateList
	 * @param state
	 * @return
	 */
	private String validateStateValues(List stateList, String state) {
		Iterator itr = stateList.iterator();
		String returnId =null;
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getType();
			if (state.equals(validValue)) {
				returnId= id;
			}
		}
		return returnId;
	}
	/**
	 * @param stateList
	 * @param state
	 * @return
	 */
	private String validateServiceRadiusValues(List stateList, String state) {
		Iterator itr = stateList.iterator();
		String returnId =null;
		while (itr.hasNext()) {
			LookupVO lookupVO = ((LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getId();
			if (state.equals(validValue)) {
				returnId= id;
			}
		}
		return returnId;
	}
	/**
	 * @param bgStatusList
	 * @param backgroundCheckStatus
	 * @return
	 */
	private String validateLookUp(List bgStatusList,String backgroundCheckStatus) {
		Iterator itr = bgStatusList.iterator();
		while (itr.hasNext()) {
			com.newco.marketplace.dto.vo.LookupVO lookupVO = ((com.newco.marketplace.dto.vo.LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			String id = lookupVO.getType();
			if (backgroundCheckStatus.equals(validValue)) {
				return id;
			}
		}
		return null;
	}
	/**
	 * @param valueList
	 * @param value
	 * @return
	 */
	private Integer validateLookUpValues(List valueList, String value) {
		Iterator itr = valueList.iterator();
		while (itr.hasNext()) {
			com.newco.marketplace.dto.vo.LookupVO lookupVO = ((com.newco.marketplace.dto.vo.LookupVO) itr.next());
			String validValue = lookupVO.getDescr();
			Integer id = lookupVO.getId();
			if (null!=value && value.equals(validValue)) {
				return id;
			}
		}
		return null;
	}
	/**
	 * @param list
	 * @param zip
	 * @return
	 */
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
	/**
	 * @param providerVO
	 * @param resourceID
	 * @return
	 * @throws Exception
	 */
	private Integer insertPlusOneKeyForBackgroundCheck(ProviderAccountVO providerVO, String resourceID) throws Exception{
		if(providerVO.getSsnUnEncrypted() != null && providerVO.getSsnUnEncrypted().trim().length() > 0 && resourceID != null && resourceID.trim().length()>0){
			String plusOne= resourceID.trim()+providerVO.getSsnUnEncrypted().trim();
			GeneralInfoVO backgroundInfo=new GeneralInfoVO();
		  	backgroundInfo.setResourceId(resourceID);
		  	backgroundInfo.setVendorId(providerVO.getVendorId());
			backgroundInfo.setPlusOneKey(CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne));
			if(StringUtils.isNotBlank(providerVO.getBackgroundCheckStatus())){
				Integer backStatusId = Integer.parseInt(providerVO.getBackgroundCheckStatus());
				backgroundInfo.setBackgroundCheckStatusId(backStatusId);
			}
			backgroundInfo.setBackgroundVerificationDate(providerVO.getBackgroundVerificationDate());
			backgroundInfo.setBackgroundReVerificationDate(providerVO.getBackgroundRecertificationDate());
			backgroundInfo.setBackGroundRequestedDate(providerVO.getBackGroundRequestedDate());
			Integer bcCheckId = iContactDao.insertBcCheckDetails(backgroundInfo);
			return bcCheckId;
		}
		return null;
	}
	 /**@Description : Audit Task for resource.
	 * @param resourceId
	 * @param state
	 * @param generalInfoVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public GeneralInfoVO auditVendorResource(int resourceId, String state,GeneralInfoVO generalInfoVO) throws BusinessServiceException {
         int vendorId = 0;
	     try{
	         vendorId = auditDao.getVendorIdByResourceId(resourceId);
	         AuditVO auditVO = new AuditVO(vendorId, resourceId, PublicAPIConstant.RESOURCE, state);
	         auditVO.setAuditKeyId(resourceId);
	         auditVendorResource(auditVO);
	        }catch (AuditException e) {
	        	LOGGER.error("Exception in auditing vendor_resource",e);
	        	throw new BusinessServiceException(e);
	        }catch ( DBException e) {
	        	LOGGER.error("Exception in auditing vendor_resource",e);
	        	throw new BusinessServiceException(e);
	        }
		return generalInfoVO;
	    }
	 
	 /**
	 * @param auditVO
	 * @throws AuditException
	 */
	public void auditVendorResource(AuditVO auditVO) throws AuditException {

	        if (auditVO.getWfState() == null) {
	            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
	        }
	        if (auditVO.getAuditKeyId() == null) {
	            auditVO.setAuditKeyId(auditVO.getResourceId());
	        }
	        for (WorkflowStateVO wfState : getWorkflowStates()) {
	            if (wfState.getWfEntity().equals(PublicAPIConstant.RESOURCE) && wfState.getWfState().equals(auditVO.getWfState())) {
	                auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());
	            }
	        }
	        if (auditVO.getWfState() == null) {
	            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
	        }
	        try {
	            int rows_updated = getAuditDao().updateByResourceId(auditVO);
	            if (rows_updated == 0) {
	                // there was no row for this vendor id
	                auditVO.setWfEntity(PublicAPIConstant.RESOURCE);
	                this.createAudit(PublicAPIConstant.RESOURCE, auditVO);
	            }
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	        try {
	            getAuditDao().updateStatusVendorResource(auditVO);
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	        // update the vendor for auditor review
	        try {
	            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	    } // auditVendorResource

	 /**
	 * @return
	 */
	 public List<WorkflowStateVO> getWorkflowStates() {

	        if (workflowStates == null) {
	            try {
	                workflowStates = getWorkflowDao().getStateTableMappings();
	            } catch (DBException e) {
	                LOGGER.error("[DBException] " + StackTraceHelper.getStackTrace(e));
	                new AuditException("[AuditBusinessBean] Could not cache the work flow states");
	            }
	        }
	        return workflowStates;
	    }// getWorkflowStates()

	 /**
	 * @param wfEntity
	 * @param auditVO
	 * @throws AuditException
	 */
	 public void createAudit(String wfEntity, AuditVO auditVO) throws AuditException {

	        for (WorkflowStateVO wfState : getWorkflowStates()) {        
	            if (wfState.getWfEntity().equals(wfEntity) && wfState.getWfState().equals(auditVO.getWfState())) {
	            	auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());                
	            }
	        }

	        try {        	
	            getAuditDao().insert(auditVO);
	        } catch (DBException dse) {
	            LOGGER.error("[DBException] " + StackTraceHelper.getStackTrace(dse));
	            final String error = messages.getMessage("biz.select.failed");
	            throw new AuditException(error, dse);
	        }

	    }// end method

	 /**@Description : Audit resource for back ground check.
	 * @param resourceId
	 * @param state
	 * @param generalInfoVO
	 * @return
	 * @throws AuditException
	 */
	 public GeneralInfoVO auditResourceBackgroundCheck(int resourceId, String state, GeneralInfoVO generalInfoVO) throws AuditException {

	        AuditVO auditVO = new AuditVO(0, resourceId, PublicAPIConstant.RESOURCE_BACKGROUND_CHECK, state);
	        auditVO.setAuditKeyId(resourceId);
	        try {
	            auditResourceBackgroundCheck(auditVO);
	        } catch (AuditException e) {
	        	LOGGER.error("Caught Exception in auditResourceBackgroundCheck():",e);
	        	throw new AuditException(e.getMessage(), e);
	        }
			return generalInfoVO;
	    }
	 
	 
	 /**
	 * @param auditVO
	 * @throws AuditException
	 */
	 public void auditResourceBackgroundCheck(AuditVO auditVO) throws AuditException {

		    if (auditVO.getWfState() == null) {
	            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
	        }
	        if (auditVO.getAuditKeyId() == null) {
	            auditVO.setAuditKeyId(auditVO.getResourceId());
	        }

	        try{
	            auditVO.setVendorId(getAuditDao().getVendorIdByResourceId(auditVO.getResourceId()));
	        }catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }

	        for (WorkflowStateVO wfState : getWorkflowStates()) {
	            if (wfState.getWfEntity().equals(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK) && wfState.getWfState().equals(auditVO.getWfState())) {
	                auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());
	            }
	        }

	        try{
	            int rows_updated = getAuditDao().updateBackGroundCheckByResourceId(auditVO);
	            if (rows_updated == 0) {
	                // there was no row for this vendor id
	                auditVO.setWfEntity(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK);
	                this.createAudit(PublicAPIConstant.RESOURCE_BACKGROUND_CHECK, auditVO);
	            }
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	      
	    }
	 
	    /**@Description : Updating Activity registry for the tabs updated.
	     * @param providerVO
	     * @param generalInfoVO
	     * @return
	     * @throws BusinessServiceException
	     */
	    public GeneralInfoVO updateActivityRegistry(ProviderAccountVO providerVO,GeneralInfoVO generalInfoVO) throws BusinessServiceException{
			try{
				   //updating activity registry for General Info
				   activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(), ActivityRegistryConstants.RESOURCE_GENERALINFO);
				   //updating activity registry for Market Place
				   activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(), ActivityRegistryConstants.RESOURCE_MARKETPLACE);
				   //update activity registry for credentails tab
				   activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
				   if(StringUtils.isNotBlank(providerVO.getBgStatusString()) && !StringUtils.equals(PublicAPIConstant.BG_STATUS_NOT_STARTED, providerVO.getBgStatusString())){
				     //updating registry for back ground check tab 
				      activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId()+"", ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK);
				   }else{
					   activityRegistryDao.updateResourceActivityStatus(generalInfoVO.getResourceId(),ActivityRegistryConstants.RESOURCE_BACKGROUNDCHECK,PublicAPIConstant.INTEGER_ZERO); 
				   }
				}catch (Exception e) {
					LOGGER.error("Exception in updating activity registry in  createProviderProfile()"+ e.getMessage());
					throw new BusinessServiceException(e.getMessage());
				}
				return generalInfoVO;
		}
	 
	 /**
	 * @param providerAccountVO
	 * @param teamCredentialsVOlist
	 * @return
	 * @throws Exception
	 */
	public ProviderAccountVO validateLicense(ProviderAccountVO providerAccountVO,List<TeamCredentialsVO> teamCredentialsVOlist) throws Exception{
			try{
				List credTypes=lookupDao.getCredentials();
				if(null!=teamCredentialsVOlist){
					for(TeamCredentialsVO teamCredentialsVO:teamCredentialsVOlist){
						if(null!=providerAccountVO.getResourceId()){
							teamCredentialsVO.setResourceId(Integer.parseInt(providerAccountVO.getResourceId()));
						}
						Integer credTypeExist =null;
						if(!errorOccured){
							credTypeExist=validateLookUpValues(credTypes,teamCredentialsVO.getCredType());	
							if(credTypeExist!=null){
								teamCredentialsVO.setTypeId(credTypeExist);
							}
						}

						if(!errorOccured){
							List credCategoryTypes=lookupDao.getCredentialCategory(credTypeExist);

							Integer categoryTypeExist=validateLookUpValues(credCategoryTypes,teamCredentialsVO.getCredCategory());
							if(categoryTypeExist!=null){
								teamCredentialsVO.setCategoryId(categoryTypeExist);
							}
							else{
								Results results = Results.getError(ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM.getMessage(),
										                           ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM.getCode());
								providerAccountVO.setResults(results);
								errorOccured = true;
								return providerAccountVO;
							}
						}
						if(teamCredentialsVO.getResourceCredId() > 0 && !iTeamCredentialsDao.isResourceCredentialIdExist(teamCredentialsVO)){
							Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_ID_PROVIDER.getMessage(), 
									                           ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_ID_PROVIDER.getCode());
							providerAccountVO.setResults(results);
							errorOccured = true;
							return providerAccountVO;
						}
						if(!errorOccured){	
						if(teamCredentialsVO.getTypeId() == 3 && teamCredentialsVO.getCategoryId() == 24){
							String credentialNumber = teamCredentialsVO.getCredentialNumber();
								if(credentialNumber == null || credentialNumber.trim().length()==0){
									Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER.getMessage(),
											                           ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER.getCode());
											
									providerAccountVO.setResults(results);
									errorOccured = true;
									return providerAccountVO;
								}else if(StringUtils.isNotBlank(credentialNumber)){
									if(!StringUtils.isAlphanumeric(credentialNumber)){
										Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER.getMessage(),
												                           ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER.getCode());
												
										providerAccountVO.setResults(results);
										errorOccured = true;
										return providerAccountVO;
									}
								}
									
							}
						}
						
						if(!errorOccured && StringUtils.isNotBlank(teamCredentialsVO.getState())){
							if(null == stateList){
								stateList = lookupDao.getStateCodes();
							 }
							String stateExist=validateStateValues(stateList,teamCredentialsVO.getState());
							if(stateExist!=null){
								teamCredentialsVO.setState(stateExist);
							}else{
								Results results = Results.getError(ResultsCode.INVALID_LICENSE_STATE_FIRM.getMessage(), ResultsCode.INVALID_LICENSE_STATE_FIRM.getCode());
								providerAccountVO.setResults(results);
								errorOccured = true;
								return providerAccountVO;
							}
						}
					
					    if(!errorOccured && null!= teamCredentialsVO.getExpirationDate() && null!= teamCredentialsVO.getIssueDate()){
							Date expirationDate = teamCredentialsVO.getExpirationDate();		
							Date issueDate = teamCredentialsVO.getIssueDate();			
							if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate)){				
								Results results = Results.getError(ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM.getMessage(), ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM.getCode());
								providerAccountVO.setResults(results);
								errorOccured=true;
								return providerAccountVO;
							}
						}

					}
				}

			}catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception occured in validating Licence Information:",e);
				throw new BusinessServiceException(e.getMessage());
			}
			return providerAccountVO;
		}
	 
	 
	 /** @Description Validate Credential Details for Insert/Update
	 * @param providerAccountVO
	 * @param teamCredentialsVOlist
	 * @return
	 * @throws Exception
	 */
	 public ProviderAccountVO validateUpdateLicense(ProviderAccountVO providerAccountVO,List<TeamCredentialsVO> teamCredentialsVOlist) throws BusinessServiceException{
			try{
				List credTypes=lookupDao.getCredentials();
				if(null!=teamCredentialsVOlist){
					for(TeamCredentialsVO teamCredentialsVO:teamCredentialsVOlist){
						
						if(null!=providerAccountVO.getResourceId())
						{
							teamCredentialsVO.setResourceId(Integer.parseInt(providerAccountVO.getResourceId()));
						}
						if(teamCredentialsVO.getResourceCredId() > 0 && !iTeamCredentialsDao.isResourceCredentialIdExist(teamCredentialsVO))
						{
							Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_ID_PROVIDER
									.getMessage(), ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_ID_PROVIDER
									.getCode());
							providerAccountVO.setResults(results);
							errorOccured = true;
							return providerAccountVO;
						}
						
						Integer credTypeExist =null;
						if(!errorOccured && StringUtils.isNotBlank(teamCredentialsVO.getCredType()) && null!=credTypes && !credTypes.isEmpty())
						{
							credTypeExist=validateLookUpValues(credTypes,teamCredentialsVO.getCredType());	

							if(credTypeExist!=null){
								teamCredentialsVO.setTypeId(credTypeExist);
							}
						}

						if(!errorOccured && null!=credTypeExist && StringUtils.isNotBlank(teamCredentialsVO.getCredCategory())){
							List credCategoryTypes=lookupDao.getCredentialCategory(credTypeExist);

							if(null!=credCategoryTypes && !credCategoryTypes.isEmpty())
							{
							Integer categoryTypeExist=validateLookUpValues(credCategoryTypes,teamCredentialsVO.getCredCategory());
								if(null!= categoryTypeExist){
								teamCredentialsVO.setCategoryId(categoryTypeExist);
							}
							else{
								Results results = Results.getError(ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM
										.getMessage(), ResultsCode.MISMATCH_CREDENTIAL_TYPE_CATEGORY_FIRM
										.getCode());
								providerAccountVO.setResults(results);
								errorOccured = true;
								return providerAccountVO;
							}
						}
						}

						if(!errorOccured && teamCredentialsVO.getTypeId() > 0 && teamCredentialsVO.getCategoryId() > 0){	
						if(teamCredentialsVO.getTypeId() == 3 && teamCredentialsVO.getCategoryId() == 24){
							String credentialNumber = teamCredentialsVO.getCredentialNumber();
								if(credentialNumber == null || credentialNumber.trim().length()==0)
								{
									Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER
											.getMessage(), ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER
											.getCode());
									providerAccountVO.setResults(results);
									errorOccured = true;
									return providerAccountVO;
								}else if(StringUtils.isNotBlank(credentialNumber)){
									if(!StringUtils.isAlphanumeric(credentialNumber))
									{
										Results results = Results.getError(ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER
												.getMessage(), ResultsCode.INVALID_LICENSE_RESOURCE_CREDENTIAL_NUMBER_PROVIDER
												.getCode());
										providerAccountVO.setResults(results);
										errorOccured = true;
										return providerAccountVO;
									}
								}
									
							}
						}
						
						if(!errorOccured && StringUtils.isNotBlank(teamCredentialsVO.getState())){
						
							if(null == stateList){
								stateList = lookupDao.getStateCodes();
							 }
							String stateExist=validateStateValues(stateList,teamCredentialsVO.getState());

							if(stateExist!=null){
								teamCredentialsVO.setState(stateExist);
								}
							else{
								Results results = Results.getError(ResultsCode.INVALID_LICENSE_STATE_FIRM
										.getMessage(), ResultsCode.INVALID_LICENSE_STATE_FIRM
										.getCode());
								providerAccountVO.setResults(results);
								errorOccured = true;
								return providerAccountVO;
							}
						}
					
						if(!errorOccured && null!=teamCredentialsVO.getExpirationDate() && null!=teamCredentialsVO.getIssueDate())
						{
							Date expirationDate = teamCredentialsVO.getExpirationDate();		
							Date issueDate = teamCredentialsVO.getIssueDate();			
							if(null!=issueDate && null!=expirationDate && issueDate.after(expirationDate))
							{				
								Results results = Results.getError(ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM
										.getMessage(), ResultsCode.INVALID_CREDENTIAL_ISSUE_DATE_FIRM
										.getCode());
								providerAccountVO.setResults(results);
								errorOccured=true;
								return providerAccountVO;
							}
						}

					}
				}

			}catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception in validating credential values in validateUpdateLicense()",e);
				throw new BusinessServiceException(e.getMessage());
			}
			return providerAccountVO;
		}
		
	 /**@Description Inserting License and Certification details
	 * @param teamCredentialsVO
	 * @throws Exception
	 */
	 public void  saveLicenseCredential(TeamCredentialsVO teamCredentialsVO)throws Exception{
            try {
				TeamCredentialsVO credentialsVO = new TeamCredentialsVO();
				credentialsVO = iTeamCredentialsDao.insert(teamCredentialsVO);
				activityRegistryDao.updateResourceActivityStatus(teamCredentialsVO.getResourceId()+"", ActivityRegistryConstants.RESOURCE_CREDENTIALS);
				if (credentialsVO != null){
					teamCredentialsVO.setResourceCredId(credentialsVO.getResourceCredId());
				}
				AuditVO auditVO = null;
				try {
                     auditVO = new AuditVO(teamCredentialsVO.getVendorId(), 0, AuditStatesInterface.RESOURCE_CREDENTIAL, AuditStatesInterface.RESOURCE_CREDENTIAL_PENDING_APPROVAL);
					 auditVO.setReviewedBy("");
					 auditVO.setDocumentId(null);
                     if (teamCredentialsVO.getResourceCredId() > 0) {
						auditVO.setAuditKeyId(teamCredentialsVO.getResourceCredId());
					 }
                    if (teamCredentialsVO != null){
						auditVO.setResourceId(teamCredentialsVO.getResourceId());
					}
					auditResourceCredentials(auditVO);
					} catch (Exception e) {
						LOGGER.error("[ProviderResourceBOImpl] - saveLicenseCredential() - Audit Exception Occured for audit record:" + auditVO.toString()+ e.getMessage());
						throw new BusinessServiceException(e);
					}
			} catch (DataServiceException dse) {
				LOGGER.error("Exception in saving credential  details of the provider"+dse.getMessage());
				throw new BusinessServiceException(dse);
			}catch (Exception dse) {
				LOGGER.error("Exception in saving credential  details of the provider"+dse.getMessage());
				throw new BusinessServiceException(dse);
			}
	} 
	 /**
	 * @param auditVO
	 * @throws AuditException
	 */
	 public void auditResourceCredentials(AuditVO auditVO) throws AuditException {

	        if (auditVO.getWfState() == null) {
	            throw new AuditException("Null value passed as audit state for method auditDocumentUpdate");
	        }
	        try {
	        	Integer vendorId;
	        	vendorId = getAuditDao().getVendorIdByResourceId(auditVO.getResourceId()); 
	            auditVO.setVendorId(vendorId);
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	        }

	        for (WorkflowStateVO wfState : getWorkflowStates()) {
	            if (wfState.getWfEntity().equals(PublicAPIConstant.RESOURCE_CREDENTIAL) && wfState.getWfState().equals(auditVO.getWfState())) {
	                auditVO.setWfStateId(wfState.getWfStateId());
	                auditVO.setAuditLinkId(wfState.getAuditLinkId());
	            }
	        }
	        // update audit task if present else create one
	        try {
	        	if (auditVO.getAuditKeyId() != null && auditVO.getAuditKeyId().intValue() > 0){
					int rowsUpdated = getAuditDao().updateByResourceId(auditVO);
					if (rowsUpdated == 0){
					  // there was no row for this vendor id
					 auditVO.setWfEntity(PublicAPIConstant.RESOURCE_CREDENTIAL);
				     this.createAudit(PublicAPIConstant.RESOURCE_CREDENTIAL, auditVO);
					}
				}else{
	            	throw new AuditException("auditResourceCredentials - No auditkey Found in auditVO");
	            }
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	    
	        if(auditVO.getAuditKeyId() != null && auditVO.getWfStateId() != null ) {
	        	updateCredentialStatus(auditVO);
	        }
	        // update the vendor for auditor review
	        try {
	            getAuditDao().setAuditPendingStatusForVendorHdr(auditVO.getVendorId());
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
	    }// auditResourceCredentials 
	 /**
		 * @param auditVO
		 * @throws AuditException
		 */
		private void updateCredentialStatus(AuditVO auditVO) throws AuditException{
			try {
	            getAuditDao().updateStatusResourceCredential(auditVO);
	        } catch (DBException e) {
	            LOGGER.error(StackTraceHelper.getStackTrace(e));
	            throw new AuditException(e.getMessage(), e);
	        }
		}
	 
		/**
		 * @Description Method to validate the skills in the AddProviderSkill request 
		 * @param addProviderSkillVO
		 * @return ProviderSkillVO
		 */
		public ProviderSkillVO validateAddSkillProvider(ProviderSkillVO addProviderSkillVO) throws BusinessServiceException {
			errorOccured=false;
			try{
				Integer proId=iSkillAssignDao.loadProviderId(addProviderSkillVO.getProviderId());
				if(null !=proId){
					String providerId=proId.toString();
				
					if(StringUtils.isNotBlank(providerId) && providerId.equals(addProviderSkillVO.getProviderId())){
						validateProviderSkills(addProviderSkillVO);
					}
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_PROVIDER_ID.getMessage(),
						ResultsCode.INVALID_PROVIDER_ID.getCode());
					addProviderSkillVO.setResults(results); 
					errorOccured = true;
				}
			}
			catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception in validating the provider Id :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return addProviderSkillVO;
		}
	  
		/**
		 * @Description Method to validate the root skill name in the request 
		 * @param skillDetailVO
		 * @return SkillDetailVO
		 */
		private SkillDetailVO validateRootSkillName(SkillDetailVO skillDetailVO) throws BusinessServiceException {
			
			try {
				List rootSkillNameList=iLookupDAO.loadRootSkillName();
				if(null !=rootSkillNameList && ! rootSkillNameList.isEmpty()){
					String rootSkillName=validateValues(rootSkillNameList,skillDetailVO.getRootSkillName());
				
					if(StringUtils.isNotBlank(rootSkillName)){
						skillDetailVO.setRootSkillName(rootSkillName);
					}
					else{
						Results results = Results.getError(ResultsCode.INVALID_SKILLS
							.getMessage()+"-:"+skillDetailVO.getRootSkillName(), ResultsCode.INVALID_SKILLS
							.getCode());
						skillDetailVO.setResults(results);
						errorOccured = true;
					}
				}
			} catch (DBException e) {
				errorOccured = true;
				LOGGER.error("Exception in validating the provider root skills :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return skillDetailVO;
		}
        
		/**
		 * @Description Method to validate the provider skill details
		 * @param addProviderSkillVO
		 * @return ProviderSkillVO
		 */
		public ProviderSkillVO validateProviderSkills(ProviderSkillVO providerSkillVO) throws BusinessServiceException {
			errorOccured=false;
			try{
				List<SkillDetailVO> skillDetailList=providerSkillVO.getSkill();
				if(null != skillDetailList && !skillDetailList.isEmpty()){
					for(SkillDetailVO skillDetailVO:skillDetailList){
						
						if(!errorOccured && StringUtils.isNotBlank(skillDetailVO.getRootSkillName())){
							validateRootSkillName(skillDetailVO);
							if(errorOccured && skillDetailVO.getResults() != null){
								providerSkillVO.setResults(skillDetailVO.getResults());
								break;
							}else{
								if(!errorOccured && StringUtils.isNotBlank(skillDetailVO.getSkillName())){
									validateSkillName(skillDetailVO);
									if(errorOccured && skillDetailVO.getResults() != null){
										providerSkillVO.setResults(skillDetailVO.getResults());
										break;
									}
								}
								if(!errorOccured && StringUtils.isNotBlank(skillDetailVO.getSkillCategoryName())){
									validateSkillCategoryName(skillDetailVO);
									if(errorOccured && skillDetailVO.getResults() != null){
										providerSkillVO.setResults(skillDetailVO.getResults());
										break;
									}
								}
							}
						}
						if(!errorOccured && null!=skillDetailVO.getServiceType() && !skillDetailVO.getServiceType().isEmpty()){
							validateServiceType(skillDetailVO);
							if(errorOccured && skillDetailVO.getResults() != null){
								providerSkillVO.setResults(skillDetailVO.getResults());
								break;
							}
						}
					}
				}	
			}
			catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception in validating the provider skills :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return providerSkillVO;
		}
		
		/**
		 * @Description Method to validate the service type details in the request
		 * @param skillDetailVO
		 * @return SkillDetailVO
		 */
		private SkillDetailVO validateServiceType(SkillDetailVO skillDetailVO) throws BusinessServiceException {
			try{
				List serviceTypeList=iLookupDAO.loadServiceTypes(skillDetailVO.getRootSkillName());
				List<String> serviceTypeReturnList= new ArrayList<String>();
				List<String> serviceType=skillDetailVO.getServiceType();
				String serviceTypeId=null;
				
				if(null !=serviceType && ! serviceType.isEmpty() && null!=serviceTypeList && !serviceTypeList.isEmpty()){
					
					for(String service:serviceType){
						serviceTypeId=validateValues(serviceTypeList, service);
						if(StringUtils.isNotBlank(serviceTypeId)){
						serviceTypeReturnList.add(serviceTypeId);
						}
						else{
							Results results = Results.getError(ResultsCode.INVALID_SERVICE_TYPES
									.getMessage()+"-:"+skillDetailVO.getServiceType(), ResultsCode.INVALID_SERVICE_TYPES
									.getCode());
							skillDetailVO.setResults(results);
							errorOccured = true;
						}
					}
					skillDetailVO.setServiceType(serviceTypeReturnList);
				}
			}
			catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception in validating the provider skills service types :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return skillDetailVO;
		}
        
		/**
		 * @Description Method to validate the skill category name in the request
		 * @param skillDetailVO
		 * @return
		 */
		private SkillDetailVO validateSkillCategoryName(SkillDetailVO skillDetailVO) throws BusinessServiceException {
			
			try {
					if(StringUtils.isBlank(skillDetailVO.getSkillName())){
						Results results = Results.getError(ResultsCode.INVALID_SKILLS
								.getMessage()+"-:"+skillDetailVO.getSkillCategoryName(), ResultsCode.INVALID_SKILLS
								.getCode());
						skillDetailVO.setResults(results);
						errorOccured = true;	
					}	
					
				if(!errorOccured){	
					List skillCategoryNameList=iLookupDAO.loadChildSkillName(skillDetailVO.getSkillName());
					
					if(null !=skillCategoryNameList && ! skillCategoryNameList.isEmpty()){
						String skillCategoryName=validateValues(skillCategoryNameList,skillDetailVO.getSkillCategoryName());
					
						if(StringUtils.isNotBlank(skillCategoryName)){
							skillDetailVO.setSkillCategoryName(skillCategoryName);
						}
						else{
							
							Results results = Results.getError(ResultsCode.INVALID_SKILLS
									.getMessage()+"-:"+skillDetailVO.getSkillCategoryName(), ResultsCode.INVALID_SKILLS
									.getCode());
							skillDetailVO.setResults(results);
							errorOccured = true;
						}
					}
					else{
						
						Results results = Results.getError(ResultsCode.INVALID_SKILLS
								.getMessage()+"-:"+skillDetailVO.getSkillCategoryName(), ResultsCode.INVALID_SKILLS
								.getCode());
						skillDetailVO.setResults(results);
						errorOccured = true;
					}
				}
			}
			catch (DBException e) {
				errorOccured = true;
				LOGGER.error("Exception in validating the provider skill category :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return skillDetailVO;
		}
        
		/**
		 * @Description Method to validate the skill name in the request
		 * @param skillDetailVO
		 * @return
		 */
		private SkillDetailVO validateSkillName(SkillDetailVO skillDetailVO) throws BusinessServiceException {
		
			try {
				if(StringUtils.isNotBlank(skillDetailVO.getRootSkillName())){
					List skillNameList=iLookupDAO.loadChildSkillName(skillDetailVO.getRootSkillName());
					
					if(null !=skillNameList && ! skillNameList.isEmpty()){
						String skillName=validateValues(skillNameList,skillDetailVO.getSkillName());
			
						if(StringUtils.isNotBlank(skillName)){
							skillDetailVO.setSkillName(skillName);
						}
						else{
							Results results = Results.getError(ResultsCode.INVALID_SKILLS
									.getMessage()+"-:"+skillDetailVO.getSkillName(), ResultsCode.INVALID_SKILLS
									.getCode());
							skillDetailVO.setResults(results);
							errorOccured = true;
						}
					}
				}
				
			}
			catch (DBException e) {
				errorOccured = true;
				LOGGER.error("Exception in validating the provider skill name :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return skillDetailVO;
		}
        
		/**
		 * @param list
		 * @param descr
		 * @return
		 */
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
		 * @Description Method to insert the corresponding ids of the skills to the DB
		 * @param addProviderSkillVO
		 * @return ProviderSkillVO
		 * @throws Exception
		 */
		public ProviderSkillVO insertProviderSkill(ProviderSkillVO addProviderSkillRequest) throws BusinessServiceException {
			
			ProviderSkillVO addProviderSkillVOResponse=new ProviderSkillVO();
			SkillAssignVO skillAssignVO=new SkillAssignVO();
			Results results = new Results();		
			List<Result> resultList = new ArrayList<Result> ();
			Result result = new Result();
			boolean addSkill =false;
			try{
				SkillAssignVO tempSkillAssignVO = null;
				SkillAssignVO fetchSkillAssignVO = null;
				SkillAssignVO fetchSkillIdVO=null;
				SkillAssignVO fetchService = null;

	            int  resourceSkillid=0;
				List<SkillDetailVO> skillDetailsList=addProviderSkillRequest.getSkill();
				java.util.Date today = new java.util.Date();
		        java.sql.Date now = new java.sql.Date(today.getTime());
		    
				if(StringUtils.isNotBlank(addProviderSkillRequest.getProviderId())){
					skillAssignVO.setResourceId(Long.parseLong(addProviderSkillRequest.getProviderId()));
				}
				//fetching resourceName
			    String resourceName=iSkillAssignDao.getResourceName(skillAssignVO).getResourceName();
			 
			    skillAssignVO.setResourceName(resourceName);
			    skillAssignVO.setCreatedDate(now);
				skillAssignVO.setModifiedDate(now);
				skillAssignVO.setModifiedBy(skillAssignVO.getResourceName());
			    if(null!=skillDetailsList && ! skillDetailsList.isEmpty()){
			    	for(int i=0;i<skillDetailsList.size();i++){   
			    		
			    		// for root skill  Eg: Home Electronics
			    		if(null != skillDetailsList.get(i).getRootSkillName()){	
			    			skillAssignVO.setRootNode(true);	
			    			skillAssignVO.setRootNodeId(0);
			    		}
			    		Integer rootNode=Integer.parseInt(skillDetailsList.get(i).getRootSkillName());
			    		skillAssignVO.setNodeId(rootNode);
			    		fetchSkillAssignVO=iSkillAssignDao.getResourceSkillIdByNode(skillAssignVO);
		    			
		    			if(null!=fetchSkillAssignVO && 0!=fetchSkillAssignVO.getResourceSkillId()){
							resourceSkillid=fetchSkillAssignVO.getResourceSkillId();
		    			}
		    			else{
		    				//inserting to resource_skill table
		    				fetchSkillIdVO=iSkillAssignDao.getRootNodeResourceSkillId(skillAssignVO);
		    				if(null == fetchSkillIdVO){
		    					tempSkillAssignVO = iSkillAssignDao.assign(skillAssignVO);
		    					if(null != tempSkillAssignVO){
		    						resourceSkillid=tempSkillAssignVO.getResourceSkillId();
		    					}
		    				}
		    				addSkill=true;
		    			}	
			    		
			    		// for root skill name Eg: Home Electronics
			    		Integer rootSkillNode=Integer.parseInt(skillDetailsList.get(i).getRootSkillName());
			    		skillAssignVO.setNodeId(rootSkillNode);
			    		skillAssignVO.setRootNode(false);	
		    			skillAssignVO.setRootNodeId(rootSkillNode);
		    			
		    			//fetching the already existing resourceSkillIds in the table
		    			fetchSkillAssignVO=iSkillAssignDao.getResourceSkillIdByNode(skillAssignVO);
		    			
		    			if(null!=fetchSkillAssignVO && 0!=fetchSkillAssignVO.getResourceSkillId()){
							resourceSkillid=fetchSkillAssignVO.getResourceSkillId();
		    			}
		    			else{
		    				//inserting to resource_skill table
		    				tempSkillAssignVO = iSkillAssignDao.assign(skillAssignVO);
				    		if(null != tempSkillAssignVO){
								resourceSkillid=tempSkillAssignVO.getResourceSkillId();
							}
		    				addSkill=true;
		    			}	
						List<String> serviceTypeList=skillDetailsList.get(i).getServiceType();
						if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
							for(String service:serviceTypeList){
								skillAssignVO.setSkillTypeId(Integer.parseInt(service));
								skillAssignVO.setResourceSkillId(resourceSkillid);
								
								fetchService = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
								if(null==fetchService || 0==fetchSkillAssignVO.getResourceSkillId()){
								 iSkillAssignDao.assignServiceTypes(skillAssignVO);
				    			 addSkill=true;
								}	
							}
						}
					
						//for skill name Eg: General Television
					    if(StringUtils.isNotBlank(skillDetailsList.get(i).getSkillName())){
					    	Integer skillNode=Integer.parseInt(skillDetailsList.get(i).getSkillName());
					    	skillAssignVO.setNodeId(skillNode);
					    	skillAssignVO.setRootNode(false);	
					    	skillAssignVO.setRootNodeId(rootSkillNode);
			    		
					    	//fetching the already existing resourceSkillIds in the table
					    	fetchSkillAssignVO=iSkillAssignDao.getResourceSkillIdByNode(skillAssignVO);
		    			
					    	if(null!=fetchSkillAssignVO && 0!=fetchSkillAssignVO.getResourceSkillId()){
					    		resourceSkillid=fetchSkillAssignVO.getResourceSkillId();	
					    	}
					    	else{
					    		//inserting to resource_skill table
					    		tempSkillAssignVO = iSkillAssignDao.assign(skillAssignVO);
					    		if(null != tempSkillAssignVO){
					    			resourceSkillid=tempSkillAssignVO.getResourceSkillId();
					    		}
			    				addSkill=true;
					    	}
		    			
					    	if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
					    		for(String service:serviceTypeList){
					    			skillAssignVO.setSkillTypeId(Integer.parseInt(service));
					    			skillAssignVO.setResourceSkillId(resourceSkillid);
					    			fetchService = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
					    			if(null==fetchService || 0==fetchSkillAssignVO.getResourceSkillId()){
					    				iSkillAssignDao.assignServiceTypes(skillAssignVO);
					    				addSkill=true;
					    			}
					    		}
					    	}
						}
						// for category Name Eg: LCD TV
						
						if((StringUtils.isNotBlank(skillDetailsList.get(i).getSkillCategoryName()))){
							Integer categoryNode=Integer.parseInt(skillDetailsList.get(i).getSkillCategoryName());
				    		skillAssignVO.setNodeId(categoryNode);
				    		skillAssignVO.setRootNode(false);	
			    			skillAssignVO.setRootNodeId(rootSkillNode);
			    			   
			    			//fetching the already existing resourceSkillIds in the table
			    			fetchSkillAssignVO=iSkillAssignDao.getResourceSkillIdByNode(skillAssignVO);
				    			
				    		if(null!=fetchSkillAssignVO && 0!=fetchSkillAssignVO.getResourceSkillId()){
								resourceSkillid=fetchSkillAssignVO.getResourceSkillId();
				    		}
				    		else{
				    			//inserting to resource_skill table
				    			tempSkillAssignVO = iSkillAssignDao.assign(skillAssignVO);
						    	if(null != tempSkillAssignVO){
									resourceSkillid=tempSkillAssignVO.getResourceSkillId();
								}
			    				addSkill=true;
				    		}
							if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
								for(String service:serviceTypeList){
									skillAssignVO.setSkillTypeId(Integer.parseInt(service));
									skillAssignVO.setResourceSkillId(resourceSkillid);
									fetchService = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
									if(null==fetchService || 0==fetchSkillAssignVO.getResourceSkillId()){
									 iSkillAssignDao.assignServiceTypes(skillAssignVO);
					    			 addSkill=true;
									}
								}
							}
						}
						 try {
							 activityRegistryDao.updateResourceActivityStatus(String.valueOf(skillAssignVO.getResourceId()), ActivityRegistryConstants.RESOURCE_SKILLS);
						 }catch (Exception e) {
							 errorOccured = true;
							 LOGGER.error("Exception in updating the activity registry :"+ e.getMessage());
						 }
			    	}	
			    }
			    if(addSkill){
			    	result.setCode(ResultsCode.PROVIDER_SKILL_SUCCESS_ALL.getCode());
			    	result.setMessage(ResultsCode.PROVIDER_SKILL_SUCCESS_ALL.getMessage());
			    	resultList.add(result);
			    	results.setResult(resultList);	
			    }
			    else{
			    	results = Results.getError(ResultsCode.SKILLS_ALREADY_IN_TABLE.getMessage(),
							ResultsCode.SKILLS_ALREADY_IN_TABLE.getCode());
			    }
				addProviderSkillVOResponse.setResults(results);
			}
			catch (DBException e) {
				
				errorOccured = true;
				LOGGER.error("Exception in inserting the provider skills :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return addProviderSkillVOResponse;
		}
		
		/**
		 * @Description Method to validate the remove skills request
		 * @param removeProviderSkillVO
		 * @return ProviderSkillVO
		 */
		public ProviderSkillVO validateRemoveSkillProvider(ProviderSkillVO removeProviderSkillVO) throws BusinessServiceException {
			errorOccured=false;
			List provider=null;
			try{
				Integer proId=iSkillAssignDao.loadProviderId(removeProviderSkillVO.getProviderId());
				if(null !=proId){
					String providerId=proId.toString();
					if(StringUtils.isNotBlank(providerId) && providerId.equals(removeProviderSkillVO.getProviderId())){
						provider=iSkillAssignDao.loadSkillsOfResource(providerId);
						if(null!=provider &&  !provider.isEmpty()){
							validateProviderSkills(removeProviderSkillVO);
						}
						else{
							Results results = Results.getError(ResultsCode.NO_SKILLS_TO_FETCH.getMessage(),
									ResultsCode.NO_SKILLS_TO_FETCH.getCode());
							removeProviderSkillVO.setResults(results); 
							errorOccured = true;
						}
					}
				}
				else{
					Results results = Results.getError(ResultsCode.INVALID_PROVIDER_ID.getMessage(),
						ResultsCode.INVALID_PROVIDER_ID.getCode());
					removeProviderSkillVO.setResults(results); 
					errorOccured = true;
				}
			}
			catch(Exception e){
				errorOccured = true;
				LOGGER.error("Exception in validating the provider Id :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return removeProviderSkillVO;
		}
        
		/**
		 * @Description Method to remove the skills associated with the provider
		 * @param removeProviderSkillRequest
		 * @return ProviderSkillVO
		 */
		public ProviderSkillVO removeProviderSkill(ProviderSkillVO removeProviderSkillRequest) throws BusinessServiceException  {
			errorOccured=false;
			ProviderSkillVO removeProviderSkillVOResponse=new ProviderSkillVO();
			SkillAssignVO skillAssignVO=new SkillAssignVO();
			Results results = new Results();		
			List<Result> resultList = new ArrayList<Result> ();
			Result result = new Result();
			try{
				SkillAssignVO tempSkillAssVO = null;
				List<SkillDetailVO> skillDetailsList=removeProviderSkillRequest.getSkill();
				
				if(StringUtils.isNotBlank(removeProviderSkillRequest.getProviderId())){
					skillAssignVO.setResourceId(Long.parseLong(removeProviderSkillRequest.getProviderId()));
				}
				if(null!=skillDetailsList && ! skillDetailsList.isEmpty()){
				    for(int i=0;i<skillDetailsList.size();i++){

				    	if(StringUtils.isNotBlank(skillDetailsList.get(i).getRootSkillName())
				    			&& StringUtils.isBlank(skillDetailsList.get(i).getSkillName())
				    			&& StringUtils.isBlank(skillDetailsList.get(i).getSkillCategoryName())){	
				    		
				    		// for root skill name Eg: Home Electronics
				    		Integer rootSkillNode=Integer.parseInt(skillDetailsList.get(i).getRootSkillName());
				    		skillAssignVO.setNodeId(rootSkillNode);
				    		List<String> serviceTypeList=skillDetailsList.get(i).getServiceType();
				    		if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
				    			for(String service:serviceTypeList){
				    				skillAssignVO.setSkillTypeId(Integer.parseInt(service));
				    				tempSkillAssVO = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
				    				if(null!=tempSkillAssVO){
				    					tempSkillAssVO.setResourceId(skillAssignVO.getResourceId());
				    					tempSkillAssVO.setNodeId(skillAssignVO.getNodeId());
				    					iSkillAssignDao.deleteOldSkills(tempSkillAssVO);
				    					
				    					List tempList= iSkillAssignDao.getResourceSkillIdsList(tempSkillAssVO);
										if(null==tempList || tempList.isEmpty()){
					    					iSkillAssignDao.deleteOldSkillsNodes(tempSkillAssVO);
										}
				    				}
				    				else{
				    					errorOccured=true;
				    				}
				    				if(StringUtils.isBlank(skillDetailsList.get(i).getSkillName())
				    						&& StringUtils.isBlank(skillDetailsList.get(i).getSkillCategoryName())){
						    			List<Integer> rootServiceTypeIdList=iSkillAssignDao.getRootServiceTypeIdList(skillAssignVO);
						    			if(null !=rootServiceTypeIdList && !rootServiceTypeIdList.isEmpty()){
						    				iSkillAssignDao.deleteRootSkillServiceIdList(rootServiceTypeIdList);
						    				continue;
						    			}
						    			List<Integer> childResourceSkillIdList=iSkillAssignDao.retrieveResourceSkillEntry(skillAssignVO);
						    			if(null !=childResourceSkillIdList && !childResourceSkillIdList.isEmpty()){
						    				SkillAssignVO skillAssVO = new SkillAssignVO();
						    				skillAssVO.setResourceId(Long.parseLong(removeProviderSkillRequest.getProviderId()));
						    				skillAssVO.setNodeId(Integer.parseInt(skillDetailsList.get(i).getSkillName()));
						    				for(Integer resourceSkillId:childResourceSkillIdList){
						    					skillAssVO.setResourceSkillId(resourceSkillId);
						    					iSkillAssignDao.deleteOldSkillsNodes(skillAssVO);
						    				}
						    			}
						    		}//End of if
				    			}
				    		}
				    	}
				    	//for skill name Eg: General Television
				    	if(StringUtils.isNotBlank(skillDetailsList.get(i).getSkillName())
			    			      && StringUtils.isBlank(skillDetailsList.get(i).getSkillCategoryName())){
				    		Integer SkillNode=Integer.parseInt(skillDetailsList.get(i).getSkillName());
				    		skillAssignVO.setNodeId(SkillNode);
				    		List<String> serviceTypeList=skillDetailsList.get(i).getServiceType();
				    		if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
				    			for(String service:serviceTypeList){
				    				skillAssignVO.setSkillTypeId(Integer.parseInt(service));
				    				tempSkillAssVO = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
				    				if(null!=tempSkillAssVO){
				    					tempSkillAssVO.setResourceId(skillAssignVO.getResourceId());
				    					tempSkillAssVO.setNodeId(skillAssignVO.getNodeId());
				    					iSkillAssignDao.deleteOldSkills(tempSkillAssVO);
				    					List tempList= iSkillAssignDao.getResourceSkillIdsList(tempSkillAssVO);
										if(null==tempList || tempList.isEmpty()){
					    					iSkillAssignDao.deleteOldSkillsNodes(tempSkillAssVO);
										}				    				
									}
				    				else{
				    					errorOccured=true;
				    				}
				    				
				    				if(StringUtils.isBlank(skillDetailsList.get(i).getSkillCategoryName())){
		                            	List<Integer> childServiceTypeIdList=iSkillAssignDao.getChildServiceTypeIdList(skillAssignVO);
		                            	if(null !=childServiceTypeIdList && !childServiceTypeIdList.isEmpty()){
		                            		iSkillAssignDao.deleteRootSkillServiceIdList(childServiceTypeIdList);
		                            		continue;
		                            	}
		                            	List<Integer> childResourceSkillIdList=iSkillAssignDao.retrieveResourceSkillEntry(skillAssignVO);
						    			if(null !=childResourceSkillIdList && !childResourceSkillIdList.isEmpty()){
						    				SkillAssignVO skillAssVO = new SkillAssignVO();
						    				skillAssVO.setResourceId(Long.parseLong(removeProviderSkillRequest.getProviderId()));
						    				skillAssVO.setNodeId(Integer.parseInt(skillDetailsList.get(i).getSkillName()));
						    				for(Integer resourceSkillId:childResourceSkillIdList){
						    					skillAssVO.setResourceSkillId(resourceSkillId);
						    					iSkillAssignDao.deleteOldSkillsNodes(skillAssVO);
						    				}
						    			}
		                            } //end of If
				    			}
				    		}
				    	}
				    	// for category Name Eg: LCD TV
				    	
				    	if((StringUtils.isNotBlank(skillDetailsList.get(i).getSkillCategoryName()))){
				    		Integer categoryNode=Integer.parseInt(skillDetailsList.get(i).getSkillCategoryName());
				    		skillAssignVO.setNodeId(categoryNode);
				    		
				    		List<String> serviceTypeList=skillDetailsList.get(i).getServiceType();
				    		if(null !=serviceTypeList && !serviceTypeList.isEmpty()){
				    			for(String service:serviceTypeList){
				    				skillAssignVO.setSkillTypeId(Integer.parseInt(service));
				    				tempSkillAssVO = iSkillAssignDao.fetchResourceSkillId(skillAssignVO);
				    				if(null!=tempSkillAssVO){
				    					tempSkillAssVO.setResourceId(skillAssignVO.getResourceId());
				    					tempSkillAssVO.setNodeId(skillAssignVO.getNodeId());
				    					iSkillAssignDao.deleteOldSkills(tempSkillAssVO);
				    					List tempList= iSkillAssignDao.getResourceSkillIdsList(tempSkillAssVO);
										if(null==tempList || tempList.isEmpty()){
					    					iSkillAssignDao.deleteOldSkillsNodes(tempSkillAssVO);
										}
									}
				    				else{
				    					errorOccured=true;
				    				}
				    			}
				    		}
				    	}
				    }
				    //for root 
				    skillAssignVO.setRootNodeId(Integer.parseInt(skillDetailsList.get(0).getRootSkillName()));
				    List<Integer> resourceSkillList=iSkillAssignDao.fetchResourceSkillEntry(skillAssignVO);
				    if(null!= resourceSkillList){
				    	int len = resourceSkillList.size();
				    	int temp =0;
				    	
				    	//To delete the skills from resource_skill table 		
				    	for(Integer resourceSkillId:resourceSkillList){
				    		SkillAssignVO skillAssignsVO=new SkillAssignVO();
				    		skillAssignsVO.setResourceSkillId(resourceSkillId);
				    		
				    		//To fetch the skills from resource_skill_service_type table based on resource_skill_id
				    		List<Integer> resourceSkillExist=iSkillAssignDao.fetchResourceSkillServiceType(skillAssignsVO);
				    		
				    		if(resourceSkillExist.isEmpty())
				    		{
				    			SkillAssignVO skillAssigVO=new SkillAssignVO();
				    			skillAssigVO.setResourceId(Long.parseLong(removeProviderSkillRequest.getProviderId()));
				    			skillAssigVO.setResourceSkillId(resourceSkillId);
				    			iSkillAssignDao.deleteOtherNodes(skillAssigVO);
				    			temp++;
				    		}
	    				}
				    	
				    	//To delete the root node from resource_skill table
				    	if(temp == len)
				    	{
				    		skillAssignVO.setNodeId(Integer.parseInt(skillDetailsList.get(0).getRootSkillName()));
					    	iSkillAssignDao.deleteRootNodeId(skillAssignVO);
				    	}
				    	
				    }
				}
				updateActivityRegistry(skillAssignVO.getResourceId());
				if(!errorOccured){
					result.setCode(ResultsCode.PROVIDER_SKILL_REMOVE_ALL.getCode());
					result.setMessage(ResultsCode.PROVIDER_SKILL_REMOVE_ALL.getMessage());
					resultList.add(result);
					results.setResult(resultList);	
				}
				else{
					results = Results.getError(ResultsCode.NO_SKILLS_TO_FETCH.getMessage(),
							ResultsCode.NO_SKILLS_TO_FETCH.getCode());
				}
				removeProviderSkillVOResponse.setResults(results);
			}
			catch (DBException e) {
				
				errorOccured = true;
				LOGGER.error("Exception in removing the provider skills :"+ e.getMessage());
				throw new BusinessServiceException(e.getMessage());
			}
			return removeProviderSkillVOResponse;
		}

	/**
	 * @Description: Validating Provider Resource ID
	 * @throws BusinessServiceException
	 */

	public boolean validateResourceID(int resourceId)
			throws BusinessServiceException {
		boolean result = false;
		Integer resultId = null;
		try {
			resultId = iVendorHdrDao.getVendorIdForResource(resourceId);
			if (resultId != null && resultId > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			LOGGER.error("Exception in validating provider Resource Id"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;

	}
	
	/**
	 * @Description: Validating Provider Firm ID
	 * @throws BusinessServiceException
	 */

	public boolean validateFirmID(int vendorId) throws BusinessServiceException {
		Integer resultId = null;
		boolean result = false;
		try {
			resultId = iVendorHdrDao.getVendorWFStateId(vendorId);
			if (resultId != null && resultId > 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (DBException e) {
			LOGGER.error("Exception in validating provider Firm Id"
					+ e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return result;
	}
		
	private void updateActivityRegistry(long resourceId)  {
		List provider=null;
		try {
			provider=iSkillAssignDao.loadSkillsOfResource(String.valueOf(resourceId));
			if(provider.isEmpty()){
					activityRegistryDao.updateResourceActivityStatus(String.valueOf(resourceId), ActivityRegistryConstants.RESOURCE_SKILLS,PublicAPIConstant.INTEGER_ZERO);	
			}
		}
		catch (Exception e) {
			errorOccured = true;
			LOGGER.error("Exception in updating the activity registry :"+ e.getMessage());
			e.printStackTrace();
		}
			
	}

	/**@Description : Sending Email to new provider email/alt email.
	 * @param providerVO
	 */
	private void sendMailToNewUser(ProviderAccountVO providerVO) {
	  	try{
	  		Integer resourceId = Integer.parseInt(providerVO.getResourceId());
	  		String provUserName = iVendorResourceDao.getUserNameAdmin(providerVO.getVendorId());
		
	  		activityRegistryBO.sendEmailForTeamMemberRegistration(resourceId, provUserName);
	  		
	  	}catch (Exception e) {
			LOGGER.error("Exception in sending mails to new provider and Provider Firm and IGNORING"+ e);
			e.printStackTrace();
		}
	}

	public boolean isErrorOccured() {
		return errorOccured;
	}

	public void setErrorOccured(boolean errorOccured) {
		this.errorOccured = errorOccured;
	}

	public IVendorResourceDao getiVendorResourceDao() {
		return iVendorResourceDao;
	}

	public void setiVendorResourceDao(IVendorResourceDao iVendorResourceDao) {
		this.iVendorResourceDao = iVendorResourceDao;
	}
    public ILookupDAO getiLookupDAO() {
		return iLookupDAO;
	}
    public void setiLookupDAO(ILookupDAO iLookupDAO) {
		this.iLookupDAO = iLookupDAO;
	}
    public LookupDao getLookupDao() {
		return lookupDao;
	}
    public void setLookupDao(LookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
    public IZipDao getZipDao() {
		return zipDao;
	}
    public void setZipDao(IZipDao zipDao) {
		this.zipDao = zipDao;
	}
    public IContactDao getiContactDao() {
		return iContactDao;
	}
    public void setiContactDao(IContactDao iContactDao) {
		this.iContactDao = iContactDao;
	}
    public IResourceScheduleDao getiResourceScheduleDao() {
		return iResourceScheduleDao;
	}
    public void setiResourceScheduleDao(IResourceScheduleDao iResourceScheduleDao) {
		this.iResourceScheduleDao = iResourceScheduleDao;
	}
    public ILocationDao getiLocationDao() {
		return iLocationDao;
	}
    public void setiLocationDao(ILocationDao iLocationDao) {
		this.iLocationDao = iLocationDao;
	}
    public IActivityRegistryDao getActivityRegistryDao() {
		return activityRegistryDao;
	}
    public void setActivityRegistryDao(IActivityRegistryDao activityRegistryDao) {
		this.activityRegistryDao = activityRegistryDao;
	}
    public IUserProfileDao getiUserProfileDao() {
		return iUserProfileDao;
	}
    public void setiUserProfileDao(IUserProfileDao iUserProfileDao) {
		this.iUserProfileDao = iUserProfileDao;
	}

    public IAuditDao getAuditDao() {
		return auditDao;
	}

    public void setAuditDao(IAuditDao auditDao) {
		this.auditDao = auditDao;
	}

    public IWorkflowDao getWorkflowDao() {
		return workflowDao;
	}

    public void setWorkflowDao(IWorkflowDao workflowDao) {
		this.workflowDao = workflowDao;
	}

    public static Logger getLogger() {
		return LOGGER;
	}


	public ITeamCredentialsDao getiTeamCredentialsDao() {
		return iTeamCredentialsDao;
	}


	public void setiTeamCredentialsDao(ITeamCredentialsDao iTeamCredentialsDao) {
		this.iTeamCredentialsDao = iTeamCredentialsDao;
	}


	public ISkillAssignDao getiSkillAssignDao() {
		return iSkillAssignDao;
	}


	public void setiSkillAssignDao(ISkillAssignDao iSkillAssignDao) {
		this.iSkillAssignDao = iSkillAssignDao;
	}


	public IMarketPlaceDao getiMarketPlaceDao() {
		return iMarketPlaceDao;
	}


	public void setiMarketPlaceDao(IMarketPlaceDao iMarketPlaceDao) {
		this.iMarketPlaceDao = iMarketPlaceDao;
	}


	public ResourceLocationDaoImpl getResourceLocationDaoImpl() {
		return resourceLocationDaoImpl;
	}


	public void setResourceLocationDaoImpl(
			ResourceLocationDaoImpl resourceLocationDaoImpl) {
		this.resourceLocationDaoImpl = resourceLocationDaoImpl;
	}
	
	public IVendorHdrDao getiVendorHdrDao() {
		return iVendorHdrDao;
	}

	public void setiVendorHdrDao(IVendorHdrDao iVendorHdrDao) {
		this.iVendorHdrDao = iVendorHdrDao;
	}

	/**
	 * @return the activityRegistryBO
	 */
	public IActivityRegistryBO getActivityRegistryBO() {
		return activityRegistryBO;
	}

	/**
	 * @param activityRegistryBO the activityRegistryBO to set
	 */
	public void setActivityRegistryBO(IActivityRegistryBO activityRegistryBO) {
		this.activityRegistryBO = activityRegistryBO;
	}


}

package com.newco.marketplace.persistence.daoImpl.leadprofile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Errors;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.daoImpl.leadsmanagement.LeadManagementDaoFactory;
import com.newco.marketplace.persistence.iDao.leadprofile.ILeadDao;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadPartnerStatusResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.SimpleRestClient;
import com.servicelive.routingrulesengine.vo.RuleErrorDisplayVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class LMSProviderProfileDaoImpl extends ABaseImplDao implements ILeadDao{
	
	private String lmsApiBaseUrl;
	private String lmsApiKey;
	private LeadManagementDaoFactory leadManagementDaoFactory;
	
	private int SLLEADDAO = 4;
	
	private static final Logger logger = Logger.getLogger(LMSProviderProfileDaoImpl.class);
	
	/*
	 * call the LMS APIs using rest-client to create the Provider's Lead Profile in LMS system
	 */
	public LeadProfileCreationResponseVO createLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws Exception {
		
		LeadProfileCreationResponseVO leadProfileCreationResponseVO = new LeadProfileCreationResponseVO();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		
		try{
			//calling createNewPartner API to create Provider's Lead Profile in LMS system
			//leadProfileCreationResponseVO = createNewPartner(leadProfileCreationRequestVO);
			
			if(!StringUtils.isBlank(leadProfileCreationRequestVO.getUrgencyServices())){
				List<String> urgencyService = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getLookUpvalues(leadProfileCreationRequestVO.getUrgencyServices(), 1);
				//leadProfileCreationRequestVO.setCustomUrgencyOfService(createArray(urgencyService,true));
				leadProfileCreationRequestVO.setCustomUrgencyOfService(urgencyService);
			}
			//Services
			if(!StringUtils.isBlank(leadProfileCreationRequestVO.getSkill())){
				List<String> services = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getLookUpvalues(leadProfileCreationRequestVO.getSkill(), 2);
				//leadProfileCreationRequestVO.setCustomSkill(createArray(services,true));
				leadProfileCreationRequestVO.setCustomSkill(services);
			}
			List<String> availableDays = new ArrayList<String>();
			if(null != leadProfileCreationRequestVO.getMondayInd() 
					&& 1 == leadProfileCreationRequestVO.getMondayInd()){
				availableDays.add(PublicAPIConstant.MONDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getTuesndayInd() 
					&& 1 == leadProfileCreationRequestVO.getTuesndayInd()){
				availableDays.add(PublicAPIConstant.TUESDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getWednesdayInd() 
					&& 1 == leadProfileCreationRequestVO.getWednesdayInd()){
				availableDays.add(PublicAPIConstant.WEDNESDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getThursdayInd() 
					&& 1 == leadProfileCreationRequestVO.getThursdayInd()){
				availableDays.add(PublicAPIConstant.THURSDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getFridayInd() 
					&& 1 == leadProfileCreationRequestVO.getFridayInd()){
				availableDays.add(PublicAPIConstant.FRIDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getSaturdayInd()
					&& 1 == leadProfileCreationRequestVO.getSaturdayInd()){
				availableDays.add(PublicAPIConstant.SATURDAY_STR);
			}
			if(null != leadProfileCreationRequestVO.getSundayInd()
					&& 1 == leadProfileCreationRequestVO.getSundayInd()){
				availableDays.add(PublicAPIConstant.SUNDAY_STR);
			}
			if(availableDays.size() > 0 ){
				//leadProfileCreationRequestVO.setDay(createArray(availableDays,true));
				leadProfileCreationRequestVO.setDay(availableDays);
			}
			
			
			leadProfileCreationResponseVO = createPartnerAndFilterSetCustom(leadProfileCreationRequestVO,true);
			
			if(null== leadProfileCreationResponseVO){
				List <String> errorList =  new ArrayList<String>();
				leadProfileCreationResponseVO = new LeadProfileCreationResponseVO();
				errorList.add(ResultsCode.INCOMPLETE_SL_PROFILE.getMessage());
				Errors errors = new Errors();
				errors.setError(errorList);
				leadProfileCreationResponseVO.setErrors(errors);
				leadProfileCreationResponseVO.setResult(ResultsCode.INCOMPLETE_SL_PROFILE.getMessage());
				leadProfileCreationResponseVO.setStatus(ResultsCode.INCOMPLETE_SL_PROFILE.getCode());
			}else if(null != leadProfileCreationResponseVO && null != leadProfileCreationResponseVO.getErrors()){
				// Errors				
				List<String> errorList = leadProfileCreationResponseVO.getErrors().getError();
				StringBuffer stringbuff = new StringBuffer();
				
				// Save the error in the table
				for(String error : errorList){
					stringbuff.append(error);
					stringbuff.append(" ");
				}
				
				if(null!=stringbuff){
					getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
						updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
						stringbuff.toString());	
					
					// Just to update the status. I know at this point we will not have partner Id
					getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updatePartnerId(null, leadProfileCreationRequestVO.getProviderFirmId(),
						PublicAPIConstant.LMS_PARTNER_STATUS_NOT_CREAETED);
				}
				// Set error in the response class
				leadProfileCreationResponseVO.setErrors(leadProfileCreationResponseVO.getErrors());
				leadProfileCreationResponseVO.setResult(ResultsCode.LEAD_REGISTRATION_FAILED.getMessage());
				leadProfileCreationResponseVO.setStatus(ResultsCode.LEAD_REGISTRATION_FAILED.getCode());
			}else if(null != leadProfileCreationResponseVO && null == leadProfileCreationResponseVO.getErrors() &&   
					PublicAPIConstant.PARTNER_CREATE_SUCCESS.equals(leadProfileCreationResponseVO.getStatus())){
				// No errors
				String partnerId = leadProfileCreationResponseVO.getPartnerId();
				logger.info("Partner ID is::"+partnerId);
				
				//update the partner_id from response in db
				getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updatePartnerId(partnerId, leadProfileCreationRequestVO.getProviderFirmId(),
					PublicAPIConstant.LMS_PARTNER_STATUS_CREAETED);
				
				// Once the partner status is updated, update the same in SL DB as well.
				getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
					updateLMSStatus(leadProfileCreationRequestVO.getProviderFirmId(),
					PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.CREATE_PARTNER_STATUS_DESC);
				leadProfileCreationResponseVO.setPartnerId(partnerId);
				leadProfileCreationResponseVO.setResult(ResultsCode.LEAD_REGISTRATION_SUCCESS.getMessage());
				leadProfileCreationResponseVO.setStatus(ResultsCode.LEAD_REGISTRATION_SUCCESS.getCode());
			}
		}catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			leadProfileCreationResponseVO.setResults(results);
			e.printStackTrace();
		}  
		return leadProfileCreationResponseVO;
	}

	
	
	
	
	public LeadProfileBillingResponseVO createLeadProfileBilling(LeadProfileBillingRequestVO leadProfileBillingRequestVO) throws Exception {

		LeadProfileBillingResponseVO leadProfileBillingResponseVO = new LeadProfileBillingResponseVO();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		try{
			//call createCIMProfileAndCharge to create Provider's Lead Profile billing account			
			leadProfileBillingResponseVO = createCIMProfileAndCharge(leadProfileBillingRequestVO);
			
			
			String vendorId  = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getVendorIdFromPartnerId(leadProfileBillingRequestVO.getPartnerId());
			
			if(null== leadProfileBillingResponseVO){
				logger.info("createLeadProfileBilling : leadProfileBillingResponseVO is NULL ");
				List <String> errorList =  new ArrayList<String>();
				leadProfileBillingResponseVO = new LeadProfileBillingResponseVO();
				errorList.add(ResultsCode.LEAD_BILLING_FAILURE.getMessage());
				Errors errors = new Errors();
				errors.setError(errorList);
				leadProfileBillingResponseVO.setErrors(errors);
				leadProfileBillingResponseVO.setErrors(errors);
				leadProfileBillingResponseVO.setStatus(ResultsCode.LEAD_BILLING_FAILURE.getCode());
				
			}else if(null != leadProfileBillingResponseVO && null != leadProfileBillingResponseVO.getErrors()){
				logger.info("createLeadProfileBilling : leadProfileBillingResponseVO has errors ");
				if(leadProfileBillingResponseVO.getErrors().getError().size() > 0 ){
					List<String> errorList = leadProfileBillingResponseVO.getErrors().getError();
					StringBuffer stringbuff = new StringBuffer();
					// Save the error in the table
					for(String error : errorList){
						stringbuff.append(error);
						stringbuff.append(" ");
					}
					logger.info("createLeadProfileBilling : errors " + stringbuff);
					//If error occured update the error received.
					if(null!=stringbuff){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(vendorId, 
							stringbuff.toString());	
						
					}
					leadProfileBillingResponseVO.setErrors(leadProfileBillingResponseVO.getErrors());
					leadProfileBillingResponseVO.setResult(ResultsCode.LEAD_BILLING_FAILURE.getMessage());
					leadProfileBillingResponseVO.setStatus(ResultsCode.LEAD_BILLING_FAILURE.getCode());
				}else{	
					logger.info("createLeadProfileBilling : No Error list returned ");
				}
				
			}else if(null != leadProfileBillingResponseVO && null == leadProfileBillingResponseVO.getErrors()){   
				logger.info("createLeadProfileBilling : No ERRORS & Success ");

				if(null != leadProfileBillingResponseVO && PublicAPIConstant.API_RESULT_SUCCESS.equalsIgnoreCase(leadProfileBillingResponseVO.getStatus())){
					LeadProfileCreationRequestVO leadProfileCreationRequestVO = new LeadProfileCreationRequestVO();
					leadProfileCreationRequestVO.setBillingAccInd(PublicAPIConstant.LMS_BILLING_ACCOUNT_IND_SET);
					leadProfileCreationRequestVO.setWfStateId(PublicAPIConstant.LEADS_PROFILE_ACCOUNT_CREATED);
					leadProfileCreationRequestVO.setPartnerId(leadProfileBillingRequestVO.getPartnerId());
					getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateLeadProfile(leadProfileCreationRequestVO);
					
					//call setPartnerStatus API to update status_reason = 12;
					LeadPartnerStatusResponseVO partnerStatusResponseVO = 
						setPartnerStatus(leadProfileBillingRequestVO.getPartnerId(), PublicAPIConstant.STATUS_INACTIVE, PublicAPIConstant.CIM_PROFILE_SUCCESS_DESC);
					
					if(null!= partnerStatusResponseVO && null==partnerStatusResponseVO.getErrors()){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSStatus(vendorId,
							PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.CIM_PROFILE_SUCCESS_DESC);
					}else{		
						// ERROR
						List<String> errorList = partnerStatusResponseVO.getErrors().getError();
						StringBuffer stringbuff = new StringBuffer();
						
						// Save the error in the table
						for(String error : errorList){
							stringbuff.append(error);
							stringbuff.append(" ");
						}
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(vendorId, stringbuff.toString());
					}
					
				}else{
					
					//call setPartnerStatus API to update status_reason = 4;
					LeadPartnerStatusResponseVO partnerStatusResponseVO = setPartnerStatus(leadProfileBillingRequestVO.getPartnerId(), PublicAPIConstant.STATUS_INACTIVE, PublicAPIConstant.CIM_PROFILE_FAILURE_DESC);
					
					if(null!= partnerStatusResponseVO && null==partnerStatusResponseVO.getErrors()){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSStatus(vendorId,
							PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.CIM_PROFILE_FAILURE_DESC);
					}else{		
						// ERROR
						List<String> errorList = partnerStatusResponseVO.getErrors().getError();
						StringBuffer stringbuff = new StringBuffer();
						
						// Save the error in the table
						for(String error : errorList){
							stringbuff.append(error);
							stringbuff.append(" ");
						}
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(vendorId, stringbuff.toString());
					}
				}
			}
		}catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			leadProfileBillingResponseVO.setResults(results);
			e.printStackTrace();
		} 
		return leadProfileBillingResponseVO;
	}
	
	
	
	/**
	 * Insert the filter sets to LMS
	 */
	/*
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,
			Map<String, List<String>> projectLists) throws DataServiceException {
		LeadInsertFilterResponseVO leadInsertFilterResponse = new LeadInsertFilterResponseVO();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		
		try{		
			// There are projects available
			if(null!=projectLists){
				
				boolean compFilterCreated = true;
				boolean exclFilterCreated = true;
				Integer competitiveFilterId = null;
				Integer exclusiveFilterId = null;
				String partnerId = leadProfileCreationRequestVO.getPartnerId();
				
				List<String> compProjects = projectLists.get(PublicAPIConstant.COMP_FILTER_LIST);
				List<String> exclProjects = projectLists.get(PublicAPIConstant.EXCL_FILTER_LIST);
				
				if(null!=compProjects && compProjects.size()>0){
					LeadInsertFilterResponseVO leadInsertFilterResponseVO = new LeadInsertFilterResponseVO();
					leadInsertFilterResponseVO = insertFilterSet(leadProfileCreationRequestVO,
							compProjects,PublicAPIConstant.INSERT_COMP_FILTER_SET_API);
					
					//if no response
					if(null == leadInsertFilterResponseVO){
						compFilterCreated = false;
					}//if error
					else if(null != leadInsertFilterResponseVO && null != leadInsertFilterResponseVO.getErrors()){
						compFilterCreated = false;
						List<String> errorList = leadInsertFilterResponseVO.getErrors().getError();
						StringBuffer stringbuff = new StringBuffer();
						for(String error : errorList){
							stringbuff.append(error);
							stringbuff.append(" ");
						}
						if(null != stringbuff){
							getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
								updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
								stringbuff.toString());	
						}
					}
					//if success
					else if(null != leadInsertFilterResponseVO && null == leadInsertFilterResponseVO.getErrors() &&
								PublicAPIConstant.API_RESULT_SUCCESS.equals(leadInsertFilterResponseVO.getStatus())){
						//set the Comp filterset_id from response
						competitiveFilterId = leadInsertFilterResponseVO.getFilterSetID();
					}						
				}
				if(null!=exclProjects && exclProjects.size()>0){
					LeadInsertFilterResponseVO leadInsertFilterResponseVO = new LeadInsertFilterResponseVO();
					leadInsertFilterResponseVO = insertFilterSet(leadProfileCreationRequestVO,
							exclProjects,PublicAPIConstant.INSERT_EXCL_FILTER_SET_API);
					
					//if no response
					if(null == leadInsertFilterResponseVO){
						exclFilterCreated = false;
					}//if error
					else if(null != leadInsertFilterResponseVO && null != leadInsertFilterResponseVO.getErrors()){
						exclFilterCreated = false;
						List<String> errorList = leadInsertFilterResponseVO.getErrors().getError();
						StringBuffer stringbuff = new StringBuffer();
						for(String error : errorList){
							stringbuff.append(error);
							stringbuff.append(" ");
						}
						if(null != stringbuff){
							getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
								updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
								stringbuff.toString());	
						}
					}
					//if success
					else if(null != leadInsertFilterResponseVO && null == leadInsertFilterResponseVO.getErrors() &&
								PublicAPIConstant.API_RESULT_SUCCESS.equals(leadInsertFilterResponseVO.getStatus())){
						//set the Exclusive filterset_id from response
						exclusiveFilterId = leadInsertFilterResponseVO.getFilterSetID();
					}	
				}
				
				if(compFilterCreated && exclFilterCreated){
					
					//update Comp filterset_id in db
					if(null != competitiveFilterId){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateFilterSetId(competitiveFilterId, partnerId, PublicAPIConstant.INSERT_COMP_FILTER_SET_API,
								PublicAPIConstant.LMS_PARTNER_STATUS_FILTERSET);
					}
					//update Exclusive filterset_id in db
					if(null != exclusiveFilterId){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateFilterSetId(exclusiveFilterId, partnerId, PublicAPIConstant.INSERT_EXCL_FILTER_SET_API,
								PublicAPIConstant.LMS_PARTNER_STATUS_FILTERSET);
					}
					
					//call setPartnerStatus API to update status_reason = 1; 
					LeadPartnerStatusResponseVO partnerStatusResponseVO = setPartnerStatus(partnerId, 
							PublicAPIConstant.STATUS_INACTIVE, PublicAPIConstant.INSERT_FILTERSET_STATUS_DESC);
					
					//if success update LMSStatus in DB
					if(null !=  partnerStatusResponseVO && null == partnerStatusResponseVO.getErrors()){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateLMSStatus(leadProfileCreationRequestVO.getProviderFirmId(),
								PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.INSERT_FILTERSET_STATUS_DESC);
					}
					//else update LMS error in DB
					else{
						List<String> errorList = partnerStatusResponseVO.getErrors().getError();
						StringBuffer stringbuff = new StringBuffer();
						
						for(String error : errorList){
							stringbuff.append(error);
							stringbuff.append(" ");
						}
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), stringbuff.toString());
					}
					leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_SUCCESS.getMessage());
					leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_SUCCESS.getCode());
				}else{
					leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
					leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_FAILURE.getCode());
				}
				
			}
		}catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			leadInsertFilterResponse.setResults(results);
			e.printStackTrace();
		}  
		return leadInsertFilterResponse;
		
	}
	*/
	public LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,
			Map<String, List<String>> projectLists) throws DataServiceException {
		LeadInsertFilterResponseVO leadInsertFilterResponse = new LeadInsertFilterResponseVO();
		LeadProfileCreationResponseVO leadProfileCreationResponseVO = new LeadProfileCreationResponseVO();
		Results results = new Results();
		ErrorResult errorResult = new ErrorResult();
		
		try{		
			// There are projects available
			if(null!=projectLists){
				
				boolean compFilterCreated = true;
				boolean exclFilterCreated = true;
				Integer competitiveFilterId = null;
				Integer exclusiveFilterId = null;
				String partnerId = leadProfileCreationRequestVO.getPartnerId();
				
				List<String> compProjects = projectLists.get(PublicAPIConstant.COMP_FILTER_LIST);
				List<String> exclProjects = projectLists.get(PublicAPIConstant.EXCL_FILTER_LIST);
				
				List<String> combinedProjects = new ArrayList<String>();
				if(null != compProjects && compProjects.size() > 0 ){
					combinedProjects.addAll(compProjects);
				}
				if(null != exclProjects && exclProjects.size() > 0 ){
					combinedProjects.addAll(exclProjects);
				}
				//TO DO: Urgency of service
				
				if(!StringUtils.isBlank(leadProfileCreationRequestVO.getUrgencyServices())){
					List<String> urgencyService = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getLookUpvalues(leadProfileCreationRequestVO.getUrgencyServices(), 1);
					//leadProfileCreationRequestVO.setCustomUrgencyOfService(createArray(urgencyService,true));
					leadProfileCreationRequestVO.setCustomUrgencyOfService(urgencyService);
				}
				
				//Services
				if(!StringUtils.isBlank(leadProfileCreationRequestVO.getSkill())){	
					List<String> services = getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).getLookUpvalues(leadProfileCreationRequestVO.getSkill(), 2);
					//leadProfileCreationRequestVO.setCustomSkill(createArray(services,true));
					leadProfileCreationRequestVO.setCustomSkill(services);
				}
				//Projects 
				if(combinedProjects.size()>0){
					//leadProfileCreationRequestVO.setCustomProject(createArray(combinedProjects,false));
					leadProfileCreationRequestVO.setCustomProject(combinedProjects);
				}
				
				//Days
				List<String> availableDays = new ArrayList<String>();
				if(null != leadProfileCreationRequestVO.getMondayInd() 
						&& 1 == leadProfileCreationRequestVO.getMondayInd()){
					availableDays.add(PublicAPIConstant.MONDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getTuesndayInd() 
						&& 1 == leadProfileCreationRequestVO.getTuesndayInd()){
					availableDays.add(PublicAPIConstant.TUESDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getWednesdayInd() 
						&& 1 == leadProfileCreationRequestVO.getWednesdayInd()){
					availableDays.add(PublicAPIConstant.WEDNESDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getThursdayInd() 
						&& 1 == leadProfileCreationRequestVO.getThursdayInd()){
					availableDays.add(PublicAPIConstant.THURSDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getFridayInd() 
						&& 1 == leadProfileCreationRequestVO.getFridayInd()){
					availableDays.add(PublicAPIConstant.FRIDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getSaturdayInd()
						&& 1 == leadProfileCreationRequestVO.getSaturdayInd()){
					availableDays.add(PublicAPIConstant.SATURDAY_STR);
				}
				if(null != leadProfileCreationRequestVO.getSundayInd()
						&& 1 == leadProfileCreationRequestVO.getSundayInd()){
					availableDays.add(PublicAPIConstant.SUNDAY_STR);
				}
				if(availableDays.size() > 0 ){
					//leadProfileCreationRequestVO.setDay(createArray(availableDays,true));
					leadProfileCreationRequestVO.setDay(availableDays);
				}
				
				leadProfileCreationResponseVO = createPartnerAndFilterSetCustom(leadProfileCreationRequestVO,false);
				
				
				if(null== leadProfileCreationResponseVO){
					
					List <String> errorList =  new ArrayList<String>();
					//errorList.add(ResultsCode.INCOMPLETE_SL_PROFILE.getMessage());
					errorList.add(ResultsCode.INCOMPLETE_FILTER_PROFILE.getMessage());
					Errors errors = new Errors();
					errors.setError(errorList);
					leadInsertFilterResponse.setErrors(errors);
					leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
					leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_FAILURE.getCode());
				}else if(null != leadProfileCreationResponseVO && null != leadProfileCreationResponseVO.getErrors()){
					
					// Errors				
					List<String> errorList = leadProfileCreationResponseVO.getErrors().getError();
					StringBuffer stringbuff = new StringBuffer();
					
					// Save the error in the table
					for(String error : errorList){
						stringbuff.append(error);
						stringbuff.append(" ");
					}
					
					if(null!=stringbuff){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
							stringbuff.toString());	
						
						// Just to update the status. I know at this point we will not have partner Id
						//getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updatePartnerId(null, leadProfileCreationRequestVO.getProviderFirmId(),
						//	PublicAPIConstant.LMS_PARTNER_STATUS_NOT_CREAETED);
					}
					// Set error in the response class
					leadInsertFilterResponse.setErrors(leadProfileCreationResponseVO.getErrors());
					leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
					leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_FAILURE.getCode());
				}else if(null != leadProfileCreationResponseVO && null == leadProfileCreationResponseVO.getErrors() &&   
						PublicAPIConstant.SUCCESS.equalsIgnoreCase(leadProfileCreationResponseVO.getStatus())){
					
					StringBuffer compStringbuffError = new StringBuffer();
					StringBuffer exclusiveStringbuffError = new StringBuffer();
					
					
					String filterSetCreationFailedMsg  = "";
					if(null != leadProfileCreationResponseVO.getCompetitiveFilter()){
						
						//getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(),stringbuff.toString());
						if(!StringUtils.isBlank(leadProfileCreationResponseVO.getCompetitiveFilter().getStatus()) &&
								leadProfileCreationResponseVO.getCompetitiveFilter().getStatus().equalsIgnoreCase(PublicAPIConstant.SUCCESS)){
							
							if(null != leadProfileCreationResponseVO.getCompetitiveFilter().getFilterSetID()){
								
								competitiveFilterId = leadProfileCreationResponseVO.getCompetitiveFilter().getFilterSetID();
								compFilterCreated = true;
							}
						}else{
							
							compFilterCreated = false;
							competitiveFilterId = null;
							if(compProjects.size() > 0 ){
								
								//filterSetCreationFailedMsg  = "Competitive FilterSet Creation Failed.";
								
								if(null != leadProfileCreationResponseVO.getCompetitiveFilter().getErrors()){
									if(null != leadProfileCreationResponseVO.getCompetitiveFilter().getErrors().getError()){
										List<String> errorList = leadProfileCreationResponseVO.getCompetitiveFilter().getErrors().getError();
										// Save the error in the table
										for(String error : errorList){
											compStringbuffError.append(error);
											compStringbuffError.append(" ");
										}
									}
									
								}
							}
						}
					}
					if(null != leadProfileCreationResponseVO.getExclusiveFilter()){
						
						//getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(),stringbuff.toString());
						if(!StringUtils.isBlank(leadProfileCreationResponseVO.getExclusiveFilter().getStatus()) &&
								leadProfileCreationResponseVO.getExclusiveFilter().getStatus().equalsIgnoreCase(PublicAPIConstant.SUCCESS)){
							
							if(null != leadProfileCreationResponseVO.getExclusiveFilter().getFilterSetID()){
								exclusiveFilterId = leadProfileCreationResponseVO.getExclusiveFilter().getFilterSetID();
								exclFilterCreated = true;
							}
						}else{
							exclFilterCreated = false;
							exclusiveFilterId = null;
							if(exclProjects.size() > 0 ){
								//filterSetCreationFailedMsg  = filterSetCreationFailedMsg +  "Exclucive FilterSet Creation Failed.";
								if(null != leadProfileCreationResponseVO.getExclusiveFilter().getErrors()){
									
									if(null != leadProfileCreationResponseVO.getExclusiveFilter().getErrors().getError()){
										List<String> errorList = leadProfileCreationResponseVO.getExclusiveFilter().getErrors().getError();
										// Save the error in the table
										for(String error : errorList){
											exclusiveStringbuffError.append(error);
											exclusiveStringbuffError.append(" ");
										}
									}
									
								}
							}
						}
					}
					
					/*
					if(!StringUtils.isBlank(filterSetCreationFailedMsg)){
						logger.info("LOOP 10 : " + filterSetCreationFailedMsg);
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
									filterSetCreationFailedMsg);	
					}*/
					if(null!=exclusiveStringbuffError || null != compStringbuffError){
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
									exclusiveStringbuffError.toString()+compStringbuffError.toString() );	

					}
					
					List <String> errorList =  new ArrayList<String>();
					
					if(compFilterCreated == false && exclFilterCreated == false){
						if(compFilterCreated == false && compProjects.size() > 0) {
							errorList.add(ResultsCode.FILTER_CREATION_COMP_FAILURE.getMessage());
						}
						if(exclFilterCreated == true && exclProjects.size() > 0){
							errorList.add(ResultsCode.FILTER_CREATION_EXEC_FAILURE.getMessage());
						}
						leadInsertFilterResponse.setErrors(leadProfileCreationResponseVO.getErrors());
						leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_FAILURE.getMessage());
						leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_FAILURE.getCode());
						
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
						updateLMSError(leadProfileCreationRequestVO.getProviderFirmId(), 
								ResultsCode.FILTER_CREATION_FAILURE.getMessage());	

					}	
					
					/*
					if(compFilterCreated == false && exclFilterCreated == false){
						Errors errors = new Errors();
						errors.setError(errorList);
						leadInsertFilterResponse.setErrors(errors);
						
					}
					*/
					
					if(((compFilterCreated == true && compProjects.size() > 0) || 
							exclFilterCreated == true && exclProjects.size() > 0) && (null != competitiveFilterId || null != exclusiveFilterId)){
						
						leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_SUCCESS.getMessage());
						leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_SUCCESS.getCode());
						
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateLMSStatus(leadProfileCreationRequestVO.getProviderFirmId(),
							PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.INSERT_FILTERSET_STATUS_DESC);
					}
					
				}
				
				//Update Competitive filterset in the SL DB
				if(compFilterCreated){
					//update Comp filterset_id in db
					if(null != competitiveFilterId){
						logger.info("Competitive Filter Set Created = " + competitiveFilterId);
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateFilterSetId(competitiveFilterId, partnerId, PublicAPIConstant.INSERT_COMP_FILTER_SET_API,
								PublicAPIConstant.LMS_PARTNER_STATUS_FILTERSET);
					}
						
				}
				//Update Exclusive filterset in the SL DB
				if(exclFilterCreated){
					//update Exclusive filterset_id in db
					if(null != exclusiveFilterId){
						logger.info("Exclusive Filter Set Created = " + exclusiveFilterId);
						getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
							updateFilterSetId(exclusiveFilterId, partnerId, PublicAPIConstant.INSERT_EXCL_FILTER_SET_API,
								PublicAPIConstant.LMS_PARTNER_STATUS_FILTERSET);
					}
				
				}
				if(compFilterCreated || exclFilterCreated){
					leadInsertFilterResponse.setResult(ResultsCode.FILTER_CREATION_SUCCESS.getMessage());
					leadInsertFilterResponse.setStatus(ResultsCode.FILTER_CREATION_SUCCESS.getCode());
				}

			}
		}catch (Exception e) {
			errorResult.setMessage(e.getMessage());
			results.addError(errorResult);
			leadInsertFilterResponse.setResults(results);
			e.printStackTrace();
		}  
		return leadInsertFilterResponse;
		
	}
	
	/**
	 * 
	 * @param leadProfileCreationRequestVO
	 * @return
	 * @throws MalformedURLException
	 */
	private LeadProfileCreationResponseVO createPartnerAndFilterSetCustom(LeadProfileCreationRequestVO leadProfileCreationRequestVO,boolean partnerCreation) throws MalformedURLException{
		LeadProfileCreationResponseVO leadProfileCreationResponseVO = new LeadProfileCreationResponseVO();
		
		if (null != leadProfileCreationRequestVO){
			
			// Check mandatory fields for LMS partner creation
			boolean mandatoryFieldsMissing = checkMandatoryFieldsForCustom(leadProfileCreationRequestVO,partnerCreation);
			
			if(mandatoryFieldsMissing){
				return null;
			}
			SimpleRestClient simpleRestClient = new SimpleRestClient(lmsApiBaseUrl,null,null,false);
			// Create the input map
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(PublicAPIConstant.KEY,lmsApiKey);
			urlParams.put(PublicAPIConstant.API_ACTION,PublicAPIConstant.CREATE_PARTNER_FILTERSET_CUSTOM_API);
			
			urlParams.put(PublicAPIConstant.API_LOGIN,leadProfileCreationRequestVO.getProviderFirmId());
			
			urlParams.put(PublicAPIConstant.API_PASSWORD,leadProfileCreationRequestVO.getLmsPassword());
			
			urlParams.put(PublicAPIConstant.COMPANY_NAME,leadProfileCreationRequestVO.getCompanyName());
			
			urlParams.put(PublicAPIConstant.FIRST_NAME,leadProfileCreationRequestVO.getFirstName());
			
			urlParams.put(PublicAPIConstant.LAST_NAME,leadProfileCreationRequestVO.getLastName());
			
			String address = leadProfileCreationRequestVO.getAddress();
			if(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(leadProfileCreationRequestVO.getStreet2())){
				address = address + leadProfileCreationRequestVO.getStreet2();
			}
			else if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getStreet2())){
				address = leadProfileCreationRequestVO.getStreet2();
			}
			
			urlParams.put(PublicAPIConstant.ADDRESS,address);
			
			urlParams.put(PublicAPIConstant.CITY,leadProfileCreationRequestVO.getCity());
			
			urlParams.put(PublicAPIConstant.STATE,leadProfileCreationRequestVO.getState());
			
			urlParams.put(PublicAPIConstant.LEAD_ZIP,leadProfileCreationRequestVO.getZip());	
			
			urlParams.put(PublicAPIConstant.PHONE,leadProfileCreationRequestVO.getLeadPhoneNo());
			//urlParams.put(PublicAPIConstant.CUSTOM_API_EMAIL,leadProfileCreationRequestVO.getContactEmail());
			
			urlParams.put(PublicAPIConstant.CUSTOM_API_EMAIL,leadProfileCreationRequestVO.getLeadEmailId());
			urlParams.put(PublicAPIConstant.STATUS,PublicAPIConstant.STATUS_INACTIVE);
			if(partnerCreation){
				urlParams.put(PublicAPIConstant.STATUS_REASON,PublicAPIConstant.CREATE_PARTNER_STATUS_DESC);
			}else{
				urlParams.put(PublicAPIConstant.STATUS_REASON,PublicAPIConstant.INSERT_FILTERSET_STATUS_DESC);
			}
			if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getComments())){
				
				urlParams.put(PublicAPIConstant.CUSTOM_COMMENTS,leadProfileCreationRequestVO.getComments());
			}
			urlParams.put(PublicAPIConstant.ACCEPT_MANUALLY_REVIEWED_LEADS,PublicAPIConstant.LMS_ACCEPT_MANUAL_LEADS); // Hardcoded as Yes
			if(leadProfileCreationRequestVO.getIsLicensingRequired() == PublicAPIConstant.CUSTOM_IND_VALUE_TRUE_INT){
				urlParams.put(PublicAPIConstant.LICENSING_IND,PublicAPIConstant.CUSTOM_IND_VALUE_TRUE);
				if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getLicensingStates())){
					//Not supported in the custom API	
					
					urlParams.put(PublicAPIConstant.CUSTOM_FILTER_STATE,leadProfileCreationRequestVO.getLicensingStates());
				}
			}else{
				urlParams.put(PublicAPIConstant.LICENSING_IND,PublicAPIConstant.CUSTOM_IND_VALUE_FALSE);
			}

			if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getIsMultipleLocation()) && leadProfileCreationRequestVO.getIsMultipleLocation().equalsIgnoreCase("y")){
				urlParams.put(PublicAPIConstant.MULTIPLE_AREAS_IND,PublicAPIConstant.CUSTOM_IND_VALUE_TRUE);
			}else{
				urlParams.put(PublicAPIConstant.MULTIPLE_AREAS_IND,PublicAPIConstant.CUSTOM_IND_VALUE_FALSE);
			}
			if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getLeadSmsNo())){
				urlParams.put(PublicAPIConstant.CUSTOM_TEXT_MESSAGE_PREF,PublicAPIConstant.CUSTOM_IND_VALUE_TRUE);
				
				urlParams.put(PublicAPIConstant.CUSTOM_CELL_PHONE,leadProfileCreationRequestVO.getLeadSmsNo());
			}
			if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getCoverageInMiles())){
				// Radius is in integer - Strip off the OR less from "XX OR less" miles			
				String[] splitStr = leadProfileCreationRequestVO.getCoverageInMiles().split("\\s+");
				
				urlParams.put(PublicAPIConstant.CUSTOM_DISTANCE,splitStr[0]);
			}
			if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getLocationType())){
				
				urlParams.put(PublicAPIConstant.CUSTOM_LOCATION_TYPE,leadProfileCreationRequestVO.getLocationType());
			}
			
			if(null !=leadProfileCreationRequestVO.getLeadPackageID() && leadProfileCreationRequestVO.getLeadPackageID() == PublicAPIConstant.LEAD_CUSTOM_PACKAGE_ID_INT){
				urlParams.put(PublicAPIConstant.CUSTOM_PACKAGE_DESC,leadProfileCreationRequestVO.getLeadPackageDesc());
			
				urlParams.put(PublicAPIConstant.DAILY_LIMIT,leadProfileCreationRequestVO.getLeadDailyLimit());
				
				urlParams.put(PublicAPIConstant.MONTHLY_LIMIT,leadProfileCreationRequestVO.getLeadMonthlyLimit());
				
				urlParams.put(PublicAPIConstant.MAXIMUM_MONTHLY_SPEND,leadProfileCreationRequestVO.getMonthlyBudget().toString());
			}else{
				urlParams.put(PublicAPIConstant.CUSTOM_PACKAGE_DESC,leadProfileCreationRequestVO.getLeadPackageDesc());
				
			}
			//Days
			int count = 0;
			String daysStr = "";
			if(null != leadProfileCreationRequestVO.getDay() && leadProfileCreationRequestVO.getDay().size()>0){
				for(String s : leadProfileCreationRequestVO.getDay()){
					daysStr = "";
					daysStr = PublicAPIConstant.DAY_DESC_STR + count + "]";
		
					if(count == 0){
						count = count + 1;
					}else{
						count = count + 1;
					}
					urlParams.put(daysStr,s);
				}
			}
			// urgency
			count = 0;
			String urgencyStr = "";
			if(null != leadProfileCreationRequestVO.getCustomUrgencyOfService() && leadProfileCreationRequestVO.getCustomUrgencyOfService().size()>0){
				for(String s : leadProfileCreationRequestVO.getCustomUrgencyOfService()){
					urgencyStr = "";
					urgencyStr = PublicAPIConstant.CUSTOM_URGENCY_OF_SERVICE_STR + count + "]";
	
					if(count == 0){
						count = count + 1;
					}else{
						count = count + 1;
					}
					urlParams.put(urgencyStr,s);
				}
			}
			//skill
			count = 0;
			String skillStr = "";
			if(null != leadProfileCreationRequestVO.getCustomSkill() && leadProfileCreationRequestVO.getCustomSkill().size()>0){
				for(String s : leadProfileCreationRequestVO.getCustomSkill()){
					skillStr = "";
					skillStr = PublicAPIConstant.CUSTOM_SERVICES_STR + count + "]";

					if(count == 0){
						count = count + 1;
					}else{
						count = count + 1;
					}
					urlParams.put(skillStr,s);
				}
			}
			count = 0;
			//StringBuffer projectStrBuff = new StringBuffer();
			String projectStr = "";
			if(!partnerCreation){
				//Create filterset
				urlParams.put(PublicAPIConstant.CUSTOM_PARTNER_ID,leadProfileCreationRequestVO.getPartnerId());

				// Project
				if(null != leadProfileCreationRequestVO.getCustomProject() && leadProfileCreationRequestVO.getCustomProject().size()>0){
					/*
					for(String s : leadProfileCreationRequestVO.getCustomProject()){
						if(count == 0){
							count = count + 1;
							projectStrBuff = projectStrBuff.append(s);
						}else{
							projectStrBuff = projectStrBuff.append("&").append(PublicAPIConstant.PROJECT_DESC + s);
						}
					}
					logger.info("Project : " + projectStrBuff.toString());
					urlParams.put(PublicAPIConstant.PROJECT_DESC,projectStrBuff.toString());
					*/
					for(String s : leadProfileCreationRequestVO.getCustomProject()){
						projectStr = "";
						//projectStr = PublicAPIConstant.PROJECT_DESC + count + "]";
						projectStr = PublicAPIConstant.PROJECT_DESC;

						if(count == 0){
							count = count + 1;
						}else{
							count = count + 1;
						}
						String key = projectStr+"="+s;

						urlParams.put(key,"PROJECTDESC");
					}
					
				}
				
			}
		
			String response = simpleRestClient.get(urlParams);
			leadProfileCreationResponseVO = (LeadProfileCreationResponseVO)deserializeResponse(response,LeadProfileCreationResponseVO.class);
		}
		return leadProfileCreationResponseVO;
		
	}
	private boolean checkMandatoryFieldsForCustom(LeadProfileCreationRequestVO leadProfileCreationRequestVO,boolean partnerCreation){
		boolean missing = false;
		if(StringUtils.isBlank(leadProfileCreationRequestVO.getProviderFirmId())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLmsPassword())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getCompanyName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getFirstName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLastName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getAddress())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getCity())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getState())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getZip())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadPhoneNo())
				//|| StringUtils.isBlank(leadProfileCreationRequestVO.getContactEmail())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadEmailId())
				){
			missing = true;
		}
		if(partnerCreation == false){
			if(StringUtils.isBlank(leadProfileCreationRequestVO.getPartnerId())
					//Need to check for projects 		
					|| (null == leadProfileCreationRequestVO.getCustomProject() || leadProfileCreationRequestVO.getCustomProject().size() == 0)
					//Package
					|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadPackageDesc())
					//orders
					|| StringUtils.isBlank(leadProfileCreationRequestVO.getLocationType())
					//services
					|| (null == leadProfileCreationRequestVO.getCustomSkill() || leadProfileCreationRequestVO.getCustomSkill().size() == 0)
					//urgency of service
					|| (null == leadProfileCreationRequestVO.getCustomUrgencyOfService() || leadProfileCreationRequestVO.getCustomUrgencyOfService().size() == 0)
					//distance
					|| StringUtils.isBlank(leadProfileCreationRequestVO.getCoverageInMiles())
					//Day
					|| (null == leadProfileCreationRequestVO.getDay() || leadProfileCreationRequestVO.getDay().size() == 0)
					){
				missing = true;
			}
		}
		return missing;
		
	}

	/**
	 * 
	 * @param leadProfileCreationRequestVO
	 * @return
	 * @throws MalformedURLException
	 */
	private LeadProfileCreationResponseVO createNewPartner(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws MalformedURLException{
		LeadProfileCreationResponseVO leadProfileCreationResponseVO = new LeadProfileCreationResponseVO();
				
		if (null != leadProfileCreationRequestVO){
			
			// Check mandatory fields for LMS partner creation
			boolean mandatoryFieldsMissing = checkMandatoryFields(leadProfileCreationRequestVO);
			
			if(mandatoryFieldsMissing){
				return null;
			}
			
			SimpleRestClient simpleRestClient = new SimpleRestClient(lmsApiBaseUrl,null,null,false);
			// Create the input map
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(PublicAPIConstant.KEY,lmsApiKey);
			urlParams.put(PublicAPIConstant.API_ACTION,PublicAPIConstant.CREATE_NEW_PARTNER_API);
			urlParams.put(PublicAPIConstant.API_LOGIN,leadProfileCreationRequestVO.getProviderFirmId());
			urlParams.put(PublicAPIConstant.API_PASSWORD,leadProfileCreationRequestVO.getLmsPassword());
			urlParams.put(PublicAPIConstant.COMPANY_NAME,leadProfileCreationRequestVO.getCompanyName());
			urlParams.put(PublicAPIConstant.FIRST_NAME,leadProfileCreationRequestVO.getFirstName());
			urlParams.put(PublicAPIConstant.LAST_NAME,leadProfileCreationRequestVO.getLastName());
			
			String address = leadProfileCreationRequestVO.getAddress();
			if(StringUtils.isNotBlank(address) && StringUtils.isNotBlank(leadProfileCreationRequestVO.getStreet2())){
				address = address + leadProfileCreationRequestVO.getStreet2();
			}
			else if(StringUtils.isNotBlank(leadProfileCreationRequestVO.getStreet2())){
				address = leadProfileCreationRequestVO.getStreet2();
			}
			
			urlParams.put(PublicAPIConstant.ADDRESS,address);
			urlParams.put(PublicAPIConstant.CITY,leadProfileCreationRequestVO.getCity());
			urlParams.put(PublicAPIConstant.STATE,leadProfileCreationRequestVO.getState());
			
			//Setting hardcoded value of United States in PublicAPIConstant for USA for testing only
			urlParams.put(PublicAPIConstant.COUNTRY,PublicAPIConstant.COUNTRY_CODE);
			urlParams.put(PublicAPIConstant.LEAD_ZIP,leadProfileCreationRequestVO.getZip());			
			urlParams.put(PublicAPIConstant.PHONE,leadProfileCreationRequestVO.getLeadPhoneNo());
			urlParams.put(PublicAPIConstant.CONTACT_EMAIL,leadProfileCreationRequestVO.getContactEmail());
			urlParams.put(PublicAPIConstant.LEAD_EMAIL,leadProfileCreationRequestVO.getLeadEmailId());
			
			//what should be the actual value to be set in delivery option while calling LMS API. Currently set as 1 if SMS & 4 if email
			if(null!=leadProfileCreationRequestVO.getLeadSmsNo()){
				urlParams.put(PublicAPIConstant.DELIVERY_OPTION,PublicAPIConstant.DELIVERY_OPTION_SMS);
			}else{
				urlParams.put(PublicAPIConstant.DELIVERY_OPTION,PublicAPIConstant.DELIVERY_OPTION_EMAIL);
			}
			if(null!=leadProfileCreationRequestVO.getSecPartnerLabel())	{
				urlParams.put(PublicAPIConstant.SECONDARY_PARTNER_LABEL,leadProfileCreationRequestVO.getSecPartnerLabel().toString());
			}
			String response = simpleRestClient.get(urlParams);
			leadProfileCreationResponseVO = (LeadProfileCreationResponseVO)deserializeResponse(response,LeadProfileCreationResponseVO.class);
		}
		return leadProfileCreationResponseVO;
	}
	
	
	/**
	 * Create the Array in the required format from the list
	 * @param data
	 * @param Type
	 * 		  Available types {Project[],Services[],Urgency_Of_Service[],Day[]}
	 * @return
	 */
	/*
	private List<String> createArray(List<String> data, boolean countRequired){	
		List<String> stringArray = new ArrayList<String>();
		for(int count = 0; count < data.size();count++){
			StringBuffer array = new StringBuffer();
			if(countRequired){
				String countString = Integer.toString(count);
				if(count>0){
					array = array.append(countString);
				}	
			}
			if(count>0 && countRequired){
				array = array.append("]=").append(data.get(count));
			}else if(count>0 && countRequired == false){
				array = array.append("[]=").append(data.get(count));
			}else{
				array = array.append(data.get(count));
			}
			logger.debug("String is :::"+array);
			stringArray.add(array.toString());
		}
		return stringArray;
	}	
	*/
	private List<String> createArray(List<String> data, boolean countRequired){	
		List<String> stringArray = new ArrayList<String>();
		for(int count = 0; count < data.size();count++){
			StringBuffer array = new StringBuffer();
			if(countRequired){
				String countString = Integer.toString(count);
				if(count>0){
					array = array.append(countString);
				}	
			}
			if(count>0 && countRequired){
				array = array.append("]=").append(data.get(count));
			}else if(count>0 && countRequired == false){
				array = array.append("[]=").append(data.get(count));
			}else{
				array = array.append(data.get(count));
			}
			stringArray.add(array.toString());
		}
		return stringArray;
	}	
	
	
	/**
	 * Anything missing
	 */
	private boolean checkMandatoryFields(LeadProfileCreationRequestVO leadProfileCreationRequestVO){
		boolean missing = false;
		if(StringUtils.isBlank(leadProfileCreationRequestVO.getProviderFirmId())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLmsPassword())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getCompanyName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getFirstName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLastName())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getAddress())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getCity())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getState())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getZip())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadPhoneNo())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getContactEmail())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadEmailId())
				){
			missing = true;
		}
		return missing;
		
	}
	
	/**
	 * 
	 * @param partnerId
	 * @param status
	 * @param statusReason
	 * @return
	 * @throws MalformedURLException
	 */
	private LeadPartnerStatusResponseVO setPartnerStatus(String partnerId, String status, String statusReason) throws MalformedURLException{
		LeadPartnerStatusResponseVO leadPartnerStatusResponseVO = new LeadPartnerStatusResponseVO();
		if(!StringUtils.isEmpty(partnerId)){
			SimpleRestClient simpleRestClient = new SimpleRestClient(lmsApiBaseUrl,null,null,false);
			// Create the input map
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(PublicAPIConstant.KEY,lmsApiKey);
			urlParams.put(PublicAPIConstant.API_ACTION,PublicAPIConstant.SET_PARTNER_STATUS_API);
			urlParams.put(PublicAPIConstant.PARTNER_ID,partnerId);
			urlParams.put(PublicAPIConstant.STATUS,status);
			urlParams.put(PublicAPIConstant.STATUS_REASON,statusReason);
			String response = simpleRestClient.get(urlParams);
			leadPartnerStatusResponseVO = (LeadPartnerStatusResponseVO)deserializeResponse(response,LeadPartnerStatusResponseVO.class);
		}
		return leadPartnerStatusResponseVO;
	}
	 
	
	private LeadInsertFilterResponseVO insertFilterSet(LeadProfileCreationRequestVO leadProfileCreationRequestVO,
			List<String> projects,String apiName) throws MalformedURLException{
		LeadInsertFilterResponseVO leadInsertFilterResponseVO = new LeadInsertFilterResponseVO();
		SimpleRestClient simpleRestClient = new SimpleRestClient(lmsApiBaseUrl,null,null,false);
		if (null != leadProfileCreationRequestVO){
			
			// Check mandatory fields
			boolean mandatoryFieldsMissing = checkMandatoryFieldsForFilter(leadProfileCreationRequestVO,projects);
			if(mandatoryFieldsMissing){
				logger.info("Missing mandatory fields");
				return null;
			}
			
			//setting the parameters in URL
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(PublicAPIConstant.KEY,lmsApiKey);
			urlParams.put(PublicAPIConstant.API_ACTION,apiName);
			urlParams.put(PublicAPIConstant.PARTNER_ID,leadProfileCreationRequestVO.getPartnerId());
			if(PublicAPIConstant.INSERT_COMP_FILTER_SET_API.equalsIgnoreCase(apiName)){
				urlParams.put(PublicAPIConstant.FILTER_SET_NAME,PublicAPIConstant.INSERT_COMP_FILTER_SET);
				urlParams.put(PublicAPIConstant.FILTER_SET_PRICE,PublicAPIConstant.INSERT_COMP_FILTER_SET_PRICE);
			}else if(PublicAPIConstant.INSERT_EXCL_FILTER_SET_API.equalsIgnoreCase(apiName)){
				urlParams.put(PublicAPIConstant.FILTER_SET_NAME,PublicAPIConstant.INSERT_EXCL_FILTER_SET);
				urlParams.put(PublicAPIConstant.FILTER_SET_PRICE,PublicAPIConstant.INSERT_EXCL_FILTER_SET_PRICE);
			}
			urlParams.put(PublicAPIConstant.ACCEPTED_SOURCES,PublicAPIConstant.LMS_ACCEPTED_ALL_SOURCES);
			urlParams.put(PublicAPIConstant.MATCH_PRIORITY,PublicAPIConstant.LMS_MATCHING_PRIORITY);
			urlParams.put(PublicAPIConstant.DAILY_LIMIT,leadProfileCreationRequestVO.getLeadDailyLimit());
			urlParams.put(PublicAPIConstant.MONTHLY_LIMIT,leadProfileCreationRequestVO.getLeadMonthlyLimit());
			
			List<String> availableDays = new ArrayList<String>();
			if(null != leadProfileCreationRequestVO.getMondayInd() 
					&& 1 == leadProfileCreationRequestVO.getMondayInd()){
				availableDays.add("Monday");
			}
			if(null != leadProfileCreationRequestVO.getTuesndayInd() 
					&& 1 == leadProfileCreationRequestVO.getTuesndayInd()){
				availableDays.add("Tuesday");
			}
			if(null != leadProfileCreationRequestVO.getWednesdayInd() 
					&& 1 == leadProfileCreationRequestVO.getWednesdayInd()){
				availableDays.add("Wednesday");
			}
			if(null != leadProfileCreationRequestVO.getThursdayInd() 
					&& 1 == leadProfileCreationRequestVO.getThursdayInd()){
				availableDays.add("Thursday");
			}
			if(null != leadProfileCreationRequestVO.getFridayInd() 
					&& 1 == leadProfileCreationRequestVO.getFridayInd()){
				availableDays.add("Friday");
			}
			if(null != leadProfileCreationRequestVO.getSaturdayInd()
					&& 1 == leadProfileCreationRequestVO.getSaturdayInd()){
				availableDays.add("Saturday");
			}
			if(null != leadProfileCreationRequestVO.getSundayInd()
					&& 1 == leadProfileCreationRequestVO.getSundayInd()){
				availableDays.add("Sunday");
			}
			urlParams.put(PublicAPIConstant.DAY_OF_WEEK,StringUtils.join(availableDays, PublicAPIConstant.COMMA));
			urlParams.put(PublicAPIConstant.TIME_OF_DAY,PublicAPIConstant.LMS_TIME_OF_DAY); // Hardcoded as 00:00-23:59
			urlParams.put(PublicAPIConstant.ACCEPT_MANUALLY_REVIEWED_LEADS,PublicAPIConstant.LMS_ACCEPT_MANUAL_LEADS); // Hardcoded as Yes
			
			// No need for ZIP_MODE as per latest LMS API specification.
			//urlParams.put(PublicAPIConstant.ZIP_MODE,PublicAPIConstant.LMS_ACCEPT_LISTED_ZIPS);
			
			// Radius is in integer - Strip off the OR less from "XX OR less" miles			
			String[] splitStr = leadProfileCreationRequestVO.getCoverageInMiles().split("\\s+");
			urlParams.put(PublicAPIConstant.ZIP_RADIUS,splitStr[0]);
			
			//Pass the Licensing states to LMS if Licensing is required
			if(null != leadProfileCreationRequestVO.getIsLicensingRequired() && leadProfileCreationRequestVO.getIsLicensingRequired() == 1){

				if(!StringUtils.isEmpty(leadProfileCreationRequestVO.getLicensingStates())){

					urlParams.put(PublicAPIConstant.LICENSING_STATE,leadProfileCreationRequestVO.getLicensingStates().trim());
				}
			}	
			urlParams.put(PublicAPIConstant.PROJECT,StringUtils.join(projects, PublicAPIConstant.COMMA));
			if(PublicAPIConstant.LOCATION_TYPE_RES.equalsIgnoreCase(leadProfileCreationRequestVO.getLocationType())){
				urlParams.put(PublicAPIConstant.LOCATION_TYPE,PublicAPIConstant.LMS_LOCATION_TYPE_RES);
			}else if(PublicAPIConstant.LOCATION_TYPE_COM.equalsIgnoreCase(leadProfileCreationRequestVO.getLocationType())){
				urlParams.put(PublicAPIConstant.LOCATION_TYPE,PublicAPIConstant.LMS_LOCATION_TYPE_COM);
			}else if(PublicAPIConstant.LOCATION_TYPE_BOTH.equalsIgnoreCase(leadProfileCreationRequestVO.getLocationType())){
				urlParams.put(PublicAPIConstant.LOCATION_TYPE,PublicAPIConstant.LMS_LOCATION_TYPE_BOTH);
			}	
			
			urlParams.put(PublicAPIConstant.SKILL,leadProfileCreationRequestVO.getSkill());
			urlParams.put(PublicAPIConstant.URGENCY_OF_SERVICE,leadProfileCreationRequestVO.getUrgencyServices());
			String response = simpleRestClient.get(urlParams);
			leadInsertFilterResponseVO = (LeadInsertFilterResponseVO)deserializeResponse(response,LeadInsertFilterResponseVO.class);
		}
		return leadInsertFilterResponseVO;
	}
	
	private boolean checkMandatoryFieldsForFilter(LeadProfileCreationRequestVO leadProfileCreationRequestVO,List<String> projects) {
		boolean missing = false;
		
		// Check if at least one day indicator is there
		
		boolean day = false;
		
		if(null != leadProfileCreationRequestVO.getMondayInd() 
				|| null != leadProfileCreationRequestVO.getTuesndayInd()
				|| null != leadProfileCreationRequestVO.getWednesdayInd()
				|| null != leadProfileCreationRequestVO.getThursdayInd()
				|| null != leadProfileCreationRequestVO.getFridayInd()
				|| null != leadProfileCreationRequestVO.getSaturdayInd()
				|| null != leadProfileCreationRequestVO.getSundayInd()){
			day = true;
		}
		if(!day){
			missing = true;			
			return missing;
		}
		

		if(null == projects || projects.size()==0){
			missing = true;			
			return missing;
		}
		if(StringUtils.isBlank(leadProfileCreationRequestVO.getPartnerId())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadDailyLimit())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLeadMonthlyLimit())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getUrgencyServices())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getLocationType())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getSkill())
				|| StringUtils.isBlank(leadProfileCreationRequestVO.getCoverageInMiles())){
			missing = true;
		}
		return missing;
	}

	private LeadProfileBillingResponseVO createCIMProfileAndCharge(LeadProfileBillingRequestVO leadProfileBillingRequestVO) throws MalformedURLException{
		LeadProfileBillingResponseVO leadProfileBillingResponseVO = new LeadProfileBillingResponseVO();
		String appendZeroStr = "";
		if(!StringUtils.isEmpty(leadProfileBillingRequestVO.getPartnerId())){
			SimpleRestClient simpleRestClient = new SimpleRestClient(lmsApiBaseUrl,null,null,false);
			// Create the input map
			Map<String, String> urlParams = new HashMap<String, String>();
			urlParams.put(PublicAPIConstant.KEY,lmsApiKey);
			urlParams.put(PublicAPIConstant.API_ACTION,PublicAPIConstant.CIM_PROFILE_CHARGE_API);
			urlParams.put(PublicAPIConstant.PARTNER_ID,leadProfileBillingRequestVO.getPartnerId());
			
			// $0.99 for th e card authentication 			
			urlParams.put(PublicAPIConstant.AMOUNT,PublicAPIConstant.CREDIT_CARD_AUTH_AMOUNT);
			urlParams.put(PublicAPIConstant.ORGANIZATION_TYPE,leadProfileBillingRequestVO.getCardType());
			urlParams.put(PublicAPIConstant.DESCRIPTION,PublicAPIConstant.CREDIT_CARD_AUTH_DESCRIPTION+leadProfileBillingRequestVO.getPartnerId());
			urlParams.put(PublicAPIConstant.CARD_NO,leadProfileBillingRequestVO.getCardNo().toString());
			urlParams.put(PublicAPIConstant.EXPIRATION_YEAR,leadProfileBillingRequestVO.getExpirationYear().toString());
			if(leadProfileBillingRequestVO.getExpirationMonth().toString().trim().length() == 1 ){
				appendZeroStr = "0";
			}
			urlParams.put(PublicAPIConstant.EXPIRATION_MONTH,appendZeroStr + leadProfileBillingRequestVO.getExpirationMonth().toString());
			if(null!=leadProfileBillingRequestVO.getCCV()){
				urlParams.put(PublicAPIConstant.CCV,leadProfileBillingRequestVO.getCCV().toString());
			}
			String response = simpleRestClient.get(urlParams);
			leadProfileBillingResponseVO = (LeadProfileBillingResponseVO)deserializeResponse(response,LeadProfileBillingResponseVO.class);
		}
		return leadProfileBillingResponseVO;
	}
	
	@SuppressWarnings("rawtypes")
	private Object deserializeResponse( String objectXml , Class<?> clazz) {
		//return this.<IAPIResponse>deserializeRequest(objectXml,c);
		Object obj = new Object();
		try {		
			XStream xstream = getXstream(clazz);
			obj = (Object) xstream.fromXML(objectXml);
			logger.info("Exiting deserializeResponse()");		
					
		} catch (Exception e) {
			logger.error(e);
		}
		return obj;
	}
	
	private XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}

	public LeadManagementDaoFactory getLeadManagementDaoFactory() {
		return leadManagementDaoFactory;
	}

	public void setLeadManagementDaoFactory(
			LeadManagementDaoFactory leadManagementDaoFactory) {
		this.leadManagementDaoFactory = leadManagementDaoFactory;
	}

	public String getLmsApiBaseUrl() {
		return lmsApiBaseUrl;
	}

	public void setLmsApiBaseUrl(String lmsApiBaseUrl) {
		this.lmsApiBaseUrl = lmsApiBaseUrl;
	}
	
	public ProjectDetailsList getleadProjectTypeAndRates(
			String vendorId) 
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public LeadProfileCreationRequestVO getProfileDetails(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateFilterSetId(Integer filterSetId, String partnerId, String filterType,String wfStates)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public boolean updateLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return true;
	}

	public void updatePartnerId(String partnerId, String vendorId,String wfStatus)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public LeadServicePriceVO validateProjectType(String providerFirmId,
			String projectType) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateProviderFirmLeadEligibility(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	public int doCrmRegistration(String request) throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String validateLeadPartnerId(String firmId) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLmsApiKey() {
		return lmsApiKey;
	}

	public void setLmsApiKey(String lmsApiKey) {
		this.lmsApiKey = lmsApiKey;
	}
	/**/

	public boolean validateVendor(String vendorId) throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	public LeadServicePriceVO getPriceDetails(LeadProjectVO projectVO) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateCrmStatus(String crmStatus,Integer providerFirmId)throws DataServiceException
	{
	}

	public void updateLMSError(String vendorId, String errors)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public void updateLMSStatus(String vendorId, String status,
			String statusReason) throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public LeadProfileCreationRequestVO getFilterDetails(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean validateIfFilterPresent(String providerFirmId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	public HashMap<String, Object> getPackagePriceDetails(String packageId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVendorIdFromPartnerId(String partnerId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public LeadProfileCreationRequestVO validateFilterPresent(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getLookUpvalues(String commaSeperatedIds, Integer type)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteFilterForVendor(String vendorId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		
	}

	public void updateVendorStatus(Map<Integer,Integer> vendorIdMap)throws DataServiceException{
		
		logger.info("updateVendorStatus");
		
		logger.info(" vendorIdMap size"+vendorIdMap.size());
		
		Set<Integer> keys = vendorIdMap.keySet();
		logger.info("after key set");
		for (Iterator i = keys.iterator(); i.hasNext();) {
			logger.info("inside loop");
			
			//call setPartnerStatus API to update status_reason = 4;
			
			String partnerId="";
			String vendorId="";
			
			vendorId=i.next().toString();
			logger.info("before partnerId");
			partnerId=vendorIdMap.get(i.next()).toString();  
			
			logger.info("vendorId "+vendorId);
			logger.info("partnerId "+partnerId);

			
			LeadPartnerStatusResponseVO partnerStatusResponseVO;
			try {
				partnerStatusResponseVO = setPartnerStatus(partnerId, PublicAPIConstant.STATUS_INACTIVE, PublicAPIConstant.CIM_PROFILE_FAILURE_DESC);
			
			
			if(null!= partnerStatusResponseVO && null==partnerStatusResponseVO.getErrors()){
				getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
					updateLMSStatus(vendorId,
					PublicAPIConstant.STATUS_INACTIVE_DESC, PublicAPIConstant.CIM_PROFILE_FAILURE_DESC);
			}else{		
				// ERROR
				List<String> errorList = partnerStatusResponseVO.getErrors().getError();
				StringBuffer stringbuff = new StringBuffer();
				
				// Save the error in the table
				for(String error : errorList){
					stringbuff.append(error);
					stringbuff.append(" ");
				}
				getLeadManagementDaoFactory().getLeadDAO(SLLEADDAO).
					updateLMSError(vendorId, stringbuff.toString());
			}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}





	public boolean getInsideSalesApiInvocationSwitch()
			throws DataServiceException {
		// TODO Auto-generated method stub
		return false;
	}





	public String createInsideSalesProfile(String request,String insideSalesLoginRequestJSON)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}





	public void updateInsideSalesResponse(String isResponse,
			Integer providerFirmId,Integer crmStatus) throws DataServiceException{
	}
	public String getState(String stateCd)
	{
		return null;	
	}


}

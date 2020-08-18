package com.newco.marketplace.api.services.leadsdetailmanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.so.v1_1.SOBaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.UpdateLeadStatusResponse;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadLoggingVO;
import com.newco.marketplace.dto.vo.leadsmanagement.UpdateLeadStatusRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
public class UpdateLeadStatusService extends SOBaseService{

	private ILeadProcessingBO leadProcessingBO;	
	private Logger logger = Logger.getLogger(UpdateLeadStatusService.class);
	private boolean errorOccured ;
	
	/**
	 * Constructor
	 */
	public UpdateLeadStatusService() {
		super(PublicAPIConstant.LEAD_UPDATE_FIRM_STATUS_REQUEST_XSD, 
			PublicAPIConstant.LEAD_UPDATE_FIRM_STATUS_RESPONSE_XSD,
			  PublicAPIConstant.NEW_SERVICES_NAMESPACE,
			  PublicAPIConstant.NEW_SERVICES_SCHEMA,
			  PublicAPIConstant.LEAD_UPDATE_FIRM_STATUS_RESPONSE_SCHEMALOCATION,
			  UpdateLeadStatusRequest.class, 
			  UpdateLeadStatusResponse.class);
		this.errorOccured = false;
		
	}

	
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Entering Update Lead Status API's execute method");	
		UpdateLeadStatusResponse response =new UpdateLeadStatusResponse();
		UpdateLeadStatusRequest request = (UpdateLeadStatusRequest) apiVO.getRequestFromPostPut();
		try{
			response=validateRequest(request, response);
			if(errorOccured){
				return response;				
			}						
		}catch (Exception e) {
			logger.error("UpdateLeadStatusService exception detail: " + e.getMessage());
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;
		}
	
	
	/**
	 * Update status
	 * @param request
	 * @param response
	 * @return
	 */
	public UpdateLeadStatusResponse validateRequest(UpdateLeadStatusRequest request,UpdateLeadStatusResponse response){
	  
		this.errorOccured=false;
		String frimId = null;
		String slLeadId = null;
		String leadStatus=null;
		String currentLeadFirmStatus=null;
		boolean firmValid = false;
	
		UpdateLeadStatusRequestVO requestVO=new UpdateLeadStatusRequestVO();
		try {
			if (null != request) {
				slLeadId = request.getLeadId();
				if (!StringUtils.isBlank(slLeadId)) {				
					//checking if lead id is present in DB and Its Status is 
					leadStatus = leadProcessingBO.validateSlLeadIdAndStatus(slLeadId);
					if( null==leadStatus){
						response.setResults(Results.getError(ResultsCode.LEAD_NOT_FOUND.getMessage(),ResultsCode.LEAD_NOT_FOUND.getCode()));
						errorOccured = true;
						return response;
					}else if(leadStatus.equalsIgnoreCase(NewServiceConstants.LEAD_STATUS_MATCHED)) {
						//checking if the response has firm status part					
						if (null!= request.getLeadfirmStatus() &&
								null!=request.getLeadfirmStatus().getFirmStatus()&& null!=request.getLeadfirmStatus().getFirmId()) {
							frimId = request.getLeadfirmStatus().getFirmId();									
							if (!StringUtils.isBlank(frimId)) {
								//checking if firm id is present in DB
								//BLM/PLM Changes --START
								//firmValid = leadProcessingBO.validateFirmId(frimId);
								String firmIdReturned= leadProcessingBO.toValidateFirmId(frimId);
								if (!StringUtils.isBlank(firmIdReturned) && firmIdReturned.equals(frimId)) {
									firmValid = true;
								}
								//BLM/PLM Changes --END
								if (!firmValid) {
									response.setResults(Results.getError(ResultsCode.FIRM_NOT_FOUND.getMessage(),ResultsCode.FIRM_NOT_FOUND
													.getCode()));
									errorOccured = true;
									//return response;
								}else{
									//logic for finding if firm id is matching with lead
									Map<String, String> reqMap = new HashMap<String, String>();
									reqMap.put(PublicAPIConstant.FIRM_ID,frimId);
									reqMap.put(PublicAPIConstant.SL_LEAD_ID,slLeadId);									
									currentLeadFirmStatus = leadProcessingBO.validateLeadFirmStatus(reqMap);
									String firmStatus="";
								    firmStatus = request.getLeadfirmStatus().getFirmStatus();
									if (!StringUtils.isBlank(firmStatus)) {
										if (null == currentLeadFirmStatus){
											response.setResults(Results.getError(
													ResultsCode.UNMATCHED1.getMessage(),ResultsCode.UNMATCHED1.getCode()));
											errorOccured = true;
											return response;
										}else if(currentLeadFirmStatus.equals(firmStatus)){
											response.setResults(Results.getSuccess("Current Lead Firm Status is same as provided lead Firm Status"));
										  
										}else if(!validateWfStatusForLead(firmStatus,currentLeadFirmStatus)){
											response.setResults(Results.getError(ResultsCode.FIRM_STATUS_CANNOT_BE_UPDATED.getMessage(),
													ResultsCode.FIRM_STATUS_CANNOT_BE_UPDATED.getCode()));
										}
										else{
											  requestVO.setFirmId(request.getLeadfirmStatus().getFirmId());
											  requestVO.setLeadId(request.getLeadId());
											  updateLeadFirmStatus(requestVO,request);
										    //Logging details into lead history
											  
											//getting user name of firm to log into history Status Changed: Working Customer requested quote
											  if(firmStatus.equalsIgnoreCase(NewServiceConstants.WORKING_LEAD_STATUS)){
												  firmStatus=NewServiceConstants.WORKING_LEAD_STATUS;
											  }
											  else if(firmStatus.equalsIgnoreCase(NewServiceConstants.FIRM_STATUS_SCHEDULED)){
												  firmStatus=NewServiceConstants.FIRM_STATUS_SCHEDULED;
											  }
											 // String userName=leadProcessingBO.getUserName(request.getLeadfirmStatus().getFirmId());	
											  String createdBy=request.getFullName();
											  String modifiedBy=request.getUserName();
											  Integer entityId=request.getEntityId();
											  String comment=NewServiceConstants.LEAD_STATUS_UPDATE_FIRM+firmStatus+" "+"|"+" "+request.getReason();
											  LeadLoggingVO leadLoggingVO=new LeadLoggingVO(slLeadId , NewServiceConstants.LEAD_FIRM_UPDATE, 
													  currentLeadFirmStatus, firmStatus,comment, 
													  createdBy,modifiedBy, NewServiceConstants.ROLE_ID_PROVIDER,entityId);					
												leadProcessingBO.insertLeadLogging(leadLoggingVO);
											 response.setResults(Results.getSuccess("Lead Firm  status updated Successfully"));
										}
									}else{
										response.setResults(Results.getError(ResultsCode.FIRM_STATUS_EMPTY.getMessage(),
												ResultsCode.FIRM_STATUS_EMPTY.getCode()));
									}
								}
							}else{
								response.setResults(Results.getError(ResultsCode.INVALID_VENDOR.getMessage(),
									ResultsCode.INVALID_VENDOR.getCode()));		
							}					
						}
					}else{
						response.setResults(Results.getError(ResultsCode.LEAD_CANNOT_BE_UPDATED.getMessage(),ResultsCode.LEAD_CANNOT_BE_UPDATED.getCode()));
						errorOccured = true;
						return response;
					}
				}else {
					response.setResults(Results.getError(ResultsCode.INVALID_LEAD_ID.getMessage(),ResultsCode.INVALID_LEAD_ID.getCode()));
					errorOccured = true;
					return response;
				}
			}else {
				errorOccured = true;
				response.setResults(Results.getError(ResultsCode.NOENTRY.getMessage(),ResultsCode.NOENTRY.getCode()));				
			}
			if (errorOccured) {
				return response;				
			}
		}catch (Exception e) {
			logger.error("UpdateLeadStatusService validate exception detail: "+ e.getMessage());
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			errorOccured = true;
		}
		return response;
	}

  	/**
  	 * Validate status
  	 * @param firmStatus
  	 * @param currentLeadFirmStatus
  	 * @return
  	 * @throws BusinessServiceException
  	 */
	private boolean validateWfStatusForLead(String firmStatus, String currentLeadFirmStatus) throws BusinessServiceException {
	    boolean isValidWfState=false;
	    List<String> resultList=leadProcessingBO.getAllleadFirmStatus();
	    Map<String,Integer> priorityMap=getPriorityMap();
	      if(firmStatus.equals(NewServiceConstants.COMPLETED_STATUS) ||
			firmStatus.equals(NewServiceConstants.CANCELLED_STATUS)){
		    return isValidWfState;
	        }
	        else if(resultList.contains(firmStatus)){
	           Integer currentPriority=priorityMap.get(currentLeadFirmStatus);
	           Integer givenPriority=priorityMap.get(firmStatus) ;  
		       if(givenPriority>currentPriority){
		    	   isValidWfState=true;
		       }else{
		    	   isValidWfState=false;
		     }
		     return isValidWfState;  
		    }else{
		     return isValidWfState;
	       }
	}

	/**
	 * Update Firm status
	 * @param requestVO
	 * @param request
	 * @throws BusinessServiceException
	 */
	public void updateLeadFirmStatus (UpdateLeadStatusRequestVO requestVO,UpdateLeadStatusRequest request)throws BusinessServiceException{
		try{
			requestVO.setLeadId(request.getLeadId());
			requestVO.setFirmId(request.getLeadfirmStatus().getFirmId());
			requestVO.setFirmStatus(request.getLeadfirmStatus().getFirmStatus());
			requestVO.setModifiedDate(new Date());
			leadProcessingBO.updateMatchedFirmStatus(requestVO);
	   }catch (Exception e) {
		   logger.error("updateFirmStatus exception detail: " + e.getMessage());
		   throw new BusinessServiceException(e.getCause());
	   }
	}
	
	/**
	 * 
	 * @return
	 */
	private Map<String,Integer> getPriorityMap() {
		Map<String,Integer>priorityMap=new HashMap<String, Integer>();
		  priorityMap.put(NewServiceConstants.NEW_LEAD_STATUS,1);
		  priorityMap.put(NewServiceConstants.WORKING_LEAD_STATUS,2);
		  priorityMap.put( NewServiceConstants.SCHEDULE_LEAD_STATUS,3);
		return priorityMap;
	}

	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	@Override
	protected IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		// TODO Auto-generated method stub
		return null;
	}
	}
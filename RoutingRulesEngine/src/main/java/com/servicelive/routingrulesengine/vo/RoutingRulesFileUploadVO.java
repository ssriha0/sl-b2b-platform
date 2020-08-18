package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.routingrules.RoutingRuleFileError;
import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.routingrulesengine.RoutingRulesConstants;

public class RoutingRulesFileUploadVO implements Serializable{

	private static final long serialVersionUID = 4209511119795041722L;
	private static final String DATE_FORMAT = "MM/dd/yyyy hh:mma zzz";
		
	private Integer routingRuleFileHdrId;
	private String uploadedFileName;
	private String fileStatus;
	private String processCompletedTime; 
	private String uploadedBy;
	private String uploadedUserId;
	private String createdDate;
	private String numNewRules;
	private String numErrors;
	private String numUpdateRules;
	private String numConflicts;
	private List<String> routingRuleFileErrors;
	private boolean uploadSuccess;
	private String uploadError;
	private String uploadAction;
	
	/**
	 * Map the domain object to VO
	 * @param domainObjs
	 * @return
	 */
	public static List<RoutingRulesFileUploadVO> getVOFromDomainObjects
			(List<RoutingRuleFileHdr> domainObjs,Map<Integer, String> ruleCauseMap) 
			throws Exception{
		
		List<RoutingRulesFileUploadVO> uploadVOs = 
			new ArrayList<RoutingRulesFileUploadVO>(domainObjs.size());
		
		for(RoutingRuleFileHdr routingRuleFileHdr : domainObjs){
			RoutingRulesFileUploadVO uploadVO = new RoutingRulesFileUploadVO();
			
			// Set the values
			uploadVO.setRoutingRuleFileHdrId(routingRuleFileHdr.
				getRoutingRuleFileHdrId());
			
			// File Name to display on the screen
			uploadVO.setUploadedFileName(routingRuleFileHdr.
				getRoutingRuleFileName());
			uploadVO.setFileStatus(routingRuleFileHdr.getFileStatus());
			
			// process_complete_time. This has to be in the form of date-time
			// TODO 
			if(null != routingRuleFileHdr.getProcessCompletedTime())
				uploadVO.setProcessCompletedTime(routingRuleFileHdr.
						getProcessCompletedTime());
			
			BuyerResource buyerResource = routingRuleFileHdr.getUploadedBy();
			
			if (buyerResource != null) {
				
				String userName = buyerResource.getUser().getUsername();
				String modifiedBy = routingRuleFileHdr.getModifiedBy();
				boolean differentUser = false;
				if((null != userName && null != modifiedBy) && 
						(!userName.equalsIgnoreCase(modifiedBy))){
					differentUser = true;
				}
				Contact contact = buyerResource.getContact();
				if (contact != null) {
					String modifiedByContactFirstNameLastNameResourceId  = 
						contact.getFirstName() + " " + contact.getLastName();
					if(differentUser){
						uploadVO.setUploadedBy(modifiedBy);
					}else{
						uploadVO.setUploadedBy(modifiedByContactFirstNameLastNameResourceId);
					}					
					uploadVO.setUploadedUserId("(#"+buyerResource.getResourceId()+")");
				}
			}
			if(null != routingRuleFileHdr.getNumNewRules() && 
					routingRuleFileHdr.getNumNewRules() > 0)
				uploadVO.setNumNewRules(routingRuleFileHdr.getNumNewRules().
						toString());
			if(null != routingRuleFileHdr.getNumConflicts() && 
					routingRuleFileHdr.getNumConflicts() > 0)
				uploadVO.setNumConflicts(routingRuleFileHdr.getNumConflicts().
						toString());
			if(null != routingRuleFileHdr.getNumErrors() && 
					routingRuleFileHdr.getNumErrors() > 0)
				uploadVO.setNumErrors(routingRuleFileHdr.getNumErrors().
						toString());
			if(null != routingRuleFileHdr.getNumUpdateRules() && 
					routingRuleFileHdr.getNumUpdateRules() > 0)
				uploadVO.setNumUpdateRules(routingRuleFileHdr.getNumUpdateRules().
						toString());
			
			if(null != routingRuleFileHdr.getRoutingRuleFileError()
					&& routingRuleFileHdr.getRoutingRuleFileError().size() > 0){
				Map<Integer,List<String>> fileErrorMap = new HashMap<Integer, List<String>>();
				List<String> errorsOnFile = new ArrayList<String>();
				String action = null; // Assuming there will be only one action per file
 				for(RoutingRuleFileError routingRuleFileError :
 						routingRuleFileHdr.getRoutingRuleFileError()){
 					
 					action = routingRuleFileError.getAction();
 					Integer errorType = routingRuleFileError.getErrorType();
 					String errorDesc = routingRuleFileError.getErrorDesc();
 					
 					// Check if the error type is present in look up
 					// Consolidate the errors based on the error type
 					if(ruleCauseMap.containsKey(errorType)){
 						if(fileErrorMap.containsKey(errorType)){
 							if(null!=errorDesc && errorDesc.length()>0){
 								List<String> errorDescriptionList = fileErrorMap.get(errorType);
 								if(!errorDescriptionList.contains(errorDesc)){
 									errorDescriptionList.add(errorDesc);
 									fileErrorMap.put(errorType, errorDescriptionList);
 								}
 							}
 						}else{
 							List<String> errorDescriptionList = new ArrayList<String>();
 							if(null!=errorDesc && errorDesc.length()>0){
 								errorDescriptionList.add(errorDesc);
 							}
 							fileErrorMap.put(errorType, errorDescriptionList);
 						}
 					}
				}
 				
 				/*Iterate the map and form the error message*/
 				Iterator<Integer> errorIterator = fileErrorMap.keySet().iterator();
 				while(errorIterator.hasNext()){
 					/* This is where the proper error message is created. 
 					 * 1.Get the proper error message based on the error type
 					 * */
 					Integer errortype = errorIterator.next();
 					String errorDescription = RoutingRulesConstants.errorDescMap.get(errortype);
 					
 					List<String> errorDesc = fileErrorMap.get(errortype);
 					String errorValues = "";
 					if(null!=errorDesc && errorDesc.size()>0){
 						for(String error : errorDesc){
 							errorValues = errorValues + error+",";
 						}	
 					}
 					// Remove the last comma.
					if(!StringUtils.isEmpty(errorValues) && !StringUtils.isEmpty(errorDescription)){
						errorValues = errorValues.substring(0,errorValues.length()-1);					
						// Relplace the ${##} with the error value;
						errorDescription = errorDescription.replace("${##}", errorValues);
						if(errortype.intValue() == RoutingRulesConstants.INVALID_STATUS.intValue()
								&& null!= action){
							if(RoutingRulesConstants.UPLOAD_ACTION_ARCHIVE.equalsIgnoreCase(action)){
								errorDescription = errorDescription+ RoutingRulesConstants.ARCHIVED;
							}else if(RoutingRulesConstants.UPLOAD_ACTION_INACTIVE.equalsIgnoreCase(action)){
								errorDescription = errorDescription+ RoutingRulesConstants.DE_ACTIVATED;
							}
						}
					}
					if(null!=errorDescription && errorDescription.length()>0)
						errorsOnFile.add(errorDescription);
 				}
 				if(errorsOnFile.size()>0)
 					uploadVO.setRoutingRuleFileErrors(errorsOnFile);
 				
 				if(null!=action)
 					uploadVO.setUploadAction(action);
			}
			uploadVO.setCreatedDate(routingRuleFileHdr.getCreatedDate());
			uploadVOs.add(uploadVO);
		}
		return uploadVOs;
    }
	
	public Integer getRoutingRuleFileHdrId() {
		return routingRuleFileHdrId;
	}
	
	public void setRoutingRuleFileHdrId(Integer routingRuleFileHdrId) {
		this.routingRuleFileHdrId = routingRuleFileHdrId;
	}
	
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	
	public String getFileStatus() {
		return fileStatus;
	}
	
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	
	public String getProcessCompletedTime() {
		return processCompletedTime;
	}

	public void setProcessCompletedTime(Date processCompletedTime) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone gmtTime = TimeZone.getTimeZone("CST");
        formatter.setTimeZone(gmtTime);
        String dateStr= formatter.format(processCompletedTime);
        if(gmtTime.inDaylightTime(processCompletedTime)){
        	dateStr = dateStr.replace("CST", "CDT");
        }
		this.processCompletedTime = dateStr;
	}
	
	
	public String getUploadedBy() {
		// Add the user name and the user id to for the uploaded by
		return uploadedBy;
	}
	
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	
	public List<String> getRoutingRuleFileErrors() {
		return routingRuleFileErrors;
	}
	
	public void setRoutingRuleFileErrors(List<String> routingRuleFileErrors) {
		this.routingRuleFileErrors = routingRuleFileErrors;
	}
	
	public String getCreatedDate(){
		return createdDate;
	}

	public void setCreatedDate(Date createdDate){
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone gmtTime = TimeZone.getTimeZone("CST");
        formatter.setTimeZone(gmtTime);
        String dateStr= formatter.format(createdDate);
        if(gmtTime.inDaylightTime(createdDate)){
        	dateStr = dateStr.replace("CST", "CDT");
        }
		this.createdDate = dateStr;
	}

	public String getUploadedUserId() {
		return uploadedUserId;
	}


	public void setUploadedUserId(String uploadedUserId) {
		this.uploadedUserId = uploadedUserId;
	}


	public boolean isUploadSuccess() {
		return uploadSuccess;
	}


	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public String getNumNewRules() {
		return numNewRules;
	}


	public void setNumNewRules(String numNewRules) {
		this.numNewRules = numNewRules;
	}


	public String getNumErrors() {
		return numErrors;
	}


	public void setNumErrors(String numErrors) {
		this.numErrors = numErrors;
	}


	public String getNumUpdateRules() {
		return numUpdateRules;
	}


	public void setNumUpdateRules(String numUpdateRules) {
		this.numUpdateRules = numUpdateRules;
	}


	public String getNumConflicts() {
		return numConflicts;
	}


	public void setNumConflicts(String numConflicts) {
		this.numConflicts = numConflicts;
	}

	public String getUploadError() {
		return uploadError;
	}

	public void setUploadError(String uploadError) {
		this.uploadError = uploadError;
	}

	public String getUploadAction() {
		return uploadAction;
	}

	public void setUploadAction(String uploadAction) {
		this.uploadAction = uploadAction;
	}
	
	
	
}
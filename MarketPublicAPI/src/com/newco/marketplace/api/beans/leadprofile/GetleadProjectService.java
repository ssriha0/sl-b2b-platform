package com.newco.marketplace.api.beans.leadprofile;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.GetLeadProjectTypeResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.vo.leadprofile.ProjectDetailsList;

public class GetleadProjectService {
	
	private ILeadProfileBO leadProfileBO;
	private Logger logger = Logger.getLogger(GetleadProjectService.class);
	private boolean errorOccured = false;
	private String vendorId = null;
	
	public GetleadProjectService() {

		this.errorOccured = false;
	}
	
	public GetLeadProjectTypeResponse execute(Map<String, String> reqMap) {
		
		GetLeadProjectTypeResponse response = new GetLeadProjectTypeResponse();
		ProjectDetailsList projectDetailsList = null;
		
		try{
			response = validateVendorId(reqMap, response);
			if(errorOccured){
				return response;
			}
			projectDetailsList = leadProfileBO.getleadProjectTypeAndRates(vendorId);
			if(null != projectDetailsList && null != projectDetailsList.getProject() && !projectDetailsList.getProject().isEmpty()){
				response.setProjectDetailsList(projectDetailsList);
				response.setResults(Results.getSuccess(ResultsCode.SUCCESS.getMessage()));
			}
			else{
				logger.error("GetleadProjectService exception");
				response.setResults(
						Results.getError(ResultsCode.NO_PROJECTS_AVAILABLE.getMessage(), 
								ResultsCode.NO_PROJECTS_AVAILABLE.getCode()));
			}
		}
		catch(Exception e) {
			logger.error("GetleadProjectService exception detail: " + e.getMessage());
			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;
	}
	
	private GetLeadProjectTypeResponse validateVendorId(Map<String, String> reqMap, GetLeadProjectTypeResponse response) {
		
		boolean isValid = false;
		try{
			if(null != reqMap && !reqMap.isEmpty()){
				vendorId = reqMap.get(PublicAPIConstant.VENDOR_ID);
				if(!StringUtils.isBlank(vendorId)){
					isValid = leadProfileBO.validateVendor(vendorId);
					if(!isValid){
						errorOccured = true;
					}
				}
				else{
					errorOccured = true;
				}
			}
			else{
				errorOccured = true;
			}
			if(errorOccured){
				response.setResults(Results.getError(ResultsCode.INVALID_VENDOR.getMessage(), 
						ResultsCode.INVALID_VENDOR.getCode()));
			}
		}catch(Exception e){
			logger.error("GetLeadProjectTypeResponse validateVendorId exception detail: " + e.getMessage());
			response.setResults(Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			errorOccured = true;
		}
		
		return response;
	}

	public ILeadProfileBO getLeadProfileBO() {
		return leadProfileBO;
	}
	public void setLeadProfileBO(ILeadProfileBO leadProfileBO) {
		this.leadProfileBO = leadProfileBO;
	}

}

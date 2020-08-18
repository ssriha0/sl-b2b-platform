package com.newco.marketplace.api.beans.leadprofile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.LeadInsertFiltersetRequest;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.ProjectVO;
import com.newco.marketplace.api.beans.leadprofile.leadprofilerequest.Projects;
import com.newco.marketplace.api.beans.leadprofile.leadprofileresponse.LeadInsertFiltersetResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.leadprofile.ILeadProfileBO;
import com.newco.marketplace.vo.leadprofile.LeadInsertFilterResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProjectVO;
import com.newco.marketplace.vo.leadprofile.LeadServicePriceVO;



public class LeadInsertFiltersetService {
	
	private ILeadProfileBO leadProfileBO;
	private Logger logger = Logger.getLogger(LeadInsertFiltersetService.class);
	private boolean errorOccured = false;

	/**
	 * Subclass needs to implement API specific logic here.
	 */
	public LeadInsertFiltersetResponse execute(LeadInsertFiltersetRequest request) {		

		logger.info("Entering Lead Insert Filterset API's execute method");	
		LeadInsertFiltersetResponse response = new LeadInsertFiltersetResponse();
		if(request!=null){
			if(errorOccured){
				errorOccured = false;
			}
			request = replaceSpecialChar(request);
			LeadProfileCreationRequestVO leadProfileCreationRequestVO = new LeadProfileCreationRequestVO();
			//Validating if provider firm has a partner id
			validateMandatoryFields(request, response);
			if(errorOccured){
				return response;
			}
			response = validateLeadPartnerId(request, response, leadProfileCreationRequestVO);
			if(errorOccured){
				return response;
			}
			// Validate if a filter set is already present
			//boolean filterPresent = validateIfFilterPresent(request, response, leadProfileCreationRequestVO);
			boolean filterPresent = validateIfFilterPresent(request, response);
			if(filterPresent){
				return response;
			}
			else{
				//fetch the price details based on input parameters
				response = getPriceDetails(request, response, leadProfileCreationRequestVO);
				if(null == leadProfileCreationRequestVO.getLeadServicePrice() || 
						(null != leadProfileCreationRequestVO.getLeadServicePrice() && leadProfileCreationRequestVO.getLeadServicePrice().isEmpty())){
					response.setResults(
							Results.getError(ResultsCode.FILTER_CREATION_FAILURE.getMessage(), 
									ResultsCode.FILTER_CREATION_FAILURE.getCode()));
					errorOccured = true;
				}
			}
			if(errorOccured){
				return response;
			}
			else{
				try{
					// Get the project List
					Map<String, List<String>> projectLists = getSeperatedProjects(request,leadProfileCreationRequestVO);
					if(PublicAPIConstant.YES.equals(request.getTermsAndCondition())){
						leadProfileCreationRequestVO.setTc(1);
					}
					//Method to persist data related to filter set creation
					LeadInsertFilterResponseVO filterResponseVO = leadProfileBO.insertFilterSet(leadProfileCreationRequestVO,projectLists);	
					// Get the results from the VO and set it to response class
					if(null != filterResponseVO && null != filterResponseVO.getErrors()){
						Results result = new Results();
						List<String> errors = filterResponseVO.getErrors().getError();
						List<ErrorResult> errorList = new ArrayList<ErrorResult>();
						for(String error: errors ){
							ErrorResult errorResult = new ErrorResult();
							errorResult.setMessage(error);
							errorList.add(errorResult);
						}
						result.setError(errorList);
						response.setResults(result);
					}else if(null != filterResponseVO && null != filterResponseVO.getResult() 
							&& ResultsCode.FILTER_CREATION_FAILURE.getMessage().equals(filterResponseVO.getResult())){
						response.setResults(
								Results.getError(ResultsCode.FILTER_CREATION_FAILURE.getMessage(), 
										ResultsCode.FILTER_CREATION_FAILURE.getCode()));
					}
					else {						
						response.setResults(
								Results.getSuccess(ResultsCode.FILTER_CREATION_SUCCESS.getMessage()));
					}
				} 
				catch(Exception e) {
					logger.error("LeadInsertFiltersetService exception detail: " + e.getMessage());
					response.setResults(
							Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
									ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
				}
				logger.info("Exiting LeadInsertFiltersetService API's execute method");	
			}
		}
		else{
			logger.info("Exiting LeadInsertFiltersetService API's execute method due tor equest null");	

			response.setResults(
					Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
							ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		return response;

	}
	
	private void validateMandatoryFields(LeadInsertFiltersetRequest request, LeadInsertFiltersetResponse response) {
		
		if(StringUtils.isBlank(request.getTermsAndCondition()) 
				|| (!StringUtils.isBlank(request.getTermsAndCondition()) 
						&& !(PublicAPIConstant.YES.equalsIgnoreCase(request.getTermsAndCondition()))) ){
			
			response.setResults(
					Results.getError(ResultsCode.TERMS_CONDITIONS.getMessage(), 
							ResultsCode.TERMS_CONDITIONS.getCode()));
			errorOccured = true;
		}
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, List<String>> getSeperatedProjects(LeadInsertFiltersetRequest request,LeadProfileCreationRequestVO requestVO){
		Map<String, List<String>> list = new HashMap<String, List<String>>();
		List<String> competitveProjects = new ArrayList<String>();
		List<String> exclusiveProjects = new ArrayList<String>();
		logger.info("size::"+requestVO.getLeadServicePrice().size());
		// Separate out based on the price selected.
		for(LeadServicePriceVO project : requestVO.getLeadServicePrice()){
			if(null != project){
				if(StringUtils.isNotBlank(project.getCompetitivePrice())){
					String compString = project.getLmsProjectTypeDesc().trim()
							+ PublicAPIConstant.SEPERATOR_PIPE
							+ project.getLmsServiceCategoryDesc().trim()
							+ PublicAPIConstant.SEPERATOR_PIPE
							+ PublicAPIConstant.FILTER_TYPE_COMPETITIVE;
					competitveProjects.add(compString.replaceAll("&", "%26"));
					logger.info("competitveProjects::" + compString);
				}
				if (StringUtils.isNotBlank(project.getExclusivePrice())) {
					String exclString = project.getLmsProjectTypeDesc().trim()
							+ PublicAPIConstant.SEPERATOR_PIPE
							+ project.getLmsServiceCategoryDesc().trim()
							+ PublicAPIConstant.SEPERATOR_PIPE
							+ PublicAPIConstant.FILTER_TYPE_EXCLUSIVE;
					exclusiveProjects.add(exclString.replaceAll("&", "%26"));
					logger.info("exclusiveProjects::"
							+ project.getLmsProjectTypeId().toString());
				}
			}	
		}
		
		list.put(PublicAPIConstant.COMP_FILTER_LIST, competitveProjects);
		list.put(PublicAPIConstant.EXCL_FILTER_LIST, exclusiveProjects);
		return list;
	}

	private LeadInsertFiltersetResponse getPriceDetails(LeadInsertFiltersetRequest request,LeadInsertFiltersetResponse response,LeadProfileCreationRequestVO requestVO) 
	{
		String vendorId = request.getVendorId().toString();
		List<LeadServicePriceVO> leadServicePriceData = new ArrayList<LeadServicePriceVO>();
		List<String> projects = new ArrayList<String>();
		try {
			for(ProjectVO project : request.getProjects().getProject()){
				if(null != project){
					LeadProjectVO projectVO = new LeadProjectVO();
					projectVO.setVendorId(vendorId);
					projectVO.setProjectId(project.getProjectId()); // This is the primary key in SL
					projectVO.setCategoryID(project.getCategoryID());
					
					if(StringUtils.isNotBlank(project.getExclusivePriceSelected()) 
							&& PublicAPIConstant.YES.equalsIgnoreCase(project.getExclusivePriceSelected())){
						projectVO.setExclusive(true);
					}else if (StringUtils.isNotBlank(project.getExclusivePriceSelected()) 
							&& PublicAPIConstant.NO.equalsIgnoreCase(project.getExclusivePriceSelected())){
						projectVO.setExclusive(false);
					}else if(StringUtils.isBlank(project.getExclusivePriceSelected())){
						projectVO.setExclusive(false);
					}
					
					if(StringUtils.isNotBlank(project.getCompPriceSelected())
							&& PublicAPIConstant.YES.equalsIgnoreCase(project.getCompPriceSelected())){
						projectVO.setCompPrice(true);
					}else if(StringUtils.isNotBlank(project.getCompPriceSelected())
							&& PublicAPIConstant.NO.equalsIgnoreCase(project.getCompPriceSelected())){
						projectVO.setCompPrice(false);
					}else if(StringUtils.isBlank(project.getCompPriceSelected())){
						projectVO.setCompPrice(false);
					}
					LeadServicePriceVO leadServicePriceVO = leadProfileBO.getPriceDetails(projectVO);
					if(null != leadServicePriceVO){
						leadServicePriceVO.setCompSelected(projectVO.isCompPrice());
						leadServicePriceVO.setExclSelected(projectVO.isExclusive());
						leadServicePriceVO.setProviderFirmId(vendorId);
						leadServicePriceData.add(leadServicePriceVO);
						projects.add(project.getProjectId().toString());
					}
				}
			}
			requestVO.setLeadServicePrice(leadServicePriceData);
			requestVO.setProjectType(projects);
			
		}catch (Exception e) {
			logger.error("Exception in LeadInsertFiltersetService getPriceDetails");
			 Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), 
					 ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
			errorOccured = true;
		}
		return response;
	}
	
	private boolean validateIfFilterPresent(LeadInsertFiltersetRequest request, LeadInsertFiltersetResponse response) {
		boolean filterPresent = false;
		LeadProfileCreationRequestVO filterType = new LeadProfileCreationRequestVO();
		String vendorId = request.getVendorId().toString();		
		boolean compFilter = false;
		boolean exclFilter = false;
		try {
			//filterPresent = leadProfileBO.validateIfFilterPresent(vendorId);
			filterType = leadProfileBO.validateFilterPresent(vendorId);
			if(null != filterType){
				Integer compId = filterType.getCompFilterId();
				Integer exclusiveId = filterType.getExclFilterId();
				
				for(ProjectVO project : request.getProjects().getProject()){
					if(null != project){
						if(compFilter && exclFilter){
							break;
						}
						if(StringUtils.isNotBlank(project.getCompPriceSelected()) 
								&& PublicAPIConstant.YES.equalsIgnoreCase(project.getCompPriceSelected())){
							compFilter = true;
						}
						if(StringUtils.isNotBlank(project.getExclusivePriceSelected()) 
								&& PublicAPIConstant.YES.equalsIgnoreCase(project.getExclusivePriceSelected())){
							exclFilter = true;
						}
					}	
				}
				
				if(compFilter && null != compId){
					filterPresent = true;
				}
				else if(exclFilter && null != exclusiveId){
					filterPresent = true;
				}
			}
			if(filterPresent){
				Results results = null;
				results = Results.getError(ResultsCode.FILTER_AVAILABLE
						.getMessage(), ResultsCode.FILTER_AVAILABLE
						.getCode());
				response.setResults(results);
			}
		} catch (Exception e) {
			logger.error("Exception in LeadInsertFiltersetService validateIfFilterPresent");
			errorOccured = true;
			e.printStackTrace();
		}
		return filterPresent;
	}
	
	private LeadInsertFiltersetResponse validateLeadPartnerId(LeadInsertFiltersetRequest request, LeadInsertFiltersetResponse response, LeadProfileCreationRequestVO requestVO) 
	{
		String vendorId = request.getVendorId().toString();
		String partnerId = null;
		try {
			partnerId = leadProfileBO.validateLeadPartnerId(vendorId);
			if(StringUtils.isNotBlank(partnerId)){
				requestVO.setProviderFirmId(vendorId);
				requestVO.setPartnerId(partnerId);
			}
			else{
				Results results = null;
				results = Results.getError(ResultsCode.INVALID_PARTNER
						.getMessage(), ResultsCode.INVALID_PARTNER
						.getCode());
				response.setResults(results);
				errorOccured = true;
			}
		} catch (Exception e) {
			logger.error("Exception in LeadInsertFiltersetService validateLeadPartnerId");
			errorOccured = true;
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * @param name
	 * 
	 * to replace special html characters in name
	 * 
	 */
	private String replaceSpecialHtmlCharacters(String name) {
		if(StringUtils.isNotBlank(name)){
			name = name.replaceAll("&quot;","\"");
			name = name.replaceAll("&amp;","&");
			name = name.replaceAll("&rsquo;","?");
			name = name.replaceAll("&gt;",">");
			name = name.replaceAll("&lt;","<");
			name = name.replaceAll("&apos;","'");
		}
	
		return name;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private LeadInsertFiltersetRequest replaceSpecialChar(LeadInsertFiltersetRequest request){
				
		try
		{	
			logger.info("in side try block");
			if(null != request.getVendorId()){
				String vendorId = request.getVendorId().toString();
				vendorId = replaceSpecialHtmlCharacters(vendorId);
				request.setVendorId(Integer.parseInt(vendorId));
			}
			
			String tc = request.getTermsAndCondition();
			tc = replaceSpecialHtmlCharacters(tc);
			request.setTermsAndCondition(tc);
			
			if(null != request.getProjects()){
				Projects projectVO = new Projects();
				List<ProjectVO> projects = new ArrayList<ProjectVO>();
				for(ProjectVO project : request.getProjects().getProject()){
					if(null != project){
						if(null != project.getCategoryID()){
							String categoryId = project.getCategoryID().toString();
							categoryId = replaceSpecialHtmlCharacters(categoryId);
							project.setCategoryID(Integer.parseInt(categoryId));
						}
						if(null != project.getProjectId()){
							String projectId = project.getProjectId().toString();
							projectId = replaceSpecialHtmlCharacters(projectId);
							project.setProjectId(Integer.parseInt(projectId));
						}
						if(StringUtils.isNotBlank(project.getExclusivePriceSelected())){
							String exclusive = project.getExclusivePriceSelected();
							exclusive = replaceSpecialHtmlCharacters(exclusive);
							project.setExclusivePriceSelected(exclusive);
						}
						if(StringUtils.isNotBlank(project.getCompPriceSelected())){
							String price = project.getCompPriceSelected();
							price = replaceSpecialHtmlCharacters(price);
							project.setCompPriceSelected(price);
						}
						projects.add(project);
					}
				}
				projectVO.setProject(projects);
				request.setProjects(projectVO);
			}
	
		}
		catch (Exception a_Ex) {
			logger.info(
					"--------- Exception inside specialChar validate  ---");		
		}
		return request;
	}

	public LeadInsertFiltersetService() {

		this.errorOccured = false;
	}

	public ILeadProfileBO getLeadProfileBO() {
		return leadProfileBO;
	}

	public void setLeadProfileBO(ILeadProfileBO leadProfileBO) {
		this.leadProfileBO = leadProfileBO;
	}



}

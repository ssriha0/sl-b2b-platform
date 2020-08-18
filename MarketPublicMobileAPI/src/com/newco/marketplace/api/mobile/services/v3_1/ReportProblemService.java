package com.newco.marketplace.api.mobile.services.v3_1;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.reportAProblem.ReportProblemVO;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemRequest;
import com.newco.marketplace.api.mobile.beans.reportProblem.ReportProblemResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.utils.mappers.v2_0.OFMapper;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.business.iBusiness.mobile.INewMobileGenericBO;
import com.newco.marketplace.core.BusinessServiceException;
import com.newco.marketplace.mobile.api.utils.validators.v3_1.NewMobileGenericValidator;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


@APIRequestClass(ReportProblemRequest.class)
@APIResponseClass(ReportProblemResponse.class)
public class ReportProblemService  extends BaseService{

	private static final Logger LOGGER = Logger.getLogger(ReportProblemService.class);
	private NewMobileGenericValidator mobileValidator;
	private NewMobileGenericMapper mobileMapper;
	private IMobileGenericBO mobileBO;
	private INewMobileGenericBO newMobileBO;
	private OFHelper ofHelper;


	@Override
	public IAPIResponse execute(final APIRequestVO apiVO) {
		ReportProblemResponse response = new ReportProblemResponse();
		
		final ReportProblemRequest request = (ReportProblemRequest) apiVO.getRequestFromPostPut();
		Results results = null;
		ReportProblemVO reportProblemVO = null;
		String strSuccessCode = null;
		String ofResponse=null;
		SecurityContext securityContext =null;
	
		try{
			//Map request to vo
			reportProblemVO = mobileMapper.mapReportProblemRequest(request, apiVO.getSOId(), apiVO.getProviderResourceId(),apiVO.getRoleId());
			if(null == reportProblemVO){
				throw new Exception("Request is null, cannot proceed");
			}
			//validate request
			response = mobileValidator.validateReportProblemRequest(reportProblemVO);
			
			//IF there are no errors,proceed 
			if(null== response.getResults()){
				//get the reason code description for insert to so notes
				reportProblemVO.setProblemReasonCodeDescription(mobileBO.getProblemTypeDescription(MPConstants.PROBLEM_STATUS,reportProblemVO.getProblemReasonCode()));
				securityContext = getSecurityContextForVendor(reportProblemVO.getResourceId());
				ofResponse = reportProblem(securityContext,reportProblemVO);
				if(StringUtils.isNotBlank(ofResponse)){
					strSuccessCode = ofResponse.substring(0,2);
				}
				if (StringUtils.isNotBlank(strSuccessCode) && strSuccessCode.equalsIgnoreCase(ResultsCode.SUCCES_OF_CALL.getCode())){
					addNoteForReportProblem(securityContext,reportProblemVO);
					results = Results.getSuccess(ResultsCode.V3_1_REPORT_PBM_SUCCESS_MSG.getCode(),ResultsCode.V3_1_REPORT_PBM_SUCCESS_MSG.getMessage());
					response.setResults(results);
				}else{// Error Response due to failure in of signal for report a problem   
					results = Results.getError(ResultsCode.V3_1_REPORT_PROBLEM_TRANSISTION_FAILED.getMessage(),ResultsCode.V3_1_REPORT_PROBLEM_TRANSISTION_FAILED.getCode());
					response.setResults(results);
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception in Reporting Problem for the order"+ apiVO.getSOId()+ e.getMessage());
			e.printStackTrace();
			results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			response.setResults(results);
		}
		return response;
	}


	private String reportProblem(SecurityContext securityContext, ReportProblemVO  reportProblemVO) throws BusinessServiceException {
		OrderFulfillmentResponse response =null;
		ProcessResponse pr = null;
		String returnVal= null;
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		
		SignalType problemSignal = SignalType.PROVIDER_REPORT_PROBLEM;
		request.setIdentification(OFMapper.createProviderIdentFromSecCtx(securityContext));
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_COMMENT, reportProblemVO.getProblemDescriptionComments());
		request.addMiscParameter(OrderfulfillmentConstants.PVKEY_PROBLEM_DESC, reportProblemVO.getProblemReasonCodeDescription());

		try{
			response=ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), problemSignal, request);
			pr = OFMapper.mapProcessResponse(response);
			List<String> arrMsgList = pr.getMessages();
			for(String msg:arrMsgList){
				returnVal = pr.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("reportProblemResolution validation messages :" + returnVal);
		}catch (Exception e) {
			LOGGER.error("Exception in executing signal for reporting problem"+ "Error Messages:"+ returnVal);
			throw new BusinessServiceException(e);
		}

		return returnVal;
	}
	
	
	private String addNoteForReportProblem(SecurityContext securityContext,ReportProblemVO reportProblemVO)throws BusinessServiceException {
		ProcessResponse processResponse = null;
		OrderFulfillmentResponse ofResponse=null;
		String returnVal=null;
		try{  
			OrderFulfillmentRequest request = mobileMapper.mapSONoteForReportAndResolveProblem(securityContext,reportProblemVO);
			request.setIdentification(OFMapper.createProviderIdentFromSecCtx(securityContext));
			ofResponse= ofHelper.runOrderFulfillmentProcess(reportProblemVO.getSoId(), SignalType.ADD_NOTE, request);
			processResponse = OFMapper.mapProcessResponse(ofResponse);
			List<String> arrMsgList = processResponse.getMessages();
			for(String msg:arrMsgList){
				returnVal = processResponse.getCode() + msg + System.getProperty("line.separator","\n");
			}
			LOGGER.info("Add Note  validation messages :" + returnVal);
		}catch (Exception e) {
			LOGGER.error("Exception inside addNoteForReportProblem() "+e.getMessage());
			throw new BusinessServiceException(e);
		}
		return returnVal;
	}
	
	public NewMobileGenericValidator getMobileValidator() {
		return mobileValidator;
	}

	public void setMobileValidator(NewMobileGenericValidator mobileValidator) {
		this.mobileValidator = mobileValidator;
	}

	public NewMobileGenericMapper getMobileMapper() {
		return mobileMapper;
	}

	public void setMobileMapper(NewMobileGenericMapper mobileMapper) {
		this.mobileMapper = mobileMapper;
	}

	public IMobileGenericBO getMobileBO() {
		return mobileBO;
	}

	public void setMobileBO(IMobileGenericBO mobileBO) {
		this.mobileBO = mobileBO;
	}

	public INewMobileGenericBO getNewMobileBO() {
		return newMobileBO;
	}

	public void setNewMobileBO(INewMobileGenericBO newMobileBO) {
		this.newMobileBO = newMobileBO;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
}

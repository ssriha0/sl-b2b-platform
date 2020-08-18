package com.newco.marketplace.api.survey;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategoryResponse;
import com.newco.marketplace.api.beans.survey.BuyerDetails;
import com.newco.marketplace.api.beans.survey.CSATSurveyQuestion;
import com.newco.marketplace.api.beans.survey.CsatOption;
import com.newco.marketplace.api.beans.survey.CsatQuestion;
import com.newco.marketplace.api.beans.survey.NPSSurveyQuestion;
import com.newco.marketplace.api.beans.survey.SurveyQuestionnaireDetailResponse;
import javax.ws.rs.*;
import com.newco.marketplace.api.beans.survey.SurveyResponse;
import com.newco.marketplace.api.beans.survey.SurveyValidationResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.server.SORequestProcessor;
import com.newco.marketplace.api.server.homeimprovement.v1_0.BaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.survey.ExtendedSurveyService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.survey.ExtendedSurveyBO;
import com.newco.marketplace.business.iBusiness.systemgeneratedemail.ISystemGeneratedBO;
import com.newco.marketplace.dto.vo.survey.SurveyBuyerQuestionDetailsVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionnaireDetailVO;
import com.newco.marketplace.util.PropertiesUtils;
import com.thoughtworks.xstream.converters.ConversionException;

/**
 * Created by kjain on 12/27/2018.
 */

@Path("/survey")
public class SurveyRequestProcessor extends BaseRequestProcessor{
    private Logger logger = Logger.getLogger(SORequestProcessor.class);    
   
    private final static String BELOW_AVERAGE = "_belowAverage";
    private final static String ABOVE_AVERAGE = "_aboveAverage";
    private final static String SERVICELIVE_TERMS_COND_URL = "servicelive_terms_cond_url";
    private final static String SERVICELIVE_PRIVACY_POLICY = "servicelive_privacy_policy";
    
	private static final String MEDIA_TYPE_XML_STR = "application/xml";
	private static final String MEDIA_TYPE_JSON_STR = "application/json";

    protected ExtendedSurveyBO extendedSurveyBO;
    protected ExtendedSurveyService extendedSurveyService;

    @Resource
    protected HttpServletRequest httpRequest;
    @Resource
    protected HttpServletResponse httpResponse;

    @GET
    @Path("/getDetails")
    @Consumes({ "application/xml", "application/json", "text/xml" })
    @Produces({ "application/xml", "application/json", "text/xml" })
    public Response getSurveyDetails(@QueryParam("key") String encryptedKey){
        logger.info("Entering SurveyRequestProcessor.getSurveyDetails()");

		SurveyQuestionnaireDetailResponse surveyDetailsResponse = new SurveyQuestionnaireDetailResponse();
		try {
			String encKey=URLEncoder.encode(encryptedKey,"UTF-8");
			if (null != encKey) {
				SurveyBuyerQuestionDetailsVO surveyDetailsVO = extendedSurveyBO.fetchQuestionnaire(encKey);
				mapVoToServiceResponse(surveyDetailsVO, surveyDetailsResponse);
			}
		} catch (Exception e) {
			logger.error("Exception Occurred: " + e);
			throw new RuntimeException(e);
		}
        return Response.ok(surveyDetailsResponse).build();
    }

    @GET
    @Path("/validate")
    @Consumes({ "application/xml", "application/json", "text/xml" })
    @Produces({ "application/xml", "application/json", "text/xml" })
    public Response validateSurvey(@QueryParam("key") String encryptedKey){
        logger.info("Entering SurveyRequestProcessor.validateSurvey()");

        SurveyValidationResponse res = new SurveyValidationResponse();
        boolean isSubmitted = false;
        
		try {
			String encKey=URLEncoder.encode(encryptedKey,"UTF-8");
			if (null != encKey) {
				isSubmitted = extendedSurveyBO.validateSurvey(encKey);
			}
		} catch (Exception e) {
			logger.error("Exception Occurred: " + e);
			throw new RuntimeException(e);
		}
        res.setSubmitted(isSubmitted+"");
        return Response.ok(res).build();
    } 
    
	/**
	 * SLT-1649
	 * 
	 * @param surveyRequest
	 * @return
	 */
	@POST
	@Path("/save")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SurveyResponse saveSurveyDetails(String surveyRequest) {
		
		logger.info("Entering SurveyRequestProcessor.saveSurveyDetails");
		String surveyResponse = null;
		SurveyResponse response = new SurveyResponse();
		Results results = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(surveyRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setAcceptHeader(MEDIA_TYPE_XML_STR);
		if (getHttpRequest().getContentType() != null) {
			String contentType = getHttpRequest().getContentType().split(";")[0];
			apiVO.setContentType(contentType);
		}
		if (getHttpRequest().getHeader("Accept") != null
				&& getHttpRequest().getHeader("Accept").equalsIgnoreCase(MEDIA_TYPE_JSON_STR)) {
			apiVO.setAcceptHeader(MEDIA_TYPE_JSON_STR);
		}
		try{
		surveyResponse = extendedSurveyService.doSubmit(apiVO);

		}
		catch(ConversionException ex){
			logger.error("ExtendedSurveyProcessor-->Exception-->" + ex.getMessage(), ex);
			results = Results.getError(ResultsCode.SAVE_SURVEY_INVALID_PARAMETER.getMessage(),
					ResultsCode.SAVE_SURVEY_INVALID_PARAMETER.getCode());
			if(ex.getCause().toString().contains("NumberFormatException")){
				results = Results.getError(ResultsCode.SAVE_SURVEY_INVALID_RATING.getMessage(),
						ResultsCode.SAVE_SURVEY_INVALID_RATING.getCode());
			}
			response.setResults(results);
			surveyResponse=convertReqObjectToXMLString(response, SurveyResponse.class);
			surveyResponse = PublicAPIConstant.XML_VERSION + surveyResponse;
			return (SurveyResponse) convertXMLStringtoRespObject(surveyResponse, SurveyResponse.class);
		}
		catch(NullPointerException ex){
			logger.error("ExtendedSurveyProcessor-->Exception-->" + ex.getMessage(), ex);
			results = Results.getError(ResultsCode.SAVE_SURVEY_INVALID_PARAMETER.getMessage(),
					ResultsCode.SAVE_SURVEY_INVALID_PARAMETER.getCode());
			response.setResults(results);
			surveyResponse=convertReqObjectToXMLString(response, SurveyResponse.class);
			surveyResponse = PublicAPIConstant.XML_VERSION + surveyResponse;
			return (SurveyResponse) convertXMLStringtoRespObject(surveyResponse, SurveyResponse.class);
		}
		if(surveyResponse.contains("Request xml schema validation failed")){
			logger.error("ExtendedSurveyProcessor-->Exception-->" + surveyResponse);
			results = Results.getError(ResultsCode.SAVE_SURVEY_INVALID_KEY.getMessage(),
					ResultsCode.SAVE_SURVEY_INVALID_KEY.getCode());
			if(surveyResponse.contains("NPSRatingType")){
				results = Results.getError("Rating should be in range of 0 to 10 for NPS survey.",
						ResultsCode.SAVE_SURVEY_INVALID_KEY.getCode());
			}
			if(surveyResponse.contains("CSATRatingType")){
				results = Results.getError("Rating should be in range of 1 to 5 for CSAT survey.",
						ResultsCode.SAVE_SURVEY_INVALID_KEY.getCode());
			}
			response.setResults(results);
			surveyResponse=convertReqObjectToXMLString(response, SurveyResponse.class);
			surveyResponse = PublicAPIConstant.XML_VERSION + surveyResponse;
			return (SurveyResponse) convertXMLStringtoRespObject(surveyResponse, SurveyResponse.class);
		}
		logger.info("Leaving SurveyRequestProcessor.saveSurveyDetails");
		surveyResponse = PublicAPIConstant.XML_VERSION + surveyResponse;
		return (SurveyResponse) convertXMLStringtoRespObject(surveyResponse, SurveyResponse.class);
	}
	
	
	
    private void mapVoToServiceResponse(SurveyBuyerQuestionDetailsVO surveyDetailsVO, SurveyQuestionnaireDetailResponse response){
        logger.debug(" start of mapVoToServiceResponse");
        CSATSurveyQuestion csat = null;
        NPSSurveyQuestion nps = null;
        BuyerDetails buyerDetail = new BuyerDetails();
        ArrayList<CsatOption> list = new ArrayList<CsatOption>();
        
        
        List<SurveyQuestionnaireDetailVO> voList = surveyDetailsVO.getSurveyQuestion(); 
        response.setSurveyType(voList.get(0).getSurveyType());
       
        for (SurveyQuestionnaireDetailVO listItem : voList) {
        	CsatOption option = new CsatOption();
            if ("csat".equalsIgnoreCase(listItem.getSubSurveyType())){
            	option.setId(listItem.getQuestionId().toString());
            	option.setText(listItem.getText());
            	list.add(option); 
            }else if("nps".equalsIgnoreCase(listItem.getSubSurveyType())){
            	nps = new NPSSurveyQuestion();
                nps.setQuestion(listItem.getText());                
            }
        }
        
        if (!list.isEmpty()){
        	csat = new CSATSurveyQuestion();
        	CsatQuestion csatQuestion = new CsatQuestion();
        	csat.setQuestions(csatQuestion);
        	String buyerDateFound = null;
        	try{
        		buyerDateFound = PropertiesUtils.getPropertyValue(surveyDetailsVO.getBuyerId()+BELOW_AVERAGE);
        	}catch(Exception e){
        		logger.warn(" Key not found in  application properties"+ (surveyDetailsVO.getBuyerId()+BELOW_AVERAGE));
        	}
        	if(buyerDateFound==null){
        		csatQuestion.setBelowAverage(PropertiesUtils.getPropertyValue("default"+BELOW_AVERAGE));
        		csatQuestion.setAboveAverage(PropertiesUtils.getPropertyValue("default"+ABOVE_AVERAGE));
        	}else{
        		csatQuestion.setBelowAverage(PropertiesUtils.getPropertyValue(surveyDetailsVO.getBuyerId()+BELOW_AVERAGE));
        		csatQuestion.setAboveAverage(PropertiesUtils.getPropertyValue(surveyDetailsVO.getBuyerId()+ABOVE_AVERAGE));
        	}
        	csatQuestion.setGeneral(voList.get(0).getInstruction());  
        	csat.setQuestions(csatQuestion);
        	csat.setOptions(list);
        	response.setCsat(csat);
        }	
        
        if( nps!= null){
        	response.setNps(nps);
        }
        
        buyerDetail.setName(surveyDetailsVO.getBuyerName());
        buyerDetail.setLogo(surveyDetailsVO.getLogo());
        response.setBuyerDetails(buyerDetail);
        
        response.setTermsURL(PropertiesUtils.getPropertyValue(SERVICELIVE_TERMS_COND_URL));
        response.setPolicyURL(PropertiesUtils.getPropertyValue(SERVICELIVE_PRIVACY_POLICY));
    }
    
    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public ExtendedSurveyBO getExtendedSurveyBO() {
        return extendedSurveyBO;
    }

    public void setExtendedSurveyBO(ExtendedSurveyBO extendedSurveyBO) {
        this.extendedSurveyBO = extendedSurveyBO;
    }

	public ExtendedSurveyService getExtendedSurveyService() {
		return extendedSurveyService;
	}

	public void setExtendedSurveyService(ExtendedSurveyService extendedSurveyService) {
		this.extendedSurveyService = extendedSurveyService;
	}

}

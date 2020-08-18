/**
 * 
 */
package com.newco.marketplace.business.iBusiness.survey;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.SurveyCSATVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author schavda
 *
 */
public interface ISurveyBO {
	
	public SurveyVO retrieveQuestionsBySurveyId(int iSurveyId)  throws DataServiceException;
	
	public SurveyVO retrieveQuestions(SurveyVO surveyVO, ServiceOrder serviceOrder)  throws DataServiceException;
	
	public String saveResponse(SurveyVO surveyVO,SecurityContext securityContext, ServiceOrder serviceOrder)  throws DataServiceException; 
	
	public ArrayList<SurveyQuesAnsVO> getSurveyResponse(SurveyVO surveyVO) throws DataServiceException;
	
	public  SurveyRatingsVO getRatings(int entityTypeId, int companyId, int resourceId) throws DataServiceException;
	
	//public  List<SurveyRatingsVO> getQuickRatings(Integer buyerID,List<Integer> vendorResourceIDs) throws DataServiceException;
	//21513
	public ServiceOrder getInHomeBuyers(String serviceOrderId) throws DataServiceException;

	public int getAnswer(int questionId, Integer scoreValue) throws DataServiceException;

	public boolean saveCSATData(String serviceOrderId) throws DataServiceException;
	
}

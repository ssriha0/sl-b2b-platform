/**
 * 
 */
package com.newco.marketplace.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.survey.SurveyAnswerVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO; 

/**
 * @author schavda
 *
 */
public class SurveyVOHelper {
	/**
	 * Build the Tree structure for SurveyVO
	 * 		SuverVO --> List of SurveyQuestionVO --> List of SurveyAnswerVO
	 * 
	 * */
	public SurveyVO buildSurveyVO(SurveyVO surveyVO, List surveyQuesAnsList){
		
		int iListSize = surveyQuesAnsList.size();
		SurveyQuesAnsVO surveyQuesAnsVO = null;
		ArrayList<SurveyQuestionVO> surveyQuestions = new ArrayList();
		ArrayList<SurveyAnswerVO> surveyAnswers = new ArrayList();
		SurveyQuestionVO surveyQuestionVO = null;
		SurveyAnswerVO surveyAnswerVO = null;
		int iPrevQuestionId = 0;
		Map<String,Integer> answerIdMap=new HashMap<String,Integer>();
		
		for(int i=0; i<iListSize; i++){
			surveyQuesAnsVO = (SurveyQuesAnsVO)surveyQuesAnsList.get(i);
			answerIdMap.put(surveyQuesAnsVO.getQuestionId()+"|"+surveyQuesAnsVO.getScore(), surveyQuesAnsVO.getAnswerId());
			
			if(i==1){
				surveyVO.setSurveyId(surveyQuesAnsVO.getSurveyId());
				surveyVO.setStatusId(surveyQuesAnsVO.getStatusId());
				surveyVO.setSurveyTypeId(surveyQuesAnsVO.getSurveyTypeId());
				surveyVO.setEntityTypeId(surveyQuesAnsVO.getEntityTypeId());
				surveyVO.setTitle(surveyQuesAnsVO.getTitle());
				surveyVO.setAuthor(surveyQuesAnsVO.getAuthor());
				surveyVO.setInstructions(surveyQuesAnsVO.getInstructions());
			}
			
			if(iPrevQuestionId != surveyQuesAnsVO.getQuestionId()){
				if(i > 1){
					//add the previous element of QuestionVO into ArrayList first and then reset
					surveyQuestionVO.setAnswers(surveyAnswers);
					surveyQuestions.add(surveyQuestionVO);
					surveyAnswers = new ArrayList<SurveyAnswerVO>();
				}
				
				surveyQuestionVO = new SurveyQuestionVO();
				surveyQuestionVO.setQuestionId(surveyQuesAnsVO.getQuestionId());
				surveyQuestionVO.setCategoryId(surveyQuesAnsVO.getCategoryId());
				surveyQuestionVO.setQuestionText(surveyQuesAnsVO.getQuestionText());
				surveyQuestionVO.setQuestionDescription(surveyQuesAnsVO.getQuestionDescription());
				surveyQuestionVO.setWeight(surveyQuesAnsVO.getWeight());
				surveyQuestionVO.setRequired(surveyQuesAnsVO.isRequired());
				surveyQuestionVO.setInstructions(surveyQuesAnsVO.getInstructionsQ());
				surveyQuestionVO.setSortOrder(surveyQuesAnsVO.getSortOrderQ());
			}
			
			surveyAnswerVO = new SurveyAnswerVO();
			surveyAnswerVO.setAnswerId(surveyQuesAnsVO.getAnswerId());
			surveyAnswerVO.setQuestionId(surveyQuesAnsVO.getQuestionId());
			surveyAnswerVO.setAnswerText(surveyQuesAnsVO.getAnswerText());
			surveyAnswerVO.setPriorityId(surveyQuesAnsVO.getPriorityId());
			surveyAnswerVO.setScore(surveyQuesAnsVO.getScore());
			surveyAnswerVO.setSortOrder(surveyQuesAnsVO.getSortOrderA());
			
			surveyAnswers.add(surveyAnswerVO);
			
			iPrevQuestionId =  surveyQuesAnsVO.getQuestionId();

			//Add Last Element
			if(i == iListSize-1){
				surveyQuestionVO.setAnswers(surveyAnswers);
				surveyQuestions.add(surveyQuestionVO);
			}
			
			
			
		}
		surveyVO.setAnswerIdMap(answerIdMap);
		surveyVO.setQuestions(surveyQuestions);
		
		return surveyVO;
	}
	
	

}

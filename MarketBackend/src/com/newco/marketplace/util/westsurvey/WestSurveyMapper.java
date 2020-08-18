package com.newco.marketplace.util.westsurvey;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.newco.marketplace.exception.core.DataServiceException;

import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao;

/**
 * Maps the WestSurveyResponseVO to SurveyVO
 */
public class WestSurveyMapper {

	private final Logger logger = Logger.getLogger(WestSurveyMapper.class);

	private WestSurveyDao westSurveyDaoImpl;

	/**
	 * Method maps WestSurveyResponseVO to SurveyVO and applies business rules
	 * SL-20908 Mapping according to the new input file and new conversion logic, 
	 * the conversion logic is moved to the WestSurveyBO.updateWestSurveyConversionValues() method
	 * @param WestSurveyVO
	 * @return SurveyVO
	 */
	public void mapWestSurveyVOToSurveyVO(WestSurveyVO westVo, SurveyVO surveyVo) throws DataServiceException {
		ArrayList<SurveyQuestionVO> questions = surveyVo.getQuestions();
		for (SurveyQuestionVO question : questions) {
			int questionId = question.getQuestionId();
			if (questionId <= 6) {
				// West Spreadsheet currently gives ratings only for questionId from 1 to 6
				ArrayList<SurveyResponseVO> responses = new ArrayList<SurveyResponseVO>();
				SurveyResponseVO response = new SurveyResponseVO();
				response.setQuestionId(questionId);
				int westRating = 0;
				switch (questionId) {
					case 1:
						//Timeliness Rating
						westRating = westVo.getTimelinessRating();
						break;
					case 2: 
						//Communications Rating
						westRating = westVo.getCommunicationsRating();
						break;
					case 3: 
						//Professional Rating
						westRating = westVo.getProfessionalRating();
						break;
					case 4:
						//Quality Rating
						westRating = westVo.getQualityRating();
						break;
					case 5:
						//Value Rating
						westRating = westVo.getValueRating();
						break;
					case 6:
						//Cleanliness Rating
						westRating = westVo.getCleanlinessRating();
						break;
				}
				int slAnswerId = westSurveyDaoImpl.retrieveSLRatingFromWestRating(questionId, westRating);
				logger.debug("QuestionId = [" + questionId + "] WestRating = [" + westRating + "] SL-Rating = [" + slAnswerId + "]");
				response.setAnswerId(slAnswerId);
				responses.add(response);
				question.setResponses(responses);
			}
		}
		surveyVo.setQuestions(questions);
	}

	public WestSurveyDao getWestSurveyDaoImpl() {
		return westSurveyDaoImpl;
	}

	public void setWestSurveyDaoImpl(WestSurveyDao westSurveyDaoImpl) {
		this.westSurveyDaoImpl = westSurveyDaoImpl;
	}

}

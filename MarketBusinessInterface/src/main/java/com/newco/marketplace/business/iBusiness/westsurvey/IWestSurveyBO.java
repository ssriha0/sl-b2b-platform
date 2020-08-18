package com.newco.marketplace.business.iBusiness.westsurvey;

import com.newco.marketplace.dto.vo.survey.WestImportSummaryVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface IWestSurveyBO {

	/**
	 * Method to save west survey response
	 * @param westSurveyResponse
	 * @return valid soId if rating record actually got processed; null if it was skipped due to some badness in record 
	 * @throws BusinessServiceException, DataServiceException 
	 */
	public String saveSurveyResponse(WestSurveyVO westSurveyVO, Integer buyerId) throws BusinessServiceException;
	
	/**
	 * Method to save the results of ratings imported from one single west survey file
	 * @param westImportSummaryVO
	 * @throws BusinessServiceException
	 */
	public void saveWestImportResults(WestImportSummaryVO westImportSummaryVO) throws BusinessServiceException;
	public String getCSATDelimiter() throws BusinessServiceException;
}

package com.newco.marketplace.persistence.iDao.westsurvey;

import java.util.List;

import com.newco.marketplace.dto.vo.survey.WestImportSummaryVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface WestSurveyDao {

	/**
	 * Retrieve the ServiceLive answer id based on given questionId and West rating as sort oder
	 * @param questionId
	 * @param westRating
	 * @return
	 * @throws DataServiceException
	 */
	public int retrieveSLRatingFromWestRating(int questionId, int westRating) throws DataServiceException;

	/**
	 * Save the summary of rating data imported from a given west file
	 * @param westImportSummaryVO
	 * @return the fileId for inserted file summary record
	 * @throws DataServiceException
	 */
	public int saveWestImportSummary(WestImportSummaryVO westImportSummaryVO) throws DataServiceException;

	/**
	 * Save the details of rating data imported from a given west file
	 * @param fileId
	 * @param soIDs
	 * @throws DataServiceException
	 */
	public void saveWestImportDetails(final int fileId, final List<String> soIDs) throws DataServiceException;
	
	public String getCSATDelimiter() throws DataServiceException;

}

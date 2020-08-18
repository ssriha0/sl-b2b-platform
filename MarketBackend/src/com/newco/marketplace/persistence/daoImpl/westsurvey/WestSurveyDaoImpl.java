package com.newco.marketplace.persistence.daoImpl.westsurvey;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.newco.marketplace.dto.vo.survey.WestImportDetailsVO;
import com.newco.marketplace.dto.vo.survey.WestImportSummaryVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class WestSurveyDaoImpl extends ABaseImplDao implements WestSurveyDao {

	private Logger logger = Logger.getLogger(WestSurveyDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao#retrieveSLRatingFromWestRating(int, int)
	 */
	public int retrieveSLRatingFromWestRating(int questionId, int westRating) throws DataServiceException {
		int slRating = 0;
		Map<String, Integer> slRatingParamMap = new HashMap<String, Integer>();
		slRatingParamMap.put("questionId", questionId);
		slRatingParamMap.put("westRating", westRating);
		try {
			slRating = (Integer)queryForObject("retrieveSLRatingFromWestRating.query", slRatingParamMap);
		} catch (Exception ex) {
			String msg = "Unexpected error while retriving ServiceLive rating based on questionId = [" + questionId +
						 "] and westRating = [" + westRating + "]";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return slRating;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao#saveWestImportSummary(com.newco.marketplace.dto.vo.survey.WestImportSummaryVO)
	 */
	public int saveWestImportSummary(WestImportSummaryVO westImportSummaryVO) throws DataServiceException {
		Integer fileId = null;
		try {
			fileId = (Integer)insert("saveWestImportSummary.insert", westImportSummaryVO);
		} catch (Exception ex) {
			String msg = "Unexpected error while saving west import summary for west survey file: " + westImportSummaryVO.getWestFileName();
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return fileId;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.westsurvey.WestSurveyDao#saveWestImportDetails(java.util.Map)
	 */
	public void saveWestImportDetails(final int fileId, final List<String> soIDs) {
		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (String soId : soIDs) {
					WestImportDetailsVO westImportDetailsVO = new WestImportDetailsVO(fileId, soId);
					executor.insert("saveWestImportDetails.insert", westImportDetailsVO);
				}
				executor.executeBatch();
				return null;
			}
		});
	}

	 /**
     * R15_4 SL-20988 Get the CSAT delimiter configured in DB
     * @param line
     * @param delimiter
     * @return WestSurveyVO
     */
	public String getCSATDelimiter() throws DataServiceException{
		String delimiter = null;
		try {
			delimiter = (String)queryForObject("getCSATDelimiter.query", null);
		} catch (Exception ex) {
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return delimiter;
	}
}

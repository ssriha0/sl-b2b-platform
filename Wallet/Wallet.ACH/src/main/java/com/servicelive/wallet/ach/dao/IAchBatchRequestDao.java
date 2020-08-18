package com.servicelive.wallet.ach.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AutoFundingVO;
import org.springframework.dao.DataAccessException;

import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IAchBatchRequestDao.
 */

public interface IAchBatchRequestDao {

	/**
	 * createAchQueueEntry.
	 * 
	 * @param queueEntry 
	 * 
	 * @return void
	 * 
	 * @throws DataAccessException 
	 */
	public Long createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException;

	/**
	 * updateAchQueueEntry.
	 * 
	 * @param queueEntry 
	 * 
	 * @return void
	 * 
	 * @throws DataAccessException 
	 */
	public void updateAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException;

	public void updateAchCreatedDate(AchProcessQueueEntryVO queueEntry) throws DataAccessException;

    /**
     *
     * @param entityId
     * @param entityTypeId
     * @return
     * @throws com.servicelive.common.exception.DataServiceException
     */
    public AutoFundingVO getAutoFundingVO(Long entityId, Integer entityTypeId) throws DataServiceException;

	/**
	 * @param achProcessId
	 * @param hsAuthRespId
	 * @throws DataServiceException
	 */
	public void updateHsAuthResponse(Long achProcessId, Long hsAuthRespId) throws DataServiceException;
}

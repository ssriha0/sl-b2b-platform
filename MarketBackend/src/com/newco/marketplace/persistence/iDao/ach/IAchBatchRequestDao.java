package com.newco.marketplace.persistence.iDao.ach;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;

/**
 * 
 * @author swamy patsa
 *
 */

public interface IAchBatchRequestDao {
	
	public boolean createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException;
	
	public boolean updateAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException;
	
	public boolean updateAchQueueEntryForWithdrawal(AchProcessQueueEntryVO queueEntry) throws DataAccessException;


}

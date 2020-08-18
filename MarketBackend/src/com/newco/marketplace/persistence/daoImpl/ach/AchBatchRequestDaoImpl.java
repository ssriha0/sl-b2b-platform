package com.newco.marketplace.persistence.daoImpl.ach;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
import com.newco.marketplace.persistence.iDao.ach.IAchBatchRequestDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * 
 * @author swamy patsa
 *
 */

public class AchBatchRequestDaoImpl extends ABaseImplDao implements IAchBatchRequestDao {
	private static final Logger achLogger = Logger.getLogger(AchBatchRequestDaoImpl.class);

	public boolean createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException{
		
		try{
			
			insert("achqueueEntry.query",queueEntry);
		}
		catch(DataAccessException de){
			throw  de;
			
		}
		
		return true;
	}
	
	public int findBusinessTransactionIdByTranTypeId(int TranTypeId){
		int TransactionTypeId = 0;
		
		try{
			
			
		}catch(DataAccessException de){
			
		}
		return TransactionTypeId;
		
	}

	public boolean updateAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException{
		try{
			
			update("achqueueEntry.update",queueEntry);
		}
		catch(DataAccessException de){
			throw  de;
			
		}
		
		return true;
	}
	
	public boolean updateAchQueueEntryForWithdrawal(AchProcessQueueEntryVO queueEntry) throws DataAccessException{
		try{
			
			update("origProcessQueue.update",queueEntry);
		}
		catch(DataAccessException de){
			throw  de;
			
		}
		
		return true;
	}
}

package com.newco.marketplace.business.iBusiness.ach;

import com.newco.marketplace.dto.vo.ach.AchProcessQueueEntryVO;
/**
 * 
 * @author swamy patsa
 *
 */
public interface IAchBatchRequestBO {
	
	public boolean  handleAchRequest(
			AchProcessQueueEntryVO queueEntry, int businessTransactionId, int entityId) throws Exception;
	
	
	
	

}

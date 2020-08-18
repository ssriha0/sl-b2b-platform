package com.servicelive.wallet.ach.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AutoFundingVO;
import org.springframework.dao.DataAccessException;

import com.servicelive.common.ABaseDao;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;

// TODO: Auto-generated Javadoc
/**
 * Class AchBatchRequestDao.
 */

public class AchBatchRequestDao extends ABaseDao implements IAchBatchRequestDao {

	/** achLogger. */
	private static final Logger achLogger = Logger.getLogger(AchBatchRequestDao.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.dao.IAchBatchRequestDao#createAchQueueEntry(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public Long createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException {

		Long achProcessId = (Long) insert("achqueueEntry.query", queueEntry);
		return achProcessId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.dao.IAchBatchRequestDao#updateAchQueueEntry(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public void updateAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException {

		update("achqueueEntry.update", queueEntry);
	}

	public void updateAchCreatedDate(AchProcessQueueEntryVO queueEntry)
			throws DataAccessException {
		update("achqueueEntrySetCreatedDate.update", queueEntry);
	}

    /**
     * @param entityId
     * @param entityTypeId
     * @return
     * @throws com.servicelive.common.exception.DataServiceException
     *
     */
    public AutoFundingVO getAutoFundingVO(Long entityId, Integer entityTypeId) throws DataServiceException {
        AutoFundingVO autoFundingVO = new AutoFundingVO();
        autoFundingVO.setEntityId(entityId);
        autoFundingVO.setEntityTypeId(entityTypeId);
        try{
            return (AutoFundingVO) queryForObject("autoFunding.query", autoFundingVO);
        }catch(Exception e){
            throw new DataServiceException("getAutoFundingVO", e);
        }
    }

	public void updateHsAuthResponse(Long achProcessId, Long hsAuthRespId)throws DataServiceException {
		Map paramMap = new HashMap<String, Long>();
		paramMap.put("respId", hsAuthRespId);
		paramMap.put("achProcessId", achProcessId);
		try{
			update("updateHsAuthResponse.update", paramMap);
		}catch (Exception e) {
			 throw new DataServiceException("exception in updating ach_+process_queue in account_hs_auth_resp table", e);
		}
		
	}


}

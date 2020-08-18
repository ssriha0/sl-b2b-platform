package com.servicelive.wallet.ach.mocks;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AutoFundingVO;
import org.springframework.dao.DataAccessException;

import com.servicelive.wallet.ach.dao.IAchBatchRequestDao;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;

// TODO: Auto-generated Javadoc
/**
 * Class MockAchBatchRequestDao.
 */
public class MockAchBatchRequestDao implements IAchBatchRequestDao {

	/** achInserts. */
	private List<AchProcessQueueEntryVO> achInserts = new ArrayList<AchProcessQueueEntryVO>();

	/** achUpdates. */
	private List<AchProcessQueueEntryVO> achUpdates = new ArrayList<AchProcessQueueEntryVO>();

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.dao.IAchBatchRequestDao#createAchQueueEntry(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public Long createAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException {

		achInserts.add(queueEntry);
		return null;
	}

	/**
	 * getAchInserts.
	 * 
	 * @return List<AchProcessQueueEntryVO>
	 */
	public List<AchProcessQueueEntryVO> getAchInserts() {

		return achInserts;
	}

	/**
	 * getAchUpdates.
	 * 
	 * @return List<AchProcessQueueEntryVO>
	 */
	public List<AchProcessQueueEntryVO> getAchUpdates() {

		return achUpdates;
	}

	/**
	 * reset.
	 * 
	 * @return void
	 */
	public void reset() {

		achInserts.clear();
		achUpdates.clear();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.ach.dao.IAchBatchRequestDao#updateAchQueueEntry(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	public void updateAchQueueEntry(AchProcessQueueEntryVO queueEntry) throws DataAccessException {

		achUpdates.add(queueEntry);
	}

	public void updateAchCreatedDate(AchProcessQueueEntryVO queueEntry)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
	}

    /**
     * @param entityId
     * @param entityTypeId
     * @return
     * @throws com.servicelive.common.exception.DataServiceException
     *
     */
    public AutoFundingVO getAutoFundingVO(Long entityId, Integer entityTypeId) throws DataServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}

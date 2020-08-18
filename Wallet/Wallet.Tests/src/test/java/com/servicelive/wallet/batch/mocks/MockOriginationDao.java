package com.servicelive.wallet.batch.mocks;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO;
import com.servicelive.wallet.batch.ach.dao.NachaDao;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;

/**
 * Class MockOriginationDao.
 */
public class MockOriginationDao extends NachaDao {

	/** nachaProcessLogVO. */
	private NachaProcessLogVO nachaProcessLogVO;
	
	/** nachaProcessLogHistoryVO. */
	private NachaProcessLogHistoryVO nachaProcessLogHistoryVO;
	
	/** queueList. */
	private List<AchProcessQueueEntryVO> queueList = new ArrayList<AchProcessQueueEntryVO>();
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.NachaDao#getAchProcessId(long)
	 */
	@Override
	public Long getAchProcessId(long ledgerEntryId) throws DataServiceException {
		return ledgerEntryId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.NachaDao#orginationProcessUpdatesForAchProceesLog(com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO, com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO)
	 */
	@Override
	public void orginationProcessUpdatesForAchProceesLog(NachaProcessLogVO nachaProcessLogVO, NachaProcessLogHistoryVO nachaProcessLogHistoryVO) throws DataServiceException {

		this.nachaProcessLogHistoryVO = nachaProcessLogHistoryVO;
		this.nachaProcessLogVO = nachaProcessLogVO;

	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ach.dao.NachaDao#orginationProcessUpdatesForAchProceesQueue(com.servicelive.wallet.ach.vo.AchProcessQueueEntryVO)
	 */
	@Override
	public void orginationProcessUpdatesForAchProceesQueue(AchProcessQueueEntryVO achProcessQueueEntryVO) throws DataServiceException{
		queueList.add(achProcessQueueEntryVO);
	}
	
	/**
	 * getNachaProcessLogVO.
	 * 
	 * @return NachaProcessLogVO
	 */
	public NachaProcessLogVO getNachaProcessLogVO(){
		return nachaProcessLogVO;
	}
	
	/**
	 * getNachaProcessLogHistoryVO.
	 * 
	 * @return NachaProcessLogHistoryVO
	 */
	public NachaProcessLogHistoryVO getNachaProcessLogHistoryVO(){
		return nachaProcessLogHistoryVO;
	}
	
	/**
	 * getAchProcessQueueEntryVO.
	 * 
	 * @return List<AchProcessQueueEntryVO>
	 */
	public List<AchProcessQueueEntryVO> getAchProcessQueueEntryVO(){
		return queueList;
	}
}

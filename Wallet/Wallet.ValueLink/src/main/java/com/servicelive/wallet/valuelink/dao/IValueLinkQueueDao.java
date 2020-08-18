package com.servicelive.wallet.valuelink.dao;

import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO.QueueStatus;

// TODO: Auto-generated Javadoc
/**
 * Interface IValueLinkQueueDao.
 */
public interface IValueLinkQueueDao {

	/**
	 * createQueueEntry.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	public void createQueueEntry(ValueLinkQueueEntryVO entry);

	/**
	 * deleteQueueEntry.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	public void deleteQueueEntry(ValueLinkQueueEntryVO entry);

	/**
	 * getQueueEntriesByStatus.
	 * 
	 * @param queueStatus 
	 * 
	 * @return List<ValueLinkQueueEntryVO>
	 */
	public List<ValueLinkQueueEntryVO> getQueueEntriesByStatus(QueueStatus queueStatus);

	/**
	 * getValueLinkEntriesReadyForQueue.
	 * 
	 * @return List<ValueLinkEntryVO>
	 */
	public List<ValueLinkEntryVO> getValueLinkEntriesReadyForQueue();

	/**
	 * updateQueueEntry.
	 * 
	 * @param entry 
	 * 
	 * @return void
	 */
	public void updateQueueEntry(ValueLinkQueueEntryVO entry);

	/**
	 * getQueueEntryById
	 * 
	 * @param fullfillmentEntryId
	 * @return ValueLinkQueueEntryVO
	 * @throws DataServiceException 
	 */
	public ValueLinkQueueEntryVO getQueueEntryById(Long fullfillmentEntryId) throws DataServiceException;

}

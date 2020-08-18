package com.servicelive.wallet.valuelink.dao;

import java.util.HashMap;
import java.util.List;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO.QueueStatus;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkQueueDao.
 */
public class ValueLinkQueueDao extends ABaseDao implements IValueLinkQueueDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#createQueueEntry(com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO)
	 */
	public void createQueueEntry(ValueLinkQueueEntryVO entry) {

		this.insert("valuelink_entry_queue.insert", entry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#deleteQueueEntry(com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO)
	 */
	public void deleteQueueEntry(ValueLinkQueueEntryVO entry) {
		this.delete("deleteQueueEntry", entry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#getQueueEntriesByStatus(com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO.QueueStatus)
	 */
	@SuppressWarnings("unchecked")
	public List<ValueLinkQueueEntryVO> getQueueEntriesByStatus(QueueStatus queueStatus) {

		return (List<ValueLinkQueueEntryVO>) this.queryForList("getQueueEntriesByStatus", queueStatus.intValue());
	}
	
	@SuppressWarnings("unchecked")
	public List<ValueLinkQueueEntryVO> getQueueEntriesByAgeAndStatus(int age, QueueStatus queueStatus) {
		
		HashMap<String,Integer> params = new HashMap<String,Integer>();
		params.put("age", age);
		params.put("queueStatusId", queueStatus.intValue());
		return (List<ValueLinkQueueEntryVO>) this.queryForList("getQueueEntriesByAgeAndStatus", params);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#getValueLinkEntriesReadyForQueue()
	 */
	@SuppressWarnings("unchecked")
	public List<ValueLinkEntryVO> getValueLinkEntriesReadyForQueue() {

		return (List<ValueLinkEntryVO>) this.queryForList("getValueLinkEntriesReadyForQueue");
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#updateQueueEntry(com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO)
	 */
	public void updateQueueEntry(ValueLinkQueueEntryVO entry) {

		this.update("updateQueueEntry", entry);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao#getQueueEntryById(java.lang.Long)
	 */
	public ValueLinkQueueEntryVO getQueueEntryById(Long fullfillmentEntryId) throws DataServiceException {
		return (ValueLinkQueueEntryVO)this.queryForObject("getQueueEntryById", fullfillmentEntryId);
	}

}

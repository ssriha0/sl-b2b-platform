package com.servicelive.wallet.valuelink;

import java.util.List;

import org.junit.Test;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.valuelink.dao.IValueLinkQueueDao;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO;
import com.servicelive.wallet.valuelink.vo.ValueLinkQueueEntryVO.QueueStatus;


/**
 * Class ValueLinkRequestHandlerBOTest.
 */
public class ValueLinkRequestHandlerBOTest extends AbstractTransactionalDataSourceSpringContextTests {

	/**
	 * ValueLinkRequestHandlerBOTest.
	 */
	public ValueLinkRequestHandlerBOTest() {
		this.setAutowireMode(AUTOWIRE_BY_NAME);
	}
	
 	/* (non-Javadoc)
	  * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	  */
	 @Override
 	protected String[] getConfigLocations() {
 		return new String[] {"com/servicelive/wallet/valuelink/testValueLinkApplicationContext.xml"};
 	}
	
 	
 	
//	@Test
//	public void testCreateValueLinkMessages() throws BusinessServiceException {			
//
//		deleteQueueEntriesByStatus(QueueStatus.WAITING);
//		deleteQueueEntriesByStatus(QueueStatus.SENT);
//		
//		// get the queue entries ready for queue
//		IValueLinkQueueDao valueLinkQueueDao = getValueLinkQueueDao();
//		List<ValueLinkEntryVO> entries = valueLinkQueueDao.getValueLinkEntriesReadyForQueue();
//		HashSet<Long> pans = new HashSet<Long>();
//		HashSet<Long> groups = new HashSet<Long>();
//		int count = 0;
//		for( ValueLinkEntryVO entry : entries ) {
//			if( !groups.contains(entry.getFullfillmentGroupId()) && 
//				!pans.contains(entry.getPrimaryAccountNumber())) {
//				count++;
//			}
//			groups.add( entry.getFullfillmentGroupId() );
//			pans.add( entry.getPrimaryAccountNumber() );
//		}
//				
//		// actuall enqueues the entries
//		IValueLinkRequestHandlerBO valueLinkRequestHandler = (IValueLinkRequestHandlerBO)getApplicationContext().getBean("valueLinkRequestHandler");
//		valueLinkRequestHandler.createValueLinkMessages();
//		
//		List<ValueLinkQueueEntryVO> enqueuedEntries = valueLinkQueueDao.getQueueEntriesByStatus(QueueStatus.WAITING);
//		
//		assertEquals(count, enqueuedEntries.size());
//	}
	
	/**
	 * testSendValueLinkMessages.
	 * 
	 * @return void
	 * 
	 * @throws SLBusinessServiceException 
	 */
	@Test
	public void testSendValueLinkMessages() throws SLBusinessServiceException {

		deleteQueueEntriesByStatus(QueueStatus.WAITING);
		deleteQueueEntriesByStatus(QueueStatus.SENT);
		
		IValueLinkQueueDao valueLinkQueueDao = getValueLinkQueueDao();
		IValueLinkRequestHandlerBO valueLinkRequestHandler = (IValueLinkRequestHandlerBO)getApplicationContext().getBean("valueLinkRequestHandler");
				
		valueLinkRequestHandler.createValueLinkMessages();
		
		List<ValueLinkQueueEntryVO> waitingEntries = valueLinkQueueDao.getQueueEntriesByStatus(QueueStatus.WAITING);
		
		valueLinkRequestHandler.sendValueLinkMessages();
		
		List<ValueLinkQueueEntryVO> waitingEntries2 = valueLinkQueueDao.getQueueEntriesByStatus(QueueStatus.WAITING);

		List<ValueLinkQueueEntryVO> sentEntries = valueLinkQueueDao.getQueueEntriesByStatus(QueueStatus.SENT);
		
		assertEquals(0, waitingEntries2.size());
		assertEquals(waitingEntries.size(), sentEntries.size());
		
	}



	/**
	 * getValueLinkQueueDao.
	 * 
	 * @return IValueLinkQueueDao
	 */
	private IValueLinkQueueDao getValueLinkQueueDao() {
		return (IValueLinkQueueDao)getApplicationContext().getBean("valueLinkQueueDao");
	}

	/**
	 * deleteQueueEntriesByStatus.
	 * 
	 * @param status 
	 * 
	 * @return void
	 */
	private void deleteQueueEntriesByStatus( QueueStatus status ) {
		IValueLinkQueueDao valueLinkQueueDao = getValueLinkQueueDao();
		List<ValueLinkQueueEntryVO> entries =valueLinkQueueDao.getQueueEntriesByStatus(status);
		for( ValueLinkQueueEntryVO entry : entries ) {
			valueLinkQueueDao.deleteQueueEntry(entry);
		}
	}

}

package com.servicelive.wallet.batch.mocks;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ptd.dao.IPTDDao;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PtdEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

/**
 * Class MockPTDDao.
 */
public class MockPTDDao implements IPTDDao {

	/** ptdDao. */
	private IPTDDao ptdDao;
	
	/** ptdEntries. */
	private List<PtdEntryVO> ptdEntries = null;
	
	/** ptdErrorEntries. */
	private List<PTDFullfillmentEntryVO> ptdErrorEntries= null;
	
	/** processLog. */
	private PTDProcessLogVO processLog = null;
	
	/**
	 * getPtdEntries.
	 * 
	 * @return List<PtdEntryVO>
	 */
	public List<PtdEntryVO> getPtdEntries() {
		return ptdEntries;
	}

	/**
	 * getPtdErrorEntries.
	 * 
	 * @return List<PTDFullfillmentEntryVO>
	 */
	public List<PTDFullfillmentEntryVO> getPtdErrorEntries() {
		return ptdErrorEntries;
	}

	/**
	 * getProcessLog.
	 * 
	 * @return PTDProcessLogVO
	 */
	public PTDProcessLogVO getProcessLog() {
		return processLog;
	}

	/**
	 * setPtdDao.
	 * 
	 * @param ptdDao 
	 * 
	 * @return void
	 */
	public void setPtdDao(IPTDDao ptdDao) {
		this.ptdDao = ptdDao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#getAllSLTransactions(java.lang.String)
	 */
	public List<Long> getAllSLTransactions(String ptdTime)
			throws DataServiceException {
		
		return ptdDao.getAllSLTransactions(ptdTime);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#getPTDRecordTypeTemplate(java.lang.String)
	 */
	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType)
			throws DataServiceException {
		return ptdDao.getPTDRecordTypeTemplate(recordType);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDErrorTransactions(java.util.List)
	 */
	public void insertPTDErrorTransactions(
			List<PTDFullfillmentEntryVO> ptdErrorTransactionList)
			throws DataServiceException {
		ptdErrorEntries = ptdErrorTransactionList;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDProcessLog(com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO)
	 */
	public int insertPTDProcessLog(PTDProcessLogVO ptdProcessLogVO)
			throws DataServiceException {
		processLog = ptdProcessLogVO;
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDRecords(java.util.ArrayList)
	 */
	public void insertPTDRecords(ArrayList<PtdEntryVO> ptdEntryList) {
		ptdEntries = ptdEntryList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#updatePTDInfo(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public int updatePTDInfo(ValueLinkEntryVO fullfillmentEntryVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#updatePTDProcessLogUpdateCount(com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO)
	 */
	public int updatePTDProcessLogUpdateCount(PTDProcessLogVO ptdProcessLogVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

}

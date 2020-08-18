package com.servicelive.wallet.batch.ptd.dao;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PtdEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDDao.
 */
public class PTDDao extends ABaseDao implements IPTDDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#getAllSLTransactions(java.lang.String)
	 */
	public List<java.lang.Long> getAllSLTransactions(String ptdTime) throws DataServiceException {

		List<Long> myList = null;
		try {
			myList = queryForList("sltransactions.select", ptdTime);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getAllSLTransactions()", ex);
		}
		return myList;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#getPTDRecordTypeTemplate(java.lang.String)
	 */
	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException {

		List<PTDRecordTypeVO> ptdRecordType = null;
		try {
			ptdRecordType = queryForList("ptdRecordTypeQuery.select", recordType);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getPTDRecordTypeTemplate()", ex);
		}
		return ptdRecordType;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDErrorTransactions(java.util.List)
	 */
	public void insertPTDErrorTransactions(List<PTDFullfillmentEntryVO> ptdErrorTransactionList) throws DataServiceException {

		try {
			if (ptdErrorTransactionList != null && !ptdErrorTransactionList.isEmpty()) {
				for (PTDFullfillmentEntryVO ptdFullfillmentEntryVO : ptdErrorTransactionList)
					insert("ptdErrorTransactions.insert", ptdFullfillmentEntryVO);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.insertPTDErrorTransactions()", ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDProcessLog(com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO)
	 */
	public int insertPTDProcessLog(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException {

		Integer id = null;
		try {
			id = (Integer) insert("ptdprocessLog.insert", ptdProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.insertPTDProcessLog()", ex);
		}
		return id;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#insertPTDRecords(java.util.ArrayList)
	 */
	public void insertPTDRecords(ArrayList<PtdEntryVO> PtdEntryVOList) {

		for (PtdEntryVO ptdEntryVO : PtdEntryVOList) {
			insert("ptdFileTransactions.insert", ptdEntryVO);
		}
	}



	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#updatePTDInfo(com.servicelive.wallet.valuelink.vo.ValueLinkEntryVO)
	 */
	public int updatePTDInfo(ValueLinkEntryVO fullfillmentEntryVO) throws DataServiceException {

		int q = 0;
		try {
			q = update("fullfillmentEntryPtd.update", fullfillmentEntryVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDInfo()", ex);
		}
		return q;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.ptd.dao.IPTDDao#updatePTDProcessLogUpdateCount(com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO)
	 */
	public int updatePTDProcessLogUpdateCount(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException {

		int q = 0;
		try {
			q = update("ptdprocessLog.update", ptdProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDProcessLogUpdateCount()", ex);
		}
		return q;

	}

}

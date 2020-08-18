package com.servicelive.wallet.batch.ptd.dao;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PtdEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPTDDao.
 */
public interface IPTDDao {

	/**
	 * Gets the all sl transactions.
	 * 
	 * @param ptdTime 
	 * 
	 * @return the all sl transactions
	 * 
	 * @throws DataServiceException 
	 */
	public List<java.lang.Long> getAllSLTransactions(String ptdTime) throws DataServiceException;

	/**
	 * Gets the pTD record type template.
	 * 
	 * @param recordType 
	 * 
	 * @return the pTD record type template
	 * 
	 * @throws DataServiceException 
	 */
	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException;

	/**
	 * Insert ptd error transactions.
	 * 
	 * @param ptdErrorTransactionList 
	 * 
	 * @throws DataServiceException 
	 */
	public void insertPTDErrorTransactions(List<PTDFullfillmentEntryVO> ptdErrorTransactionList) throws DataServiceException;

	/**
	 * Insert ptd process log.
	 * 
	 * @param ptdProcessLogVO 
	 * 
	 * @return the int
	 * 
	 * @throws DataServiceException 
	 */
	public int insertPTDProcessLog(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException;

	/**
	 * Insert ptd records.
	 * 
	 * @param ptdEntryList 
	 */
	public void insertPTDRecords(ArrayList<PtdEntryVO> ptdEntryList);

	/**
	 * Update ptd info.
	 * 
	 * @param fullfillmentEntryVO 
	 * 
	 * @return the int
	 * 
	 * @throws DataServiceException 
	 */
	public int updatePTDInfo(ValueLinkEntryVO fullfillmentEntryVO) throws DataServiceException;

	/**
	 * Update ptd process log update count.
	 * 
	 * @param ptdProcessLogVO 
	 * 
	 * @return the int
	 * 
	 * @throws DataServiceException 
	 */
	public int updatePTDProcessLogUpdateCount(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException;
}

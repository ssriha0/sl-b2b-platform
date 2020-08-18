package com.newco.marketplace.persistence.iDao.ptd;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentVLAccountsVO;
import com.newco.marketplace.dto.vo.ptd.PTDFullfillmentEntryVO;
import com.newco.marketplace.dto.vo.ptd.PTDProcessLogVO;
import com.newco.marketplace.dto.vo.ptd.PTDRecordTypeVO;
import com.newco.marketplace.dto.vo.ptd.PtdEntryVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface PTDDao {
	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException;
	public int insertPTDProcessLog(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException ;
	public int updatePTDProcessLogUpdateCount(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException ;
	public int updatePTDInfo(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException;
 	public void insertPTDErrorTransactions(List<PTDFullfillmentEntryVO> ptdErrorTransactionList) throws DataServiceException;
 	public List<FullfillmentEntryVO> updateFullFillmentEntry() throws DataServiceException;
 	public int updatePTDReconcilationStatus(Integer status) throws DataServiceException;
	public List<java.lang.Long> getAllSLTransactions(String ptdTime) throws DataServiceException;
	public List<PTDFullfillmentEntryVO> getAllPTDErrorTransactions() throws DataServiceException;
	public void deletePTDErrorTransaction(Long ptdFullfillmentEntryID) throws DataServiceException;
	public List<PTDFullfillmentEntryVO> getMyPTDErrorTransactions(Long ptdFullfillmentEntryID) throws DataServiceException ;
	public void deleteAllPTDErrorTransactions() throws DataServiceException ;
	public void updatePTDErrorTransactionEmailFlag() throws DataServiceException ;
	public void deleteMyPTDErrorTransactions(List ptdTransactionIds) throws DataServiceException ;
   	public FullfillmentVLAccountsVO getV1V2Details(String v1AccountNumber) throws DataServiceException ;
	public int updateMyPTDTransactionStatus(PTDFullfillmentEntryVO ptdFullfillmentEntryVO) throws DataServiceException ;
	public void updateFullfillmentGroupReconciliation(long fullfillmentGroupId)throws DataServiceException;
 	public void updateFullfillmentEntryReconciliation(long fullfillmentEntryId, long reconciliationInd) throws DataServiceException;
	public void insertPTDRecords(ArrayList<PtdEntryVO> ptdEntryList);
	public FullfillmentEntryVO selectFulfillmentById(long fullfillmentEntryId); 	
}

package com.newco.marketplace.persistence.daoImpl.ptd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.newco.marketplace.dto.vo.fullfillment.FullfillmentVLAccountsVO;
import com.newco.marketplace.dto.vo.ptd.PTDFullfillmentEntryVO;
import com.newco.marketplace.dto.vo.ptd.PTDProcessLogVO;
import com.newco.marketplace.dto.vo.ptd.PTDRecordTypeVO;
import com.newco.marketplace.dto.vo.ptd.PtdEntryVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.ptd.PTDDao;

public class PTDDaoImpl extends MPBaseDaoImpl implements PTDDao {

	public List<PTDRecordTypeVO> getPTDRecordTypeTemplate(String recordType) throws DataServiceException {
		List<PTDRecordTypeVO> ptdRecordType = null;
		try {
			ptdRecordType = queryForList("ptdRecordTypeQuery.select", recordType);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getPTDRecordTypeTemplate()", ex);
		}
		return ptdRecordType;
	}

	public int insertPTDProcessLog(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException {
		Integer id = null;
		try {
			id = (Integer) insert("ptdprocessLog.insert", ptdProcessLogVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in PTDDaoImpl.insertPTDProcessLog()", ex);
		}
		return id;
	}

	public int updatePTDInfo(FullfillmentEntryVO fullfillmentEntryVO) throws DataServiceException {
		int q = 0;
		try {
			q = update("fullfillmentEntryPtd.update", fullfillmentEntryVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDInfo()", ex);
		}
		return q;
	}

	public void insertPTDErrorTransactions(List<PTDFullfillmentEntryVO> ptdErrorTransactionList) throws DataServiceException {
		Long id = null;
		try {
			if (ptdErrorTransactionList != null && ptdErrorTransactionList.size() > 0) {
				for (PTDFullfillmentEntryVO ptdFullfillmentEntryVO : ptdErrorTransactionList)
					id = (Long) insert("ptdErrorTransactions.insert", ptdFullfillmentEntryVO);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataServiceException("Exception occurred in PTDDaoImpl.insertPTDErrorTransactions()", ex);
		}
	}

	public List<FullfillmentEntryVO> updateFullFillmentEntry() throws DataServiceException {
		List<FullfillmentEntryVO> fullfillmentEntryVOList;
		try {
			fullfillmentEntryVOList = queryForList("fulllfillmentEntryPtd.select", null);
			for (FullfillmentEntryVO fullfillmentEntryVO : fullfillmentEntryVOList) {
				update("fulllfillmentEntryPtd.update1", fullfillmentEntryVO);
			}
			update("fulllfillmentEntryPtd.update2", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updateFulFillmentEntry()", ex);
		}
		return fullfillmentEntryVOList;
	}

	public int updatePTDReconcilationStatus(Integer status) throws DataServiceException {
		int q = 0;
		try {
			q = update("fulllfillmentEntryPtdMark.update", status);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDReconcilationStatus()", ex);
		}
		return q;
	}

	public List<java.lang.Long> getAllSLTransactions(String ptdTime) throws DataServiceException {
		List<Long> myList = null;
		try {
			myList = queryForList("sltransactions.select", ptdTime);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getAllSLTransactions()", ex);
		}
		return myList;
	}

	public List<PTDFullfillmentEntryVO> getAllPTDErrorTransactions() throws DataServiceException {
		List<PTDFullfillmentEntryVO> myList = null;
		try {
			myList = queryForList("ptdErrorTransactions.select", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getAllPTDErrorTransactions()", ex);
		}
		return myList;
	}

	public List<PTDFullfillmentEntryVO> getMyPTDErrorTransactions(Long ptdFullfillmentEntryID) throws DataServiceException {
		List<PTDFullfillmentEntryVO> myList = null;
		try {
			myList = queryForList("ptdErrorTransaction.select", ptdFullfillmentEntryID);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getMyPTDErrorTransactions()", ex);
		}
		return myList;
	}

	public void deletePTDErrorTransaction(Long ptdFullfillmentEntryID) throws DataServiceException {
		try {
			delete("ptdTransactions.delete", ptdFullfillmentEntryID);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.deletePTDErrorTransaction()", ex);
		}
	}

	public void deleteAllPTDErrorTransactions() throws DataServiceException {
		try {
			int d = delete("ptdTransactionsAll.delete", null);
			System.out.println(d);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.deleteAllPTDErrorTransactions()", ex);
		}
	}

	public void deleteMyPTDErrorTransactions(List ptdTransactionIds) throws DataServiceException {
		try {
			if (ptdTransactionIds != null && ptdTransactionIds.size() > 0) {
				int d = delete("ptdTransactionsMultiple.delete", ptdTransactionIds);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.deleteMyPTDErrorTransactions()", ex);
		}
	}

	public void updatePTDErrorTransactionEmailFlag() throws DataServiceException {
		try {
			int d = update("ptdTransactionEmailSent.update", null);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDErrorTransactionEmailFlag()", ex);
		}
	}

	public FullfillmentVLAccountsVO getV1V2Details(String v1AccountNumber) throws DataServiceException {
		FullfillmentVLAccountsVO fullfillmentVLAccountsVO = null;
		try {
			fullfillmentVLAccountsVO = (FullfillmentVLAccountsVO) queryForObject("v1Accounts.select", v1AccountNumber);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.getV1V2Details()", ex);
		}
		return fullfillmentVLAccountsVO;
	}

	public int updateMyPTDTransactionStatus(PTDFullfillmentEntryVO ptdFullfillmentEntryVO) throws DataServiceException {
		int q = 0;
		try {
			q = update("fulllfillmentPtdMarkMyTran.update", ptdFullfillmentEntryVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updateMyPTDTransactionStatus()", ex);
		}
		return q;
	}

	public void updateFullfillmentGroupReconciliation(long fullfillmentGroupId) throws DataServiceException {
		try {
			update("fullfillmentGroupReconciliation.update", fullfillmentGroupId);
		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updateFullfillmentGroupReconciliation()", e);
		}
	}

	public void updateFullfillmentEntryReconciliation(long fullfillmentEntryId, long reconciledInd) throws DataServiceException {
		try {
			HashMap<String, Long> inputFullFillmentMap = new HashMap<String, Long>();
			inputFullFillmentMap.put("fullfillmentEntryId", fullfillmentEntryId);
			inputFullFillmentMap.put("reconciledInd", reconciledInd);
			update("fullfillmentEntryReconciliation.update", inputFullFillmentMap);
		} catch (Exception e) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updateFullfillmentEntryReconciliation()", e);
		}
	}

	public void insertPTDRecords(ArrayList<PtdEntryVO> PtdEntryVOList) {
		for (PtdEntryVO ptdEntryVO : PtdEntryVOList) {
			insert("ptdFileTransactions.insert", ptdEntryVO);
		}
	}

	public int updatePTDProcessLogUpdateCount(PTDProcessLogVO ptdProcessLogVO) throws DataServiceException {
		int q = 0;
		try {
			q = update("ptdprocessLog.update", ptdProcessLogVO);
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in PTDDaoImpl.updatePTDProcessLogUpdateCount()", ex);
		}
		return q;

	}

	public FullfillmentEntryVO selectFulfillmentById(long fullfillmentEntryId){
		FullfillmentEntryVO feVo = null;
		feVo = (FullfillmentEntryVO) queryForObject("fullfillmentEntry.select", fullfillmentEntryId);
		return feVo;
	}
}

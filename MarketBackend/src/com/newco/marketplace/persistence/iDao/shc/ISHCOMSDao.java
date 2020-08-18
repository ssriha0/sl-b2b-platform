package com.newco.marketplace.persistence.iDao.shc;


import java.util.ArrayList;
import java.util.List;
import com.newco.marketplace.dto.vo.ledger.GLSummaryVO;
import com.newco.marketplace.exception.core.DataServiceException;

public interface ISHCOMSDao {
	/**
	 * Method to get the summary accounts on all unprocessed records oms sku's
	 * @return
	 */
	public List<GLSummaryVO> getOMSSkuSummary(int statusIds);
	public List<GLSummaryVO> getOMSCommissionLocs(ArrayList<String> soIDs);
	public void updateOMSSKU(Integer glPorcessId, List<String> soIdList);
	public ArrayList<GLSummaryVO>  getAccountsCommissions();	
	public void insertSHCGLHistoryRecord(Integer glProcessLogId) throws DataServiceException;

}

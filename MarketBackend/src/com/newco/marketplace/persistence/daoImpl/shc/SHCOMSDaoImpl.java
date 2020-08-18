package com.newco.marketplace.persistence.daoImpl.shc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.newco.marketplace.dto.vo.ledger.GLSummaryVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.shc.ISHCOMSDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class SHCOMSDaoImpl extends ABaseImplDao implements ISHCOMSDao {

	
	public List<GLSummaryVO> getOMSSkuSummary(int statusId){

		List<GLSummaryVO> skuSummaryVO = null;
		skuSummaryVO = (List<GLSummaryVO>) queryForList("shc_gl.query",	new Integer(statusId));
		return skuSummaryVO;
	}

	public List<GLSummaryVO> getOMSCommissionLocs(ArrayList<String> soIDs) {
		List<GLSummaryVO> commissionsLocs = new ArrayList<GLSummaryVO>();
		if (soIDs.size() > 0){
			commissionsLocs = (List<GLSummaryVO>) queryForList("shc_gl_commission_loc.query",	soIDs);
		}	
		return commissionsLocs;
	}
	
	public void updateOMSSKU(Integer glProcessId, List<String> soIdList) {
		Map updateOMSData = new HashMap<String, Integer>();
		updateOMSData.put("glProcessId", new Integer(glProcessId));
		updateOMSData.put("statusId", new Integer(OrderConstants.CLOSED_STATUS));
		updateOMSData.put("soIdList", soIdList);
		update("shc_gl.update.query",updateOMSData);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<GLSummaryVO>  getAccountsCommissions()
	{
		ArrayList<GLSummaryVO> commissions = new ArrayList<GLSummaryVO>();
		commissions =  (ArrayList<GLSummaryVO>) queryForList("shc_gl_commission.query", 0);
		return commissions;
	}

	public void insertSHCGLHistoryRecord(Integer glProcessLogId) throws DataServiceException {
		insert("insertSHCGLHistory.storeproc", glProcessLogId);
	}

}

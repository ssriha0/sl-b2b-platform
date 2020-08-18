package com.newco.marketplace.dto.vo.ledger;

import java.util.List;

import com.newco.marketplace.vo.PaginationVO;
import com.sears.os.vo.SerializableBaseVO;

public class AccountHistoryResultVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<AccountHistoryVO> acctHistory = null;
	private  PaginationVO  paginationVO ;
	private String exportMessageCheck;
	private String walletMaxExportLimit;
	private Integer totalCount;
	private String maxSearchDaysWalletHistory;
	
		
	
	public String getMaxSearchDaysWalletHistory() {
		return maxSearchDaysWalletHistory;
	}
	public void setMaxSearchDaysWalletHistory(String maxSearchDaysWalletHistory) {
		this.maxSearchDaysWalletHistory = maxSearchDaysWalletHistory;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public String getWalletMaxExportLimit() {
		return walletMaxExportLimit;
	}
	public void setWalletMaxExportLimit(String walletMaxExportLimit) {
		this.walletMaxExportLimit = walletMaxExportLimit;
	}
	public String getExportMessageCheck() {
		return exportMessageCheck;
	}
	public void setExportMessageCheck(String exportMessageCheck) {
		this.exportMessageCheck = exportMessageCheck;
	}
	public List<AccountHistoryVO> getAcctHistory() {
		return acctHistory;
	}
	public void setAcctHistory(List<AccountHistoryVO> acctHistory) {
		this.acctHistory = acctHistory;
	}
	public PaginationVO getPaginationVO() {
		return paginationVO;
	}
	public void setPaginationVO(PaginationVO paginationVO) {
		this.paginationVO = paginationVO;
	}
	
	

}


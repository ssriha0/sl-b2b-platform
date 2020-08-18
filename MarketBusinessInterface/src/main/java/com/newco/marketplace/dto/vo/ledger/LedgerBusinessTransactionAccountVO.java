package com.newco.marketplace.dto.vo.ledger;

import java.util.List;

import com.newco.marketplace.dto.vo.ach.NachaProcessQueueVO;
import com.sears.os.vo.SerializableBaseVO;

public class LedgerBusinessTransactionAccountVO extends SerializableBaseVO{
	
	private List<NachaProcessQueueVO> ledgerEntryIdList;
	private Long accountId;
	private Account oldAccount;
	private Account newAccount;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public List<NachaProcessQueueVO> getLedgerEntryIdList() {
		return ledgerEntryIdList;
	}
	public void setLedgerEntryIdList(List<NachaProcessQueueVO> ledgerEntryIdList) {
		this.ledgerEntryIdList = ledgerEntryIdList;
	}
	public Account getOldAccount() {
		return oldAccount;
	}
	public void setOldAccount(Account oldAccount) {
		this.oldAccount = oldAccount;
	}
	public Account getNewAccount() {
		return newAccount;
	}
	public void setNewAccount(Account newAccount) {
		this.newAccount = newAccount;
	}
	
}

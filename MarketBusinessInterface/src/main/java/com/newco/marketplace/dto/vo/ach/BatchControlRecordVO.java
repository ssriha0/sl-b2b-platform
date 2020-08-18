package com.newco.marketplace.dto.vo.ach;
  
/**  
* BatchControlRecordVO.java - This class is a template class for Batch Control Record as specified by NACHA
* 
* @author  Siva
* @version 1.0  
*/  
public class BatchControlRecordVO extends NachaGenericRecordVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2610603337293181770L;
	private long entryHash;
	private long totalCreditAmount;
	private long totalDebitAmount;
	
	public long getTotalCreditAmount() {
		return totalCreditAmount;
	}
	public void setTotalCreditAmount(long totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}
	public long getTotalDebitAmount() {
		return totalDebitAmount;
	}
	public void setTotalDebitAmount(long totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}
	public long getEntryHash() {
		return entryHash;
	}
	public void setEntryHash(long entryHash) {
		this.entryHash = entryHash;
	}

}

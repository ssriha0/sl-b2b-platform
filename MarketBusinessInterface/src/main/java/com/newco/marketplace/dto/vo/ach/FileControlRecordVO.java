package com.newco.marketplace.dto.vo.ach;

/**  
* FileControlRecordVO.java - This class represents the file control record as specified by NACHA
* 
* @author  Siva
* @version 1.0  
*/
public class FileControlRecordVO extends NachaGenericRecordVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5601409914139601130L;
	
	private String totalCreditAmount;
	
	private String totalDebitAmount;

	public String getTotalCreditAmount() {
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(String totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}

	public String getTotalDebitAmount() {
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(String totalDebitAmount) {
		this.totalDebitAmount = totalDebitAmount;
	}
	

	


}

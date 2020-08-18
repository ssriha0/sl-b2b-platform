package com.newco.marketplace.dto.vo.ach;

/**  
* EntryDetailRecordVO.java - This class represents the entry detail record as specified by NACHA
* 
* @author  Siva
* @version 1.0  
*/
public class EntryDetailRecordVO  extends NachaGenericRecordVO {
/**
	 * 
	 */
	private static final long serialVersionUID = 5299455215743371452L;
private String transactionType; // holds "CREDIT" ( for CREDIT ) or "DEBIT" (for DEBIT)
private String transactionCode;
private String immediateDestination;
private String entryDetailNumber;
private boolean isAddendaAvailable;


public String getImmediateDestination() {
	return immediateDestination;
}
public void setImmediateDestination(String immediateDestination) {
	this.immediateDestination = immediateDestination;
}
public String getTransactionCode() {
	return transactionCode;
}
public void setTransactionCode(String transactionCode) {
	this.transactionCode = transactionCode;
}
public String getTransactionType() {
	return transactionType;
}
public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}

/*public Object clone() {
    try {
        return super.clone();
    }
    catch (CloneNotSupportedException e) {
        // This should never happen
        throw new InternalError(e.toString());
    }
}*/
//public Object clone() {
//	EntryDetailRecordVO entryDetail = new EntryDetailRecordVO();
//	entryDetail.setFieldDetailVO(fieldDetailVO);
//	entryDetail.setAddendaAvailable(isAddendaAvailable);
//	entryDetail.setEntryDetailNumber(entryDetailNumber);
//	entryDetail.setEntryDetailRecord(entryDetailRecord);
//	entryDetail.setImmediateDestination(immediateDestination);
//	entryDetail.setTransactionCode(transactionCode);
//	entryDetail.setTransactionType(transactionType);
//	return entryDetail;
//}

public String getEntryDetailNumber() {
	return entryDetailNumber;
}
public void setEntryDetailNumber(String entryDetailNumber) {
	this.entryDetailNumber = entryDetailNumber;
}
public boolean isAddendaAvailable() {
	return isAddendaAvailable;
}
public void setAddendaAvailable(boolean isAddendaAvailable) {
	this.isAddendaAvailable = isAddendaAvailable;
}

}

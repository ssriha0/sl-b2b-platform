package com.newco.marketplace.dto.vo.ach;

import com.sears.os.vo.SerializableBaseVO;

/**  
* EntryRecordVO.java - This class aggregates the Entry Detail and Addenda records
* 
* @author  Siva
* @version 1.0  
*/
public class EntryRecordVO extends SerializableBaseVO{
/**
	 * 
	 */
	private static final long serialVersionUID = -3574531301694934857L;
private EntryDetailRecordVO entryDetailRecord;
private AddendaRecordVO addendaRecord;

public EntryDetailRecordVO getEntryDetailRecord() {
	return entryDetailRecord;
}
public void setEntryDetailRecord(EntryDetailRecordVO entryDetailRecord) {
	this.entryDetailRecord = entryDetailRecord;
}
public AddendaRecordVO getAddendaRecord() {
	return addendaRecord;
}
public void setAddendaRecord(AddendaRecordVO addendaRecord) {
	this.addendaRecord = addendaRecord;
}




}

package com.newco.marketplace.dto.vo.ach;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

/**  
* BatchRecordVO.java - This class aggregates BatchHeader, BatchControl and an arraylist 
* of EntryRecords
* 
* @author  Siva
* @version 1.0  
*/

public class BatchRecordVO extends SerializableBaseVO{

	private static final long serialVersionUID = 7854195068381498747L;
	private BatchHeaderRecordVO batchHeaderRecord;
	private BatchControlRecordVO batchControlRecord;
	private ArrayList<EntryRecordVO> entryRecords;

public BatchHeaderRecordVO getBatchHeaderRecord() {
	return batchHeaderRecord;
}
public void setBatchHeaderRecord(BatchHeaderRecordVO batchHeaderRecord) {
	this.batchHeaderRecord = batchHeaderRecord;
}
public BatchControlRecordVO getBatchControlRecord() {
	return batchControlRecord;
}
public void setBatchControlRecord(BatchControlRecordVO batchControlRecord) {
	this.batchControlRecord = batchControlRecord;
}
public ArrayList<EntryRecordVO> getEntryRecords() {
	return entryRecords;
}
public void setEntryRecords(ArrayList<EntryRecordVO> entryRecords) {
	this.entryRecords = entryRecords;
}




}

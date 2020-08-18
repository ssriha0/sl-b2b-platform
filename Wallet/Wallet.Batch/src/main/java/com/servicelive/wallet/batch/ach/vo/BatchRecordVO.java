package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * BatchRecordVO.java - This class aggregates BatchHeader, BatchControl and an arraylist
 * of EntryRecords
 * 
 * @author Siva
 * @version 1.0
 */

public class BatchRecordVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7854195068381498747L;

	/** The batch control record. */
	private BatchControlRecordVO batchControlRecord;

	/** The batch header record. */
	private BatchHeaderRecordVO batchHeaderRecord;

	/** The entry records. */
	private ArrayList<EntryRecordVO> entryRecords;
	
	private int batchEntryTypeId;

	public int getBatchEntryTypeId() {
		return batchEntryTypeId;
	}

	public void setBatchEntryTypeId(int batchEntryTypeId) {
		this.batchEntryTypeId = batchEntryTypeId;
	}

	/**
	 * Gets the batch control record.
	 * 
	 * @return the batch control record
	 */
	public BatchControlRecordVO getBatchControlRecord() {

		return batchControlRecord;
	}

	/**
	 * Gets the batch header record.
	 * 
	 * @return the batch header record
	 */
	public BatchHeaderRecordVO getBatchHeaderRecord() {

		return batchHeaderRecord;
	}

	/**
	 * Gets the entry records.
	 * 
	 * @return the entry records
	 */
	public ArrayList<EntryRecordVO> getEntryRecords() {

		return entryRecords;
	}

	/**
	 * Sets the batch control record.
	 * 
	 * @param batchControlRecord the new batch control record
	 */
	public void setBatchControlRecord(BatchControlRecordVO batchControlRecord) {

		this.batchControlRecord = batchControlRecord;
	}

	/**
	 * Sets the batch header record.
	 * 
	 * @param batchHeaderRecord the new batch header record
	 */
	public void setBatchHeaderRecord(BatchHeaderRecordVO batchHeaderRecord) {

		this.batchHeaderRecord = batchHeaderRecord;
	}

	/**
	 * Sets the entry records.
	 * 
	 * @param entryRecords the new entry records
	 */
	public void setEntryRecords(ArrayList<EntryRecordVO> entryRecords) {

		this.entryRecords = entryRecords;
	}

}

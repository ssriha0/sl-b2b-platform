package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * EntryRecordVO.java - This class aggregates the Entry Detail and Addenda records
 * 
 * @author Siva
 * @version 1.0
 */
public class EntryRecordVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3574531301694934857L;

	/** The addenda record. */
	private AddendaRecordVO addendaRecord;

	/** The entry detail record. */
	private EntryDetailRecordVO entryDetailRecord;

	/**
	 * Gets the addenda record.
	 * 
	 * @return the addenda record
	 */
	public AddendaRecordVO getAddendaRecord() {

		return addendaRecord;
	}

	/**
	 * Gets the entry detail record.
	 * 
	 * @return the entry detail record
	 */
	public EntryDetailRecordVO getEntryDetailRecord() {

		return entryDetailRecord;
	}

	/**
	 * Sets the addenda record.
	 * 
	 * @param addendaRecord the new addenda record
	 */
	public void setAddendaRecord(AddendaRecordVO addendaRecord) {

		this.addendaRecord = addendaRecord;
	}

	/**
	 * Sets the entry detail record.
	 * 
	 * @param entryDetailRecord the new entry detail record
	 */
	public void setEntryDetailRecord(EntryDetailRecordVO entryDetailRecord) {

		this.entryDetailRecord = entryDetailRecord;
	}

}

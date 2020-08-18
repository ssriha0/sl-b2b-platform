package com.servicelive.wallet.batch.ptd.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDRecordVO.
 */
public class PTDRecordVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ptd record type list. */
	private List<PTDRecordTypeVO> ptdRecordTypeList;

	/** The record identifier. */
	String recordIdentifier;

	/**
	 * Gets the hash.
	 * 
	 * @param fieldDetails 
	 * 
	 * @return the hash
	 */
	public HashMap<String, PTDRecordTypeVO> getHash(List<PTDRecordTypeVO> fieldDetails) {

		HashMap<String, PTDRecordTypeVO> hashMap = new HashMap<String, PTDRecordTypeVO>();
		for (int r = 0; r < fieldDetails.size(); r++) {
			PTDRecordTypeVO fd = (PTDRecordTypeVO) fieldDetails.get(r);
			hashMap.put(fd.getFieldName(), fd);
		}
		return hashMap;
	}

	/**
	 * Gets the ptd record type list.
	 * 
	 * @return the ptd record type list
	 */
	public List<PTDRecordTypeVO> getPtdRecordTypeList() {

		return ptdRecordTypeList;
	}

	/**
	 * Gets the record identifier.
	 * 
	 * @return the record identifier
	 */
	public String getRecordIdentifier() {

		return recordIdentifier;
	}

	/**
	 * Sets the ptd record type list.
	 * 
	 * @param ptdRecordTypeList the new ptd record type list
	 */
	public void setPtdRecordTypeList(List<PTDRecordTypeVO> ptdRecordTypeList) {

		this.ptdRecordTypeList = ptdRecordTypeList;
	}

	/**
	 * Sets the record identifier.
	 * 
	 * @param recordIdentifier the new record identifier
	 */
	public void setRecordIdentifier(String recordIdentifier) {

		this.recordIdentifier = recordIdentifier;
	}
}

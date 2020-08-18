package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaFileVO.
 */
public class NachaFileVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3889811245637825077L;

	/** The batch records. */
	private List<BatchRecordVO> batchRecords;

	/** The file control record vo. */
	private FileControlRecordVO fileControlRecordVO;

	/** The file header record vo. */
	private FileHeaderRecordVO fileHeaderRecordVO;

	/**
	 * Gets the batch records.
	 * 
	 * @return the batch records
	 */
	public List<BatchRecordVO> getBatchRecords() {

		return batchRecords;
	}

	/**
	 * Gets the file control record vo.
	 * 
	 * @return the file control record vo
	 */
	public FileControlRecordVO getFileControlRecordVO() {

		return fileControlRecordVO;
	}

	/**
	 * Gets the file header record vo.
	 * 
	 * @return the file header record vo
	 */
	public FileHeaderRecordVO getFileHeaderRecordVO() {

		return fileHeaderRecordVO;
	}

	/**
	 * Sets the batch records.
	 * 
	 * @param batchRecords the new batch records
	 */
	public void setBatchRecords(List<BatchRecordVO> batchRecords) {

		this.batchRecords = batchRecords;
	}

	/**
	 * Sets the file control record vo.
	 * 
	 * @param fileControlRecordVO the new file control record vo
	 */
	public void setFileControlRecordVO(FileControlRecordVO fileControlRecordVO) {

		this.fileControlRecordVO = fileControlRecordVO;
	}

	/**
	 * Sets the file header record vo.
	 * 
	 * @param fileHeaderRecordVO the new file header record vo
	 */
	public void setFileHeaderRecordVO(FileHeaderRecordVO fileHeaderRecordVO) {

		this.fileHeaderRecordVO = fileHeaderRecordVO;
	}

}

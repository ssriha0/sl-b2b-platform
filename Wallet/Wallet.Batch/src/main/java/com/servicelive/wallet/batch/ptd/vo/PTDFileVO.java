package com.servicelive.wallet.batch.ptd.vo;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDFileVO.
 */
public class PTDFileVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ptd file header record vo. */
	private PTDRecordVO ptdFileHeaderRecordVO;

	/** The ptd file trailer record vo. */
	private PTDRecordVO ptdFileTrailerRecordVO;

	/** The ptd transaction list. */
	private List<PTDRecordVO> ptdTransactionList;

	/**
	 * Gets the ptd file header record vo.
	 * 
	 * @return the ptd file header record vo
	 */
	public PTDRecordVO getPtdFileHeaderRecordVO() {

		return ptdFileHeaderRecordVO;
	}

	/**
	 * Gets the ptd file trailer record vo.
	 * 
	 * @return the ptd file trailer record vo
	 */
	public PTDRecordVO getPtdFileTrailerRecordVO() {

		return ptdFileTrailerRecordVO;
	}

	/**
	 * Gets the ptd transaction list.
	 * 
	 * @return the ptd transaction list
	 */
	public List<PTDRecordVO> getPtdTransactionList() {

		return ptdTransactionList;
	}

	/**
	 * Sets the ptd file header record vo.
	 * 
	 * @param ptdFileHeaderRecordVO the new ptd file header record vo
	 */
	public void setPtdFileHeaderRecordVO(PTDRecordVO ptdFileHeaderRecordVO) {

		this.ptdFileHeaderRecordVO = ptdFileHeaderRecordVO;
	}

	/**
	 * Sets the ptd file trailer record vo.
	 * 
	 * @param ptdFileTrailerRecordVO the new ptd file trailer record vo
	 */
	public void setPtdFileTrailerRecordVO(PTDRecordVO ptdFileTrailerRecordVO) {

		this.ptdFileTrailerRecordVO = ptdFileTrailerRecordVO;
	}

	/**
	 * Sets the ptd transaction list.
	 * 
	 * @param ptdTransactionList the new ptd transaction list
	 */
	public void setPtdTransactionList(List<PTDRecordVO> ptdTransactionList) {

		this.ptdTransactionList = ptdTransactionList;
	}

}

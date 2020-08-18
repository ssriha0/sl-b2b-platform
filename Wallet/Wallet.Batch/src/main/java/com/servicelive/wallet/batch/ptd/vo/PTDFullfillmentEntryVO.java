package com.servicelive.wallet.batch.ptd.vo;

import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDFullfillmentEntryVO.
 */
public class PTDFullfillmentEntryVO extends ValueLinkEntryVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ptd amount sign. */
	private String ptdAmountSign;

	/** The ptd auth code. */
	private String ptdAuthCode;

	/** The ptd error code. */
	private int ptdErrorCode;

	/** The ptd file id. */
	private long ptdFileId;

	/** The ptd log id. */
	private int ptdLogId;

	/** The ptd request code. */
	private int ptdRequestCode;

	/** The ptd response code. */
	private int ptdResponseCode;

	/** The ptd reversal flag. */
	private int ptdReversalFlag;

	/** The ptd source code. */
	private int ptdSourceCode;

	/** The ptd transaction id. */
	private long ptdTransactionId;

	/** The ptd v1 account number. */
	private Long ptdV1AccountNumber;

	/**
	 * Instantiates a new pTD fullfillment entry vo.
	 */
	public PTDFullfillmentEntryVO() {

	}

	/**
	 * 
	 * 
	 * @param ptdErrorCode 
	 * @param feVo 
	 */
	public PTDFullfillmentEntryVO(ValueLinkEntryVO feVo, int ptdErrorCode) {

		super(feVo);
		this.ptdErrorCode = ptdErrorCode;
	}

	/**
	 * Gets the ptd amount sign.
	 * 
	 * @return the ptd amount sign
	 */
	public String getPtdAmountSign() {

		return ptdAmountSign;
	}

	/**
	 * Gets the ptd auth code.
	 * 
	 * @return the ptd auth code
	 */
	public String getPtdAuthCode() {

		return ptdAuthCode;
	}

	/**
	 * Gets the ptd error code.
	 * 
	 * @return the ptd error code
	 */
	public int getPtdErrorCode() {

		return ptdErrorCode;
	}

	/**
	 * Gets the ptd file id.
	 * 
	 * @return the ptd file id
	 */
	public long getPtdFileId() {

		return ptdFileId;
	}

	/**
	 * Gets the ptd log id.
	 * 
	 * @return the ptd log id
	 */
	public int getPtdLogId() {

		return ptdLogId;
	}

	/**
	 * Gets the ptd request code.
	 * 
	 * @return the ptd request code
	 */
	public int getPtdRequestCode() {

		return ptdRequestCode;
	}

	/**
	 * Gets the ptd response code.
	 * 
	 * @return the ptd response code
	 */
	public int getPtdResponseCode() {

		return ptdResponseCode;
	}

	/**
	 * Gets the ptd reversal flag.
	 * 
	 * @return the ptd reversal flag
	 */
	public int getPtdReversalFlag() {

		return ptdReversalFlag;
	}

	/**
	 * Gets the ptd source code.
	 * 
	 * @return the ptd source code
	 */
	public int getPtdSourceCode() {

		return ptdSourceCode;
	}

	/**
	 * Gets the ptd transaction id.
	 * 
	 * @return the ptd transaction id
	 */
	public long getPtdTransactionId() {

		return ptdTransactionId;
	}

	/**
	 * Gets the ptd v1 account number.
	 * 
	 * @return the ptd v1 account number
	 */
	public Long getPtdV1AccountNumber() {

		return ptdV1AccountNumber;
	}

	/**
	 * Sets the ptd amount sign.
	 * 
	 * @param ptdAmountSign the new ptd amount sign
	 */
	public void setPtdAmountSign(String ptdAmountSign) {

		this.ptdAmountSign = ptdAmountSign;
	}

	/**
	 * Sets the ptd auth code.
	 * 
	 * @param ptdAuthCode the new ptd auth code
	 */
	public void setPtdAuthCode(String ptdAuthCode) {

		this.ptdAuthCode = ptdAuthCode;
	}

	/**
	 * Sets the ptd error code.
	 * 
	 * @param ptdErrorCode the new ptd error code
	 */
	public void setPtdErrorCode(int ptdErrorCode) {

		this.ptdErrorCode = ptdErrorCode;
	}

	/**
	 * Sets the ptd file id.
	 * 
	 * @param ptdFileId the new ptd file id
	 */
	public void setPtdFileId(long ptdFileId) {

		this.ptdFileId = ptdFileId;
	}

	/**
	 * Sets the ptd log id.
	 * 
	 * @param ptdLogId the new ptd log id
	 */
	public void setPtdLogId(int ptdLogId) {

		this.ptdLogId = ptdLogId;
	}

	/**
	 * Sets the ptd request code.
	 * 
	 * @param ptdRequestCode the new ptd request code
	 */
	public void setPtdRequestCode(int ptdRequestCode) {

		this.ptdRequestCode = ptdRequestCode;
	}

	/**
	 * Sets the ptd response code.
	 * 
	 * @param ptdResponseCode the new ptd response code
	 */
	public void setPtdResponseCode(int ptdResponseCode) {

		this.ptdResponseCode = ptdResponseCode;
	}

	/**
	 * Sets the ptd reversal flag.
	 * 
	 * @param ptdReversalFlag the new ptd reversal flag
	 */
	public void setPtdReversalFlag(int ptdReversalFlag) {

		this.ptdReversalFlag = ptdReversalFlag;
	}

	/**
	 * Sets the ptd source code.
	 * 
	 * @param ptdSourceCode the new ptd source code
	 */
	public void setPtdSourceCode(int ptdSourceCode) {

		this.ptdSourceCode = ptdSourceCode;
	}

	/**
	 * Sets the ptd transaction id.
	 * 
	 * @param ptdTransactionId the new ptd transaction id
	 */
	public void setPtdTransactionId(long ptdTransactionId) {

		this.ptdTransactionId = ptdTransactionId;
	}

	/**
	 * Sets the ptd v1 account number.
	 * 
	 * @param ptdV1AccountNumber the new ptd v1 account number
	 */
	public void setPtdV1AccountNumber(Long ptdV1AccountNumber) {

		this.ptdV1AccountNumber = ptdV1AccountNumber;
	}
}

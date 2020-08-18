package com.newco.marketplace.dto.vo.ptd;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;

public class PTDFullfillmentEntryVO extends FullfillmentEntryVO {



	private static final long serialVersionUID = 1L;
	private long ptdFileId;
	private long ptdTransactionId;
	private int ptdRequestCode;
    private int ptdResponseCode;
    private int ptdSourceCode;
    private int ptdReversalFlag;
    private String ptdAuthCode;
    private String ptdAmountSign;
    private Long ptdV1AccountNumber;	
    private int ptdErrorCode;	
    private int ptdLogId;	
    
    	
    public Long getPtdV1AccountNumber() {
		return ptdV1AccountNumber;
	}
	public void setPtdV1AccountNumber(Long ptdV1AccountNumber) {
		this.ptdV1AccountNumber = ptdV1AccountNumber;
	}
	public int getPtdRequestCode() {
		return ptdRequestCode;
	}
	public void setPtdRequestCode(int ptdRequestCode) {
		this.ptdRequestCode = ptdRequestCode;
	}
	public int getPtdResponseCode() {
		return ptdResponseCode;
	}
	public void setPtdResponseCode(int ptdResponseCode) {
		this.ptdResponseCode = ptdResponseCode;
	}
	public int getPtdReversalFlag() {
		return ptdReversalFlag;
	}
	public void setPtdReversalFlag(int ptdReversalFlag) {
		this.ptdReversalFlag = ptdReversalFlag;
	}
	public String getPtdAuthCode() {
		return ptdAuthCode;
	}
	public void setPtdAuthCode(String ptdAuthCode) {
		this.ptdAuthCode = ptdAuthCode;
	}
	public String getPtdAmountSign() {
		return ptdAmountSign;
	}
	public void setPtdAmountSign(String ptdAmountSign) {
		this.ptdAmountSign = ptdAmountSign;
	}
	public long getPtdTransactionId() {
		return ptdTransactionId;
	}
	public void setPtdTransactionId(long ptdTransactionId) {
		this.ptdTransactionId = ptdTransactionId;
	}
	public long getPtdFileId() {
		return ptdFileId;
	}
	public void setPtdFileId(long ptdFileId) {
		this.ptdFileId = ptdFileId;
	}
	
	public int getPtdErrorCode() {
		return ptdErrorCode;
	}
	
	public void setPtdErrorCode(int ptdErrorCode) {
		this.ptdErrorCode = ptdErrorCode;
	}
	
	public int getPtdSourceCode() {
		return ptdSourceCode;
	}
	
	public void setPtdSourceCode(int ptdSourceCode) {
		this.ptdSourceCode = ptdSourceCode;
	}
	
	public int getPtdLogId() {
		return ptdLogId;
	}
	
	public void setPtdLogId(int ptdLogId) {
		this.ptdLogId = ptdLogId;
	}
	
	
	
	/**
	 * @param FullfillmentEntryVO
	 * @param ptdErrorCode
	 */
	public PTDFullfillmentEntryVO(FullfillmentEntryVO feVo, int ptdErrorCode) {
		super(feVo);
		this.ptdErrorCode = ptdErrorCode;
	}
	
	public PTDFullfillmentEntryVO() {
		
	}
}

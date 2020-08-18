package com.newco.marketplace.dto.vo.ptd;


public class PtdEntryVO extends PTDFullfillmentEntryVO {

    
	private Integer recordFormIndicator;
	private Integer reserved1;
	private Integer reserved2;
	private Long merchantId;
	private Integer reserved3;
	private Integer reserved4;
	private Long alternateMerchantNumber;
	private Integer terminalId;
	private Long terminalTransactionNumber;
	private Long baseAmount;
	private Long reportingAmount;
	private Integer baseCurrencyCode;
	private Integer localCurrencyCode;
	private Integer reportingCurrencyCode;
	private Long exchangeCode;
	private String clerkId;
	private String balanceSign;
	private Long baseBalance;
	private Integer consortiumCode;
	private Integer promotionCode;
	private Long fdmsLocalTimestamp;
	private Long terminalLocalTimestamp;
	private Long replacedByAccountNumber;
	private String reserved5;
	private String userId;
	private Integer cardClass;
	private Integer expirationDate;
	private Long cardCost;
	private String escheaTableTransaction;
	private String referenceNumber;
	private String user2;
	private Long localCashback;
	private Long baseCashback;
	private Long reportingCashback;
	private Long localLockAmount;
	private Long baseLockAmount;
	private Long reversedTimestamp;
	private Long processedDate;
	private Integer originalRequestCode;
	private Integer internalRequestCode;
	private String callTraceInfo;
	private Long upliftAmount;
	private String gmtTimestamp;
	private Long alternateAccountNumber;
	private Integer emailSentFlag;
	
	
	public PtdEntryVO() {
		
	}



	public PtdEntryVO(PTDFullfillmentEntryVO ptdFullfillmentEntryVO) {
		super(ptdFullfillmentEntryVO, 0);
	}



	public Integer getRecordFormIndicator() {
		return recordFormIndicator;
	}



	public void setRecordFormIndicator(Integer recordFormIndicator) {
		this.recordFormIndicator = recordFormIndicator;
	}



	public Integer getReserved1() {
		return reserved1;
	}



	public void setReserved1(Integer reserved1) {
		this.reserved1 = reserved1;
	}



	public Integer getReserved2() {
		return reserved2;
	}



	public void setReserved2(Integer reserved2) {
		this.reserved2 = reserved2;
	}



	public Long getMerchantId() {
		return merchantId;
	}



	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}



	public Integer getReserved3() {
		return reserved3;
	}



	public void setReserved3(Integer reserved3) {
		this.reserved3 = reserved3;
	}



	public Integer getReserved4() {
		return reserved4;
	}



	public void setReserved4(Integer reserved4) {
		this.reserved4 = reserved4;
	}



	public Long getAlternateMerchantNumber() {
		return alternateMerchantNumber;
	}



	public void setAlternateMerchantNumber(Long alternateMerchantNumber) {
		this.alternateMerchantNumber = alternateMerchantNumber;
	}



	public Integer getTerminalId() {
		return terminalId;
	}



	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}



	public Long getTerminalTransactionNumber() {
		return terminalTransactionNumber;
	}



	public void setTerminalTransactionNumber(Long terminalTransactionNumber) {
		this.terminalTransactionNumber = terminalTransactionNumber;
	}



	public Long getBaseAmount() {
		return baseAmount;
	}



	public void setBaseAmount(Long baseAmount) {
		this.baseAmount = baseAmount;
	}



	public Long getReportingAmount() {
		return reportingAmount;
	}



	public void setReportingAmount(Long reportingAmount) {
		this.reportingAmount = reportingAmount;
	}



	public Integer getBaseCurrencyCode() {
		return baseCurrencyCode;
	}



	public void setBaseCurrencyCode(Integer baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}



	public Integer getLocalCurrencyCode() {
		return localCurrencyCode;
	}



	public void setLocalCurrencyCode(Integer localCurrencyCode) {
		this.localCurrencyCode = localCurrencyCode;
	}



	public Integer getReportingCurrencyCode() {
		return reportingCurrencyCode;
	}



	public void setReportingCurrencyCode(Integer reportingCurrencyCode) {
		this.reportingCurrencyCode = reportingCurrencyCode;
	}



	public Long getExchangeCode() {
		return exchangeCode;
	}



	public void setExchangeCode(Long exchangeCode) {
		this.exchangeCode = exchangeCode;
	}



	public String getClerkId() {
		return clerkId;
	}



	public void setClerkId(String clerkId) {
		this.clerkId = clerkId;
	}



	public String getBalanceSign() {
		return balanceSign;
	}



	public void setBalanceSign(String balanceSign) {
		this.balanceSign = balanceSign;
	}



	public Long getBaseBalance() {
		return baseBalance;
	}



	public void setBaseBalance(Long baseBalance) {
		this.baseBalance = baseBalance;
	}



	public Integer getConsortiumCode() {
		return consortiumCode;
	}



	public void setConsortiumCode(Integer consortiumCode) {
		this.consortiumCode = consortiumCode;
	}



	public Integer getPromotionCode() {
		return promotionCode;
	}



	public void setPromotionCode(Integer promotionCode) {
		this.promotionCode = promotionCode;
	}



	public Long getFdmsLocalTimestamp() {
		return fdmsLocalTimestamp;
	}



	public void setFdmsLocalTimestamp(Long fdmsLocalTimestamp) {
		this.fdmsLocalTimestamp = fdmsLocalTimestamp;
	}



	public Long getTerminalLocalTimestamp() {
		return terminalLocalTimestamp;
	}



	public void setTerminalLocalTimestamp(Long terminalLocalTimestamp) {
		this.terminalLocalTimestamp = terminalLocalTimestamp;
	}



	public Long getReplacedByAccountNumber() {
		return replacedByAccountNumber;
	}



	public void setReplacedByAccountNumber(Long replacedByAccountNumber) {
		this.replacedByAccountNumber = replacedByAccountNumber;
	}



	public String getReserved5() {
		return reserved5;
	}



	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public Integer getCardClass() {
		return cardClass;
	}



	public void setCardClass(Integer cardClass) {
		this.cardClass = cardClass;
	}



	public Integer getExpirationDate() {
		return expirationDate;
	}



	public void setExpirationDate(Integer expirationDate) {
		this.expirationDate = expirationDate;
	}



	public Long getCardCost() {
		return cardCost;
	}



	public void setCardCost(Long cardCost) {
		this.cardCost = cardCost;
	}



	public String getEscheaTableTransaction() {
		return escheaTableTransaction;
	}



	public void setEscheaTableTransaction(String escheaTableTransaction) {
		this.escheaTableTransaction = escheaTableTransaction;
	}



	public String getReferenceNumber() {
		return referenceNumber;
	}



	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}



	public String getUser2() {
		return user2;
	}



	public void setUser2(String user2) {
		this.user2 = user2;
	}



	public Long getLocalCashback() {
		return localCashback;
	}



	public void setLocalCashback(Long localCashback) {
		this.localCashback = localCashback;
	}



	public Long getBaseCashback() {
		return baseCashback;
	}



	public void setBaseCashback(Long baseCashback) {
		this.baseCashback = baseCashback;
	}



	public Long getReportingCashback() {
		return reportingCashback;
	}



	public void setReportingCashback(Long reportingCashback) {
		this.reportingCashback = reportingCashback;
	}



	public Long getLocalLockAmount() {
		return localLockAmount;
	}



	public void setLocalLockAmount(Long localLockAmount) {
		this.localLockAmount = localLockAmount;
	}



	public Long getBaseLockAmount() {
		return baseLockAmount;
	}



	public void setBaseLockAmount(Long baseLockAmount) {
		this.baseLockAmount = baseLockAmount;
	}



	public Long getReversedTimestamp() {
		return reversedTimestamp;
	}



	public void setReversedTimestamp(Long reversedTimestamp) {
		this.reversedTimestamp = reversedTimestamp;
	}



	public Long getProcessedDate() {
		return processedDate;
	}



	public void setProcessedDate(Long processedDate) {
		this.processedDate = processedDate;
	}



	public Integer getOriginalRequestCode() {
		return originalRequestCode;
	}



	public void setOriginalRequestCode(Integer originalRequestCode) {
		this.originalRequestCode = originalRequestCode;
	}



	public Integer getInternalRequestCode() {
		return internalRequestCode;
	}



	public void setInternalRequestCode(Integer internalRequestCode) {
		this.internalRequestCode = internalRequestCode;
	}



	public String getCallTraceInfo() {
		return callTraceInfo;
	}



	public void setCallTraceInfo(String callTraceInfo) {
		this.callTraceInfo = callTraceInfo;
	}



	public Long getUpliftAmount() {
		return upliftAmount;
	}



	public void setUpliftAmount(Long upliftAmount) {
		this.upliftAmount = upliftAmount;
	}



	public String getGmtTimestamp() {
		return gmtTimestamp;
	}



	public void setGmtTimestamp(String gmtTimestamp) {
		this.gmtTimestamp = gmtTimestamp;
	}



	public Long getAlternateAccountNumber() {
		return alternateAccountNumber;
	}



	public void setAlternateAccountNumber(Long alternateAccountNumber) {
		this.alternateAccountNumber = alternateAccountNumber;
	}



	public Integer getEmailSentFlag() {
		return emailSentFlag;
	}



	public void setEmailSentFlag(Integer emailSentFlag) {
		this.emailSentFlag = emailSentFlag;
	}

}

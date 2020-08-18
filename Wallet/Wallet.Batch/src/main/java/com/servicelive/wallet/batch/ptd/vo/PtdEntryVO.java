package com.servicelive.wallet.batch.ptd.vo;

// TODO: Auto-generated Javadoc
/**
 * The Class PtdEntryVO.
 */
public class PtdEntryVO extends PTDFullfillmentEntryVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6175612341470746088L;

	/** The alternate account number. */
	private Long alternateAccountNumber;

	/** The alternate merchant number. */
	private Long alternateMerchantNumber;

	/** The balance sign. */
	private String balanceSign;

	/** The base amount. */
	private Long baseAmount;

	/** The base balance. */
	private Long baseBalance;

	/** The base cashback. */
	private Long baseCashback;

	/** The base currency code. */
	private Integer baseCurrencyCode;

	/** The base lock amount. */
	private Long baseLockAmount;

	/** The call trace info. */
	private String callTraceInfo;

	/** The card class. */
	private Integer cardClass;

	/** The card cost. */
	private Long cardCost;

	/** The clerk id. */
	private String clerkId;

	/** The consortium code. */
	private Integer consortiumCode;

	/** The email sent flag. */
	private Integer emailSentFlag;

	/** The eschea table transaction. */
	private String escheaTableTransaction;

	/** The exchange code. */
	private Long exchangeCode;

	/** The expiration date. */
	private Integer expirationDate;

	/** The fdms local timestamp. */
	private Long fdmsLocalTimestamp;

	/** The gmt timestamp. */
	private String gmtTimestamp;

	/** The internal request code. */
	private Integer internalRequestCode;

	/** The local cashback. */
	private Long localCashback;

	/** The local currency code. */
	private Integer localCurrencyCode;

	/** The local lock amount. */
	private Long localLockAmount;

	/** The merchant id. */
	private Long merchantId;

	/** The original request code. */
	private Integer originalRequestCode;

	/** The processed date. */
	private Long processedDate;

	/** The promotion code. */
	private Integer promotionCode;

	/** The record form indicator. */
	private Integer recordFormIndicator;

	/** The reference number. */
	private String referenceNumber;

	/** The replaced by account number. */
	private Long replacedByAccountNumber;

	/** The reporting amount. */
	private Long reportingAmount;

	/** The reporting cashback. */
	private Long reportingCashback;

	/** The reporting currency code. */
	private Integer reportingCurrencyCode;

	/** The reserved1. */
	private Integer reserved1;

	/** The reserved2. */
	private Integer reserved2;

	/** The reserved3. */
	private Integer reserved3;

	/** The reserved4. */
	private Integer reserved4;

	/** The reserved5. */
	private String reserved5;

	/** The reversed timestamp. */
	private Long reversedTimestamp;

	/** The terminal id. */
	private Integer terminalId;

	/** The terminal local timestamp. */
	private Long terminalLocalTimestamp;

	/** The terminal transaction number. */
	private Long terminalTransactionNumber;

	/** The uplift amount. */
	private Long upliftAmount;

	/** The user2. */
	private String user2;

	/** The user id. */
	private String userId;

	/**
	 * Instantiates a new ptd entry vo.
	 */
	public PtdEntryVO() {

	}

	/**
	 * Instantiates a new ptd entry vo.
	 * 
	 * @param ptdFullfillmentEntryVO 
	 */
	public PtdEntryVO(PTDFullfillmentEntryVO ptdFullfillmentEntryVO) {

		super(ptdFullfillmentEntryVO, 0);
	}

	/**
	 * Gets the alternate account number.
	 * 
	 * @return the alternate account number
	 */
	public Long getAlternateAccountNumber() {

		return alternateAccountNumber;
	}

	/**
	 * Gets the alternate merchant number.
	 * 
	 * @return the alternate merchant number
	 */
	public Long getAlternateMerchantNumber() {

		return alternateMerchantNumber;
	}

	/**
	 * Gets the balance sign.
	 * 
	 * @return the balance sign
	 */
	public String getBalanceSign() {

		return balanceSign;
	}

	/**
	 * Gets the base amount.
	 * 
	 * @return the base amount
	 */
	public Long getBaseAmount() {

		return baseAmount;
	}

	/**
	 * Gets the base balance.
	 * 
	 * @return the base balance
	 */
	public Long getBaseBalance() {

		return baseBalance;
	}

	/**
	 * Gets the base cashback.
	 * 
	 * @return the base cashback
	 */
	public Long getBaseCashback() {

		return baseCashback;
	}

	/**
	 * Gets the base currency code.
	 * 
	 * @return the base currency code
	 */
	public Integer getBaseCurrencyCode() {

		return baseCurrencyCode;
	}

	/**
	 * Gets the base lock amount.
	 * 
	 * @return the base lock amount
	 */
	public Long getBaseLockAmount() {

		return baseLockAmount;
	}

	/**
	 * Gets the call trace info.
	 * 
	 * @return the call trace info
	 */
	public String getCallTraceInfo() {

		return callTraceInfo;
	}

	/**
	 * Gets the card class.
	 * 
	 * @return the card class
	 */
	public Integer getCardClass() {

		return cardClass;
	}

	/**
	 * Gets the card cost.
	 * 
	 * @return the card cost
	 */
	public Long getCardCost() {

		return cardCost;
	}

	/**
	 * Gets the clerk id.
	 * 
	 * @return the clerk id
	 */
	public String getClerkId() {

		return clerkId;
	}

	/**
	 * Gets the consortium code.
	 * 
	 * @return the consortium code
	 */
	public Integer getConsortiumCode() {

		return consortiumCode;
	}

	/**
	 * Gets the email sent flag.
	 * 
	 * @return the email sent flag
	 */
	public Integer getEmailSentFlag() {

		return emailSentFlag;
	}

	/**
	 * Gets the eschea table transaction.
	 * 
	 * @return the eschea table transaction
	 */
	public String getEscheaTableTransaction() {

		return escheaTableTransaction;
	}

	/**
	 * Gets the exchange code.
	 * 
	 * @return the exchange code
	 */
	public Long getExchangeCode() {

		return exchangeCode;
	}

	/**
	 * Gets the expiration date.
	 * 
	 * @return the expiration date
	 */
	public Integer getExpirationDate() {

		return expirationDate;
	}

	/**
	 * Gets the fdms local timestamp.
	 * 
	 * @return the fdms local timestamp
	 */
	public Long getFdmsLocalTimestamp() {

		return fdmsLocalTimestamp;
	}

	/**
	 * Gets the gmt timestamp.
	 * 
	 * @return the gmt timestamp
	 */
	public String getGmtTimestamp() {

		return gmtTimestamp;
	}

	/**
	 * Gets the internal request code.
	 * 
	 * @return the internal request code
	 */
	public Integer getInternalRequestCode() {

		return internalRequestCode;
	}

	/**
	 * Gets the local cashback.
	 * 
	 * @return the local cashback
	 */
	public Long getLocalCashback() {

		return localCashback;
	}

	/**
	 * Gets the local currency code.
	 * 
	 * @return the local currency code
	 */
	public Integer getLocalCurrencyCode() {

		return localCurrencyCode;
	}

	/**
	 * Gets the local lock amount.
	 * 
	 * @return the local lock amount
	 */
	public Long getLocalLockAmount() {

		return localLockAmount;
	}

	/**
	 * Gets the merchant id.
	 * 
	 * @return the merchant id
	 */
	public Long getMerchantId() {

		return merchantId;
	}

	/**
	 * Gets the original request code.
	 * 
	 * @return the original request code
	 */
	public Integer getOriginalRequestCode() {

		return originalRequestCode;
	}

	/**
	 * Gets the processed date.
	 * 
	 * @return the processed date
	 */
	public Long getProcessedDate() {

		return processedDate;
	}

	/**
	 * Gets the promotion code.
	 * 
	 * @return the promotion code
	 */
	public Integer getPromotionCode() {

		return promotionCode;
	}

	/**
	 * Gets the record form indicator.
	 * 
	 * @return the record form indicator
	 */
	public Integer getRecordFormIndicator() {

		return recordFormIndicator;
	}

	/**
	 * Gets the reference number.
	 * 
	 * @return the reference number
	 */
	public String getReferenceNumber() {

		return referenceNumber;
	}

	/**
	 * Gets the replaced by account number.
	 * 
	 * @return the replaced by account number
	 */
	public Long getReplacedByAccountNumber() {

		return replacedByAccountNumber;
	}

	/**
	 * Gets the reporting amount.
	 * 
	 * @return the reporting amount
	 */
	public Long getReportingAmount() {

		return reportingAmount;
	}

	/**
	 * Gets the reporting cashback.
	 * 
	 * @return the reporting cashback
	 */
	public Long getReportingCashback() {

		return reportingCashback;
	}

	/**
	 * Gets the reporting currency code.
	 * 
	 * @return the reporting currency code
	 */
	public Integer getReportingCurrencyCode() {

		return reportingCurrencyCode;
	}

	/**
	 * Gets the reserved1.
	 * 
	 * @return the reserved1
	 */
	public Integer getReserved1() {

		return reserved1;
	}

	/**
	 * Gets the reserved2.
	 * 
	 * @return the reserved2
	 */
	public Integer getReserved2() {

		return reserved2;
	}

	/**
	 * Gets the reserved3.
	 * 
	 * @return the reserved3
	 */
	public Integer getReserved3() {

		return reserved3;
	}

	/**
	 * Gets the reserved4.
	 * 
	 * @return the reserved4
	 */
	public Integer getReserved4() {

		return reserved4;
	}

	/**
	 * Gets the reserved5.
	 * 
	 * @return the reserved5
	 */
	public String getReserved5() {

		return reserved5;
	}

	/**
	 * Gets the reversed timestamp.
	 * 
	 * @return the reversed timestamp
	 */
	public Long getReversedTimestamp() {

		return reversedTimestamp;
	}

	/**
	 * Gets the terminal id.
	 * 
	 * @return the terminal id
	 */
	public Integer getTerminalId() {

		return terminalId;
	}

	/**
	 * Gets the terminal local timestamp.
	 * 
	 * @return the terminal local timestamp
	 */
	public Long getTerminalLocalTimestamp() {

		return terminalLocalTimestamp;
	}

	/**
	 * Gets the terminal transaction number.
	 * 
	 * @return the terminal transaction number
	 */
	public Long getTerminalTransactionNumber() {

		return terminalTransactionNumber;
	}

	/**
	 * Gets the uplift amount.
	 * 
	 * @return the uplift amount
	 */
	public Long getUpliftAmount() {

		return upliftAmount;
	}

	/**
	 * Gets the user2.
	 * 
	 * @return the user2
	 */
	public String getUser2() {

		return user2;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public String getUserId() {

		return userId;
	}

	/**
	 * Sets the alternate account number.
	 * 
	 * @param alternateAccountNumber the new alternate account number
	 */
	public void setAlternateAccountNumber(Long alternateAccountNumber) {

		this.alternateAccountNumber = alternateAccountNumber;
	}

	/**
	 * Sets the alternate merchant number.
	 * 
	 * @param alternateMerchantNumber the new alternate merchant number
	 */
	public void setAlternateMerchantNumber(Long alternateMerchantNumber) {

		this.alternateMerchantNumber = alternateMerchantNumber;
	}

	/**
	 * Sets the balance sign.
	 * 
	 * @param balanceSign the new balance sign
	 */
	public void setBalanceSign(String balanceSign) {

		this.balanceSign = balanceSign;
	}

	/**
	 * Sets the base amount.
	 * 
	 * @param baseAmount the new base amount
	 */
	public void setBaseAmount(Long baseAmount) {

		this.baseAmount = baseAmount;
	}

	/**
	 * Sets the base balance.
	 * 
	 * @param baseBalance the new base balance
	 */
	public void setBaseBalance(Long baseBalance) {

		this.baseBalance = baseBalance;
	}

	/**
	 * Sets the base cashback.
	 * 
	 * @param baseCashback the new base cashback
	 */
	public void setBaseCashback(Long baseCashback) {

		this.baseCashback = baseCashback;
	}

	/**
	 * Sets the base currency code.
	 * 
	 * @param baseCurrencyCode the new base currency code
	 */
	public void setBaseCurrencyCode(Integer baseCurrencyCode) {

		this.baseCurrencyCode = baseCurrencyCode;
	}

	/**
	 * Sets the base lock amount.
	 * 
	 * @param baseLockAmount the new base lock amount
	 */
	public void setBaseLockAmount(Long baseLockAmount) {

		this.baseLockAmount = baseLockAmount;
	}

	/**
	 * Sets the call trace info.
	 * 
	 * @param callTraceInfo the new call trace info
	 */
	public void setCallTraceInfo(String callTraceInfo) {

		this.callTraceInfo = callTraceInfo;
	}

	/**
	 * Sets the card class.
	 * 
	 * @param cardClass the new card class
	 */
	public void setCardClass(Integer cardClass) {

		this.cardClass = cardClass;
	}

	/**
	 * Sets the card cost.
	 * 
	 * @param cardCost the new card cost
	 */
	public void setCardCost(Long cardCost) {

		this.cardCost = cardCost;
	}

	/**
	 * Sets the clerk id.
	 * 
	 * @param clerkId the new clerk id
	 */
	public void setClerkId(String clerkId) {

		this.clerkId = clerkId;
	}

	/**
	 * Sets the consortium code.
	 * 
	 * @param consortiumCode the new consortium code
	 */
	public void setConsortiumCode(Integer consortiumCode) {

		this.consortiumCode = consortiumCode;
	}

	/**
	 * Sets the email sent flag.
	 * 
	 * @param emailSentFlag the new email sent flag
	 */
	public void setEmailSentFlag(Integer emailSentFlag) {

		this.emailSentFlag = emailSentFlag;
	}

	/**
	 * Sets the eschea table transaction.
	 * 
	 * @param escheaTableTransaction the new eschea table transaction
	 */
	public void setEscheaTableTransaction(String escheaTableTransaction) {

		this.escheaTableTransaction = escheaTableTransaction;
	}

	/**
	 * Sets the exchange code.
	 * 
	 * @param exchangeCode the new exchange code
	 */
	public void setExchangeCode(Long exchangeCode) {

		this.exchangeCode = exchangeCode;
	}

	/**
	 * Sets the expiration date.
	 * 
	 * @param expirationDate the new expiration date
	 */
	public void setExpirationDate(Integer expirationDate) {

		this.expirationDate = expirationDate;
	}

	/**
	 * Sets the fdms local timestamp.
	 * 
	 * @param fdmsLocalTimestamp the new fdms local timestamp
	 */
	public void setFdmsLocalTimestamp(Long fdmsLocalTimestamp) {

		this.fdmsLocalTimestamp = fdmsLocalTimestamp;
	}

	/**
	 * Sets the gmt timestamp.
	 * 
	 * @param gmtTimestamp the new gmt timestamp
	 */
	public void setGmtTimestamp(String gmtTimestamp) {

		this.gmtTimestamp = gmtTimestamp;
	}

	/**
	 * Sets the internal request code.
	 * 
	 * @param internalRequestCode the new internal request code
	 */
	public void setInternalRequestCode(Integer internalRequestCode) {

		this.internalRequestCode = internalRequestCode;
	}

	/**
	 * Sets the local cashback.
	 * 
	 * @param localCashback the new local cashback
	 */
	public void setLocalCashback(Long localCashback) {

		this.localCashback = localCashback;
	}

	/**
	 * Sets the local currency code.
	 * 
	 * @param localCurrencyCode the new local currency code
	 */
	public void setLocalCurrencyCode(Integer localCurrencyCode) {

		this.localCurrencyCode = localCurrencyCode;
	}

	/**
	 * Sets the local lock amount.
	 * 
	 * @param localLockAmount the new local lock amount
	 */
	public void setLocalLockAmount(Long localLockAmount) {

		this.localLockAmount = localLockAmount;
	}

	/**
	 * Sets the merchant id.
	 * 
	 * @param merchantId the new merchant id
	 */
	public void setMerchantId(Long merchantId) {

		this.merchantId = merchantId;
	}

	/**
	 * Sets the original request code.
	 * 
	 * @param originalRequestCode the new original request code
	 */
	public void setOriginalRequestCode(Integer originalRequestCode) {

		this.originalRequestCode = originalRequestCode;
	}

	/**
	 * Sets the processed date.
	 * 
	 * @param processedDate the new processed date
	 */
	public void setProcessedDate(Long processedDate) {

		this.processedDate = processedDate;
	}

	/**
	 * Sets the promotion code.
	 * 
	 * @param promotionCode the new promotion code
	 */
	public void setPromotionCode(Integer promotionCode) {

		this.promotionCode = promotionCode;
	}

	/**
	 * Sets the record form indicator.
	 * 
	 * @param recordFormIndicator the new record form indicator
	 */
	public void setRecordFormIndicator(Integer recordFormIndicator) {

		this.recordFormIndicator = recordFormIndicator;
	}

	/**
	 * Sets the reference number.
	 * 
	 * @param referenceNumber the new reference number
	 */
	public void setReferenceNumber(String referenceNumber) {

		this.referenceNumber = referenceNumber;
	}

	/**
	 * Sets the replaced by account number.
	 * 
	 * @param replacedByAccountNumber the new replaced by account number
	 */
	public void setReplacedByAccountNumber(Long replacedByAccountNumber) {

		this.replacedByAccountNumber = replacedByAccountNumber;
	}

	/**
	 * Sets the reporting amount.
	 * 
	 * @param reportingAmount the new reporting amount
	 */
	public void setReportingAmount(Long reportingAmount) {

		this.reportingAmount = reportingAmount;
	}

	/**
	 * Sets the reporting cashback.
	 * 
	 * @param reportingCashback the new reporting cashback
	 */
	public void setReportingCashback(Long reportingCashback) {

		this.reportingCashback = reportingCashback;
	}

	/**
	 * Sets the reporting currency code.
	 * 
	 * @param reportingCurrencyCode the new reporting currency code
	 */
	public void setReportingCurrencyCode(Integer reportingCurrencyCode) {

		this.reportingCurrencyCode = reportingCurrencyCode;
	}

	/**
	 * Sets the reserved1.
	 * 
	 * @param reserved1 the new reserved1
	 */
	public void setReserved1(Integer reserved1) {

		this.reserved1 = reserved1;
	}

	/**
	 * Sets the reserved2.
	 * 
	 * @param reserved2 the new reserved2
	 */
	public void setReserved2(Integer reserved2) {

		this.reserved2 = reserved2;
	}

	/**
	 * Sets the reserved3.
	 * 
	 * @param reserved3 the new reserved3
	 */
	public void setReserved3(Integer reserved3) {

		this.reserved3 = reserved3;
	}

	/**
	 * Sets the reserved4.
	 * 
	 * @param reserved4 the new reserved4
	 */
	public void setReserved4(Integer reserved4) {

		this.reserved4 = reserved4;
	}

	/**
	 * Sets the reserved5.
	 * 
	 * @param reserved5 the new reserved5
	 */
	public void setReserved5(String reserved5) {

		this.reserved5 = reserved5;
	}

	/**
	 * Sets the reversed timestamp.
	 * 
	 * @param reversedTimestamp the new reversed timestamp
	 */
	public void setReversedTimestamp(Long reversedTimestamp) {

		this.reversedTimestamp = reversedTimestamp;
	}

	/**
	 * Sets the terminal id.
	 * 
	 * @param terminalId the new terminal id
	 */
	public void setTerminalId(Integer terminalId) {

		this.terminalId = terminalId;
	}

	/**
	 * Sets the terminal local timestamp.
	 * 
	 * @param terminalLocalTimestamp the new terminal local timestamp
	 */
	public void setTerminalLocalTimestamp(Long terminalLocalTimestamp) {

		this.terminalLocalTimestamp = terminalLocalTimestamp;
	}

	/**
	 * Sets the terminal transaction number.
	 * 
	 * @param terminalTransactionNumber the new terminal transaction number
	 */
	public void setTerminalTransactionNumber(Long terminalTransactionNumber) {

		this.terminalTransactionNumber = terminalTransactionNumber;
	}

	/**
	 * Sets the uplift amount.
	 * 
	 * @param upliftAmount the new uplift amount
	 */
	public void setUpliftAmount(Long upliftAmount) {

		this.upliftAmount = upliftAmount;
	}

	/**
	 * Sets the user2.
	 * 
	 * @param user2 the new user2
	 */
	public void setUser2(String user2) {

		this.user2 = user2;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {

		this.userId = userId;
	}

}

package com.servicelive.wallet.batch.trans.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionRecordVO.
 */
public class TransactionRecordVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 141581978302600487L;

	/** The ach process id. */
	private long achProcessId;

	/** The ach trans code id. */
	private String achTransCodeId;

	/** The amount. */
	private double amount;

	/** The authorization code. */
	private String authorizationCode;

	/** The card expire date. */
	private String cardExpireDate;

	/** The card type code. */
	private String cardTypeCode;

	/** The credit card number. */
	private String creditCardNumber;

	/** The ledger entry id. */
	private long ledgerEntryId;

	/** The transaction type. */
	private Integer transactionType;
    
	//SL-20853
	
	/** The app key type. */
	private boolean pciVersion;
	
	/** The ajb key. */
	private String settlementKey;
	
	/** The token id. */
	private String tokenId;
	
	/** The masked account number. */
	private String maskedAccountNo;
	
	/**
	 * Gets the ach process id.
	 * 
	 * @return the ach process id
	 */
	public long getAchProcessId() {

		return achProcessId;
	}


	/**
	 * Gets the ach trans code id.
	 * 
	 * @return the ach trans code id
	 */
	public String getAchTransCodeId() {

		return achTransCodeId;
	}

	/**
	 * Gets the amount.
	 * 
	 * @return the amount
	 */
	public double getAmount() {

		return amount;
	}

	/**
	 * Gets the authorization code.
	 * 
	 * @return the authorization code
	 */
	public String getAuthorizationCode() {

		return authorizationCode;
	}

	/**
	 * Gets the card expire date.
	 * 
	 * @return the card expire date
	 */
	public String getCardExpireDate() {

		return cardExpireDate;
	}

	/**
	 * Gets the card type code.
	 * 
	 * @return the card type code
	 */
	public String getCardTypeCode() {

		return cardTypeCode;
	}

	/**
	 * Gets the credit card number.
	 * 
	 * @return the credit card number
	 */
	public String getCreditCardNumber() {

		return creditCardNumber;
	}

	/**
	 * Gets the ledger entry id.
	 * 
	 * @return the ledger entry id
	 */
	public long getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * Gets the transaction type.
	 * 
	 * @return the transaction type
	 */
	public Integer getTransactionType() {

		return transactionType;
	}

	/**
	 * Sets the ach process id.
	 * 
	 * @param achProcessId the new ach process id
	 */
	public void setAchProcessId(long achProcessId) {

		this.achProcessId = achProcessId;
	}

	/**
	 * Sets the ach trans code id.
	 * 
	 * @param achTransCodeId the new ach trans code id
	 */
	public void setAchTransCodeId(String achTransCodeId) {

		this.achTransCodeId = achTransCodeId;
	}

	/**
	 * Sets the amount.
	 * 
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {

		this.amount = amount;
	}

	/**
	 * Sets the authorization code.
	 * 
	 * @param authorizationCode the new authorization code
	 */
	public void setAuthorizationCode(String authorizationCode) {

		this.authorizationCode = authorizationCode;
	}

	/**
	 * Sets the card expire date.
	 * 
	 * @param cardExpireDate the new card expire date
	 */
	public void setCardExpireDate(String cardExpireDate) {

		this.cardExpireDate = cardExpireDate;
	}

	/**
	 * Sets the card type code.
	 * 
	 * @param cardTypeCode the new card type code
	 */
	public void setCardTypeCode(String cardTypeCode) {

		this.cardTypeCode = cardTypeCode;
	}

	/**
	 * Sets the credit card number.
	 * 
	 * @param creditCardNumber the new credit card number
	 */
	public void setCreditCardNumber(String creditCardNumber) {

		this.creditCardNumber = creditCardNumber;
	}
 
	/**
	 * Sets the ledger entry id.
	 * 
	 * @param ledgerEntryId the new ledger entry id
	 */
	public void setLedgerEntryId(long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * Sets the transaction type.
	 * 
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(Integer transactionType) {

		this.transactionType = transactionType;
	}
    
	//SL-20853
	
	/**
	 * Gets true if pci version
	 * @return true if pci version
	 */
	public boolean isPciVersion() {
		return pciVersion;
	}

    /**
     * Sets the value of the PCI version
     * @param pciVersion
     */
	public void setPciVersion(boolean pciVersion) {
		this.pciVersion = pciVersion;
	}

    /**
     * Gets the ajb key as settlementKey
     * @return ajb key as settlementKey
     */
	public String getSettlementKey() {
		return settlementKey;
	}

    /**
     * Sets the ajb key as settlement key
     * @param settlementKey
     */
	public void setSettlementKey(String settlementKey) {
		this.settlementKey = settlementKey;
	}

    /**
     * Gets the token Id
     * @return tokenId
     */
	public String getTokenId() {
		return tokenId;
	}
    
	/**
	 * Sets the token Id
	 * @param tokenId
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

    /**
     * Gets the masked Account Number
     * @return masked Account Number
     */
	public String getMaskedAccountNo() {
		return maskedAccountNo;
	}
    
	/**
	 * Sets the masked Account Number
	 * @param maskedAccountNo
	 */
	public void setMaskedAccountNo(String maskedAccountNo) {
		this.maskedAccountNo = maskedAccountNo;
	}
    
	
}

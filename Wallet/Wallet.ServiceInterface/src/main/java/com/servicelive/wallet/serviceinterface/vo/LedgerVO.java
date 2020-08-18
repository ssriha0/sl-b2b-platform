package com.servicelive.wallet.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.vo.ABaseVO;


// TODO: Auto-generated Javadoc
/**
 * Class LedgerVO.
 */
@XmlRootElement()
public class LedgerVO extends ABaseVO {

	/** serialVersionUID. */
	private static final long serialVersionUID = 2412497870470128770L;

	/** accountId. */
	private Long accountId;

	/** creditCardAuthorizationNumber. */
	private String creditCardAuthorizationNumber;

	/** creditCardTypeId. */
	private Long creditCardTypeId;

	/** transactionNote. */
	private String transactionNote;

	/** transferReasonCode. */
	private Integer transferReasonCode;
	
	private String nonce;


	/**
	 * LedgerVO.
	 */
	public LedgerVO() {

	}

	/**
	 * LedgerVO.
	 * 
	 * @param aBaseVO 
	 */
	public LedgerVO(ABaseVO aBaseVO) {

		super(aBaseVO);
	}

	/**
	 * LedgerVO.
	 * 
	 * @param ledgerVO 
	 */
	public LedgerVO(LedgerVO ledgerVO) {

		super(ledgerVO);
		this.transactionNote = ledgerVO.getTransactionNote();
		this.transferReasonCode = ledgerVO.getTransferReasonCode();
		this.accountId = ledgerVO.getAccountId();
		this.creditCardAuthorizationNumber = ledgerVO.getCreditCardAuthorizationNumber();
	}

	/**
	 * getAccountId.
	 * 
	 * @return Long
	 */
	public Long getAccountId() {

		return accountId;
	}

	/**
	 * getCreditCardAuthorizationNumber.
	 * 
	 * @return String
	 */
	public String getCreditCardAuthorizationNumber() {

		return creditCardAuthorizationNumber;
	}

	/**
	 * getCreditCardTypeId.
	 * 
	 * @return Long
	 */
	public Long getCreditCardTypeId() {

		return creditCardTypeId;
	}

	/**
	 * getTransactionNote.
	 * 
	 * @return String
	 */
	public String getTransactionNote() {

		return transactionNote;
	}

	/**
	 * getTransferReasonCode.
	 * 
	 * @return Integer
	 */
	public Integer getTransferReasonCode() {

		return transferReasonCode;
	}

	/**
	 * setAccountId.
	 * 
	 * @param accountId 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setAccountId(Long accountId) {

		this.accountId = accountId;
	}

	/**
	 * setCreditCardAuthorizationNumber.
	 * 
	 * @param creditCardAuthorizationNumber 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setCreditCardAuthorizationNumber(String creditCardAuthorizationNumber) {

		this.creditCardAuthorizationNumber = creditCardAuthorizationNumber;
	}

	/**
	 * setCreditCardTypeId.
	 * 
	 * @param creditCardTypeId 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setCreditCardTypeId(Long creditCardTypeId) {

		this.creditCardTypeId = creditCardTypeId;
	}

	/**
	 * setTransactionNote.
	 * 
	 * @param transactionNote 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setTransactionNote(String transactionNote) {

		this.transactionNote = transactionNote;
	}

	/**
	 * setTransferReasonCode.
	 * 
	 * @param transferReasonCode 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setTransferReasonCode(Integer transferReasonCode) {

		this.transferReasonCode = transferReasonCode;
	}
	
	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

}
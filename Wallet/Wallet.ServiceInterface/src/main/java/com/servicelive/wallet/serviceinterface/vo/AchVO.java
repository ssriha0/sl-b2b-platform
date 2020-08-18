package com.servicelive.wallet.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.vo.ABaseVO;


// TODO: Auto-generated Javadoc
/**
 * Class AchVO.
 */
@XmlRootElement()
public class AchVO extends ABaseVO {

	/** serialVersionUID. */
	private static final long serialVersionUID = -4526280867505101469L;

	/** accountId. */
	private Long accountId;

	/** amount. */
	private double amount;

	/** ledgerEntryId. */
	private long ledgerEntryId;
	
	private Long hsAuthRespId;
	
	private boolean hsWebLive;

	/**
	 * AchVO.
	 */
	public AchVO() {

	}

	/**
	 * AchVO.
	 * 
	 * @param vo 
	 */
	public AchVO(ABaseVO vo) {

		super(vo);
	}

	/**
	 * getAccountId.
	 * 
	 * @return long
	 */
	public Long getAccountId() {

		return accountId;
	}

	/**
	 * getAmount.
	 * 
	 * @return double
	 */
	public double getAmount() {

		return amount;
	}

	/**
	 * getLedgerEntryId.
	 * 
	 * @return long
	 */
	public long getLedgerEntryId() {

		return ledgerEntryId;
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
	 * setAmount.
	 * 
	 * @param amount 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setAmount(double amount) {

		this.amount = amount;
	}

	/**
	 * setLedgerEntryId.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setLedgerEntryId(long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	public Long getHsAuthRespId() {
		return hsAuthRespId;
	}

	public void setHsAuthRespId(Long hsAuthRespId) {
		this.hsAuthRespId = hsAuthRespId;
	}
	public boolean isHsWebLive() {
		return hsWebLive;
	}

	public void setHsWebLive(boolean hsWebLive) {
		this.hsWebLive = hsWebLive;
	}
}
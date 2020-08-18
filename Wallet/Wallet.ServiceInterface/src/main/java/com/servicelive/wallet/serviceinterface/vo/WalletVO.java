package com.servicelive.wallet.serviceinterface.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.common.vo.ABaseVO;
import com.servicelive.common.vo.SLCreditCardVO;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Class WalletVO.
 */
@XmlRootElement()
public class WalletVO extends ABaseVO {

	/** serialVersionUID. */
	private static final long serialVersionUID = -2904710734175035010L;

	/** ach. */
	private AchVO ach;

	/** creditCard. */
	private SLCreditCardVO creditCard;

	/** ledger. */
	private LedgerVO ledger;

	/** orderPricing. */
	private OrderPricingVO orderPricing;

	/** valueLink. */
	private ValueLinkVO valueLink;
	
	/** providerMaxWithdrawalLimit. */
	private Double providerMaxWithdrawalLimit;
	
	/** providerMaxWithdrawalNo. */
	private Integer providerMaxWithdrawalNo;
	
	/** The instantACH flag */
	private Boolean instantACH;
	
	private Boolean hsWebFlag;
	
	/**
	 * getproviderMaxWithdrawalNo.
	 * 
	 * @return Integer
	 */
	public Integer getProviderMaxWithdrawalNo() {

		return providerMaxWithdrawalNo;
	}

	/**
	 * setProviderMaxWithdrawalNo.
	 * 
	 * @param providerMaxWithdrawalNo 
	 * 
	 * @return void
	 */
	public void setProviderMaxWithdrawalNo(Integer providerMaxWithdrawalNo) {

		this.providerMaxWithdrawalNo = providerMaxWithdrawalNo;
	}
	/**
	 * getProviderMaxWithdrawalLimit.
	 * 
	 * @return Double
	 */
	public Double getProviderMaxWithdrawalLimit() {

		return providerMaxWithdrawalLimit;
	}

	/**
	 * setProviderMaxWithdrawalLimit.
	 * 
	 * @param providerMaxWithdrawalLimit 
	 * 
	 * @return void
	 */
	public void setProviderMaxWithdrawalLimit(Double providerMaxWithdrawalLimit) {

		this.providerMaxWithdrawalLimit = providerMaxWithdrawalLimit;
	}
	
	/**
	 * WalletVO.
	 */
	public WalletVO() {

	}

	/**
	 * getAch.
	 * 
	 * @return AchVO
	 */
	public AchVO getAch() {

		return ach;
	}

	/**
	 * getCreditCard.
	 * 
	 * @return CreditCardVO
	 */
	public SLCreditCardVO getCreditCard() {

		return creditCard;
	}

	/**
	 * getLedger.
	 * 
	 * @return LedgerVO
	 */
	public LedgerVO getLedger() {

		return ledger;
	}

	/**
	 * getOrderPricing.
	 * 
	 * @return OrderPricingVO
	 */
	public OrderPricingVO getOrderPricing() {

		return orderPricing;
	}

	/**
	 * getValueLink.
	 * 
	 * @return ValueLinkVO
	 */
	public ValueLinkVO getValueLink() {

		return valueLink;
	}
	
	/**
	 * Gets the instantACH flag
	 * 
	 * @return the instantACH flag
	 */
	public Boolean getInstantACH() {
		return instantACH;
	}

	/**
	 * setAch.
	 * 
	 * @param ach 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setAch(AchVO ach) {

		this.ach = ach;
	}

	/**
	 * setCreditCard.
	 * 
	 * @param creditCard 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setCreditCard(SLCreditCardVO creditCard) {

		this.creditCard = creditCard;
	}

	/**
	 * setLedger.
	 * 
	 * @param ledger 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setLedger(LedgerVO ledger) {

		this.ledger = ledger;
	}

	/**
	 * setOrderPricing.
	 * 
	 * @param orderPricing 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setOrderPricing(OrderPricingVO orderPricing) {

		this.orderPricing = orderPricing;
	}

	/**
	 * setValueLink.
	 * 
	 * @param valueLink 
	 * 
	 * @return void
	 */
	@XmlElement()
	public void setValueLink(ValueLinkVO valueLink) {

		this.valueLink = valueLink;
	}
	
	/**
	 * Sets the instantACH flag
	 * 
	 * @param instantACH the new instantACH flag
	 */
	@XmlElement()
	public void setInstantACH(Boolean instantACH) {
		this.instantACH = instantACH;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public Boolean getHsWebFlag() {
		return hsWebFlag;
	}

	public void setHsWebFlag(Boolean hsWebFlag) {
		this.hsWebFlag = hsWebFlag;
	}
}
package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ShcUpsellPayment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_upsell_payment", uniqueConstraints = { @UniqueConstraint(columnNames = { "shc_order_id" }) })
public class ShcUpsellPayment extends AbstractShcUpsellPayment implements
		java.io.Serializable {

	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ShcUpsellPayment() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcUpsellPayment(Integer shcUpsellPaymentId, String paymentMethodInd,
			String paymentAccNo) {
		super(shcUpsellPaymentId, paymentMethodInd, paymentAccNo);
	}

	/** full constructor */
	public ShcUpsellPayment(Integer shcUpsellPaymentId, ShcOrder shcOrder,
			String paymentMethodInd, String paymentAccNo, String paymentExpDate,
			String authNo, String secPaymentMethodInd, String secPaymentAccNo,
			String secPaymentExpDate, String secAuthNo, Double amountCollected,
			Double priAmountCollected, Double secAmountCollected,
			Date createdDate, Date modifiedDate, String modifiedBy) {
		super(shcUpsellPaymentId, shcOrder, paymentMethodInd, paymentAccNo,
				paymentExpDate, authNo, secPaymentMethodInd, secPaymentAccNo,
				secPaymentExpDate, secAuthNo, amountCollected,
				priAmountCollected, secAmountCollected, createdDate,
				modifiedDate, modifiedBy);
	}

}

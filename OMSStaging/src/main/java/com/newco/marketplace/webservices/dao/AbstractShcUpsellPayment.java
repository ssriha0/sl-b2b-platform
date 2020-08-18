package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AbstractShcUpsellPayment entity provides the base persistence definition of
 * the ShcUpsellPayment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractShcUpsellPayment implements java.io.Serializable {

	// Fields

	private Integer shcUpsellPaymentId;
	private ShcOrder shcOrder;
	private String paymentMethodInd;
	private String paymentAccNo;
	private String paymentExpDate;
	private String authNo;
	private String secPaymentMethodInd;
	private String secPaymentAccNo;
	private String secPaymentExpDate;
	private String secAuthNo;
	private Double amountCollected;
	private Double priAmountCollected;
	private Double secAmountCollected;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;

	// Constructors

	/** default constructor */
	public AbstractShcUpsellPayment() {
		// intentionally blank
	}

	/** minimal constructor */
	public AbstractShcUpsellPayment(Integer shcUpsellPaymentId,
			String paymentMethodInd, String paymentAccNo) {
		this.shcUpsellPaymentId = shcUpsellPaymentId;
		this.paymentMethodInd = paymentMethodInd;
		this.paymentAccNo = paymentAccNo;
	}

	/** full constructor */
	public AbstractShcUpsellPayment(Integer shcUpsellPaymentId,
			ShcOrder shcOrder, String paymentMethodInd, String paymentAccNo,
			String paymentExpDate, String authNo, String secPaymentMethodInd,
			String secPaymentAccNo, String secPaymentExpDate, String secAuthNo,
			Double amountCollected, Double priAmountCollected,
			Double secAmountCollected, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		this.shcUpsellPaymentId = shcUpsellPaymentId;
		this.shcOrder = shcOrder;
		this.paymentMethodInd = paymentMethodInd;
		this.paymentAccNo = paymentAccNo;
		this.paymentExpDate = paymentExpDate;
		this.authNo = authNo;
		this.secPaymentMethodInd = secPaymentMethodInd;
		this.secPaymentAccNo = secPaymentAccNo;
		this.secPaymentExpDate = secPaymentExpDate;
		this.secAuthNo = secAuthNo;
		this.amountCollected = amountCollected;
		this.priAmountCollected = priAmountCollected;
		this.secAmountCollected = secAmountCollected;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "shc_upsell_payment_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getShcUpsellPaymentId() {
		return this.shcUpsellPaymentId;
	}

	public void setShcUpsellPaymentId(Integer shcUpsellPaymentId) {
		this.shcUpsellPaymentId = shcUpsellPaymentId;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "shc_order_id", unique = true, nullable = true, insertable = true, updatable = true)
	public ShcOrder getShcOrder() {
		return this.shcOrder;
	}

	public void setShcOrder(ShcOrder shcOrder) {
		this.shcOrder = shcOrder;
	}

	@Column(name = "payment_method_ind", unique = false, nullable = false, insertable = true, updatable = true)
	public String getPaymentMethodInd() {
		return this.paymentMethodInd;
	}

	public void setPaymentMethodInd(String paymentMethodInd) {
		this.paymentMethodInd = paymentMethodInd;
	}

	@Column(name = "payment_acc_no", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getPaymentAccNo() {
		return this.paymentAccNo;
	}

	public void setPaymentAccNo(String paymentAccNo) {
		this.paymentAccNo = paymentAccNo;
	}

	@Column(name = "payment_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getPaymentExpDate() {
		return this.paymentExpDate;
	}

	public void setPaymentExpDate(String paymentExpDate) {
		this.paymentExpDate = paymentExpDate;
	}

	@Column(name = "auth_no", unique = false, nullable = true, insertable = true, updatable = true)
	public String getAuthNo() {
		return this.authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}

	@Column(name = "sec_payment_method_ind", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSecPaymentMethodInd() {
		return this.secPaymentMethodInd;
	}

	public void setSecPaymentMethodInd(String secPaymentMethodInd) {
		this.secPaymentMethodInd = secPaymentMethodInd;
	}

	@Column(name = "sec_payment_acc_no", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public String getSecPaymentAccNo() {
		return this.secPaymentAccNo;
	}

	public void setSecPaymentAccNo(String secPaymentAccNo) {
		this.secPaymentAccNo = secPaymentAccNo;
	}

	@Column(name = "sec_payment_exp_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public String getSecPaymentExpDate() {
		return this.secPaymentExpDate;
	}

	public void setSecPaymentExpDate(String secPaymentExpDate) {
		this.secPaymentExpDate = secPaymentExpDate;
	}

	@Column(name = "sec_auth_no", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSecAuthNo() {
		return this.secAuthNo;
	}

	public void setSecAuthNo(String secAuthNo) {
		this.secAuthNo = secAuthNo;
	}

	@Column(name = "amount_collected", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	public Double getAmountCollected() {
		return this.amountCollected;
	}

	public void setAmountCollected(Double amountCollected) {
		this.amountCollected = amountCollected;
	}

	@Column(name = "pri_amount_collected", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	public Double getPriAmountCollected() {
		return this.priAmountCollected;
	}

	public void setPriAmountCollected(Double priAmountCollected) {
		this.priAmountCollected = priAmountCollected;
	}

	@Column(name = "sec_amount_collected", unique = false, nullable = true, insertable = true, updatable = true, precision = 22, scale = 0)
	public Double getSecAmountCollected() {
		return this.secAmountCollected;
	}

	public void setSecAmountCollected(Double secAmountCollected) {
		this.secAmountCollected = secAmountCollected;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
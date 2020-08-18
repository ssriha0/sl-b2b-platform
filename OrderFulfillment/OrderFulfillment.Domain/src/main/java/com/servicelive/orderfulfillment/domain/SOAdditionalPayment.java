/**
 * 
 */
package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Parameter;

import com.servicelive.orderfulfillment.domain.type.PaymentCardType;
import com.servicelive.orderfulfillment.domain.type.PaymentType;

/**
 * @author sahmed
 * 
 */
@Entity
@Table(name = "so_additional_payment")
@XmlRootElement()
public class SOAdditionalPayment extends SORelative {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 9198121661215439661L;

	/*
	 * Unfortunately JPA does not allow a PrimaryKey Object to participate in
	 * Entity Associations. As noted by Christian Bauer & King (pg. 281)
	 */
	@Id
	@GeneratedValue(generator = "additionalPaymentForeignGenerator")
	@org.hibernate.annotations.GenericGenerator(name = "additionalPaymentForeignGenerator", strategy = "foreign", parameters = @Parameter(name = "property", value = "serviceOrder"))
	@Column(name = "so_id")
	@SuppressWarnings("unused")
	private String soId;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "additionalPayment", optional = false)
	protected ServiceOrder serviceOrder;

	@Column(name = "additional_payment_desc")
	private String description;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "payment_amount")
	private BigDecimal paymentAmount;
	
	@Column(name = "card_type")
	private String cardType;

	@Column(name = "card_expire_month")
	private Integer expirationDateMonth;

	@Column(name = "card_expire_year")
	private Integer expirationDateYear;

	@Column(name = "cc_no")
	private String creditCardNumber;

	@Column(name = "check_no")
	private String checkNumber;

	@Column(name = "auth_no")
	private String authorizationNumber;

	@Override
	public ServiceOrder getServiceOrder() {
		return this.serviceOrder;
	}
	
	@Column(name = "masked_acc_num")
	private String maskedAccountNumber;

	@Column(name = "token")
	private String token;

	@Transient
	private String responseXML;
	
	@Transient
	private String editOrCancel;

	public String getDescription() {
		return description;
	}

	public PaymentType getPaymentType() {
		if (paymentType == null) return null;
		try {
			return PaymentType.fromShortName(paymentType);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	
	public PaymentCardType getCardType() {
		//TODO fix this; cardType is always int in the DB!
		if (cardType == null) return null;
		try {
			return PaymentCardType.fromId(Integer.valueOf(cardType));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public Integer getExpirationDateMonth() {
		return expirationDateMonth;
	}

	public Integer getExpirationDateYear() {
		return expirationDateYear;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public String getAuthorizationNumber() {
		return authorizationNumber;
	}

	@Override
	@XmlTransient
	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement
	public void setPaymentType(PaymentType paymentType) {
		if (paymentType != null) {
			this.paymentType = paymentType.getShortName();
		} else {
			this.paymentType = null;
		}
	}

	@XmlElement
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	@XmlElement
	public void setCardType(PaymentCardType cardType) {
		if (cardType != null) {
			this.cardType = String.valueOf(cardType.getId());
		} else {
			this.cardType = null;
		}
	}

	@XmlElement
	public void setExpirationDateMonth(Integer expirationDateMonth) {
		this.expirationDateMonth = expirationDateMonth;
	}

	@XmlElement
	public void setExpirationDateYear(Integer expirationDateYear) {
		this.expirationDateYear = expirationDateYear;
	}

	@XmlElement
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	@XmlElement
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	@XmlElement
	public void setAuthorizationNumber(String authorizationNumber) {
		this.authorizationNumber = authorizationNumber;
	}

	public String getMaskedAccountNumber() {
		return maskedAccountNumber;
	}

	public void setMaskedAccountNumber(String maskedAccountNumber) {
		this.maskedAccountNumber = maskedAccountNumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResponseXML() {
		return responseXML;
	}

	public void setResponseXML(String responseXML) {
		this.responseXML = responseXML;
	}

	public String getEditOrCancel() {
		return editOrCancel;
	}

	public void setEditOrCancel(String editOrCancel) {
		this.editOrCancel = editOrCancel;
	}
	
	
}

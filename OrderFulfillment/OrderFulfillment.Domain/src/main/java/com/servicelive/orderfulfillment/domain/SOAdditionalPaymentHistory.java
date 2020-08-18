/**
 * 
 */
package com.servicelive.orderfulfillment.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "so_additional_payment_history")
@XmlRootElement()
public class SOAdditionalPaymentHistory implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 9198121661215439661L;

	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "history_id")
	private Integer historyId;
	
	@Column(name = "so_id")
	private String soId;

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
	
	@Column(name = "response")
	private String response;

	@Column(name = "masked_acc_num")
	private String maskedAccountNumber;

	@Column(name = "token")
	private String token;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_date")
	private Date modifiedDate;

	

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

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	

	
	
}

package com.servicelive.wallet.batch.activity.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * The Class SpBalanceReportVO.
 */
public class SpBalanceReportVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1374471418794703793L;

	/** The account. */
	private String account;

	/** The account type. */
	private String accountType;

	/** The entity id. */
	private Integer entityId;

	/** The entity type id. */
	private Integer entityTypeId;

	/** The promo code. */
	private Integer promoCode;

	/** The sl balance. */
	private Double slBalance;

	/** The sl balance date. */
	private Date slBalanceDate;

	/** The vl balance. */
	private Double vlBalance;

	/** The vl balance date. */
	private Date vlBalanceDate;

	/**
	 * Gets the account.
	 * 
	 * @return the account
	 */
	public String getAccount() {

		if (account == null) {
			account = "";
		}
		return account;
	}

	/**
	 * Gets the account type.
	 * 
	 * @return the account type
	 */
	public String getAccountType() {

		if (accountType == null) {
			accountType = "";
		}
		return accountType;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public Integer getEntityId() {

		if (entityId == null) {
			entityId = Integer.valueOf(0);
		}
		return entityId;
	}

	/**
	 * Gets the entity type id.
	 * 
	 * @return the entity type id
	 */
	public Integer getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * Gets the promo code.
	 * 
	 * @return the promo code
	 */
	public Integer getPromoCode() {

		if (promoCode == null) {
			promoCode = Integer.valueOf(0);
		}
		return promoCode;
	}

	/**
	 * Gets the sl balance.
	 * 
	 * @return the sl balance
	 */
	public Double getSlBalance() {

		if (slBalance == null) {
			slBalance = Double.valueOf(0);
		}
		return slBalance;
	}

	/**
	 * Gets the sl balance date.
	 * 
	 * @return the sl balance date
	 */
	public Date getSlBalanceDate() {

		if (slBalanceDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(1999, 12, 31);
			slBalanceDate = new Date(cal.getTimeInMillis());
		}
		return slBalanceDate;
	}

	/**
	 * Gets the vl balance.
	 * 
	 * @return the vl balance
	 */
	public Double getVlBalance() {

		if (vlBalance == null) {
			vlBalance = Double.valueOf(0);
		}
		return vlBalance;
	}

	/**
	 * Gets the vl balance date.
	 * 
	 * @return the vl balance date
	 */
	public Date getVlBalanceDate() {

		if (vlBalanceDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.set(1999, 12, 31);
			vlBalanceDate = new Date(cal.getTimeInMillis());
		}
		return vlBalanceDate;
	}

	/**
	 * Sets the account.
	 * 
	 * @param account the new account
	 */
	public void setAccount(String account) {

		this.account = account;
	}

	/**
	 * Sets the account type.
	 * 
	 * @param accountType the new account type
	 */
	public void setAccountType(String accountType) {

		this.accountType = accountType;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Integer entityId) {

		this.entityId = entityId;
	}

	/**
	 * Sets the entity type id.
	 * 
	 * @param entityTypeId the new entity type id
	 */
	public void setEntityTypeId(Integer entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * Sets the promo code.
	 * 
	 * @param promoCode the new promo code
	 */
	public void setPromoCode(Integer promoCode) {

		this.promoCode = promoCode;
	}

	/**
	 * Sets the sl balance.
	 * 
	 * @param slBalance the new sl balance
	 */
	public void setSlBalance(Double slBalance) {

		this.slBalance = slBalance;
	}

	/**
	 * Sets the sl balance date.
	 * 
	 * @param slBalanceDate the new sl balance date
	 */
	public void setSlBalanceDate(Date slBalanceDate) {

		this.slBalanceDate = slBalanceDate;
	}

	/**
	 * Sets the vl balance.
	 * 
	 * @param vlBalance the new vl balance
	 */
	public void setVlBalance(Double vlBalance) {

		this.vlBalance = vlBalance;
	}

	/**
	 * Sets the vl balance date.
	 * 
	 * @param vlBalanceDate the new vl balance date
	 */
	public void setVlBalanceDate(Date vlBalanceDate) {

		this.vlBalanceDate = vlBalanceDate;
	}

}

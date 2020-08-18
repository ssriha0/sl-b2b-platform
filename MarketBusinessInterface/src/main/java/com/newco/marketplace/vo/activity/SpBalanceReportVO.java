package com.newco.marketplace.vo.activity;

import java.sql.Date;
import java.util.Calendar;

import com.sears.os.vo.SerializableBaseVO;

public class SpBalanceReportVO extends SerializableBaseVO {

	private static final long serialVersionUID = -1374471418794703793L;
	private Integer entityTypeId;
	private Integer entityId;
	private String accountType;
	private Integer promoCode;
	private String account;
	private Double slBalance;
	private Date slBalanceDate;
	private Double vlBalance;
	private Date vlBalanceDate;
	
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	
	public Integer getEntityId() {
		if (entityId == null){
			entityId = Integer.valueOf(0);
		}
		return entityId;
	}
	
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	public String getAccountType() {
		if (accountType == null){
			accountType = "";
		}
		return accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Integer getPromoCode() {
		if (promoCode == null){
			promoCode = Integer.valueOf(0);
		}
		return promoCode;
	}
	
	public void setPromoCode(Integer promoCode) {
		this.promoCode = promoCode;
	}
	
	public String getAccount() {
		if (account == null){
			account = "";
		}
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getSlBalance() {
		if (slBalance == null){
			slBalance = Double.valueOf(0);
		}
		return slBalance;
	}
	
	public void setSlBalance(Double slBalance) {
		this.slBalance = slBalance;
	}

	public Date getSlBalanceDate() {
		if (slBalanceDate == null){
			Calendar cal = Calendar.getInstance();
			cal.set(1999, 12, 31);
			slBalanceDate = new Date(cal.getTimeInMillis());
		}
		return slBalanceDate;
	}

	public void setSlBalanceDate(Date slBalanceDate) {
		this.slBalanceDate = slBalanceDate;
	}

	public Double getVlBalance() {
		if (vlBalance == null){
			vlBalance = Double.valueOf(0);
		}
		return vlBalance;
	}

	public void setVlBalance(Double vlBalance) {
		this.vlBalance = vlBalance;
	}

	public Date getVlBalanceDate() {
		if (vlBalanceDate == null){
			Calendar cal = Calendar.getInstance();
			cal.set(1999, 12, 31);
			vlBalanceDate = new Date(cal.getTimeInMillis());
		}
		return vlBalanceDate;
	}

	public void setVlBalanceDate(Date vlBalanceDate) {
		this.vlBalanceDate = vlBalanceDate;
	}

}

package com.servicelive.esb.integration.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class HsrBuyerNotification implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = -7793064282441893469L;
	  private long hsrBuyerNotificationId;
	  private Long buyerNotificationId;
	  private String unitNumber;
	  private String orderNumber;
	  private Date routedDate;
		private String techId;
	  
	  public HsrBuyerNotification(long hsrBuyerNotificationId,
			Long buyerNotificationId, String unitNumber, String orderNumber,
			Date routedDate, String techId) {
		super();
		this.hsrBuyerNotificationId = hsrBuyerNotificationId;
		this.buyerNotificationId = buyerNotificationId;
		this.unitNumber = unitNumber;
		this.orderNumber = orderNumber;
		this.routedDate = routedDate;
		this.techId = techId;
	}

	public long getHsrBuyerNotificationId() {
		return hsrBuyerNotificationId;
	}

	public Long getBuyerNotificationId() {
		return buyerNotificationId;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public Date getRoutedDate() {
		return routedDate;
	}

	public String getTechId() {
		return techId;
	}

	public void setHsrBuyerNotificationId(long hsrBuyerNotificationId) {
		this.hsrBuyerNotificationId = hsrBuyerNotificationId;
	}

	public void setBuyerNotificationId(Long buyerNotificationId) {
		this.buyerNotificationId = buyerNotificationId;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
	}

	public void setTechId(String techId) {
		this.techId = techId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
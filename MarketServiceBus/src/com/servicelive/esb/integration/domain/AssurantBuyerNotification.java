/**
 * 
 */
package com.servicelive.esb.integration.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sahmed
 *
 */
public class AssurantBuyerNotification implements Serializable {

	/** generated serialVersionUID */
	private static final long serialVersionUID = 3843683744653332413L;
	private long assurantBuyerNotificationId;
	private Long buyerNotificationId;
	private Date etaOrShippingDate;
	private String shippingAirBillNumber;
	private String returnAirBillNumber;
	private String incidentActionDescription;
	
	public AssurantBuyerNotification() {}

	public AssurantBuyerNotification(long assurantBuyerNotificationId,
			Long buyerNotificationId, Date etaOrShippingDate,
			String shippingAirBillNumber, String returnAirBillNumber, String incidentActionDescription) {
			this.assurantBuyerNotificationId = assurantBuyerNotificationId;
			this.buyerNotificationId = buyerNotificationId;
			this.etaOrShippingDate = etaOrShippingDate;
			this.returnAirBillNumber = returnAirBillNumber;
			this.shippingAirBillNumber = shippingAirBillNumber;
			this.incidentActionDescription = incidentActionDescription;
	}

	public long getAssurantBuyerNotificationId() {
		return this.assurantBuyerNotificationId;
	}
	public Long getBuyerNotificationId() {
		return this.buyerNotificationId;
	}
	public Date getEtaOrShippingDate() {
		return this.etaOrShippingDate;
	}
	public String getReturnAirBillNumber() {
		return this.returnAirBillNumber;
	}
	public String getShippingAirBillNumber() {
		return this.shippingAirBillNumber;
	}
	public void setAssurantBuyerNotificationId(long assurantBuyerNotificationId) {
		this.assurantBuyerNotificationId = assurantBuyerNotificationId;
	}
	public void setBuyerNotificationId(Long buyerNotificationId) {
		this.buyerNotificationId = buyerNotificationId;
	}
	public void setEtaOrShippingDate(Date etaOrShippingDate) {
		this.etaOrShippingDate = etaOrShippingDate;
	}
	public void setReturnAirBillNumber(String returnAirBillNumber) {
		this.returnAirBillNumber = returnAirBillNumber;
	}
	public void setShippingAirBillNumber(String shippingAirBillNumber) {
		this.shippingAirBillNumber = shippingAirBillNumber;
	}
	
	public String getIncidentActionDescription() {
		return incidentActionDescription;
	}

	public void setIncidentActionDescription(String incidentActionDescription) {
		this.incidentActionDescription = incidentActionDescription;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

/**
 * 
 */
package com.newco.marketplace.api.mobile.beans.vo;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author Infosys $ $Date: 2015/05/07
 * @version 1.0
 */
public class RequestBidVO {

	private String soId;
	private Integer buyerId;
	private Timestamp serviceDateStart;
	private Timestamp serviceDateEnd;
	private String serviceTimeEnd;
	private Integer vendorId;
	private Integer routedResourceId;
	private Double laborRate;
	private Double totalHours;
	private Double totalLaborPrice;
	private Double partsPrice;
	private Double totalLaborPartsPrice;
	private String bidExpirationHour;
	private Date bidExpirationDate;
	private Date newServiceDateStart;
	private Date newServiceDateEnd;
	private String newServiceTimeStart;
	private String newServiceTimeEnd;
	private boolean newServiceDateType;
	private String comment;
	private String userName;
	private boolean saveBidToActivityLogFailed=true;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Timestamp getServiceDateStart() {
		return serviceDateStart;
	}
	public void setServiceDateStart(Timestamp serviceDateStart) {
		this.serviceDateStart = serviceDateStart;
	}
	public Timestamp getServiceDateEnd() {
		return serviceDateEnd;
	}
	public void setServiceDateEnd(Timestamp serviceDateEnd) {
		this.serviceDateEnd = serviceDateEnd;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getRoutedResourceId() {
		return routedResourceId;
	}
	public void setRoutedResourceId(Integer routedResourceId) {
		this.routedResourceId = routedResourceId;
	}
	public Double getLaborRate() {
		return laborRate;
	}
	public void setLaborRate(Double laborRate) {
		this.laborRate = laborRate;
	}
	public Double getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}
	public Double getTotalLaborPrice() {
		return totalLaborPrice;
	}
	public void setTotalLaborPrice(Double totalLaborPrice) {
		this.totalLaborPrice = totalLaborPrice;
	}
	public Double getPartsPrice() {
		return partsPrice;
	}
	public void setPartsPrice(Double partsPrice) {
		this.partsPrice = partsPrice;
	}
	public Double getTotalLaborPartsPrice() {
		return totalLaborPartsPrice;
	}
	public void setTotalLaborPartsPrice(Double totalLaborPartsPrice) {
		this.totalLaborPartsPrice = totalLaborPartsPrice;
	}
	public String getBidExpirationHour() {
		return bidExpirationHour;
	}
	public void setBidExpirationHour(String bidExpirationHour) {
		this.bidExpirationHour = bidExpirationHour;
	}
	public Date getBidExpirationDate() {
		return bidExpirationDate;
	}
	public void setBidExpirationDate(Date bidExpirationDate) {
		this.bidExpirationDate = bidExpirationDate;
	}
	public Date getNewServiceDateStart() {
		return newServiceDateStart;
	}
	public void setNewServiceDateStart(Date newServiceDateStart) {
		this.newServiceDateStart = newServiceDateStart;
	}
	public Date getNewServiceDateEnd() {
		return newServiceDateEnd;
	}
	public void setNewServiceDateEnd(Date newServiceDateEnd) {
		this.newServiceDateEnd = newServiceDateEnd;
	}
	public String getNewServiceTimeStart() {
		return newServiceTimeStart;
	}
	public void setNewServiceTimeStart(String newServiceTimeStart) {
		this.newServiceTimeStart = newServiceTimeStart;
	}
	public String getNewServiceTimeEnd() {
		return newServiceTimeEnd;
	}
	public void setNewServiceTimeEnd(String newServiceTimeEnd) {
		this.newServiceTimeEnd = newServiceTimeEnd;
	}
	public boolean isNewServiceDateType() {
		return newServiceDateType;
	}
	public void setNewServiceDateType(boolean newServiceDateType) {
		this.newServiceDateType = newServiceDateType;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isSaveBidToActivityLogFailed() {
		return saveBidToActivityLogFailed;
	}
	public void setSaveBidToActivityLogFailed(boolean saveBidToActivityLogFailed) {
		this.saveBidToActivityLogFailed = saveBidToActivityLogFailed;
	}
}

package com.newco.marketplace.dto.vo.financemanger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.newco.marketplace.vo.provider.BaseVO;

public class ProviderSOReportVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer providerFirmId;
	private String providerFirmName;
	private String buyerId;
	private String buyerName;
	private Date completedDate;
	private String soId;
	private Date datePaid;
	private BigDecimal grossLabor;
	private BigDecimal grossOther;
	private BigDecimal totalFinalPrice;
	private BigDecimal serviceLiveFee;
	private BigDecimal netPayment;
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	public BigDecimal getGrossLabor() {
		return grossLabor;
	}
	public void setGrossLabor(BigDecimal grossLabor) {
		this.grossLabor = grossLabor;
	}
	public BigDecimal getGrossOther() {
		return grossOther;
	}
	public void setGrossOther(BigDecimal grossOther) {
		this.grossOther = grossOther;
	}
	public BigDecimal getTotalFinalPrice() {
		return totalFinalPrice;
	}
	public void setTotalFinalPrice(BigDecimal totalFinalPrice) {
		this.totalFinalPrice = totalFinalPrice;
	}
	public BigDecimal getServiceLiveFee() {
		return serviceLiveFee;
	}
	public void setServiceLiveFee(BigDecimal serviceLiveFee) {
		this.serviceLiveFee = serviceLiveFee;
	}
	public BigDecimal getNetPayment() {
		return netPayment;
	}
	public void setNetPayment(BigDecimal netPayment) {
		this.netPayment = netPayment;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public Date getDatePaid() {
		return datePaid;
	}
	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public String getProviderFirmName() {
		return providerFirmName;
	}
	public void setProviderFirmName(String providerFirmName) {
		this.providerFirmName = providerFirmName;
	}


}

package com.newco.marketplace.dto.vo.financemanger;

import java.math.BigDecimal;
import java.util.Date;

import javax.mail.Address;

import com.newco.marketplace.vo.provider.BaseVO;

public class BuyerSOReportVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String buyerId;
	private String buyerName;
	private Integer providerFirmId;
	private String providerFirmName;
	private String taxPayerType;
	private String exempt;
	private String tinType;
	private String encrypedTaxPayerId;
	private String decrypedTaxPayerId;
	private Date paymentDate;
	private String serviceOrderId;
	private BigDecimal totalGrossPayment;
	private BigDecimal buyerPostingFee;
	private Double postingFee;
	private BigDecimal providerServiceLiveFee;
	private int taxPayerTypeId;
    private String dbaName;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;
    private String zip4;
    private String aptNo;
    private String aptNoStreet2;
    private String completeAddress;
    
    public Double getPostingFee() {
		return postingFee;
	}
	public void setPostingFee(Double postingFee) {
		this.postingFee = postingFee;
	}
    
	public String getDecrypedTaxPayerId() {
		return decrypedTaxPayerId;
	}
	public void setDecrypedTaxPayerId(String decrypedTaxPayerId) {
		this.decrypedTaxPayerId = decrypedTaxPayerId;
	}
	public int getTaxPayerTypeId() {
		return taxPayerTypeId;
	}
	public void setTaxPayerTypeId(int taxPayerTypeId) {
		this.taxPayerTypeId = taxPayerTypeId;
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
	public String getTaxPayerType() {
		return taxPayerType;
	}
	public void setTaxPayerType(String taxPayerType) {
		this.taxPayerType = taxPayerType;
	}
	public String getExempt() {
		return exempt;
	}
	public void setExempt(String exempt) {
		this.exempt = exempt;
	}
	public String getTinType() {
		return tinType;
	}
	public void setTinType(String tinType) {
		this.tinType = tinType;
	}
	public String getEncrypedTaxPayerId() {
		return encrypedTaxPayerId;
	}
	public void setEncrypedTaxPayerId(String encrypedTaxPayerId) {
		this.encrypedTaxPayerId = encrypedTaxPayerId;
	}
	public String getEncryptedId() {
		return decrypedTaxPayerId;
	}
	public void setEncryptedId(String decrypedTaxPayerId) {
		this.decrypedTaxPayerId = decrypedTaxPayerId;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public BigDecimal getTotalGrossPayment() {
		return totalGrossPayment;
	}
	public void setTotalGrossPayment(BigDecimal totalGrossPayment) {
		this.totalGrossPayment = totalGrossPayment;
	}
	public BigDecimal getBuyerPostingFee() {
		return buyerPostingFee;
	}
	public void setBuyerPostingFee(BigDecimal buyerPostingFee) {
		this.buyerPostingFee = buyerPostingFee;
	}
	public BigDecimal getProviderServiceLiveFee() {
		return providerServiceLiveFee;
	}
	public void setProviderServiceLiveFee(BigDecimal providerServiceLiveFee) {
		this.providerServiceLiveFee = providerServiceLiveFee;
	}
	public String getDbaName() {
		return dbaName;
	}
	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}
	public String getStreet1() {
		return street1;
	}
	public void setStreet1(String street1) {
		this.street1 = street1;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
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
	public String getCompleteAddress() {
		return completeAddress;
	}
	public void setCompleteAddress(String completeAddress) {
		this.completeAddress = completeAddress;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}
	public String getAptNoStreet2() {
		return aptNoStreet2;
	}
	public void setAptNoStreet2(String aptNoStreet2) {
		this.aptNoStreet2 = aptNoStreet2;
	}	

}

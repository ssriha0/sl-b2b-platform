package com.newco.marketplace.api.beans.leadprofile.leadprofilerequest;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XmlRootElement(name = "leadProfileBillingRequest")
@XStreamAlias("leadProfileBillingRequest")
public class LeadProfileBillingRequest {
	
	@XStreamAlias("version")   
	@XStreamAsAttribute()   
	private String version = PublicAPIConstant.SORESPONSE_VERSION;
	 
	@XStreamAlias("xsi:schemaLocation")   
	@XStreamAsAttribute()   
	private String schemaLocation = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileBillingRequest.xsd";
	 
	@XStreamAlias("xmlns")   
	@XStreamAsAttribute()   
	private String namespace = PublicAPIConstant.ProviderAccount.NAMESPACE+"leadProfileBillingRequest";
	
	@XStreamAlias("xmlns:xsi")   
	@XStreamAsAttribute()   
	private String schemaInstance = PublicAPIConstant.SCHEMA_INSTANCE;
	
	@XStreamAlias("partnerId")
	private String partnerId;
	
	@XStreamAlias("cardType")
	private String cardType;
	
	@XStreamAlias("cardNo")
	private String cardNo;
	
	@XStreamAlias("expirationYear")
	private Integer expirationYear;
	
	@XStreamAlias("expirationMonth")
	private Integer expirationMonth;
	
	@XStreamAlias("ccv")
	private Integer ccv;
	
	//TODO - these fields are not given in the api spec
	/*@XStreamAlias("amount")
	private Double  amount;
	
	//TODO -uncomment once clarification is obtained regarding description value
	
	@XStreamAlias("description")
	private String description;
	*/
	
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	/*public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	*/
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public Integer getCcv() {
		return ccv;
	}
	public void setCcv(Integer ccv) {
		this.ccv = ccv;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSchemaLocation() {
		return schemaLocation;
	}
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getSchemaInstance() {
		return schemaInstance;
	}
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}

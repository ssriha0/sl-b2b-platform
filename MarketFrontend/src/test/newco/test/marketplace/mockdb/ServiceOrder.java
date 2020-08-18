package test.newco.test.marketplace.mockdb;

import java.util.Date;

//TODO this is a 'mock' object, that will eventually be deleted.
// The one we want to use has the same name but is in the MarketBusinessInterface
public class ServiceOrder {

	private String serviceOrderId;
	private Integer status;
	private String title;
	private Date serviceOrderDate;
	private String city;
	private String state;
	private String zip;
	private String providerName;
	private String buyerName;
	
	private String phoneNumber;
	private String userID;
	private Integer locationId;
	private Integer subStatus;
	private Integer spendLimit;
	private String note;
	//For story  8439
	private String subject;
	private String message;
	
	private Integer providersSentTo = new Integer(0);
	private Integer providersDeclined = new Integer(0);
	private Integer providersConditionalAccept = new Integer(0);
	
	public ServiceOrder()
	{
		
	}
	public ServiceOrder(ServiceOrder so)
	{
		setServiceOrderId(so.getServiceOrderId());
		setStatus(so.getStatus());
		setTitle(so.getTitle());
		setNote(so.getNote());
		//For story 8439
		setSubject(so.getSubject());
		setMessage(so.getMessage());
		setServiceOrderDate(so.getServiceOrderDate());
		setProvidersSentTo(so.getProvidersSentTo());
		setProvidersConditionalAccept(so.getProvidersConditionalAccept());
		setProvidersDeclined(so.getProvidersDeclined());
		setCity(so.getCity());
		setLocationId(so.getLocationId());
		
	}
	
	public Integer getStatus()
  {
  	return status;
  }
	public void setStatus(Integer status)
  {
  	this.status = status;
  }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getServiceOrderDate() {
		return serviceOrderDate;
	}
	public void setServiceOrderDate(Date serviceOrderDate) {
		this.serviceOrderDate = serviceOrderDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getServiceOrderId() {
		return serviceOrderId;
	}
	public void setServiceOrderId(String serviceOrderId) {
		this.serviceOrderId = serviceOrderId;
	}
	public String getPhoneNumber()
  {
  	return phoneNumber;
  }
	public void setPhoneNumber(String phoneNumber)
  {
  	this.phoneNumber = phoneNumber;
  }
	public String getUserID()
  {
  	return userID;
  }
	public void setUserID(String userID)
  {
  	this.userID = userID;
  }
	public Integer getLocationId()
  {
  	return locationId;
  }
	public void setLocationId(Integer locationId)
  {
  	this.locationId = locationId;
  }
	public Integer getSubStatus()
  {
  	return subStatus;
  }
	public void setSubStatus(Integer subStatus)
  {
  	this.subStatus = subStatus;
  }
	public Integer getSpendLimit()
  {
  	return spendLimit;
  }
	public void setSpendLimit(Integer spendLimit)
  {
  	this.spendLimit = spendLimit;
  }
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getProvidersSentTo()
	{
		return providersSentTo;
	}
	public void setProvidersSentTo(Integer providersSentTo)
	{
		this.providersSentTo = providersSentTo;
	}
	public Integer getProvidersDeclined()
	{
		return providersDeclined;
	}
	public void setProvidersDeclined(Integer providersDeclined)
	{
		this.providersDeclined = providersDeclined;
	}
	public Integer getProvidersConditionalAccept()
	{
		return providersConditionalAccept;
	}
	public void setProvidersConditionalAccept(Integer providersConditionalAccept)
	{
		this.providersConditionalAccept = providersConditionalAccept;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	//For story 8439
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

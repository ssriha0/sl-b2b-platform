package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;
import java.util.ArrayList;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderDetail extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7697696177933554447L;
	private String soId;
	private String creatorUserName;
	private String closerUserName;
	private String continueSoId;
	private Date lastStatusChange;
	private Date createdDate;
	private Date routedDate;
	private Date acceptedDate;
	private Date cancelledDate;
	private Date completedDate;
	private Date closedDate;
	private Date actArrivalStartDate;
	private Date actArrivalEndDate;
	private Double  initialPrice;
	private Double  finalPrice;
	private Double  serviceTax;
	private Double  accessFee;
	private Double  credits;
	private String sowTitle;
	private String sowDs;
	private Date scheduledDate;
	private String providerInstructions;
	private String buyerTermsCond;
	private Integer providerTermsCondResp;
	private String providerTermsCondDate;
	private String resolutionDs;
	private String modifiedDate;
	private String modifiedBy;
	private String street1;
	private String street2;
	private String city;
	private String stateCd;
	private String zip;
	private String zip4;
	private String country;
	private String pricingTypeDs;
	private String locTypeDs;
	private Integer wfStateId;
	private String wfStateDs;
	private String primarySkillCatDs;
	private ProviderDetail acceptedProvider;
	private BuyerDetail buyerDetails;
	private Buyer buyer;
	private Integer buyerId;
	private Contact buyerSupportContact;
	private Contact vendorResourceContact;
	private Contact buyerResourceSupportContact;
	private ArrayList<ServiceOrderTaskDetail> tasks;
	private ArrayList<ServiceOrderNoteDetail> notes;
	private ArrayList<ProviderDetail> routedProviders;
	private ArrayList<ProviderDetail> allProviders;
	
	public Contact getBuyerResourceSupportContact() {
		return buyerResourceSupportContact;
	}
	public void setBuyerResourceSupportContact(Contact buyerResourceSupportContact) {
		this.buyerResourceSupportContact = buyerResourceSupportContact;
	}
	public ArrayList<ProviderDetail> getAllProviders() {
		return allProviders;
	}
	public void setAllProviders(ArrayList<ProviderDetail> allProviders) {
		this.allProviders = allProviders;
	}
	public Date getAcceptedDate() {
		return acceptedDate;
	}
	public void setAcceptedDate(Date acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	public Double getAccessFee() {
		return accessFee;
	}
	public void setAccessFee(Double accessFee) {
		this.accessFee = accessFee;
	}
	public Date getActArrivalEndDate() {
		return actArrivalEndDate;
	}
	public void setActArrivalEndDate(Date actArrivalEndDate) {
		this.actArrivalEndDate = actArrivalEndDate;
	}
	public Date getActArrivalStartDate() {
		return actArrivalStartDate;
	}
	public void setActArrivalStartDate(Date actArrivalStartDate) {
		this.actArrivalStartDate = actArrivalStartDate;
	}
	public BuyerDetail getBuyerDetails() {
		return buyerDetails;
	}
	public void setBuyerDetails(BuyerDetail buyerDetails) {
		this.buyerDetails = buyerDetails;
	}
	public String getBuyerTermsCond() {
		return buyerTermsCond;
	}
	public void setBuyerTermsCond(String buyerTermsCond) {
		this.buyerTermsCond = buyerTermsCond;
	}
	public Date getCancelledDate() {
		return cancelledDate;
	}
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	public String getCloserUserName() {
		return closerUserName;
	}
	public void setCloserUserName(String closerUserName) {
		this.closerUserName = closerUserName;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public String getContinueSoId() {
		return continueSoId;
	}
	public void setContinueSoId(String continueSoId) {
		this.continueSoId = continueSoId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatorUserName() {
		return creatorUserName;
	}
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}
	public Double getCredits() {
		return credits;
	}
	public void setCredits(Double credits) {
		this.credits = credits;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Double getInitialPrice() {
		return initialPrice;
	}
	public void setInitialPrice(Double initialPrice) {
		this.initialPrice = initialPrice;
	}
	public Date getLastStatusChange() {
		return lastStatusChange;
	}
	public void setLastStatusChange(Date lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}
	public String getLocTypeDs() {
		return locTypeDs;
	}
	public void setLocTypeDs(String locTypeDs) {
		this.locTypeDs = locTypeDs;
	}
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public ArrayList<ServiceOrderNoteDetail> getNotes() {
		return notes;
	}
	public void setNotes(ArrayList<ServiceOrderNoteDetail> notes) {
		this.notes = notes;
	}
	public String getPricingTypeDs() {
		return pricingTypeDs;
	}
	public void setPricingTypeDs(String pricingTypeDs) {
		this.pricingTypeDs = pricingTypeDs;
	}
	public String getPrimarySkillCatDs() {
		return primarySkillCatDs;
	}
	public void setPrimarySkillCatDs(String primarySkillCatDs) {
		this.primarySkillCatDs = primarySkillCatDs;
	}
	public ProviderDetail getAcceptedProvider() {
		return acceptedProvider;
	}
	public void setAcceptedProvider(ProviderDetail provider) {
		this.acceptedProvider = provider;
	}
	public String getProviderInstructions() {
		return providerInstructions;
	}
	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;
	}
	public String getProviderTermsCondDate() {
		return providerTermsCondDate;
	}
	public void setProviderTermsCondDate(String providerTermsCondDate) {
		this.providerTermsCondDate = providerTermsCondDate;
	}
	public Integer getProviderTermsCondResp() {
		return providerTermsCondResp;
	}
	public void setProviderTermsCondResp(Integer providerTermsCondResp) {
		this.providerTermsCondResp = providerTermsCondResp;
	}
	public String getResolutionDs() {
		return resolutionDs;
	}
	public void setResolutionDs(String resolutionDs) {
		this.resolutionDs = resolutionDs;
	}
	public Date getRoutedDate() {
		return routedDate;
	}
	public void setRoutedDate(Date routedDate) {
		this.routedDate = routedDate;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public Double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSowDs() {
		return sowDs;
	}
	public void setSowDs(String sowDs) {
		this.sowDs = sowDs;
	}
	public String getSowTitle() {
		return sowTitle;
	}
	public void setSowTitle(String sowTitle) {
		this.sowTitle = sowTitle;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
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
	public ArrayList<ServiceOrderTaskDetail> getTasks() {
		return tasks;
	}
	public void setTasks(ArrayList<ServiceOrderTaskDetail> tasks) {
		this.tasks = tasks;
	}
	public String getWfStateDs() {
		return wfStateDs;
	}
	public void setWfStateDs(String wfStateDs) {
		this.wfStateDs = wfStateDs;
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
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public ArrayList<ProviderDetail> getRoutedProviders() {
		return routedProviders;
	}
	public void setRoutedProviders(ArrayList<ProviderDetail> routedProviders) {
		this.routedProviders = routedProviders;
	}
	public Contact getBuyerSupportContact() {
		return buyerSupportContact;
	}
	public void setBuyerSupportContact(Contact buyerSupportContact) {
		this.buyerSupportContact = buyerSupportContact;
	}
	public Contact getVendorResourceContact() {
		return vendorResourceContact;
	}
	public void setVendorResourceContact(Contact vendorResourceContact) {
		this.vendorResourceContact = vendorResourceContact;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
}

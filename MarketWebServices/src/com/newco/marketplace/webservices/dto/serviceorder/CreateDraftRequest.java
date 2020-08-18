package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.Date;
import java.util.List;

import org.codehaus.xfire.aegis.type.java5.XmlElement;

public class CreateDraftRequest extends ABaseWebserviceRequest {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6385906520351731402L;
	private Boolean autoRoute;
	private Integer buyerContactId;
	private Integer pricingTypeId;
	private Double spendLimitLabor;
	private Double spendLimitParts;
	private String sowTitle;
	private String sowDs;
	private Date scheduledDate;
	private String providerInstructions;
	private String buyerTermsCond;
	private Integer locTypeInd;
	
	private List<CreateTaskRequest> tasks;
	private Integer primarySkillCatId;
	private String appointmentStartDate;
	private String appointmentEndDate;
	private String appointmentStartTime;
	private String appointmentEndTime;
	private Integer serviceDateTypeId;
	private Integer confirmServiceTime;
	private List<CreatePartRequest> parts;
	
	private ContactRequest serviceContact;
	private ContactRequest serviceContactAlt;
	private ContactRequest endUserContact;
	private ContactRequest buyerSupportContact;
	private Integer partsSuppliedBy;
	private Double retailPrice;
	private String orderNumber;
	private String unitNumber;
	
	private Double retailCancellationFee;
	
	//location objects
	private LocationRequest serviceLocation;
	private LocationRequest buyerSupportLocation;
	private List<Document> documents;
	
	private List<NoteRequest> notes;
	
	private List<CustomRef> customRef;
	
	private String templateName;
	
	private String clientStatus;
	
	private List<SpecialtyAddonRequest> addons;  
	
	public String getAppointmentEndDate() {
		return appointmentEndDate;
	}

	public void setAppointmentEndDate(String appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}

	public String getAppointmentStartDate() {
		return appointmentStartDate;
	}

	public void setAppointmentStartDate(String appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}

	public Integer getBuyerContactId() {
		return buyerContactId;
	}

	public void setBuyerContactId(Integer buyerContactId) {
		this.buyerContactId = buyerContactId;
	}
	
	public String getBuyerTermsCond() {
		return buyerTermsCond;
	}

	public void setBuyerTermsCond(String buyerTermsCond) {
		this.buyerTermsCond = buyerTermsCond;
	}

	public Double getSpendLimitLabor() {
		return spendLimitLabor;
	}

	public void setSpendLimitLabor(Double spendLimitLabor) {
		this.spendLimitLabor = spendLimitLabor;
	}

	public Integer getLocTypeInd() {
		return locTypeInd;
	}

	public void setLocTypeInd(Integer locTypeInd) {
		this.locTypeInd = locTypeInd;
	}

	public Integer getPricingTypeId() {
		return pricingTypeId;
	}

	public void setPricingTypeId(Integer pricingTypeId) {
		this.pricingTypeId = pricingTypeId;
	}
	
	public Integer getPrimarySkillCatId() {
		return primarySkillCatId;
	}
	
	public void setPrimarySkillCatId(Integer primarySkillCatId) {
		this.primarySkillCatId = primarySkillCatId;
	}

	public String getProviderInstructions() {
		return providerInstructions;
	}

	public void setProviderInstructions(String providerInstructions) {
		this.providerInstructions = providerInstructions;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	@XmlElement(minOccurs="0", nillable=true)
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getSowDs() {
		return sowDs;
	}

	public void setSowDs(String sowDs) {
		this.sowDs = sowDs;
	}
	@XmlElement(minOccurs="1",nillable=false)
	public String getSowTitle() {
		return sowTitle;
	}
	
	public void setSowTitle(String sowTitle) {
		this.sowTitle = sowTitle;
	}

	

	public List<CreateTaskRequest> getTasks() {
		return tasks;
	}

	public void setTasks(List<CreateTaskRequest> tasks) {
		this.tasks = tasks;
	}

	
	public List<CreatePartRequest> getParts() {
		return parts;
	}
	public void setParts(List<CreatePartRequest> parts) {
		this.parts = parts;
	}

	public ContactRequest getServiceContact() {
		return serviceContact;
	}

	public void setServiceContact(ContactRequest serviceContact) {
		this.serviceContact = serviceContact;
	}

	public ContactRequest getServiceContactAlt() {
		return serviceContactAlt;
	}

	public void setServiceContactAlt(ContactRequest serviceContactAlt) {
		this.serviceContactAlt = serviceContactAlt;
	}

	public ContactRequest getEndUserContact() {
		return endUserContact;
	}

	public void setEndUserContact(ContactRequest endUserContact) {
		this.endUserContact = endUserContact;
	}

	public ContactRequest getBuyerSupportContact() {
		return buyerSupportContact;
	}

	public void setBuyerSupportContact(ContactRequest buyerSupportContact) {
		this.buyerSupportContact = buyerSupportContact;
	}
	
	@XmlElement(minOccurs="1",nillable=false)
	public LocationRequest getServiceLocation() {
		return serviceLocation;
	}
	
	public void setServiceLocation(LocationRequest serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

	public LocationRequest getBuyerSupportLocation() {
		return buyerSupportLocation;
	}

	public void setBuyerSupportLocation(LocationRequest buyerSupportLocation) {
		this.buyerSupportLocation = buyerSupportLocation;
	}

	public Double getSpendLimitParts() {
		return spendLimitParts;
	}

	public void setSpendLimitParts(Double spendLimitParts) {
		this.spendLimitParts = spendLimitParts;
	}

	public String getAppointmentStartTime() {
		return appointmentStartTime;
	}

	public void setAppointmentStartTime(String appointmentStartTime) {
		this.appointmentStartTime = appointmentStartTime;
	}

	public String getAppointmentEndTime() {
		return appointmentEndTime;
	}

	public void setAppointmentEndTime(String appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}

	public Integer getPartsSuppliedBy() {
		return partsSuppliedBy;
	}

	public void setPartsSuppliedBy(Integer partsSuppliedBy) {
		this.partsSuppliedBy = partsSuppliedBy;
	}
	
	@XmlElement(minOccurs="1",nillable=false)
	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<CustomRef> getCustomRef() {
		return customRef;
	}

	public void setCustomRef(List<CustomRef> customRef) {
		this.customRef = customRef;
	}

	@XmlElement(minOccurs="1",nillable=false)
	public Double getRetailCancellationFee() {
		return retailCancellationFee;
	}

	public void setRetailCancellationFee(Double retailCancellationFee) {
		this.retailCancellationFee = retailCancellationFee;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<NoteRequest> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteRequest> notes) {
		this.notes = notes;
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public Boolean getAutoRoute() {
		return autoRoute;
	}

	public void setAutoRoute(Boolean autoRoute) {
		this.autoRoute = autoRoute;
	}

	/**
	 * @return the confirmServiceTime
	 */
	public Integer getConfirmServiceTime() {
		return confirmServiceTime;
	}

	/**
	 * @param confirmServiceTime the confirmServiceTime to set
	 */
	public void setConfirmServiceTime(Integer confirmServiceTime) {
		this.confirmServiceTime = confirmServiceTime;
	}

	/**
	 * @return the serviceDateTypeId
	 */
	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}

	/**
	 * @param serviceDateTypeId the serviceDateTypeId to set
	 */
	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}

	public List<SpecialtyAddonRequest> getAddons() {
		return addons;
	}

	public void setAddons(List<SpecialtyAddonRequest> addons) {
		this.addons = addons;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	
}

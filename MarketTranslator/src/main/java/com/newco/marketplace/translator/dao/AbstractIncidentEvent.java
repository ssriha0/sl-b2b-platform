package com.newco.marketplace.translator.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * An incident event occurs anytime a client sends information regarding an incident on a warranty contract. The incident may be broken harddrive
 * or install new memory.
 * @author gjackson
 *
 */
@MappedSuperclass
public abstract class AbstractIncidentEvent {

	/**
	 * Incidents may be associated to other incidents. The client id of the incident related to this one
	 */
	private String associatedIncident;
	private String authorizedAmount;
	/**
	 * Client field representing the auth for this incident
	 */
	private String authorizingCode;
	private Date createdDate;
	/**
	 * Parent object incident - there can be many events for an incident
	 */
	private Incident incident;
	private String incidentComment;
	private IncidentContact contact;
	/**
	 * Unique identifier of the event
	 */
	private Integer incidentEventID;
	private Set<IncidentPart> incidentParts = new HashSet<IncidentPart>(0);
	private String manufacturer;
	private String modelNumber;
	private Date modifiedDate;
	private String numberOfParts;
	private String partLaborIndicator;
	private String partsWarrentySKU;
	private String specialCoverageFlag;
	private String productLine;
	/**
	 * Where the consumer bought the item under contract
	 */
	private String retailer;
	private String serialNumber;
	/**
	 * Client may provide a servicer id
	 */
	private String servicerID;
	private String serviceProviderLocation;
	private String shippingMethod;
	private String status;
	private String supportGroup;
	private String vendorClaimNumber;
	private String warrentyStatus;
	private Boolean updateAccepted;
	
	/**
	 * min constructor
	 */
	public AbstractIncidentEvent() {
		// intentionally blank
	}
	
	/**
	 * Full Constructor
	 * @param associatedIncident
	 * @param authorizedAmount
	 * @param authorizingCode
	 * @param createdDate
	 * @param incident
	 * @param incidentComment
	 * @param contact
	 * @param incidentEventID
	 * @param incidentParts
	 * @param manufacturer
	 * @param modelNumber
	 * @param modifiedDate
	 * @param numberOfParts
	 * @param partLaborIndicator
	 * @param partsWarrentySKU
	 * @param productLine
	 * @param retailer
	 * @param serialNumber
	 * @param servicerID
	 * @param serviceProviderLocation
	 * @param shippingMethod
	 * @param status
	 * @param supportGroup
	 * @param vendorClaimNumber
	 * @param warrentyStatus
	 */
	public AbstractIncidentEvent(String associatedIncident,
			String authorizedAmount, String authorizingCode, Date createdDate,
			Incident incident, String incidentComment, IncidentContact contact,
			Integer incidentEventID, Set<IncidentPart> incidentParts,
			String manufacturer, String modelNumber, Date modifiedDate,
			String numberOfParts, String partLaborIndicator,
			String partsWarrentySKU, String productLine, String retailer,
			String serialNumber, String servicerID,
			String serviceProviderLocation, String shippingMethod,
			String status, String supportGroup, String vendorClaimNumber,
			String warrentyStatus) {
		super();
		this.associatedIncident = associatedIncident;
		this.authorizedAmount = authorizedAmount;
		this.authorizingCode = authorizingCode;
		this.createdDate = createdDate;
		this.incident = incident;
		this.incidentComment = incidentComment;
		this.contact = contact;
		this.incidentEventID = incidentEventID;
		this.incidentParts = incidentParts;
		this.manufacturer = manufacturer;
		this.modelNumber = modelNumber;
		this.modifiedDate = modifiedDate;
		this.numberOfParts = numberOfParts;
		this.partLaborIndicator = partLaborIndicator;
		this.partsWarrentySKU = partsWarrentySKU;
		this.productLine = productLine;
		this.retailer = retailer;
		this.serialNumber = serialNumber;
		this.servicerID = servicerID;
		this.serviceProviderLocation = serviceProviderLocation;
		this.shippingMethod = shippingMethod;
		this.status = status;
		this.supportGroup = supportGroup;
		this.vendorClaimNumber = vendorClaimNumber;
		this.warrentyStatus = warrentyStatus;
	}

	@Id
	@TableGenerator(name="tg", table="lu_last_identifier",
	pkColumnName="identifier", valueColumnName="last_value",
	allocationSize=1
	)@GeneratedValue(strategy=GenerationType.TABLE, generator="tg") 
	@Column(name = "incident_event_id", unique = true, nullable = false, insertable = true, updatable = true)
	public Integer getIncidentEventID() {
		return incidentEventID;
	}

	public void setIncidentEventID(Integer incidentEventID) {
		this.incidentEventID = incidentEventID;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "incident_id", unique = false, nullable = true, insertable = true, updatable = true)
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	@Column(name = "comment", unique = false, nullable = true, insertable = true, updatable = true, length = 255)
	public String getIncidentComment() {
		return incidentComment;
	}

	public void setIncidentComment(String incidentComment) {
		this.incidentComment = incidentComment;
	}

	@Column(name = "part_labor_indicator", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
	public String getPartLaborIndicator() {
		return partLaborIndicator;
	}

	public void setPartLaborIndicator(String partLaborIndicator) {
		this.partLaborIndicator = partLaborIndicator;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "incident_contact_id", unique = false, nullable = true, insertable = true, updatable = true)
	public IncidentContact getContact() {
		return contact;
	}

	public void setContact(IncidentContact contact) {
		this.contact = contact;
	}

	@Column(name = "manufacturer", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Column(name = "model_number", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	@Column(name = "serial_number", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "associated_incident_number", unique = false, nullable = true, insertable = true, updatable = true)
	public String getAssociatedIncident() {
		return associatedIncident;
	}

	public void setAssociatedIncident(String associatedIncident) {
		this.associatedIncident = associatedIncident;
	}

	@Column(name = "parts_warrenty_sku", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getPartsWarrentySKU() {
		return partsWarrentySKU;
	}

	public void setPartsWarrentySKU(String partsWarrentySKU) {
		this.partsWarrentySKU = partsWarrentySKU;
	}

	@Column(name = "spcl_coverage_flg", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSpecialCoverageFlag() {
		return specialCoverageFlag;
	}

	public void setSpecialCoverageFlag(String specialCoverageFlag) {
		this.specialCoverageFlag = specialCoverageFlag;
	}
	
	@Column(name = "warrenty_status", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getWarrentyStatus() {
		return warrentyStatus;
	}

	public void setWarrentyStatus(String warrentyStatus) {
		this.warrentyStatus = warrentyStatus;
	}

	@Column(name = "shipping_method", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@Column(name = "service_provider_location", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getServiceProviderLocation() {
		return serviceProviderLocation;
	}

	public void setServiceProviderLocation(String serviceProviderLocation) {
		this.serviceProviderLocation = serviceProviderLocation;
	}

	@Column(name = "vendor_claim_number", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getVendorClaimNumber() {
		return vendorClaimNumber;
	}

	public void setVendorClaimNumber(String vendorClaimNumber) {
		this.vendorClaimNumber = vendorClaimNumber;
	}

	@Column(name = "authorized_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getAuthorizedAmount() {
		return authorizedAmount;
	}

	public void setAuthorizedAmount(String authorizedAmount) {
		this.authorizedAmount = authorizedAmount;
	}

	@Column(name = "support_group", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSupportGroup() {
		return supportGroup;
	}

	public void setSupportGroup(String supportGroup) {
		this.supportGroup = supportGroup;
	}

	@Column(name = "servicer_id", unique = false, nullable = true, insertable = true, updatable = true, length = 25)
	public String getServicerID() {
		return servicerID;
	}

	public void setServicerID(String servicerID) {
		this.servicerID = servicerID;
	}

	@Column(name = "authorizing_code", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getAuthorizingCode() {
		return authorizingCode;
	}

	public void setAuthorizingCode(String authorizingCode) {
		this.authorizingCode = authorizingCode;
	}

	@Column(name = "retailer", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	@Column(name = "product_line", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getProductLine() {
		return productLine;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	@Column(name = "number_of_parts", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public String getNumberOfParts() {
		return numberOfParts;
	}

	public void setNumberOfParts(String numberOfParts) {
		this.numberOfParts = numberOfParts;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true)
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "incidentEvent")
	public Set<IncidentPart> getIncidentParts() {
		return incidentParts;
	}

	public void setIncidentParts(Set<IncidentPart> incidentParts) {
		this.incidentParts = incidentParts;
	}
	
	@Column(name = "client_status", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "update_accepted", unique = false, nullable = true, insertable = true, updatable = true)
	public Boolean getUpdateAccepted() {
		return updateAccepted;
	}

	public void setUpdateAccepted(Boolean updateAccepted) {
		this.updateAccepted = updateAccepted;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

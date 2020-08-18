package com.servicelive.orderfulfillment.domain;
  
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Formula;

import com.servicelive.orderfulfillment.domain.type.LeadPricingType;
import com.servicelive.orderfulfillment.domain.type.LeadSkillType;
import com.servicelive.orderfulfillment.domain.type.LeadUrgencyOfServiceType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;


@Entity
@Table(name = "lead_hdr")
@XmlRootElement()
public class LeadHdr extends LeadElement {
	
	private static Logger logger = Logger.getLogger(LeadHdr.class);
	
	@Id @Column(name = "sl_lead_id")
	@GeneratedValue(generator="leadIdGenerator")
	@org.hibernate.annotations.GenericGenerator(name = "leadIdGenerator", strategy = "com.servicelive.orderfulfillment.domain.util.LeadIdGenerator")
	private String leadId;
	
	@Column(name = "lms_lead_id")
	private String lmsLeadId;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	//this is an enum column
	@Column(name = "skill")
	private String skill = LeadSkillType.UNKNOWN.name();
	
	@Column(name = "service_date")
	private Date serviceDate;
	
	@Column(name = "service_timezone")
	private String serviceTimezone;
	
	@Column(name = "service_start_time")
	private String serviceStartTime;
	
	@Column(name = "service_end_time")
	private String serviceEndTime;
	
	//this is an enum column
	@Column(name = "urgency_of_service")
	//SL-20893 removed the default UNKNOWN value as part of SL-20893 needs
	private String urgencyOfService;
	
	@Column(name = "project_description")
	private String projectDescription;
	
	//this is an enum column
	@Column(name = "lead_type")
	private String leadType = LeadPricingType.UNKNOWN.name();
	
	@Column(name = "lead_source")
	private String leadSource;
	
	@Column(name = "lead_wf_status")
	private Integer leadWfStatus;
	
	@Column(name = "client_project_type_id")
	private Integer clientProjectTypeId;
	
	@Column(name = "secondary_projects")
	private String secondaryProjects;
	
	@Column(name = "lead_final_price")
	private BigDecimal leadFinalPrice;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate = new Date();
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate = new Date();
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "buyer_id")
	private Long buyerId;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "completed_date")
	private Date completedDate;
    
	@Column(name = "completed_time")
	private String completedTime;
	
	@Column(name = "completion_comments")
	private String completionComments;
	
	@Column(name = "lead_labor_price")
	private BigDecimal leadLaborPrice;
	
	@Column(name = "lead_material_price")
	private BigDecimal leadMaterialPrice;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="lead")
	private LeadDocuments leadDocument;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="lead")
	private List<LeadCancel> cancelLeadFirms=new ArrayList<LeadCancel>();
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="lead")
	private LeadContactInfo leadContactInfo;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lead")
	private List<LeadPostedFirm>postedFirms = new ArrayList<LeadPostedFirm>();
	
	public String getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}

	public String getCompletionComments() {
		return completionComments;
	}

	public void setCompletionComments(String completionComments) {
		this.completionComments = completionComments;
	}

	public BigDecimal getLeadLaborPrice() {
		return leadLaborPrice;
	}

	public void setLeadLaborPrice(BigDecimal leadLaborPrice) {
		this.leadLaborPrice = leadLaborPrice;
	}

	public BigDecimal getLeadMaterialPrice() {
		return leadMaterialPrice;
	}

	public void setLeadMaterialPrice(BigDecimal leadMaterialPrice) {
		this.leadMaterialPrice = leadMaterialPrice;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public String getServiceTimezone() {
		return serviceTimezone;
	}

	public void setServiceTimezone(String serviceTimezone) {
		this.serviceTimezone = serviceTimezone;
	}

	public String getServiceStartTime() {
		return serviceStartTime;
	}

	public void setServiceStartTime(String serviceStartTime) {
		this.serviceStartTime = serviceStartTime;
	}

	public String getServiceEndTime() {
		return serviceEndTime;
	}

	public void setServiceEndTime(String serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}

	public String getUrgencyOfService() {
		return urgencyOfService;
	}

	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public Integer getLeadWfStatus() {
		return leadWfStatus;
	}

	public void setLeadWfStatus(Integer leadWfStatus) {
		this.leadWfStatus = leadWfStatus;
	}

	public Integer getClientProjectTypeId() {
		return clientProjectTypeId;
	}

	public void setClientProjectTypeId(Integer clientProjectTypeId) {
		this.clientProjectTypeId = clientProjectTypeId;
	}

	public String getSecondaryProjects() {
		return secondaryProjects;
	}

	public void setSecondaryProjects(String secondaryProjects) {
		this.secondaryProjects = secondaryProjects;
	}

	public BigDecimal getLeadFinalPrice() {
		return leadFinalPrice;
	}

	public void setLeadFinalPrice(BigDecimal leadFinalPrice) {
		this.leadFinalPrice = leadFinalPrice;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getLeadType() {
		return leadType;
	}

	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public LeadDocuments getLeadDocument() {
		return leadDocument;
	}

	public void setLeadDocument(LeadDocuments leadDocument) {
		this.leadDocument = leadDocument;
	}

	public List<LeadPostedFirm> getPostedFirms() {
		return postedFirms;
	}

	public void setPostedFirms(List<LeadPostedFirm> postedFirms) {
		this.postedFirms = postedFirms;
	}

	public LeadContactInfo getLeadContactInfo() {
		return leadContactInfo;
	}

	public void setLeadContactInfo(LeadContactInfo leadContactInfo) {
		this.leadContactInfo = leadContactInfo;
	}

	public List<LeadCancel> getCancelLeadFirms() {
		return cancelLeadFirms;
	}

	public void setCancelLeadFirms(List<LeadCancel> cancelLeadFirms) {
		this.cancelLeadFirms = cancelLeadFirms;
	}
	
	
	

}

package com.newco.marketplace.vo.leadmanagement.lead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class LeadInfoDetail {
	
	private String leadId;
	private String leadStatus;
	private String skill;
	private String projectType;
	private String urgency;
	private String custFirstName;
	private String custLastName;
	private String formattedCustPhoneNo;
	private String custPhoneNo;
	private String city;
	private String zip;
	private String leadDescription;
	private String createdDate; 
	private String formattedDreatedDate; 
	private String street;
	private String email;
	private String leadCategory;
	private String leadSource;
	private String leadType;
	private Integer resourceAssigned;
	private String resFirstName;
	private String resLastName;
	private Notes notes;
	private Historys history;
	private Attachments attachments;
	private String completionDate;
	private String completionTime;
	private Integer numberOfTrips;
	private String completionComments;
	private String leadFinalPrice;
	private String leadMaterialPrice;
	private String leadLaborPrice;
	private int rating;
	private String cancelledDate;
	private String cancelledBy;
	private String cancelledReason;
	private String lmsLeadId;
	private String vendorId;

	public String getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(String cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	
	public void setCancelledDate() {
		this.cancelledDate = getFormattedDateNew(this.cancelledDate);
	}
	
	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getCancelledReason() {
		return cancelledReason;
	}

	public void setCancelledReason(String cancelledReason) {
		this.cancelledReason = cancelledReason;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Historys getHistory() {
		return history;
	}

	public void setHistory(Historys history) {
		this.history = history;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	
	public void setCompletionDate() {
		this.completionDate = getFormattedCompletionDate(this.completionDate);
	}

	

	public String getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}

	public Integer getNumberOfTrips() {
		return numberOfTrips;
	}

	public void setNumberOfTrips(Integer numberOfTrips) {
		this.numberOfTrips = numberOfTrips;
	}

	public Notes getNotes() {
		return notes;
	}

	public void setNotes(Notes notes) {
		this.notes = notes;
	}

	public Integer getResourceAssigned() {
		return resourceAssigned;
	}

	public void setResourceAssigned(Integer resourceAssigned) {
		this.resourceAssigned = resourceAssigned;
	}

	public String getResFirstName() {
		return resFirstName;
	}

	public void setResFirstName(String resFirstName) {
		this.resFirstName = resFirstName;
	}

	public String getResLastName() {
		return resLastName;
	}

	public void setResLastName(String resLastName) {
		this.resLastName = resLastName;
	}
	private String timeZone;

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	private String serviceDate; 
	private String serviceStartTime;
	private String serviceEndTime;
	private String scheduledDate; 
	private String scheduledStartTime;
	private String scheduledEndTime;


	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getScheduledStartTime() {
		return scheduledStartTime;
	}

	public void setScheduledStartTime(String scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	public String getScheduledEndTime() {
		return scheduledEndTime;
	}

	public void setScheduledEndTime(String scheduledEndTime) {
		this.scheduledEndTime = scheduledEndTime;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
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


	public String getLeadType() {
		return leadType;
	}

	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public String getLeadCategory() {
		return leadCategory;
	}

	public void setLeadCategory(String leadCategory) {
		this.leadCategory = leadCategory;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String state;
	
	private String industry;
	
	private String leadPrice;

	public String getLeadPrice() {
		return leadPrice;
	}

	public void setLeadPrice(String leadPrice) {
		this.leadPrice = leadPrice;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		//Ensure Lead Status in small case only.
		if(StringUtils.isNotBlank(leadStatus)){
			leadStatus=leadStatus.toLowerCase();
		    this.leadStatus = leadStatus;
		}
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustPhoneNo() {
		return custPhoneNo;
	}

	public void setCustPhoneNo(String custPhoneNo) {
		this.custPhoneNo = custPhoneNo;
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

	public String getLeadDescription() {
		return leadDescription;
	}

	public void setLeadDescription(String leadDescription) {
		this.leadDescription = leadDescription;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getFormattedDreatedDate() {
		return formattedDreatedDate;
	}

	public void setFormattedDreatedDate(String formattedDreatedDate) {
		this.formattedDreatedDate = formattedDreatedDate;
	}

	public String getFormattedCustPhoneNo() {
		return formattedCustPhoneNo;
	}

	public void setFormattedCustPhoneNo(String formattedCustPhoneNo) {
		this.formattedCustPhoneNo = formattedCustPhoneNo;
	}

	public Attachments getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachments attachments) {
		this.attachments = attachments;
	}

	public String getCompletionComments() {
		return completionComments;
	}

	public void setCompletionComments(String completionComments) {
		this.completionComments = completionComments;
	}

	public String getLeadFinalPrice() {
		return leadFinalPrice;
	}

	public void setLeadFinalPrice(String leadFinalPrice) {
		this.leadFinalPrice = leadFinalPrice;
	}

	public String getLeadMaterialPrice() {
		return leadMaterialPrice;
	}

	public void setLeadMaterialPrice(String leadMaterialPrice) {
		this.leadMaterialPrice = leadMaterialPrice;
	}

	public String getLeadLaborPrice() {
		return leadLaborPrice;
	}

	public void setLeadLaborPrice(String leadLaborPrice) {
		this.leadLaborPrice = leadLaborPrice;
	}
	
	public String getLmsLeadId() {
		return lmsLeadId;
	}

	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
	private static SimpleDateFormat popupDateNew = new SimpleDateFormat("EEEE MMM d, yyyy, hh:mm a");
	private static SimpleDateFormat popupCompletionDate = new SimpleDateFormat("EEEE MMM d, yyyy");
	
	public String getFormattedDateNew(String inDate){
		Date date = formatStringToDateNew(inDate);
		String outDate = getPopupDateNew(date);
		return outDate;

	}
	private String getFormattedCompletionDate(String inDate) {
		Date date = formatStringToDateNew(inDate);
		String outDate = getPopupDateCompleted(date);
		return outDate;
	}
	public static Date formatStringToDateNew(String source)
	{
			try {
				return defaultFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public static String getPopupDateNew(Date pDate) {
		return popupDateNew.format(pDate);
	}
	public static String getPopupDateCompleted(Date pDate) {
		return popupCompletionDate.format(pDate);
	}
}

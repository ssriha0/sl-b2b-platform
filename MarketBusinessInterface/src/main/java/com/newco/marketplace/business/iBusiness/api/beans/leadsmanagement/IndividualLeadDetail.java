package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XmlRootElement(name = "LeadDetails")
@XStreamAlias("LeadDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndividualLeadDetail {
	@XStreamAlias("leadStatus")
	private String leadStatus;
	
	@XStreamAlias("skill")
	private String skill;
	
	@XStreamAlias("projectType")
	private String projectType;
	
	@XStreamAlias("urgencyOfService")
	private String urgencyOfService;
	
	@XStreamAlias("firstName")
	private String firstName;
	
	@XStreamAlias("lastName")
	private String lastName;
	
	@XStreamAlias("phoneNumber")
	private String phoneNumber;
	
	@XStreamAlias("slLeadId")
	private String slLeadId;
	
	@XStreamAlias("lmsLeadId")
	private String lmsLeadId;
	
	@XStreamAlias("leadSource")
	private String leadSource;
	
	@XStreamAlias("leadCategory")
	private String leadCategory;
	
	@XStreamAlias("email")
	private String email;
	
    @XmlElement(name="address")
	@XStreamAlias("address")
	private Address address;

	@XStreamAlias("leadPrice")
	private Double leadPrice;
	
	@XStreamAlias("leadType")
	private String leadType;
	
	@XStreamAlias("additionalProjects")
	private String additionalProjects;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("preferredAppointment")
	private String preferredAppointment;
	
	@XmlElement(name="Notes")
	@XStreamAlias("Notes")
	private Notes notes;
	
	@XmlElement(name="Historys")
	@XStreamAlias("Historys")
	private Historys historys;
	
	@XmlElement(name="Attachments")
	@XStreamAlias("Attachments")
	private Attachments attachments;
	
	@XStreamAlias("postedDate")
	private String postedDate;
	
	@XStreamAlias("serviceTimeZone")
	private String serviceTimeZone;
	
	@XStreamAlias("startTime")
	private String startTime;
	
	@XStreamAlias("endTime")
	private String endTime;
	
	@XStreamAlias("createdDate")
	private String createdDate;
	
	@XStreamAlias("createdDateFormatted")
	private String createdDateFormatted;
	
	@XStreamAlias("assignedResourceId")
	private Integer resourceAssigned;
	
	@XStreamAlias("assigneeFirstName")
	private String resFirstName;
	
	@XStreamAlias("assigneeLastName")
	private String resLastName;
	
	@XStreamAlias("assigneeRating")
	private Double resRating;
	
	
	@XStreamAlias("completionDate")
	private String completionDate;
	
	@XStreamAlias("completionTime")
	private String completionTime;
	
	@XStreamAlias("numberOfTrips")
	private Integer numberOfTrips;
	
	@XStreamAlias("completionComments")
	private String completionComments;
	
	@XStreamAlias("leadFinalPrice")
	private Double leadFinalPrice;
	
	@XStreamAlias("leadLaborPrice")
	private Double leadLaborPrice;
	
	@XStreamAlias("leadMaterialPrice")
	private Double leadMaterialPrice;
	
	@XStreamAlias("cancelledDate")
	private String cancelledDate;
	
	@XStreamAlias("cancelledBy")
	private String cancelledBy;
	
	@XStreamAlias("cancelledReason")
	private String cancelledReason;

	@XStreamAlias("scheduledStartTime")
	private String scheduledStartTime;
	
	@XStreamAlias("scheduledEndTime")
	private String scheduledEndTime;
	
	@XStreamAlias("scheduledDate")
	private String scheduledDate;
	
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
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	public void setCompletionDate() {
		this.completionDate = getFormattedDateNew(this.completionDate);
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
	private String formattedServiceDate;
	private String formattedServiceTime1;
	private String formattedServiceTime2;
	private String formattedScheduledDate;
	private String formattedScheduleTime1;
	private String formattedScheduleTime2;
	
	public void setFormattedScheduledDate() {
		this.formattedScheduledDate = getFormattedDate(this.scheduledDate);
	}
	public String getFormattedScheduledDate() {
		return formattedScheduledDate;
	}
	public void setFormattedScheduledDate(String formattedScheduledDate) {
		this.formattedScheduledDate = formattedScheduledDate;
	}
	
	public String getFormattedScheduleTime1() {
		return formattedScheduleTime1;
	}
	public void setFormattedScheduleTime1() {
		String serviceTime = this.scheduledStartTime;
		this.formattedScheduleTime1 = getFormattedServiceTime(serviceTime);
	}
	public String getFormattedScheduleTime2() {
		return formattedScheduleTime2;
	}
	public void setFormattedScheduleTime2() {
		String serviceTime = this.scheduledEndTime;
		this.formattedScheduleTime2 = getFormattedServiceTime(serviceTime);
	}
	public String getFormattedServiceTime1() {
		return formattedServiceTime1;
	}
	public void setFormattedServiceTime1() {
		String serviceTime = this.startTime;
		this.formattedServiceTime1 = getFormattedServiceTime(serviceTime);
	}
	public String getFormattedServiceTime2() {
		return formattedServiceTime2;
	}
	public void setFormattedServiceTime2() {
		String serviceTime = this.endTime;
		this.formattedServiceTime2 = getFormattedServiceTime(serviceTime);
	}
	
	
	private static SimpleDateFormat popupDate = new SimpleDateFormat("EEEE MMM d, yyyy");
	private static SimpleDateFormat popupTime = new SimpleDateFormat("hh:mm aa");
	private static SimpleDateFormat defaultFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");	
	private static SimpleDateFormat defaultTimeFormat = new SimpleDateFormat("HH:mm:ss");	

	
	
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
	public double getLeadPrice() {
		return leadPrice;
	}
	public void setLeadPrice(Double leadPrice) {
		this.leadPrice = leadPrice;
	}
	public String getLeadType() {
		return leadType;
	}
	public void setLeadType(String leadType) {
		this.leadType = leadType;
	}
	public String getAdditionalProjects() {
		return additionalProjects;
	}
	public void setAdditionalProjects(String additionalProjects) {
		this.additionalProjects = additionalProjects;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSlLeadId() {
		return slLeadId;
	}
	public void setSlLeadId(String slLeadId) {
		this.slLeadId = slLeadId;
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
	public String getUrgencyOfService() {
		return urgencyOfService;
	}
	public void setUrgencyOfService(String urgencyOfService) {
		this.urgencyOfService = urgencyOfService;
	}
	public String getPreferredAppointment() {
		return preferredAppointment;
	}
	public void setPreferredAppointment(String preferredAppointment) {
		this.preferredAppointment = preferredAppointment;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getServiceTimeZone() {
		return serviceTimeZone;
	}
	public void setServiceTimeZone(String serviceTimeZone) {
		this.serviceTimeZone = serviceTimeZone;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLeadStatus() {
		return leadStatus;
	}
	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}
	public Notes getNotes() {
		return notes;
	}
	
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public void setNotes(Notes notes) {
		this.notes = notes;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedDateFormatted() {
		return createdDateFormatted;
	}
	public void setCreatedDateFormatted(String createdDateFormatted) {
		this.createdDateFormatted = createdDateFormatted;
	}
	public void setFormattedServiceDate() {
		this.formattedServiceDate = getFormattedDate(this.preferredAppointment);
	}
	public String getFormattedServiceDate() {
		return formattedServiceDate;
	}
	public void setFormattedServiceDate(String formattedServiceDate) {
		this.formattedServiceDate = formattedServiceDate;
	}
	
	public String getFormattedDate(String inDate){
		Date date = formatStringToDate(inDate);
		String outDate = getPopUpDate(date);
		return outDate;

	}
	public static Date formatStringToDate(String source)
	{
			try {
				return defaultFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public static String getPopUpDate(Date pDate) {
		return popupDate.format(pDate);
	}
	
	public String getFormattedServiceTime(String inTime){
		Date date = formatStringTimeToDate(inTime);
		String outTime = getPopUpTime(date);
		
		return outTime;

	}
	public String getFormattedTime(String inTime){
		Date date = formatStringTimeToDate(inTime);
		String outTime = getPopUpTime(date);
		
		return outTime;

	}
	
	public static Date formatStringTimeToDate(String source)
	{
			try {
				return defaultTimeFormat.parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}	
	public static String getPopUpTime(Date pDate) {
		return popupTime.format(pDate);
	
}
	public Historys getHistorys() {
		return historys;
	}
	public void setHistorys(Historys historys) {
		this.historys = historys;
	}
	public Double getResRating() {
		return resRating;
	}
	public void setResRating(Double resRating) {
		this.resRating = resRating;
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
	public Double getLeadFinalPrice() {
		return leadFinalPrice;
	}
	public void setLeadFinalPrice(Double leadFinalPrice) {
		this.leadFinalPrice = leadFinalPrice;
	}
	public Double getLeadLaborPrice() {
		return leadLaborPrice;
	}
	public void setLeadLaborPrice(Double leadLaborPrice) {
		this.leadLaborPrice = leadLaborPrice;
	}
	public Double getLeadMaterialPrice() {
		return leadMaterialPrice;
	}
	public void setLeadMaterialPrice(Double leadMaterialPrice) {
		this.leadMaterialPrice = leadMaterialPrice;
	}
	private static SimpleDateFormat defaultFormatNew = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	
	private static SimpleDateFormat popupDateNew = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
	private static SimpleDateFormat timeFormatNew = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	
	public String getFormattedDateNew(String inDate){
		Date date = formatStringToDateNew(inDate);
		String outDate = getPopupDateNew(date);
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
	public static String getPopupDateNew(Date pDate) {
		return popupDateNew.format(pDate);
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
	public String getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public String getLmsLeadId() {
		return lmsLeadId;
	}
	public void setLmsLeadId(String lmsLeadId) {
		this.lmsLeadId = lmsLeadId;
	}
	

}

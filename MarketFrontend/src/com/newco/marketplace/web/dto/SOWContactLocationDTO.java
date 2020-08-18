package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWContactLocationDTO extends SOWBaseTabDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1082999413142089668L;
	private String locationTypeId = "2"; //Needs to default to Residential
	private Integer locationId; 
	private String businessName;
	private String firstName;
	private String middleName;
	private String lastName;
	private String title;
	private String suffix;
	private String locationName;
	private String streetName1;
	private String streetName2;
	private String aptNo;
	private String city;
	private String state;
	private String stateCd;
	private String zip;
	private String zip4;
	private Integer avilableFlag;
	private String email;
	private String serviceLocationNote;
	private Integer contactId;
	private Integer resourceId;
	private double rating;
	
	private List<SOWPhoneDTO> phones  = new ArrayList<SOWPhoneDTO>();
	
	private String fax;
	
	private String serviceLocationNotes;
	

	public SOWContactLocationDTO()
	{
	}
	
	public SOWContactLocationDTO(SOWContactLocationDTO loc)
	{
		if(loc != null)
			copy(loc);
	}
	
	public String getLocationTypeId() {
		return locationTypeId;
	}
	public void setLocationTypeId(String locationTypeId) {
		
		this.locationTypeId = locationTypeId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		if(businessName!=null && businessName.trim().equals("") )
		{
			businessName=null;
		}
		this.businessName = businessName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		if(firstName!=null && firstName.trim().equals("") )
		{
			firstName=null;
		}
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		if(lastName!=null && lastName.trim().equals("") )
		{
			lastName=null;
		}
		this.lastName = lastName;
	}
	public String getStreetName1() {
		return streetName1;
	}
	public void setStreetName1(String streetName1) {
		if(streetName1!=null && streetName1.trim().equals(""))
		{
			streetName1 = null;
		}
		this.streetName1 = streetName1;
	}
	public String getStreetName2() {
		return streetName2;
	}
	public void setStreetName2(String streetName2) {
		if(streetName2!=null && streetName2.trim().equals(""))
		{
			streetName2 = null;
		}
		this.streetName2 = streetName2;
	}
	public String getAptNo() {
		return aptNo;
	}
	public void setAptNo(String aptNo) {
		if( aptNo!=null &&  aptNo.trim().equals(""))
		{
			 aptNo= null;
		}
		this.aptNo = aptNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		if( city!=null && city.trim().equals(""))
		{
			city= null;
		}
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		if(state!=null && state.trim().equals(""))
		{
			state= null;
		}
		this.state = state;
		
	}
	
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		if(stateCd!=null && stateCd.trim().equals(""))
		{
			stateCd= null;
		}
		this.stateCd = stateCd;
		
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		if(zip!=null && zip.trim().equals(""))
		{
			zip= null;
		}
		this.zip = zip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email!=null && email.trim().equals(""))
		{
			email= null;
		}
		if(email != null){
			this.email = email.trim();
		}else{
			this.email = email;
		}		
	}
	
	public String getServiceLocationNotes() {
		return serviceLocationNotes;
	}
	public void setServiceLocationNotes(String serviceLocationNotes) {
		this.serviceLocationNotes = serviceLocationNotes;
	}
	public List<SOWPhoneDTO> getPhones() {
		return phones;
	}
	public void setPhones(List<SOWPhoneDTO> phones) {
		this.phones = phones;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public void copy(SOWContactLocationDTO loc)
	{
		this.locationTypeId = loc.locationTypeId;
		//this.locationId; 
		this.businessName = loc.businessName;
		this.firstName = loc.firstName;
		this.middleName = loc.middleName;
		this.lastName = loc.lastName;
		this.title = loc.title;
		this.suffix = loc.suffix;
		this.locationName = loc.locationName;
		this.streetName1 = loc.streetName1;
		this.streetName2 = loc.streetName2;
		this.aptNo = loc.aptNo;
		this.city = loc.city;
		this.state = loc.state;
		this.stateCd = loc.stateCd;
		this.zip = loc.zip;
		this.zip4 = loc.zip4;
		this.avilableFlag = loc.avilableFlag;
		if(loc.email != null){
			this.email = loc.email.trim();
		}else{
			this.email = loc.email;
		}		
		this.serviceLocationNote = loc.serviceLocationNote;
		this.contactId = loc.contactId;
		this.fax = loc.fax;
		//this.resourceId =;
		
		for(SOWPhoneDTO phone : loc.phones)
		{
			SOWPhoneDTO newPhone = new SOWPhoneDTO(phone);
			phones.add(newPhone);			
		}
		
	}
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
		if (firstName == null || firstName.length()<1 || firstName.length() >50 ){
			addError("firstName", "Check first name ", OrderConstants.SOW_TAB_ERROR);
		}
		
		if (lastName == null || lastName.length() < 1 || lastName.length() >50){
			addError("lastName", "Check last name", OrderConstants.SOW_TAB_ERROR);
			
		}
		
		if(streetName1 == null || streetName1.length()<1  || streetName1.length() > 30){
			addError("streetName1", "Street name is a required field", OrderConstants.SOW_TAB_ERROR);
			
		}
		
		if(city == null || city.length()<1){
			addError("city", "City is a required field", OrderConstants.SOW_TAB_ERROR);
		}
		
		//Add State Validation
		
		if(getZip() == null || getZip().length()<1 || getZip().length() >10 ){
			addError(getTheResourceBundle().getString("Zip"),
					getTheResourceBundle().getString("Zip_Validation"),
					OrderConstants.SOW_TAB_ERROR);
		}

		//Validation of zip length
		//not in the documentation
		
		//addError(new SOWError("","",""));
		if(getStreetName2() != null && getStreetName2().length() > 30){
			addError(getTheResourceBundle().getString("Street2"),
					getTheResourceBundle().getString("Street2_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getAptNo() != null && getAptNo().length() > 10){
			addError(getTheResourceBundle().getString("Apt_No"),
					getTheResourceBundle().getString("Apt_No_Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		if(getServiceLocationNote() != null && getServiceLocationNote().length() >255 ){
			addError(getTheResourceBundle().getString("Service_Location_Notes"), 
					getTheResourceBundle().getString("Service_Location__Validation_Msg"), 
					OrderConstants.SOW_TAB_ERROR);
		}
		
		//return getErrors();
	}
	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceLocationNote() {
		return serviceLocationNote;
	}
	public void setServiceLocationNote(String serviceLocationNote) {
		this.serviceLocationNote = serviceLocationNote;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getSuffix()
	{
		return suffix;
	}
	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}
	public String getMiddleName()
	{
		return middleName;
	}
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

}

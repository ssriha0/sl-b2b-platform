package com.newco.marketplace.web.dto;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.web.utils.SLStringUtils;

/**
 * $Revision: 1.36 $ $Author: glacy $ $Date: 2008/04/26 01:13:45 $
 */

/*
 * Maintenance History
 * $Log: SOContactDTO.java,v $
 * Revision 1.36  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.32.12.1  2008/04/23 11:41:31  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.35  2008/04/23 05:19:47  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.33  2008/04/15 17:57:49  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.32.24.2  2008/04/08 21:27:29  paugus2
 * CR # 50053
 *
 * Revision 1.32.24.1  2008/04/08 21:24:44  paugus2
 * CR # 50053
 *
 * Revision 1.32  2008/02/14 23:44:49  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.31  2008/01/31 00:15:09  pbhinga
 * Code for implementing enhancement "Resource Id Refactoring".
 *
 * 1> Changes include AOP backend changes to pass in SecurityContext as a new parameter for every AOP method.
 * 2> Frontend changes to capture buyer resource id and buyer resource contact id in SO_HDR and use it buyer resource contact details to send emails for SO.
 *
 * Revision 1.30  2007/12/14 23:20:10  pkoppis
 * updated for checking state as -1
 *
 * Revision 1.29  2007/12/14 21:28:33  spannee
 * Apt # fix
 *
 * Revision 1.28  2007/12/13 23:58:26  pkoppis
 * updated to remove null values when dispalying in review tab
 *
 * Revision 1.27  2007/12/13 21:44:43  sali030
 * Fixed the display of zip on the review tab
 *
 * Revision 1.26  2007/12/08 20:58:17  pkoppis
 * updated for displaying zip4
 *
 * Revision 1.25  2007/12/07 15:51:10  pkoppis
 * made changes for displaying the phones format  in the constructor
 *
 * Revision 1.24  2007/12/07 15:35:45  mhaye05
 * updated init() so that the part that builds the location data uses the common methods
 *
 */
public class SOContactDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = -697022467017649555L;
	private String type;
	private String individualName;
	private String individualID;
	private String companyName;
	private String businessName;
	private String companyID;
	private String streetAddress; 
	private String streetAddress2;
	private String cityStateZip;
	private String phoneHome;
	private String phoneWork;
	private String phoneMobile;
	private String pager;
	private String other;
	private String fax="";
	private String email;
	private String phonePrimary;
	private String phoneAlternate;
	private int locationtypeId;
    private Integer resourceId;
    private String firstName;
    private String lastName;
    private String displayName;
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFirstName() {
		return firstName;
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

	public SOContactDTO()
	{
	}
	
	public SOContactDTO(SOWContactLocationDTO dto)
	{
		if(dto == null)
			return;
			
		 locationtypeId = -1;
		
		if(dto.getLocationTypeId() != null)
			locationtypeId = Integer.parseInt(dto.getLocationTypeId());
		
		switch (locationtypeId) {
        case 1: type = "Business"; break;
        case 2: type = "Mailing"; break;
        case 3: type = "Home"; break;
        case 4: type = "Work";  break;
        case 5: type = "Billing"; break;        
        default:type = locationtypeId+"";	
		}
		
		
		if(dto.getFirstName()!=null)
		{	
			individualName = dto.getFirstName() + " " ;
			if(dto.getLastName()!=null)
			individualName = dto.getFirstName() + " " + dto.getLastName();
		}
		
		if(dto.getContactId()!=null )
		individualID = dto.getResourceId() + "";
		if(dto.getBusinessName()!=null)
		companyName = dto.getBusinessName();
		
		if(dto.getStreetName1()!=null )
		{	
			streetAddress = dto.getStreetName1();
			if (dto.getAptNo()!=null)
			{
				streetAddress =streetAddress+", " +OrderConstants.APT_NUMBER+ dto.getAptNo();
			}

		}  
	   if(dto.getStreetName2()!=null )
	   {
		   streetAddress2 = dto.getStreetName2();
	   }		
		   if(dto.getCity() != null && dto.getCity() !="")
			   cityStateZip = dto.getCity();
		   
		   if(dto.getState()!=null )
		   { 
			  
			   if(!dto.getState().equals("-1"))
			   {
			     if(cityStateZip != null)
				  cityStateZip +=", " + dto.getState();
			     else
			     cityStateZip = dto.getState();
			   }
			 }
		   
		   if(dto.getZip()!=null && dto.getZip()!="")
		   {  
			   if(cityStateZip != null)
			      {
				   cityStateZip +=" " + dto.getZip();
				     if(dto.getZip4()!=null && dto.getZip4().trim().length()>1)
				    	 cityStateZip +="-" + dto.getZip4();  
			      } 
			   else
			   {  
			   cityStateZip = dto.getZip();
			   if(dto.getZip4()!=null && dto.getZip4().trim().length()>1)
			    	 cityStateZip +="-" + dto.getZip4();  
			   
			   }
		   }  
		   
		 
		
			 
		

		if(dto.getPhones() != null)
		{
			
			if(dto.getPhones().size() > 0)
			{
				for(SOWPhoneDTO phone : dto.getPhones())
				{
					if(phone != null && phone.getPhoneClassId() != null)
					{
						// Work
						if(phone.getPhoneClassId() == 1)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								phoneWork = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();
							}
							if(phone.getExt() != null)
								phoneWork += " Ext. " + phone.getExt();
						}
						// Mobile
						if(phone.getPhoneClassId() == 2)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								phoneMobile = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();	
							}
							if(phone.getExt() != null)
								phoneMobile += " Ext. " + phone.getExt();
						}
						// Home
						if(phone.getPhoneClassId() == 0)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								phoneHome = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();	
							}
							if(phone.getExt() != null)
								phoneHome += " Ext. " + phone.getExt();
						}
						// pager
						if(phone.getPhoneClassId() == 4)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								pager = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();		
							}
							if(phone.getExt() != null)
								pager += " Ext. " + phone.getExt();
						}
						// Other
						if(phone.getPhoneClassId() == 5)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								other = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();
							}
							if(phone.getExt() != null)
								other += " Ext. " + phone.getExt();
						}
						// Fax
						if(phone.getPhoneClassId() == 6)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								fax = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();
							}
							if(phone.getExt() != null)
								fax += " Ext. " + phone.getExt();
						}  
					
					}
					
					if(phone != null && phone.getPhoneType() != null)
					{
						// Primary
						if(phone.getPhoneType() == 1)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								phonePrimary = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();	
							}
							if(phone.getExt() != null)
								phonePrimary += " Ext. " + phone.getExt();
						}
						// Alternate
						if(phone.getPhoneType() == 2)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								phoneAlternate = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();		
							}
							if(phone.getExt() != null)
								phoneAlternate += " Ext. " + phone.getExt();
						}
						// Fax
						if(phone.getPhoneType() == 3)
						{
							if(!phone.getAreaCode().equals("") && !phone.getAreaCode().equals("") && !phone.getAreaCode().equals("")){
								fax = phone.getAreaCode() + "-" + phone.getPhonePart1() + "-" + phone.getPhonePart2();	
							}
							if(phone.getExt() != null)
								fax += " Ext. " + phone.getExt();
						}  
					
					}
								    
				}
			}				
		}
		email = dto.getEmail();
		
	}

	public SOContactDTO(Contact contact, SoLocation loc)
	{
		init(contact, loc);		
	}
	
	public void init(Contact contact, SoLocation loc)
	{
		if(contact != null)
		{
			String indName = "";
			if(contact.getFirstName() != null)
				indName += contact.getFirstName();
			if(contact.getLastName() != null)
				indName += " " + contact.getLastName();
			
			setIndividualName(indName);
			String phone = contact.getPhoneNo();
			if(!SLStringUtils.isNullOrEmpty(contact.getPhoneNoExt()))
				phone += " Ext. " + contact.getPhoneNoExt();
			setPhoneWork(phone);
			
			setPhoneMobile(contact.getCellNo());
			setFax(contact.getFaxNo());
			setEmail(contact.getEmail());
			setBusinessName(contact.getBusinessName());
		}		
		
		if(loc != null)
		{
			// Residential / Business
			setType(loc.getLocnClassDesc());
			
			StringBuffer streetAddress = new StringBuffer();
			if(org.apache.commons.lang.StringUtils.isNotEmpty(loc.getStreet1())) {
				streetAddress.append(loc.getStreet1());
			}
			if(org.apache.commons.lang.StringUtils.isNotEmpty(loc.getAptNo())) {
				streetAddress.append(", ").append(loc.getAptNo());
			}
			setStreetAddress(streetAddress.toString());
			
			setStreetAddress2(loc.getStreet2());
			
			String cityStateZip = ServiceLiveStringUtils.concatenateCityStateZip(
					loc.getCity(), loc.getState(), loc.getZip(), loc.getZip4());			
			setCityStateZip(cityStateZip);			
		}
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		if(streetAddress!=null && streetAddress.trim().equals(""))
		{
			streetAddress = "";
		}
		this.streetAddress = streetAddress;
	}
	public String getCityStateZip() {
		return cityStateZip;
	}
	public void setCityStateZip(String cityStateZip) {
		if(cityStateZip!=null && cityStateZip.trim().equals(""))
		{
			cityStateZip = "";
		}
		this.cityStateZip = cityStateZip;
	}
	public String getPhoneHome() {
		return phoneHome;
	}
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}
	public String getPhoneMobile() {
		return phoneMobile;
	}
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIndividualName() {
		return individualName;
	}
	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}
	public String getIndividualID() {
		return individualID;
	}
	public void setIndividualID(String individualID) {
		this.individualID = individualID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getPhoneWork() {
		return phoneWork;
	}
	public void setPhoneWork(String phoneWork) {
		this.phoneWork = phoneWork;
	}
	public String getStreetAddress2() {
		return streetAddress2;
	}
	public void setStreetAddress2(String streetAddress2) {
		if(streetAddress2!=null && streetAddress2.trim().equals(""))
		{
			streetAddress2 = "";
		}
		this.streetAddress2 = streetAddress2;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public int getLocationtypeId() {
		return locationtypeId;
	}

	public void setLocationtypeId(int locationtypeId) {
		this.locationtypeId = locationtypeId;
	}

	public String getPhonePrimary() {
		return phonePrimary;
	}

	public void setPhonePrimary(String phonePrimary) {
		this.phonePrimary = phonePrimary;
	}

	public String getPhoneAlternate() {
		return phoneAlternate;
	}

	public void setPhoneAlternate(String phoneAlternate) {
		this.phoneAlternate = phoneAlternate;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	}

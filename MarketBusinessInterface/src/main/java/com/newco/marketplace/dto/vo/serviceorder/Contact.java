package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.SerializableBaseVO;


public class Contact extends SerializableBaseVO implements Comparable<Contact> {

	private static final long serialVersionUID = 9180446939179439349L;
	//Do not remove individual phone parameters as 
	//needed for population of so related contact tables.
	private String soId;
	private Integer contactId;
	private Integer contactTypeId;
	private Integer phoneTypeId;
	private String businessName= null;
	private String lastName= null;
	private String firstName=null;
	private String mi=null;
	private String suffix=null;
	private String title=null;
	private Date phoneCreatedDate;
	private String phoneNo=null;
	private String phoneNoExt=null;
	private String phoneClassId;
	private String faxNo=null;
	private String cellNo=null;
	private String homeNo=null;
	private String pagerText=null;
	private String email=null;
	private Integer contactGroup=-1;
	private Integer contactMethodId=-1;
	private Timestamp createdDate = null;
	private Timestamp modifiedDate = null;
	private String modifiedBy;
    private String honorific = null;
    private String userName;
    private String emailPreference;
    private String smsPreference;
    private Integer entityTypeId;
    private Integer entityId;
    private List<PhoneVO> phones;
	private double rating;
	private Integer locationId = null;
    private String street_1 = null;
    private String street_2 = null;
    private String aptNo = null;
    private String city = null;
    private String stateCd = null;
    private String zip = null;
    private String zip4 = null;
    private String country = null;
    private Integer locnTypeId = null;
    private String locName = null;
    private Integer contactLocTypeId = null;
    private Integer resLocId = null;
    private Integer resourceId=0;
    private Integer companyRoleId;
    private String companyRoleName;
    private String altEmail = null;
    private Integer role_id;
    private Integer opt_in;
    private Integer vendorId;
    
	public Integer getLocationId() {
		return locationId;
	}



	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}



	public String getStreet_1() {
		return street_1;
	}



	public void setStreet_1(String street_1) {
		this.street_1 = street_1;
	}



	public String getStreet_2() {
		return street_2;
	}



	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getStateCd() {
		return stateCd;
	}



	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
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



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public Integer getLocnTypeId() {
		return locnTypeId;
	}



	public void setLocnTypeId(Integer locnTypeId) {
		this.locnTypeId = locnTypeId;
	}



	public String getLocName() {
		return locName;
	}



	public void setLocName(String locName) {
		this.locName = locName;
	}



	public Integer getContactLocTypeId() {
		return contactLocTypeId;
	}



	public void setContactLocTypeId(Integer contactLocTypeId) {
		this.contactLocTypeId = contactLocTypeId;
	}

	
	public String getEmailPreference() {
		if (emailPreference == null || emailPreference.equals("0") || emailPreference.equals("N")) return "N";
		else return "Y";

	}
	public void setEmailPreference(String emailPreference) {
		this.emailPreference = emailPreference;
	}
	public String getSmsPreference() {
		if (smsPreference == null || smsPreference.equals("0") || smsPreference.equals("N")) return "N";
		else return "Y";	
	}
	public void setSmsPreference(String smsPreference) {
		this.smsPreference = smsPreference;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	public Integer getContactGroup() {
		return contactGroup;
	}
	public void setContactGroup(Integer contactGroup) {
		this.contactGroup = contactGroup;
	}
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getContactMethodId() {
		return contactMethodId;
	}
	public void setContactMethodId(Integer contactMethodId) {
		this.contactMethodId = contactMethodId;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
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
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPagerText() {
		return pagerText;
	}
	public void setPagerText(String pagerText) {
		this.pagerText = pagerText;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    /**
     * @return the honorific
     */
    public String getHonorific() {
        return honorific;
    }
    /**
     * @param honorific the honorific to set
     */
    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(Integer contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the homeNo
	 */
	public String getHomeNo() {
		return homeNo;
	}
	/**
	 * @param homeNo the homeNo to set
	 */
	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}
	/**
	 * @return the phoneNoExt
	 */
	public String getPhoneNoExt() {
		return phoneNoExt;
	}
	/**
	 * @param phoneNoExt the phoneNoExt to set
	 */
	public void setPhoneNoExt(String phoneNoExt) {
		this.phoneNoExt = phoneNoExt;
	}
	public List<PhoneVO> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneVO> phones) {
		this.phones = phones;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getPhoneClassId() {
		return phoneClassId;
	}
	public void setPhoneClassId(String phoneClassId) {
		this.phoneClassId = phoneClassId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("soId", getSoId())
			.append("contactId", getContactId())
			.append("businessName", getBusinessName())
			.append("lastName", getLastName())	
			.append("firstName", getFirstName())
			.append("mi", getMi())
			.append("suffix", getSuffix())
			.append("title", getTitle())
			.append("phoneNo", getPhoneNo())
			.append("phoneNoExt", getPhoneNoExt())
			.append("faxNo", getFaxNo())
			.append("cellNo", getCellNo())
			.append("email", getEmail())
			.append("honorific", getHonorific())
			.append("locationId", getLocationId()) 
			.append("street_1", getStreet_1())
			.append("street_2", getStreet_2())
			.append("city", getCity())
			.append("stateCd", getStateCd())
			.append("zip", getZip())
			.append("zip4", getZip4())
			.append("country", getCountry())
			.append("locnTypeId", getLocnTypeId())
			.append("locName", getResLocId())
			.toString();
	}

	public Integer getResLocId() {
		return resLocId;
	}

	public void setResLocId(Integer resLocId) {
		this.resLocId = resLocId;
	}
	
	public Date getPhoneCreatedDate() {
		return phoneCreatedDate;
	}
	public void setPhoneCreatedDate(Date phoneCreatedDate) {
		this.phoneCreatedDate = phoneCreatedDate;
	}

	public Integer getPhoneTypeId() {
		return phoneTypeId;
	}

	public void setPhoneTypeId(Integer phoneTypeId) {
		this.phoneTypeId = phoneTypeId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getCompanyRoleId() {
		return companyRoleId;
	}

	public void setCompanyRoleId(Integer companyRoleId) {
		this.companyRoleId = companyRoleId;
	}

	public String getCompanyRoleName() {
		return companyRoleName;
	}

	public void setCompanyRoleName(String companyRoleName) {
		this.companyRoleName = companyRoleName;
	}


	public String getAltEmail() {
		return altEmail;
	}



	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
	public int compareTo(Contact o) {
		if (o == null || !o.getComparableString().equals(this.getComparableString())) {
			return 1;
		}
		return 0;
	}
	
	public List<PhoneVO> getPhonesAsArray() {
		if (getPhones() != null && getPhones().size() > 0) {
			return getPhones();
		}
		else {
			List<PhoneVO> phones = new ArrayList<PhoneVO>();
			//push the phones into the array
			if (getPhoneNo() != null) {
				PhoneVO phone = new PhoneVO();
				phone.setPhoneNo(getPhoneNo());
				phone.setPhoneExt(getPhoneNoExt());
				phones.add(phone);
			}
			if (getCellNo() != null) {
				PhoneVO phone = new PhoneVO();
				phone.setPhoneNo(getCellNo());
				phones.add(phone);
			}
			return phones;
		}
	}
	
	public String getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getBusinessName() != null) ? getBusinessName() : "");
		//sb.append((getCellNo() != null) ? getCellNo() : "");
		sb.append((getCity() != null) ? getCity() : "");
		sb.append((getCompanyRoleName() != null) ? getCompanyRoleName() : "");
		sb.append((getCountry() != null) ? getCountry() : "");
		sb.append((getEmail() != null) ? getEmail() : "");
		sb.append((getEmailPreference() != null) ? getEmailPreference() : "");
		sb.append((getFirstName() != null) ? getFirstName() : "");
		sb.append((getHonorific() != null) ? getHonorific() : "");
		sb.append((getLastName() != null) ? getLastName() : "");
		sb.append((getLocName() != null) ? getLocName() : "");
		sb.append((getMi() != null) ? getMi() : "");
		sb.append((getPagerText() != null) ? getPagerText() : "");
		sb.append((getSmsPreference() != null) ? getSmsPreference() : "");
		sb.append((getStateCd() != null) ? getStateCd() : "");
		sb.append((getStreet_1() != null) ? getStreet_1() : "");
		sb.append((getStreet_2() != null) ? getStreet_2() : "");
		sb.append((getSuffix() != null) ? getSuffix() : "");
		sb.append((getTitle() != null) ? getTitle() : "");
		sb.append((getZip() != null) ? getZip() : "");
		sb.append((getZip4() != null) ? getZip4() : "");
		if (getPhonesAsArray() != null && getPhonesAsArray().size() > 0) {
			for (PhoneVO phone : getPhonesAsArray()) {
				sb.append(phone.getComparableString());
			}
		}
		return sb.toString();
	}



	public String getAptNo() {
		return aptNo;
	}



	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}	
	public Integer getRole_id() {
		return role_id;
	}



	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}



	public Integer getOpt_in() {
		return opt_in;
	}



	public void setOpt_in(Integer opt_in) {
		this.opt_in = opt_in;
	}



    /**
     * @return the vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }



    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }	
	
}

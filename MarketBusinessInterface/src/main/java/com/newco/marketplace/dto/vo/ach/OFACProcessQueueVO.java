package com.newco.marketplace.dto.vo.ach;

import java.util.Date;

import com.newco.marketplace.vo.MPBaseVO;

public class OFACProcessQueueVO extends MPBaseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6608011980160871443L;
	private String userType;
	private int entityId;
	private String TaxPayerId;
	private String user;
	private int UserID;
	private String businessName;
	private String contactNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String ZipCode;
    private String aptNo;
    private Long V1Account;
    private Long V2Account;
    private String createdDate;
    private Long slAccountNumber;
    private String dateOfBirth;
    private String alternateIdType;
    private String alternateIdCountryOfIssuance;
    private String taxPayerIdType;
   
    
    
     
     
    public int getEntityId() {

        return entityId;
    }
    public void setEntityId(int entityId) {

        this.entityId = entityId;
    }

    @Override
	public String toString() {
      return ( getDelimitedString(this, "|" ));
    }
    
    public String getDelimitedString(
    	OFACProcessQueueVO ofacProcessQueueVO, String delimiter) {
        StringBuffer sb = new StringBuffer("");
        
        sb.append(ofacProcessQueueVO.getUserType());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getEntityId());
        sb.append(delimiter);
        
       
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Provider"))
        {
        sb.append(ofacProcessQueueVO.getTaxPayerId());
        sb.append(delimiter);
        }
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Buyer")
        		&& ofacProcessQueueVO.getTaxPayerIdType()!=null && (ofacProcessQueueVO.getTaxPayerIdType().equals("0") || ofacProcessQueueVO.getTaxPayerIdType().equals("1")))
        		 
        {
        sb.append(ofacProcessQueueVO.getTaxPayerId());
        sb.append(delimiter);
        }
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Buyer")
        		&& ofacProcessQueueVO.getTaxPayerIdType()!=null && ofacProcessQueueVO.getTaxPayerIdType().equals("2"))
        		 
        {
        sb.append("");
        sb.append(delimiter);
        }
        
        sb.append(ofacProcessQueueVO.getUser());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getUserID());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getBusinessName());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getContactNumber());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getFirstName());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getLastName());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getEmail());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getStreet1());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getStreet2());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getCity());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getState());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getZipCode());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getAptNo());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getV1Account());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getV2Account());
        sb.append(delimiter);

        sb.append(ofacProcessQueueVO.getCreatedDate());
        sb.append(delimiter);
        
        
        
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Buyer") && ofacProcessQueueVO.getDateOfBirth()!=null && ofacProcessQueueVO.getDateOfBirth().length()>0)
         {
        	ofacProcessQueueVO.setDateOfBirth(ofacProcessQueueVO.getDateOfBirth().replace('/', '-'));
        	if(ofacProcessQueueVO.getDateOfBirth().length()>=10)
        	{
        	String year=ofacProcessQueueVO.getDateOfBirth().substring(6,10);
            String monthAndDate=ofacProcessQueueVO.getDateOfBirth().substring(0,5);
            String date=year+"-"+monthAndDate;
        	ofacProcessQueueVO.setDateOfBirth(date);
        	}

         }
        
        sb.append(ofacProcessQueueVO.getDateOfBirth());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getAlternateIdType());
        sb.append(delimiter);
        
        sb.append(ofacProcessQueueVO.getAlternateIdCountryOfIssuance());
        sb.append(delimiter);
        
        /*if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Provider"))
        {
        sb.append("");
        sb.append(delimiter);
        }*/
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Buyer")
        		&& ofacProcessQueueVO.getTaxPayerIdType()!=null && ofacProcessQueueVO.getTaxPayerIdType().equals("2"))
        		 
        {
        sb.append(ofacProcessQueueVO.getTaxPayerId());
        sb.append(delimiter);
        }
        if(ofacProcessQueueVO.getUserType()!=null && ofacProcessQueueVO.getUserType().equals("Buyer")
        		&& ofacProcessQueueVO.getTaxPayerIdType()!=null && (ofacProcessQueueVO.getTaxPayerIdType().equals("0") || ofacProcessQueueVO.getTaxPayerIdType().equals("1")))
        		 
        {
        sb.append("");
        sb.append(delimiter);
        }
        return (sb.toString());
    } // getDelimitedString

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTaxPayerId() {
		return TaxPayerId;
	}

	public void setTaxPayerId(String taxPayerId) {
	
		this.TaxPayerId = taxPayerId;
	}

	

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

	public String getAptNo() {
		return aptNo;
	}

	public void setAptNo(String aptNo) {
		this.aptNo = aptNo;
	}

	public Long getV1Account() {
		return V1Account;
	}

	public void setV1Account(Long account) {
		V1Account = account;
	}

	public Long getV2Account() {
		return V2Account;
	}

	public void setV2Account(Long account) {
		V2Account = account;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getSlAccountNumber() {
		return slAccountNumber;
	}
	public void setSlAccountNumber(Long slAccountNumber) {
		this.slAccountNumber = slAccountNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAlternateIdType() {
		return alternateIdType;
	}
	public void setAlternateIdType(String alternateIdType) {
		this.alternateIdType = alternateIdType;
	}
	public String getAlternateIdCountryOfIssuance() {
		return alternateIdCountryOfIssuance;
	}
	public void setAlternateIdCountryOfIssuance(String alternateIdCountryOfIssuance) {
		this.alternateIdCountryOfIssuance = alternateIdCountryOfIssuance;
	}
	
	public String getTaxPayerIdType() {
		return taxPayerIdType;
	}
	public void setTaxPayerIdType(String taxPayerIdType) {
		this.taxPayerIdType = taxPayerIdType;
	}
	
	
	
	
	
}

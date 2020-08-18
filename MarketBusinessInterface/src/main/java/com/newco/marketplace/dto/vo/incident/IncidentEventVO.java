package com.newco.marketplace.dto.vo.incident;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class IncidentEventVO extends SerializableBaseVO {

	private static final long serialVersionUID = 0L;

	// Maps to columns in incident_event DB table
	private Integer incidentEventID;
	private Date createdDate;
	private String clientStatus = "INFO";
	private String comment;
	private String partLaborInd;
	private String productLine;
	private String numberOfParts;
	private String manufacturer;
	private String modelNumber;
	private String serialNumber;	
	private String associatedIncidentNumber;
	private String partsWarrentySKU;
	private String specialCoverageFlag;
	private String warrantyStatus;
	private String shippingMethod;
	private String serviceProviderLocation;
	private String vendorClaimNumber;
	private String authorizedAmount;
	private String supportGroup;
	private String servicerID;
	private String authorizingCode;
	private String retailer;
	private Integer updateAccepted;
	
	// Maps to columns in incident DB table
	private Integer incidentID;
	private String clientIncidentID;
	private Integer warrantyContractID; 

	// Maps to columns in incident_contact DB table
	private String firstName;
	private String lastName;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String zipCode;
	private String zipCodeExt;
	private String phone1;
	private String phone1Ext;
	private String phone2;
	private String phone2Ext;
	
	// Maps to columns in warranty_contract
	private Date contractDate;
	private Date expirationDate;
	private String warrantyNotes;
	private String contractNumber;
	private String contractTypeCode;
	
	
	
	
	
	public Integer getIncidentEventID()
	{
		return incidentEventID;
	}
	public void setIncidentEventID(Integer incidentEventID)
	{
		this.incidentEventID = incidentEventID;
	}
	public Integer getIncidentID()
	{
		return incidentID;
	}
	public void setIncidentID(Integer incidentID)
	{
		this.incidentID = incidentID;
	}
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public String getPartLaborInd()
	{
		return partLaborInd;
	}
	public void setPartLaborInd(String partLaborInd)
	{
		this.partLaborInd = partLaborInd;
	}
	public String getProductLine()
	{
		return productLine;
	}
	public void setProductLine(String productLine)
	{
		this.productLine = productLine;
	}
	public String getNumberOfParts()
	{
		return numberOfParts;
	}
	public void setNumberOfParts(String numberOfParts)
	{
		this.numberOfParts = numberOfParts;
	}
	public String getManufacturer()
	{
		return manufacturer;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	public String getModelNumber()
	{
		return modelNumber;
	}
	public void setModelNumber(String modelNumber)
	{
		this.modelNumber = modelNumber;
	}
	public String getSerialNumber()
	{
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}
	public String getAssociatedIncidentNumber()
	{
		return associatedIncidentNumber;
	}
	public void setAssociatedIncidentNumber(String associatedIncidentNumber)
	{
		this.associatedIncidentNumber = associatedIncidentNumber;
	}
	public String getPartsWarrentySKU()
	{
		return partsWarrentySKU;
	}
	public void setPartsWarrentySKU(String partsWarrentySKU)
	{
		this.partsWarrentySKU = partsWarrentySKU;
	}
	public String getSpecialCoverageFlag() {
		return specialCoverageFlag;
	}
	public void setSpecialCoverageFlag(String specialCoverageFlag) {
		this.specialCoverageFlag = specialCoverageFlag;
	}
	public String getWarrantyStatus()
	{
		return warrantyStatus;
	}
	public void setWarrantyStatus(String warrantyStatus)
	{
		this.warrantyStatus = warrantyStatus;
	}
	public String getShippingMethod()
	{
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod)
	{
		this.shippingMethod = shippingMethod;
	}
	public String getServiceProviderLocation()
	{
		return serviceProviderLocation;
	}
	public void setServiceProviderLocation(String serviceProviderLocation)
	{
		this.serviceProviderLocation = serviceProviderLocation;
	}
	public String getVendorClaimNumber()
	{
		return vendorClaimNumber;
	}
	public void setVendorClaimNumber(String vendorClaimNumber)
	{
		this.vendorClaimNumber = vendorClaimNumber;
	}
	public String getAuthorizedAmount()
	{
		return authorizedAmount;
	}
	public void setAuthorizedAmount(String authorizedAmount)
	{
		this.authorizedAmount = authorizedAmount;
	}
	public String getSupportGroup()
	{
		return supportGroup;
	}
	public void setSupportGroup(String supportGroup)
	{
		this.supportGroup = supportGroup;
	}
	public String getServicerID()
	{
		return servicerID;
	}
	public void setServicerID(String servicerID)
	{
		this.servicerID = servicerID;
	}
	public String getAuthorizingCode()
	{
		return authorizingCode;
	}
	public void setAuthorizingCode(String authorizingCode)
	{
		this.authorizingCode = authorizingCode;
	}
	public String getClientStatus()
	{
		if(clientStatus != null)
			return clientStatus.toLowerCase();
		
		return clientStatus;
	}
	public void setClientStatus(String clientStatus)
	{
		this.clientStatus = clientStatus;
		
		if(clientStatus != null)
		{
			clientStatus = clientStatus.toLowerCase();
		}
	}
	public String getRetailer()
	{
		return retailer;
	}
	public void setRetailer(String retailer)
	{
		this.retailer = retailer;
	}
	public Integer getUpdateAccepted()
	{
		return updateAccepted;
	}
	public void setUpdateAccepted(Integer updateAccepted)
	{
		this.updateAccepted = updateAccepted;
	}
	public Integer getWarrantyContractID()
	{
		return warrantyContractID;
	}
	public void setWarrantyContractID(Integer warrantyContractID)
	{
		this.warrantyContractID = warrantyContractID;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state)
	{
		this.state = state;
	}
	public String getZipCode()
	{
		return zipCode;
	}
	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}
	public String getZipCodeExt()
	{
		return zipCodeExt;
	}
	public void setZipCodeExt(String zipCodeExt)
	{
		this.zipCodeExt = zipCodeExt;
	}
	public String getPhone1()
	{
		return phone1;
	}
	public void setPhone1(String phone1)
	{
		this.phone1 = phone1;
	}
	public String getPhone1Ext()
	{
		return phone1Ext;
	}
	public void setPhone1Ext(String phone1Ext)
	{
		this.phone1Ext = phone1Ext;
	}
	public String getPhone2()
	{
		return phone2;
	}
	public void setPhone2(String phone2)
	{
		this.phone2 = phone2;
	}
	public String getPhone2Ext()
	{
		return phone2Ext;
	}
	public void setPhone2Ext(String phone2Ext)
	{
		this.phone2Ext = phone2Ext;
	}
	public String getStreet1()
	{
		return street1;
	}
	public void setStreet1(String street1)
	{
		this.street1 = street1;
	}
	public String getStreet2()
	{
		return street2;
	}
	public void setStreet2(String street2)
	{
		this.street2 = street2;
	}
	public String getClientIncidentID()
	{
		return clientIncidentID;
	}
	public void setClientIncidentID(String clientIncidentID)
	{
		this.clientIncidentID = clientIncidentID;
	}
	public Date getContractDate()
	{
		return contractDate;
	}
	public void setContractDate(Date contractDate)
	{
		this.contractDate = contractDate;
	}
	public Date getExpirationDate()
	{
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}
	public String getWarrantyNotes()
	{
		return warrantyNotes;
	}
	public void setWarrantyNotes(String warrantyNotes)
	{
		this.warrantyNotes = warrantyNotes;
	}
	public String getContractNumber()
	{
		return contractNumber;
	}
	public void setContractNumber(String contractNumber)
	{
		this.contractNumber = contractNumber;
	}
	public String getContractTypeCode()
	{
		return contractTypeCode;
	}
	public void setContractTypeCode(String contractTypeCode)
	{
		this.contractTypeCode = contractTypeCode;
	}
	public Date getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
}

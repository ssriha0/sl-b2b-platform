package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Insurance")
public class InsuranceInfoDto extends BaseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3355281209715403886L;
	@XStreamOmitField
	private List  vendorCredentialList;
	@XStreamAlias("CredentialTypeName")
	private String credentialTypeName;
	@XStreamAlias("CredentialCategoryName")
	private String credentialCategoryName;
	@XStreamAlias("CredentialState")
	private String credentialState;
	@XStreamAlias("CredentialCity")
	private String credentialCity;
	@XStreamOmitField
	private Map companyCredentialList;
	@XStreamAlias("UserId")
	private String userId;
	@XStreamAlias("VendorId")
	private Integer vendorId;
	
	//Changes for Insurance story #17 START
	@XStreamAlias("VLI")
	private String VLI ;
	@XStreamAlias("WCI")
	private String WCI ;
	@XStreamAlias("CBGLI")
	private String CBGLI ;
	@XStreamAlias("VLIAmount")
	private String VLIAmount ;
	@XStreamAlias("WCIAmount")
	private String WCIAmount ;
	@XStreamAlias("CBGLIAmount")
	private String CBGLIAmount;
	/*******************Added for New Insurance Pages - Starts**********/
	@XStreamAlias("VLIPersonAmount")
	private String VLIPersonAmount;
	@XStreamAlias("VLIAccidentAmount")
	private String VLIAccidentAmount;
	@XStreamAlias("VLIAggregateAmount")
	private String VLIAggregateAmount;
	@XStreamAlias("WCIAccidentAmount")
	private String WCIAccidentAmount;
	@XStreamAlias("WCIAggregateAmount")
	private String WCIAggregateAmount;
	@XStreamAlias("CBGLIOccurenceAmount")
	private String CBGLIOccurenceAmount;
	@XStreamAlias("CBGLIAggregateAmount")
	private String CBGLIAggregateAmount;
	@XStreamAlias("CBGLIGenAggregateAmount")
	private String CBGLIGenAggregateAmount;
	@XStreamAlias("CBGLIIncreasedAmount")
	private String CBGLIIncreasedAmount;
	@XStreamOmitField
	private ArrayList insuranceList;
	@XStreamAlias("Status")
	private String status;
	@XStreamAlias("AddPolicy")
	private boolean addPolicy;
	@XStreamOmitField
	private List additionalInsuranceList;
	@XStreamOmitField
	private Integer additionalInsuranceListSize;
	
	/**
	 * Variable added for Document Content display
	 */
	private int currentDocumentId;
	
	public int getCurrentDocumentId() {
		return currentDocumentId;
	}
	public void setCurrentDocumentId(int currentDocumentId) {
		this.currentDocumentId = currentDocumentId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/*******************Added for New Insuramce Pages - Ends**********/
	public String getVLIAmount()  			{ 
		if(VLIAmount != null){
			VLIAmount=VLIAmount.trim();
			if(VLIAmount.indexOf(".")!=-1)
			{
				if(VLIAmount.substring(VLIAmount.lastIndexOf(".")+1).length() == 1 )
				{
						VLIAmount = VLIAmount + "0";
				}
			}
		}
		return this.VLIAmount;    
	}
	public String getWCIAmount()  			{ 
		if(WCIAmount != null){
			WCIAmount=WCIAmount.trim();
			if(WCIAmount.indexOf(".")!=-1)
			{
				if(WCIAmount.substring(WCIAmount.lastIndexOf(".")+1).length() == 1 )
				{	
					   WCIAmount = WCIAmount + "0";
				}
			}
		}
		return this.WCIAmount;    }
	public String getCBGLIAmount()			{ 
		if(CBGLIAmount != null){
			CBGLIAmount=CBGLIAmount.trim();
			if(CBGLIAmount.indexOf(".")!=-1)
			{
				if(CBGLIAmount.substring(CBGLIAmount.lastIndexOf(".")+1).length() == 1 )
				{
						CBGLIAmount = CBGLIAmount + "0";
				}
			}
		}
		return this.CBGLIAmount;  }
	public void setVLIAmount(String VLIAmount)		{  this.VLIAmount =VLIAmount; }
	public void setWCIAmount(String WCIAmount)  	{  this.WCIAmount = WCIAmount;}
	public void setCBGLIAmount(String CBGLIAmount)	{  this.CBGLIAmount = CBGLIAmount; }
	
	/*******************Added for New Insuramce Pages - Starts**********/
	public String getVLIPersonAmount()  {return this.VLIPersonAmount; }
	public String getVLIAccidentAmount()  			{  return this.VLIAccidentAmount;    }
	public String getVLIAggregateAmount()  			{  return this.VLIAggregateAmount;    }
	public String getWCIAccidentAmount()  			{  return this.WCIAccidentAmount;    }
	public String getWCIAggregateAmount()  			{  return this.WCIAggregateAmount;    }
	public String getCBGLIOccurenceAmount()			{  return this.CBGLIOccurenceAmount;  }
	public String getCBGLIAggregateAmount()			{  return this.CBGLIAggregateAmount;  }
	public String getCBGLIGenAggregateAmount()		{  return this.CBGLIGenAggregateAmount;  }
	public String getCBGLIIncreasedAmount()			{  return this.CBGLIIncreasedAmount;  }
	
	public void setVLIPersonAmount(String VLIPersonAmount)		{  this.VLIPersonAmount =VLIPersonAmount; }
	public void setVLIAccidentAmount(String VLIAccidentAmount)  	{  this.VLIAccidentAmount = VLIAccidentAmount;}
	public void setVLIAggregateAmount(String VLIAggregateAmount)	{  this.VLIAggregateAmount= VLIAggregateAmount;}
	public void setWCIAccidentAmount(String WCIAccidentAmount)		{  this.WCIAccidentAmount =WCIAccidentAmount; }
	public void setWCIAggregateAmount(String WCIAggregateAmount)  	{  this.WCIAggregateAmount = WCIAggregateAmount;}
	public void setCBGLIOccurenceAmount(String CBGLIOccurenceAmount)	{  this.CBGLIOccurenceAmount= CBGLIOccurenceAmount;}
	public void setCBGLIAggregateAmount(String CBGLIAggregateAmount)		{  this.CBGLIAggregateAmount =CBGLIAggregateAmount; }
	public void setCBGLIGenAggregateAmount(String CBGLIGenAggregateAmount)  	{  this.CBGLIGenAggregateAmount =CBGLIGenAggregateAmount;}
	public void setCBGLIIncreasedAmount(String CBGLIIncreasedAmount)	{  this.CBGLIIncreasedAmount= CBGLIIncreasedAmount;}
	
	public ArrayList getInsuranceList() {
		
		return insuranceList;
	}
	public void setInsuranceList(ArrayList insuranceList) {
		this.insuranceList = insuranceList;
	}
	/*******************Added for New Insuramce Pages - Ends**********/
	public String getVLI()  			{
		if (VLI != null && VLI.equals("false"))
			this.VLI = "0";
		else if (VLI != null && VLI.equals("true"))
			this.VLI = "1";
		
		return this.VLI;    }
	
	public String getWCI()  			
	{  
		if (WCI != null && WCI.equals("false"))
			this.WCI = "0";
		else if (WCI != null && WCI.equals("true"))
			this.WCI = "1";
		return this.WCI;    
	}
	
	public String getCBGLI()			{
		if (CBGLI != null && CBGLI.equals("false"))
			this.CBGLI = "0";
		else if (CBGLI != null && CBGLI.equals("true"))
			this.CBGLI = "1";
		
		System.out.println("this.CBGLI\t"+this.CBGLI);
		return this.CBGLI;  }
	
	public void setVLI(String VLI)		{  this.VLI =VLI; }
	public void setWCI(String WCI)  	{  this.WCI = WCI;}
	public void setCBGLI(String CBGLI)	{  this.CBGLI = CBGLI;}
		//Changes for Insurance story #17 END
	
	public List getVendorCredentialList() {
		return vendorCredentialList;
	}

	public void setVendorCredentialList(List vendorCredentialList) {
		this.vendorCredentialList = vendorCredentialList;
	}

	public Map getCompanyCredentialList() {
		return this.companyCredentialList;
	}

	public void setCompanyCredentialList(Map companyCredentialList) {
		this.companyCredentialList = companyCredentialList;
	}

	public String getCredentialCategoryName() {
		return credentialCategoryName;
	}

	public void setCredentialCategoryName(String credentialCategoryName) {
		this.credentialCategoryName = credentialCategoryName;
	}

	public String getCredentialCity() {
		return credentialCity;
	}

	public void setCredentialCity(String credentialCity) {
		this.credentialCity = credentialCity;
	}

	public String getCredentialState() {
		return credentialState;
	}

	public void setCredentialState(String credentialState) {
		this.credentialState = credentialState;
	}

	public String getCredentialTypeName() {
		return credentialTypeName;
	}

	public void setCredentialTypeName(String credentialTypeName) {
		this.credentialTypeName = credentialTypeName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public boolean isAddPolicy() {
		return addPolicy;
	}
	public void setAddPolicy(boolean addPolicy) {
		this.addPolicy = addPolicy;
	}
	public List getAdditionalInsuranceList() {
		return additionalInsuranceList;
	}
	public void setAdditionalInsuranceList(List additionalInsuranceList) {
		this.additionalInsuranceList = additionalInsuranceList;
	}
	public Integer getAdditionalInsuranceListSize() {
		return additionalInsuranceListSize;
	}
	public void setAdditionalInsuranceListSize(Integer additionalInsuranceListSize) {
		this.additionalInsuranceListSize = additionalInsuranceListSize;
	}
	
	

}

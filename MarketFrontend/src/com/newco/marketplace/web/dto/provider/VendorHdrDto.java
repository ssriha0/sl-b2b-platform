/**
 * 
 */
package com.newco.marketplace.web.dto.provider;


public class VendorHdrDto extends BaseDto
{
/**
	 * 
	 */
	private static final long serialVersionUID = -8912897924848801197L;

private Integer vendorId = -1;
    
    private String businessName;
    
    private Integer businessTypeId = -1;
    
    private String dbaName;
    
    private String dunsNo;
    
    private String einNo;
    
    private Integer  companySizeId = -1;
    
    private String webAddress;
    
    private Integer referralId = -1;
    
    private Integer contactGroup = -1;
    
    private Integer taxStatus = -1;
    
    private Integer foreignOwnedInd;
    
    private Integer foreignOwnedPct = -1;
    
    private Integer vendorStatusId = -1;
    
    private Integer yrsInBusiness = -1;
    
    private Integer yrsInBusinessUnderName = -1;
    
    private String businessAffiliateName;
    
    private Integer  businessAffiliateRelationship = -1;
    
    private java.sql.Timestamp termsCondDate;
    
    private java.sql.Timestamp termsCondVersion;
    
    private Integer termsCondInd=-1;
    
    private java.sql.Timestamp businessStartDate;
    
    private Boolean noCredInd;
    
    private String auditClaimedBy;
    
    private Integer profileState = -1;
    
    private String businessPhone;
    
    private String businessFax;
    
    private String busPhoneExtn;

    private Integer primaryIndustryId = -1;
    
  //Changed for Sears Rebranding (09/23/2007)
    private String promotionCode = "";
    private String sourceSystemId;
    
  //Changes done for Insurance section
    private Integer VLI = 0;
	private Integer WCI = 0;
	private Integer CBGLI = 0;
	
	//changes done in oct30
	private String VLIAmount;
	private String WCIAmount;
	private String CBGLIAmount; 

	public String getVLIAmount(){
		return VLIAmount;
	}
	public String getWCIAmount(){
		return WCIAmount;
	}
	public String getCBGLIAmount(){
		return CBGLIAmount;
	}
	public void setVLIAmount(String VLIAmount){
		this.VLIAmount =VLIAmount;
	}
	public void setWCIAmount(String WCIAmount){
		this.WCIAmount = WCIAmount;
	}
	public void setCBGLIAmount(String CBGLIAmount){
		this.CBGLIAmount = CBGLIAmount;
	} 
	
	
	
    public Integer getVLI(){
		return VLI;
	}
	public Integer getWCI(){
		return WCI;
	}
	public Integer getCBGLI(){
		return CBGLI;
	}
	public void setVLI(Integer VLI){
		this.VLI =VLI;
	}
	public void setWCI(Integer WCI){
		this.WCI = WCI;
	}
	public void setCBGLI(Integer CBGLI){
		this.CBGLI = CBGLI;
	}
	//End of changes done for Insurance section
    public String getSourceSystemId() {
		return sourceSystemId;
	}

	public void setSourceSystemId(String sourceSystemId) {
		this.sourceSystemId = sourceSystemId;
	}

    public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}
    //End of changes

	/**
	 * @return the auditClaimedBy
	 */
	public String getAuditClaimedBy() {
		return auditClaimedBy;
	}

	/**
	 * @param auditClaimedBy the auditClaimedBy to set
	 */
	public void setAuditClaimedBy(String auditClaimedBy) {
		this.auditClaimedBy = auditClaimedBy;
	}

	/**
	 * @return the foreignOwnedInd
	 */


	public String getBusinessAffiliateName() {
		return businessAffiliateName;
	}

	public void setBusinessAffiliateName(String businessAffiliateName) {
		this.businessAffiliateName = businessAffiliateName;
	}

	public Integer getBusinessAffiliateRelationship() {
		return businessAffiliateRelationship;
	}

	public void setBusinessAffiliateRelationship(
			Integer businessAffiliateRelationship) {
		this.businessAffiliateRelationship = businessAffiliateRelationship;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	public Integer getCompanySizeId() {
		return companySizeId;
	}

	public void setCompanySizeId(Integer companySizeId) {
		this.companySizeId = companySizeId;
	}

	public Integer getContactGroup() {
		return contactGroup;
	}

	public void setContactGroup(Integer contactGroup) {
		this.contactGroup = contactGroup;
	}

	public String getDbaName() {
		return dbaName;
	}

	public void setDbaName(String dbaName) {
		this.dbaName = dbaName;
	}

	public String getDunsNo() {
		return dunsNo;
	}

	public void setDunsNo(String dunsNo) {
		this.dunsNo = dunsNo;
	}

	public String getEinNo() {
		return einNo;
	}

	public void setEinNo(String einNo) {
		this.einNo = einNo;
	}

	

	public Integer getForeignOwnedPct() {
		return foreignOwnedPct;
	}

	public void setForeignOwnedPct(Integer foreignOwnedPct) {
		this.foreignOwnedPct = foreignOwnedPct;
	}

	public Integer getReferralId() {
		return referralId;
	}

	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}

	public Integer getTaxStatus() {
		return taxStatus;
	}

	public void setTaxStatus(Integer taxStatus) {
		this.taxStatus = taxStatus;
	}

	public java.sql.Timestamp getTermsCondDate() {
		return termsCondDate;
	}

	public void setTermsCondDate(java.sql.Timestamp termsCondDate) {
		this.termsCondDate = termsCondDate;
	}

	public Integer getTermsCondInd() {
		return termsCondInd;
	}

	public void setTermsCondInd(Integer termsCondInd) {
		this.termsCondInd = termsCondInd;
	}

	public java.sql.Timestamp getTermsCondVersion() {
		return termsCondVersion;
	}

	public void setTermsCondVersion(java.sql.Timestamp termsCondVersion) {
		this.termsCondVersion = termsCondVersion;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getVendorStatusId() {
		return vendorStatusId;
	}

	public void setVendorStatusId(Integer vendorStatusId) {
		this.vendorStatusId = vendorStatusId;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Integer getYrsInBusiness() {
		return yrsInBusiness;
	}

	public void setYrsInBusiness(Integer yrsInBusiness) {
		this.yrsInBusiness = yrsInBusiness;
	}

	public Integer getYrsInBusinessUnderName() {
		return yrsInBusinessUnderName;
	}

	public void setYrsInBusinessUnderName(Integer yrsInBusinessUnderName) {
		this.yrsInBusinessUnderName = yrsInBusinessUnderName;
	}

	public java.sql.Timestamp getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(java.sql.Timestamp businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	/**
	 * @return the foreignOwnedInd
	 */
	public Integer getForeignOwnedInd() {
		return foreignOwnedInd;
	}

	/**
	 * @param foreignOwnedInd the foreignOwnedInd to set
	 */
	public void setForeignOwnedInd(Integer foreignOwnedInd) {
		this.foreignOwnedInd = foreignOwnedInd;
	}


	public Boolean getNoCredInd() {
		return noCredInd;
	}
	
	public Boolean isNoCredInd() {
		return noCredInd;
	}

	public void setNoCredInd(Boolean noCredInd) {
		this.noCredInd = noCredInd;
	}

	/**
	 * @return the profileState
	 */
	public Integer getProfileState() {
		return profileState;
	}

	/**
	 * @param profileState the profileState to set
	 */
	public void setProfileState(Integer profileState) {
		this.profileState = profileState;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	public String getBusinessFax() {
		return businessFax;
	}
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public String getBusPhoneExtn() {
		return busPhoneExtn;
	}
	public void setBusPhoneExtn(String busPhoneExtn) {
		this.busPhoneExtn = busPhoneExtn;
	}
	public Integer getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	public void setPrimaryIndustryId(Integer primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}


}

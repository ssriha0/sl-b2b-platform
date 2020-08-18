/**
 *
 */
package com.newco.marketplace.vo.provider;

import java.util.Date;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupTaxPayerIdNumberType;
import com.newco.marketplace.dto.vo.LookupVO;


/**
 * @author hoza
 *
 */
public class W9RegistrationVO extends BaseVO implements Comparable<W9RegistrationVO> {
	private static final long serialVersionUID = 20090428L;

	private String legalBusinessName;
	private String doingBusinessAsName;
	private LookupVO taxStatus;
	private LocationVO address;
	private Boolean isTaxExempt;
	private String ein;
	private String ein2;
	private String originalEin;
	private String originalUnmaskedEin;
	private Boolean isPenaltyIndicatiorCertified = Boolean.FALSE;
	private Integer version;
	private Date modifiedDate;
	private Date createdDate;
	private Date archivedDate;
	private Integer vendorId;
	private String modifiedBy;
	private String cancel;
	private int taxPayerTypeId;
	private Date dateOfBirth;
	private String dob;
	private String editOrCancel;
	private int orginalTaxPayerTypeId;
	
	
	/**
	 * @return the legalBusinessName
	 */

	public String getLegalBusinessName() {
		return legalBusinessName;
	}
	/**
	 * @param legalBusinessName the legalBusinessName to set
	 */
	public void setLegalBusinessName(String legalBusinessName) {
		this.legalBusinessName = legalBusinessName;
	}
	/**
	 * @return the doingBusinessAsName
	 */
	public String getDoingBusinessAsName() {
		return doingBusinessAsName;
	}
	/**
	 * @param doingBusinessAsName the doingBusinessAsName to set
	 */
	public void setDoingBusinessAsName(String doingBusinessAsName) {
		this.doingBusinessAsName = doingBusinessAsName;
	}

	/**
	 * @return the address
	 */
	public LocationVO getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(LocationVO address) {
		this.address = address;
	}
	/**
	 * @return the isTaxExempt
	 */
	public Boolean getIsTaxExempt() {
		return isTaxExempt;
	}
	/**
	 * @param isTaxExempt the isTaxExempt to set
	 */
	public void setIsTaxExempt(Boolean isTaxExempt) {
		this.isTaxExempt = isTaxExempt;
	}
	/**
	 * @return the ein
	 */
	public String getEin() {
		return ein;
	}
	/**
	 * @param ein the ein to set
	 */
	public void setEin(String ein) {
		this.ein = ein;
	}
	/**
	 * @return the isPenaltyIndicatiorCertified
	 */
	public Boolean getIsPenaltyIndicatiorCertified() {
		return isPenaltyIndicatiorCertified;
	}
	/**
	 * @param isPenaltyIndicatiorCertified the isPenaltyIndicatiorCertified to set
	 */
	public void setIsPenaltyIndicatiorCertified(Boolean isPenaltyIndicatiorCertified) {
		this.isPenaltyIndicatiorCertified = isPenaltyIndicatiorCertified;
	}
	/**
	 * @param taxStatus the taxStatus to set
	 */
	public void setTaxStatus(LookupVO taxStatus) {
		this.taxStatus = taxStatus;
	}
	/**
	 * @return the taxStatus
	 */
	public LookupVO getTaxStatus() {
		return taxStatus;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the archivedDate
	 */
	public Date getArchivedDate() {
		return archivedDate;
	}
	/**
	 * @param archivedDate the archivedDate to set
	 */
	public void setArchivedDate(Date archivedDate) {
		this.archivedDate = archivedDate;
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
	/**
	 * @return the modifyBy
	 */
	public String getModifyBy() {
		return modifiedBy;
	}
	/**
	 * @param modifyBy the modifyBy to set
	 */
	public void setModifyBy(String modifyBy) {
		this.modifiedBy = modifyBy;
	}
	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(W9RegistrationVO comparewith) {
		if( this.getCompareStringWithoutAddress().equals(comparewith.getCompareStringWithoutAddress()) ) {
			return 0 ;
		}
		return 1;
	}

	public Object getCompareStringWithoutAddress() {
		//Check everything but address
		StringBuilder sb = new StringBuilder();
		sb.append((getVendorId() != null) ? getVendorId().toString() : "");
		sb.append((getLegalBusinessName() != null) ? getLegalBusinessName() : "");
		sb.append((getDoingBusinessAsName() != null) ? getDoingBusinessAsName() : "");
		sb.append((getEin() != null) ? getEin() : "");
		sb.append((getIsTaxExempt() != null) ? getIsTaxExempt().toString() : "");
		sb.append((getIsPenaltyIndicatiorCertified() != null) ? getIsPenaltyIndicatiorCertified().toString() : "");
		sb.append((getTaxStatus() != null && getTaxStatus().getId() != null) ? getTaxStatus().getId().toString() : "");
		return sb.toString();
	}
	/**
	 * @return the ein2
	 */
	public String getEin2() {
		return ein2;
	}
	/**
	 * @param ein2 the ein2 to set
	 */
	public void setEin2(String ein2) {
		this.ein2 = ein2;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
	}
	/**
	 * @return the originalEin
	 */
	public String getOriginalEin() {
		return originalEin;
	}
	/**
	 * @param originalEin the originalEin to set
	 */
	public void setOriginalEin(String originalEin) {
		this.originalEin = originalEin;
	}
	/**
	 * @return the originalUnmaskedEin
	 */
	public String getOriginalUnmaskedEin() {
		return originalUnmaskedEin;
	}
	/**
	 * @param originalUnmaskedEin the originalUnmaskedEin to set
	 */
	public void setOriginalUnmaskedEin(String originalUnmaskedEin) {
		this.originalUnmaskedEin = originalUnmaskedEin;
	}
	public int getTaxPayerTypeId() {
		return taxPayerTypeId;
	}
	public void setTaxPayerTypeId(int taxPayerTypeId) {
		this.taxPayerTypeId = taxPayerTypeId;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getEditOrCancel() {
		return editOrCancel;
	}
	public void setEditOrCancel(String editOrCancel) {
		this.editOrCancel = editOrCancel;
	}
	public int getOrginalTaxPayerTypeId() {
		return orginalTaxPayerTypeId;
	}
	public void setOrginalTaxPayerTypeId(int orginalTaxPayerTypeId) {
		this.orginalTaxPayerTypeId = orginalTaxPayerTypeId;
	}
	
	
	
	
}

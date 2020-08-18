package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:19 $
 */

public class SPNBuilderFormDTO  extends SerializedBaseDTO
{
	
	private static final long serialVersionUID = -8921680114723701648L;
	private Integer spnId;
	private Integer buyerId;
	private String criteriaDescr;
	
	private String networkName;
	private String contactName;
	private String contactEmail;
	private String networkDescription;
	private String approvalInstructions;
	private boolean spnLocked;
	private SPNCriteriaDTO theCriteria = new SPNCriteriaDTO();
			
	private String selectedDocument;
	private String businessName;
	private String logoFileName;
	
	private List<SPNBuilderDocRowDTO> documentsDetailsList = new ArrayList<SPNBuilderDocRowDTO>();
	
	private String checkboxNetworkRequiresDocuments;
	
	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getNetworkDescription() {
		return networkDescription;
	}

	public void setNetworkDescription(String networkDescription) {
		this.networkDescription = networkDescription;
	}

	public String getApprovalInstructions() {
		return approvalInstructions;
	}

	public void setApprovalInstructions(String approvalInstructions) {
		this.approvalInstructions = approvalInstructions;
	}

	public String getSelectedDocument() {
		return selectedDocument;
	}

	public void setSelectedDocument(String selectedDocument) {
		this.selectedDocument = selectedDocument;
	}


	public String getCheckboxNetworkRequiresDocuments() {
		return checkboxNetworkRequiresDocuments;
	}

	public void setCheckboxNetworkRequiresDocuments(
			String checkboxNetworkRequiresDocuments) {
		this.checkboxNetworkRequiresDocuments = checkboxNetworkRequiresDocuments;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getCriteriaDescr() {
		return criteriaDescr;
	}

	public void setCriteriaDescr(String criteriaDescr) {
		this.criteriaDescr = criteriaDescr;
	}


	public List<SPNBuilderDocRowDTO> getDocumentsDetailsList() {
		return documentsDetailsList;
	}

	public void setDocumentsDetailsList(
			List<SPNBuilderDocRowDTO> documentsDetailsList) {
		this.documentsDetailsList = documentsDetailsList;
	}

	/**
	 * @return the spnLocked
	 */
	public boolean getSpnLocked() {
		return spnLocked;
	}

	/**
	 * @param spnLocked the spnLocked to set
	 */
	public void setSpnLocked(boolean spnLocked) {
		this.spnLocked = spnLocked;
	}

	public SPNCriteriaDTO getTheCriteria() {
		return theCriteria;
	}

	public void setTheCriteria(SPNCriteriaDTO theCriteria) {
		this.theCriteria = theCriteria;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	
	public int getNumDocs()
	{
		if(documentsDetailsList == null)
			return 0;
		
		return documentsDetailsList.size();
	}
	
	
}

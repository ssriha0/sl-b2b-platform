package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;

/**
 * 
 * 
 *
 */
public class SPNCriteriaAndCredentialsTabModel extends SPNBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SPNDocumentRowDTO> documents;

	//Input data
	private Integer selectedDocument;
	private String fileName;
	
	private String providerFirmId;
	private String networkId;
	
	/**
	 * 
	 * @return List
	 */
	public List<SPNDocumentRowDTO> getDocuments()
	{
		return documents;
	}
	/**
	 * 
	 * @param documents
	 */
	public void setDocuments(List<SPNDocumentRowDTO> documents)
	{
		this.documents = documents;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getSelectedDocument()
	{
		return selectedDocument;
	}
	/**
	 * 
	 * @param selectedDocument
	 */
	public void setSelectedDocument(Integer selectedDocument)
	{
		this.selectedDocument = selectedDocument;
	}
	/**
	 * 
	 * @return String
	 */
	public String getFileName()
	{
		return fileName;
	}
	/**
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	public String getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(String providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
}

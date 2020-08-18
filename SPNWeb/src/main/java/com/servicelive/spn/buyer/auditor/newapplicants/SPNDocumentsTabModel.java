package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;
import com.servicelive.domain.spn.detached.SPNAuditorSearchExpandCriteriaVO;
import com.servicelive.domain.lookup.LookupSPNDocumentState;

/**
 * 
 *
 */
public class SPNDocumentsTabModel extends SPNBaseModel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Dropdown list, display only
	private List<LookupSPNDocumentState> actionList;
	private String errorMessage;
	private String networkId;
	private String providerFirmId;
	private SPNAuditorSearchExpandCriteriaVO expandCriteriaVO;
	
	
	private Integer selectedAction;
	private String numPages;
	private String comments;
	
	private List<SPNDocumentRowDTO> documents;


	/**
	 * @return the selectedAction
	 */
	public Integer getSelectedAction() {
		return selectedAction;
	}

	/**
	 * @param selectedAction the selectedAction to set
	 */
	public void setSelectedAction(Integer selectedAction) {
		this.selectedAction = selectedAction;
	}

	/**
	 * @return the numPages
	 */
	public String getNumPages() {
		return numPages;
	}

	/**
	 * @param numPages the numPages to set
	 */
	public void setNumPages(String numPages) {
		this.numPages = numPages;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the documents
	 */
	public List<SPNDocumentRowDTO> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<SPNDocumentRowDTO> documents) {
		this.documents = documents;
	}
	/**
	 * 
	 * @return List
	 */
	public List<LookupSPNDocumentState> getActionList()
	{
		return actionList;
	}
	/**
	 * 
	 * @param actionList
	 */
	public void setActionList(List<LookupSPNDocumentState> actionList)
	{
		this.actionList = actionList;
	}
	/**
	 * 
	 * @return String
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}
	/**
	 * 
	 * @param errorMessage
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	/**
	 * 
	 * @return String
	 */
	public String getNetworkId()
	{
		return networkId;
	}
	/**
	 * 
	 * @param networkId
	 */
	public void setNetworkId(String networkId)
	{
		this.networkId = networkId;
	}
	/**
	 * 
	 * @return String
	 */
	public String getProviderFirmId()
	{
		return providerFirmId;
	}
	/**
	 * 
	 * @param providerFirmId
	 */
	public void setProviderFirmId(String providerFirmId)
	{
		this.providerFirmId = providerFirmId;
	}

	public SPNAuditorSearchExpandCriteriaVO getExpandCriteriaVO() {
		return expandCriteriaVO;
	}

	public void setExpandCriteriaVO(
			SPNAuditorSearchExpandCriteriaVO expandCriteriaVO) {
		this.expandCriteriaVO = expandCriteriaVO;
	}
	
	
}

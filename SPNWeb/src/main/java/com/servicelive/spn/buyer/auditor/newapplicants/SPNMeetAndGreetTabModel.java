package com.servicelive.spn.buyer.auditor.newapplicants;

import java.util.List;

import com.servicelive.spn.core.SPNBaseModel;
import com.servicelive.domain.spn.detached.SPNAuditorSearchExpandCriteriaVO;
import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;

/**
 * 
 *
 */
public class SPNMeetAndGreetTabModel extends SPNBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SPNMeetGreetRowDTO> meetList;
	
	// Right side input
	private String selectedAction;
	private String name;
	private String date;
	private String comments;
	private SPNAuditorSearchExpandCriteriaVO expandCriteriaVO;

	// Dropdown Selections
	List<LookupSPNMeetAndGreetState> actionList;	
	
	
	private String errorMessage;
	
	private String providerFirmId;
	private String networkId;
	
	/**
	 * @return the meetList
	 */
	public List<SPNMeetGreetRowDTO> getMeetList() {
		return meetList;
	}
	/**
	 * @param meetList the meetList to set
	 */
	public void setMeetList(List<SPNMeetGreetRowDTO> meetList) {
		this.meetList = meetList;
	}
	/**
	 * @return the selectedAction
	 */
	public String getSelectedAction() {
		return selectedAction;
	}
	/**
	 * @param selectedAction the selectedAction to set
	 */
	public void setSelectedAction(String selectedAction) {
		this.selectedAction = selectedAction;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
	 * @return List
	 */
	public List<LookupSPNMeetAndGreetState> getActionList()
	{
		return actionList;
	}
	/**
	 * 
	 * @param actionList
	 */
	public void setActionList(List<LookupSPNMeetAndGreetState> actionList)
	{
		this.actionList = actionList;
	}
	public SPNAuditorSearchExpandCriteriaVO getExpandCriteriaVO() {
		return expandCriteriaVO;
	}
	public void setExpandCriteriaVO(
			SPNAuditorSearchExpandCriteriaVO expandCriteriaVO) {
		this.expandCriteriaVO = expandCriteriaVO;
	}
	
}

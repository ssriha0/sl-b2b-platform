package com.servicelive.spn.buyer.auditor.newapplicants;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 */
public class SPNMeetGreetRowDTO implements Serializable
{
	private static final long serialVersionUID = 20090127L;
	private String status;
	private String subStatus;
	private String networkTitle;
	private String networkId;
	private String name;
	private String resourceID;
	private Date date;
	private String comments;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}
	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}
	/**
	 * @return the networkTitle
	 */
	public String getNetworkTitle() {
		return networkTitle;
	}
	/**
	 * @param networkTitle the networkTitle to set
	 */
	public void setNetworkTitle(String networkTitle) {
		this.networkTitle = networkTitle;
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
	 * @return the resourceID
	 */
	public String getResourceID() {
		return resourceID;
	}
	/**
	 * @param resourceID the resourceID to set
	 */
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
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
	
	

}

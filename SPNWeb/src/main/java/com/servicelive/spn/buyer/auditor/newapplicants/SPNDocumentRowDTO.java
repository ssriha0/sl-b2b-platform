package com.servicelive.spn.buyer.auditor.newapplicants;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 *
 */
public class SPNDocumentRowDTO implements Serializable
{

	private static final long serialVersionUID = -5794336545864180513L;
	
	private Integer id;
	private String status;
	private String statusIcon;
	private String title;
	private String filename;
	private Integer numPages;
	private String lastAuditorName;
	private String lastAuditorID;
	private Date lastAuditDate;
	
	private String action;
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
	 * @return the statusIcon
	 */
	public String getStatusIcon() {
		return statusIcon;
	}
	/**
	 * @param statusIcon the statusIcon to set
	 */
	public void setStatusIcon(String statusIcon) {
		this.statusIcon = statusIcon;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the numPages
	 */
	public Integer getNumPages() {
		return numPages;
	}
	/**
	 * @param numPages the numPages to set
	 */
	public void setNumPages(Integer numPages) {
		this.numPages = numPages;
	}
	/**
	 * @return the lastAuditorName
	 */
	public String getLastAuditorName() {
		return lastAuditorName;
	}
	/**
	 * @param lastAuditorName the lastAuditorName to set
	 */
	public void setLastAuditorName(String lastAuditorName) {
		this.lastAuditorName = lastAuditorName;
	}
	/**
	 * @return the lastAuditorID
	 */
	public String getLastAuditorID() {
		return lastAuditorID;
	}
	/**
	 * @param lastAuditorID the lastAuditorID to set
	 */
	public void setLastAuditorID(String lastAuditorID) {
		this.lastAuditorID = lastAuditorID;
	}
	/**
	 * 
	 * @return Date
	 */
	public Date getLastAuditDate()
	{
		return lastAuditDate;
	}
	/**
	 * 
	 * @param lastAuditDate
	 */
	public void setLastAuditDate(Date lastAuditDate)
	{
		this.lastAuditDate = lastAuditDate;
	}
	/**
	 * 
	 * @return String
	 */
	public String getAction()
	{
		return action;
	}
	/**
	 * 
	 * @param action
	 */
	public void setAction(String action)
	{
		this.action = action;
	}
	/**
	 * 
	 * @return String
	 */
	public String getComments()
	{
		return comments;
	}
	/**
	 * 
	 * @param comments
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getId()
	{
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	

}

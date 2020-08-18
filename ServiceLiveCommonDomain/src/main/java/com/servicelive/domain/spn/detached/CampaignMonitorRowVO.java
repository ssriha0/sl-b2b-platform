package com.servicelive.domain.spn.detached;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.servicelive.domain.BaseDomain;
/**
 * 
 * 
 *
 */
public class CampaignMonitorRowVO extends BaseDomain
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20090203L;
	// Sanity Check data.  Do not keep these 3 around after initial testing
	private Integer buyerId;
	private Integer spnId;
	private Integer campaignId;
	
	
	private String wfStatus;
	private String wfStatusId;
	private String wfStatusColor;
	private String campaignName;
	private String networkName;
	
	private Date startDate;
	private Date endDate;
	private String dates = "uninitialized"; // This is the string to be sent to the front end
	
	private ProviderMatchingCountsVO invitedPros = new ProviderMatchingCountsVO();

	/**
	 * 
	 * @return String
	 */
	public String getCampaignName()
	{
		return campaignName;
	}
	/**
	 * 
	 * @param campaignName
	 */
	public void setCampaignName(String campaignName)
	{
		this.campaignName = campaignName;
	}
	/**
	 * 
	 * @return String
	 */
	public String getNetworkName()
	{
		return networkName;
	}
	/**
	 * 
	 * @param networkName
	 */
	public void setNetworkName(String networkName)
	{
		this.networkName = networkName;
	}
	/**
	 * 
	 * @return String
	 */
	public String getDates()
	{
		if(startDate != null && endDate != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			dates = sdf.format(startDate);
			dates += "-";
			dates += sdf.format(endDate);			
		}
		return dates;
	}
	/**
	 * 
	 * @param dates
	 */
	public void setDates(String dates)
	{
		this.dates = dates;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getBuyerId()
	{
		return buyerId;
	}
	/**
	 * 
	 * @param buyerId
	 */
	public void setBuyerId(Integer buyerId)
	{
		this.buyerId = buyerId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getSpnId()
	{
		return spnId;
	}
	/**
	 * 
	 * @param spnId
	 */
	public void setSpnId(Integer spnId)
	{
		this.spnId = spnId;
	}
	/**
	 * 
	 * @return Integer
	 */
	public Integer getCampaignId()
	{
		return campaignId;
	}
	/**
	 * 
	 * @param campaignId
	 */
	public void setCampaignId(Integer campaignId)
	{
		this.campaignId = campaignId;
	}
	/**
	 * 
	 * @return Date
	 */
	public Date getStartDate()
	{
		return startDate;
	}
	/**
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	/**
	 * 
	 * @return Date
	 */
	public Date getEndDate()
	{
		return endDate;
	}
	/**
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return String
	 */
	public String getWfStatus()
	{
		return wfStatus;
	}
	/**
	 * 
	 * @param wfStatus
	 */
	public void setWfStatus(String wfStatus)
	{
		this.wfStatus = wfStatus;
	}
	/**
	 * @return the wfStatusColor
	 */
	public String getWfStatusColor() {
		return wfStatusColor;
	}
	/**
	 * @param wfStatusColor the wfStatusColor to set
	 */
	public void setWfStatusColor(String wfStatusColor) {
		this.wfStatusColor = wfStatusColor;
	}
	/**
	 * @return the wfStatusId
	 */
	public String getWfStatusId() {
		return wfStatusId;
	}
	/**
	 * @param wfStatusId the wfStatusId to set
	 */
	public void setWfStatusId(String wfStatusId) {
		this.wfStatusId = wfStatusId;
	}
	/**
	 * @return the invitedPros
	 */
	public ProviderMatchingCountsVO getInvitedPros() {
		return invitedPros;
	}
	/**
	 * @param invitedPros the invitedPros to set
	 */
	public void setInvitedPros(ProviderMatchingCountsVO invitedPros) {
		this.invitedPros = invitedPros;
	}
}

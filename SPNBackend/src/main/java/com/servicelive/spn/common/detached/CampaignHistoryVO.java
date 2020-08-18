/**
 * 
 */
package com.servicelive.spn.common.detached;

import java.util.Date;

import com.servicelive.domain.LoggableBaseDomain;

/**
 * @author hoza
 *
 */
public class CampaignHistoryVO extends LoggableBaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3140914470547830610L;

	private Integer campaignId;
	private String campaignName;
	private String statusDescription;
	private String status;
	private Date campaignStartDate;
	private Date campaignEndDate;
	private String comments;
	
	
	/**
	 * @return the campaignId
	 */
	public Integer getCampaignId() {
		return campaignId;
	}
	/**
	 * @param campaignId the campaignId to set
	 */
	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}
	/**
	 * @return the campaignName
	 */
	public String getCampaignName() {
		return campaignName;
	}
	/**
	 * @param campaignName the campaignName to set
	 */
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}
	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	/**
	 * @return the campaignStartDate
	 */
	public Date getCampaignStartDate() {
		return campaignStartDate;
	}
	/**
	 * @param campaignStartDate the campaignStartDate to set
	 */
	public void setCampaignStartDate(Date campaignStartDate) {
		this.campaignStartDate = campaignStartDate;
	}
	/**
	 * @return the campaignEndDate
	 */
	public Date getCampaignEndDate() {
		return campaignEndDate;
	}
	/**
	 * @param campaignEndDate the campaignEndDate to set
	 */
	public void setCampaignEndDate(Date campaignEndDate) {
		this.campaignEndDate = campaignEndDate;
	}
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
}

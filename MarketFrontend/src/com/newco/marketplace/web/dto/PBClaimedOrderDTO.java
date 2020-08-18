package com.newco.marketplace.web.dto;

import java.util.Date;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 01:13:45 $
 */

/*
 * Maintenance History
 * $Log: PBClaimedOrderDTO.java,v $
 * Revision 1.7  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.12.1  2008/04/23 11:41:31  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.6  2008/04/23 05:19:46  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.5  2008/02/14 23:44:49  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.4.6.1  2008/02/08 02:34:15  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.4  2008/01/16 18:23:20  mhaye05
 * changed datetime attribute from string to date
 *
 * Revision 1.3  2008/01/16 17:17:35  mhaye05
 * added statusId attribute
 *
 */
public class PBClaimedOrderDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6765312053642871878L;
	private String selected;
	private Date dateTime;
	private String soId;
	private String title;
	private Integer statusId;
	private String destinationTab;
	private String parentGroupId;
	
	public String getDestinationTab() {
		return destinationTab;
	}
	public void setDestinationTab(String destinationTab) {
		this.destinationTab = destinationTab;
	}
	public String getSelected()
	{
		return selected;
	}
	public void setSelected(String selected)
	{
		this.selected = selected;
	}
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public String getSoId()
	{
		return soId;
	}
	public void setSoId(String soId)
	{
		this.soId = soId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}
	
}

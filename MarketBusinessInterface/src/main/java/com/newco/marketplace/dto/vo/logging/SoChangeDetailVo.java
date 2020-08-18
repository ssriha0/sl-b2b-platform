package com.newco.marketplace.dto.vo.logging;

import java.util.Date;

import com.sears.os.vo.ABaseVO;

public class SoChangeDetailVo extends ABaseVO implements Comparable<SoChangeDetailVo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6809168278630367195L;
	private Integer soLoggingId=null;
	private Integer soContactId=null;
	private String soId=null;
	private Integer actionId=null;
	private String actionDescription=null;
	private String chgComment=null;
	private Date createdDate;
	private String modifiedBy=null;
	private Date modifiedDate;
	private Integer roleId;
	private String roleName;
	private String createdByName;
	private Integer entityId;	
		
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	public Integer getSoLoggingId() {
		return soLoggingId;
	}
	public void setSoLoggingId(Integer soLoggingId) {
		this.soLoggingId = soLoggingId;
	}
	public Integer getSoContactId() {
		return soContactId;
	}
	public void setSoContactId(Integer soContactId) {
		this.soContactId = soContactId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	/*public Integer getChgTypeId() {
		return chgTypeId;
	}
	public void setChgTypeId(Integer chgTypeId) {
		this.chgTypeId = chgTypeId;
	}*/
	public String getChgComment() {
		return chgComment;
	}
	public void setChgComment(String chgComment) {
		this.chgComment = chgComment;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString(){
		//TODO
		return("<SoChangeDetailVo" );
	}
	/* public String toString() {
	        return ("<SoChangeVo>" 
	                + changeType+ "   |  "
	                + changeId + "   |  "
	                + changeDescription + "   |  "
	                + "</SoChangeVo>");
	    } // soChangeTypeVo
*/
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionDescription() {
		return actionDescription;
	}
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCreatedByName() {
		return createdByName;
	}
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	} 
	public int compareTo(SoChangeDetailVo soChngDetail) 
	{
		if(this.soLoggingId!=null && soChngDetail != null && soChngDetail.getSoLoggingId()!= null){
			return this.soLoggingId.compareTo(soChngDetail.getSoLoggingId());
		}	
		else if (this.createdDate != null && soChngDetail != null && soChngDetail.getCreatedDate()!= null){
			 int lastCmp = this.createdDate.compareTo(soChngDetail.getCreatedDate());
	
			 return (lastCmp != 0 ? lastCmp : compareWithActionDescription(this, soChngDetail));
}
		return 0;
	} 
	
	public int compareWithActionDescription(SoChangeDetailVo soChngDetail,SoChangeDetailVo soChngDetail1)
	{
		if (soChngDetail.actionDescription.startsWith("Service Order Activated"))
		{
			return -1;		
		}
		else 
		{
			return soChngDetail.actionDescription.compareTo(soChngDetail1.getActionDescription());
		}	
	}
}

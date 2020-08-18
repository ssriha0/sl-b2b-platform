package com.newco.marketplace.dto.vo.permission;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA LLC
 * $Revision$ $Author$ $Date$
 *
 */

public class PermissionSetVO extends SerializableBaseVO {

	private static final long serialVersionUID = -6867633735003150330L;
	private Integer permissionSetId;
	private String permissionSetName;
	private Integer adminInd;
	private List<ActivityVO> activities;
	
	public Integer getPermissionSetId() {
		return permissionSetId;
	}
	public void setPermissionSetId(Integer permissionSetId) {
		this.permissionSetId = permissionSetId;
	}
	public String getPermissionSetName() {
		return permissionSetName;
	}
	public void setPermissionSetName(String permissionSetName) {
		this.permissionSetName = permissionSetName;
	}
	public List<ActivityVO> getActivities() {
		return activities;
	}
	public void setActivities(List<ActivityVO> activities) {
		this.activities = activities;
	}
	public Integer getAdminInd() {
		return adminInd;
	}
	public void setAdminInd(Integer adminInd) {
		this.adminInd = adminInd;
	}
}
/*
 * Maintenance History
 * $Log: PermissionSetVO.java,v $
 * Revision 1.4  2008/04/26 00:40:09  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.4.1  2008/04/01 21:57:39  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.2  2008/03/27 18:57:28  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.1.2.1  2008/03/07 21:45:12  mhaye05
 * Initial check in
 *
 */
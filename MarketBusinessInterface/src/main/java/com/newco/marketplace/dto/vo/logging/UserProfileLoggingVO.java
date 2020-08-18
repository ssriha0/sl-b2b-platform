package com.newco.marketplace.dto.vo.logging;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 * $Revision$ $Author$ $Date$
 * 
 */
public class UserProfileLoggingVO extends SerializableBaseVO {

	private static final long serialVersionUID = 5608445458239940710L;
	private String userName;
	private String action;
	private Date modifiedDate;
	private String modifiedBy;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
/*
 * Maintenance History:
 * $Log: UserProfileLoggingVO.java,v $
 * Revision 1.4  2008/04/26 00:40:08  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.4.1  2008/04/01 21:57:40  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.2  2008/03/27 18:57:29  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.1.2.1  2008/03/12 19:43:09  mhaye05
 * Initial check in
 *
 */
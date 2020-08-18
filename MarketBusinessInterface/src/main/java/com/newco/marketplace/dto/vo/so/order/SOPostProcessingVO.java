package com.newco.marketplace.dto.vo.so.order;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History
 * $Log: SOPostProcessingVO.java,v $
 * Revision 1.4  2008/04/26 00:40:11  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.12.1  2008/04/23 11:41:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:17:07  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.2  2008/02/14 23:44:38  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.1.8.1  2008/02/08 02:32:15  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.1  2008/01/08 18:27:50  mhaye05
 * Initial Check In
 *
 */
public class SOPostProcessingVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 251078371211519016L;
	private Integer buyerId;
	private String actionId;
	private String className;
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}

package com.newco.marketplace.web.dto;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 01:13:44 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBWorkfolwTabDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537105842251708249L;
	private Integer filterId;

	public Integer getFilterId() {
		return filterId;
	}

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	
	
}
/*
 * Maintenance History
 * $Log: PBWorkfolwTabDTO.java,v $
 * Revision 1.6  2008/04/26 01:13:44  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.4.12.1  2008/04/23 11:41:30  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.5  2008/04/23 05:19:46  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.4  2008/02/14 23:44:47  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.3.6.1  2008/02/08 02:34:11  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.3  2008/01/18 01:59:10  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.1  2008/01/16 21:59:41  mhaye05
 * updated for workflow monitor tab
 *
 */
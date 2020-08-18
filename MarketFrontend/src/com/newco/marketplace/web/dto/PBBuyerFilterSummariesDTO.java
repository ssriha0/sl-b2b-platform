package com.newco.marketplace.web.dto;

import java.util.ArrayList;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:44 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBBuyerFilterSummariesDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5551899169871190607L;
	private Integer filterId;
	private String filterName;
	private Integer count1;
	private Integer count2;
	private Integer count3;
	private String excBuyerList; 

	public String getExcBuyerList() {
		return excBuyerList;
	}
	public void setExcBuyerList(String excBuyerList) {
		this.excBuyerList = excBuyerList;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public Integer getCount1() {
		return count1;
	}
	public void setCount1(Integer count) {
		this.count1 = count;
	}
	public Integer getCount2() {
		return count2;
	}
	public void setCount2(Integer count2) {
		this.count2 = count2;
	}
	public Integer getCount3() {
		return count3;
	}
	public void setCount3(Integer count3) {
		this.count3 = count3;
	}
	
}
/*
 * Maintenance History
 * $Log: PBBuyerFilterSummariesDTO.java,v $
 * Revision 1.4  2008/04/26 01:13:44  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.12.1  2008/04/23 11:41:29  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:19:46  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.2  2008/02/14 23:44:47  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.1.6.1  2008/02/08 02:34:10  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.1  2008/01/16 21:59:41  mhaye05
 * updated for workflow monitor tab
 *
 */
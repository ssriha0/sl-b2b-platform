package com.newco.marketplace.vo;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:51:56 $
 */

/*
 * Maintenance History
 * $Log: ServiceOrderMonitorResultVO.java,v $
 * Revision 1.5  2008/04/26 00:51:56  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.12.1  2008/04/23 11:42:05  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:17:46  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.3  2008/02/14 23:44:14  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.2.14.1  2008/02/08 02:33:23  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.2  2007/11/17 18:39:18  mhaye05
 * changed attributes from being typed as ArrayLists to being Lists
 *
 */
public class ServiceOrderMonitorResultVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2962159949900634292L;
	private  PaginationVO  paginationVO ;
	private List inactiveOrderList;
	private List<Integer> workFlowStatusIds;
	private List serviceOrderResults;
	
	
	public List getInactiveOrderList() {
		return inactiveOrderList;
	}
	public void setInactiveOrderList(List inactiveOrderList) {
		this.inactiveOrderList = inactiveOrderList;
	}
	public PaginationVO getPaginationVO() {
		return paginationVO;
	}
	public void setPaginationVO(PaginationVO paginationVO) {
		this.paginationVO = paginationVO;
	}
	public List<Integer> getWorkFlowStatusIds() {
		return workFlowStatusIds;
	}
	public void setWorkFlowStatusIds(List<Integer> workFlowStatusIds) {
		this.workFlowStatusIds = workFlowStatusIds;
	}
	public List getServiceOrderResults() {
		return serviceOrderResults;
	}
	public void setServiceOrderResults(List serviceOrderResults) {
		this.serviceOrderResults = serviceOrderResults;
	}

}

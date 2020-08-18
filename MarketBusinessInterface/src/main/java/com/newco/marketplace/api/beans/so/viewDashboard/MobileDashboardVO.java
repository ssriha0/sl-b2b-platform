package com.newco.marketplace.api.beans.so.viewDashboard;

import java.util.Arrays;
import java.util.List;

import com.newco.marketplace.mobile.constants.MPConstants;




public class MobileDashboardVO {
	private Integer firmId;
	private Integer resourceId;
	private Integer roleId;
	private List<DashBoardCountVO> countVO;
	private List<Integer> wfStatusIdList;
	
	public MobileDashboardVO(){
		this.wfStatusIdList = Arrays.asList(MPConstants.STATUS_ARRAY_WITH_OUT_RECIEVED);
	}
	public MobileDashboardVO(Integer roleId){
		this.wfStatusIdList = Arrays.asList(MPConstants.STATUS_ARRAY_WITH_RECIEVED);	
	}
	public Integer getFirmId() {
		return firmId;
	}
	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<DashBoardCountVO> getCountVO() {
		return countVO;
	}
	public void setCountVO(List<DashBoardCountVO> countVO) {
		this.countVO = countVO;
	}
	public List<Integer> getWfStatusIdList() {
		return wfStatusIdList;
	}
	public void setWfStatusIdList(List<Integer> wfStatusIdList) {
		this.wfStatusIdList = wfStatusIdList;
	}
}
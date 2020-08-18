package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.newco.marketplace.dto.vo.LookupVO;
import com.sears.os.vo.SerializableBaseVO;

public class SPNMainMonitorModel extends SerializableBaseVO{
	private static final long serialVersionUID = 5792243355104464860L;
	private List<SPNMainMonitorVO> spnMonitorList;
	private SPNMainMonitorVO spnMonitorVO;
	private String acceptInvite;
	private Integer selectedSpnId;
	private boolean incompleteSpnInd;
	private ComplianceCriteriaVO spnComplianceVO;
	private List<LookupVO> buyerFilter;
	private List<LookupVO> membershipStatusFilter;
	private List<String> selectedBuyerValues;
	private List<String> selectedMemStatus;
	private List<String> selectedFilterMemStatus;
	private Integer memStatusResetInd;
	private Integer buyerResetInd;
	
	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	private String aaData[][];
	
	//SL-19387 -To set value of Advance filter to model(Background Check)
	private BackgroundFilterProviderVO backgroundFilterProviderVO;


	public String[][] getAaData() {
		return aaData;
	}
	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
	public Integer getMemStatusResetInd() {
		return memStatusResetInd;
	}
	public void setMemStatusResetInd(Integer memStatusResetInd) {
		this.memStatusResetInd = memStatusResetInd;
	}
	public Integer getBuyerResetInd() {
		return buyerResetInd;
	}
	public void setBuyerResetInd(Integer buyerResetInd) {
		this.buyerResetInd = buyerResetInd;
	}
	public boolean isIncompleteSpnInd() {
		return incompleteSpnInd;
	}
	public void setIncompleteSpnInd(boolean incompleteSpnInd) {
		this.incompleteSpnInd = incompleteSpnInd;
	}
	public List<SPNMainMonitorVO> getSpnMonitorList() {
		return spnMonitorList;
	}
	public void setSpnMonitorList(List<SPNMainMonitorVO> spnMonitorList) {
		this.spnMonitorList = spnMonitorList;
	}
	public SPNMainMonitorVO getSpnMonitorVO() {
		return spnMonitorVO;
	}
	public void setSpnMonitorVO(SPNMainMonitorVO spnMonitorVO) {
		this.spnMonitorVO = spnMonitorVO;
	}
	public String getAcceptInvite() {
		return acceptInvite;
	}
	public void setAcceptInvite(String acceptInvite) {
		this.acceptInvite = acceptInvite;
	}
	public Integer getSelectedSpnId() {
		return selectedSpnId;
	}
	public void setSelectedSpnId(Integer selectedSpnId) {
		this.selectedSpnId = selectedSpnId;
	}
	public ComplianceCriteriaVO getSpnComplianceVO() {
		return spnComplianceVO;
	}
	public void setSpnComplianceVO(ComplianceCriteriaVO spnComplianceVO) {
		this.spnComplianceVO = spnComplianceVO;
	}
	
	public List<LookupVO> getBuyerFilter() {
		return buyerFilter;
	}
	public void setBuyerFilter(List<LookupVO> buyerFilter) {
		this.buyerFilter = buyerFilter;
	}
	public List<LookupVO> getMembershipStatusFilter() {
		return membershipStatusFilter;
	}
	public void setMembershipStatusFilter(List<LookupVO> membershipStatusFilter) {
		this.membershipStatusFilter = membershipStatusFilter;
	}
	public List<String> getSelectedBuyerValues() {
		return selectedBuyerValues;
	}
	public void setSelectedBuyerValues(List<String> selectedBuyerValues) {
		this.selectedBuyerValues = selectedBuyerValues;
	}
	public List<String> getSelectedMemStatus() {
		return selectedMemStatus;
	}
	public void setSelectedMemStatus(List<String> selectedMemStatus) {
		this.selectedMemStatus = selectedMemStatus;
	}
	public List<String> getSelectedFilterMemStatus() {
		return selectedFilterMemStatus;
	}
	public void setSelectedFilterMemStatus(List<String> selectedFilterMemStatus) {
		this.selectedFilterMemStatus = selectedFilterMemStatus;
	}
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public BackgroundFilterProviderVO getBackgroundFilterProviderVO() {
		return backgroundFilterProviderVO;
	}
	public void setBackgroundFilterProviderVO(
			BackgroundFilterProviderVO backgroundFilterProviderVO) {
		this.backgroundFilterProviderVO = backgroundFilterProviderVO;
	}

		
}
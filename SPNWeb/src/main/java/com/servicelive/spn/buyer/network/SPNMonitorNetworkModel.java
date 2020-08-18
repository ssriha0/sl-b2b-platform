package com.servicelive.spn.buyer.network;

import java.util.LinkedList;
import java.util.List;

import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.spn.detached.SPNSummaryFilterVO;
import com.servicelive.domain.spn.detached.SPNSummaryVO;
import com.servicelive.domain.spn.network.SPNComplianceFilterVO;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.spn.core.SPNBaseModel;
/**
 * 
 * 
 *
 */
public class SPNMonitorNetworkModel extends SPNBaseModel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -334794863125859640L;
	
	private List<SPNSummaryVO> spnSummary = new LinkedList<SPNSummaryVO>();
	private List<LookupMarket> marketList;
	private List<LookupStates> stateList;
	private SPNSummaryFilterVO spnSummaryFilterVO;
	private SPNExclusionsVO exclusionsVO;
	private SPNComplianceFilterVO spnComplianceVO;
	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	private String aaData[][];
	
	
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

	public String[][] getAaData() {
		return aaData;
	}

	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}

	public SPNComplianceFilterVO getSpnComplianceVO() {
		return spnComplianceVO;
	}

	public void setSpnComplianceVO(SPNComplianceFilterVO spnComplianceVO) {
		this.spnComplianceVO = spnComplianceVO;
	}

	public SPNExclusionsVO getExclusionsVO() {
		return exclusionsVO;
	}

	public void setExclusionsVO(SPNExclusionsVO exclusionsVO) {
		this.exclusionsVO = exclusionsVO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the spnSummary
	 */
	public List<SPNSummaryVO> getSpnSummary() {
		return spnSummary;
	}

	/**
	 * @param spnSummary the spnSummary to set
	 */
	public void setSpnSummary(List<SPNSummaryVO> spnSummary) {
		this.spnSummary = spnSummary;
	}

	/**
	 * @return the marketList
	 */
	public List<LookupMarket> getMarketList() {
		return marketList;
	}

	/**
	 * @param marketList the marketList to set
	 */
	public void setMarketList(List<LookupMarket> marketList) {
		this.marketList = marketList;
	}

	/**
	 * @return the stateList
	 */
	public List<LookupStates> getStateList() {
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<LookupStates> stateList) {
		this.stateList = stateList;
	}

	/**
	 * @return the spnSummaryFilterVO
	 */
	public SPNSummaryFilterVO getSpnSummaryFilterVO() {
		return spnSummaryFilterVO;
	}

	/**
	 * @param spnSummaryFilterVO the spnSummaryFilterVO to set
	 */
	public void setSpnSummaryFilterVO(SPNSummaryFilterVO spnSummaryFilterVO) {
		this.spnSummaryFilterVO = spnSummaryFilterVO;
	}
}

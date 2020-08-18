/**
 * 
 */
package com.servicelive.spn.buyer.membermanagement;

import java.util.List;

import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.spn.detached.MemberManageSearchCriteriaVO;
import com.servicelive.domain.spn.detached.MemberManageSearchResultVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.core.SPNBaseModel;

/**
 * @author hoza
 *
 */
public class SPNMemberManageSearchModel extends SPNBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MemberManageSearchCriteriaVO searchCriteriaVO;
	private List<MemberManageSearchResultVO> searchResultsList;
	private List<SPNHeader> spnList;
	private List<LookupStates> stateList;
	private String userName;
	private Integer buyerId;
	
	/**
	 * @return the searchCriteriaVO
	 */
	public MemberManageSearchCriteriaVO getSearchCriteriaVO() {
		return searchCriteriaVO;
	}
	/**
	 * @param searchCriteriaVO the searchCriteriaVO to set
	 */
	public void setSearchCriteriaVO(MemberManageSearchCriteriaVO searchCriteriaVO) {
		this.searchCriteriaVO = searchCriteriaVO;
	}
	/**
	 * @return the searchResultsList
	 */
	public List<MemberManageSearchResultVO> getSearchResultsList() {
		return searchResultsList;
	}
	/**
	 * @param searchResultsList the searchResultsList to set
	 */
	public void setSearchResultsList(
			List<MemberManageSearchResultVO> searchResultsList) {
		this.searchResultsList = searchResultsList;
	}
	/**
	 * @return the spnList
	 */
	public List<SPNHeader> getSpnList() {
		return spnList;
	}
	/**
	 * @param spnList the spnList to set
	 */
	public void setSpnList(List<SPNHeader> spnList) {
		this.spnList = spnList;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

}

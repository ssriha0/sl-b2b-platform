package com.servicelive.spn.buyer.network;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.domain.lookup.LookupPerformanceLevel;
import com.servicelive.domain.spn.network.ReleaseTiersVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.detached.SPNRoutingDTO;
import com.servicelive.spn.core.SPNBaseModel;

public class SPNReleaseTierModel extends SPNBaseModel
{

	private static final long serialVersionUID = -3029486312585993640L;


	private List<LabelValueBean> spnList = new ArrayList<LabelValueBean>();
	private List<Integer> avPfCb = new ArrayList<Integer>();//available Per level check box
	private List<ReleaseTiersVO> tiersToSave = new ArrayList<ReleaseTiersVO>();
	private Integer spnId;
	private Boolean validatefail = Boolean.FALSE;
	private Boolean deleteAllFlag = Boolean.FALSE;
	private List<LookupPerformanceLevel> allPerfLevels = new ArrayList<LookupPerformanceLevel>();
	private SPNRoutingDTO routingDTO;
	 

	private Integer spnBeingEdited = Integer.valueOf(-1);

	private Integer marketplaceOverflow = Integer.valueOf(0);





	public List<LabelValueBean> getSpnList()
	{
		return spnList;
	}

	public void setSpnList(List<LabelValueBean> spnList)
	{
		this.spnList = spnList;
	}




	public Integer getMarketplaceOverflow() {
		return marketplaceOverflow;
	}

	public void setMarketplaceOverflow(Integer marketplaceOverflow) {
		this.marketplaceOverflow = marketplaceOverflow;
	}

	public Integer getSpnId() {
		return spnId;
	}

	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}


	

	/**
	 * @return the tiersToSave
	 */
	public List<ReleaseTiersVO> getTiersToSave() {
		return tiersToSave;
	}

	/**
	 * @param tiersToSave the tiersToSave to set
	 */
	public void setTiersToSave(List<ReleaseTiersVO> tiersToSave) {
		this.tiersToSave = tiersToSave;
	}

	/**
	 * @return the validatefail
	 */
	public Boolean getValidatefail() {
		return validatefail;
	}

	/**
	 * @param validatefail the validatefail to set
	 */
	public void setValidatefail(Boolean validatefail) {
		this.validatefail = validatefail;
	}

	/**
	 * @return the avPfCb
	 */
	public List<Integer> getAvPfCb() {
		return avPfCb;
	}

	/**
	 * @param avPfCb the avPfCb to set
	 */
	public void setAvPfCb(List<Integer> avPfCb) {
		this.avPfCb = avPfCb;
	}

	/**
	 * @return the allPerfLevels
	 */
	public List<LookupPerformanceLevel> getAllPerfLevels() {
		return allPerfLevels;
	}

	/**
	 * @param allPerfLevels the allPerfLevels to set
	 */
	public void setAllPerfLevels(List<LookupPerformanceLevel> allPerfLevels) {
		this.allPerfLevels = allPerfLevels;
	}

	public Integer getSpnBeingEdited() {
		return spnBeingEdited;
	}

	public void setSpnBeingEdited(Integer spnBeingEdited) {
		this.spnBeingEdited = spnBeingEdited;
	}

	/**
	 * @return the deleteAllFlag
	 */
	public Boolean getDeleteAllFlag() {
		return deleteAllFlag;
	}

	/**
	 * @param deleteAllFlag the deleteAllFlag to set
	 */
	public void setDeleteAllFlag(Boolean deleteAllFlag) {
		this.deleteAllFlag = deleteAllFlag;
	}

	public SPNRoutingDTO getRoutingDTO() {
		return routingDTO;
	}

	public void setRoutingDTO(SPNRoutingDTO routingDTO) {
		this.routingDTO = routingDTO;
	}




}

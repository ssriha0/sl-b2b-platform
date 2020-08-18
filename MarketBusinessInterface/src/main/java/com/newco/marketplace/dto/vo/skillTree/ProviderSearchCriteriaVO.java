package com.newco.marketplace.dto.vo.skillTree;

import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderSearchCriteriaVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7619809306017292856L;
	private ArrayList<Integer> skillNodeIds; 
	private long buyerZipCode;
	private int providerRating;
	
	public long getBuyerZipCode() {
		return buyerZipCode;
	}
	public void setBuyerZipCode(long buyerZipCode) {
		this.buyerZipCode = buyerZipCode;
	}
	public int getProviderRating() {
		return providerRating;
	}
	public void setProviderRating(int providerRating) {
		this.providerRating = providerRating;
	}
	public ArrayList<Integer> getSkillNodeIds() {
		return skillNodeIds;
	}
	public void setSkillNodeIds(ArrayList<Integer> skillNodeIds) {
		this.skillNodeIds = skillNodeIds;
	}
	/**
	 * @see com.sears.os.vo.request.ABaseServiceRequestVO#toString()
	 */
	@Override
	public String toString(){
		return "";
	}
}

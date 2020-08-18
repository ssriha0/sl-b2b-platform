package com.newco.marketplace.dto.request.providerSearch;

import java.util.ArrayList;

import com.sears.os.vo.request.ABaseServiceRequestVO;

/**
 * @author zizrale
 *
 */
public class ProviderSearchRequest extends ABaseServiceRequestVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2239229615685416373L;
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

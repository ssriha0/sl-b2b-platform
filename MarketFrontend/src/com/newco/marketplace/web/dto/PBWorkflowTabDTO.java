package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 01:13:44 $
 */
public class PBWorkflowTabDTO extends SerializedBaseDTO{
	private static final long serialVersionUID = -2457304350725253520L;

	private Integer filterId;
	private List<BuyerReferenceVO> refFields;
	private Integer buyerRefTypeId;
	private String buyerRefValue;
	
	public Integer getFilterId() {
		return filterId;
	}

	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	
	public List<BuyerReferenceVO> getRefFields() {
		return refFields;
	}
	
	public void setRefFields(List<BuyerReferenceVO> refFields) {
		this.refFields = refFields;
	}

	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}

	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

	public String getBuyerRefValue() {
		return buyerRefValue;
	}

	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}
}

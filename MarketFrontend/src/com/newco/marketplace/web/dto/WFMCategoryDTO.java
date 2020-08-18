package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WorkflowSetUp")
public class WFMCategoryDTO extends SerializedBaseDTO{ 
	
	@XStreamAlias("BuyerId")
	private Integer buyerId;
	private String category;
	private List<WFMQueueVO> wfmQueueVos;
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<WFMQueueVO> getWfmQueueVos() {
		return wfmQueueVos;
	}
	public void setWfmQueueVos(List<WFMQueueVO> wfmQueueVos) {
		this.wfmQueueVos = wfmQueueVos;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
}

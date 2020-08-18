package com.newco.marketplace.api.beans.so.soDetails.vo;

import java.util.List;

public class PartDetailVO {

	private Integer countofParts;

	private List<PartVO> part;

	public Integer getCountofParts() {
		return countofParts;
	}

	public void setCountofParts(Integer countofParts) {
		this.countofParts = countofParts;
	}

	public List<PartVO> getPart() {
		return part;
	}

	public void setPart(List<PartVO> part) {
		this.part = part;
	}
	
	
}

package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import com.newco.marketplace.dto.vo.DispatchLocationVO;

public class CoverageLocationVO extends DispatchLocationVO{
	
	private String id;
	private Integer coverageRadius;
	private Integer count;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCoverageRadius() {
		return coverageRadius;
	}

	public void setCoverageRadius(Integer coverageRadius) {
		this.coverageRadius = coverageRadius;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	

}

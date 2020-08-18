package com.newco.marketplace.vo.mobile;

import java.util.List;

public class SoResultsVO {
	private Integer totalSoCount;
	private List<ProviderSOSearchVO> soResultList;

	public Integer getTotalSoCount() {
		return totalSoCount;
	}

	public void setTotalSoCount(Integer totalSoCount) {
		this.totalSoCount = totalSoCount;
	}

	public List<ProviderSOSearchVO> getSoResultList() {
		return soResultList;
	}

	public void setSoResultList(List<ProviderSOSearchVO> soResultList) {
		this.soResultList = soResultList;
	}

}

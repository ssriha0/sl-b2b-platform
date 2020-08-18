package com.newco.marketplace.dto.response.providerSearch;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.sears.os.vo.response.ABaseServiceResponseVO;

/**
 * @author zizrale
 *
 */
public class ProviderSearchResponse extends ABaseServiceResponseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 686147893552251694L;
	private ArrayList<ProviderResultVO> providerResult = new ArrayList<ProviderResultVO>();

	public ArrayList<ProviderResultVO> getProviderResult() {
		return providerResult;
	}

	public void setProviderResult(ArrayList<ProviderResultVO> providerResult) {
		this.providerResult = providerResult;
	}

}

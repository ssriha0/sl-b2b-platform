package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.sears.os.vo.response.ABaseServiceResponseVO;

public class ServiceOrderSearchResponse extends ABaseServiceResponseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8453615099868542902L;

	private List<ServiceOrderSearchResultsVO> searchList;
	
	private boolean hasError = false;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		    .append("processId", getProcessId())
			.append("code", getCode())
			.append("subCode", getSubCode())
			.append("messages", getMessages())
			.toString();
	}

	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	public List<ServiceOrderSearchResultsVO> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<ServiceOrderSearchResultsVO> searchList) {
		this.searchList = searchList;
	}

	
	
}

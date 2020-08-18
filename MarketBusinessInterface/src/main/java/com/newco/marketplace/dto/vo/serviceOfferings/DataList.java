package com.newco.marketplace.dto.vo.serviceOfferings;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("DataList")
public class DataList {

	@XStreamImplicit(itemFieldName = "CodeDetails")
	private List<CodeDetails> CodeDetails;

	public List<CodeDetails> getCodeDetails() {
		return CodeDetails;
	}

	public void setCodeDetails(List<CodeDetails> codeDetails) {
		CodeDetails = codeDetails;
	}
}

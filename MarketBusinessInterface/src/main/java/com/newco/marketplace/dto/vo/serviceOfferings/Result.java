package com.newco.marketplace.dto.vo.serviceOfferings;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Result")
public class Result {
	
	@XStreamAsAttribute
    final String xmlns = "http://www.w3.org/2001/XMLSchema-instance";
	
	@XStreamAlias("DataList")
	private DataList dataList;
	public DataList getDataList() {
		return dataList;
	}
	public void setDataList(DataList dataList) {
		this.dataList = dataList;
	}
}

package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;
public class ProblemLookupVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4541373395716608707L;
	private int so_substatus_id = 0;
	private String descr = "";
	private int sort_order = 0;
	
	public int getSo_substatus_id() {
		return so_substatus_id;
	}


	public void setSo_substatus_id(int so_substatus_id) {
		this.so_substatus_id = so_substatus_id;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String desc) {
		this.descr = desc;
	}


	public int getSort_order() {
		return sort_order;
	}


	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}


	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("idSubStatusId", getSo_substatus_id())
			.append("sort_order", getSort_order())
			.append("desc", getDescr())
			.toString();
	}
}

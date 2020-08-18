package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;
public class ReasonLookupVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2746993687170410418L;
	private int id = 0;
	private String descr = "";
	private int sort_order = 0;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
			.append("id", getId())
			.append("sort_order", getSort_order())
			.append("desc", getDescr())
			.toString();
	}
}

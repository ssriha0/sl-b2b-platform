package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;


public class ServiceOrderStatusInput extends SerializableBaseVO{
	Integer statusIds[];

	public Integer[] getStatusIds() {
		return statusIds;
	}

	public void setStatusIds(Integer[] statusIds) {
		this.statusIds = statusIds;
	}
}

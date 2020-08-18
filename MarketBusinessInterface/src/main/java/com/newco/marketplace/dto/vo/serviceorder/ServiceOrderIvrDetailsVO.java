package com.newco.marketplace.dto.vo.serviceorder;


import java.util.List;

import com.sears.os.vo.SerializableBaseVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;


public class ServiceOrderIvrDetailsVO extends SerializableBaseVO{

	private static final long serialVersionUID = 0L;
	private String soId;
	private Integer buyerId;
	private List<ServiceOrderCustomRefVO> soCustRefs;
	private List<PhoneVO> phoneDetails;
	
	public List<ServiceOrderCustomRefVO> getSoCustRefs() {
		return soCustRefs;
	}
	public void setSoCustRefs(List<ServiceOrderCustomRefVO> soCustRefs) {
		this.soCustRefs = soCustRefs;
	}
	public List<PhoneVO> getPhoneDetails() {
		return phoneDetails;
	}
	public void setPhoneDetails(List<PhoneVO> phoneDetails) {
		this.phoneDetails = phoneDetails;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
}
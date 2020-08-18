package com.newco.marketplace.dto.vo.spn;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Rajitha Gurram
 *
 */
public class SPNProviderProfileVO extends SerializableBaseVO {

	private static final long serialVersionUID = 3986693785909543037L;
	
	private String spnName;
	private Integer status;
	private Date membershipDate;
	private Integer spnId;
	private Integer spnNetworkId;
	
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public Date getMembershipDate() {
		return membershipDate;
	}
	public void setMembershipDate(Date membershipDate) {
		this.membershipDate = membershipDate;
	}
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getSpnNetworkId() {
		return spnNetworkId;
	}
	public void setSpnNetworkId(Integer spnNetworkId) {
		this.spnNetworkId = spnNetworkId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	


}

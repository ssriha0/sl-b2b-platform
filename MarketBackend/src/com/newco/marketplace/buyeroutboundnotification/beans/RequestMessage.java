package com.newco.marketplace.buyeroutboundnotification.beans;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MESSAGE")
public class RequestMessage {
	
	@XStreamAlias("serviceOrderTxtDS")
	private String serviceOrderTxtDS;
	
	@XStreamAlias("serviceOrderTxtDSModDate")
	private String serviceOrderTxtDSModDate;
	
	@XStreamAlias("serviceOrderTxtDSModTime")
	private String serviceOrderTxtDSModTime;
	
	@XStreamAlias("serviceOrderTxtDSSeqNo")
	private String serviceOrderTxtDSSeqNo;
	
	@XStreamAlias("serviceOrderTxtDSEmpId")
	private String serviceOrderTxtDSEmpId;
	
	@XStreamAlias("serviceOrderTxtDSInqNo")
	private String serviceOrderTxtDSInqNo;
	
	@XStreamAlias("serviceOrderTxtDSCalBakDt")
	private String serviceOrderTxtDSCalBakDt;
	
	@XStreamAlias("serviceOrderTxtDSCalBakTm")
	private String serviceOrderTxtDSCalBakTm;
	
	@XStreamAlias("serviceOrderTxtDSInqTrgCMyCd")
	private String serviceOrderTxtDSInqTrgCMyCd;
	
	@XStreamAlias("serviceOrderTxtDSInqCrtCMyCd")
	private String serviceOrderTxtDSInqCrtCMyCd;
	
	public String getServiceOrderTxtDS() {
		return serviceOrderTxtDS;
	}

	public void setServiceOrderTxtDS(String serviceOrderTxtDS) {
		this.serviceOrderTxtDS = serviceOrderTxtDS;
	}

	public String getServiceOrderTxtDSModDate() {
		return serviceOrderTxtDSModDate;
	}

	public void setServiceOrderTxtDSModDate(String serviceOrderTxtDSModDate) {
		this.serviceOrderTxtDSModDate = serviceOrderTxtDSModDate;
	}

	public String getServiceOrderTxtDSModTime() {
		return serviceOrderTxtDSModTime;
	}

	public void setServiceOrderTxtDSModTime(String serviceOrderTxtDSModTime) {
		this.serviceOrderTxtDSModTime = serviceOrderTxtDSModTime;
	}

	public String getServiceOrderTxtDSSeqNo() {
		return serviceOrderTxtDSSeqNo;
	}

	public void setServiceOrderTxtDSSeqNo(String serviceOrderTxtDSSeqNo) {
		this.serviceOrderTxtDSSeqNo = serviceOrderTxtDSSeqNo;
	}

	public String getServiceOrderTxtDSEmpId() {
		return serviceOrderTxtDSEmpId;
	}

	public void setServiceOrderTxtDSEmpId(String serviceOrderTxtDSEmpId) {
		this.serviceOrderTxtDSEmpId = serviceOrderTxtDSEmpId;
	}

	public String getServiceOrderTxtDSInqNo() {
		return serviceOrderTxtDSInqNo;
	}

	public void setServiceOrderTxtDSInqNo(String serviceOrderTxtDSInqNo) {
		this.serviceOrderTxtDSInqNo = serviceOrderTxtDSInqNo;
	}

	public String getServiceOrderTxtDSCalBakDt() {
		return serviceOrderTxtDSCalBakDt;
	}

	public void setServiceOrderTxtDSCalBakDt(String serviceOrderTxtDSCalBakDt) {
		this.serviceOrderTxtDSCalBakDt = serviceOrderTxtDSCalBakDt;
	}

	public String getServiceOrderTxtDSCalBakTm() {
		return serviceOrderTxtDSCalBakTm;
	}

	public void setServiceOrderTxtDSCalBakTm(String serviceOrderTxtDSCalBakTm) {
		this.serviceOrderTxtDSCalBakTm = serviceOrderTxtDSCalBakTm;
	}

	public String getServiceOrderTxtDSInqTrgCMyCd() {
		return serviceOrderTxtDSInqTrgCMyCd;
	}

	public void setServiceOrderTxtDSInqTrgCMyCd(String serviceOrderTxtDSInqTrgCMyCd) {
		this.serviceOrderTxtDSInqTrgCMyCd = serviceOrderTxtDSInqTrgCMyCd;
	}

	public String getServiceOrderTxtDSInqCrtCMyCd() {
		return serviceOrderTxtDSInqCrtCMyCd;
	}

	public void setServiceOrderTxtDSInqCrtCMyCd(String serviceOrderTxtDSInqCrtCMyCd) {
		this.serviceOrderTxtDSInqCrtCMyCd = serviceOrderTxtDSInqCrtCMyCd;
	}
	
}

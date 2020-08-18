package com.newco.marketplace.vo.orderGroup;

import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderGroupMatchVO extends CommonVO {

	private static final long serialVersionUID = -8329974952133204392L;
	
	private String soId;
	private Integer buyerId;
	private String status;
	private Integer primarySkillCatId;
	private Integer wfSubStatusId;
	private String subStatusId;
	
	private Timestamp serviceDate1;
	private Timestamp serviceDate2;
	private String serviceTimeStart;
	private String serviceTimeEnd;
	private Integer serviceDateTypeId;
	
	private String streetAdd1;
	private String streetAdd2;
	private String zip;
	private String stateCd;
	private String city;
	
	private String locationType;
	
	private String parentGroupId;
	
	public String getParentGroupId() {
		return parentGroupId;
	}
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPrimarySkillCatId() {
		return primarySkillCatId;
	}
	public void setPrimarySkillCatId(Integer primarySkillCatId) {
		this.primarySkillCatId = primarySkillCatId;
	}
	public Integer getWfSubStatusId() {
		return wfSubStatusId;
	}
	public void setWfSubStatusId(Integer wfSubStatusId) {
		this.wfSubStatusId = wfSubStatusId;
	}
	public Timestamp getServiceDate1() {
		return serviceDate1;
	}
	public void setServiceDate1(Timestamp serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}
	public Timestamp getServiceDate2() {
		return serviceDate2;
	}
	public void setServiceDate2(Timestamp serviceDate2) {
		this.serviceDate2 = serviceDate2;
	}
	public String getServiceTimeStart() {
		return serviceTimeStart;
	}
	public void setServiceTimeStart(String serviceTimeStart) {
		this.serviceTimeStart = serviceTimeStart;
	}
	public String getServiceTimeEnd() {
		return serviceTimeEnd;
	}
	public void setServiceTimeEnd(String serviceTimeEnd) {
		this.serviceTimeEnd = serviceTimeEnd;
	}
	public Integer getServiceDateTypeId() {
		return serviceDateTypeId;
	}
	public void setServiceDateTypeId(Integer serviceDateTypeId) {
		this.serviceDateTypeId = serviceDateTypeId;
	}
	public String getStreetAdd1() {
		return streetAdd1;
	}
	public void setStreetAdd1(String streetAdd1) {
		this.streetAdd1 = streetAdd1;
	}
	public String getStreetAdd2() {
		return streetAdd2;
	}
	public void setStreetAdd2(String streetAdd2) {
		this.streetAdd2 = streetAdd2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStateCd() {
		return stateCd;
	}
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getSubStatusId() {
		return subStatusId;
	}
	public void setSubStatusId(String subStatusId) {
		this.subStatusId = subStatusId;
	}
	

}

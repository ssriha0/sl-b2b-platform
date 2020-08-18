package com.newco.marketplace.dto.vo.serviceorder;

import java.util.ArrayList;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrderTaskDetail extends CommonVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2187327548868391728L;
	private Integer taskId;
	private String skillNodeDs;
	private String serviceTypeDs;
	private String soId;  
    private Double finalPrice; 
	private Date createDate;  
	private Date modifiedDate; 
	private String modifiedBy;
	private ArrayList<PartDetail> partList;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public ArrayList<PartDetail> getPartList() {
		return partList;
	}
	public void setPartList(ArrayList<PartDetail> partList) {
		this.partList = partList;
	}
	public String getServiceTypeDs() {
		return serviceTypeDs;
	}
	public void setServiceTypeDs(String serviceTypeDs) {
		this.serviceTypeDs = serviceTypeDs;
	}
	public String getSkillNodeDs() {
		return skillNodeDs;
	}
	public void setSkillNodeDs(String skillNodeDs) {
		this.skillNodeDs = skillNodeDs;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
}

package com.servicelive.orderfulfillment.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "so_process_map") // so to process mapping table
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceOrderProcess {
	
	private static final long serialVersionUID = 12345678987564321L;
	
	@Id @Column(name = "so_id")
	private String soId;
	
	@Column(name = "buyer_id")
	private Long buyerId;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "created_date")
	private Date createdDate = new Date();
		
	@Column(name = "so_group_id", nullable = true)
	private String groupId;
	
	@Column(name = "group_process_id", nullable = true)
	private String groupProcessId;

	@Column(name = "new_so")
	private Boolean  newSo = Boolean.TRUE;

	@Column(name = "primary_skill_category_id")
	private Integer primarySkillCatId;

	@Column(name = "process_id")
	private String processId;
	
	@Column(name = "service_date1")
	private Date serviceDate1;

	@Column(name = "so_type")
	private String soType;

	@Column(name = "state_cd")
	private String state;

	@Column(name = "street_1")
	private String street1;

	@Column(name = "street_2")
	private String street2;

	@Column(name = "zip")
	private String zip;
	
	@Column(name = "is_finished")
	private Boolean finished = false;
	
	@Column(name = "is_updatable")
	private Boolean updatable = true;
	
	@Column(name = "is_grouping_searchable")
	private Boolean groupingSearchable = false;

    @Column(name = "is_car_order")
    private Boolean carOrder = false;
    
    @Column(name = "group_lock")
    private String groupLock;
	
    @Column(name = "jms_message_id")
    private String jmsMessageId;
    
	public String getJmsMessageId() {
		return jmsMessageId;
	}

	public void setJmsMessageId(String jmsMessageId) {
		this.jmsMessageId = jmsMessageId;
	}

	public ServiceOrderProcess(){}
	
	public ServiceOrderProcess(String soId, String processId, String soType)
	{
		this.soId=soId;
		this.processId=processId;
		this.soType=soType;
	}

	public void demoteSo() {
		this.newSo = Boolean.FALSE;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public String getCity() {
		return city;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getGroupLock() {
		return groupLock;
	}

	public String getGroupProcessId() {
		return groupProcessId;
	}

	public Integer getPrimarySkillCatId() {
		return primarySkillCatId;
	}

	public String getProcessId() {
		return processId;
	}

	public Date getServiceDate1() {
		return serviceDate1;
	}

	public String getSoId() {
		return soId;
	}

	public String getSoType() {
		return soType;
	}

	public String getState() {
		return state;
	}

	public String getStreet1() {
		return street1;
	}

	public String getStreet2() {
		if(null==street2){
			return "";
		}
		return street2;
	}

	public String getZip() {
		return zip;
	}

	public Boolean isCarOrder() {
        return carOrder;
    }

	public Boolean isFinished() {
		return finished;
	}
	
	public Boolean isUpdatable() {
		return updatable;
	}

	public Boolean isGroupingSearchable() {
		return groupingSearchable;
	}

	public Boolean isNewSo() {
		return newSo;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public void setCarOrder(Boolean carOrder) {
        this.carOrder = carOrder;
    }
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public void setGroupingSearchable(Boolean groupingSearchable) {
		this.groupingSearchable = groupingSearchable;
	}
	
	public void setGroupLock(String groupLock) {
		this.groupLock = groupLock;
	}
	
	public void setGroupProcessId(String groupProcessId) {
		this.groupProcessId = groupProcessId;
	}

	public void setNewSo(Boolean newSo) {
		this.newSo = newSo;
	}

	public void setPrimarySkillCatId(Integer primarySkillCatId) {
		this.primarySkillCatId = primarySkillCatId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setServiceDate1(Date serviceDate1) {
		this.serviceDate1 = serviceDate1;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public void setSoType(String soType) {
		this.soType = soType;
	}

    public void setState(String state) {
		this.state = state;
	}

    public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}

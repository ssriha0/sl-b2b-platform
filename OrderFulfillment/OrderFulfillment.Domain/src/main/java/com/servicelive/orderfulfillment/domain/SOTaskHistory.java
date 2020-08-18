package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "so_task_price_history")
@XmlRootElement()
public class SOTaskHistory extends SOElement{

private static final long serialVersionUID = 5242278822016834315L;

@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "so_task_history_id")
private Integer soTaskHistoryId;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name ="so_task_id")
	private SOTask task;

	@Column(name = "so_id")
	private String soId;
		
	@Column(name = "modified_by")
	private String modifiedBy;
	
	@Column(name = "modified_by_name")
	private String modifiedByName;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "sku")
	private String sku;
	
	@Column(name = "task_seq_num")
	private Integer taskSeqNum;
	
	public Integer getTaskSeqNum() {
		return taskSeqNum;
	}

	public void setTaskSeqNum(Integer taskSeqNum) {
		this.taskSeqNum = taskSeqNum;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
	/*public SOTask getTask() {
		return task;
	}
	@XmlTransient()
	public void setTask(SOTask task) {
		this.task = task;
	}*/

	public Integer getSoTaskHistoryId() {
		return soTaskHistoryId;
	}

	public void setSoTaskHistoryId(Integer soTaskHistoryId) {
		this.soTaskHistoryId = soTaskHistoryId;
	}

	public SOTask getTask() {
		return task;
	}

	@XmlTransient()
	public void setTask(SOTask task) {
		this.task = task;
	}

}

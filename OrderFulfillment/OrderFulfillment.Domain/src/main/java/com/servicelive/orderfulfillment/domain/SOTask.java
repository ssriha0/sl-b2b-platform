package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Formula;

@Entity
@Table(name = "so_tasks")
@XmlRootElement()
public class SOTask extends SOChild implements Comparable<SOTask> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3313785717727460939L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_task_id")
	private Integer taskId;
		
	@Column(name = "skill_node_id")
	private Integer skillNodeId;

	@Column(name = "service_type_template_id")
	private Integer serviceTypeId;

	@Column(name = "task_name")
	private String taskName;

	@Column(name = "task_comments")
	private String taskComments;
	
	@Column(name = "sequence_number")
	private Integer sequenceNumber;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "selling_price")
	private BigDecimal sellingPrice;
	
	@Column(name = "final_price")
	private BigDecimal finalPrice;
	
	@Column(name = "retail_price")
	private BigDecimal retailPrice ;
	

	@Column(name = "permit_type_id")
	private Integer permitType;
	
	@Column(name = "auto_injected_ind")
	private Integer autoInjectedInd;

	@Column(name = "sort_order")
	private Integer sortOrder;

    @Column(name = "primary_task")
    private boolean primaryTask;
    
   // @Column(name = "task_type")
   // private boolean permitTask;
    
    @Column(name = "sku")
    private String externalSku;    
    
    @Column(name = "task_seq_num")
	private Integer taskSeqNum;
    
    @Column(name = "task_status")
    private String taskStatus = "ACTIVE";    
    
    //Primary(0), Permit(1), Delivery(2), and Non-Primary(3).
    @Column(name = "task_type")
    private Integer taskType = 0;
    
    //Primary Key of Manage Scope table 
    @Column(name = "manage_scope_id", insertable=false,updatable=false)
    private Integer manageScopeId = null;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manage_scope_id")
    private SOManageScope soManageScope;
    
    @Column(name = "sku_id")
    private Integer skuId;    
    
    @Formula(value = "(SELECT s.price FROM so_task_price_history s WHERE s.so_task_id = so_task_id ORDER BY s.modified_date DESC LIMIT 1)")
	@Column(insertable=false, updatable=false)
	private BigDecimal latestPrice;
    
    @Column(name = "purchase_amount")
    private BigDecimal purchaseAmount;
	
	

	////////////////////////////////////////////////////////////////////////////
    //                       NON-PERSISTENT FIELDS
    ////////////////////////////////////////////////////////////////////////////    
    @Transient
    private String category;
    
    @Transient
    private String subCategory;
    
    @Transient
    private String serviceType;

    @Transient
    private String specialtyCode;

    @Transient
    private int taskNo;
    
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, mappedBy = "task")
	private List<SOTaskHistory> taskHistory = new ArrayList<SOTaskHistory>();
    
    @Transient
    boolean matched = false;

	public boolean isMatched() {
		return matched;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public int compareTo(SOTask o) {
		return new CompareToBuilder()
				.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
			 	.append(this.primaryTask, o.primaryTask).append(this.price, o.price)
			 	.append(this.taskName, o.taskName).append(this.taskComments, o.taskComments)
			 	.append(this.externalSku, o.externalSku).toComparison();
				//.append(this.permitTask, o.permitTask).append(this.sellingPrice, o.sellingPrice)
				//.append(this.finalPrice, o.finalPrice).append(this.sequenceNumber, o.sequenceNumber)
	}
	
	public int compareTask(SOTask o) {
		return new CompareToBuilder()
				.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
			 	.append(this.primaryTask, o.primaryTask)
			 	.append(this.externalSku, o.externalSku).toComparison();
	}
	public int compareTaskForAPI(SOTask o) {
		return new CompareToBuilder()
				.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
			 	.append(this.externalSku, o.externalSku).toComparison();
	}
	
	public int compareDeliveryTask(SOTask o) {
		return new CompareToBuilder()
				.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
			 	.append(this.primaryTask, o.primaryTask)
			 	.append(this.taskName, o.taskName).append(this.taskComments, o.taskComments)
			 	.append(this.sequenceNumber, o.sequenceNumber).append(this.externalSku, o.externalSku)
			 	.append(this.taskType, o.taskType).toComparison();
	}
	
	
	
	/**
    * Define equality of SOTask.
    */
    @Override 
    public boolean equals( Object aThat ) {
       if ( this == aThat ) return true;
       if ( !(aThat instanceof SOTask) ) return false;

       SOTask o = (SOTask)aThat;
       return new EqualsBuilder()
					.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
				 	.append(this.primaryTask, o.primaryTask).append(this.price, o.price)
				 	.append(this.taskName, o.taskName).append(this.taskComments, o.taskComments).append(this.sequenceNumber, o.sequenceNumber).append(this.externalSku, o.externalSku).isEquals();
				// 	.append(this.permitTask, o.permitTask).append(this.sellingPrice, o.sellingPrice)
				 	//.append(this.finalPrice, o.finalPrice).append(this.sequenceNumber, o.sequenceNumber)
    }

   
    public boolean compareFrontEndTask( Object aThat ) {
       if ( this == aThat ) return true;
       if ( !(aThat instanceof SOTask) ) return false;

       SOTask o = (SOTask)aThat;
       return new EqualsBuilder()
					.append(this.serviceTypeId, o.serviceTypeId).append(this.skillNodeId, o.skillNodeId)
				 	.append(this.primaryTask, o.primaryTask).append(this.price, o.price)
				 	.append(this.taskName, o.taskName).append(this.taskComments, o.taskComments).append(this.sequenceNumber, o.sequenceNumber).append(this.externalSku, o.externalSku).append(this.finalPrice, o.finalPrice).isEquals();
				// 	.append(this.permitTask, o.permitTask).append(this.sellingPrice, o.sellingPrice)
				 	//.append(this.finalPrice, o.finalPrice).append(this.sequenceNumber, o.sequenceNumber)
    }
    
    
    
    
	public Integer getTaskSeqNum() {
		return taskSeqNum;
	}

	public void setTaskSeqNum(Integer taskSeqNum) {
		this.taskSeqNum = taskSeqNum;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Integer getAutoInjectedInd() {
		return autoInjectedInd;
	}

	public String getCategory() {
		return category;
	}

	public String getExternalSku() {
        return externalSku;
    }

	public BigDecimal getPrice() {
		return price;
	}

	public String getServiceType() {
		return serviceType;
	}

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public Integer getSkillNodeId() {
		return skillNodeId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public String getSpecialtyCode() {
        return specialtyCode;
    }

	public String getSubCategory() {
		return subCategory;
	}

	public String getTaskComments() {
		return taskComments;
	}

	public Integer getTaskId() {
		return taskId;
	}

    public String getTaskName() {
		return taskName;
	}
    
   
    /**
     * A class that overrides equals must also override hashCode.
     */
     @Override 
     public int hashCode() {
    	 HashCodeBuilder hcb = new HashCodeBuilder();
    	 hcb.append(this.serviceTypeId).append(this.skillNodeId)
    	 	.append(this.primaryTask).append(this.price)
    	 	.append(this.taskName).append(this.sequenceNumber).append(this.externalSku).append(this.taskComments);
    	 	//.append(this.permitTask).append(this.sellingPrice)
    	 	//.append(this.finalPrice).append(this.sequenceNumber);
    	 return hcb.toHashCode();
     }

    public boolean isPrimaryTask() {
        return primaryTask;
    }

    public void setAutoInjectedInd(Integer autoInjectedInd) {
		this.autoInjectedInd = autoInjectedInd;
	}

    @XmlElement
	public void setCategory(String category) {
		this.category = category;
	}

    public void setExternalSku(String externalSku) {
        this.externalSku = externalSku;
    }

    public void setPrice(BigDecimal price) {
		this.price = price;
	}

    public void setPrimaryTask(boolean primaryTask) {
        this.primaryTask = primaryTask;
    }

    @XmlElement
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@XmlElement()
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

    @XmlElement()
	public void setSkillNodeId(Integer skillNodeId) {
		this.skillNodeId = skillNodeId;
	}

	@XmlElement()
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

    @XmlElement()
    public void setSpecialtyCode(String specialtyCode) {
        this.specialtyCode = specialtyCode;
    }

	@XmlElement
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
    @XmlElement()
	public void setTaskComments(String taskComments) {
		this.taskComments = taskComments == null ? "" : taskComments;
	}

     @XmlElement()
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

     @XmlElement()
	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? "" : taskName;
	}

     @Override
     public String toString(){
 		return ToStringBuilder.reflectionToString(this);
     }

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public List<SOTaskHistory> getTaskHistory() {
		
		return taskHistory;
	}

	
	public void setTaskHistory(List<SOTaskHistory> taskHistory) {
		/*for(SOTaskHistory scl : taskHistory){
			scl.setTask(this);
		}*/
		this.taskHistory = taskHistory;
	
	}

	public void setPermitType(Integer permitType) {
		this.permitType = permitType;
	}
	

	public Integer getPermitType() {
		return permitType;
	}
	/*
	public boolean isPermitTask() {
		return permitTask;
	}

	public void setPermitTask(boolean permitTask) {
		this.permitTask = permitTask;
	}*/

	public Integer getManageScopeId() {
		return manageScopeId;
	}

	public void setManageScopeId(Integer manageScopeId) {
		this.manageScopeId = manageScopeId;
	}

	public SOManageScope getSoManageScope() {
		return soManageScope;
	}

	public void setSoManageScope(SOManageScope soManageScope) {
		this.soManageScope = soManageScope;
	}

	public BigDecimal getLatestPrice() {
		return latestPrice;
	}

	public void setLatestPrice(BigDecimal latestPrice) {
		this.latestPrice = latestPrice;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	

	
	
	

}

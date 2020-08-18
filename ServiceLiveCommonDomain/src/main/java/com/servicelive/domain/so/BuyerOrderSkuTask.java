package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.servicelive.domain.lookup.LookupServiceType;

import java.io.Serializable;

@Entity
@Table(name = "buyer_sku_task")
public class BuyerOrderSkuTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sku_task_id")
    private Long skuTaskId;

    @Column(name = "category_node_id", nullable=false)
    private Long skillNodeId;

    @Column(name = "service_type_template_id", nullable=false)
    private Long serviceTypeTemplateId;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_type_template_id", updatable=false, insertable=false)
	private LookupServiceType luServiceTypeTemplate;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_comments")
    private String taskComments;

    @ManyToOne
    @JoinColumn(name="sku_id", nullable=false)
    private BuyerOrderSku buyerSku;

    public Long getSkuTaskId() {
        return skuTaskId;
    }

    public void setSkuTaskId(Long skuTaskId) {
        this.skuTaskId = skuTaskId;
    }

    public Long getSkillNodeId() {
        return skillNodeId;
    }

    public void setSkillNodeId(Long skillNodeId) {
        this.skillNodeId = skillNodeId;
    }

    public Long getServiceTypeTemplateId() {
        return serviceTypeTemplateId;
    }

    public void setServiceTypeTemplateId(Long serviceTypeTemplateId) {
        this.serviceTypeTemplateId = serviceTypeTemplateId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(String taskComments) {
        this.taskComments = taskComments;
    }

    public BuyerOrderSku getBuyerSku() {
        return buyerSku;
    }

    public void setBuyerSku(BuyerOrderSku buyerSku) {
        this.buyerSku = buyerSku;
    }

	public LookupServiceType getLuServiceTypeTemplate() {
		return luServiceTypeTemplate;
	}

	public void setLuServiceTypeTemplate(LookupServiceType luServiceTypeTemplate) {
		this.luServiceTypeTemplate = luServiceTypeTemplate;
	}
}

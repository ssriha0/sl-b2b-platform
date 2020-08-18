package com.servicelive.domain.sku.maintenance;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * SkuCategoryHistoy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "sku_history",  uniqueConstraints = {})
public class SkuHistory implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BuyerSKUEntity buyerSku;
	private Integer skuHistoryId;
	//private Integer skuId;
	private String skuName;
	private String modifiedBy;
	private String roleId;
	private String action;
	private String actionDetails;
	@Transient
	private  List<String> actionDetailsList;
	//private String modifiedOnCstToStr;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sku_history_id", unique = true, nullable = false)	
	public Integer getSkuHistoryId() {
		return skuHistoryId;
	}
	public void setSkuHistoryId(Integer skuHistoryId) {
		this.skuHistoryId = skuHistoryId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name = "sku_id")
	public BuyerSKUEntity getBuyerSku() {
		return buyerSku;
	}
	public void setBuyerSku(BuyerSKUEntity buyerSku) {
		this.buyerSku = buyerSku;
	}
	
	@Column(name = "sku_name", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true)
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
		
	@Column(name = "action", unique = false, nullable = true, insertable = true, updatable = true)
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	@Column(name = "modified_on", nullable = false, length = 19)
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	
	@Column(name = "role_id", nullable = false, length = 19)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "action_details")
	public String getActionDetails() {
		return actionDetails;
	}
	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}
//	public String getModifiedOnCstToStr() {
//		return modifiedOnCstToStr;
//	}
//	public void setModifiedOnCstToStr(String modifiedOnCstToStr) {
//		this.modifiedOnCstToStr = modifiedOnCstToStr;
//	}
	@Transient
	public List<String> getActionDetailsList() {
		return actionDetailsList;
	}
	public void setActionDetailsList(List<String> actionDetailsList) {
		this.actionDetailsList = actionDetailsList;
	}

}
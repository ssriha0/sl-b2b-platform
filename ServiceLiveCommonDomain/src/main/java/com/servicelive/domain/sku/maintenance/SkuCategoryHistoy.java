package com.servicelive.domain.sku.maintenance;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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


/**
 * SkuCategoryHistoy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "sku_category_history",  uniqueConstraints = {})
public class SkuCategoryHistoy implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BuyerSkuCategory buyerSkuCategory;
	private Integer skuCategoryHistoryId;
	private String skuCategoryName;
	private String modifiedBy;
	private String roleId;
	private String action;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "modified_on")
	private Date modifiedOn;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sku_category_history_id", unique = true, nullable = false)	
	public Integer getSkuCategoryHistoryId() {
		return skuCategoryHistoryId;
	}
	public void setSkuCategoryHistoryId(Integer skuCategoryHistoryId) {
		this.skuCategoryHistoryId = skuCategoryHistoryId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn (name = "sku_category_id")
	public BuyerSkuCategory getBuyerSkuCategory() {
		return buyerSkuCategory;
	}
	public void setBuyerSkuCategory(BuyerSkuCategory buyerSkuCategory) {
		this.buyerSkuCategory = buyerSkuCategory;
	}
	
	@Column(name = "sku_category_name", unique = false, nullable = true, insertable = true, updatable = true)
	public String getSkuCategoryName() {
		return skuCategoryName;
	}
	public void setSkuCategoryName(String skuCategoryName) {
		this.skuCategoryName = skuCategoryName;
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
	
}

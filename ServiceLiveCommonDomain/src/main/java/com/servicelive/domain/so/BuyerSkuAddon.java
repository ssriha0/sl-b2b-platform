package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="buyer_sku_addons")
public class BuyerSkuAddon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="buyer_id", nullable=false)
	private Long buyerId;
	
	@Column(name="sku_cat_id")
	private Long skuCategoryId;
	
	@Column(name="sku")
	private String sku;
	
	@Column(name="group_no")
	private String groupNo; 
	
	@Column(name="type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSkuCategoryId() {
		return skuCategoryId;
	}

	public void setSkuCategoryId(Long skuCategoryId) {
		this.skuCategoryId = skuCategoryId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}


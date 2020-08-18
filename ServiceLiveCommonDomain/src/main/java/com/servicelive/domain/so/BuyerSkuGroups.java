package com.servicelive.domain.so;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.servicelive.domain.so.type.SkuGroupType;

@Entity
@Table(name = "buyer_sku_groups")
public class BuyerSkuGroups {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	 @Column(name="sku_id")
	 private Long skuId;
	
	@Column(name="group_type", nullable=false)
	private Integer group_type; 
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Integer getGroup_type() {
		return group_type;
	}

	public void setGroup_type(Integer group_type) {
		this.group_type = group_type;
	}

	

	
}



package com.newco.marketplace.translator.dao;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BuyerSkuAttributesId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class BuyerSkuAttributesId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8559101068270638014L;
	private Integer skuId;
	private String attributeType;

	// Constructors

	/** default constructor */
	public BuyerSkuAttributesId() {
	}

	/** full constructor */
	public BuyerSkuAttributesId(Integer skuId, String attributeType) {
		this.skuId = skuId;
		this.attributeType = attributeType;
	}

	// Property accessors

	@Column(name = "sku_id", nullable = false)
	public Integer getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	@Column(name = "attribute_type", nullable = false, length = 8)
	public String getAttributeType() {
		return this.attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BuyerSkuAttributesId))
			return false;
		BuyerSkuAttributesId castOther = (BuyerSkuAttributesId) other;

		return ((this.getSkuId() == castOther.getSkuId()) || (this.getSkuId() != null
				&& castOther.getSkuId() != null && this.getSkuId().equals(
				castOther.getSkuId())))
				&& ((this.getAttributeType() == castOther.getAttributeType()) || (this
						.getAttributeType() != null
						&& castOther.getAttributeType() != null && this
						.getAttributeType()
						.equals(castOther.getAttributeType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSkuId() == null ? 0 : this.getSkuId().hashCode());
		result = 37
				* result
				+ (getAttributeType() == null ? 0 : this.getAttributeType()
						.hashCode());
		return result;
	}

}
package com.newco.marketplace.translator.dao;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractBuyerSkuAttributes entity provides the base persistence definition of
 * the BuyerSkuAttributes entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractBuyerSkuAttributes implements
		java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -1624929515751743184L;
	private BuyerSkuAttributesId id;
	private BuyerSku buyerSku;
	private Timestamp createdDate;

	// Constructors

	/** default constructor */
	public AbstractBuyerSkuAttributes() {
	}

	/** full constructor */
	public AbstractBuyerSkuAttributes(BuyerSkuAttributesId id,
			BuyerSku buyerSku, Timestamp createdDate) {
		this.id = id;
		this.buyerSku = buyerSku;
		this.createdDate = createdDate;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "skuId", column = @Column(name = "sku_id", nullable = false)),
			@AttributeOverride(name = "attributeType", column = @Column(name = "attribute_type", nullable = false, length = 8)) })
	public BuyerSkuAttributesId getId() {
		return this.id;
	}

	public void setId(BuyerSkuAttributesId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sku_id", nullable = false, insertable = false, updatable = false)
	public BuyerSku getBuyerSku() {
		return this.buyerSku;
	}

	public void setBuyerSku(BuyerSku buyerSku) {
		this.buyerSku = buyerSku;
	}

	@Column(name = "created_date", nullable = false, length = 19)
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}
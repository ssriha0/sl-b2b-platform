package com.servicelive.domain.so;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.servicelive.domain.buyer.Buyer;

/**
 * BuyerReferenceType entity.
 * 
 */
@Entity
@Table(name = "buyer_reference_type",  uniqueConstraints = {})
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class BuyerReferenceType implements java.io.Serializable {

	private static final long serialVersionUID = 20090823L;

	/**
	 * 
	 */
	public BuyerReferenceType() {
		super();
	}

	@Id
	@Column(name = "buyer_ref_type_id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer buyerRefTypeId;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id", unique = false, nullable = false, insertable = true, updatable = true)
	private Buyer buyer;

	@Column(name = "ref_type", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	private String refType;

	@Column(name = "ref_descr", unique = false, nullable = true, insertable = true, updatable = true)
	private String refDescr;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	private Date modifiedDate;

	@Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	private String modifiedBy;

	@Column(name = "sort_order", unique = false, nullable = true, insertable = true, updatable = true)
	private Integer sortOrder;

	@Column(name = "active_ind", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean activeInd;

	@Column(name = "buyer_input", unique = false, nullable = true, insertable = true, updatable = true)
	private Boolean buyerInput;

    @Column(name = "so_identifier")
    private Boolean soIdentifier;

	/**
	 * @return the buyerRefTypeId
	 */
	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}

	/**
	 * @param buyerRefTypeId the buyerRefTypeId to set
	 */
	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

	/**
	 * @return the buyer
	 */
	public Buyer getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	/**
	 * @return the refType
	 */
	public String getRefType() {
		return refType;
	}

	/**
	 * @param refType the refType to set
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}

	/**
	 * @return the refDescr
	 */
	public String getRefDescr() {
		return refDescr;
	}

	/**
	 * @param refDescr the refDescr to set
	 */
	public void setRefDescr(String refDescr) {
		this.refDescr = refDescr;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the activeInd
	 */
	public Boolean getActiveInd() {
		return activeInd;
	}

	/**
	 * @return the buyerInput
	 */
	public Boolean getBuyerInput()
	{
		return buyerInput;
	}

	/**
	 * @param buyerInput the buyerInput to set
	 */
	public void setBuyerInput(Boolean buyerInput)
	{
		this.buyerInput = buyerInput;
	}

	/**
	 * @param activeInd the activeInd to set
	 */
	public void setActiveInd(Boolean activeInd) {
		this.activeInd = activeInd;
	}

    public Boolean getSoIdentifier() {
        return soIdentifier;
    }

    public void setSoIdentifier(Boolean soIdentifier) {
        this.soIdentifier = soIdentifier;
    }
}

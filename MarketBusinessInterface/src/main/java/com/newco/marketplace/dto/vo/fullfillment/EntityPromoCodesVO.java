/**
 * 
 */
package com.newco.marketplace.dto.vo.fullfillment;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class EntityPromoCodesVO  extends SerializableBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6219453056709509923L;
	private Long entityId;
	private Integer entityTypeId;
	private Integer v1PromoCodeId;
	private String v1PromoCode;
	private Integer v2PromoCodeId;
	private String v2PromoCode;
	private Timestamp createdDate;
	private Timestamp modifiedDate;
	private String modifiedBy;
	
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public Integer getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Integer entityTypeId) {
		this.entityTypeId = entityTypeId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getV1PromoCode() {
		return v1PromoCode;
	}
	public void setV1PromoCode(String promoCode) {
		v1PromoCode = promoCode;
	}
	public Integer getV1PromoCodeId() {
		return v1PromoCodeId;
	}
	public void setV1PromoCodeId(Integer promoCodeId) {
		v1PromoCodeId = promoCodeId;
	}
	public String getV2PromoCode() {
		return v2PromoCode;
	}
	public void setV2PromoCode(String promoCode) {
		v2PromoCode = promoCode;
	}
	public Integer getV2PromoCodeId() {
		return v2PromoCodeId;
	}
	public void setV2PromoCodeId(Integer promoCodeId) {
		v2PromoCodeId = promoCodeId;
	}
	
	
}

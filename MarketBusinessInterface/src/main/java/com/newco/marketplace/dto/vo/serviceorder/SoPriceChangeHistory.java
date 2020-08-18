package com.newco.marketplace.dto.vo.serviceorder;

import java.math.BigDecimal;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;
/**
 * SL-18007 VO added for mapping SO Price Change History
 */
public class SoPriceChangeHistory extends CommonVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String soId;
	private BigDecimal soLabourPrice;
	private BigDecimal soMaterialsPrice;
	private BigDecimal soPermitPrice;
	private BigDecimal soAddonPrice;
	private BigDecimal soPartsInvoicePrice;
	private BigDecimal soTotalPrice;
	private String action;
	private String reasonComment;
	private Date createdDate;
	private Date modifiedDate;
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	private String modifiedBy;
	private String modifiedByName;
	
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public BigDecimal getSoLabourPrice() {
		return soLabourPrice;
	}
	public void setSoLabourPrice(BigDecimal soLabourPrice) {
		this.soLabourPrice = soLabourPrice;
	}
	public BigDecimal getSoMaterialsPrice() {
		return soMaterialsPrice;
	}
	public void setSoMaterialsPrice(BigDecimal soMaterialsPrice) {
		this.soMaterialsPrice = soMaterialsPrice;
	}
	public BigDecimal getSoPermitPrice() {
		return soPermitPrice;
	}
	public void setSoPermitPrice(BigDecimal soPermitPrice) {
		this.soPermitPrice = soPermitPrice;
	}
	public BigDecimal getSoAddonPrice() {
		return soAddonPrice;
	}
	public void setSoAddonPrice(BigDecimal soAddonPrice) {
		this.soAddonPrice = soAddonPrice;
	}
	public BigDecimal getSoPartsInvoicePrice() {
		return soPartsInvoicePrice;
	}
	public void setSoPartsInvoicePrice(BigDecimal soPartsInvoicePrice) {
		this.soPartsInvoicePrice = soPartsInvoicePrice;
	}
	public BigDecimal getSoTotalPrice() {
		return soTotalPrice;
	}
	public void setSoTotalPrice(BigDecimal soTotalPrice) {
		this.soTotalPrice = soTotalPrice;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedByName() {
		return modifiedByName;
	}
	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}
	public String getReasonComment() {
		return reasonComment;
	}
	public void setReasonComment(String reasonComment) {
		this.reasonComment = reasonComment;
	}
}

package com.servicelive.orderfulfillment.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Entity class to map so_price_change_history table, 
 *  introduced as part of JIRA 18007 to hold the so level price change history.
 */

@Entity
@Table(name = "so_price_change_history")
@XmlRootElement()
public class SOPriceChangeHistory extends SOChild{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7502154086166574943L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_price_change_history_id")
	private Integer soPriceChangeHistoryId;
	
	@Column(name = "so_labour_price")
	private BigDecimal soLabourPrice;
	
	@Column(name = "so_materials_price")
	private BigDecimal soMaterialsPrice;
	
	@Column(name = "so_permit_price")
	private BigDecimal soPermitPrice;
	
	@Column(name = "so_addon_price")
	private BigDecimal soAddonPrice;
	
	@Column(name = "so_parts_invoice_price")
	private BigDecimal soPartsInvoicePrice;
	
	@Column(name = "so_total_price")
	private BigDecimal soTotalPrice;
	
	@Column(name = "action")
	private String action;
	
	/*@Column(name = "reason_code_id")
	private String reasonCodeId;
	
	@Column(name = "reason_role_id")
	private Integer reasonRoleId;*/
	
	@Column(name = "reason_comment")
	private String reasonComment;
	
	@Column(name = "modified_by_name")
	private String modifiedByName;
	
	public Integer getSoPriceChangeHistoryId() {
		return soPriceChangeHistoryId;
	}

	public void setSoPriceChangeHistoryId(Integer soPriceChangeHistoryId) {
		this.soPriceChangeHistoryId = soPriceChangeHistoryId;
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

	/*public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}*/

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

	/*public Integer getReasonRoleId() {
		return reasonRoleId;
	}

	public void setReasonRoleId(Integer reasonRoleId) {
		this.reasonRoleId = reasonRoleId;
	}*/

			
}

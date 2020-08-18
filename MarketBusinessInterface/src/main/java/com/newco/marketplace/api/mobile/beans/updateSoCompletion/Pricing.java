package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing pricing information.
 * @author Infosys
 *
 */
@XStreamAlias("pricing")
public class Pricing {
	
	@XStreamAlias("labour")
	private String labour;
	
	@XStreamAlias("materials")
	private String materials;
	
	@XStreamAlias("originalLabour")
	private String originalLabour;
	
	@XStreamAlias("originalMaterials")
	private String originalMaterials;
	
	@XStreamAlias("labourChangeReasonCodeId")
	private Integer labourChangeReasonCodeId;
	
	@XStreamAlias("labourChangeReasonComments")
	private String labourChangeReasonComments;

	
	@XStreamAlias("partChangeReasonCodeId")
	private Integer partChangeReasonCodeId;
	
	@XStreamAlias("partChangeReasonComments")
	private String partChangeReasonComments;
	
	@XStreamOmitField
	private String soId;
	
	@OptionalParam
	@XStreamAlias("partsTax")
	private String partsTax;
	
	@OptionalParam
	@XStreamAlias("partsDiscount")
	private String partsDiscount;
	
	@OptionalParam
	@XStreamAlias("laborTax")
	private String laborTax;
	
	@OptionalParam
	@XStreamAlias("laborDiscount")
	private String laborDiscount;
	
	public String getLabour() {
		return labour;
	}

	public void setLabour(String labour) {
		this.labour = labour;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getOriginalLabour() {
		return originalLabour;
	}

	public void setOriginalLabour(String originalLabour) {
		this.originalLabour = originalLabour;
	}

	public String getOriginalMaterials() {
		return originalMaterials;
	}

	public void setOriginalMaterials(String originalMaterials) {
		this.originalMaterials = originalMaterials;
	}

	
	public Integer getLabourChangeReasonCodeId() {
		return labourChangeReasonCodeId;
	}

	public void setLabourChangeReasonCodeId(Integer labourChangeReasonCodeId) {
		this.labourChangeReasonCodeId = labourChangeReasonCodeId;
	}

	public String getLabourChangeReasonComments() {
		return labourChangeReasonComments;
	}

	public void setLabourChangeReasonComments(String labourChangeReasonComments) {
		this.labourChangeReasonComments = labourChangeReasonComments;
	}

	public Integer getPartChangeReasonCodeId() {
		return partChangeReasonCodeId;
	}

	public void setPartChangeReasonCodeId(Integer partChangeReasonCodeId) {
		this.partChangeReasonCodeId = partChangeReasonCodeId;
	}

	public String getPartChangeReasonComments() {
		return partChangeReasonComments;
	}

	public void setPartChangeReasonComments(String partChangeReasonComments) {
		this.partChangeReasonComments = partChangeReasonComments;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getPartsTax() {
		return partsTax;
	}

	public void setPartsTax(String partsTax) {
		this.partsTax = partsTax;
	}

	public String getPartsDiscount() {
		return partsDiscount;
	}

	public void setPartsDiscount(String partsDiscount) {
		this.partsDiscount = partsDiscount;
	}

	public String getLaborTax() {
		return laborTax;
	}

	public void setLaborTax(String laborTax) {
		this.laborTax = laborTax;
	}

	public String getLaborDiscount() {
		return laborDiscount;
	}

	public void setLaborDiscount(String laborDiscount) {
		this.laborDiscount = laborDiscount;
	}

	

	
	
}

package com.newco.marketplace.api.beans.so.cancel.v1_2;


import com.newco.marketplace.api.beans.common.UserIdentificationRequest;
import com.newco.marketplace.api.beans.so.cancel.v1_2.CancelSKU;
import com.newco.marketplace.api.common.IAPIRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * This is a bean class for storing request information for 
 * the SOCancelService v1_2 added as part of SL-18041.
 * @author Infosys
 *
 */
@XStreamAlias("soCancelRequest")

public class SOCancelRequest extends UserIdentificationRequest implements IAPIRequest{

//	@XStreamAlias("identification")
//	private IdentificationType identification;
	
	@XStreamAlias("cancelReason")
	private String cancelReasonCode;
	
	private String cancelComment;
	private Double cancelAmount;
	private String providerPaymentReqd;
	
	@XStreamAlias("providerPaymentAcknowledgement")
	private String providerPaymentAck;
	
	@XStreamAlias("cancelSkus")
	private CancelSKU cancelSkus;

	

	
 
	@XStreamAlias("xsi:noNamespaceSchemaLocation")
	@XStreamAsAttribute()
	private String schemaLocation;

	@XStreamAlias("xmlns")
	@XStreamAsAttribute()
	private String namespace;

	@XStreamAlias("xmlns:xsi")
	@XStreamAsAttribute()
	private String schemaInstance;
	public String getCancelReasonCode() {
		return cancelReasonCode;
	}


	public void setCancelReasonCode(String cancelReasonCode) {
		this.cancelReasonCode = cancelReasonCode;
	}


	public String getCancelComment() {
		return cancelComment;
	}


	public void setCancelComment(String cancelComment) {
		this.cancelComment = cancelComment;
	}


	public Double getCancelAmount() {
		return cancelAmount;
	}


	public void setCancelAmount(Double cancelAmount) {
		this.cancelAmount = cancelAmount;
	}


	public String getProviderPaymentReqd() {
		return providerPaymentReqd;
	}


	public void setProviderPaymentReqd(String providerPaymentReqd) {
		this.providerPaymentReqd = providerPaymentReqd;
	}


	public String getProviderPaymentAck() {
		return providerPaymentAck;
	}


	public void setProviderPaymentAck(String providerPaymentAck) {
		this.providerPaymentAck = providerPaymentAck;
	}


	

	public void setVersion(String version) {
		
	}
	
	
	public CancelSKU getCancelSkus() {
		return cancelSkus;
	}


	public void setCancelSkus(CancelSKU cancelSkus) {
		this.cancelSkus = cancelSkus;
	}


	/**
	 * @return the schemaLocation
	 */
	public String getSchemaLocation() {
		return schemaLocation;
	}


	/**
	 * @param schemaLocation the schemaLocation to set
	 */
	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}


	/**
	 * @return the namespace
	 */
	public String getNamespace() {
		return namespace;
	}


	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	/**
	 * @return the schemaInstance
	 */
	public String getSchemaInstance() {
		return schemaInstance;
	}


	/**
	 * @param schemaInstance the schemaInstance to set
	 */
	public void setSchemaInstance(String schemaInstance) {
		this.schemaInstance = schemaInstance;
	}
	
	
}


package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.fullfillment.FullfillmentEntryVO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;

public class FulfillmentAdminDTO {

	private String fulfillmentEntryId;
	private Double amount;
	private Boolean groupId;
	private String adjustComments;
	private String copyComments;
	private String resendComments;
	private String glDate;
	private String nachaDate;
	private FullfillmentEntryVO fulfillmentEntryVO;
	private String glRunMessage;
	private String nachaRunMessage;
	private String groupResendMessage;
	private String adjustFulfillmentMessage;
	private String createFulfillmentMessage;
	private String lookUpMessage;
	private List<ServiceOrder> pendingServiceOrders;
	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getBalanceInquiryRunMessage() {
		return balanceInquiryRunMessage;
	}

	public void setBalanceInquiryRunMessage(String balanceInquiryRunMessage) {
		this.balanceInquiryRunMessage = balanceInquiryRunMessage;
	}

	private String vendorId;
	private String balanceInquiryRunMessage;
	
	
	private ArrayList<ValueLinkEntryVO> valueLinkEntryVOs;

	public String getFulfillmentEntryId() {
		return fulfillmentEntryId;
	}

	public void setFulfillmentEntryId(String fulfillmentEntryId) {
		this.fulfillmentEntryId = fulfillmentEntryId;
	}

	public FullfillmentEntryVO getFulfillmentEntryVO() {
		return fulfillmentEntryVO;
	}

	public void setFulfillmentEntryVO(FullfillmentEntryVO fulfillmentEntryVO) {
		this.fulfillmentEntryVO = fulfillmentEntryVO;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public ArrayList<ValueLinkEntryVO> getValueLinkEntryVOs() {
		return valueLinkEntryVOs;
	}

	public void setValueLinkEntryVOs(ArrayList<ValueLinkEntryVO> vos) {
		this.valueLinkEntryVOs = vos;
	}

	public Boolean getGroupId() {
		return groupId;
	}

	public void setGroupId(Boolean groupId) {
		this.groupId = groupId;
	}

	
	public String getAdjustComments() {
		return adjustComments;
	}

	public void setAdjustComments(String adjustComments) {
		this.adjustComments = adjustComments;
	}

	public String getCopyComments() {
		return copyComments;
	}

	public void setCopyComments(String copyComments) {
		this.copyComments = copyComments;
	}

	public String getResendComments() {
		return resendComments;
	}

	public void setResendComments(String resendComments) {
		this.resendComments = resendComments;
	}

	public String getGlDate() {
		return glDate;
	}

	public void setGlDate(String glDate) {
		this.glDate = glDate;
	}

	public String getGlRunMessage() {
		return glRunMessage;
	}

	public void setGlRunMessage(String glRunMessage) {
		this.glRunMessage = glRunMessage;
	}

	public String getNachaRunMessage() {
		return nachaRunMessage;
	}

	public void setNachaRunMessage(String nachaRunMessage) {
		this.nachaRunMessage = nachaRunMessage;
	}

	public String getNachaDate() {
		return nachaDate;
	}

	public void setNachaDate(String nachaDate) {
		this.nachaDate = nachaDate;
	}

	public String getGroupResendMessage() {
		return groupResendMessage;
	}

	public void setGroupResendMessage(String groupResendMessage) {
		this.groupResendMessage = groupResendMessage;
	}

	public String getAdjustFulfillmentMessage() {
		return adjustFulfillmentMessage;
	}

	public void setAdjustFulfillmentMessage(String adjustFulfillmentMessage) {
		this.adjustFulfillmentMessage = adjustFulfillmentMessage;
	}

	public String getCreateFulfillmentMessage() {
		return createFulfillmentMessage;
	}

	public void setCreateFulfillmentMessage(String createFulfillmentMessage) {
		this.createFulfillmentMessage = createFulfillmentMessage;
	}

	public String getLookUpMessage() {
		return lookUpMessage;
	}

	public void setLookUpMessage(String lookUpMessage) {
		this.lookUpMessage = lookUpMessage;
	}

	public List<ServiceOrder> getPendingServiceOrders() {
		return pendingServiceOrders;
	}

	public void setPendingServiceOrders(List<ServiceOrder> pendingServiceOrders) {
		this.pendingServiceOrders = pendingServiceOrders;
	}

}

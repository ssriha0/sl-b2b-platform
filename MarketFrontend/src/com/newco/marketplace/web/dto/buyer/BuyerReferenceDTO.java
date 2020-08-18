package com.newco.marketplace.web.dto.buyer;

import java.sql.Date;



import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * @author nsanzer
 *
 */
public class BuyerReferenceDTO extends SerializedBaseDTO{
	
		private static final long serialVersionUID = -9039887133181049570L;
		private Integer buyerId;
		private Integer buyerRefTypeId;
		private String referenceType;
		private String referenceDescription;
		private Integer soIdentifier;
		private String activeInd;
		private String buyerInput;
		private String providerInput;
		private String required;
		private String searchable;
		private Date createdDate;
		private Date modifiedDate;
		private String modifiedBy;
		private String refEdit;
		private boolean privateInd;
		private String editable; 
		private String pdfRefInd;
		
		//SL-18825
		//DTO attribute for new attribute 'Display field if no value' in Manage Custom Reference
		private String displayNoValue;
		
		public String getDisplayNoValue() {
			return displayNoValue;
		}

		public void setDisplayNoValue(String displayNoValue) {
			this.displayNoValue = displayNoValue;
		}

		public String getPdfRefInd() {
			return pdfRefInd;
		}

		public void setPdfRefInd(String pdfRefInd) {
			this.pdfRefInd = pdfRefInd;
		}

		public BuyerReferenceDTO() {
		}

		public BuyerReferenceDTO(BuyerReferenceVO buyerRefVO) {
			super();
			this.buyerId = buyerRefVO.getBuyerId();
			this.buyerRefTypeId = buyerRefVO.getBuyerRefTypeId();
			this.referenceType = buyerRefVO.getReferenceType();
			this.referenceDescription = buyerRefVO.getReferenceDescription();
			this.soIdentifier = buyerRefVO.getSoIdentifier();
			this.activeInd = UIUtils.getBooleanStringFromInt(buyerRefVO.getActiveInd());
			this.buyerInput = UIUtils.getBooleanStringFromInt(buyerRefVO.getBuyerInput());
			this.providerInput = UIUtils.getBooleanStringFromInt(buyerRefVO.getProviderInput());
			this.required = UIUtils.getBooleanStringFromInt(buyerRefVO.getRequired());
			this.searchable = UIUtils.getBooleanStringFromInt(buyerRefVO.getSearchable());
			this.privateInd = buyerRefVO.isPrivateInd();
			this.editable = UIUtils.getBooleanStringFromInt(buyerRefVO.getEditable());
			this.pdfRefInd = UIUtils.getBooleanStringFromInt(buyerRefVO.getPdfRefInd()!= null? buyerRefVO.getPdfRefInd():0);
			
			//SL-18825
			//DTO attribute for new attribute 'Display field if no value' in Manage Custom Reference
			this.displayNoValue=UIUtils.getBooleanStringFromInt(buyerRefVO.getDisplayNoValue());
		
		}
		
		public Integer getBuyerId() {
			return buyerId;
		}
		public void setBuyerId(Integer buyerId) {
			this.buyerId = buyerId;
		}
		public Integer getBuyerRefTypeId() {
			return buyerRefTypeId;
		}
		public void setBuyerRefTypeId(Integer buyerRefTypeId) {
			this.buyerRefTypeId = buyerRefTypeId;
		}
		public String getReferenceType() {
			return referenceType;
		}
		public void setReferenceType(String referenceType) {
			this.referenceType = referenceType;
		}
		public String getReferenceDescription() {
			return referenceDescription;
		}
		public void setReferenceDescription(String referenceDescription) {
			this.referenceDescription = referenceDescription;
		}
		public Integer getSoIdentifier() {
			return soIdentifier;
		}
		public void setSoIdentifier(Integer soIdentifier) {
			this.soIdentifier = soIdentifier;
		}
		public String getActiveInd() {
			return activeInd;
		}
		public void setActiveInd(String activeInd) {
			this.activeInd = activeInd;
		}
		public String getBuyerInput() {
			return buyerInput;
		}
		public void setBuyerInput(String buyerInput) {
			this.buyerInput = buyerInput;
		}
		public String getProviderInput() {
			return providerInput;
		}
		public void setProviderInput(String providerInput) {
			this.providerInput = providerInput;
		}
		public String getRequired() {
			return required;
		}
		public void setRequired(String required) {
			this.required = required;
		}
		public String getSearchable() {
			return searchable;
		}
		public void setSearchable(String searchable) {
			this.searchable = searchable;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public Date getModifiedDate() {
			return modifiedDate;
		}

		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public String getRefEdit() {
			return refEdit;
		}

		public void setRefEdit(String refEdit) {
			this.refEdit = refEdit;
		}

		public boolean isPrivateInd() {
			return privateInd;
		}

		public void setPrivateInd(boolean privateInd) {
			this.privateInd = privateInd;
		}

		public String getEditable() {
			return editable;
		}

		public void setEditable(String editable) {
			this.editable = editable;
		}	

}

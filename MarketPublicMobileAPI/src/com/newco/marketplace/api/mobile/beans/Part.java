package com.newco.marketplace.api.mobile.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("part")
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {

		@XStreamAlias("itemId")
		private Integer itemId;
	
		@XStreamAlias("partSeqNumber")
		private Integer partSeqNumber;
		
		@XStreamAlias("partNo")
		private String partNo;
		
		@XStreamAlias("partName")
		private String partName;
	
		@XStreamAlias("description")
		private String description;
		
		@XStreamAlias("unitPrice")
		private Double unitPrice;
		
		@XStreamAlias("quantity")
		private Integer quantity;
		
		@XStreamAlias("totalPrice")
		private Double totalPrice;
		
		@XStreamAlias("additionalDetails")
		private String additionalDetails;
		
		
		
		public Integer getItemId() {
			return itemId;
		}

		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Double getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}

		
        


		public Integer getPartSeqNumber() {
			return partSeqNumber;
		}

		public void setPartSeqNumber(Integer partSeqNumber) {
			this.partSeqNumber = partSeqNumber;
		}

		public String getPartName() {
			return partName;
		}

		public void setPartName(String partName) {
			this.partName = partName;
		}

		public String getAdditionalDetails() {
			return additionalDetails;
		}

		public void setAdditionalDetails(String additionalDetails) {
			this.additionalDetails = additionalDetails;
		}

		public String getPartNo() {
			return partNo;
		}

		public void setPartNo(String partNo) {
			this.partNo = partNo;
		}
		
		
}

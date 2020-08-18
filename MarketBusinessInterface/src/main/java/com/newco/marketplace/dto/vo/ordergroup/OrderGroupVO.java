package com.newco.marketplace.dto.vo.ordergroup;

import java.sql.Timestamp;
import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.sears.os.vo.SerializableBaseVO;


public class OrderGroupVO extends SerializableBaseVO{

		private static final long serialVersionUID = 1170321597394890451L;

		private String groupSurrKeyId;
		private String originalGroupId; // lesser used currently
		private String groupId;			// The one we care about
		private String title;			// Dynamically created from service order(s) data
		private Timestamp groupServiceDate;
		private Timestamp groupServiceTime;
		private Integer wfStateId;
		private Integer lockEditInd = OrderConstants.SO_VIEW_MODE_FLAG;
		private Timestamp createdDate;
		private Timestamp modifiedDate;
		private String modifiedBy;
		private Double spendLimit;
		private Double conditionalOfferPrice;
		private Double originalSpendLimitLabor;
		private Double originalSpendLimitParts;
		private Double discountedSpendLimitLabor;
		private Double discountedSpendLimitParts;
		private Double finalSpendLimitLabor;
		private Double finalSpendLimitParts;

		private Integer acceptedResourceId;
		private List<ServiceOrderSearchResultsVO> serviceOrders;

		// Need following extra attributes for showing on Group-SOD - Order Quick Links widget
		// These are not populated from query result-set!
		private String status;
		private String buyerWidget;
		private String endCustomerWidget;
		private String endCustomerPrimaryPhoneNumberWidget;
		private String locationWidget;
		private String distanceInMiles;
		private String appointmentDates;
		private String serviceWindow;
		private String zip;
		private String resourceDispatchAddress;
		private Double totalPermitPrice;
		
		private List<ProviderResultVO> routedResourcesForFirm; // For providers panel specific to firm's providers
		private boolean carSO;
		
		public List<ProviderResultVO> getRoutedResourcesForFirm() {
			return routedResourcesForFirm;
		}

		public void setRoutedResourcesForFirm(
				List<ProviderResultVO> routedResourcesForFirm) {
			this.routedResourcesForFirm = routedResourcesForFirm;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getOriginalGroupId() {
			return originalGroupId;
		}

		public void setOriginalGroupId(String originalGroupId) {
			this.originalGroupId = originalGroupId;
		}

		public List<ServiceOrderSearchResultsVO> getServiceOrders() {
			return serviceOrders;
		}

		public void setServiceOrders(List<ServiceOrderSearchResultsVO> serviceOrders) {
			this.serviceOrders = serviceOrders;
		}

		public Double getOriginalSpendLimitLabor() {
			return originalSpendLimitLabor;
		}

		public void setOriginalSpendLimitLabor(Double originalSpendLimitLabor) {
			this.originalSpendLimitLabor = originalSpendLimitLabor;
		}

		public Double getOriginalSpendLimitParts() {
			return originalSpendLimitParts;
		}

		public void setOriginalSpendLimitParts(Double originalSpendLimitParts) {
			this.originalSpendLimitParts = originalSpendLimitParts;
		}

		public Double getDiscountedSpendLimitLabor() {
			return discountedSpendLimitLabor;
		}

		public void setDiscountedSpendLimitLabor(Double discountedSpendLimitLabor) {
			this.discountedSpendLimitLabor = discountedSpendLimitLabor;
		}

		public Double getDiscountedSpendLimitParts() {
			return discountedSpendLimitParts;
		}

		public void setDiscountedSpendLimitParts(Double discountedSpendLimitParts) {
			this.discountedSpendLimitParts = discountedSpendLimitParts;
		}

		public Integer getLockEditInd() {
			return lockEditInd;
		}

		public void setLockEditInd(Integer lockEditInd) {
			this.lockEditInd = lockEditInd;
		}

		public Integer getWfStateId() {
			return wfStateId;
		}

		public void setWfStateId(Integer wfStateId) {
			this.wfStateId = wfStateId;
		}

		public Timestamp getModifiedDate() {
			return modifiedDate;
		}

		public void setModifiedDate(Timestamp modifiedDate) {
			this.modifiedDate = modifiedDate;
		}

		public Timestamp getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Timestamp createdDate) {
			this.createdDate = createdDate;
		}

		public Integer getAcceptedResourceId() {
			return acceptedResourceId;
		}

		public void setAcceptedResourceId(Integer acceptedResourceId) {
			this.acceptedResourceId = acceptedResourceId;
		}

		public Double getConditionalOfferPrice() {
			return conditionalOfferPrice;
		}

		public void setConditionalOfferPrice(Double conditionalOfferPrice) {
			this.conditionalOfferPrice = conditionalOfferPrice;
		}

		public String getGroupSurrKeyId() {
			return groupSurrKeyId;
		}

		public void setGroupSurrKeyId(String groupSurrKeyId) {
			this.groupSurrKeyId = groupSurrKeyId;
		}

		public Timestamp getGroupServiceDate() {
			return groupServiceDate;
		}

		public void setGroupServiceDate(Timestamp groupServiceDate) {
			this.groupServiceDate = groupServiceDate;
		}

		public Timestamp getGroupServiceTime() {
			return groupServiceTime;
		}

		public void setGroupServiceTime(Timestamp groupServiceTime) {
			this.groupServiceTime = groupServiceTime;
		}

		public String getModifiedBy() {
			return modifiedBy;
		}

		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}

		public Double getSpendLimit() {
			if (spendLimit == null) {
				spendLimit = MoneyUtil.add(finalSpendLimitLabor, finalSpendLimitParts);
			}
			return spendLimit;
		}

		public void setSpendLimit(Double spendLimit) {
			this.spendLimit = spendLimit;
		}

		public Double getFinalSpendLimitLabor() {
			return finalSpendLimitLabor;
		}

		public void setFinalSpendLimitLabor(Double finalSpendLimitLabor) {
			this.finalSpendLimitLabor = finalSpendLimitLabor;
		}

		public Double getFinalSpendLimitParts() {
			return finalSpendLimitParts;
		}

		public void setFinalSpendLimitParts(Double finalSpendLimitParts) {
			this.finalSpendLimitParts = finalSpendLimitParts;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getBuyerWidget() {
			return buyerWidget;
		}

		public void setBuyerWidget(String buyerWidget) {
			this.buyerWidget = buyerWidget;
		}

		public String getEndCustomerWidget() {
			return endCustomerWidget;
		}

		public void setEndCustomerWidget(String endCustomerWidget) {
			this.endCustomerWidget = endCustomerWidget;
		}

		public String getLocationWidget() {
			return locationWidget;
		}

		public void setLocationWidget(String locationWidget) {
			this.locationWidget = locationWidget;
		}

		public String getDistanceInMiles() {
			return distanceInMiles;
		}

		public void setDistanceInMiles(String distanceInMiles) {
			this.distanceInMiles = distanceInMiles;
		}

		public String getAppointmentDates() {
			return appointmentDates;
		}

		public void setAppointmentDates(String appointmentDates) {
			this.appointmentDates = appointmentDates;
		}

		public String getServiceWindow() {
			return serviceWindow;
		}

		public void setServiceWindow(String serviceWindow) {
			this.serviceWindow = serviceWindow;
		}

		public String getEndCustomerPrimaryPhoneNumberWidget() {
			return endCustomerPrimaryPhoneNumberWidget;
		}

		public void setEndCustomerPrimaryPhoneNumberWidget(
				String endCustomerPrimaryPhoneNumberWidget) {
			this.endCustomerPrimaryPhoneNumberWidget = endCustomerPrimaryPhoneNumberWidget;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getResourceDispatchAddress() {
			return resourceDispatchAddress;
		}

		public void setResourceDispatchAddress(String resourceDispatchAddress) {
			this.resourceDispatchAddress = resourceDispatchAddress;
		}

		public boolean isCarSO() {
			return carSO;
		}

		public void setCarSO(boolean carSO) {
			this.carSO = carSO;
		}

		public Double getTotalPermitPrice() {
			return totalPermitPrice;
		}

		public void setTotalPermitPrice(Double totalPermitPrice) {
			this.totalPermitPrice = totalPermitPrice;
		}

		

}

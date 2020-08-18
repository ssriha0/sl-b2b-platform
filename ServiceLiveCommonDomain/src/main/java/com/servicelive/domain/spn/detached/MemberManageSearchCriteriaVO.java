/**
 * 
 */
package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.List;

/**
 * @author hoza
 *
 */
public class MemberManageSearchCriteriaVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5276323994318396398L;
		private Integer buyerId;
		private String searchByType;
		private String providerFirmIdsStr;
		private List<Integer> providerFirmIds;
		private String providerFirmName;
		
		private String serviceProviderIdsStr;
		private  List<Integer> serviceProviderIds; 
	    private String serviceProviderName;
        private   MemberManageFilterVO filterCriteria;
        private Boolean isViewAll; 
        private Integer maxResultsDisplayNumber;
        private Integer totalResultsCount; //Actual count of the results
		/**
		 * @return the providerFirmIds
		 */
		public List<Integer> getProviderFirmIds() {
			return providerFirmIds;
		}
		/**
		 * @param providerFirmIds the providerFirmIds to set
		 */
		public void setProviderFirmIds(List<Integer> providerFirmIds) {
			this.providerFirmIds = providerFirmIds;
		}
		/**
		 * @return the serviceProviderIds
		 */
		public List<Integer> getServiceProviderIds() {
			return serviceProviderIds;
		}
		/**
		 * @param serviceProviderIds the serviceProviderIds to set
		 */
		public void setServiceProviderIds(List<Integer> serviceProviderIds) {
			this.serviceProviderIds = serviceProviderIds;
		}
		/**
		 * @return the providerFirmName
		 */
		public String getProviderFirmName() {
			return providerFirmName;
		}
		/**
		 * @param providerFirmName the providerFirmName to set
		 */
		public void setProviderFirmName(String providerFirmName) {
			this.providerFirmName = providerFirmName;
		}
		/**
		 * @return the serviceProviderName
		 */
		public String getServiceProviderName() {
			return serviceProviderName;
		}
		/**
		 * @param serviceProviderName the serviceProviderName to set
		 */
		public void setServiceProviderName(String serviceProviderName) {
			this.serviceProviderName = serviceProviderName;
		}
		/**
		 * @return the filterCriteria
		 */
		public MemberManageFilterVO getFilterCriteria() {
			return filterCriteria;
		}
		/**
		 * @param filterCriteria the filterCriteria to set
		 */
		public void setFilterCriteria(MemberManageFilterVO filterCriteria) {
			this.filterCriteria = filterCriteria;
		}
		/**
		 * @return the buyerId
		 */
		public Integer getBuyerId() {
			return buyerId;
		}
		/**
		 * @param buyerId the buyerId to set
		 */
		public void setBuyerId(Integer buyerId) {
			this.buyerId = buyerId;
		}
		/**
		 * @return the searchByType
		 */
		public String getSearchByType() {
			return searchByType;
		}
		/**
		 * @param searchByType the searchByType to set
		 */
		public void setSearchByType(String searchByType) {
			this.searchByType = searchByType;
		}
		/**
		 * @return the providerFirmIdsStr
		 */
		public String getProviderFirmIdsStr() {
			return providerFirmIdsStr;
		}
		/**
		 * @param providerFirmIdsStr the providerFirmIdsStr to set
		 */
		public void setProviderFirmIdsStr(String providerFirmIdsStr) {
			this.providerFirmIdsStr = providerFirmIdsStr;
		}
		/**
		 * @return the serviceProviderIdsStr
		 */
		public String getServiceProviderIdsStr() {
			return serviceProviderIdsStr;
		}
		/**
		 * @param serviceProviderIdsStr the serviceProviderIdsStr to set
		 */
		public void setServiceProviderIdsStr(String serviceProviderIdsStr) {
			this.serviceProviderIdsStr = serviceProviderIdsStr;
		}
		/**
		 * @return the isViewAll
		 */
		public Boolean getIsViewAll() {
			return isViewAll;
		}
		/**
		 * @param isViewAll the isViewAll to set
		 */
		public void setIsViewAll(Boolean isViewAll) {
			this.isViewAll = isViewAll;
		}
		/**
		 * @return the maxResultsDisplayNumber
		 */
		public Integer getMaxResultsDisplayNumber() {
			return maxResultsDisplayNumber;
		}
		/**
		 * @param maxResultsDisplayNumber the maxResultsDisplayNumber to set
		 */
		public void setMaxResultsDisplayNumber(Integer maxResultsDisplayNumber) {
			this.maxResultsDisplayNumber = maxResultsDisplayNumber;
		}
		/**
		 * @return the totalResultsCount
		 */
		public Integer getTotalResultsCount() {
			return totalResultsCount;
		}
		/**
		 * @param totalResultsCount the totalResultsCount to set
		 */
		public void setTotalResultsCount(Integer totalResultsCount) {
			this.totalResultsCount = totalResultsCount;
		} 

}

package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class SPNProviderSearchVO extends SerializableBaseVO {

	private static final long serialVersionUID = -7705888970876835701L;
	private Integer resourceId;
	private Integer vendorId;
	private Double  aggregateRatingScoreByNode;
	private Integer aggregateRatingCount;
	private Double generalInsAmount;
	private Double autoInsAmount;
	private Double workmanCompInsAmount;
	private Integer totalSOCompleted;
	
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the aggregateRatingScoreByNode
	 */
	public Double getAggregateRatingScoreByNode() {
		return aggregateRatingScoreByNode;
	}
	/**
	 * @param aggregateRatingScoreByNode the aggregateRatingScoreByNode to set
	 */
	public void setAggregateRatingScoreByNode(Double aggregateRatingScoreByNode) {
		this.aggregateRatingScoreByNode = aggregateRatingScoreByNode;
	}
	/**
	 * @return the aggregateRatingCount
	 */
	public Integer getAggregateRatingCount() {
		return aggregateRatingCount;
	}
	/**
	 * @param aggregateRatingCount the aggregateRatingCount to set
	 */
	public void setAggregateRatingCount(Integer aggregateRatingCount) {
		this.aggregateRatingCount = aggregateRatingCount;
	}
	/**
	 * @return the generalInsAmount
	 */
	public Double getGeneralInsAmount() {
		return generalInsAmount;
	}
	/**
	 * @param generalInsAmount the generalInsAmount to set
	 */
	public void setGeneralInsAmount(Double generalInsAmount) {
		this.generalInsAmount = generalInsAmount;
	}
	/**
	 * @return the autoInsAmount
	 */
	public Double getAutoInsAmount() {
		return autoInsAmount;
	}
	/**
	 * @param autoInsAmount the autoInsAmount to set
	 */
	public void setAutoInsAmount(Double autoInsAmount) {
		this.autoInsAmount = autoInsAmount;
	}
	/**
	 * @return the workmanCompInsAmount
	 */
	public Double getWorkmanCompInsAmount() {
		return workmanCompInsAmount;
	}
	/**
	 * @param workmanCompInsAmount the workmanCompInsAmount to set
	 */
	public void setWorkmanCompInsAmount(Double workmanCompInsAmount) {
		this.workmanCompInsAmount = workmanCompInsAmount;
	}
	/**
	 * @return the totalSOCompleted
	 */
	public Integer getTotalSOCompleted() {
		return totalSOCompleted;
	}
	/**
	 * @param totalSOCompleted the totalSOCompleted to set
	 */
	public void setTotalSOCompleted(Integer totalSOCompleted) {
		this.totalSOCompleted = totalSOCompleted;
	}
	/**
	 * @return the vendorId
	 */
	public Integer getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	
	
}

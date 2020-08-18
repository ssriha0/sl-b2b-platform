/**
 * 
 */
package com.newco.marketplace.vo.provider;

/**
 * @author hoza
 *
 */
public class PowerAuditorSearchResultVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String workflowQueueName;
	private Double averageAge;
	private Long auditableItems;
	
	//SL-20645
	private String reasonNameQueue;
	/**
	 * @return the workflowQueueName
	 */
	public String getWorkflowQueueName() {
		return workflowQueueName;
	}
	/**
	 * @param workflowQueueName the workflowQueueName to set
	 */
	public void setWorkflowQueueName(String workflowQueueName) {
		this.workflowQueueName = workflowQueueName;
	}
	/**
	 * @return the averageAge
	 */
	public Double getAverageAge() {
		return averageAge;
	}
	/**
	 * @param averageAge the averageAge to set
	 */
	public void setAverageAge(Double averageAge) {
		this.averageAge = averageAge;
	}
	/**
	 * @return the auditableItems
	 */
	public Long getAuditableItems() {
		return auditableItems;
	}
	/**
	 * @param auditableItems the auditableItems to set
	 */
	public void setAuditableItems(Long auditableItems) {
		this.auditableItems = auditableItems;
	}
	
	public String getReasonNameQueue() {
		return reasonNameQueue;
	}
	public void setReasonNameQueue(String reasonNameQueue) {
		this.reasonNameQueue = reasonNameQueue;
	}

}

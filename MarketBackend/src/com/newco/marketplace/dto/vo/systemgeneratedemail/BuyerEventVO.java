/**
 * 
 */
package com.newco.marketplace.dto.vo.systemgeneratedemail;

/**
 * @author lprabha
 *
 */
public class BuyerEventVO {
	
	private Integer buyerEventId;
	private EventVO eventVO;
	
	/**
	 * @return the buyerEventId
	 */
	public Integer getBuyerEventId() {
		return buyerEventId;
	}
	/**
	 * @param buyerEventId the buyerEventId to set
	 */
	public void setBuyerEventId(Integer buyerEventId) {
		this.buyerEventId = buyerEventId;
	}
	/**
	 * @return the eventVO
	 */
	public EventVO getEventVO() {
		return eventVO;
	}
	/**
	 * @param eventVO the eventVO to set
	 */
	public void setEventVO(EventVO eventVO) {
		this.eventVO = eventVO;
	}
	
	

}

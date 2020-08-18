/**
 * 
 */
package com.newco.marketplace.dto.vo.systemgeneratedemail;

/**
 * @author rkumari
 *
 */
public class EmailDataVO {
	private String paramKey;
	private String paramValue;
	private Integer eventId;
	private boolean active;
	private Integer buyerId;
	private Integer buyerEventId;
	private Integer templateId;
	
	public EmailDataVO() {
		super();
	}
	public EmailDataVO(String paramKey, String paramValue) {
		super();
		this.paramKey = paramKey;
		this.paramValue = paramValue;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getBuyerEventId() {
		return buyerEventId;
	}
	public void setBuyerEventId(Integer buyerEventId) {
		this.buyerEventId = buyerEventId;
	}
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	
}

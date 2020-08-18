/**
 * 
 */
package com.newco.marketplace.dto.vo.aop;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author schavda
 *
 */
public class AOPAdviceVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5137455372323611850L;
	public Integer aopId;
	public String aopName;
	
	public Integer actionId;
	public String actionName;
	public Integer aopActionId;
	
	public Integer aopActionTargetId;
	public Integer templateId;
	
	public Integer cachingEventId;
	public String cachingEventClass;
	/**
	 * @return the actionId
	 */
	public Integer getActionId() {
		return actionId;
	}
	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	/**
	 * @return the aopActionId
	 */
	public Integer getAopActionId() {
		return aopActionId;
	}
	/**
	 * @param aopActionId the aopActionId to set
	 */
	public void setAopActionId(Integer aopActionId) {
		this.aopActionId = aopActionId;
	}
	/**
	 * @return the aopActionTargetId
	 */
	public Integer getAopActionTargetId() {
		return aopActionTargetId;
	}
	/**
	 * @param aopActionTargetId the aopActionTargetId to set
	 */
	public void setAopActionTargetId(Integer aopActionTargetId) {
		this.aopActionTargetId = aopActionTargetId;
	}
	/**
	 * @return the aopId
	 */
	public Integer getAopId() {
		return aopId;
	}
	/**
	 * @param aopId the aopId to set
	 */
	public void setAopId(Integer aopId) {
		this.aopId = aopId;
	}
	/**
	 * @return the aopName
	 */
	public String getAopName() {
		return aopName;
	}
	/**
	 * @param aopName the aopName to set
	 */
	public void setAopName(String aopName) {
		this.aopName = aopName;
	}
	/**
	 * @return the cachingEventClass
	 */
	public String getCachingEventClass() {
		return cachingEventClass;
	}
	/**
	 * @param cachingEventClass the cachingEventClass to set
	 */
	public void setCachingEventClass(String cachingEventClass) {
		this.cachingEventClass = cachingEventClass;
	}
	/**
	 * @return the cachingEventId
	 */
	public Integer getCachingEventId() {
		return cachingEventId;
	}
	/**
	 * @param cachingEventId the cachingEventId to set
	 */
	public void setCachingEventId(Integer cachingEventId) {
		this.cachingEventId = cachingEventId;
	}
	/**
	 * @return the templateId
	 */
	public Integer getTemplateId() {
		return templateId;
	}
	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	
	
}

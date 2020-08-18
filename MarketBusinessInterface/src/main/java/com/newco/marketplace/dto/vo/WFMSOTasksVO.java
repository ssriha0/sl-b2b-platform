/**
 * @author Munish Joshi
 * Feb 3, 2009
 * WFMSOTasksVO.java
 */
package com.newco.marketplace.dto.vo;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Munish Joshi
 * Feb 3, 2009
 * WFMSOTasksVO.java
 *
 */
public class WFMSOTasksVO extends SerializableBaseVO {
	
	public Integer qId;
	public String qName;
	public String qDesc;
	public Integer taskId;
	public String taskCode;
	public String taskDesc;
	public String taskState;
	public Integer requeueHours;
	public Integer requeueMins;
	
	public Integer getQId() {
		return qId;
	}
	public void setQId(Integer id) {
		qId = id;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getTaskState() {
		return taskState;
	}
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	/**
	 * @return the requeueHours
	 */
	public Integer getRequeueHours() {
		return requeueHours;
	}
	/**
	 * @param requeueHours the requeueHours to set
	 */
	public void setRequeueHours(Integer requeueHours) {
		this.requeueHours = requeueHours;
	}
	/**
	 * @return the requeueMins
	 */
	public Integer getRequeueMins() {
		return requeueMins;
	}
	/**
	 * @param requeueMins the requeueMins to set
	 */
	public void setRequeueMins(Integer requeueMins) {
		this.requeueMins = requeueMins;
	}
	/**
	 * @return the qName
	 */
	public String getQName() {
		return qName;
	}
	/**
	 * @param name the qName to set
	 */
	public void setQName(String name) {
		qName = name;
	}
	/**
	 * @return the qDesc
	 */
	public String getQDesc() {
		return qDesc;
	}
	/**
	 * @param desc the qDesc to set
	 */
	public void setQDesc(String desc) {
		qDesc = desc;
	}
	


}

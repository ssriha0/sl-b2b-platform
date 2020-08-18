/**
 * @author Munish Joshi
 * Feb 3, 2009
 * QueueTasksGroupVO.java
 */
package com.newco.marketplace.dto.vo.group;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;

/**
 * @author Munish Joshi
 * Feb 3, 2009
 * QueueTasksGroupVO.java
 *
 */
public class QueueTasksGroupVO {
	
	List wFMBuyerQueueVO = new ArrayList<WFMBuyerQueueVO>();
	List wFMSOTasksVO = new ArrayList<WFMSOTasksVO>();
	/**
	 * @return the wFMBuyerQueueVO
	 */
	public List getWFMBuyerQueueVO() {
		return wFMBuyerQueueVO;
	}
	/**
	 * @param buyerQueueVO the wFMBuyerQueueVO to set
	 */
	public void setWFMBuyerQueueVO(List buyerQueueVO) {
		wFMBuyerQueueVO = buyerQueueVO;
	}
	/**
	 * @return the wFMSOTasksVO
	 */
	public List getWFMSOTasksVO() {
		return wFMSOTasksVO;
	}
	/**
	 * @param tasksVO the wFMSOTasksVO to set
	 */
	public void setWFMSOTasksVO(List tasksVO) {
		wFMSOTasksVO = tasksVO;
	}

	
	
	

}

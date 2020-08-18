package com.newco.marketplace.dto.vo.ordermanagement.comparator;

import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getCallInfo.Task;
import com.newco.marketplace.vo.ordermanagement.so.OMServiceOrder;
public class SoScopeComparator implements Comparator<OMServiceOrder>{
	Logger LOGGER = Logger.getLogger(SoScopeComparator.class);
	public SoScopeComparator() {
		super();
	}
	
	//TODO Need review
	/**
	 * Compares two OMServiceOrder based on the Scope. List of Scope(Task) <br>
	 * is expected to pre-sorted from sql. Each task names are appended on which sort
	 * is done.
	 * **/
	public int compare(OMServiceOrder object, OMServiceOrder compareObject) {
		StringBuilder scope1 = new StringBuilder();
		List<Task> taskList = object.getScope();
		if(null != taskList && taskList.size() >0){
			for(Task task : taskList){
				scope1.append(task.getServiceType()).append(" ");
			}
		}
		StringBuilder scope2 = new StringBuilder();
		taskList = compareObject.getScope();
		if(null != taskList && taskList.size() >0){
			for(Task task : taskList){
				scope2.append(task.getServiceType()).append(" ");
			}
		}
		String objValue = scope1.toString().toLowerCase();
		String compareObjValue = scope2.toString().toLowerCase();
		return objValue.compareTo(compareObjValue);
	}
}
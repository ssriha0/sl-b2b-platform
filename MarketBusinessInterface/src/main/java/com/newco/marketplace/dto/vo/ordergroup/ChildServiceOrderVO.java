package com.newco.marketplace.dto.vo.ordergroup;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.sears.os.vo.SerializableBaseVO;

/**
 * is used to input info into template_input
 * adding attributes only which are required to display in email
 * @author RGURRA0
 *
 */
public class ChildServiceOrderVO extends SerializableBaseVO{
	
	private String soId;
	private List<ServiceOrderTask> tasks;
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public List<ServiceOrderTask> getTasks() {
		return tasks;
	}
	public void setTasks(List<ServiceOrderTask> tasks) {
		this.tasks = tasks;
	}

}

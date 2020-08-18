package com.newco.marketplace.webservices.dto.serviceorder;

import java.util.List;
import com.newco.marketplace.webservices.response.WSProcessResponse;

public class CreateDraftResponse extends WSProcessResponse {
	
	private Boolean autoRoute;
	private String templateName;
	private boolean orderUpdate;
	private List<CreateTaskReponse> tasks;
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public boolean isOrderUpdate() {
		return orderUpdate;
	}
	public void setOrderUpdate(boolean orderUpdate) {
		this.orderUpdate = orderUpdate;
	}
	public Boolean getAutoRoute() {
		return autoRoute;
	}
	public void setAutoRoute(Boolean autoRoute) {
		this.autoRoute = autoRoute;
	}
	public List<CreateTaskReponse> getTasks() {
		return tasks;
	}
	public void setTasks(List<CreateTaskReponse> tasks) {
		this.tasks = tasks;
	}
	
}

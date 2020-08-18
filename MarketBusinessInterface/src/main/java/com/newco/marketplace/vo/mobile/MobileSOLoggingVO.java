package com.newco.marketplace.vo.mobile;

import java.sql.Date;

public class MobileSOLoggingVO {
	
	private Integer loggingId;
	
	private String soId;
	
	private Integer actionId;
	
	private String request;
	
	private String response;
	
	private Date createdDate;
	
	private Integer createdBy;
	
    private String httpMethod;
    
    private Integer roleId;
    
    public MobileSOLoggingVO() {}
    
  	public MobileSOLoggingVO(String request, Integer actionId,String httpMethod) {
  		this.request=request;
  		this.actionId=actionId;
  		this.httpMethod=httpMethod;
  	}

  	
	public Integer getLoggingId() {
		return loggingId;
	}

	public void setLoggingId(Integer loggingId) {
		this.loggingId = loggingId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public void setRoleId(String roleId) {
		// TODO Auto-generated method stub
		
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	
	
	
	

}

package com.newco.marketplace.vo.hi.provider;

import java.sql.Date;

public class APILoggingVO {
	
	private Integer loggingId;
	
	private String soId;
	
	private String apiName;
	
	private String request;
	
	private String response;
	
	private Date createdDate;
	
	private Date modifieddate;
	
	private Integer createdBy;
	
    private String httpMethod;
    
    private Integer roleId;
    
    private String loggingType;
    
    private String status;
    
    public APILoggingVO() {}
    
  	public APILoggingVO(String request, String apiName,String httpMethod,String response,String loggingType,String status) {
  		this.request=request;
  		this.apiName=apiName;
  		this.httpMethod=httpMethod;
  		this.createdDate =new Date(System.currentTimeMillis());
  		this.response=response;
  		this.loggingType=loggingType;
  		this.status=status;
  	}

  	
  	
  	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
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

	public String getLoggingType() {
		return loggingType;
	}

	public void setLoggingType(String loggingType) {
		this.loggingType = loggingType;
	}

	public Date getModifieddate() {
		return modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}
	
	
	
	
	
	

}

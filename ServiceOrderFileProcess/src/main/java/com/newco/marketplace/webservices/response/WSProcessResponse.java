package com.newco.marketplace.webservices.response;
/**
 *  This class holds information about the 
 *  response from web service.
 *
 *@author     Siva
 *@created    January 4, 2008
 */
import java.util.ArrayList;
import java.util.List;

public class WSProcessResponse {
	
	private String processCode;
	private String processStatus;
	private List<WSErrorInfo> errorList = new ArrayList<WSErrorInfo>();
	private List<WSErrorInfo> warningList = new ArrayList<WSErrorInfo>();
	private String SLServiceOrderId;
	private String clientServiceOrderId;
	
	
	public String getProcessCode() {
		return processCode;
	}
	
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	
	public String getProcessStatus() {
		return processStatus;
	}
	
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	
	public List<WSErrorInfo> getErrorList() {
		return errorList;
	}
	
	public void setErrorList(List<WSErrorInfo> errorList) {
		this.errorList = errorList;
	}
	
	public List<WSErrorInfo> getWarningList() {
		return warningList;
	}
	
	public void setWarningList(List<WSErrorInfo> warningList) {
		this.warningList = warningList;
	}
	
	public void setWarningList(ArrayList<WSErrorInfo> warningList) {
		this.warningList = warningList;
	}
	
	public String getSLServiceOrderId() {
		return SLServiceOrderId;
	}
	
	public void setSLServiceOrderId(String serviceOrderId) {
		SLServiceOrderId = serviceOrderId;
	}
	
	public String getClientServiceOrderId() {
		return clientServiceOrderId;
	}
	
	public void setClientServiceOrderId(String clientServiceOrderId) {
		this.clientServiceOrderId = clientServiceOrderId;
	}


}

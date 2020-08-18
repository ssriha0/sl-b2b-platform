package com.newco.marketplace.api.beans.so.assignSO;

import java.util.List;

import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;


public class AssignVO {
	private String soId;
	private Integer wfStateId;
	private List<RoutedProvider>routedResources;
	private String assignmentType;
	private Integer resourceId;
	private Integer firmId;	
	private String reassignComment;
	private Integer roleId;
	private String requestFor;
	private Integer urlResourceId;
	private Integer acceptedVendorId;
	Contact soContact;
	SoLoggingVo soLoggingVO;
	ServiceOrderNote soNote;
	ServiceOrder serviceOrder;
	

	/*ServiceOrder serviceOrder;
	Contact soContact;*/

	public Integer getAcceptedVendorId() {
		return acceptedVendorId;
	}

	public void setAcceptedVendorId(Integer acceptedVendorId) {
		this.acceptedVendorId = acceptedVendorId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getFirmId() {
		return firmId;
	}

	public void setFirmId(Integer firmId) {
		this.firmId = firmId;
	}

	public String getReassignComment() {
		return reassignComment;
	}

	public void setReassignComment(String reassignComment) {
		this.reassignComment = reassignComment;
	}

	public String getRequestFor() {
		return requestFor;
	}

	public void setRequestFor(String requestFor) {
		this.requestFor = requestFor;
	}

	public Contact getSoContact() {
		return soContact;
	}

	public void setSoContact(Contact soContact) {
		this.soContact = soContact;
	}

	public SoLoggingVo getSoLoggingVO() {
		return soLoggingVO;
	}

	public void setSoLoggingVO(SoLoggingVo soLoggingVO) {
		this.soLoggingVO = soLoggingVO;
	}

	public ServiceOrderNote getSoNote() {
		return soNote;
	}

	public void setSoNote(ServiceOrderNote soNote) {
		this.soNote = soNote;
	}

	public ServiceOrder getServiceOrder() {
		return serviceOrder;
	}

	public void setServiceOrder(ServiceOrder serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUrlResourceId() {
		return urlResourceId;
	}

	public void setUrlResourceId(Integer urlResourceId) {
		this.urlResourceId = urlResourceId;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}


	public List<RoutedProvider> getRoutedResources() {
		return routedResources;
	}

	public void setRoutedResources(List<RoutedProvider> routedResources) {
		this.routedResources = routedResources;
	}

	public String getAssignmentType() {
		return assignmentType;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}
	
	

}

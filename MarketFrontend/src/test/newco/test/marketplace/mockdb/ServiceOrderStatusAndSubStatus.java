package test.newco.test.marketplace.mockdb;

import java.util.List;

public class ServiceOrderStatusAndSubStatus {
	
	private String status;
	private List<ServiceOrderSubStatus> subStatus;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<ServiceOrderSubStatus> getSubStatus() {
		return subStatus;
	}
	
	public void setSubStatus(List<ServiceOrderSubStatus> subStatus) {
		this.subStatus = subStatus;
	}

}

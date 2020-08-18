package com.newco.marketplace.web.action.monitor;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegatesImpl.SOMonitorDelegateImpl;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.ServiceOrderReverseSortComparator;
import com.newco.marketplace.web.utils.ServiceOrderSortComparator;
import com.opensymphony.xwork2.Preparable;


public class ServiceOrderSortAction extends SLBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2713859122374624364L;
	private String sortBy;
	private String status;
	private String lastSortedBy;
	private List<ServiceOrderDTO> serviceOrders;
	private SOMonitorDelegateImpl manager;
	public ServiceOrderSortAction(SOMonitorDelegateImpl manager) {
		this.manager = manager;
	}
	
	public String getSortedServiceOrders() throws Exception{
		ArrayList<ServiceOrderDTO> dtoList = (ArrayList<ServiceOrderDTO>)getSession().getAttribute("dtoList"); 
		
		if(!sortBy.equalsIgnoreCase(lastSortedBy))
		{
			
			ServiceOrderSortComparator sort = new ServiceOrderSortComparator(getSortBy());
			if(dtoList != null)
				Collections.sort(dtoList, sort);
			
			
		} else {
			ServiceOrderReverseSortComparator reverseSort = new ServiceOrderReverseSortComparator(getSortBy());
			
			
			if(dtoList != null)
				Collections.sort(dtoList, reverseSort);
			
		}
		
		setAttribute("dtoList", getSession().getAttribute("dtoList"));

		return "showSortedData";
		
	}
	
	public void prepare() throws Exception {
		
		
	}
	
	public String execute() throws Exception
	{

		return "success";
	}
	
	


	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastSortedBy() {
		return lastSortedBy;
	}

	public void setLastSortedBy(String lastSortedBy) {
		this.lastSortedBy = lastSortedBy;
	}

	public List<ServiceOrderDTO> getServiceOrders() {
		return serviceOrders;
	}

	public void setServiceOrders(List<ServiceOrderDTO> serviceOrders) {
		this.serviceOrders = serviceOrders;
	}

	

	

}



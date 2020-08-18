package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("InstallationServiceOrders")
public class ServiceOrders implements Serializable {
	 public static final int minNumberOfMsgPerThread = 10; 

	/**
	 * 
	 */
	private static final long serialVersionUID = -6205150479005601818L;

	@XStreamAlias("BuyerId")
	private String buyerId;
	
	@XStreamAlias("identification")
	private Identification identification;
	
	@XStreamImplicit(itemFieldName="InstallationServiceOrder")
	private List<ServiceOrder> serviceOrders;

	public List<ServiceOrder> getServiceOrders() {
		return serviceOrders;
	}

	public void setServiceOrders(List<ServiceOrder> serviceOrders) {
		this.serviceOrders = serviceOrders;
	}
	

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Identification getIdentification() {
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}
	
	public void sortSOList() {
		Collections.sort(serviceOrders);		
	}
	
	public static String getListString(List<ServiceOrder> list) {
		StringBuilder bld = new StringBuilder();
		if (list == null) 
			return null;
		
		bld.append("------ ServiceOrderList :" + list.size() + " ------- \n");
		for (ServiceOrder so: list) {
			bld.append(so + "\n");
		}
		bld.append("----------------------------------------------\n");		
		return bld.toString();
	}
	
	public List<List<ServiceOrder>> getServiceOrdersMultiList(int part) {
		sortSOList();
		int size = serviceOrders.size();		
		int singleSize = size/ part;
		//List[] array = new List[part];
		List<List<ServiceOrder>> holder = new ArrayList<List<ServiceOrder>>();

		if (singleSize < minNumberOfMsgPerThread) {
			singleSize = minNumberOfMsgPerThread;
		}
		// 4/3, 5/3 case
		//if (singleSize == 1 && size > part) {
		//	singleSize ++;
		//}
		
		//System.out.println(singleSize);
		
		
		int i = 0;
		List<ServiceOrder> newList = new ArrayList<ServiceOrder>();
		boolean next = false, readyForSplit = false;
		ServiceOrder previousSo = null;
		int nextSplit = i + singleSize;
		//System.out.println("nextSplit :" + nextSplit);
		int index = 1;
		
		for (ServiceOrder so: serviceOrders) {
			if (i >= nextSplit) {
				readyForSplit = true;	
			//	System.out.println("readyForSplit true at " + i);
			}
			
			if (readyForSplit == true) {				
				if (previousSo != null && !so.getUniqueKey().equals(previousSo.getUniqueKey())) {
					next = true;
					readyForSplit = false;					
				}
			}

			if (next == true) {			
				if (index < part) {
					//System.out.println("Starting new List with index :" + index  + " at " + i);				
					holder.add(newList);
					newList = new ArrayList<ServiceOrder>();
					nextSplit =   i + singleSize;;
					System.out.println("nextSplit :" + nextSplit);
					next = false;
					index++;
				} else {
					//System.out.println("Fixing index at " + index);
				}
			}			

			newList.add(so);
			previousSo = so;
			i++;
		}
		
		holder.add(newList);		
		
		return holder;		
	}
	
	public String toString() {
		StringBuilder bld = new StringBuilder();
		//bld.append("\n BuyerId[" + buyerId + "] identification[" + identification + "] \n");
		bld.append("\n" + getListString(serviceOrders));
		return bld.toString();
	}
}

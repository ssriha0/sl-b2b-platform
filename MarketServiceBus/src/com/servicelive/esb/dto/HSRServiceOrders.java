package com.servicelive.esb.dto;

import java.io.Serializable;
import java.util.List;


public class HSRServiceOrders implements Serializable {

	private static final long serialVersionUID = 1L;
	private String inputFileName;

	private List<HSRServiceOrder> serviceOrders;

	public List<HSRServiceOrder> getServiceOrders() {
		return serviceOrders;
	}

	public void setServiceOrders(List<HSRServiceOrder> serviceOrders) {
		this.serviceOrders = serviceOrders;
	}

	/**
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	
}

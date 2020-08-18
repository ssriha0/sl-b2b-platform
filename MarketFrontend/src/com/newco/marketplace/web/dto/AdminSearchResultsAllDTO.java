package com.newco.marketplace.web.dto;

import java.util.List;

public class AdminSearchResultsAllDTO extends SerializedBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5735181290871188340L;
	private Boolean tooManyRows;
	private int numberOfRows;
	private List<AdminSearchResultsDTO> listOfProviders;
	
	public List<AdminSearchResultsDTO> getListOfProviders() {
		return listOfProviders;
	}
	public void setListOfProviders(List<AdminSearchResultsDTO> listOfProviders) {
		this.listOfProviders = listOfProviders;
	}
	public int getNumberOfRows() {
		return numberOfRows;
	}
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public Boolean getTooManyRows() {
		return tooManyRows;
	}
	public void setTooManyRows(Boolean tooManyRows) {
		this.tooManyRows = tooManyRows;
	}
	
	
	
}

package com.newco.marketplace.search.vo;

import java.util.ArrayList;

public class SearchQueryVO {
	
	public String searchString;
	public int zipCode;
	public String category; 
	public String serviceType;
	public int radius;
	public int spnId;
	public String stateId;
	public ArrayList<String> facetingFields;
	public int	typeOfSearch;
	public int	sizeOfResultset;
	public ArrayList<String> sortingFields;

}

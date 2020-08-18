package com.newco.marketplace.business.businessImpl.skillTree;

import java.util.ArrayList;

/**
 * @author zizrale
 *
 */
public class SearchResultContainer {
	private ArrayList resultsList = new ArrayList();

	/**
	 * constructor
	 */
	public SearchResultContainer(){
	} 

	/**
	 * @param result
	 */
	public void add(SearchResult result) {
		resultsList.add(result);
	}
	
	/**
	 * @return SearchResult[] - converts ArrayList to Array
	 */
	public SearchResult[] toArray() {
		return (SearchResult[]) resultsList
				.toArray(new SearchResult[resultsList.size()]);
	}
	
	/**
	 * @return ArrayList of SearchResult objects
	 */
	public ArrayList getResultList(){
		return resultsList;
	}
}

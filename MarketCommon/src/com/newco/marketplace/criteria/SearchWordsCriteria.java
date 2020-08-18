/**
 * 
 */
package com.newco.marketplace.criteria;

/**
 * @author Munish Joshi
 *
 */
public class SearchWordsCriteria implements ICriteria {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSet = false;
	private String searchWords=null;
	
	public SearchWordsCriteria(){
		
	}
	
	public SearchWordsCriteria(boolean isSet,String searchWords){
		
		this.isSet=isSet;
		this.searchWords = searchWords;
		
	}
	
	
	public String getSearchWords() {
		return searchWords;
	}

	public void setSearchWords(String searchWords) {
		this.searchWords = searchWords;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.criteria.ICriteria#isSet()
	 */
	public boolean isSet() {
		// TODO Auto-generated method stub
		return isSet;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.criteria.ICriteria#reset()
	 */
	public void reset() {
		setSearchWords(null);
		setSet(false);

	}

}

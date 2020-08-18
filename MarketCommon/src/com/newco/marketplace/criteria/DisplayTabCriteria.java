/**
 * 
 */
package com.newco.marketplace.criteria;

/**
 * @author Munish Joshi
 *
 */
public class DisplayTabCriteria implements ICriteria {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSet = false;
	private String displayTab=null;
	
	public DisplayTabCriteria(){
		
	}
	
	public DisplayTabCriteria(boolean isSet,String displayTab){
		
		this.isSet=isSet;
		this.displayTab = displayTab;
		
	}

	public String getDisplayTab() {
		return displayTab;
	}

	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}
	
	public boolean isSet() {
		// TODO Auto-generated method stub
		return isSet;
	}
	
	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	public void reset() {
		setSet(false);
		displayTab=null;
		
	}

}

package com.newco.marketplace.web.action.base;

public class SLErrorAction extends SLBaseAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6329719940931499502L;
	private String errorMessage;
	private String returnURL;
	
	public void prepare() throws Exception {
		
		getRequest().setAttribute("errorMessage", errorMessage);
		getRequest().setAttribute("returnURL", returnURL);

	}
	
	public String execute() throws Exception{
		
		return SUCCESS;
		
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = getRequest().getContextPath()+ returnURL;
	}


	
	
}

/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * @author KSudhanshu
 *
 */
public class BaseTabDto extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4473834181345736649L;
	private Map<String,String> tabStatus = new HashMap<String, String>();
	private Object dtObject;
	private Map fieldsError = new HashMap();
	private List actionErrors = new ArrayList<String>();
	private List actionMessages = new ArrayList<String>();
	
	/**
	 * @return the dtObject
	 */
	public Object getDtObject() {
		return dtObject;
	}
	/**
	 * @param dtObject the dtObject to set
	 */
	public void setDtObject(Object dtObject) {
		this.dtObject = dtObject;
	}
	
	/**
	 * @return the tabStatus
	 */
	public Map<String, String> getTabStatus() {
		return tabStatus;
	}
	/**
	 * @param tabStatus the tabStatus to set
	 */
	public void setTabStatus(Map<String, String> tabStatus) {
		this.tabStatus = tabStatus;
	}
	/**
	 * @return the fieldsError
	 */
	public Map getFieldsError() {
		return fieldsError;
	}
	/**
	 * @param fieldsError the fieldsError to set
	 */
	public void setFieldsError(Map fieldsError) {
		this.fieldsError = fieldsError;
	}
	/**
	 * @return the actionErrors
	 */
	public List getActionErrors() {
		return actionErrors;
	}
	/**
	 * @param actionErrors the actionErrors to set
	 */
	public void setActionErrors(List actionErrors) {
		this.actionErrors = actionErrors;
	}
	/**
	 * @return the actionMessages
	 */
	public List getActionMessages() {
		return actionMessages;
	}
	/**
	 * @param actionMessages the actionMessages to set
	 */
	public void setActionMessages(List actionMessages) {
		this.actionMessages = actionMessages;
	}
	
	
	
	
}

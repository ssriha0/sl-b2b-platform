/**
 * 
 */
package com.servicelive.spn.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.struts2.components.ActionError;

/**
 * @author hoza
 *
 */
public abstract class SPNBaseModel implements Serializable{
	private Collection<String> actionErrors = new ArrayList<String>();
	private Map<String, List<String>>  fieldErrors = new HashMap<String, List<String>>();
	/**
	 * @return the actionErrors
	 */
	public Collection<String> getActionErrors() {
		return actionErrors;
	}
	/**
	 * @param actionErrors the actionErrors to set
	 */
	public void setActionErrors(List<String> actionErrors) {
		this.actionErrors = actionErrors;
	}
	/**
	 * @return the fieldErrors
	 */
	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}
	/**
	 * @param fieldErrors the fieldErrors to set
	 */
	public void setFieldErrors(Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}

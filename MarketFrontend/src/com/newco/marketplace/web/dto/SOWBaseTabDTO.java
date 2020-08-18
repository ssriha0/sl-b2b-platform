package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.utils.Config;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.opensymphony.xwork2.ActionContext;

/**
 * $Revision: 1.34 $ $Author: groma $ $Date: 2008/05/27 18:00:40 $
 *
 */
public abstract class SOWBaseTabDTO extends SerializedBaseDTO{
	
	private static final Logger logger = Logger.getLogger(SOWBaseTabDTO.class.getName());
	protected boolean dirtyFlag = false; 
	private Integer entityId;
	private Date createdDate;
	private String createdDateString;
	private String modifiedDate;
	private List<IError> errors = new ArrayList<IError>();
	private List<IWarning> warnings = new ArrayList<IWarning>();
	private boolean saveAndAutoPost=false;
	private boolean routeFromFE;
	
	private String soId; // New for handling Order Groups
	
	transient private ResourceBundle TheResourceBundle;
	private boolean doFullValidation = true;
	private int _index;
	
	public boolean isRouteFromFE() {
		return routeFromFE;
	}

	public void setRouteFromFE(boolean routeFromFE) {
		this.routeFromFE = routeFromFE;
	}
	
	public String getSoId()
	{
		return soId;
	}

	public void setSoId(String soId)
	{
		this.soId = soId;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setErrors(List<IError> errors) {
		this.errors = errors;
	}

	public boolean isDirtyFlag() {
		return dirtyFlag;
	}
	
	protected void addError(SOWError error) {
		if (errors != null) {
			errors.add(error);
		} else {
			errors = new ArrayList<IError>();
			errors.add(error);
		}

	}

	public void clearAllErrors() {
		if (errors!=null){
			errors.clear();
		}
	}

	protected void addZipCodeErrors(List<IError> errorList){
		
		if(errorList != null && errorList.size() > 0){
			for(int i=0;i<errorList.size();i++){
				if(errorList.get(i).getMsg() != null
						&& (errorList.get(i).getMsg().equalsIgnoreCase(getTheResourceBundle().getString("Zip_Not_Valid"))
						|| errorList.get(i).getMsg().equalsIgnoreCase(getTheResourceBundle().getString("Zip_State_No_Match")))){
					getErrors().add(errorList.get(i));
				}
			}
		}
	}
	protected void addWarning(SOWWarning warning) {
		if (warnings != null) {
			warnings.add(warning);
		} else {
			warnings = new ArrayList<IWarning>();
			warnings.add(warning);
		}

	}

	public void clearAllWarnings() {
		if (warnings!=null){
			warnings.clear();
		}
	}
	public List<IError> getErrorsOnly() {
		List<IError> errorsOnly = new ArrayList<IError>();
		if (errors != null && errors.size() > 0) {
			Iterator<IError> errorsItr = errors.iterator();
			while (errorsItr.hasNext()) {
				SOWError sowErr = (SOWError) errorsItr.next();
				if (OrderConstants.SOW_TAB_ERROR.equals(sowErr.getMsgType())) {
					errorsOnly.add(sowErr);
				}
			}
		}
		return (List<IError>)new ArrayList(errorsOnly).clone();
	}

	public List<IWarning> getWarningsOnly() {
		List<IWarning> warningsOnly = new ArrayList<IWarning>();
		if (warnings != null && warnings.size() > 0) {
			Iterator<IWarning> warningsItr = warnings.iterator();
			while (warningsItr.hasNext()) {
				SOWWarning sowWarn = (SOWWarning) warningsItr.next();
				if (OrderConstants.SOW_TAB_WARNING
						.equals(sowWarn.getMsgType())) {
					warningsOnly.add(sowWarn);
				}
			}
		}
		return (List<IWarning>)new ArrayList(warningsOnly).clone();
	}
	
	protected void _doWorkFlowValidation()
	{
		Map sessionMap = getApplicationSessionMap();
		//SL-19820
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		if(sessionMap != null && 
		   new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap, soId))
		{
			//SL-19820
			//Object soId = getApplicationSessionMap().get(OrderConstants.SO_ID);
			Object commonCriteria = getApplicationSessionMap().get(OrderConstants.SERVICE_ORDER_CRITERIA_KEY);
			if(soId != null && commonCriteria != null)
			{
				try{
					if(!new SOClaimedFacility().isCurrentUserTheClaimedUser(soId,(ServiceOrdersCriteria)commonCriteria)){
						addError("", "You are not the Current Claimed User", OrderConstants.SOW_TAB_ERROR);
					}
				} catch (BusinessServiceException e) {
					logger.info("Caught Exception and ignoring",e);
				}
			}
		}
	}

	public List<IError> getErrors() {
		return errors;  // clone() is not working somehow so reverted back.
	}

	protected void addError(String fieldId, String errorMsg, String errorType) {
		addError(new SOWError(fieldId, errorMsg, errorType));
	}
	
	protected void addWarning(String fieldId, String warningMsg, String warningType) {
		addWarning(new SOWWarning(fieldId, warningMsg, warningType));
	}
	
	protected Map getApplicationSessionMap() {
		return ActionContext.getContext().getSession();
	}
	
	public abstract void validate();
	
	public abstract String getTabIdentifier();

	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}

	public void setTheResourceBundle(ResourceBundle theResourceBundle) {
		TheResourceBundle = theResourceBundle;
	}

	public boolean isDoFullValidation() {
		return doFullValidation;
	}

	public void setDoFullValidation(boolean doFullValidation) {
		this.doFullValidation = doFullValidation;
	}

	public int get_index() {
		return _index;
	}

	public void set_index(int _index) {
		this._index = _index;
	}

	public List<IWarning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<IWarning> warnings) {
		this.warnings = warnings;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public boolean isSaveAndAutoPost() {
		return saveAndAutoPost;
	}

	public void setSaveAndAutoPost(boolean saveAndAutoPost) {
		this.saveAndAutoPost = saveAndAutoPost;
	}	
	
	//SL-19820
	private void setAttribute(String name, Object obj) {
		ServletActionContext.getRequest().setAttribute(name, obj);
	}

	private Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

}

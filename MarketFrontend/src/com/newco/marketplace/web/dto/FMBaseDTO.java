package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.utils.Config;

public abstract class FMBaseDTO extends SerializedBaseDTO{

	private List<IError> errors = new ArrayList<IError>();
	private List<IWarning> warnings = new ArrayList<IWarning>();
    transient private ResourceBundle TheResourceBundle;
	private boolean doFullValidation = true;
	private int _index;
	
	public void setErrors(List<IError> errors) {
		this.errors = errors;
	}


	protected void addError(SOWError error) {
		if (errors != null) {
			errors.add(error);
		} else {
			errors = new ArrayList<IError>();
			errors.add(error);
		}

	}

	protected void clearAllErrors() {
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

	protected void clearAllWarnings() {
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

	public List<IError> getErrors() {
		return errors;  // clone() is not working somehow so reverted back.
	}

	protected void addError(String fieldId, String errorMsg, String errorType) {
		addError(new SOWError(fieldId, errorMsg, errorType));
	}
	
	protected void addWarning(String fieldId, String warningMsg, String warningType) {
		addWarning(new SOWWarning(fieldId, warningMsg, warningType));
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
}
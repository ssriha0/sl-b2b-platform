package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.utils.Config;

public abstract class SLBaseDTO extends SerializedBaseDTO{

	public SLBaseDTO() {
	}

	private List<IMessage> allMessages = new ArrayList<IMessage>();

    transient private ResourceBundle resourceBundle=Config.getResouceBundle();

	public abstract boolean validateErrors();

	public abstract boolean validateWarnings();
	
	/**
	 * @return the allMessages
	 */
	public List<IMessage> getAllMessages() {
		return allMessages;
	}

	/**
	 * @param allMessages the allMessages to set
	 */
	public void setAllMessages(List<IMessage> allMessages) {
		this.allMessages = allMessages;
	}	
	
	private void addMessage(String fieldId, String errorMsg, String errorType){
		IMessage iMessage=new SOWError(fieldId, resourceBundle.getString(errorMsg), errorType);
		allMessages.add(iMessage);
	}
	
	public void addError(String fieldId, String errorMsg){
		addMessage(fieldId, errorMsg, OrderConstants.SOW_TAB_ERROR);
	}

	public void addWarning(String fieldId, String errorMsg){
		addMessage(fieldId, errorMsg, OrderConstants.SOW_TAB_WARNING);
	}	
}

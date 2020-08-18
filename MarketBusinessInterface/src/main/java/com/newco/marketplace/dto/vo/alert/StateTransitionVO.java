/**
 * 
 */
package com.newco.marketplace.dto.vo.alert;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class StateTransitionVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5556888716038141794L;
	String transitionDesc;
	Integer targetState;
	Integer sourceState;
	ArrayList<Contact> contactList;
	String payload;
	String payloadKey;
	Integer alertTypeId;
	
	public Integer getAlertTypeId() {
		return alertTypeId;
	}
	public void setAlertTypeId(Integer alertTypeId) {
		this.alertTypeId = alertTypeId;
	}
	public ArrayList<Contact> getContactList() {
		return contactList;
	}
	public void setContactList(ArrayList<Contact> contactList) {
		this.contactList = contactList;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public String getPayloadKey() {
		return payloadKey;
	}
	public void setPayloadKey(String payloadKey) {
		this.payloadKey = payloadKey;
	}
	public Integer getSourceState() {
		return sourceState;
	}
	public void setSourceState(Integer sourceState) {
		this.sourceState = sourceState;
	}
	public Integer getTargetState() {
		return targetState;
	}
	public void setTargetState(Integer targetState) {
		this.targetState = targetState;
	}
	public String getTransitionDesc() {
		return transitionDesc;
	}
	public void setTransitionDesc(String transitionDesc) {
		this.transitionDesc = transitionDesc;
	}
	
	
	
	
	

}

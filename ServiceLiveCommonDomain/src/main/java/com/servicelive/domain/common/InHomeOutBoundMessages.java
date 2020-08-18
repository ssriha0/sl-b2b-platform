package com.servicelive.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name="lu_inhome_outbound_notification_messages")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InHomeOutBoundMessages  implements java.io.Serializable {
	
 /**
	 * 
	 */
	private static final long serialVersionUID = 9010095254475680551L;

@Id
 @Column(name="notification_message_id")
 private Integer messageId;
 
 @Column(name="status_id")
 private Integer wfStatus;
 
 @Column(name ="substatus_id")
 private Integer wfSubStatus;
 
 @Column(name="message")
 private String message;

public Integer getMessageId() {
	return messageId;
}

public void setMessageId(Integer messageId) {
	this.messageId = messageId;
}

public Integer getWfStatus() {
	return wfStatus;
}

public void setWfStatus(Integer wfStatus) {
	this.wfStatus = wfStatus;
}

public Integer getWfSubStatus() {
	return wfSubStatus;
}

public void setWfSubStatus(Integer wfSubStatus) {
	this.wfSubStatus = wfSubStatus;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
}

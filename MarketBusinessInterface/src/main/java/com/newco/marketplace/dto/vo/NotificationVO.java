package com.newco.marketplace.dto.vo;
import java.util.Date;

public class NotificationVO {

	private Integer entityId;// firm or providerId
	private String entityName; // firm or provider Name
	private String credentailType;// credential or insurance Type
	private Date expirationDate;
	private Date notificationDate;// notice sent on
	private Integer notificationType; // insurance/company/provider.
	

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getCredentailType() {
		return credentailType;
	}

	public void setCredentailType(String credentailType) {
		this.credentailType = credentailType;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}

	public Integer getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}

}

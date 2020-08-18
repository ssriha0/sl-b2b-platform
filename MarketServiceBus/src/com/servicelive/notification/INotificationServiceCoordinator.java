package com.servicelive.notification;

import com.servicelive.notification.bo.INotificationBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public interface INotificationServiceCoordinator {

	/**
	 * @param serviceOrder
	 */
	public void insertInHomeOutBoundNotification(ServiceOrder serviceOrder);

	/**
	 * @return
	 */
	public INotificationBO getNotificationBO();

	/**
	 * @param bean
	 */
	public void setNotificationBO(INotificationBO bean);
	
	/**
	 * @param serviceOrder
	 */
	public void insertRecordForStatusChange(ServiceOrder serviceOrder,String orderEventName);


}

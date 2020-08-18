package com.newco.marketplace.business.businessImpl.serviceorder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOReschedulePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.utils.RandomGUID;
import com.servicelive.www.sl.integration.OutboundService.DeliveryDateUpdateType;


/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 00:40:24 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class HPSOReschedulePostProcessImpl extends HPSOPostProcessing implements ISOReschedulePostProcess {
	
	private static final Logger LOGGER = Logger.getLogger(HPSOReschedulePostProcessImpl.class);

	private static final String RESCHEDULE_METHOD_NAME = "sendDeliveryDateUpdate";
	
	/**
	 * Usually invoked by AOP this fires a webservice call 
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.ISOReschedulePostProcess#execute(java.lang.String, java.lang.Integer)
	 * @param String
	 * @param Integer
	 */
	public void execute(String serviceOrderId, Integer buyerId)
			throws BusinessServiceException {
		
		if (null != serviceOrderId && null != buyerId) {
			IServiceOrderBO bo;
			Object beanFacility = null;
			ServiceOrder serviceOrder;
			//get the service order business object from the context
			try {
				beanFacility = MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE );
			} catch (BeansException e) {
				e.printStackTrace();
			}
			
			bo = (IServiceOrderBO)beanFacility;
			serviceOrder = bo.getServiceOrder(serviceOrderId);
			
			DeliveryDateUpdateType ut = new DeliveryDateUpdateType();
			ut.setBuyerID(serviceOrder.getBuyer().getBuyerId().toString());
			ut.setSLServiceOrderID(serviceOrderId);
	
			//format the date out to "yyyyMMdd"
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			String datetime = "";
			
			Timestamp dateToSend;
			//String timeToSend;
			
			if (null != serviceOrder.getServiceDate2()) {
				dateToSend = serviceOrder.getServiceDate2();
				//timeToSend = serviceOrder.getServiceTimeEnd();
			} else {
				dateToSend = serviceOrder.getServiceDate1();
				//timeToSend = serviceOrder.getServiceTimeStart();
			}
			
			datetime = fmt.format(dateToSend);
			//datetime += " " + timeToSend;
			ut.setServiceDeliveryDate(datetime);
			
			//persist the payload
			try {
				RandomGUID random = new RandomGUID();
				WSPayloadVO vo = new WSPayloadVO();
				vo.setQueueID(random.generateGUID().longValue());
				vo.setPortName(Constants.AppPropConstants.HP_OUTBOUND_WS_URL_PORT);
				vo.setCreateTimestamp(new Date());
				vo.setMethodName(RESCHEDULE_METHOD_NAME);
				vo.setPayload(ut);
				getPayloadDao().insertPayload(vo);
			} catch (Exception e) {
				throw new BusinessServiceException("Unable to persist Reschedule Order message",e);
			}
		} else {
			throw new BusinessServiceException("Unable to persist Reschedule Order message - ServiceOrderID or BuyerID is null");
		}
		return;
	}

}
/*
 * Maintenance History
 * $Log: HPSOReschedulePostProcessImpl.java,v $
 * Revision 1.11  2008/04/26 00:40:24  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.12.1  2008/04/23 11:42:20  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:01:49  hravi
 * Reverting to build 247.
 *
 * Revision 1.9  2008/01/31 21:55:00  gjacks8
 * new outbound jar, again
 *
 * Revision 1.8  2008/01/31 15:04:11  gjacks8
 * added random gen
 *
 * Revision 1.7  2008/01/24 15:47:25  gjacks8
 * changes made for ws queue
 *
 * Revision 1.6  2008/01/09 17:52:29  mhaye05
 * now pulling buyer id from service order
 *
 * Revision 1.5  2008/01/08 21:56:11  mhaye05
 * added logic to handle range and specific reschedule dates
 *
 * Revision 1.4  2008/01/08 21:00:31  mhaye05
 * added call to parent to get port
 *
 * Revision 1.3  2008/01/08 20:54:19  gjacks8
 * filled client type code -> pass to mhayes for dynamic port impl
 *
 * Revision 1.2  2008/01/08 19:21:50  mhaye05
 * added buyer id to input parameters
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */
package com.newco.marketplace.business.businessImpl.serviceorder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeansException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOClosePostProcess;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.utils.RandomGUID;
import com.servicelive.www.sl.integration.OutboundService.ClosureOrderType;


/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 00:40:24 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class HPSOClosePostProcessImpl extends HPSOPostProcessing implements ISOClosePostProcess {
	
	private static final String CLOSE_METHOD_NAME = "sendClosureOrder";
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.serviceorder.ISOClosePostProcess#execute(java.lang.String, java.lang.Integer)
	 */
	public void execute(String serviceOrderId, Integer buyerId)
			throws BusinessServiceException {

		if (null != buyerId && null != serviceOrderId) {
			ClosureOrderType closureOrder = new ClosureOrderType();
			//get the service order for summary
			IServiceOrderBO bo;
			Object beanFacility = null;
			ServiceOrder serviceOrder;
			//get the service order business object from the context
			try {
				beanFacility = MPSpringLoaderPlugIn.getCtx().getBean( ABaseRequestDispatcher.SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE );
			} catch (BeansException e) {
				e.printStackTrace();
			}
			
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat fmtTime = new SimpleDateFormat("HH:mm");
			
			bo = (IServiceOrderBO)beanFacility;
			serviceOrder = bo.getServiceOrder(serviceOrderId);
			
			
			closureOrder.setSLServiceOrderID(serviceOrderId);
			closureOrder.setBuyerID(buyerId.toString());
			closureOrder.setStatus("CLOSED");
			closureOrder.setRepairSummary(serviceOrder.getResolutionDs());
			closureOrder.setReasonCancelled("NA");
			String closedDate = fmt.format(serviceOrder.getClosedDate());
			closureOrder.setTransactionDate(closedDate);
			String closedTime = fmtTime.format(serviceOrder.getClosedDate());
			closureOrder.setTransactionTime(closedTime);
			
			//persist the payload
			try {
				RandomGUID random = new RandomGUID();
				WSPayloadVO vo = new WSPayloadVO();
				vo.setQueueID(random.generateGUID().longValue());
				vo.setPortName(Constants.AppPropConstants.HP_OUTBOUND_WS_URL_PORT);
				vo.setCreateTimestamp(new Date());
				vo.setMethodName(CLOSE_METHOD_NAME);
				vo.setPayload(closureOrder);
				getPayloadDao().insertPayload(vo);
			
			} catch (Exception e) {
				throw new BusinessServiceException("Unable to persist Closure Order message",e);
			}
		} else {
			throw new BusinessServiceException("Unable to persist Closure Order message - ServiceOrderID or BuyerID is null");
		}
		return;
	}
}
/*
 * Maintenance History
 * $Log: HPSOClosePostProcessImpl.java,v $
 * Revision 1.12  2008/04/26 00:40:24  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.12.1  2008/04/23 11:42:20  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:01:49  hravi
 * Reverting to build 247.
 *
 * Revision 1.10  2008/02/01 08:40:50  gjacks8
 * added trans time
 *
 * Revision 1.9  2008/02/01 08:06:42  gjacks8
 * closureOrder.setReasonCancelled("NA");
 *
 * Revision 1.8  2008/02/01 07:24:16  gjacks8
 * set status
 *
 * Revision 1.7  2008/01/31 21:55:00  gjacks8
 * new outbound jar, again
 *
 * Revision 1.6  2008/01/31 15:04:11  gjacks8
 * added random gen
 *
 * Revision 1.5  2008/01/24 15:47:25  gjacks8
 * changes made for ws queue
 *
 * Revision 1.4  2008/01/11 18:55:42  gjacks8
 * added resolution description to outbound call to satisfy T10 return to HP. The provider enters a return part tracking number in this field which is passed to HP.
 *
 * Revision 1.3  2008/01/08 20:19:47  mhaye05
 * changed return type
 *
 * Revision 1.2  2008/01/08 19:21:50  mhaye05
 * added buyer id to input parameters
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */
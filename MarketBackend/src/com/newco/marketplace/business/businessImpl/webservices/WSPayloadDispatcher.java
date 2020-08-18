package com.newco.marketplace.business.businessImpl.webservices;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.webservices.IWSPayloadDispatcher;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.webservices.WebServiceQueueDao;
import com.servicelive.www.sl.integration.OutboundService.ClosureOrderType;
import com.servicelive.www.sl.integration.OutboundService.DeliveryDateUpdateType;
import com.ws.SLOutboundServicePortType;
import com.ws.SLOutboundWebServiceLocator;

/**
 * 
 * @author Gordon Jackson, Sogeti USA, LLC.
 * 
 * Gets payload objects from persistence using the WebServiceQueueDao
 * invokes the correct ws operation by reflection - the operation
 * name is saved as a string in persistence.
 *
 */
public class WSPayloadDispatcher implements IWSPayloadDispatcher {
	
	private static final Logger logger = Logger.getLogger(WSPayloadDispatcher.class);
	
	private WebServiceQueueDao payloadDao;
	private List<WSPayloadVO> payloads;
	
	/**
	 * Get the correct port name from persistence by name
	 * @param portName
	 * @return
	 */
	private SLOutboundServicePortType getPort(String portName) {
		SLOutboundServicePortType port = null;
		
		IApplicationPropertiesDao dao = (IApplicationPropertiesDao) MPSpringLoaderPlugIn.getCtx()
			.getBean(Constants.ApplicationContextBeans.APPLICATION_PROP_DAO);
		try {
			ApplicationPropertiesVO vo = dao.query(portName);
			SLOutboundWebServiceLocator client = new SLOutboundWebServiceLocator();
			port = client.getSLOutboundServicePort(new URL(vo.getAppValue()));
		} 
		catch (DataNotFoundException e) {
			logger.error(e.getMessage(),e);
		} 
		catch (DataAccessException e) {
			logger.error(e.getMessage(),e);
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return port;
	}

	/**
	 * Call the correct operation name for the payload using reflection
	 * @param vo
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void invokeMethod(WSPayloadVO vo) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		getClass().getMethod(vo.getMethodName(), new Class[] {WSPayloadVO.class}).invoke(this, new Object[] {vo});
	}

	/**
	 * Convert a byte array to an object
	 * @param bytes
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private Object unmarshallPayload(byte[] bytes) throws IOException, ClassNotFoundException {
		Object object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		return object;
	}

	/**
	 * Get the next payloads to process from persistence
	 * @return
	 */
	private List<WSPayloadVO> getPayloads() {
		return getPayloadDao().getNext10Payloads();
	}

	/**
	 * Loop thru the payloads and invoke the correct operation.
	 * Delete if successful.
	 */
	public boolean sendPayloads() {
		payloads = getPayloads();
		for (WSPayloadVO vo : payloads) {
			try {
				invokeMethod(vo);
				getPayloadDao().deletePayload(vo);
			} 
			catch (Exception e) {
				logger.error(vo.getQueueID(), e);
			} 
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public void sendClosureOrder(WSPayloadVO vo) throws Exception {
		try {
			ClosureOrderType closureOrder = (ClosureOrderType) unmarshallPayload(vo.getPayload());
			if (closureOrder.getStatus() == null) {
				closureOrder.setStatus("CLOSED");
			}
			if (closureOrder.getReasonCancelled() == null) {
				closureOrder.setReasonCancelled("NA");
			}
			if (closureOrder.getTransactionDate() == null) {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				Date date = new Date();
				String dateString = fmt.format(date);
				closureOrder.setTransactionDate(dateString);
			}
			if (closureOrder.getTransactionTime() == null) {
				SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
				Date date = new Date();
				String timeString = fmt.format(date);
				closureOrder.setTransactionTime(timeString);
			}
			if (closureOrder.getCustomerInfoUpdateFlag() == null) {
				closureOrder.setCustomerInfoUpdateFlag("-");
			}
			if (closureOrder.getReturnsComment() == null) {
				closureOrder.setReturnsComment("-");
			}
			
			getPort(vo.getPortName()).sendClosureOrder(closureOrder);
		} 
		catch (Exception e) {
			logger.error("Error calling close on queueID " + vo.getQueueID(), e);
			throw e;
		}
	}
	
	@SuppressWarnings("unused")
	public void sendDeliveryDateUpdate(WSPayloadVO vo) throws Exception {
		try {
			DeliveryDateUpdateType ut = (DeliveryDateUpdateType) unmarshallPayload(vo.getPayload());
			SLOutboundServicePortType port = getPort(vo.getPortName());
			port.sendDeliveryDateUpdate(ut);
		}
		catch (Exception e) {
			logger.error("Error calling reschedule on queueID " + vo.getQueueID(), e);
			throw e;
		}
	}

	public WebServiceQueueDao getPayloadDao() {
		return payloadDao;
	}

	public void setPayloadDao(WebServiceQueueDao payloadDao) {
		this.payloadDao = payloadDao;
	}

}

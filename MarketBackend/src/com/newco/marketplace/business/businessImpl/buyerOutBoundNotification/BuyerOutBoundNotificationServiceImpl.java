package com.newco.marketplace.business.businessImpl.buyerOutBoundNotification;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestHeader;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestJobcode;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMessage;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMessages;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestMsgBody;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrder;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestOrders;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestReschedInformation;
import com.newco.marketplace.buyeroutboundnotification.beans.RequestRescheduleInfo;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.newco.marketplace.buyeroutboundnotification.service.IBuyerOutBoundNotificationService;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerNotificationStatusEnum;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundFailOverVO;
import com.newco.marketplace.buyeroutboundnotification.vo.BuyerOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.CustomerInformationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.CustomrefsOmsVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestMessageVO;
import com.newco.marketplace.buyeroutboundnotification.vo.RequestVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOCustRefOutboundNotificationVO;
import com.newco.marketplace.buyeroutboundnotification.vo.SOLocationNotesOutboundNotificationVO;
import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.buyerOutBoundNotification.BuyerOutBoundNotificationDao;
import com.newco.marketplace.util.TimeChangeUtil;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.thoughtworks.xstream.XStream;


public  class BuyerOutBoundNotificationServiceImpl implements
		IBuyerOutBoundNotificationService {

	// Create the logger
	private static final Logger LOGGER = Logger
			.getLogger(BuyerOutBoundNotificationServiceImpl.class.getName());
	private BuyerOutBoundNotificationDao buyerOutBoundNotificationDao;
	private IApplicationProperties applicationProperties;

	public BuyerOutBoundNotificationDao getBuyerOutBoundNotificationDao() {
		return buyerOutBoundNotificationDao;
	}

	public void setBuyerOutBoundNotificationDao(
			BuyerOutBoundNotificationDao buyerOutBoundNotificationDao) {
		this.buyerOutBoundNotificationDao = buyerOutBoundNotificationDao;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	// call service by passing request Object.
	public BuyerOutboundFailOverVO callService(RequestMsgBody requestMsgBody, String soId) throws BusinessServiceException {

		// 1. call getCustRefOms & set 4 values in Request Order
		// 2.checkNotification
		// 3.set header in requestMsgBody
		// 4.insert
		String notificationSwitch = getPropertyFromDB(MPConstants.NPS_NOTIFICATION_SWITCH);
		if(StringUtils.equalsIgnoreCase(BuyerOutBoundConstants.TRUE, notificationSwitch)){
			CustomrefsOmsVO customRef = getCustomReferenceOms(soId);
			if(null!=customRef)
			{
			RequestOrders orders = new RequestOrders();
			List<RequestOrder> order = new ArrayList<RequestOrder>();
			RequestOrder requestOrder = requestMsgBody.getOrders().getOrder()
					.get(0);
			requestOrder.setClientid(BuyerOutBoundConstants.CLIENT_ID);
			
			requestOrder.setServiceUnitNumber(customRef
					.getUnitNumber());
			
			requestOrder.setServiceOrderNumber(customRef.getOrderNumber());
			requestOrder.setSalesDate(customRef.getSalesCheckDate());
			requestOrder.setSalescheck(customRef.getSalesCheckNumber());
			order.add(requestOrder);
			orders.setOrder(order);
			requestMsgBody.setOrders(orders);
			checkNotification(requestMsgBody, soId);
	
			// TODO : Call the JMS only if the insert is successful
			
			// If there is a reschedule, the Reschedule Modification unit number 
			// should be set and it should be equal to Service UnitNumber
			
			// Assumption : There is only one order and it is present always
			RequestReschedInformation information = requestMsgBody.getOrders().getOrder().get(0).
					getRequestReschedInformation();
			
			if(null!=information && null != information.getRequestRescheduleInf() && 
					information.getRequestRescheduleInf().size()>0){
				if(BuyerOutBoundConstants.RESHEDULE_FLAG_YES.equalsIgnoreCase(
						requestMsgBody.getOrders().getOrder().get(0).getServiceOrderRescheduledFlag())){
					requestMsgBody.getOrders().getOrder().get(0).
						getRequestReschedInformation().getRequestRescheduleInf().
						get(0).setRescheduleModificationUnitNo(customRef
								.getUnitNumber());
				}
			}
			BuyerOutboundFailOverVO failoverVO  = insertBuyerOutboundNotification(requestMsgBody, soId);
			if(null!=failoverVO){
				LOGGER.info("Completed the insert to DB::"+failoverVO.getSeqNO());
				
			}
			
			return failoverVO;
			}
			else
			{
				return null;
			}
		}else{
				return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.buyeroutboundnotification.service.
	 *      IBuyerOutBoundNotificationService#getWebServiceDetails(int) This
	 *      method fetches details for buyer.
	 */
	public RequestVO getWebServiceDetails(int buyerId)
			throws BusinessServiceException {
		RequestVO requestVO = new RequestVO();
		try {
			requestVO = buyerOutBoundNotificationDao.getWebServiceDetails(buyerId);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the VOobject due to"
							+ dse.getMessage(), dse);

		}
		return requestVO;
	}
	
	
	public String getMockedUpData(String data){
		String value = null;
		try {
			 value =  applicationProperties.getPropertyFromDB(data);
		} catch (DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.buyeroutboundnotification.service.
	 *      IBuyerOutBoundNotificationService
	 *      #insertBuyerOutboundNotification(com.newco
	 *      .marketplace.buyeroutboundnotification.beans.RequestMsgBody,
	 *      java.lang.String) Inserts a new row in table
	 *      buyer_outbound_notification .
	 */
	@SuppressWarnings("unchecked")
	public BuyerOutboundFailOverVO insertBuyerOutboundNotification(RequestMsgBody requestMsgBody,
			String soId) throws BusinessServiceException {
		try {
			BuyerOutboundFailOverVO failoverVO = new BuyerOutboundFailOverVO();
			Integer defaultNoOfRetries = 0;
			try {
				defaultNoOfRetries = Integer.valueOf(buyerOutBoundNotificationDao
						.getPropertyFromDB(BuyerOutBoundConstants.NO_OF_RETRIES_FOR_NPS_NOTIFICATION));
			} catch (Exception e) {
				LOGGER.error("Failed to fetch value from app properties tables.Setting to defaults.. ",e);
				defaultNoOfRetries = BuyerOutBoundConstants.NO_OF_RETRIES;
			}
			
			failoverVO.setSoId(soId);
			failoverVO.setServiceId(1);
			failoverVO.setSeqNO(generateSeqNo());
			RequestHeader requestHeader = new RequestHeader();
			requestHeader.setNoOfOrders(BuyerOutBoundConstants.NO_OF_ORDERS);
			requestHeader.setSeqNo(failoverVO.getSeqNO());
			requestMsgBody.setHeader(requestHeader);
			XStream xstream = new XStream();
			Class[] classes = new Class[] { RequestMsgBody.class,
					RequestHeader.class, RequestOrders.class,
					RequestOrder.class, RequestReschedInformation.class,
					RequestJobcode.class, RequestRescheduleInfo.class };
			xstream.processAnnotations(classes);
			String createResponseXml = (String) xstream.toXML(requestMsgBody);
			createResponseXml = BuyerOutBoundConstants.XML_VERSION
					+ createResponseXml;
			failoverVO.setXml(createResponseXml);
			
			failoverVO.setNoOfRetries(defaultNoOfRetries+1);
			failoverVO.setActiveInd(false);
			failoverVO.setStatus(BuyerNotificationStatusEnum.STARTED.name());
			failoverVO.setCreatedDate(new Date());
			buyerOutBoundNotificationDao.insertBuyerOutboundNotification(failoverVO);
			return failoverVO;
		} catch (DataServiceException dse){
			LOGGER.error("Exception in inserting due to" + dse.getMessage(),
					dse);
			throw new BusinessServiceException(
					"Exception in getting the VOobject due to"
							+ dse.getMessage(), dse);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.buyeroutboundnotification.service.
	 *      IBuyerOutBoundNotificationService#generateSeqNo() Generates Sequence
	 *      Number for table buyer_outbound_notification .
	 */
	public String generateSeqNo() throws BusinessServiceException {
		String seqNo = new String();
		try {
			seqNo = buyerOutBoundNotificationDao.generateSeqNO().toString();
		} catch (DataServiceException bse) {
			LOGGER.error("Exception in fetching count due to"
					+ bse.getMessage(), bse);
			// throw new BusinessServiceException
			// ("Exception in getting the sequence No. due to"
			// + bse.getMessage(), bse);
		} catch (Exception bse) {
			LOGGER.error("Exception in fetching count due to"
					+ bse.getMessage(), bse);
			// throw new BusinessServiceException
			// ("Exception in getting the sequence No. due to"
			// + bse.getMessage(), bse);
		}
		int length = seqNo.length();
		length = 10 - length;
		for (int i = 0; i < length; i++) {
			seqNo = BuyerOutBoundConstants.SEQ_ZERO + seqNo;

		}
		String hypen1 = seqNo.substring(0, 3);
		String hypen2 = seqNo.substring(3, 7);
		String hypen3 = seqNo.substring(7, 10);
		seqNo = hypen1 + BuyerOutBoundConstants.SEQ_HYP + hypen2 + BuyerOutBoundConstants.SEQ_HYP + hypen3;
		
		return seqNo;

	}
//get service order details for an so
	public HashMap<String, BuyerOutboundNotificationVO> getServiceOrderDetails(
			List<String> soIdList) throws BusinessServiceException {

		HashMap<String, BuyerOutboundNotificationVO> hashMap = new HashMap<String, BuyerOutboundNotificationVO>();
		try {
			hashMap = buyerOutBoundNotificationDao
					.getServiceOrderDetails(soIdList);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return hashMap;
	}
	
	// get schedule details of service order
	public BuyerOutboundNotificationVO getScheduleDetails(String soId)
			throws BusinessServiceException {
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = null;
		try {
			buyerOutboundNotificationVO = buyerOutBoundNotificationDao
					.getScheduleDetails(soId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
//			throw new BusinessServiceException(
//					"Exception in getting the buyerOutboundNotificationVO object due to"
//							+ dse.getMessage(), dse);
		}
		return buyerOutboundNotificationVO;
	}

	// get reason Code for reschedule
	public String getReasonCode(Integer reasonCodeId)

	{
		String reasonCode = null;
		try {
			reasonCode = (String) buyerOutBoundNotificationDao
					.getReasonCode(reasonCodeId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);

		}
		return reasonCode;
	}
	
	// below method added as part of SL-19240
	public String getRescheduleReason(Integer reasonCodeId)
	{
		String reason = null;
		try {
			reason = (String) buyerOutBoundNotificationDao
					.getRescheduleReason(reasonCodeId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting getRescheduleReason from DB due to "
							+ dse.getMessage(), dse);
		}
		return reason;
	}
	
	// get the logging comment for reschedule to obtaion reason Code & comment.
	public BuyerOutboundNotificationVO getLoggingDetails(String soId)
			throws BusinessServiceException {
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = null;
		try {
			buyerOutboundNotificationVO = buyerOutBoundNotificationDao
					.getLoggingDetails(soId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return buyerOutboundNotificationVO;
	}
	
	
	// get counter Offer details for service order.
	public BuyerOutboundNotificationVO getCounterOfferDetails(
			BuyerOutboundNotificationVO counterOffer)
			throws BusinessServiceException {
		BuyerOutboundNotificationVO buyerOutboundNotificationVO = null;
		try {
			buyerOutboundNotificationVO = buyerOutBoundNotificationDao
					.getCounterOfferDetails(counterOffer);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return buyerOutboundNotificationVO;
	}
	
	// get counter Offer details for all service order in a group
	public List<BuyerOutboundNotificationVO> getCounterOfferDetailsForGroup(
			BuyerOutboundNotificationVO counterOffer)
			throws BusinessServiceException {
		List<BuyerOutboundNotificationVO> buyerOutboundNotificationList = null;
		try {
			buyerOutboundNotificationList = buyerOutBoundNotificationDao
					.getCounterOfferDetailsForGroup(counterOffer);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return buyerOutboundNotificationList;
	}
	
	// get all soIds in a grouped order.
	public List<String> getSoIdsForGroup(String groupId)
			throws BusinessServiceException {
		List<String> soIds = null;
		try {
			soIds = buyerOutBoundNotificationDao.getSoIdsForGroup(groupId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return soIds;
	}
	/**
	 * Get the service location notes - this is the special instructions from
	 * NPS
	 */
	public SOLocationNotesOutboundNotificationVO getLocationNotes(String soId)
			throws BusinessServiceException {
		SOLocationNotesOutboundNotificationVO locationVO = new SOLocationNotesOutboundNotificationVO();
		try {
			locationVO = buyerOutBoundNotificationDao.getLocationNotes(soId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return locationVO;
	}

	/**
	 * Get the brand,serial number and the model number
	 */
	public List<SOCustRefOutboundNotificationVO> getCustRefs(String soId)
			throws BusinessServiceException {
		List<SOCustRefOutboundNotificationVO> custRefVO = new ArrayList<SOCustRefOutboundNotificationVO>();
		try {
			custRefVO = buyerOutBoundNotificationDao.getCustRefs(soId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the buyerOutboundNotificationVO object due to"
							+ dse.getMessage(), dse);
		}
		return custRefVO;
	}



	

	

	public String fetchResponse() throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	

	public CustomrefsOmsVO getCustomReferenceOms(String soId)
			throws BusinessServiceException {

		CustomrefsOmsVO customref = new CustomrefsOmsVO();
		Integer orderId = buyerOutBoundNotificationDao.getOrderNo(soId);
		if (null == orderId) {
			return null;
		}
		try {
			customref = buyerOutBoundNotificationDao
					.getCustomReferenceOms(orderId);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the VOobject due to"
							+ dse.getMessage(), dse);
		}
		return customref;
	}
	
	public List<Integer> getTierRoutedProviders(String soId)throws BusinessServiceException {

		List<Integer> providerList =new ArrayList<Integer>();
		try {
			providerList = buyerOutBoundNotificationDao
			.getTierRoutedProviders(soId);
		} catch (DataServiceException dse) {
			LOGGER.info("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
			"Exception in getting the VOobject due to"
					+ dse.getMessage(), dse);
		}
		return providerList;
	}

	/**
	 * Compare the details with the data from DataBase to see whether the data
	 * has been changed
	 */
	public RequestMsgBody getNPSNotificationRequest(ServiceOrder fromFrontEnd,
			BuyerOutboundNotificationVO compareTo,
			String modifiedUserId) throws BusinessServiceException {
		RequestMsgBody requestMsgBody = new RequestMsgBody();
		try {
			requestMsgBody = getNPSNotificationRequestDetails(fromFrontEnd,
					compareTo, modifiedUserId);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requestMsgBody;
	}
//updating sucess Indicator
	public void updateSuccessIndicator(String seqNo, boolean failureInd)
			throws BusinessServiceException {

		try {
			buyerOutBoundNotificationDao.updateSuccessIndicator(seqNo,
					failureInd);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in Updating data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting the VOobject due to"
							+ dse.getMessage(), dse);
		}

	}

	// getting values for the previous failed API and incorporating in new request xml
	public void checkNotification(RequestMsgBody newRequest, String soId)
	throws BusinessServiceException {


if (null == newRequest.getOrders().getOrder().get(0)
		.getMessages()
		|| null==newRequest.getOrders().getOrder().get(0)
				.getMessages().getRequestMessage() || newRequest.getOrders().getOrder().get(0)
				.getMessages().getRequestMessage().size()==0) {
	try {
		BuyerOutboundFailOverVO buyerOutboundFailOverVO = new BuyerOutboundFailOverVO();
		
		buyerOutboundFailOverVO=buyerOutBoundNotificationDao
				.getRequestWithoutNotes(soId);
		
		
		if (null != buyerOutboundFailOverVO) {
			String targetXml = buyerOutboundFailOverVO.getXml();
			RequestMsgBody oldRequest = getRequestMsgBody(targetXml);
			RequestOrder oldOrderRequest = oldRequest.getOrders()
					.getOrder().get(0);
			RequestOrder newOrderRequest = newRequest.getOrders()
					.getOrder().get(0);
			
			
			RequestRescheduleInfo resheduleOldInformation = null;
			if(null!=oldOrderRequest
					.getRequestReschedInformation() && null!=oldOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf() &&
					oldOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf().size()>0)
			{
				resheduleOldInformation=oldOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf().get(0);
			}
			RequestRescheduleInfo resheduleNewInformation = null;
			if(null!=newOrderRequest
					.getRequestReschedInformation() &&
					null!=newOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf()
					&&
					newOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf().size()>0
					
			)
			{
				resheduleNewInformation=newOrderRequest
					.getRequestReschedInformation()
					.getRequestRescheduleInf().get(0);
			}
			
			//setting customer information
			if (!StringUtils.isBlank(oldOrderRequest
					.getCustomerAddress())
					&& StringUtils.isBlank(newOrderRequest
							.getCustomerAddress())) {
				newOrderRequest.setCustomerAddress(oldOrderRequest
						.getCustomerAddress());
			}
			if (!StringUtils.isBlank(oldOrderRequest.getCustomerCity())
					&& StringUtils.isBlank(newOrderRequest
							.getCustomerCity())) {
				newOrderRequest.setCustomerCity(oldOrderRequest
						.getCustomerCity());
			}
			if (!StringUtils
					.isBlank(oldOrderRequest.getCustomerState())
					&& StringUtils.isBlank(newOrderRequest
							.getCustomerState())) {
				newOrderRequest.setCustomerState(oldOrderRequest
						.getCustomerState());
			}
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceOrderSpecialInstruction1())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceOrderSpecialInstruction1())) {
				newOrderRequest
						.setServiceOrderSpecialInstruction1(oldOrderRequest
								.getServiceOrderSpecialInstruction1());
			}
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceOrderSpecialInstruction2())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceOrderSpecialInstruction2())) {
				newOrderRequest
						.setServiceOrderSpecialInstruction2(oldOrderRequest
								.getServiceOrderSpecialInstruction2());
			}
			
			
			if (null!=oldOrderRequest
					.getCustomerZipCode()
					&& null==newOrderRequest
							.getCustomerZipCode()) {
				newOrderRequest.setCustomerZipCode(oldOrderRequest
						.getCustomerZipCode());
			}
			if (null!=oldOrderRequest
					.getCustomerPrimaryPhoneNumber()
					&& null==newOrderRequest
									.getCustomerPrimaryPhoneNumber()
									) {
				newOrderRequest
						.setCustomerPrimaryPhoneNumber(oldOrderRequest
								.getCustomerPrimaryPhoneNumber());
			}
			if (null!=oldOrderRequest
					.getCustomerZipCodeSuffix()
					&& null==newOrderRequest
							.getCustomerZipCodeSuffix()) {
				newOrderRequest
						.setCustomerZipCodeSuffix(oldOrderRequest
								.getCustomerZipCodeSuffix());
			}
			
			
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceOrderRescheduledFlag())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceOrderRescheduledFlag())) {
				newOrderRequest
						.setServiceOrderRescheduledFlag(oldOrderRequest
								.getServiceOrderRescheduledFlag());
			}
			
			
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceScheduleDate())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceScheduleDate())) {
				newOrderRequest
						.setServiceScheduleDate(oldOrderRequest
								.getServiceScheduleDate());
			}
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceScheduleFromTime())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceScheduleFromTime())) {
				newOrderRequest
						.setServiceScheduleFromTime(oldOrderRequest
								.getServiceScheduleFromTime());
			}
			
			if (!StringUtils.isBlank(oldOrderRequest
					.getServiceScheduletoTime())
					&& StringUtils.isBlank(newOrderRequest
							.getServiceScheduletoTime())) {
				 
				newOrderRequest
						.setServiceScheduletoTime(oldOrderRequest
								.getServiceScheduletoTime());
			}
			
			
			//setting reschedule information
			if (resheduleNewInformation == null && resheduleOldInformation!=null) {
				resheduleNewInformation = resheduleOldInformation;
			
				}
			//
			RequestReschedInformation requestReschedInformation=new RequestReschedInformation();
			List<RequestRescheduleInfo> requestRescheduleInf=new ArrayList<RequestRescheduleInfo>();
			requestRescheduleInf.add(resheduleNewInformation);
			requestReschedInformation.setRequestRescheduleInf(requestRescheduleInf);
			newOrderRequest.setRequestReschedInformation(requestReschedInformation);
			
			RequestOrders orders=new RequestOrders();
			List<RequestOrder> orderList=new ArrayList<RequestOrder>();
	    	orderList.add(newOrderRequest);
	    	orders.setOrder(orderList);
	    	newRequest.setOrders(orders);
	    	//
	    	BuyerOutboundFailOverVO vo = new BuyerOutboundFailOverVO();
			vo.setSeqNO(buyerOutboundFailOverVO.getSeqNO());
			vo.setActiveInd(false);
			//mark the active_ind as 0 and status as excluded
			buyerOutBoundNotificationDao.excludeNotification(buyerOutboundFailOverVO);
			}
		
		
		
	} catch (Exception e) {
		LOGGER.error("Exception in Updating ActiveIndicator "
				+ e.getMessage(), e);
		// throw new BusinessServiceException(
		// "Exception in Updating ActiveIndicator"
		//								+ e.getMessage(), e);
	}
}

}

	public RequestMsgBody getRequestMsgBody(String xml) {
		LOGGER.info("Entering XStreamUtility.getRequestMsgBody()");
		XStream xstream = new XStream();
		Class[] classes = new Class[] { RequestMsgBody.class,
				RequestHeader.class, RequestOrders.class, RequestOrder.class,
				RequestReschedInformation.class, RequestJobcode.class,
				RequestRescheduleInfo.class };
		xstream.processAnnotations(classes);
		RequestMsgBody msgBody = (RequestMsgBody) xstream.fromXML(xml);
		return msgBody;

	}

	/**
	 * See whether there are any changes
	 * 
	 * @param fronFrontEnd
	 * @param compareTo
	 * @return
	 * @throws DataServiceException
	 */
	private RequestMsgBody getNPSNotificationRequestDetails(
			ServiceOrder fromFrontEnd, BuyerOutboundNotificationVO compareTo,
			String modifiedUserId) throws DataServiceException {
		RequestMsgBody requestMsgBody = new RequestMsgBody();
		RequestOrder order = new RequestOrder();
		RequestOrders orders = new RequestOrders();
		List<RequestOrder> orderList = new ArrayList<RequestOrder>();
        boolean custFlag=false;
        boolean resheduleFlag = false;
        boolean zipFlag=false;
        // ONLY for Sears RI
		if (null != fromFrontEnd && fromFrontEnd.getBuyer().getBuyerId()== 1000) {
             if(null!=compareTo && null!=compareTo.getCustomerInfoVo()){
				Contact contactfromFE = fromFrontEnd.getServiceContact();
				SoLocation locationfromFE = fromFrontEnd.getServiceLocation();
				CustomerInformationVO custInfo = compareTo.getCustomerInfoVo();
				String addressFromFrontEnd = locationfromFE.getStreet1()+locationfromFE.getStreet2()+locationfromFE.getAptNo();
				String addressFromDB =custInfo.getCustomerAddress1()+custInfo.getCustomerAddress2()+custInfo.getApt();
				String city = locationfromFE.getCity();
				String zipSuffix = locationfromFE.getZip4();
				if (!StringUtils.equalsIgnoreCase(city,custInfo.getCustomerCity())) {
					order.setCustomerCity(city);
					zipFlag=true;
					custFlag=true;
					}
				if (!StringUtils.equalsIgnoreCase(addressFromFrontEnd,addressFromDB)) {
					order.setCustomerAddress(locationfromFE.getStreet1()+" "+locationfromFE.getStreet2()+" "+locationfromFE.getAptNo());
					zipFlag=true;
					custFlag=true;
					}
				if (!StringUtils.equalsIgnoreCase(zipSuffix,
						custInfo.getCustomerZipSuffix())) {
					if (null!=zipSuffix) {
						if (null != fromFrontEnd
								&& StringUtils.isNotBlank(fromFrontEnd.getSoId())
								&& fromFrontEnd.getSoId().equals(compareTo.getSoId())) {
							order.setCustomerZipCodeSuffix(zipSuffix);
							custFlag = true;
						}else if (zipFlag){
							order.setCustomerZipCodeSuffix(zipSuffix);
							custFlag = true;
						}
					}
				}
				// Compare the primary phone number
				List<PhoneVO> phoneList = contactfromFE.getPhones();
				String primaryPhoneNo =null;
				String alternatePhoneNo=null;
				for (PhoneVO phone : phoneList) {
					if (phone.getPhoneType() == 1 && phone.getClassId() == 0) {
						primaryPhoneNo = phone.getPhoneNo();}
					if(phone.getPhoneType() == 2 && phone.getClassId() == 1){
						alternatePhoneNo=phone.getPhoneNo();
					}
				}
				/* Grouped Order-Editing zip suffix,primary and alternate phone no 
				 * is reflected in current order only and not other child service 
				 * orders in the group */
				if (null != fromFrontEnd
						&& StringUtils.isNotBlank(fromFrontEnd.getSoId())
						&& fromFrontEnd.getSoId().equals(compareTo.getSoId())) {
					if (!StringUtils.equalsIgnoreCase(primaryPhoneNo,
							custInfo.getCustomerPrimaryPhoneNumber())) {
						if (null!=primaryPhoneNo) {
							order.setCustomerPrimaryPhoneNumber(primaryPhoneNo);
							custFlag = true;
						}
					}
					if (!StringUtils.equalsIgnoreCase(alternatePhoneNo,
							custInfo.getCustomerAlternatePhonenumber())) {
						// If there is a change in the alternatePhone Number and is empty
						if(null != custInfo.getCustomerAlternatePhonenumber() 
								&& null == alternatePhoneNo ){
							alternatePhoneNo = "";
						}
						if (null!=alternatePhoneNo) {
							order.setCustomerAlternatePhoneNumber(alternatePhoneNo);
							custFlag = true;
						}
					}
				}
				if(custFlag){
					if(null == order.getCustomerCity()){
						order.setCustomerCity(StringUtils.isBlank(custInfo.getCustomerCity())?"":custInfo.getCustomerCity());
					}
					if(null==order.getCustomerZipCodeSuffix()){
						order.setCustomerZipCodeSuffix(StringUtils.isBlank(custInfo.getCustomerZipSuffix())?"":custInfo.getCustomerZipSuffix());
					}
					if(null==order.getCustomerPrimaryPhoneNumber()){
						order.setCustomerPrimaryPhoneNumber(StringUtils.isBlank(custInfo.getCustomerPrimaryPhoneNumber())?"":custInfo.getCustomerPrimaryPhoneNumber());
					}
					if(null==order.getCustomerAlternatePhoneNumber()){
						order.setCustomerAlternatePhoneNumber(StringUtils.isBlank(custInfo.getCustomerAlternatePhonenumber())?"":custInfo.getCustomerAlternatePhonenumber());
					}
					//Added Fix for SL-18769
					order.setCustomerState(StringUtils.isBlank(custInfo.getCustomerState())?"":custInfo.getCustomerState());
					if(null!=custInfo.getCustomerZipCode()){
						order.setCustomerZipCode(custInfo.getCustomerZipCode());
					}
					if(null == order.getCustomerAddress()){
						order.setCustomerAddress(StringUtils.isBlank(locationfromFE.getStreet1())?"":locationfromFE.getStreet1()+" "+
								(StringUtils.isBlank(locationfromFE.getStreet2())?"":locationfromFE.getStreet2())+" "
								+(StringUtils.isBlank(locationfromFE.getAptNo())?"":locationfromFE.getAptNo()));
					}
					
				}
			}			
             


			// 4. Compare the schedule information
			// - The current schdule
			// - If there is a reschdule 'Y' and the reschdule details
			// This method convert the shedule information to the GMT
			Calendar startDateAndTime = null;
			Calendar endDateAndTime = null;
			String defaultEndTime="";
			
			DateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd");
			if (null != fromFrontEnd && null != fromFrontEnd.getServiceDate1()
					&& null != fromFrontEnd.getServiceTimeStart()) {
				Date datePartStart = fromFrontEnd.getServiceDate1();
				String timePartStart = fromFrontEnd.getServiceTimeStart();
				TimeZone timeZoneStart = TimeZone.getTimeZone(fromFrontEnd
						.getServiceLocationTimeZone());
				startDateAndTime = TimeChangeUtil.getCalTimeFromParts(
						datePartStart, timePartStart, timeZoneStart);
			}
			if (null != fromFrontEnd && null != fromFrontEnd.getServiceDate2()
					&& null != fromFrontEnd.getServiceTimeEnd()) {
				Date datePartEnd = fromFrontEnd.getServiceDate2();
				String timePartEnd = fromFrontEnd.getServiceTimeEnd();
				TimeZone timeZoneEnd = TimeZone.getTimeZone(fromFrontEnd
						.getServiceLocationTimeZone());

				endDateAndTime = TimeChangeUtil.getCalTimeFromParts(
						datePartEnd, timePartEnd, timeZoneEnd);
			}
			if (null != startDateAndTime) {
				Date serviceDate1GMT = TimeChangeUtil.getDate(startDateAndTime,TimeZone.getTimeZone("GMT"));
				String serviceTimeStartGMT = TimeChangeUtil.getTimeString(startDateAndTime, TimeZone.getTimeZone("GMT"));
				Date serviceDate1FromDB=compareTo.getServiceOrderScheduleFromDate();
				String formattedserviceDate1GMT=formatDate.format(serviceDate1GMT);
				String formattedserviceDate1FromDB=formatDate.format(serviceDate1FromDB);
				if (!StringUtils.equalsIgnoreCase(formattedserviceDate1FromDB,formattedserviceDate1GMT)) {
					order.setServiceScheduleDate(formatDate.format(fromFrontEnd.getServiceDate1()));
					    order.setServiceScheduleFromTime(fromFrontEnd.getServiceTimeStart());
					    order.setServiceScheduletoTime(fromFrontEnd.getServiceTimeEnd());
					resheduleFlag = true;
				        } 
				 if (!StringUtils.equalsIgnoreCase(compareTo.getServiceOrderScheduleFromTime(),serviceTimeStartGMT)) {
						order.setServiceScheduleDate(formatDate.format(fromFrontEnd.getServiceDate1()));
						order.setServiceScheduleFromTime(fromFrontEnd.getServiceTimeStart());
						order.setServiceScheduletoTime(fromFrontEnd.getServiceTimeEnd());
						resheduleFlag = true;
					}
				}
			
			if (null != endDateAndTime) {
				String serviceTimeEndGMT = TimeChangeUtil.getTimeString(endDateAndTime, TimeZone.getTimeZone("GMT"));
				if (!StringUtils.equalsIgnoreCase(compareTo.getServiceOrderScheduleToTime(),serviceTimeEndGMT)) {
						order.setServiceScheduleDate(formatDate.format(fromFrontEnd.getServiceDate1()));
						order.setServiceScheduleFromTime(fromFrontEnd.getServiceTimeStart());
						order.setServiceScheduletoTime(fromFrontEnd.getServiceTimeEnd());
						resheduleFlag = true;
					}
				
		   	} else {
				if (null != order.getServiceScheduleDate()
						&& null != order.getServiceScheduleFromTime()
						&& null == order.getServiceScheduletoTime()) {
					if (null != fromFrontEnd.getServiceTimeEnd()) {
						order.setServiceScheduletoTime(fromFrontEnd
								.getServiceTimeEnd());
					} else {
						// Set the from time as to time
						order.setServiceScheduletoTime(fromFrontEnd.
								getServiceTimeStart());
						//order.setServiceScheduletoTime(defaultEndTime);
					}
				}

			}
			 
			 

			// Reshedule Details to populate based on resheduleFlag
			if (resheduleFlag) {
				List<RequestRescheduleInfo> rescheduleInfos =new ArrayList<RequestRescheduleInfo>();
						
				RequestReschedInformation information = new RequestReschedInformation();
				RequestRescheduleInfo resheduleInfo = new RequestRescheduleInfo();
				DateFormat formatModifiedDate = new SimpleDateFormat("yyyy-MM-dd");
				DateFormat formatModifiedTime = new SimpleDateFormat("hh:mm a");
				//String modifiedDate = formatModifiedDate.format(new Date(System.currentTimeMillis()));
				//String modifiedTime=formatModifiedTime.format(new Date(System.currentTimeMillis()));
				Calendar calender=Calendar.getInstance();
				String modifiedfromDate=formatModifiedTime.format(calender.getTime());
				Calendar modificationDateTime = TimeChangeUtil.getCalTimeFromParts(calender.getTime(), modifiedfromDate,calender.getTimeZone());
				Date modificationDate = TimeChangeUtil.getDate(modificationDateTime, TimeZone.getTimeZone(fromFrontEnd.getServiceLocationTimeZone()));
				String modificationDateValue=formatModifiedDate.format(modificationDate);
				String modificationTime = TimeChangeUtil.getTimeString(modificationDateTime,TimeZone.getTimeZone(fromFrontEnd.getServiceLocationTimeZone()));
				resheduleInfo.setReschedCancelModificationDate(modificationDateValue);
				resheduleInfo.setReschedCancelModificationTime(modificationTime);
				resheduleInfo.setRescheduleModificationID(modifiedUserId);
				resheduleInfo.setRescheduleReasonCode(BuyerOutBoundConstants.RESHEDULE_REASON_CODE);
				resheduleInfo.setRescheduleRsnCdDescription(BuyerOutBoundConstants.RESHEDULE_REASON_CODE);
				rescheduleInfos.add(resheduleInfo);
				information.setRequestRescheduleInf(rescheduleInfos);
				order.setRequestReschedInformation(information);
				order.setServiceOrderRescheduledFlag(BuyerOutBoundConstants.RESHEDULE_FLAG_YES);
			}
			
			orderList.add(order);
			orders.setOrder(orderList);
			requestMsgBody.setOrders(orders);

		}
		if(custFlag || resheduleFlag){
		      return requestMsgBody;}
		else{
			return null;
		}
	}



	public List<BuyerOutboundFailOverVO> fetchRecords()
			throws BusinessServiceException {
		List<BuyerOutboundFailOverVO> failureVOList = new ArrayList<BuyerOutboundFailOverVO>();
		try {

			failureVOList = buyerOutBoundNotificationDao.fetchRecords();
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in fetching the VOobject due to"
							+ dse.getMessage(), dse);
		}

		return failureVOList;
	}

	public void updateNotification(BuyerOutboundFailOverVO failOverVO)
			throws BusinessServiceException {
		try {
			buyerOutBoundNotificationDao.updateNotification(failOverVO);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
		}
	}


	
	public void insertInAlertTask(AlertTaskVO alertTask)
			throws BusinessServiceException {
		try {
			buyerOutBoundNotificationDao.insertInAlertTask(alertTask);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);

		}
	}
	
	public void addAlertToQueue(AlertTask alertTask)
			throws BusinessServiceException {

		try {
			buyerOutBoundNotificationDao.addAlertToQueue(alertTask);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in inserting the alert task due to"
							+ dse.getMessage(), dse);

		}

	}

	
	
    //Check these methods ????
	public void callNPSNotification(String soId) {
		// TODO Auto-generated method stub
		
	}

	
	public RequestMsgBody getNPSNotificationRequest(ServiceOrder targetSo,
			BuyerOutboundNotificationVO sourceSo)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Create the request with notes
	 */
	public RequestMsgBody getNPSNotificationRequestForNotes(
			RequestMessageVO soNote) throws BusinessServiceException {
		RequestMsgBody requestMsgBody = new RequestMsgBody();
		RequestOrder order = new RequestOrder();
		RequestOrders orders = new RequestOrders();
		List<RequestMessage> messageList = new ArrayList<RequestMessage>();
		RequestMessages messages = new RequestMessages();
		List<RequestOrder> orderList = new ArrayList<RequestOrder>();
		// setting created date and time in given timeZone
		try {
		// String timeZone=buyerOutBoundNotificationDao.getServiceLocationTimeZone(soNote.getSoId());
		String timeZone = BuyerOutBoundConstants.TIMEZONE_NPS_MESSGE;
		DateFormat formatModifiedDate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatModifiedTime = new SimpleDateFormat("HH:mm:ss");
		String modificationDateValue="";
		String modificationTime="";
		String modificationTimeAdded="";
		int secondsToAdd = 1; 
		Calendar modificationDateTime = null;
		Calendar calender=Calendar.getInstance();
		if(StringUtils.isNotBlank(timeZone)){
			String modifiedfromDate=formatModifiedTime.format(calender.getTime());
			modificationDateTime = TimeChangeUtil.getCalTimeFromPartsWithSeconds(calender.getTime(), modifiedfromDate,calender.getTimeZone());
			Date modificationDate = TimeChangeUtil.getDateSec(modificationDateTime, TimeZone.getTimeZone(timeZone));
			modificationDateValue= formatModifiedDate.format(modificationDate);
		    modificationTime = TimeChangeUtil.getTimeStringSec(modificationDateTime,TimeZone.getTimeZone(timeZone));
		}else{
		      modificationDateValue = formatModifiedDate.format(new Date(System.currentTimeMillis()));
		      modificationTime=formatModifiedTime.format(new Date(System.currentTimeMillis()));}
		if (null != soNote) {
			
			/* NPS accepts only 132 characters in one message. Hence divide the message
			 * in to different parts each having up to 132 characters. ServiceLive can
			 * have maximum 1006 characters (Subject[255]+SPACE[1]+Note[755]) as part of message 
			 * NPS can accept maximum 9 such messages in a single web service request*/
			
			List<String> serviceLiveMessages = getParts(soNote.getServiceOrderTxtDS(),
					BuyerOutBoundConstants.MESSAGE_LENGTH.intValue());
			for(int count = 0;count<serviceLiveMessages.size() && 
					count< BuyerOutBoundConstants.MAX_NPS_ALLOWED_MESSAGE_COUNT;count++){
				RequestMessage message = new RequestMessage();
				message.setServiceOrderTxtDS(serviceLiveMessages.get(count));
				message.setServiceOrderTxtDSModDate(modificationDateValue);
				
				// for each messages , add 1 minute to the modification time
				// since NPS cannot accept the more than one message with same time
				if(count == 0){
					message.setServiceOrderTxtDSModTime(modificationTime);
				}else{
					if(StringUtils.isNotBlank(timeZone)){
						modificationDateTime.add(Calendar.SECOND,secondsToAdd);
						modificationTimeAdded = TimeChangeUtil.getTimeStringSec
					    	(modificationDateTime,TimeZone.getTimeZone(timeZone));
					}else{
						// Add 1 second
						modificationTimeAdded = formatModifiedTime.format
							(new Date(System.currentTimeMillis()+1000));
					}
					message.setServiceOrderTxtDSModTime(modificationTimeAdded);
				}
				messageList.add(message);
			}
			
			// Set the data to the request object			
			messages.setRequestMessage(messageList);
			order.setMessages(messages);
			orderList.add(order);
			orders.setOrder(orderList);
			requestMsgBody.setOrders(orders);
		}
			} catch (Exception dse) {
				LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
				throw new BusinessServiceException(
						"Exception in setting requstOrder object"
								+ dse.getMessage(), dse);
			}	
			
		return requestMsgBody;
	}
	
	/**
	 * 
	 * @param string
	 * @param partitionSize
	 * @return
	 */
	private static List<String> getParts(String string, int partitionSize) {
        List<String> parts = new ArrayList<String>();
        int len = string.length();
        for (int count=0; count<len; count+=partitionSize){
            parts.add(string.substring(count, Math.min(len, count + partitionSize)));
        }
        return parts;
    }
	
	/**
	 * Counter offer reasons
	 */
	public List<String> getResonCodeDetailsforCounterOffer(Integer soRoutedProviderId)
	{
		List<String> reasonList= null;
		try {
			reasonList = buyerOutBoundNotificationDao
					.getResonCodeDetailsforCounterOffer(soRoutedProviderId);
		} catch (Exception dse) {
			LOGGER.error(
					"Exception in getting buyerOutboundNotification from DB due to "
							+ dse.getMessage(), dse);
			
		}
		return reasonList;		
	}
	//fetch notification entry for the sequence no
	public BuyerOutboundFailOverVO fetchNotification(String sequenceNo)
	throws BusinessServiceException {
		BuyerOutboundFailOverVO failureVO = new BuyerOutboundFailOverVO();
		try {
		
			failureVO = buyerOutBoundNotificationDao.fetchNotification(sequenceNo);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in fetching the VOobject due to"
							+ dse.getMessage(), dse);
		}
		
		return failureVO;
		}
	
	// get value from application_properties based on the key
	public String getPropertyFromDB(String appKey)	throws BusinessServiceException
	{
		try {
			return buyerOutBoundNotificationDao.getPropertyFromDB(appKey);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			throw new BusinessServiceException(
					"Exception in getting app key value due to"
							+ dse.getMessage(), dse);
		}	
	}

	public Integer getBuyerIdForSo(String soId)throws BusinessServiceException
	{
		try {
			return buyerOutBoundNotificationDao.getBuyerIdForSo(soId);
		} catch (DataServiceException dse) {
			LOGGER.error("Exception in fetching data " + dse.getMessage(), dse);
			return null;
		}		
	}

	
}
